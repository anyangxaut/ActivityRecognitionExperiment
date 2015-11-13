package edu.xaut.activityrecognition;

/**
 * LDA（线性判别分析）算法实现
 * 需要JAMAjar包的支持
 * http://www.psychometrica.de/lda.html
 * 
 */
import java.util.ArrayList;

import Jama.Matrix;

public class LDA {
	private double[][] groupMean;
	private double[][] pooledInverseCovariance;
	private double[] probability;
	private ArrayList<Integer> groupList = new ArrayList<Integer>();

	/**
	 * LDA算法主要实现代码：
	 * @param d data---存储数据信息，其数据个数必须与group分组个数一致(7)，即一一对应关系---x(i)
	 * @param g group---分组数据信息---y(i)
	 * @param p Set to true, if the probability estimation should be based on
	 *          the real group sizes (true), or if the share of each group
	 *          should be equal
	 */
	@SuppressWarnings("unchecked")
	public LDA(double[][] d, int[] g, boolean p) {
		
		// 检查数据和分组信息是否一致，即一一对应关系
		if (d.length != g.length)
			return;
		
		// 存储data数据信息{ { 2.95, 6.63 }, { 2.53, 7.79 }, { 3.57, 5.65 },  
		// { 3.16, 5.47 }, { 2.58, 4.46 }, { 2.16, 6.22 }, { 3.27, 3.52 } }
		double[][] data = new double[d.length][d[0].length];
		for (int i = 0; i < d.length; i++) {
			for (int j = 0; j < d[i].length; j++) {
				data[i][j] = d[i][j];
			}
		}
		
		// 存储group数据信息{1,1,1,1,2,2,2}
		int[] group = new int[g.length];
		for (int j = 0; j < g.length; j++) {
			group[j] = g[j];
		}
		
		// 原始数据（不区分类别）均值
		double[] globalMean;
		// 协方差
		double[][][] covariance;

		// 通过groupList存储标签信息1,2
		for (int i = 0; i < group.length; i++) {
			if (!groupList.contains(group[i])) {
				groupList.add(group[i]);
			}
		}

		// 根据标签信息将数据划分为一个个子集，同一子集内的数据信息所对应的标签（类别）相同
		ArrayList<double[]>[] subset = new ArrayList[groupList.size()];
		for (int i = 0; i < subset.length; i++) {
			subset[i] = new ArrayList<double[]>();
			for (int j = 0; j < data.length; j++) {
				if (group[j] == groupList.get(i)) {
					subset[i].add(data[j]);
				}
			}
		}

		// 计算每一个子集（标签1,2）内的中心点，即均值mean
		groupMean = new double[subset.length][data[0].length];
		for (int i = 0; i < groupMean.length; i++) {
			for (int j = 0; j < groupMean[i].length; j++) {
				groupMean[i][j] = getGroupMean(j, subset[i]);
			}
		}

		// 计算原始数据（不区分类别）的均值
		globalMean = new double[data[0].length];
		for (int i = 0; i < data[0].length; i++) {
			globalMean[i] = getGlobalMean(i, data);
		}

		// 将每一个子集内的数据-globalMean，存储在原来的位置,为下一步计算协方差准备好数据
		for (int i = 0; i < subset.length; i++) {
			for (int j = 0; j < subset[i].size(); j++) {
				double[] v = subset[i].get(j);

				for (int k = 0; k < v.length; k++)
					v[k] = v[k] - globalMean[k];

				subset[i].set(j, v);
			}
		}

		// 计算协方差
		covariance = new double[subset.length][globalMean.length][globalMean.length];
		for (int i = 0; i < covariance.length; i++) {
			for (int j = 0; j < covariance[i].length; j++) {
				for (int k = 0; k < covariance[i][j].length; k++) {
					// 为每一个子集计算协方差，因为subset中存储的数据已经减去了globalMean，所以只需要相乘求和取其均值即可
					for (int l = 0; l < subset[i].size(); l++)
						covariance[i][j][k] += (subset[i].get(l)[j] * subset[i]
								.get(l)[k]);

					covariance[i][j][k] = covariance[i][j][k]
							/ subset[i].size();
				}
			}
		}

		// 逆协方差池
		pooledInverseCovariance = new double[globalMean.length][globalMean.length];
		for (int j = 0; j < pooledInverseCovariance.length; j++) {
			for (int k = 0; k < pooledInverseCovariance[j].length; k++) {
				for (int l = 0; l < subset.length; l++) {
					pooledInverseCovariance[j][k] += ((double) subset[l].size() / (double) data.length)
							* covariance[l][j][k];
				}
			}
		}

		pooledInverseCovariance = new Matrix(pooledInverseCovariance).inverse()
				.getArray();

		// 计算测试数据属于不同分组的概率
		this.probability = new double[subset.length];
		// 如果p为false的话，则每种类别的概率相同1/n，n为类别数
		if (!p) {
			double prob = 1.0d / groupList.size();
			for (int i = 0; i < groupList.size(); i++) {
				this.probability[i] = prob;
			}
		} else {
			// 如果p为true的话，则每种类别的概率等于属于该分组的条目数除以总数
			for (int i = 0; i < subset.length; i++) {
				this.probability[i] = (double) subset[i].size()
						/ (double) data.length;
			}
		}
	}

	private double getGroupMean(int column, ArrayList<double[]> data) {
		double[] d = new double[data.size()];
		for (int i = 0; i < data.size(); i++) {
			d[i] = data.get(i)[column];
		}

		return getMean(d);
	}

	private double getGlobalMean(int column, double data[][]) {
		double[] d = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			d[i] = data[i][column];
		}

		return getMean(d);
	}

	/**
	 * 计算不同分组的判别函数值
	 * 
	 * @param values
	 * @return
	 */
	public double[] getDiscriminantFunctionValues(double[] values) {
		double[] function = new double[groupList.size()];
		for (int i = 0; i < groupList.size(); i++) {
			// tmp就是w的转置
			double[] tmp = matrixMultiplication(groupMean[i],
					pooledInverseCovariance);
			function[i] = (matrixMultiplication(tmp, values))
					- (.5d * matrixMultiplication(tmp, groupMean[i]))
					+ Math.log(probability[i]);
		}

		return function;
	}

	/**
	 * 根据马氏距离计算算不同分组的判别函数值
	 * 
	 * @param values
	 * @return
	 */
	public double[] getMahalanobisDistance(double[] values) {
		double[] function = new double[groupList.size()];
		for (int i = 0; i < groupList.size(); i++) {
			double[] dist = new double[groupMean[i].length];
			for (int j = 0; j < dist.length; j++)
				dist[j] = values[j] - groupMean[i][j];
			function[i] = matrixMultiplication(matrixMultiplication(dist,
					this.pooledInverseCovariance), dist);
		}

		return function;
	}

	/**
	 * 通过玛氏距离预测测试数据属于哪个类别
	 * 
	 * @param values
	 * @return the group
	 */
	public int predictM(double[] values) {
		int group = -1;
		double max = Double.NEGATIVE_INFINITY;
		double[] discr = this.getMahalanobisDistance(values);
		for (int i = 0; i < discr.length; i++) {
			if (discr[i] > max) {
				max = discr[i];
				group = groupList.get(i);
			}
		}

		return group;
	}

	/**
	 * 计算测试数据属于不同分组的概率
	 * 
	 * @param values
	 * @return the probabilities
	 */
	public double[] getProbabilityEstimates(double[] values) {
		// TODO
		return new double[] {};
	}

	/**
	 * 计算权值
	 * 
	 * @return the weights
	 */
	public double[] getFisherWeights() {
		
		double[] tmp = null;
		
		for (int i = 0; i < groupList.size(); i++) {
			// tmp就是w的转置
			tmp = matrixMultiplication(groupMean[i],
					pooledInverseCovariance);	
		}
		return tmp;
	}

	/**
	 * 预测一个测试数据属于哪个类别
	 * 
	 * @param values
	 * @return the group
	 */
	public int predict(double[] values) {
		int group = -1;
		double max = Double.NEGATIVE_INFINITY;
		double[] discr = this.getDiscriminantFunctionValues(values);
		for (int i = 0; i < discr.length; i++) {
			if (discr[i] > max) {
				max = discr[i];
				group = groupList.get(i);
			}
		}

		return group;
	}

//	/**
//	 * 两个矩阵相乘并将计算结果以 double[][]-array的形式返回.
//	 * 
//	 * @param a
//	 *            the first matrix
//	 * @param b
//	 *            the second matrix
//	 * @return the resulting matrix
//	 */
//	@SuppressWarnings("unused")
//	private double[][] matrixMultiplication(final double[][] matrixA,
//			final double[][] matrixB) {
//		int rowA = matrixA.length;
//		int colA = matrixA[0].length;
//		int colB = matrixB[0].length;
//
//		double c[][] = new double[rowA][colB];
//		for (int i = 0; i < rowA; i++) {
//			for (int j = 0; j < colB; j++) {
//				c[i][j] = 0;
//				for (int k = 0; k < colA; k++) {
//					c[i][j] = c[i][j] + matrixA[i][k] * matrixB[k][j];
//				}
//			}
//		}
//
//		return c;
//	}

	/**
	 * 两个矩阵相乘并将计算结果以double[]-array形式返回.
	 * 
	 * @param a
	 *            the first matrix
	 * @param b
	 *            the second matrix
	 * @return the resulting matrix
	 */
	public double[] matrixMultiplication(double[] A, double[][] B) {

		if (A.length != B.length) {
			throw new IllegalArgumentException("A:Rows: " + A.length
					+ " did not match B:Columns " + B.length + ".");
		}

		double[] C = new double[A.length];
		for (int i = 0; i < C.length; i++) {
			C[i] = 0.00000;
		}

		for (int i = 0; i < A.length; i++) { // aRow
			for (int j = 0; j < B[0].length; j++) { // bColumn
				C[i] += A[j] * B[i][j];
			}
		}

		return C;
	}

	/**
	 * 两个矩阵相乘并将计算结果以double形式返回 
	 * 
	 * @param a
	 *            the first matrix
	 * @param b
	 *            the second matrix
	 * @return the resulting matrix
	 */
	private double matrixMultiplication(double[] matrixA, double[] matrixB) {

		double c = 0d;
		for (int i = 0; i < matrixA.length; i++) {
			c += matrixA[i] * matrixB[i];
		}

		return c;
	}

//	/**
//	 * 矩阵变换
//	 * 
//	 * @param matrix
//	 *            the matrix to transpose
//	 * @return the transposed matrix
//	 */
//	@SuppressWarnings("unused")
//	private double[][] transpose(final double[][] matrix) {
//		double[][] trans = new double[matrix[0].length][matrix.length];
//		for (int i = 0; i < matrix.length; i++) {
//			for (int j = 0; j < matrix[0].length; j++) {
//				trans[j][i] = matrix[i][j];
//			}
//		}
//
//		return trans;
//	}

//	/**
//	 * 矩阵变换
//	 * 
//	 * @param matrix
//	 *            the matrix to transpose
//	 * @return the transposed matrix
//	 */
//	@SuppressWarnings("unused")
//	private double[][] transpose(final double[] matrix) {
//		double[][] trans = new double[1][matrix.length];
//		for (int i = 0; i < matrix.length; i++) {
//			trans[0][i] = matrix[i];
//		}
//
//		return trans;
//	}

	/**
	 * 返回样本均值. 当数据为null或者length=0时返回NaN.
	 * 
	 * @param values
	 *            The values.
	 * @return The mean.
	 * @since 1.5
	 */
	public static double getMean(final double[] values) {
		if (values == null || values.length == 0)
			return Double.NaN;

		double mean = 0.0d;

		for (int index = 0; index < values.length; index++)
			mean += values[index];

		return mean / (double) values.length;
	}

	/**
	 * 测试代码
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int[] group = { 1, 1, 1, 1, 2, 2, 2 };
		double[][] data = { { 2.95, 6.63 }, { 2.53, 7.79 }, { 3.57, 5.65 },
				{ 3.16, 5.47 }, { 2.58, 4.46 }, { 2.16, 6.22 }, { 3.27, 3.52 } };

		LDA test = new LDA(data, group, true);
		double[] testData = { 2.81, 5.46 };
		
		//test
		double[] values = test.getDiscriminantFunctionValues(testData);
//		double[] values = test.getFisherWeights();
		for(int i = 0; i < values.length; i++){
			System.out.println("Weights " + (i+1) + ": " + values[i]);	
		}
		
		System.out.println("Predicted group: " + test.predict(testData));
	}
	
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//		int[] group = { 1, 1, 1, 1, 2, 2, 2 };
//				
//		LDAData[] ld = new LDAData[7];
//		ld[0] = new LDAData(2.95, 6.63, 2.63);
//		ld[1] = new LDAData(2.53, 7.79, 2.95);
//		ld[2] = new LDAData(3.57, 5.65, 3.53);
//		ld[3] = new LDAData(3.16, 5.47, 3.57);
//		ld[4] = new LDAData(2.58, 4.46, 2.15);
//		ld[5] = new LDAData(2.16, 6.22, 2.55);
//		ld[6] = new LDAData(3.27, 3.52, 3.16);
//		
//		ArrayList<LDAData> data = new ArrayList<LDAData>();
//		for(int i = 0; i < 7; i++){
//			data.add(ld[i]);
//		}
//
//		LDANew test = new LDANew(data, group);
//
//		double[][] values = test.getFisherWeights();
//		for(int i = 0; i < values.length; i++){
//			System.out.println("Class " + (i+1) + ": ");	
//			for(int j = 0; j < values[i].length; j++){
//				System.out.println(values[i][j]);	
//			}
//		}	
//	
//	}

}

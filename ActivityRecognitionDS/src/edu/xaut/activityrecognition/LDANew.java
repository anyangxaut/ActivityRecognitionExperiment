package edu.xaut.activityrecognition;

import java.util.ArrayList;

import Jama.Matrix;
import edu.xaut.entity.LDAData;
/**
 * LDA（线性判别分析）算法实现
 * 需要JAMAjar包的支持
 * http://www.psychometrica.de/lda.html
 * 
 */
public class LDANew {
	
	// 进行特征转换的原始数据维度---3维
	public final static int DiMENSION = 3;
	// 每个类别的均值数据
	private ArrayList<LDAData> groupMean = null;
	// 逆协方差池
	double[][] pooledInverseCovariance = null;
	// 类别标签
	private ArrayList<Integer> groupList = new ArrayList<Integer>();
	
	/**
	 * LDA算法主要实现代码：
	 * @param d data---存储数据信息，其数据个数必须与group分组个数一致(7)，即一一对应关系---x(i)
	 * @param g group---分组数据信息---y(i)
	 */
	@SuppressWarnings("unchecked")
	public LDANew(ArrayList<LDAData> data, int[] group) {
		// TODO Auto-generated constructor stub
		// 检查数据和分组信息是否一致，即一一对应关系
		if (data.size() != group.length)
			return;
			
		// 原始数据（不区分类别）均值
		LDAData globalMean = null;
		// 协方差
		double[][][] covariance = null;
		
		// 通过groupList存储标签信息1,2
		for (int i = 0; i < group.length; i++) {
			if (!groupList.contains(group[i])) {
				groupList.add(group[i]);
			}
		}

		// 根据标签信息将数据划分为一个个子集，同一子集内的数据信息所对应的标签（类别）相同
		ArrayList<LDAData>[] subset = new ArrayList[groupList.size()];
		for (int i = 0; i < subset.length; i++) {
		subset[i] = new ArrayList<LDAData>();
		for (int j = 0; j < data.size(); j++) {
			if (group[j] == groupList.get(i)) {
					subset[i].add(data.get(j));
				}
			}
		}
		
		// 计算每一个子集（标签1,2）内的中心点，即均值mean
		groupMean = new ArrayList<LDAData>();
		for (int i = 0; i < subset.length; i++) {
			groupMean.add(getMean(subset[i]));	
		}		
		
		// 计算原始数据（不区分类别）的均值
		globalMean = getMean(data);
		
		// 将每一个子集内的数据-globalMean，存储在原来的位置,为下一步计算协方差准备好数据
		for (int i = 0; i < subset.length; i++) {
			for (int j = 0; j < subset[i].size(); j++) {
				subset[i].get(j).setSensor1Data(subset[i].get(j).getSensor1Data()-globalMean.getSensor1Data());
				subset[i].get(j).setSensor2Data(subset[i].get(j).getSensor2Data()-globalMean.getSensor2Data());
				subset[i].get(j).setSensor3Data(subset[i].get(j).getSensor3Data()-globalMean.getSensor3Data());
			}
		}		
		
		// 计算协方差
		covariance = new double[subset.length][DiMENSION][DiMENSION];
		for (int i = 0; i < covariance.length; i++) {
			double xxCovariance = 0;
			double xyCovariance = 0;
			double xzCovariance = 0;
			double yyCovariance = 0;
			double yzCovariance = 0;
			double zzCovariance = 0;
			for(int j = 0; j < subset[i].size(); j++){
				xxCovariance += subset[i].get(j).getSensor1Data() * subset[i].get(j).getSensor1Data();
				xyCovariance += subset[i].get(j).getSensor1Data() * subset[i].get(j).getSensor2Data();
				xzCovariance += subset[i].get(j).getSensor1Data() * subset[i].get(j).getSensor3Data();
				yyCovariance += subset[i].get(j).getSensor2Data() * subset[i].get(j).getSensor2Data();
				yzCovariance += subset[i].get(j).getSensor2Data() * subset[i].get(j).getSensor3Data(); 
				zzCovariance += subset[i].get(j).getSensor3Data() * subset[i].get(j).getSensor3Data();
			}
			covariance[i][0][0] = xxCovariance / subset[i].size();
			covariance[i][0][1] = xyCovariance / subset[i].size();
			covariance[i][0][2] = xzCovariance / subset[i].size();
			covariance[i][1][0] = covariance[i][0][1];
			covariance[i][1][1] = yyCovariance / subset[i].size();
			covariance[i][1][2] = yzCovariance / subset[i].size();
			covariance[i][2][0] = covariance[i][0][2];
			covariance[i][2][1] = covariance[i][1][2];
			covariance[i][2][2] = zzCovariance / subset[i].size();
		}		

		// 逆协方差池
		pooledInverseCovariance = new double[DiMENSION][DiMENSION];
		for (int j = 0; j < pooledInverseCovariance.length; j++) {
			for (int k = 0; k < pooledInverseCovariance[j].length; k++) {
				for (int l = 0; l < subset.length; l++) {
					pooledInverseCovariance[j][k] += ((double) subset[l].size() / (double) data.size())
							* covariance[l][j][k];
				}
			}
		}		
		
//		// 计算协方差
//		for (int i = 0; i < subset.length; i++) {
//			double xyCovariance = 0;
//			double yzCovariance = 0;
//			double xzCovariance = 0;
//			for (int j = 0; j < subset[i].size(); j++) {
//				// 为每一个子集计算协方差，因为subset中存储的数据已经减去了globalMean，所以只需要相乘求和取其均值即可
//				xyCovariance += subset[i].get(j).getSensor1Data() * subset[i].get(j).getSensor2Data();
//				yzCovariance += subset[i].get(j).getSensor2Data() * subset[i].get(j).getSensor3Data();
//				xzCovariance += subset[i].get(j).getSensor1Data() * subset[i].get(j).getSensor3Data();
//			}
//			xyCovariance /= subset[i].size();
//			yzCovariance /= subset[i].size();
//			xzCovariance /= subset[i].size();
//			covariance.add(new LDAData(xyCovariance, yzCovariance, xzCovariance));
//		}
		
//		// 逆协方差池
//		double xy = 0;
//		double yz = 0;
//		double xz = 0;
//		for (int i = 0; i < covariance.size(); i++) {
//			xy += ((double)subset[i].size() / (double)data.size()) * covariance.get(i).getSensor1Data();
//			yz += ((double)subset[i].size() / (double)data.size()) * covariance.get(i).getSensor2Data();
//			xz += ((double)subset[i].size() / (double)data.size()) * covariance.get(i).getSensor3Data();
//		}
//		pooledInverseCovariance = new LDAData(xy, yz, xz);

		pooledInverseCovariance = new Matrix(pooledInverseCovariance).inverse()
				.getArray();		
	}
	
	// 计算一个ArrayList数据的均值
	public LDAData getMean(ArrayList<LDAData> ld){
		// 每种类别三种传感器数据之和
		double sum1 = 0;
		double sum2 = 0;
		double sum3 = 0;	
		// 数据个数
		int size = ld.size();
		// 计算结果---均值
		LDAData ldMean = null;
		
		for (int j = 0; j < size; j++) {
			sum1 += ld.get(j).getSensor1Data();
			sum2 += ld.get(j).getSensor2Data();
			sum3 += ld.get(j).getSensor3Data();
		}
		ldMean = new LDAData((sum1/size), (sum2/size), (sum3/size));
		return ldMean;
	}
	
	/**
	 * 计算权值
	 * 
	 * @return the weights
	 */
	public double[][] getFisherWeights() {
		
		double[][] tmp = new double[groupList.size()][];
		
		for (int i = 0; i < groupList.size(); i++) {
			double[] group = new double[DiMENSION];
			group[0] = groupMean.get(i).getSensor1Data();
			group[1] = groupMean.get(i).getSensor2Data();
			group[2] = groupMean.get(i).getSensor3Data();
			// tmp就是w的转置
			tmp[i] = matrixMultiplication(group,
					pooledInverseCovariance);	
		}
		return tmp;
	}
	
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

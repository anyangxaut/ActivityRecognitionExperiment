package edu.xaut.activityrecognition;

import java.util.ArrayList;
import java.util.List;

import edu.xaut.dao.FeatureExtractionDao;
import edu.xaut.daoImpl.FeatureExtractionImpl;
import edu.xaut.entity.DataEntity;

/**
 * // 窗口划分，特征提取(窗口大小64,重叠率50%，均值，方差，相关系数，能量)
 * @author Administrator
 *
 */
public class FeatureExtraction {
	// 传感器个数
	private final int sensorNum;	
	// 窗口大小
	private final int windowSize;
	// 重叠率
	private final double overlap;
	// 数据总量
//	private final int sumData;
	
	// 通过构造方法初始化类属性字段
	public FeatureExtraction(int sensorNum, int windowSize, double overlap) {
		// TODO Auto-generated constructor stub
		 this.sensorNum = sensorNum;
		 this.windowSize = windowSize;
		 this.overlap = overlap;
	}
	
	// 将不同的动作类型数据存储在不同的表中
	public void splitAction(int Locomotion, String tableName){
		// 创建FeatureExtractionDao类
		FeatureExtractionDao dao = new FeatureExtractionImpl();
		// 查询特定动作数据信息的sql语句
		String sqlFind1 = "select * from preprocessingdata where Locomotion=" + Locomotion + ";";
		// 执行查询操作
		List<DataEntity> list = dao.search(sqlFind1);
		// sql插入语句
		String[] sqlAdd = new String[list.size() * sensorNum];
		// sql数组下标
		int index = 0;
		// 循环读取查询到的数据记录
		for(int i = 0; i < list.size(); i++){
			List<String> data = list.get(i).getDataInfo();
			// 传感器1的数据信息
			sqlAdd[index++] = "insert into " + tableName + " (SensorId, AccX, AccY, AccZ, Locomotion) " +
						"values ('1', '" + data.get(1) + "', '" + data.get(2) + "', '" + data.get(3) + "', '" + Locomotion + "');";
			// 传感器2的数据信息
			sqlAdd[index++] = "insert into " + tableName + " (SensorId, AccX, AccY, AccZ, Locomotion) " +
					"values ('2', '" + data.get(4) + "', '" + data.get(5) + "', '" + data.get(6) + "', '" + Locomotion + "');";
			// 传感器3的数据信息
			sqlAdd[index++] = "insert into " + tableName + " (SensorId, AccX, AccY, AccZ, Locomotion) " +
					"values ('3', '" + data.get(7) + "', '" + data.get(8) + "', '" + data.get(9) + "', '" + Locomotion + "');";
		}
		// 执行插入语句
		dao.save(sqlAdd);
		System.out.println(tableName + "表插入完毕！");
	}

	@SuppressWarnings("unchecked")
	public void startFeatureExtraction(int Locomotion, String tableName, int sumData){
		// 创建FeatureExtractionDao类
		FeatureExtractionDao dao = new FeatureExtractionImpl();
		// 计算重叠窗口大小
		final int overlapSzie = (int)(windowSize * overlap);
		// 创建sensorNum个字符串，用来存储查询各个传感器数据的sql语句
		String[] sqlFind = new String[sensorNum];
		// 创建sensorNum个List，用来存储各条sql语句查询到的结果数据信息
		List<DataEntity>[] list = new List[sensorNum];
		
		// 对每种传感器以窗口大小windowSize，重叠率overlap进行窗口切分，并提取其特征值
		for(int i = 0; i < sensorNum; i++){
			// 循环查询各个传感器的数据信息，并进行存储
			sqlFind[i] = "select * from " + tableName + " where SensorId = " + (i+1) + ";";
			list[i] = dao.searchNew(sqlFind[i]);
			
			// 窗口划分
			for(int j = 0; j < sumData; j = j + overlapSzie){
				// 当前窗口下线
				int minWindow = j;
				// 当前窗口上线
				int maxWindow = j + windowSize;
				// 当maxWindow大于sumData时，跳出循环
				if(maxWindow > sumData){break;}
//				// 查询特定窗口大小数据信息的sql语句
//				String sqlFind = "select * from " + tableName + " where Id between " + minWindow + " and " + maxWindow + ";";
//				// 执行查询操作
//				List<DataEntity> list = dao.search(sqlFind);
				List<DataEntity> sensorData = new ArrayList<DataEntity>();
				for(int k = minWindow; k < maxWindow; k++){
					sensorData.add(list[i].get(k));
				}
				// 计算均值-3
				double[] meanValue = means(sensorData);
				// 计算方差-3
				double[] varianceValue = variance(sensorData, meanValue);
				// 计算相关系数-3
				double[] correlationValue = correlation(sensorData, meanValue, varianceValue);
				// 计算能量-3
				double[] energyValue = energy(sensorData);
				String[] sqlAdd = new String[1];
				sqlAdd[0] = "INSERT INTO `featureextraction` (`SensorId`, `AccX_mean`, `AccY_mean`, `AccZ_mean`, " +
						"`AccX_variance`, `AccY_variance`, `AccZ_variance`, `AccX_AccY_correlation`, `AccY_AccZ_correlation`, " +
						"`AccX_AccZ_correlation`, `AccX_energy`, `AccY_energy`, `AccZ_energy`, `locomotion`) VALUES " +
						"(" + (i+1) + ", '" + meanValue[0] + "', '" + meanValue[1] + "', '" + meanValue[2] + "', '" + 
						varianceValue[0] + "', '" + varianceValue[1] + "', '" + varianceValue[2]
						 + "', '" + correlationValue[0] + "', '" + correlationValue[1] + "', '" + correlationValue[2] + 
						 "', '" + energyValue[0] + "', '" + energyValue[1] + "', '" + energyValue[2] + "', '" +
						 Locomotion + "');";

				// 执行插入语句
				dao.save(sqlAdd);
			}
		}

		System.out.println("动作" + tableName + "特征提取完毕！");
	}
	
	// 计算均值
	public double[] means(List<DataEntity> list){
		// 各轴加速度数据之和
		double sum_AccX = 0;
		double sum_AccY = 0;
		double sum_AccZ = 0;
		// 各轴加速度数据均值
		double[] means = new double[3];
		
		// 循环读取查询到的数据记录
		for(int i = 0; i < list.size(); i++){
			List<String> data = list.get(i).getDataInfo();
			// 计算各轴加速度数据之和
			sum_AccX = sum_AccX + Double.parseDouble(data.get(1));
			sum_AccY = sum_AccY + Double.parseDouble(data.get(2));
			sum_AccZ = sum_AccZ + Double.parseDouble(data.get(3));
		}
			
		// 计算各轴加速度数据均值
		means[0] = sum_AccX / windowSize;
		means[1] = sum_AccY / windowSize;
		means[2] = sum_AccZ / windowSize;

		return means;
	}
	
	// 计算方差
	public double[] variance(List<DataEntity> list, double[] meanValue){
		// 各轴加速度数据与均值之差的平方求和
		double square_AccX = 0;
		double square_AccY = 0;
		double square_AccZ = 0;
		// 各轴加速度数据方差
		double[] variance = new double[3];
		
		// 循环读取查询到的数据记录
		for(int i = 0; i < list.size(); i++){
			List<String> data = list.get(i).getDataInfo();
			// 计算各轴加速度数据与均值之差的平方求和
			double tmp = Double.parseDouble(data.get(1))- meanValue[0];
			square_AccX = square_AccX + Math.pow(tmp, 2);
			tmp = Double.parseDouble(data.get(2))- meanValue[1];
			square_AccY = square_AccY + Math.pow(tmp, 2);
			tmp = Double.parseDouble(data.get(3))- meanValue[2];
			square_AccZ = square_AccZ + Math.pow(tmp, 2);
		}
		// 计算各轴加速度数据方差
		variance[0] = square_AccX / windowSize;
		variance[1] = square_AccY / windowSize;
		variance[2] = square_AccZ / windowSize;

		return variance;
	}
	
	// 计算相关系数
	public double[] correlation(List<DataEntity> list, double[] meanValue, double[] varianceValue){
		// 计算协方差*n
		double[] correlation_difference = new double[3];
		// 计算相关系数
		double[] correlation = new double[3];
			

		// 循环读取查询到的数据记录
		for(int i = 0; i < list.size(); i++){
			List<String> data = list.get(i).getDataInfo();
			// 计算x-y轴协方差
			correlation_difference[0] += (Double.parseDouble(data.get(1))- meanValue[0])
					* (Double.parseDouble(data.get(2))- meanValue[1]);
			// 计算y-z轴协方差
			correlation_difference[1] += (Double.parseDouble(data.get(2))- meanValue[1])
					* (Double.parseDouble(data.get(3))- meanValue[2]);
			// 计算x-z轴协方差
			correlation_difference[2] += (Double.parseDouble(data.get(1))- meanValue[0])
					* (Double.parseDouble(data.get(3))- meanValue[2]);
		}		
				
		// 计算x-y轴相关系数
		correlation[0] = (correlation_difference[0] / windowSize) / (Math.sqrt(varianceValue[0]*varianceValue[1]));
		// 计算y-z轴相关系数
		correlation[1] = (correlation_difference[1] / windowSize) / (Math.sqrt(varianceValue[1]*varianceValue[2]));
		// 计算x-z轴相关系数
		correlation[2] = (correlation_difference[2] / windowSize) / (Math.sqrt(varianceValue[0]*varianceValue[2]));
			
		return correlation;
	}
	
	// 计算能量
	public double[] energy(List<DataEntity> list){
		// 数据实部
		Double[] real_AccX = new Double[windowSize];
		Double[] real_AccY = new Double[windowSize];
		Double[] real_AccZ = new Double[windowSize];
		// 数据虚部
		Double[] imag_AccX = new Double[windowSize];
		Double[] imag_AccY = new Double[windowSize];
		Double[] imag_AccZ = new Double[windowSize];
		// 各轴数据的能量
		double[] energy = new double[3];
		// 下标
		int index = 0;

		// 循环读取查询到的数据记录
		for(int i = 0; i < list.size(); i++){
			List<String> data = list.get(i).getDataInfo();
			// 数据实部
			real_AccX[index] = Double.parseDouble(data.get(1));
			real_AccY[index] = Double.parseDouble(data.get(2));
			real_AccZ[index] = Double.parseDouble(data.get(3));
			// 数据虚部
			imag_AccX[index] = new Double(0);
			imag_AccY[index] = new Double(0);
			imag_AccZ[index] = new Double(0);
			index++;
			}	
			// 快速傅里叶变换
			FFT (real_AccX, imag_AccX, windowSize);
			FFT (real_AccY, imag_AccY, windowSize);
			FFT (real_AccZ, imag_AccZ, windowSize);
			// 计算能量
			for (int i = 0;i < windowSize; i++) {
				energy[0] += Math.abs((real_AccX[i])*(real_AccX[i])-(imag_AccX[i])*(imag_AccX[i]));
				energy[1] += Math.abs((real_AccY[i])*(real_AccY[i])-(imag_AccY[i])*(imag_AccY[i]));
				energy[2] += Math.abs((real_AccZ[i])*(real_AccZ[i])-(imag_AccZ[i])*(imag_AccZ[i]));			
			  }
			for(int j = 0; j < 3; j++){
			energy[j] = energy[j] / windowSize;
			}
			
		return energy;
	}
	
	// 快速傅立叶变换FFT
	public void FFT(Double[] xreal, Double[] ximag, int n){
		// 快速傅立叶变换，将复数 x 变换后仍保存在 x 中，xreal, ximag 分别是 x 的实部和虚部
		double[] wreal = new double[n/2];
		double[] wimag = new double[n/2];
		double treal, timag, ureal, uimag, arg;
		int m, k, g, t, index1, index2;
		int i, j, a, b, p;
		
		// 比特（位）反转置换
		for (i = 1, p = 0; i < n; i *= 2)
		{
			p ++;
		}
		for (i = 0; i < n; i ++)
		{
			a = i;
			b = 0;
			for (j = 0; j < p; j ++)
			{
				b = (b << 1) + (a & 1);    // b = b * 2 + a % 2;
				a >>= 1;        // a = a / 2;
			}
			if ( b > i)
			{
				// 将b和i交换
				double tmp;
				tmp = xreal[i];
				xreal[i] = xreal[b];
				xreal[b] = tmp;
				
				tmp = ximag[i];
				ximag[i] = ximag[b];
				ximag [b] = tmp;
			}
		}
		
		// 计算 1 的前 n / 2 个 n 次方根的共轭复数 W'j = wreal [j] + i * wimag [j] , j = 0, 1, ... , n / 2 - 1
		arg = - 2 * Math.PI / n;
		treal = Math.cos (arg);
		timag = Math.sin (arg);
		wreal [0] = 1.0;
		wimag [0] = 0.0;
		for (g = 1; g < n / 2; g ++)
		{
			wreal [g] = wreal [g - 1] * treal - wimag [g - 1] * timag;
			wimag [g] = wreal [g - 1] * timag + wimag [g - 1] * treal;
		}

		for (m = 2; m <= n; m *= 2)
		{
			for (k = 0; k < n; k += m)
			{
				for (g = 0; g < m / 2; g ++)
				{
					index1 = k + g;
					index2 = index1 + m / 2;
					t = n * g / m;    // 旋转因子 w 的实部在 wreal [] 中的下标为 t
					treal = wreal [t] * xreal [index2] - wimag [t] * ximag [index2];
					timag = wreal [t] * ximag [index2] + wimag [t] * xreal [index2];
					ureal = xreal [index1];
					uimag = ximag [index1];
					xreal [index1] = ureal + treal;
					ximag [index1] = uimag + timag;
					xreal [index2] = ureal - treal;
					ximag [index2] = uimag - timag;
				}
			}
		}
	}
	
}

package edu.xaut.activityrecognition;

import java.util.ArrayList;
import java.util.List;

import edu.xaut.dao.DataFusionDao;
import edu.xaut.dao.LDADao;
import edu.xaut.daoImpl.DataFusionImpl;
import edu.xaut.daoImpl.LDAImpl;
import edu.xaut.entity.DataEntity;
import edu.xaut.entity.LDAData;

public class DataFusion {
	
	// 使用LDA方法计算权值
	@SuppressWarnings("unchecked")
	public void startLDA(int Locomotion){
		// 创建Dao类进行数据库操作
		LDADao dao = new LDAImpl();
		// 二分类问题：存储标签为0的动作类型数据信息
		List<DataEntity>[] list0 = new ArrayList[LDANew.DiMENSION];
		String[] sqlFind0 = new String[LDANew.DiMENSION];
		// 二分类问题：存储标签为1的动作类型数据信息
		List<DataEntity>[] list1 = new ArrayList[LDANew.DiMENSION];
		String[] sqlFind1 = new String[LDANew.DiMENSION];
		// 二分类问题：查询存储标签为0的动作类型数据信息
		for(int i = 0; i < LDANew.DiMENSION; i++){
			sqlFind0[i] = "select * from `featureextraction` where Locomotion != " + Locomotion + " and SensorId = " + (i+1) + ";";
			list0[i] = dao.search(sqlFind0[i]);
		}
		// 二分类问题：查询存储标签为1的动作类型数据信息
		for(int i = 0; i < LDANew.DiMENSION; i++){
			sqlFind1[i] = "select * from `featureextraction` where Locomotion = " + Locomotion + " and SensorId = " + (i+1) + ";";
			list1[i] = dao.search(sqlFind1[i]);
		}
		// 计算group数组的大小
		int size = list0[0].size() + list1[0].size();
		// 声明group数组
		int[] group = new int[size];
		// 初始化存储标签为0的动作类型数据分组信息
		for(int i = 0; i < list0[0].size(); i++){
			group[i] = 0;
		}
		// 初始化存储标签为1的动作类型数据分组信息
		for(int i = list0[0].size(); i < group.length; i++){
			group[i] = 1;
		}
		
		// 对每一维特征使用LDA求解对应的权值
		for(int j = 0; j < 12; j++){
		// 初始化原始数据信息
		LDAData[] ld = new LDAData[size];
		// 初始化存储标签为0的动作类型数据分组信息
		for(int i = 0; i < list0[0].size(); i++){
			double sensor1 = Double.parseDouble(list0[0].get(i).getDataInfo().get(j+1));
			double sensor2 = Double.parseDouble(list0[1].get(i).getDataInfo().get(j+1));
			double sensor3 = Double.parseDouble(list0[2].get(i).getDataInfo().get(j+1));
			ld[i] = new LDAData(sensor1, sensor2, sensor3);
		}
		// ld数组下标
		int index = list0[0].size();
		// 初始化存储标签为1的动作类型数据分组信息
		for(int i = 0; i < list1[0].size(); i++){
			double sensor1 = Double.parseDouble(list1[0].get(i).getDataInfo().get(j+1));
			double sensor2 = Double.parseDouble(list1[1].get(i).getDataInfo().get(j+1));
			double sensor3 = Double.parseDouble(list1[2].get(i).getDataInfo().get(j+1));
			ld[index++] = new LDAData(sensor1, sensor2, sensor3);
		}
		// 将数组转换为list
		ArrayList<LDAData> data = new ArrayList<LDAData>();
		for(int i = 0; i < size; i++){
			data.add(ld[i]);
		}
		// 创建LDANew类对象
		LDANew test = new LDANew(data, group);
		// 使用LDA计算权值
		double[][] values = test.getFisherWeights();
//		for(int i = 0; i < values.length; i++){
//		System.out.println("类 " + i + "第" + (j+1) + "个特征: ");	
//		for(int k = 0; k < values[i].length; k++){
//			System.out.println(values[i][k]);	
//		}
//	}
		// 将计算结果存储进数据库
		String[] sqlAdd = new String[1];
		sqlAdd[0] = "INSERT INTO `datafusion` (`Locomotion`, `FeatureId`, `Sensor1`, `Sensor2`, `Sensor3`) VALUES " +
				"(" + Locomotion + "," + (j+1) + ", '" + values[1][0] + "', '" + values[1][1] + "', '" + values[1][2] + "');";

		// 执行插入语句
		dao.save(sqlAdd);
		}
	}

	
	// 利用权值进行特征融合
	@SuppressWarnings("unchecked")
	public void startFusion(int weightType){

		// 创建datafusion表的Dao类进行数据库操作
		DataFusionDao dao = new DataFusionImpl();
		// 融合权值
		List<DataEntity>[] listFusion = new ArrayList[12];
		String[] sqlFindFusion = new String[12];
		for(int i = 0; i < 12; i++){
			sqlFindFusion[i] = "select * from `datafusion` where Locomotion = " + weightType + " and FeatureId = " + (i+1) + ";";
			listFusion[i] = dao.search(sqlFindFusion[i]);
		}
		// 动作类型1的特征数据
		fusion(1, weightType, listFusion);
		fusion(2, weightType, listFusion);
		fusion(4, weightType, listFusion);
		fusion(5, weightType, listFusion);
	}
	
	
	@SuppressWarnings("unchecked")
	public void fusion(int Locomotion, int weightType, List<DataEntity>[] listFusion){
		// 创建featureextraction表的Dao类进行数据库操作
		LDADao dao = new LDAImpl();
		// 特征数据
		List<DataEntity>[] listFeature = new ArrayList[LDANew.DiMENSION];
		String[] sqlFindFeature = new String[LDANew.DiMENSION];
		for(int i = 0; i < LDANew.DiMENSION; i++){
			sqlFindFeature[i] = "select * from `featureextraction` where Locomotion = " + Locomotion + " and SensorId = " + (i+1) + ";";
			listFeature[i] = dao.search(sqlFindFeature[i]);
		}
		// 融合结果
				for(int i = 0; i < listFeature[0].size(); i++){
					double[] fusionResult = new double[12];
					for(int j = 0; j < 12; j++){
						double sensor1 = Double.parseDouble(listFeature[0].get(i).getDataInfo().get(j+1)) * 
								Double.parseDouble(listFusion[j].get(0).getDataInfo().get(0));
						double sensor2 = Double.parseDouble(listFeature[1].get(i).getDataInfo().get(j+1)) * 
								Double.parseDouble(listFusion[j].get(0).getDataInfo().get(1));
						double sensor3 = Double.parseDouble(listFeature[2].get(i).getDataInfo().get(j+1)) * 
								Double.parseDouble(listFusion[j].get(0).getDataInfo().get(2));
						fusionResult[j] = sensor1 + sensor2 + sensor3;
					}
					// 将计算结果存储进数据库
					String[] sqlAdd = new String[1];
					sqlAdd[0] = "INSERT INTO `fusionresult` (`WeightType`, `AccX_mean`, `AccY_mean`, `AccZ_mean`, `AccX_variance`, " +
							"`AccY_variance`, `AccZ_variance`, `AccX_AccY_correlation`, `AccY_AccZ_correlation`, " +
							"`AccX_AccZ_correlation`, `AccX_energy`, `AccY_energy`, `AccZ_energy`, `locomotion`) VALUES " +
							"(" + weightType + ", '" + fusionResult[0] + "', '" + fusionResult[1] + "', '" + fusionResult[2] + "', '" +
							fusionResult[3] + "', '" + fusionResult[4] + "', '" + fusionResult[5] + "', '" + fusionResult[6] + 
							"', '" + fusionResult[7] + "', '" + fusionResult[8] + "', '" + fusionResult[9] + "', '" + 
							fusionResult[10] + "', '" + fusionResult[11] + "', '" + Locomotion + "');";

					// 执行插入语句
					dao.save(sqlAdd);
				}
	}
	
}

package edu.xaut.activityrecognition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import quickml.data.AttributesMap;
import quickml.data.InstanceWithAttributesMap;
import quickml.supervised.classifier.randomForest.RandomForest;
import quickml.supervised.classifier.randomForest.RandomForestBuilder;
import edu.xaut.dao.DSDataFusionDao;
import edu.xaut.daoImpl.DSDataFusionImpl;

/**
 * C4.5决策树分类算法实现
 * @author Administrator
 *
 */
public class DecisionTreeAlgorithm {
	// 特征维度
	private final static int featureDimension = 12;
	// 创建ClassificationAlgorithmsDao类
	private DSDataFusionDao dao = new DSDataFusionImpl();
	// 待填充的训练集
	private List<InstanceWithAttributesMap> trainingSet = new ArrayList<InstanceWithAttributesMap>();
	// 训练好的决策树模型
	private RandomForest randomForest = null;
	
	// C4.5决策树算法:利用训练数据训练C4.5决策树识别模型
	public void trainDecisionTree() {
		// 在WeightType下，选择训练数据
		// 查询相关数据
		String sql = "select * from dsfusion;";
		List<List<Double>> list = dao.search(sql);
		
		// 循环读取训练数据
		for(int i= 0; i < list.size(); i++){
			// 装填每一个特征序列
			AttributesMap map = new AttributesMap();
			for(int j = 0; j < featureDimension; j++){
			map.put("Attribute" + j, list.get(i).get(j));
			}
			// 添加训练数据
			InstanceWithAttributesMap item = new InstanceWithAttributesMap(map, list.get(i).get(featureDimension));
			trainingSet.add(item);
		}

		// 训练决策树模型
		randomForest = new RandomForestBuilder().buildPredictiveModel(trainingSet);
	}
	
	// 对待分类数据进行预测
	public String testDecisionTree(List<Double> test){
		// 装填测试数据
		AttributesMap map = new AttributesMap();
		for(int j = 0; j < featureDimension; j++){
			map.put("Attribute" + j, test.get(j));
			}
		// 选取各动作对应的概率
		HashMap<String,Double> pair=new HashMap<String, Double>();
	
		pair.put("1", randomForest.getProbability(map, 1));
		pair.put("2", randomForest.getProbability(map, 2));
		pair.put("4", randomForest.getProbability(map, 4));
		pair.put("5", randomForest.getProbability(map, 5));

		// 获取最大概率的动作
		Map.Entry<String, Double> maxEntry = null;
		for (Map.Entry<String, Double> entry: pair.entrySet()) {
			if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
		        maxEntry = entry;
		    }
		}
		return (maxEntry.getKey());
	}
	
	// C4.5决策树分类
	public void startDecisionTree(){

 		// 存储测试集数据
 		List<List<Double>> testList = null;
 		
 		// 九折交叉验证
 		for(int i = 0; i < 9; i++){
// 			// 对于动作stand,locomation=1,(1-692)
// 			// 训练DecisionTree模型
//			trainDecisionTree(1);
// 			// 查询测试数据信息的sql语句	
// 			String sqlFindTest = "select * from fusionresult where Id between " + (i*50+1) + " and " + ((i+1)*50) + ";";
// 			// 执行查询操作
// 			testList = dao.search(sqlFindTest);
 			
// 			// 对于动作walk,locomation=2,(693-1384)
// 			// 训练DecisionTree模型
//			trainDecisionTree(2);
// 			// 查询测试数据信息的sql语句	
// 			String sqlFindTest = "select * from fusionresult where Id between " + (i*50+693) + " and " + ((i+1)*50+692) + ";";
// 			// 执行查询操作
// 			testList = dao.search(sqlFindTest);
 			
 			
// 			// 对于动作sit,locomation=4,(1385-2076)
// 		    // 训练DecisionTree模型
//			trainDecisionTree(4);
// 			// 查询测试数据信息的sql语句	
// 			String sqlFindTest = "select * from fusionresult where Id between " + (i*50+1385) + " and " + ((i+1)*50+1384) + ";";
// 			// 执行查询操作
// 			testList = dao.search(sqlFindTest);

 			
 			// 对于动作lie,locomation=5,(2077-2768)
 		    // 训练DecisionTree模型
//			trainDecisionTree(5);
// 			// 查询测试数据信息的sql语句	
// 			String sqlFindTest = "select * from fusionresult where Id between " + (i*50+2077) + " and " + ((i+1)*50+2076) + ";";
// 			// 执行查询操作
// 			testList = dao.search(sqlFindTest);

 			
 			// 正确分类的数据量
 			int correctClassify = 0;
 			
 			// 通过DecisionTree算法进行分类
 			for(int j = 0; j < testList.size(); j++){
 				// 从测试数据列表中取出单个测试数据信息
 				 List<Double> test = testList.get(j);    
// 	             System.out.print("类别为: ");  
// 	             System.out.println(Math.round(Float.parseFloat((knn(trainList, test, 3)))));
 
 				 String decisionTreeClassify = String.valueOf(Double.parseDouble(testDecisionTree(test)));
 				 String realClassify = String.valueOf(test.get(test.size()-1));
 				 if(decisionTreeClassify.equals(realClassify)){
 					 correctClassify++;
 				 }
 			}
 			System.out.println("第" + (i+1) + "轮交叉验证测试数据量为" + testList.size() + "，正确分类数据量为" + correctClassify
 					+ "，识别率为" + ((double)correctClassify / testList.size()));  
 		}
 	
	}
	
	
	    // C4.5-DS决策树分类
		public void startDecisionTreeDS(){

	 		// 存储测试集数据
	 		List<List<Double>> testList = null;
	 		
	 		// 九折交叉验证
	 		for(int i = 0; i < 9; i++){
	 			// 对于动作stand,locomation=1,(1-692)
	 			// 训练DecisionTree模型
				trainDecisionTree();
	 			// 查询测试数据信息的sql语句	
	 			String sqlFindTest = "select * from dsfusion where Id between " + (i*50+1) + " and " + ((i+1)*50) + ";";
	 			// 执行查询操作
	 			testList = dao.search(sqlFindTest);
	 			
	 			
	 			// 正确分类的数据量
	 			int correctClassify = 0;
	 			
	 			// 通过DecisionTree算法进行分类
	 			for(int j = 0; j < testList.size(); j++){
	 				// 从测试数据列表中取出单个测试数据信息
	 				 List<Double> test = testList.get(j);    
	 
	 				 String decisionTreeClassify = String.valueOf(Double.parseDouble(testDecisionTree(test)));
	 				 String realClassify = String.valueOf(test.get(test.size()-1));
	 				 if(decisionTreeClassify.equals(realClassify)){
	 					 correctClassify++;
	 				 }
	 			}
	 			System.out.println("第" + (i+1) + "轮交叉验证测试数据量为" + testList.size() + "，正确分类数据量为" + correctClassify
	 					+ "，识别率为" + ((double)correctClassify / testList.size()));  
	 		}
	 	
		}

}

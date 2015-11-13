package edu.xaut.activityrecognition;

import java.util.List;

import edu.xaut.dao.ClassificationAlgorithmsDao;
import edu.xaut.daoImpl.ClassificationAlgorithmsImpl;

/**
 * 朴素贝叶斯分类算法（连续型）
 * @author Administrator
 *
 */
public class NaiveBayesianAlgorithm {
	// 特征维度
	private final static int featureDimension = 12;
	// 动作stand每一个特征属性的均值和方差
	private double[] meanStand = null;
	private double[] varianceStand = null;
	// 动作walk每一个特征属性的均值和方差
	private double[] meanWalk = null;
	private double[] varianceWalk = null;
	// 动作sit每一个特征属性的均值和方差
	private double[] meanSit = null;
	private double[] varianceSit = null;
	// 动作lie每一个特征属性的均值和方差
	private double[] meanLie = null;
	private double[] varianceLie = null;
	// 创建ClassificationAlgorithmsDao类
	private ClassificationAlgorithmsDao dao = new ClassificationAlgorithmsImpl();

	// NBC算法:利用训练数据训练NBC识别模型
	public void trainNBC(int WeightType) {
		
		// 在WeightType下，对于locomotion = 1
		// 查询相关数据
		String sqlStand = "select * from fusionresult where locomotion = 1 and WeightType = " + WeightType + ";";
		List<List<Double>> listStand = dao.search(sqlStand);
		// 计算均值
		meanStand = getMean(listStand);	
		// 计算方差
		varianceStand = getVariance(listStand, meanStand);
		
		
		// 在WeightType下，对于locomotion = 2
		// 查询相关数据
		String sqlWalk = "select * from fusionresult where locomotion = 2 and WeightType = " + WeightType + ";";
		List<List<Double>> listWalk = dao.search(sqlWalk);
		// 计算均值
		meanWalk = getMean(listWalk);	
		// 计算方差
		varianceWalk = getVariance(listWalk, meanWalk);
		
		
		// 在WeightType下，对于locomotion = 4
		// 查询相关数据
		String sqlSit = "select * from fusionresult where locomotion = 4 and WeightType = " + WeightType + ";";
		List<List<Double>> listSit = dao.search(sqlSit);
		// 计算均值
		meanSit = getMean(listSit);	
		// 计算方差
		varianceSit = getVariance(listSit, meanSit);
		
		
		// 在WeightType下，对于locomotion = 5
		// 查询相关数据
		String sqlLie = "select * from fusionresult where locomotion = 5 and WeightType = " + WeightType + ";";
		List<List<Double>> listLie = dao.search(sqlLie);
		// 计算均值
		meanLie = getMean(listLie);	
		// 计算方差
		varianceLie = getVariance(listLie, meanLie);
		
	}
	
	// 对待分类数据进行预测
	public double testNBC(List<Double> test){
		// 识别率
		double resultStand = 1;
		double resultWalk = 1;
		double resultSit = 1;
		double resultLie = 1;
		
		// 在WeightType下，对于locomotion = 1
		for(int i = 0; i < featureDimension; i++){
			resultStand *= gaussianDensity(meanStand[i], varianceStand[i], test.get(i));
		}
		
		
		// 在WeightType下，对于locomotion = 2
		for(int i = 0; i < featureDimension; i++){
			resultWalk *= gaussianDensity(meanWalk[i], varianceWalk[i], test.get(i));
		}
		
		
		// 在WeightType下，对于locomotion = 4
		for(int i = 0; i < featureDimension; i++){
			resultSit *= gaussianDensity(meanSit[i], varianceSit[i], test.get(i));
		}
		
		
		// 在WeightType下，对于locomotion = 5
		for(int i = 0; i < featureDimension; i++){
			resultLie *= gaussianDensity(meanLie[i], varianceLie[i], test.get(i));
		}
		
		return Max(resultStand, resultWalk, resultSit, resultLie);
	}
	
	// 计算均值
	public double[] getMean(List<List<Double>> listStand){
		// 存放均值的数组
		double[] mean = new double[featureDimension];
		
		for(int i = 0; i < listStand.size(); i++){
			for(int j = 0; j < featureDimension; j++){
				mean[j] += listStand.get(i).get(j);
			}
		}
		for(int i = 0; i < featureDimension; i++){
			mean[i] = mean[i] / listStand.size();
		}
		return mean;
	}
	
	// 计算方差
	public double[] getVariance(List<List<Double>> listStand, double[] mean){
		// 存放方差的数组
		double[] variance = new double[featureDimension];
		
		for(int i = 0; i < listStand.size(); i++){
			for(int j = 0; j < featureDimension; j++){
				variance[j] += Math.pow((listStand.get(i).get(j) - mean[j]), 2);
			}
		}
		for(int i = 0; i < featureDimension; i++){
			variance[i] = variance[i] / listStand.size();
		}
		return variance;
	}

    /** 高斯概率密度
     * 
     * @param mean   样本所在列的均值
     * @param variance   样本方差
     * @param x   样本的取值
     * @return
     */
    public double gaussianDensity(double mean, double variance, double x)
    {
        return 1 / Math.sqrt(2 * Math.PI * variance) * Math.pow(Math.E, -(x - mean) * (x - mean) / (2 * variance));
    }
    
    // 四个值之间比较大小
    public double Max(double a, double b, double c, double d)
    {
        double temp;
        if (a >= b) temp = a; else temp =b;
        if (c >= temp) temp = c;
        if (d >= temp) temp = d;
        
        if(temp == a){
        	return 1;
        } if(temp == b){
        	return 2;
        } if(temp == c){
        	return 4;
        } if(temp == d){
        	return 5;
        }else{
        	return 0;
        }
    }
    
    
 // NBC分类
 	public void startNBC(){
 		// 存储测试集数据
 		List<List<Double>> testList = null;
 		
 		// 10折交叉验证7003
 		for(int i = 0; i < 10; i++){
// 			// 对于动作stand,locomation=1,(1-7003)
// 			// 训练NBC模型
//			trainNBC(1);
// 			// 查询测试数据信息的sql语句	
// 			String sqlFindTest = "select * from fusionresult where Id between " + (i*700+1) + " and " + ((i+1)*700) + ";";
// 			// 执行查询操作
// 			testList = dao.search(sqlFindTest);
 			
// 			// 对于动作walk,locomation=2,(7004-14006)
// 			// 训练NBC模型
//			trainNBC(2);
// 			// 查询测试数据信息的sql语句	
// 			String sqlFindTest = "select * from fusionresult where Id between " + (i*700+7004) + " and " + ((i+1)*700+7003) + ";";
// 			// 执行查询操作
// 			testList = dao.search(sqlFindTest);
 			
 			
// 			// 对于动作sit,locomation=4,(14007-21009)
// 		    // 训练NBC模型
//			trainNBC(4);
// 			// 查询测试数据信息的sql语句	
// 			String sqlFindTest = "select * from fusionresult where Id between " + (i*700+14007) + " and " + ((i+1)*700+14006) + ";";
// 			// 执行查询操作
// 			testList = dao.search(sqlFindTest);

 			
 			// 对于动作lie,locomation=5,(21010-28012)
 		    // 训练NBC模型
			trainNBC(5);
 			// 查询测试数据信息的sql语句	
 			String sqlFindTest = "select * from fusionresult where Id between " + (i*700+21010) + " and " + ((i+1)*700+21009) + ";";
 			// 执行查询操作
 			testList = dao.search(sqlFindTest);

 			
 			// 正确分类的数据量
 			int correctClassify = 0;
 			
 			// 通过NBC算法进行分类
 			for(int j = 0; j < testList.size(); j++){
 				// 从测试数据列表中取出单个测试数据信息
 				 List<Double> test = testList.get(j);    
// 	             System.out.print("类别为: ");  
// 	             System.out.println(Math.round(Float.parseFloat((knn(trainList, test, 3)))));
 
 				 String nbcClassify = String.valueOf(testNBC(test));
 				 String realClassify = String.valueOf(test.get(test.size()-1));
 				 if(nbcClassify.equals(realClassify)){
 					 correctClassify++;
 				 }
 			}
 			System.out.println("第" + (i+1) + "轮交叉验证测试数据量为" + testList.size() + "，正确分类数据量为" + correctClassify
 					+ "，识别率为" + ((double)correctClassify / testList.size()));  
 		}
 	}
 	
 	
//    // NBC-DS分类
//  	public void startNBCDS(){
//  		// 存储测试集数据
//  		List<List<Double>> testList = null;
//  		
//  		// 九折交叉验证
//  		for(int i = 0; i < 9; i++){
//  			// 对于动作stand,locomation=1,(1-692)
//  			// 训练NBC模型
// 			trainNBC();
//  			// 查询测试数据信息的sql语句	
//  			String sqlFindTest = "select * from dsfusion where Id between " + (i*50+1) + " and " + ((i+1)*50) + ";";
//  			// 执行查询操作
//  			testList = dao.search(sqlFindTest);
//  			
//  			// 正确分类的数据量
//  			int correctClassify = 0;
//  			
//  			// 通过NBC算法进行分类
//  			for(int j = 0; j < testList.size(); j++){
//  				// 从测试数据列表中取出单个测试数据信息
//  				 List<Double> test = testList.get(j);    
////  	             System.out.print("类别为: ");  
////  	             System.out.println(Math.round(Float.parseFloat((knn(trainList, test, 3)))));
//  
//  				 String nbcClassify = String.valueOf(testNBC(test));
//  				 String realClassify = String.valueOf(test.get(test.size()-1));
//  				 if(nbcClassify.equals(realClassify)){
//  					 correctClassify++;
//  				 }
//  			}
//  			System.out.println("第" + (i+1) + "轮交叉验证测试数据量为" + testList.size() + "，正确分类数据量为" + correctClassify
//  					+ "，识别率为" + ((double)correctClassify / testList.size()));  
//  		}
//  	}
}

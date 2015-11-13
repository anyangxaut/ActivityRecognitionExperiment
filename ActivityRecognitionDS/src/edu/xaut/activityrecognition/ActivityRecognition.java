package edu.xaut.activityrecognition;

public class ActivityRecognition {
	/**
	 * 行为识别main文件
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// 数据预处理
//		DataPreprocess dataPreprocess = new DataPreprocess("C:\\Users\\Administrator\\Desktop\\anyang\\train\\");
//		dataPreprocess.startPreprocess();
		
		
		// 窗口划分，特征提取
//		FeatureExtraction featureExtraction = new FeatureExtraction(3, 64, 0.5, 5572);
//		// 将不同的动作类型数据存储在不同的表中
//		System.out.println("********************动作类型划分开始**********************");
//		featureExtraction.splitAction(1, "stand");
//		featureExtraction.splitAction(2, "walk");
//		featureExtraction.splitAction(4, "sit");
//		featureExtraction.splitAction(5, "lie");
//		System.out.println("********************动作类型划分结束**********************");
		
//		System.out.println("********************特征提取开始**********************");
//		// 对"stand"动作进行特征提取
//		featureExtraction.startFeatureExtraction(1, "stand");
//		// 对"walk"动作进行特征提取
//		featureExtraction.startFeatureExtraction(2, "walk");
//		// 对"sit"动作进行特征提取
//		featureExtraction.startFeatureExtraction(4, "sit");
//		// 对"lie"动作进行特征提取
//		featureExtraction.startFeatureExtraction(5, "lie");
//		System.out.println("********************特征提取结束**********************");
		
		
		// 数据融合
//		System.out.println("********************数据融合开始**********************");
//		DataFusion df = new DataFusion();
//		df.startLDA(1);
//		df.startLDA(2);
//		df.startLDA(4);
//		df.startLDA(5);
//		// 以动作1为分类目标进行融合
//		df.startFusion(1);
//		// 以动作2为分类目标进行融合
//		df.startFusion(2);
//		// 以动作4为分类目标进行融合
//		df.startFusion(4);
//		// 以动作5为分类目标进行融合
//		df.startFusion(5);
//		System.out.println("********************数据融合结束**********************");
		
		// 分类算法
		System.out.println("********************KNN分类算法开始**********************");
		// 利用KNN进行分类识别
		KNNAlgorithm knn = new KNNAlgorithm();
		knn.startKNNDS();	
		System.out.println("********************KNN分类算法结束**********************");
//		System.out.println("********************NBC分类算法开始**********************");
//		NaiveBayesianAlgorithm nbc = new NaiveBayesianAlgorithm();
//		nbc.startNBCDS();
//		System.out.println("********************NBC分类算法结束**********************");
//		System.out.println("********************C4.5决策树分类算法开始**********************");
//		DecisionTreeAlgorithm dt = new DecisionTreeAlgorithm();
//		dt.startDecisionTreeDS();
//		System.out.println("********************C4.5决策树分类算法结束**********************");
		
		
		//D-S证据理论预备工作之NBC求出各个行为动作的分类概率
//		System.out.println("********************NBC分类算法开始**********************");
//		DSNaiveBayesianAlgorithm ds = new DSNaiveBayesianAlgorithm();
//		ds.startDSNBC();
//		System.out.println("********************NBC分类算法结束**********************");
		
	}

}

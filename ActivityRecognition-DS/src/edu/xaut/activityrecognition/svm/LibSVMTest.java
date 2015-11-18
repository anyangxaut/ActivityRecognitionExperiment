package edu.xaut.activityrecognition.svm;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import edu.xaut.dao.DSDataFusionDao;
import edu.xaut.daoImpl.DSDataFusionImpl;

public class LibSVMTest {

	/**JAVA test code for LibSVM
	 * @author yangliu
	 * @throws IOException 
	 * @blog http://blog.csdn.net/yangliuy
	 * @mail yang.liu@pku.edu.cn
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//Test for svm_train and svm_predict
		//svm_train: 
		//	  param: String[], parse result of command line parameter of svm-train
		//    return: String, the directory of modelFile
		//svm_predect:
		//	  param: String[], parse result of command line parameter of svm-predict, including the modelfile
		//    return: Double, the accuracy of SVM classification

		System.out.println("********************SVM分类算法开始**********************");
		
		LibSVMTest.startSVMDS();
				
		System.out.println("********************SVM分类算法开始**********************");
		
		//Test for cross validation
		//String[] crossValidationTrainArgs = {"-v", "10", "UCI-breast-cancer-tra"};// 10 fold cross validation
		//modelFile = svm_train.main(crossValidationTrainArgs);
		//System.out.print("Cross validation is done! The modelFile is " + modelFile);
	}
	
	
	
	public static void startSVMDS(){
		// 创建DSDataFusionDao类
		DSDataFusionDao dao = new DSDataFusionImpl();
		// 存储训练集数据
		List<List<Double>> trainList = null;
		// 存储测试集数据
		List<List<Double>> testList = null;
		
		// 10折交叉验证
		for(int i = 9; i < 10; i++){
			// 查询测试数据信息的sql语句	
			String sqlFindTest = "select * from dsfusion where Id between " + (i*2802+1) + " and " + ((i+1)*2802) + ";";
			// 执行查询操作
			testList = dao.search(sqlFindTest);
			
			// 查询训练数据信息的sql语句(前半部分)
			String sqlFindTrain1 = "select * from dsfusion where Id between 0 and " + (i*2802) + ";";
			// 执行查询操作
			trainList = dao.search(sqlFindTrain1);
			// 查询训练数据信息的sql语句(后半部分)
			String sqlFindTrain2 = "select * from dsfusion where Id between " + ((i+1)*2802+1) + " and 28025;";
			// 执行查询操作
			trainList.addAll(dao.search(sqlFindTrain2));
			
			// 向UCI-breast-cancer-test文件写入测试数据
			saveDataAsFile(testList, "UCI-breast-cancer-test");
			
			
			// 向UCI-breast-cancer-tra文件写入测试数据
			saveDataAsFile(trainList, "UCI-breast-cancer-tra");
						
			
			try {
				String[] trainArgs = {"-b", "1", "UCI-breast-cancer-tra"};
				String modelFile = svm_train.main(trainArgs);
				String[] testArgs = {"-b", "1", "UCI-breast-cancer-test", modelFile, "UCI-breast-cancer-result"};//directory of test file, model file, result file
				Double accuracy = svm_predict.main(testArgs);
				System.out.println("SVM Classification is done! The accuracy is " + accuracy);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.toString());
			}
			
					
		}
	
	}
	
	
	  // 文件存储
		private static boolean saveDataAsFile(List<List<Double>> dataList, String fileName){
			
			FileWriter writer;
			try {
				// 通过保存文件的路径及其文件名称初始化FileWriter对象
				writer = new FileWriter(fileName, true);
				// 向文件写入测试数据
				for(int j = 0; j < dataList.size(); j++){
					// 从测试数据列表中取出单个测试数据信息
					 List<Double> test = dataList.get(j); 
					 StringBuilder sb = new StringBuilder();
					 int size = test.size() - 1;
					 sb.append(test.get(size));
					 sb.append("	");
					 for(int k = 0; k < size; k++){
						 sb.append((k+1) + ":");
						 sb.append(test.get(k));
						 sb.append("	");
					 }
					 writer.write(sb.toString()  + "\n");
				}
							
				writer.close();
				return true;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

}

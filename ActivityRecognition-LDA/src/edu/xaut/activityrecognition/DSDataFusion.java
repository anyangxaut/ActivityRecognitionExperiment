package edu.xaut.activityrecognition;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import edu.xaut.entity.DataEntity;
/**
 * 为DS证据理论后续处理做准备
 * 将featureextraction表中的数据按SensorId进行划分，并存储在为文件中
 * @author Administrator
 *
 */
public class DSDataFusion {
	// 传感器个数
	private final static int SENSORNUM = 3;
	
//	public static void main(String[] args) {
//		// 创建Dao类进行数据库操作
//		DSDataFusionDao dao = new DSDataFusionImpl();
//		// 二分类问题：查询存储标签为0的动作类型数据信息
//		for(int i = 0; i < SENSORNUM; i++){
//			String sqlFind = "select * from `featureextraction` where SensorId = " + (i+1) + ";";
//			List<DataEntity> list = dao.search(sqlFind);
//			saveDataAsFile(list, "dsdata" + (i+1));
//		}
//	}
	
	// 将处理后的数据进行文件存储
	private static boolean saveDataAsFile(List<DataEntity> dataList, String fileName){
		
		FileWriter writer;
		try {
			// 通过保存文件的路径及其文件名称初始化FileWriter对象
			writer = new FileWriter("C:\\Users\\Administrator\\Desktop\\anyang\\" + fileName + ".txt",true);
			// 将预处理后的原始数据逐条进行存储
			for(int i = 0; i < dataList.size();i++){
				writer.write(dataList.get(i).getDataInfo().toString() + "\n");
//				System.out.println(dataList.get(i).getDataInfo().get(10));
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

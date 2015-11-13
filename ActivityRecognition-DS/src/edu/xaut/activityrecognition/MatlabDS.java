package edu.xaut.activityrecognition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.xaut.dao.PreprocessingDataSaveDao;
import edu.xaut.daoImpl.PreprocessingDataSaveImpl;
import edu.xaut.entity.DataEntity;

public class MatlabDS {

	public void startMatlabDS(){
		preprocessing("C:\\Users\\Administrator\\Desktop\\version1\\DSfusion.txt");
	}
	
	private void preprocessing(String filePath){

		
		List<DataEntity> dataList = new ArrayList<DataEntity>();
		int counter1 = 0;
		
		// 根据文件目录及其名称创建File对象
		File dataFile = new File(filePath);
		
		// 判断文件是否存在，且其属性是否为file
		 if(dataFile.exists() && dataFile.isFile()){ 
			 
			 try {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(dataFile));
				String dataLine = "";
				String[] wholeData = null;
				try {
						// 按行读取数据集中的数据条目
					while((dataLine =  bufferedReader.readLine()) != null){
						// 创建新的List对象
						// 这里需要注意，必须重新创建一个新的new ArrayList<String>()赋值给useDate，否则当利用useDate
						// 创建DataEntity对象，并添加至dataList.add(dataEntity);中时，会由于引用了同一个useDate对象而使得dataList
						// 中的值都变成相同的（因为都引用了同一个对象地址），因此为了避免这种现象，需要新建一个useDate引用
						List<String> useData = new ArrayList<String>();
						// 将数据集中的数据条目按照空格分隔开来，并存储在数组中(0~249)
						wholeData = dataLine.split("	");
						// 使用ArrayList存储wholeData中第0~9，243列数据
						for(int i = 0; i < wholeData.length; i++){
							useData.add(wholeData[i]);
							
						}
						// 利用useData创建DataEntity对象，该对象存储即将要使用到的数据信息，我们将这些数据视为原始数据item
						DataEntity dataEntity = new DataEntity(useData);
						// DataEntity对象的集合，为原始数据集合
						dataList.add(dataEntity);
						// 计数器
						counter1++;
					
					}
						// 输出信息
						System.out.print(filePath + "原始数据集数据共" + counter1 + "项！");
						// 将预处理后的原始数据进行数据库存储
						saveDataAsDatabase(dataList);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    } else{
		    	// 要打开的文件不存在或者该文件属性不是一个文件
		    	System.out.println(dataFile.getName() + "is not exist or not a file!");
		    }
	
	}
	
	
	// 将预处理后的原始数据进行数据库存储
		private boolean saveDataAsDatabase(List<DataEntity> dataList){
			// 返回值
			boolean result = false;
			// 要执行的sql语句
			String[] sql = new String[dataList.size()];
			for(int i = 0; i < dataList.size(); i++){
				List<String> items = dataList.get(i).getDataInfo();
				sql[i] = "INSERT INTO `dsfusion` (`AccX_mean`, `AccY_mean`, `AccZ_mean`, `AccX_variance`, " +
						"`AccY_variance`, `AccZ_variance`, `AccX_AccY_correlation`, `AccY_AccZ_correlation`, " +
						"`AccX_AccZ_correlation`, `AccX_energy`, `AccY_energy`, `AccZ_energy`, `locomotion`) " +
						"VALUES ('" + items.get(0) + "', '" + items.get(1) + "', '" + items.get(2) + "', " +
								"'" + items.get(3) + "', '" + items.get(4) + "', '" + items.get(5) + "', " +
										"'" + items.get(6) + "', '" + items.get(7) + "', '" + items.get(8) + "', " +
												"'" + items.get(9) + "', '" + items.get(10) + "', '" + items.get(11) + 
												"', '1');";
			}
			
			// 创建PreprocessingDataSaveDao对象
			PreprocessingDataSaveDao dao = new PreprocessingDataSaveImpl();
			// 保存预处理后的数据信息
			result = dao.save(sql);
			// 返回result
			return result;
		}
}

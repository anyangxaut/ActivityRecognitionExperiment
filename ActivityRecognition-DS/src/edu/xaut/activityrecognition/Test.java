package edu.xaut.activityrecognition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.xaut.dao.ClassificationAlgorithmsDao;
import edu.xaut.daoImpl.ClassificationAlgorithmsImpl;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		preprocessing("C:\\Users\\Administrator\\Desktop\\version1\\NBC\\finalresult.txt");
	}
	
	private static void preprocessing(String filePath){

		
		List<Integer> dataList = new ArrayList<Integer>();
		
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
						// 将数据集中的数据条目按照空格分隔开来，并存储在数组中(0~249)
						wholeData = dataLine.split("	");
						dataList.add(Max(wholeData[0], wholeData[1], wholeData[2], wholeData[3]));
					}
					
					realClassify(dataList);
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
		    	System.out.println(dataFile.getName() + " is not exist or not a file!");
		    }
	
	}
	
	
	 // 四个值之间比较大小
    public static int Max(String a1, String b1, String c1, String d1)
    {
    	double a = Double.parseDouble(a1);
    	double b = Double.parseDouble(b1);
    	double c = Double.parseDouble(c1);
    	double d = Double.parseDouble(d1);
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
    
    
    
  	public static void realClassify(List<Integer> dataList){
  		ClassificationAlgorithmsDao dao = new ClassificationAlgorithmsImpl();
  		
  		// 存储测试集数据
  		List<List<Double>> testList = null;
  		
  			// 查询测试数据信息的sql语句	
  			String sqlFindTest = "select * from featureextraction where SensorId = 1 and Id % 4 = 0";
  			// 执行查询操作
  			testList = dao.search(sqlFindTest);
  		
  			
  			// 正确分类的数据量
  			int correctClassify = 0;
  			
  			// 通过NBC算法进行分类
  			for(int j = 0; j < dataList.size(); j++){
  				// 从测试数据列表中取出单个测试数据信息
  				 List<Double> test = testList.get(j);    
  				 String realClassify = String.valueOf(test.get(test.size()-1));
  				 
  				 String nbcClassify = String.valueOf((double)dataList.get(j));
  				
  				 if(nbcClassify.equals(realClassify)){
  					 correctClassify++;
  				 }
  			}
  			System.out.println("测试数据量为" + dataList.size() + "，正确分类数据量为" + correctClassify
  					+ "，识别率为" + ((double)correctClassify / testList.size()));  
  	}

}

package edu.xaut.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.xaut.dao.FeatureExtractionDao;
import edu.xaut.entity.DataEntity;
import edu.xaut.util.DBOperation;

public class FeatureExtractionImpl implements FeatureExtractionDao{

	// 查询数据库中的数据库,并以list的形式返回数据信息
	public List<DataEntity> search(String sql) {
		// TODO Auto-generated method stub
		// 创建返回值对象
		List<DataEntity> result = new ArrayList<DataEntity>();
		// 创建数据库操作类对象--增删查改
		DBOperation dboperation = new DBOperation ();
		// 调用DBOperation对象的findsql方法执行sql语句---查
		ResultSet rs = dboperation.findsql(sql);
		
			try {
				// 循环读取查询到的数据记录
				while (rs != null && rs.next() == true){
					// 将每条数据记录存储在list中
					List<String> data = new ArrayList<String>();
					data.add(rs.getString(2));
					data.add(rs.getString(3));
					data.add(rs.getString(4));
					data.add(rs.getString(5));
					data.add(rs.getString(6));
					data.add(rs.getString(7));
					data.add(rs.getString(8));
					data.add(rs.getString(9));
					data.add(rs.getString(10));
					data.add(rs.getString(11));
					data.add(rs.getString(12));
					// 讲数据记录添加至返回值列表
					result.add(new DataEntity(data));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		// 关闭数据库连接
		dboperation.closeConn();
		// 返回查询到的数据信息
		return result;
	}

	// 将特征提取后的数据保存至mysql数据库中
	public boolean save(String[] sql) {
		// TODO Auto-generated method stub
		// 创建数据库操作类对象--增删查改
		DBOperation dboperation = new DBOperation ();
		// 调用DBOperation对象的excutesql方法执行sql语句---增删改
		boolean rs = dboperation.excutesql(sql);
		// 关闭数据库连接
		dboperation.closeConn();
		// sql语句是否执行成功
		return rs;
	}

}

package edu.xaut.dao;

import java.util.List;

/**
 * 接口类：定义操作数据库的方法
 * @author Administrator
 *
 */
public interface DSDataFusionDao {
		// 查询数据库中的数据库,并以List的形式返回数据信息(区分sensor)
	   public List<List<Double>> search(String sql);
		// 将特征提取后的数据保存至mysql数据库中
		public boolean save(String[] sql);
}

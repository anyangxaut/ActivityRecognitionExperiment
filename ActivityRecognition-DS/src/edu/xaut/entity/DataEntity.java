package edu.xaut.entity;

import java.util.List;

/**
 * 存储数据集中每条数据项的第1~10列，244列数据
 * @author Administrator
 *
 */
public class DataEntity {

	private List<String> dataInfo = null;
	
	public DataEntity(List<String> dataInfo) {
		// TODO Auto-generated constructor stub
		this.dataInfo = dataInfo;
	}

	public List<String> getDataInfo() {
		return dataInfo;
	}

}

package com.ait.pa.dao;

public interface salaryCheckDao {
/**
 * 根据公司id和项目item_no来确定该法人对该条公司代码的使用状态
 */
	@SuppressWarnings("unchecked")
	public int findTypeByParam(String cpny_id,String item_no);
}

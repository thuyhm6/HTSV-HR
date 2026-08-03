package com.ait.ar.dao;

import java.util.List;

public interface RecordTestListDao {
//查询查看打卡记录
	@SuppressWarnings("unchecked")
	public List getRecordTestList(Object object);
	
	@SuppressWarnings("unchecked")
	public int getRecordTestCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getRecordTestList(Object object, int currentPage, int pageSize);
	
	
}

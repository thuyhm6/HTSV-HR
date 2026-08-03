package com.ait.ar.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface RecordTestListSer {
	/**查询打卡记录*/

	@SuppressWarnings("unchecked")
	public List getRecordTestList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getRecordTestCnt(HttpServletRequest request) ;
}

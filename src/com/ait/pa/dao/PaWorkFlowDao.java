package com.ait.pa.dao;

import java.util.List;


public interface PaWorkFlowDao {
	
	@SuppressWarnings("unchecked")
	public Object getPaWorkFlowInfoByScheduleNo(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public String execPaWorkFlow(Object object)  throws Exception;
	
	@SuppressWarnings("unchecked")
	public int getPayObjNumByScheduleNo(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List getPaWorkFlowOperationRecordList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public int getPaWorkFlowOperationRecordListCnt(Object object);
	
}

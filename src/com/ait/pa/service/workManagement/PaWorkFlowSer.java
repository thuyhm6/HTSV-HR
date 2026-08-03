package com.ait.pa.service.workManagement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


public interface PaWorkFlowSer {	

	@SuppressWarnings("unchecked")
	public Object getPaWorkFlowInfoByScheduleNo(HttpServletRequest request,String scheduleNo) ;
	
	@SuppressWarnings("unchecked")
	public String execPaWorkFlow(HttpServletRequest request) ;

	@SuppressWarnings("unchecked")
	public int getPayObjNumByScheduleNo(String scheduleNo) ;
	
	@SuppressWarnings("unchecked")
	public List getPaWorkFlowOperationRecordList(HttpServletRequest request) ;	
	
	@SuppressWarnings("unchecked")
	public int getPaWorkFlowOperationRecordListCnt(HttpServletRequest request) ;	
	
}

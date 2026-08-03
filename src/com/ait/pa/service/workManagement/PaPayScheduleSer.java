package com.ait.pa.service.workManagement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


public interface PaPayScheduleSer {
	
	@SuppressWarnings("unchecked")
	public List getPayScheduleList(HttpServletRequest request) ;	
	
	@SuppressWarnings("unchecked")
	public List getPayScheduleAllList(HttpServletRequest request) ;	
	
	@SuppressWarnings("unchecked")
	public List getPayScheduleAllWithPaConfirmList(HttpServletRequest request) ;	
	
	@SuppressWarnings("unchecked")
	public int getPayScheduleCnt(HttpServletRequest request) ;	
	
	@SuppressWarnings("unchecked")
	public int addPaPayScheduleInfo(HttpServletRequest request) ;	

	@SuppressWarnings("unchecked")
	public Object getPaPayScheduleInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updatePaPayScheduleInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int confirmOrRelievePaySchedule(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int doDeletePayScheduleInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List dayPersonCountInfoList(HttpServletRequest request) ;	
	
	 
	
	
	
}

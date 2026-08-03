package com.ait.pa.dao;

import java.util.LinkedHashMap;
import java.util.List;


public interface PaPayScheduleDao {
	
	@SuppressWarnings("unchecked")
	public List getPayScheduleList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getPayScheduleAllList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPayScheduleAllWithPaConfirmList(Object object);
	
	@SuppressWarnings("unchecked")
	public int getPayScheduleCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public void addPaPayScheduleInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public Object getPaPayScheduleInfo(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public void updatePaPayScheduleInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void confirmOrRelievePaySchedule(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void doDeletePayScheduleInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List dayPersonCountInfoList(Object object);
	
	

	
	
}

package com.ait.ar.dao;

import java.util.List;
import java.util.Map;


public interface ArShiftGroupHistoryDao {
	
	@SuppressWarnings("unchecked")
	public List getArBaseEmpInfoList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public int getArBaseEmpInfoCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public Object getArBaseEmpInfoDetail(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public Object getArEmpShiftGroupFinalInfo(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public int updateArBaseEmpInfoDetail(Object object)throws Exception ;
	
	/**
	 * 班组履历的查询
	 */
	@SuppressWarnings("unchecked")
	public List getArShiftRecordCheckList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public int getArShiftRecordCheckListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public Object getArShiftRecordCheckInfoDetail(Object obj) ;
	
	/**
	 * 班组的月别列表查询
	 */
	@SuppressWarnings("unchecked")
	public List getArClassCalendarListForNormalShift(Object object, int currentPage, int pageSize);
		
	@SuppressWarnings("unchecked")
	public int getArShiftMonthCheckListViewHtmlCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getArClassCalendarList(Object object, int currentPage, int pageSize);
}

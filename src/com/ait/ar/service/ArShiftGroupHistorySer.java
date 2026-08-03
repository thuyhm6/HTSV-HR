package com.ait.ar.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


public interface ArShiftGroupHistorySer {
	
	@SuppressWarnings("unchecked")
	public List getArBaseEmpInfoList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int getArBaseEmpInfoCnt(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public Object getArBaseEmpInfoDetail(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public Object getArEmpShiftGroupFinalInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updateArBaseEmpInfoDetail(HttpServletRequest request);
	
	/*
	 * 班组履历的查询
	 */
	@SuppressWarnings("unchecked")
	public List getArShiftRecordCheckList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int getArShiftRecordCheckListCnt(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public Object getArShiftRecordCheckInfoDetail(HttpServletRequest request) ;
	
	/**
	 * 班组的月别列表查询
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArShiftMonthCheckListViewHtml(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getArShiftMonthCheckListViewHtmlCnt(HttpServletRequest request);
			
}

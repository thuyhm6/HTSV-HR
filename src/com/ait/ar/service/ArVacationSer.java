package com.ait.ar.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


public interface ArVacationSer {
	
	@SuppressWarnings("unchecked")
	public List getArVacationLiquidationList(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public int getArVacationLiquidationCnt(HttpServletRequest request) ;
	
	
	@SuppressWarnings("unchecked")
    public List getArVacationUpdateMonthList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
    public List getArVacationMonthExcel(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public String monthVacCalculate(HttpServletRequest request) ;
	
	
	@SuppressWarnings("unchecked")
	public int saveVacationLiquidation(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getLeaveViewList(HttpServletRequest request);
	public int RetrieveAttStatus(HttpServletRequest request);
	
	public int monthVacationCnt(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public String monthVacCreate(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public int getArVacationUpdateYearCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getArVacationUpdateYearList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getLeaveViewListCnt(HttpServletRequest request) ;
	
	
	
	
	@SuppressWarnings("unchecked")
	public int updateArVacationYear(HttpServletRequest request);
	
	public int yearVacationCnt(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public String yearVacCalculate(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public String yearVacCreate(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getArVacationNextCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getArVacationNextList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public String nextVacationMove(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public String getTAWelfare(HttpServletRequest request) ;
	
}

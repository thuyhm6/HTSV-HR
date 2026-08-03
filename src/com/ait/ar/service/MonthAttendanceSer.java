package com.ait.ar.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: MonthAttendanceSer.java
 * @Description:
 * @Create date: 2012-5-16 下午08:37:44
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface MonthAttendanceSer {
	
	public String makeDataTable(HttpServletRequest request, String str) ;

	@SuppressWarnings("unchecked")
	public List getMonthWorkSchedule(HttpServletRequest request) throws Exception;
	
	public int getMonthWorkScheduleCnt(HttpServletRequest request) throws Exception;
	
	public int getMonthWorkListCnt(HttpServletRequest request) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int noSwipingCardListCnt(HttpServletRequest request) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getMonthWorkScheduleExcel(HttpServletRequest request) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getMonthWorkList(HttpServletRequest request) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List noSwipingCardList(HttpServletRequest request) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getEveryDayWorkList(HttpServletRequest request) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getAnnualUsage(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getAnnualUsageCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getAnnualUsageExcel(HttpServletRequest request) throws Exception;

	public List getMonthControlList(HttpServletRequest request) throws Exception;

	public int getMonthControlListCnt(HttpServletRequest request) throws Exception;

	public Map getControlItemMap(HttpServletRequest request) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getMonthWorkNewOne(HttpServletRequest request) throws Exception;

	public List getAnnualUsageFact(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List getArAfrimExceptionList(HttpServletRequest request) throws Exception;

	public int getArAfrimExceptionCnt(HttpServletRequest request) throws Exception;
	@SuppressWarnings("unchecked")
	public List getArExceptionTempList(HttpServletRequest request)throws Exception;
	@SuppressWarnings("unchecked")
	public int getArExceptionTempCnt(HttpServletRequest request, String errorFlag);


	public List getDetailControlList(HttpServletRequest request);

	public int getDetailControlListCnt(HttpServletRequest request) throws Exception;


	public List getAllApplyList(HttpServletRequest request);

	public int getAllApplyListCnt(HttpServletRequest request) throws Exception;

	List getItemForApplyList(HttpServletRequest request);

}

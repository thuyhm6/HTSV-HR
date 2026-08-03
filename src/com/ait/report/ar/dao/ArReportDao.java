package com.ait.report.ar.dao;

import java.util.List;
import java.util.Map;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArReportDao.java
 * @Description: interface Class ArReportDao.java
 * @Create date: April 14, 2012 3:29:38 PM
 * @Create by: hanzhe (hanzhe@ait.net.cn)
 * @version 5.1
 */
public interface ArReportDao {
	
	@SuppressWarnings("unchecked")
	public List getCodeListByParentCode(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getCodeListByParentCode(Object object);
	
	@SuppressWarnings("unchecked")
	public List getArCodeNameByCode(Object object);
	
	@SuppressWarnings("unchecked")
	public List getreportList(Object object);

	
	/**
	 * 按部门查出员工考勤  导出excel用
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArDeptShiftList(Map paramMap);

	/**
	 * 查找公司日历排班
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArCompanyCalendarShiftList(Map paramMap);
	
	
	
	
	@SuppressWarnings("unchecked")
	public List getHrPersonFormalList(Object object);
	@SuppressWarnings("unchecked")
	public List getHrPersonEntryList(Object object);
	@SuppressWarnings("unchecked")
	public List getHrPersonEntryList1(Object object);
	@SuppressWarnings("unchecked")
	public List getHrPersonRankList(Object object);
	@SuppressWarnings("unchecked")
	public List getHrPersonDeptList(Object object);
	
	
}

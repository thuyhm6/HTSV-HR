package com.ait.report.ar.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: ArReportSer.java
 * @Description: interface Class ArReportSer.java
 * @Create date: April 14, 2012 3:29:38 PM
 * @Create by: hanzhe (hanzhe@ait.net.cn)
 * @version 5.1
 */
public interface ArReportSer {
	@SuppressWarnings("unchecked")
	public List getCodeListByParentCode(HttpServletRequest request,
			String parent_code_no);

	@SuppressWarnings("unchecked")
	public List getArCodeNameByCode(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List getreportList(HttpServletRequest request);

	/**
	 * 按部门查出员工考勤 导出excel用
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArDeptShiftList(Map paramMap);

	/**
	 * 查找公司日历排班
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArCompanyCalendarShiftList(Map paramMap);

	/**
	 * 正规非正规区分
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getHrPersonFormalList(HttpServletRequest request);

	/**
	 * 入社增减区分
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getHrPersonEntryList(HttpServletRequest request);

	/**
	 * 职级别人力
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getHrPersonRankList(HttpServletRequest request);

	/**
	 * part别增减情况
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getHrPersonDeptList(HttpServletRequest request);

}

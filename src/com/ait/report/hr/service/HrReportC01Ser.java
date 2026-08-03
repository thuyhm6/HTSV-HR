package com.ait.report.hr.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: HrReportSer.java
 * @Description: interface Class HrReportSer.java
 * @Create date: April 14, 2012 3:29:38 PM
 * @Create by: hanzhe (hanzhe@ait.net.cn)
 * @version 5.1
 */
@SuppressWarnings("unused")
public interface HrReportC01Ser {
	
	@SuppressWarnings("unchecked")
	public List getGradeLevelList (HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getEmpPaRiseExcelList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getEmpPaRiseListCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getEmpOnStatusExcelList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPersonalStatusInfoList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getPersonalStatusInfoListCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPersonalStatusInfoExcelList(HttpServletRequest request) ;
	
}

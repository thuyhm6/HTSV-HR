package com.ait.ar.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: EmpCalendarSer.java
 * @Description: implement Class EmpCalendarSerImp.java
 * @Create date: 2012-2-6 上午10:50:53
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface EmpCalendarSer {
	//EmpCalendarSerImp
	@SuppressWarnings("unchecked")
	public String getEmpCalendarViewHtml(HttpServletRequest request) ;
	public String getEmpCalendarViewHtmlPer(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updateEmpCalendarInfo(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public Object getEmpInfo(HttpServletRequest request) ;
	public Object getBigDistinct();
	@SuppressWarnings("unchecked")
	public int updateClassCalendarInfo(HttpServletRequest request) ;
	
	public int createArDEtailClassCalendarInfo(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public List viewEmpCalendarCheckInfoList(String target, HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public String submitImportExcelCalendarData (HttpServletRequest request, String target);
}

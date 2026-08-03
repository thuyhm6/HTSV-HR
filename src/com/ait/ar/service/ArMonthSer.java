package com.ait.ar.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArMonthSer.java
 * @Description: implement Class ArMonthSerImp.java
 * @Create date: 2012-2-11 下午03:07:54
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ArMonthSer {
	
	@SuppressWarnings("unchecked")
	public List getArColumns(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public List getArColumnsYN(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
    public List getArMonthList(HttpServletRequest request, List list, String str);
	
	@SuppressWarnings("unchecked")
    public List getArMonthListYN(HttpServletRequest request, List list, String str);
	
	@SuppressWarnings("unchecked")
	public int getArMonthListCnt(HttpServletRequest request, String str);
	@SuppressWarnings("unchecked")
	public String retrieveMonthlyStatus(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public int updateArMonthInfo(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public List getMonthlyStatusList(HttpServletRequest request, String str);
	@SuppressWarnings("unchecked")
	public List getMonthlyStatusList(HttpServletRequest request, String str, String statNo);
	
	public Object getLastDayByMonth(String monthStr);
	
	@SuppressWarnings("unchecked")
	public String makeTableHTML(String name,List Clise,List Dlist);
	@SuppressWarnings("unchecked")
	public List getArMonthEssListYN(HttpServletRequest request,
			List getArColumnsListYN, String arMonth);
	
	
	 
	
}

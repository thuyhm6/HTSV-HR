package com.ait.ar.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: SummaryFormulaSer.java
 * @Description: implement Class SummaryFormulaSerImp.java
 * @Create date: 2012-1-13 下午06:09:28
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface SummaryFormulaSer {
 
	@SuppressWarnings("unchecked")
	public List getSummaryFormulaItemList(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public List getsummaryFormulaList(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public List getSummaryFormulaItemToCN() ;
	@SuppressWarnings("unchecked")
	public List getSummaryFormulaToCN(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public List getSummaryFormulaAR_STA_ITEM();
	@SuppressWarnings("unchecked")
	public List getPersonBasicInfo(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public List getItemList(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public int addFormulaItem(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public Object getFormulaInfo(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public int updateFormulaItem(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public int deleteFormulaInfo(HttpServletRequest request) ;
}

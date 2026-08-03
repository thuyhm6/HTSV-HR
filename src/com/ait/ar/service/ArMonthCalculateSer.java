package com.ait.ar.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArMonthCalculateSer.java
 * @Description: implement Class ArMonthCalculateSerImp.java
 * @Create date: 2012-2-10 下午05:51:59
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ArMonthCalculateSer {
	@SuppressWarnings("unchecked")
	public String monthCalculate(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public String monthCalculateConfirm(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public String monthCalculateConfirmApply(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int arapplyCloseGuan(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public String arapplyCloseGuanstr(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public String paapplyCloseGuanstr(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List arEssNOApplyCount(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List paEssNOApplyCount(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int arOFF(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int paOFF(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int arapplyCloseOpen(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public String arapplyCloseOpenstr(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getStatNoList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getDeptAreaList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getDeptTypeList(HttpServletRequest request) ;
}

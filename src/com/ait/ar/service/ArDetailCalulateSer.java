package com.ait.ar.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArDetailCalulateSer.java
 * @Description: implement Class ArDetailCalulateSerImp.java
 * @Create date: 2012-2-7 上午11:58:04
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ArDetailCalulateSer {
	
	@SuppressWarnings("unchecked")
	public List getArSupervisorList(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public String detailCalculate(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public String detailShiHouCalculate(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public List getardetailsendview(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public String detailLastMonthCalculate(HttpServletRequest request) ;
	

}

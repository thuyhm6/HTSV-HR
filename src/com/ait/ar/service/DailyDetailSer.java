package com.ait.ar.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: DailyDetailSer.java
 * @Description: implement Class DailyDetailSerImp.java
 * @Create date: 2012-2-6 上午10:50:53
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface DailyDetailSer {
	//EmpCalendarSerImp
	@SuppressWarnings("unchecked")
	public String getDailyDetailViewHtml(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public int updateDailyDetailInfo(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public Object getEmpInfo(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public Object getDailyDetailPersonList(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public int getDailyDetailPersonCnt(HttpServletRequest request);
}

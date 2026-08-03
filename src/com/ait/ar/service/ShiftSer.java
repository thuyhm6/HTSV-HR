package com.ait.ar.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ShiftSer.java
 * @Description: implement Class ShiftSerImp.java
 * @Create date: 2012-1-9 下午02:51:17
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ShiftSer {

	@SuppressWarnings("unchecked")
	public Object getShift(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getShiftList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getShiftCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int addShiftInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updateShiftInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int deleteShiftInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getShiftParameterList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int checkShiftInfo(HttpServletRequest request) ;

	public List getItemList(HttpServletRequest request);
	
	
	public List calculateAvg(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int updateShiftInfoByNO(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getShiftList1(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public List getWorkTimeLsit(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public List getDateTypeLsit(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public List getWorkTimeLsit1(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public List getWorkTimeLsit2(HttpServletRequest request) ;
}

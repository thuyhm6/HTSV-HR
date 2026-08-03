package com.ait.ar.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArAnnualLeaveSer.java
 * @Description: implement Class ArAnnualStandardSerImp.java 
 * @Create date: 2012-2-14 下午12:53:48
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ArAnnualStandardSer {
	
	@SuppressWarnings("unchecked")
	public Object getArAnnualStandardInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
    public List getArAnnualStandardList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
    public List getArAnnualMonthList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getArAnnualStandardCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int deleteArAnnualStandard(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updateArAnnualStandardInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int addArAnnualStandardInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int checkAddArAnnualStandardInfo(HttpServletRequest request) ;
}

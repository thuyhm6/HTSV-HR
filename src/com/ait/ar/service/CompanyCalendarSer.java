package com.ait.ar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ait.web.util.ObjectBindUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: CompanyCalendarSer.java
 * @Description: implement Class CompanyCalendarSerImp.java
 * @Create date: 2012-1-12 下午08:18:34
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface CompanyCalendarSer {
	
	@SuppressWarnings("unchecked")
	public String getCalendarViewHtml(HttpServletRequest request) ;

	@SuppressWarnings("unchecked")
	public String getCompanyCalendarViewHtml(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getCompanyCalendarList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int addCompanyCalendarInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int addCompanyCalendarInfoBanCi(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updateCompanyCalendarInfo(HttpServletRequest request) ;

	public List getShiftList(HttpServletRequest request);
	
	public int addStatutoryHolidaysInfo(HttpServletRequest request) ;
	
	public int updateStatutoryHolidaysInfo(HttpServletRequest request) ;
	
	public Object getOneStatutoryHolidayInfo(HttpServletRequest request);
	
	public int deleteStatutoryHolidaysInfo(HttpServletRequest request) ;
	
	/**
	 * 获取年假信息
	 */
	public List viewVacEmpList(HttpServletRequest request);

	/**
	 * 清算上一年年假
	 */
	public String executeVacClear(HttpServletRequest request);
	
	/**
	 * 保存年假信息
	 */
	@SuppressWarnings("unchecked")
	public int saveEmpVacInfo(HttpServletRequest request) ;
	
	/**
	 * 获取倒休信息
	 */
	public List viewTxEmpList(HttpServletRequest request);
	/**
	 * 获取倒休信息
	 */
	public List viewTxEmpTSTOList(HttpServletRequest request);
	
	/**
	 * 保存倒休信息
	 */
	@SuppressWarnings("unchecked")
	public int saveEmpTxInfo(HttpServletRequest request);
	
	/**
	 * 保存倒休信息
	 */
	@SuppressWarnings("unchecked")
	public int saveEmpTxInfo2(HttpServletRequest request) ;
}

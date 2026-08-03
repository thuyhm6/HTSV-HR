package com.ait.ar.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: CompanyCalendarDao.java
 * @Description: implement Class CompanyCalendarDaoImpl.java
 * @Create date: 2012-1-12 下午08:24:14
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface CompanyCalendarDao {
	
	@SuppressWarnings("unchecked")
	public List getCalendarList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPersonalCalendarList(Object object);
	
	@SuppressWarnings("unchecked")
	public Object getCompanyCalendar(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List getCompanyCalendarList(Object object);
	
	@SuppressWarnings("unchecked")
	public int addCompanyCalendarInfo(Object object);
	
	@SuppressWarnings("unchecked")
	public int addCompanyCalendarInfoBanCi(Object object);
	
	@SuppressWarnings("unchecked")
	public int updateCompanyCalendarInfo(Object object);
	
	@SuppressWarnings("unchecked")
	public int updateCompanyCalendarInfo(List list);

	public List getShiftList(Map paramMap);
	
	public int addStatutoryHolidaysInfo(Object object);
	
	public int updateStatutoryHolidaysInfo(Map paramMap);
	
	public Object getOneStatutoryHolidayInfo(Map paramMap);
	
	public int deleteStatutoryHolidaysInfo(Map paramMap);
	
	/**
	 * 获取年假信息
	 */
	public List viewVacEmpList(Map paramMap) ;
	
	/**
	 * 清算上一年年假
	 */
	public String executeVacClear(Map paramMap);
	
	/**
	 * 保存年假信息
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveEmpVacInfo(Object object)throws Exception;
	/**
	 * 保存年假计划信息信息
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveEmpPlanVacInfo(Object object)throws Exception;
	
	/**
	 * 获取倒休信息
	 */
	public List viewTxEmpList(Map paramMap);
	/**
	 * 获取倒休信息
	 */
	public List viewTxEmpTSTOList(Map paramMap);

	/**
	 * 保存倒休信息
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveEmpTxInfo(Object object)throws Exception;

	/**
	 * 保存倒休信息
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveEmpTxInfo2(Object object)throws Exception ;
}

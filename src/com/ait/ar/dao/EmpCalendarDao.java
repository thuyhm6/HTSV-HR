package com.ait.ar.dao;

import java.util.List;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: EmpCalendarDao.java
 * @Description: implement Class EmpCalendarDaoImpl.java
 * @Create date: 2012-2-6 上午10:52:48
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface EmpCalendarDao {
   
	// EmpCalendarDaoImpl
	@SuppressWarnings("unchecked")
	public List getEmpCalendarList(Object object);
	@SuppressWarnings("unchecked")
	public List getShiftList(Object object);
	@SuppressWarnings("unchecked")
	public List getDateTypeList(Object object);
	@SuppressWarnings("unchecked")
	public int deleteEmpCalendarInfo(Object object);
	@SuppressWarnings("unchecked")
	public String getArDetailTSTONUM(Object object);
	@SuppressWarnings("unchecked")
	public int getEmpCalendarInfo(Object object);
	@SuppressWarnings("unchecked")
	public void insertEmpCalendarInfo(List list) throws Exception;
	@SuppressWarnings("unchecked")
	public Object getEmpInfo(Object object);
	@SuppressWarnings("unchecked")
	public List getBigDistinct();
	@SuppressWarnings("unchecked")
	public String getDefaultGroup(Object object);
	@SuppressWarnings("unchecked")
	public List getArClassCalendarList(Object object);
	@SuppressWarnings("unchecked")
	public List getArClassCalendarListForNormalShift(Object object);
	@SuppressWarnings("unchecked")
	public List getArClassCalendarListGs(Object object);
	@SuppressWarnings("unchecked")
	public void updateClassCalendarInfo(Object object) throws Exception;
	@SuppressWarnings("unchecked")
	public void insertArShiftChange(Object object) throws Exception;
	@SuppressWarnings("unchecked")
	public List getShiftNo(Object object);
	public void insertArShiftChangeByShift(Object object) throws Exception ;
	public void insertArShiftChangeByCompany(Object object) throws Exception ;
	public void createArDEtailClassCalendarInfo(Object object) throws Exception ;
	public void createArDEtailClassOverTimeLimit(Object object) throws Exception ;
	/**
	 * 取个人日历真实日期
	 * @param Object
	 * @return int
	 * @throws 
	 */
	public String getRealDate(Object obj);
	
	@SuppressWarnings("unchecked")
	public List viewEmpCalendarCheckInfoList(String target, Object object);
}

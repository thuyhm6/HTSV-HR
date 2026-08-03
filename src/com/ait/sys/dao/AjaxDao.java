package com.ait.sys.dao;

import java.util.List;


/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: AjaxDao.java
 * @Description: implement Class AjaxDaoImpl.java
 * @Create date: Jan 16, 2012 3:36:16 PM
 * @author : hanzhe(hanzhe@ait.net.cn)
 * @version 5.1
 */
public interface AjaxDao {
	/**
	 * 查询所有公司信息(query all the company info)
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getCompanyInfoList(Object object);
	
	/**
	 * 根据法人Cpny_id查询工资月份信息（query the pa_month info list by cpny_id）
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaMonthList(Object object);
	
	/**
	 * 根据法人Cpny_id、工资月份查询薪资发放日期（query the GIVE_DATE info list by cpny_id 、pa_month）
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaGiveDateList(Object object);
	
	/**
	 * 根据法人Cpny_id查询工资项目信息（query the pa item info list by cpny_id）
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaItemInfoList(Object object);
	
	/**
	 * 根据公司ID查询该公司的部门树（query the dept tree by the company id ）
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getDeptInfoTree(Object object);
	
	/**
	 * 根据传入的年月输出该月所以的 *月*日格式
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getDayAll(Object object);
	
	/**
	 * 根据公司ID查询该公司考勤区间
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getTimeIntervalList(Object object);
	
	/**
	 * 未刷卡查询的状态(旷工)
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List statusList(Object object);
}

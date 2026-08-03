package com.ait.sys.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: AjaxSer.java
 * @Description: implement Class AjaxSerImpl.java
 * @Create date: Jan 16, 2012 3:24:55 PM
 * @author : hanzhe(hanzhe@ait.net.cn)
 * @version 5.1
 */
public interface AjaxSer {
	/**
	 * 查询所有公司信息(query all the company info)
	 * @param param
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getCompanyInfoList(HttpServletRequest request);	
	
	/**
	 * 根据法人Cpny_id查询工资月份信息（query the pa_month info list by cpny_id）
	 * @param param
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaMonthList(HttpServletRequest request);	
	
	/**
	 * 根据法人Cpny_id、工资月份查询薪资发放日期（query the GIVE_DATE info list by cpny_id 、pa_month）
	 * @param param
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaGiveDateList(HttpServletRequest request);	
	
	/**
	 * 根据法人Cpny_id查询工资项目信息（query the pa item info list by cpny_id）
	 * @param param
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaItemInfoList(HttpServletRequest request);	
	
	/**
	 * 根据公司ID查询该公司的部门树（query the dept tree by the company id）
	 * @param param
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getDeptInfoTree(Map param);		
	
	/**
	 * 根据传入的年月输出该月所以的 *月*日格式
	 * @param param
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getDayAll(HttpServletRequest request);
	
	/**
	 * 根据公司ID查询该公司考勤区间
	 * @param param
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getTimeIntervalList(HttpServletRequest request);
	
	
	/**
	 * 获取未刷卡查询的页面中的状态（旷工）
	 * @param param
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List statusList(HttpServletRequest request);
}

package com.ait.report.hr.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: HrReportDao.java
 * @Description: interface Class HrReportDao.java
 * @Create date: April 14, 2012 3:29:38 PM
 * @Create by: hanzhe (hanzhe@ait.net.cn)
 * @version 5.1
 */
public interface HrReportC01Dao {
	/**
	 * 查询C01的职等信息
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getGradeLevelList(Object object);
	
	/**
	 * 根据条件查询员工的调任信息，不分页
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getEmpPaRiseList(Object object);
	
	/**
	 * 根据条件查询员工的调任信息数据量
	 * @param object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int getEmpPaRiseListCnt(Object object);
	
	/**
	 * 根据person_id查询改员工的社外工作经历
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getOutExperienceList(Object object);
	
	/**
	 * 根据person_id查询改员工的社内工作经历
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getInsideExperienceList(Object object);
	
	/**
	 * 乐天玛特--在职人数统计信息报表,Excel导出用
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getEmpOnStatusExcelList(Object object);
	
	/**
	 * 获取C01所有门店号，包括总部、分店、门店号信息
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getAllDistinguishList(Object object);
	
	/**
	 * 根据查询月份、类型（总部、分店、门店号），查询该月份、该（总部、分店、门店号）的在职员工数量
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public Object getEmpOnStatusCntByDisType(Object object);
	
	/**
	 * 根据查询月份、类型（总部、分店、门店号），查询该月份、该（总部、分店、门店号）的参保人员数
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public Object getEmpCalIsCntByDisType(Object object);
	
	/**
	 * 根据查询月份、类型（总部、分店、门店号），查询该月份、该（总部、分店、门店号）的计薪人员数
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public Object getEmpCalPaCntByDisType(Object object);
	
	/**
	 * 员工在离职查询(query the emp status info)，不分页
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPersonalStatusInfoList(Object object);
	
	/**
	 * 员工在离职查询数量(query the emp status info count)
	 * @param object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int getPersonalStatusInfoListCnt(Object object);
	
	/**
	 * 员工在离职查询(query the emp status info)
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPersonalStatusInfoList(Object object, int currentPage, int pageSize);
}

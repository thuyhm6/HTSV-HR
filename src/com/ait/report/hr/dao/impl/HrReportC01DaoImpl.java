package com.ait.report.hr.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.report.hr.dao.HrReportC01Dao;
import com.ait.web.util.SqlMapClientSupport;
/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: HrReportDaoImpl.java
 * @Description: implement Class HrReportDaoImpl.java
 * @Create date: April 14, 2012 3:29:38 PM
 * @Create by: hanzhe (hanzhe@ait.net.cn)
 * @version 5.1
 */
@Repository
public class HrReportC01DaoImpl extends SqlMapClientSupport implements HrReportC01Dao {	
	/**
	 * 根据条件查询员工的调任信息，不分页
	 * @param object
	 * @return list
	 */
	@Override
	public List getGradeLevelList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.hrc01.getGradeLevelList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	
	/**
	 * 根据条件查询员工的调任信息，不分页
	 * @param object
	 * @return list
	 */
	@Override
	public List getEmpPaRiseList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.hrc01.getEmpPaRiseList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 根据条件查询员工的调任信息数量
	 * @param object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int getEmpPaRiseListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("report.hrc01.getEmpPaRiseListCnt",obj)), Integer.class);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 根据条件查询员工的调任信息
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getOutExperienceList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.hrc01.getOutExperienceList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 根据条件查询员工的调任信息
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getInsideExperienceList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.hrc01.getInsideExperienceList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 乐天玛特--在职人数统计信息报表,Excel导出用
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getEmpOnStatusExcelList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.hrc01.getEmpOnStatusExcelList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 获取C01所有门店号，包括总部、分店、门店号信息
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getAllDistinguishList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.hrc01.getAllDistinguishList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 根据查询月份、类型（总部、分店、门店号），查询该月份、该（总部、分店、门店号）的在职员工数量
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public Object getEmpOnStatusCntByDisType(Object object) {
		Object obj = null;
		try {
			obj = this.queryForObject("report.hrc01.getEmpOnStatusCntByDisType",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return obj;	
	}
	
	/**
	 * 根据查询月份、类型（总部、分店、门店号），查询该月份、该（总部、分店、门店号）的参保人员数
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public Object getEmpCalIsCntByDisType(Object object) {
		Object obj = null;
		try {
			obj = this.queryForObject("report.hrc01.getEmpCalIsCntByDisType",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return obj;	
	}
	
	/**
	 * 根据查询月份、类型（总部、分店、门店号），查询该月份、该（总部、分店、门店号）的计薪人员数
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public Object getEmpCalPaCntByDisType(Object object) {
		Object obj = null;
		try {
			obj = this.queryForObject("report.hrc01.getEmpCalPaCntByDisType",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return obj;	
	}
	
	/**
	 * 员工在离职查询，Excel导出用(query the emp status info)，不分页
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonalStatusInfoList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPersonalStatusInfoList(obj, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 员工在离职查询，Excel导出用(query the emp status info)
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPersonalStatusInfoList(Object obj, int currentPage,int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("report.hrc01.getPersonalStatusInfoList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("report.hrc01.getPersonalStatusInfoList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 员工在离职查询数量(query the emp status info count)
	 * @param object
	 * @return int
	 */
	public int getPersonalStatusInfoListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("report.hrc01.getPersonalStatusInfoListCnt",obj)), Integer.class);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
}

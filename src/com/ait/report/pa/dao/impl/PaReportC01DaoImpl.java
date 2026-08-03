package com.ait.report.pa.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.report.pa.dao.PaReportC01Dao;
import com.ait.report.pa.dao.PaReportDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaReportC01DaoImpl.java
 * @Description: implement Class PaReportC01DaoImpl.java
 * @Create date: Sep 14, 2012 5:29:38 PM
 * @Create by: lufeng (lufeng@ait.net.cn)
 * @version 5.1
 */
@Repository
public class PaReportC01DaoImpl extends SqlMapClientSupport implements PaReportC01Dao {	
	
	/**
	 * 查询乐天玛特实发薪资汇总表（总公司）报表信息
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaActualSalaryExcelList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac01.getPaActualSalaryList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 查询乐天玛特薪资成本汇总表（总公司）报表信息
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaPayrollCostsExcelList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac01.getPaPayrollCostsList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 查询系统时间 (query the sysdate )
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object getSysdate() {
		Object obj = null;
		try {
			obj = this.queryForObject("report.pac01.getSysdate");
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return obj;	
	}
	
	/**
	 * 查询系统时间 (query the sysdate )
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object getLastMonthStr(Object object) {
		Object obj = null;
		try {
			obj = this.queryForObject("report.pac01.getLastMonthStr",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return obj;	
	}
	
	/**
	 * 根据门店编号查询门店名称
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object getDistinguishNameByNo(Object object) {
		Object obj = null;
		try {
			obj = this.queryForObject("report.pac01.getDistinguishNameByNo",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return obj;	
	}
	
	/**
	 * 异常明细--人员情况 按月份查询某月末员工在职人数
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getEmpInfoListByMonth(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac01.getEmpInfoListByMonth", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 异常明细--人员情况 按月份查询某门店调入人数
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getDiaoRuList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac01.getDiaoDongList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 异常明细--人员情况 按月份查询某门店调出人数
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getDiaoChuList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac01.getDiaoDongList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 异常明细--人员情况 按月份查询某门店入职人数
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getRuZhiList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac01.getRuZhiList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 异常明细--人员情况 按月份查询某门店离职人数
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getLiZhiList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac01.getLiZhiList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 异常明细--人员情况 按月份查询本月计薪人数
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaInfoList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac01.getPaInfoList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 异常明细--人员情况 按月份、员工类型查询在职人数
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getEmpInfoListByEmpType(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac01.getEmpInfoListByEmpType", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 异常明细--人员情况 按月份、门店查询当月派入、派出人数
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getDispatchEmpInfoList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac01.getDispatchEmpInfoList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 异常明细--人员情况 按月份、门店查询本月实发工资为0的人数
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getActualSalaryZeroList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac01.getActualSalaryZeroList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 查询异常明细--保险情况明细信息
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getAbnormalInsExcelList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac01.getAbnormalInsExcelList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 乐天玛特--外派人员薪资信息报表信息,Excel导出用
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getDispatchEmpPaExcelList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac01.getDispatchEmpPaExcelList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 乐天玛特--外派人员保险信息报表信息,Excel导出用
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getDispatchEmpInsExcelList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pac01.getDispatchEmpInsExcelList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
}

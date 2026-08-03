package com.ait.report.pa.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;



import com.ait.report.pa.dao.PaReportDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaReportDaoImpl.java
 * @Description: implement Class PaReportDaoImpl.java
 * @Create date: April 14, 2012 3:29:38 PM
 * @Create by: hanzhe (hanzhe@ait.net.cn)
 * @version 5.1
 */
@Repository
public class PaReportDaoImpl extends SqlMapClientSupport implements PaReportDao {	
	/**
	 * 员工工资转账信息（按月份查询）(query the pa transer info of employee by monthed)，不分页
	 * @param object
	 * @return list
	 */
	@Override
	public List getPaTranserList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPaTranserList(obj, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 员工工资转账信息的数量（按月份查询）(query the pa transer info count of employee by monthed)
	 * @param object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public List getPaTranserList(Object obj, int currentPage,int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("report.pa.getPaTranserList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("report.pa.getPaTranserList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 员工工资转账信息（按月份查询）(query the pa transer info of employee by monthed)，不分页
	 * @param object
	 * @return list
	 */
	@Override
	public List getPaTranserTwoList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPaTranserTwoList(obj, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 员工工资转账信息的数量（按月份查询）(query the pa transer info count of employee by monthed)
	 * @param object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public List getPaTranserTwoList(Object obj, int currentPage,int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("report.pa.getPaTranserTwoList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("report.pa.getPaTranserTwoList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 员工工资转账信息（按月份查询）(query the pa transer info of employee by monthed)，分页查询
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public int getPaTranserListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = NumberUtils.parseNumber(
					ObjectUtils.toString(this.queryForObject("report.pa.getPaTranserListCnt",obj)), Integer.class);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 根据工资月份、部门编号查询该部门、该月的所有员工类型的种类数量(query the emp type count by pa info by the month and deptno)
	 * @param object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int getPaEmpTypeByDeptno(Object obj){
		int returnInt = 0 ;
		try {
			returnInt = NumberUtils.parseNumber(
					ObjectUtils.toString(this.queryForObject("report.pa.getPaEmpTypeByDeptno",obj)), Integer.class);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 根据cpny_no获取SAP工资信息查询的纵向表头（工资项目）（query the sap pa item info for the header by the cpny_no）
	 * @param object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public List getSapDepartList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getSapDepartList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 根据cpny_no获取SAP工资信息查询的纵向表头（工资项目）（query the sap pa item info for the header by the cpny_no）
	 * @param object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public List getSapPaItemList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getSapPaItemList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 根据dept_no统计SAP工资信息数量（query the sap pa info count by the dept_no）
	 * @param object
	 * @return object
	 */
	public Object getPaHrmCountByDept(Object obj){
		Object object = new Object();
		try {
			object = this.queryForObject("report.pa.getPaHrmCountByDept", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object; 
	}
	
	/**
	 * 根据dept_no统计SAP工资信息数量，按员工类型分组（query the sap pa info count by the dept_no，group by emp_type）
	 * @param object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public List getPaHrmCountByDeptAndEmptype(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getPaHrmCountByDeptAndEmptype", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 根据dept_no和工资项目ID（item_id）统计该部门、该项目的金额小计（query the sap pa info sum by the dept_no and pa_item_id）
	 * @param object
	 * @return object
	 */
	public Object getPaitemSumInfoByDept(Object obj){
		Object object = new Object();
		try {
			object = this.queryForObject("report.pa.getPaitemSumInfoByDept", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object; 
	}
	
	/**
	 * 员工在职证明信息（按工资月份查询）(query the office prove info of employee by pa monthed)，不分页
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getOfficeProveList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getOfficeProveList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 员工在职证明信息（按工资月份查询）(query the office prove info of employee by pa monthed)，分页查询
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getOfficeProveList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("report.pa.getOfficeProveList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("report.pa.getOfficeProveList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 员工在职证明信息（按工资月份查询）(query the office prove info of employee by pa monthed)
	 * @param object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int getOfficeProveListCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = NumberUtils.parseNumber(
					ObjectUtils.toString(this.queryForObject("report.pa.getOfficeProveCnt",object)), Integer.class);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 工资单，added on 2012-10-09
	 * @param object
	 * @return list
	 */
	public List retrievePaJasperReportPayrollData(Object parameterObject) {

		List list = null;
		try {
			list = this.queryForList("report.pa.retrievePaJasperReportPayrollData",parameterObject);
			//list = commonSQLMapAdapter.executeQueryForMulti("report.pa.retrievePaJasperReportPayrollData", parameterObject);

		} catch (Exception e) {              
			Logger.getLogger(getClass()).error(e.toString());
			
		}
		return list;
	}

	/**
	 * 工资对照
	 */
	@Override
	public List getPacontrastList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("report.pa.getPacontrastList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("report.pa.getPacontrastList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	@Override
	public List getPacontrastList(Object object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getPacontrastList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 工资查看--特殊值查看页面，查看页面，不分页
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaInfoByItemList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getPaInfoByItemList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 工资查看--特殊值查看页面，查看页面，分页查询
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaInfoByItemList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("report.pa.getPaInfoByItemList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("report.pa.getPaInfoByItemList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 工资查看--特殊值查看页面，数量查询
	 * @param object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int getPaInfoByItemListCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = NumberUtils.parseNumber(
					ObjectUtils.toString(this.queryForObject("report.pa.getPaInfoByItemListCnt",object)), Integer.class);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 薪资查看--年工资，查看页面，不分页
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaInfoByYearList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getPaInfoByYearList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 薪资查看--年工资，查看页面，分页查询
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaInfoByYearList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("report.pa.getPaInfoByYearList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("report.pa.getPaInfoByYearList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 薪资查看--年工资，查看页面，数量查询
	 * @param object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int getPaInfoByYearListCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = NumberUtils.parseNumber(
					ObjectUtils.toString(this.queryForObject("report.pa.getPaInfoByYearListCnt",object)), Integer.class);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 工资汇总表
	 * */
	@SuppressWarnings("unchecked")
	public List getPaSummaryList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getPaSummaryList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 加班费对比分析
	 * */
	@SuppressWarnings("unchecked")
	public List getOtFeeContrastList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getOtFeeContrastList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 应发工资对比分析
	 * */
	@SuppressWarnings("unchecked")
	public List getGrossPayList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getGrossPayList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 工资决裁
	 * */
	@SuppressWarnings("unchecked")
	public List getPaDecisionList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getPaDecisionList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 工资决裁
	 * */
	@SuppressWarnings("unchecked")
	public List getPaNetPayList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getPaNetPayList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 工资决裁
	 * */
	@SuppressWarnings("unchecked")
	public List getPaSendFeeList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getPaSendFeeList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 工资决裁
	 * */
	@SuppressWarnings("unchecked")
	public List getPaOtFeeList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getPaOtFeeList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 工资决裁
	 * */
	@SuppressWarnings("unchecked")
	public List getPayInfoList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getPayInfoList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 工资决裁
	 * */
	@SuppressWarnings("unchecked")
	public List getEmpDeftSpcBjList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getEmpDeftSpcBjList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 工资决裁
	 * */
	@SuppressWarnings("unchecked")
	public List getEmpDeftInfoSpcBjList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getEmpDeftInfoSpcBjList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 工资决裁
	 * */
	@SuppressWarnings("unchecked")
	public List getSalaryTotalSpcBjList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getSalaryTotalSpcBjList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 工资奖金支付现状
	 * */
	@SuppressWarnings("unchecked")
	public List getWagesBonusList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getWagesBonusList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 工资分配
	 * */
	@SuppressWarnings("unchecked")
	public List getPaAllocationList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getPaAllocationList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List paAllocationForOwned(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.paAllocationForOwned", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List paAllocationForDept(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.paAllocationForDept", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 查询
	 * 
	 * @param Object
	 * @return
	 */
	public List getPaAllocationListForSpcBj(Object object,String target) {
		List result = null;
		try {
			result = this
					.queryForList("report.pa." + target, object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 *  各  部  门  比  例  明  细
	 * */
	@SuppressWarnings("unchecked")
	public List getPaDetaiByDeptNolList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getPaDetaiByDeptNolList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	

	
	/**
	 *  某职群下职种
	 * */
	@SuppressWarnings("unchecked")
	public List getPositionList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getPositionList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 *  某职群下职种
	 * */
	@SuppressWarnings("unchecked")
	public List getPostGradeNOList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getPostGradeNOList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 *  某职群下职种
	 * */
	@SuppressWarnings("unchecked")
	public List getPostGradeNOForPostFamilyList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getPostGradeNOForPostFamilyList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 *  子部门
	 * */
	@SuppressWarnings("unchecked")
	public List getDeptChildList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getDeptChildList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 *  子部门
	 * */
	@SuppressWarnings("unchecked")
	public List getDeptChildGroupList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getDeptChildGroupList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 *  子部门
	 * */
	@SuppressWarnings("unchecked")
	public List getDeptForLeve(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getDeptForLeve", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 *  各部门实际情况
	 * */
	@SuppressWarnings("unchecked")
	public List getManagementSituationList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getManagementSituationList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 各部门实际情况
	 * */
	@SuppressWarnings("unchecked")
	public List getManagementSituationList(Object object,String postGrade){
		List returnList = new ArrayList() ;
		try {
			Map paramMap = (Map) object;
			paramMap.put("POST_GRADE_NO", postGrade);
			returnList = this.queryForList("report.pa.getManagementSituationList", paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 *  店铺前厅
	 * */
	@SuppressWarnings("unchecked")
	public List getStoreKitchenSituationList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.pa.getStoreKitchenSituationList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
}

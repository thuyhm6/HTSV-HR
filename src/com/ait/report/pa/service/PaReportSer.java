package com.ait.report.pa.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaReportSer.java
 * @Description: interface Class PaReportSer.java
 * @Create date: April 14, 2012 3:29:38 PM
 * @Create by: hanzhe (hanzhe@ait.net.cn)
 * @version 5.1
 */
public interface PaReportSer {
	/**
	 * 员工工资转账信息（按月份查询）(query the pa transer info of employee by monthed)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getPaTranserList(HttpServletRequest request) ;
	
	/**
	 * 员工工资转账信息的数量（按月份查询）(query the pa transer info count of employee by monthed)
	 * @param request
	 * @return int
	 */
	public int getPaTranserListCnt(HttpServletRequest request) ;
	
	/**
	 * 员工工资转账信息（按月份查询）(query the pa transer info of employee by monthed)
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaTranserExcelList(HttpServletRequest request) ;
 
	/**
	 * 根据deptno获取SAP工资信息查询的横向表头（部门信息）（query the sap dept info for the header by the deptno）
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getSapDepartList(HttpServletRequest request) ;
	
	/**
	 * 根据cpny_no获取SAP工资信息查询的纵向表头（工资项目）（query the sap pa item info for the header by the cpny_no）
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getSapPaItemList(HttpServletRequest request) ;
	
	/**
	 * 根据工资月、部门NO查询SAP工资信息（query the sap pa info by the condition）
	 * @param request
	 * @return LinkedHashMap
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getSapPaInfoList(HttpServletRequest request) ;
	
	/**
	 * 员工在职证明信息（按工资月份查询）(query the office prove info of employee by pa monthed)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getOfficeProveList(HttpServletRequest request) ;
	
	/**
	 * 员工在职证明信息的数量（按工资月份查询）(query the office prove info of employee by pa monthed)
	 * @param request
	 * @return int
	 */
	public int getOfficeProveListCnt(HttpServletRequest request) ;
	
	/**
	 * 员工在职证明信息（按工资月份查询）(query the office prove info of employee by pa monthed),Excel导出用
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getOfficeProveExcelList(HttpServletRequest request) ;
	
	/**
	 * 根据法人Cpny_id查询该法人所有薪资项目
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getPaItemListByCpnyId(HttpServletRequest request) ;
	
	/**
	 * 根据法人Cpny_id、薪资月份查询该法人、该月的所有薪资发放日期
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getPaGiveDateListByCpnyIdAndMonth(HttpServletRequest request) ;
	
	/**
	 * 工资查看--特殊值查看页面，查看页面
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getPaInfoByItemList(HttpServletRequest request) ;
	
	/**
	 * 工资查看--特殊值查看页面，数量查询
	 * @param request
	 * @return int
	 */
	public int getPaInfoByItemListCnt(HttpServletRequest request) ;
	
	/**
	 * 工资查看--特殊值查看页面，导出Excel用
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaInfoByItemExcelList(HttpServletRequest request) ;
	
	
	/**
	 * 薪资查看--年工资，查看页面
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getPaInfoByYearList(HttpServletRequest request) ;
	
	/**
	 * 薪资查看--年工资，查看页面
	 * @param request
	 * @return int
	 */
	public int getPaInfoByYearListCnt(HttpServletRequest request) ;
	
	/**
	 * 薪资查看--年工资，查看页面
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaInfoByYearExcelList(HttpServletRequest request) ;
	/**
	 * 工资汇总表
	 * */
	public List getPaSummaryList(HttpServletRequest request);
	/**
	 * 加班费嘴比分析
	 * */
	public List getOtFeeContrastList(HttpServletRequest request);
	/**
	 * 工资对比分析
	 * */
	public List getGrossPayList(HttpServletRequest request);
	/**
	 * 工资决裁
	 * */
	public List getPaDecisionList(HttpServletRequest request);
	/**
	 * 工资决裁
	 * */
	public List getPaNetPayList(HttpServletRequest request);
	/**
	 * 工资决裁
	 * */
	public List getPaSendFeeList(HttpServletRequest request);
	/**
	 * 工资决裁
	 * */
	public List getPaOtFeeList(HttpServletRequest request);
	/**
	 * 工资决裁
	 * */
	public List getPayInfoList(HttpServletRequest request);
	/**
	 * 工资决裁
	 * */
	public List getEmpDeftSpcBjList(HttpServletRequest request);
	/**
	 * 工资决裁
	 * */
	public List getEmpDeftInfoSpcBjList(HttpServletRequest request);
	/**
	 * 工资决裁
	 * */
	public List getSalaryTotalSpcBjList(HttpServletRequest request);
	/**
	 * 工资奖金支付现状
	 * */
	public List getWagesBonusList(HttpServletRequest request);
	/**
	 * 工资分配
	 * */
	public List getPaAllocationList(HttpServletRequest request,String deptNo,String deptType);
	public List paAllocationForOwned(HttpServletRequest request,String deptNo,String deptType,String owned);
	
	public List getPaAllocationListForSpcBj(HttpServletRequest request,String target,String owned);
	public List paAllocationForDept(HttpServletRequest request,String target,String owned);
	public List paAllocationForDept(HttpServletRequest request,String target,String owned,String empType);
	/**
	 * 各  部  门  比  例  明  细
	 * */
	public LinkedHashMap getPaDetaiByDeptNolList(HttpServletRequest request,String flag,String dept_lv);
	/**
	 * 某职群下所有职种
	 * */
	public List getPositionList(HttpServletRequest request,String postFamily);
	/**
	 * 某职种下所有职级
	 * */
	public List getPostGradeNOList(HttpServletRequest request,String position);
	/**
	 * 某职群下所有职级
	 * */
	public List getPostGradeNOForPostFamilyList(HttpServletRequest request,String postFamily);
	/**
	 * 子部门 
	 */
	public List getDeptChildList(HttpServletRequest request,String deptNo);
	/**
	 * 子部门 
	 */
	public List getDeptChildGroupList(HttpServletRequest request,String deptNo);
	/**
	 * 管理实际情况
	 * */
	public LinkedHashMap getManagementSituationList(HttpServletRequest request,String deptLeve,String include);
	/**
	 * 工厂实际情况
	 * */
	public LinkedHashMap getFactorySituationList(HttpServletRequest request);
	/**
	 * 店铺（前厅）实际情况
	 * */
	public LinkedHashMap getStoreOperationSituationList(HttpServletRequest request);
	/**
	 * 店铺（前厅）实际情况
	 * */
	public LinkedHashMap getStoreKitchenSituationList(HttpServletRequest request);
}

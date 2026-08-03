package com.ait.report.pa.dao;

import java.util.List;
import java.util.Map;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaReportDao.java
 * @Description: interface Class PaReportDao.java
 * @Create date: April 14, 2012 3:29:38 PM
 * @Create by: hanzhe (hanzhe@ait.net.cn)
 * @version 5.1
 */
public interface PaReportDao {
	/**
	 * 员工工资转账信息（按月份查询）(query the pa transer info of employee by monthed)，不分页
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaTranserList(Object object);
	
	/**
	 * 员工工资转账信息的数量（按月份查询）(query the pa transer info count of employee by monthed)
	 * @param object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int getPaTranserListCnt(Object object);
	
	/**
	 * 员工工资转账信息（按月份查询）(query the pa transer info of employee by monthed)，分页查询
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaTranserList(Object object, int currentPage, int pageSize);
	
	/**
	 * 员工工资转账信息（按月份查询）(query the pa transer info of employee by monthed)，不分页
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaTranserTwoList(Object object);
	
	/**
	 * 员工工资转账信息（按月份查询）(query the pa transer info of employee by monthed)，分页查询
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaTranserTwoList(Object object, int currentPage, int pageSize);
 
	/**
	 * 根据deptno获取SAP工资信息查询的横向表头（部门信息）（query the sap pa info for the header by the deptno）
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getSapDepartList(Object object);
	
	/**
	 * 根据工资月份、部门编号查询该部门、该月的所有员工类型的种类数量(query the emp type count by pa info by the month and deptno)
	 * @param object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int getPaEmpTypeByDeptno(Object object);
	
	/**
	 * 根据cpny_no获取SAP工资信息查询的纵向表头（工资项目）（query the sap pa item info for the header by the cpny_no）
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getSapPaItemList(Object object);
	
	/**
	 * 根据dept_no统计SAP工资信息数量（query the sap pa info count by the dept_no）
	 * @param object
	 * @return object
	 */
	@SuppressWarnings("unchecked")
	public Object getPaHrmCountByDept(Object object);
	
	/**
	 * 根据dept_no统计SAP工资信息数量，按员工类型分组（query the sap pa info count by the dept_no，group by emp_type）
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaHrmCountByDeptAndEmptype(Object object);
	
	/**
	 * 根据dept_no和工资项目ID（item_id）统计该部门、该项目的金额小计（query the sap pa info sum by the dept_no and pa_item_id）
	 * @param object
	 * @return object
	 */
	@SuppressWarnings("unchecked")
	public Object getPaitemSumInfoByDept(Object object);
	
	/**
	 * 员工在职证明信息（按工资月份查询）(query the office prove info of employee by pa monthed)，不分页
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getOfficeProveList(Object object);
	
	/**
	 * 员工在职证明信息（按工资月份查询）(query the office prove info of employee by pa monthed)
	 * @param object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int getOfficeProveListCnt(Object object);
	
	/**
	 * 员工在职证明信息（按工资月份查询）(query the office prove info of employee by pa monthed)，分页查询
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getOfficeProveList(Object object, int currentPage, int pageSize);
	
	
	/**
	 * 工资单，added on 2012-10-09
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List retrievePaJasperReportPayrollData(Object object);

	/**
	 * 工资对照表
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPacontrastList(Object object, int currentPage, int pageSize);
	
	/**
	 * 工资对照表
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPacontrastList(Object object);
	
	/**
	 * 薪资查看--年工资，查看页面
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaInfoByItemList(Object object);
	
	/**
	 * 薪资查看--年工资，查看页面，数量查询
	 * @param object
	 * @return int
	 */
	public int getPaInfoByItemListCnt(Object object);
	
	/**
	 * 工资查看--特殊值查看页面，查看页面
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaInfoByItemList(Object object, int currentPage, int pageSize);
	
	/**
	 * 工资查看--特殊值查看页面，查看页面
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaInfoByYearList(Object object);
	/**
	 * 薪资查看--年工资，查看页面，数量查询
	 * @param object
	 * @return int
	 */
	public int getPaInfoByYearListCnt(Object object);
	
	/**
	 * 薪资查看--年工资，查看页面
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPaInfoByYearList(Object object, int currentPage, int pageSize);
	/**
	 * 工资汇总表
	 * */
	@SuppressWarnings("unchecked")
	public List getPaSummaryList(Object object);
	/**
	 * 加班费对比分析
	 * */
	@SuppressWarnings("unchecked")
	public List getOtFeeContrastList(Object object);
	/**
	 * 工资对比分析
	 * */
	@SuppressWarnings("unchecked")
	public List getGrossPayList(Object object);
	/**
	 * 工资决裁
	 * */
	@SuppressWarnings("unchecked")
	public List getPaDecisionList(Object object);
	/**
	 * 工资决裁
	 * */
	@SuppressWarnings("unchecked")
	public List getPaNetPayList(Object object);
	/**
	 * 工资决裁
	 * */
	@SuppressWarnings("unchecked")
	public List getPaSendFeeList(Object object);
	/**
	 * 工资决裁
	 * */
	@SuppressWarnings("unchecked")
	public List getPaOtFeeList(Object object);
	/**
	 * 工资决裁
	 * */
	@SuppressWarnings("unchecked")
	public List getPayInfoList(Object object);
	/**
	 * 工资决裁
	 * */
	@SuppressWarnings("unchecked")
	public List getEmpDeftSpcBjList(Object object);
	/**
	 * 工资决裁
	 * */
	@SuppressWarnings("unchecked")
	public List getEmpDeftInfoSpcBjList(Object object);
	/**
	 * 工资决裁
	 * */
	@SuppressWarnings("unchecked")
	public List getSalaryTotalSpcBjList(Object object);
	/**
	 * 工资奖金支付
	 * */
	@SuppressWarnings("unchecked")
	public List getWagesBonusList(Object object);
	/**
	 * 工资分配
	 * */
	@SuppressWarnings("unchecked")
	public List getPaAllocationList(Object object);
	
	@SuppressWarnings("unchecked")
	public List paAllocationForOwned(Object object);
	
	@SuppressWarnings("unchecked")
	public List paAllocationForDept(Object object);
	/**
	 * 工资分配
	 * */
	@SuppressWarnings("unchecked")
	public List getPaAllocationListForSpcBj(Object object,String target);
	/**
	 *  各  部  门  比  例  明  细
	 * */
	@SuppressWarnings("unchecked")
	public List getPaDetaiByDeptNolList(Object object);

	/**
	 * 某职群下职种
	 * */
	@SuppressWarnings("unchecked")
	public List getPositionList(Object object);
	/**
	 * 某职种下职级
	 * */
	@SuppressWarnings("unchecked")
	public List getPostGradeNOList(Object object);
	/**
	 * 子部门
	 * */
	@SuppressWarnings("unchecked")
	public List getDeptChildList(Object object);
	/**
	 * 子部门
	 * */
	@SuppressWarnings("unchecked")
	public List getDeptChildGroupList(Object object);
	/**
	 * 子部门
	 * */
	@SuppressWarnings("unchecked")
	public List getDeptForLeve(Object object);
	/**
	 * 管理部门情况
	 * */
	@SuppressWarnings("unchecked")
	public List getManagementSituationList(Object object);
	/**
	 *  各  部  门  比  例  明  细
	 * */
	@SuppressWarnings("unchecked")
	public List getManagementSituationList(Object object,String postGrade);
	/**
	 * 某职种下职级
	 * */
	@SuppressWarnings("unchecked")
	public List getPostGradeNOForPostFamilyList(Object object);
	/**
	 * 店铺前厅
	 * */
	@SuppressWarnings("unchecked")
	public List getStoreKitchenSituationList(Object object);
}

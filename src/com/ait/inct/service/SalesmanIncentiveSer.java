package com.ait.inct.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
/**
 * 
 * @fileName SalesmanIncentiveSer.java
 * @author 			@Date			@version		@Desc
 * ---------------------------------------------------------------------------------------------------------------
 * penghaixia 		2014-6-4 		1.0				initial  
 *----------------------------------------------------------------------------------------------------------------
 */
public interface SalesmanIncentiveSer {
	//营业员提成
	@SuppressWarnings("unchecked")
	public List getIncentiveCalcList(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public int getIncentiveCalcListCnt(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public String getSalesInctClosedFlag(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public String getSalesInctPayClosedFlagByMonth(HttpServletRequest request, Map paramMap);
	
	//营业员提成数据导入
	@SuppressWarnings("unchecked")
	public List getSalesIncCalculateImportList(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public List getSalesIncCalculateImportListExcel(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public int getSalesIncCalculateImportListCnt(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public int getSalesIncCalculateImportErrCnt(HttpServletRequest request, Map paramMap);	
	@SuppressWarnings("unchecked")
	public int callSalesmanIncentiveCalcImport(HttpServletRequest request, Map paramMap);
	
	/*营业员提成计算*/
	@SuppressWarnings("unchecked")
	public int callSalesmanIncentiveCalc(HttpServletRequest request, Map paramMap);
	
	/*营业员提成 读取变动工资执行执行*/
	@SuppressWarnings("unchecked")
	public int callSalesmanVariablePayRead(HttpServletRequest request, Map paramMap);
	
	/*营业员提成  员工别提成修改 */
	@SuppressWarnings("unchecked")
	public Map getIncentiveCalcByItem(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public int editIncentiveCalcItemReq(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public int editIncentiveCalcItem(HttpServletRequest request, Map paramMap);
	
	//营业员提成调整
	@SuppressWarnings("unchecked")
	public List getIncentiveCalcAdjuList(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public int getIncentiveCalcAdjuListCnt(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public Map getIncentiveCalcAdjuListByReqId(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public List getIncentiveCalcAdjuDtlList(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public int getIncentiveCalcAdjuDtlListCnt(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public List getAffirmorListByReqId(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public List getCheckListByReqId(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public int affirmSalesmanInctCalcAdju(HttpServletRequest request);
}

package com.ait.inct.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
/**
 * 
 * @fileName SalesmanEvaluationSer.java
 * @author 			@Date			@version		@Desc
 * ---------------------------------------------------------------------------------------------------------------
 * penghaixia 		2014-6-4 		1.0				initial  
 *----------------------------------------------------------------------------------------------------------------
 */
public interface SalesmanEvaluationSer {
	//评价数据导入
	@SuppressWarnings("unchecked")
	public List getEvaluationDataImportList(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public int getEvaluationDataImportListCnt(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public List getEvaluationDataImportListExcel(HttpServletRequest request, Map paramMap)throws Exception;
	//评价数据导入结果
	@SuppressWarnings("unchecked")
	public List getEvaluationDataImportResultList(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public List getEvaluationDataImportResultListExcel(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public int getEvaluationDataImportResultListCnt(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public int getEvaluationDataImportErrCnt(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public String importSalesEvalRAWFromExcel(HttpServletRequest request, Map paramMap);
	
	//评价项目权重设置
	@SuppressWarnings("unchecked")
	public List getEvaluationItemWeightList(HttpServletRequest request,Map paramMap);
	@SuppressWarnings("unchecked")
	public int getEvaluationItemWeightListCnt(HttpServletRequest request,Map paramMap);
	@SuppressWarnings("unchecked")
	public List getEvaluationItemWeightListExcel(HttpServletRequest request,Map paramMap)throws Exception;
	@SuppressWarnings("unchecked")
	public Map getEvaluationWeightByItem(HttpServletRequest request,Map paramMap);
	@SuppressWarnings("unchecked")
	public int updateEvaluationWeightByItem(HttpServletRequest request,Map paramMap);
	@SuppressWarnings("unchecked")
	public int createEvaluationWeightByItem(HttpServletRequest request,Map paramMap);
	
	//评价项目系数设置
	@SuppressWarnings("unchecked")
	public List getSalesIncEvaluationRatioList(HttpServletRequest request,Map paramMap);
	@SuppressWarnings("unchecked")
	public int getSalesIncEvaluationRatioListCnt(HttpServletRequest request,Map paramMap);
	@SuppressWarnings("unchecked")
	public List getEvaluationItemRatioListExcel(HttpServletRequest request,Map paramMap)throws Exception;
	@SuppressWarnings("unchecked")
	public Map getEvaluationRatioByItem(HttpServletRequest request,Map paramMap);
	@SuppressWarnings("unchecked")
	public int updateEvaluationRatioByItem(HttpServletRequest request,Map paramMap);
	@SuppressWarnings("unchecked")
	public int createEvaluationRatioByItem(HttpServletRequest request,Map paramMap);
	
	//营业员评价--汇总
	@SuppressWarnings("unchecked")
	public List getSalesIncEvaluationInquiryList(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public int getSalesIncEvaluationInquiryListCnt(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public List getSalesIncEvaluationInquiryListExcel(HttpServletRequest request, Map paramMap)throws Exception;
	//营业员评价--明细
	@SuppressWarnings("unchecked")
	public List getSalesIncEvaluationInquiryDetailList(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public int getSalesIncEvaluationInquiryDetailListCnt(HttpServletRequest request, Map paramMap);
	@SuppressWarnings("unchecked")
	public List getSalesIncEvaluationInquiryDetailListExcel(HttpServletRequest request, Map paramMap)throws Exception;
	@SuppressWarnings("unchecked")
	public Map getEvaluationRatioByItemForAdjust(HttpServletRequest request,Map paramMap);
	@SuppressWarnings("unchecked")
	public int updateEvaluationRatioByItemForAdjust(HttpServletRequest request,Map paramMap);
	
	//营业员个人评价结果--汇总
	@SuppressWarnings("unchecked")
	public Map getPersonalEvaluationResultInfo(Map map);
	@SuppressWarnings("unchecked")
	public Map getPersonalEvaluationResultInfoExcel(Map map)throws Exception;
	//营业员个人评价结果--明细
	@SuppressWarnings("unchecked")
	public Map getPersonalEvaluationResultDetailInfo(Map map);
	@SuppressWarnings("unchecked")
	public Map getPersonalEvaluationResultDetailInfoExcel(Map map)throws Exception;
	
	/*取工资关帐标志*/
	@SuppressWarnings("unchecked")
	public String getSalesIncPayClosedFlag(Object object);
	
	/*营业员评价执行*/
	@SuppressWarnings("unchecked")
	public Map callSalesmanEvaluation(Map map);
	
	/*营业员评价项目 code list*/
	@SuppressWarnings("unchecked")
	public List getSalesEvaluationTypeCodeList(Map paramMap);	
	/*大区code list*/
	@SuppressWarnings("unchecked")
	public List getPayAreaCodeList(Map paramMap);
}

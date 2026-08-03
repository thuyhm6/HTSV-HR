package com.ait.inct.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
/**
 * 
 * @fileName SalesmanEvaluationDao.java
 * @author 			@Date			@version		@Desc
 * ---------------------------------------------------------------------------------------------------------------
 * penghaixia 		2014-6-4 		1.0				initial  
 *----------------------------------------------------------------------------------------------------------------
 */
public interface SalesmanEvaluationDao {
	/*评价数据导入*/
	@SuppressWarnings("unchecked")
	public List getEvaluationDataImportList(Object object, int currentPage, int pageSize);
	@SuppressWarnings("unchecked")
	public List getEvaluationDataImportList(Object object);	
	@SuppressWarnings("unchecked")
	public int getEvaluationDataImportListCnt(Object object);
	
	/* 评价数据导入结果 */
	@SuppressWarnings("unchecked")
	public List getEvaluationDataImportResultList(Object object, int currentPage, int pageSize);
	@SuppressWarnings("unchecked")
	public List getEvaluationDataImportResultList(Object object);
	@SuppressWarnings("unchecked")
	public int getEvaluationDataImportResultListCnt(Object obj);
	@SuppressWarnings("unchecked")
	public int getEvaluationDataImportErrCnt(Object obj);
	@SuppressWarnings("unchecked")
	public String importSalesEvalRAWFromExcel(Map paramMap);
	
	/*评价项目权重设置*/
	@SuppressWarnings("unchecked")
	public List getEvaluationItemWeightList(Object object, int currentPage, int pageSize);
	@SuppressWarnings("unchecked")
	public List getEvaluationItemWeightList(Object object);	
	@SuppressWarnings("unchecked")
	public int getEvaluationItemWeightListCnt(Object object);
	
	/*评价项目权重设置(修改)*/
	@SuppressWarnings("unchecked")
	public Map getEvaluationWeightByItem(Object object);
	@SuppressWarnings("unchecked")
	public void updateEvaluationWeightByItem(Object object) throws Exception;
	@SuppressWarnings("unchecked")
	public void createEvaluationItemWeight(Object object) throws Exception;
	
	/*评价项目系数设置*/
	@SuppressWarnings("unchecked")
	public List getSalesIncEvaluationRatioList(Object object, int currentPage, int pageSize);
	@SuppressWarnings("unchecked")
	public List getSalesIncEvaluationRatioList(Object object);	
	@SuppressWarnings("unchecked")
	public int getSalesIncEvaluationRatioListCnt(Object object);
	
	/*评价项目系数设置(修改)*/
	@SuppressWarnings("unchecked")
	public Map getEvaluationRatioByItem(Object object);
	@SuppressWarnings("unchecked")
	public void updateEvaluationRatioByItem(Object object) throws Exception;
	@SuppressWarnings("unchecked")
	public void createEvaluationItemRatio(Object object) throws Exception;
	@SuppressWarnings("unchecked")
	public int getEvaluationRatioDupPeriodCnt(Object object);
	
	/*营业员评价 --汇总*/
	@SuppressWarnings("unchecked")
	public List getSalesIncEvaluationInquiryList(Object object, int currentPage, int pageSize);
	@SuppressWarnings("unchecked")
	public List getSalesIncEvaluationInquiryList(Object object);	
	@SuppressWarnings("unchecked")
	public int getSalesIncEvaluationInquiryListCnt(Object object);
	/*营业员评价  --明细*/
	@SuppressWarnings("unchecked")
	public List getSalesIncEvaluationInquiryDetailList(Object object, int currentPage, int pageSize);
	@SuppressWarnings("unchecked")
	public List getSalesIncEvaluationInquiryDetailList(Object object);	
	@SuppressWarnings("unchecked")
	public int getSalesIncEvaluationInquiryDetailListCnt(Object object);
	@SuppressWarnings("unchecked")
	public Map getEvaluationRatioByItemForAdjust(Object object);
	@SuppressWarnings("unchecked")
	public Map updateEvaluationRatioByItemForAdjust(Map paramMap) throws Exception;
	
	/* 营业员个人评价结果--汇总*/
	@SuppressWarnings("unchecked")
	public Map getPersonalEvaluationResultInfo(Object object);
	/*营业员个人评价结果  --明细*/
	@SuppressWarnings("unchecked")
	public Map getPersonalEvaluationResultDetailInfo(Object object);
	
	/*取工资关帐标志*/
	@SuppressWarnings("unchecked")
	public int getSalesIncPayClosedFlag(Object object);
	
	/*营业员评价执行*/
	@SuppressWarnings("unchecked")
	public Map callSalesmanEvaluation(Map map) throws Exception;
	
	/*营业员评价项目 code list*/
	@SuppressWarnings("unchecked")
	public List getSalesEvaluationTypeCodeList(Object object);	
	/*大区 code list*/
	@SuppressWarnings("unchecked")
	public List getPayAreaCodeList(Object object);
}

package com.ait.inct.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.ait.sys.bean.AdminBean;
import com.ait.inct.dao.SalesmanEvaluationDao;
import com.ait.inct.service.SalesmanEvaluationSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
/**
 * 
 * @fileName SalesmanEvaluationSerImpl.java
 * @author 			@Date			@version		@Desc
 * ---------------------------------------------------------------------------------------------------------------
 * penghaixia 		2014-6-4 		1.0				initial  
 * ----------------------------------------------------------------------------------------------------------------
 */
@Service
public class SalesmanEvaluationSerImpl implements SalesmanEvaluationSer {

	@Autowired
	private SalesmanEvaluationDao salesmanEvaluationDao;

	/**
	 * 评价数据导入查询
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEvaluationDataImportList(HttpServletRequest request, Map paramMap){
		List retrunList = new ArrayList() ;
		if (UiUtil.getPageNum(request) > 0){
			retrunList =  salesmanEvaluationDao.getEvaluationDataImportList(paramMap , 
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
					);
		}else{
			retrunList =  salesmanEvaluationDao.getEvaluationDataImportList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 评价数据导入查询
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getEvaluationDataImportListCnt(HttpServletRequest request, Map paramMap){
		return salesmanEvaluationDao.getEvaluationDataImportListCnt(paramMap);
	}
	
	/**
	 * 评价数据导入结果查询
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEvaluationDataImportResultList(HttpServletRequest request, Map paramMap){
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getEmpID());
		if (UiUtil.getPageNum(request) > 0){
			retrunList =  salesmanEvaluationDao.getEvaluationDataImportResultList(paramMap , 
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
					);
		}else{
			retrunList =  salesmanEvaluationDao.getEvaluationDataImportResultList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 评价数据导入结果导出
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEvaluationDataImportResultListExcel(HttpServletRequest request, Map paramMap){
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getEmpID());
		retrunList =  salesmanEvaluationDao.getEvaluationDataImportResultList(paramMap);
		return retrunList;
	}
	
	/**
	 * 评价数据导入结果查询
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getEvaluationDataImportResultListCnt(HttpServletRequest request, Map paramMap){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getEmpID());
		return salesmanEvaluationDao.getEvaluationDataImportResultListCnt(paramMap);
	}
	
	/**
	 * 评价数据导入结果查询
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getEvaluationDataImportErrCnt(HttpServletRequest request, Map paramMap){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getEmpID());
		return salesmanEvaluationDao.getEvaluationDataImportErrCnt(paramMap);
	}
	
	/**
	 * 评价数据验证并导入正式表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String importSalesEvalRAWFromExcel(HttpServletRequest request, Map paramMap) {
		String result = "";
		result = this.salesmanEvaluationDao.importSalesEvalRAWFromExcel(paramMap);
		return result;
	}
	
	/**
	 * 评价数据导出Excel
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEvaluationDataImportListExcel(HttpServletRequest request, Map paramMap)throws Exception {
		List retrunList = new ArrayList() ;	
		retrunList = salesmanEvaluationDao.getEvaluationDataImportList(paramMap) ;		
		return retrunList ;
	}
	/**
	 * 评价项目权重设置查询
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@Override
	public List getEvaluationItemWeightList(HttpServletRequest request,Map paramMap){		
		List retrunList = new ArrayList() ;
		if (UiUtil.getPageNum(request) > 0){
			retrunList = salesmanEvaluationDao.getEvaluationItemWeightList(paramMap , 
				UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
				);
		}else{
			retrunList = salesmanEvaluationDao.getEvaluationItemWeightList(paramMap) ;
		}
		return retrunList;
	}
	/**
	 * 评价项目权重设置查询
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@Override
	public int getEvaluationItemWeightListCnt(HttpServletRequest request,Map paramMap){		
		return salesmanEvaluationDao.getEvaluationItemWeightListCnt(paramMap);
	}
	/**
	 * 评价项目权重设置   导出Excel
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@Override
	public List getEvaluationItemWeightListExcel(HttpServletRequest request,Map paramMap)
			throws Exception {		
		List retrunList = new ArrayList() ;				
		retrunList = salesmanEvaluationDao.getEvaluationItemWeightList(paramMap) ;		
		return retrunList ;
	}
	/**
	 * 评价项目权重设置   (新增)
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map getEvaluationWeightByItem(HttpServletRequest request,Map paramMap){
		return this.salesmanEvaluationDao.getEvaluationWeightByItem(paramMap);
	}	
	
	// @Create date: 2014.06.10
	@Override
	@SuppressWarnings("unchecked")
	public int updateEvaluationWeightByItem(HttpServletRequest request,Map paramMap) {
		try {
			this.salesmanEvaluationDao.updateEvaluationWeightByItem(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int createEvaluationWeightByItem(HttpServletRequest request,Map paramMap) {
		try {			
			int existFlag = this.salesmanEvaluationDao.getEvaluationItemWeightListCnt(paramMap);
			if(existFlag>0){
				return 2;//数据已存在 
			}else{
				this.salesmanEvaluationDao.createEvaluationItemWeight(paramMap);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**
	 * 评价项目系数设置(查询)
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public List getSalesIncEvaluationRatioList(HttpServletRequest request,Map paramMap) {
		List retrunList = new ArrayList() ;
		if (UiUtil.getPageNum(request) > 0){
			retrunList = salesmanEvaluationDao.getSalesIncEvaluationRatioList(paramMap , 
				UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
				);
		}else{
			retrunList = salesmanEvaluationDao.getSalesIncEvaluationRatioList(paramMap) ;
		}
		return retrunList;
	}

	@Override
	public int getSalesIncEvaluationRatioListCnt(HttpServletRequest request,Map paramMap) {
		return salesmanEvaluationDao.getSalesIncEvaluationRatioListCnt(paramMap);
	}

	@Override
	public List getEvaluationItemRatioListExcel(HttpServletRequest request,Map paramMap)
			throws Exception {		
		List retrunList = new ArrayList() ;
		retrunList = salesmanEvaluationDao.getSalesIncEvaluationRatioList(paramMap);		
		return retrunList ;
	}

	@Override
	public Map getEvaluationRatioByItem(HttpServletRequest request,Map paramMap) {		
		return this.salesmanEvaluationDao.getEvaluationRatioByItem(paramMap);
	}

	@Override
	public int updateEvaluationRatioByItem(HttpServletRequest request,Map paramMap) {
		try {		
			int dupPeriod = this.salesmanEvaluationDao.getEvaluationRatioDupPeriodCnt(paramMap);
			if(dupPeriod>0){
				return 2;
			}else{
				this.salesmanEvaluationDao.updateEvaluationRatioByItem(paramMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int createEvaluationRatioByItem(HttpServletRequest request,Map paramMap) {
		try {
			int dupPeriod = this.salesmanEvaluationDao.getEvaluationRatioDupPeriodCnt(paramMap);
			if(dupPeriod>0){
				return 2;
			}else{
				this.salesmanEvaluationDao.createEvaluationItemRatio(paramMap);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**
	 * 营业员评价 --汇总
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public List getSalesIncEvaluationInquiryList(HttpServletRequest request, Map paramMap) {		
		List retrunList = new ArrayList() ;
		if (UiUtil.getPageNum(request) > 0){
			retrunList = salesmanEvaluationDao.getSalesIncEvaluationInquiryList(paramMap , 
				UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
				);
		}else{
			retrunList = salesmanEvaluationDao.getSalesIncEvaluationInquiryList(paramMap) ;
		}
		return retrunList;
	}

	@Override
	public int getSalesIncEvaluationInquiryListCnt(HttpServletRequest request, Map paramMap) {
		return salesmanEvaluationDao.getSalesIncEvaluationInquiryListCnt(paramMap);
	}

	@Override
	public List getSalesIncEvaluationInquiryListExcel(HttpServletRequest request, Map paramMap)
			throws Exception {		
		List retrunList = new ArrayList() ;
		retrunList = salesmanEvaluationDao.getSalesIncEvaluationInquiryList(paramMap);		
		return retrunList ;
	}
	/**
	 * 营业员评价 --明细
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public List getSalesIncEvaluationInquiryDetailList(
			HttpServletRequest request, Map paramMap) {		
		List retrunList = new ArrayList() ;
		if (UiUtil.getPageNum(request) > 0){
			retrunList = salesmanEvaluationDao.getSalesIncEvaluationInquiryDetailList(paramMap , 
				UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
				);
		}else{
			retrunList = salesmanEvaluationDao.getSalesIncEvaluationInquiryDetailList(paramMap) ;
		}
		return retrunList;
	}

	@Override
	public int getSalesIncEvaluationInquiryDetailListCnt(
			HttpServletRequest request, Map paramMap) {		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		return salesmanEvaluationDao.getSalesIncEvaluationInquiryDetailListCnt(paramMap);
	}

	@Override
	public List getSalesIncEvaluationInquiryDetailListExcel(
			HttpServletRequest request, Map paramMap) throws Exception {
		List retrunList = new ArrayList() ;		
		retrunList = salesmanEvaluationDao.getSalesIncEvaluationInquiryDetailList(paramMap);		
		return retrunList ;
	}
	/**
	 * 营业员个人评价结果 （汇总）
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public Map getPersonalEvaluationResultInfo(Map paramMap) {
		return this.salesmanEvaluationDao.getPersonalEvaluationResultInfo(paramMap);
	}

	@Override
	public Map getPersonalEvaluationResultInfoExcel(Map paramMap) throws Exception {
		return salesmanEvaluationDao.getPersonalEvaluationResultInfo(paramMap) ;
	}
	/**
	 * 营业员个人评价结果 （明细）
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public Map getPersonalEvaluationResultDetailInfo(Map paramMap) {
		return this.salesmanEvaluationDao.getPersonalEvaluationResultDetailInfo(paramMap);
	}

	@Override
	public Map getPersonalEvaluationResultDetailInfoExcel(
			Map paramMap) throws Exception {		
		return salesmanEvaluationDao.getPersonalEvaluationResultDetailInfo(paramMap) ;
	}
	
	/*取工资关帐标志*/
	@Override
	public String getSalesIncPayClosedFlag(Object object) {
		return salesmanEvaluationDao.getSalesIncPayClosedFlag(object)==0?"N":"Y" ;
	}
	
	/*营业员评价执行*/
	@Override
	public Map callSalesmanEvaluation(Map paramMap) {	
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = salesmanEvaluationDao.callSalesmanEvaluation(paramMap);				
			String msg =  ObjectUtils.toString(map.get("MESSAGE")) ;
			if(msg.equals("OK")) map.put("result", 1); else map.put("result", 0);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", 0);
		}
		return map;
	}
	/*营业员评价项目 code list*/
	@Override
	public List getSalesEvaluationTypeCodeList(Map paramMap){
		List retrunList = new ArrayList() ;		
		retrunList = salesmanEvaluationDao.getSalesEvaluationTypeCodeList(paramMap);		
		return retrunList ;
	}
	
	/*大区code list*/
	@Override
	public List getPayAreaCodeList(Map paramMap){
		List retrunList = new ArrayList() ;		
		retrunList = salesmanEvaluationDao.getPayAreaCodeList(paramMap);		
		return retrunList ;
	}
	
	@Override
	public Map getEvaluationRatioByItemForAdjust(HttpServletRequest request,Map paramMap) {		
		return this.salesmanEvaluationDao.getEvaluationRatioByItemForAdjust(paramMap);
	}
	
	@Override
	public int updateEvaluationRatioByItemForAdjust(HttpServletRequest request,Map paramMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {		
			map = this.salesmanEvaluationDao.updateEvaluationRatioByItemForAdjust(paramMap);
			String msg =  ObjectUtils.toString(map.get("MESSAGE")) ;
			if(msg.equals("OK")) return 1; else return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}

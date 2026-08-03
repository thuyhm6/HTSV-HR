package com.ait.inct.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.sys.bean.AdminBean;
import com.ait.inct.dao.SalesmanEvaluationDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

/**
 * 
 * @fileName SalesmanEvaluationDaoImpl.java
 * @author 			@Date			@version		@Desc
 * ---------------------------------------------------------------------------------------------------------------
 * penghaixia 		2014-6-4 		1.0				initial  
 *----------------------------------------------------------------------------------------------------------------
 */
@Repository
public class SalesmanEvaluationDaoImpl extends SqlMapClientSupport implements SalesmanEvaluationDao {
	/**
	 * 评价数据导入
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEvaluationDataImportList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("inct.salesman.getEvaluationDataImportList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("inct.salesman.getEvaluationDataImportList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}	
	/**
	 * 评价数据导入
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public List getEvaluationDataImportList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getEvaluationDataImportList(obj, -1, -1) ;
		
		return returnList ;
	}
	/**
	 * 评价数据导入
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public int getEvaluationDataImportListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("inct.salesman.getEvaluationDataImportListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	
	
	/**
	 * 评价数据导入结果
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEvaluationDataImportResultList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("inct.salesman.getEvaluationDataImportResultList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("inct.salesman.getEvaluationDataImportResultList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}	
	/**
	 * 评价数据导入结果
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public List getEvaluationDataImportResultList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getEvaluationDataImportResultList(obj, -1, -1) ;
		
		return returnList ;
	}
	/**
	 * 评价数据导入结果
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public int getEvaluationDataImportResultListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("inct.salesman.getEvaluationDataImportResultListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	/**
	 * 评价数据导入结果
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public int getEvaluationDataImportErrCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("inct.salesman.getEvaluationDataImportErrCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 评价数据导入(验证通过后--从临时表导入到正式表)
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String importSalesEvalRAWFromExcel(Map paramMap) {
		String returnString = "" ;		
		try {
			paramMap.put("message", "") ;
			this.insert("inct.salesman.importSalesEvalRAWFromExcel", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			e.printStackTrace();
		}
		return returnString ;
	}
	
	/**
	 * 评价项目权重设置
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public List getEvaluationItemWeightList(Object object, int currentPage,
			int pageSize) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("inct.salesman.getEvaluationItemWeightList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("inct.salesman.getEvaluationItemWeightList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 评价项目权重设置
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public List getEvaluationItemWeightList(Object obj) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		
		returnList = this.getEvaluationItemWeightList(obj, -1, -1) ;
		
		return returnList ;
	}
	/**
	 * 评价项目权重设置
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public int getEvaluationItemWeightListCnt(Object obj){
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("inct.salesman.getEvaluationItemWeightListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 评价项目权重设置(单行查询)
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */	
	@SuppressWarnings("unchecked")
	@Override
	public Map getEvaluationWeightByItem(Object object){
		Map temp = null;
		try {
			temp = (Map) this.queryForObject("inct.salesman.getEvaluationItemWeightList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp!=null?temp:null;
	}
	/**
	 * 评价项目权重设置(修改)
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */	
	@Override
	public void updateEvaluationWeightByItem(Object object) throws Exception{
		this.update("inct.salesman.updateEvaluationItemWeight",object);
	}
	
	/**
	 * 评价项目权重设置(新增)
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */	
	@Override
	public void createEvaluationItemWeight(Object object) throws Exception{
		this.insert("inct.salesman.createEvaluationItemWeight", object);	
	}
	/**
	 * 评价项目系数设置(查询)
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public List getSalesIncEvaluationRatioList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("inct.salesman.getSalesIncEvaluationRatioList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("inct.salesman.getSalesIncEvaluationRatioList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	@Override
	public List getSalesIncEvaluationRatioList(Object object) {
		List returnList = new ArrayList() ;
		
		returnList = this.getSalesIncEvaluationRatioList(object, -1, -1) ;
		
		return returnList ;
	}
	@Override
	public int getSalesIncEvaluationRatioListCnt(Object object) {
		int rtn = 0 ;
		try {
			rtn = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("inct.salesman.getSalesIncEvaluationRatioListCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			rtn = 0;
		}
		return rtn ;
	}
	/**
	 * 评价项目系数设置(查询--单行)
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public Map getEvaluationRatioByItem(Object object) {
		Map temp = null;
		try {
			temp = (Map) this.queryForObject("inct.salesman.getSalesIncEvaluationRatioList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp!=null?temp:null;
	}
	/* 重复期间检查 */
	@Override
	public int getEvaluationRatioDupPeriodCnt(Object object) {
		int rtn = 0 ;
		try {
			rtn = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("inct.salesman.getEvaluationRatioDupPeriodCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			rtn = 0;
		}
		return rtn ;
	}
	/**
	 * 评价项目系数设置(修改)
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public void updateEvaluationRatioByItem(Object object)   throws Exception{
		this.update("inct.salesman.updateSalesIncEvaluationRatio",object);
	}
	/**
	 * 评价项目系数设置(新增)
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public void createEvaluationItemRatio(Object object)  throws Exception{
		this.insert("inct.salesman.creatSalesIncEvaluationRatio", object);
	}
	/**
	 * 营业员评价(查询--汇总)
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public List getSalesIncEvaluationInquiryList(Object object,
			int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("inct.salesman.getSalesIncEvaluationInquiryList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("inct.salesman.getSalesIncEvaluationInquiryList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	@Override
	public List getSalesIncEvaluationInquiryList(Object object) {
		List returnList = new ArrayList() ;		
		returnList = this.getSalesIncEvaluationInquiryList(object, -1, -1) ;		
		return returnList ;
	}
	@Override
	public int getSalesIncEvaluationInquiryListCnt(Object object) {
		int rtn = 0 ;
		try {
			rtn = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("inct.salesman.getSalesIncEvaluationInquiryListCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			rtn = 0;
		}
		return rtn ;
	}
	/**
	 * 营业员评价(查询--明细)
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public List getSalesIncEvaluationInquiryDetailList(Object object,
			int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("inct.salesman.getSalesIncEvaluationInquiryDetailList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("inct.salesman.getSalesIncEvaluationInquiryDetailList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	@Override
	public List getSalesIncEvaluationInquiryDetailList(Object object) {
		List returnList = new ArrayList() ;		
		returnList = this.getSalesIncEvaluationInquiryDetailList(object, -1, -1) ;		
		return returnList ;
	}
	@Override
	public int getSalesIncEvaluationInquiryDetailListCnt(Object object) {
		int rtn = 0 ;
		try {
			rtn = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("inct.salesman.getSalesIncEvaluationInquiryDetailListCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			rtn = 0;
		}
		return rtn ;
	}
	/**
	 * 营业员个人评价结果(查询汇总)
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public Map getPersonalEvaluationResultInfo(Object object) {
		Map temp = null;
		try {
			temp = (Map) this.queryForObject("inct.salesman.getPersonalEvaluationResultInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp!=null?temp:null;
	}

	/**
	 * 营业员个人评价结果(查询明细)
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public Map getPersonalEvaluationResultDetailInfo(Object object) {		
		Map temp = null;
		try {
			temp = (Map) this.queryForObject("inct.salesman.getPersonalEvaluationResultDetailInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp!=null?temp:null;
	}
	/*取工资关帐标志*/
	@Override
	public int getSalesIncPayClosedFlag(Object object) {
		int rtn = 0 ;
		try {
			rtn = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("inct.salesman.getSalesInctCalcClosFlagByQuarter", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			rtn = 0;
		}
		return rtn ;
	}
	/*营业员评价执行*/
	@Override
	public Map callSalesmanEvaluation(Map paramMap) throws Exception {
		this.insert("inct.salesman.callSalesEvaluation", paramMap);	
		return paramMap;
	}
	/*营业员评价项目 code list*/
	@Override
	public List getSalesEvaluationTypeCodeList(Object object){
		List returnList = new ArrayList() ;		
		try {
			returnList = this.queryForList("inct.salesman.getSalesEvaluationTypeCodeList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return returnList ;
	};
	/*大区 list*/
	@Override
	public List getPayAreaCodeList(Object object){
		List returnList = new ArrayList() ;		
		try {
			returnList = this.queryForList("inct.salesman.getPayAreaCodeList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return returnList ;
	};
	
	/**
	 * 评价项结果明细系数调整(查询--单行)
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public Map getEvaluationRatioByItemForAdjust(Object object) {
		Map temp = null;
		try {
			temp = (Map) this.queryForObject("inct.salesman.getSalesIncEvaluationInquiryDetailList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp!=null?temp:null;
	}
	
	/**
	 * 评价项结果明细系数调整(修改)
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	public Map updateEvaluationRatioByItemForAdjust(Map paramMap)   throws Exception{	
		int updatedCount = 0;
		try {
			updatedCount = (Integer)this.update("inct.salesman.updateSalesIncEvaluationRatioForAdjust",paramMap);
			if(updatedCount==1){
				this.insert("inct.salesman.callSalesEvaluationFuncForAdjust", paramMap);	
			}else{
				paramMap.put("MESSAGE", "");
			}
		}catch (SQLException e) {
			e.printStackTrace();
			paramMap.put("MESSAGE", "");
		}
		return paramMap;
	}
}

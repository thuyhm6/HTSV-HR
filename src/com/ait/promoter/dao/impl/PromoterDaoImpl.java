package com.ait.promoter.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.promoter.dao.PromoterDao;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

/**
 * Copyright:    LG CNS
 * Company:      LG CNS
 * @fileName:    PromoterDaoImpl.java
 * @Create date: 2014.06.09
 * @Create by:   CH.W.G
 * @version 1.0
 */
@Repository
public class PromoterDaoImpl extends SqlMapClientSupport implements
		PromoterDao {

	/**
	 * 固定工资设置
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @Create date: 2014.06.09
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getFixedPayList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("promoter.searchFixedPayList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("promoter.searchFixedPayList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 固定工资设置
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.09
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getFixedPayList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getFixedPayList(obj, -1, -1) ;
		
		return returnList ;
	}

	/**
	 * 固定工资设置
	 * @param obj
	 * @return int
	 * @Create date: 2014.06.09
	 */
	@Override
	public int getFixedPayListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.searchFixedPayListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	// @Create date: 2014.06.09
	@SuppressWarnings("rawtypes")
	public Object getFixedPayInfo(Object obj) {
		Object returnObj = new Object();
		List returnList = this.getFixedPayList(obj);
		if (returnList.size() > 0) {
			returnObj = returnList.get(0);
		}
		return returnObj;
	}

	// @Create date: 2014.06.09
	public void updateFixedPay(Object obj) throws Exception {
		this.update("promoter.updateFixedPay", obj) ;
	}

	// @Create date: 2014.06.13
	public void addFixedPay(Object obj) throws Exception {
		this.update("promoter.addFixedPay", obj) ;
	}
	
	/**************************************************************************************************************/
	/**
	 * 城市等级设置信息
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getCityLevelList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("promoter.searchCityLevelList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("promoter.searchCityLevelList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 城市等级信息
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getCityLevelList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getCityLevelList(obj, -1, -1) ;
		
		return returnList ;
	}

	/**
	 * 城市等级信息
	 * @param obj
	 * @return int
	 * @Create date: 2014.06.10
	 */
	@Override
	public int getCityLevelListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.searchCityLevelListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	// @Create date: 2014.06.10
	@Override
	public Object getCityLevelInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject("promoter.searchCityLevelList",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return object2;
	}

	// @Create date: 2014.06.10
	public void updateCityLevel(Object obj) throws Exception {
		this.update("promoter.updateCityLevel", obj) ;
	}
	
	/**************************************************************************************************************/
	/**
	 * 门店基本信息设置
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getCustInfoList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("promoter.searchCustInfoList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("promoter.searchCustInfoList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 门店基本信息设置
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getCustInfoList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getCustInfoList(obj, -1, -1) ;
		
		return returnList ;
	}

	/**
	 * 门店基本信息设置
	 * @param obj
	 * @return int
	 * @Create date: 2014.06.10
	 */
	@Override
	public int getCustInfoListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.searchCustInfoListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	// @Create date: 2014.06.10
	@Override
	public Object getCustInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject("promoter.searchCustInfoList",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return object2;
	}

	// @Create date: 2014.06.10
	public void updateCustInfo(Object obj) throws Exception {
		this.update("promoter.updateCustInfo", obj) ;
	}
	
	/**************************************************************************************************************/
	@SuppressWarnings({ "rawtypes" })
	/* 促销员提成计算
	 * @Create date: 2014.06.17
	 */
	@Override
	public List getSalesAchLocalList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			Map paramMap = (Map)obj;
			String ls_sql = "";
			ls_sql = paramMap.get("search_Type")==null ?"promoter.searchSalesAchLocalList"
					:( (paramMap.get("search_Type").equals("DTL")) ? "promoter.searchSalesAchLocalListD" : "promoter.searchSalesAchLocalList" ) ;
			
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList(ls_sql, obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList(ls_sql, obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 促销员提成计算
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.09
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getSalesAchLocalList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getFixedPayList(obj, -1, -1) ;
		
		return returnList ;
	}

	/**
	 * 促销员提成计算
	 * @param obj
	 * @return int
	 * @Create date: 2014.06.17
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public int getSalesAchLocalListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			Map paramMap = (Map)obj;
			String ls_sql = "";
			ls_sql = paramMap.get("search_Type")==null ?"promoter.searchSalesAchLocalListCnt"
					:( (paramMap.get("search_Type").equals("DTL")) ? "promoter.searchSalesAchLocalListDCnt" : "promoter.searchSalesAchLocalListCnt" ) ;
			
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject(ls_sql, obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	// * @Create date: 2014.06.17
	public void calSalesAchLocal(Object paramMap) throws Exception
	{
		this.insert("promoter.calSalesAchLocal", paramMap);
	}

	/**************************************************************************************************************/
	/**
	 * 总公司单台提成设置信息
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @Create date: 2014.06.19
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getIncBasicSetupList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("promoter.searchIncBasicSetupList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("promoter.searchIncBasicSetupList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 总公司单台提成设置
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.19
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getIncBasicSetupList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getIncBasicSetupList(obj, -1, -1) ;
		
		return returnList ;
	}

	/**
	 * 总公司单台提成设置
	 * @param obj
	 * @return int
	 * @Create date: 2014.06.19
	 */
	@Override
	public int getIncBasicSetupListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.searchIncBasicSetupListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	// @Create date: 2014.06.19
	@Override
	public Object getIncBasicSetupInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject("promoter.searchIncBasicSetupList",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return object2;
	}

	// @Create date: 2014.06.19
	public void updateIncBasicSetup(Object obj) throws Exception {
		this.update("promoter.updateIncBasicSetup", obj) ;
	}
	
	/**************************************************************************************************************/
	/**
	 * 大区单台提成调整信息
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @Create date: 2014.06.20
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getOfficeIncAdjustList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("promoter.searchOfficeIncAdjustList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("promoter.searchOfficeIncAdjustList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 大区单台提成调整
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.20
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getOfficeIncAdjustList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getOfficeIncAdjustList(obj, -1, -1) ;
		
		return returnList ;
	}

	/**
	 * 大区单台提成调整
	 * @param obj
	 * @return int
	 * @Create date: 2014.06.20
	 */
	@Override
	public int getOfficeIncAdjustListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.searchOfficeIncAdjustListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	// @Create date: 2014.06.20
	@Override
	public Object getOfficeIncAdjustInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject("promoter.searchOfficeIncAdjustList",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return object2;
	}

	// @Create date: 2014.06.20
	public void updateOfficeIncAdjust(Object obj) throws Exception {
		this.update("promoter.updateOfficeIncAdjust", obj) ;
	}

	// @Create date: 2014.06.20
	public void addOfficeIncAdjust(Object obj) throws Exception {
		this.update("promoter.addOfficeIncAdjust", obj) ;
	}

	/**************************************************************************************************************/
	/**
	 * 日别单台提成设置
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @Create date: 2014.06.23
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getIncBasicSetupByDayList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("promoter.searchIncBasicSetupByDayList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("promoter.searchIncBasicSetupByDayList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 日别单台提成设置
	 * @param obj
	 * @return List
	 * @Create date: 2014.06.23
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getIncBasicSetupByDayList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getIncBasicSetupByDayList(obj, -1, -1) ;
		
		return returnList ;
	}

	/**
	 * 日别单台提成设置
	 * @param obj
	 * @return int
	 * @Create date: 2014.06.23
	 */
	@Override
	public int getIncBasicSetupByDayListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.searchIncBasicSetupByDayListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	// @Create date: 2014.06.23
	@Override
	public Object getIncBasicSetupByDayInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject("promoter.searchIncBasicSetupByDayList",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return object2;
	}

	// @Create date: 2014.06.23
	public void updateIncBasicSetupByDay(Object obj) throws Exception {
		this.update("promoter.updateIncBasicSetupByDay", obj) ;
	}

	// @Create date: 2014.06.23
	public void addIncBasicSetupByDay(Object obj) throws Exception {
		this.update("promoter.addIncBasicSetupByDay", obj) ;
	}

	// @Create date: 2014.08.07
	@Override
	public int incBasicCheckProdId(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.incBasicCheckProdId", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	// @Create date: 2014.08.07
	@Override
	public int incBasicCheckPrcDayCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.incBasicCheckPrcDayCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**************************************************************************************************************/
	/**
	 * 最小目标管理
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @Create date: 2014.06.24
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getMinGoalSetupList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("promoter.searchMinGoalSetupList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("promoter.searchMinGoalSetupList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * @Create date: 2014.06.24
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getMinGoalSetupList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getMinGoalSetupList(obj, -1, -1) ;
		
		return returnList ;
	}

	/**
	 * @Create date: 2014.06.24
	 */
	@Override
	public int getMinGoalSetupListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.searchMinGoalSetupListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	// @Create date: 2014.06.24
	@Override
	public Object getMinGoalSetupInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject("promoter.searchMinGoalSetupList",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return object2;
	}

	// @Create date: 2014.06.24
	public void updateMinGoalSetup(Object obj) throws Exception {
		this.update("promoter.updateMinGoalSetup", obj) ;
	}

	// @Create date: 2014.06.24
	public void addMinGoalSetup(Object obj) throws Exception {
		this.update("promoter.addMinGoalSetup", obj) ;
	}
	
	/**************************************************************************************************************/
	/**
	 * 达成率对应指标设置
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @Create date: 2014.06.24
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getSalsRateList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("promoter.searchSalsRateList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("promoter.searchSalsRateList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * @Create date: 2014.06.24
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getSalsRateList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getSalsRateList(obj, -1, -1) ;
		
		return returnList ;
	}

	/**
	 * @Create date: 2014.06.24
	 */
	@Override
	public int getSalsRateListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.searchSalsRateListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	// @Create date: 2014.06.24
	@Override
	public Object getSalsRateInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject("promoter.searchSalsRateList",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return object2;
	}

	// @Create date: 2014.06.24
	public void updateSalsRate(Object obj) throws Exception {
		this.update("promoter.updateSalsRate", obj) ;
	}

	// @Create date: 2014.06.24
	public void addSalsRate(Object obj) throws Exception {
		this.update("promoter.addSalsRate", obj) ;
	}

	/**************************************************************************************************************/
	/**
	 * 促销员担当产品
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @Create date: 2014.06.25
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getIncProdTpByPromoterList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("promoter.searchIncProdTpByPromoterList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("promoter.searchIncProdTpByPromoterList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * @Create date: 2014.06.25
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getIncProdTpByPromoterList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getIncProdTpByPromoterList(obj, -1, -1) ;
		
		return returnList ;
	}

	/**
	 * @Create date: 2014.06.25
	 */
	@Override
	public int getIncProdTpByPromoterListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.searchIncProdTpByPromoterListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	// @Create date: 2014.06.25
	@Override
	public Object getIncProdTpByPromoterInfo(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject("promoter.searchIncProdTpByPromoterList",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return object2;
	}

	// @Create date: 2014.06.25
	public void updateIncProdTpByPromoter(Object obj) throws Exception {
		this.update("promoter.updateIncProdTpByPromoter", obj) ;
	}

	/**************************************************************************************************************/
	@SuppressWarnings("rawtypes")
	/* 促销员实绩上报
	 * @Create date: 2014.06.26
	 */
	@Override
	public List getPromoterSelloutUploadList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("promoter.searchPromoterSelloutUploadList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("promoter.searchPromoterSelloutUploadList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * @Create date: 2014.06.26
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getPromoterSelloutUploadList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getFixedPayList(obj, -1, -1) ;
		
		return returnList ;
	}

	/**
	 * @Create date: 2014.06.26
	 */
	@Override
	public int getPromoterSelloutUploadListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.searchPromoterSelloutUploadListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	/**************************************************************************************************************/
	/* 促销员实绩上报确认
	 * @Create date: 2014.08.19
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List getPromoterSelloutConfirmList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("promoter.searchPromoterSelloutConfirmList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("promoter.searchPromoterSelloutConfirmList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * @Create date: 2014.08.19
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getPromoterSelloutConfirmList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPromoterSelloutConfirmList(obj, -1, -1) ;
		return returnList ;
	}

	/**
	 * @Create date: 2014.08.19
	 */
	@Override
	public int getPromoterSelloutConfirmListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.searchPromoterSelloutConfirmListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List viewPromoterSelloutConfirmList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("promoter.viewPromoterSelloutConfirmList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("promoter.viewPromoterSelloutConfirmList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * @Create date: 2014.08.19
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List viewPromoterSelloutConfirmList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.viewPromoterSelloutConfirmList(obj, -1, -1) ;
		return returnList ;
	}

	/**
	 * @Create date: 2014.08.19
	 */
	@Override
	public int viewPromoterSelloutConfirmListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.viewPromoterSelloutConfirmListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	// * @Create date: 2014.06.26
	public void selloutConfirm(Object paramMap) throws Exception
	{
		this.insert("promoter.selloutConfirm", paramMap);
	}

	/**************************************************************************************************************/
	@SuppressWarnings("rawtypes")
	/* 促销员实贩卖实绩读取
	 * @Create date: 2014.06.26
	 */
	@Override
	public List getSalesAchievementList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("promoter.searchSalesAchievementList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("promoter.searchSalesAchievementList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * @Create date: 2014.06.26
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getSalesAchievementList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getSalesAchievementList(obj, -1, -1) ;
		
		return returnList ;
	}

	/**
	 * @Create date: 2014.06.26
	 */
	@Override
	public int getSalesAchievementListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.searchSalesAchievementListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	// * @Create date: 2014.06.26
	public void reLoadSalesAchievement(Object paramMap) throws Exception
	{
		this.insert("promoter.reLoadSalesAchievement", paramMap);
	}

	// * @Create date: 2014.06.26
	public void calSalesAchievement(Object paramMap) throws Exception
	{
		this.insert("promoter.calSalesAchievement", paramMap);
	}

	/**************************************************************************************************************/
	/**
	 * @Create date: 2014.07.10
	 */
	@SuppressWarnings("rawtypes")
	public void deleteIncBasicSetupTemp(LinkedHashMap paramMap) throws Exception{
			this.delete("promoter.deleteIncBasicSetupTemp", paramMap);
	}

	/**
	 * @Create date: 2014.07.10
	 */
	@SuppressWarnings("rawtypes")
	public void insertIncBasicSetupTemp(Object obj) throws Exception{
		this.insertForList("promoter.insertExcelDataForTmp", (List) ((Map) obj).get("addList"));
	}
	
	/**
	 * @Create date: 2014.07.10
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public List getDataImportResultList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("promoter.getDataImportResultList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("promoter.getDataImportResultList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}	
	/**
	 * @Create date: 2014.07.10
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getDataImportResultList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getDataImportResultList(obj, -1, -1) ;
		
		return returnList ;
	}
	/**
	 * @Create date: 2014.07.10
	 */
	@Override
	public int getDataImportResultListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.getDataImportResultListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	/**
	 * @Create date: 2014.07.10
	 */
	@Override
	public int getDataImportErrCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.getDataImportErrCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String importIncBasicSetupFromExcel(LinkedHashMap paramMap) {
		String returnString = "" ;		
		try {
			paramMap.put("message", "") ;
			this.insert("promoter.importIncBasicSetupFromExcel", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			e.printStackTrace();
		}
		return returnString ;
	}

	/**************************************************************************************************************/
	/**
	 * @Create date: 2014.07.16
	 */
	@SuppressWarnings("rawtypes")
	public void deleteOfficeIncAdjustTemp(LinkedHashMap paramMap) throws Exception{
			this.delete("promoter.deleteOfficeIncAdjustTemp", paramMap);
	}

	/**
	 * @Create date: 2014.07.16
	 */
	@SuppressWarnings("rawtypes")
	public void insertOfficeIncAdjustTemp(Object obj) throws Exception{
		this.insertForList("promoter.insertExcelDataForTmp", (List) ((Map) obj).get("addList"));
	}
	
	/**
	 * @Create date: 2014.07.16
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public List getImportOfficeIncAdjustResultList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("promoter.getImportOfficeIncAdjustResultList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("promoter.getImportOfficeIncAdjustResultList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}	
	/**
	 * @Create date: 2014.07.16
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getImportOfficeIncAdjustResultList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getImportOfficeIncAdjustResultList(obj, -1, -1) ;
		
		return returnList ;
	}
	/**
	 * @Create date: 2014.07.16
	 */
	@Override
	public int getImportOfficeIncAdjustResultCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.getImportOfficeIncAdjustResultCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	/**
	 * @Create date: 2014.07.16
	 */
	@Override
	public int getImportOfficeIncAdjustErrCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.getImportOfficeIncAdjustErrCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String importOfficeIncAdjustFromExcel(LinkedHashMap paramMap) {
		String returnString = "" ;		
		try {
			paramMap.put("message", "") ;
			this.insert("promoter.importOfficeIncAdjustFromExcel", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			e.printStackTrace();
		}
		return returnString ;
	}

	/**************************************************************************************************************/
	/**
	 * @Create date: 2014.07.16
	 */
	@SuppressWarnings("rawtypes")
	public void deleteSelloutTemp(LinkedHashMap paramMap) throws Exception{
			this.delete("promoter.deleteSelloutTemp", paramMap);
	}

	/**
	 * @Create date: 2014.07.16
	 */
	@SuppressWarnings("rawtypes")
	public void insertSelloutTemp(Object obj) throws Exception{
		this.insertForList("promoter.insertExcelDataForTmp", (List) ((Map) obj).get("addList"));
	}
	
	/**
	 * @Create date: 2014.07.16
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public List getImportSelloutResultList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("promoter.getImportSelloutResultList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("promoter.getImportSelloutResultList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}	
	/**
	 * @Create date: 2014.07.16
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getImportSelloutResultList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getImportSelloutResultList(obj, -1, -1) ;
		
		return returnList ;
	}
	/**
	 * @Create date: 2014.07.16
	 */
	@Override
	public int getImportSelloutResultCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.getImportSelloutResultCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	/**
	 * @Create date: 2014.07.16
	 */
	@Override
	public int getImportSelloutErrCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.getImportSelloutErrCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String importSelloutFromExcel(LinkedHashMap paramMap) {
		String returnString = "" ;		
		try {
			paramMap.put("message", "") ;
			this.insert("promoter.importSelloutFromExcel", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			e.printStackTrace();
		}
		return returnString ;
	}
	
	/**************************************************************************************************************/

	@SuppressWarnings("rawtypes")
	public List getPayAreaCodeList(Object object){
		List returnList = new ArrayList() ;		
		try {
			returnList = this.queryForList("promoter.getBranchList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return returnList ;
	};

	/**
	 * @Create date: 2014.08.26
	 */
	@Override
	public String getLastMonth(Object obj) {
		String res = "" ;
		try {
			res = ObjectUtils.toString(this.queryForObject("promoter.getLastMonth", obj)) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return res ;
	}

	/**************************************************************************************************************/
	/**
	 * 促销员实绩审批申请列表查询
	 * @Create date: 2014.08.29
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List getSelloutRequestList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("promoter.searchSelloutRequestList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("promoter.searchSelloutRequestList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getSelloutRequestList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getSelloutRequestList(obj, -1, -1) ;
		
		return returnList ;
	}

	@Override
	public int getSelloutRequestListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.searchSelloutRequestListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/** @Create date: 2014.09.01 */
	@Override
	@SuppressWarnings("rawtypes")
	public List getSelloutReqDtlList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("promoter.searchSelloutReqDtlList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("promoter.searchSelloutReqDtlList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getSelloutReqDtlList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getSelloutReqDtlList(obj, -1, -1) ;
		
		return returnList ;
	}

	@Override
	public int getSelloutReqDtlListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.searchSelloutReqDtlListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	// * @Create date: 2014.09.01
	@Override
	public void addSelloutReq(Object paramMap) throws Exception
	{
		this.insert("promoter.addSelloutReq", paramMap);
		//保存附件
		LinkedHashMap param = (LinkedHashMap)paramMap;
		if (param.get("Sell_fileName") != null && !"".equals(StringUtil.checkNull(param.get("Sell_fileName")))) {
			String[] fileName = StringUtil.checkNull(param.get("Sell_fileName")).split(";");
			String[] fileUrl = StringUtil.checkNull(param.get("Sell_fileUrl")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				LinkedHashMap fileMap = new LinkedHashMap();
				fileMap.put("APPLY_NO", param.get("REQ_ID"));
				fileMap.put("APPLY_TYPE", param.get("APPLY_TYPE_NO"));
				fileMap.put("CREATED_BY", param.get("CREATED_BY"));
				this.insert("ess.infoApplyLeave.deleteEssFile",fileMap);
				for (int j=0;j<fileUrl.length ;j++) {
					fileMap.put("fileName", fileName[j]);
					fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + param.get("PERSON_ID") + "/" + fileUrl[j]);
					this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
				}
			}
		}
	}
	public void updateSellInvoiceQty(Object obj) throws Exception {
		this.update("promoter.updateSellInvoiceQty", obj) ;
	}
	public void updateSellPayRate(Object obj) throws Exception {
		this.update("promoter.updateSellPayRate", obj) ;
	}
	public void updateSellPayRateRep(Object obj) throws Exception {
		this.update("promoter.updateSellPayRateRep", obj) ;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public LinkedHashMap getSelloutReqByReqId(Object object){
		try {
			return  (LinkedHashMap)this.queryForList("promoter.getSelloutReqByReqId", object).get(0);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return null ;
	}

	@Override
	public Long getNewReqId(Object obj) {
		Long returnInt = null ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.getNewReqId", obj)), Long.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	@SuppressWarnings("rawtypes")
	public List getAffirmorList(Object object) {
		List returnList = new ArrayList() ;
			try {
				returnList = this.queryForList("promoter.getAffirmorList", object);
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		return returnList ;
	}

	@SuppressWarnings("rawtypes")
	public List getCheckList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("promoter.getCheckList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**************************************************************************************************************/
	@SuppressWarnings("rawtypes")
	// * @Create date: 2014.09.10
	@Override
	public List getMenuThirdList(Map paramMap) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("promoter.getMenuThirdList", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public void insertAffirmor(Object object)  throws Exception{
		this.insert("promoter.insertAffirmor", object) ;
	}

	@Override
	public void deleteAffirmor(Object object)  throws Exception{
		this.delete("promoter.deleteAffirmor", object) ;
	}

	// * @Create date: 2014.09.16
	@Override
	public void affirmSellout(Object paramMap) throws Exception
	{
		this.insert("promoter.affirmSellout", paramMap);
	}

	// * @Create date: 2014.09.16
	@SuppressWarnings("rawtypes")
	public List getCheckListToLgep(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("promoter.getCheckListToLgep", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	// * @Create date: 2014.09.18
	@Override
	public void delSelloutReq(Object paramMap) throws Exception
	{
		this.insert("promoter.delSelloutReq", paramMap);
	}
	@Override
	public void submitSellout(Object paramMap) throws Exception
	{
		this.insert("promoter.submitSellout", paramMap);
	}
	@Override
	public void getSelloutFromCnmas(Object paramMap) throws Exception
	{
		this.insert("promoter.getSelloutFromCnmas", paramMap);
	}

	@SuppressWarnings("rawtypes")
	public List getReqOver10List(Map paramMap)
	{
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("promoter.getReqOver10List", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	@SuppressWarnings("rawtypes")
	public List getReqOver9kList(Map paramMap)
	{
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("promoter.getReqOver9kList", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	@SuppressWarnings("rawtypes")
	public List getReqRatioList(Map paramMap)
	{
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("promoter.getReqRatioList", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	@SuppressWarnings("rawtypes")
	public List getReqExshopList(Map paramMap)
	{
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("promoter.getReqExshopList", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	@SuppressWarnings("rawtypes")
	public List getReqReportList(Map paramMap, int currentPage, int pageSize)
	{
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("promoter.getReqReportList", paramMap, currentPage, pageSize);
			}else{
				returnList = this.queryForList("promoter.getReqReportList", paramMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public int getReqReportListCnt(Map paramMap) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.getReqReportListCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	@SuppressWarnings("rawtypes")
	public List getReqTotalList(Map paramMap)
	{
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("promoter.getReqTotalList", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	@SuppressWarnings("rawtypes")
	public List getNewReqOver10List(Map paramMap)
	{
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("promoter.getNewReqOver10List", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	@SuppressWarnings("rawtypes")
	public List getNewReqOver9kList(Map paramMap)
	{
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("promoter.getNewReqOver9kList", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	@SuppressWarnings("rawtypes")
	public List getNewReqRatioList(Map paramMap)
	{
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("promoter.getNewReqRatioList", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	@SuppressWarnings("rawtypes")
	public List getNewReqExshopList(Map paramMap)
	{
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("promoter.getNewReqExshopList", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	@SuppressWarnings("rawtypes")
	public List getNewReqReportList(Map paramMap, int currentPage, int pageSize)
	{
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("promoter.getNewReqReportList", paramMap, currentPage, pageSize);
			}else{
				returnList = this.queryForList("promoter.getNewReqReportList", paramMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public int getNewReqReportListCnt(Map paramMap) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("promoter.getNewReqReportListCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	/**************************************************************************************************************/

	@SuppressWarnings("rawtypes")
	public void deleteimportTempEmp(LinkedHashMap paramMap) throws Exception{
			this.delete("promoter.deleteimportTempEmp", paramMap);
	}

	@SuppressWarnings("rawtypes")
	public void insertTempEmp(Object obj) throws Exception{
		this.insertForList("promoter.insertExcelDataForTmp", (List) ((Map) obj).get("addList"));
	}
	
}

package com.ait.promoter.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.NumberUtils;

import com.ait.affirm.service.AffirmInfoToLGEPSer;
import com.ait.ess.service.InfoApplySer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.promoter.dao.PromoterDao;
import com.ait.promoter.service.PromoterSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.LoginDao;
import com.ait.web.i18n.TipMessage;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.uploadpicture.uploadExcel;

/**
 * Copyright:    LG CNS
 * Company:      LG CNS
 * @fileName:    PromoterSerImpl.java
 * @Create date: 2014.06.09
 * @Create by:   CH.W.G
 * @version 1.0
 */
@Service
public class PromoterSerImpl implements PromoterSer {

	Logger logger = Logger.getLogger(PromoterSerImpl.class);

	@Autowired
	private PromoterDao promoterDao;
	
	@Autowired
	private LoginDao loginDao;

	@Autowired
	private AffirmInfoToLGEPSer affirmInfoToLGEPSer;
	
	String filename = "";

	String path = "";

	String pathCpnyID = "";

	String item_no = "";

	String type = "";

	String language = "";

	private static String APPLY_TYPE_NO = "278651";
	
	@Autowired
	private ExcelUtilSer excelUtilSer;
	
	/**
	 * 固定工资设置
	 * @param request
	 * @return List
	 * @Create date: 2014.06.09
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getFixedPayList(HttpServletRequest request) {
		
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ; 
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO",admin.getUserNo());
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
					promoterDao.getFixedPayList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = promoterDao.getFixedPayList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	// @Create date: 2014.06.09
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getFixedPayListCnt(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO",admin.getUserNo());
		
		return promoterDao.getFixedPayListCnt(paramMap) ;
	}
	
	// @Create date: 2014.06.09
	@Override
	@SuppressWarnings({"rawtypes", "unchecked" })
	public Object getFixedPayInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("USER_NO",admin.getUserNo());
		
		returnObj = promoterDao.getFixedPayInfo(paramMap) ;
		return returnObj ;
	}

	// @Create date: 2014.06.09
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int updateFixedPay(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("UPDATED_BY", admin.getUserNo()) ;
			this.promoterDao.updateFixedPay(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	// @Create date: 2014.06.13
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int addFixedPay(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("UPDATED_BY", admin.getUserNo()) ;
			this.promoterDao.addFixedPay(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**************************************************************************************************************/
	/**
	 * 城市等级信息
	 * @param request
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getCityLevelList(HttpServletRequest request) {

		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				promoterDao.getCityLevelList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = promoterDao.getCityLevelList(paramMap) ;
		}
		
		return retrunList ;
	}

	/**
	 * 城市等级信息
	 * @param request
	 * @return int
	 * @throws Exception
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getCityLevelListCnt(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		
		return promoterDao.getCityLevelListCnt(paramMap);
	}
	
	/**
	 * 城市等级信息Excel导出
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getCityLevelListExcel(HttpServletRequest request) throws Exception{
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		retrunList = promoterDao.getCityLevelList(paramMap) ;
		
		return retrunList ;
	}
	
	// @Create date: 2014.06.10
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object getCityLevelInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		return promoterDao.getCityLevelInfo(paramMap);
	}

	// @Create date: 2014.06.10
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int updateCityLevel(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("UPDATED_BY", admin.getUserNo()) ;
			this.promoterDao.updateCityLevel(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**************************************************************************************************************/
	/**
	 * 门店基本信息设置
	 * @param request
	 * @return List
	 * @Create date: 2014.06.10
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getCustInfoList(HttpServletRequest request) {

		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("USER_NO", admin.getUserNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		
        java.util.Date date = new java.util.Date();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
        if (paramMap.get("YEAR") == null || paramMap.get("YEAR").equals("")) {
        	paramMap.put("YEAR", "" + year);
        }
        
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				promoterDao.getCustInfoList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = promoterDao.getCustInfoList(paramMap) ;
		}
		
		return retrunList ;
	}

	/**
	 * 门店基本信息设置
	 * @param request
	 * @return int
	 * @throws Exception
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getCustInfoListCnt(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("USER_NO", admin.getUserNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
        java.util.Date date = new java.util.Date();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
        if (paramMap.get("YEAR") == null || paramMap.get("YEAR").equals("")) {
        	paramMap.put("YEAR", "" + year);
        }
        
		return promoterDao.getCustInfoListCnt(paramMap);
		
	}
	
	/**
	 * 门店基本信息Excel导出
	 * @param request
	 * @return
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getCustInfoListExcel(HttpServletRequest request) throws Exception{
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("USER_NO", admin.getUserNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
        java.util.Date date = new java.util.Date();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
        if (paramMap.get("YEAR") == null || paramMap.get("YEAR").equals("")) {
        	paramMap.put("YEAR", "" + year);
        }
		
		retrunList = promoterDao.getCustInfoList(paramMap) ;
		
		return retrunList ;
	}
	
	// @Create date: 2014.06.10
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object getCustInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("USER_NO", admin.getUserNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		
		return promoterDao.getCustInfo(paramMap);
	}

	// @Create date: 2014.06.10
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int updateCustInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("UPDATED_BY", admin.getUserNo()) ;
			this.promoterDao.updateCustInfo(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**************************************************************************************************************/
	/**
	 * 促销员提成计算
	 * @param request
	 * @return List
	 * @Create date: 2014.06.17
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSalesAchLocalList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO",admin.getUserNo());
		String year = request.getParameter("year") != null ? request.getParameter("year") : "";
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		paramMap.put("YEAR_MON", year + month);
		paramMap.put("ACC_YN", "N");
		if(paramMap.get("PAY_AREA_3") != null)
		{
			paramMap.put("PAY_AREA_CD",paramMap.get("PAY_AREA_3").toString());
		}
		if(paramMap.get("BRANCH_3") != null)
		{
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_3").toString());
		}
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
					promoterDao.getSalesAchLocalList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = promoterDao.getSalesAchLocalList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	// @Create date: 2014.06.17
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getSalesAchLocalListCnt(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO",admin.getUserNo());
		String year = request.getParameter("year") != null ? request.getParameter("year") : "";
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		paramMap.put("YEAR_MON", year + month);
		paramMap.put("ACC_YN", "N");
		if(paramMap.get("PAY_AREA_3") != null)
		{
			paramMap.put("PAY_AREA_CD",paramMap.get("PAY_AREA_3").toString());
		}
		if(paramMap.get("BRANCH_3") != null)
		{
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_3").toString());
		}
		
		return promoterDao.getSalesAchLocalListCnt(paramMap) ;
	}

	// @Create date: 2014.06.17
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map calSalesAchLocal(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("RET", 1);
		retMap.put("MESSAGE", "");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		String year = request.getParameter("year") != null ? request.getParameter("year") : "";
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		paramMap.put("SUBSD_CD", admin.getCpnyId());
		paramMap.put("YEAR_MON", year + month);
		paramMap.put("UPDT_USER", admin.getUserNo());
		paramMap.put("USER_NO",admin.getUserNo());
		paramMap.put("ACC_YN", "N");
		if(paramMap.get("PAY_AREA_3") != null)
		{
			paramMap.put("PAY_AREA_CD",paramMap.get("PAY_AREA_3").toString());
		}
		if(paramMap.get("BRANCH_3") != null)
		{
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_3").toString());
		}
		
		try {
			promoterDao.calSalesAchLocal(paramMap);
			retMap.put("RET", paramMap.get("RET").toString());
			retMap.put("MESSAGE", paramMap.get("MESSAGE").toString());
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("RET", 0);
			return retMap;
		}
		return retMap;
	}

	/**************************************************************************************************************/
	/**
	 * 促销员预提计算
	 * @Create date: 2014.08.05
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSalesAchLocalPreList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO",admin.getUserNo());
		String year = request.getParameter("year") != null ? request.getParameter("year") : "";
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		paramMap.put("YEAR_MON", year + month);
		paramMap.put("ACC_YN", "Y");
		if(paramMap.get("PAY_AREA_5") != null)
		{
			paramMap.put("PAY_AREA_CD",paramMap.get("PAY_AREA_5").toString());
		}
		if(paramMap.get("BRANCH_5") != null)
		{
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_5").toString());
		}
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
					promoterDao.getSalesAchLocalList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = promoterDao.getSalesAchLocalList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	// @Create date: 2014.08.05
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getSalesAchLocalPreListCnt(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO",admin.getUserNo());
		String year = request.getParameter("year") != null ? request.getParameter("year") : "";
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		paramMap.put("YEAR_MON", year + month);
		paramMap.put("ACC_YN", "Y");
		if(paramMap.get("PAY_AREA_5") != null)
		{
			paramMap.put("PAY_AREA_CD",paramMap.get("PAY_AREA_5").toString());
		}
		if(paramMap.get("BRANCH_5") != null)
		{
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_5").toString());
		}
		
		return promoterDao.getSalesAchLocalListCnt(paramMap) ;
	}

	// @Create date: 2014.08.05
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map calSalesAchLocalPre(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("RET", 1);
		retMap.put("MESSAGE", "");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		String year = request.getParameter("year") != null ? request.getParameter("year") : "";
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		paramMap.put("SUBSD_CD", admin.getCpnyId());
		paramMap.put("PAY_AREA_CD", admin.getCpnyId());
		paramMap.put("YEAR_MON", year + month);
		paramMap.put("UPDT_USER", admin.getUserNo());
		paramMap.put("USER_NO",admin.getUserNo());
		paramMap.put("ACC_YN", "Y");
		if(paramMap.get("PAY_AREA_5") != null)
		{
			paramMap.put("PAY_AREA_CD",paramMap.get("PAY_AREA_5").toString());
		}
		if(paramMap.get("BRANCH_5") != null)
		{
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_5").toString());
		}
		
		try {
			promoterDao.calSalesAchLocal(paramMap);
			retMap.put("RET", paramMap.get("RET").toString());
			retMap.put("MESSAGE", paramMap.get("MESSAGE").toString());
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("RET", 0);
			return retMap;
		}
		return retMap;
	}

	/**************************************************************************************************************/
	/**
	 * 总公司单台提成设置
	 * @param request
	 * @return List
	 * @Create date: 2014.06.19
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getIncBasicSetupList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO",admin.getUserNo());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("EMPID", admin.getUserNo());
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				promoterDao.getIncBasicSetupList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = promoterDao.getIncBasicSetupList(paramMap) ;
		}
		
		return retrunList ;
	}

	/**
	 * 总公司单台提成设置
	 * @param request
	 * @return int
	 * @throws Exception
	 * @Create date: 2014.06.19
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getIncBasicSetupListCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("EMPID", admin.getUserNo());
		
		return promoterDao.getIncBasicSetupListCnt(paramMap);
	}
	
	/**
	 * 总公司单台提成设置Excel导出
	 * @param request
	 * @return
	 * @Create date: 2014.06.19
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getIncBasicSetupListExcel(HttpServletRequest request) throws Exception{
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("EMPID", admin.getUserNo());
		
		retrunList = promoterDao.getIncBasicSetupList(paramMap) ;
		
		return retrunList ;
	}
	
	// @Create date: 2014.06.19
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object getIncBasicSetupInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		
		return promoterDao.getIncBasicSetupInfo(paramMap);
	}

	// @Create date: 2014.06.19
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int updateIncBasicSetup(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("UPDATED_BY", admin.getUserNo()) ;
			this.promoterDao.updateIncBasicSetup(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**************************************************************************************************************/
	/**
	 * 大区单台提成调整
	 * @param request
	 * @return List
	 * @Create date: 2014.06.20
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getOfficeIncAdjustList(HttpServletRequest request) {

		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("USER_NO",admin.getUserNo());
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				promoterDao.getOfficeIncAdjustList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = promoterDao.getOfficeIncAdjustList(paramMap) ;
		}
		
		return retrunList ;
	}

	/**
	 * 大区单台提成调整
	 * @param request
	 * @return int
	 * @throws Exception
	 * @Create date: 2014.06.20
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getOfficeIncAdjustListCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("USER_NO",admin.getUserNo());
		
		return promoterDao.getOfficeIncAdjustListCnt(paramMap);
	}
	
	/**
	 * 大区单台提成调整Excel导出
	 * @param request
	 * @return
	 * @Create date: 2014.06.20
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getOfficeIncAdjustListExcel(HttpServletRequest request) throws Exception{
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("USER_NO",admin.getUserNo());
		
		retrunList = promoterDao.getOfficeIncAdjustList(paramMap) ;
		
		return retrunList ;
	}
	
	// @Create date: 2014.06.20
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object getOfficeIncAdjustInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("USER_NO",admin.getUserNo());
		
		return promoterDao.getOfficeIncAdjustInfo(paramMap);
	}

	// @Create date: 2014.06.20
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int updateOfficeIncAdjust(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("UPDATED_BY", admin.getUserNo()) ;
			this.promoterDao.updateOfficeIncAdjust(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	// @Create date: 2014.06.20
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int addOfficeIncAdjust(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("UPDATED_BY", admin.getUserNo()) ;
			this.promoterDao.addOfficeIncAdjust(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**************************************************************************************************************/
	/**
	 * 日别单台提成设置
	 * @param request
	 * @return List
	 * @Create date: 2014.06.23
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getIncBasicSetupByDayList(HttpServletRequest request) {

		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("USER_NO",admin.getUserNo());
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				promoterDao.getIncBasicSetupByDayList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = promoterDao.getIncBasicSetupByDayList(paramMap) ;
		}
		
		return retrunList ;
	}

	/**
	 * 日别单台提成设置
	 * @param request
	 * @return int
	 * @throws Exception
	 * @Create date: 2014.06.23
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getIncBasicSetupByDayListCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("USER_NO",admin.getUserNo());
		
		return promoterDao.getIncBasicSetupByDayListCnt(paramMap);
	}
	
	/**
	 * 日别单台提成设置Excel导出
	 * @param request
	 * @return
	 * @Create date: 2014.06.23
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getIncBasicSetupByDayListExcel(HttpServletRequest request) throws Exception{
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("USER_NO",admin.getUserNo());
		
		retrunList = promoterDao.getIncBasicSetupByDayList(paramMap) ;
		
		return retrunList ;
	}
	
	// @Create date: 2014.06.23
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object getIncBasicSetupByDayInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("USER_NO",admin.getUserNo());
		return promoterDao.getIncBasicSetupByDayInfo(paramMap);
	}

	// @Create date: 2014.06.23
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int updateIncBasicSetupByDay(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("UPDATED_BY", admin.getUserNo()) ;
			this.promoterDao.updateIncBasicSetupByDay(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	// @Create date: 2014.06.23
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int addIncBasicSetupByDay(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("UPDATED_BY", admin.getUserNo()) ;
			this.promoterDao.addIncBasicSetupByDay(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	// @Create date: 2014.08.07
	@Override
	@SuppressWarnings({ "rawtypes" })
	public int incBasicCheckProdId(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;

		return promoterDao.incBasicCheckProdId(paramMap) ;
	}

	// @Create date: 2014.08.07
	@Override
	@SuppressWarnings({ "rawtypes" })
	public int incBasicCheckPrcDayCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;

		return promoterDao.incBasicCheckPrcDayCnt(paramMap) ;
	}

	/**************************************************************************************************************/
	/**
	 * 最小目标管理
	 * @param request
	 * @return List
	 * @Create date: 2014.06.24
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getMinGoalSetupList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("USER_NO",admin.getUserNo());
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				promoterDao.getMinGoalSetupList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = promoterDao.getMinGoalSetupList(paramMap) ;
		}
		
		return retrunList ;
	}

	/**
	 * @Create date: 2014.06.24
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getMinGoalSetupListCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("USER_NO",admin.getUserNo());
		
		return promoterDao.getMinGoalSetupListCnt(paramMap);
	}
	
	/**
	 * @Create date: 2014.06.24
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getMinGoalSetupListExcel(HttpServletRequest request) throws Exception{
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("USER_NO",admin.getUserNo());
		retrunList = promoterDao.getMinGoalSetupList(paramMap) ;
		
		return retrunList ;
	}
	
	// @Create date: 2014.06.24
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object getMinGoalSetupInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("USER_NO",admin.getUserNo());
		return promoterDao.getMinGoalSetupInfo(paramMap);
	}

	// @Create date: 2014.06.24
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int updateMinGoalSetup(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("UPDATED_BY", admin.getUserNo()) ;
			this.promoterDao.updateMinGoalSetup(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	// @Create date: 2014.06.24
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int addMinGoalSetup(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("UPDATED_BY", admin.getUserNo()) ;
			this.promoterDao.addMinGoalSetup(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**************************************************************************************************************/
	/**
	 * 达成率对应指标设置
	 * @param request
	 * @return List
	 * @Create date: 2014.06.24
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSalsRateList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("USER_NO",admin.getUserNo());
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				promoterDao.getSalsRateList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = promoterDao.getSalsRateList(paramMap) ;
		}
		
		return retrunList ;
	}

	/**
	 * @Create date: 2014.06.24
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getSalsRateListCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("USER_NO",admin.getUserNo());
		
		return promoterDao.getSalsRateListCnt(paramMap);
	}
	
	/**
	 * @Create date: 2014.06.24
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getSalsRateListExcel(HttpServletRequest request) throws Exception{
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("USER_NO",admin.getUserNo());
		
		retrunList = promoterDao.getSalsRateList(paramMap) ;
		
		return retrunList ;
	}
	
	// @Create date: 2014.06.24
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object getSalsRateInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("USER_NO",admin.getUserNo());
		return promoterDao.getSalsRateInfo(paramMap);
	}

	// @Create date: 2014.06.24
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int updateSalsRate(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("UPDATED_BY", admin.getUserNo()) ;
			this.promoterDao.updateSalsRate(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	// @Create date: 2014.06.24
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int addSalsRate(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("UPDATED_BY", admin.getUserNo()) ;
			if(paramMap.get("PROD_TP") == null | paramMap.get("PROD_TP").equals(""))
			{
				paramMap.put("PROD_TP","ALL");
			}
			this.promoterDao.addSalsRate(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**************************************************************************************************************/
	/**
	 * 促销员担当产品
	 * @param request
	 * @return List
	 * @Create date: 2014.06.24
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getIncProdTpByPromoterList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		if(paramMap.containsKey("dwz.person.empId"))
			paramMap.put("EMPID", paramMap.get("dwz.person.empId").toString());
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				promoterDao.getIncProdTpByPromoterList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = promoterDao.getIncProdTpByPromoterList(paramMap) ;
		}
		
		return retrunList ;
	}

	/**
	 * @Create date: 2014.06.24
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getIncProdTpByPromoterListCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		if(paramMap.containsKey("dwz.person.empId"))
			paramMap.put("EMPID", paramMap.get("dwz.person.empId").toString());
		
		return promoterDao.getIncProdTpByPromoterListCnt(paramMap);
	}
	
	/**
	 * @Create date: 2014.06.24
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getIncProdTpByPromoterListExcel(HttpServletRequest request) throws Exception{
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		if(paramMap.containsKey("dwz.person.empId"))
			paramMap.put("EMPID", paramMap.get("dwz.person.empId").toString());
		retrunList = promoterDao.getIncProdTpByPromoterList(paramMap) ;
		
		return retrunList ;
	}
	
	// @Create date: 2014.06.24
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object getIncProdTpByPromoterInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		
		return promoterDao.getIncProdTpByPromoterInfo(paramMap);
	}

	// @Create date: 2014.06.24
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int updateIncProdTpByPromoter(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("UPDATED_BY", admin.getUserNo()) ;
			this.promoterDao.updateIncProdTpByPromoter(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**************************************************************************************************************/
	/**
	 * 促销员实绩上报
	 * @param request
	 * @return List
	 * @Create date: 2014.06.26
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getPromoterSelloutUploadList(HttpServletRequest request) {
		
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO",admin.getUserNo());
		String year = request.getParameter("year") != null ? request.getParameter("year") : "";
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		paramMap.put("YEAR_MON", year + month);
		if(paramMap.get("PAY_AREA_1") != null)
		{
			paramMap.put("PAY_AREA_CD",paramMap.get("PAY_AREA_1").toString());
		}
		if(paramMap.get("BRANCH_1") != null)
		{
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_1").toString());
		}
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
					promoterDao.getPromoterSelloutUploadList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = promoterDao.getPromoterSelloutUploadList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	// @Create date: 2014.06.26
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getPromoterSelloutUploadListCnt(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO",admin.getUserNo());
		String year = request.getParameter("year") != null ? request.getParameter("year") : "";
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		paramMap.put("YEAR_MON", year + month);
		if(paramMap.get("PAY_AREA_1") != null)
		{
			paramMap.put("PAY_AREA_CD",paramMap.get("PAY_AREA_1").toString());
		}
		if(paramMap.get("BRANCH_1") != null)
		{
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_1").toString());
		}
		
		return promoterDao.getPromoterSelloutUploadListCnt(paramMap) ;
	}

	/**************************************************************************************************************/
	/**
	 * 促销员实绩上报确认
	 * @Create date: 2014.08.19
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getPromoterSelloutConfirmList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO",admin.getUserNo());
		String year = request.getParameter("year") != null ? request.getParameter("year") : "";
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		paramMap.put("YEAR_MON", year + month);
		if(paramMap.get("PAY_AREA_1") != null)
		{
			paramMap.put("PAY_AREA_CD",paramMap.get("PAY_AREA_1").toString());
		}else if(paramMap.get("PAY_AREA_cx1600") != null)
		{
			paramMap.put("PAY_AREA_CD",paramMap.get("PAY_AREA_cx1600").toString());
		}
		if(paramMap.get("BRANCH_1") != null)
		{
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_1").toString());
		}else if(paramMap.get("BRANCH_cx1600") != null)
		{
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_cx1600").toString());
		}
				
		if (UiUtil.getPageNum(request) > 0){
			retrunList = promoterDao.getPromoterSelloutConfirmList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = promoterDao.getPromoterSelloutConfirmList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	// @Create date: 2014.08.19
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getPromoterSelloutConfirmListCnt(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO",admin.getUserNo());
		String year = request.getParameter("year") != null ? request.getParameter("year") : "";
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		paramMap.put("YEAR_MON", year + month);
		
		return promoterDao.getPromoterSelloutConfirmListCnt(paramMap) ;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List viewPromoterSelloutConfirmList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO",admin.getUserNo());
		String year = request.getParameter("year") != null ? request.getParameter("year") : "";
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		String confirmFlag = request.getParameter("seach_CONFIRM_FLAG") != null ? request.getParameter("seach_CONFIRM_FLAG") : "";
		paramMap.put("YEAR_MON", year + month);
		paramMap.put("CONFIRM_FLAG", confirmFlag);
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = promoterDao.viewPromoterSelloutConfirmList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = promoterDao.viewPromoterSelloutConfirmList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	// @Create date: 2014.08.19
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int viewPromoterSelloutConfirmListCnt(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO",admin.getUserNo());
		String year = request.getParameter("year") != null ? request.getParameter("year") : "";
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		paramMap.put("YEAR_MON", year + month);
		
		return promoterDao.viewPromoterSelloutConfirmListCnt(paramMap) ;
	}

	// @Create date: 2014.08.19
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map selloutConfirm(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		Map<String, Object> retMap = new HashMap<String, Object>();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("SUBSD_CD", admin.getCpnyId());
		paramMap.put("USER_NO", admin.getUserNo());

		String   ls = "";
		String[] isChecked = request.getParameterValues("cx1500Check");
		if(isChecked != null)
		{
			for(int i = 0; i < isChecked.length; i++){
				ls = ls.equals("")?paramMap.get("BRANCH_" + isChecked[i]).toString():(ls + "," + paramMap.get("BRANCH_" + isChecked[i]).toString());
			}
		}
		
		if(ls.equals(""))
		{
			retMap.put("RET", 0);
			retMap.put("MESSAGE", "请选择需要确认的支社别信息！");
			return retMap;
		}
		paramMap.put("BRANCHS", ls);
		paramMap.put("YEAR_MON", paramMap.get("SALE_MONTH_0").toString());
		
		try {
			promoterDao.selloutConfirm(paramMap);
			retMap.put("RET", paramMap.get("RET").toString());
			retMap.put("MESSAGE", paramMap.get("MESSAGE").toString());
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("RET", 0);
			return retMap;
		}
		return retMap;
	}

	/**************************************************************************************************************/
	/**
	 * 促销员实贩卖实绩读取
	 * @param request
	 * @return List
	 * @Create date: 2014.06.26
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSalesAchievementList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO",admin.getUserNo());
		String year = request.getParameter("year") != null ? request.getParameter("year") : "";
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		paramMap.put("YEAR_MON", year + month);
		if(paramMap.get("PAY_AREA_2") != null)
		{
			paramMap.put("PAY_AREA_CD",paramMap.get("PAY_AREA_2").toString());
		}else
		{
			if(paramMap.get("PAY_AREA_4") != null)
			{
				paramMap.put("PAY_AREA_CD",paramMap.get("PAY_AREA_4").toString());
			}
		}
		
		if(paramMap.get("BRANCH_2") != null){
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_2").toString());
		}else if(paramMap.get("BRANCH_4") != null){
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_4").toString());
		}else if(paramMap.containsKey("hBRANCH4")){
			paramMap.put("BRANCH_CD", paramMap.get("hBRANCH4"));
		}

		String ACC_YN = request.getAttribute("ACC_YN").toString();
		paramMap.put("ACC_YN", ACC_YN);
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
					promoterDao.getSalesAchievementList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = promoterDao.getSalesAchievementList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	// @Create date: 2014.06.26
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getSalesAchievementListCnt(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO",admin.getUserNo());
		String year = request.getParameter("year") != null ? request.getParameter("year") : "";
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		paramMap.put("YEAR_MON", year + month);
		if(paramMap.get("PAY_AREA_2") != null)
		{
			paramMap.put("PAY_AREA_CD",paramMap.get("PAY_AREA_2").toString());
		}else
		{
			if(paramMap.get("PAY_AREA_4") != null)
			{
				paramMap.put("PAY_AREA_CD",paramMap.get("PAY_AREA_4").toString());
			}
		}
		if(paramMap.get("BRANCH_2") != null){
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_2").toString());
		}else if(paramMap.get("BRANCH_4") != null){
				paramMap.put("BRANCH_CD",paramMap.get("BRANCH_4").toString());
		}else if(paramMap.containsKey("hBRANCH4")){
			paramMap.put("BRANCH_CD", paramMap.get("hBRANCH4"));
		}

		String ACC_YN = request.getAttribute("ACC_YN")!=null?request.getAttribute("ACC_YN").toString():"";
		paramMap.put("ACC_YN", ACC_YN);
		
		return promoterDao.getSalesAchievementListCnt(paramMap) ;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getSalesAchievementListExcel(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO",admin.getUserNo());
		String year = request.getParameter("year") != null ? request.getParameter("year") : "";
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		paramMap.put("YEAR_MON", year + month);
		if(paramMap.get("PAY_AREA_2") != null)
		{
			paramMap.put("PAY_AREA_CD",paramMap.get("PAY_AREA_2").toString());
		}else
		{
			if(paramMap.get("PAY_AREA_4") != null)
			{
				paramMap.put("PAY_AREA_CD",paramMap.get("PAY_AREA_4").toString());
			}
		}
		if(paramMap.get("BRANCH_2") != null)
		{
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_2").toString());
		}else
		{
			if(paramMap.get("BRANCH_4") != null)
			{
				paramMap.put("BRANCH_CD",paramMap.get("BRANCH_4").toString());
			}
		}

		if(request.getAttribute("ACC_YN")!=null)
		{
			paramMap.put("ACC_YN",request.getAttribute("ACC_YN").toString());
		}
		
		return promoterDao.getSalesAchievementList(paramMap) ;
	}

	// @Create date: 2014.06.26
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map reLoadSalesAchievement(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("RET", 1);
		retMap.put("MESSAGE", "");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		String year = request.getParameter("year") != null ? request.getParameter("year") : "";
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		paramMap.put("YEAR_MON", year + month);
		paramMap.put("SUBSD_CD", admin.getCpnyId());
		paramMap.put("UPDT_USER", admin.getUserNo());
		retMap.put("ACC_YN", paramMap.get("ACC_YN").toString());
		if(paramMap.get("PAY_AREA_2") != null)
		{
			paramMap.put("PAY_AREA_CD",paramMap.get("PAY_AREA_2").toString());
		}else
		{
			if(paramMap.get("PAY_AREA_4") != null)
			{
				paramMap.put("PAY_AREA_CD",paramMap.get("PAY_AREA_4").toString());
			}
		}
		if(paramMap.get("BRANCH_2") != null)
		{
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_2").toString());
		}else
		{
			if(paramMap.get("BRANCH_4") != null)
			{
				paramMap.put("BRANCH_CD",paramMap.get("BRANCH_4").toString());
			}
		}

		try {
			promoterDao.reLoadSalesAchievement(paramMap);
			retMap.put("RET", paramMap.get("RET").toString());
			retMap.put("MESSAGE", paramMap.get("MESSAGE").toString());
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("RET", 0);
			return retMap;
		}
		return retMap;
	}

	// @Create date: 2014.06.26
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map calSalesAchievement(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("RET", 1);
		retMap.put("MESSAGE", "");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("LANGUAGE", Messages.getLanguage(request));
		String year = request.getParameter("year") != null ? request.getParameter("year") : "";
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		paramMap.put("YEAR_MON", year + month);
		paramMap.put("SUBSD_CD", admin.getCpnyId());
		paramMap.put("UPDT_USER", admin.getUserNo());
		paramMap.put("PROD_TP", "ALL");
		retMap.put("ACC_YN", paramMap.get("ACC_YN").toString());
		if(paramMap.get("PAY_AREA_2") != null)
		{
			paramMap.put("PAY_AREA_CD",paramMap.get("PAY_AREA_2").toString());
		}else
		{
			if(paramMap.get("PAY_AREA_4") != null)
			{
				paramMap.put("PAY_AREA_CD",paramMap.get("PAY_AREA_4").toString());
			}
		}
		if(paramMap.get("BRANCH_2") != null)
		{
			paramMap.put("BRANCH_CD",paramMap.get("BRANCH_2").toString());
		}else
		{
			if(paramMap.get("BRANCH_4") != null)
			{
				paramMap.put("BRANCH_CD",paramMap.get("BRANCH_4").toString());
			}
		}

		try {
			promoterDao.calSalesAchievement(paramMap);
			retMap.put("RET", paramMap.get("RET").toString());
			retMap.put("MESSAGE", paramMap.get("MESSAGE").toString());
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("RET", 0);
			return retMap;
		}
		return retMap;
	}

	/**************************************************************************************************************/

	@SuppressWarnings("deprecation")
	public boolean processUploadFile(HttpServletRequest request,
			HttpServletResponse response) {
		boolean b = true;

		// 设置文件在服务器上的路径
		this.path = request.getRealPath("");
		int i = path.indexOf("\\");
		String server = "win";
		if (i < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\files\\" + pathCpnyID + "\\";
		} else {
			path = path + "/resources/temp/files/" + pathCpnyID;
		}
		File file = new File(path);
		if (!file.exists()) {// 目录或文件是否存在
			file.mkdirs();
		}
		try {
			uploadExcel up = new uploadExcel(request, response);
			if (up.getdata()) {
				up.initFileComents();
				up.disposeData(filename);
				up.deletefile(pathCpnyID, filename + ".xls");
				up.setFilePath("", pathCpnyID);
				up.WriteMdata();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * 将excel的数据导入到数据库中（总公司单台提成设置导入 临时表）
	 * @author  CH.W.G
	 * @date    2014.7.10
	 * @version V1.0
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ModelMap importIncBasicSetup(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getUserNo();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			excelUtilSer.setFileName(filename);
			excelUtilSer.setPath(path);
			ModelMap preparedMap = excelUtilSer.prepareForImportExcel(request, adminID, map);
			int returnInt  = (Integer) preparedMap.get("token");
			//如果excel数据读取成功，将数据导入 临时表
			if(returnInt == 1){
				LinkedHashMap paramMap = new LinkedHashMap();
				paramMap.put("UPDT_USER", admin.getUserNo());
				// 删除临时表中当前用户旧数据
				try {
					promoterDao.deleteIncBasicSetupTemp(paramMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 0;
				}
				Map dataMap = new HashMap();
				dataMap = (Map) preparedMap.get("dataMap");
				// 为当前用户向临时表中插入数据
				try {
					promoterDao.insertIncBasicSetupTemp(dataMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 0;
				}
			}			
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.writebyrule", request));// 调整事由有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				modelMap.put("navTabId", "cx0400");
				modelMap.put(
						"forwardUrl",
						"/promoter/viewDataImportResultList");
			} else if (returnInt == 5) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfailcalladmin",
						request));// 更新此人上一条数据出错，请联系管理员
			} else if (returnInt == 6) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"alert.message.pa.bonus.thisDataIsExist_add_fail",
						request));// 此数据已经存在，请核对数据后再进行导入!
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	/**
	 * @Create date: 2014.07.10
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getDataImportResultList(HttpServletRequest request, Map paramMap){
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getUserNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO",admin.getUserNo());
		paramMap.put("language", Messages.getLanguage(request));
		if (UiUtil.getPageNum(request) > 0){
			retrunList =  promoterDao.getDataImportResultList(paramMap , 
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
					);
		}else{
			retrunList =  promoterDao.getDataImportResultList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * @Create date: 2014.07.10
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getDataImportResultListCnt(HttpServletRequest request, Map paramMap){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getUserNo());
		return promoterDao.getDataImportResultListCnt(paramMap);
	}
	
	/**
	 * @Create date: 2014.07.10
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getDataImportErrCnt(HttpServletRequest request, Map paramMap){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getUserNo());
		return promoterDao.getDataImportErrCnt(paramMap);
	}

	/**
	 * @Create date: 2014.07.10
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String importIncBasicSetupFromExcel(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String result = "";
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDT_USER", admin.getUserNo()) ;
		paramMap.put("SUBSD_CD", admin.getCpnyId()) ;
		result = promoterDao.importIncBasicSetupFromExcel(paramMap);
		return result;
	}

	/**
	 * @Create date: 2014.07.16
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getImportIncBasicSetupFromExcel(HttpServletRequest request) throws Exception{
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getUserNo()) ;
		paramMap.put("SUBSD_CD", admin.getCpnyId()) ;
		paramMap.put("language", Messages.getLanguage(request));
		
		retrunList = promoterDao.getDataImportResultList(paramMap) ;
		
		return retrunList ;
	}
	/**************************************************************************************************************/
	/**
	 * 大区单台提成调整导入 临时表
	 * @author  CH.W.G
	 * @date    2014.7.16
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ModelMap importOfficeIncAdjust(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getUserNo();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			excelUtilSer.setFileName(filename);
			excelUtilSer.setPath(path);
			ModelMap preparedMap = excelUtilSer.prepareForImportExcel(request, adminID, map);
			int returnInt  = (Integer) preparedMap.get("token");
			//如果excel数据读取成功，将数据导入 临时表
			if(returnInt == 1){
				LinkedHashMap paramMap = new LinkedHashMap();
				paramMap.put("UPDT_USER", admin.getUserNo());
				// 删除临时表中当前用户旧数据
				try {
					promoterDao.deleteOfficeIncAdjustTemp(paramMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 0;
				}
				Map dataMap = new HashMap();
				dataMap = (Map) preparedMap.get("dataMap");
				// 为当前用户向临时表中插入数据
				try {
					promoterDao.insertOfficeIncAdjustTemp(dataMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 0;
				}
			}			
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.writebyrule", request));// 调整事由有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				modelMap.put("navTabId", "cx0500");
				modelMap.put(
						"forwardUrl",
						"/promoter/viewImportOfficeIncAdjustResultList");
			} else if (returnInt == 5) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfailcalladmin",
						request));// 更新此人上一条数据出错，请联系管理员
			} else if (returnInt == 6) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"alert.message.pa.bonus.thisDataIsExist_add_fail",
						request));// 此数据已经存在，请核对数据后再进行导入!
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	/**
	 * @Create date: 2014.07.16
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getImportOfficeIncAdjustResultList(HttpServletRequest request, Map paramMap){
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getUserNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO",admin.getUserNo());
		paramMap.put("language", Messages.getLanguage(request));
		if (UiUtil.getPageNum(request) > 0){
			retrunList =  promoterDao.getImportOfficeIncAdjustResultList(paramMap , 
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
					);
		}else{
			retrunList =  promoterDao.getImportOfficeIncAdjustResultList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * @Create date: 2014.07.16
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getImportOfficeIncAdjustResultCnt(HttpServletRequest request, Map paramMap){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getUserNo());
		return promoterDao.getImportOfficeIncAdjustResultCnt(paramMap);
	}
	
	/**
	 * @Create date: 2014.07.16
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getImportOfficeIncAdjustErrCnt(HttpServletRequest request, Map paramMap){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getUserNo());
		return promoterDao.getImportOfficeIncAdjustErrCnt(paramMap);
	}

	/**
	 * @Create date: 2014.07.16
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String importOfficeIncAdjustFromExcel(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String result = "";
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDT_USER", admin.getUserNo()) ;
		paramMap.put("SUBSD_CD", admin.getCpnyId()) ;
		result = promoterDao.importOfficeIncAdjustFromExcel(paramMap);
		return result;
	}

	/**
	 * @Create date: 2014.07.16
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getImportOfficeIncAdjustFromExcel(HttpServletRequest request) throws Exception{
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getUserNo()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		paramMap.put("language", Messages.getLanguage(request));
		
		retrunList = promoterDao.getImportOfficeIncAdjustResultList(paramMap) ;
		
		return retrunList ;
	}
	/**************************************************************************************************************/
	/**
	 * 促销员实绩上报导入临时表
	 * @author  CH.W.G
	 * @date    2014.7.16
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ModelMap importSellout(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getUserNo();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			excelUtilSer.setFileName(filename);
			excelUtilSer.setPath(path);
			ModelMap preparedMap = excelUtilSer.prepareForImportExcel(request, adminID, map);
			int returnInt  = (Integer) preparedMap.get("token");
			//如果excel数据读取成功，将数据导入 临时表
			if(returnInt == 1){
				LinkedHashMap paramMap = new LinkedHashMap();
				paramMap.put("UPDT_USER", admin.getUserNo());
				// 删除临时表中当前用户旧数据
				try {
					promoterDao.deleteSelloutTemp(paramMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 0;
				}
				Map dataMap = new HashMap();
				dataMap = (Map) preparedMap.get("dataMap");
				// 为当前用户向临时表中插入数据
				try {
					promoterDao.insertSelloutTemp(dataMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 0;
				}
			}			
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.writebyrule", request));// 调整事由有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				modelMap.put("navTabId", "cx1000");
				modelMap.put(
						"forwardUrl",
						"/promoter/viewImportSelloutResultList");
			} else if (returnInt == 5) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfailcalladmin",
						request));// 更新此人上一条数据出错，请联系管理员
			} else if (returnInt == 6) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"alert.message.pa.bonus.thisDataIsExist_add_fail",
						request));// 此数据已经存在，请核对数据后再进行导入!
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	/**
	 * @Create date: 2014.07.16
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getImportSelloutResultList(HttpServletRequest request, Map paramMap){
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getUserNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		if (UiUtil.getPageNum(request) > 0){
			retrunList =  promoterDao.getImportSelloutResultList(paramMap , 
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
					);
		}else{
			retrunList =  promoterDao.getImportSelloutResultList(paramMap, 1, 10);
		}
		return retrunList;
	}
	
	/**
	 * @Create date: 2014.07.16
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getImportSelloutResultCnt(HttpServletRequest request, Map paramMap){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getUserNo());
		return promoterDao.getImportSelloutResultCnt(paramMap);
	}
	
	/**
	 * @Create date: 2014.07.16
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getImportSelloutErrCnt(HttpServletRequest request, Map paramMap){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getUserNo());
		return promoterDao.getImportSelloutErrCnt(paramMap);
	}

	/**
	 * @Create date: 2014.07.16
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String importSelloutFromExcel(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String result = "";
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDT_USER", admin.getUserNo()) ;
		paramMap.put("SUBSD_CD", admin.getCpnyId()) ;
		result = promoterDao.importSelloutFromExcel(paramMap);
		return result;
	}

	/**
	 * @Create date: 2014.07.16
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getImportSelloutFromExcel(HttpServletRequest request) throws Exception{
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("UPDT_USER", admin.getUserNo()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		paramMap.put("language", Messages.getLanguage(request));
		
		retrunList = promoterDao.getImportSelloutResultList(paramMap) ;
		
		return retrunList ;
	}
	/**************************************************************************************************************/

	@SuppressWarnings("rawtypes")
	public List getPayAreaCodeList(Map paramMap){
		List retrunList = new ArrayList() ;		
		retrunList = promoterDao.getPayAreaCodeList(paramMap);		
		return retrunList ;
	}

	/**
	 * @Create date: 2014.08.26
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public String getLastMonth(HttpServletRequest request, Map paramMap){
		return promoterDao.getLastMonth(paramMap);
	}
	
	/**************************************************************************************************************/
	/**
	 * 促销员实绩审批申请列表查询
	 * @Create date: 2014.08.29
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSelloutRequestList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO",admin.getUserNo());
		String year = request.getParameter("year") != null ? request.getParameter("year") : promoterDao.getLastMonth(paramMap);
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		paramMap.put("YYYYMM", year + month);
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
					promoterDao.getSelloutRequestList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = promoterDao.getSelloutRequestList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	// @Create date: 2014.08.29
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getSelloutRequestListCnt(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO",admin.getUserNo());
		String year = request.getParameter("year") != null ? request.getParameter("year") : promoterDao.getLastMonth(paramMap);
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		paramMap.put("YYYYMM", year + month);
		
		return promoterDao.getSelloutRequestListCnt(paramMap) ;
	}

	// @Create date: 2014.09.01 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getSelloutReqDtlList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = null ;
		Map paramMap    = null ;

		if(request.getParameter("personId") == null || "".equals(request.getParameter("personId"))){
			admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("CPNY_ID", admin.getCpnyId());
			paramMap.put("USER_NO",admin.getUserNo());
		}else{
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		}
		
		paramMap.put("LANGUAGE", Messages.getLanguage(request));
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
					promoterDao.getSelloutReqDtlList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = promoterDao.getSelloutReqDtlList(paramMap) ;
		}
		return retrunList ;
	}

	// @Create date: 2014.09.01 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public List getSelloutReqDtl(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		return promoterDao.getSelloutReqDtlList(paramMap) ;
	}
	
	// @Create date: 2014.09.01
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map addSelloutReq(HttpServletRequest request) {
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("RET", 1);
		retMap.put("MESSAGE", "");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("SUBSD_CD", admin.getCpnyId());
		paramMap.put("UPDT_USER", admin.getUserNo());
		String year = request.getParameter("year") != null ? request.getParameter("year") : promoterDao.getLastMonth(paramMap);
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		paramMap.put("YYYYMM", year + month);
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("USER_NO",admin.getUserNo());
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		if(paramMap.get("REQ_ID") == null || paramMap.get("REQ_ID").equals(""))
		{
			paramMap.put("REQ_ID", promoterDao.getNewReqId(paramMap));
		}
		String[] affirmId = request.getParameterValues("AFFIRMOR_ID");
		if(affirmId==null)
		{
			retMap.put("RET", -1);
			retMap.put("MESSAGE", "没有设置审批人信息！");
			return retMap;
		}else{
			for(int j=0; j<affirmId.length; j++)
			{
				if(affirmId[j]==null || affirmId[j].trim().equals("")){
					retMap.put("RET", -1);
					retMap.put("MESSAGE", "审批人信息设置不正确！");
					return retMap;
				}
			}
		}
		try {
			paramMap.put("AFFIRMOR_ID", affirmId[0]);
			promoterDao.addSelloutReq(paramMap);
			retMap.put("RET", paramMap.get("RET").toString());
			retMap.put("MESSAGE", paramMap.get("MESSAGE").toString());
			if(NumberUtils.parseNumber(paramMap.get("RET").toString(),Integer.class) >= 0)
			{
				promoterDao.updateSellInvoiceQty(paramMap);
//				String[] SELL_SEQ_CX1601 = request.getParameterValues("SELL_SEQ_CX1601");
//				for(int i = 0; i < SELL_SEQ_CX1601.length; i++){
//					paramMap.put("SELL_SEQ", SELL_SEQ_CX1601[i]);
//					paramMap.put("PAY_RATE", paramMap.get("PAY_RATE_CX1601" + i));
//					promoterDao.updateSellPayRate(paramMap);
//					promoterDao.updateSellPayRateRep(paramMap);
//				}
				for(int i=0; i<affirmId.length; i++)
				{
					paramMap.put("AFFIRMOR_ID", affirmId[i]);
					paramMap.put("AFFIRM_LEVEL", i+1);
					promoterDao.insertAffirmor(paramMap);
					if(i==0){
						if(paramMap.get("RET").toString().equals("1"))
						{
							if("1".equals(paramMap.get("viewAddSelloutReqFLAG").toString())){//提交状态发送LGEP
								this.sendToLGEP(new String[]{paramMap.get("REQ_ID").toString()});
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("RET", -1);
			return retMap;
		}

		return retMap;
	}

	// @Create date: 2014.09.05
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public LinkedHashMap getSelloutReqByReqId(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(admin != null){
			paramMap.put("LANGUAGE", admin.getLanguage());
		}
		return promoterDao.getSelloutReqByReqId(paramMap);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getAffirmorListByReqId(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("REQ_ID", request.getParameter("REQ_ID"));
		if(request.getParameter("LANGUAGE") == null || "".equals(request.getParameter("LANGUAGE"))){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("LANGUAGE", admin.getLanguage());
		}else{
			paramMap.put("LANGUAGE", request.getParameter("LANGUAGE"));
		}
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		retrunList = promoterDao.getAffirmorList(paramMap);
		return retrunList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getCheckListByReqId(HttpServletRequest request){
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("REQ_ID", request.getParameter("REQ_ID"));
		if(request.getParameter("LANGUAGE") == null || "".equals(request.getParameter("LANGUAGE"))){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("LANGUAGE", admin.getLanguage());
		}else{
			paramMap.put("LANGUAGE", request.getParameter("LANGUAGE"));
		}
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		retrunList = promoterDao.getCheckList(paramMap);
		return retrunList;
	}
	
	/**
	 * 提交后发送LGEP
	 * @param eventId
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void sendToLGEP(String[] reqId){
		for(int i=0;i<reqId.length;i++){
			LinkedHashMap paramMap = new LinkedHashMap();
			paramMap.put("REQ_ID", reqId[i]);
			LinkedHashMap lgepMap = promoterDao.getSelloutReqByReqId(paramMap);
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			lgepMap.put("APPLY_NO", reqId[i]);
			lgepMap.put("APPLY_TYPE_NAME", "促销员实绩上报审批申请");
			lgepMap.put("APPLY_TITLE", lgepMap.get("REQ_TITLE") + "审批申请");
			lgepMap.put("APPLY_EMPID", lgepMap.get("CREATED_BY"));
			lgepMap.put("ARI_URL", "http://{serverIp}/LGEP/affirm/viewSelloutAffirm?LGEP=LGEP&LANGUAGE=zh&CPNY_ID=LGECH&personId=123&REQ_ID=" + lgepMap.get("REQ_ID") + "&affirmOrCheck=1");
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewSelloutAffirm?LGEP=LGEP&LANGUAGE=zh&CPNY_ID=LGECH&personId=" + lgepMap.get("CURRENT_AFFIRM_ID") + "&REQ_ID=" + lgepMap.get("REQ_ID") + "&affirmOrCheck=1");
			lgepMap.put("PRE_AFFIRM_EMPID", lgepMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("AFFIRM_LEVEL", "1");
			this.affirmInfoToLGEPSer.crateAffirm(lgepMap);
		}
	}

	/**
	 * 审批后发送LGEP
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void sendToLGEP(LinkedHashMap paramMap){
			LinkedHashMap lgepMap = promoterDao.getSelloutReqByReqId(paramMap);
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			lgepMap.put("APPLY_NO", paramMap.get("REQ_ID"));
			if("0".equals(paramMap.get("viewAffirmSellOutFLAG").toString())){
				lgepMap.put("AFFIRM_LEVEL", paramMap.get("AFFIRM_LEVEL"));
				lgepMap.put("AFFIRM_FLAG", 1);
			}else{//决裁完成
				lgepMap.put("FINISH", "FINISH");
				lgepMap.put("AFFIRM_FLAG", paramMap.get("viewAffirmSellOutFLAG"));
				lgepMap.put("AFFIRM_LEVEL", Integer.parseInt(paramMap.get("dept_level").toString()) - 1);
			}
			lgepMap.put("AFFIRM_EMPID", paramMap.get("PERSON_ID"));
			lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/viewSelloutAffirm?LGEP=LGEP&LANGUAGE=zh&CPNY_ID=LGECH&personId=123&REQ_ID=" + lgepMap.get("REQ_ID") + "&affirmOrCheck=1");
			lgepMap.put("AAF_URL", "http://{serverIp}/LGEP/affirm/viewSelloutAffirm?LGEP=LGEP&LANGUAGE=zh&CPNY_ID=LGECH&personId=123&REQ_ID=" + lgepMap.get("REQ_ID") + "&affirmOrCheck=1");
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewSelloutAffirm?LGEP=LGEP&LANGUAGE=zh&CPNY_ID=LGECH&personId=" + lgepMap.get("CURRENT_AFFIRM_ID") + "&REQ_ID=" + lgepMap.get("REQ_ID") + "&affirmOrCheck=1");
			lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.affirm(lgepMap);
	}
	/**
	 * 审批后发送LGEP
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private void sendToLGEPCheck(LinkedHashMap paramMap){
			LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			lgepMap.put("APPLY_NO", paramMap.get("REQ_ID"));
			lgepMap.put("AFFIRM_FLAG", '1');
			lgepMap.put("AFFIRM_LEVEL", paramMap.get("ESS_CHECK_NO"));
			lgepMap.put("AFFIRM_EMPID", paramMap.get("PERSON_ID"));
			lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/viewSelloutAffirm?LGEP=LGEP&LANGUAGE=zh&CPNY_ID=LGECH&personId=123&REQ_ID=" + paramMap.get("REQ_ID") + "&affirmOrCheck=2");
			this.affirmInfoToLGEPSer.check(lgepMap);
	}

	/**
	 * 审批后发送LGEP
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void sendToLGEPCheckBatch(LinkedHashMap paramMap){
		List checkList = promoterDao.getCheckListToLgep(paramMap);
		if(checkList != null && checkList.size() > 0){
			for(int i=0;i<checkList.size();i++){
				LinkedHashMap lgepMap = (LinkedHashMap)checkList.get(i);
				lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
				lgepMap.put("APPLY_NO", paramMap.get("REQ_ID"));
				lgepMap.put("AFFIRM_FLAG", '1');
				lgepMap.put("AFFIRM_LEVEL", lgepMap.get("ESS_CHECK_NO"));
				lgepMap.put("AFFIRM_EMPID", lgepMap.get("CHECKOR_ID"));
				lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/viewSelloutAffirm?LGEP=LGEP&LANGUAGE=zh&CPNY_ID=LGECH&personId=123&REQ_ID=" + paramMap.get("REQ_ID") + "&affirmOrCheck=2");
				this.affirmInfoToLGEPSer.check(lgepMap);
			}
		}
	}
	
	/**************************************************************************************************************/
	/**
	 * 取实绩审批申请Tab分页菜单
	 * @Create date: 2014.09.10
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getMenuThirdList(String menu_code,HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = new LinkedHashMap() ;
		paramMap.put("LANGUAGE", Messages.getLanguage(request));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USERNO", admin.getUserNo());
		
		retrunList = promoterDao.getMenuThirdList(paramMap);
		return retrunList ;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getMenuThirdConfrimList(String menu_code,HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		LinkedHashMap paramMap = new LinkedHashMap() ;
		paramMap.put("LANGUAGE", request.getParameter("LANGUAGE"));
		paramMap.put("CPNY_ID", request.getParameter("CPNY_ID"));
		
		retrunList = promoterDao.getMenuThirdList(paramMap);
		return retrunList ;
	}
	/**************************************************************************************************************/
	/**
	 * 取审批人列表
	 * @Create date: 2014.09.10
	 */
	@Autowired
	private InfoApplySer  infoApplySer ;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getApplyFeeList(HttpServletRequest request) throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		if(paramMap==null || paramMap.get("APPLY_TYPE_NO")==null || "".equals(paramMap.get("APPLY_TYPE_NO").toString())){
			paramMap.put("APPLY_TYPE_NO", "218064");
		}
		return this.infoApplySer.getAffirmorListByString("218064", paramMap.get("PERSON_ID").toString(), "", "", paramMap.get("LANGUAGE").toString());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private LinkedHashMap getLinkedMapByRequest(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("UPDATED_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if(paramMap.get("PERSON_ID")==null || "".equals(paramMap.get("PERSON_ID"))){
			paramMap.put("PERSON_ID", admin.getPersonId());
		}
		return paramMap;
	}

	// @Create date: 2014.09.16
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map affirmSellout(HttpServletRequest request) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		LinkedHashMap paramMap = null;
		AdminBean admin = null;
		if(request.getParameter("personId") == null || "".equals(request.getParameter("personId"))){
			admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap = ObjectBindUtil.getRequestParamData(request) ;
		}else{
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		}
		String[] affirmId = request.getParameterValues("AFFIRMOR_ID");
		paramMap.put("UPDATED_BY", admin == null ? request.getParameter("personId") :admin.getAdminID());
		paramMap.put("PERSON_ID", admin == null ? request.getParameter("personId") : admin.getAdminID());
		paramMap.put("CURRENT_AFFIRM_ID",  "");
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		int dept_level = Integer.parseInt(paramMap.get("dept_level").toString());
		paramMap.put("dept_level", dept_level + 1);

		if(affirmId==null)
		{
			retMap.put("RET", -1);
			retMap.put("MESSAGE", "没有设置审批人信息！");
			return retMap;
		}else{
			for(int j=0; j<affirmId.length; j++)
			{
				if(affirmId[j]==null || affirmId[j].trim().equals("")){
					retMap.put("RET", -1);
					retMap.put("MESSAGE", "审批人信息设置不正确！");
					return retMap;
				}
			}
		}
		retMap.put("RET", 1);
		retMap.put("MESSAGE", "");
		try {
			//重新添加决裁者
			promoterDao.deleteAffirmor(paramMap);
			for(int i=dept_level; i<affirmId.length; i++){
				if(!affirmId[i].trim().equals(""))
				{
					paramMap.put("AFFIRMOR_ID", affirmId[i]);
					paramMap.put("AFFIRM_LEVEL", i + 1);
					promoterDao.insertAffirmor(paramMap);
				}
			}
			promoterDao.affirmSellout(paramMap);
			retMap.put("RET", paramMap.get("RET").toString());
			retMap.put("MESSAGE", paramMap.get("MESSAGE").toString());

			if(retMap.get("RET").toString().equals("1")||retMap.get("RET").toString().equals("2"))
			{
				if(!"2".equals(paramMap.get("viewAffirmSellOutFLAG"))){
					paramMap.put("ACTIVITY", "0");
					List<LinkedHashMap> affirmList = promoterDao.getAffirmorList(paramMap);
					if (affirmList != null && affirmList.size() > 0) {
						for (LinkedHashMap parmers : affirmList) {
							if("0".equals(parmers.get("AFFIRM_FLAG").toString())){
								paramMap.put("CURRENT_AFFIRM_ID",  parmers.get("AFFIRMOR_ID"));
								paramMap.put("AFFIRM_LEVEL",  parmers.get("AFFIRM_LEVEL"));
								paramMap.put("viewAffirmSellOutFLAG", "0");
								break;
							}
							paramMap.put("AFFIRM_LEVEL",  affirmList.size());
						}
					}
				}
				this.sendToLGEPCheckBatch(paramMap);
				this.sendToLGEP(paramMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("RET", -1);
			return retMap;
		}
		return retMap;
	}

	// @Create date: 2014.09.18
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public Map submitSelloutReq(HttpServletRequest request) {
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("RET", 1);
		retMap.put("MESSAGE", "");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;

		String   ls = "";
		String[] isChecked = request.getParameterValues("cx1600Check");
		if(isChecked != null)
		{
			for(int i = 0; i < isChecked.length; i++){
				ls = ls.equals("")?isChecked[i].toString():(ls + "," + isChecked[i].toString());
			}
		}
		if(ls.equals(""))
		{
			retMap.put("RET", 0);
			retMap.put("MESSAGE", "请至少选择一条信息！");
			return retMap;
		}
		paramMap.put("REQ_IDS", ls);
		try {
			if(paramMap.get("SUBMITFLAG").toString().equals("2"))
			{
				promoterDao.delSelloutReq(paramMap);
				retMap.put("RET", paramMap.get("RET").toString());
				retMap.put("MESSAGE", paramMap.get("MESSAGE").toString());
			}
			if(paramMap.get("SUBMITFLAG").toString().equals("1"))
			{
				for(int i = 0; i < isChecked.length; i++){
					paramMap.put("REQ_ID", isChecked[i].toString());
					promoterDao.submitSellout(paramMap);
					retMap.put("RET", paramMap.get("RET").toString());
					retMap.put("MESSAGE", paramMap.get("MESSAGE").toString());
					if(paramMap.get("RET").toString().equals("1"))
					{
						sendToLGEP(new String[]{paramMap.get("REQ_ID").toString()});
					}else
					{
						return retMap;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("RET", 0);
			return retMap;
		}

		return retMap;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getSelloutFromCnmas(HttpServletRequest request, Map paramMap) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("RET", 1);
		retMap.put("MESSAGE", "");
		String year = request.getParameter("year") != null ? request.getParameter("year") : promoterDao.getLastMonth(paramMap);
		String month = request.getParameter("month") != null ? request.getParameter("month") : "";
		paramMap.put("YEAR_MON", year + month);

		try {
			promoterDao.getSelloutFromCnmas(paramMap);
			retMap.put("RET", paramMap.get("RET").toString());
			retMap.put("MESSAGE", paramMap.get("MESSAGE").toString());
		}catch (Exception e) {
			e.printStackTrace();
			retMap.put("RET", 0);
			return retMap;
		}
		return retMap;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getReqOver10List(Map paramMap) {
		return promoterDao.getReqOver10List(paramMap);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List getReqOver9kList(Map paramMap) {
		return promoterDao.getReqOver9kList(paramMap);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List getReqRatioList(Map paramMap) {
		return promoterDao.getReqRatioList(paramMap);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List getReqExshopList(Map paramMap) {
		return promoterDao.getReqExshopList(paramMap);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List getReqReportList(Map paramMap, int currentPage, int pageSize) {
		return promoterDao.getReqReportList(paramMap, currentPage, pageSize);
	}
	@SuppressWarnings({ "rawtypes" })
	@Override
	public int getReqReportListCnt(Map paramMap) {
		return promoterDao.getReqReportListCnt(paramMap);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getReqTotalList(Map paramMap) {
		return promoterDao.getReqTotalList(paramMap);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List getNewReqOver10List(Map paramMap) {
		return promoterDao.getNewReqOver10List(paramMap);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List getNewReqOver9kList(Map paramMap) {
		return promoterDao.getNewReqOver9kList(paramMap);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List getNewReqRatioList(Map paramMap) {
		return promoterDao.getNewReqRatioList(paramMap);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List getNewReqExshopList(Map paramMap) {
		return promoterDao.getNewReqExshopList(paramMap);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List getNewReqReportList(Map paramMap, int currentPage, int pageSize) {
		return promoterDao.getNewReqReportList(paramMap, currentPage, pageSize);
	}
	/**
	 * @Create date: 2014.07.16
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public int getNewReqReportListCnt(Map paramMap) {
		return promoterDao.getNewReqReportListCnt(paramMap);
	}
	/**************************************************************************************************************/

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ModelMap importTempEmp(HttpServletRequest request, HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getUserNo();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			excelUtilSer.setFileName(filename);
			excelUtilSer.setPath(path);
			ModelMap preparedMap = excelUtilSer.prepareForImportExcel(request, adminID, map);
			int returnInt  = (Integer) preparedMap.get("token");
			//如果excel数据读取成功，将数据导入 临时表
			if(returnInt == 1){
				LinkedHashMap paramMap = new LinkedHashMap();
				paramMap.put("UPDT_USER", admin.getUserNo());
				// 删除临时表中当前用户旧数据
				try {
					promoterDao.deleteimportTempEmp(paramMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 0;
				}
				Map dataMap = new HashMap();
				dataMap = (Map) preparedMap.get("dataMap");
				// 为当前用户向临时表中插入数据
				try {
					promoterDao.insertTempEmp(dataMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 0;
				}
			}			
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.writebyrule", request));// 调整事由有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				modelMap.put("navTabId", "ess0517");
				modelMap.put(
						"forwardUrl",
						"/hrm/empinfo/viewImportTempEmpResultList");
			} else if (returnInt == 5) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfailcalladmin",
						request));// 更新此人上一条数据出错，请联系管理员
			} else if (returnInt == 6) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"alert.message.pa.bonus.thisDataIsExist_add_fail",
						request));// 此数据已经存在，请核对数据后再进行导入!
			}else if (returnInt == 3) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", "请检查必填项内容！");// 此数据已经存在，请核对数据后再进行导入!
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}
	
}

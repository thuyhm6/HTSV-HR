package com.ait.promoter.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ess.service.InfoApplyLeaveSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.promoter.service.PromoterSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.UserConfiguration;

/**
 * Copyright:    LG CNS
 * Company:      LG CNS
 * @fileName:    PromoterCtroller.java
 * @Create date: 2014.06.09
 * @Create by:   CH.W.G
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/promoter")
public class PromoterCtroller {
	Logger logger = Logger.getLogger(PromoterCtroller.class);
	
	@Autowired
	private PromoterSer promoterSer ;

	@Autowired
	private ToolMenuSer toolMenuSer;

	@Autowired
	private ExcelUtilSer excelUtilSer;

	@Autowired
	private InfoApplyLeaveSer infoApplySerOt;
	
	public static UserConfiguration config = UserConfiguration.getInstance("/system.properties");

	public final static int VARCHAR = 0;

	public final static int DATE = 1;//yyyy-MM-dd

	public final static int NUMBER = 2;
	
	public final static int SINGLE = 0;
	
	public final static int SYSDATE = 2;
	
	public final static int DATE_HMS = 11;//yyyy-MM-dd hh:mm:ss
	
	public final static int DATE_HM = 12;//yyyy-MM-dd hh:mm
	
	public final static int INVOLUTE = 1;
	
	public final static int NVARCHAR = 13;//yyyy-MM-dd

	/**************************************************************************************************************/
	/**
	 * 固定工资设置信息列表
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * @Create date: 2014.06.09
	 */
	@RequestMapping(value = "/viewFixedPayList")
	public ModelAndView viewFixedPayList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("fixedPay", promoterSer.getFixedPayList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, promoterSer.getFixedPayListCnt(request));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "215849"));
		
		return new ModelAndView("/promoter/viewFixedPayList",modelMap);
	}

	// @Create date: 2014.06.09
	@RequestMapping(value = "/updateFixedPayView")
	public ModelAndView updateFixedPay(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("fixedPayInfo", promoterSer.getFixedPayInfo(request));
		return new ModelAndView("/promoter/updateFixedPayView", modelMap);
	}

	// @Create date: 2014.06.09
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/updateFixedPay")
	@ResponseBody
	public Map updateFixedPay(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = promoterSer.updateFixedPay(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "cx0100");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	// @Create date: 2014.06.13
	@RequestMapping(value = "/addFixedPayView")
	public ModelAndView addFixedPayView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		modelMap.put("defaultCpny", admin.getCpnyId());
		return new ModelAndView("/promoter/addFixedPayView", modelMap);
	}

	// @Create date: 2014.06.13
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addFixedPay")
	@ResponseBody
	public Map addFixedPay(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = promoterSer.addFixedPay(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "cx0100");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**************************************************************************************************************/
	/**
	 * 城市等级信息查询
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/viewCityLevelList")
	public ModelAndView viewCityLevelList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{

		List ContractByInsertList = this.promoterSer.getCityLevelList(request) ;
		int  ContractByInsertCnt  = this.promoterSer.getCityLevelListCnt(request) ;

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("searchMap", ObjectBindUtil.getRequestParamData(request));
		modelMap.put("itemList", ContractByInsertList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, ContractByInsertCnt) ;
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "147676"));
		
		return new ModelAndView("/promoter/viewCityLevelList",modelMap);
	}

	/**
	 * 城市等级信息导出Excel
	 * @param request
	 * @param responses
	 * @param modelMap
	 * @return
	 * @throws Exception
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/viewCityLevelListExcel")
	public ModelAndView viewCityLevelListExcel(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List ContractByInsertList = this.promoterSer.getCityLevelListExcel(request) ;
		modelMap.put("itemList", ContractByInsertList);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/promoter/viewCityLevelListExcel",modelMap);
	}

	/**
	 * 城市等级信息
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * @Create date: 2014.06.10
	 */
	@RequestMapping(value = "/updateCityLevelView")
	public ModelAndView updateCityLevelView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("CityLevelInfo", promoterSer.getCityLevelInfo(request));
		
		return new ModelAndView("/promoter/updateCityLevelView",modelMap);
	}

	// @Create date: 2014.06.10
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/updateCityLevel")
	@ResponseBody
	public Map updateCityLevel(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = promoterSer.updateCityLevel(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "cx0300");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**************************************************************************************************************/
	/**
	 * 门店基本信息查询
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/viewCustInfoList")
	public ModelAndView viewCustInfoList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{

		List ContractByInsertList = this.promoterSer.getCustInfoList(request) ;
		int  ContractByInsertCnt  = this.promoterSer.getCustInfoListCnt(request) ;

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("searchMap", ObjectBindUtil.getRequestParamData(request));
		modelMap.put("itemList", ContractByInsertList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, ContractByInsertCnt) ;
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("COM_CD", admin.getCpnyName());
		
        if (modelMap.get("YEAR") == null || modelMap.get("YEAR").equals("")) {
            java.util.Date date = new java.util.Date();
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
    		int year = cal.get(Calendar.YEAR);
        	modelMap.put("YEAR", "" + year);
        }
        
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "215850"));
		
		return new ModelAndView("/promoter/viewCustInfoList",modelMap);
	}

	/**
	 * 门店基本信息导出Excel
	 * @param request
	 * @param responses
	 * @param modelMap
	 * @return
	 * @throws Exception
	 * @Create date: 2014.06.10
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping("/viewCustInfoListExcel")
	public ModelAndView viewCustInfoListExcel(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List ContractByInsertList = this.promoterSer.getCustInfoListExcel(request) ;
		modelMap.put("itemList", ContractByInsertList);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("COM_CD", admin.getCpnyName());
		
		return new ModelAndView("/promoter/viewCustInfoListExcel",modelMap);
	}

	/**
	 * 门店基本信息
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * @Create date: 2014.06.10
	 */
	@RequestMapping(value = "/updateCustInfoView")
	public ModelAndView updateCustInfoView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("CustInfo", promoterSer.getCustInfo(request));
		
		return new ModelAndView("/promoter/updateCustInfoView",modelMap);
	}

	// @Create date: 2014.06.10
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/updateCustInfo")
	@ResponseBody
	public Map updateCustInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = promoterSer.updateCustInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "cx0200");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**************************************************************************************************************/
	// @Create date: 2014.06.11
	@RequestMapping(value = "/getListBySelect")
	public String getListBySelect(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		request.setAttribute("type", request.getParameter("type"));
		request.setAttribute("name", request.getParameter("name"));
		request.setAttribute("parentNo", request.getParameter("parentNo"));
		request.setAttribute("selected", request.getParameter("selected"));
		return "/promoter/selectListTag";
	}

	/**************************************************************************************************************/
	/**
	 * 促销员提成计算
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * @Create date: 2014.06.16
	 */
	@RequestMapping(value = "/viewSalesAchLocalList")
	public ModelAndView viewSalesAchLocalList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String    lastMonth = promoterSer.getLastMonth(request, null);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(lastMonth.length() == 6)
		{
			modelMap.put("year", lastMonth.substring(0, 4));
			modelMap.put("month", lastMonth.substring(4, 6));
		}
		if(!paramMap.containsKey("INIT")){
			modelMap.put("fixedPay", promoterSer.getSalesAchLocalList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, promoterSer.getSalesAchLocalListCnt(request));
		}
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/promoter/viewSalesAchLocalList",modelMap);
	}

	/**
	 * 促销员提成计算
	 * @param request
	 * @return String
	 * @throws Exception
	 * @Create date: 2014.06.17
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/calSalesAchLocal")
	@ResponseBody
	public Map calSalesAchLocal(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap = promoterSer.calSalesAchLocal(request) ;
		String result = retMap.get("RET").toString();
		
		if(result.equals("1")){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("ar.viewarannualeave.title.initsuccess",request));//初始化成功
			map.put("navTabId", "cx1200");
		}else{	
			map.put("statusCode", "300");
			map.put("message", retMap.get("MESSAGE").toString());
		}
		return map;
	}

	/**************************************************************************************************************/
	/**
	 * 总公司单台提成设置
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * @Create date: 2014.06.19
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/viewIncBasicSetupList")
	public ModelAndView viewIncBasicSetupList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{

		List IncBasicSetupList = this.promoterSer.getIncBasicSetupList(request) ;
		int  IncBasicSetupListCnt  = this.promoterSer.getIncBasicSetupListCnt(request) ;

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("searchMap", ObjectBindUtil.getRequestParamData(request));
		modelMap.put("itemList", IncBasicSetupList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, IncBasicSetupListCnt) ;
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "216006"));
		
		return new ModelAndView("/promoter/viewIncBasicSetupList",modelMap);
	}

	/**
	 * 总公司单台提成设置导出Excel
	 * @param request
	 * @param responses
	 * @param modelMap
	 * @return
	 * @throws Exception
	 * @Create date: 2014.06.19
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/viewIncBasicSetupListExcel")
	public ModelAndView viewIncBasicSetupListExcel(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List IncBasicSetupList = this.promoterSer.getIncBasicSetupListExcel(request) ;
		modelMap.put("itemList", IncBasicSetupList);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/promoter/viewIncBasicSetupListExcel",modelMap);
	}

	/**
	 * 总公司单台提成设置
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * @Create date: 2014.06.19
	 */
	@RequestMapping(value = "/updateIncBasicSetupView")
	public ModelAndView updateIncBasicSetupView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{

		modelMap.put("itemInfo", promoterSer.getIncBasicSetupInfo(request));
		
		return new ModelAndView("/promoter/updateIncBasicSetupView",modelMap);
	}

	// @Create date: 2014.06.19
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/updateIncBasicSetup")
	@ResponseBody
	public Map updateIncBasicSetup(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = promoterSer.updateIncBasicSetup(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "cx0400");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**************************************************************************************************************/
	/**
	 * 大区单台提成调整
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * @Create date: 2014.06.20
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/viewOfficeIncAdjustList")
	public ModelAndView viewOfficeIncAdjustList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{

		List OfficeIncAdjustList = this.promoterSer.getOfficeIncAdjustList(request) ;
		int  OfficeIncAdjustListCnt  = this.promoterSer.getOfficeIncAdjustListCnt(request) ;

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("searchMap", ObjectBindUtil.getRequestParamData(request));
		modelMap.put("itemList", OfficeIncAdjustList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, OfficeIncAdjustListCnt) ;
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "216007"));
		
		return new ModelAndView("/promoter/viewOfficeIncAdjustList",modelMap);
	}

	/**
	 * 大区单台提成调整导出Excel
	 * @param request
	 * @param responses
	 * @param modelMap
	 * @return
	 * @throws Exception
	 * @Create date: 2014.06.20
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/viewOfficeIncAdjustListExcel")
	public ModelAndView viewOfficeIncAdjustListExcel(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List OfficeIncAdjustList = this.promoterSer.getOfficeIncAdjustListExcel(request) ;
		modelMap.put("itemList", OfficeIncAdjustList);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/promoter/viewOfficeIncAdjustListExcel",modelMap);
	}

	/**
	 * 大区单台提成调整
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * @Create date: 2014.06.20
	 */
	@RequestMapping(value = "/updateOfficeIncAdjustView")
	public ModelAndView updateOfficeIncAdjustView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{

		modelMap.put("itemInfo", promoterSer.getOfficeIncAdjustInfo(request));
		
		return new ModelAndView("/promoter/updateOfficeIncAdjustView",modelMap);
	}

	// @Create date: 2014.06.20
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/updateOfficeIncAdjust")
	@ResponseBody
	public Map updateOfficeIncAdjust(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = promoterSer.updateOfficeIncAdjust(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "cx0500");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	// @Create date: 2014.06.20
	@RequestMapping(value = "/addOfficeIncAdjustView")
	public ModelAndView addOfficeIncAdjustView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		//modelMap.put("PAY_AREA_CD", admin.getCpnyId());
		modelMap.put("defaultCpny", admin.getCpnyId());
		return new ModelAndView("/promoter/addOfficeIncAdjustView", modelMap);
	}

	// @Create date: 2014.06.20
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addOfficeIncAdjust")
	@ResponseBody
	public Map addOfficeIncAdjust(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int  checkCnt  = promoterSer.incBasicCheckProdId(request) ;
		if ( checkCnt <= 0)
		{
			map.put("statusCode", "300");
			map.put("message", "产品类型或产品ID不存在！");
			return map;
		}
		
		int result = promoterSer.addOfficeIncAdjust(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "cx0500");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**************************************************************************************************************/
	/**
	 * 日别单台提成设置
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * @Create date: 2014.06.23
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/viewIncBasicSetupByDayList")
	public ModelAndView viewIncBasicSetupByDayList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{

		List IncBasicSetupByDayList = this.promoterSer.getIncBasicSetupByDayList(request) ;
		int  IncBasicSetupByDayListCnt  = this.promoterSer.getIncBasicSetupByDayListCnt(request) ;

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("searchMap", ObjectBindUtil.getRequestParamData(request));
		modelMap.put("itemList", IncBasicSetupByDayList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, IncBasicSetupByDayListCnt) ;
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "216008"));
		
		return new ModelAndView("/promoter/viewIncBasicSetupByDayList",modelMap);
	}

	/**
	 * 日别单台提成设置导出Excel
	 * @param request
	 * @param responses
	 * @param modelMap
	 * @return
	 * @throws Exception
	 * @Create date: 2014.06.23
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/viewIncBasicSetupByDayListExcel")
	public ModelAndView viewIncBasicSetupByDayListExcel(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List IncBasicSetupByDayList = this.promoterSer.getIncBasicSetupByDayListExcel(request) ;
		modelMap.put("itemList", IncBasicSetupByDayList);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/promoter/viewIncBasicSetupByDayListExcel",modelMap);
	}

	/**
	 * 日别单台提成设置
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * @Create date: 2014.06.23
	 */
	@RequestMapping(value = "/updateIncBasicSetupByDayView")
	public ModelAndView updateIncBasicSetupByDayView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{

		modelMap.put("itemInfo", promoterSer.getIncBasicSetupByDayInfo(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/promoter/updateIncBasicSetupByDayView",modelMap);
	}

	// @Create date: 2014.06.23
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/updateIncBasicSetupByDay")
	@ResponseBody
	public Map updateIncBasicSetupByDay(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		int checkCnt = promoterSer.incBasicCheckPrcDayCnt(request);
		if( checkCnt > 0)
		{
			map.put("statusCode", "300");
			map.put("message", "选择的日期区间有重复！");
			return map;
		}
		else
		{
			int result = promoterSer.updateIncBasicSetupByDay(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("navTabId", "cx0600");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_fail", request));
			}
		}
		
		return map;
	}

	// @Create date: 2014.06.23
	@RequestMapping(value = "/addIncBasicSetupByDayView")
	public ModelAndView addIncBasicSetupByDayView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		modelMap.put("PAY_AREA_CD", admin.getCpnyId());
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("SEQ", -1);
		modelMap.put("reqest_year", StringUtil.checkNull(request.getParameter("year")));
		modelMap.put("reqest_month", StringUtil.checkNull(request.getParameter("month")));
		modelMap.put("reqest_PRC_BEGIN_DAY", StringUtil.checkNull(request.getParameter("PRC_BEGIN_DAY")));
		modelMap.put("reqest_PRC_END_DAY", StringUtil.checkNull(request.getParameter("PRC_END_DAY")));
		return new ModelAndView("/promoter/addIncBasicSetupByDayView", modelMap);
	}

	// @Create date: 2014.06.23
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addIncBasicSetupByDay")
	@ResponseBody
	public Map addIncBasicSetupByDay(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		int  checkCnt  = promoterSer.incBasicCheckProdId(request) ;
		if ( checkCnt <= 0)
		{
			map.put("statusCode", "300");
			map.put("message", "产品类型或产品ID不存在！");
			return map;
		}
		
		checkCnt = promoterSer.incBasicCheckPrcDayCnt(request);
		if( checkCnt > 0)
		{
			map.put("statusCode", "300");
			map.put("message", "选择的日期区间有重复！");
			return map;
		}
		else
		{
			int result = promoterSer.addIncBasicSetupByDay(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("navTabId", "cx0600");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_fail", request));
			}
		}
		
		return map;
	}

	/**************************************************************************************************************/
	/**
	 * 最小目标管理
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * @Create date: 2014.06.24
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/viewMinGoalSetupList")
	public ModelAndView viewMinGoalSetupList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{

		List MinGoalSetupList = this.promoterSer.getMinGoalSetupList(request) ;
		int  MinGoalSetupListCnt  = this.promoterSer.getMinGoalSetupListCnt(request) ;

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("searchMap", ObjectBindUtil.getRequestParamData(request));
		modelMap.put("itemList", MinGoalSetupList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, MinGoalSetupListCnt) ;
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "216009"));
		
		return new ModelAndView("/promoter/viewMinGoalSetupList",modelMap);
	}

	/**
	 * @Create date: 2014.06.24
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/viewMinGoalSetupListExcel")
	public ModelAndView viewMinGoalSetupListExcel(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap)throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List MinGoalSetupList = this.promoterSer.getMinGoalSetupListExcel(request) ;
		modelMap.put("itemList", MinGoalSetupList);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/promoter/viewMinGoalSetupListExcel",modelMap);
	}

	/**
	 * @Create date: 2014.06.24
	 */
	@RequestMapping(value = "/updateMinGoalSetupView")
	public ModelAndView updateMinGoalSetupView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("itemInfo", promoterSer.getMinGoalSetupInfo(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/promoter/updateMinGoalSetupView",modelMap);
	}

	// @Create date: 2014.06.24
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/updateMinGoalSetup")
	@ResponseBody
	public Map updateMinGoalSetup(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = promoterSer.updateMinGoalSetup(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "cx0700");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	// @Create date: 2014.06.24
	@RequestMapping(value = "/addMinGoalSetupView")
	public ModelAndView addMinGoalSetupView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		modelMap.put("PAY_AREA_CD", admin.getCpnyId());
		modelMap.put("defaultCpny", admin.getCpnyId());
		return new ModelAndView("/promoter/addMinGoalSetupView", modelMap);
	}

	// @Create date: 2014.06.24
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/addMinGoalSetup")
	@ResponseBody
	public Map addMinGoalSetup(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = promoterSer.addMinGoalSetup(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "cx0700");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}
	
	/**************************************************************************************************************/
	/**
	 * 达成率对应指标设置
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * @Create date: 2014.06.24
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/viewSalsRateList")
	public ModelAndView viewSalsRateList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List SalsRateList = this.promoterSer.getSalsRateList(request) ;
		int  SalsRateListCnt  = this.promoterSer.getSalsRateListCnt(request) ;

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("searchMap", ObjectBindUtil.getRequestParamData(request));
		modelMap.put("itemList", SalsRateList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, SalsRateListCnt) ;
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "216010"));
		
		return new ModelAndView("/promoter/viewSalsRateList",modelMap);
	}

	/**
	 * @Create date: 2014.06.24
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/viewSalsRateListExcel")
	public ModelAndView viewSalsRateListExcel(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap)throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List SalsRateList = this.promoterSer.getSalsRateListExcel(request) ;
		modelMap.put("itemList", SalsRateList);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/promoter/viewSalsRateListExcel",modelMap);
	}

	/**
	 * @Create date: 2014.06.24
	 */
	@RequestMapping(value = "/updateSalsRateView")
	public ModelAndView updateSalsRateView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("itemInfo", promoterSer.getSalsRateInfo(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/promoter/updateSalsRateView",modelMap);
	}

	// @Create date: 2014.06.24
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/updateSalsRate")
	@ResponseBody
	public Map updateSalsRate(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = promoterSer.updateSalsRate(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "cx0800");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	// @Create date: 2014.06.24
	@RequestMapping(value = "/addSalsRateView")
	public ModelAndView addSalsRateView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		modelMap.put("PAY_AREA_CD", admin.getCpnyId());
		modelMap.put("defaultCpny", admin.getCpnyId());
		return new ModelAndView("/promoter/addSalsRateView", modelMap);
	}

	// @Create date: 2014.06.24
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addSalsRate")
	@ResponseBody
	public Map addSalsRate(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = promoterSer.addSalsRate(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "cx0800");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**************************************************************************************************************/
	/**
	 * 促销员担当产品
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * @Create date: 2014.06.25
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/viewIncProdTpByPromoterList")
	public ModelAndView viewIncProdTpByPromoterList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{

		List IncProdTpByPromoterList = this.promoterSer.getIncProdTpByPromoterList(request) ;
		int  IncProdTpByPromoterListCnt  = this.promoterSer.getIncProdTpByPromoterListCnt(request) ;

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("searchMap", ObjectBindUtil.getRequestParamData(request));
		modelMap.put("searchMap", modelMap.get("searchMap").toString().replace("dwz.person.empId", "seach_EMPID"));
		modelMap.put("itemList", IncProdTpByPromoterList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, IncProdTpByPromoterListCnt) ;
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "216011"));
		
		return new ModelAndView("/promoter/viewIncProdTpByPromoterList",modelMap);
	}

	/**
	 * @Create date: 2014.06.25
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/viewIncProdTpByPromoterListExcel")
	public ModelAndView viewIncProdTpByPromoterListExcel(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap)throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List IncProdTpByPromoterList = this.promoterSer.getIncProdTpByPromoterListExcel(request) ;
		modelMap.put("itemList", IncProdTpByPromoterList);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/promoter/viewIncProdTpByPromoterListExcel",modelMap);
	}

	/**
	 * @Create date: 2014.06.25
	 */
	@RequestMapping(value = "/updateIncProdTpByPromoterView")
	public ModelAndView updateIncProdTpByPromoterView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("itemInfo", promoterSer.getIncProdTpByPromoterInfo(request));
		
		return new ModelAndView("/promoter/updateIncProdTpByPromoterView",modelMap);
	}

	// @Create date: 2014.06.25
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/updateIncProdTpByPromoter")
	@ResponseBody
	public Map updateIncProdTpByPromoter(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = promoterSer.updateIncProdTpByPromoter(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "cx0900");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	// @Create date: 2014.06.25
	@RequestMapping(value = "/addIncProdTpByPromoterView")
	public ModelAndView addIncProdTpByPromoterView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("PAY_AREA_CD", admin.getCpnyId());
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/promoter/addIncProdTpByPromoterView", modelMap);
	}

	/**************************************************************************************************************/
	/**
	 * 促销员实绩上报
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * @Create date: 2014.06.26
	 */
	@RequestMapping(value = "/viewPromoterSelloutUploadList")
	public ModelAndView viewPromoterSelloutUploadList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String    lastMonth = promoterSer.getLastMonth(request, null);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(lastMonth.length() == 6)
		{
			modelMap.put("year", lastMonth.substring(0, 4));
			modelMap.put("month", lastMonth.substring(4, 6));
		}
		if(!paramMap.containsKey("INIT")){
			modelMap.put("items", promoterSer.getPromoterSelloutUploadList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, promoterSer.getPromoterSelloutUploadListCnt(request));
		}
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "215973"));
		
		return new ModelAndView("/promoter/viewPromoterSelloutUploadList",modelMap);
	}

	/**************************************************************************************************************/
	/**
	 * 促销员实绩审批申请页面
	 * @Create date: 2014.08.29
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/viewSelloutRequest")
	public ModelAndView viewSelloutRequest(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map map = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(map.get("year")==null||map.get("month")==null)
		{
			String    lastMonth = promoterSer.getLastMonth(request, null);
			if(lastMonth.length() == 6)
			{
				modelMap.put("year", lastMonth.substring(0, 4));
				modelMap.put("month", lastMonth.substring(4, 6));
			}
		}
		else
		{
			modelMap.put("year", map.get("year").toString());
			modelMap.put("month", map.get("month").toString());
		}
		modelMap.put("defaultCpny", admin.getCpnyId());
		if(map.get("PAY_AREA_1") != null)
		{
			modelMap.put("PAY_AREA_CD",map.get("PAY_AREA_1").toString());
		}else if(map.get("PAY_AREA_cx1600") != null)
		{
			modelMap.put("PAY_AREA_CD",map.get("PAY_AREA_cx1600").toString());
		}
		if(map.get("BRANCH_1") != null)
		{
			modelMap.put("BRANCH_CD",map.get("BRANCH_1").toString());
		}else if(map.get("BRANCH_cx1600") != null)
		{
			modelMap.put("BRANCH_CD",map.get("BRANCH_cx1600").toString());
		}

		String tabsSelected = request.getSession().getAttribute("TABS_SELECTED") ==null?"0": request.getSession().getAttribute("TABS_SELECTED").toString();
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "219983")) ;
		modelMap.put("menuThirdList", promoterSer.getMenuThirdList("",request));
		//设置决裁人
		modelMap.put("affirmorList", promoterSer.getApplyFeeList(request));
		modelMap.put("tabsSelected",tabsSelected);
		request.getSession().removeAttribute("TABS_SELECTED") ;
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("BRANCH_CD", modelMap.get("BRANCH_CD"));
		paramMap.put("YEAR_MON", modelMap.get("year").toString() + modelMap.get("month").toString());
		promoterSer.getSelloutFromCnmas(request,paramMap);
		modelMap.put("itemList", promoterSer.getPromoterSelloutConfirmList(request));
		modelMap.put("reqTotalList", promoterSer.getReqTotalList(paramMap));
		modelMap.put("reqOver10List", promoterSer.getNewReqOver10List(paramMap));
		modelMap.put("reqOver9kList", promoterSer.getNewReqOver9kList(paramMap));
		modelMap.put("reqRatioList", promoterSer.getNewReqRatioList(paramMap));
		modelMap.put("reqExshopList", promoterSer.getNewReqExshopList(paramMap));

		int iCurrPageNum = 1, iNumPerPage = 10;
		if (UiUtil.getPageNum(request) > 0){
			iCurrPageNum =  UiUtil.getPageNum(request);
			iNumPerPage  = UiUtil.getNumPerPage(request);
		}
		modelMap.put("reqReportList", promoterSer.getNewReqReportList(paramMap, iCurrPageNum, iNumPerPage));
		modelMap.put("TOTALREQCNT", promoterSer.getNewReqReportListCnt(paramMap));
		modelMap.put("PERSON_ID", admin.getPersonId());
		
		return new ModelAndView("/promoter/viewSelloutRequest", modelMap);
	}

	/**
	 * 促销员实绩审批申请列表查询
	 * @Create date: 2014.08.29
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping(value = "/viewSelloutRequestList")
	public ModelAndView viewSelloutRequestList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String    lastMonth = promoterSer.getLastMonth(request, null);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(lastMonth.length() == 6)
		{
			modelMap.put("year", lastMonth.substring(0, 4));
			modelMap.put("month", lastMonth.substring(4, 6));
		}
		if(!paramMap.containsKey("INIT")){
			List list = promoterSer.getSelloutRequestList(request);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, promoterSer.getSelloutRequestListCnt(request));
			modelMap.put("itemList", list);
		}
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "219983"));
		return new ModelAndView("/promoter/viewSelloutRequestList",modelMap);
	}
	
	/**************************************************************************************************************/
	/**
	 * 促销员实绩上报确认查询
	 * @Create date: 2014.08.19
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/viewPromoterSelloutConfirmList")
	public ModelAndView viewPromoterSelloutConfirmList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String    lastMonth = promoterSer.getLastMonth(request, null);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(lastMonth.length() == 6)
		{
			modelMap.put("year", lastMonth.substring(0, 4));
			modelMap.put("month", lastMonth.substring(4, 6));
		}
		modelMap.put("confirmFlag", request.getParameter("seach_CONFIRM_FLAG"));
		if(!paramMap.containsKey("INIT")){
			modelMap.put("items", promoterSer.viewPromoterSelloutConfirmList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, promoterSer.viewPromoterSelloutConfirmListCnt(request));
		}
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "219983")) ;
		
		return new ModelAndView("/promoter/viewPromoterSelloutConfirmList",modelMap);
	}

	/**
	 * 促销员实绩上报确认
	 * @Create date: 2014.08.19
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/selloutConfirm")
	@ResponseBody
	public Map selloutConfirm(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		retMap = promoterSer.selloutConfirm(request) ;
		String result = retMap.get("RET").toString();

		if(result.equals("1")){
			map.put("statusCode", "200");
			map.put("message", "促销员实绩上报确认完成");
			map.put("navTabId", "cx1500");
		}else{	
			map.put("statusCode", "300");
			map.put("message", retMap.get("MESSAGE").toString());
		}
		return map;
	}
	/**************************************************************************************************************/
	/**
	 * 促销员实贩卖实绩读取
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * @Create date: 2014.06.26
	 */
	@RequestMapping(value = "/viewSalesAchievementList")
	public ModelAndView viewSalesAchievementList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String    lastMonth = promoterSer.getLastMonth(request, null);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(lastMonth.length() == 6)
		{
			modelMap.put("year", lastMonth.substring(0, 4));
			modelMap.put("month", lastMonth.substring(4, 6));
		}
		request.setAttribute("ACC_YN", "N");
		modelMap.put("ACC_YN", "N");
		if(!paramMap.containsKey("INIT")){
			modelMap.put("itemList", promoterSer.getSalesAchievementList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, promoterSer.getSalesAchievementListCnt(request));
		}
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/promoter/viewSalesAchievementList",modelMap);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/viewSalesAchievementListExcel")
	public ModelAndView viewSalesAchievementListExcel(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap)throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List itemList = this.promoterSer.getSalesAchievementListExcel(request) ;
		modelMap.put("itemList", itemList);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/promoter/viewSalesAchievementListExcel",modelMap);
	}

	/**
	 * 促销员实贩卖实绩读取 -- 数据提取
	 * @Create date: 2014.06.26
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/reLoadSalesAchievement")
	@ResponseBody
	public Map reLoadSalesAchievement(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		retMap = promoterSer.reLoadSalesAchievement(request) ;
		String result = retMap.get("RET").toString();
		String acc_yn = retMap.get("ACC_YN").toString();
		
		if(result.equals("1")){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("ar.viewarannualeave.title.initsuccess",request));//初始化成功
			map.put("navTabId", acc_yn=="Y"?"cx1300":"cx1100");
		}else{	
			map.put("statusCode", "300");
			map.put("message", retMap.get("MESSAGE").toString());
		}
		return map;
	}

	/**
	 * 促销员实贩卖实绩读取 -- 产品别提成
	 * @Create date: 2014.06.26
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/calSalesAchievement")
	@ResponseBody
	public Map calSalesAchievement(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap = promoterSer.calSalesAchievement(request) ;
		String result = retMap.get("RET").toString();
		String acc_yn = retMap.get("ACC_YN").toString();
		
		if(result.equals("1")){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("ar.viewarannualeave.title.initsuccess",request));//初始化成功
			map.put("navTabId", acc_yn=="Y"?"cx1300":"cx1100");
		}else{	
			map.put("statusCode", "300");
			map.put("message", retMap.get("MESSAGE").toString());
		}
		return map;
	}

	/**************************************************************************************************************/
	/**
	 * 促销员预提业绩读取
	 * @Create date: 2014.06.26
	 */
	@RequestMapping(value = "/viewSalesAchievementPreList")
	public ModelAndView viewSalesAchievementPreList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String    lastMonth = promoterSer.getLastMonth(request, null);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(lastMonth.length() == 6)
		{
			modelMap.put("year", lastMonth.substring(0, 4));
			modelMap.put("month", lastMonth.substring(4, 6));
		}
		request.setAttribute("ACC_YN", "Y");
		modelMap.put("ACC_YN", "Y");
		if(!paramMap.containsKey("BRANCH_4")){
			modelMap.put("BRANCH_4", paramMap.get("hBRANCH4"));
		}
		if(!paramMap.containsKey("INIT")){
			modelMap.put("itemList", promoterSer.getSalesAchievementList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, promoterSer.getSalesAchievementListCnt(request));
		}
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/promoter/viewSalesAchievementPreList",modelMap);
	}

	/**************************************************************************************************************/
	/**
	 * 促销员预提计算
	 * @Create date: 2014.08.05
	 */
	@RequestMapping(value = "/viewSalesAchLocalPreList")
	public ModelAndView viewSalesAchLocalPreList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String    lastMonth = promoterSer.getLastMonth(request, null);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if(lastMonth.length() == 6)
		{
			modelMap.put("year", lastMonth.substring(0, 4));
			modelMap.put("month", lastMonth.substring(4, 6));
		}
		if(!paramMap.containsKey("INIT")){
			modelMap.put("fixedPay", promoterSer.getSalesAchLocalPreList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, promoterSer.getSalesAchLocalPreListCnt(request));
		}
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/promoter/viewSalesAchLocalPreList",modelMap);
	}

	/**
	 * 促销员预提计算
	 * @Create date: 2014.08.05
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/calSalesAchLocalPre")
	@ResponseBody
	public Map calSalesAchLocalPre(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap = promoterSer.calSalesAchLocalPre(request) ;
		String result = retMap.get("RET").toString();
		
		if(result.equals("1")){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("ar.viewarannualeave.title.initsuccess",request));//初始化成功
			map.put("navTabId", "cx1400");
		}else{	
			map.put("statusCode", "300");
			map.put("message", retMap.get("MESSAGE").toString());
		}
		return map;
	}

	/**************************************************************************************************************/
	/**
	 * 总公司单台提成设置模板下载
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 * @Create date: 2014.07.08
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/downIncBasicSetupTemplate")
	public void downIncBasicSetupTemplate(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();

		aliasNameList.add("产品ID");
		aliasNameList.add("总部单价");
		aliasNameList.add("提成率(%)");

		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "WAD31UG2 AGRPLGT"); 
		map.put("CELL1", "2998");
		map.put("CELL2", "5");
		
        List tipList = new ArrayList();

		list.add(map);
		String name = "incBasicSetup";
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelByNameWithHeaderTip(
				request, response, modelMap, sqlContentmap, aliasNameList, null, name, tipList);
	}

	/**************************************************************************************************************/

	/**
	 * @Create date: 2014.07.08
	 */
	@RequestMapping(value = "/importDataFromExcel")
	public ModelAndView importDataFromExcel(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		modelMap.put("importFunName", request.getParameter("importFunName"));
		return new ModelAndView("/promoter/importDataFromExcel",modelMap);		
	}

	/**
	 * @Create date: 2014.07.08
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/viewDataImportResultList")
	public ModelAndView viewDataImportResultList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		searchMap.put("SUBSD_CD", admin.getCpnyId());

		List itemList   = promoterSer.getDataImportResultList(request, searchMap);
		int impTotalCnt = promoterSer.getDataImportResultListCnt(request, searchMap);	
		int impErrCnt   = promoterSer.getDataImportErrCnt(request, searchMap);
		modelMap.put("item", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "216006"));
		return new ModelAndView("/promoter/viewDataImportResultList",modelMap);
	}
	
	@RequestMapping(value = "/createDataImportResult")
	@ResponseBody
	public int createDataImportResult(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap) throws Exception{
		String result = promoterSer.importIncBasicSetupFromExcel(request,response,modelMap);
		return result.equals("OK")?1:0;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/viewDataImportResultListExcel")
	public ModelAndView viewDataImportResultListExcel(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List itemList = this.promoterSer.getImportIncBasicSetupFromExcel(request) ;
		modelMap.put("itemList", itemList);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/promoter/viewDataImportResultListExcel",modelMap);
	}
	
	/**************************************************************************************************************/
	/**
	 * 大区单台提成调整导入模板下载
	 * @Create date: 2014.07.16
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/downOfficeIncAdjustTemplate")
	public void downOfficeIncAdjustTemplate(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		
		aliasNameList.add("大区ID");
		aliasNameList.add("产品ID");
		aliasNameList.add("调整比率");
		aliasNameList.add("固定提成");

		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "212738");
		map.put("CELL1", "WAD31UG2 AGRPLGT");
		map.put("CELL2", "1");
		map.put("CELL3", "2");
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("CPNYID",  admin.getCpnyId());
		paramMap.put("USER_NO",  admin.getUserNo());
		paramMap.put("deptLevel", "2");
  		List payAreaCdList  = promoterSer.getPayAreaCodeList(paramMap);
  		Object[] TypeList = payAreaCdList.toArray();
        String PayAreaHeader = "说明:";
        for (int i = 0; i < TypeList.length; i++) {
        	PayAreaHeader = PayAreaHeader + "\n"+ ((Map)TypeList[i]).get("CODE_NO")+ "   "+ ((Map)TypeList[i]).get("CODE_NAME");
        }  
        LinkedHashMap tipMap_Area = new LinkedHashMap();
        tipMap_Area.put("TIP_COLUMN", "大区ID");
        tipMap_Area.put("TIP_CONTENT", PayAreaHeader);
        List tipList = new ArrayList();
        tipList.add(tipMap_Area);

		list.add(map);
		String name = "incAdjust";
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelByNameWithHeaderTip(
				request, response, modelMap, sqlContentmap, aliasNameList, null, name, tipList);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/viewImportOfficeIncAdjustResultList")
	public ModelAndView viewImportOfficeIncAdjustResultList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		searchMap.put("SUBSD_CD", admin.getCpnyId());

		List itemList   = promoterSer.getImportOfficeIncAdjustResultList(request, searchMap);
		int impTotalCnt = promoterSer.getImportOfficeIncAdjustResultCnt(request, searchMap);	
		int impErrCnt   = promoterSer.getImportOfficeIncAdjustErrCnt(request, searchMap);
		modelMap.put("item", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "216007"));
		return new ModelAndView("/promoter/viewImportOfficeIncAdjustResultList",modelMap);
	}
	
	@RequestMapping(value = "/createImportOfficeIncAdjustResult")
	@ResponseBody
	public int createImportOfficeIncAdjustResult(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap) throws Exception{
		String result = promoterSer.importOfficeIncAdjustFromExcel(request,response,modelMap);
		return result.equals("OK")?1:0;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/viewImportOfficeIncAdjustResultExcel")
	public ModelAndView viewImportOfficeIncAdjustResultExcel(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List itemList = this.promoterSer.getImportOfficeIncAdjustFromExcel(request) ;
		modelMap.put("itemList", itemList);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/promoter/viewImportOfficeIncAdjustResultExcel",modelMap);
	}

	/**************************************************************************************************************/
	/**
	 * 促销员实绩上报模板下载
	 * @Create date: 2014.07.16
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/downSelloutTemplate")
	public void downSelloutTemplate(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();

		aliasNameList.add("支社");
		aliasNameList.add("社号");
		aliasNameList.add("月份");
		aliasNameList.add("日期");
		aliasNameList.add("产品类型");
		aliasNameList.add("产品ID");
		aliasNameList.add("客户ID");
		aliasNameList.add("CHANNEL");
		aliasNameList.add("销售数量");
		aliasNameList.add("NOTICE_PRICE");
		aliasNameList.add("SELLOUT_PRICE");
		aliasNameList.add("SEQ");

		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "20006");       
		map.put("CELL1", "YCH431778");
		map.put("CELL2", "201407");
		map.put("CELL3", "2014-07-18");
		map.put("CELL4", "REF");
		map.put("CELL5", "GR-A2075FHA CBWPLGE");
		map.put("CELL6", "00065938S");
		map.put("CELL7", "B00");
		map.put("CELL8", "1");
		map.put("CELL9", "1000");
		map.put("CELL10", "1100");
		map.put("CELL11", "101");
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("CPNYID",  admin.getCpnyId());
		paramMap.put("USER_NO",  admin.getUserNo());
		paramMap.put("deptLevel", "3");
  		List payAreaCdList  = promoterSer.getPayAreaCodeList(paramMap);
  		Object[] TypeList = payAreaCdList.toArray();
        String branchHeader = "说明:";
        for (int i = 0; i < TypeList.length; i++) {
        	branchHeader = branchHeader + "\n"+ ((Map)TypeList[i]).get("ACC_ORG_CODE")+ "   "+ ((Map)TypeList[i]).get("CODE_NAME");
        }  
        LinkedHashMap tipMap_Area = new LinkedHashMap();
        tipMap_Area.put("TIP_COLUMN", "支社");
        tipMap_Area.put("TIP_CONTENT", branchHeader);
        List tipList = new ArrayList();
        tipList.add(tipMap_Area);

		list.add(map);
		String name = "Sellout";
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelByNameWithHeaderTip(
				request, response, modelMap, sqlContentmap, aliasNameList, null, name, tipList);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/viewImportSelloutResultList")
	public ModelAndView viewImportSelloutResultList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		searchMap.put("SUBSD_CD", admin.getCpnyId());

		List itemList   = promoterSer.getImportSelloutResultList(request, searchMap);
		int impTotalCnt = promoterSer.getImportSelloutResultCnt(request, searchMap);	
		int impErrCnt   = promoterSer.getImportSelloutErrCnt(request, searchMap);
		modelMap.put("item", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "215973"));
		return new ModelAndView("/promoter/viewImportSelloutResultList",modelMap);
	}
	
	@RequestMapping(value = "/createImportSelloutResult")
	@ResponseBody
	public int createImportSelloutResult(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap) throws Exception{
		String result = promoterSer.importSelloutFromExcel(request,response,modelMap);
		return result.equals("OK")?1:0;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/viewImportSelloutResultExcel")
	public ModelAndView viewImportSelloutResultExcel(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List itemList = this.promoterSer.getImportSelloutFromExcel(request) ;
		modelMap.put("itemList", itemList);
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/promoter/viewImportSelloutResultExcel",modelMap);
	}

	/**************************************************************************************************************/
	/**
	 * 门店基本信息加密后导出Excel
	 * @Create date: 2014.08.06
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@RequestMapping(value = "/expCustInfoListExcel")
	public void expCustInfoListExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String lang = searchMap.get("interLanguage").toString();
		//设置excel header
		List aliasNameList = new ArrayList();
		aliasNameList.add("年份");
		aliasNameList.add("大区");
		aliasNameList.add("支社");
		aliasNameList.add("门店地区");
		aliasNameList.add("门店代码");
		aliasNameList.add("门店名称");
		aliasNameList.add("门店等级");
		aliasNameList.add("GoldenShop");
		aliasNameList.add("商场形态");
		aliasNameList.add("渠道2");
		aliasNameList.add("省名称");
		aliasNameList.add("城市名称");
		aliasNameList.add("地区名称");
		aliasNameList.add("开店时间");
		aliasNameList.add("状态");
		aliasNameList.add("门店提成率");
		aliasNameList.add("提成上限");

		String[] columns = { "YYYY", "DIV_CD_NM", "DEPT_NM", "CITY_LEVEL_NM", "SHOP_CD", "SHOP_NAME"
				           , "SHOP_LEVEL", "GOLDEN_SHOP", "CHANNEL1_NAME", "CHANNEL2_NAME", "STATE_NAME"
				           , "CITY_NAME"
				           , "AREA_NAME", "OPEN_DATE", "USE_YN", "DEDUCT_RATIO", "INC_UP_LIMIT" };
		//提取导出数据列表
		List aliasValueList  = promoterSer.getCustInfoListExcel(request) ;
		String name = "custInfoList";
		this.excelUtilSer.exportExcelByNamePwd(
				request, response, modelMap, aliasValueList, aliasNameList, columns, name, searchMap);
	}

	/**************************************************************************************************************/
	/**
	 * 城市等级信息加密后导出Excel
	 * @Create date: 2014.08.06
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@RequestMapping(value = "/expCityLevelListExcel")
	public void expCityLevelListExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String lang = searchMap.get("interLanguage").toString();
		//设置excel header
		List aliasNameList = new ArrayList();
		aliasNameList.add("省编号");
		aliasNameList.add("省名称");
		aliasNameList.add("城市编号");
		aliasNameList.add("城市名称");
		aliasNameList.add("地区编号");
		aliasNameList.add("地区名称");
		aliasNameList.add("城市等级");

		String[] columns = { "STATE_CD", "STATE_NM", "CITY_CD", "CITY_NM", "REGION_CD", "REGION_NM", "CITY_LEVEL" };
		//提取导出数据列表
		List aliasValueList  = promoterSer.getCityLevelListExcel(request) ;
		String name = "cityLevelList";
		this.excelUtilSer.exportExcelByNamePwd(
				request, response, modelMap, aliasValueList, aliasNameList, columns, name, searchMap);
	}

	/**************************************************************************************************************/
	/**
	 * 总公司单台提成设置信息加密后导出Excel
	 * @Create date: 2014.08.06
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@RequestMapping(value = "/expIncBasicSetupListExcel")
	public void expIncBasicSetupListExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String lang = searchMap.get("interLanguage").toString();
		//设置excel header
		List aliasNameList = new ArrayList();
		aliasNameList.add("产品ID");
		aliasNameList.add("产品类型");
		aliasNameList.add("单价");
		aliasNameList.add("总部单价");
		aliasNameList.add("提成率%");
		aliasNameList.add("总部提成");
		aliasNameList.add("更新人");
		aliasNameList.add("状态");
		aliasNameList.add("单价同步时间");

		String[] columns = { "PROD_ID", "PROD_TP_NM", "UNIT_PRC", "HEAD_UNIT_PRC", "INC_RATE", "SUBSD_INCTV_AMT", "UPDT_USER", "USE_YN", "PRICE_IF_DATE" };
		//提取导出数据列表
		List aliasValueList  = promoterSer.getIncBasicSetupListExcel(request) ;
		String name = "incBasicSetupList";
		this.excelUtilSer.exportExcelByNamePwd(
				request, response, modelMap, aliasValueList, aliasNameList, columns, name, searchMap);
	}

	/**************************************************************************************************************/
	/**
	 * 大区单台提成调整信息加密后导出Excel
	 * @Create date: 2014.08.06
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@RequestMapping(value = "/expOfficeIncAdjustListExcel")
	public void expOfficeIncAdjustListExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String lang = searchMap.get("interLanguage").toString();
		//设置excel header
		List aliasNameList = new ArrayList();
		aliasNameList.add("大区");
		aliasNameList.add("产品类型");
		aliasNameList.add("产品ID");
		aliasNameList.add("单价");
		aliasNameList.add("总部单价");
		aliasNameList.add("标准提成");
		aliasNameList.add("调整比率");
		aliasNameList.add("基本提成");
		aliasNameList.add("固定提成");
		aliasNameList.add("修改人");
		aliasNameList.add("使用标记");

		String[] columns = { "PAY_AREA_NM", "PROD_TP_NM", "PROD_ID", "UNIT_PRC", "HEAD_UNIT_PRC", "SUBSD_INCTV_AMT", "DIFF_RAT", "BASE_AMT", "FXD_AMT", "UPDT_USER", "USE_YN" };
		//提取导出数据列表
		List aliasValueList  = promoterSer.getOfficeIncAdjustListExcel(request) ;
		String name = "incAdjustList";
		this.excelUtilSer.exportExcelByNamePwd(
				request, response, modelMap, aliasValueList, aliasNameList, columns, name, searchMap);
	}

	/**************************************************************************************************************/
	/**
	 * 促销员实贩卖实绩加密后导出Excel
	 * @Create date: 2014.08.06
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@RequestMapping(value = "/expSalesAchievementListExcel")
	public void expSalesAchievementListExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String lang = searchMap.get("interLanguage").toString();
		//设置excel header
		List aliasNameList = new ArrayList();
		aliasNameList.add("大区");
		aliasNameList.add("支社");
		aliasNameList.add("姓名");
		aliasNameList.add("社号");
		aliasNameList.add("产品类型");
		aliasNameList.add("产品ID");
		aliasNameList.add("客户ID");
		aliasNameList.add("客户类型");
		aliasNameList.add("销售数量");
		aliasNameList.add("变动提成");
		aliasNameList.add("固定提成");
		aliasNameList.add("销售金额");

		String[] columns = { "PAY_AREA_NM", "BRANCH","EMP_NM", "EMPNO", "PROD_TP", "PROD_ID", "CUST_ID", "CUST_TP", "SALS_QTY", "VARB_INCTV_AMT", "FXD_INCTV_AMT", "SALS_AMT" };
		//提取导出数据列表
		List aliasValueList  = promoterSer.getSalesAchievementListExcel(request) ;
		String name = "salesAchievementList";
		if(searchMap.get("ACC_YN").equals("Y"))
		{
			name = "salesAchievementPreList";
		}
		this.excelUtilSer.exportExcelByNamePwd(
				request, response, modelMap, aliasValueList, aliasNameList, columns, name, searchMap);
	}

	/**************************************************************************************************************/
	/**
	 * 促销员实贩卖实绩申请信息
	 * @Create date: 2014.09.01
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/viewSelloutRequestDtl")
	public ModelAndView viewSelloutRequestDtl(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		modelMap.put("REQ_ID", request.getParameter("REQ_ID"));
		modelMap.put("REQ_TITLE", request.getParameter("REQ_TITLE"));
		modelMap.put("itemList", promoterSer.getSelloutReqDtlList(request));
		modelMap.put("mstList", promoterSer.getSelloutReqByReqId(request));
		modelMap.put("defaultCpny", admin.getCpnyId());

		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("REQ_ID", request.getParameter("REQ_ID"));
		modelMap.put("reqOver10List", promoterSer.getReqOver10List(paramMap));
		modelMap.put("reqOver9kList", promoterSer.getReqOver9kList(paramMap));
		modelMap.put("reqRatioList", promoterSer.getReqRatioList(paramMap));
		modelMap.put("reqExshopList", promoterSer.getReqExshopList(paramMap));
		int iCurrPageNum = 1, iNumPerPage = 10;
		if (UiUtil.getPageNum(request) > 0){
			iCurrPageNum =  UiUtil.getPageNum(request);
			iNumPerPage  = UiUtil.getNumPerPage(request);
		}
		modelMap.put("reqReportList", promoterSer.getReqReportList(paramMap, iCurrPageNum, iNumPerPage));
		modelMap.put("TOTALREQCNT", promoterSer.getReqReportListCnt(paramMap));
		
		String tabsSelected = request.getSession().getAttribute("TABS_SELECTED") ==null?"0": request.getSession().getAttribute("TABS_SELECTED").toString();
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "219983")) ;
		modelMap.put("menuThirdList", promoterSer.getMenuThirdList("",request));
		modelMap.put("affirmorList", promoterSer.getAffirmorListByReqId(request));
		modelMap.put("tabsSelected",tabsSelected);
		request.getSession().removeAttribute("TABS_SELECTED") ;
		LinkedHashMap paraMap = new LinkedHashMap();
		paraMap.put("APPLY_TYPE", "278651");
		paraMap.put("APPLY_NO", request.getParameter("REQ_ID"));
		List fileList = infoApplySerOt.getEssFileList(paraMap);
		LinkedHashMap tmpMap = new LinkedHashMap();
		String SellDtl_fileNmae="",SellDtl_fileUrl="",SellDtl_fileName="";
		for(int i=0;i<fileList.size();i++){
			tmpMap = (LinkedHashMap) fileList.get(i);
			if(i==0){
				SellDtl_fileUrl=tmpMap.get("FILE_URL").toString();
				SellDtl_fileName=tmpMap.get("FILE_NAME").toString();
				SellDtl_fileNmae="<a href='/ess/infoApplyLeave/downloadFile?fileName="+SellDtl_fileUrl+"&file="+SellDtl_fileName+"'>"+SellDtl_fileName+"</a>";
			}else{
				SellDtl_fileUrl+=";"+ tmpMap.get("FILE_URL").toString();
				SellDtl_fileName+=";"+ tmpMap.get("FILE_NAME").toString();
				SellDtl_fileNmae+=";&nbsp;&nbsp;<a href='/ess/infoApplyLeave/downloadFile?fileName="+tmpMap.get("FILE_URL").toString()+"&file="+tmpMap.get("FILE_NAME").toString()+"'>"+tmpMap.get("FILE_NAME").toString()+"</a>";
			}
		}
		modelMap.put("SellDtl_fileNmae", SellDtl_fileNmae);
		modelMap.put("SellDtl_fileUrl", SellDtl_fileUrl);
		modelMap.put("SellDtl_fileName", SellDtl_fileName);
		modelMap.put("PERSON_ID", admin.getPersonId());
		
		return new ModelAndView("/promoter/viewSelloutRequestDtl", modelMap);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addSelloutReq")
	@ResponseBody
	public Map addSelloutReq(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap = promoterSer.addSelloutReq(request) ;
		String result = retMap.get("RET").toString();
		
		if(result.equals("0")){
			map.put("statusCode", "200");
			map.put("message", "暂存成功！");
			map.put("navTabId", "cx1400");
		}else if(result.equals("1")){
			map.put("statusCode", "200");
			map.put("message", "申请成功！");
			map.put("navTabId", "cx1400");
		}else{
			map.put("statusCode", "300");
			map.put("message", retMap.get("MESSAGE").toString());
		}
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/submitSelloutReq")
	@ResponseBody
	public Map submitSelloutReq(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		retMap = promoterSer.submitSelloutReq(request) ;
		String result = retMap.get("RET").toString();

		if(result.equals("1")){
			map.put("statusCode", "200");
			map.put("message", "促销员实绩上报确认完成");
			map.put("navTabId", "cx1500");
		}else{	
			map.put("statusCode", "300");
			map.put("message", retMap.get("MESSAGE").toString());
		}
		return map;
	}
	
	/**************************************************************************************************************/

	/**
	 * 实绩上传后申请确认页面导出Excel
	 * @Create date: 2014.08.06
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/expSelloutRequestExcel")
	public void expSelloutRequestExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		searchMap.put("YEAR_MON", searchMap.get("year").toString() + searchMap.get("month").toString());
		
		String[] colnames1 = { "大区","支社","产品类型","销售数量","支社核对","发票销量","截屏及凭证销量"};
		String[] colnames2 = { "门店编码","PR社番","销售时间","产品型号","PR上报销量汇总","办事处","卖场名称","门店等级","渠道等级","渠道","Bill to渠道","支付比例(%)","支社确认销量"};
		String[] colnames3 = { "办事处","社号","姓名","门店代码","主责商场","channel","主责产品","本月销售金额","本月销量","本月预计提成" };
		String[] colnames4 = { "支社","社号","姓名","门店代码","主责商场","channel","主责产品","上月确认销量","上月实发提成","本月确认销量","本月预计提成","对比上月伸张率%" };
		String[] colnames5 = { "支社","社号","姓名","门店代码","主责商场","channel","主责产品","上月确认销量","上月实发提成","本月确认销量","本月预计提成","对比上月伸张率%" };
		String[] colnames6 = { "COME_CODE","社号","姓名","月份","销售日期","产品类型","产品ID","支社","客户ID","门店名称","CHANNEL_CODE","支社确认销量","NOTICE PRICE","SELLOUT PRICE","SEQ" };
		List aliasNameList = new ArrayList();
		aliasNameList.add(colnames1);
		aliasNameList.add(colnames2);
		aliasNameList.add(colnames3);
		aliasNameList.add(colnames4);
		aliasNameList.add(colnames5);
		aliasNameList.add(colnames6);
		String[] columns1 = { "PAY_AREA_NM", "BRANCH_NM", "PROD_TP_NM","TOTAL_NUM","BRANCH_QTY","INVOICE_QTY","SCREEN_QTY"};
		String[] columns2 = { "SHOP_CD","PR_EMPID","SALE_DATE","PROD_ID","SALS_QTY","BRANCH","SHOP_NAME","SHOP_LEVEL","CHANNEL_GRADE","CHANNEL_NAME","BILL_TO_NAME","PAY_RATE","REAL_QTY"};
		String[] columns3 = { "BRANCH", "EMPID", "LOCAL_NAME", "SHOP_CD", "SHOP_NAME", "CHANNEL_NAME", "PROD_TP", "SALS_AMT", "SALS_QTY", "INC_AMT" };
		String[] columns4 = { "BRANCH", "EMPID", "LOCAL_NAME", "SHOP_CD", "SHOP_NAME", "CHANNEL_NAME", "PROD_TP", "LAST_SALS_QTY", "LAST_INC_AMT", "SALS_QTY", "INC_AMT", "RATIO" };
		String[] columns5 = { "BRANCH", "EMPID", "LOCAL_NAME", "SHOP_CD", "SHOP_NAME", "CHANNEL_NAME", "PROD_TP", "LAST_SALS_QTY", "LAST_INC_AMT", "SALS_QTY", "INC_AMT", "RATIO" };
		String[] columns6 = { "COM_CODE", "EMP_NO", "EMP_NM", "SALE_MONTH", "SALE_DAY", "MODEL_CATEGORY_CODE", "MODEL_CODE", "BRANCH", "SHIP_TO_CODE", "SHIP_TO_NAME", "CHANNEL_CODE", "SALE_QTY", "NOTICE_PRICE", "SELLOUT_PRICE", "SEQ" };
		List aliasColList = new ArrayList();
		aliasColList.add(columns1);
		aliasColList.add(columns2);
		aliasColList.add(columns3);
		aliasColList.add(columns4);
		aliasColList.add(columns5);
		aliasColList.add(columns6);
		
		List valueList1 = promoterSer.getPromoterSelloutConfirmList(request);
		List valueList2 = promoterSer.getNewReqOver10List(searchMap);
		List valueList3 = promoterSer.getNewReqOver9kList(searchMap);
		List valueList4 = promoterSer.getNewReqRatioList(searchMap);
		List valueList5 = promoterSer.getNewReqExshopList(searchMap);
		List valueList6 = promoterSer.getNewReqReportList(searchMap,-1,-1);
		List aliasValueList = new ArrayList();
		aliasValueList.add(valueList1);
		aliasValueList.add(valueList2);
		aliasValueList.add(valueList3);
		aliasValueList.add(valueList4);
		aliasValueList.add(valueList5);
		aliasValueList.add(valueList6);
		
		String name = "SelloutRequestExcel";
		String[] sheets = { "实贩卖核对全月说明", "团购、批发10台以上明细","提成9000元以上明细","对比上月伸张率（提成5000元以上，对比上月伸张率100%以上）","专卖店明细","支社确认结果明细" };
		this.excelUtilSer.exportExcelMoreTab(request, response, modelMap, aliasValueList, aliasNameList, aliasColList, name, sheets, searchMap);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/expSelloutReqDtlExcel")
	public void expSelloutReqDtlExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		String[] colnames1 = { "大区","支社","产品类型","销售数量","支社核对","发票销量","截屏及凭证销量"};
		String[] colnames2 = { "门店编码","PR社番","销售时间","产品型号","PR上报销量汇总","办事处","卖场名称","门店等级","渠道等级","渠道","Bill to渠道","支付比例(%)","支社确认销量"};
		String[] colnames3 = { "办事处","社号","姓名","门店代码","主责商场","channel","主责产品","本月销售金额","本月销量","本月预计提成" };
		String[] colnames4 = { "支社","社号","姓名","门店代码","主责商场","channel","主责产品","上月确认销量","上月实发提成","本月确认销量","本月预计提成","对比上月伸张率%" };
		String[] colnames5 = { "支社","社号","姓名","门店代码","主责商场","channel","主责产品","上月确认销量","上月实发提成","本月确认销量","本月预计提成","对比上月伸张率%" };
		String[] colnames6 = { "COME_CODE","社号","姓名","月份","销售日期","产品类型","产品ID","支社","客户ID","门店名称","CHANNEL_CODE","支社确认销量","NOTICE PRICE","SELLOUT PRICE","SEQ" };
		List aliasNameList = new ArrayList();
		aliasNameList.add(colnames1);
		aliasNameList.add(colnames2);
		aliasNameList.add(colnames3);
		aliasNameList.add(colnames4);
		aliasNameList.add(colnames5);
		aliasNameList.add(colnames6);
		String[] columns1 = { "PAY_AREA_NM", "BRANCH_NM", "PROD_TP_NM","TOTAL_NUM","BRANCH_QTY","INVOICE_QTY","SCREEN_QTY"};
		String[] columns2 = { "SHOP_CD","PR_EMPID","SALE_DATE","PROD_ID","SALS_QTY","BRANCH","SHOP_NAME","SHOP_LEVEL","CHANNEL_GRADE","CHANNEL_NAME","BILL_TO_NAME","PAY_RATE","REAL_QTY"};
		String[] columns3 = { "BRANCH", "EMPID", "LOCAL_NAME", "SHOP_CD", "SHOP_NAME", "CHANNEL_NAME", "PROD_TP", "SALS_AMT", "SALS_QTY", "INC_AMT" };
		String[] columns4 = { "BRANCH", "EMPID", "LOCAL_NAME", "SHOP_CD", "SHOP_NAME", "CHANNEL_NAME", "PROD_TP", "LAST_SALS_QTY", "LAST_INC_AMT", "SALS_QTY", "INC_AMT", "RATIO" };
		String[] columns5 = { "BRANCH", "EMPID", "LOCAL_NAME", "SHOP_CD", "SHOP_NAME", "CHANNEL_NAME", "PROD_TP", "LAST_SALS_QTY", "LAST_INC_AMT", "SALS_QTY", "INC_AMT", "RATIO" };
		String[] columns6 = { "COM_CODE", "EMP_NO", "EMP_NM", "SALE_MONTH", "SALE_DAY", "MODEL_CATEGORY_CODE", "MODEL_CODE", "BRANCH", "SHIP_TO_CODE", "SHIP_TO_NAME", "CHANNEL_CODE", "SALE_QTY", "NOTICE_PRICE", "SELLOUT_PRICE", "SEQ" };
		List aliasColList = new ArrayList();
		aliasColList.add(columns1);
		aliasColList.add(columns2);
		aliasColList.add(columns3);
		aliasColList.add(columns4);
		aliasColList.add(columns5);
		aliasColList.add(columns6);
		
		List valueList1 = promoterSer.getSelloutReqDtlList(request);
		List valueList2 = promoterSer.getReqOver10List(searchMap);
		List valueList3 = promoterSer.getReqOver9kList(searchMap);
		List valueList4 = promoterSer.getReqRatioList(searchMap);
		List valueList5 = promoterSer.getReqExshopList(searchMap);
		List valueList6 = promoterSer.getReqReportList(searchMap, -1, -1);
		List aliasValueList = new ArrayList();
		aliasValueList.add(valueList1);
		aliasValueList.add(valueList2);
		aliasValueList.add(valueList3);
		aliasValueList.add(valueList4);
		aliasValueList.add(valueList5);
		aliasValueList.add(valueList6);
		
		String name = "SelloutReqDtlExcel";
		String[] sheets = { "实贩卖核对全月说明", "团购、批发10台以上明细","提成9000元以上明细","对比上月伸张率（提成5000元以上，对比上月伸张率100%以上）","专卖店明细","支社确认结果明细" };
		this.excelUtilSer.exportExcelMoreTab(request, response, modelMap, aliasValueList, aliasNameList, aliasColList, name, sheets, searchMap);
	}

}

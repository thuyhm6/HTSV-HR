package com.ait.inct.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.bean.AdminBean;
import com.ait.ess.service.InfoApplySer;
import com.ait.inct.service.SalesmanIncentiveSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * 
 * @fileName SalesmanIncentiveCtroller.java
 * @author 			@Date			@version		@Desc
 * ---------------------------------------------------------------------------------------------------------------
 * penghaixia 		2014-6-4 		1.0				initial  
 *----------------------------------------------------------------------------------------------------------------
 */
@Controller
@RequestMapping(value = "/inct/salesman")
public class SalesmanIncentiveCtroller {

	@Autowired
	private SalesmanIncentiveSer SalesmanIncentiveSer;

	@Autowired
	private ToolMenuSer toolMenuSer;
	
	@Autowired
	private ExcelUtilSer excelUtilSer;
	
	@Autowired
	private InfoApplySer infoApplySer;

	/**
	 * 营业员提成
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewIncentiveCalcList")
	public ModelAndView viewIncentiveCalcList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		searchMap.put("SUBSD_CD", admin.getCpnyId());
		searchMap.put("USER_NO", admin.getUserNo());
		searchMap.put("ACCRUAL_YN", "N");
		searchMap.put("EMPNO", searchMap.get("dwz.person.empId"));
		if(!searchMap.containsKey("YEAR")){
			String SALS_MON = DateUtil.getLastMonthStr();
			searchMap.put("YEAR", SALS_MON.substring(0, 4));
			searchMap.put("MONTH", SALS_MON.substring(4, 6));
		}
		searchMap.put("SALS_MON", searchMap.get("YEAR").toString()+searchMap.get("MONTH").toString());
		
		List itemList = this.SalesmanIncentiveSer.getIncentiveCalcList(request, searchMap);
		int itemListCnt = this.SalesmanIncentiveSer.getIncentiveCalcListCnt(request, searchMap);
		String closeFlag = this.SalesmanIncentiveSer.getSalesInctPayClosedFlagByMonth(request, searchMap);
		modelMap.put("closeFlag", closeFlag);
		modelMap.put("searchMap", searchMap);
		modelMap.put("itemList", itemList);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, itemListCnt) ;		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "215989")) ;

		return new ModelAndView("/inct/salesman/viewIncentiveCalcList",modelMap);
	}	
	
	/**
	 * 营业员提成  列表的导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewIncentiveCalcListExcel")
	public void viewIncentiveCalcListExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String lang=paramMap.get("interLanguage").toString();
		//设置excel header
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.DEPTNAME",lang));
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.empNo",lang)+"*");
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.empName",lang));
		aliasNameList.add(TipMessage.getTipMessage("ar.excelexport.title.month",lang)+"*");
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.variablePay",lang));
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.ratio",lang));
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.deduct",lang));
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.adjustInct",lang)+"*");
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.inctResult",lang));
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.remark",lang));
		aliasNameList.add("注册人");
		aliasNameList.add("注册时间");
		aliasNameList.add("更新人");
		aliasNameList.add("更新时间");
		//列名
		String[] columns = { "ORG_NM", "EMPNO", "EMP_NM", "INCTV_MON",
	            "VARB_INCTV_AMT", "ACHV_RAT", "DEDUT_AMT","ADJST_AMT","TOT_INCTV_AMT","REMARK",
	            "RGST_USER","RGST_DTIME","UPDT_USER","UPDT_DTIME"};
		//提取导出数据列表
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("SUBSD_CD", admin.getCpnyId());
		paramMap.put("USER_NO", admin.getUserNo());
		paramMap.put("ACCRUAL_YN", "N");
		paramMap.put("EMPNO", paramMap.get("dwz.person.empId"));
		if(!paramMap.containsKey("YEAR")){
			String SALS_MON = DateUtil.getLastMonthStr();
			paramMap.put("YEAR", SALS_MON.substring(0, 3));
			paramMap.put("MONTH", SALS_MON.substring(4, 5));
			paramMap.put("SALS_MON", SALS_MON);
		}else{
			paramMap.put("SALS_MON", paramMap.get("YEAR").toString()+paramMap.get("MONTH").toString());
		}
		List aliasValueList  = this.SalesmanIncentiveSer.getIncentiveCalcList(request, paramMap);
		String name = "salesmanIncentiveCalcList";
		this.excelUtilSer.exportExcelByNamePwd(
				request, response, modelMap, aliasValueList, aliasNameList, columns, name,paramMap);
		
	}
	
	/**
	 * 营业员提成（预提）查询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewIncentiveAccrualCalcList")
	public ModelAndView viewIncentiveAccrualCalcList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		searchMap.put("SUBSD_CD", admin.getCpnyId());
		searchMap.put("USER_NO", admin.getUserNo());
		searchMap.put("ACCRUAL_YN", "Y");
		searchMap.put("EMPNO", searchMap.get("dwz.person.empId"));
		if(!searchMap.containsKey("YEAR")){
			String SALS_MON = DateUtil.getLastMonthStr();
			searchMap.put("YEAR", SALS_MON.substring(0, 4));
			searchMap.put("MONTH", SALS_MON.substring(4, 6));
		}
		searchMap.put("SALS_MON", searchMap.get("YEAR").toString()+searchMap.get("MONTH").toString());
		List itemList = this.SalesmanIncentiveSer.getIncentiveCalcList(request, searchMap);
		int itemListCnt = this.SalesmanIncentiveSer.getIncentiveCalcListCnt(request, searchMap);
		String closeFlag = this.SalesmanIncentiveSer.getSalesInctPayClosedFlagByMonth(request, searchMap);
		modelMap.put("closeFlag", closeFlag);
		modelMap.put("searchMap", searchMap);
		modelMap.put("itemList", itemList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, itemListCnt) ;	
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "215990")) ;

		return new ModelAndView("/inct/salesman/viewIncentiveAccrualCalcList",modelMap);
	}	
	
	/**
	 * 营业员提成（预提）  列表的导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewIncentiveAccrualCalcListExcel")
	public void viewIncentiveAccrualCalcListExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{	
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		String lang=paramMap.get("interLanguage").toString();
		//设置excel header
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.DEPTNAME",lang));
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.empNo",lang));
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.empName",lang));
		aliasNameList.add(TipMessage.getTipMessage("ar.excelexport.title.month",lang));
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.variablePay",lang));
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.ratio",lang));
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.variablePay",lang)+"*"
						 +TipMessage.getTipMessage("inct.salesman.evalPoint",lang));
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.deduct",lang));
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.adjustInct",lang));
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.inctResult",lang));
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.remark",lang));
		aliasNameList.add("注册人");
		aliasNameList.add("注册时间");
		aliasNameList.add("更新人");
		aliasNameList.add("更新时间");
		//列名
		String[] columns = { "ORG_NM", "EMPNO", "EMP_NM", "INCTV_MON",
	            "VARB_INCTV_AMT", "ACHV_RAT", "BASE_PAY", "DEDUT_AMT","ADJST_AMT","TOT_INCTV_AMT","REMARK",
	            "RGST_USER","RGST_DTIME","UPDT_USER","UPDT_DTIME"};
		//提取导出数据列表
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("SUBSD_CD", admin.getCpnyId());
		paramMap.put("USER_NO", admin.getUserNo());
		paramMap.put("ACCRUAL_YN", "Y");
		paramMap.put("EMPNO", paramMap.get("dwz.person.empId"));
		if(!paramMap.containsKey("YEAR")){
			String SALS_MON = DateUtil.getLastMonthStr();
			paramMap.put("YEAR", SALS_MON.substring(0, 4));
			paramMap.put("MONTH", SALS_MON.substring(4, 6));
			paramMap.put("SALS_MON", SALS_MON);
		}else{
			paramMap.put("SALS_MON", paramMap.get("YEAR").toString()+paramMap.get("MONTH").toString());
		}
		List aliasValueList  = this.SalesmanIncentiveSer.getIncentiveCalcList(request, paramMap) ;
		String name = "salesmanIncentiveAccrualCalcList";
		this.excelUtilSer.exportExcelByNamePwd(
				request, response, modelMap, aliasValueList, aliasNameList, columns, name,paramMap);
	}
	
	/**
	 * 营业员提成 （读取变动工资）
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/callSalesmanVariablePayRead")
	@ResponseBody
	public Map callSalesmanVariablePayRead(HttpServletRequest request,HttpServletResponse response) throws Exception{
		int result = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//参数处理
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_") ;
		paramMap.put("SUBSD_CD", admin.getCpnyId());
		if(!paramMap.containsKey("YEAR")){
			String SALS_MON = DateUtil.getLastMonthStr();
			paramMap.put("YEAR", SALS_MON.substring(0, 4));
			paramMap.put("MONTH", SALS_MON.substring(4, 6));
			paramMap.put("SALS_MON", SALS_MON);
		}else{
			paramMap.put("SALS_MON", paramMap.get("YEAR").toString()+paramMap.get("MONTH").toString());
		}
		paramMap.put("UPDT_USER", admin.getEmpID());
		paramMap.put("MESSAGE", "");
		paramMap.put("CNT", 0);
		//检查工资是否关帐
		String closeFlag = this.SalesmanIncentiveSer.getSalesInctPayClosedFlagByMonth(request, paramMap);
		map.put("closeFlag", closeFlag);
		if(closeFlag.equals("Y")){
			//工资已经关帐
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("inct.message.info.payClosed.variablePayNg",request));//工资计算已经关闭,不可以读取变动工资!
		}else{
			//工资未关帐//ACCRUAL_FLAG:  Y-预提 N-正式
			result = this.SalesmanIncentiveSer.callSalesmanVariablePayRead(request, paramMap);
			if(result==1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("inct.message.info.save_success",request));//成功
				map.put("navTabId", "se0201");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("inct.message.info.save_failed",request));//失败
			}			
		}
		return map;
	}
	
	/**
	 * 营业员提成 （提成计算）
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/callSalesmanIncentiveCalc")
	@ResponseBody
	public Map callSalesmanIncentiveCalc(HttpServletRequest request,HttpServletResponse response) throws Exception{
		int result = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//参数处理
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_") ;
		paramMap.put("SUBSD_CD", admin.getCpnyId());
		if(!paramMap.containsKey("YEAR")){
			String SALS_MON = DateUtil.getLastMonthStr();
			paramMap.put("YEAR", SALS_MON.substring(0, 4));
			paramMap.put("MONTH", SALS_MON.substring(4, 6));
			paramMap.put("SALS_MON", SALS_MON);
		}else{
			paramMap.put("SALS_MON", paramMap.get("YEAR").toString()+paramMap.get("MONTH").toString());
		}
		paramMap.put("UPDT_USER", admin.getEmpID());
		paramMap.put("MESSAGE", "");
		paramMap.put("CNT", 0);
		//检查工资是否关帐
		String closeFlag = this.SalesmanIncentiveSer.getSalesInctPayClosedFlagByMonth(request, paramMap);
		map.put("closeFlag", closeFlag);
		if(closeFlag.equals("Y")){
			//工资已经关帐
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("inct.message.info.payClosed.incentiveNg",request));//工资计算已经关闭,不可以读取变动工资!
		}else{
			//工资未关帐//ACCRUAL_FLAG:  Y-预提 N-正式
			result = this.SalesmanIncentiveSer.callSalesmanIncentiveCalc(request, paramMap);
			if(result==1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("inct.message.info.save_success",request));//成功
				map.put("navTabId", "se0201");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("inct.message.info.save_failed",request));//失败
			}			
		}
		return map;
	}
	/**
	 * 营业员提成（单个社员修改）
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editIncentiveCalcItemView")
	public ModelAndView editIncentiveCalcItemView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		//参数处理
		Map searchMap = ObjectBindUtil.getRequestParamData(request, "seach_") ;
		Map resultMap = this.SalesmanIncentiveSer.getIncentiveCalcByItem(request, searchMap);		
		modelMap.put("incentiveItem", resultMap);			
		modelMap.put("searchMap", searchMap);
		return new ModelAndView(modelMap);
	}
	
	/**
	 * 营业员提成计算（员工别提成修改--保存）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editIncentiveCalcItem")
	@ResponseBody
	public Map editIncentiveCalcItem(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("SUBSD_CD", admin.getCpnyId());
		paramMap.put("UPDT_USER", admin.getEmpID());	
		paramMap.put("SALS_MON", paramMap.get("INCTV_MON"));
		String closeFlag = this.SalesmanIncentiveSer.getSalesInctPayClosedFlagByMonth(request, paramMap);
		if(closeFlag.equals("Y")){
			//工资已经关帐
			map.put("statusCode", "300");
			map.put("message", "提成计算已经关闭,不可以调整!");
		}else{
			int result = this.SalesmanIncentiveSer.editIncentiveCalcItem(request, paramMap);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"inct.message.info.save_success", request));
				map.put("navTabId", "se0101");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"inct.message.info.save_failed", request));
			}
		}
		return map;
	}
	
	/**
	 * 营业员提成 数据导入结果查询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewIncentiveCalcImpList")
	public ModelAndView viewIncentiveCalcImpList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("IMP_EMPNO", admin.getEmpID());
		paramMap.put("IMP_DATE", DateUtil.getSysdateStr().replace("-", ""));
		if(!paramMap.containsKey("YEAR")){
			String SALS_MON = DateUtil.getLastMonthStr();
			paramMap.put("YEAR", SALS_MON.substring(0, 4));
			paramMap.put("MONTH", SALS_MON.substring(4, 6));
			paramMap.put("SALS_MON", SALS_MON);
		}else{
			paramMap.put("SALS_MON", paramMap.get("YEAR").toString()+paramMap.get("MONTH").toString());
		}
		List itemList = this.SalesmanIncentiveSer.getSalesIncCalculateImportList(request, paramMap);
		int itemListCnt = this.SalesmanIncentiveSer.getSalesIncCalculateImportListCnt(request, paramMap);
		int impErrCnt   = this.SalesmanIncentiveSer.getSalesIncCalculateImportErrCnt(request, paramMap);
		String closeFlag = this.SalesmanIncentiveSer.getSalesInctPayClosedFlagByMonth(request, paramMap);
		
		modelMap.put("closeFlag", closeFlag);
		modelMap.put("itemList", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", itemListCnt);
		modelMap.put("searchMap", paramMap);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, itemListCnt) ;	
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "215989")) ;

		return new ModelAndView("/inct/salesman/viewIncentiveCalcImpList",modelMap);
	}
	/**
	 * 营业员提成  excel导入结果列表的导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewIncentiveCalcImpListExcel")
	public ModelAndView viewIncentiveCalcImpListExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("IMP_EMPNO", admin.getEmpID());
		paramMap.put("IMP_DATE", DateUtil.getSysdateStr().replace("-", ""));
		if(!paramMap.containsKey("YEAR")){
			String SALS_MON = DateUtil.getLastMonthStr();
			paramMap.put("YEAR", SALS_MON.substring(0, 4));
			paramMap.put("MONTH", SALS_MON.substring(4, 6));
			paramMap.put("SALS_MON", SALS_MON);
		}else{
			paramMap.put("SALS_MON", paramMap.get("YEAR").toString()+paramMap.get("MONTH").toString());
		}
		List salesmanInctDataList = this.SalesmanIncentiveSer.getSalesIncCalculateImportListExcel(request, paramMap);
		modelMap.put("itemList", salesmanInctDataList);
		return new ModelAndView("/inct/salesman/viewIncentiveCalcImpListExcel",modelMap);
	}
	
	/**
	 * 营业员提成 （excel导入提成数据到正式表）
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/callIncentiveDataImportResult")
	@ResponseBody
	public Map callIncentiveDataImportResult(HttpServletRequest request,HttpServletResponse response) throws Exception{
		int result = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//参数处理
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_") ;
		paramMap.put("SUBSD_CD", admin.getCpnyId());

		if(!paramMap.containsKey("YEAR")){
			String SALS_MON = DateUtil.getLastMonthStr();
			paramMap.put("YEAR", SALS_MON.substring(0, 4));
			paramMap.put("MONTH", SALS_MON.substring(4, 6));
			paramMap.put("SALS_MON", SALS_MON);
		}else{
			paramMap.put("SALS_MON", paramMap.get("YEAR").toString()+paramMap.get("MONTH").toString());
		}
		
		paramMap.put("IMP_EMPNO", admin.getEmpID());
		paramMap.put("IMP_DATE", DateUtil.getSysdateStr().replace("-", ""));
		paramMap.put("MESSAGE", "");
		paramMap.put("CNT", 0);
		//检查工资是否关帐
		String closeFlag = this.SalesmanIncentiveSer.getSalesInctPayClosedFlagByMonth(request, paramMap);
		map.put("closeFlag", closeFlag);
		if(closeFlag.equals("Y")){
			//工资已经关帐
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("inct.message.info.payClosed.incentiveNg",request));//工资计算已经关闭,不可以读取变动工资!
		}else{
			//工资未关帐
			result = this.SalesmanIncentiveSer.callSalesmanIncentiveCalcImport(request, paramMap);
			if(result==1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("inct.message.info.save_success",request));//成功
				map.put("navTabId", "se0201");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("inct.message.info.save_failed",request));//失败
			}			
		}
		return map;
	}
	
	/**
	 * 下载导入模板
	 */
	@RequestMapping(value = "/downloadIncentiveCalcTemplate")
	public void downloadIncentiveCalcTemplate(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		List aliasNameList = new ArrayList();
		aliasNameList.add("社号*");
		aliasNameList.add("月份*");
		aliasNameList.add("调整提成*");
		aliasNameList.add("备注");
		
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "CH2XXXXX");
		map.put("CELL1", "201407");
		map.put("CELL2", "100");
		map.put("CELL3", "提成调整");
		list.add(map);
		List tipList = new ArrayList();
		
		String name = "SalesInctCalcAdjuExcelImport";
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelByNameWithHeaderTip(
				request, response, modelMap, sqlContentmap, aliasNameList, null, name, tipList);
	}
	
	/**
	 * 营业员提成调整
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewIncentiveAdjustList")
	public ModelAndView viewIncentiveCalcAdjuList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		searchMap.put("SUBSD_CD", admin.getCpnyId());
		searchMap.put("USER_NO", admin.getUserNo());
		searchMap.put("EMPNO", searchMap.get("dwz.person.empId"));
		searchMap.put("REQ_EMPNO", admin.getEmpID());
		if(!searchMap.containsKey("YEAR")){
			String SALS_MON = DateUtil.getLastMonthStr();
			searchMap.put("YEAR", SALS_MON.substring(0, 4));
			searchMap.put("MONTH", SALS_MON.substring(4, 6));
		}
		searchMap.put("SALS_MON", searchMap.get("YEAR").toString()+searchMap.get("MONTH").toString());
		
		List itemList = this.SalesmanIncentiveSer.getIncentiveCalcAdjuList(request, searchMap);
		int itemListCnt = this.SalesmanIncentiveSer.getIncentiveCalcAdjuListCnt(request, searchMap);
		modelMap.put("searchMap", searchMap);
		modelMap.put("itemList", itemList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, itemListCnt) ;	
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "219934")) ;

		return new ModelAndView("/inct/salesman/viewIncentiveAdjustList",modelMap);
	}	
	
	/**
	 * 营业员提成调整  详细查询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewIncentiveCalcAdjuDtlList")
	public ModelAndView viewIncentiveCalcAdjuDtlList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		searchMap.put("SUBSD_CD", admin.getCpnyId());		
		List itemList = this.SalesmanIncentiveSer.getIncentiveCalcAdjuDtlList(request, searchMap);
		int itemListCnt = this.SalesmanIncentiveSer.getIncentiveCalcAdjuDtlListCnt(request, searchMap);
		List affirmList = this.SalesmanIncentiveSer.getAffirmorListByReqId(request);
		List checkList = this.SalesmanIncentiveSer.getCheckListByReqId(request);
		
		modelMap.put("searchMap", searchMap);
		modelMap.put("itemList", itemList);
		modelMap.put("affirmList", affirmList);
		modelMap.put("affirmListCnt", affirmList.size());
		modelMap.put("checkList", checkList);
		modelMap.put("checkListCnt", checkList == null ? 0 : checkList.size());
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, itemListCnt) ;		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "219934")) ;

		return new ModelAndView("/inct/salesman/viewIncentiveCalcAdjuDtlList",modelMap);
	}
}

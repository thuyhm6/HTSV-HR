package com.ait.inct.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
import com.ait.inct.service.SalesmanEvaluationSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * 
 * @fileName SalesmanEvaluationCtroller.java
 * @author 			@Date			@version		@Desc
 * ---------------------------------------------------------------------------------------------------------------
 * penghaixia 		2014-6-4 		1.0				initial  
 *----------------------------------------------------------------------------------------------------------------
 */
@Controller
@RequestMapping(value = "/inct/salesman")
public class SalesmanEvaluationCtroller {

	@Autowired
	private SalesmanEvaluationSer salesmanEvaluationSer;

	@Autowired
	private ToolMenuSer toolMenuSer;
	
	@Autowired
	private ExcelUtilSer excelUtilSer;

	/**
	 * 评价数据导入查询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvaluationDataImportList")
	public ModelAndView viewEvaluationDataImportList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		searchMap.put("SUBSD_CD", admin.getCpnyId());
		searchMap.put("USER_NO", admin.getUserNo());
		searchMap.put("EMPNO", searchMap.get("dwz.person.empId"));
		if(!searchMap.containsKey("YEAR")){
			searchMap.put("YEAR"	,""+getYearQuarter().get("currentYear"));
			searchMap.put("QUARTER"	,""+getYearQuarter().get("currentQuarter"));
		}
		List itemList = this.salesmanEvaluationSer.getEvaluationDataImportList(request, searchMap);
		int itemListCnt = this.salesmanEvaluationSer.getEvaluationDataImportListCnt(request, searchMap);		
		modelMap.put("searchMap", searchMap);
		modelMap.put("itemList", itemList);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, itemListCnt) ;		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "147822")) ;

		return new ModelAndView("/inct/salesman/viewEvaluationDataImportList",modelMap);
	}	
	/**
	 * 评价数据导入结果查询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvaluationDataImportResultList")
	public ModelAndView viewEvaluationDataImportResultList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		searchMap.put("SUBSD_CD", admin.getCpnyId());
		List itemList = this.salesmanEvaluationSer.getEvaluationDataImportResultList(request, searchMap);
		int impTotalCnt = this.salesmanEvaluationSer.getEvaluationDataImportResultListCnt(request, searchMap);	
		int impErrCnt   = this.salesmanEvaluationSer.getEvaluationDataImportErrCnt(request, searchMap);
		modelMap.put("MDATA", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "215992"));
		return new ModelAndView("/inct/salesman/viewEvaluationDataImportResultList",modelMap);
	}
	
	/**
	 * 评价数据导入正式表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/createEvaluationDataImportResult")
	@ResponseBody
	public int createEvaluationDataImportResult(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("IMP_EMPNO", admin.getEmpID()) ;
		paramMap.put("SUBSD_CD", admin.getCpnyId()) ;
		String result = this.salesmanEvaluationSer.importSalesEvalRAWFromExcel(request,paramMap);
		return result.equals("OK")?1:0;
	}
	
	/**
	 * 下载导入模板
	 */
	@RequestMapping(value = "/downloadSalesEvalDataImpTemplate")
	public void downloadSalesEvalDataImpTemplate(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		List aliasNameList = new ArrayList();
		aliasNameList.add("大区*");
		aliasNameList.add("年*");
		aliasNameList.add("季度*");
		aliasNameList.add("评价项目*");
		aliasNameList.add("社编*");
		aliasNameList.add("今年实绩*");
		aliasNameList.add("去年实绩");
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "256519");
		map.put("CELL1", "2014");
		map.put("CELL2", "1");
		map.put("CELL3", "AA");
		map.put("CELL4", "CH000002");
		map.put("CELL5", "10000");
		map.put("CELL6", "9000");
		list.add(map);
		
		List tipList = new ArrayList();
		//评价项目code TIP
		paramMap.put("PARENT_CODE_NO","210868");
		paramMap.put("CPNY_ID",admin.getCpnyId());
		List codeList = this.salesmanEvaluationSer.getSalesEvaluationTypeCodeList(paramMap);
        Object[] TypeList = codeList.toArray();
        String EvalHeader = "说明:";
        for (int i = 0; i < TypeList.length; i++) {
        	EvalHeader = EvalHeader + "\n"+ ((Map)TypeList[i]).get("CODE_DESC");
        }
        LinkedHashMap tipMap_Eval = new LinkedHashMap();
        tipMap_Eval.put("TIP_COLUMN", "评价项目*");
        tipMap_Eval.put("TIP_CONTENT", EvalHeader);
        tipList.add(tipMap_Eval);
        //大区code TIP
  		List payAreaCdList = this.salesmanEvaluationSer.getPayAreaCodeList(paramMap);
        TypeList = payAreaCdList.toArray();
        String PayAreaHeader = "说明:";
        for (int i = 0; i < TypeList.length; i++) {
        	PayAreaHeader = PayAreaHeader + "\n"+ ((Map)TypeList[i]).get("CODE_DESC");
        }  
        LinkedHashMap tipMap_Area = new LinkedHashMap();
        tipMap_Area.put("TIP_COLUMN", "大区*");
        tipMap_Area.put("TIP_CONTENT", PayAreaHeader);
        tipList.add(tipMap_Area);
		
		String name = "SalesEvaluationExcelImport";
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelByNameWithHeaderTip(
				request, response, modelMap, sqlContentmap, aliasNameList, null, name, tipList);
	}
	/**
	 * 评价数据导入  列表的导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEvaluationDataImportListExcel")
	public void viewEvaluationDataImportListExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String lang=searchMap.get("interLanguage").toString();
		//设置excel header
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.year",lang));//年
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.Season",lang)); //季度
		aliasNameList.add("大区CD");//大区CD
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.daqu",lang));//大区
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.empNo",lang));//社号
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.evaluationType",lang));//评价类型
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.empName",lang));//员工姓名	
		aliasNameList.add("评价项目CD");//评价项目CD
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.evaluationItemType",lang));//评价项目
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.currentYearAchieve",lang));//今年实绩
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.lastYearAchieve",lang));//去年实绩
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.updateBy",lang));//更新人
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.updateTime",lang));//更新时间
		//列名
		String[] columns = { "YYYY", "QUARTER", "PAY_AREA_CD", "PAY_AREA_NM", "EMPNO", 
				"EV_TP_NM",	"EMP_NM", "CD", "CATEGORY_NM","CURRENT_VALUE", 
				"LAST_VALUE", "UPDT_USER", "UPDT_DTIME" };
		//提取导出数据列表
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		searchMap.put("SUBSD_CD", admin.getCpnyId());
		searchMap.put("USER_NO", admin.getUserNo());
		if(!searchMap.containsKey("YEAR")){
			searchMap.put("YEAR"	,""+getYearQuarter().get("currentYear"));
			searchMap.put("QUARTER"	,""+getYearQuarter().get("currentQuarter"));
		}
		List aliasValueList  = this.salesmanEvaluationSer.getEvaluationDataImportList(request, searchMap);
		String name = "salesmanEvaluationDataList";
		this.excelUtilSer.exportExcelByNamePwd(
				request, response, modelMap, aliasValueList, aliasNameList, columns, name, searchMap);
	}
	
	/**
	 * 评价数据导入  导入的数据列表的导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEvaluationDataImportResultListExcel")
	public ModelAndView viewEvaluationDataImportResultListExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		searchMap.put("SUBSD_CD", admin.getCpnyId());
		if(!searchMap.containsKey("YEAR")){
			searchMap.put("YEAR"	,""+getYearQuarter().get("currentYear"));
			searchMap.put("QUARTER"	,""+getYearQuarter().get("currentQuarter"));
		}
		List itemList = this.salesmanEvaluationSer.getEvaluationDataImportResultListExcel(request, searchMap);		
		modelMap.put("searchMap", searchMap);
		modelMap.put("MDATA", itemList);
		return new ModelAndView("/inct/salesman/viewEvaluationDataImportResultListExcel",modelMap);
	}
	
	/**
	 * 评价项目权重设置
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEvaluationItemWeightList")
	public ModelAndView viewEvaluationItemWeightList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List itemList = this.salesmanEvaluationSer.getEvaluationItemWeightList(request,paramMap);
		int itemListCnt = this.salesmanEvaluationSer.getEvaluationItemWeightListCnt(request,paramMap);
		modelMap.put("MDATA", itemList);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, itemListCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "147784"));
		return new ModelAndView("/inct/salesman/viewEvaluationItemWeightList",modelMap);
	}
	/**
	 * 评价项目权重设置  列表的导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEvaluationItemWeightListExcel")
	public void viewEvaluationItemWeightListExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{			
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String lang=paramMap.get("interLanguage").toString();
		//设置excel header
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("sys.affirm.CodeNo",lang));//代码
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.evaluationItemType",lang)); //评价项目
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.evaluationType",lang));//评价类型
		aliasNameList.add(TipMessage.getTipMessage("sys.affirm.title.duty",lang));//职责		
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.evaluation.weight",lang));//权重
		aliasNameList.add(TipMessage.getTipMessage("sys.postManage.title.ifUsed",lang));//是否使用
		aliasNameList.add(TipMessage.getTipMessage("ar.excelexport.title.createempid",lang));//注册人
		aliasNameList.add(TipMessage.getTipMessage("ar.excelexport.title.createdate",lang));//注册时间
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.updateBy",lang));//更新人
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.updateTime",lang));//更新时间
		//列名
		String[] columns = { "CATEGORY", "CATEGORY_NM", "EV_TP_NM", "JOB_POSI_NM", "WEIGHT",
				"USE_YN", "RGST_USER","RGST_DTIME", "UPDT_USER","UPDT_DTIME" };
		//提取导出数据列表		
		List aliasValueList  = this.salesmanEvaluationSer.getEvaluationItemWeightList(request,paramMap) ;
		String name = "salesmanEvaluationItemWeightList";
		this.excelUtilSer.exportExcelByNamePwd(
				request, response, modelMap, aliasValueList, aliasNameList, columns, name,paramMap);
	}
	/**
	 * 评价项目权重设置（新增）
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addEvaluationItemWeightView")
	public ModelAndView addEvaluationItemWeightView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		modelMap.put("interCpnyID", admin.getCpnyId());
		return new ModelAndView(modelMap);
	}
	
	/**
	 * 评价项目权重设置（新增--保存）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEvaluationItemWeight")
	@ResponseBody
	public Map addEvaluationItemWeight(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("LOGIN_USER", admin.getEmpID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		int result = this.salesmanEvaluationSer.createEvaluationWeightByItem(request,paramMap);
		if (result == 1) {
			//成功
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));
			map.put("navTabId", "se0101");
		} else if (result == 2){
			//主键冲突
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.ID_conflict", request));
		} else {
			//失败
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}
	
	/**
	 * 评价项目权重设置（修改）
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editEvaluationItemWeightView")
	public ModelAndView editEvaluationItemWeightView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("evaluationItem", this.salesmanEvaluationSer.getEvaluationWeightByItem(request,paramMap));
		return new ModelAndView(modelMap);
	}
	
	/**
	 * 评价项目权重设置（修改--保存）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editEvaluationItemWeight")
	@ResponseBody
	public Map editEvaluationItemWeight(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("LOGIN_USER", admin.getEmpID()) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		int result = this.salesmanEvaluationSer.updateEvaluationWeightByItem(request, paramMap);
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
		return map;
	}
	
	/**
	 * 评价项目系数设置
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEvaluationItemRatioList")
	public ModelAndView viewEvaluationItemRatioList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);		
		List itemList = this.salesmanEvaluationSer.getSalesIncEvaluationRatioList(request,paramMap);
		int itemListCnt = this.salesmanEvaluationSer.getSalesIncEvaluationRatioListCnt(request,paramMap);
		modelMap.put("MDATA", itemList);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, itemListCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "215882"));
		return new ModelAndView("/inct/salesman/viewEvaluationItemRatioList",modelMap);
	}
	/**
	 * 评价项目系数设置  列表的导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEvaluationItemRatioListExcel")
	public void viewEvaluationItemRatioListExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{			
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String lang=paramMap.get("interLanguage").toString();
		//设置excel header
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.evaluationItemType",lang)); //评价项目
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.evaluationType",lang));//评价类型
		aliasNameList.add(TipMessage.getTipMessage("sys.affirm.title.duty",lang));//职责
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.itemRatio.leftStart",lang));//左侧区间开始值
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.itemRatio.leftEnd",lang));//左侧区间结束值
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.itemRatio.topStart",lang));//顶部区间开始值
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.itemRatio.topEnd",lang));//顶部区间结束值
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.itemRatio.Ratio",lang));//系数
		aliasNameList.add(TipMessage.getTipMessage("ar.excelexport.title.createempid",lang));//注册人
		aliasNameList.add(TipMessage.getTipMessage("ar.excelexport.title.createdate",lang));//注册时间
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.updateBy",lang));//更新人
		aliasNameList.add(TipMessage.getTipMessage("inct.salesman.updateTime",lang));//更新时间
		//列名
		String[] columns = { "CATEGORY_NM", "EV_TP_NM", "JOB_POSI_NM", "LEFT_START_VALUE","LEFT_END_VALUE", 
				"TOP_START_VALUE", "TOP_END_VALUE", "RATIO","RGST_USER","RGST_DTIME",
				"UPDT_USER","UPDT_DTIME" };
		//提取导出数据列表		
		List aliasValueList  = this.salesmanEvaluationSer.getSalesIncEvaluationRatioList(request,paramMap) ;
		String name = "salesmanEvaluationItemRatioList";
		this.excelUtilSer.exportExcelByNamePwd(
				request, response, modelMap, aliasValueList, aliasNameList, columns, name,paramMap);
	}
	/**
	 * 评价项目系数设置（新增）
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addEvaluationItemRatioView")
	public ModelAndView addEvaluationItemRatioView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		modelMap.put("interCpnyID", admin.getCpnyId());
		return new ModelAndView(modelMap);
	}
	
	/**
	 * 评价项目系数设置（新增--保存）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEvaluationItemRatio")
	@ResponseBody
	public Map addEvaluationItemRatio(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("LOGIN_USER", admin.getEmpID()) ;
		paramMap.put("MAP_SEQ", "1") ;
		int result = this.salesmanEvaluationSer.createEvaluationRatioByItem(request,paramMap);
		if (result == 1) {
			//成功
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));
			map.put("navTabId", "se0103");
		} else if (result == 2){
			//已存在
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"inct.message.info.dupRatioPeriod", request));
		} else {
			//失败
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}
	
	/**
	 * 评价项目系数设置（修改）
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editEvaluationItemRatioView")
	public ModelAndView editEvaluationItemRatioView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		modelMap.put("evaluationItem", this.salesmanEvaluationSer.getEvaluationRatioByItem(request,paramMap));
		return new ModelAndView(modelMap);
	}
	
	/**
	 * 评价项目系数设置（修改--保存）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editEvaluationItemRatio")
	@ResponseBody
	public Map editEvaluationItemRatio(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("LOGIN_USER", admin.getEmpID()) ;
		int result = this.salesmanEvaluationSer.updateEvaluationRatioByItem(request,paramMap);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"inct.message.info.save_success", request));
			map.put("navTabId", "se0103");
		} else if (result == 2){
			//已存在
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"inct.message.info.dupRatioPeriod", request));
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"inct.message.info.save_failed", request));
		}
		return map;
	}
	
	/**
	 * 营业员评价
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEvaluationInquiryList")
	public ModelAndView viewSalesIncEvaluationInquiryList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map searchMap = ObjectBindUtil.getRequestParamData(request, "seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List itemList 	= new ArrayList();
		int itemListCnt = 0;
		searchMap.put("USER_NO", admin.getUserNo());
		searchMap.put("EMPNO", searchMap.get("dwz.person.empId"));
		//默认查询汇总list
		if(!searchMap.containsKey("ResultType")){	
			searchMap.put("ResultType", "Total");
			searchMap.put("YEAR"	,""+getYearQuarter().get("currentYear"));
			searchMap.put("QUARTER"	,""+getYearQuarter().get("currentQuarter"));
		}
		if(searchMap.get("ResultType").equals("Detail")){
			itemList 	= this.salesmanEvaluationSer.getSalesIncEvaluationInquiryDetailList(request, searchMap);
			itemListCnt = this.salesmanEvaluationSer.getSalesIncEvaluationInquiryDetailListCnt(request, searchMap);
		}else{
			itemList 	= this.salesmanEvaluationSer.getSalesIncEvaluationInquiryList(request, searchMap);
			itemListCnt = this.salesmanEvaluationSer.getSalesIncEvaluationInquiryListCnt(request, searchMap);
		}
		modelMap.put("searchMap", searchMap);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("MDATA", itemList);
		modelMap.put("CLOS_FLAG", this.salesmanEvaluationSer.getSalesIncPayClosedFlag(searchMap));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, itemListCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "147779"));
		return new ModelAndView("/inct/salesman/viewEvaluationInquiryList",modelMap);
	}
	/**
	 * 营业员评价  列表的导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEvaluationInquiryListExcel")
	public void viewSalesIncEvaluationInquiryListExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String lang=searchMap.get("interLanguage").toString();
		//提取导出数据列表
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasValueList= new ArrayList();
		List aliasNameList = new ArrayList();
		searchMap.put("USER_NO", admin.getUserNo());
		String name = "salesmanEvaluationList";
		//默认查询汇总list
		if(!searchMap.containsKey("ResultType")){	
			searchMap.put("ResultType", "Total");
			searchMap.put("YEAR"	,""+getYearQuarter().get("currentYear"));
			searchMap.put("QUARTER"	,""+getYearQuarter().get("currentQuarter"));
		}
		if(searchMap.get("ResultType").equals("Detail")){
			//设置excel header	
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.year",lang));//年
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.Season",lang)); //季度
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.daqu",lang));//大区
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.evaluationType",lang));//评价类型
			aliasNameList.add(TipMessage.getTipMessage("sys.affirm.title.duty",lang));//职责
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.empNo",lang));//社号		
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.empName",lang));//员工姓名
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.evaluationItemType",lang));//评价项目
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.leftCurrent",lang));//左侧当期
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.leftLast",lang));//左侧同期
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.itemRatio.left",lang));//左侧值
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.topCurrent",lang));//顶层当期
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.topLast",lang));//顶层同期
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.itemRatio.top",lang));//顶层值
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.evaluation.weight",lang));//权重
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.itemRatio.Ratio",lang));//系数
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.totalScore",lang));//评价总分
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.bonusRatio",lang));//奖金系数
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.description",lang));//评价说明
			aliasNameList.add("创建时间");
			aliasNameList.add("创建人");
			//列名
			String[] columns = { "YYYY", "QUARTER", "PAY_AREA_NM", "EV_TP_NM", "JOB_POSI_NM",
					"EMPNO", "EMPNM", "CATEGORY_NM","LEFT_CURRENT","LEFT_LAST",
					"LEFT_VALUE","TOP_CURRENT","TOP_LAST","TOP_VALUE","WEIGHT",
					"RATIO", "EVAL_VALUE", "BONUS_RATIO", "REMARK","RGST_DTIME","RGST_USER"};
			aliasValueList 	= this.salesmanEvaluationSer.getSalesIncEvaluationInquiryDetailList(request, searchMap);
			this.excelUtilSer.exportExcelByNamePwd(
					request, response, modelMap, aliasValueList, aliasNameList, columns, name, searchMap);
		}else{
			//设置excel header			
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.year",lang));//年
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.Season",lang)); //季度
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.daqu",lang));//大区
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.evaluationType",lang));//评价类型
			aliasNameList.add(TipMessage.getTipMessage("sys.affirm.title.duty",lang));//职责
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.empNo",lang));//社号		
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.empName",lang));//员工姓名		
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.totalScore",lang));//评价总分
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.bonusRatio",lang));//奖金系数
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.description",lang));//评价说明
			aliasNameList.add("创建时间");
			aliasNameList.add("创建人");
			//列名
			String[] columns = { "YYYY", "QUARTER", "PAY_AREA_NM", "EV_TP_NM", "JOB_POSI_NM",
					"EMPNO", "EMPNM", "EVAL_VALUE", "BONUS_RATIO", "REMARK","RGST_DTIME","RGST_USER"};
			aliasValueList 	= this.salesmanEvaluationSer.getSalesIncEvaluationInquiryList(request, searchMap);
			this.excelUtilSer.exportExcelByNamePwd(
					request, response, modelMap, aliasValueList, aliasNameList, columns, name, searchMap);
		}
	}	
	
	/**
	 * 营业员个人评价结果 查询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEvaluationResultForPersonalInfo")
	public ModelAndView viewEvaluationResultForPersonalInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map searchMap = ObjectBindUtil.getRequestParamData(request, "seach_") ;
		Map<String, Object> map = new HashMap<String, Object>();
		int itemListCnt = 0;
		//默认查询汇总list
		if(!searchMap.containsKey("ResultType")){	
			searchMap.put("ResultType", "Total");
			searchMap.put("YEAR"	,""+getYearQuarter().get("currentYear"));
			searchMap.put("QUARTER"	,""+getYearQuarter().get("currentQuarter"));
			searchMap.put("EMPNO", admin.getEmpID());
			searchMap.put("EMPNM", admin.getLocalName() );
		}		
		if(searchMap.get("ResultType").equals("Detail")){
			map 	= this.salesmanEvaluationSer.getPersonalEvaluationResultDetailInfo(searchMap);
		}else{
			map 	= this.salesmanEvaluationSer.getPersonalEvaluationResultInfo(searchMap);
		}
		modelMap.put("personalEvalResult", map);
		modelMap.put("searchMap", searchMap);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, itemListCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "215884"));
		return new ModelAndView("/inct/salesman/viewEvaluationResultForPersonalInfo",modelMap);
	}
	/**
	 * 营业员个人评价结果  列表的导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEvaluationResultForPersonalInfoExcel")
	public void viewEvaluationResultForPersonalInfoExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String lang=searchMap.get("interLanguage").toString();
		String name = "salesmanPersonalEvaluationResult";
		List aliasNameList = new ArrayList();
		List aliasValueList  = new ArrayList();
		//提取导出数据列表
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();		
		//默认查询汇总list
		if(!searchMap.containsKey("ResultType")){	
			searchMap.put("ResultType", "Total");
			searchMap.put("YEAR"	,""+getYearQuarter().get("currentYear"));
			searchMap.put("QUARTER"	,""+getYearQuarter().get("currentQuarter"));
		}
		if(searchMap.get("ResultType").equals("Total")){
			//设置excel header
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.year",lang));//年
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.Season",lang)); //季度
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.daqu",lang));//大区
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.evaluationType",lang));//评价类型
			aliasNameList.add(TipMessage.getTipMessage("sys.affirm.title.duty",lang));//职责
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.empNo",lang));//社号	
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.empName",lang));//员工姓名		
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.totalScore",lang));//评价总分
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.bonusRatio",lang));//奖金系数
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.description",lang));//评价说明
			//列名
			String[] columns = { "YYYY", "QUARTER", "PAY_AREA_CD", "EV_TP_NM", "JOB_POSI_CD",
					"EMPNO", "EMPNM", "EVAL_VALUE", "BONUS_RATIO", "REMARK"};
			//数据集
			map 	= this.salesmanEvaluationSer.getPersonalEvaluationResultInfo(searchMap);
			if(map != null){ aliasValueList.add(map);}
			this.excelUtilSer.exportExcelByNamePwd(
					request, response, modelMap, aliasValueList, aliasNameList, columns, name, searchMap);
		}else{
			//设置excel header
			aliasNameList.add("年");//年
			aliasNameList.add("季度"); //季度
			aliasNameList.add("社号");//社号	
			aliasNameList.add("员工姓名");//员工姓名		
			aliasNameList.add("大区");//大区
			aliasNameList.add("组织名称");//组织名称
			aliasNameList.add("评价类型");//评价类型
			aliasNameList.add("职责");//职责
			
			aliasNameList.add("卖出实绩(A)当期");//卖出实绩(A)当期
			aliasNameList.add("卖出实绩(A)去年同期");//卖出实绩(A)去年同期
			aliasNameList.add("卖出实绩(A)目标");//卖出实绩(A)目标
			aliasNameList.add("收款(B、F)当期");//收款(B、F)当期
			aliasNameList.add("收款(B、F)去年同期");//收款(B、F)去年同期
			aliasNameList.add("收款(B、F)目标");//收款(B、F)目标
			aliasNameList.add("限界利润(C、E)当期");//限界利润(C、E)当期
			aliasNameList.add("限界利润(C、E)去年同期");//限界利润(C、E)去年同期
			aliasNameList.add("限界利润(C、E)目标");//限界利润(C、E)目标
			aliasNameList.add("MS 率(D)当期");//MS 率(D)当期
			aliasNameList.add("MS 率(D)去年同期");//MS 率(D)去年同期
			
			aliasNameList.add("High-end(H)当期");//High-end(H)当期
			aliasNameList.add("High-end(H)去年同期");//High-end(H)去年同期
			aliasNameList.add("High-end(H)目标");//High-end(H)目标
			aliasNameList.add("Credit(J)超期债权");//Credit(J)超期债权
			aliasNameList.add("Credit(J)长期债权");//Credit(J)长期债权
			aliasNameList.add("Credit(J)总债权");//Credit(J)总债权
			aliasNameList.add("New Customer(G)下部流通目标GE");//New Customer(G)下部流通目标GE
			aliasNameList.add("New Customer(G)下部流通实绩GE");//New Customer(G)下部流通实绩GE
			aliasNameList.add("New Customer(G)专卖店目标GA");//New Customer(G)专卖店目标GA
			aliasNameList.add("New Customer(G)专卖店实绩GA");//New Customer(G)专卖店实绩GA
			aliasNameList.add("New Customer(G)店中店目标GC");//New Customer(G)店中店目标GC
			aliasNameList.add("New Customer(G)店中店实绩GC");//New Customer(G)店中店实绩GC
			
			aliasNameList.add("评价总分");//评价总分
			aliasNameList.add("奖金系数");//奖金系数
			aliasNameList.add("评价说明");//评价说明
			//列名
			String[] columns = { "YYYY", "QUARTER", "EMPNO", "EMP_NM", "PAY_AREA_CD", 
					"ORG_NM", "EV_TP_NM", "JOB_POSI_NM", "A_CURRENT_VALUE", "A_LAST_VALUE", 
					"A_TARGET_VALUE", "B_CURRENT_VALUE","B_LAST_VALUE","B_TARGET_VALUE","C_CURRENT_VALUE",
					"C_LAST_VALUE", "C_TARGET_VALUE","D_CURRENT_VALUE","D_LAST_VALUE","H_CURRENT_VALUE",
					"H_LAST_VALUE",	"H_TARGET_VALUE","J_OVERDUE_VALUE","J_LOGNTERM_VALUE","J_TOTAL_VALUE",
					"GE_TARGET_VALUE", "GE_CURRENT_VALUE","GA_TARGET_VALUE","GA_CURRENT_VALUE","GC_TARGET_VALUE",
					"GC_CURRENT_VALUE",	"EVAL_VALUE","BONUS_RATIO","REMARK"};
			map 	= this.salesmanEvaluationSer.getPersonalEvaluationResultDetailInfo(searchMap);
			if(map != null){ aliasValueList.add(map);}			
			this.excelUtilSer.exportExcelByNamePwd(
					request, response, modelMap, aliasValueList, aliasNameList, columns, name, searchMap);
		}
	}	
	
	/**
	 * 营业员评价 查询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/callSalesmanEvaluation")
	@ResponseBody
	public Map callSalesmanEvaluation(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//参数处理
		Map searchMap = ObjectBindUtil.getRequestParamData(request, "seach_") ;
		if (!searchMap.containsKey("EV_TP_CD")) {
			searchMap.put("EV_TP_CD", "%");
			searchMap.put("JOB_POSI_CD", "%");
			searchMap.put("dwz.person.empId", "%");
		}else{
			if (searchMap.get("EV_TP_CD").equals("")) searchMap.put("EV_TP_CD", "%");
			if (searchMap.get("JOB_POSI_CD").equals("")) searchMap.put("JOB_POSI_CD", "%");			 
			if (searchMap.get("dwz.person.empId").equals("")) searchMap.put("dwz.person.empId", "%");
		}
		searchMap.put("EMPNO", searchMap.get("dwz.person.empId"));
		searchMap.put("SUBSD_CD", admin.getCpnyId());
		searchMap.put("UPDT_USER", admin.getEmpID());
		searchMap.put("REMARK", "CHRS");
		searchMap.put("MESSAGE", "");
		searchMap.put("CNT", 0);
		//检查工资是否关帐
		String closeFlag = this.salesmanEvaluationSer.getSalesIncPayClosedFlag(searchMap);
		if(closeFlag.equals("Y")){
			//工资已经关帐
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("inct.message.info.payClosed.evaluationNg",request));//工资计算已经关闭,不可以进行评价!
		}else{
			//工资未关帐
			map = this.salesmanEvaluationSer.callSalesmanEvaluation(searchMap);
			if((Integer)map.get("result")==1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("inct.message.info.save_success",request)
						+"共评价人数:"+map.get("CNT"));//成功
				map.put("navTabId", "se0104");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("inct.message.info.save_failed",request)
						+"错误:"+map.get("MESSAGE"));//失败
			}			
		}
		return map;
	}
	//取当前年度， 季度
	public Map getYearQuarter(){
		Map<String, Object> map = new HashMap<String, Object>();
		java.util.Date date = new java.util.Date();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
		int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        String quarter= "";
        if(month<=3){
        	quarter = "4";
        }else if(month>3 && month<=6){
        	quarter = "1";
        }else if(month>6 && month<=9){
        	quarter = "2";
        }else if(month>9 && month<=12){
        	quarter = "3";
        }
        if(month<=3){year = year -1; }
        map.put("currentYear", year);
        map.put("currentQuarter", quarter);
		return map;
	}
	
	/**
	 * 营业员评价信息查询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEvaluationInfoInquiryList")
	public ModelAndView viewEvaluationInfoInquiryList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map searchMap = ObjectBindUtil.getRequestParamData(request, "seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List itemList 	= new ArrayList();
		int itemListCnt = 0;
		searchMap.put("USER_NO", admin.getUserNo());
		searchMap.put("EMPNO", searchMap.get("dwz.person.empId"));
		//默认查询汇总list
		if(!searchMap.containsKey("ResultType")){	
			searchMap.put("ResultType", "Total");
			searchMap.put("YEAR"	,""+getYearQuarter().get("currentYear"));
			searchMap.put("QUARTER"	,""+getYearQuarter().get("currentQuarter"));
		}
		if(searchMap.get("ResultType").equals("Detail")){
			itemList 	= this.salesmanEvaluationSer.getSalesIncEvaluationInquiryDetailList(request, searchMap);
			itemListCnt = this.salesmanEvaluationSer.getSalesIncEvaluationInquiryDetailListCnt(request, searchMap);
		}else{
			itemList 	= this.salesmanEvaluationSer.getSalesIncEvaluationInquiryList(request, searchMap);
			itemListCnt = this.salesmanEvaluationSer.getSalesIncEvaluationInquiryListCnt(request, searchMap);
		}
		modelMap.put("searchMap", searchMap);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put("MDATA", itemList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, itemListCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "217907"));
		return new ModelAndView("/inct/salesman/viewEvaluationInfoInquiryList",modelMap);
	}
	/**
	 * 营业员评价信息查询  列表的导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEvaluationInfoInquiryListExcel")
	public void viewEvaluationInfoInquiryListExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String lang=searchMap.get("interLanguage").toString();
		//提取导出数据列表
		List aliasValueList= new ArrayList();	
		List aliasNameList = new ArrayList();
		searchMap.put("USER_NO", admin.getUserNo());
		String name = "salesmanEvaluationInfoList";
		//默认查询汇总list
		if(!searchMap.containsKey("ResultType")){	
			searchMap.put("ResultType", "Total");
			searchMap.put("YEAR"	,""+getYearQuarter().get("currentYear"));
			searchMap.put("QUARTER"	,""+getYearQuarter().get("currentQuarter"));
		}
		if(searchMap.get("ResultType").equals("Detail")){
			//设置excel header
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.year",lang));//年
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.Season",lang)); //季度
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.daqu",lang));//大区
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.evaluationType",lang));//评价类型
			aliasNameList.add(TipMessage.getTipMessage("sys.affirm.title.duty",lang));//职责
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.empNo",lang));//社号		
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.empName",lang));//员工姓名
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.evaluationItemType",lang));//评价项目
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.leftCurrent",lang));//左侧当期
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.leftLast",lang));//左侧同期
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.itemRatio.left",lang));//左侧值
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.topCurrent",lang));//顶层当期
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.topLast",lang));//顶层同期
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.itemRatio.top",lang));//顶层值
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.evaluation.weight",lang));//权重
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.itemRatio.Ratio",lang));//系数
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.totalScore",lang));//评价总分
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.bonusRatio",lang));//奖金系数
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.description",lang));//评价说明
			aliasNameList.add("创建时间");
			aliasNameList.add("创建人");
			//列名
			String[] columns = { "YYYY", "QUARTER", "PAY_AREA_NM", "EV_TP_NM", "JOB_POSI_NM",
					"EMPNO", "EMPNM", "CATEGORY_NM","LEFT_CURRENT","LEFT_LAST",
					"LEFT_VALUE","TOP_CURRENT","TOP_LAST","TOP_VALUE","WEIGHT",
					"RATIO", "EVAL_VALUE", "BONUS_RATIO", "REMARK","RGST_DTIME","RGST_USER"};
			aliasValueList 	= this.salesmanEvaluationSer.getSalesIncEvaluationInquiryDetailList(request, searchMap);
			this.excelUtilSer.exportExcelByNamePwd(
					request, response, modelMap, aliasValueList, aliasNameList, columns, name, searchMap);
		}else{
			//设置excel header
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.year",lang));//年
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.Season",lang)); //季度
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.daqu",lang));//大区
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.evaluationType",lang));//评价类型
			aliasNameList.add(TipMessage.getTipMessage("sys.affirm.title.duty",lang));//职责
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.empNo",lang));//社号		
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.empName",lang));//员工姓名		
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.totalScore",lang));//评价总分
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.bonusRatio",lang));//奖金系数
			aliasNameList.add(TipMessage.getTipMessage("inct.salesman.eval.description",lang));//评价说明
			aliasNameList.add("创建时间");
			aliasNameList.add("创建人");
			//列名
			String[] columns = { "YYYY", "QUARTER", "PAY_AREA_NM", "EV_TP_NM", "JOB_POSI_NM",
					"EMPNO", "EMPNM", "EVAL_VALUE", "BONUS_RATIO", "REMARK","RGST_DTIME","RGST_USER"};
			aliasValueList 	= this.salesmanEvaluationSer.getSalesIncEvaluationInquiryList(request, searchMap);
			this.excelUtilSer.exportExcelByNamePwd(
					request, response, modelMap, aliasValueList, aliasNameList, columns, name, searchMap);
		}
	}
	
	/**
	 * 评价结果明细系数调整
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editEvaluationItemRatioForAdjustView")
	public ModelAndView editEvaluationItemRatioForAdjustView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("USER_NO", admin.getUserNo());
		modelMap.put("evaluationItem", this.salesmanEvaluationSer.getEvaluationRatioByItemForAdjust(request, paramMap));
		return new ModelAndView(modelMap);
	}
	
	/**
	 * 评价结果明细系数调整（修改--保存）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editEvaluationItemRatioForAdjust")
	@ResponseBody
	public Map editEvaluationItemRatioForAdjust(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("UPDT_USER", admin.getEmpID()) ;
		paramMap.put("SUBSD_CD", admin.getCpnyId()) ;
		
		//检查工资是否关帐
		String closeFlag = this.salesmanEvaluationSer.getSalesIncPayClosedFlag(paramMap);
		if(closeFlag.equals("Y")){
			//工资已经关帐
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("inct.message.info.payClosed.evaluationNg",request));//工资计算已经关闭,不可以进行评价!
		}else{
			int result = this.salesmanEvaluationSer.updateEvaluationRatioByItemForAdjust(request,paramMap);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"inct.message.info.save_success", request));
				map.put("navTabId", "se0104");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"inct.message.info.save_failed", request));
			}
		}
		return map;
	}
}

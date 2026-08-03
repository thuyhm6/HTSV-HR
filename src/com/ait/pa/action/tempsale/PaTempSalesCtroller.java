package com.ait.pa.action.tempsale;

import java.util.ArrayList;
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

import com.ait.ar.service.CycleSer;
import com.ait.ess.service.InfoApplySer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.pa.service.tempsale.PaTempSalesSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.CommonException;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * 临促工资管理
 * 
 * @author weizhengchen
 * @date 2014-07-03
 * 
 */
@Controller
@RequestMapping(value = "/pa/tempsale")
public class PaTempSalesCtroller {
	Logger logger = Logger.getLogger(PaTempSalesCtroller.class);

	@Autowired
	private EmpInfoSer empInfoSer;
	@Autowired
	private PaTempSalesSer paTempSalesSer;
	@Autowired
	private ExcelUtilSer excelUtilSer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private CycleSer cycleSer;
	
	@Autowired
	private InfoApplySer infoApplySer;
	
	/**
	 * 临促工信息查询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempSale")
	public ModelAndView viewTempSaleList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
        //AdminBean  admin = SessionUtil.getLoginUserFromSession(request);
		List paTempSalesList = this.paTempSalesSer.getTempSalesList(request,"N");
		int paTempSalesCnt = this.paTempSalesSer.getTempSalesCnt(request,"N");
		
		modelMap.put("paTempSalesList", paTempSalesList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempSalesCnt);
		modelMap.put("branchList", this.paTempSalesSer.getBranchList(request));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "125089"));
		return new ModelAndView("/pa/tempsale/viewTempSale", modelMap);
	}
	
	/**
	 * 跳转到临促工资人员详情页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempSaleEmpInfoList")
	public ModelAndView viewTempSaleEmpInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		LinkedHashMap tempSalInfo = this.paTempSalesSer.getTempSalesEmpInfoByEventId(request);
		if(tempSalInfo != null && "N".equals(tempSalInfo.get("ACCRUAL_FLAG"))){
			List paTempSalesEmpInfoList = this.paTempSalesSer.getTempSalesEmpInfoList(request);
			int paTempSalesEmpInfoCnt = this.paTempSalesSer.getTempSalesEmpInfoCnt(request);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempSalesEmpInfoCnt);
			modelMap.put("paTempSalesEmpInfoList", paTempSalesEmpInfoList);
		}else if(tempSalInfo != null && "CONFIRM_N".equals(tempSalInfo.get("ACCRUAL_FLAG"))){
			request.setAttribute("PA_MONTH", tempSalInfo.get("PAY_DATE"));
			modelMap.put("paTempSalesAccuralInfoList", this.paTempSalesSer.getTempSalesSendList(request,"N"));
			modelMap.put("paTempSalesSum", this.paTempSalesSer.getTempSalesSum(request,"N"));
		}else if(tempSalInfo != null && "CONFIRM_Y".equals(tempSalInfo.get("ACCRUAL_FLAG"))){
			request.setAttribute("PA_MONTH", tempSalInfo.get("PAY_DATE"));
			modelMap.put("paTempSalesAccuralInfoList", this.paTempSalesSer.getTempSalesSendList(request,"Y"));
			modelMap.put("paTempSalesSum", this.paTempSalesSer.getTempSalesSum(request,"Y"));
		}
		
		List affirmList = this.paTempSalesSer.getAffirmorListByEventId(request);

		List checkList = this.paTempSalesSer.getCheckListByEventId(request);
		modelMap.put("checkListCnt", checkList == null ? 0 : checkList.size());
		modelMap.put("checkList", checkList);
		
		modelMap.put("affirmList", affirmList);
		modelMap.put("affirmListCnt", affirmList.size());
		modelMap.put("EVENT_ID", request.getParameter("EVENT_ID"));
		modelMap.put("SUBMIT_STATUS", request.getParameter("SUBMIT_STATUS"));
		modelMap.put("tempSalInfo", tempSalInfo);
		return new ModelAndView("/pa/tempsale/viewTempSaleEmpInfoList", modelMap);
	}
	
	/**
	 * 跳转到临促工资添加页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddPaTempSales")
	public ModelAndView viewAddPaTempSales(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("branchList", this.paTempSalesSer.getBranchList(request));
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		modelMap.put("DEPTNO", admin.getDeptNo());
		modelMap.put("PERSON_ID", admin.getPersonId());
		modelMap.put("defaultCpny", admin.getCpnyId());
		String SALS_MON = DateUtil.getLastMonthStr();
		modelMap.put("YEAR", SALS_MON.substring(0, 4));
		modelMap.put("MONTH", SALS_MON.substring(4, 6));
		modelMap.put("EVENT_ID", paTempSalesSer.getPaTempSalesSeq(request));
		modelMap.put("grade", empInfoSer.getCodeList("3538", request));
		return new ModelAndView("/pa/tempsale/viewAddPaTempSales", modelMap);
	}

	/**
	 * 添加临促工资
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaTempSales")
	@ResponseBody
	public Map<String, Object> addPaTempSales(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.paTempSalesSer.addPaTempSales(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"liang.alert.message.ess.trans.Successful_operation", request));// 保存成功
			map.put("navTabId", "pa0701");
		} else {
			map.put("statusCode", "300");
			if(result == -1){
				map.put("message", "支社长不存在，请先设置支社长。");// 保存失败
			}else{
				map.put("message", "操作失败。");// 保存失败
			}
		}
		return map;
	}

	/**
	 * 跳转到临促工资修改页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewUpdatePaTempSales")
	public ModelAndView viewUpdatePaTempSales(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		modelMap.put("tempSalInfo",this.paTempSalesSer.getTempSalesEmpInfoByEventId(request));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("branchList", this.paTempSalesSer.getBranchList(request));
		return new ModelAndView("/pa/tempsale/viewUpdatePaTempSales", modelMap);
	}

	/**
	 * 临促工资修改
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePaTempSales")
	@ResponseBody
	public Map updatePaTempSales(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		String flag = request.getParameter("FLAG");
		int paTempSalesEmpInfoCnt = this.paTempSalesSer.getTempSalesEmpInfoCnt(request);
		if(paTempSalesEmpInfoCnt == 0 && "1".equals(flag)){
			map.put("statusCode", "300");
			map.put("message", "该促销人数为零，不能提交，只可以保存。请先添加完促销人员信息后再进行提交。");// 保存失败
		}else{
			int result = this.paTempSalesSer.updatePaTempSales(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"liang.alert.message.ess.trans.Successful_operation", request));// 保存成功
				map.put("navTabId", "pa0701");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"liang.alert.message.ess.trans.operation_failure", request));// 保存失败
			}
		}
		return map;
	}
	
	/**
	 * 批量提交未提交的临促工资项目
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitTempSalary")
	@ResponseBody
	public Map<String, Object> submitTempSalary(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		String flag = request.getParameter("FLAG");
		String accural = request.getParameter("accural");
		int paTempSalesEmpInfoCnt = 1;
		if(accural == null || "".equals(accural)){
			paTempSalesEmpInfoCnt = this.paTempSalesSer.checkTempSalesCount(request);
		}
		if(paTempSalesEmpInfoCnt == 0 && "1".equals(flag)){
			map.put("statusCode", "300");
			map.put("message", "请先添加人员信息后，再进行提交。");// 保存失败
		}else{
			int result = this.paTempSalesSer.submitTempSalary(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"liang.alert.message.ess.trans.Successful_operation", request));// 保存成功
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"liang.alert.message.ess.trans.operation_failure", request));// 保存失败
			}
		}
		return map;
	}
	
	/**
	 * 临促模板下载
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downloadExcelTemplate")
	public void downloadExcelTemplate(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = paTempSalesSer.getTemplateInfo(request, aliasNameList, list , mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet2(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
		
	}
	
	/**
	 * 临促模板下载
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downloadExcelTemplateByExcelData")
	public void downloadExcelTemplateByExcelData(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = paTempSalesSer.getTemplateInfoByExcelData(request, aliasNameList, list , mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet2(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
		
	}
	
	/**
	 * 跳转到临促工资添加页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddPaTempSalesEmpInfo")
	public ModelAndView viewAddPaTempSalesEmpInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("EVENT_ID", request.getParameter("EVENT_ID"));
		modelMap.put("grade", empInfoSer.getCodeList("3538", request));
		return new ModelAndView("/pa/tempsale/viewAddPaTempSalesEmpInfo", modelMap);
	}

	/**
	 * 添加临促工资
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaTempSalesEmpInfo")
	@ResponseBody
	public Map<String, Object> addPaTempSalesEmpInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.paTempSalesSer.addPaTempSalesEmpInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"liang.alert.message.ess.trans.Successful_operation", request));// 保存成功
			map.put("navTabId", "pa0701_EMPINFO");
			map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"liang.alert.message.ess.trans.operation_failure", request));// 保存失败
		}
		return map;
	}

	/**
	 * 跳转到临促工资修改页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewUpdatePaTempSalesEmpInfo")
	public ModelAndView viewUpdatePaTempSalesEmpInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("INFO_NO", request.getParameter("INFO_NO"));
		modelMap.put("EVENT_ID", request.getParameter("EVENT_ID"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("empInfo", this.paTempSalesSer.getTempSalesEmpInfoByInfoNo(request));
		return new ModelAndView("/pa/tempsale/viewUpdatePaTempSalesEmpInfo", modelMap);
	}

	/**
	 * 临促工资修改
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePaTempSalesEmpInfo")
	@ResponseBody
	public Map updatePaTempSalesEmpInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.paTempSalesSer.updatePaTempSalesEmpInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"liang.alert.message.ess.trans.Successful_operation", request));// 保存成功
			map.put("navTabId", "pa0701_EMPINFO");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"liang.alert.message.ess.trans.operation_failure", request));// 保存失败
		}
		return map;
	}
	
	/**
	 * 删除临促工资
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePaTempSalesEmpInfo")
	@ResponseBody
	public Map deletePaTempSalesEmpInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.paTempSalesSer.deletePaTempSalesEmpInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"liang.alert.message.ess.trans.Successful_operation", request));// 保存成功
			map.put("navTabId", "pa0701_EMPINFO");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"liang.alert.message.ess.trans.operation_failure", request));// 保存失败
		}
		return map;
	}
	
	/**
	 * 获取需要裁决的临促信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempSaleAffirmList")
	public ModelAndView viewTempSaleAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paTempSalesList = this.paTempSalesSer.getTempSalesAffirmList(request,"N");
		int paTempSalesCnt = this.paTempSalesSer.getTempSalesAffirmCnt(request,"N");

		modelMap.put("paTempSalesList", paTempSalesList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempSalesCnt);
		return new ModelAndView("/pa/tempsale/viewTempSaleAffirmList", modelMap);
	}
	
	/**
	 * 获取需要check的临促信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempSaleCheckList")
	public ModelAndView viewTempSaleCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paTempSalesList = this.paTempSalesSer.getTempSalesCheckList(request,"N");
		int paTempSalesCnt = this.paTempSalesSer.getTempSalesCheckCnt(request,"N");

		modelMap.put("paTempSalesList", paTempSalesList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempSalesCnt);
		return new ModelAndView("/pa/tempsale/viewTempSaleCheckList", modelMap);
	}
	
	/**
	 * 临促工信息查询(决裁)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempSaleAccrualAffirm")
	public ModelAndView viewTempSaleAccrualAffirm(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		String personId = admin.getPersonId();
		List affirmList = this.paTempSalesSer.getAffirmorListByEventId(request);

		List checkList = this.paTempSalesSer.getCheckListByEventId(request);
		//裁决or check标识
		String affirmOrCheck = request.getParameter("affirmOrCheck");
		//查找当前裁决信息编号（第一条未裁决的信息）
		String affirm_no = "";
		String check_no = "";
		int dept_level = 0;
		if("1".equals(affirmOrCheck)){
			for(int i=0; i < affirmList.size(); i++){
				LinkedHashMap paramMap = (LinkedHashMap)affirmList.get(i);
				if("0".equals(paramMap.get("AFFIRM_FLAG").toString())){
					if(personId.equals(paramMap.get("AFFIRMOR_ID").toString())){
						affirm_no = paramMap.get("ESS_AFFIRM_NO").toString();
						dept_level = i + 1;
					}
					break;
				}
			}
		}else{
			//查找当前需要check信息编号（第一条未check的信息）
			for(int i=0; i < checkList.size(); i++){
				LinkedHashMap paramMap = (LinkedHashMap)checkList.get(i);
				if("0".equals(paramMap.get("CHECK_FLAG").toString()) && personId.equals(paramMap.get("CHECKOR_ID").toString())){
					check_no = paramMap.get("ESS_CHECK_NO").toString();
					break;
				}
			}
		}
		modelMap.put("affirm_no", affirm_no);
		modelMap.put("check_no", check_no);
		modelMap.put("dept_level", dept_level);
		modelMap.put("personId", admin.getPersonId());
		modelMap.put("affirmList", affirmList);
		modelMap.put("affirmOrCheck", affirmOrCheck);
		modelMap.put("checkList", checkList);
		modelMap.put("checkListCnt", checkList == null ? 0 : checkList.size());
		modelMap.put("affirmorListCnt", affirmList == null ? 0 : affirmList.size());
		modelMap.put("EVENT_ID", request.getParameter("EVENT_ID"));
		modelMap.put("accrualFlag", request.getParameter("accrualFlag"));
		List paTempSalesAccuralInfoList = this.paTempSalesSer.getTempSalesAccuralInfoList(request);
		modelMap.put("eventInfo", this.paTempSalesSer.getTempSalesInfoByEventId(request));
		modelMap.put("paTempSalesAccuralInfoList", paTempSalesAccuralInfoList);

		return new ModelAndView("/pa/tempsale/viewTempSaleAccrualAffirm", modelMap);
	}
	/**
	 * 获取需要裁决的临促信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempSaleAccrualAffirmList")
	public ModelAndView viewTempSaleAccrualAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paTempSalesList = this.paTempSalesSer.getTempSalesAffirmList(request,"Y");
		int paTempSalesCnt = this.paTempSalesSer.getTempSalesAffirmCnt(request,"Y");

		modelMap.put("paTempSalesList", paTempSalesList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempSalesCnt);
		return new ModelAndView("/pa/tempsale/viewTempSaleAccrualAffirmList", modelMap);
	}
	
	/**
	 * 获取需要check的临促信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempSaleAccrualCheckList")
	public ModelAndView viewTempSaleAccrualCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paTempSalesList = this.paTempSalesSer.getTempSalesCheckList(request,"Y");
		int paTempSalesCnt = this.paTempSalesSer.getTempSalesCheckCnt(request,"Y");

		modelMap.put("paTempSalesList", paTempSalesList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempSalesCnt);
		return new ModelAndView("/pa/tempsale/viewTempSaleAccrualCheckList", modelMap);
	}
	/**
	 * 临促工信息查询(决裁)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempSaleAffirm")
	public ModelAndView viewTempSaleAffirm2List(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		LinkedHashMap tempSalInfo = this.paTempSalesSer.getTempSalesEmpInfoByEventId(request);
		if(tempSalInfo != null && "N".equals(tempSalInfo.get("ACCRUAL_FLAG"))){
			List paTempSalesEmpInfoList = this.paTempSalesSer.getTempSalesEmpInfoList(request);
			int paTempSalesEmpInfoCnt = this.paTempSalesSer.getTempSalesEmpInfoCnt(request);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempSalesEmpInfoCnt);
			modelMap.put("paTempSalesEmpInfoList", paTempSalesEmpInfoList);
		}else if(tempSalInfo != null && "CONFIRM_N".equals(tempSalInfo.get("ACCRUAL_FLAG"))){
			request.setAttribute("PA_MONTH", tempSalInfo.get("PAY_DATE"));
			modelMap.put("paTempSalesAccuralInfoList", this.paTempSalesSer.getTempSalesSendList(request,"N"));
			modelMap.put("paTempSalesSum", this.paTempSalesSer.getTempSalesSum(request,"N"));
		}else if(tempSalInfo != null && "CONFIRM_Y".equals(tempSalInfo.get("ACCRUAL_FLAG"))){
			request.setAttribute("PA_MONTH", tempSalInfo.get("PAY_DATE"));
			modelMap.put("paTempSalesAccuralInfoList", this.paTempSalesSer.getTempSalesSendList(request,"Y"));
			modelMap.put("paTempSalesSum", this.paTempSalesSer.getTempSalesSum(request,"Y"));
		}
		
		String personId = admin.getPersonId();
		List affirmList = this.paTempSalesSer.getAffirmorListByEventId(request);

		List checkList = this.paTempSalesSer.getCheckListByEventId(request);
		//裁决or check标识
		String affirmOrCheck = request.getParameter("affirmOrCheck");
		//查找当前裁决信息编号（第一条未裁决的信息）
		String affirm_no = "";
		String check_no = "";
		int dept_level = 0;
		if("1".equals(affirmOrCheck)){
			for(int i=0; i < affirmList.size(); i++){
				LinkedHashMap paramMap = (LinkedHashMap)affirmList.get(i);
				if("0".equals(paramMap.get("AFFIRM_FLAG").toString())){
					affirm_no = paramMap.get("ESS_AFFIRM_NO").toString();
					dept_level = i + 1;
					break;
				}
			}
		}else{
			//查找当前需要check信息编号（第一条未check的信息）
			for(int i=0; i < checkList.size(); i++){
				LinkedHashMap paramMap = (LinkedHashMap)checkList.get(i);
				if("0".equals(paramMap.get("CHECK_FLAG").toString()) && personId.equals(paramMap.get("CHECKOR_ID").toString())){
					check_no = paramMap.get("ESS_CHECK_NO").toString();
					break;
				}
			}
		}
		modelMap.put("affirm_no", affirm_no);
		modelMap.put("check_no", check_no);
		modelMap.put("dept_level", dept_level);
		modelMap.put("personId", admin.getPersonId());
		modelMap.put("affirmList", affirmList);
		modelMap.put("affirmOrCheck", affirmOrCheck);
		modelMap.put("checkList", checkList);
		modelMap.put("checkListCnt", checkList == null ? 0 : checkList.size());
		modelMap.put("affirmorListCnt", affirmList == null ? 0 : affirmList.size());
		modelMap.put("EVENT_ID", request.getParameter("EVENT_ID"));
		modelMap.put("tempSalInfo", tempSalInfo);

		return new ModelAndView("/pa/tempsale/viewTempSaleAffirm", modelMap);
	}
	/**
	 * 删除临促工人员信息
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @author weizhengchen@ait.net.cn 
	* @date 2013-10-14 下午4:46:40 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/affirmTempSale")
	@ResponseBody
	public Map affirmTempSale (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();
		int errorInt=this.paTempSalesSer.deletePaTempSalesEmpInfo(request);
		
		if(errorInt==1){
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));//保存成功
		}else{
			jo.put("statusCode", "300");
			jo.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));//保存失败
		}
		return jo;
	}
	/**
	 * 跳转到添加Check人页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddCheckPaTempSales")
	public ModelAndView viewAddCheckPaTempSales(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("AFFIRM_NO", request.getParameter("AFFIRM_NO"));
		modelMap.put("APPLY_NO", request.getParameter("APPLY_NO"));
		return new ModelAndView("/pa/tempsale/viewAddCheckPaTempSales", modelMap);
	}
	
	/**
	 * 添加Check人
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @author weizhengchen@ait.net.cn 
	* @date 2013-10-14 下午4:46:40 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addCheckPaTempSales")
	@ResponseBody
	public Map addCheckPaTempSales (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();
		int errorInt=this.paTempSalesSer.addCheckPaTempSales(request);
		
		if(errorInt==1){
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));//保存成功
			jo.put("navTabId", "pa0901_affirm");
		}else{
			jo.put("statusCode", "300");
			jo.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));//保存失败
		}
		return jo;
	}
	
	/**
	 * 决裁
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @author weizhengchen@ait.net.cn 
	* @date 2013-10-14 下午4:46:40 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/affirmPaTempSales")
	@ResponseBody
	public Map affirmPaTempSales (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		
		String returnMsg = "";
		Map<String, Object> jo = new HashMap<String, Object>();

		int errorInt= 0;
		//裁决or check标识
		String affirmOrCheck = request.getParameter("affirmOrCheck");
		if("1".equals(affirmOrCheck)){
			errorInt=this.paTempSalesSer.affirmPaTempSales(request);
			if(request.getParameter("accrualFlag") != null && "Y".equals(request.getParameter("accrualFlag"))){
				jo.put("navTabId", "pa0903");
			}else if(request.getParameter("accrualFlag") != null && "N".equals(request.getParameter("accrualFlag"))){
				jo.put("navTabId", "pa0901");
			}else {
				jo.put("navTabId", "pa0907");
			}
			returnMsg="审批";
		}else{
			errorInt=this.paTempSalesSer.checkPaTempSales(request);
			if(request.getParameter("accrualFlag") != null && "Y".equals(request.getParameter("accrualFlag"))){
				jo.put("navTabId", "pa0904");
			}else if(request.getParameter("accrualFlag") != null && "N".equals(request.getParameter("accrualFlag"))){
				jo.put("navTabId", "pa0902");
			}else {
				jo.put("navTabId", "pa0908");
			}
			returnMsg="check";
		}
		if(errorInt==1){
			jo.put("statusCode", "200");
			jo.put("message", returnMsg + "成功");//保存成功
			jo.put("callbackType", "closeCurrent");
		}else{
			jo.put("statusCode", "300");
			jo.put("message", returnMsg + "失败");//保存失败
		}
		return jo;
	}
	
	/**
	 * 临促工资导入临时保存画面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportExcelTempSalesDataList")
	public ModelAndView viewImportExcelTempSalesDataList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paTempSalesTempList = this.paTempSalesSer.getTempSalesTempList(request);
		int paTempSalesTempCnt = this.paTempSalesSer.getTempSalesTempCnt(request , "T");
		int errorCnt = this.paTempSalesSer.getTempSalesTempCnt(request , "E");
		
		modelMap.put("paTempSalesTempList", paTempSalesTempList);
		modelMap.put("paTempSalesTempCnt", paTempSalesTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", paTempSalesTempCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempSalesTempCnt);
		return new ModelAndView("/pa/tempsale/viewImportExcelTempSalesDataList", modelMap);
	}
	
	/**
	 * 临促工资预提导入临时保存画面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportExcelTempSalesAccrualDataList")
	public ModelAndView viewImportExcelTempSalesAccrualDataList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paTempSalesTempList = this.paTempSalesSer.getTempSalesTempList(request);
		int paTempSalesTempCnt = this.paTempSalesSer.getTempSalesTempCnt(request , "T");
		int errorCnt = this.paTempSalesSer.getTempSalesTempCnt(request , "E");
		
		modelMap.put("paTempSalesTempList", paTempSalesTempList);
		modelMap.put("paTempSalesTempCnt", paTempSalesTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", paTempSalesTempCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempSalesTempCnt);
		return new ModelAndView("/pa/tempsale/viewImportExcelTempSalesAccrualDataList", modelMap);
	}
	
	/**
	 * 临促工资excel信息提交
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitImportExcelTempSalesData")
	@ResponseBody
	public Map submitImportExcelTempSalesData (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();

		String msg= this.paTempSalesSer.submitImportExcelTempSalesData(request);
		if("OK".equals(msg)){
			jo.put("statusCode", "200");
			jo.put("message", "提交成功");//保存成功

			if(request.getParameter("accrual") == null){
				jo.put("navTabId", "pa0701");
			}else{
				jo.put("navTabId", "pa0711");
			}
			jo.put("callbackType", "closeCurrent");
		}else{
			jo.put("statusCode", "200");
			jo.put("message", "提交失败");//保存失败
		}
		return jo;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitImportExcelTempZuiDiGongZiBiaoZhunFeiCuXiaoYuanData")
	@ResponseBody
	public Map submitImportExcelTempZuiDiGongZiBiaoZhunFeiCuXiaoYuanData (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();

		//如果有错误的数据就不返回OK
		String msg= this.paTempSalesSer.submitImportExcelTempZuiDiGongZiBiaoZhunFeiCuXiaoYuanData(request);
		if("OK".equals(msg)){
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));//保存成功
			jo.put("navTabId", "pa0807");
			jo.put("callbackType", "closeCurrent");
		}else{
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));//保存失败
		}
		return jo;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitImportExcelTempZuiDiGongZiBiaoZhunCuXiaoYuanData")
	@ResponseBody
	public Map submitImportExcelTempZuiDiGongZiBiaoZhunCuXiaoYuanData (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();

		//如果有错误的数据就不返回OK
		String msg= this.paTempSalesSer.submitImportExcelTempZuiDiGongZiBiaoZhunCuXiaoYuanData(request);
		if("OK".equals(msg)){
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));//保存成功
			jo.put("navTabId", "pa0808");
			jo.put("callbackType", "closeCurrent");
		}else{
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));//保存失败
		}
		return jo;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitImportExcelTempPqdGuanLiData")
	@ResponseBody
	public Map submitImportExcelTempPqdGuanLiData (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();

		//如果有错误的数据就不返回OK
		String msg= this.paTempSalesSer.submitImportExcelTempPqdGuanLiData(request);
		if("OK".equals(msg)){
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));//保存成功
			jo.put("navTabId", "pa0801");
			jo.put("callbackType", "closeCurrent");
		}else{
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));//保存失败
		}
		return jo;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitImportExcelTempPqdJtbzData")
	@ResponseBody
	public Map submitImportExcelTempPqdJtbzData (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();

		//如果有错误的数据就不返回OK
		String msg= this.paTempSalesSer.submitImportExcelTempPqdJtbzData(request);
		if("OK".equals(msg)){
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));//保存成功
			jo.put("navTabId", "pa0802");
			jo.put("callbackType", "closeCurrent");
		}else{
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));//保存失败
		}
		return jo;
	}
	
	/**
	 * 临促工资人员导入临时保存画面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportExcelTempSalesEmpDataList")
	public ModelAndView viewImportExcelTempSalesEmpDataList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paTempSalesEmpTempList = this.paTempSalesSer.getTempSalesTempEmpList(request);
		int paTempSalesEmpTempCnt = this.paTempSalesSer.getTempSalesEmpTempCnt(request , "T");
		int errorCnt = this.paTempSalesSer.getTempSalesEmpTempCnt(request , "E");
		
		modelMap.put("paTempSalesEmpTempList", paTempSalesEmpTempList);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", paTempSalesEmpTempCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempSalesEmpTempCnt);
		return new ModelAndView("/pa/tempsale/viewImportExcelTempSalesEmpDataList", modelMap);
	}
	
	/**
	 * 临促工资人员excel信息提交
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitImportExcelTempSalesEmpData")
	@ResponseBody
	public Map submitImportExcelTempSalesEmpData (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();

		String msg= this.paTempSalesSer.submitImportExcelTempSalesEmpData(request);
		if("OK".equals(msg)){
			jo.put("statusCode", "200");
			jo.put("message", "提交成功");//保存成功
			jo.put("navTabId", "pa0701_EMPINFO");
			jo.put("callbackType", "closeCurrent");
		}else{
			jo.put("statusCode", "200");
			jo.put("message", "提交失败");//保存失败
		}
		return jo;
	}
	
	/**
	 * 获取门店信息
	 * 
	 * @param request
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getSpmsShopList")
	@ResponseBody
	public Map getSpmsShopList(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List list = this.paTempSalesSer.getSpmsShopList(request);
		if (list != null && list.size() > 0) {
			map.put("shopList", list);
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}
	
	/**
	 * 获取共同社编信息
	 * 
	 * @param request
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getCommonEmpId")
	@ResponseBody
	public Map getCommonEmpId(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		LinkedHashMap map1 = this.paTempSalesSer.getCommonEmpId(request);
		if (map1 != null ) {
			map.put("commonEmp", map1);
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}
	
	/**
	 * 临促工资预提
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempSaleAccrualList")
	public ModelAndView viewTempSaleAccrualList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paTempSalesList = this.paTempSalesSer.getTempSalesList(request,"Y");
		int paTempSalesCnt = this.paTempSalesSer.getTempSalesCnt(request,"Y");

		modelMap.put("paTempSalesList", paTempSalesList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempSalesCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "125089"));
		return new ModelAndView("/pa/tempsale/viewTempSaleAccrualList", modelMap);
	}
	
	/**
	 * 跳转到预提工资决裁详情页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempSaleAccrualAffirmInfoList")
	public ModelAndView viewTempSaleAccrualAffirmInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paTempSalesAccuralInfoList = this.paTempSalesSer.getTempSalesAccuralInfoList(request);
		
		List affirmList = this.paTempSalesSer.getAffirmorListByEventId(request);

		List checkList = this.paTempSalesSer.getCheckListByEventId(request);
		modelMap.put("eventInfo", this.paTempSalesSer.getTempSalesInfoByEventId(request));
		modelMap.put("checkListCnt", checkList == null ? 0 : checkList.size());
		modelMap.put("checkList", checkList);
		modelMap.put("affirmList", affirmList);
		modelMap.put("affirmListCnt", affirmList.size());
		modelMap.put("paTempSalesAccuralInfoList", paTempSalesAccuralInfoList);
		return new ModelAndView("/pa/tempsale/viewTempSaleAccrualAffirmInfoList", modelMap);
	}

	/**
	 * 跳转到预提工资决裁详情页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempSaleAccrualAffirmInfoList1")
	public ModelAndView viewTempSaleAccrualAffirmInfo1List(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paTempSalesAccuralInfoList = this.paTempSalesSer.getTempSalesAccuralInfoList(request);
		
		List affirmList = this.paTempSalesSer.getAffirmorListByEventId(request);

		List checkList = this.paTempSalesSer.getCheckListByEventId(request);
		modelMap.put("eventInfo", this.paTempSalesSer.getTempSalesInfoByEventId(request));
		modelMap.put("checkListCnt", checkList == null ? 0 : checkList.size());
		modelMap.put("checkList", checkList);
		modelMap.put("affirmList", affirmList);
		modelMap.put("personInfo", infoApplySer.getPersonalInfo(request));
		modelMap.put("affirmListCnt", affirmList.size());
		modelMap.put("EVENT_ID", request.getParameter("EVENT_ID"));
		modelMap.put("paTempSalesAccuralInfoList", paTempSalesAccuralInfoList);
		return new ModelAndView("/pa/tempsale/viewTempSaleAccrualAffirmInfoList", modelMap);
	}
	
	/**
	 * 临促工信息查询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempSaleEmp")
	public ModelAndView viewTempSaleEmpList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
        String seach_FIRST_FLAG = request.getParameter("seach_FIRST_FLAG");
        if(seach_FIRST_FLAG != null && !"".equals(seach_FIRST_FLAG)){
    		List paTempSalesEmpList = this.paTempSalesSer.getTempSalesEmpList(request);
    		int paTempSalesEmpCnt = this.paTempSalesSer.getTempSalesEmpCnt(request);
    		modelMap.put("paTempSalesEmpList", paTempSalesEmpList);
    		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempSalesEmpCnt);
        }

		modelMap.put("branchList", this.paTempSalesSer.getBranchListHR(request));
		
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("toolbarInfo",  request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "219939")) ;
		return new ModelAndView("/pa/tempsale/viewTempSaleEmp", modelMap);
	}
	

	/**
	 * 跳转到临促工资修改页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaTempSalesEmpInfoUpdate")
	public ModelAndView viewPaTempSalesEmpInfoUpdate(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("branchList", this.paTempSalesSer.getBranchListHR(request));
		modelMap.put("empInfo", this.paTempSalesSer.getTempSalesEmpInfoByIdCard(request));
		return new ModelAndView("/pa/tempsale/viewUpdatePaTempSalesEmpInfo", modelMap);
	}
	
	/**
	 * 临促人员信息修改
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePaTempSalesEmp")
	@ResponseBody
	public Map updatePaTempSalesEmp(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.paTempSalesSer.updatePaTempSalesEmp(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message","保存成功");// 保存成功
			map.put("navTabId", "pa0714");
		} else {
			map.put("statusCode", "300");
			map.put("message", "保存失败");// 保存失败
		}
		return map;
	}
	
	/**
	 * 临促信息汇总修改
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewTempSaleSummary")
	public ModelAndView viewTempSaleSummaryList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String YEAR = request.getParameter("seach_YEAR");
		if(YEAR != null){
			List paTempSalesSummaryList = this.paTempSalesSer.getTempSalesSummaryList(request);
			int paTempSalesSummaryCnt = this.paTempSalesSer.getTempSalesSummaryCnt(request);
			modelMap.put("paTempSalesSummaryList", paTempSalesSummaryList);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempSalesSummaryCnt);
		}else{
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
		}

		//默认为汇总
		String type = request.getParameter("seach_TYPE");
		if(type == null || "".equals(type.toString())){
			modelMap.put("TYPE", "summary");
		}
		modelMap.put("defaultCpny", admin.getCpnyId());
		return new ModelAndView("/pa/tempsale/viewTempSaleSummary", modelMap);
	}
	
	/**
	 * 临促预提信息汇总修改
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewTempSaleAccrualSummary")
	public ModelAndView viewTempSaleAccrualSummaryList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String YEAR = request.getParameter("seach_YEAR");
		if(YEAR != null){
			List paTempSalesSummaryList = this.paTempSalesSer.getTempSalesSendList(request,"Y");
			int paTempSalesSummaryCnt = this.paTempSalesSer.getTempSalesSendCnt(request,"Y");
			modelMap.put("paTempSalesSummaryList", paTempSalesSummaryList);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempSalesSummaryCnt);
		}else{
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
		}
		modelMap.put("defaultCpny", admin.getCpnyId());
		return new ModelAndView("/pa/tempsale/viewTempSaleAccrualSummary", modelMap);
	}
	
	/**
	 * 临促汇总
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/tempSaleSummary")
	@ResponseBody
	public Map tempSaleSummary(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		String result = this.paTempSalesSer.viewTempSaleSummary(request);
		if ("OK".equals(result)) {
			map.put("statusCode", "200");
			map.put("message","计算成功");// 保存成功
			//map.put("navTabId", "pa0715");
		} else {
			map.put("statusCode", "300");
			map.put("message", result);// 保存失败
		}
		return map;
	}
	

	/**
	 * 临促汇总结果导出并加密
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewTempSaleSummaryInfoExcel")
	public void viewTempSaleSummaryInfoExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String type = request.getParameter("TYPE");
		List aliasValueList = this.paTempSalesSer.getTempSalesSummaryList(request);
		List aliasNameList = new ArrayList();
		if("summary".equals(type)){
			//提取导出数据列表
			aliasNameList.add("姓名");
			aliasNameList.add("工资月");
			aliasNameList.add("身份证号");
			aliasNameList.add("大区");
			aliasNameList.add("支社");
			aliasNameList.add("产品类型");
			aliasNameList.add("银行账号");
			aliasNameList.add("开户行");
			aliasNameList.add("应发金额");
			aliasNameList.add("税金");
			aliasNameList.add("实发金额");
			aliasNameList.add("工作天数");
			String[] columns = {"LOCAL_NAME","PA_MONTH","ID_CARD","PAY_AREA_NAME","BRANCH_NAME","PROD_NAME","BANK_NO",
					"BANK_NAME","TOTAL_PAY","TAX_PAY",
					"NET_PAY","WORK_DAYS"};
			
			String name = "tempSaleSummaryInfo";
			this.excelUtilSer.exportExcelByNamePwd(request,response,modelMap,aliasValueList,aliasNameList,columns,name,searchMap);
		}else{
			//提取导出数据列表
			aliasNameList.add("姓名");
			aliasNameList.add("工资月");
			aliasNameList.add("身份证号");
			aliasNameList.add("大区");
			aliasNameList.add("支社");
			aliasNameList.add("产品类型");
			aliasNameList.add("应发金额");
			aliasNameList.add("工作天数");
			String[] columns = {"LOCAL_NAME","PA_MONTH","ID_CARD","PAY_AREA_NAME",
					"BRANCH_NAME","PROD_NAME","TOTAL_PAY","WORK_DAYS"};
			
			String name = "tempSaleSDetailInfo";
			this.excelUtilSer.exportExcelByNamePwd(request,response,modelMap,aliasValueList,aliasNameList,columns,name,searchMap);
			
		}
	}
	
	/**
	 * 添加临促工资
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAffirmorInfo")
	@ResponseBody
	public Map<String, Object> getAffirmorInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		List list = this.paTempSalesSer.getAffirmorList(request);
		if (list != null && list.size() > 0) {
			map.put("affirmList", list);
			map.put("affirmListCnt", list.size());
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"liang.alert.message.ess.trans.Successful_operation", request));// 保存成功
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"liang.alert.message.ess.trans.operation_failure", request));// 保存失败
		}
		return map;
	}

	

	/**
	 * 临促人员信息修改
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewTempSaleSendList")
	public ModelAndView viewTempSaleSendList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String YEAR = request.getParameter("seach_YEAR");
		if(YEAR != null){
			List paTempSalesSendList = this.paTempSalesSer.getTempSalesSendList(request,"N");
			int paTempSalesSendCnt = this.paTempSalesSer.getTempSalesSendCnt(request,"N");
			modelMap.put("paTempSalesSendList", paTempSalesSendList);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempSalesSendCnt);
		}else{
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
		}
		modelMap.put("defaultCpny", admin.getCpnyId());
		return new ModelAndView("/pa/tempsale/viewTempSaleSendList", modelMap);
	}
	
	/**
	 * 临促人员信息修改
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewTempSaleAccrualSendList")
	public ModelAndView viewTempSaleAccrualSendList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String YEAR = request.getParameter("seach_YEAR");
		if(YEAR != null){
			List paTempSalesSendList = this.paTempSalesSer.getTempSalesSendList(request,"Y");
			int paTempSalesSendCnt = this.paTempSalesSer.getTempSalesSendCnt(request,"Y");
			modelMap.put("paTempSalesSendList", paTempSalesSendList);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempSalesSendCnt);
		}else{
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
		}
		modelMap.put("defaultCpny", admin.getCpnyId());
		return new ModelAndView("/pa/tempsale/viewTempSaleAccrualSendList", modelMap);
	}

	/**
	 * 临促传送财务
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/tempSaleSend")
	@ResponseBody
	public Map tempSaleSend(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		String ACCRUAL_YN = request.getParameter("ACCRUAL_YN");

		String result = this.paTempSalesSer.viewTempSaleSend(request);
		if ("OK".equals(result)) {
			map.put("statusCode", "200");
			map.put("message","传送成功");// 保存成功
			/*if("Y".equals(ACCRUAL_YN)){
				map.put("navTabId", "pa0906");
			}else{
				map.put("navTabId", "pa0905");
			}*/
		} else {
			map.put("statusCode", "300");
			map.put("message", result);// 保存失败
		}
		return map;
	}
	

	/**
	 * 临促汇总结果导出并加密
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewTempSaleSendInfoExcel")
	public void viewTempSaleSendInfoExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String ACCRUAL_YN = request.getParameter("ACCRUAL_YN");
		List aliasValueList = this.paTempSalesSer.getTempSalesSendList(request,ACCRUAL_YN);
		List aliasNameList = new ArrayList();
		if("Y".equals(ACCRUAL_YN)){
			//提取导出数据列表
			aliasNameList.add("大区");
			aliasNameList.add("支社");
			aliasNameList.add("工资月");
			aliasNameList.add("产品类型");
			aliasNameList.add("金额");
			String[] columns = {"PAY_AREA_NAME","BRANCH_NAME","PA_MONTH","PROD_NAME",
					"TOTAL_PAY"};
				
			String name = "tempSaleAccrualSendInfo";
			this.excelUtilSer.exportExcelByNamePwd(request,response,modelMap,aliasValueList,aliasNameList,columns,name,searchMap);
		}else{
			//提取导出数据列表
			aliasNameList.add("大区");
			aliasNameList.add("支社");
			aliasNameList.add("工资月");
			aliasNameList.add("产品类型");
			aliasNameList.add("应发金额");
			aliasNameList.add("税金");
			aliasNameList.add("实发金额");
			String[] columns = {"PAY_AREA_NAME","BRANCH_NAME","PA_MONTH","PROD_NAME",
					"TOTAL_PAY","TAX_PAY","NET_PAY"};
				
			String name = "tempSaleSendInfo";
			this.excelUtilSer.exportExcelByNamePwd(request,response,modelMap,aliasValueList,aliasNameList,columns,name,searchMap);
		}
	}

	/**
	 * 大区担当确认页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempSaleConfirmList")
	public ModelAndView viewTempSaleConfirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paTempSalesList = this.paTempSalesSer.getTempSalesAffirmList(request,"CONFIRM_");
		int paTempSalesCnt = this.paTempSalesSer.getTempSalesAffirmCnt(request,"CONFIRM_");

		modelMap.put("paTempSalesList", paTempSalesList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempSalesCnt);
		return new ModelAndView("/pa/tempsale/viewTempSaleConfirmList", modelMap);
	}
	

	/**
	 * 获取需要check的临促信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempSaleConfirmCheckList")
	public ModelAndView viewTempSaleConfirmCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paTempSalesList = this.paTempSalesSer.getTempSalesCheckList(request,"CONFIRM_");
		int paTempSalesCnt = this.paTempSalesSer.getTempSalesCheckCnt(request,"CONFIRM_");

		modelMap.put("paTempSalesList", paTempSalesList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempSalesCnt);
		return new ModelAndView("/pa/tempsale/viewTempSaleConfirmCheckList", modelMap);
	}
	/**
	 * 确认，传送数据给大区担当确认
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempSaleConfirm")
	@ResponseBody
	public Map<String, Object>  viewTempSaleConfirm(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		String ACCRUAL_YN = request.getParameter("ACCRUAL_YN");

		String result = this.paTempSalesSer.viewTempSaleConfirm(request);
		if ("OK".equals(result)) {
			map.put("statusCode", "200");
			map.put("message"," 确认成功");// 保存成功
		} else {
			map.put("statusCode", "300");
			map.put("message", result);// 保存失败
		}
		return map;
	}
	

	/**
	 * 临促工资状态查看
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempSaleState")
	public ModelAndView viewTempSaleStateList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paTempSalesState = this.paTempSalesSer.viewTempSaleState(request);

		modelMap.put("paTempSalesState", paTempSalesState);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempSalesState.size());
		return new ModelAndView("/pa/tempsale/viewTempSaleState", modelMap);
	}
	

	/**
	 * 批量加班审批者调整
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modifyAffirmorForBatchTempSale")
	@ResponseBody
	public Map modifyAffirmorForBatchTempSale(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = infoApplySer.modifyAffirmorForBatch(request);
			if (result == 1) {
				map.put("navTabId", "pa0711");
				map.put("message", "操作成功");//"保存加班申请成功!"
				map.put("statusCode", "200");
				map.put("callbackType", "closeCurrent");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "操作失败");
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * 临促汇总结果导出并加密
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewTempSaleSEmpInfoExcel")
	public void viewTempSaleSEmpInfoExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		List aliasValueList = this.paTempSalesSer.getTempSalesEmpList(request);
		List aliasNameList = new ArrayList();
		
		//提取导出数据列表
		aliasNameList.add("姓名");
		aliasNameList.add("出生日期");
		aliasNameList.add("身份证号");
		aliasNameList.add("大银行账号");
		aliasNameList.add("开户行");
		aliasNameList.add("联系方式");
		aliasNameList.add("评价等级");
		aliasNameList.add("黑名单与否");
		aliasNameList.add("开始日期");
		aliasNameList.add("所属部门");
		String[] columns = {"LOCAL_NAME","BIRTH_DATE","ID_CARD","BANK_NO",
				"BANK_NAME","CELLPHONE","EVS_GRADE_NAME","IS_BLACK_LIST",
				"START_DATE","DEPT_NAME_DISPLAY"};
			
		String name = "tempSaleSEmpInfo";
		this.excelUtilSer.exportExcelByNamePwd(request,response,modelMap,aliasValueList,aliasNameList,columns,name,searchMap);
	}
	
	/**
	 * 跳转到临促工资人员详情页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTempSaleSummaryInfoList")
	public ModelAndView viewTempSaleSummaryInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		LinkedHashMap tempSalInfo = this.paTempSalesSer.getTempSalesSummaryInfo(request);
		if(tempSalInfo != null && "N".equals(tempSalInfo.get("ACCRUAL_FLAG"))){
			List paTempSalesEmpInfoList = this.paTempSalesSer.getTempSalesEmpInfoList(request);
			int paTempSalesEmpInfoCnt = this.paTempSalesSer.getTempSalesEmpInfoCnt(request);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, paTempSalesEmpInfoCnt);
			modelMap.put("paTempSalesEmpInfoList", paTempSalesEmpInfoList);
		}else if(tempSalInfo != null && "CONFIRM_N".equals(tempSalInfo.get("ACCRUAL_FLAG"))){
			request.setAttribute("PA_MONTH", tempSalInfo.get("PAY_DATE"));
			modelMap.put("paTempSalesAccuralInfoList", this.paTempSalesSer.getTempSalesSendList(request,"N"));
			modelMap.put("paTempSalesSum", this.paTempSalesSer.getTempSalesSum(request,"N"));
		}else if(tempSalInfo != null && "CONFIRM_Y".equals(tempSalInfo.get("ACCRUAL_FLAG"))){
			request.setAttribute("PA_MONTH", tempSalInfo.get("PAY_DATE"));
			modelMap.put("paTempSalesAccuralInfoList", this.paTempSalesSer.getTempSalesSendList(request,"Y"));
			modelMap.put("paTempSalesSum", this.paTempSalesSer.getTempSalesSum(request,"Y"));
		}
		
		List affirmList = this.paTempSalesSer.getAffirmorListByEventId(StringUtil.checkNull(tempSalInfo.get("EVENT_ID")));

		List checkList = this.paTempSalesSer.getCheckListByEventId(StringUtil.checkNull(tempSalInfo.get("EVENT_ID")));
		modelMap.put("checkListCnt", checkList == null ? 0 : checkList.size());
		modelMap.put("checkList", checkList);
		
		modelMap.put("affirmList", affirmList);
		modelMap.put("affirmListCnt", affirmList.size());
		modelMap.put("EVENT_ID", request.getParameter("EVENT_ID"));
		modelMap.put("SUBMIT_STATUS", request.getParameter("SUBMIT_STATUS"));
		modelMap.put("tempSalInfo", tempSalInfo);
		return new ModelAndView("/pa/tempsale/viewTempSaleEmpInfoList", modelMap);
	}
}
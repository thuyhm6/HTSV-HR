package com.ait.pa.action.salary;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.CycleSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.pa.service.insurance.InsuranceInputItemSer;
import com.ait.pa.service.salary.PaCalculateSer;
import com.ait.pa.service.salary.PaInputItemParamSer;
import com.ait.pa.service.salary.PaInputItemSer;
import com.ait.pa.service.wagebase.PaBasicItemSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: PaInputItemCtroller.java
 * @Description:
 * @Create date: 2012-1-16 下午06:56:57
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/salary")
public class PaInputItemCtroller {
	Logger logger = Logger.getLogger(PaInputItemCtroller.class);

	@Autowired
	private PaInputItemSer paInputItemSer;
	
	@Autowired
	private ExcelUtilSer excelUtilSer;

	@Autowired
	private PaInputItemParamSer paInputItemParamSer;

	@Autowired
	private InsuranceInputItemSer insuranceInputItemSer;
	
	@Autowired
	private PaCalculateSer paCalculateSer;

	@Autowired
	private CompanySer companySer;

	@Autowired
	private ToolMenuSer toolMenuSer;

	@Autowired
	private PaBasicItemSer paBasicItemSer;
	@Autowired
	private CycleSer cycleSer;

	/**
	 * 查询所有工资输入项目数据(view Pa Input Item List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaInputItem")
	public ModelAndView viewPaInputItemList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paInputItemList = this.paInputItemSer.getPaInputItemList(request);
		int paInputItemCnt = this.paInputItemSer.getPaInputItemCnt(request);
		modelMap.put("itemList", paInputItemList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paInputItemCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2407"));

		return new ModelAndView("/pa/salary/viewPaInputItem", modelMap);
	}

	/**
	 * 跳转到添加工资输入项目页面 (show add Pa Input Item View)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPaInputItemView")
	public ModelAndView addPaInputItemView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		return new ModelAndView("/pa/salary/addPaInputItemView", modelMap);
	}

	/**
	 * 添加工资输入项目信息(add Pa Input Item Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaInputItemInfo")
	@ResponseBody
	public Map addPaInputItemInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int errorInt = this.paInputItemSer.checkAddPaInputItemInfo(request);

		if (errorInt == 0) {
			int resultNum = this.paInputItemSer.addPaInputItemInfo(request);
			if (resultNum == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));
				map.put("navTabId", "pa0201");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_fail", request));
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.ID_conflict", request));
		}
		return map;
	}

	/**
	 * 跳转到修改工资输入项目页面(update Pa Input Item View)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaInputItemView")
	public ModelAndView updatePaInputItemView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Object paInputItemInfo = this.paInputItemSer
				.getPaInputItemInfo(request);
		modelMap.put("paInputItemInfo", paInputItemInfo);

		return new ModelAndView("/pa/salary/updatePaInputItemView", modelMap);
	}

	/**
	 * 修改工资输入项目信息(update Pa Input Item Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePaInputItemInfo")
	@ResponseBody
	public Map updatePaInputItemInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int errorInt = this.paInputItemSer.checkAddPaInputItemInfo(request);
		if (errorInt == 0) {
			int result = this.paInputItemSer.updatePaInputItemInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("navTabId", "pa0201");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_fail", request));
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.ID_update_conflict", request));
		}
		return map;
	}

	/**
	 * 删除工资输入项目信息（delete Pa Input Item Info）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePaInputItemInfo")
	@ResponseBody
	public Map deletePaInputItemInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int errorInt = this.paInputItemSer.checkDeletePaInputItemInfo(request);
		if (errorInt == 0) {
			int resultNum = this.paInputItemSer.deletePaInputItemInfo(request);
			if (resultNum == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_success", request));
				map.put("navTabId", "pa0201");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_fail", request));
			}

		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_info_use", request));
		}
		return map;
	}

	/**
	 * 查询所有输入项目参数（view Pa Input Item Param）
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaInputItemParam")
	public ModelAndView viewPaInputItemParamList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paInputItemParamList = this.paInputItemParamSer
				.getPaInputItemParamList(request);
		int paInputItemParamCnt = this.paInputItemParamSer
				.getPaInputItemParamCnt(request);
//		modelMap.put("cpnyList", companySer.getCompanyItemAllList(request));
		modelMap.put("paInputItemParamList", paInputItemParamList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paInputItemParamCnt);

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CPNY_ID", admin .getCpnyId());

		return new ModelAndView("/pa/salary/viewPaInputItemParam", modelMap);
	}

	/**
	 * 跳转到添加页面（add Pa Input Item Param View）
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaInputItemParamView")
	public ModelAndView addPaInputItemParamView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		request.setAttribute("INPUT_ITEM_PARAM", "1");//输入项目参数 添加区分
		List paInputItemList = this.paInputItemSer.getPaInputItemList(request);
		modelMap.put("itemList", paInputItemList);

		List distinctFieleList = this.insuranceInputItemSer
				.getDistinctFieldList(request);
		modelMap.put("distinctFieleList", distinctFieleList);
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID") == null ? admin
				.getCpnyId() : paramMap.get("CPNY_ID").toString());

		return new ModelAndView("/pa/salary/addPaInputItemParamView", modelMap);
	}

	/**
	 * 执行添加工资输入项目参数信息(add Pa Input Item Param Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaInputItemParamInfo")
	@ResponseBody
	public Map addPaInputItemParamInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int errorInt = this.paInputItemParamSer
				.checkAddPaInputItemParamInfo(request);
		if (errorInt == 0) {
			int resultNum = this.paInputItemParamSer
					.addPaInputItemParamInfo(request);
			if (resultNum == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));
				map.put("navTabId", "pa0211");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_fail", request));
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.ID_conflict", request));
		}
		return map;
	}

	/**
	 * 跳转到修改页面(update Pa Input Item Param View)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePaInputItemParamView")
	public ModelAndView updatePaInputItemParamView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Object paInputItemParamInfo = this.paInputItemParamSer
				.getPaInputItemParamInfo(request);
		List distinctFieleList = this.insuranceInputItemSer
				.getDistinctFieldList(request);
//		modelMap.put("cpnyList", companySer.getCompanyItemList(request));
		modelMap.put("distinctFieleList", distinctFieleList);
		modelMap.put("paInputItemParamInfo", paInputItemParamInfo);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/pa/salary/updatePaInputItemParamView",
				modelMap);
	}

	/**
	 * 修改工资输入项目参数信息(update Pa Input Item Param Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePaInputItemParamInfo")
	@ResponseBody
	public Map updatePaInputItemParamInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int errorInt = this.paInputItemParamSer
				.updatePaInputItemParamInfo(request);
		if (errorInt == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("formId", "viewPaInputItemParamForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
			map.put("formId", "viewPaInputItemParamForm");
		}

		return map;
	}

	/**
	 *批量删除输入项目参数
	 * @param request
	 * @param response
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCheckPaInputItemData",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCheckPaInputItemData(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result =  0 ;
		String type = request.getParameter("viewPaInputItemDataList_pa0212");
		if ("PERSON_ID".equals(type)) 
			result = this.paInputItemParamSer.deleteCheckPaInputItemData(request) ;
		else
			result = this.paInputItemParamSer.deleteCheckPaInputItemDataOther(request) ;
		
		if (result == 1) {     
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));
			map.put("navTabId", "pa0211");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail",//删除失败
					request));
		}
		/**
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "pa0211");
		}else{	
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}	*/
		map.put("result", result);
		return map;
	}
	
	/**
	 *清除输入项目参数
	 * @param request
	 * @param response
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/clearPaInputItemDataCallback",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> clearPaInputItemDataCallback(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result =  0 ;
		String type = request.getParameter("viewPaInputItemDataList_pa0212");
		if ("PERSON_ID".equals(type)) 
			result = this.paInputItemParamSer.clearPaInputItemDataCallback(request) ;
		else
			result = this.paInputItemParamSer.clearCheckPaInputItemDataOther(request) ;
		
		if (result == 1) {     
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));
			map.put("navTabId", "pa0211");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail",//删除失败
					request));
		}
		/**
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "pa0211");
		}else{	
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}	*/
		map.put("result", result);
		return map;
	}

	/**
	 *批量删除基础项目参数
	 * @param request
	 * @param response
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCheckPaBasicItemData",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCheckPaBasicItemData(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.paInputItemParamSer.deleteCheckPaBasicItemData(request) ;
		
		if (result == 1) {     
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));
			map.put("navTabId", "pa0211");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail",//删除失败
					request));
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 *TA FSE批量删除输入项目参数
	 * @param request
	 * @param response
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCheckPaInputItemDataFSE",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCheckPaInputItemDataFSE(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.paInputItemParamSer.deleteCheckPaInputItemDataFSE(request) ;
		
		if (result == 1) {     
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));
			map.put("navTabId", "pa0211");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail",//删除失败
					request));
		}
		/**
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "pa0211");
		}else{	
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}	*/
		map.put("result", result);
		return map;
	}

	/**
	 * 删除工资输入项目参数信息（delete Pa Input Item Param Info）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePaInputItemParamInfo")
	@ResponseBody
	public Map deletePaInputItemParamInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int errorInt = this.paInputItemParamSer
				.checkDeletePaInputItemParamInfo(request);
		int errorIntM = this.paInputItemParamSer
				.checkDeletePaInputItemParamInfoSummary(request);
		if (errorInt == 0 && errorIntM == 0) {
			int resultNum = this.paInputItemParamSer
					.deletePaInputItemParamInfo(request);
			if (resultNum == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_success", request));
				map.put("navTabId", "pa0211");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_fail", request));
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_info_use", request));
		}
		return map;
	}

	/**
	 * 跳转输入项目数据主页面(view Pa Input Item Data)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaInputItemData")
	public ModelAndView viewPaInputItemData(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap
				.put("compList", this.companySer.getCompanyItemAllList(request));

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID") == null ? admin
				.getCpnyId() : paramMap.get("CPNY_ID").toString());

		request.setAttribute("FSE_FLAG", "N");
		List proList = this.paInputItemParamSer
				.getPaInputItemParamListNotPageNum(request);
		modelMap.put("proList", proList);
		modelMap.put("itemType", request.getParameter("itemType"));

		return new ModelAndView("/pa/salary/viewPaInputItemData", modelMap);
	}
	
	/**
	 * 跳转输入项目数据主页面FSE(view Pa Input Item Data)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaInputItemDataFSE")
	public ModelAndView viewPaInputItemDataFSE(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap
				.put("compList", this.companySer.getCompanyItemAllList(request));

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID") == null ? admin
				.getCpnyId() : paramMap.get("CPNY_ID").toString());

		request.setAttribute("FSE_FLAG", "Y");
		List proList = this.paInputItemParamSer
				.getPaInputItemParamListNotPageNum(request);
		modelMap.put("proList", proList);

		return new ModelAndView("/pa/salary/viewPaInputItemDataFSE", modelMap);
	}

	/**
	 * 取出所有工资输入项目数据(view Pa Input Item Data List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaInputItemDataList")
	public ModelAndView viewPaInputItemDataList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		     
		List paItemDataList = this.paInputItemSer.getPaInputItemDataListByParamNo(request);
		int paItemParamCnt = this.paInputItemSer.getPaInputItemDataListByParamNoCnt(request);
		Object paInputItemParamInfo = this.paInputItemParamSer.getPaInputItemParamDataInfo(request);

		modelMap.put("paInputItemParamInfo", paInputItemParamInfo);
		modelMap.put("paItemDataList", paItemDataList);
		modelMap.put("itemType", request.getParameter("itemType"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("seach_PARAM_NO", request.getParameter("seach_PARAM_NO"));
		modelMap.put("POST_FAMILY", request.getParameter("seach_WORKER_FLAG"));
		modelMap.put("loginName", admin.getUsername()!=null?admin.getUsername().toString():"NOIT");
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paItemParamCnt);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2568")) ;

		Calendar c = Calendar.getInstance();
		modelMap.put("paYear", request.getParameter("seach_paYear") == null ? String.valueOf(c.get(Calendar.YEAR)) : request.getParameter("seach_paYear"));
		modelMap.put("paMonth", request.getParameter("seach_paMonth") == null ? String.valueOf(c.get(Calendar.MONTH)+1) : request.getParameter("seach_paMonth"));
		String type="";
		if(request.getParameter("viewPaInputItemDataList_pa0212")!=null&&!request.getParameter("viewPaInputItemDataList_pa0212").equals("")){
			type=request.getParameter("viewPaInputItemDataList_pa0212");
			modelMap.put("type", type);
		};
		
		return new ModelAndView("/pa/salary/viewPaInputItemDataList", modelMap);
	}
	
	/**
	 * 取出所有工资输入项目数据FSE(view Pa Input Item Data List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaInputItemDataListFSE")
	public ModelAndView viewPaInputItemDataListFSE(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paItemDataList = this.paInputItemSer.getPaInputItemDataListByParamNo(request);
		int paItemParamCnt = this.paInputItemSer.getPaInputItemDataListByParamNoCnt(request);
		Object paInputItemParamInfo = this.paInputItemParamSer.getPaInputItemParamDataInfo(request);

		modelMap.put("paInputItemParamInfo", paInputItemParamInfo);
		modelMap.put("paItemDataList", paItemDataList);
		modelMap.put("PARAM_NO", request.getParameter("seach_PARAM_NO"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("loginName", admin.getUsername()!=null?admin.getUsername().toString():"NOIT");
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paItemParamCnt);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2568")) ;
		
		modelMap.put("paYear", request.getParameter("seach_paYear") == null ? null : request.getParameter("seach_paYear"));
		modelMap.put("paMonth", request.getParameter("seach_paMonth") == null ? null : request.getParameter("seach_paMonth"));
		String type="";
		if(request.getParameter("viewPaInputItemDataList_pa0219")!=null&&!request.getParameter("viewPaInputItemDataList_pa0219").equals("")){
			type=request.getParameter("viewPaInputItemDataList_pa0219");
			modelMap.put("type", type);
		}
		//因未取到值，特设置pageNum默认值为1，numPerPage默认值为10
		modelMap.put("pageNum", request.getParameter("pageNum")!=null?request.getParameter("pageNum"):"1");
		modelMap.put("numPerPage", request.getParameter("numPerPage")!=null?request.getParameter("numPerPage"):"10");
		
		return new ModelAndView("/pa/salary/viewPaInputItemDataListFSE", modelMap);
	}

	/**
	 * 跳转到添加工资输入项目数据页面(add Pa Input Item Data View)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaInputItemDataView")
	public ModelAndView addPaInputItemDataView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 取得数据项目信息
		LinkedHashMap paInputItemInfo = (LinkedHashMap) this.paInputItemParamSer
				.getPaInputItemParamDataInfo(request);
		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(paInputItemInfo
				.get("DISTINCT_FIELD"));
		String distinctField2 = ObjectUtils.toString(paInputItemInfo
				.get("DISTINCT_FIELD_2ND"));
		if (!distinctField.equals("PERSON_ID")) {
			this.paInputItemSer.createAddPaInputItemDataInfo(request);
			List distinctList = this.paInputItemSer.getPaParamDataList(request);
			modelMap.put("distinctList", distinctList);
			if (!"".equals(distinctField2)) {
				List distinctList2 = this.paInputItemSer
						.getPaParamDataTwoList(request);
				modelMap.put("distinctList2", distinctList2);
			}
		}
		Object paInputItemParamInfo = this.paInputItemParamSer
				.getPaInputItemParamDataInfo(request);
		String PARAM_NO = request.getParameter("seach_PARAM_NO");

		modelMap.put("PARAM_NO", PARAM_NO);
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));
		modelMap.put("paInputItemParamInfo", paInputItemParamInfo);
		modelMap.put("CPNY_ID", admin.getCpnyId());

		return new ModelAndView("/pa/salary/addPaInputItemDataView", modelMap);
	}
	
	/**
	 * 跳转到添加工资输入项目数据页面(add Pa Input Item Data View)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaInputItemDataViewFSE")
	public ModelAndView addPaInputItemDataViewFSE(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 取得数据项目信息
		LinkedHashMap paInputItemInfo = (LinkedHashMap) this.paInputItemParamSer
				.getPaInputItemParamDataInfo(request);
		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(paInputItemInfo
				.get("DISTINCT_FIELD"));
		String distinctField2 = ObjectUtils.toString(paInputItemInfo
				.get("DISTINCT_FIELD_2ND"));
		if (!distinctField.equals("PERSON_ID")) {
			this.paInputItemSer.createAddPaInputItemDataInfo(request);
			List distinctList = this.paInputItemSer.getPaParamDataList(request);
			modelMap.put("distinctList", distinctList);
			if (!"".equals(distinctField2)) {
				List distinctList2 = this.paInputItemSer
						.getPaParamDataTwoList(request);
				modelMap.put("distinctList2", distinctList2);
			}
		}
		Object paInputItemParamInfo = this.paInputItemParamSer
				.getPaInputItemParamDataInfo(request);
		String PARAM_NO = request.getParameter("seach_PARAM_NO");

		modelMap.put("PARAM_NO", PARAM_NO);
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));
		modelMap.put("paInputItemParamInfo", paInputItemParamInfo);
		modelMap.put("CPNY_ID", admin.getCpnyId());

		return new ModelAndView("/pa/salary/addPaInputItemDataViewFSE", modelMap);
	}

	/**
	 * 根绝EMP_ID进行模糊查询(The beautiful EMP_ID for fuzzy query)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAddPaPersonalDataList")
	public ModelAndView viewAddInsurancePersonalDataList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
        AdminBean admin = SessionUtil.getLoginUserFromSession(request);
        List getEmpTypeCodeList =   this.cycleSer.getKeeperEmpTypeCodeList(request,admin.getPersonId()) ; 
		List jobTypeGroupList =this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());
		modelMap.put("getEmpTypeCodeList", getEmpTypeCodeList);
		modelMap.put("jobTypeGroupList", jobTypeGroupList);
		modelMap.put("empList", this.paInputItemSer.getEmpIdList(request));
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.paInputItemSer
				.getEmpIdListCnt(request));

		return new ModelAndView("/pa/salary/viewAddPaPersonalDataList",
				modelMap);
	}

	/**
	 * 添加工资输入项目数据信息(add Pa Input Item Data Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaInputItemDataInfo")
	@ResponseBody
	public Map addPaInputItemDataInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Object paInputItemParamInfo = this.paInputItemParamSer.getPaInputItemParamInfo(request);
		try {
			if (((Map) paInputItemParamInfo).get("DISTINCT_FIELD").equals("PERSON_ID")) {
				int result = this.paInputItemSer.addPaInputItemDataInfo(request);
				if (result == 0) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));
//					map.put("rel", "viewPaInputItemData");
				} else if (result == 2) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("alert.message.info_exits", request));
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));
				}
			} else {
				int result = this.paInputItemSer.addPaInputItemOtherDataInfo(request);
				if (result == 0) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));
//					map.put("rel", "viewPaInputItemData");
				} else if (result == 2) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("alert.message.info_exits", request));
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));
				}
			}

		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));
		}

		return map;
	}
	
	/**
	 * 添加工资输入项目数据信息FSE(add Pa Input Item Data Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaInputItemDataInfoFSE")
	@ResponseBody
	public Map addPaInputItemDataInfoFSE(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Object paInputItemParamInfo = this.paInputItemParamSer.getPaInputItemParamInfo(request);
		try {
			if (((Map) paInputItemParamInfo).get("DISTINCT_FIELD").equals("PERSON_ID")) {
				int result = this.paInputItemSer.addPaInputItemDataInfo(request);
				if (result == 0) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));
					map.put("rel", "viewPaInputItemDataFSE");
				} else if (result == 2) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("alert.message.info_exits", request));
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));
				}
			} else {
				int result = this.paInputItemSer.addPaInputItemOtherDataInfo(request);
				if (result == 0) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));
					map.put("rel", "viewPaInputItemDataFSE");
				} else if (result == 2) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("alert.message.info_exits", request));
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));
				}
			}

		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));
		}

		return map;
	}

	/**
	 * 跳转到工资输入项目数据修改页面(update Pa Input Item Data View)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaInputItemDataView")
	public ModelAndView updatePaInputItemDataView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Object paInputItemDataInfo = this.paInputItemSer
				.getPaInputItemDataInfo(request);
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));
		modelMap.put("paInputItemDataInfo", paInputItemDataInfo);

		return new ModelAndView("/pa/salary/updatePaInputItemDataView",
				modelMap);
	}

	/**
	 * 修改工资输入项目数据信息(update Pa Input Item Data Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePaInputItemDataInfo")
	@ResponseBody
	public Map updatePaInputItemDataInfo(HttpServletRequest request,HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int errorInt = this.paInputItemSer.updatePaInputItemDataInfo(request);
			if (errorInt == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success", request));
				map.put("rel", "viewPaInputItemData");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.update_fail", request));
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail", request));
		}

		return map;

	}
	
	/**
	 * 修改工资输入项目数据信息(update Pa Input Item Data Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePaInputItemDataInfoFSE")
	@ResponseBody
	public Map updatePaInputItemDataInfoFSE(HttpServletRequest request,HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int errorInt = this.paInputItemSer.updatePaInputItemDataInfo(request);
			if (errorInt == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success", request));
				map.put("rel", "viewPaInputItemDataFSE");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.update_fail", request));
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail", request));
		}

		return map;

	}

	/**
	 * 查看工资输入项目数据个人信息（view Pa Input Item Data Person List）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaInputItemDataPersonList")
	public ModelAndView viewPaInputItemDataPersonList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		List paInputItemPersonList = this.paInputItemSer
				.getPaInputItemDataPersonList(request);

		int paInputItemPersonListCnt = this.paInputItemSer
				.getPaInputItemDataPersonListCnt(request);

		modelMap.put("PA_MONTH", request.getParameter("PA_MONTH"));
		modelMap.put("paInputItemPersonList", paInputItemPersonList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paInputItemPersonListCnt);

		return new ModelAndView("/pa/salary/viewPaInputItemDataPersonList",
				modelMap);
	}
	
	/**
	 * 查看工资输入项目数据个人信息FSE（view Pa Input Item Data Person List）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaInputItemDataPersonListFSE")
	public ModelAndView viewPaInputItemDataPersonListFSE(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		List paInputItemPersonList = this.paInputItemSer
				.getPaInputItemDataPersonList(request);

		int paInputItemPersonListCnt = this.paInputItemSer
				.getPaInputItemDataPersonListCnt(request);

		modelMap.put("PA_MONTH", request.getParameter("PA_MONTH"));
		modelMap.put("paInputItemPersonList", paInputItemPersonList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paInputItemPersonListCnt);

		return new ModelAndView("/pa/salary/viewPaInputItemDataPersonListFSE",
				modelMap);
	}

	/**
	 * 跳转个人输入项目页面（view Add Pa Personal Input List）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaPersonalInputView")
	public ModelAndView viewAddPaPersonalInputList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String cpnyID = request.getParameter("seach_CPNY_ID");
		String personID = request.getParameter("seach_PERSON_ID");

		List paInputItemPersonList = this.paInputItemSer
				.getAddPaPersonalInputList(request);

		int paInputItemPersonListCnt = this.paInputItemSer
				.getAddPaPersonalInputListCnt(request);
		List paInputItemPersonInfo = this.paInputItemSer
				.getPaInputItemDataPersonListNoPage(request);
		LinkedHashMap returnObj = new LinkedHashMap();
		if (paInputItemPersonInfo.size() > 0) {
			returnObj = (LinkedHashMap) paInputItemPersonInfo.get(0);
		}

		modelMap.put("personID", personID);
		modelMap.put("cpnyID", cpnyID);
		modelMap.put("paInputItemPersonInfo", returnObj);
		modelMap.put("paInputItemPersonList", paInputItemPersonList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paInputItemPersonListCnt);

		return new ModelAndView("/pa/salary/addPaPersonalInputView", modelMap);
	}

	/**
	 * 处理工资输入项目数据修改请求(update Pa Input Item Data Person Info)
	 * 
	 * @param List
	 * @return
	 */
	@RequestMapping(value = "/updatePaInputItemDataPersonInfo")
	@ResponseBody
	public Map<String, Object> updatePaInputItemDataPersonInfo(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (this.paInputItemSer.updatePaInputItemDataPersonInfo(request) == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success", request));
				map.put("navTabId", "pa0212");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.pa.bonus.noThisData", request));
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 处理工资输入项目数据单个删除请求(delete Pa Input Item Data Info)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePaInputItemDataInfo")
	@ResponseBody
	public Map deletePaInputItemDataInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 查看数否有该条奖金输入项目
			String type = request.getParameter("type");
			if ("1".equals(type)) {
				int errorInt = this.paInputItemSer
						.checkDeletePaInputItemDataInfoType(request);
				if (errorInt > 0) {     
					this.paInputItemSer.deletePaInputItemDataInfoType(request);
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("rel", "viewPaInputItemData");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.pa.bonus.databaseHasNoThisRecord",
							request));
				}
			} else {
				int errorInt = this.paInputItemSer
						.checkDeletePaInputItemDataInfo(request);

				if (errorInt > 0) {
					this.paInputItemSer.deletePaInputItemDataInfo(request);
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("rel", "viewPaInputItemData");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.pa.bonus.databaseHasNoThisRecord",
							request));
				}
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));
		}
		return map;
	}
	/**
	 * 处理工资输入项目数据多个删除请求(delete Pa Input Item Data Info)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePaInputItemDataView")
	@ResponseBody
	public Map delPaInputItemDataView(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 查看数否有该条奖金输入项目
			String type = request.getParameter("type");
			if ("1".equals(type)) {
				int errorInt = this.paInputItemSer
						.checkDeletePaInputItemDataInfoType(request);
				if (errorInt > 0) {     
					this.paInputItemSer.deletePaInputItemDataInfoType(request);
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("rel", "viewPaInputItemData");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.pa.bonus.databaseHasNoThisRecord",
							request));
				}
			} else {
				int errorInt = this.paInputItemSer
						.checkDeletePaInputItemDataInfo(request);

				if (errorInt > 0) {
					this.paInputItemSer.deletePaInputItemDataInfo(request);
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("rel", "viewPaInputItemData");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.pa.bonus.databaseHasNoThisRecord",
							request));
				}
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));
		}
		return map;
	}
	/**
	 * 处理工资输入项目数据单个删除请求(delete Pa Input Item Data Info)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePaInputItemDataInfoFSE")
	@ResponseBody
	public Map deletePaInputItemDataInfoFSE(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 查看数否有该条奖金输入项目
			String type = request.getParameter("type");
			if ("1".equals(type)) {
				int errorInt = this.paInputItemSer
						.checkDeletePaInputItemDataInfoType(request);
				if (errorInt > 0) {
					this.paInputItemSer.deletePaInputItemDataInfoType(request);
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("rel", "viewPaInputItemDataFSE");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.pa.bonus.databaseHasNoThisRecord",
							request));
				}
			} else {
				int errorInt = this.paInputItemSer
						.checkDeletePaInputItemDataInfo(request);

				if (errorInt > 0) {
					this.paInputItemSer.deletePaInputItemDataInfo(request);
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("rel", "viewPaInputItemDataFSE");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.pa.bonus.databaseHasNoThisRecord",
							request));
				}
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));
		}
		return map;
	}

	/**
	 * 处理 工资输入项目数据批量删除请求(delete Pa Input Item Data Batch Info)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePaInputItemDataBatchInfo")
	@ResponseBody
	public Map deletePaInputItemDataBatchInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String type = request.getParameter("type");
			if ("1".equals(type)) {
				int count = this.paInputItemSer
						.deletePaInputItemDataBatchInfoType(request);
				if (count == 0) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("rel", "viewPaInputItemData");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.pa.bonus.databaseHasNoThisRecord",
							request));
				}
			} else {
				int count = this.paInputItemSer
						.deletePaInputItemDataBatchInfo(request);
				if (count == 0) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("rel", "viewPaInputItemData");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.pa.bonus.databaseHasNoThisRecord",
							request));
				}
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));
		}
		return map;
	}

	
	@RequestMapping(value = "/viewItemBatchImport")
	public ModelAndView viewItemBatchImportList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List deptList = this.paCalculateSer.getDeptAreaList(request) ;
		modelMap.put("deptList", deptList) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("amp;ITEM_DISTINGUISH")!=null){
			paramMap.put("ITEM_DISTINGUISH",paramMap.get("amp;ITEM_DISTINGUISH"));
		}
		
		if(paramMap.get("ITEM_DISTINGUISH")!=null){
			List getItemNameList = this.insuranceInputItemSer.getItemNameListPa(request);
			modelMap.put("getItemNameList", getItemNameList);
			
			modelMap.put("PARAM_NO", paramMap.get("PARAM_NO"));
			modelMap.put("ITEM_DISTINGUISH", paramMap.get("ITEM_DISTINGUISH"));
			List insuranceItemDataList = this.insuranceInputItemSer.getItemBatchImportListPa(request);
			int insuranceItemDataListCnt = this.insuranceInputItemSer.getItemBatchImportListoCntPa(request);
			modelMap.put("insuranceItemDataList", insuranceItemDataList);
			
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, insuranceItemDataListCnt);
		}
		modelMap.put("seach_UP_FLAG", paramMap.get("UP_FLAG"));
		return new ModelAndView("/pa/salary/viewItemBatchImport", modelMap);
	}
	/** 
	* @Title: updateItemBatchDataView 
	* @Description: TODO 跳转项目修改
	* @param @param request
	* @param @param response
	* @param @param modelMap
	* @param @return
	* @param @throws Exception    
	* @return ModelAndView    
	* @throws 
	*/
	@RequestMapping(value = "/updateItemBatchDataView")
	public ModelAndView updateItemBatchDataView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("amp;ITEM_DISTINGUISH")!=null){
			paramMap.put("ITEM_DISTINGUISH",paramMap.get("amp;ITEM_DISTINGUISH"));
		}
		
		if(paramMap.get("ITEM_DISTINGUISH")!=null){
			
			modelMap.put("PARAM_NO", paramMap.get("PARAM_NO"));
			modelMap.put("ITEM_DISTINGUISH", paramMap.get("ITEM_DISTINGUISH"));
			modelMap.put("DATA_NO", paramMap.get("DATA_NO"));
			List insuranceItemDataList = this.insuranceInputItemSer.getItemBatchImportListPa(request);
			int insuranceItemDataListCnt = this.insuranceInputItemSer.getItemBatchImportListoCntPa(request);
			modelMap.put("insuranceItemDataList", insuranceItemDataList.get(0));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, insuranceItemDataListCnt);
		}
		return new ModelAndView("/pa/salary/updateItemBatchDataView", modelMap);
	}
	/** 
	* @Title: updateItemBatchData 
	* @Description: TODO 提交修改的数据
	* @param @param request
	* @param @param response
	* @param @param modelMap
	* @param @return
	* @param @throws Exception    
	* @return Map    
	* @throws 
	*/
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateItemBatchData")
	@ResponseBody
	public Map updateItemBatchData (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		String msg= this.insuranceInputItemSer.updateItemBatchData(request);
		if("OK".equals(msg)){
			map.put("statusCode", "200");
			map.put("message", "修改成功");//保存成功			
		}else{
			map.put("statusCode", "300");
			map.put("message", "修改失败");//保存失败
		}
		return map;
	}
	/** 
	* @Title: viewItemBatchImportTempList 
	* @Description: TODO 对页面导入临时信息的处理
	* @param @param request
	* @param @param response
	* @param @param modelMap
	* @param @return
	* @param @throws Exception    
	* @return ModelAndView    
	* @throws 
	*/
	@RequestMapping(value = "/viewItemBatchImportTempList")
	public ModelAndView viewItemBatchImportTempList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List deptList = this.paCalculateSer.getDeptAreaList(request);
		modelMap.put("deptList", deptList) ;
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if(paramMap.get("amp;ITEM_DISTINGUISH")!=null){
			paramMap.put("ITEM_DISTINGUISH",paramMap.get("amp;ITEM_DISTINGUISH"));
			modelMap.put("ITEM_DISTINGUISH", paramMap.get("amp;ITEM_DISTINGUISH"));
		}
		
		if(paramMap.get("ITEM_DISTINGUISH")!=null){
//			List getItemNameList = this.insuranceInputItemSer.getItemNameListPa(request);
//			modelMap.put("getItemNameList", getItemNameList);
//			
//			modelMap.put("PARAM_NO", paramMap.get("PARAM_NO"));
//			modelMap.put("ITEM_DISTINGUISH", paramMap.get("ITEM_DISTINGUISH"));
//			List insuranceItemDataList = this.insuranceInputItemSer.getItemBatchImportListPa(request);
//			int insuranceItemDataListCnt = this.insuranceInputItemSer.getItemBatchImportListoCntPa(request);
//			modelMap.put("insuranceItemDataList", insuranceItemDataList);
//			
//			modelMap.put(UiUtil.TOTAL_COUNT_NAME, insuranceItemDataListCnt);
//		}
		//this.insuranceInputItemSer.deleteErrorOldItemBatchData(request);
		List insuranceItemDataList = this.insuranceInputItemSer.getItemBatchImportDataTemp(request);
		int insuranceItemDataListCnt = this.insuranceInputItemSer.getItemBatchImportDataTempCnt(request);
		int insuranceItemDataListErrorCnt = this.insuranceInputItemSer.getItemBatchImportDataTempErrorCnt(request);
		modelMap.put("MDATA", insuranceItemDataList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, insuranceItemDataListCnt);
		modelMap.put("totalCnt", insuranceItemDataListCnt);
		modelMap.put("errCnt", insuranceItemDataListErrorCnt);
		modelMap.put("ITEM_DISTINGUISH", paramMap.get("ITEM_DISTINGUISH"));
		
		}
		return new ModelAndView("/pa/salary/viewItemBatchImportTempList", modelMap);
	}
	/** 
	* @Title: submitItemBatchData 
	* @Description: TODO 提交临时表数据，进行验证，通过的导入正式表
	* @param @param request
	* @param @param response
	* @param @param modelMap
	* @param @return
	* @param @throws Exception    
	* @return Map    
	* @throws 
	*/
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitItemBatchData")
	@ResponseBody
	public Map submitItemBatchData (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		String msg= this.insuranceInputItemSer.submitItemBatchData(request);
		if("OK".equals(msg)){
			jo.put("statusCode", "200");
			jo.put("message", "提交成功");//保存成功
			if(paramMap.get("ITEM_DISTINGUISH") == null){
				jo.put("navTabId", "hrm4533");
			}else{
				jo.put("navTabId", "pa0218");
			}
			jo.put("callbackType", "closeCurrent");
		}else{
			jo.put("statusCode", "200");
			jo.put("message", "提交失败");//保存失败
		}
		return jo;
	}
	/**
	 * 单条删除导入的批量数据申请
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delExceplImportLine")
	@ResponseBody
	public String delExceplImportLine(HttpServletRequest request) throws Exception {
		String result = "";
		boolean bol = this.insuranceInputItemSer.delExceplImportLine(request);
		if (bol) {
			result = "Y";
		} else {
			result = "N";
		}
		return result;
	}
	/**
	 * 项目区分关联项目名称
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-22 下午8:37:34 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getqufenAndNameCheng")
	@ResponseBody
	public Map getqufenAndNameCheng (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		List getItemNameList = this.insuranceInputItemSer.getItemNameListPa(request);
			
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			for(int i=0;i<getItemNameList.size();i++){
				map.put((String)((Map) getItemNameList.get(i)).get("PARAM_NO"), ((Map) getItemNameList.get(i)).get("ALIAS_NAME"));
			}
			return map;
		
	}

	/**
	 * 项目区分关联项目名称
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-22 下午8:37:34 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getqufenAndNameCheng2")
	@ResponseBody
	public Map getqufenAndNameCheng2 (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		List getItemNameList = this.insuranceInputItemSer.getItemNameListPa2(request);
			
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			for(int i=0;i<getItemNameList.size();i++){
				map.put((String)((Map) getItemNameList.get(i)).get("PARAM_NO"), ((Map) getItemNameList.get(i)).get("ALIAS_NAME"));
			}
			return map;
		
	}
	
	/**
	 * 跳转个人输入项目页面（view Add Pa Personal Input List）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaInsPersonalInputView")
	public ModelAndView viewAddPaInsPersonalInputList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String cpnyID = request.getParameter("seach_CPNY_ID");
		String personID = request.getParameter("seach_PERSON_ID");

		//社保输入项目数据
		List insuranceInputItemPersonList = new ArrayList();
		List insuranceInputItemPersonTempList = this.insuranceInputItemSer.getAddInsurancePersonalInputItemList(request);
		List insuranceInputItemPersonAllList = this.insuranceInputItemSer.getInsuranceInputItemPersonTempAllList(request);
		for(int i=0;i<insuranceInputItemPersonAllList.size();i++){
			Map map=(Map)insuranceInputItemPersonAllList.get(i);
			boolean insFlag=false;
			for(int j=0;j<insuranceInputItemPersonTempList.size();j++){
				if(map.get("PARAM_ITEM_NO").equals(((Map)insuranceInputItemPersonTempList.get(j)).get("PARAM_ITEM_NO"))){
					((Map)insuranceInputItemPersonTempList.get(j)).put("FLAG", "UPDATE");
					insuranceInputItemPersonList.add(insuranceInputItemPersonTempList.get(j));
					insFlag=true;
				}
			}
			if(!insFlag){
				((Map)insuranceInputItemPersonAllList.get(i)).put("FLAG", "INSERT");
				insuranceInputItemPersonList.add(insuranceInputItemPersonAllList.get(i));
			}
		}
		//工资输入项目数据
		List paInputItemPersonList = new ArrayList();
		List paInputItemPersonTempList = this.paInputItemSer.getAddPaPersonalInputItemList(request);
		List paInputItemPersonAllList = this.paInputItemSer.getPaInputItemPersonAllList(request);
		for(int a=0;a<paInputItemPersonAllList.size();a++){
			Map map=(Map)paInputItemPersonAllList.get(a);
			boolean inputFlag=false;
			for(int b=0;b<paInputItemPersonTempList.size();b++){
				if(map.get("PARAM_ITEM_NO").equals(((Map)paInputItemPersonTempList.get(b)).get("PARAM_ITEM_NO"))){
					((Map)paInputItemPersonTempList.get(b)).put("FLAG", "UPDATE");
					paInputItemPersonList.add(paInputItemPersonTempList.get(b));
					inputFlag=true;
				}
			}
			if(!inputFlag){
				((Map)paInputItemPersonAllList.get(a)).put("FLAG", "INSERT");
				paInputItemPersonList.add(paInputItemPersonAllList.get(a));
			}
		}
		//工资基本项目数据
		List paBasicInputItemList = new ArrayList();
		List paBasicInputItemTempList = this.paBasicItemSer.getPaBasicInputItemDataList(request);
		List paBasicInputItemAllList = this.paBasicItemSer.paBasicInputItemAllList(request);
		for(int x=0;x<paBasicInputItemAllList.size();x++){
			Map map=(Map)paBasicInputItemAllList.get(x);
			boolean basicFlag=false;
			for(int y=0;y<paBasicInputItemTempList.size();y++){
				if(map.get("ITEM_NO").equals(((Map)paBasicInputItemTempList.get(y)).get("ITEM_NO"))){
					((Map)paBasicInputItemTempList.get(y)).put("FLAG", "UPDATE");
					paBasicInputItemList.add(paBasicInputItemTempList.get(y));
					basicFlag=true;
				}
			}
			if(!basicFlag){
				((Map)paBasicInputItemAllList.get(x)).put("FLAG", "INSERT");
				paBasicInputItemList.add(paBasicInputItemAllList.get(x));
			}
		}
		List paInputItemPersonInfo = this.paInputItemSer
				.getPaInputItemDataPersonListNoPage(request);
		LinkedHashMap returnObj = new LinkedHashMap();
		if (paInputItemPersonInfo.size() > 0) {
			returnObj = (LinkedHashMap) paInputItemPersonInfo.get(0);
		}

		modelMap.put("personID", personID);
		modelMap.put("cpnyID", cpnyID);
		modelMap.put("paInputItemPersonInfo", returnObj);
		modelMap.put("paInputItemPersonList", paInputItemPersonList);
		modelMap.put("insuranceInputItemPersonList", insuranceInputItemPersonList);
		modelMap.put("paBasicInputItemList", paBasicInputItemList);
		
		return new ModelAndView("/pa/salary/addPaInsPersonalInputView", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/updatePaInsPersonalInput")
	@ResponseBody
	public Map updatePaInsPersonalInput(HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		//保存成功
		String mess = "";
		
		try {
			int basic = this.paBasicItemSer.insertOrUpdatePaBasicItemData(request);
			int input = this.paInputItemSer.insertOrUpdatePaInputItemData(request);
			int ins = this.insuranceInputItemSer.insertOrUpdateInsuranceInputItemData(request);
			if(basic == 1){
				map.put("statusCode", "200");
				map.put("navTabId", "pa0217");
				//工资基础项目保存成功
				mess += TipMessage.getTipMessage("alert.message.add_success1", request);
			}else if(basic == 2){
				map.put("statusCode", "200");
				map.put("navTabId", "pa0217");
				//工资基础项目数据存在
				mess += TipMessage.getTipMessage("alert.message.info_exits1", request);
			}else{
				map.put("statusCode", "300");
				//工资基础项目保存失败 
				mess += TipMessage.getTipMessage("alert.message.add_success2", request);
			}
			
			if(input == 1){
				map.put("statusCode", "200");
				map.put("navTabId", "pa0217");
				//工资输入项目保存成功 
				mess += TipMessage.getTipMessage("alert.message.add_success3", request);
			}else if(input == 2){
				map.put("statusCode", "200");
				map.put("navTabId", "pa0217");
				//工资输入项目数据存在
				mess += TipMessage.getTipMessage("alert.message.info_exits2", request);
			}else{
				map.put("statusCode", "300");
				//工资输入项目保存失败 
				mess += TipMessage.getTipMessage("alert.message.add_success4", request);
			}

			if(ins == 1){
				map.put("statusCode", "200");
				map.put("navTabId", "pa0217");
				//社保保存成功
				mess += TipMessage.getTipMessage("alert.message.add_success5", request);
			}else if(input == 2){
				map.put("statusCode", "200");
				map.put("navTabId", "pa0217");
				//社保数据存在
				mess += TipMessage.getTipMessage("alert.message.info_exits3", request);
			}else{
				map.put("statusCode", "300");
				//社保保存失败 
				mess += TipMessage.getTipMessage("alert.message.add_success6", request);
			}
			
			if(basic == 1 && input == 1 && ins == 1){
				//保存成功
				mess = TipMessage.getTipMessage("ar.alert.message.addempshift.success", request);
			}
//			if (
//					this.insuranceInputItemSer.insertOrUpdateInsuranceInputItemData(request) == 1
//					&&
//					this.paInputItemSer.insertOrUpdatePaInputItemData(request) == 1
//					&&
//					this.paBasicItemSer.insertOrUpdatePaBasicItemData(request) == 1
//					) {
//				map.put("statusCode", "200");
//				map.put("message", TipMessage.getTipMessage("alert.message.update_success", request));
//				map.put("navTabId", "pa0217");
//			} else {
//				map.put("statusCode", "300");
//				map.put("message", TipMessage.getTipMessage("alert.message.pa.bonus.thisDataIsExist_add_fail", request));
//			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			//保存失败
			mess = TipMessage.getTipMessage("alert.message.add_fail", request);
		}
		map.put("message", mess);
		return map;
	}
	/*------------------------------------------------------------------------------------------------
	*/
	/**
	 * 工资输入项目数据导入结果展示页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @author chenff
	 * @throws Exception
	 */
	@RequestMapping(value="/viewImportExcelTempPaParamList")
	public ModelAndView viewImportExcelTempPaParamList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List itemList = paBasicItemSer.getImportExcelTempPaParamList(request);
		int impTotalCnt = paBasicItemSer.getImportExcelTempPaParamListCnt(request);
		int impErrCnt   = paBasicItemSer.getImportExcelTempPaParamListErrCnt(request);
		modelMap.put("MDATA", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2568"));
		return new ModelAndView("/pa/salary/viewImportExcelTempPaParamList",modelMap);
	}	
	/*------------------------------------------------------------------------------------------------
	*/
	/**
	 * 工资输入项目数据导入结果展示页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @author chenff
	 * @throws Exception
	 */
	@RequestMapping(value="/viewImportExcelTempPaParamMonthList")
	public ModelAndView viewImportExcelTempPaParamMonthList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List itemList = paBasicItemSer.getImportExcelTempPaParamMonthList(request);
		int impTotalCnt = paBasicItemSer.getImportExcelTempPaParamMonthListCnt(request);
		int impErrCnt   = paBasicItemSer.getImportExcelTempPaParamMonthListErrCnt(request);
		modelMap.put("MDATA", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2568"));
		return new ModelAndView("/pa/salary/viewImportExcelTempPaParamMonthList",modelMap);
	}	
	/**
	 * 工资输入项目数据导入结果展示页面(按职级导入)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @author chenff
	 * @throws Exception
	 */
	@RequestMapping(value="/viewImportExcelTempPaParamGradeList")
	public ModelAndView viewImportExcelTempPaParamGradeList(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap) throws Exception{
		List itemList = paBasicItemSer.getImportExcelTempPaParamGradeList(request);
		int impTotalCnt = paBasicItemSer.getImportExcelTempPaParamListGradeCnt(request);
		int impErrCnt   = paBasicItemSer.getImportExcelTempPaParamListErrGradeCnt(request);
		modelMap.put("MDATA", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
								request, "2568"));
		return new ModelAndView("/pa/salary/viewImportExcelTempPaParamGradeList",modelMap);
	}	
	/**
	 * 工资输入项目数据FSE导入结果展示页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @author chenff
	 * @throws Exception
	 */
	@RequestMapping(value="/viewImportExcelTempPaParamListFSE")
	public ModelAndView viewImportExcelTempPaParamListFSE(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List itemList = paBasicItemSer.getImportExcelTempPaParamList(request);
		int impTotalCnt = paBasicItemSer.getImportExcelTempPaParamListCnt(request);
		int impErrCnt   = paBasicItemSer.getImportExcelTempPaParamListErrCnt(request);
		modelMap.put("MDATA", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "216718"));
		return new ModelAndView("/pa/salary/viewImportExcelTempPaParamListFSE",modelMap);
	}	
	/**
	 * 提交工资输入项目数据
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createImportPaParamListExcel")
	@ResponseBody
	public int createImportPaParamListExcel(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap) throws Exception{
		String result = paBasicItemSer.importPaParamExcel(request);
		return result.equals("OK")?1:0;
	}
	
	/**
	 * 提交工资输入项目数据
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createImportPaParamMonthListExcel")
	@ResponseBody
	public int createImportPaParamMonthListExcel(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap) throws Exception{
		String result = paBasicItemSer.importPaParamMonthExcel(request);
		return result.equals("OK")?1:0;
	}
	
	/**
	 * 工资输入项目数据导出临时数据
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewImportPaParamListExcel")
	public void viewImportPaParamListExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String DISTINCT_FIELD = request.getParameter("DISTINCT_FIELD");
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		List paParamItemTempList ;
		if ("POST_GRADE_NO".equals(DISTINCT_FIELD)) {
			aliasNameList.add("职级名称");
			aliasNameList.add("职级CODE");
			aliasNameList.add("开始月");
			aliasNameList.add("结束月");
			aliasNameList.add("数值");
			aliasNameList.add("备注");
			aliasNameList.add("验证结果");
			paParamItemTempList = paBasicItemSer.getImportExcelTempPaParamGradeList(request);
			for(int i=0;i<paParamItemTempList.size();i++){
				LinkedHashMap map = new LinkedHashMap();
				LinkedHashMap map1 = (LinkedHashMap)paParamItemTempList.get(i);
				map.put("CELL0", map1.get("POST_NAME"));
				map.put("CELL1", map1.get("POST_GRADE_NO"));
				map.put("CELL2", map1.get("START_MONTH"));
				map.put("CELL3", map1.get("END_MONTH"));
				map.put("CELL4", map1.get("RETURN_VALUE"));
				map.put("CELL5", map1.get("REMARK"));
				map.put("CELL6", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
				list.add(map);
			}
		}else {
			aliasNameList.add("社号");
			aliasNameList.add("员工姓名");
			aliasNameList.add("开始月");
			aliasNameList.add("结束月");
			aliasNameList.add("数值");
			aliasNameList.add("备注");
			aliasNameList.add("验证结果");
			paParamItemTempList = paBasicItemSer.getImportExcelTempPaParamList(request);
			for(int i=0;i<paParamItemTempList.size();i++){
				LinkedHashMap map = new LinkedHashMap();
				LinkedHashMap map1 = (LinkedHashMap)paParamItemTempList.get(i);
				map.put("CELL0", map1.get("EMPID"));
				map.put("CELL1", map1.get("CHINESENAME"));
				map.put("CELL2", map1.get("START_MONTH"));
				map.put("CELL3", map1.get("END_MONTH"));
				map.put("CELL4", map1.get("RETURN_VALUE"));
				map.put("CELL5", map1.get("REMARK"));
				map.put("CELL6", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
				list.add(map);
			}
		}
		
		String name = "viewPaParamListExcel";
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
	}
	
	/**
	 * 工资输入项目数据导出临时数据
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewImportPaParamMonthListExcel")
	public void viewImportPaParamMonthListExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String DISTINCT_FIELD = request.getParameter("DISTINCT_FIELD");
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		List paParamItemTempList ;
		if ("POST_GRADE_NO".equals(DISTINCT_FIELD)) {
			aliasNameList.add("职级名称");
			aliasNameList.add("职级CODE");
			aliasNameList.add("开始月");
			aliasNameList.add("结束月");
			aliasNameList.add("验证结果");
			paParamItemTempList = paBasicItemSer.getImportExcelTempPaParamGradeList(request);
			for(int i=0;i<paParamItemTempList.size();i++){
				LinkedHashMap map = new LinkedHashMap();
				LinkedHashMap map1 = (LinkedHashMap)paParamItemTempList.get(i);
				map.put("CELL0", map1.get("POST_NAME"));
				map.put("CELL1", map1.get("POST_GRADE_NO"));
				map.put("CELL2", map1.get("START_MONTH"));
				map.put("CELL3", map1.get("END_MONTH"));
				map.put("CELL4", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
				list.add(map);
			}
		}else {
			aliasNameList.add("社号");
			aliasNameList.add("员工姓名");
			aliasNameList.add("开始月");
			aliasNameList.add("结束月");
			aliasNameList.add("验证结果");
			paParamItemTempList = paBasicItemSer.getImportExcelTempPaParamList(request);
			for(int i=0;i<paParamItemTempList.size();i++){
				LinkedHashMap map = new LinkedHashMap();
				LinkedHashMap map1 = (LinkedHashMap)paParamItemTempList.get(i);
				map.put("CELL0", map1.get("EMPID"));
				map.put("CELL1", map1.get("CHINESENAME"));
				map.put("CELL2", map1.get("START_MONTH"));
				map.put("CELL3", map1.get("END_MONTH"));
				map.put("CELL4", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
				list.add(map);
			}
		}
		
		String name = "viewPaParamListExcel";
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
	}
}

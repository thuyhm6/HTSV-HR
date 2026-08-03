package com.ait.pa.action.wagebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.CycleSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.pa.service.insurance.InsuranceInputItemSer;
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
 * @fileName: PaBasicItemCtroller.java
 * @Description:
 * @Create date: 2012-1-16 下午03:25:05
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/wagebase")
public class PaBasicItemCtroller {
	Logger logger = Logger.getLogger(PaBasicItemCtroller.class);

	@Autowired
	private PaBasicItemSer paBasicItemSer;

	@Autowired
	private ExcelUtilSer excelUtilSer;
	
	@Autowired
	private InsuranceInputItemSer insuranceInputItemSer;

	@Autowired
	private CompanySer companySer;

	@Autowired
	private ToolMenuSer toolMenuSer;

	@Autowired
	private PaInputItemSer paInputItemSer;
	@Autowired
	private CycleSer cycleSer;

	/**
	 * 跳转到基础项目库首页(view Pa Basic Item)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaBasicItem")
	public ModelAndView viewPaBasicItemList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paBasicItemList = this.paBasicItemSer.getPaBasicItemList(request);
		int paBasicItemCnt = this.paBasicItemSer.getPaBasicItemCnt(request);

		modelMap.put("paBasicItemList", paBasicItemList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paBasicItemCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2526"));
		return new ModelAndView("/pa/wagebase/viewPaBasicItem", modelMap);
	}

	/**
	 * 跳转到添加基础项目库(add Pa Basic Item View)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaBasicItemView")
	public ModelAndView addPaBasicItemView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID") == null ? admin
				.getCpnyId() : paramMap.get("CPNY_ID").toString());

		return new ModelAndView("/pa/wagebase/addPaBasicItemView", modelMap);
	}

	/**
	 * 执行添加(add Pa Basic Item Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaBasicItemInfo")
	@ResponseBody
	public Map addPaBasicItemInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int errorInt = this.paBasicItemSer.checkAddPaBasicItemInfo(request);
		try {
			if (errorInt == 0) {
				int resultNum = this.paBasicItemSer.addPaBasicItemInfo(request);
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_success", request));
					map.put("navTabId", "pa0501");
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
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}

		return map;

	}

	/**
	 * 跳转到修改工资基础项目页面（update Pa Basic Item View）
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaBasicItemView")
	public ModelAndView updatePaBasicItemView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Object paBasicItemInfo = this.paBasicItemSer
				.getPaBasicItemInfo(request);

		modelMap.put("paBasicItemInfo", paBasicItemInfo);

		return new ModelAndView("/pa/wagebase/updatePaBasicItemView", modelMap);
	}

	/**
	 * 修改工资基础项目信息(update Pa Basic Item Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePaBasicItemInfo")
	@ResponseBody
	public Map updatePaBasicItemInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int errorInt = this.paBasicItemSer.checkAddPaBasicItemInfo(request);
		if (errorInt == 0) {
			int result = this.paBasicItemSer.updatePaBasicItemInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("navTabId", "pa0501");
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
	 * 删除工资基础项目信息(delete Pa Basic Item Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePaBasicItemInfo")
	@ResponseBody
	public Map deletePaBasicItemInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int errorInt = this.paBasicItemSer
					.checkDeletePaBasicItemInfo(request);

			if (errorInt == 0) {
				int resultNum = this.paBasicItemSer
						.deletePaBasicItemInfo(request);
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("navTabId", "pa0501");
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
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));
		}

		return map;
	}

	/**
	 * 跳转到基础项目参数首页(view Pa Basic Item Param)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaBasicItemParam")
	public ModelAndView viewPaBasicItemParamList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		//request.setAttribute("TABLE_NAME", "PA_HR_V");
		List paBasicItemParamList = this.paBasicItemSer
				.getPaBasicItemParamList(request);
		int paBasicItemParamCnt = this.paBasicItemSer
				.getPaBasicItemParamCnt(request);

		modelMap.put("paBasicItemParamList", paBasicItemParamList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paBasicItemParamCnt);
		//modelMap.put("cpnyList", companySer.getCompanyItemList(request));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "3279"));
		return new ModelAndView("/pa/wagebase/viewPaBasicItemParam", modelMap);
	}

	/**
	 * 跳转到添加基础项目参数页面(add Pa Basic Item Param View)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaBasicItemParamView")
	public ModelAndView addPaBasicItemParamView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		request.setAttribute("BASIC_ITEM_PARAM", "1");//工资基础项目参数添加标示
		List distinctFieleList = this.insuranceInputItemSer
				.getDistinctFieldList(request);
		modelMap.put("distinctFieleList", distinctFieleList);
		modelMap.put("paBasicItemList", this.paBasicItemSer
				.getPaBasicItemList(request));
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID") == null ? admin
				.getCpnyId() : paramMap.get("CPNY_ID").toString());

		return new ModelAndView("/pa/wagebase/addPaBasicItemParamView",
				modelMap);
	}

	/**
	 * 添加工资基础输入项目参数信息(add Pa Basic Item Param Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaBasicItemParamInfo")
	@ResponseBody
	public Map addPaInputItemParamInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int errorInt = this.paBasicItemSer
				.checkAddPaBasicItemParamInfo(request);
		if (errorInt == 0) {
			if (this.paBasicItemSer.addPaBasicItemParamInfo(request) == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));
				map.put("navTabId", "pa0504");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_fail", request));
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.pa.bonus.basicItemNoIsExist", request));
		}
		return map;
	}

	/**
	 * 跳转到修改工资基础项目参数页面 (update Pa Basic Item Param View)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePaBasicItemParamView")
	public ModelAndView updatePaBasicItemParamView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Object paBasicItemParamInfo = this.paBasicItemSer
				.getPaBasicItemParamInfo(request);
		List distinctFieleList = this.insuranceInputItemSer
				.getDistinctFieldList(request);
		modelMap.put("distinctFieleList", distinctFieleList);
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));
		modelMap.put("paBasicItemParamInfo", paBasicItemParamInfo);

		return new ModelAndView("/pa/wagebase/updatePaBasicItemParamView",
				modelMap);
	}

	/**
	 * 修改工资基础输入项目信息(update Pa Basic Item Param Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePaBasicItemParamInfo")
	@ResponseBody
	public Map updatePaBasicItemParamInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			int errorInt = this.paBasicItemSer
					.updatePaBasicItemParamInfo(request);

			if (errorInt == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("navTabId", "pa0504");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_fail", request));
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}

		return map;
	}

	/**
	 * 删除工资基础输入项目信息(delete Pa Basic Item Param Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePaBasicItemParamInfo")
	@ResponseBody
	public Map deletePaBasicItemParamInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int errorInt = this.paBasicItemSer
					.checkDeletePaBasicItemParamInfo(request);
			if (errorInt == 0) {
				int resultNum = this.paBasicItemSer
						.deletePaBasicItemParamInfo(request);
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("navTabId", "pa0504");
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
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));
		}

		return map;
	}

	/**
	 * 查看基础项目数据主页面(view Pa Basic Item Data)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/viewPaBasicItemData")
	public ModelAndView viewPaBasicItemData(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap
				.put("cpnyList", this.companySer.getCompanyItemAllList(request));

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID") == null ? admin
				.getCpnyId() : paramMap.get("CPNY_ID").toString());

		List paBasicItemParamList = this.paBasicItemSer
				.getPaBasicItemParamListNotPageNum(request);
		modelMap.put("paBasicItemParamList", paBasicItemParamList);

		return new ModelAndView("/pa/wagebase/viewPaBasicItemData", modelMap);
	}

	/**
	 * 查看工资基础项目数据集合（view Pa Basic Item Data List）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaBasicItemDataList")
	public ModelAndView viewPaBasicItemDataList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paBasicItemDataList = this.paBasicItemSer.getPaBasicItemDataList(request);
		int paBasicItemDataListCnt = this.paBasicItemSer.getPaBasicItemDataListCnt(request);
		Object paBasicItemDataInfo = this.paBasicItemSer.getPaBasicItemDataInfo(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
        
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("loginName", admin.getUsername()!=null?admin.getUsername().toString():"NOIT");
		modelMap.put("paBasicItemDataList", paBasicItemDataList);
		modelMap.put("paBasicItemDataInfo", paBasicItemDataInfo);
		modelMap.put("PARAM_NO", request.getParameter("seach_PARAM_NO"));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paBasicItemDataListCnt);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "3264")) ;

		String type="";
		if(request.getParameter("viewPaBasicItemDataList_pa0610")!=null&&!request.getParameter("viewPaBasicItemDataList_pa0610").equals("")){
			type=request.getParameter("viewPaBasicItemDataList_pa0610");
			modelMap.put("type", type);
		}
		return new ModelAndView("/pa/wagebase/viewPaBasicItemDataList",
				modelMap);
	}

	/**
	 * 修改工资基础项目数据信息（update Pa Basic Item Data Info）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePaBasicItemDataInfo")
	@ResponseBody
	public Map<String, Object> updatePaBasicItemDataInfo(
			HttpServletRequest request,HttpServletResponse response) throws Exception {
	
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int errorInt = this.paBasicItemSer
					.updatePaBasicItemDataInfo(request);
			if (errorInt == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("rel", "viewPaBasicItemData");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_fail", request));
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		
		return map;
	}

	/**
	 * 获取工资基础项目数据信息（get Add Pa Basic Item Data List）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAddPaBasicItemDataList")
	@ResponseBody
	public Map getAddPaBasicItemDataList(HttpServletRequest request)
			throws Exception {

		List addPaBasicItemDataList = this.paBasicItemSer
				.getAddPaBasicItemDataList(request);

		Map model = new HashMap();
		model.put("Rows", addPaBasicItemDataList);

		return model;
	}

	/**
	 * 添加工资基础项目数据信息（add Pa Basic Item Data Info）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPaBasicItemDataInfo")
	@ResponseBody
	public String addPaBasicItemDataInfo(HttpServletRequest request)
			throws Exception {

		this.paBasicItemSer.addPaBasicItemDataInfo(request);

		return "Y";
	}

	/**
	 * 添加工资基础项目数据页面（add Pa Basic Item Data View）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaBasicItemDataView")
	public ModelAndView addPaBasicItemDataView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 取得数据项目信息
		LinkedHashMap paBasicItemDataInfo = (LinkedHashMap) this.paBasicItemSer
				.getPaBasicItemDataInfo(request);
		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(paBasicItemDataInfo
				.get("DISTINCT_FIELD"));
		String distinctField2 = ObjectUtils.toString(paBasicItemDataInfo
				.get("DISTINCT_FIELD_2ND"));
		if (!distinctField.equals("PERSON_ID")) {
			this.paBasicItemSer.createAddPaBasicInputItemDataInfo(request);
			List distinctList = this.paBasicItemSer
					.getPaBasicParamDataList(request);
			modelMap.put("distinctList", distinctList);
			if (!"".equals(distinctField2)) {
				List distinctList2 = this.paBasicItemSer
						.getPaBasicParamDataTwoList(request);
				modelMap.put("distinctList2", distinctList2);
			}
		}

		String PARAM_NO = request.getParameter("seach_PARAM_NO");
		modelMap.put("PARAM_NO", PARAM_NO);
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));
		modelMap.put("paBasicItemDataInfo", paBasicItemDataInfo);
		modelMap.put("CPNY_ID", admin.getCpnyId());

		return new ModelAndView("/pa/wagebase/addPaBasicItemDataView", modelMap);
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
	@RequestMapping(value = "/viewAddPaBasicPersonalDataList")
	public ModelAndView viewAddPaBasicPersonalDataList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
        AdminBean  admin = SessionUtil.getLoginUserFromSession(request);
        List getEmpTypeCodeList =   this.cycleSer.getKeeperEmpTypeCodeList(request,admin.getPersonId()) ; 
		List jobTypeGroupList =this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());
		modelMap.put("getEmpTypeCodeList", getEmpTypeCodeList);
		modelMap.put("jobTypeGroupList", jobTypeGroupList);
		modelMap.put("empList", this.paInputItemSer.getEmpIdList(request));
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.paInputItemSer
				.getEmpIdListCnt(request));

		return new ModelAndView("/pa/insurance/viewAddPaBasicPersonalDataList",
				modelMap);
	}

	/**
	 * 添加保险输入项目数据信息（add Insurance Input Item Data Info）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaBasicInputItemDataInfo")
	@ResponseBody
	public Map<String, Object> addPaBasicInputItemDataInfo(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		LinkedHashMap paBasicItemDataInfo = (LinkedHashMap) this.paBasicItemSer
				.getPaBasicItemDataInfo(request);
		String distinctField = ObjectUtils.toString(paBasicItemDataInfo.get("DISTINCT_FIELD"));
		try {
			if ("PERSON_ID".equals(distinctField)) {
				int result = this.paBasicItemSer
						.addPaBasicInputItemDataInfo(request);
				if (result == 0) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_success", request));
					map.put("rel", "viewPaBasicItemData");
				} else if (result == 2) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.info_exits", request));
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));
				}
			} else {
				int result = this.paBasicItemSer
						.addPaBasicInputItemOtherDataInfo(request);
				if (result == 0) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_success", request));
					map.put("rel", "viewPaBasicItemData");
				} else if (result == 2) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.info_exits", request));
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));
				}
			}

		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAndAddPaBasicItemDataInfo")
	@ResponseBody
	public String updateAndAddPaBasicItemDataInfo(HttpServletRequest request)
			throws Exception {

		this.paBasicItemSer.updateAndAddPaBasicItemDataInfo(request);

		return "Y";
	}

	/**
	 * 处理工资基础项目数据单个删除请求(delete Pa Basic Item Data Info)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePaBasicItemDataInfo")
	@ResponseBody
	public Map deletePaBasicItemDataInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 查看数否有该条奖金输入项目
			String type = request.getParameter("type");
			if ("1".equals(type)) {
				int errorInt = this.paBasicItemSer
						.checkDeletePaBasicItemDataInfoType(request);
				if (errorInt > 0) {
					this.paBasicItemSer.deletePaBasicItemDataInfoType(request);
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("rel", "viewPaBasicItemData");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.pa.bonus.databaseHasNoThisRecord",
							request));
				}
			} else {
				int errorInt = this.paBasicItemSer
						.checkDeletePaBasicItemDataInfo(request);

				if (errorInt > 0) {
					this.paBasicItemSer.deletePaBasicItemDataInfo(request);
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("rel", "viewPaBasicItemData");
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
	 * 处理工资基础项目数据批量删除请求(delete Pa Basic Item Data Batch Info)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePaBasicItemDataBatchInfo")
	@ResponseBody
	public Map deletePaBasicItemDataBatchInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String type = request.getParameter("type");
			if ("1".equals(type)) {
				int count = this.paBasicItemSer
						.deletePaBasicItemDataBatchInfoType(request);
				if (count == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("rel", "viewPaBasicItemData");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.pa.bonus.databaseHasNoThisRecord",
							request));
				}
			} else {
				int count = this.paBasicItemSer
						.deletePaBasicItemDataBatchInfo(request);
				if (count == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("rel", "viewPaBasicItemData");
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
	
	//
	/**
	 * 跳转到派遣地管理(viewSendToAdministration)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	           ///pa/wagebase/viewSendToAdministration
	@RequestMapping(value = "/viewSendToAdministration")
	public ModelAndView viewSendToAdministration(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID") == null ? admin
				.getCpnyId() : paramMap.get("CPNY_ID").toString());
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "125230"));
		
		modelMap.put("sendList", this.paBasicItemSer.viewPaSendToAdministrationList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.paBasicItemSer.getPaSendToAdministrationListCnt(request));
		
		return new ModelAndView("/pa/wagebase/viewSendToAdministration", modelMap);
	}
	/**
	 * 跳转到派遣地添加参数页面(add Pa send to administrator View)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaSendToAdministration")
	public ModelAndView addPaSendToAdministration(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		

		return new ModelAndView("/pa/wagebase/addPaSendToAdministration",
				modelMap);
	}
	/**
	 * 执行添加派遣地项目(add Pa send to administrator Item Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaSendToAdministrationItemInfo")
	@ResponseBody
	public Map addPaSendToAdministration(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int resultNum = this.paBasicItemSer.addPaSendToAdministrationItemInfo(request);
		try {
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_success", request));
					map.put("navTabId", "pa9999");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));
				}
			
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}

		return map;

	}
	/**
	 * 删除派遣地项目信息(delete send to administrator Item Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePaSendToAdministrationInfo")
	@ResponseBody
	public Map deletePaSendToAdministrationInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
				int resultNum = this.paBasicItemSer.deletePaSendToAdministrationInfo(request);
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("navTabId", "pa9999");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_fail", request));
				}
			
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));
		}

		return map;
	}
	/**
	 * 工资基础项目数据导入结果展示页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @author chenff
	 * @throws Exception
	 */
	@RequestMapping(value="/viewImportExcelTempPaBasicItemList")
	public ModelAndView viewImportExcelTempPaBasicItemList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List itemList = paBasicItemSer.getImportExcelTempPaBasicItemList(request);
		int impTotalCnt = paBasicItemSer.getImportExcelTempPaBasicItemListCnt(request);
		int impErrCnt   = paBasicItemSer.getImportExcelTempPaBasicItemListErrCnt(request);
		modelMap.put("MDATA", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "3264"));
		return new ModelAndView("/pa/wagebase/viewImportExcelTempPaBasicItemList",modelMap);
	}
	
	/**
	 * 工资基础项目数据导出临时数据
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewImportPaBasicItemListExcel")
	public void viewImportPaBasicItemListExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		aliasNameList.add("社号");
		aliasNameList.add("员工姓名");
		aliasNameList.add("开始月");
		aliasNameList.add("数值");
		aliasNameList.add("备注");
		aliasNameList.add("验证结果");
		List paBasicItemTempList = paBasicItemSer.getImportExcelTempPaBasicItemList(request);
		for(int i=0;i<paBasicItemTempList.size();i++){
			LinkedHashMap map = new LinkedHashMap();
			LinkedHashMap map1 = (LinkedHashMap)paBasicItemTempList.get(i);
			map.put("CELL0", map1.get("EMPID"));
			map.put("CELL1", map1.get("CHINESENAME"));
			map.put("CELL2", map1.get("START_DATE"));
			map.put("CELL3", map1.get("RETURN_VALUE"));
			map.put("CELL4", map1.get("REMARK"));
			map.put("CELL5", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
			list.add(map);
		}
		String name = "viewPaBasicItemListExcel";
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
	}
	/**
	 * 提交工资基础项目数据
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createImportPaBasicItemListExcel")
	@ResponseBody
	public int createImportPaBasicItemListExcel(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap) throws Exception{
		String result = paBasicItemSer.importPaBasicItemExcelExcel(request);
		
		return result.equals("OK")?1:0;
	}
	
	
	
/*-----------------------------------------------------------------------------------------------------	*/
	
}

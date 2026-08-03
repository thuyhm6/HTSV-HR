package com.ait.pa.action.insurance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.CycleSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.pa.service.insurance.InsuranceInputItemSer;
import com.ait.pa.service.salary.PaInputItemSer;
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
 * @fileName: InsuranceInputItemCtroller.java
 * @Description:
 * @Create date: 2012-1-16 下午06:53:46
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/insurance")
@SuppressWarnings("unchecked")
public class InsuranceInputItemCtroller {
	Logger logger = Logger.getLogger(InsuranceInputItemCtroller.class);

	@Autowired
	private InsuranceInputItemSer insuranceInputItemSer;
	@Autowired
	private CompanySer companySer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private PaInputItemSer paInputItemSer;
	@Autowired
	private ExcelUtilSer excelUtilSer;
	@Autowired
    private CycleSer cycleSer;
	/**
	 * 显示保险输入项目（view Insurance Input Item）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewInsuranceInputItem")
	public ModelAndView viewInsuranceInputItemList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List isInputItemList = this.insuranceInputItemSer
				.getInsuranceInputItemList(request);
		int isInputItemCnt = this.insuranceInputItemSer
				.getInsuranceInputItemCnt(request);
		modelMap.put("isInputItemList", isInputItemList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, isInputItemCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2546"));
		return new ModelAndView("/pa/insurance/viewInsuranceInputItem",
				modelMap);
	}

	/**
	 * 显示保险输入项目参数（view Insurance Input Item Param）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewInsuranceInputItemParam")
	public ModelAndView viewInsuranceInputItemParamList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List isInputItemParamList = this.insuranceInputItemSer
				.getInsuranceInputItemParamList(request);
		int isInputItemParamCnt = this.insuranceInputItemSer
				.getInsuranceInputItemParamCnt(request);
		modelMap.put("isInputItemParamList", isInputItemParamList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, isInputItemParamCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2572"));
		return new ModelAndView("/pa/insurance/viewInsuranceInputItemParam",
				modelMap);
	}

	/**
	 * 跳转到添加保险输入项目页面（show add Insurance Input Item View）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addInsuranceInputItemView")
	public ModelAndView addInsuranceInputItemView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("isInputItemInfo", insuranceInputItemSer
				.getInsuranceInputItemInfo(request));

		return new ModelAndView("/pa/insurance/addInsuranceInputItemView",modelMap);
	}

	/**
	 * 添加保险输入项目信息（add Insurance Input Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addInsuranceInputItemInfo")
	@ResponseBody
	public Map<String, Object> addInsuranceInputItemInfo(
			HttpServletRequest request) throws Exception {
		int errorInt = this.insuranceInputItemSer
				.checkAddInsuranceInputItemInfo(request);
		Map<String, Object> map = new HashMap<String, Object>();
		if (errorInt == 0) {
			int result = this.insuranceInputItemSer.addInsuranceInputItemInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));
				map.put("navTabId", "pa0402");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));
			}

		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.ID_update_conflict", request));
		}
		return map;
	}

	/**
	 * 跳转到添加保险输入项目参数页面（show add Insurance Input Item Param View）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addInsuranceInputItemParamView")
	public ModelAndView addInsuranceInputItemParamView(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		request.setAttribute("INSURANCE_INPUT_PARAM", "1");//保险输入项目添加区分
		List isInputItemList = this.insuranceInputItemSer.getInsuranceInputItemList(request);
		modelMap.put("isInputItemList", isInputItemList);

		List distinctFieleList = this.insuranceInputItemSer.getDistinctFieldList(request);
		modelMap.put("distinctFieleList", distinctFieleList);
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));

		return new ModelAndView("/pa/insurance/addInsuranceInputItemParamView",
				modelMap);
	}

	/**
	 * 添加保险输入项目参数信息（add Insurance Input Item Param Info）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addInsuranceInputItemParamInfo")
	@ResponseBody
	public Map<String, Object> addInsuranceInputItemParamInfo(
			HttpServletRequest request) throws Exception {
		int errorInt = this.insuranceInputItemSer
				.checkAddInsuranceInputItemParamInfo(request);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (errorInt == 0) {
				if (this.insuranceInputItemSer.addInsuranceInputItemParamInfo(request) == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));
					map.put("navTabId", "pa0408");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));
				}
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.ID_update_conflict", request));
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));
		}
		return map;
	}

	/**
	 * 跳转到修改保险输入项目页面（show update Insurance Input Item View）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateInsuranceInputItemView")
	public ModelAndView updateInsuranceInputItemView(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		Object insuranceInputItemInfo = this.insuranceInputItemSer
				.getInsuranceInputItemInfo(request);

		modelMap.put("insuranceInputItemInfo", insuranceInputItemInfo);
		return new ModelAndView("/pa/insurance/updateInsuranceInputItemView",
				modelMap);
	}

	/**
	 * 修改保险输入项目信息（update Insurance Input Item Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateInsuranceInputItemInfo")
	@ResponseBody
	public Map<String, Object> updateInsuranceInputItemInfo(
			HttpServletRequest request) throws Exception {
		int errorInt = this.insuranceInputItemSer
				.checkAddInsuranceInputItemInfo(request);
		Map<String, Object> map = new HashMap<String, Object>();
		if (errorInt == 0) {
			int result = this.insuranceInputItemSer
					.updateInsuranceInputItemInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("navTabId", "pa0402");

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
	 * 跳转到保险输入项目参数页面（update Insurance Input Item Param View）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateInsuranceInputItemParamView")
	public ModelAndView updateInsuranceInputItemParamView(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		Object insuranceInputItemParamInfo = this.insuranceInputItemSer
				.getInsuranceInputItemParamInfo(request);
		List distinctFieleList = this.insuranceInputItemSer
				.getDistinctFieldList(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("distinctFieleList", distinctFieleList);
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));
		modelMap.put("insuranceInputItemParamInfo", insuranceInputItemParamInfo);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView(
				"/pa/insurance/updateInsuranceInputItemParamView", modelMap);
	}

	/**
	 * 修改保险输入项目参数信息（update Insurance Input Item Param Info）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateInsuranceInputItemParamInfo")
	@ResponseBody
	public Map<String, Object> updateInsuranceInputItemParamInfo(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = this.insuranceInputItemSer.updateInsuranceInputItemParamInfo(request);

			if (result == 0) {
				this.insuranceInputItemSer.updateInsuranceInputItemParamInfo(request);
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success", request));
				map.put("navTabId", "pa0408");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.ID_update_conflict", request));
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 删除保险输入项目信息（delete Insurance Input Item Info）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteInsuranceInputItemInfo")
	@ResponseBody
	public Map<String, Object> deleteInsuranceInputItemInfo(
			HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int errorInt = this.insuranceInputItemSer
				.checkDeleteInsuranceInputItemInfo(request);
		if (errorInt > 0) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_info_use", request));
		} else {
			int result = this.insuranceInputItemSer
					.deleteInsuranceInputItemInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_success", request));
				map.put("navTabId", "pa0402");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_fail", request));
			}
		}
		return map;
	}

	/**
	 * 删除保险输入项目参数信息（delete Insurance Input Item Param Info）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteInsuranceInputItemParamInfo")
	@ResponseBody
	public Map<String, Object> deleteInsuranceInputItemParamInfo(
			HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int errorInt = this.insuranceInputItemSer
				.checkDeleteInsuranceInputItemParamInfo(request);
		try {
			if (errorInt > 0) {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_info_use", request));
			} else {
				int result = this.insuranceInputItemSer
						.deleteInsuranceInputItemParamInfo(request);
				if (result == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("navTabId", "pa0408");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_fail", request));
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
	 * 显示保险输入项目数据（view Insurance Input Item Data）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewInsuranceInputItemData")
	public ModelAndView viewInsuranceInputItemData(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List isInputItemDataList = this.insuranceInputItemSer
				.getInsuranceInputItemParamListForData(request);
		modelMap.put("isInputItemDataList", isInputItemDataList);

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID") == null ? admin
				.getCpnyId() : paramMap.get("CPNY_ID").toString());

		modelMap
				.put("compList", this.companySer.getCompanyItemAllList(request));

		return new ModelAndView("/pa/insurance/viewInsuranceInputItemData",
				modelMap);
	}

	/**
	 * 显示所有保险输入项目数据列表（view Insurance Input Item Data List）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewInsuranceInputItemDataList")
	public ModelAndView viewInsuranceInputItemDataList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		List insuranceItemDataList = this.insuranceInputItemSer.getInsuranceInputItemDataListByParamNo(request);
		int insuranceItemDataListCnt = this.insuranceInputItemSer.getInsuranceInputItemDataListByParamNoCnt(request);
		Object insuranceInputItemParamInfo = this.insuranceInputItemSer.getInsuranceInputItemParamInfo(request);

		modelMap.put("insuranceInputItemParamInfo", insuranceInputItemParamInfo);
		modelMap.put("insuranceItemDataList", insuranceItemDataList);
		modelMap.put("PARAM_NO", request.getParameter("seach_PARAM_NO"));
		modelMap.put("ITEM_NO", request.getParameter("ITEM_NO"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("loginName", admin.getUsername()!=null?admin.getUsername().toString():"NOIT");
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, insuranceItemDataListCnt);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2573")) ;
		int rolesGroup=this.insuranceInputItemSer.getUserRolesGroupCnt(request);
		modelMap.put("rolesGroup",rolesGroup);
		String type="";
		String year=request.getParameter("seach_insYear");
		String month=request.getParameter("seach_insMonth");
		modelMap.put("insYear", year);
		modelMap.put("insMonth", month);
		if(request.getParameter("viewInsuranceInputItemDataList_pa0409")!=null&&!request.getParameter("viewInsuranceInputItemDataList_pa0409").equals("")){
			type=request.getParameter("viewInsuranceInputItemDataList_pa0409");
			modelMap.put("type", type);
		}
		return new ModelAndView("/pa/insurance/viewInsuranceInputItemDataList",
				modelMap);
	}

	/**
	 * 删除保险输入项目数据信息（delete Insurance Input Item Data Info）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteInsuranceInputItemDataInfo")
	@ResponseBody
	public Map<String, Object> deleteInsuranceInputItemDataInfo(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 查看数否有该条奖金输入项目
			String type = request.getParameter("type");
			if ("1".equals(type)) {
				int errorInt = this.insuranceInputItemSer
						.checkDeleteInsuranceInputItemDataInfoType(request);
				if (errorInt > 0) {
					this.insuranceInputItemSer
							.deleteInsuranceInputItemDataInfoType(request);
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("rel", "viewInsuranceInputItemData");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.pa.bonus.databaseHasNoThisRecord",
							request));
				}
			} else {
				int errorInt = this.insuranceInputItemSer
						.checkDeleteInsuranceInputItemDataInfo(request);

				if (errorInt > 0) {
					this.insuranceInputItemSer
							.deleteInsuranceInputItemDataInfo(request);
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("rel", "viewInsuranceInputItemData");
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
	 * 删除保险输入项目数据信息（delete Insurance Input Item Data Info）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteInsuranceInputItemDataBatchInfo")
	@ResponseBody
	public Map<String, Object> deleteInsuranceInputItemDataBatchInfo(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 查看数否有该条奖金输入项目
			String type = request.getParameter("type");
			if ("1".equals(type)) {
				if (this.insuranceInputItemSer
						.deleteInsuranceInputItemDataBatchInfoType(request) == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("rel", "viewInsuranceInputItemData");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.pa.bonus.databaseHasNoThisRecord",
							request));
				}
			} else {

				if (this.insuranceInputItemSer
						.deleteInsuranceInputItemDataBatchInfo(request) == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("rel", "viewInsuranceInputItemData");
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
	 * 跳转到保险输入项目数据页面（show update Insurance Input Item Data View）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateInsuranceInputItemDataView")
	public ModelAndView updateInsuranceInputItemDataView(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		Object isInputItemDataInfo = this.insuranceInputItemSer
				.getInsuranceInputItemDataInfo(request);
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));
		modelMap.put("isInputItemDataInfo", isInputItemDataInfo);

		return new ModelAndView(
				"/pa/insurance/updateInsuranceInputItemDataView", modelMap);
	}

	/**
	 * 修改保险输入项目数据信息（update Insurance Input Item Data Info）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateInsuranceInputItemDataInfo")
	@ResponseBody
	public Map<String, Object> updateInsuranceInputItemDataInfo(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = this.insuranceInputItemSer
					.updateInsuranceInputItemDataInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("rel", "viewInsuranceInputItemData");
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
	 * 批量删除保险输入项目数据信息（update Insurance Input Item Data Info）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAllInsuranceInputItemDataInfo")
	@ResponseBody
	public Map<String, Object> deleteAllInsuranceInputItemDataInfo(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = this.insuranceInputItemSer
					.deleteAllInsuranceInputItemDataInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", "删除成功");
				map.put("rel", "viewInsuranceInputItemData");
			} else {
				map.put("statusCode", "300");
				map.put("message", "删除失败");
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", "删除失败");
		}

		return map;
	}

	/**
	 * 跳转到保险输入项目数据添加页面（show add Insurance Input Item Data View）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addInsuranceInputItemDataView")
	public ModelAndView addInsuranceInputItemDataView(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap isInputItemParamInfo = (LinkedHashMap) this.insuranceInputItemSer
				.getInsuranceInputItemParamInfo(request);
		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(isInputItemParamInfo
				.get("DISTINCT_FIELD"));
		String distinctField2 = ObjectUtils.toString(isInputItemParamInfo
				.get("DISTINCT_FIELD_2ND"));
		if (!distinctField.equals("PERSON_ID")) {
			this.insuranceInputItemSer
					.createAddInsuranceInputItemDataInfo(request);
			List distinctList = this.insuranceInputItemSer
					.getIsParamDataList(request);
			modelMap.put("distinctList", distinctList);
			if (!"".equals(distinctField2)) {
				List distinctList2 = this.insuranceInputItemSer
						.getIsParamDataTwoList(request);
				modelMap.put("distinctList2", distinctList2);
			}
		}

		Object insuranceInputItemParamInfo = this.insuranceInputItemSer
				.getInsuranceInputItemParamInfo(request);
		String PARAM_NO = request.getParameter("seach_PARAM_NO");

		modelMap.put("PARAM_NO", PARAM_NO);
		modelMap.put("ITEM_NO", request.getParameter("ITEM_NO"));
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));
		modelMap
				.put("insuranceInputItemParamInfo", insuranceInputItemParamInfo);
		modelMap.put("CPNY_ID", admin.getCpnyId());

		return new ModelAndView("/pa/insurance/addInsuranceInputItemDataView",
				modelMap);
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
	@RequestMapping(value = "/viewAddInsurancePersonalDataList")
	public ModelAndView viewAddInsurancePersonalDataList(
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

		return new ModelAndView(
				"/pa/insurance/viewAddInsurancePersonalDataList", modelMap);
	}

	/**
	 * 添加保险输入项目数据信息（add Insurance Input Item Data Info）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addInsuranceInputItemDataInfo")
	@ResponseBody
	public Map<String, Object> addInsuranceInputItemDataInfo(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Object insuranceInputItemParamInfo = this.insuranceInputItemSer.getInsuranceInputItemParamInfo(request);
		try {
			if (((Map) insuranceInputItemParamInfo).get("DISTINCT_FIELD").equals("PERSON_ID")) {
				int result = this.insuranceInputItemSer.addInsuranceInputItemDataInfo(request);
				if (result == 0) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));
					map.put("rel", "viewInsuranceInputItemData");
				} else if (result == 2) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("alert.message.pa.bonus.thisDataIsExist_add_fail",request));
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));
				}
			} else {
				int result = this.insuranceInputItemSer.addInsuranceInputItemOtherDataInfo(request);
				if (result == 0) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));
					map.put("rel", "viewInsuranceInputItemData");
				} else if (result == 2) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("alert.message.pa.bonus.thisDataIsExist_add_fail",request));
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
	 * 初始化保险输入项目信息（create Insurance Input Item Info）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createInsuranceInputItemInfo")
	@ResponseBody
	public String createInsuranceInputItemInfo(HttpServletRequest request)
			throws Exception {

		this.insuranceInputItemSer.createInsuranceInputItemInfo(request);

		return "Y";
	}

	/**
	 * 查看个人保险输入项目数据列表（view Insurance Input Item Data Person List）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewInsuranceInputItemDataPersonList")
	public ModelAndView viewInsuranceInputItemDataPersonList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		List insuranceInputItemPersonList = this.insuranceInputItemSer
				.getInsuranceInputItemDataPersonList(request);

		int insuranceInputItemPersonListCnt = this.insuranceInputItemSer
				.getInsuranceInputItemDataPersonListCnt(request);

		modelMap.put("PA_MONTH", request.getParameter("PA_MONTH"));
		modelMap.put("insuranceInputItemPersonList",
				insuranceInputItemPersonList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, insuranceInputItemPersonListCnt);

		return new ModelAndView(
				"/pa/insurance/viewInsuranceInputItemDataPersonList", modelMap);
	}

	/**
	 * 跳转到保险输入项目个人页面（add Insurance Personal Input View）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addInsurancePersonalInputView")
	public ModelAndView viewAddPaPersonalInputList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String cpnyID = request.getParameter("seach_CPNY_ID");
		String personID = request.getParameter("seach_PERSON_ID");
		List insuranceInputItemPersonList = this.insuranceInputItemSer.getAddInsurancePersonalInputList(request);
		int insuranceInputItemPersonListCnt = this.insuranceInputItemSer.getAddInsurancePersonalInputListCnt(request);
		List insuranceInputItemPersonInfo = this.insuranceInputItemSer.getInsuranceInputItemDataPersonListNoPage(request);
		LinkedHashMap returnObj = new LinkedHashMap();
		if (insuranceInputItemPersonInfo.size() > 0) {
			returnObj = (LinkedHashMap) insuranceInputItemPersonInfo.get(0);
		}
		modelMap.put("personID", personID);
		modelMap.put("cpnyID", cpnyID);
		modelMap.put("insuranceInputItemPersonInfo", returnObj);
		modelMap.put("insuranceInputItemPersonList",insuranceInputItemPersonList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, insuranceInputItemPersonListCnt);

		return new ModelAndView("/pa/insurance/addInsurancePersonalInputView",modelMap);
	}

	/**
	 * 处理保险输入项目数据修改请求(update Insurance Input Item Data Person Info)
	 * 
	 * @param List
	 * @return
	 */
	@RequestMapping(value = "/updateInsuranceInputItemDataPersonInfo")
	@ResponseBody
	public Map<String, Object> updateInsuranceInputItemDataPersonInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (this.insuranceInputItemSer.updateInsuranceInputItemDataPersonInfo(request) == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success", request));
				map.put("navTabId", "pa0212");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.pa.bonus.thisDataIsExist_add_fail", request));
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail", request));
		}
		return map;
	}
	
	//2013-09-01  lufeng
	/**
	 * 显示保险输入项目申请生效的数据（view Insurance Input Item Data）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewInsuranceInputItemDataApply")
	public ModelAndView viewInsuranceInputItemDataApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID") == null ? admin.getCpnyId() : paramMap.get("CPNY_ID").toString());
		
		List isInputItemDataList = this.insuranceInputItemSer.getInsuranceInputItemDataForApply(request);
		
		modelMap.put("isInputItemDataList", isInputItemDataList);
		modelMap.put("compList", this.companySer.getCompanyItemAllList(request));

		return new ModelAndView("/pa/insurance/viewInsuranceInputItemDataApply",
				modelMap);
	}
	
	/**
	 * 显示所有保险输入项目申请有效数据列表（view Insurance Input Item Data List）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewInsuranceInputItemDataApplyList")
	public ModelAndView viewInsuranceInputItemDataApplyList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		//获取已通过的申请数据 所有保存 未裁决 未申请(0,0) 
		List insuranceItemDataList = this.insuranceInputItemSer.getInsuranceInputApplyDataListByParamNo(request,0,0);
		int insuranceItemDataListCnt = this.insuranceInputItemSer.getInsuranceInputApplyDataListByParamNoCnt(request,0,0);
		Object insuranceInputItemParamInfo = this.insuranceInputItemSer.getInsuranceInputApplyDataInfo(request);

		modelMap.put("insuranceInputItemParamInfo", insuranceInputItemParamInfo);
		modelMap.put("insuranceItemDataList", insuranceItemDataList);
		modelMap.put("PARAM_NO", request.getParameter("seach_PARAM_NO"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("loginName", admin.getUsername()!=null?admin.getUsername().toString():"NOIT");
		
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, insuranceItemDataListCnt);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "38")) ;


		return new ModelAndView("/pa/insurance/viewInsuranceInputItemDataApplyList",
				modelMap);
	}
	
	/**
	 * 跳转到保险输入项目数据申请页面（show add Insurance Input Item Data View）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addInsuranceInputItemDataApplyView")
	public ModelAndView addInsuranceInputItemDataApplyView(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap isInputItemParamInfo = (LinkedHashMap) this.insuranceInputItemSer
				.getInsuranceInputItemParamInfo(request);
		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(isInputItemParamInfo.get("DISTINCT_FIELD"));
		String distinctField2 = ObjectUtils.toString(isInputItemParamInfo.get("DISTINCT_FIELD_2ND"));
		if (!distinctField.equals("PERSON_ID")) {
			this.insuranceInputItemSer.createAddInsuranceInputItemDataInfo(request);
			List distinctList = this.insuranceInputItemSer.getIsParamDataList(request);
			modelMap.put("distinctList", distinctList);
			if (!"".equals(distinctField2)) {
				List distinctList2 = this.insuranceInputItemSer.getIsParamDataTwoList(request);
				modelMap.put("distinctList2", distinctList2);
			}
		}

		Object insuranceInputItemParamInfo = this.insuranceInputItemSer.getInsuranceInputItemParamInfo(request);
		String PARAM_NO = request.getParameter("seach_PARAM_NO");

		modelMap.put("PARAM_NO", PARAM_NO);
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));
		modelMap.put("insuranceInputItemParamInfo", insuranceInputItemParamInfo);
		modelMap.put("CPNY_ID", admin.getCpnyId());
		String paramDataNo = this.insuranceInputItemSer.getNextparamDataNo();
		modelMap.put("PARAMDATANO", paramDataNo);
		return new ModelAndView("/pa/insurance/addInsuranceInputItemDataApplyView",modelMap);
	}
	
	/**
	 * 添加保险输入项目申请数据信息（add Insurance Input Item Data Info）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addInsuranceInputItemDataApply")
	@ResponseBody
	public Map<String, Object> addInsuranceInputItemDataApply(HttpServletRequest request) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		Object insuranceInputItemParamInfo = this.insuranceInputItemSer.getInsuranceInputItemParamInfo(request);
		try {
			if (((Map) insuranceInputItemParamInfo).get("DISTINCT_FIELD").equals("PERSON_ID")) {
				int result = this.insuranceInputItemSer.addInsuranceInputItemDataApply(request);
				
				if (result == 0) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));
					map.put("rel", "viewInsuranceInputItemDataApply");
				} else if (result == 2) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("alert.message.pa.bonus.thisDataIsExist_add_fail",request));
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));
				}
			} else {
				int result = this.insuranceInputItemSer.addInsuranceInputItemOtherDataApply(request);
				if (result == 0) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));
					map.put("rel", "viewInsuranceInputItemDataApply");
				} else if (result == 2) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage("alert.message.pa.bonus.thisDataIsExist_add_fail",request));
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
	 * 根据公司显示项目（view Insurance Input Item Data）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewInsuranceInputItemDataAffirmByCpnyId")
	@ResponseBody
	public Map<String, Object> viewInsuranceInputItemDataAffirmByCpnyId(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		Map<String, Object> map1 = new HashMap<String, Object>();
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID")!=null?paramMap.get("CPNY_ID").toString():(admin.getCpnyId().toString()));
		
		List isInputItemDataList = this.insuranceInputItemSer.getInsuranceInputItemDataForApplyName(request);
		for(int i=0;i<isInputItemDataList.size();i++){
			Map map=(Map)isInputItemDataList.get(i);
			map1.put(map.get("PARAM_NO").toString(), map.get("ALIAS_NAME"));
		}
		//modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "39")) ;
		return map1;
	}
	/**
	 * 显示保险输入项目待决裁数据（view Insurance Input Item Data）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewInsuranceInputItemDataAffirm")
	public ModelAndView viewInsuranceInputItemDataAffirmAList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID")!=null?paramMap.get("CPNY_ID").toString():(admin.getCpnyId().toString()));
		modelMap.put("LAN", paramMap.get("interLanguage")!=null?paramMap.get("interLanguage").toString():(admin.getLanguage().toString()));
		
		List isInputItemDataList = this.insuranceInputItemSer.getInsuranceInputItemDataForApplyName(request);
		//List isInputItemDataList = this.insuranceInputItemSer.getInsuranceInputItemDataForApplyName(request);
		
		modelMap.put("isInputItemDataList", isInputItemDataList);
		//modelMap.put("compList", this.companySer.getCompanyItemAllList(request));
		if(paramMap.get("PARAM_NO")!=null){
		modelMap.put("PARAM_NO", request.getParameter("PARAM_NO"));
		//modelMap.put("compList", this.companySer.getCompanyItemAllList(request));
		//modelMap.put("loginName", admin.getUsername()!=null?admin.getUsername().toString():"NOIT");
		//获取未决裁的申请数据 待裁决的数据 为裁决 已申请(0,1)
		List  insuranceItemDataList = this.insuranceInputItemSer.getInsuranceInputApplyDataListByParamNo(request,0,1);
		int insuranceItemDataListCnt = this.insuranceInputItemSer.getInsuranceInputApplyDataListByParamNoCnt(request,0,1);
		Object insuranceInputItemParamInfo = this.insuranceInputItemSer.getInsuranceInputApplyDataInfo(request);

		modelMap.put("insuranceInputItemParamInfo", insuranceInputItemParamInfo);
		modelMap.put("insuranceItemDataList", insuranceItemDataList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, insuranceItemDataListCnt);
		//modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "39")) ;
		}
		return new ModelAndView("/pa/insurance/viewInsuranceInputItemDataAffirm",modelMap);
	}
	
	/**
	 * 显示所有保险输入项目待决裁数据列表（view Insurance Input Item Data List）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewInsuranceInputItemDataAffirmList")
	public ModelAndView viewInsuranceInputItemDataAffirmList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID")!=null?paramMap.get("CPNY_ID").toString():(admin.getCpnyId().toString()));
		modelMap.put("PARAM_NO", request.getParameter("seach_PARAM_NO"));
		//modelMap.put("compList", this.companySer.getCompanyItemAllList(request));
		//modelMap.put("loginName", admin.getUsername()!=null?admin.getUsername().toString():"NOIT");
		//获取未决裁的申请数据 待裁决的数据 为裁决 已申请(0,1)
		List  insuranceItemDataList = this.insuranceInputItemSer.getInsuranceInputApplyDataListByParamNo(request,0,1);
		int insuranceItemDataListCnt = this.insuranceInputItemSer.getInsuranceInputApplyDataListByParamNoCnt(request,0,1);
		Object insuranceInputItemParamInfo = this.insuranceInputItemSer.getInsuranceInputApplyDataInfo(request);

		modelMap.put("insuranceInputItemParamInfo", insuranceInputItemParamInfo);
		modelMap.put("insuranceItemDataList", insuranceItemDataList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, insuranceItemDataListCnt);
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "39")) ;

		return new ModelAndView("/pa/insurance/viewInsuranceInputItemDataAffirmList",
				modelMap);
	}
	
	/**
	 * 通过/否决--保险输入项目申请数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveInsDataApply")
	@ResponseBody
	public Map<String, Object> approveInsDataApply(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.insuranceInputItemSer.approveInsDataApply(request);
		if (result == 1) {
			//map.put("navTabId", "pa0411");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));//操作失败
			map.put("statusCode", "200");
			map.put("rel", "viewInsuranceInputItemDataAffirm");
			//map.put("navTabId", "pa0411");
		} else {
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));//操作成功
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * 批量通过/否决--保险输入项目申请数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveInsDataApplyInBatch")
	@ResponseBody
	public Map<String, Object> approveInsDataApplyInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.insuranceInputItemSer.approveInsDataApplyInBatch(request);
		if (result == 1) {
			map.put("navTabId", "pa0411");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 修改保险输入项目申请数据信息（update Insurance Input Item Data Info）
	 * （暂时未使用）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateInsDataApply")
	@ResponseBody
	public Map<String, Object> updateInsDataApply(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = this.insuranceInputItemSer.updateInsDataApply(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("rel", "viewInsuranceInputItemDataAffirm");
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
	 * 显示保险输入项目申请数据（view Insurance Input Item Data）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewInsuranceInputItemDataView")
	public ModelAndView viewInsuranceInputItemDataViewAList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID")!=null?paramMap.get("CPNY_ID").toString():(admin.getCpnyId().toString()));
		modelMap.put("LAN", paramMap.get("interLanguage")!=null?paramMap.get("interLanguage").toString():(admin.getLanguage().toString()));
		modelMap.put("AFFIRM_FLAGA", paramMap.get("AFFIRM_FLAGA")!=null?paramMap.get("AFFIRM_FLAGA").toString():"-1");
		//List isInputItemDataList = this.insuranceInputItemSer.getInsuranceInputItemDataForApply(request);
		List isInputItemDataList = this.insuranceInputItemSer.getInsuranceInputItemDataForApplyName(request);
		modelMap.put("isInputItemDataList", isInputItemDataList);
		
		if(paramMap.get("PARAM_NO")!=null){
			modelMap.put("PARAM_NO", paramMap.get("PARAM_NO"));
			List insuranceItemDataList = this.insuranceInputItemSer.getInsuranceInputApplyDataListByParamNo(request,-1,1);
			int insuranceItemDataListCnt = this.insuranceInputItemSer.getInsuranceInputApplyDataListByParamNoCnt(request,-1,1);
			Object insuranceInputItemParamInfo = this.insuranceInputItemSer.getInsuranceInputApplyDataInfo(request);

			modelMap.put("insuranceInputItemParamInfo", insuranceInputItemParamInfo);
			modelMap.put("insuranceItemDataList", insuranceItemDataList);
			
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, insuranceItemDataListCnt);
			//modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "40")) ;
		}
		//modelMap.put("compList", this.companySer.getCompanyItemAllList(request));

		return new ModelAndView("/pa/insurance/viewInsuranceInputItemDataView",
				modelMap);
	}
	
	/**
	 * 显示所有保险输入项目申请数据列表（view Insurance Input Item Data List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewInsuranceInputItemDataViewList")
	public ModelAndView viewInsuranceInputItemDataViewList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID")!=null?paramMap.get("CPNY_ID").toString():(admin.getCpnyId().toString()));
		modelMap.put("PARAM_NO", request.getParameter("seach_PARAM_NO"));
		//modelMap.put("compList", this.companySer.getCompanyItemAllList(request));
		//modelMap.put("loginName", admin.getUsername()!=null?admin.getUsername().toString():"NOIT");
		//获取所有的申请数据 查看所有已申请 的状态 只要是以申请 显示
		List insuranceItemDataList = this.insuranceInputItemSer.getInsuranceInputApplyDataListByParamNo(request,-1,1);
		int insuranceItemDataListCnt = this.insuranceInputItemSer.getInsuranceInputApplyDataListByParamNoCnt(request,-1,1);
		Object insuranceInputItemParamInfo = this.insuranceInputItemSer.getInsuranceInputApplyDataInfo(request);

		modelMap.put("insuranceInputItemParamInfo", insuranceInputItemParamInfo);
		modelMap.put("insuranceItemDataList", insuranceItemDataList);
		
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, insuranceItemDataListCnt);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "40")) ;

		return new ModelAndView("/pa/insurance/viewInsuranceInputItemDataViewList",
				modelMap);
	}
	
	/**
	 * 删除--保险输入项目申请数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteInsDataApply")
	@ResponseBody
	public Map<String, Object> deleteInsDataApply(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.insuranceInputItemSer.deleteInsDataApply(request);
		if (result == 1) {
			//map.put("navTabId", "ess1002");
			if(request.getParameter("applyMark")!=null && ("1").equals(request.getParameter("applyMark")) ){
				map.put("rel", "viewInsuranceInputItemDataViewApplyMark");
			}else{
				map.put("rel", "viewInsuranceInputItemDataView");
			}
			
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));
			map.put("statusCode", "300");
		}
		return map;
	}
	
	/**
	 * 批量删除--保险输入项目申请数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteInsDataApplyInBatch")
	@ResponseBody
	public Map<String, Object> deleteInsDataApplyInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.insuranceInputItemSer.deleteInsDataApplyInBatch(request);
		if (result == 1) {
			//map.put("navTabId", "pa0412");
			map.put("PARAM_NO", request.getParameter("PARAM_NO"));
			map.put("rel", "viewInsuranceInputItemDataView");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));
			map.put("statusCode", "200");
			
		} else {
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 批量提交保险申请申请
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-5 上午11:44:48 
	* @version V1.0
	 */
	@RequestMapping(value = "/insuranceApply")
	@ResponseBody
	public Map<String, Object> insuranceApply(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.insuranceInputItemSer.updateInsuranceApply(request);
		if (result == 1) {
			map.put("navTabId", "pa0411");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));
			map.put("statusCode", "200");
			map.put("PARAM_NO", request.getParameter("seach_PARAM_NO"));
			map.put("CPNY_ID", request.getParameter("seach_CPNY_ID"));
		} else {
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));
			map.put("statusCode", "300");
			map.put("PARAM_NO", request.getParameter("seach_PARAM_NO"));
			map.put("CPNY_ID", request.getParameter("seach_CPNY_ID"));
		}
		map.put("result", result);
		return map;
	}
	/**
	 * 统一适用公司弹出页
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-6 下午4:52:31 
	* @version V1.0
	 */
	@RequestMapping(value = "/viewUnifySuitCompany")
	public ModelAndView viewUnifySuitCompany(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		
		List unifySuitCompanyList=this.insuranceInputItemSer.getUnifySuitCompanyList(request);
		modelMap.put("unifySuitCompanyList", unifySuitCompanyList);
		modelMap.put("PARAM_DATA_NO",request.getParameter("PARAM_DATA_NO"));
		modelMap.put("AFFIRM_FLAG", request.getParameter("AFFIRM_FLAG"));
		
		return new ModelAndView("/pa/insurance/viewUnifySuitCompany",modelMap);
		
	}
	
	@RequestMapping(value = "/accessoryUpload")
	public ModelAndView accessoryUpload(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		
		modelMap.put("PARAM_DATA_NO", request.getParameter("PARAM_DATA_NO"));
		
		return new ModelAndView("/pa/insurance/accessoryUpload",modelMap);
	}
	
	/**
	 * 附件上传
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-12 下午4:18:11 
	* @version V1.0
	 */
	@RequestMapping(value = "/upload")
	public ModelAndView upload(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		if (!(request instanceof MultipartHttpServletRequest) && multipartResolver.isMultipart(request)) {
			try {
				multipartResolver.setMaxUploadSize(20971520);
				multipartRequest = multipartResolver.resolveMultipart(request);
			} catch(MaxUploadSizeExceededException e) {
				return null;
			}
		} else if (request instanceof MultipartHttpServletRequest){
			multipartRequest = (MultipartHttpServletRequest)request;
		} else {
			return null;
		}
		String PARAMDATANO=multipartRequest.getParameter("seach_PARAMDATANO").toString();
		
		//MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;     
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");     
		 /**构建图片保存的目录**/    
		 String logoPathDir = "/resources/temp/apply/"+PARAMDATANO;// dateformat.format(new Date());     
		 /**得到图片保存目录的真实路径**/    
		 String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);     
		/**根据真实路径创建目录**/    
		 File logoSaveFile = new File(logoRealPathDir);     
		 if(!logoSaveFile.exists())     
		logoSaveFile.mkdirs();           
		/**页面控件的文件流**/    
		MultipartFile multipartFile = multipartRequest.getFile("file");      
		/**获取文件的后缀**/    
		String suffix = multipartFile.getOriginalFilename().substring  
		(multipartFile.getOriginalFilename().lastIndexOf("."));     
		 /**使用UUID生成文件名称**/    
		// String logImageName = request.getParameter("PARAM_DATA_NO").toString()+ suffix;
		//构建文件名称     
		String logImageNameA = multipartFile.getOriginalFilename();  
		String logImageName = PARAMDATANO+suffix;
		/**拼成完整的文件保存路径加文件**/    
		String fileName = logoRealPathDir + File.separator   + logImageName;                
		File file = new File(fileName);           
		try {     
		  multipartFile.transferTo(file);  
		  LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			map.put("fileName", logoPathDir+"/"+logImageName); //路径 
			map.put("PARAMDATANO", PARAMDATANO); //对应申请的编号 
			map.put("CREATED_BY", admin.getAdminID());
			map.put("ORIGINAL_NAME", logImageNameA);
		//	request.setAttribute("fileName", fileName);
			int result = this.insuranceInputItemSer.insertAccessory(map);
			modelMap.put("sign", result);
		 } catch (IllegalStateException e) {     
		 e.printStackTrace(); 
		 modelMap.put("sign", -1);
		} catch (IOException e) {            
		 e.printStackTrace();  
		 modelMap.put("sign", -1);
		 }   
		return new ModelAndView("/pa/insurance/upload",modelMap);
		
	}
	
	@RequestMapping("downloadFile")
	public void downloadFile(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		
		String fileName=request.getRealPath("")+request.getParameter("fileName");
		String fileNameA = URLEncoder.encode(request.getParameter("file"), "UTF-8");
		response.setHeader("Content-Disposition", "attachment;fileName="+fileNameA);
		try {
			File file=new File(fileName);
			System.out.println(file.getAbsolutePath());
			InputStream inputStream=new FileInputStream(file);
			OutputStream os=response.getOutputStream();
			byte[] b=new byte[102400];
			int length;
			while((length=inputStream.read(b))>0){
				os.write(b,0,length);
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/error")
	public ModelAndView error(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		modelMap.put("PARAM_DATA_NO", request.getParameter("PARAM_DATA_NO"));
		return new ModelAndView("/pa/insurance/error",modelMap);
	}
	@RequestMapping(value = "/viewItemBatchImport")
	public ModelAndView viewItemBatchImportList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID")!=null?paramMap.get("CPNY_ID").toString():(admin.getCpnyId().toString()));
		
		//List isInputItemDataList = this.insuranceInputItemSer.getInsuranceInputItemDataForApply(request);
		List getItemNameList = this.insuranceInputItemSer.getItemNameList(request);
		modelMap.put("getItemNameList", getItemNameList);
		
		//if(paramMap.get("PARAM_NO")!=null){
			modelMap.put("PARAM_NO", paramMap.get("PARAM_NO"));
			

	        String seach_FIRST_FLAG = request.getParameter("seach_FIRST_FLAG");
	        if(seach_FIRST_FLAG != null && !"".equals(seach_FIRST_FLAG)){
				List insuranceItemDataList = this.insuranceInputItemSer.getItemBatchImportList(request);
				int insuranceItemDataListCnt = this.insuranceInputItemSer.getItemBatchImportListoCnt(request);
				modelMap.put("insuranceItemDataList", insuranceItemDataList);
				modelMap.put(UiUtil.TOTAL_COUNT_NAME, insuranceItemDataListCnt);
	        }
			//Object insuranceInputItemParamInfo = this.insuranceInputItemSer.getInsuranceInputApplyDataInfo(request);

			//modelMap.put("insuranceInputItemParamInfo", insuranceInputItemParamInfo);
			modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "123453")) ;
		//}
		//modelMap.put("compList", this.companySer.getCompanyItemAllList(request));

		return new ModelAndView("/pa/insurance/viewItemBatchImport",modelMap);
	}
	/**
	 * 保险申请项目通知
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-25 下午5:20:27 
	* @version V1.0
	 */
	@RequestMapping(value = "/applyInform")
	@ResponseBody
	public Map<String, Object> applyInform(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.insuranceInputItemSer.addApplyInform(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		if (result == 1) {
			//map.put("navTabId", "pa0412");
			//map.put("PARAM_NO", request.getParameter("PARAM_NO"));
			//map.put("rel", "tonzhi");
			//map.put("navTabId", "tonzhi");
			map.put("dialogId", "tonzhi");
			map.put("url", "/pa/insurance/viewUnifySuitCompany");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));
			map.put("statusCode", "200");
			
		} else {
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	/**
	 * 保险查看（个人别）
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-30 下午03:56:27 
	* @version V1.0
	 */
	@RequestMapping(value = "/viewInsureSelf")
	public ModelAndView viewInsureSelfList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int viewInsureSelfCnt = insuranceInputItemSer.getViewInsureSelfCnt(request);
		
		List getEmpTypeCodeList =   this.cycleSer.getKeeperEmpTypeCodeList(request,admin.getPersonId()) ; 
		List jobTypeGroupList =this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());
		modelMap.put("CPNY_ID",admin.getCpnyId());
		modelMap.put("getEmpTypeCodeList", getEmpTypeCodeList);
		modelMap.put("jobTypeGroupList", jobTypeGroupList);
		modelMap.put("welfareList", insuranceInputItemSer.getWelfareArea(request));//福利地区
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, viewInsureSelfCnt);
		modelMap.put("dataList", insuranceInputItemSer.getViewInsureSelfList(request));
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "217880")) ;
		return new ModelAndView("/pa/insurance/viewInsureSelf",modelMap);
	}
	/**
	 * 保险查看（部门别）
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-30 下午03:56:46 
	* @version V1.0
	 */
	@RequestMapping(value = "/viewInsureDept")
	public ModelAndView viewInsureDeptList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		int viewInsureSelfCnt = insuranceInputItemSer.getViewInsureDeptCnt(request);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, viewInsureSelfCnt);
		modelMap.put("dataList", insuranceInputItemSer.getViewInsureDeptList(request));
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "217881")) ;
		return new ModelAndView("/pa/insurance/viewInsureDept",modelMap);
	}
	/**
	 * 保险查看（个人别）Excel导出
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-30 下午03:57:28 
	* @version V1.0
	 */
	/*@RequestMapping(value = "/viewInsureSelfExcel")
	public ModelAndView viewInsureSelfListExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("dataList", insuranceInputItemSer.getViewInsureSelfList(request));
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "217880")) ;
		return new ModelAndView("/pa/insurance/viewInsureSelfExcel",modelMap);
	}*/
	
	@RequestMapping(value = "/viewInsureSelfExcel")
	public void viewInsureSelfExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//设置excel header
		List aliasNameList = new ArrayList();
		aliasNameList.add("部门");
		aliasNameList.add("姓名");
		aliasNameList.add("工号");
		aliasNameList.add("考勤月");
		aliasNameList.add("福利地区");
	if(searchMap.get("interCpnyID").toString().equals("TSTO")){
		aliasNameList.add("养老(公司)");
		aliasNameList.add("失业(公司)");
		aliasNameList.add("医疗(公司)");
		aliasNameList.add("工伤(公司)");
		aliasNameList.add("生育(公司)");
		aliasNameList.add("养老(个人)");
		aliasNameList.add("失业(个人)");
		aliasNameList.add("医疗(个人)");
		aliasNameList.add("社保(公司)");
		aliasNameList.add("社保(个人)");
		aliasNameList.add("保险补扣(个人)");
		aliasNameList.add("保险补扣(公司)");
		aliasNameList.add("大额大病(个人)");
		aliasNameList.add("大额大病(公司)");
		aliasNameList.add("大病统筹(公司)");
		aliasNameList.add("公积金(公司)");
		aliasNameList.add("公积金(个人)");
		aliasNameList.add("合计五险一金(公司)");
		aliasNameList.add("合计五险一金(个人)");
	}else if(searchMap.get("interCpnyID").toString().equals("LGEYT")) {
		aliasNameList.add("人员类型");
		/*aliasNameList.add("医疗保险基数(公司)");
	    aliasNameList.add("医疗保险基数(个人)");
	    aliasNameList.add("养老保险基数(公司)");
	    aliasNameList.add("养老保险基数(个人)");
	    aliasNameList.add("失业保险基数(公司)");
	    aliasNameList.add("失业保险基数(个人)");
	    aliasNameList.add("生育保险基数(公司)");
	    
	    aliasNameList.add("工伤保险基数(公司)");
	    aliasNameList.add("大额大病保险基数(个人)");*/
		aliasNameList.add("社保基数个人");
		aliasNameList.add("社保基数公司"); 
		aliasNameList.add("公积金基数(个人)");
		aliasNameList.add("公积金基数(公司)");
	    /*aliasNameList.add("保险补扣(公司)");
	    aliasNameList.add("保险补扣(个人)");*/
	    aliasNameList.add("个人养老保险");
	    aliasNameList.add("个人失业保险");
	    aliasNameList.add("个人医疗保险");
	    aliasNameList.add("个人公积金");
	    aliasNameList.add("个人保险补扣");
	    aliasNameList.add("公司养老保险");
	    aliasNameList.add("公司失业保险");
	    aliasNameList.add("公司医疗保险");
	    aliasNameList.add("公司公积金");
	    aliasNameList.add("公司生育保险");
	    aliasNameList.add("公司工伤保险");
	    aliasNameList.add("公司保险补扣");
	    aliasNameList.add("个人五险一金");
	    aliasNameList.add("公司五险一金");
	}
		
		//列名
		String[] columns = null;
		 
		
		if(searchMap.get("interCpnyID").toString().equals("TSTO")){
			columns= new String[] {"DEPT_NAME","LOCAL_NAME","EMPID","IS_MONTH","INSRAREA_ID_NAME",
					"IS_ENDOWMENT_COR","IS_UNEMPLOY_COR","IS_MEDICAL_COR","IS_INJURY_COR","IS_FERTILITY_COR",
					"IS_ENDOWMENT_PER","IS_UNEMPLOY_PER","IS_MEDICAL_PER","IS_TOTAL_COR","IS_TOTAL_PER",
					"P_IS_AJUST_PER","P_IS_AJUST_COR","IS_SERIOUS_P","IS_SERIOUS_C","IS_BIGDISEASE_C",
					"IS_FUND_COR","IS_FUND_RER","COR","PER"};
		}else if(searchMap.get("interCpnyID").toString().equals("LGEYT")){ 
			 columns = new String[] {"DEPT_NAME","LOCAL_NAME","EMPID","IS_MONTH","INSRAREA_ID_NAME",
						/*"IS_MEDICAL_BC","IS_MEDICAL_BASE","IS_ENDOWMENT_BC","IS_ENDOWMENT_BASE",
						"IS_UNEMPLOY_BC","IS_UNEMPLOY_BASE","IS_FERTILITY_BC","IS_FUND_BC",
						"IS_FUND_BASE","IS_INJURY_BASE","IS_SERIOUS_P",*/
						
						"EMP_TYPE_NAME","P_INSUR_BASE_PER","P_INSUR_BASE_COP","IS_FUND_BASE","IS_FUND_BC",
						//"P_IS_AJUST_COR","P_IS_AJUST_PER",
						"IS_ENDOWMENT_PER","IS_UNEMPLOY_PER","IS_MEDICAL_PER",
						"IS_FUND_RER","IS_AJUST_PER","IS_ENDOWMENT_COR","IS_UNEMPLOY_COR",
						"IS_MEDICAL_COR","IS_FUND_COR","IS_FERTILITY_COR","IS_INJURY_COR",
						"IS_AJUST_COR","IS_TOTAL_PER","IS_TOTAL_COR"};
		}
		List aliasValueList  = insuranceInputItemSer.getViewInsureSelfList(request);
		String name = "paInsureSelfDataList";
		this.excelUtilSer.exportExcelByNamePwd(request,response,modelMap,aliasValueList,aliasNameList,columns,name,searchMap);
	}
	/**
	 * 保险查看（部门别）Excel导出
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-30 下午03:56:46 
	* @version V1.0
	 */
	/*@RequestMapping(value = "/viewInsureDeptExcel")
	public ModelAndView viewInsureDeptListExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("dataList", insuranceInputItemSer.getViewInsureDeptList(request));
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "217881")) ;
		return new ModelAndView("/pa/insurance/viewInsureDeptExcel",modelMap);
	}*/
	
	@RequestMapping(value = "/viewInsureDeptExcel")
	public void viewInsureDeptExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//设置excel header
		List aliasNameList = new ArrayList();
		aliasNameList.add("部门");
		aliasNameList.add("考勤月");
		aliasNameList.add("养老(公司)");
		aliasNameList.add("失业(公司)");
		aliasNameList.add("医疗(公司)");
		aliasNameList.add("工伤(公司)");
		aliasNameList.add("生育(公司)");
		aliasNameList.add("养老(个人)");
		aliasNameList.add("失业(个人)");
		aliasNameList.add("医疗(个人)");
		aliasNameList.add("社保(公司)");
		aliasNameList.add("社保(个人)");
		aliasNameList.add("保险补扣(个人)");
		aliasNameList.add("保险补扣(公司)");
		aliasNameList.add("大额大病(个人)");
		aliasNameList.add("大额大病(公司)");
		aliasNameList.add("大病统筹(公司)");
		aliasNameList.add("公积金(公司)");
		aliasNameList.add("公积金(个人)");
		aliasNameList.add("合计五险一金(公司)");
		aliasNameList.add("合计五险一金(个人)");
		
		//列名
		String[] columns = {"DEPT_NAME","IS_MONTH","IS_ENDOWMENT_COR","IS_UNEMPLOY_COR",
				"IS_MEDICAL_COR","IS_INJURY_COR","IS_FERTILITY_COR","IS_ENDOWMENT_PER",
				"IS_UNEMPLOY_PER","IS_MEDICAL_PER","IS_TOTAL_COR","IS_TOTAL_PER",
				"P_IS_AJUST_PER","P_IS_AJUST_COR","IS_SERIOUS_P","IS_SERIOUS_C","IS_BIGDISEASE_C",
				"IS_FUND_COR","IS_FUND_RER","COR","PER"};
		
		List aliasValueList  = insuranceInputItemSer.getViewInsureDeptList(request);
		String name = "paInsureSelfDataList";
		this.excelUtilSer.exportExcelByNamePwd(request,response,modelMap,aliasValueList,aliasNameList,columns,name,searchMap);
	}
	
	
	/* --------------------------------------------------------------------------*/
	
	/**
	 * 工资基础项目数据导入结果展示页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @author chenff
	 * @throws Exception
	 */
	@RequestMapping(value="/viewImportExcelTempISParamDataList")
	public ModelAndView viewImportExcelTempPaBasicItemList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List itemList = insuranceInputItemSer.getImportExcelTempISParamDataList(request);
		int impTotalCnt = insuranceInputItemSer.getImportExcelTempISParamDataListCnt(request);
		int impErrCnt   = insuranceInputItemSer.getImportExcelTempISParamDataListErrCnt(request);
		modelMap.put("MDATA", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2573"));
		return new ModelAndView("/pa/insurance/viewImportExcelTempISParamDataList",modelMap);
	}
	
	/**
	 * 工资基础项目数据导出临时数据
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewImportISParamDataListExcel")
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
		List iSParamDataTempList = insuranceInputItemSer.getImportExcelTempISParamDataList(request);
		for(int i=0;i<iSParamDataTempList.size();i++){
			LinkedHashMap map = new LinkedHashMap();
			LinkedHashMap map1 = (LinkedHashMap)iSParamDataTempList.get(i);
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
	@RequestMapping(value = "/createImportISParamDataListExcel")
	@ResponseBody
	public int createImportISParamDataListExcel(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap) throws Exception{
		String result = insuranceInputItemSer.importISParamDataExcelExcel(request);
		
		return result.equals("OK")?1:0;
	}
}

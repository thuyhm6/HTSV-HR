package com.ait.pa.action.bonus;

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
import org.springframework.web.portlet.ModelAndView;

import com.ait.pa.service.bonus.BonusInputItemDataSer;
import com.ait.pa.service.bonus.BonusInputItemParamSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: BonusInputItemDataCtroller.java
 * @Description:
 * @Create date: 2012-1-30 下午04:59:08
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/bonus")
public class BonusInputItemDataCtroller {
	Logger logger = Logger.getLogger(BonusInputItemCtroller.class);

	@Autowired
	private BonusInputItemParamSer bonusInputItemParamSer;

	@Autowired
	private BonusInputItemDataSer bonusInputItemDataSer;

	@Autowired
	private CompanySer companySer;
	
	@Autowired
	private ToolMenuSer toolMenuSer;

	/**
	 * 跳转到奖金输入项目参数页面以及检索指定工资月和公司法人的输入项目参数
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewBonusInputItemData")
	public ModelAndView viewBonusInputItemData(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap.put("cpnyList", companySer.getCompanyItemAllList(request));

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID") == null ? admin
				.getCpnyId() : paramMap.get("CPNY_ID").toString());

		List paramList = this.bonusInputItemParamSer
				.viewBonusInputItemParamListForData(request);
		modelMap.put("paramList", paramList);

		return new ModelAndView("/pa/bonus/viewBonusInputItemData", modelMap);
	}

	/**
	 * 根据输入项目项目号PARAM_NO查询出BN_PARAM_DATA表里面的记录
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewBonusInputItemDataList")
	public ModelAndView viewBonusInputItemDataList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List bonusInputItemDataList = this.bonusInputItemDataSer
				.getPaBonusInputItemDataList(request);

		int bonusComputeItemParamCnt = this.bonusInputItemDataSer
				.getPaBonusInputItemDataCnt(request);

		Object bonusInputItemParamInfo = this.bonusInputItemDataSer
				.getPaBonusInputItemParamInfo(request);
		modelMap.put("bonusInputItemParamInfo", bonusInputItemParamInfo);
		modelMap.put("PARAM_NO", request.getParameter("seach_PARAM_NO"));
		modelMap.put("bonusInputItemDataList", bonusInputItemDataList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, bonusComputeItemParamCnt);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2575")) ;

		modelMap.put("bonusBasicYear", request.getParameter("bonusBasicYear") == null ? "" : request.getParameter("bonusBasicYear"));
		modelMap.put("bonusBasicMonth", request.getParameter("bonusBasicMonth") == null ? "" : request.getParameter("bonusBasicMonth"));
		String type="";
		
		if(request.getParameter("viewBonusInputItemDataList_pa0610")!=null&&!request.getParameter("viewBonusInputItemDataList_pa0610").equals("")){
			type=request.getParameter("viewBonusInputItemDataList_pa0610");
			modelMap.put("type", type);
		}
		modelMap.put("numPerPage", request.getParameter("numPerPage"));
		return new ModelAndView("/pa/bonus/viewBonusInputItemDataList",
				modelMap);
	}

	/**
	 * 跳转到奖金输入项目数据增加页面
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addBonusInputItemDataView")
	public ModelAndView addBonusInputItemDataView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap bnInputItemParamInfo = (LinkedHashMap) this.bonusInputItemDataSer
				.getPaBonusInputItemParamInfo(request);
		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(bnInputItemParamInfo
				.get("DISTINCT_FIELD"));
		String distinctField2 = ObjectUtils.toString(bnInputItemParamInfo
				.get("DISTINCT_FIELD_2ND"));
		if (!distinctField.equals("PERSON_ID")) {
			this.bonusInputItemDataSer.createAddBonusInputItemDataInfo(request);
			List distinctList = this.bonusInputItemDataSer
					.getBnParamDataList(request);
			modelMap.put("distinctList", distinctList);
			if (!"".equals(distinctField2)) {
				List distinctList2 = this.bonusInputItemDataSer
						.getBnParamDataTwoList(request);
				modelMap.put("distinctList2", distinctList2);
			}
		}

		Object bonusInputItemParamInfo = this.bonusInputItemDataSer
				.getPaBonusInputItemParamInfo(request);

		modelMap.put("PARAM_NO", request.getParameter("seach_PARAM_NO"));
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));
		modelMap.put("bonusInputItemParamInfo", bonusInputItemParamInfo);
		modelMap.put("CPNY_ID", admin.getCpnyId());
		return new ModelAndView("/pa/bonus/addBonusInputItemDataView", modelMap);
	}

	/**
	 * 处理奖金输入项目数据增加请求
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addBonusInputItemDataInfo")
	@ResponseBody
	public Map<String, Object> addBonusInputItemDataInfo(
			HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		Object insuranceInputItemParamInfo = this.bonusInputItemDataSer
				.getPaBonusInputItemParamInfo(request);
		try {
			if (((Map) insuranceInputItemParamInfo).get("DISTINCT_FIELD")
					.equals("PERSON_ID")) {
				int result = this.bonusInputItemDataSer
						.addBonusInputItemDataInfo(request);
				if (result == 0) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_success", request));
					map.put("rel", "viewBonusInputItemData");
				} else if (result == 2) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.pa.bonus.thisDataIsExist", request));
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));
				}
			} else {
				int result = this.bonusInputItemDataSer
						.addBonusInputItemOtherDataInfo(request);
				if (result == 0) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_success", request));
					map.put("rel", "viewBonusInputItemData");
				} else if (result == 2) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.pa.bonus.thisDataIsExist", request));
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
	 * 跳转到奖金输入项目数据修改页面
	 * 
	 * @param List
	 * @return
	 */
	@RequestMapping(value = "/updateBonusInputItemDataView")
	public ModelAndView updateBonusInputItemDataView(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		Object bonusInputItemDataInfo = this.bonusInputItemDataSer
				.getPaBonusInputItemDataInfo(request);

		modelMap.put("bonusInputItemDataInfo", bonusInputItemDataInfo);

		return new ModelAndView("/pa/bonus/updateBonusInputItemDataView",
				modelMap);
	}

	/**
	 * 处理奖金输入项目数据修改请求
	 * 
	 * @param List
	 * @return
	 */
	@RequestMapping(value = "/updateBonusInputItemDataInfo")
	@ResponseBody
	public Map<String, Object> updateBonusInputItemDataInfo(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (this.bonusInputItemDataSer
					.updatePaBonusInputItemDataInfo(request) == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("rel", "viewBonusInputItemData");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.pa.bonus.noThisData", request));
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.pa.bonus.noThisData", request));
		}
		return map;
	}

	/**
	 * 处理奖金输入项目数据单个删除请求
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteBonusInputItemDataInfo")
	@ResponseBody
	public Map deleteBonusInputItemDataInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 查看数否有该条奖金输入项目
			String type = request.getParameter("type");
			if ("1".equals(type)) {
				int errorInt = this.bonusInputItemDataSer
						.checkBonusInputItemDataInfoType(request);
				if (errorInt > 0) {
					this.bonusInputItemDataSer
							.deletePaBonusInputItemDataInfoType(request);
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("rel", "viewBonusInputItemData");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.pa.bonus.databaseHasNoThisRecord",
							request));
				}
			} else {
				int errorInt = this.bonusInputItemDataSer
						.checkBonusInputItemDataInfo(request);

				if (errorInt > 0) {
					this.bonusInputItemDataSer
							.deletePaBonusInputItemDataInfo(request);
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("rel", "viewBonusInputItemData");
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
	 * 处理奖金输入项目数据批量删除请求
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteBonusInputItemDataBatchInfo")
	@ResponseBody
	public Map deleteBonusInputItemDataBatchInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String type = request.getParameter("type");
			if ("1".equals(type)) {
				int count = this.bonusInputItemDataSer
						.deletePaBonusInputItemDataBatchInfoType(request);
				if (count == 0) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("navTabId", "pa0610");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.pa.bonus.databaseHasNoThisRecord",
							request));
				}
			} else {
				int count = this.bonusInputItemDataSer
						.deletePaBonusInputItemDataBatchInfo(request);
				if (count == 0) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("navTabId", "pa0610");
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
	 * 输入项目数据个人别录入--员工查询列表,并在人名下面链接到个人项目数据录入
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewBonusInputItemDataPersonList")
	public ModelAndView viewBonusInputItemDataPersonList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		List bonusPersonList = this.bonusInputItemDataSer
				.getBonusInputItemDataPersonList(request);

		int bonusPersonListCnt = this.bonusInputItemDataSer
				.getBonusInputItemDataPersonCnt(request);

		modelMap.put("PA_MONTH", request.getParameter("PA_MONTH"));
		modelMap.put("bonusPersonList", bonusPersonList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, bonusPersonListCnt);

		return new ModelAndView("/pa/bonus/viewBonusInputItemDataPersonList",
				modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addBonusPersonalInputView")
	public ModelAndView viewbonusPersonalInputView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List bonusPersonList = this.bonusInputItemDataSer
				.getAddBonusPersonalInputList(request);

		int bonusPersonListCnt = this.bonusInputItemDataSer
				.getAddBonusPersonalInputListCnt(request);
		List bonusPersonInfo = this.bonusInputItemDataSer
				.getBonusInputItemDataPersonList(request);
		LinkedHashMap returnObj = new LinkedHashMap();
		if (bonusPersonInfo.size() > 0) {
			returnObj = (LinkedHashMap) bonusPersonInfo.get(0);
		}

		modelMap.put("bonusPersonInfo", returnObj);
		modelMap.put("bonusPersonList", bonusPersonList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, bonusPersonListCnt);

		return new ModelAndView("/pa/bonus/addBonusPersonalInputView", modelMap);
	}

	/**
	 * 处理奖金输入项目数据修改请求
	 * 
	 * @param List
	 * @return
	 */
	@RequestMapping(value = "/updateBonusInputItemDataPersonInfo")
	@ResponseBody
	public Map<String, Object> updateBonusInputItemDataPersonInfo(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (this.bonusInputItemDataSer
					.updateBonusInputItemDataPersonInfo(request) == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("navTabId", "pa0610");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.pa.bonus.noThisData", request));
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

}
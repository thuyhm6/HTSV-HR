package com.ait.pa.action.insurance;

import java.util.HashMap;
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
import com.ait.pa.service.insurance.InsuranceComputeItemSer;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.UiUtil;

/**
 * addInsuranceComputeItemInfo Copyright: LDCC Company: LDCC
 * 
 * @fileName: InsuranceComputeItemCtroller.java
 * @Description:
 * @Create date: 2012-1-16 下午06:50:57
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/insurance")
public class InsuranceComputeItemCtroller {
	Logger logger = Logger.getLogger(InsuranceComputeItemCtroller.class);

	@Autowired
	private InsuranceComputeItemSer insuranceComputeItemSer;

	@Autowired
	private CompanySer companySer;

	@Autowired
	private ToolMenuSer toolMenuSer;

	/**
	 * 查看保险计算项目（view Insurance Compute Item）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewInsuranceComputeItem")
	public ModelAndView viewInsuranceComputeItemList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		List insuranceComputeItemList = this.insuranceComputeItemSer
				.getInsuranceComputeItemList(request);
		int insuranceComputeItemCnt = this.insuranceComputeItemSer
				.getInsuranceComputeItemCnt(request);

		modelMap.put("itemList", insuranceComputeItemList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, insuranceComputeItemCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2547"));

		return new ModelAndView("/pa/insurance/viewInsuranceComputeItem",
				modelMap);
	}

	/**
	 * 跳转添加保险计算项目页面（show add Insurance Compute Item View）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addInsuranceComputeItemView")
	public ModelAndView addInsuranceComputeItemView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		return new ModelAndView("/pa/insurance/addInsuranceComputeItemView",
				modelMap);
	}

	/**
	 * 添加保险计算项目信息（add Insurance Compute Item Info）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addInsuranceComputeItemInfo")
	@ResponseBody
	public Map<String, Object> addInsuranceComputeItemInfo(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int errorNum = this.insuranceComputeItemSer
				.checkAddInsuranceComputeItemInfo(request);
		if (errorNum == 0) {
			this.insuranceComputeItemSer.addInsuranceComputeItemInfo(request);
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));
			map.put("navTabId", "pa0403");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.ID_conflict", request));
		}
		return map;
	}

	/**
	 * 跳转到修改保险计算项目页面（show update Insurance Compute Item View）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateInsuranceComputeItemView")
	public ModelAndView updateInsuranceComputeItemView(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		Object insuranceComputeItemInfo = this.insuranceComputeItemSer
				.getInsuranceComputeItemInfo(request);

		modelMap.put("insuranceComputeItemInfo", insuranceComputeItemInfo);

		return new ModelAndView("/pa/insurance/updateInsuranceComputeItemView",
				modelMap);
	}

	/**
	 * 修改保险计算项目信息（update Insurance Compute Item Info）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateInsuranceComputeItemInfo")
	@ResponseBody
	public Map<String, Object> updateInsuranceComputeItemInfo(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int errorNum = this.insuranceComputeItemSer
				.checkAddInsuranceComputeItemInfo(request);
		if (errorNum == 0) {
			if (this.insuranceComputeItemSer
					.updateInsuranceComputeItemInfo(request) == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("navTabId", "pa0403");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.ID_update_conflict", request));
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.ID_update_conflict", request));
		}
		return map;
	}

	/**
	 * 删除保险计算项目信息（delete Insurance Compute Item Info）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteInsuranceComputeItemInfo")
	@ResponseBody
	public Map<String, Object> checkDeleteInsuranceComputeItemInfo(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> jo = new HashMap<String, Object>();
		int errorNum = this.insuranceComputeItemSer
				.checkDeleteInsuranceComputeItemInfo(request);
		if (errorNum > 0) {
			jo.put("statusCode", "300");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.delete_info_use", request));
		} else {
			this.insuranceComputeItemSer
					.deleteInsuranceComputeItemInfo(request);
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));
			jo.put("navTabId", "pa0403");
		}
		return jo;
	}

	/**
	 * 修改保险计算项目计算顺序（update Insurance Compute Item Info CalOrder）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateInsuranceComputeItemInfoCalOrder")
	@ResponseBody
	public String updateInsuranceComputeItemInfoCalOrder(
			HttpServletRequest request) throws Exception {

		String returnString = "Y";

		this.insuranceComputeItemSer
				.updateInsuranceComputeItemInfoCalOrder(request);

		return returnString;
	}

	/**
	 * 修改保险计算项目计算顺序（update ICInfo By Calcu Order）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateICInfoByCalcuOrder")
	@ResponseBody
	public Map<String, Object> updateICInfoByCalcuOrder(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		int a = Integer.parseInt(request.getParameter("type"));
		String param_no = request.getParameter("param_no");
		String calcu_order = request.getParameter("calcu_order");
		int type = 0;

		// 1是up 0是down
		if (a == 0) {
			type = 0;
		} else {
			type = 1;
		}

		int c = this.insuranceComputeItemSer.updateICInfoByCalcuOrder(request,
				type, param_no, calcu_order);
		int d = this.insuranceComputeItemSer.updateICInfoByItemNo(request,
				type, param_no, calcu_order);

		Map<String, Object> jo = new HashMap<String, Object>();
		if (c + d == 2) {
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			jo.put("navTabId", "pa0407");
		} else {
			jo.put("statusCode", "300");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}

		return jo;

	}

	/**
	 * 跳转保险计算项目参数页面（view Insurance Compute Item Param）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewInsuranceComputeItemParam")
	public ModelAndView viewInsuranceComputeItemParamList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		List isItemPramList = this.insuranceComputeItemSer
				.getInsuranceComputeItemParamList(request);
		// int isItemPramListCnt =
		// this.insuranceComputeItemSer.getInsuranceComputeItemParamCnt(request);

		modelMap.put("isItemPramList", isItemPramList);
		// modelMap.put(UiUtil.TOTAL_COUNT_NAME, isItemPramListCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2578"));

		return new ModelAndView("/pa/insurance/viewInsuranceComputeItemParam",
				modelMap);
	}

	/**
	 * 跳转到添加保险计算项目参数页面（show add Insurance Compute Item Param View）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addInsuranceComputeItemParamView")
	public ModelAndView addInsuranceInputItemParamView(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		List insuranceComputeItemList = this.insuranceComputeItemSer
				.getInsuranceComputeItemList(request);
		modelMap.put("itemList", insuranceComputeItemList);
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));
		return new ModelAndView(
				"/pa/insurance/addInsuranceComputeItemParamView", modelMap);
	}

	/**
	 * 添加保险计算项目参数信息（add Insurance Compute Item Param Info）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addInsuranceComputeItemParamInfo")
	@ResponseBody
	public Map<String, Object> addInsuranceComputeItemParamInfo(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int errorNum = this.insuranceComputeItemSer
				.checkAddInsuranceComputeItemParamInfo(request);
		if (errorNum == 0) {
			if (this.insuranceComputeItemSer
					.addInsuranceComputeItemParamInfo(request) == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));
				map.put("navTabId", "pa0407");
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
	 * 跳转到修改保险计算项目参数页面（update Insurance Compute Item Param View）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateInsuranceComputeItemParamView")
	public ModelAndView upInsuranceComputeItemParamView(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		Object isComputeItemParam = this.insuranceComputeItemSer
				.getInsuranceComputeItemParamInfo(request);
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));
		modelMap.put("isComputeItemParam", isComputeItemParam);

		return new ModelAndView(
				"/pa/insurance/updateInsuranceComputeItemParamView", modelMap);
	}

	/**
	 * 修改保险计算项目参数信息（update Insurance Compute ItemParam Info）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateInsuranceComputeItemParamInfo")
	@ResponseBody
	public Map<String, Object> updateInsuranceComputeItemParamInfo(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		// int errorNum =
		// this.insuranceComputeItemSer.checkAddInsuranceComputeItemParamInfo(request);
		// if (errorNum == 0) {
		int returnNum = this.insuranceComputeItemSer
				.updateInsuranceComputeItemParamInfo(request);
		if (returnNum == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "pa0407");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		// } else {
		// map.put("statusCode", "300");
		// map.put("message", "修改失败");
		// }
		return map;
	}

	/**
	 * 删除保险计算项目参数信息（delete Insurance Compute Item Param Info）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteInsuranceComputeItemParamInfo")
	@ResponseBody
	public Map<String, Object> deleteInsuranceComputeItemParamInfo(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int errorNum = this.insuranceComputeItemSer
				.checkDeleteInsuranceComputeItemParamInfo(request);
		if (errorNum > 0) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_info_use", request));
		} else {
			int resultNum = this.insuranceComputeItemSer
					.deleteInsuranceComputeItemParamInfo(request);
			if (resultNum == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_success", request));
				map.put("navTabId", "pa0407");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_fail", request));
			}
		}
		return map;
	}
}
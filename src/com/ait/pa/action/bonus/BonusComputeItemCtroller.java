package com.ait.pa.action.bonus;

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
import com.ait.pa.service.bonus.BonusComputeItemParamSer;
import com.ait.pa.service.bonus.BonusComputeItemSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: BonusComputeItemCtroller.java
 * @Description:
 * @Create date: 2012-1-13 下午03:58:43
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/bonus")
public class BonusComputeItemCtroller {
	Logger logger = Logger.getLogger(BonusComputeItemCtroller.class);

	@Autowired
	private BonusComputeItemSer bonusComputeItemSer;
	@Autowired
	private BonusComputeItemParamSer bonusComputeItemParamSer;

	@Autowired
	private ToolMenuSer toolMenuSer;

	/**
	 * 跳转到奖金计算项目列表页面（Jump to the calculation of bonus items list page）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewBonusComputeItem")
	public ModelAndView viewBonusComputeItemList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List bonusComputeItemList = this.bonusComputeItemSer
				.getBonusComputeItemNoParamList(request);

		int bonusComputeItemCnt = this.bonusComputeItemSer
				.getBonusComputeItemNoParamCnt(request);

		modelMap.put("itemList", bonusComputeItemList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, bonusComputeItemCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2372"));

		return new ModelAndView("/pa/bonus/viewBonusComputeItem", modelMap);
	}

	/**
	 * 跳转到奖金计算项目增加页面(Jump to the bonus calculation project to increase the page)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addBonusComputeItemView")
	public ModelAndView addBonusComputeItemView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		return new ModelAndView("/pa/bonus/addBonusComputeItemView", modelMap);
	}

	/**
	 * 处理奖金计算项目增加请求(Processing calculation of bonus items to increase request)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addBonusComputeItemInfo")
	@ResponseBody
	public Map<String, Object> addBonusComputeItemInfo(
			HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int errorInt = this.bonusComputeItemSer
				.checkAddBonusComputeItemInfo(request);
		if (errorInt == 0) {
			int result = this.bonusComputeItemSer
					.addBonusComputeItemInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));
				map.put("navTabId", "pa0604");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_fail", request));
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.pa.bonus.bonusInputNotRepeat", request));
		}
		return map;
	}

	/**
	 * 跳转到奖金计算项目修改页面(Jump to the bonus calculation program to modify the page)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateBonusComputeItemView")
	public ModelAndView updateBonusComputeItemView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Object bonusComputeItemInfo = this.bonusComputeItemSer
				.getBonusComputeItemInfo(request);
		modelMap.put("bonusComputeItemInfo", bonusComputeItemInfo);
		return new ModelAndView("/pa/bonus/updateBonusComputeItemView",
				modelMap);
	}

	/**
	 * 处理奖金计算项目修改请求(Processing calculation of bonus project change request)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateBonusComputeItemInfo")
	@ResponseBody
	public Map<String, Object> updateBonusComputeItemInfo(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int errorNum = this.bonusComputeItemSer
				.checkAddBonusComputeItemInfo(request);
		if (errorNum == 0) {
			int result = this.bonusComputeItemSer
					.updateBonusComputeItemInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("navTabId", "pa0604");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_fail", request));
			}
		} else {
			int result = this.bonusComputeItemSer
					.updateBonusComputeItemInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("navTabId", "pa0604");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.ID_update_conflict", request));// 修改失败，项目ID重复
			}
		}
		return map;
	}

	/**
	 * 处理奖金计算项目删除请求(Processing calculation of bonus item delete request)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteBonusComputeItemInfo")
	@ResponseBody
	public Map deleteBonusComputeItemInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		// 查看数否有该条奖金输入项目
		int errorInt = this.bonusComputeItemSer
				.checkDeleteBonusComputeItemInfo(request)// 检查是否被bn_summary_+“公司法人”引用数据
				+ this.bonusComputeItemParamSer
						.checkBonusComputeItemParamCanBeDeleted(request);// 看是否被计算项目参数引用

		if (errorInt == 0) {
			int result = this.bonusComputeItemSer
					.deleteBonusComputeItemInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_success", request));
				map.put("navTabId", "pa0604");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_fail", request));
			}

		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.pa.bonus.thisRecordIsRelatedWithOther",
					request));
		}
		return map;
	}
}
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
import com.ait.pa.service.bonus.BonusInputItemParamSer;
import com.ait.pa.service.bonus.BonusInputItemSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: BonusInputItemCtroller.java
 * @Description: BonusInputItemCtroller
 * @Create date: 2012-1-13 下午05:33:00
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/bonus")
public class BonusInputItemCtroller {
	Logger logger = Logger.getLogger(BonusInputItemCtroller.class);

	@Autowired
	private BonusInputItemSer bonusInputItemSer;

	@Autowired
	private BonusInputItemParamSer bonusInputItemParamSer;

	@Autowired
	private ToolMenuSer toolMenuSer;

	/**
	 * 跳转到奖金输入项目列表页面（Jump to the bonus input item list page）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewBonusInputItem")
	public ModelAndView viewBonusInputItemList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		// 获得奖金输入项目列表
		List bonusInputItemList = this.bonusInputItemSer
				.getPaBonusInputItemList(request);

		// 获得奖金输入项目总条数
		int paInputItemCnt = this.bonusInputItemSer
				.getPaBonusInputItemCnt(request);
		modelMap.put("itemList", bonusInputItemList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paInputItemCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2371"));
		return new ModelAndView("/pa/bonus/viewBonusInputItem", modelMap);
	}

	/**
	 * 跳转到奖金输入项目增加页面（Jump to the bonus input increase project page）
	 * 
	 * @param List
	 * @return
	 */
	@RequestMapping(value = "/addBonusInputItemView")
	public ModelAndView addBonusInputItemView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return new ModelAndView("/pa/bonus/addBonusInputItemView", modelMap);
	}

	/**
	 * 处理奖金输入项目增加请求（Processing input project bonus increase request）
	 * 
	 * @param List
	 * @return
	 */
	@RequestMapping(value = "/addBonusInputItemInfo")
	@ResponseBody
	public Map<String, Object> addBonusInputItemInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int errorInt = this.bonusInputItemSer
				.checkAddPaBonusInputItemInfo(request);

		if (errorInt == 0) {
			int result = this.bonusInputItemSer
					.addPaBonusInputItemInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));
				map.put("navTabId", "pa0603");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_fail", request));
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage
					.getTipMessage(
							"alert.message.pa.bonus.update_fail_itemIdIsExist",
							request));
		}
		return map;
	}

	/**
	 * 跳转到奖金输入项目修改页面（Jump to the bonus input project to modify the page）
	 * 
	 * @param List
	 * @return
	 */
	@RequestMapping(value = "/updateBonusInputItemView")
	public ModelAndView updateBonusInputItemView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Object bonusInputItemInfo = this.bonusInputItemSer
				.getPaBonusInputItemInfo(request);

		modelMap.put("bonusInputItemInfo", bonusInputItemInfo);

		return new ModelAndView("/pa/bonus/updateBonusInputItemView", modelMap);
	}

	/**
	 * 处理奖金输入项目修改请求（Processing bonus input project change request）
	 * 
	 * @param List
	 * @return
	 */
	@RequestMapping(value = "/updateBonusInputItemInfo")
	@ResponseBody
	public Map<String, Object> updateBonusInputItemInfo(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int errorInt = this.bonusInputItemSer
				.checkAddPaBonusInputItemInfo(request);
		if (errorInt == 0) {
			int result = this.bonusInputItemSer
					.updatePaBonusInputItemInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("navTabId", "pa0603");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_fail", request));
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage
					.getTipMessage(
							"alert.message.pa.bonus.update_fail_itemIdIsExist",
							request));
		}

		return map;
	}

	/**
	 * 处理奖金输入项目删除请求（Processing bonus input item deletion request）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteBonusInputItemInfo")
	@ResponseBody
	public Map deleteBonusInputItemInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		// 查看数否有该条奖金输入项目
		int errorInt = this.bonusInputItemSer
				.checkCountPaBonusInputItemByParamItemNo(request);
		int returnCount = this.bonusInputItemParamSer
				.checkPaBonusInputItemParamByParamItemNo(request);
		if (errorInt > 0) {
			if (returnCount == 0) {
				int result = this.bonusInputItemSer
						.deletePaBonusInputItemInfo(request);
				if (result == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("navTabId", "pa0603");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_fail", request));
				}
			} else {
				map.put("statusCode", "300");
				map
						.put(
								"message",
								TipMessage
										.getTipMessage(
												"alert.message.pa.bonus.inputItemChangedToInputParamRelate",
												request));
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.pa.bonus.databaseHasNoThisRecord", request));
		}
		return map;
	}
}
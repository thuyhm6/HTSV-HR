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
import com.ait.pa.service.bonus.BonusTypeSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: BonusComputeItemParamCtroller.java
 * @Description:
 * @Create date: 2012-1-16 下午01:13:57
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/bonus")
public class BonusComputeItemParamCtroller {
	Logger logger = Logger.getLogger(BonusComputeItemCtroller.class);

	@Autowired
	private BonusComputeItemParamSer bonusComputeItemParamSer;

	@Autowired
	private CompanySer companySer;

	@Autowired
	private BonusComputeItemSer bonusComputeItemSer;

	@Autowired
	private ToolMenuSer toolMenuSer;
	
	@Autowired
	private BonusTypeSer bonusTypeSer;
	/**
	 * 跳转到奖金计算项目列表页面
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewBonusComputeItemParam")
	public ModelAndView viewBonusComputeItemParamList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		// 获得公司法人下拉列表需要的数据
		modelMap.put("cpnyList", companySer.getCompanyItemAllList(request));

		List bonusComputeItemParamList = this.bonusComputeItemParamSer
				.getBonusComputeItemParamList(request);

		// int bonusComputeItemParamCnt =
		// this.bonusComputeItemParamSer.getBonusComputeItemParamCnt(request);

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID") == null ? admin
				.getCpnyId() : paramMap.get("CPNY_ID").toString());
		modelMap.put("itemList", bonusComputeItemParamList);
		// modelMap.put(UiUtil.TOTAL_COUNT_NAME, bonusComputeItemParamCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2576"));

		return new ModelAndView("/pa/bonus/viewBonusComputeItemParam", modelMap);
	}

	/**
	 * 跳转到奖金计算项目增加页面
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addBonusComputeItemParamView")
	public ModelAndView addBonusComputeItemView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// 获得公司法人下拉列表需要的数据
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));

		List bonusComputeItemList = this.bonusComputeItemSer
				.getBonusComputeItemNoParamList(request);
		modelMap.put("bonusComputeItemList", bonusComputeItemList);

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID") == null ? admin
				.getCpnyId() : paramMap.get("CPNY_ID").toString());

		return new ModelAndView("/pa/bonus/addBonusComputeItemParamView",
				modelMap);
	}

	/**
	 * 处理奖金计算项目增加请求
	 * 
	 * @param List
	 * @return
	 */
	@RequestMapping(value = "/addBonusComputeItemParamInfo")
	@ResponseBody
	public Map<String, Object> addBonusComputeItemParamInfo(
			HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int errorInt = this.bonusComputeItemParamSer
				.checkAddBonusComputeItemParamInfo(request);
		if (errorInt == 0) {
			int result = this.bonusComputeItemParamSer
					.addBonusComputeItemParamInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));
				map.put("navTabId", "pa0609");
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
	 * 跳转到奖金计算项目修改页面
	 * 
	 * @param List
	 * @return
	 */
	@RequestMapping(value = "/updateBonusComputeItemParamView")
	public ModelAndView updateBonusComputeItemParamView(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		// 获得公司法人下拉列表需要的数据
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));
		// 获得奖金类型下拉列表需要的数据
		modelMap.put("bonusList", bonusTypeSer.getBonusTypeList(request));
		
		Object bonusComputeItemParamInfo = this.bonusComputeItemParamSer
				.getBonusComputeItemParamInfo(request);
		
		modelMap.put("bonusComputeItemParamInfo", bonusComputeItemParamInfo);

		return new ModelAndView("/pa/bonus/updateBonusComputeItemParamView",
				modelMap);
	}

	/**
	 * 处理奖金计算项目修改请求
	 * 
	 * @param List
	 * @return
	 */
	@RequestMapping(value = "/updateBonusComputeItemParamInfo")
	@ResponseBody
	public Map<String, Object> updateBonusComputeItemParamInfo(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int errorInt = this.bonusComputeItemParamSer
				.checkAddBonusComputeItemParamInfo(request);
		try {
			if (errorInt == 0) {
				this.bonusComputeItemParamSer
						.updateBonusComputeItemParamInfo(request);
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("navTabId", "pa0609");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.pa.bonus.bonusInputNotRepeat", request));
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 处理奖金计算项目删除请求
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteBonusComputeItemParamInfo")
	@ResponseBody
	public Map deleteBonusComputeItemParamInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查看数否有该条奖金输入项目
		int errorInt = this.bonusComputeItemParamSer
				.checkDeleteBonusComputeItemParamInfo(request);

		if (errorInt == 0) {
			int result = this.bonusComputeItemParamSer
					.deleteBonusComputeItemParamInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_success", request));
				map.put("navTabId", "pa0609");
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

	/**
	 * 修改计算顺序CALCU_ORDER
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateBonusItemParamCalcuOrder")
	@ResponseBody
	public Map updateBonusItemParamCalcuOrder(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		int type = Integer.parseInt(request.getParameter("type"));
		String Param_no = request.getParameter("param_no");
		String calcu_order = request.getParameter("calcu_order");

		int c = this.bonusComputeItemParamSer.updateBonusItemParamCalcuOrder(
				request, type, Param_no, calcu_order);
		int d = this.bonusComputeItemParamSer
				.updateBonusItemParamCalcuOrderByParamNo(request, type,
						Param_no, calcu_order);

		Map<String, Object> jo = new HashMap<String, Object>();
		if (c + d == 2) {
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			jo.put("navTabId", "pa0609");
		} else {
			jo.put("statusCode", "300");
			jo.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return jo;
	}
}
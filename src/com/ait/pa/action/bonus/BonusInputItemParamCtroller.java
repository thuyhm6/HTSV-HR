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
import com.ait.pa.service.insurance.InsuranceInputItemSer;
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
 * @fileName: BonusInputItemParamCtroller.java
 * @Description:
 * @Create date: 2012-1-13 下午05:33:05
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/bonus")
public class BonusInputItemParamCtroller {
	Logger logger = Logger.getLogger(BonusInputItemCtroller.class);

	@Autowired
	private BonusInputItemParamSer bonusInputItemParamSer;

	@Autowired
	private InsuranceInputItemSer insuranceInputItemSer;

	@Autowired
	private CompanySer companySer;

	@Autowired
	private BonusInputItemSer bonusInputItemSer;

	@Autowired
	private ToolMenuSer toolMenuSer;

	/**
	 * 取得奖金输入参数列表（Get a bonus input parameter list）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewBonusInputItemParam")
	public ModelAndView viewBonusInputItemParamList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// 获得公司法人下拉列表需要的数据
		modelMap.put("cpnyList", companySer.getCompanyItemAllList(request));
		// 获得奖金输入参数的数据
		List bonusInputItemList = this.bonusInputItemParamSer
				.viewBonusInputItemParamList(request);
		// 获得奖金输入参数的数据总条数
		int bonusInputItemCnt = this.bonusInputItemParamSer
				.getPaBonusInputItemParamCnt(request);
		modelMap.put("itemList", bonusInputItemList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, bonusInputItemCnt);

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID") == null ? admin
				.getCpnyId() : paramMap.get("CPNY_ID").toString());
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2577"));

		return new ModelAndView("/pa/bonus/viewBonusInputItemParam", modelMap);
	}

	/**
	 * 跳转到奖金项目参数页面（Jump to the bonus item parameter page）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addBonusInputItemParamView")
	public ModelAndView addBonusInputItemParamView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// 获取奖金输入项目下拉框
		request.setAttribute("BONUS_IMPORT_PARAMETER", "1");//奖金输入参数
		List bonusInputItemList = this.bonusInputItemSer
				.getPaBonusInputItemList(request);
		modelMap.put("bonusInputItemList", bonusInputItemList);
		// 获得区分项目 下拉列表需要的数据
		List distinctFileList = this.insuranceInputItemSer
				.getDistinctFieldList(request);
		modelMap.put("distinctFileList", distinctFileList);
		List conpanyItemList = this.companySer.getCompanyItemList(request);
		// 获得公司法人下拉列表需要的数据
		modelMap.put("cpnyList", conpanyItemList);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID") == null ? admin
				.getCpnyId() : paramMap.get("CPNY_ID").toString());
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/pa/bonus/addBonusInputItemParamView",
				modelMap);
	}

	/**
	 * 处理奖金项目增加请求（Treatment of bonus items that increase request）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addBonusInputItemParamInfo")
	@ResponseBody
	public Map addBonusInputItemParamInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		// 判断在当前项目ID,公司法人,工资月条件下 项目参数的个数
		int errorInt = this.bonusInputItemParamSer
				.checkAddPaBonusInputItemParamInfo(request);
		if (errorInt == 0) {
			// 如果数据库没有记录 添加到数据库里
			int result = this.bonusInputItemParamSer
					.addPaBonusInputItemParamInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));
				map.put("navTabId", "pa0608");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_fail", request));
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.pa.bonus.itemParamterIsExsits", request));
		}
		return map;
	}

	/**
	 * 跳转到奖金项目参数修改页面（Jump to the bonus item parameter modifying page）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateBonusInputItemParamView")
	public ModelAndView updateBonusInputItemParamView(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		// 获得区分项目 下拉列表需要的数据
		List distinctFileList = this.insuranceInputItemSer
				.getDistinctFieldList(request);
		modelMap.put("distinctFileList", distinctFileList);

		// 获得公司法人下拉列表需要的数据
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));

		// 获得要修改的项目参数的对象
		Object bonusInputItemParamInfo = this.bonusInputItemParamSer
				.getPaBonusInputItemParamInfo(request);
		modelMap.put("bonusInputItemParamInfo", bonusInputItemParamInfo);

		return new ModelAndView("/pa/bonus/updateBonusInputItemParamView",
				modelMap);
	}

	/**
	 * 处理奖金项目参数修改请求（Processing bonus item parameter modification request）
	 * 
	 * @param List
	 * @return
	 */
	@RequestMapping(value = "/updateBonusInputItemParamInfo")
	@ResponseBody
	public Map<String, Object> updateBonusInputItemParamInfo(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		// 判断在当前项目ID,公司法人,工资月条件下 项目参数的个数
//		int errorInt = this.bonusInputItemParamSer
//				.checkAddPaBonusInputItemParamInfo(request);
//		if (errorInt == 0) {
			int result = this.bonusInputItemParamSer
					.updatePaBonusInputItemParamInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("navTabId", "pa0608");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_fail", request));
			}
//		} else {
//			map.put("statusCode", "300");
//			map.put("message", TipMessage.getTipMessage(
//					"alert.message.update_fail", request));
//		}

		return map;
	}

	/**
	 * 处理奖金项目参数删除请求（Processing bonus item parameters delete request）
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteBonusInputItemParamInfo")
	@ResponseBody
	public Map deleteBonusInputItemParamInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		// 判断下要删除的数据是否存在
		int errorInt = this.bonusInputItemParamSer
				.checkDeletePaBonusInputItemParamInfo(request);
		if (errorInt > 0) {
			// 如果存在该记录则删除
			int result = this.bonusInputItemParamSer
					.deletePaBonusInputItemParamInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_success", request));
				map.put("navTabId", "pa0608");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_fail", request));
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.itemParamterNotExsits", request));
		}
		return map;
	}
}
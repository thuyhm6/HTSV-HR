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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.pa.service.bonus.BonusTypeSer;
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
 * @fileName: BonusTypeCtroller.java
 * @Description:
 * @Create date: 2012-1-13 下午05:11:34
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/bonus")
public class BonusTypeCtroller {
	Logger logger = Logger.getLogger(BonusTypeCtroller.class);

	@Autowired
	private BonusTypeSer bonusTypeSer;

	@Autowired
	private CompanySer companySer;

	@Autowired
	private ToolMenuSer toolMenuSer;

	/**
	 * 跳转到奖金类型列表页面(Jump to the bonus types list page)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewBonusType")
	public ModelAndView viewBonusTypeList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List bonusTypeList = this.bonusTypeSer.getBonusTypeList(request);
		int bonusTypeCnt = this.bonusTypeSer.getBonusTypeCnt(request);
		modelMap.put("bonusTypeList", bonusTypeList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, bonusTypeCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2370"));

		return new ModelAndView("/pa/bonus/viewBonusType", modelMap);
	}

	/**
	 * 跳转到奖金类型添加页面(Jump to the bonus types add page)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addBonusTypeView")
	public ModelAndView addBonusTypeView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		return new ModelAndView("/pa/bonus/addBonusTypeView", modelMap);
	}

	/**
	 * 处理奖金类型添加的请求(Processing bonus types add request)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addBonusTypeInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBonusTypeInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int errorInt = 0;
		errorInt = this.bonusTypeSer.checkAddBonusTypeByTypeId(request);
		try {
			if (errorInt == 0) {
				this.bonusTypeSer.addBonusTypeInfo(request);
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));
				map.put("navTabId", "pa0601");
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
	 * 跳转到修改奖金类型页面(Jump to modify the bonus type page)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateBonusTypeView")
	public ModelAndView updateBonusTypeView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Object bonusTypeInfo = this.bonusTypeSer.getBonusTypeInfo(request);

		modelMap.put("bonusTypeInfo", bonusTypeInfo);

		return new ModelAndView("/pa/bonus/bonusTypeInfo", modelMap);
	}

	/**
	 * 修改奖金类型（Modified bonus types）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateBonusTypeInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBonusTypeInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.bonusTypeSer.updateBonusTypeInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "pa0601");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 删除奖金类型(Delete bonus types)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteBonusTypeInfo")
	@ResponseBody
	public Map deleteBonusTypeInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int isDelete = this.bonusTypeSer.checkDeleteBonusTypeInfo(request);
		if (isDelete == 0) {
			int result = this.bonusTypeSer.deleteBonusTypeInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_success", request));
				map.put("navTabId", "pa0601");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_fail", request));
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.pa.bonus.messageIsUsing", request));
		}
		return map;
	}

	/**
	 * 跳转到奖金类型参数列表页面(Jump to the bonus type parameter list page)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewBonusTypeParam")
	public ModelAndView viewBonusTypeParamList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// 获得公司法人下拉列表需要的数据
		modelMap.put("cpnyList", companySer.getCompanyItemAllList(request));

		List bonusTypeParamList = this.bonusTypeSer
				.getBonusTypeParamList(request);
		int bonusTypeParamCnt = this.bonusTypeSer.getBonusTypeParamCnt(request);

		modelMap.put("bonusTypeParamList", bonusTypeParamList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, bonusTypeParamCnt);

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID") == null ? admin
				.getCpnyId() : paramMap.get("CPNY_ID").toString());
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2574"));

		return new ModelAndView("/pa/bonus/viewBonusTypeParam", modelMap);
	}

	/**
	 * 跳转到奖金类型参数添加页面(Jump to the bonus type parameters add page)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addBonusTypeParamView")
	public ModelAndView addBonusTypeParamView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// 获得公司法人下拉列表需要的数据
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));
		List bonusTypeList = this.bonusTypeSer.getBonusTypeList(request);
		modelMap.put("bonusTypeList", bonusTypeList);

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID") == null ? admin
				.getCpnyId() : paramMap.get("CPNY_ID").toString());

		return new ModelAndView("/pa/bonus/addBonusTypeParamView", modelMap);
	}

	/**
	 * 处理奖金类型参数添加的请求(Processing parameters of bonus types add request)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addBonusTypeParamInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBonusTypeParamInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int errorInt = this.bonusTypeSer.checkAddBonusTypeParamInfo(request);
		if (errorInt == 0) {
			int result = this.bonusTypeSer.addBonusTypeParamInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));
				map.put("navTabId", "pa0611");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_fail", request));
			}

		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.pa.bonus.updateBonusTypeCanNotBeRepeat",
					request));
		}
		return map;
	}

	/**
	 * 跳转到修改奖金类型参数页面(Jump to page modified bonus type parameters)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateBonusTypeParamView")
	public ModelAndView updateBonusTypeParamView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// 获得公司法人下拉列表需要的数据
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));

		Object bonusTypeParamInfo = this.bonusTypeSer
				.getBonusTypeParamInfo(request);

		modelMap.put("bonusTypeParamInfo", bonusTypeParamInfo);

		return new ModelAndView("/pa/bonus/updateBonusTypeParamView", modelMap);
	}

	/**
	 * 修改奖金类型参数(Modified bonus type parameters)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateBonusTypeParamInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBonusTypeParamInfo(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		int errorInt = this.bonusTypeSer.checkAddBonusTypeParamInfo(request);
		if (errorInt == 0) {
			int result = this.bonusTypeSer.updateBonusTypeParamInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.pa.bonus.updateBonusType_success",
						request));
				map.put("navTabId", "pa0611");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
								"alert.message.pa.bonus.updateBonusType_fail",
								request));
			}

		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.pa.bonus.updateBonusTypeCanNotBeRepeat",
					request));
		}
		return map;
	}

	/**
	 * 根据奖金类型ID删除奖金类型参数(According to the bonus types ID delete bonus type
	 * parameters)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteBonusTypeParamInfo")
	@ResponseBody
	public Map deleteBonusTypeParamInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.bonusTypeSer.deleteBonusTypeParamInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));
			map.put("navTabId", "pa0611");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));
		}
		return map;
	}
}
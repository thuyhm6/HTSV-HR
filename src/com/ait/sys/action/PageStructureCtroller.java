package com.ait.sys.action;

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

import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.PageStructureSer;
import com.ait.sys.service.SyLanguageSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;

/**
 * Copyright: LDCC (c) Company: LDCC
 * 
 * @fileName PageStructureCtroller.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-4-19 am 11:55:54
 * @version 5.0
 * 
 */
@Controller
@RequestMapping(value = "/sys/pageStructure")
public class PageStructureCtroller {
	Logger logger = Logger.getLogger(CompanyCtroller.class);

	@Autowired
	private PageStructureSer pageStructureSer;

	@Autowired
	private ToolMenuSer toolMenuSer;

	@Autowired
	private SyLanguageSer syLanguageSer;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPageStructureList")
	public ModelAndView viewPageStructureList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List pageStructureList = this.pageStructureSer
				.getPageStructureList(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List languageList = syLanguageSer.getSyLanguageListByActivity();
		modelMap.put("interLanguage", admin.getLanguage());// 当前系统的语言
		modelMap.put("languageList", languageList);// 所有语言状态
		modelMap.put("pageStructureList", pageStructureList);
		return new ModelAndView("/sys/pageStructure/viewPageStructureList",
				modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPsDataList")
	@ResponseBody
	public List getPsDataList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List dataList = this.pageStructureSer.getPsDataList(request);
		return dataList;
	}

	/**
	 * 跳转到新建表的页面(redirect to the page to add a table)
	 * 
	 * @param requ7est
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addNewTableView")
	public ModelAndView addNewTableView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List languageList = syLanguageSer.getSyLanguageListByActivity();
		modelMap.put("interLanguage", admin.getLanguage());// 当前系统的语言
		modelMap.put("languageList", languageList);// 所有语言状态
		return new ModelAndView("/sys/pageStructure/addNewTableView", modelMap);
	}

	/**
	 * 跳转到新建列的页面(redirect to the page to add alias)
	 * 
	 * @param requ7est
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addNewAliasView")
	public ModelAndView addNewAliasView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// List languageList = syLanguageSer.getSyLanguageListByActivity();
		List aliasList = this.pageStructureSer
				.retrieveReportItemListByTableName(request);
		modelMap.put("interLanguage", admin.getLanguage());// 当前系统的语言
		// modelMap.put("languageList",languageList);//所有语言状态
		modelMap.put("aliasList", aliasList);
		modelMap.put("RT_NO", request.getParameter("RT_NO"));
		modelMap.put("ROW_NUM", request.getParameter("ROW_NUM"));
		return new ModelAndView("/sys/pageStructure/addNewAliasView", modelMap);
	}

	@RequestMapping(value = "/addNewAliasInfo")
	@ResponseBody
	public Map<String, Object> addNewAliasInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.pageStructureSer.addNewAliasInfo(request);
		if (result == 1) {
			map.put("navTabId", "sy0305");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));
			map.put("statusCode", "200");
		} else if (result == 2) {
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
			map.put("statusCode", "300");
		} else {
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 跳转到修改列的页面(redirect to the page to add alias)
	 * 
	 * @param requ7est
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateNewAliasView")
	public ModelAndView updateNewAliasView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasItemList = this.pageStructureSer
				.retrieveReportItemList(request);
		modelMap.put("interLanguage", admin.getLanguage());// 当前系统的语言
		modelMap.put("aliasItemList", aliasItemList);
		modelMap.put("RT_NO", request.getParameter("RT_NO"));
		return new ModelAndView("/sys/pageStructure/updateNewAliasView",
				modelMap);
	}

	@RequestMapping(value = "/updateNewAliasInfo")
	@ResponseBody
	public Map<String, Object> updateNewAliasInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.pageStructureSer.updateNewAliasInfo(request);
		if (result == 1) {
			map.put("navTabId", "sy0305");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("statusCode", "200");
		} else if (result == 2) {
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
			map.put("statusCode", "300");
		} else {
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	@RequestMapping(value = "/saveAliasInfo")
	@ResponseBody
	public Map<String, Object> saveAliasInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List languageList = syLanguageSer.getSyLanguageListByActivity();
		int result = this.pageStructureSer.saveAliasInfo(request, languageList);
		if (result == 1) {
			map.put("navTabId", "sy0305");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));
			map.put("statusCode", "200");
		} else if (result == 2) {
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
			map.put("statusCode", "300");
		} else {
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
}
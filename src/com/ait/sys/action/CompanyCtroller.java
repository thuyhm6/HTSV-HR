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
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC (c) Company: LDCC
 * 
 * @fileName CompanyCtroller.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 pm 04:37:54
 * @version 5.0
 * 
 */
@Controller
@RequestMapping(value = "/sys/basicMaintenance")
public class CompanyCtroller {
	Logger logger = Logger.getLogger(CompanyCtroller.class);

	@Autowired
	private CompanySer companySer;

	@Autowired
	private ToolMenuSer toolMenuSer;

	/**
	 * 跳转到法人的查看页面(Jump to company list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewCompany")
	public ModelAndView viewCompanyList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List companyList = this.companySer.getCompanyItemList(request);
		int companyCnt = this.companySer.getCompanyItemCnt(request);
		modelMap.put("searchMap", ObjectBindUtil.getRequestParamData(request));
		modelMap.put("companyList", companyList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, companyCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2565"));
		return new ModelAndView("/sys/basicMaintenance/viewCompany", modelMap);
	}

	/**
	 * 跳转到添加法人的页面(redirect to the page to add a company)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCompanyView")
	public ModelAndView addCompanyItemView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("companyInfo", companySer.getCompanyItemInfo(request));
		modelMap.put("operationList", this.companySer.getHrOpeationList(request));
		return new ModelAndView("/sys/basicMaintenance/addCompanyView",
				modelMap);
	}

	/**
	 * 添加法人(add a company)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addCompanyInfo")
	@ResponseBody
	public Map addCompanyInfo(HttpServletRequest request) throws Exception {
		int checkNum = this.companySer.checkCompanyIdExsit(request);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (checkNum == 0) {
				int result = this.companySer.addCompanyItemInfo(request);
				if (result == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_success", request));
					map.put("navTabId", "sy0450");
					map.put("callbackType", "closeCurrent");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));
				}
			} else {
				map.put("statusCode", "300");
				map
						.put("message", TipMessage.getTipMessage(
								"alert.message.sys.company.companyIdIsExsist",
								request));// "公司ID已存在,请重新输入"
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}

	/**
	 * 跳转到法人更新的页面(redirect to the page to update a company)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCompanyView")
	public ModelAndView updateCompanyView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("companyInfo", companySer.getCompanyItemInfo(request));
		modelMap.put("operationList", this.companySer.getHrOpeationList(request));
		return new ModelAndView("/sys/basicMaintenance/updateCompanyView",
				modelMap);
	}

	/**
	 * 更新法人信息(update the infomation of the company)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateCompanyInfo")
	@ResponseBody
	public Map updatePostInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = companySer.updateCompanyItemInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "sy0450");
			map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 删除选中法人(delete the company checked)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteCompany")
	@ResponseBody
	public Map deleteCompany(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = companySer.deleteCompanyItemInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));
			map.put("navTabId", "sy0450");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));
		}
		return map;
	}
}

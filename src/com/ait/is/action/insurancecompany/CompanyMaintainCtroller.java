package com.ait.is.action.insurancecompany;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.is.service.CompanyMaintainSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: AIT Company: AIT
 * 
 * @fileName: CompanyMaintainCtroller.java
 * @Description: 社会保险===社保公司维护
 * @Create date: 2014-3-4 下午01:55:16
 * @Create by: limeng(liemng@ait.net.cn)
 * @version 5.5
 */
@Controller
@RequestMapping(value="/is/insurancecompany")
public class CompanyMaintainCtroller {

	@Autowired
	private CompanyMaintainSer manageSer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/viewCompanyMaintainList")
	public ModelAndView viewCompanyMaintainList(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			List corpList = manageSer.getIsCorpInfo(request);
			int corpCnt = manageSer.getIsCorpCnt(request);
			modelMap.put("searchMap", ObjectBindUtil.getRequestParamData(request));
			modelMap.put("corpList", corpList);
			modelMap.put("corpCnt", corpCnt);
			modelMap.put("defaultCpny", admin.getCpnyId());
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, corpCnt);
			modelMap.put("toolbarInfo",
					request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "124942"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("/is/insurancesystem/viewCompanyMaintainList",modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/addIsCompanyInfoView")
	public ModelAndView addIsCompanyInfoView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		return new ModelAndView("/is/insurancecompany/addIsCompanyInfoView",modelMap);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addIsCompanyInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map addIsCompanyInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = this.manageSer.addIsCompanyInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));// 添加成功
				map.put("navTabId", "bx0120");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_fail", request));// 保存失败
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateIsCompanyInfoView")
	public ModelAndView updateIsCompanyInfoView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("item", manageSer.getIsCorpInfoByNo(request));
		return new ModelAndView("/is/insurancecompany/updateIsCompanyInfoView", modelMap);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateIsCompanyInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map updateIsCompanyInfo(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = this.manageSer.updateIsCompanyInfo(request) ;
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success", request));// 修改成功
				map.put("navTabId", "bx0120");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.update_fail", request));// 修改失败
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteIsCompanyInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteIsCompanyInfo(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result  = this.manageSer.deleteIsCompanyInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success", request));// 删除成功
				map.put("navTabId", "bx0120");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_fail", request));// 删除失败
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}

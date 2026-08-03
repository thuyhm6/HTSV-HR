package com.ait.ess.action;

import java.util.HashMap;
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

import com.ait.ess.service.HumanAffirmApplySer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: HumanAffirmCtroller.java
 * @Description:人事确认
 * @Create date: Apr 10, 2012 10:01:49 AM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Apr 10, 2012 10:01:49 AM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ess/humanAffirm")
public class HumanAffirmCtroller {

	Logger logger = Logger.getLogger(HumanAffirmCtroller.class);

	@Autowired
	private HumanAffirmApplySer humanAffirmApplySer;

	/**
	 * 个人信息申请(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPersonInfoApplyConfirmList")
	public ModelAndView viewPersonInfoApplyConfirmList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		modelMap.put("personApplyList", humanAffirmApplySer
				.getPersonInfoApplyConfirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, humanAffirmApplySer
				.getPersonInfoApplyConfirmListCnt(request));

		return new ModelAndView(
				"/ess/humanAffirm/viewPersonInfoApplyConfirmList", modelMap);
	}

	/**
	 * 人事确认--批量确认个人信息申请(personnel confirm:batch to confirm personal information
	 * apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmPersonInfoApplyInBatch")
	@ResponseBody
	public Map<String, Object> confirmPersonInfoApplyInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = humanAffirmApplySer
				.savePersonInfoHumanConfirmInBatch(request);
		if (result == 1) {
			map.put("navTabId", "ess0510");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.humanAffirm.confirmPersonApplyInBatch_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.humanAffirm.confirmPersonApplyInBatch_fail",request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 人事确认--个人信息申请(personnel confirm:confirm personal information apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmPersonInfoApply")
	@ResponseBody
	public Map<String, Object> confirmPersonInfoApply(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.humanAffirmApplySer
				.savePersonInfoHumanConfirm(request);
		if (result == 1) {
			map.put("navTabId", "ess0510");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.humanAffirm.confirmPersonApply_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.humanAffirm.confirmPersonApply_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 人事确认--加班申请信息列表(personnel confirm:overtime apply information list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOvertimeApplyConfirmList")
	public ModelAndView viewOvertimeApplyConfirmList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("defaultDaoXiuList", humanAffirmApplySer.getDefaultDaoXiuList(request));
		
		modelMap.put("ovetimeApplyPersonList", humanAffirmApplySer.getOvertimeApplyConfirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, humanAffirmApplySer.getOvertimeApplyConfirmListCnt(request));
		modelMap.put("IF_CONVERT_APPLY", this.humanAffirmApplySer.getIfConverValue(request));

		modelMap.put("defaultCpny", admin.getCpnyId());
		return new ModelAndView("/ess/humanAffirm/viewOvertimeApplyConfirmList", modelMap);
	}

	/**
	 * 人事确认--批量确认加班信息申请(personnel confirm:batch to confirm overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmOvertimeApplyInBatch")
	@ResponseBody
	public Map<String, Object> confirmOvertimeApplyInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = humanAffirmApplySer.saveOvertimeApplyHumanConfirmInBatch(request);
		if (result == 1) {
			map.put("navTabId", "ess0506");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.humanAffirm.confirmOvertimeApplyInBatch_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.humanAffirm.confirmOvertimeApplyInBatch_fail",request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 人事确认--加班信息申请(personnel confirm:confirm overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmOvertimeInfoApply")
	@ResponseBody
	public Map<String, Object> confirmOvertimeInfoApply(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.humanAffirmApplySer.saveOvertimeApplyHumanConfirm(request);
		if (result == 1) {
			map.put("navTabId", "ess0506");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.humanAffirm.confirmOvertimeApply_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.humanAffirm.confirmOvertimeApply_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 人事确认--休假申请列表(personnel confirm:leave apply information list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLeaveApplyConfirmList")
	public ModelAndView viewLeaveApplyConfirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("leaveApplyList", humanAffirmApplySer.getLeaveApplyConfirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, humanAffirmApplySer.getLeaveApplyConfirmListCnt(request));

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		return new ModelAndView("/ess/humanAffirm/viewLeaveApplyConfirmList",modelMap);
	}

	/**
	 * 人事确认--批量确认休假申请(personnel confirm:batch to confirm leave apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmLeaveApplyInBatch")
	@ResponseBody
	public Map<String, Object> confirmLeaveApplyInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = humanAffirmApplySer
				.saveLeaveApplyHumanConfirmInBatch(request);
		if (result == 1) {
			String navTabId=request.getParameter("navTabId");
			map.put("navTabId",navTabId);
			//map.put("navTabId", "ess0507");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.humanAffirm.confirmLeaveApplyInBatch_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.humanAffirm.confirmLeaveApplyInBatch_fail",request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 人事确认--休假申请(personnel confirm:confirm leave apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmLeaveApply")
	@ResponseBody
	public Map<String, Object> confirmLeaveApply(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.humanAffirmApplySer
				.saveLeaveApplyHumanConfirm(request);
		if (result == 1) {
			String navTabId=request.getParameter("navTabId");
			map.put("navTabId",navTabId);
			//map.put("navTabId", "ess0507");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.humanAffirm.confirmLeaveApply_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.humanAffirm.confirmLeaveApply_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 人事确认--出差申请列表(personnel confirm:Evection apply information list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvectionApplyConfirmList")
	public ModelAndView viewEvectionApplyConfirmList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		modelMap.put("evectionApplyPersonList", humanAffirmApplySer
				.getEvectionApplyConfirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, humanAffirmApplySer
				.getEvectionApplyConfirmListCnt(request));

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		return new ModelAndView(
				"/ess/humanAffirm/viewEvectionApplyConfirmList", modelMap);
	}

	/**
	 * 人事确认--批量确认出差申请(personnel confirm:batch to confirm Evection apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmEvectionApplyInBatch")
	@ResponseBody
	public Map<String, Object> confirmEvectionApplyInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = humanAffirmApplySer
				.saveLeaveApplyHumanConfirmInBatch(request);
		if (result == 1) {
			map.put("navTabId", "ess0509");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.humanAffirm.confirmEvectionApplyInBatch_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.humanAffirm.confirmEvectionApplyInBatch_fail",request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 人事确认--出差申请(personnel confirm:confirm Evection apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmEvectionApply")
	@ResponseBody
	public Map<String, Object> confirmEvectionApply(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.humanAffirmApplySer
				.saveLeaveApplyHumanConfirm(request);
		if (result == 1) {
			map.put("navTabId", "ess0509");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.humanAffirm.confirmEvectionApply_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.humanAffirm.confirmEvectionApply_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 人事确认-外出申请列表(personnel confirm:Egression apply information list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEgressionApplyConfirmList")
	public ModelAndView viewEgressionApplyConfirmList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		modelMap.put("egressionApplyPersonList", humanAffirmApplySer
				.getEgressionApplyConfirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, humanAffirmApplySer
				.getEgressionApplyConfirmListCnt(request));

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		return new ModelAndView(
				"/ess/humanAffirm/viewEgressionApplyConfirmList", modelMap);
	}

	/**
	 * 人事确认--批量确认休假申请(personnel confirm:batch to confirm Egression apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmEgressionApplyInBatch")
	@ResponseBody
	public Map<String, Object> confirmEgressionApplyInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = humanAffirmApplySer
				.saveLeaveApplyHumanConfirmInBatch(request);
		if (result == 1) {
			map.put("navTabId", "ess0515");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.humanAffirm.confirmEgressionApplyInBatch_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.humanAffirm.confirmEgressionApplyInBatch_fail",request));
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 人事确认--外出申请(personnel confirm:confirm egression apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmEgressionApply")
	@ResponseBody
	public Map<String, Object> confirmEgressionApply(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.humanAffirmApplySer
				.saveLeaveApplyHumanConfirm(request);
		if (result == 1) {
			map.put("navTabId", "ess0515");
			map.put("message", TipMessage.getTipMessage("alert.message.ess.humanAffirm.confirmEgressionApply_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.humanAffirm.confirmEgressionApply_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}
	/**
	 * 人事确认--喜丧假申请列表(personnel confirm:leave apply information list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLikeLeaveApplyConfirmList")
	public ModelAndView viewLikeLeaveApplyConfirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("leaveApplyList", humanAffirmApplySer.getLeaveApplyConfirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, humanAffirmApplySer.getLeaveApplyConfirmListCnt(request));

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		return new ModelAndView("/ess/humanAffirm/viewLikeLeaveApplyConfirmList",modelMap);
	}
	/**
	 * 人事确认--喜丧假申请列表(personnel confirm:leave apply information list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewWorkDaysApplyConfirmList")
	public ModelAndView viewWorkDaysApplyConfirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("leaveApplyList", humanAffirmApplySer.getLeaveApplyConfirmList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, humanAffirmApplySer.getLeaveApplyConfirmListCnt(request));

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		return new ModelAndView("/ess/humanAffirm/viewWorkDaysApplyConfirmList",modelMap);
	}
}
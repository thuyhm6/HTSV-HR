package com.ait.ess.action;

import java.util.HashMap;
import java.util.LinkedHashMap;
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

import com.ait.ess.service.PersonInfoSer;
import com.ait.ess.service.ViewApplySer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.BeanUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.UserConfiguration;

/**
 * 决裁情况查看(view apply approve result)
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: ViewApplyCtroller.java
 * @Description:
 * @Create date: Feb 17, 2012 9:40:40 AM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 17, 2012 9:40:40 AM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ess/viewApply")
public class ViewApplyCtroller {

	public static UserConfiguration config = UserConfiguration
			.getInstance("/system.properties");

	Logger logger = Logger.getLogger(ViewApplyCtroller.class);

	@Autowired
	private ViewApplySer viewApplySer;

	@Autowired
	private EmpInfoSer empInfoSer;

	@Autowired
	private PersonInfoSer personInfoSer;

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
	@RequestMapping(value = "/viewPersonalInfoList")
	public ModelAndView viewPersonalInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("personApplyList", viewApplySer
				.viewPersonalInfoList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, viewApplySer
				.viewPersonalInfoListCnt(request));
		return new ModelAndView("/ess/viewApply/viewPersonalInfoList", modelMap);
	}

	/**
	 * 加班信息申请(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewOvertimeInfoList")
	public ModelAndView viewOvertimeInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personOtApplyList", viewApplySer.viewOvertimeInfoList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, viewApplySer.viewOvertimeInfoListCnt(request));
		return new ModelAndView("/ess/viewApply/viewOvertimeInfoList", modelMap);
	}

	/**
	 * 休假信息申请查看(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLeaveInfoList")
	public ModelAndView viewLeaveInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personLeaveApplyList", viewApplySer
				.viewLeaveInfoList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, viewApplySer
				.viewLeaveInfoListCnt(request));
		return new ModelAndView("/ess/viewApply/viewLeaveInfoList", modelMap);
	}

	/**
	 * 出差信息申请查看(view evection personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEvectionInfoList")
	public ModelAndView viewEvectionInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personEvectionApplyList", viewApplySer
				.viewEvectionInfoList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, viewApplySer
				.viewEvectionInfoListCnt(request));
		return new ModelAndView("/ess/viewApply/viewEvectionInfoList", modelMap);
	}

	/**
	 * 外出信息申请(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEgressionInfoList")
	public ModelAndView viewEgressionInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personEgressionApplyList", viewApplySer
				.viewEgressionInfoList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, viewApplySer
				.viewEgressionInfoListCnt(request));
		return new ModelAndView("/ess/viewApply/viewEgressionInfoList",
				modelMap);
	}

	/**
	 * 审批状态查询
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAffirmorList")
	@ResponseBody
	public List getAffirmorList(HttpServletRequest request) throws Exception {
		return viewApplySer.getAffirmorList(request);// 审批状态查询
	}

	/**
	 * 删除未审核个人信息申请(delete personal apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delPersonInfoApply")
	@ResponseBody
	public Map<String, Object> delPersonInfoApply(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = viewApplySer.delPersonInfoApply(request);
		if (result) {
			map.put("navTabId", "ess0601");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 删除未审核加班信息申请(delete overtime apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delViewOtviewApply")
	@ResponseBody
	public Map<String, Object> delViewOtviewApply(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = viewApplySer.delViewOtviewApply(request);
		if (result) {
			map.put("navTabId", "ess0607");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 删除未审核休假信息申请(delete leave apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delViewLeaveviewApply")
	@ResponseBody
	public Map<String, Object> delViewLeaveviewApply(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = viewApplySer.delViewLeaveviewApply(request);
		if (result) {
			String navTabId=request.getParameter("navTabId");
			map.put("navTabId", navTabId);
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 删除未审核出差信息申请(delete evection apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delViewEvectionview")
	@ResponseBody
	public Map<String, Object> delViewEvectionview(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = viewApplySer.delViewEvectionview(request);
		if (result) {
			map.put("navTabId", "ess0606");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 删除未审核外出信息申请(delete egression apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delViewEgressionview")
	@ResponseBody
	public Map<String, Object> delViewEgressionview(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = viewApplySer.delViewEgressionview(request);
		if (result) {
			map.put("navTabId", "ess0618");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));
			map.put("statusCode", "200");
		} else {
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));
			map.put("statusCode", "300");
		}
		return map;
	}

	/**
	 * 显示个人信息申请查看页面（View personal information apply）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPersonInfo")
	public ModelAndView getPersonInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer.getPersonalInfoByPid(request);
		modelMap.put("personInfo", linkMap);
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID").toString() : "";
		String EMPID = linkMap.get("EMPID") != null ? linkMap.get("EMPID").toString() : "";
		modelMap.put("PhotoPath", PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID+ EMPID + ".jpg");

		modelMap.put("photoId", "essViewPersonalApplyInfo");

		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		BeanUtil util = new BeanUtil();
		modelMap.put("personalInfo", util.compareObject(this.personInfoSer.getEssPersonInfo(request), 
				this.personInfoSer.getHrPersonInfo(request)));
		return new ModelAndView("/ess/viewApply/viewPersonInfo", modelMap);
	}

	/**
	 * 查看完整信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFullApplyInfo")
	public ModelAndView viewFullApplyInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		//request.setCharacterEncoding("UTF-8");
		//response.setContentType("text/html; charset=UTF-8");
		//String remark=request.getParameter("APPLY_REMARK");
		//String name=new String(request.getParameter("APPLY_REMARK").getBytes("iso8859-1"),"utf-8");	
		modelMap.put("APPLY_REMARK", request.getParameter("APPLY_REMARK"));
		 response.setCharacterEncoding("UTF-8");
		return new ModelAndView(
				"/ess/infoApply/viewFullApplyInfo", modelMap);
	}
	/**
	 * 喜丧假信息申请查看(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLikeLeaveInfoList")
	public ModelAndView viewLikeLeaveInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personLeaveApplyList", viewApplySer
				.viewLeaveInfoList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, viewApplySer
				.viewLeaveInfoListCnt(request));
		return new ModelAndView("/ess/viewApply/viewLikeLeaveInfoList", modelMap);
	}
	/**
	 * 调休信息申请查看(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewWorkingDaysInfoList")
	public ModelAndView viewWorkingDaysInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personLeaveApplyList", viewApplySer
				.viewLeaveInfoList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, viewApplySer
				.viewLeaveInfoListCnt(request));
		return new ModelAndView("/ess/viewApply/viewWorkingDaysInfoList", modelMap);
	}
}

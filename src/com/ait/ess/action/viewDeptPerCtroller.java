package com.ait.ess.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ait.ar.service.ShiftSer;
import com.ait.ar.service.ItemsSer;
import com.ait.web.util.AuthorityUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ait.ess.service.InfoApplyLeaveSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.hrm.service.RecruitManageSer;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ait.ar.service.EmpCalendarSer;
import com.ait.ess.service.EssEmpInfoSer;
import com.ait.ess.service.PersonInfoSer;
import com.ait.ess.service.ViewDeptPerSer;
import com.ait.evs.service.EvsManageSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.UserConfiguration;
import com.sun.org.apache.bcel.internal.generic.NEW;

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
@RequestMapping(value = "/ess/viewDept")
public class viewDeptPerCtroller {

	public static UserConfiguration config = UserConfiguration
			.getInstance("/system.properties");

	Logger logger = Logger.getLogger(viewDeptPerCtroller.class);

	@Autowired
	private ViewDeptPerSer ViewDeptPerSer;
	
	@Autowired
	private EmpCalendarSer empCalendarSer;
	
	@Autowired
	private EmpInfoSer empInfoSers;
	
	
	@Autowired
	private EssEmpInfoSer empInfoSer;
	
	@Autowired
	private ItemsSer itemsSer;
	
	@Autowired
	private ShiftSer shiftSer;
	
	@Autowired
	private PersonInfoSer personInfoSer;
	@Autowired
	private EvsManageSer evsManageSer;
	
	@Autowired
	private InfoApplyLeaveSer infoApplyLeaveSer;
	
	@Autowired
	private AuthorityUtil authorityUtil;
/*	@Autowired
	private EmpInfoSer empInfoSer;

	@Autowired
	private PersonInfoSer personInfoSer;
*/
	@Autowired 
	private ToolMenuSer toolMenuSer;
	
	@Autowired
	private RecruitManageSer recruitManageSer;
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewDeptPersonalInfoList")
	public ModelAndView viewDeptPersonalInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List list = ViewDeptPerSer.getPersonList(request);
		modelMap.put("personList", list);
	modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.ViewDeptPerSer.getPersonListCnt(request));
	modelMap.put("DEPTNO",request.getParameter("seach_DEPT_NO"));
/*	modelMap.put("seach_FROM_DATE",request.getParameter("seach_FROM_DATE"));

	modelMap.put("seach_TO_DATE",request.getParameter("seach_TO_DATE"));*/

	modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.ViewDeptPerSer.getPersonListCnt(request));

	
	modelMap.put("toolbarInfo",
			request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
		return new ModelAndView("/ess/viewDept/viewDeptPersonalInfoList", modelMap);
	}
	
	
	

	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewDeptPersonalInfo2List")
	public ModelAndView viewDeptPersonalInfo2List(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List list = ViewDeptPerSer.getPersonList(request);
		modelMap.put("personList", list);
	modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.ViewDeptPerSer.getPersonListCnt(request));
	modelMap.put("DEPTNO",request.getParameter("seach_DEPT_NO"));
/*	modelMap.put("seach_FROM_DATE",request.getParameter("seach_FROM_DATE"));

	modelMap.put("seach_TO_DATE",request.getParameter("seach_TO_DATE"));*/

	modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.ViewDeptPerSer.getPersonListCnt(request));

	
	modelMap.put("toolbarInfo",
			request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
		return new ModelAndView("/ess/viewDept/viewDeptPersonalInfo2List", modelMap);
	}
	
	
	
	
	/**
	 * 部门员工查询
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewDeptPersonalInfoManageList")
	public ModelAndView viewDeptPersonalInfoManageList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("DEPTNO",request.getParameter("seach_DEPT_NO"));
		modelMap.put("NATIONALITY_CODE", request.getParameter("NATIONALITY_CODE"));
		modelMap.put("defaultRoleGroupName",request.getParameter("defaultRoleGroupName"));
		List list = ViewDeptPerSer.getPersonManageList(request);
		modelMap.put("personList", list);
	//modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.ViewDeptPerSer.getPersonManageListCnt(request));
/*	modelMap.put("seach_FROM_DATE",request.getParameter("seach_FROM_DATE"));
	modelMap.put("seach_TO_DATE",request.getParameter("seach_TO_DATE"));*/
//	modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.ViewDeptPerSer.getPersonManageListCnt(request));
	modelMap.put("toolbarInfo",
			request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
		return new ModelAndView("/ess/viewDept/viewDeptPersonalInfoManageList", modelMap);
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewDeptPersonalInfo")
	public ModelAndView viewDeptPersonalInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("LOCAL_NAME", request.getParameter("LOCAL_NAME"));

		modelMap.put("personInfo",  ViewDeptPerSer.getPersonInfo(request));
	 
	modelMap.put("toolbarInfo",
			request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
		return new ModelAndView("/ess/viewDept/viewDeptPersonalInfo", modelMap);
	}
	/**
	 * 内务考勤申请查看个人信息
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApplyDeptPersonalInfo")
	public ModelAndView viewApplyDeptPersonalInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("LOCAL_NAME", request.getParameter("LOCAL_NAME"));
		
		modelMap.put("personInfoTemp",  ViewDeptPerSer.getPersonInfo2(request));
		
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
		return new ModelAndView("/ess/viewDept/viewApplyDeptPersonalInfo", modelMap);
	}
	
	
	
	
	/*
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

	*//**
	 * 加班信息申请(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping(value = "/viewOvertimeInfoList")
	public ModelAndView viewOvertimeInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("personOtApplyList", viewApplySer.viewOvertimeInfoList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, viewApplySer.viewOvertimeInfoListCnt(request));
		return new ModelAndView("/ess/viewApply/viewOvertimeInfoList", modelMap);
	}

	*//**
	 * 休假信息申请查看(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 *//*
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

	*//**
	 * 出差信息申请查看(view evection personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 *//*
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

	*//**
	 * 外出信息申请(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 *//*
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

	*//**
	 * 审批状态查询
	 *//*
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAffirmorList")
	@ResponseBody
	public List getAffirmorList(HttpServletRequest request) throws Exception {
		return viewApplySer.getAffirmorList(request);// 审批状态查询
	}

	*//**
	 * 删除未审核个人信息申请(delete personal apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 *//*
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

	*//**
	 * 删除未审核加班信息申请(delete overtime apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 *//*
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

	*//**
	 * 删除未审核休假信息申请(delete leave apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 *//*
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

	*//**
	 * 删除未审核出差信息申请(delete evection apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 *//*
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

	*//**
	 * 删除未审核外出信息申请(delete egression apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 *//*
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

	*//**
	 * 显示个人信息申请查看页面（View personal information apply）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 *//*
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

	*//**
	 * 查看完整信息(view full information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 *//*
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
	*//**
	 * 喜丧假信息申请查看(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 *//*
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
	*//**
	 * 调休信息申请查看(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 *//*
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
	}*/
	
	
	
	/**
	 * 个人考勤现状
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArPersonalList")
	public ModelAndView viewArPersonalList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
	List list = ViewDeptPerSer.viewArPersonalList(request);
	modelMap.put("personList", list);
	//modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.ViewDeptPerSer.viewArPersonalListCnt(request));
	modelMap.put("DEPTNO",request.getParameter("seach_DEPT_NO"));
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 

	if(request.getParameter("seach_STIME")==""||request.getParameter("seach_STIME")==null){
		//获取当前月第一天：
		Calendar c = Calendar.getInstance();    
		c.add(Calendar.MONTH, 0);
		//设置为1号,当前日期既为本月第一天 
		c.set(Calendar.DAY_OF_MONTH,1);
		String first = format.format(c.getTime());

		modelMap.put("STIME",first);		
	}else{		
		modelMap.put("STIME",request.getParameter("seach_STIME"));
	}
    if(request.getParameter("seach_ETIME")==""||request.getParameter("seach_STIME")==null){
    	Calendar c = Calendar.getInstance();  
    	String  sysdate= format.format(c.getTime());
    	modelMap.put("ETIME",sysdate);
	}else{		
		modelMap.put("ETIME",request.getParameter("seach_ETIME"));
	}

	modelMap.put("getDynamicArTypeList", ViewDeptPerSer.getDynamicGroup1List(request));
	modelMap.put("toolbarInfo",
			request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
		
		return new ModelAndView("/ess/viewDept/viewArPersonalList", modelMap);
	}
	
	/**
	 * 个人考勤现状
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArPersonalSelfList")
	public ModelAndView viewArPersonalSelfList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
	List list = ViewDeptPerSer.viewArPersonalList(request);
	modelMap.put("personList", list);
	//modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.ViewDeptPerSer.viewArPersonalListCnt(request));
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 

	if(request.getParameter("seach_STIME")==""||request.getParameter("seach_STIME")==null){
		//获取当前月第一天：
		Calendar c = Calendar.getInstance();    
		c.add(Calendar.MONTH, 0);
		//设置为1号,当前日期既为本月第一天 
		c.set(Calendar.DAY_OF_MONTH,1);
		String first = format.format(c.getTime());

		modelMap.put("STIME",first);		
	}else{		
		modelMap.put("STIME",request.getParameter("seach_STIME"));
	}
    if(request.getParameter("seach_ETIME")==""||request.getParameter("seach_STIME")==null){
    	Calendar c = Calendar.getInstance();  
    	String  sysdate= format.format(c.getTime());
    	modelMap.put("ETIME",sysdate);
	}else{		
		modelMap.put("ETIME",request.getParameter("seach_ETIME"));
	}

	modelMap.put("getDynamicArTypeList", ViewDeptPerSer.getDynamicGroup1List(request));
	modelMap.put("toolbarInfo",request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
		
		return new ModelAndView("/ess/viewDept/viewArPersonalSelfList", modelMap);
	}
	
	
	
	/**
	 * 个人考勤现状 manage
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArPersonalManageList")
	public ModelAndView viewArPersonalManageList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List list = ViewDeptPerSer.viewArPersonalManageList(request);
		modelMap.put("personList", list);
	modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.ViewDeptPerSer.viewArPersonalManageListCnt(request));
	modelMap.put("DEPTNO",request.getParameter("seach_DEPT_NO"));
	SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd"); 

	if(request.getParameter("seach_STIME")==""||request.getParameter("seach_STIME")==null){
		//获取当前月第一天：
		Calendar c = Calendar.getInstance();    
		c.add(Calendar.MONTH, 0);
		//设置为1号,当前日期既为本月第一天 
		c.set(Calendar.DAY_OF_MONTH,1);
		String first = format.format(c.getTime());

		modelMap.put("STIME",first);

		
	}else{
		
		modelMap.put("STIME",request.getParameter("seach_STIME"));

	}
    if(request.getParameter("seach_ETIME")==""||request.getParameter("seach_STIME")==null){
    	Calendar c = Calendar.getInstance();  
    	String  sysdate= format.format(c.getTime());

    	modelMap.put("ETIME",sysdate);

	}else{
		
		modelMap.put("ETIME",request.getParameter("seach_ETIME"));

	}

	modelMap.put("getDynamicArTypeList", ViewDeptPerSer.getDynamicGroup1List(request));
	modelMap.put("toolbarInfo",
			request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
	
	
		return new ModelAndView("/ess/viewDept/viewArPersonalManageList", modelMap);
	}
	
	
	
	/**
	 * 单个的情况点击数字  待条件查询
	 */
	
	
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArPersonalSingleList")
	public ModelAndView viewArPersonalSingleList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
	
		modelMap.put("viewArPersonalSingleList", ViewDeptPerSer.viewArPersonalSingleList(request));

	
		return new ModelAndView("/ess/viewDept/viewArPersonalSingleList", modelMap);
	}
	
	
	/**
	 * 部门汇总明细查询
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArSummarySingleList")
	public ModelAndView viewArSummarySingleList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
	
		modelMap.put("viewArSummarySingleList", ViewDeptPerSer.viewArSummarySingleList(request));

	
		return new ModelAndView("/ess/viewDept/viewArSummarySingleList", modelMap);
	}
	

	/**
	 * 个人加班现状
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOtApplyPersonalList")
	public ModelAndView viewOtApplyPersonalList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 

		if(request.getParameter("seach_STIME")==""||request.getParameter("seach_STIME")==null){
			//获取当前月第一天：
			Calendar c = Calendar.getInstance();    
			c.add(Calendar.MONTH, 0);
			//设置为1号,当前日期既为本月第一天 
			c.set(Calendar.DAY_OF_MONTH,1);
			String first = format.format(c.getTime());

			modelMap.put("FROM_DATE",first);

			
		}else{
			
			modelMap.put("FROM_DATE",request.getParameter("seach_STIME"));

		}
	    if(request.getParameter("seach_ETIME")==""||request.getParameter("seach_ETIME")==null){
	    	Calendar c = Calendar.getInstance();  
	    	String  sysdate= format.format(c.getTime());

	    	modelMap.put("TO_DATE",sysdate);

		}else{
			
			modelMap.put("TO_DATE",request.getParameter("seach_ETIME"));

		}
        
		
		List list = ViewDeptPerSer.viewOtApplyPersonalList(request);
		modelMap.put("viewOtApplyPersonalList", list);
		//modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.ViewDeptPerSer.viewOtApplyPersonalListCnt(request));
		modelMap.put("DEPTNO",request.getParameter("seach_DEPT_NO"));
		modelMap.put("toolbarInfo",
			request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
	
		return new ModelAndView("/ess/viewDept/viewOtApplyPersonalList", modelMap);
	}
	
	/**
	 * 个人加班现状
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOtApplyPersonalSelfList")
	public ModelAndView viewOtApplyPersonalSelfList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 

		if(request.getParameter("seach_STIME")==""||request.getParameter("seach_STIME")==null){
			//获取当前月第一天：
			Calendar c = Calendar.getInstance();    
			c.add(Calendar.MONTH, 0);
			//设置为1号,当前日期既为本月第一天 
			c.set(Calendar.DAY_OF_MONTH,1);
			String first = format.format(c.getTime());
			modelMap.put("FROM_DATE",first);
		}else{
			modelMap.put("FROM_DATE",request.getParameter("seach_STIME"));
		}
	    if(request.getParameter("seach_ETIME")==""||request.getParameter("seach_ETIME")==null){
	    	Calendar c = Calendar.getInstance();  
	    	String  sysdate= format.format(c.getTime());
	    	modelMap.put("TO_DATE",sysdate);
		}else{
			modelMap.put("TO_DATE",request.getParameter("seach_ETIME"));
		}
		
		List list = ViewDeptPerSer.viewOtApplyPersonalList(request);
		modelMap.put("viewOtApplyPersonalSelfList", list);
		//modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.ViewDeptPerSer.viewOtApplyPersonalListCnt(request));
		modelMap.put("toolbarInfo",
			request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
	
		return new ModelAndView("/ess/viewDept/viewOtApplyPersonalSelfList", modelMap);
	}
	
	/**
	 * 年假现状(内务和部门长)
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewUseOfAnnualLeaveList")
	public ModelAndView viewUseOfAnnualLeaveList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List itemList = itemsSer.getItemParamList2(request);
		Date d=new Date();   
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		if (request.getParameter("seach_VAR_YEAR") == ""
				|| request.getParameter("seach_VAR_YEAR") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());
			modelMap.put("VAR_YEAR", sysdate);

		}else{
			modelMap.put("VAR_YEAR",request.getParameter("seach_VAR_YEAR"));
		}
		String firstFlag= request.getParameter("firstFlag");
		if (firstFlag != null&& !"".equals(firstFlag)) {
			
		}
		List list = ViewDeptPerSer.viewUseOfAnnualLeaveList(request);
		modelMap.put("useOfAnnualLeaveList", list);
		modelMap.put("itemList",itemList) ;
	    //modelMap.put("viewUseOfAnnualLeaveListCnt", this.ViewDeptPerSer.viewUseOfAnnualLeaveListCnt(request));
	    modelMap.put("toolbarInfo",
			request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
	
		return new ModelAndView("/ess/viewDept/viewUseOfAnnualLeaveList", modelMap);
	}
	/**
	 * 倒休使用现状(内务和部门长)
	 */
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewUseOfAdjustLeaveList")
	public ModelAndView viewUseOfAdjustLeaveList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");
		String ar_month_str =  sformat.format(new Date());
	
		List list = ViewDeptPerSer.viewUseOfAdjustLeaveList(request);
		modelMap.put("useOfAdjustLeaveList", list);
		modelMap.put("AR_MONTHR", request.getParameter("seach_AR_MONTH") != null ? request.getParameter("seach_AR_MONTH") : ar_month_str);
		modelMap.put("DEPTNO",request.getParameter("seach_DEPT_NO"));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME,this.ViewDeptPerSer.viewUseOfAdjustLeaveListCnt(request));
		modelMap.put("useOfAdjustLeaveListCnt",this.ViewDeptPerSer.viewUseOfAdjustLeaveListCnt(request));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
		return new ModelAndView("/ess/viewDept/viewUseOfAdjustLeaveList", modelMap);
	}
	

	/**
	 * 个人加班现状  manage权限下
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOtApplyPersonalManageList")
	public ModelAndView viewOtApplyPersonalManageList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd"); 

		if(request.getParameter("seach_FROM_DATE")==""||request.getParameter("seach_FROM_DATE")==null){
			//获取当前月第一天：
			Calendar c = Calendar.getInstance();    
			c.add(Calendar.MONTH, 0);
			//设置为1号,当前日期既为本月第一天 
			c.set(Calendar.DAY_OF_MONTH,1);
			String first = format.format(c.getTime());

			modelMap.put("FROM_DATE",first);

			
		}else{
			
			modelMap.put("FROM_DATE",request.getParameter("seach_FROM_DATE"));

		}
	    if(request.getParameter("seach_TO_DATE")==""||request.getParameter("seach_TO_DATE")==null){
	    	Calendar c = Calendar.getInstance();  
	    	String  sysdate= format.format(c.getTime());

	    	modelMap.put("TO_DATE",sysdate);

		}else{
			
			modelMap.put("TO_DATE",request.getParameter("seach_TO_DATE"));

		}
        
		
		List list = ViewDeptPerSer.viewOtApplyPersonalManageList(request);
		modelMap.put("viewOtApplyPersonalList", list);
	modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.ViewDeptPerSer.viewOtApplyPersonalManageListCnt(request));
	modelMap.put("DEPTNO",request.getParameter("seach_DEPT_NO"));
	modelMap.put("toolbarInfo",
			request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
	
		return new ModelAndView("/ess/viewDept/viewOtApplyPersonalManageList", modelMap);
	}
	
	/**
	 * 单个的情况点击数字  待条件查询 加班
	 */
	
	
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOtApplySingleList")
	public ModelAndView viewOtApplySingleList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
	
		modelMap.put("viewOtApplySingleList", ViewDeptPerSer.viewOtApplySingleList(request));

	
		return new ModelAndView("/ess/viewDept/viewOtApplySingleList", modelMap);
	}
	
	/**
	 * 单个的情况点击数字  带条件查询 中夜班津贴
	 */
	
	
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAllowanceSingleList")
	public ModelAndView viewAllowanceSingleList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
	
		modelMap.put("viewAllowanceSingleList", ViewDeptPerSer.viewAllowanceSingleList(request));

	
		return new ModelAndView("/ess/viewDept/viewAllowanceSingleList", modelMap);
	}
	
	

	/**
	 * 单个的情况点击数字  待条件查询 加班
	 */
	
	
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPersonInfoSingleList")
	public ModelAndView viewPersonInfoSingleList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
	
		modelMap.put("viewOtApplySingleList", ViewDeptPerSer.viewOtApplySingleList(request));

	
		return new ModelAndView("/ess/viewDept/viewPersonInfoSingleList", modelMap);
	}
	

	/**
	 * 旷工查询页面
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAbsenteeismInfoList")
	public ModelAndView viewAbsenteeismInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
	    
        if(request.getParameter("seach_EMP_OFFICE")==null){
        	modelMap.put("EMP_OFFICE", "15119");
        }
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
		Calendar c = Calendar.getInstance();    
		if((request.getParameter("seach_FROM_DATE")==""||request.getParameter("seach_FROM_DATE")==null )&& (request.getParameter("seach_TO_DATE")==""||request.getParameter("seach_TO_DATE")==null)){
			//获取当前年第一天：
			c.add(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_MONTH,1);
			String first = format.format(c.getTime());
			modelMap.put("FROM_DATE",first);
			//获取当前月最后一天：
			c = Calendar.getInstance();  
			c.add(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
			String last = format.format(c.getTime());
			modelMap.put("TO_DATE",last);
		}
		List list = ViewDeptPerSer.viewAbsenteeismInfoList(request);
		modelMap.put("viewAbsenteeismInfoList", list);
		//modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.ViewDeptPerSer.viewAbsenteeismInfoListCnt(request));
		//modelMap.put("DEPTNO",request.getParameter("seach_DEPT_NO"));
	/*	modelMap.put("seach_FROM_DATE",request.getParameter("seach_FROM_DATE"));
	
		modelMap.put("seach_TO_DATE",request.getParameter("seach_TO_DATE"));*/
	
		//modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.ViewDeptPerSer.getPersonListCnt(request));
	
		//modelMap.put("getDynamicArTypeList", ViewDeptPerSer.getDynamicGroup1List(request));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
		
		return new ModelAndView("/ess/viewDept/viewAbsenteeismInfoList", modelMap);
	}
	
	

	/**
	 * 考勤出入数据查询
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEntryInfoList")
	public ModelAndView viewEntryInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List list = ViewDeptPerSer.viewEntryInfoList(request);
		modelMap.put("viewEntryInfoList", list);
		List shiftList = shiftSer.getShiftList1(request);
		modelMap.put("shiftList", shiftList) ;
		String firstFlag= request.getParameter("firstFlag");
		if ("2".equals(firstFlag)) {
			modelMap.put("STIME", DateUtil.getSysdateStr("dd/MM/yyyy"));
			modelMap.put("ETIME", DateUtil.getSysdateStr("dd/MM/yyyy"));
		}
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSers.viewHrPersonalInfo2(request);
		modelMap.put("personInfo",linkMap);
	modelMap.put("DEPTNO",request.getParameter("seach_DEPT_NO"));
	modelMap.put("SHIFT_NAME",request.getParameter("seach_SHIFT_NO"));

	modelMap.put("toolbarInfo", toolMenuSer.getToolMenuForNo(request, "14013716"));
		return new ModelAndView("/ess/viewDept/viewEntryInfoList", modelMap);
	}
	
	
	/**
	 * 模板方法
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArVacationMonthExcel")
	public ModelAndView viewArVacationMonthExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
     
		/*List vacationEmpList = this.arVacationSer.getArVacationMonthExcel(request);*/
		
		/*modelMap.put("vacationEmpList", vacationEmpList) ;*/
		
	    return new ModelAndView("/ar/attendanceVacations/viewArVacationMonthExcel", modelMap);
	}
	
	

	/**
	 * 公司日历查询(view Company Calendar)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewCompanyCalendar")
	public ModelAndView viewCompanyCalendar(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
        String cpnyid=admin.getCpnyId();
		String shift=(String) request.getParameter("person_id");
		String GROUP=(String) request.getParameter("GROUP");
//		System.out.println(GROUP+"ss*******+{"+shift);
		
		//String STAT_NO=GROUP.equals("CH_W2")?"219948":"141436";


		String calendarHtml = ViewDeptPerSer
		        .getCompanyCalendarViewHtml(request);
		//List shiftNo=arClassCalendarSer.getShiftNo(request);
		

		modelMap.put("empid", admin.getEmpID());
		modelMap.put("GROUP", GROUP);
		modelMap.put("name", admin.getLocalName());
		modelMap.put("deptname", admin.getDepartment());
		modelMap.put("person_id", admin.getPersonId());
		modelMap.put("cpny_id", admin.getCpnyId());
		//modelMap.put("STAT_NO", STAT_NO);
		modelMap.put("calendarHtml", calendarHtml);
		//modelMap.put("shiftNo", shiftNo);
		modelMap.put("reqest_year", StringUtil.checkNull(request.getParameter("year")));
		modelMap.put("reqest_month", StringUtil.checkNull(request.getParameter("month")));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "216695"));
		//modelMap.put("shiftsList", shiftsList) ;


		return new ModelAndView("/ess/viewDept/viewCompanyCalendar",
				modelMap);
	}
	
	/**
	 * Manage权限下的页面   部门员工部门类别经历
	 */

	/**
	 * 部门员工部门类别经历
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ManageEmpPositionInfoList")
	public ModelAndView viewManageEmpPositionInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String EMP_OFFICE=request.getParameter("seach_EMP_OFFICE");
		modelMap.put("DEPTNO",request.getParameter("seach_DEPT_NO"));
		modelMap.put("NATIONALITY_CODE", request.getParameter("NATIONALITY_CODE"));
		modelMap.put("COMPANY_NAME",request.getParameter("COMPANY_NAME"));
		modelMap.put("DATE_FROM_STARTED",request.getParameter("DATE_FROM_STARTED"));
		modelMap.put("DATE_TO_STARTED",request.getParameter("DATE_TO_STARTED"));
		modelMap.put("defaultRoleGroupName",request.getParameter("defaultRoleGroupName"));
		
		LinkedHashMap paramData = ObjectBindUtil.getRequestParamData(request,"seach_");
		List supplierList = this.recruitManageSer.viewPersonSupplier(paramData);
		modelMap.put("supplierList", supplierList);
		List list = ViewDeptPerSer.ManageEmpPositionInfoList(request);
		modelMap.put("ManageEmpPositionInfoList", list);
//	modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.ViewDeptPerSer.ManageEmpPositionInfoListCnt(request));
		modelMap.put("toolbarInfo",
		request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
		modelMap.put("AGE", request.getParameter("AGE"));
		return new ModelAndView("/ess/viewDept/ManageEmpPositionInfoList", modelMap);
	}

	/**
	 * 部门员工考评信息
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewManageEvsResultEmpList")
	public ModelAndView viewManageEvsResultEmpList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
        String cpnyid=admin.getCpnyId();
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy"); 
		Calendar c = Calendar.getInstance();    
		if(request.getParameter("firstFlag")==null&&(request.getParameter("seach_EVS_YEAR")==""||request.getParameter("seach_EVS_YEAR")==null )){
			//获取当前年：
			String year = format.format(c.getTime());
			modelMap.put("EVS_YEAR",year);
		}

		//List objectList = this.evsManageSer.viewEvsInfoList(request,"viewEvsResultEmp");
		List objectList = this.evsManageSer.viewEvsInfoList(request,"viewManageEvsResultEmp"+cpnyid);
		modelMap.put("objectList", objectList);
//	modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.ViewDeptPerSer.ManageEmpPositionInfoListCnt(request));
	modelMap.put("DEPTNO",request.getParameter("seach_DEPT_NO"));

	modelMap.put("toolbarInfo",
			request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
	
		return new ModelAndView("/ess/viewDept/viewManageEvsResultEmpList", modelMap);
	}
	
	
	/**
	 * 部门员工 任职经历  （个人）
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ManageEmpPositionSinglList")
	public ModelAndView ManageEmpPositionSinglList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("ManageEmpPositionSinglList", ViewDeptPerSer.ManageEmpPositionSinglList(request));

		modelMap.put("empInfo", ViewDeptPerSer.getEmpInfoById(request));

		return new ModelAndView("/ess/viewDept/ManageEmpPositionSinglList", modelMap);
	}
	
	/**
	 *  部门员工人事信息查询
	 */

	
	/**
	 *    部门别人事统计

	 */

	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ManageCountInfoList")
	public ModelAndView ManageCountInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String currentIndex = request.getParameter("currentIndex");
		if("1".equals(currentIndex)){
			modelMap.put("currentIndex", 1);
		}else if("2".equals(currentIndex)){
			modelMap.put("currentIndex", 2);
		}else if("3".equals(currentIndex)){
			modelMap.put("currentIndex", 3);
		}else if("4".equals(currentIndex)){
			modelMap.put("currentIndex", 4);
		}else{
			modelMap.put("currentIndex", 0);
		}
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		
		modelMap.put("managePart", admin.getCpnyId().substring(admin.getCpnyId().lastIndexOf("_")+1));
		modelMap.put("RESUME_NO", request.getParameter("RESUME_NO"));
		modelMap.put("DEPTNO", request.getParameter("DEPTNO"));
		modelMap.put("SON_FLAG", request.getParameter("SON_FLAG"));
		modelMap.put("DDATE", this.getDateFirst("MM/yyyy"));
		modelMap.put("empTypeCodeList", empInfoSer.getCodeList("13864", request));
		modelMap.put("empStatusCodeList", empInfoSer.getCodeList("1372", request));
		return new ModelAndView("/ess/viewDept/ManageCountInfoList", modelMap);
	}
	
	// 获取当前月第一天 传入的是 日期格式
	public String getDateFirst(String forma) {
		SimpleDateFormat format = new SimpleDateFormat(forma);
		String first = "";
		// 获取当前月第一天：
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		// 设置为1号,当前日期既为本月第一天
		c.set(Calendar.DAY_OF_MONTH, 1);
		first = format.format(c.getTime());
		return first;
	}
	
	/**
	 *  部门类别人事统计
	 */
	
	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ManageCountInfoSonList")
	public ModelAndView ManageCountInfoSonList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");
		if("0".equals(currentIndex)){
			LinkedHashMap codeInfoTreeList = this.ViewDeptPerSer.getParentCodeGradeList(request);
			modelMap.put("codeInfoTreeList22", codeInfoTreeList.get("deptList"));
			modelMap.put("deptListCount", codeInfoTreeList.get("deptListCount"));
			modelMap.put("deptListCount3", codeInfoTreeList.get("deptListCount3"));
			modelMap.put("currentIndex", 0);
			modelMap.put("postGradeBGZCodeList", empInfoSer.getCodeList("14015813", request));
			modelMap.put("postGradeYBZCodeList", empInfoSer.getCodeList("14015814", request));
			modelMap.put("postGradeSCZCodeList", empInfoSer.getCodeList("14015815", request));
		}else if("1".equals(currentIndex)){
			LinkedHashMap codeInfoTreeList = this.ViewDeptPerSer.getParentCodePositionList(request);
			modelMap.put("codeInfoTreeList22", codeInfoTreeList.get("deptList"));
			modelMap.put("deptListCount", codeInfoTreeList.get("deptListCount"));
			modelMap.put("currentIndex", 1);
		}else if("2".equals(currentIndex)){
			LinkedHashMap codeInfoTreeList = this.ViewDeptPerSer.getParentCodeEduList(request);
			modelMap.put("codeInfoTreeList22", codeInfoTreeList.get("deptList"));
			modelMap.put("deptListCount", codeInfoTreeList.get("deptListCount"));
			modelMap.put("deptListCount3", codeInfoTreeList.get("deptListCount3"));
			modelMap.put("currentIndex", 2);
		}else if("3".equals(currentIndex)){
			//List list = ViewDeptPerSer.manageAgeCountList(request);
			LinkedHashMap codeInfoTreeList = this.ViewDeptPerSer.getParentCodeAgeList(request);
			modelMap.put("codeInfoTreeList22", codeInfoTreeList.get("deptList"));
			modelMap.put("deptListCount", codeInfoTreeList.get("deptListCount"));
			//modelMap.put("manageAgeCountList", list);
			modelMap.put("currentIndex", 3);
		}else{
			LinkedHashMap codeInfoTreeList = this.ViewDeptPerSer.getParentCodeEmpTypeList(request);
			modelMap.put("codeInfoTreeList22", codeInfoTreeList.get("deptList"));
			modelMap.put("deptListCount", codeInfoTreeList.get("deptListCount"));
			modelMap.put("currentIndex", 4);
			modelMap.put("empTypeCodeList", empInfoSer.getCodeList("13864", request));
		}
		
		return new ModelAndView("/ess/viewDept/ManageCountInfoSonList", modelMap);
	}
	
	

	/**
	 * 生成部门树
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getParentTreeData")
	@ResponseBody
	public List getParentTreeData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		LinkedHashMap map = this.ViewDeptPerSer
				.getParentCodeAgeList(request);
		List codeInfoTreeList=(ArrayList)map.get("deptList");
		return codeInfoTreeList;
	}	
	
	//月别采用现状
	


	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/monthCountInfoList")
	public ModelAndView monthCountInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String currentIndex = request.getParameter("currentIndex");
		if("1".equals(currentIndex)){
			modelMap.put("currentIndex", 1);
		}
	
		modelMap.put("RESUME_NO", request.getParameter("RESUME_NO"));
		modelMap.put("DEPTNO", request.getParameter("DEPTNO"));
		modelMap.put("SON_FLAG", request.getParameter("SON_FLAG"));
		modelMap.put("empTypeCodeList", empInfoSer.getCodeList("13864", request));
		
		return new ModelAndView("/ess/viewDept/monthCountInfoList", modelMap);
	}
	
	/**
	 *  部门类别人事统计
	 */
	
	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/monthCountInfoSonList")
	public ModelAndView monthCountInfoSonList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");
		
		
		
	
			LinkedHashMap codeInfoTreeList = this.ViewDeptPerSer
			.getParentCodeMonthList(request);
			
			modelMap.put("codeInfoTreeList22", codeInfoTreeList.get("deptList"));
			modelMap.put("deptListCount", codeInfoTreeList.get("deptListCount"));
			modelMap.put("currentIndex", 1);
		
		
		return new ModelAndView("/ess/viewDept/monthCountInfoSonList", modelMap);
	}
	
	
	//月别退职现状




	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/monthResignCountInfoList")
	public ModelAndView monthResignCountInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String currentIndex = request.getParameter("currentIndex");
		if("1".equals(currentIndex)){
			modelMap.put("currentIndex", 1);
		}
	
		modelMap.put("RESUME_NO", request.getParameter("RESUME_NO"));
		modelMap.put("DEPTNO", request.getParameter("DEPTNO"));
		modelMap.put("SON_FLAG", request.getParameter("SON_FLAG"));
		modelMap.put("empTypeCodeList", empInfoSer.getCodeList("13864", request));
		
		return new ModelAndView("/ess/viewDept/monthResignCountInfoList", modelMap);
	}
	
	/**
	 *  部门类别人事统计
	 */
	
	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/monthResignCountInfoSonList")
	public ModelAndView monthResignCountInfoSonList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");
		
		
		
	
			LinkedHashMap codeInfoTreeList = this.ViewDeptPerSer
			.getParentCodeMonthResignList(request);
			
			modelMap.put("codeInfoTreeList22", codeInfoTreeList.get("deptList"));
			modelMap.put("deptListCount", codeInfoTreeList.get("deptListCount"));
			modelMap.put("currentIndex", 1);
		
		
		return new ModelAndView("/ess/viewDept/monthResignCountInfoSonList", modelMap);
	}
	
	
	/**
	 * 加班年假现状
	 */
	
	
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArPersonalYearList")
	public ModelAndView viewArPersonalYearList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List list = ViewDeptPerSer.viewArPersonalYearList(request);
		modelMap.put("viewArPersonalYearList", list);
		modelMap.put("YEAR",request.getParameter("YEAR"));
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
        if (request.getParameter("YEAR") == "" || request.getParameter("YEAR") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());
			modelMap.put("YEAR", sysdate);
		}
	
		return new ModelAndView("/ess/viewDept/viewArPersonalYearList", modelMap);
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArSummaryList")
	public ModelAndView viewArSummaryList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		List shiftList = shiftSer.getShiftList1(request);
		List itemList = itemsSer.getItemParamList2(request);
		List workTimeLsit = shiftSer.getWorkTimeLsit(request);
		modelMap.put("workTimeList", workTimeLsit) ;
		modelMap.put("itemList", itemList) ;
		modelMap.put("shiftList", shiftList) ;
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		SimpleDateFormat format=new SimpleDateFormat("yyyy/MM");
		String AR_MONTH=request.getParameter("seach_AR_MONTH");
		Calendar c = Calendar.getInstance();
		String firstFlag= request.getParameter("firstFlag");
		if(firstFlag==null){
			Date date= new Date();
			String arMonth = format.format(date);
			modelMap.put("AR_MONTH", arMonth);
		}else{
			modelMap.put("AR_MONTH",AR_MONTH);
		}
		modelMap.put("DEPTNO", request.getParameter("seach_DEPTNO")); 
		modelMap.put("CONFIRM_FLAG", request.getParameter("seach_CONFIRM_FLAG")); 
		modelMap.put("empName", request.getParameter("dwz.person.empName")); 
		modelMap.put("empInfo", request.getParameter("dwz.person.empInfo")); 
		modelMap.put("GROUP_NO", request.getParameter("seach_GROUP_NO")); 
		modelMap.put("SHIFT_NO", request.getParameter("seach_SHIFT_NO")); 
		modelMap.put("ITEM_NO", request.getParameter("seach_ITEM_NO")); 
		modelMap.put("AFFIRM_FLAG", request.getParameter("seach_AFFIRM_FLAG")); 
		modelMap.put("CONFIRM_FLAG", request.getParameter("seach_CONFIRM_FLAG")); 
		modelMap.put("EMP_TYPE_CODE", request.getParameter("seach_EMP_TYPE_CODE")); 
		modelMap.put("EmpOffice", request.getParameter("seach_EmpOffice")); 
		modelMap.put("defaultRoleGroupName", request.getParameter("defaultRoleGroupName"));
		if (firstFlag != null&& !"".equals(firstFlag)) {
			modelMap.put("YEAR",request.getParameter("YEAR"));
			LinkedHashMap linkMap2= (LinkedHashMap)empInfoSers.viewHrPersonalInfo(request);
			modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			modelMap.put("personInfo",linkMap2);
			modelMap.put("FROM_DATE",request.getParameter("seach_FROM_DATE"));
			modelMap.put("TO_DATE",request.getParameter("seach_TO_DATE")); 
		}
		List list = ViewDeptPerSer.viewArSummaryList(request);
		modelMap.put("viewArSummaryList", list);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
	
		return new ModelAndView("/ess/infoApplyAttendance/viewArSummaryList", modelMap);
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/yearUseInfo")
	public ModelAndView yearUseInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List list = ViewDeptPerSer.yearUseInfo(request);
		modelMap.put("yearUseInfo", list);
		List yearlist = ViewDeptPerSer.yearInfo(request);
		modelMap.put("yearInfo", yearlist);
		//List vaclist = ViewDeptPerSer.vacInfo(request);
		//modelMap.put("vacInfo", vaclist);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		if (request.getParameter("seach_VAR_YEAR") == ""
				|| request.getParameter("seach_VAR_YEAR") == null) {
			Calendar c = Calendar.getInstance();
			String sysdate = format.format(c.getTime());
			modelMap.put("VAR_YEAR", sysdate);
		}else{
			modelMap.put("VAR_YEAR", request.getParameter("seach_VAR_YEAR"));
		}
		return new ModelAndView("/ess/viewDept/yearUseInfo", modelMap);
	}
	
	
	
	
	/**
	 * 个人日历查看页面(add EmpShift View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEmpCalendar")
	public ModelAndView addEmpShiftView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
	   String calendarHtml = this.empCalendarSer.getEmpCalendarViewHtmlPer(request) ;
	   
		modelMap.put("calendarHtml", calendarHtml) ;
		modelMap.put("reqest_year", StringUtil.checkNull(request.getParameter("year")));
		modelMap.put("reqest_month", StringUtil.checkNull(request.getParameter("month")));
		return new ModelAndView("/ess/viewDept/viewEmpCalendar",modelMap);
	}
	
	/**
	 * 月考勤查看（个人）
	 * 
	 * */
	@RequestMapping(value = "/viewArDetailSummaryForMonthList")
	public ModelAndView viewArDetailSummaryForMonthList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		List list = ViewDeptPerSer.viewArDetailSummaryForMonthList(request);
		modelMap.put("viewArDetailSummaryForMonthList", list);
		int days = 0;
		if(request.getParameter("firstflag")!=""&&request.getParameter("firstflag")!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
			Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(sdf.parse(paramMap.get("seach_MONTH").toString()));  
	        days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); 
		}
		 modelMap.put("DAY_OF_MONTH",days);
		return new ModelAndView("/ess/viewDept/viewArDetailSummaryForMonthList",modelMap);
	}
	/**
	 * 月考勤查看（考勤员）
	 * 
	 * */
	@RequestMapping(value = "/viewArDetailSummaryForAttenceList")
	public ModelAndView viewArDetailSummaryForAttenceList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		List list = new ArrayList();
		list = ViewDeptPerSer.viewArDetailSummaryForAttenceList(request);
		modelMap.put("viewArDetailSummaryForAttenceList", list);
		int days = 0;
		if(request.getParameter("firstflag")!=""&&request.getParameter("firstflag")!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
			Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(sdf.parse(paramMap.get("seach_MONTH").toString()));  
	        days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); 
		}
		 modelMap.put("DAY_OF_MONTH",days);
		return new ModelAndView("/ess/viewDept/viewArDetailSummaryForAttenceList",modelMap);
	}
}

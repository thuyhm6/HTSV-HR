package com.ait.ess.action;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ait.ess.service.changeSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.LoginSer;
import com.ait.sys.service.MyHomeSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.UserConfiguration;

/**
 * 更改密码
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
@RequestMapping(value = "/ess/change")
public class changeCtroller {

	public static UserConfiguration config = UserConfiguration
			.getInstance("/system.properties");

	Logger logger = Logger.getLogger(changeCtroller.class);

	@Autowired
	private changeSer changeSer;

	@Autowired
	private EmpInfoSer empInfoSer;

	@Autowired
	private AuthorityUtil authorityUtil;

	@Autowired
	private MyHomeSer myHomeSer;

	@Autowired
	@Qualifier("loginSerImpl")
	private LoginSer loginStr;

	/**
	 * 更改使用者密码
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/changePassword")
	public ModelAndView changePassword(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		modelMap.put("local_Name", admin.getLocalName());

		return new ModelAndView("/ess/change/changePassword", modelMap);
	}

	@RequestMapping(value = "/changePassword2")
	@ResponseBody
	public Map<String, Object> changePassword2(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.changeSer.changePassword(request);
		// String isEssSystem =
		// StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
		// int result=1;
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success", request));// 保存成功

		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail", request));// 保存失败
		}
		return map;
	}

	/**
	 * 更改使用者
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/changeUser")
	public ModelAndView changeUser(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap linkMap = (LinkedHashMap) changeSer
				.getPersonalInfoByPid(request);
		modelMap.put("personalInfo", linkMap);
		modelMap.put("local_Name", admin.getLocalName());

		return new ModelAndView("/ess/change/changeUser", modelMap);
	}

	/*
	 * viewApply end
	 */

	// 综合简介查询弹出页面 放大镜
	@RequestMapping(value = "/viewEmpInfoListSearch")
	public ModelAndView viewEmpInfoTanchuList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String firstFlag = request.getParameter("firstFlag");
		String searchChange = StringUtil.checkNull(request
				.getParameter("searchChange"));
		if (firstFlag != null && !"".equals(firstFlag)) {
			// 员工信息查询
			modelMap.put("empInfo", empInfoSer.getEmpInfoList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, empInfoSer
					.getEmpInfoListCnt(request));
		} else {
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
		}
		modelMap.put("positionList", empInfoSer.getPositionList(request));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("authority", authorityUtil
				.isSuperUser(admin.getPersonId()));
		modelMap.put("searchChange", searchChange);
		modelMap.put("defaultCpny",
				request.getParameter("defaultCpny") == null ? admin.getCpnyId()
						: request.getParameter("defaultCpny"));
		// 分页
		modelMap.put("pageNum", request.getParameter("pageNum"));
		modelMap.put("numPerPage", request.getParameter("numPerPage"));
		return new ModelAndView("/ess/change/viewEmpInfoListSearch", modelMap);
	}

	@RequestMapping(value = "/in")
	@ResponseBody
	public ModelAndView loginIn(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// logger.info("login waiting...");

		// 在执行操作前先把登陆者id存入Session中 以用来控制权限
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		if (!admin.getAdminID().equals(request.getParameter("PEROSN_ID_ID"))
				&& request.getSession().getAttribute("CurrentOperator") == null) {

			request.getSession().setAttribute("CurrentOperator",
					admin.getAdminID());// 如果不为空而且不等于自己就把当前操作者ID存入
			// 保证只在第一次使用者变更时存入
		}
		request.getSession().removeAttribute("LoginUser");// 移除之前的信息
		String backInfo = changeSer.findUser(request, response);

		Map<String, Object> map = new HashMap<String, Object>();

		// String isEssSystem =
		// StringUtil.checkNull(request.getParameter("isEssSystem"),"0");
		// int result=1;

		map.put("statusCode", "200");
		map.put("message", backInfo);// 保存成功

		String defaultRoleGroupId = StringUtil.checkNull(request
				.getParameter("defaultRoleGroupId"), "00002");
		String defaultRoleGroupName = StringUtil.checkNull(request
				.getParameter("defaultRoleGroupName"), "MyHome");
		String defaultRoleGroupNameDis  = StringUtil.checkNull(request
				.getParameter("defaultRoleGroupNameDis"), "MyHome");
		modelMap.put("defaultRoleGroupId", defaultRoleGroupId);
		modelMap.put("defaultRoleGroupName", defaultRoleGroupName);
		modelMap.put("defaultRoleGroupNameDis", defaultRoleGroupNameDis);
		// 根据personid获取该登陆者的所有权限组
		List roleGroupList = loginStr.getRoleGroupListByPersonId(request,
				response);
		// 移除掉当前选择的权限组 第一次登陆默认是MyHome权限组
		for (int i = 0; i < roleGroupList.size(); i++) {
			LinkedHashMap menuMap = (LinkedHashMap) roleGroupList.get(i);
			if (menuMap.get("ROLE_GROUP_ID").equals(defaultRoleGroupId))
				roleGroupList.remove(i);
		}
		modelMap.put("roleGroupList", roleGroupList);

		// 根据权限组ID获取对应的二级菜单
		List menuList = loginStr.getTopMenuByRoleGroupId(request, response);
		modelMap.put("menuList", menuList);
		// 获取默认的第一个二级菜单code 为了页面初始化左侧菜单
		if (menuList != null && menuList.size() > 0)
			modelMap.put("defaultSecMenu", ((LinkedHashMap) menuList.get(0))
					.get("MENU_CODE"));
		else
			modelMap.put("defaultSecMenu", "ess0100");
		// 让首页面左侧 默认显示人事模块信息
		List leftList = loginStr.getLeftMenuForPartner(request, response);
		modelMap.put("leftList", leftList);
        
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		// 公告 --魏正晨 2014-06-19
		modelMap.put("getNoticeList", loginStr.getNoticeList(paramMap));
		// modelMap.put("getNoticeListCnt", getNoticeListCnt);
		
		//考勤异常
		List viewAttendanceEx = this.loginStr.viewAttendanceEx(request);
		modelMap.put("viewAttendanceEx", viewAttendanceEx);
		modelMap.put("viewAttendanceExCnt", viewAttendanceEx == null ? 0:viewAttendanceEx.size());
		//Approval Status
		List viewApprovalStatusList = this.loginStr.viewApplyList(request);
		modelMap.put("viewApprovalStatusList", viewApprovalStatusList);
		
		Object tipsMap = myHomeSer.getTipsForLogin(request);
		Object manualMenu = myHomeSer.getManualMenu(request);
		modelMap.put("tipsMap", tipsMap);
		modelMap.put("manualMenu", manualMenu);

		modelMap.put("admin", admin);

		return new ModelAndView("/login/home_partner", modelMap);

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/home_partner", method = RequestMethod.GET)
	public ModelAndView loginHomePartner(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String defaultRoleGroupId = StringUtil.checkNull(request
				.getParameter("defaultRoleGroupId"), "00002");
		String defaultRoleGroupName = StringUtil.checkNull(request
				.getParameter("defaultRoleGroupName"), "MyHome");
		modelMap.put("defaultRoleGroupId", defaultRoleGroupId);
		modelMap.put("defaultRoleGroupName", defaultRoleGroupName);
		// 根据personid获取该登陆者的所有权限组
		List roleGroupList = loginStr.getRoleGroupListByPersonId(request,
				response);
		// 移除掉当前选择的权限组 第一次登陆默认是MyHome权限组
		for (int i = 0; i < roleGroupList.size(); i++) {
			LinkedHashMap menuMap = (LinkedHashMap) roleGroupList.get(i);
			if (menuMap.get("ROLE_GROUP_ID").equals(defaultRoleGroupId))
				roleGroupList.remove(i);
		}
		modelMap.put("roleGroupList", roleGroupList);

		// 根据权限组ID获取对应的二级菜单
		List menuList = loginStr.getTopMenuByRoleGroupId(request, response);
		modelMap.put("menuList", menuList);
		// 获取默认的第一个二级菜单code 为了页面初始化左侧菜单
		if (menuList != null && menuList.size() > 0)
			modelMap.put("defaultSecMenu", ((LinkedHashMap) menuList.get(0))
					.get("MENU_CODE"));
		else
			modelMap.put("defaultSecMenu", "ess0100");
		// 让首页面左侧 默认显示人事模块信息
		List leftList = loginStr.getLeftMenuForPartner(request, response);
		modelMap.put("leftList", leftList);

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Object tipsMap = myHomeSer.getTipsForLogin(request);
		Object manualMenu = myHomeSer.getManualMenu(request);
		modelMap.put("tipsMap", tipsMap);
		modelMap.put("manualMenu", manualMenu);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("USERNO", admin.getUserNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", admin.getLanguage());
		paramMap.put("STATUS_CODE", "1375");// 离职人员
		paramMap.put("EMP_OFFICE", "15120");// 离职人员
		paramMap.put("PROBATION_STATUS_CODE", "('1373','1374')");// 实习

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		// ///为财务报表加来】两个权限
        
		modelMap.put("isAccountUser", authorityUtil.isAccountUser(admin
				.getPersonId()));

		modelMap.put("isAccountTxtUser", authorityUtil.isAccountTxtUser(admin
				.getPersonId()));

		return new ModelAndView("/login/home_partner", modelMap);
	}

}

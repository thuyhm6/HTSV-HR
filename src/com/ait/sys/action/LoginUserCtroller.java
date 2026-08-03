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
import com.ait.sys.service.LoginUserSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC (c) Company: LDCC
 * 
 * @fileName LoginUserCtroller.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:19:24
 * @version 5.0
 */
@Controller
@RequestMapping(value = "/sys/rightsManagement")
public class LoginUserCtroller {
	Logger logger = Logger.getLogger(LoginUserCtroller.class);

	@Autowired
	private LoginUserSer loginUserSer;

	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private AuthorityUtil authorityUtil;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLoginUser")
	public ModelAndView viewLoginUserList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List loginUserList = this.loginUserSer.getLoginUserList(request);
		for (int i = 0; i < loginUserList.size(); i++) {
			List relationList = this.loginUserSer
					.getLoginUserRoleGroupRelation(request,
							((Map) loginUserList.get(i)).get("USER_NO"));
			((Map) loginUserList.get(i)).put("relationList", relationList);
		}
		int loginUserCnt = this.loginUserSer.getLoginUserCnt(request);
		modelMap.put("loginUserList", loginUserList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, loginUserCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2342"));
		return new ModelAndView("/sys/rightsManagement/viewLoginUser", modelMap);
	}

	/**
	 * 登陆用户信息导出
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLoginUserExcel")
	public ModelAndView viewEmpPaRiseExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List loginUserList = this.loginUserSer.getLoginUserExcelList(request);
		for (int i = 0; i < loginUserList.size(); i++) {
			List relationList = this.loginUserSer
					.getLoginUserRoleGroupRelation(request,
							((Map) loginUserList.get(i)).get("USER_NO"));
			((Map) loginUserList.get(i)).put("relationList", relationList);
		}
		modelMap.put("loginUserList", loginUserList);

		return new ModelAndView("/sys/rightsManagement/viewLoginUserExcel",
				modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/validatePersonIdExist")
	@ResponseBody
	public void validatePersonIdExist(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int result = this.loginUserSer.validatePersonIdExist(request);
		Map modelMap = new HashMap<String, Object>();
		if (result > 0) {
			modelMap.put("flagYn", "Y");
		} else {
			modelMap.put("flagYn", "N");
		}
		response.getWriter().print(JsonUtil.writeInternal(modelMap));
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addLoginUserView")
	public ModelAndView addLoginUserView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List loginUserInfoRolesGroupList = this.loginUserSer.getLoginUserRolesGroupList(request);
		modelMap.put("loginUserInfoRolesGroupList", loginUserInfoRolesGroupList);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		return new ModelAndView("/sys/rightsManagement/addLoginUserView",
				modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateLoginUserView")
	public ModelAndView updateLoginUserView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Object loginUserInfo = this.loginUserSer.getLoginUser(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("USER_NAME", admin.getUsername());
		List loginUserInfoRolesGroupList = this.loginUserSer.getLoginUserRolesGroupList(request);

		List empTypeCodeList = this.loginUserSer.getEmpTypeCodeList(request);
		List statisticList = this.loginUserSer.getSySupervisorEmpTypeCodeList(request);
		modelMap.put("empTypeCodeList", empTypeCodeList);
		modelMap.put("statisticList", statisticList);
		modelMap.put("loginUserInfo", loginUserInfo);
		modelMap.put("loginUserInfoRolesGroupList", loginUserInfoRolesGroupList);
		int isSuperHrUser = authorityUtil.isSuperHrUser(admin.getPersonId());
		modelMap.put("isSuperHrUser", isSuperHrUser);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		return new ModelAndView("/sys/rightsManagement/updateLoginUserView",
				modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getLoginUserDeptList")
	@ResponseBody
	public List getLoginUserDeptList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List deptInfoTreeList = this.loginUserSer.getDeptInfoTree(request);
		return deptInfoTreeList;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateLoginUserInfo")
	@ResponseBody
	public Map updateLoginUserInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = loginUserSer.updateLoginUserInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "sy0120");
			map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}

		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addLoginUserInfo")
	@ResponseBody
	public Map addLoginUserInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = loginUserSer.addLoginUserInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));
			map.put("navTabId", "sy0120");
			map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteLoginUser")
	@ResponseBody
	public Map deleteLoginUser(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = loginUserSer.deleteLoginUser(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));
			map.put("navTabId", "sy0120");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));
		}
		return map;
	}

	/**
	 * IP权限管理
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLoginUserIPList")
	public ModelAndView viewLoginUserIPList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List loginUserIPList = this.loginUserSer.getLoginUserIPList(request);
		modelMap.put("loginUserIPList", loginUserIPList);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "14015461"));
		return new ModelAndView("/sys/rightsManagement/viewLoginUserIPList",
				modelMap);
	}

	/**
	 * 资料室
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFileInfoList")
	public ModelAndView viewFileInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List viewFileInfoList = this.loginUserSer.viewFileInfoList(request);
		modelMap.put("viewFileInfoList", viewFileInfoList);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2343"));
		return new ModelAndView("/sys/rightsManagement/viewFileInfoList",
				modelMap);
	}

	/**
	 * 资料url
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFileUrlInfo")
	public ModelAndView viewFileUrlInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List viewFileUrlInfo = this.loginUserSer.viewFileUrlInfo(request);

		Object viewFileInfo = this.loginUserSer.viewFileInfo(request);

		modelMap.put("viewFileUrlInfo", viewFileUrlInfo);

		modelMap.put("viewFileInfo", viewFileInfo);

		return new ModelAndView("/sys/rightsManagement/viewFileUrlInfo",
				modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addFileInfoView")
	public ModelAndView addFileInfoView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
	  
		return new ModelAndView("/sys/rightsManagement/addFileInfoView",
				modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addFileInfo")
	@ResponseBody
	public Map addFileInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = loginUserSer.addFileInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));
			map.put("navTabId", "sy0120");
			map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteFileInfo")
	@ResponseBody
	public Map deleteFileInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = loginUserSer.addFileInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));
			map.put("navTabId", "sy0120");
			map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateFileInfo")
	@ResponseBody
	public Map updateFileInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = loginUserSer.addFileInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));
			map.put("navTabId", "sy0120");
			map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateFileInfoView")
	public ModelAndView updateFileInfoView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		return new ModelAndView("/sys/rightsManagement/updateFileInfoView",
				modelMap);
	}
}

package com.ait.sys.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.MenuSer;
import com.ait.sys.service.RolesGroupSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC (c) Company: LDCC
 * 
 * @fileName RolesGroupCtroller.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:19:44
 * @version 5.0
 */
@Controller
@RequestMapping(value = "/sys/rightsManagement")
public class RolesGroupCtroller {

	Logger logger = Logger.getLogger(RolesGroupCtroller.class);

	@Autowired
	private RolesGroupSer rolesGroupSer;

	@Autowired
	private MenuSer menuSer;

	@Autowired
	private CompanySer companySer;

	@Autowired
	private ToolMenuSer toolMenuSer;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewRolesGroup")
	public ModelAndView viewRolesGroupList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List rolesGroupList = this.rolesGroupSer.getRolesGroupList(request);
		int rolesGroupCnt = this.rolesGroupSer.getRolesGroupCnt(request);
		modelMap.put("rolesGroupList", rolesGroupList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, rolesGroupCnt);
		modelMap.put(
				"toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2341"));
		return new ModelAndView("/sys/rightsManagement/viewRolesGroup",
				modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteRolesGroup")
	@ResponseBody
	public Map deleteRolesGroup(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			rolesGroupSer.deleteRolesGroupInfo(request);
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
			map.put("navTabId", "sy0320");
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addRolesGroupInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map addRolesGroupInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int checkNum = this.rolesGroupSer.checkRolesGroupIdExsit(request);
		try {
			if (checkNum == 0) {
				this.rolesGroupSer.addRolesGroupInfo(request);
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));// "添加成功"
				map.put("navTabId", "sy0320");
				map.put("callbackType", "closeCurrent");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.sys.rolesGroup.roleIdIsExsist", request));// "权限组ID已存在,请重新选择!"
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message",
					TipMessage.getTipMessage("alert.message.add_fail", request));// "添加失败"
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addRolesGroupView")
	public ModelAndView addRolesGroupView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));

		String roleid = this.companySer.getRoleID(request);

		List info = menuSer.getMenuTree(request);
		modelMap.put("roleid2", roleid);
		modelMap.put("info", info);
		modelMap.put("SYS_TYPE", request.getParameter("SYS_TYPE"));
		return new ModelAndView("/sys/rightsManagement/addRolesGroupView",
				modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateRolesGroupView")
	public ModelAndView updateRolesGroupView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("roleGroup", this.rolesGroupSer.getRolesGroup(request));
//		modelMap.put("cpnyList", companySer.getCompanyItemList(request));
		List info = menuSer.getMenuTree(request);
		modelMap.put("info", info);
		List subMenuLists = this.menuSer.getMenuForRolesGroup(request);
		int rolesGroupCnt = this.menuSer.getMenuForRolesGroupCnt(request);
		modelMap.put("subMenuLists", subMenuLists);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, rolesGroupCnt);
		return new ModelAndView("/sys/basicMaintenance/updateRolesGroupView",
				modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateRolesGroupInfo")
	@ResponseBody
	public Map updateRolesGroupInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List list = new ArrayList();
		list.remove(null);
		int checkNum = this.rolesGroupSer.checkRolesGroupIdExsit(request);
		try {
			if (checkNum == 0) {
				rolesGroupSer.updateRolesGroupInfo(request);
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));// "修改成功"
				map.put("navTabId", "sy0110");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.sys.rolesGroup.roleIdIsExsist", request));
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));// "修改失败"
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addRolesGroupMenuView")
	public ModelAndView addRolesGroupMenuView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List subMenuLists = this.rolesGroupSer.getMenuForRolesGroup(request);
		int rolesGroupCnt = (subMenuLists == null ? 0 : subMenuLists.size());
		List selectLists = this.rolesGroupSer.getSelectMenuForRoles(request);
		modelMap.put("selectedMenu", this.menuSer.getMenuByNo(request));
		modelMap.put("subMenuLists", subMenuLists);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, rolesGroupCnt);
		modelMap.put("selectLists", selectLists);
		return new ModelAndView(modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateRolesGroupMenuView")
	public ModelAndView updateRolesGroupMenuView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List subMenuLists = this.rolesGroupSer.getMenuForRolesGroup(request);
		int rolesGroupCnt = (subMenuLists == null ? 0 : subMenuLists.size());
		List selectLists = this.rolesGroupSer.getSelectMenuForRoles(request);
		modelMap.put("selectedMenu", this.menuSer.getMenuByNo(request));
		modelMap.put("subMenuLists", subMenuLists);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, rolesGroupCnt);
		modelMap.put("selectLists", selectLists);
		return new ModelAndView(modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveOrUpdateRolesGroupInfo")
	@ResponseBody
	public Map saveOrUpdateRolesGroupInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = rolesGroupSer.saveOrUpdateRolesGroupInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			// map.put("message", TipMessage.getTipMessage(
			// "alert.message.add_success", request));// "保存成功"
			map.put("navTabId", "sy0110");
			map.put("callbackType", "closeCurrent");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// "保存成功"
		} else {
			map.put("statusCode", "300");
			map.put("message",
					TipMessage.getTipMessage("alert.message.add_fail", request));// "保存失败"

		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateERolesGroupInfo")
	@ResponseBody
	public Map updateERolesGroupInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = rolesGroupSer.saveOrUpdateRolesGroupInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// "修改成功"
			map.put("navTabId", "sy0110");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));// "修改失败"
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteRolesGroupView")
	@ResponseBody
	public Map deleteRolesGroupView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.rolesGroupSer.deleteRolesGroupView(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// "删除成功"
			map.put("navTabId", "sy0110");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// "删除失败"
		}
		return map;
	}

	/**
	 * 查看SY_ROLE_GROUP表中的组数据(select the datas from table of SY_ROLE_GROUP)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/syRoleGroup/viewSyRolesGroupList")
	public ModelAndView viewSyRolesGroupList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List rolesGroupList = this.rolesGroupSer.getSyRolesGroupList(request);
		int rolesGroupCnt = this.rolesGroupSer.getSyRolesGroupCnt(request);
		modelMap.put("rolesGroupList", rolesGroupList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, rolesGroupCnt);
		modelMap.put(
				"toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "3276"));
		return new ModelAndView(
				"/sys/rightsManagement/syRoleGroup/viewSyRolesGroupList",
				modelMap);
	}

	@RequestMapping(value = "/syRoleGroup/addSyRolesGroupView")
	public ModelAndView addSyRolesGroupView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));
		modelMap.put("SYS_TYPE", request.getParameter("SYS_TYPE"));
		return new ModelAndView(
				"/sys/rightsManagement/syRoleGroup/addSyRolesGroupView",
				modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getRolesIfChecked")
	@ResponseBody
	public void getRolesIfChecked(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List rolesIfChecked = this.rolesGroupSer
				.getSyRolesIfCheckedList(request);
		response.getWriter().print(JsonUtil.writeInternal(rolesIfChecked));
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/syRoleGroup/saveOrUpdateSyRolesGroupInfo")
	@ResponseBody
	public Map saveOrUpdateSyRolesGroupInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = rolesGroupSer.saveOrUpdateSyRolesGroupInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// "保存成功"
			map.put("navTabId", "sy0470");
		} else {
			map.put("statusCode", "300");
			map.put("message",
					TipMessage.getTipMessage("alert.message.add_fail", request));// "保存失败"
		}
		return map;
	}

	@RequestMapping(value = "/syRoleGroup/updateSyRolesGroupView")
	public ModelAndView updateSyRolesGroupView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("roleGroup",
				this.rolesGroupSer.getSyRolesGroupInfo(request));
		modelMap.put("cpnyList", companySer.getAllCompanyItemList(request));

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("SYS_TYPE", request.getParameter("SYS_TYPE"));
		return new ModelAndView("/sys/basicMaintenance/updateSyRolesGroupView",
				modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/syRoleGroup/deleteSyRolesGroupView")
	@ResponseBody
	public Map deleteSyRolesGroupView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		int result = this.rolesGroupSer.deleteSyRolesGroupView(request);
		Map<String, Object> map = new HashMap<String, Object>();
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// "删除成功"
			map.put("navTabId", "sy0470");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// "该信息使用中,不能删除"
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkExistRoleId")
	public void checkExistRoleId(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List list = this.rolesGroupSer.getRolesGroupList(request);
		if (list == null || (list != null && list.size() == 0)) {
			response.getWriter().print("ok");
		} else {
			response.getWriter().print("no");
		}
	}

	/****************************** gsod权限 *********************************/
	/***
	 * gsod权限管理的List页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewGsodRoleGroupList")
	public ModelAndView viewGsodRoleGroupList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List gsodRoleGroupList = this.rolesGroupSer.getGsodRoleGroupList(request);
		int gsodRoleGroupCnt = this.rolesGroupSer.getGsodRoleGroupCnt(request);
		modelMap.put("gsodRoleGroupList", gsodRoleGroupList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, gsodRoleGroupCnt);
		modelMap.put(
				"toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "218532"));
		return new ModelAndView("/sys/rightsManagement/viewGsodRoleGroupList",
				modelMap);
	}

	/***
	 * 去添加页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addGsodRoleGroupInfoView")
	public ModelAndView addGsodRoleGroupInfoView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String SY_ROLE_GROUP_NAME = request.getParameter("SY_ROLE_GROUP_NAME");
		String role_GSOD_NAME = request.getParameter("role_GSOD_NAME");
		modelMap.put("SY_ROLE_GROUP_NAME", SY_ROLE_GROUP_NAME);
		modelMap.put("role_GSOD_NAME", role_GSOD_NAME);
		modelMap.put("rolesGroupList",
				rolesGroupSer.getSyRolesGroupListGsodAdd(request));
		return new ModelAndView(
				"/sys/rightsManagement/addGsodRoleGroupInfoView", modelMap);
	}

	/**
	 * 添加
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addGsodRoleGroupInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map addGsodRoleGroupInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int checkNum = this.rolesGroupSer.checkGsodRoleGroupExsit(request);
		try {
			if (checkNum == 0) {
				int num = this.rolesGroupSer.addGsodRoleGroupInfo(request);
				if(num==1){
				    map.put("statusCode", "200");
				    map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));// "添加成功"
				    map.put("navTabId", "sys2014gsod");
				    map.put("callbackType", "closeCurrent");
			    } else {
				    map.put("statusCode", "300");
				    map.put("message", TipMessage.getTipMessage(
						"alert.message.add_fail", request));// "该信息已经存在，请重新添加!"
			    }
			}else if(checkNum==1){
				map.put("statusCode", "300");
			    map.put("message", "名称已经存在，请重新添加！");// "该信息已经存在，请重新添加!"
			}else{
				map.put("statusCode", "300");
			    map.put("message", "数据已经存在，请重新添加！");// "该信息已经存在，请重新添加!"
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message",
					TipMessage.getTipMessage("alert.message.add_fail", request));// "添加失败"
		}
		return map;
	}

	/**
	 * 去修改页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateGsodRoleGroupInfoVew")
	public ModelAndView updateGsodRoleGroupInfoView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String SY_ROLE_GROUP_NAME = request.getParameter("SY_ROLE_GROUP_NAME");
		String groupNo = request.getParameter("GROUPNO");
		modelMap.put("SY_ROLE_GROUP_NAME", SY_ROLE_GROUP_NAME);
		modelMap.put("GROUPNO", groupNo);
		List gsodRoleGroupList = this.rolesGroupSer
				.getGsodRoleGroupList(request);
		String ROLE_GSOD_NAME = null;
		if (gsodRoleGroupList != null && gsodRoleGroupList.size() > 0) {
			LinkedHashMap gsodRoleInfo = (LinkedHashMap) gsodRoleGroupList
					.get(0);
			ROLE_GSOD_NAME = gsodRoleInfo.get("ROLE_GSOD_NAME").toString();
		}
		modelMap.put("rolesGroupList",
				rolesGroupSer.getSyRolesGroupListGsod(request));
		modelMap.put("gsodRoleGroupList", gsodRoleGroupList);
		modelMap.put("ROLE_GSOD_NAME", ROLE_GSOD_NAME);
		return new ModelAndView(
				"/sys/rightsManagement/updateGsodRoleGroupInfoVew", modelMap);
	}

	/**
	 * 修改
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateGsodRoleGroupInfo")
	@ResponseBody
	public Map updateGsodRoleGroupInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int errorNum = this.rolesGroupSer.deleteGsodRoleGroupInfoAll(request);
		if (errorNum == 1) {
			int checkNum = this.rolesGroupSer.checkGsodRoleGroupExsit(request);
			if (checkNum == 0) {
				int result = rolesGroupSer.addGsodRoleGroupInfo(request);
				if (result > 0) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_success", request));// "修改成功"
					map.put("navTabId", "sys2014gsod");
					map.put("callbackType", "closeCurrent");
				} else {
					map.put("statusCode", "300");
					map.put("message",
							TipMessage.getTipMessage("修改失败！", request));// "该信息已经存在，请重新添加!"
				}
			} else if(checkNum == 1){
				map.put("statusCode", "300");
			    map.put("message", "名称已经存在，请重新命名！");
			}else{
				map.put("statusCode", "300");
				map.put("message","数据已经存在，请重新修改！");// "该信息经存在，请重新添加!"
			}
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteGsodRoleGroupInfo")
	@ResponseBody
	public Map deleteGsodRoleGroupInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			rolesGroupSer.deleteGsodRoleGroupInfo(request);
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
			map.put("navTabId", "sys2014gsod");
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}

	/***
	 * gsod权限管理的List页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	// @SuppressWarnings("unchecked")
	// @RequestMapping(value = "/viewRolesGroupList")
	// public ModelAndView getSyRolesGroupListGsod(HttpServletRequest request,
	// HttpServletResponse response, ModelMap modelMap) throws Exception {
	// List gsodRoleGroupList =
	// this.rolesGroupSer.getGsodRoleGroupList(request);
	// int gsodRoleGroupCnt = this.rolesGroupSer.getGsodRoleGroupCnt(request);
	// modelMap.put("gsodRoleGroupList", gsodRoleGroupList);
	// modelMap.put(UiUtil.TOTAL_COUNT_NAME, gsodRoleGroupCnt);
	// modelMap.put("toolbarInfo",
	// request.getParameter("menuNo") != null ? toolMenuSer
	// .getToolMenu(request) : toolMenuSer.getToolMenuForNo(
	// request, "2341"));
	// return new ModelAndView("/sys/rightsManagement/viewGsodRoleGroupList",
	// modelMap);
	// }
}

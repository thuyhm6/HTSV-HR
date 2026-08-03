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
import com.ait.sys.service.MenuSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC (c) Company: LDCC
 * 
 * @fileName MenuCtroller.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:19:33
 * @version 5.0
 */
@Controller
@RequestMapping(value = "/sys/menu")
public class MenuCtroller {

	Logger logger = Logger.getLogger(MenuCtroller.class);

	@Autowired
	private MenuSer menuSer;

	@Autowired
	private CompanySer companySer;

	@Autowired
	private ToolMenuSer toolMenuSer;

	/**
	 * 页面跳转到菜单列表页面(Jump to the lists of management)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{getMenuList}")
	public ModelAndView viewMenuList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("MENUS", menuSer.getMenuList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, menuSer.getMenuListCnt(request));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2468"));
		return new ModelAndView(modelMap);
	}

	/**
	 * 更改菜单状态（有效到失效或者失效到有效） Description:update the ACTIVITY of the menu
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/disableMenu")
	@ResponseBody
	public Map getdisableMenuList(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.menuSer.disableMenu(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "sy0410");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 页面跳转到菜单添加页面(Jump to the page to add a new menu)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addNewMenuView")
	public ModelAndView addNewMenuView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return new ModelAndView(modelMap);
	}

	/**
	 * 页面跳转到菜单修改页面(Jump to the page to update a menu)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editMenuView")
	public ModelAndView editMenuView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("menu", this.menuSer.getMenuByNo(request));
		return new ModelAndView(modelMap);
	}

	/**
	 * 获取菜单树 Description:get menu data
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getMenuTree")
	@ResponseBody
	public List getMenuTree(HttpServletRequest request) throws Exception {
		List info = menuSer.getMenuTree(request);
		return info;
	}

	/**
	 * 保存菜单 Description:save the infomation of a menu
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveMenu")
	@ResponseBody
	public Map saveMenu(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.menuSer.saveMenu(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));
			map.put("formId", "viewMenuList_Form");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}

	/**
	 * 修改之后保存菜单 Description:save the menu after update
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateMenu")
	@ResponseBody
	public Map updateMenu(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.menuSer.updateMenu(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("formId", "viewMenuList_Form");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 屏幕参数list页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewMenuParamList")
	public ModelAndView viewMenuParamList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		modelMap.put("add_name", TipMessage.getTipMessage(
				"sys.basic.title.designatedLegalPerson", request));//指定法人
		modelMap.put("MenuParam", menuSer.getMenuParamList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, menuSer
				.getMenuParamListCnt(request));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2567"));
		return new ModelAndView(modelMap);
	}

	/**
	 * 添加菜单法人关系页面（set the company which be relation to company）
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addMenuParamView")
	public ModelAndView addMenuParamView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("companyList", companySer.getCompanyItemList(request));
		List info = menuSer.getMenuTreeForAll(request);
		modelMap.put("parentMenuList", info);
		return new ModelAndView(modelMap);
	}

	/**
	 * 编辑修改屏幕参数页面 THE PAGE TO UPDATE SY_MENU_PARAM
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editMenuParamView")
	public ModelAndView editMenuParamView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("companyList", companySer.getCompanyItemList(request));
		return new ModelAndView(modelMap);
	}

	/**
	 * 获取所有作为父节点的菜单树 Description:get menus to be parents
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
		List codeInfoTreeList = this.menuSer.getMenuTreeForAll(request);
		return codeInfoTreeList;
	}

	/**
	 * 获取菜单树的数据 Description:
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getMenuTreeForAll")
	@ResponseBody
	public List getMenuTreeForAll(HttpServletRequest request) throws Exception {
		List info = menuSer.getMenuTreeForAll(request);
		return info;
	}

	/**
	 * 保存屏幕参数 Description:insert into sy_menu_param
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveMenuParam")
	@ResponseBody
	public Map saveMenuParam(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.menuSer.saveMenuParam(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));
			map.put("navTabId", "sy0460");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}

	/**
	 * 更新屏幕参数 Description:update sy_menu_param
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateMenuParam")
	@ResponseBody
	public Map updateMenuParam(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.menuSer.updateMenuParam(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "sy0460");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 
	 * Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMenuTreeByParentMenu")
	public ModelAndView getMenuTreeByParentMenu(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("CPNY_ID", request.getParameter("CPNY_ID"));
		modelMap.put("MENU_NO", request.getParameter("MENU_NO"));
		modelMap.put("companyList", companySer.getCompanyItemList(request));
		modelMap.put("subMenus", menuSer.getMenuTreeByParentMenu(request));
		return new ModelAndView(modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getMenuTreeListByParentMenu")
	@ResponseBody
	public List getMenuTreeListByParentMenu(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List codeInfoTreeList = this.menuSer.getMenuTreeByParentMenu(request);
		return codeInfoTreeList;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/validateMenuIdExist")
	@ResponseBody
	public void validateMenuIdExist(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List menuList = this.menuSer.validateMenuIdExist(request);
		Map modelMap = new HashMap<String, Object>();
		if (menuList == null || (menuList != null && menuList.size() == 0)) {
			modelMap.put("flagYn", "Y");
		} else {
			modelMap.put("flagYn", "N");
		}
		response.getWriter().print(JsonUtil.writeInternal(modelMap));
	}

}

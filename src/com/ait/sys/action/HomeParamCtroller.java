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
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.HomeParamSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;

/**
 * Copyright: LDCC (c) Company: LDCC
 * 
 * @fileName HomeParamCtroller.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-3-19 pm 05:47:54
 * @version 5.0
 */
@Controller
@RequestMapping(value = "/sys/homeParam")
public class HomeParamCtroller {
	Logger logger = Logger.getLogger(HomeParamCtroller.class);

	@Autowired
	private HomeParamSer homeParamSer;

	@Autowired
	private CompanySer companySer;

	/**
	 * 跳转到查看系统首页参数配置 Description:SELECT FROM SY_Home_CHECK_INFO
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewHomeParamList")
	public ModelAndView viewHomeParamList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List homeParamList = this.homeParamSer.getHomeParamList(request);
		modelMap.put("homeParamList", homeParamList);
		return new ModelAndView("/sys/homeParam/viewHomeParamList", modelMap);
	}

	/**
	 * 跳转更新系统首页参数配置 页面（指定参数给某个法人） Description:the page of init data to
	 * sy_param_info_param
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHomeParamView")
	public ModelAndView updateHomeParamView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Object homeParam = this.homeParamSer.getHomeParam(request);
		// List roleList=this.homeParamSer.getRoleListByCpnyId(request);
		modelMap.put("homeParam", homeParam);
		// modelMap.put("roleList", roleList) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("companyList", companySer.getCompanyItemList(request));
		modelMap.put("reqest_PARAM_NO", StringUtil.checkNull(request.getParameter("PARAM_NO")));
		return new ModelAndView("/sys/homeParam/updateHomeParamView", modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getRoleListByCpnyId")
	@ResponseBody
	public List getRoleListByCpnyId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List roleList = this.homeParamSer.getRoleListByCpnyId(request);

		return roleList;
	}


	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getEmpTypeListByCpnyId")
	@ResponseBody
	public List getEmpTypeListByCpnyId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List empTypeList = this.homeParamSer.getEmpTypeListByCpnyId(request);

		return empTypeList;
	}
	
	@RequestMapping(value = "/updateHomeParamCpnyView")
	public ModelAndView updateHomeParamCpnyView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Object homeParam = this.homeParamSer.getHomeParam(request);
		// List roleList=this.homeParamSer.getRoleListByCpnyId(request);
		modelMap.put("homeParam", homeParam);
		// modelMap.put("roleList", roleList) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("companyList", companySer.getCompanyItemList(request));
		return new ModelAndView("/sys/homeParam/updateHomeParamCpnyView",
				modelMap);
	}

	/**
	 * 更新系统首页参数配置 （只更新国际化信息） Description:update globalName
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateHomeParamCpnyInfo")
	@ResponseBody
	public Map updateHomeParamCpnyInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.homeParamSer.updateHomeParamCpnyInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "sy0205");
			map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 更新系统首页参数配置 （只更新国际化信息） Description:update globalName
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateHomeParamInfo")
	@ResponseBody
	public Map updateHomeParamInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.homeParamSer.updateHomeParamInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "sy0205");
			map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 跳转到查看系统首页参数 （每个法人只pdateHomeParamView能看到自己的数据） Description:view
	 * sys_param_info_param
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewHomeParamToCpny")
	public ModelAndView viewHomeParamToCpny(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List homeCheckParamList = this.homeParamSer
				.getHomeCheckParamList(request);
		modelMap.put("homeParamList", homeCheckParamList);
		return new ModelAndView("/sys/homeParam/viewHomeParamToCpny", modelMap);
	}

	/**
	 * 更新系统首页参数配置（只更新国际化信息） Description:update globalname
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateHomeCheckParamInfo")
	@ResponseBody
	public Map updateHomeCheckParamInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.homeParamSer.updateHomeCheckParamInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "sy0205");
			map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

}

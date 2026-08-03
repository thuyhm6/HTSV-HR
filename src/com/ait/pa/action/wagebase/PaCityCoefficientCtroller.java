package com.ait.pa.action.wagebase;

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
import com.ait.pa.service.wagebase.PaCityCoefficientSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: PaCityCoefficientCtroller.java
 * @Description:
 * @Create date: 2012-3-16 下午12:05:30
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/wagebase")
public class PaCityCoefficientCtroller {
	Logger logger = Logger.getLogger(PaCityCoefficientCtroller.class);

	@Autowired
	private PaCityCoefficientSer paCityCoefficientSer;

	@Autowired
	private ToolMenuSer toolMenuSer;

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewCityCoefficient")
	public ModelAndView viewPaAccountList(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List paCityCoefficientList = this.paCityCoefficientSer.paCityCoefficientList(request);
		int paCityCoefficientCnt = this.paCityCoefficientSer.paCityCoefficientCnt(request);

		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("paCityCoefficientList", paCityCoefficientList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paCityCoefficientCnt);
		modelMap.put("toolbarInfo",request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "15876"));

		return new ModelAndView("/pa/wagebase/viewCityCoefficient", modelMap);
	}

	/**
	 * 方法说明（中文，英文）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCityCoefficientView")
	public ModelAndView addCityCoefficientView(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("defaultCpny", admin.getCpnyId());

		return new ModelAndView("/pa/wagebase/addCityCoefficientView", modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addCityCoefficientInfo")
	@ResponseBody
	public Map addCityCoefficientInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int checkResult = this.paCityCoefficientSer.checkAddCityCoefficientInfo(request);
		if(checkResult == 0) {
			if (this.paCityCoefficientSer.addCityCoefficientInfo(request) == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));
				map.put("navTabId", "pa0505");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));
			}
		}else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.pa.wagebase.existWagebaseCity_add_fail",request));
		}

		return map;
	}

	@RequestMapping(value = "/updateCityCoefficientView")
	public ModelAndView updateCityCoefficientView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Object cityCoefficient = this.paCityCoefficientSer.paCityCoefficientInfo(request);

		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("cityCoefficient", cityCoefficient);
		return new ModelAndView("/pa/wagebase/updateCityCoefficientView",modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateCityCoefficientInfo")
	@ResponseBody
	public Map updateCityCoefficientInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int checkResult = this.paCityCoefficientSer.checkAddCityCoefficientInfo(request);
		if (checkResult == 1) {
			if (this.paCityCoefficientSer.updateCityCoefficientInfo(request) == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success", request));
				map.put("navTabId", "pa0505");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.update_fail", request));
			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.pa.wagebase.existWagebaseCity_update_fail",request));
		}

		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteCityCoefficientInfo")
	@ResponseBody
	public Map deleteCityCoefficientInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (this.paCityCoefficientSer.deleteCityCoefficientInfo(request) == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success", request));
			map.put("navTabId", "pa0505");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail", request));
		}
		return map;
	}
}
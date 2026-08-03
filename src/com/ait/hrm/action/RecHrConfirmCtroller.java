package com.ait.hrm.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.hrm.service.RecHrConfirmSer;
import com.ait.web.i18n.TipMessage;

@Controller
@RequestMapping(value = "/hrm/recruit")
public class RecHrConfirmCtroller {
	Logger logger = Logger.getLogger(RecHrConfirmCtroller.class);
	@Autowired
	private RecHrConfirmSer recHrConfirmSer;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/recHrConfirm")
	public ModelAndView viewRecHrConfirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List readyToHrConfirmInfoList = this.recHrConfirmSer
				.getReadyToHrConfirmInfoList(request);
		modelMap.put("readyToHrConfirmInfoList", readyToHrConfirmInfoList);
		return new ModelAndView("/hrm/recruit/recHrConfirm", modelMap);
	}
	
	/**
	 * 人事确认(recruitment HR Confirm)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/recruitmentHrConfirm")
	@ResponseBody
	public Map recruitmentHrConfirm(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = this.recHrConfirmSer.recruitmentHrConfirm(request);
			if (result == 1) {
				map.put("navTabId", "hr3706");
				map.put("message", TipMessage.getTipMessage(//保存成功
						"ar.alert.message.addempshift.success", request));
				map.put("statusCode", "200");				
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(//保存失败
						"alert.message.add_fail", request));
			}
		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		}
		return map;
	}
}

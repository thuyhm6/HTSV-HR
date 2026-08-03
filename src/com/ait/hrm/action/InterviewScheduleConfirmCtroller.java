package com.ait.hrm.action;

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

import com.ait.hrm.service.InterviewScheduleConfirmSer;
import com.ait.web.i18n.TipMessage;


@Controller
@RequestMapping(value = "/hrm/recruit")
public class InterviewScheduleConfirmCtroller {
	Logger logger = Logger.getLogger(InterviewScheduleConfirmCtroller.class);
	@Autowired
	private InterviewScheduleConfirmSer interviewScheduleConfirmSer;
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/interviewScheduleConfirm")
	public ModelAndView viewInterviewScheduleList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("interviewScheduleConfirmList", this.interviewScheduleConfirmSer.getInterviewScheduleConfirmList(request));
		return new ModelAndView("/hrm/recruit/interviewScheduleConfirm", modelMap);
	}
	
	//确认面试计划
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveInterviewConfirm")
	@ResponseBody
	public Map saveInterviewConfirm (HttpServletRequest request, HttpServletResponse response, ModelMap modelMap ) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int resuft = this.interviewScheduleConfirmSer.saveInterviewConfirm(request);
			if (resuft == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.add_success", request));// 保存成功
				map.put("navTabId", "hr3707");
			} else if (resuft == 2) {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("hr.alert.message.interviewConfirm.yimianshiwufaxiugai", request));
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));
			}
		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("statuscode", "300");
		}
		return map;
	}

}

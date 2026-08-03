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

import com.ait.hrm.service.InterviewScheduleSer;
import com.ait.web.i18n.TipMessage;

@Controller
@RequestMapping(value = "/hrm/recruit")
public class InterviewScheduleCtroller {
	Logger logger = Logger.getLogger(InterviewScheduleCtroller.class);
	@Autowired
	private InterviewScheduleSer interviewScheduleSer;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/interviewSchedule")
	public ModelAndView viewInterviewScheduleList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		modelMap.put("interviewScheduleList", this.interviewScheduleSer.getInterviewScheduleList(request));

		return new ModelAndView("/hrm/recruit/interviewSchedule", modelMap);
	}
	
	/**
	 * 为简历安排面试官 (add Rec Affirmer)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addRecAffirmInfo")
	@ResponseBody
	public Map addRecAffirmInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = this.interviewScheduleSer.addRecAffirmInfo(request);
			if (result == 1) {
				map.put("navTabId", "hr3704");
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

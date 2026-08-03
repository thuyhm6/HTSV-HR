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

import com.ait.hrm.service.InterviewProcessSer;
import com.ait.web.i18n.TipMessage;

@Controller
@RequestMapping(value = "/hrm/recruit")
public class InterviewProcessCtroller {
	Logger logger = Logger.getLogger(InterviewProcessCtroller.class);
	@Autowired
	private InterviewProcessSer interviewProcessSer;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/interviewProcess")
	public ModelAndView viewInterviewProcessList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List readyToInterviewInfoList = this.interviewProcessSer
				.getReadyToInterviewInfoList(request);
		modelMap.put("readyToInterviewInfoList", readyToInterviewInfoList);
		return new ModelAndView("/hrm/recruit/interviewProcess", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewInterviewAffirmList")
	public ModelAndView viewInterviewAffirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List interviewAffirmList= this.interviewProcessSer
				.getInterviewAffirmList(request);
		modelMap.put("interviewAffirmList", interviewAffirmList);
		return new ModelAndView("/hrm/recruit/viewInterviewAffirmList", modelMap);
	}
	
	/**
	 * 面试审批 (interview Affirm)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/interviewProcessApproval")
	@ResponseBody
	public Map interviewProcessApproval(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = this.interviewProcessSer.interviewProcessApproval(request);
			if (result == 1) {
				map.put("navTabId", "hr3705");
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

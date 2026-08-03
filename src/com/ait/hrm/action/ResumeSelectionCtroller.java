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

import com.ait.hrm.service.ResumeSelectionSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.UiUtil;

@Controller
@RequestMapping(value = "/hrm/recruit")
public class ResumeSelectionCtroller {
	Logger logger = Logger.getLogger(ResumeSelectionCtroller.class);
	@Autowired
	private ResumeSelectionSer resumeSelectionSer;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/resumeSelection")
	public ModelAndView viewResumeSelectionList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List resumeSelectionList = this.resumeSelectionSer
		.getResumeSelectionInfoList(request);
		int contractCnt = this.resumeSelectionSer.getResumeSelectionCnt(request);
		modelMap.put("itemList", resumeSelectionList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, contractCnt);
		return new ModelAndView("/hrm/recruit/resumeSelection", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateResume")
	@ResponseBody
	public Map updateResumeInfoForUpdate(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		int result = this.resumeSelectionSer.updateResumeInfoForUpdate(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			map.put("navTabId", "hr3702");
		} else {

			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		}
		return map;
	}
}

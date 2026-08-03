package com.ait.hrm.action;

import java.util.List;

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

import com.ait.hrm.service.ResumeSearchSer;
import com.ait.web.util.ObjectBindUtil;

@Controller
@RequestMapping(value = "/hrm/recruit")
public class ResumeSearchCtroller {
	Logger logger = Logger.getLogger(ResumeSearchCtroller.class);
	@Autowired
	private ResumeSearchSer resumeSearchSer;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/resumeSearch")
	public ModelAndView viewResumeSearchList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		List contractInfoList = this.resumeSearchSer
		.getResumeInfoListForSearch(request);
		
		modelMap.put("itemList", contractInfoList);
		/*modelMap.put("KEY", request.getParameter("seach_KEY"));
		modelMap.put("POST", request.getParameter("seach_POST"));
		modelMap.put("EDUCATION", request.getParameter("seach_EDUCATION"));
		modelMap.put("CONCOUNT", request.getParameter("seach_CONCOUNT"));
		modelMap.put("eqOrMore", request.getParameter("seach_eqOrMore"));
		modelMap.put("INTERVIEW", request.getParameter("seach_INTERVIEW"));
		modelMap.put("TYPE", request.getParameter("seach_TYPE"));*/
		return new ModelAndView("/hrm/recruit/resumeSearch", modelMap);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/resumeSearchAffrim")
	public ModelAndView viewResumeSearchInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List contractInfoList = this.resumeSearchSer
		.getResumeInfoListForSearchAffrim(request);
		modelMap.put("itemList", contractInfoList);
		return new ModelAndView("/hrm/recruit/resumeSearchAffrim", modelMap);
	}
}

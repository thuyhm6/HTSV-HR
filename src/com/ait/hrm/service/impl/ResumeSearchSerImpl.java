package com.ait.hrm.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.hrm.dao.ResumeSearchDao;
import com.ait.hrm.service.ResumeSearchSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;

@Service
public class ResumeSearchSerImpl implements ResumeSearchSer {
	Logger logger = Logger.getLogger(ResumeSearchSerImpl.class);
	@Autowired
	private ResumeSearchDao resumeSearchDao;
	@Override
	/**
	 * 简历查询 
	 * 
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getResumeInfoListForSearch(HttpServletRequest request) {
		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

	/*	String EmpOffice = request.getParameter("seach_EmpOffice") == null ? "15119"
				: request.getParameter("seach_EmpOffice");

		paramMap.put("EmpOffice", EmpOffice);

		String showAllFlag = request.getParameter("showAllFlag") == null ? "N"
				: request.getParameter("showAllFlag");

		paramMap.put("showAllFlag", showAllFlag);*/
		String INTERVIEW_PERIOD = DateUtil.convertStringDateFormat(StringUtil.checkNull(paramMap.get("INTERVIEW_PERIOD")), "dd/MM/yyyy", "yyyy/MM/dd");
		paramMap.put("INTERVIEW_PERIOD", INTERVIEW_PERIOD);
	
		retrunList = resumeSearchDao.getResumeInfoListForSearch(paramMap);

		return retrunList;
	}
	@Override
	/**
	 * 简历审批情况 
	 * 
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getResumeInfoListForSearchAffrim(HttpServletRequest request) {
		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

	
		retrunList = resumeSearchDao.getResumeInfoListForSearchAffrim(paramMap);

		return retrunList;
	}

}

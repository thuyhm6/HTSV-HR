package com.ait.hrm.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.hrm.dao.InterviewProcessDao;
import com.ait.hrm.service.InterviewProcessSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class InterviewProcessSerImpl implements InterviewProcessSer {
	Logger logger = Logger.getLogger(InterviewProcessSerImpl.class);
	@Autowired
	private InterviewProcessDao interviewProcessDao;
	/**
	 * 面试记录查询 
	 * 
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getReadyToInterviewInfoList(HttpServletRequest request) {

		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		if(paramMap.get("INTER_STATUS") == null)
			paramMap.put("INTER_STATUS","0");

		retrunList = interviewProcessDao.getReadyToInterviewInfoList(paramMap, UiUtil
				.getPageNum(request), UiUtil.getNumPerPage(request));

		return retrunList;

	}
	/**
	 * 面试管面试信息查询 
	 * 
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getInterviewAffirmList(HttpServletRequest request) {

		List retrunList = new ArrayList();

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		retrunList = interviewProcessDao.getInterviewAffirmList(paramMap);

		return retrunList;

	}
	/**
	 * 
	 * 简历审批
	 * 
	 * @param request
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int interviewProcessApproval(HttpServletRequest request) throws Exception{
		// TODO Auto-generated method stub

	    LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
	    AdminBean admin = SessionUtil.getLoginUserFromSession(request);
	    paramMap.put("createBy", admin.getAdminID());
	    this.interviewProcessDao.interviewProcessApproval(request,paramMap);
		    
		return 1;
	}
}

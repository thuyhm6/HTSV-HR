package com.ait.hrm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.hrm.dao.InterviewScheduleConfirmDao;
import com.ait.hrm.service.InterviewScheduleConfirmSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

import common.Logger;
@Service
public class InterviewScheduleConfirmSerImpl implements InterviewScheduleConfirmSer{
	Logger logger = Logger.getLogger(InterviewScheduleConfirmSerImpl.class);
	@Autowired
	private InterviewScheduleConfirmDao interviewScheduleConfirmDao;

	/**
	 * 通过request请求封装查询条件(get conditions from request)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap getLinkedMapByRequest(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("UPDATED_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if(paramMap.get("PERSON_ID")==null || "".equals(paramMap.get("PERSON_ID"))){
			paramMap.put("PERSON_ID", admin.getPersonId());
		}
		return paramMap;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List getInterviewScheduleConfirmList(HttpServletRequest request) throws Exception {
		List returnList = new ArrayList();
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		returnList = this.interviewScheduleConfirmDao.getInterviewScheduleConfirmList(paramMap);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int saveInterviewConfirm (HttpServletRequest request) throws Exception {
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int confirm = 1;
		String[] candidates = request.getParameterValues("SINGLE_LEAVE");
		for (int i = 0; i < candidates.length; i++) {
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("UPDATE_DATE", "SYSDATE");
			paramMap.put("REC_EMPLOYEE_NO", candidates[i]);
			paramMap.put("INTERVIEW_TIME", request.getParameter("INTERVIEW_TIME_"+candidates[i]));
			paramMap.put("INTERVIEW_ADDRESS", request.getParameter("INTERVIEW_ADDRESS_"+candidates[i]));
			confirm = this.interviewScheduleConfirmDao.saveInterviewConfirm(paramMap);
			if (confirm == 2) {
				return confirm;
			}
		}
		
		return confirm;
	}
}

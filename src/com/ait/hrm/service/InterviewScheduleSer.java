package com.ait.hrm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface InterviewScheduleSer {
	
	@SuppressWarnings("unchecked")
	public List getInterviewScheduleList(HttpServletRequest request)throws Exception;
	
	public int addRecAffirmInfo(HttpServletRequest request) throws Exception;
}

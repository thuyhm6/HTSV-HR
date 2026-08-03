package com.ait.hrm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface InterviewScheduleConfirmSer {

	@SuppressWarnings("unchecked")
	public List getInterviewScheduleConfirmList(HttpServletRequest request) throws Exception;
	public int saveInterviewConfirm(HttpServletRequest request) throws Exception;
}

package com.ait.hrm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface InterviewProcessSer {
	@SuppressWarnings("unchecked")
	public List getReadyToInterviewInfoList(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public List getInterviewAffirmList(HttpServletRequest request) ;
	
	public int interviewProcessApproval(HttpServletRequest request) throws Exception;
	
}

package com.ait.hrm.dao;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface InterviewProcessDao {
	@SuppressWarnings("unchecked")
	public List getReadyToInterviewInfoList(Object object, int currentPage, int pageSize);
	@SuppressWarnings("unchecked")
	public List getInterviewAffirmList(Object object);
	
	@SuppressWarnings("unchecked")
	public void interviewProcessApproval(HttpServletRequest request,LinkedHashMap obj);	
}

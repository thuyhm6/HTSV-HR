package com.ait.hrm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface ResumeSelectionSer {
	@SuppressWarnings("unchecked")
	public List getResumeSelectionInfoList(HttpServletRequest request) ;
	
	public int getResumeSelectionCnt(HttpServletRequest request) ;
	
	public int updateResumeInfoForUpdate(HttpServletRequest request) ;
	
}

package com.ait.hrm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface ResumeSearchSer {
	@SuppressWarnings("unchecked")
	public List getResumeInfoListForSearch(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public List getResumeInfoListForSearchAffrim(HttpServletRequest request) ;
}

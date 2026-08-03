package com.ait.ess.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface EssDeptEmpAttSer {

	@SuppressWarnings("unchecked")
	public List viewArShiftGroupList(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public int viewArShiftGroupCnt(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int addArShiftGroupInfo(HttpServletRequest request) ;	
	
	@SuppressWarnings("unchecked")
	public int delArShiftGroupInfo(HttpServletRequest request) ;
	
}

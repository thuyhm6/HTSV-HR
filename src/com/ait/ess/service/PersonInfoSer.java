package com.ait.ess.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface PersonInfoSer {

	public Map getEssPersonInfo(HttpServletRequest request)throws Exception;
	
	public Map getHrPersonInfo(HttpServletRequest request)throws Exception;
	
	public Map getEducationInfoList(HttpServletRequest request)throws Exception;
	
	public Map getEvaluateforList(HttpServletRequest request)throws Exception;
	
}

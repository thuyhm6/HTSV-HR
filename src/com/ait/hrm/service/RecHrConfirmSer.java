package com.ait.hrm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface RecHrConfirmSer {
	@SuppressWarnings("unchecked")
	public List getReadyToHrConfirmInfoList(HttpServletRequest request) ;
	
	public int recruitmentHrConfirm(HttpServletRequest request) throws Exception;
}

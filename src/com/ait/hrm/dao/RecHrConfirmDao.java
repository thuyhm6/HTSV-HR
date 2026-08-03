package com.ait.hrm.dao;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface RecHrConfirmDao {
	@SuppressWarnings("unchecked")
	public List getReadyToHrConfirmInfoList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public void recruitmentHrConfirm(HttpServletRequest request,LinkedHashMap obj);	
}

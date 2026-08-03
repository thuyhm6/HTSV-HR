package com.ait.is.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface BaseManagementForSearchSer {
	
	@SuppressWarnings("unchecked")
	public List ViewInsuranceBaseManagementForSearch(HttpServletRequest request)throws Exception;
	@SuppressWarnings("unchecked")
    int createInstanceBaseManagement(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public int getInsuranceBaseCnt(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public List getInsBaseNumInfoExcel(HttpServletRequest request)throws Exception;
	@SuppressWarnings("unchecked")
	public int deleteInstanceaseManagement(HttpServletRequest request)throws Exception;
	@SuppressWarnings("unchecked")
	public int allowInstanceBaseNumUpdate(HttpServletRequest request)throws Exception;
	@SuppressWarnings("unchecked")
	public List getupdateInstanceBaseManagement(HttpServletRequest request)throws Exception;
	@SuppressWarnings("unchecked")
	public int editInstanceBaseManagement(HttpServletRequest request)throws Exception;
    
	
}

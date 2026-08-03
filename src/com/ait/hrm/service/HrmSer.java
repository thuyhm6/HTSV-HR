package com.ait.hrm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface HrmSer {
	public Object getDeptById(Object object);
	@SuppressWarnings("unchecked")
	public List getDeptTree(HttpServletRequest request,String limit);
	@SuppressWarnings("unchecked")
	public List getAllDept(Object object);
	@SuppressWarnings("unchecked")
	public List getBusiness(HttpServletRequest request);
	
}

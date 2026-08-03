package com.ait.ar.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface ArBasicSer {
	
	@SuppressWarnings("unchecked")
	public List getArSearchEmployeeList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getArSearchEmployeeCnt(HttpServletRequest request);
}

package com.ait.pa.service.wagebase;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface PaCityCoefficientSer {
	@SuppressWarnings("unchecked")
	public List paCityCoefficientList(HttpServletRequest request) ;
	
	public int paCityCoefficientCnt(HttpServletRequest request);
	
	public int checkAddCityCoefficientInfo(HttpServletRequest request);
	
	public int addCityCoefficientInfo(HttpServletRequest request);
	
	public Object paCityCoefficientInfo(HttpServletRequest request);
	
	public int updateCityCoefficientInfo(HttpServletRequest request);
	
	public int deleteCityCoefficientInfo(HttpServletRequest request);
	
}

package com.ait.pa.dao;

import java.util.List;


public interface PaCityCoefficientDao {
	@SuppressWarnings("unchecked")
	public List getPaCityCoefficientList(Object object) ;
	
	public int getPaCityCoefficientCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaCityCoefficientList(Object object, int currentPage, int pageSize);
	
	public int checkAddCityCoefficientInfo(Object object);
	
	public int addCityCoefficientInfo(Object object);
	
	public Object paCityCoefficientInfo(Object object);
	
	public int updateCityCoefficientInfo(Object object);
	
	public int deleteCityCoefficientInfo(Object object);
	
}

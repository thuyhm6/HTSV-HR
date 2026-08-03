package com.ait.ar.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface ArBasicDao {
	@SuppressWarnings("unchecked")
	public List getArSearchEmployeeList(Object object);
	@SuppressWarnings("unchecked")
	public int getArSearchEmployeeCnt(Object object);
	@SuppressWarnings("unchecked")
	public List getArSearchEmployeeList(Object object, int currentPage,
			int pageSize);
}

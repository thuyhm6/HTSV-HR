package com.ait.pa.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface PaWorkerSalaryTableDao {
	
	@SuppressWarnings("unchecked")
	public List paWorkerSalaryTableList(Object object) ;	
	
	@SuppressWarnings("unchecked")
	public int addPaWorkerSalaryTable(Object object) ;	

	@SuppressWarnings("unchecked")
	public int updatePaWorkerSalaryTable(Object object) ;
	
	@SuppressWarnings("unchecked")
	public int updatePaWorkerSalaryTableAll(Object object) ;
	

}

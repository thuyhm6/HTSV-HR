package com.ait.pa.service.workManagement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface PaWorkerSalaryTableSer {
	
	@SuppressWarnings("unchecked")
	public List paWorkerSalaryTableList(HttpServletRequest request) ;	
	
	@SuppressWarnings("unchecked")
	public int addPaWorkerSalaryTable(HttpServletRequest request) ;	

	@SuppressWarnings("unchecked")
	public int updatePaWorkerSalaryTable(HttpServletRequest request) ;

}

package com.ait.pa.service.workManagement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


public interface PaEmpAccountSer {
	
	@SuppressWarnings("unchecked")
	public List getPaEmpAccountList(HttpServletRequest request) ;	
	
	@SuppressWarnings("unchecked")
	public int getPaEmpAccountListCnt(HttpServletRequest request) ;	
	
	@SuppressWarnings("unchecked")
	public List getPaEmpAccountTempList(HttpServletRequest request) throws Exception ;	
	
	@SuppressWarnings("unchecked")
	public List getPaEmpAccountTempList1(HttpServletRequest request) throws Exception ;	
	
	
	@SuppressWarnings("unchecked")
	public String submitImportExcelPaEmpAccountData(HttpServletRequest request) throws Exception ;
	
	@SuppressWarnings("unchecked")
	public String valImportExcelPaEmpAccountData(HttpServletRequest request) throws Exception ;	
	
	
	@SuppressWarnings("unchecked")
	public String valImportExcelPaEmpAccountData1(HttpServletRequest request) throws Exception ;	
	
	public int getPaEmpAccountTempCnt(HttpServletRequest request, String errorFlag)  throws Exception ;

	@SuppressWarnings("unchecked")
	public int addPaEmpAccountInfo(HttpServletRequest request) ;	

	@SuppressWarnings("unchecked")
	public Object getPaEmpAccountInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updatePaEmpAccountInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int doDeletePaEmpAccountInfo(HttpServletRequest request) ;
	
}

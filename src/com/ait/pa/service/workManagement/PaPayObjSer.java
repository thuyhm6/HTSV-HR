package com.ait.pa.service.workManagement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


public interface PaPayObjSer {
	
	@SuppressWarnings("unchecked")
	public List getPaPayObjList(HttpServletRequest request) ;	
	
	@SuppressWarnings("unchecked")
	public int getPaPayObjCnt(HttpServletRequest request) ;	
	
	@SuppressWarnings("unchecked")
	public int addPaPayObjInfo(HttpServletRequest request) ;	

	@SuppressWarnings("unchecked")
	public Object getPaPayObjInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updatePaPayObjInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int doDeletePaPayObjInfo(HttpServletRequest request) ;

	@SuppressWarnings("unchecked")
	public Object getEmpListForPop(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public Object getEmpSHListForPop(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public int getEmpListForPopCnt(HttpServletRequest request);
	
}

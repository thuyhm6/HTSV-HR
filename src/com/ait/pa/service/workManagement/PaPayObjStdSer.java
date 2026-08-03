package com.ait.pa.service.workManagement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


public interface PaPayObjStdSer {
	
	@SuppressWarnings("unchecked")
	public List getPayObjStdList(HttpServletRequest request) ;	
	
	@SuppressWarnings("unchecked")
	public int getPayObjStdCnt(HttpServletRequest request) ;	
	
	@SuppressWarnings("unchecked")
	public int addPayObjStdInfo(HttpServletRequest request) ;	

	@SuppressWarnings("unchecked")
	public Object getPayObjStdInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updatePayObjStdInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int doDeletePayObjStdInfo(HttpServletRequest request) ;
	
}

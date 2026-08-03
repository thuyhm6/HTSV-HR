package com.ait.pa.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public interface PaPayObjDao {
	
	@SuppressWarnings("unchecked")
	public List getPaPayObjList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public int getPaPayObjCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public void addPaPayObjInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public Object getPaPayObjInfo(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public void updatePaPayObjInfo(HttpServletRequest request,LinkedHashMap object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void doDeletePaPayObjInfo(HttpServletRequest request,LinkedHashMap object) throws Exception;

	@SuppressWarnings("unchecked")
	public List getEmpListForPop(Map paramMap, int pageNum, int numPerPage);
	
	@SuppressWarnings("unchecked")
	public List getEmpSHListForPop(Map paramMap, int pageNum, int numPerPage);

	@SuppressWarnings("unchecked")
	public int getEmpListForPopCnt(Map paramMap);
	
}

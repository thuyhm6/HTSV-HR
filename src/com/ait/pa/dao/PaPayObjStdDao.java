package com.ait.pa.dao;

import java.util.List;


public interface PaPayObjStdDao {
	
	@SuppressWarnings("unchecked")
	public List getPayObjStdList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public int getPayObjStdCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public void addPayObjStdInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public Object getPayObjStdInfo(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public void updatePayObjStdInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void doDeletePayObjStdInfo(Object object) throws Exception;
	
}

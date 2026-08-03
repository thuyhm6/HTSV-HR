package com.ait.pa.dao;

import java.util.List;


public interface PaEmpAccountDao {
	
	@SuppressWarnings("unchecked")
	public List getPaEmpAccountList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public int getPaEmpAccountListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaEmpAccountTempList(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public int getPaEmpAccountTempErrorCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getPaEmpAccountTempCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaEmpAccountTempList1(Object obj)throws Exception;
	
	@SuppressWarnings("unchecked")
	public void addPaEmpAccountInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public Object getPaEmpAccountInfo(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public void updatePaEmpAccountInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void doDeletePaEmpAccountInfo(Object object) throws Exception;
	
}

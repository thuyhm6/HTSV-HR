package com.ait.pa.dao;

import java.util.List;


public interface PaChainStafftDao {
	
	@SuppressWarnings("unchecked")
	public List getPaEmpAccountList(Object object, int currentPage, int pageSize);
	
}

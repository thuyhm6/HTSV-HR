package com.ait.hrm.dao;

import java.util.LinkedHashMap;
import java.util.List;

public interface AddRecPageHubDao {
	
	@SuppressWarnings("unchecked")
	public void addRecPageInfo (LinkedHashMap map) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void addEditRecPageInfoHub(LinkedHashMap paramMap) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getRecPageList (LinkedHashMap map) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int getRecPageListCnt(LinkedHashMap map) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getRecPageInfo (LinkedHashMap map) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getRecPageworkInfo (LinkedHashMap map) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void deleteRecPageInfo(LinkedHashMap paramMap) throws Exception;
	
	/**
	 * 删除空的数据 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteRecPageList(Object object)throws Exception;
}

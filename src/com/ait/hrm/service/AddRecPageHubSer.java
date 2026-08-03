package com.ait.hrm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface AddRecPageHubSer {
	public int addRecPageInfo(HttpServletRequest request) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getRecPageList(HttpServletRequest request) throws Exception;
	
	public int getRecPageListCnt(HttpServletRequest request) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getRecPageInfo(HttpServletRequest request) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getRecPageworkInfo(HttpServletRequest request) throws Exception;
	
	public int addEditRecPageInfoHub(HttpServletRequest request) throws Exception;
	
	public int deleteRecPageInfo(HttpServletRequest request) throws Exception;
	
	/**
	 * 删除空的数据 
	 */
	@SuppressWarnings("unchecked")
	public int deleteRecPageList(HttpServletRequest request);
}

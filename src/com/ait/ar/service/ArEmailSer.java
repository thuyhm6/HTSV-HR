package com.ait.ar.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface ArEmailSer {
	/**
	 * 查找发送list
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArEmailList(HttpServletRequest request,String person_id,String deptno);
	
	/**
	 * 查找发送list
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArDetailListEmail(Map paramMap,String person_id);
	
	/**
	 * 查找考勤员
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAttKeeperList(Map paramMap);
	
	//查找部门
	@SuppressWarnings("unchecked")
	public List getOrgDeptList(HttpServletRequest request);
}

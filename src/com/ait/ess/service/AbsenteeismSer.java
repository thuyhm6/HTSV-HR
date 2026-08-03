package com.ait.ess.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @fileName: AbsenteeismCtroller
 * @Description:旷工申请 
 */
public interface AbsenteeismSer {

	/**
	 * 旷工申请 信息查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getAbsenteeismList(HttpServletRequest request) throws Exception;
}
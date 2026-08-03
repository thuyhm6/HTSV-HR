package com.ait.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface HomeParamSer {
	@SuppressWarnings("unchecked")
	public List getHomeParamList(HttpServletRequest request) ;
	
	public Object getHomeParam(HttpServletRequest request) ;
	
	public int updateHomeParamInfo(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List getCpnyList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getHomeCheckParamList(HttpServletRequest request) ;
	
	public int updateHomeCheckParamInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getRoleListByCpnyId(HttpServletRequest request) ;
	
	public int updateHomeParamCpnyInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getEmpTypeListByCpnyId(HttpServletRequest request) ;
}

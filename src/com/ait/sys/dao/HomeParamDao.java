package com.ait.sys.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface HomeParamDao {
	
	@SuppressWarnings("unchecked")
	public List getHomeParamList(Object object);
	
	public Object getHomeParam(Object object);
	
	public void updateHomeParamInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getCpnyList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getHomeCheckParamList(Object object);
	
	public void updateHomeCheckParamInfo(Object object) throws Exception;

	@SuppressWarnings("unchecked")
	public List getRoleListByCpnyId(Object object);
	
	public void updateHomeParamCpnyInfo(Object object) throws Exception;
	
	public Object getHomeParamCheck(Object object);
	
	public void deleteHomeParamCpnyAndEmpTypeInfo(Object object) throws Exception;
	
	public void insertHomeParamCpnyAndEmpTypeInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getEmpTypeListByCpnyId(Object object);
	
	@SuppressWarnings("unchecked")
	public List getEmpTypeParamListByCpnyId(Object object);
	
}

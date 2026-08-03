package com.ait.ess.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


public interface EssDeptEmpAttDao {

	@SuppressWarnings("unchecked")
	List viewArShiftGroupList(Object object);

	@SuppressWarnings("unchecked")
	int viewArShiftGroupCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public void addArShiftGroupInfo(Object object) throws Exception;
	
	
	@SuppressWarnings("unchecked")
	public int delArShiftGroupInfo(Object object) throws Exception;

}

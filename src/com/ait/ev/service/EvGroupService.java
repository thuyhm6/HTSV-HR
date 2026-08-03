package com.ait.ev.service;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface EvGroupService {

	public List getEvGroupInfo(HttpServletRequest request) throws Exception;
	
	public LinkedHashMap getEvGroupInfoById(HttpServletRequest request) throws Exception;
	
	public int insertEvGroupInfo(HttpServletRequest request);
	
	public int updateEvGroupInfo(HttpServletRequest request);
	
	public int delEvGroupInfo(HttpServletRequest request) throws Exception;
	
	public List getEvGroupTree(HttpServletRequest request) throws Exception;
}

package com.ait.ev.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface EvGroupDao {

	public List getEvGroupInfo(Object parameterObject) throws Exception;
	
	public LinkedHashMap getEvGroupInfoById(Object parameterObject)throws Exception;
	
	public int insertEvGroupInfo(Object parameterObject);
	
	public int updateEvGroupInfo(Object parameterObject);
	
	public int delEvGroupInfo(Object parameterObject);
	
	public List getEvGroupTree(Object parameterObject)throws Exception;
	
	public boolean getGroupHasChild(Object parameterObject)throws Exception;
}

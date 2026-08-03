package com.ait.sys.dao;

import java.util.List;

public interface SyLanguageDao {
	
	@SuppressWarnings("unchecked")
	
	public List getSyLanguageListByActivity();
	
	@SuppressWarnings("unchecked")
	public List getSyLanguageNameListByActivity(Object object);
	
	public String getSyGlobalNameNo();
	
	public void insertSyGlobalName(Object object)throws Exception ;
	
	public void deleteSyGlobalName(Object object)throws Exception ;
	
	public Object saveSyGlobalName(Object object)throws Exception ;
	
	public void updateSyGlobalName(Object object)throws Exception ;
	
}

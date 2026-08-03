package com.ait.sys.dao;

import java.util.List;

import com.ait.sys.bean.CodeBean;
 
public interface SysDao {
	@SuppressWarnings("unchecked")
	public void deleteModel(List list);
	@SuppressWarnings("unchecked")
	public void insertModel(List list);
	@SuppressWarnings("unchecked")
	public List getModel(Object object);

	@SuppressWarnings("unchecked")
	public List getPosition(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPostGrade(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPostGroup(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPost(Object object);
	
	@SuppressWarnings("unchecked")
	public List getLanguage(String object);
	
	public List<CodeBean> getParentCodeNo();
	
	@SuppressWarnings("unchecked")
	public List getCode();
	
	@SuppressWarnings("unchecked")
	public List getCodeLanguage();
	
	@SuppressWarnings("unchecked")
	public List getCodeParamList();
	@SuppressWarnings("unchecked")
	public List getSelectTable(Object object);
	@SuppressWarnings("unchecked")
	public List getSelectTableByHrDept(Object object);
	@SuppressWarnings("unchecked")
	public List getDeptListByCpnyID(Object object);
}

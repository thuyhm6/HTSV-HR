package com.ait.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ait.sys.bean.CodeBean;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName SysSer.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:24:13
 * @version 5.0
 *
 */
public interface SysSer {
	
	public void updateModel(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getModel(HttpServletRequest request);

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
	public List getCodeParamList();
	
	@SuppressWarnings("unchecked")
	public List getCodeLanguage();
	
	@SuppressWarnings("unchecked")
	public List getSelectTable(Object object);
	@SuppressWarnings("unchecked")
	public List getSelectTableByHrDept(Object object);
	@SuppressWarnings("unchecked")
	public List getDeptListByCpnyID(Object object);
	
}

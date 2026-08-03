package com.ait.sys.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName MyHomeSer.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:23:52
 * @version 5.0
 *
 */
public interface MyHomeSer {
	public void updateModel(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public List getModel(HttpServletRequest request);
	public Object getHomePurview(HttpServletRequest request);
	public Object getHomePage(HttpServletRequest request);
	public void updateApp(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public List getApp(HttpServletRequest request);
	@SuppressWarnings("unchecked")
	public List getSyMenu(HttpServletRequest request);
	public Object getTips(HttpServletRequest request);
	public Object getTips_home(HttpServletRequest request);
	public Object getTipsForLogin(HttpServletRequest request);
	public Object getTipsForLogin_home(HttpServletRequest request);
	public Object getManualMenu(HttpServletRequest request) ;
	public Map getSystemDate(LinkedHashMap paramMap);
	public int createLoginInfo(HttpServletRequest request, LinkedHashMap paramMap);
	public Map getSealControl(LinkedHashMap paramMap);
}

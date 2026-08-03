package com.ait.sys.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName CompanySer.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:23:31
 * @version 5.0
 *
 */
public interface CompanySer {
	
	public Object getCompanyItemInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getCompanyItemList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getAllCompanyItemList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getCompanyItemAllList(HttpServletRequest request) ;
	
	public int getCompanyItemCnt(HttpServletRequest request);
	
	public String getRoleID(HttpServletRequest request);
	
	public int addCompanyItemInfo(HttpServletRequest request);
	
	public int updateCompanyItemInfo(HttpServletRequest request);
	
	public int deleteCompanyItemInfo(HttpServletRequest request);
	
	public int checkCompanyIdExsit(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getHrOpeationList(HttpServletRequest request) ;
}

package com.ait.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * Copyright:   LDCC (c)
 * Company:     LDCC
 * @fileName PageStructureSer.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-4-19 am 12:00:22
 * @version 5.0
 *
 */
public interface PageStructureSer {
	
	@SuppressWarnings("unchecked")
	public List getPageStructureList(HttpServletRequest request) ;
	
	public int getPageStructureListCnt(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getPsDataList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List retrieveReportItemListByTableName(HttpServletRequest request) ;
	
	public int addNewAliasInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List retrieveReportItemList(HttpServletRequest request) ;
	
	public int updateNewAliasInfo(HttpServletRequest request);
	
	public int saveAliasInfo(HttpServletRequest request,List langList);
	
}

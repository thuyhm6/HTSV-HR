package com.ait.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 
* @ClassName: TableStructureSer 
* @Description: TODO
* @author yorio youjia@ait.net.cn
* @date Feb 4, 2013 5:17:35 PM 
*
 */
public interface TableStructureSer {
	
	@SuppressWarnings("unchecked")
	public List getTableStructureList(HttpServletRequest request) ;
	
	public int getTableStructureListCnt(HttpServletRequest request);
	

	
}

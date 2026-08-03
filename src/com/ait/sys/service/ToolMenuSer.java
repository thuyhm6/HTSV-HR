package com.ait.sys.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ToolMenuSer.java
 * @Description: implement Class ToolMenuSerImpl.java
 * @Create date: 2012-3-21 上午11:13:26
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ToolMenuSer {
	
	@SuppressWarnings("unchecked")
	public Object getToolMenu(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public Object getToolMenuForNo(HttpServletRequest request, String menuNo) ;
	
	/**
	 * 查询用,通过request的查询参数和指定的sql名称
	 * @param map
	 * @param sqlName
	 * @return
	 */
	public Object getLinkMapByName(Map map,String sqlName);
}

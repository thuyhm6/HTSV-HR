package com.ait.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName LoginInfoSer.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:23:35
 * @version 5.0
 *
 */
public interface LoginInfoSer {
	
	@SuppressWarnings("unchecked")
	public List getLoginInfoList(HttpServletRequest request) ;
	
	public int getLoginInfoCnt(HttpServletRequest request) ;
}

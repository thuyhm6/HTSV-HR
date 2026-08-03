package com.ait.ar.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArProgressSer.java
 * @Description: implement Class ArProgressSerImp.java 
 * @Create date: 2012-2-12 下午12:02:35
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ArProgressSer {
	
	@SuppressWarnings("unchecked")
    public List getArProgressList(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public int updateArProgressInfo(HttpServletRequest request) ;
	@SuppressWarnings("unchecked")
	public int getArProgressCnt(HttpServletRequest request) ;
}

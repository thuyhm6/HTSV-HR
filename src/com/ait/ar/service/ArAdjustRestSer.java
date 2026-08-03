package com.ait.ar.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArAdjustRestSer.java
 * @Description: implement Class ArAdjustRestSerImp.java
 * @Create date: 2012-3-23 下午04:29:11
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ArAdjustRestSer {
	
	@SuppressWarnings("unchecked")
    public List getArAdjustRestList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getArAdjustRestCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
    public Object getArAdjustRestInfo(HttpServletRequest request) ;
}

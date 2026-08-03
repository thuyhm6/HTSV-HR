package com.ait.ar.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: AttendanceKeeperSer.java
 * @Description: implement Class AttendanceKeeperSerImp.java
 * @Create date: 2012-1-14 下午01:44:55
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ArCardAssociateSer {

	@SuppressWarnings("unchecked")
	public List getCardAssociateList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getCardAssociateCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updateCardAssociateInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public String checkCardValidity(HttpServletRequest request) ;
}

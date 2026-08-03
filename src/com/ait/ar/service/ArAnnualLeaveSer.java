package com.ait.ar.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArAnnualLeaveSer.java
 * @Description: implement Class ArAnnualLeaveSerImp.java 
 * @Create date: 2012-2-14 下午12:53:48
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ArAnnualLeaveSer {
	
	@SuppressWarnings("unchecked")
	public Object getArAnnualLeaveInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
    public List getArAnnualLeaveList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getArAnnualLeaveCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int deleteArAnnualLeave(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updateArAnnualLeaveInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int addArAnnualLeaveInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int checkAddArAnnualLeaveInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int createArAnnualLeaveInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
    public List getEmpForSupervisorList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getEmpForSupervisorCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int createArAnnualLeaveAuto(Map paramMap) ;
	
	/**
	 * 添加portal日志信息时的IP地址
	 * @param request
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int addPortalIp(Map paramMap);
}

package com.ait.ar.dao;

import java.util.List;
import java.util.Map;

import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArAnnualLeaveDao.java
 * @Description: implement Class ArAnnualLeaveDaoImpl.java
 * @Create date: 2012-2-14 下午12:58:31
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ArAnnualLeaveDao {
	
	@SuppressWarnings("unchecked")
	public Object getArAnnualLeaveInfo(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getArAnnualLeaveList(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getArAnnualLeaveList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public int getArAnnualLeaveCnt(Object object) ;
	
	@SuppressWarnings("unchecked")
	public void deleteArAnnualLeave(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updateArAnnualLeave(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void addArAnnualLeave(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int checkAddArAnnualLeaveInfo(Object object) ;

	@SuppressWarnings("unchecked")
	public void createArAnnualLeaveInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getEmpForSupervisorList(Map paramMap, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public int getEmpForSupervisorCnt(Map paramMap) ;
	
	/**
	 * 添加portal日志信息时的IP地址
	 * @param object
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void addPortalIp(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void createArAnnualLeaveAuto(Object object) throws Exception;
}

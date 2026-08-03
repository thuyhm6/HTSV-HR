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
public interface AttendanceKeeperSer {

	@SuppressWarnings("unchecked")
	public Object getAttendanceKeeper(HttpServletRequest request) ;
	
	
	@SuppressWarnings("unchecked")
	public List getAttendanceKeeperList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getAttendanceKeeperCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int addAttendanceKeeperInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updateAttendanceKeeperInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int deleteAttendanceKeeperInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getAttendanceKeeperDeptList(HttpServletRequest request) ;

	public List getAttendanceDeptList(HttpServletRequest request);

	public Object getPersonListView(HttpServletRequest request);
	
	public Object viewKeeperListOtApplyCheck(HttpServletRequest request);
	
	public Object getPersonListView2(HttpServletRequest request);

	public int getPersonListCnt(HttpServletRequest request);
	
	public int getPersonListOtApplyCheckCnt(HttpServletRequest request);
	
	public Object getEmpCalendarList(HttpServletRequest request);

	public int getEmpCalendarCnt(HttpServletRequest request);
	
	public Object getEmpCalendar2List(HttpServletRequest request);

	public int getEmpCalendar2Cnt(HttpServletRequest request);
	
	public List getDeptTree(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getPositionList(HttpServletRequest request);
	
	public int addDepartManagerInfo(HttpServletRequest request) ;
	
	public int addDepartManagerUnifyInfo(HttpServletRequest request) ;
	
	public List getDepartmentManageList(HttpServletRequest request) ;
}

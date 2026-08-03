package com.ait.ar.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: AttendanceKeeperDao.java
 * @Description: implement Class AttendanceKeeperDaoImpl.java
 * @Create date: 2012-1-14 下午01:46:43
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface AttendanceKeeperDao {
	
	@SuppressWarnings("unchecked")
	public Object getAttendanceKeeper(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List getAttendanceKeeperList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getAttendanceKeeperList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public int getAttendanceKeeperCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int addAttendanceKeeperInfo(Object object);
	
	@SuppressWarnings("unchecked")
	public int updateAttendanceKeeperInfo(Object object);
	
	@SuppressWarnings("unchecked")
	public int addAttendanceKeeperDeptInfo(List object);
	
	@SuppressWarnings("unchecked")
	public int deleteAttendanceKeeperInfo(Object object);
	
	@SuppressWarnings("unchecked")
	public List getAttendanceKeeperDeptList(Object object);

	public List getAttendanceDeptList(LinkedHashMap paramMap);

	public List getMaxDeptList(LinkedHashMap paramMap);

	public List getPersonListView(Map paramMap, int pageNum, int numPerPage);
	
	public List viewKeeperListOtApplyCheck(Map paramMap, int pageNum, int numPerPage);
	
	public List getPersonListView2(Map paramMap, int pageNum, int numPerPage);

	public int getPersonListCnt(Map paramMap);

	public int getPersonListOtApplyCheckCnt(Map paramMap);
	
	public List getEmpCalendarList(Map paramMap, int pageNum, int numPerPage);

	public int getEmpCalendarCnt(Map paramMap);
	
	public List getEmpCalendar2List(Map paramMap, int pageNum, int numPerPage);

	public int getEmpCalendar2Cnt(Map paramMap);
	
	public List getDeptTree(Map paramMap);
	
	@SuppressWarnings("unchecked")
	public List getPositionList(Object object);
	
	public List getDepartmentManageList(Object object);
	
	public int addDepartManagerInfo(Object object);
	
	public int addDepartManagerUnifyInfo(Object object);
}

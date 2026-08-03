package com.ait.pa.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaSupervisorDao.java
 * @Description: implement Class PaSupervisorDaoImpl.java
 * @Create date: 2012-1-14 下午01:46:43
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface PaSupervisorDao {
	
	public Object getPaSupervisor(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List getPaSupervisorList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaSupervisorList(Object object, int currentPage, int pageSize);
	
	public int getPaSupervisorCnt(Object object);
	
	public int addPaSupervisorInfo(Object object);
	
	public int updatePaSupervisorInfo(Object object);
	
	@SuppressWarnings("unchecked")
	public int addPaSupervisorDeptInfo(List object);
	
	public int deletePaSupervisorInfo(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaSupervisorDeptList(Object object);

	@SuppressWarnings("unchecked")
	public List getAttendanceDeptList(LinkedHashMap paramMap);

	@SuppressWarnings("unchecked")
	public List getMaxDeptList(LinkedHashMap paramMap);

	@SuppressWarnings("unchecked")
	public List getPersonListView(Map paramMap, int pageNum, int numPerPage);

	@SuppressWarnings("unchecked")
	public int getPersonListCnt(Map paramMap);
	
	@SuppressWarnings("unchecked")
	public List getEmpCalendarList(Map paramMap, int pageNum, int numPerPage);

	@SuppressWarnings("unchecked")
	public int getEmpCalendarCnt(Map paramMap);
	
	@SuppressWarnings("unchecked")
	public List getDeptTree(Map paramMap);

	/**
	 * 部门树(get Dept Tree)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getDeptTreeResumeNo(Map paramMap);
}

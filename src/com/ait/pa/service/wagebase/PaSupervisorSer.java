package com.ait.pa.service.wagebase;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaSupervisorSer.java
 * @Description: implement Class PaSupervisorSerImp.java
 * @Create date: 2012-1-14 下午01:44:55
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface PaSupervisorSer {

	public Object getPaSupervisor(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPaSupervisorList(HttpServletRequest request) ;
	
	public int getPaSupervisorCnt(HttpServletRequest request) ;
	
	public int addPaSupervisorInfo(HttpServletRequest request) ;
	
	public int updatePaSupervisorInfo(HttpServletRequest request) ;
	
	public int deletePaSupervisorInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPaSupervisorDeptList(HttpServletRequest request) ;

	@SuppressWarnings("unchecked")
	public List getAttendanceDeptList(HttpServletRequest request);

	public Object getPersonListView(HttpServletRequest request);

	public int getPersonListCnt(HttpServletRequest request);
	
	public Object getEmpCalendarList(HttpServletRequest request);

	public int getEmpCalendarCnt(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getDeptTree(HttpServletRequest request);
	
	/**
	 * 部门树(get Dept Tree)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getDeptTreeResumeNo(HttpServletRequest request);
}

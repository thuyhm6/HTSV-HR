package com.ait.ar.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: CycleSer.java
 * @Description: implement Class CycleSerImp.java
 * @Create date: 2012-1-6 下午02:22:35
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface CycleSer {

	@SuppressWarnings("unchecked")
	public Object getCycle(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getCycleList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getCycleCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int addCycleInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updateCycleInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int deleteCycleInfo(HttpServletRequest request) ;

	public List getCycleParameterList(HttpServletRequest request);

	public int getCycleParameterCnt(HttpServletRequest request);

	public int addCycleParamInfo(HttpServletRequest request);

	public int updateCycleParamInfo(HttpServletRequest request);

	public int deleteCycleParamInfo(HttpServletRequest request);

	public Object getCycleParam(HttpServletRequest request);

	public int checkCycleInfoUnique(HttpServletRequest request);

	public int checkCycleForDelete(HttpServletRequest request);
	
	public List getEmpTypeCodeList(HttpServletRequest request);
	
	public List getEmpTypeCodeListSUPERVISOR(HttpServletRequest request);
	
	public List getStatisticList(HttpServletRequest request);
	
	public List getKeeperEmpTypeCodeList(HttpServletRequest request);
	
	public List getKeeperEmpTypeCodeList(HttpServletRequest request,String person_id);
	
	public String getDeptNameByDeptNo(HttpServletRequest request,String deptNo, String cnpyId);
	
	public List getPaSupervisorEmpTypeCodeList(HttpServletRequest request);

	public List getJobTypeGroupList(HttpServletRequest request, String cpnyId);

	public List getKeeperJobGroupList(HttpServletRequest request);

	public List getPaSupervisorJobTypeGroupCodeList(HttpServletRequest request);
	
	public List getStatutoryHolidaysList(HttpServletRequest request) ;
	
	public int getStatutoryHolidaysCnt(HttpServletRequest request) ;
}

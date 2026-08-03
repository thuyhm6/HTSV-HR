package com.ait.ar.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: CycleDao.java
 * @Description: implement Class CycleDaoImp.java
 * @Create date: 2012-1-6 下午03:29:47
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface CycleDao {
	
	@SuppressWarnings("unchecked")
	public Object getCycle(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List getCycleList(Object object);
	
	@SuppressWarnings("unchecked")
	public int getCycleCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getCycleList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public void addCycleInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updateCycleInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void deleteCycleInfo(Object object) throws Exception;

	public List getCycleParamList(Map paramMap, int pageNum, int numPerPage);

	public List getCycleParamList(Map paramMap);

	public int getCycleParamCnt(Map paramMap);

	@SuppressWarnings("unchecked")
	public void addCycleParamInfo(LinkedHashMap paramMap) throws Exception;

	@SuppressWarnings("unchecked")
	public void updateCycleParamInfo(LinkedHashMap paramMap) throws Exception;

	@SuppressWarnings("unchecked")
	public void deleteCycleParamInfo(LinkedHashMap paramMap) throws Exception;

	public Object getCycleParam(Map paramMap);

	public int checkCycleInfoUnique(LinkedHashMap paramMap);

	public int checkCycleForDelete(Map paramMap);
	
	public List getEmpTypeCodeList(Map paramMap);
	
	public List getEmpTypeCodeListSUPERVISOR(Map paramMap);
	
	public List getStatisticList(Map paramMap);
	
	public List getKeeperEmpTypeCodeList(Map paramMap);
	
	public String getDeptNameByDeptNo(Map paramMap);
	
	public List getPaSupervisorEmpTypeCodeList(Map paramMap);

	public List getJobTypeGroupList(Map paramMap);

	public List getKeeperJobGroupList(Object object);

	public List getPaSupervisorJobTypeGroupCodeList(Map paramMap);
	
	public List getStatutoryHolidaysList(Object object);
	
	public List getStatutoryHolidaysList(Object object, int currentPage, int pageSize);
	
	public int getStatutoryHolidaysCnt(Object object);
}

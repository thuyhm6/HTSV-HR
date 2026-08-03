package com.ait.pa.dao;

import java.util.List;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName ControlDao.java
 * @author lufeng(lufeng@ait.net.cn)
 * @Date 2012-3-7 下午05:22:19
 * @version 5.0
 *
 */
public interface PortalDao {
	
	@SuppressWarnings("unchecked")
	public List getPortalInfoList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPortalInfoList(Object object, int currentPage, int pageSize);
	
	public int getPortalInfoListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getSapItemList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaSummaryInfoList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaDataToSapList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getDepartMentInfoList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getC01PaSummaryInfoList(Object object);
	
	public void addC01PaSummary(Object object) throws Exception;
	
	public String getC01SapSummarySendCount(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getC01SapSummaryList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getC01SapSummaryList(Object object, int currentPage, int pageSize);
	
	public int getC01SapSummaryListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getC01PaActualSalaryAllList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getC01PaActualSalaryAllList(Object object, int currentPage, int pageSize);
	
	public int getC01PaActualSalaryAllListCnt(Object object);
	
	public String getC01SapActualSalarySendCount(Object object) throws Exception;
	
	public int getC01PaActualSalaryCntByEmpid(Object object);
	
	@SuppressWarnings("unchecked")
	public List getC01PaActualSalaryAfterList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getC01PaActualSalaryAfterList(Object object, int currentPage, int pageSize);
	
	public int getC01PaActualSalaryAfterListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List checkEmpSapInfoList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getZhaBeiAndJiaXingEmpPaInfoList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getC01PaActualSalaryInfoList(Object object);
	
	public void addC01PaActual(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getC01EmpInfoList(Object object);
	
	public Object getC01SapEmpByPersonid(Object object);
	
	public void addC01EmpInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getC01EmpPostInfoList(Object object);
	
	public Object getC01SapEmpPostByEmpid(Object object);
	
	public void addC01EmpPost(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getC01DepartMentInfoList(Object object);
	
	public Object getC01SapDeptByDeptno(Object object);
	
	public void addC01DepartMent(Object object) throws Exception;
	
	public void addPortalLog(Object object) throws Exception;
	
	public void deleteSAPItem(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void addSAPItem(List aliasList) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void addZhaBeiAndJiaXingCash(List aliasList) throws Exception;
}

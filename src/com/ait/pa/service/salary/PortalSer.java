package com.ait.pa.service.salary;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName ControlSer.java
 * @author lufeng(lufeng@ait.net.cn)
 * @Date 2012-3-7 下午05:22:19
 * @version 5.0
 *
 */
public interface PortalSer {
	
	@SuppressWarnings("unchecked")
	public List getPortalInfoList(HttpServletRequest request) ;
	
	public int getPortalInfoListCnt(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getSapItemList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPaSummaryInfoList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getDepartMentInfoList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getC01PaSummaryInfoList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getC01SapSummaryList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getC01SapSummaryAllExcelList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int getC01SapSummaryListCnt(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List checkEmpSapInfoList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getSapErrorEmpInfoList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getZhaBeiAndJiaXingEmpPaInfoList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getC01PaActualSalaryInfoList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getC01PaActualSalaryAllList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getC01PaActualSalaryAllExcelList(HttpServletRequest request);
	
	public int getC01PaActualSalaryAllListCnt(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getC01PaActualSalaryAfterList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getC01PaActualSalaryAfterExcelList(HttpServletRequest request);
	
	public int getC01PaActualSalaryAfterListCnt(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getC01EmpInfoList(Map map);
	
	@SuppressWarnings("unchecked")
	public List getC01EmpPostInfoList(Map map);
	
	@SuppressWarnings("unchecked")
	public List getC01DepartMentInfoList(Map map);
	
	public int addPortalLog(HttpServletRequest request);
	
	public int addPortalLog(List<String> portalResultList,String cpny_id);
	
	public int deleteSAPItem(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int addSAPItem(HttpServletRequest request,List aliasList);
	
	public int addZhaBeiAndJiaXingCash(HttpServletRequest request);
}

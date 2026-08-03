package com.ait.report.hr.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: HrReportSer.java
 * @Description: interface Class HrReportSer.java
 * @Create date: April 14, 2012 3:29:38 PM
 * @Create by: hanzhe (hanzhe@ait.net.cn)
 * @version 5.1
 */
public interface HrReportSer {
	/**
	 * 预览图
	 * @param request
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public Map viewReport(HttpServletRequest request);	

	/**
	 * 预览图
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String viewReportChart(HttpServletRequest request) throws Exception;	
	
	/**
	 * 员工在职证明信息(Certificate of Employment info query)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getCertificateEmploymentList(HttpServletRequest request) ;
	
	/**
	 * 员工在职证明信息数量(Certificate of Employment info count)
	 * @param request
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int getCertificateEmploymentListCnt(HttpServletRequest request) ;
	
	/**
	 * 员工在职证明信息(Certificate of Employment info query)
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getCertificateEmploymentExcelList(HttpServletRequest request) ;
	
	/**
	 * 员工人事信息(person info query)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getPersonRecodeList(HttpServletRequest request) ;
	
	/**
	 * 员工人事信息数量(query the person info count)
	 * @param request
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int getPersonRecodeListCnt(HttpServletRequest request) ;
	
	/**
	 * 查询系统时间 (query the sysdate )
	 * @param request
	 * @return Object
	 */
	public Object getSysdate(HttpServletRequest request);
	
	/**
	 * 员工人事记录卡信息 (personal record info )
	 * @param request
	 * @return Object
	 */
	public Object getPersonRecordByPid(HttpServletRequest request) ;
	
	/**
	 * 根据person_id查询教育背景信息(query the education info by the person_id)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getRecordEducationList(HttpServletRequest request) ;
	
	/**
	 * 根据person_id查询合同信息(query the contract info by the person_id)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getRecordContractList(HttpServletRequest request) ;
	
	/**
	 * 根据person_id查询社内经历信息(query the experience inside info by the person_id)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getRecordExperienceInsideList(HttpServletRequest request) ;
	
	/**
	 * 根据person_id查询社外经历信息(query the experience outside info by the person_id)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getRecordExperienceOutsideList(HttpServletRequest request) ;
	
	/**
	 * 根据person_id查询家庭信息(query the family info by the person_id)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getRecordFamilyList(HttpServletRequest request) ;
	
	
	
	@SuppressWarnings("unchecked")
	public List getHrEmployeeForInterfaceList() ;
	
	@SuppressWarnings("unchecked")
	public List getHrEmployeeForTxt() ;

	@SuppressWarnings("unchecked")
	public int getHrEmployeeForInterfaceCnt() ;
	
	@SuppressWarnings("unchecked")
	public List getHrDeptForMess() ;
	
	@SuppressWarnings("unchecked")
	public List getHrUserInfoForMess() ;
	
	@SuppressWarnings("unchecked")
	public List getHrPerInfoForSap() ;
	
	@SuppressWarnings("unchecked")
	public List getMoneyAwardForSap() ;
	
	@SuppressWarnings("unchecked")
	public List getPersonList() ;
	
	@SuppressWarnings("unchecked")
	public int getPersonCount() ;
	
	/**
	 * 人事登记卡C11   导出Excel
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getPersonBasicInfo(HttpServletRequest request);
	
	//C12
	@SuppressWarnings("unchecked")
	public List getHrDeptForMessC12() ;
	
	@SuppressWarnings("unchecked")
	public List getHrUserInfoForMessC12() ;
	
	@SuppressWarnings("unchecked")
	public List getPersonListC12() ;
	
	@SuppressWarnings("unchecked")
	public List getPersonListC12New() ;
	
	public int getPersonCountC12() ;
	
	public int getPersonCountC12New() ;
	
	@SuppressWarnings("unchecked")
	public List getHrPerInfoForSapC12() ;
	
	@SuppressWarnings("unchecked")
	public List getMoneyAwardForSapC12() ;
	
	@SuppressWarnings("unchecked")
	public List getHrEmployeeForTxtC12() ;
	
	@SuppressWarnings("unchecked")
	public List getHrEmployeeC12ForInterfaceList() ;

	public int getHrEmployeeC12ForInterfaceCnt() ;
	
	//C13
	@SuppressWarnings("unchecked")
	public List getHrDeptForMessC13() ;
	
	@SuppressWarnings("unchecked")
	public List getHrUserInfoForMessC13() ;
	
	@SuppressWarnings("unchecked")
	public List getPersonListC13() ;
	
	public int getPersonCountC13() ;
	
	@SuppressWarnings("unchecked")
	public List getHrPerInfoForSapC13() ;
	
	@SuppressWarnings("unchecked")
	public List getMoneyAwardForSapC13() ;
	
	@SuppressWarnings("unchecked")
	public List getHrEmployeeForTxtC13() ;
	
	@SuppressWarnings("unchecked")
	public List getHrEmployeeC13ForInterfaceList() ;

	public int getHrEmployeeC13ForInterfaceCnt() ;
}

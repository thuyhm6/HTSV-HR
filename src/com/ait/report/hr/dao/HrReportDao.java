package com.ait.report.hr.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: HrReportDao.java
 * @Description: interface Class HrReportDao.java
 * @Create date: April 14, 2012 3:29:38 PM
 * @Create by: hanzhe (hanzhe@ait.net.cn)
 * @version 5.1
 */
public interface HrReportDao {
	
	@SuppressWarnings("unchecked")
	public Map viewReport(Map object);
	
	@SuppressWarnings("unchecked")
	public void calReport(Map object);
	
	/**
	 * 根据条件查询员工在职证明信息(query the Certificate of Employment info )，不分页
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getCertificateEmploymentList(Object object);
	
	/**
	 * 根据条件查询员工在职证明信息数量(query the Certificate of Employment info count)
	 * @param object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int getCertificateEmploymentListCnt(Object object);
	
	/**
	 * 根据条件查询员工在职证明信息(query the Certificate of Employment info )
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getCertificateEmploymentList(Object object, int currentPage, int pageSize);
	
	/**
	 * 员工人事信息(person info query)，不分页
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPersonRecodeList(Object object);
	
	/**
	 * 员工人事信息数量(query the person info count)
	 * @param object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int getPersonRecodeListCnt(Object object);
	
	/**
	 * 员工人事信息(person info query)
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPersonRecodeList(Object object, int currentPage, int pageSize);
	
	/**
	 * 查询系统时间 (query the sysdate )
	 * @param object
	 * @return Object
	 */
	public Object getSysdate();
	
	/**
	 * 员工人事记录卡信息 (personal record info )
	 * @param object
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public Object getPersonRecordByPid(Object object);
	
	/**
	 * 根据person_id查询教育背景信息(query the education info by the person_id)
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getRecordEducationList(Object object);
	
	/**
	 * 根据person_id查询合同信息(query the contract info by the person_id)
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getRecordContractList(Object object);
	
	/**
	 * 根据person_id查询社内经历信息(query the experience inside info by the person_id)
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getRecordExperienceInsideList(Object object);
	
	/**
	 * 根据person_id查询社外经历信息(query the experience outside info by the person_id)
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getRecordExperienceOutsideList(Object object);
	
	/**
	 * 根据person_id查询家庭信息(query the family info by the person_id)
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getRecordFamilyList(Object object);
	
	
	@SuppressWarnings("unchecked")
	public List getHrEmployeeForInterfaceList();
	
	@SuppressWarnings("unchecked")
	public int getHrEmployeeForInterfaceCnt( );
	
	@SuppressWarnings("unchecked")
	public List getHrDeptForMess( );
	
	@SuppressWarnings("unchecked")
	public List getHrUserInfoForMess( );
	
	@SuppressWarnings("unchecked")
	public List getHrPerInfoForSap( );
	
	@SuppressWarnings("unchecked")
	public List getMoneyAwardForSap( );
	
	@SuppressWarnings("unchecked")
	public List getPersonList( );
	
	@SuppressWarnings("unchecked")
	public int getPersonCount( );
	/**
	 * 人事登记卡C11   导出Excel
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getPersonBasicInfo(Object object);
	
	//C12-------------------start---------------------C12
	@SuppressWarnings("unchecked")
	public List getHrDeptForMessC12( );
	
	@SuppressWarnings("unchecked")
	public List getHrUserInfoForMessC12( );
	
	@SuppressWarnings("unchecked")
	public List getPersonListC12( );
	
	@SuppressWarnings("unchecked")
	public List getPersonListC12New( );
	
	@SuppressWarnings("unchecked")
	public int getPersonCountC12( );
	
	@SuppressWarnings("unchecked")
	public int getPersonCountC12New( );
	
	@SuppressWarnings("unchecked")
	public List getHrPerInfoForSapC12( );
	
	@SuppressWarnings("unchecked")
	public List getMoneyAwardForSapC12( );
	
	@SuppressWarnings("unchecked")
	public List getHrEmployeeC12ForInterfaceList();
	
	@SuppressWarnings("unchecked")
	public int getHrEmployeeC12ForInterfaceCnt( );
	//C12-------------------end---------------------C12
	
	//C13-------------------start---------------------C13
	@SuppressWarnings("unchecked")
	public List getHrDeptForMessC13( );
	
	@SuppressWarnings("unchecked")
	public List getHrUserInfoForMessC13( );
	
	@SuppressWarnings("unchecked")
	public List getPersonListC13( );
	
	@SuppressWarnings("unchecked")
	public int getPersonCountC13( );
	
	@SuppressWarnings("unchecked")
	public List getHrPerInfoForSapC13( );
	
	@SuppressWarnings("unchecked")
	public List getMoneyAwardForSapC13( );
	
	@SuppressWarnings("unchecked")
	public List getHrEmployeeC13ForInterfaceList();
	
	@SuppressWarnings("unchecked")
	public int getHrEmployeeC13ForInterfaceCnt( );
	//C13-------------------end---------------------C13
}

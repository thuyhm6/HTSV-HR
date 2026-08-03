package com.ait.report.hr.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.report.hr.dao.HrReportDao;
import com.ait.web.util.SqlMapClientSupport;
/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: HrReportDaoImpl.java
 * @Description: implement Class HrReportDaoImpl.java
 * @Create date: April 14, 2012 3:29:38 PM
 * @Create by: hanzhe (hanzhe@ait.net.cn)
 * @version 5.1
 */
@Repository
public class HrReportDaoImpl extends SqlMapClientSupport implements HrReportDao {	
	
	
	@SuppressWarnings("unchecked")
	public Map viewReport(Map object){		
		
		Map object2 = new LinkedHashMap();
		object2.put("param", object);
		
		try {
			if(object.get("page")!=null){
				int skipResults = Integer.parseInt(object.get("page").toString());
				int maxResults = Integer.parseInt(object.get("pagesize").toString());
				object2.put("Rows",this.queryForList("report.hr."+object.get("reportName"), object, skipResults, maxResults));
				object2.put("Total",(Integer)this.queryForObject("report.hr."+object.get("reportName")+"Cnt", object));
			}else{
				object2.put("Rows",this.queryForList("report.hr."+object.get("reportName"), object));
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return object2;
	}

	@SuppressWarnings("unchecked")
	public void calReport(Map object) {
		try {
			this.insert("report.hr."+object.get("reportName")+"Cal",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据条件查询员工在职证明信息(query the Certificate of Employment info )，不分页
	 * @param object
	 * @return list
	 */
	@Override
	public List getCertificateEmploymentList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getCertificateEmploymentList(obj, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 根据条件查询员工在职证明信息(query the Certificate of Employment info )
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getCertificateEmploymentList(Object obj, int currentPage,int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("report.hr.getCertificateEmploymentList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("report.hr.getCertificateEmploymentList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 根据条件查询员工在职证明信息数量(query the Certificate of Employment info count)
	 * @param object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int getCertificateEmploymentListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("report.hr.getCertificateEmploymentListCnt",obj)), Integer.class);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 员工人事信息(person info query)，不分页
	 * @param object
	 * @return list
	 */
	@Override
	public List getPersonRecodeList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getCertificateEmploymentList(obj, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 员工人事信息(person info query)
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getPersonRecodeList(Object obj, int currentPage,int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("report.hr.getPersonRecodeList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("report.hr.getPersonRecodeList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 员工人事信息数量(query the person info count)
	 * @param object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int getPersonRecodeListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("report.hr.getPersonRecodeListCnt",obj)), Integer.class);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 查询系统时间 (query the sysdate )
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object getSysdate() {
		Object obj = null;
		try {
			obj = this.queryForObject("report.hr.getSysdate");
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return obj;	
	}
	
	/**
	 * 员工人事记录卡信息 (personal record info )
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object getPersonRecordByPid(Object object) {
		Object obj = null;
		try {
			obj = this.queryForObject("report.hr.getPersonRecordByPid",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return obj;	
	}
	
	/**
	 * 根据person_id查询教育背景信息(query the education info by the person_id)
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getRecordEducationList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.hr.getRecordEducationList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 根据person_id查询合同信息(query the contract info by the person_id)
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getRecordContractList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.hr.getRecordContractList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 根据person_id查询社内经历信息(query the experience inside info by the person_id)
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getRecordExperienceInsideList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.hr.getRecordExperienceInsideList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 根据person_id查询社外经历信息(query the experience outside info by the person_id)
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getRecordExperienceOutsideList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.hr.getRecordExperienceOutsideList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 根据person_id查询家庭信息(query the family info by the person_id)
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getRecordFamilyList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("report.hr.getRecordFamilyList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	


	/**
	 * 人员查询 (employee inquires)
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getHrEmployeeForInterfaceList() {
		List returnList = new ArrayList() ;
		try {
			 returnList = this.queryForList("report.hr.getHrEmployeeForInterfaceList");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}

	/**
	 * 人员数量查询 (employee count inquires)
	 * @param obj
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int getHrEmployeeForInterfaceCnt( ) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("report.hr.getHrEmployeeForInterfaceCnt")), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	
	/**
	 * hr to 考勤食堂 部门
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getHrDeptForMess() {
		List returnList = new ArrayList() ;
		try {
			 returnList = this.queryForList("report.hr.getHrDeptForMess");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	
	/**
	 * hr to 考勤食堂 人事
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getHrUserInfoForMess( ) {
		List returnList = new ArrayList() ;
		try {
			 returnList = this.queryForList("report.hr.getHrUserInfoForMess");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}

	
	/**
	 * hr to sap 人事信息
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getHrPerInfoForSap() {
		List returnList = new ArrayList() ;
		try {
			 returnList = this.queryForList("report.hr.getHrPerInfoForSap");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	
	/**
	 * hr to sap 奖金
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getMoneyAwardForSap() {
		List returnList = new ArrayList() ;
		try {
			 returnList = this.queryForList("report.hr.getMoneyAwardForSap");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}

	
	@SuppressWarnings("unchecked")
	public List getPersonList() {
		List returnList = new ArrayList() ;
		try {
			 returnList = this.queryForList("report.hr.getPersonList");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@Override
	public int getPersonCount( ) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("report.hr.getPersonCount")), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 人事登记卡C11   导出Excel
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonBasicInfo(Object object){
		List returnList = new ArrayList() ;
		try {
			 returnList = this.queryForList("report.hr.getPersonBasicInfoList",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	//C12---------------start---------------C12
	/**
	 * C12 hr to 考勤食堂 部门
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getHrDeptForMessC12() {
		List returnList = new ArrayList() ;
		try {
			 returnList = this.queryForList("report.hr.getHrDeptForMessC12");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * C12 hr to 考勤食堂 人事
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getHrUserInfoForMessC12( ) {
		List returnList = new ArrayList() ;
		try {
			 returnList = this.queryForList("report.hr.getHrUserInfoForMessC12");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getPersonListC12() {
		List returnList = new ArrayList() ;
		try {
			 returnList = this.queryForList("report.hr.getPersonListC12");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getPersonListC12New() {
		List returnList = new ArrayList() ;
		try {
			 returnList = this.queryForList("report.hr.getPersonListC12New");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@Override
	public int getPersonCountC12( ) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("report.hr.getPersonCountC12")), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	@Override
	public int getPersonCountC12New( ) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("report.hr.getPersonCountC12New")), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * C12 hr to sap 人事信息
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getHrPerInfoForSapC12() {
		List returnList = new ArrayList() ;
		try {
			 returnList = this.queryForList("report.hr.getHrPerInfoForSapC12");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	
	/**
	 * C12 hr to sap 奖金
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getMoneyAwardForSapC12() {
		List returnList = new ArrayList() ;
		try {
			 returnList = this.queryForList("report.hr.getMoneyAwardForSapC12");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * C12 人员查询 (employee inquires)
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getHrEmployeeC12ForInterfaceList() {
		List returnList = new ArrayList() ;
		try {
			 returnList = this.queryForList("report.hr.getHrEmployeeC12ForInterfaceList");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}

	/**
	 * C12 人员数量查询 (employee count inquires)
	 * @param obj
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int getHrEmployeeC12ForInterfaceCnt( ) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("report.hr.getHrEmployeeC12ForInterfaceCnt")), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	//C12---------------end---------------C12
	
	//C13---------------start---------------C13
	/**
	 * C13 hr to 考勤食堂 部门
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getHrDeptForMessC13() {
		List returnList = new ArrayList() ;
		try {
			 returnList = this.queryForList("report.hr.getHrDeptForMessC13");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * C13 hr to 考勤食堂 人事
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getHrUserInfoForMessC13( ) {
		List returnList = new ArrayList() ;
		try {
			 returnList = this.queryForList("report.hr.getHrUserInfoForMessC13");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getPersonListC13() {
		List returnList = new ArrayList() ;
		try {
			 returnList = this.queryForList("report.hr.getPersonListC13");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@Override
	public int getPersonCountC13( ) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("report.hr.getPersonCountC13")), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * C13 hr to sap 人事信息
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getHrPerInfoForSapC13() {
		List returnList = new ArrayList() ;
		try {
			 returnList = this.queryForList("report.hr.getHrPerInfoForSapC13");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	
	/**
	 * C13 hr to sap 奖金
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getMoneyAwardForSapC13() {
		List returnList = new ArrayList() ;
		try {
			 returnList = this.queryForList("report.hr.getMoneyAwardForSapC13");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * C13 人员查询 (employee inquires)
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getHrEmployeeC13ForInterfaceList() {
		List returnList = new ArrayList() ;
		try {
			 returnList = this.queryForList("report.hr.getHrEmployeeC13ForInterfaceList");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}

	/**
	 * C13 人员数量查询 (employee count inquires)
	 * @param obj
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int getHrEmployeeC13ForInterfaceCnt( ) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("report.hr.getHrEmployeeC13ForInterfaceCnt")), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	//C13---------------end---------------C13
}

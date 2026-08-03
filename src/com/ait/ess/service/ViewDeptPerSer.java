package com.ait.ess.service;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface ViewDeptPerSer {


	/**
	 * 人员查询(search person list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPersonList(HttpServletRequest request) throws Exception;
	/**
	 * 人员查询(search person list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	/*@SuppressWarnings("unchecked")
	public List getPersonListOt(HttpServletRequest request) throws Exception;*/
	@SuppressWarnings("unchecked")
	public Object getPersonInfo(HttpServletRequest request) throws Exception;
	@SuppressWarnings("unchecked")
	public Object getPersonInfo2(HttpServletRequest request) throws Exception;

	/**
	 * 批量加班申请的人员列表总数(get overtime employee list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getPersonListCnt(HttpServletRequest request) throws Exception;
	
	public List getPersonManageList(HttpServletRequest request) throws Exception;

	public int getPersonManageListCnt(HttpServletRequest request) throws Exception;


	public List getDynamicGroup1List(HttpServletRequest request) ;
	
	public List viewArPersonalSingleList(HttpServletRequest request) ;
	
	
	public List viewArSummarySingleList(HttpServletRequest request) ;
	
	public List viewOtApplySingleList(HttpServletRequest request) ;
	
	public List viewAllowanceSingleList(HttpServletRequest request) ;

	/**
	 * 个人考勤现况  
	 */
	public List viewArPersonalList(HttpServletRequest request) throws Exception;
	public int viewArPersonalListCnt(HttpServletRequest request) throws Exception;
	
	
	//考勤动态组
	public List viewAutoColumnList(HttpServletRequest request) throws Exception;

	/**
	 * 加班年假
	 */
	public List viewArPersonalYearList(HttpServletRequest request) throws Exception;

	
	/**
	 * 考勤汇总
	 */
	public List viewArSummaryList(HttpServletRequest request) throws Exception;
	
	/**
	 * 考勤汇总(个人)
	 */
	public List viewArDetailSummaryForMonthList(HttpServletRequest request) throws Exception;
	/**
	 * 考勤汇总(考勤员)
	 */
	public List viewArDetailSummaryForAttenceList(HttpServletRequest request) throws Exception;
	
	/**
	 * 考勤汇总(报表)
	 */
	public List viewAttendanceForMonthList(HttpServletRequest request) throws Exception;
	
	public LinkedHashMap viewAttendanceForSpcBjMonthList(HttpServletRequest request,String deptLeve,String include) throws Exception;
	
	public LinkedHashMap viewAttendanceForSpcBjMonthList1(HttpServletRequest request,String deptLeve,String include) throws Exception;
	public LinkedHashMap getShopItemList(HttpServletRequest request,String cpnyId) throws Exception;
	public LinkedHashMap getShopItemListsh(HttpServletRequest request,String cpnyId) throws Exception;
	public LinkedHashMap getShopItemListsh1(HttpServletRequest request,String cpnyId) throws Exception;

	public LinkedHashMap getShopCountItemList(HttpServletRequest request) throws Exception;
	public LinkedHashMap getShopCountItemListsh(HttpServletRequest request) throws Exception;
	/**
	 * 年假使用情况
	 */
	public List yearInfo(HttpServletRequest request) throws Exception;
	
	public List vacInfo(HttpServletRequest request) throws Exception;
    
	public List yearUseInfo(HttpServletRequest request) throws Exception;
	/**
	 * 医疗期天数信息
	 */
	public List arForMedicalCountInfoList(HttpServletRequest request) throws Exception;
	
	/**
	 * 个人加班现况
	 */

	public List viewOtApplyPersonalList(HttpServletRequest request) throws Exception;
	public List viewUseOfAnnualLeaveList(HttpServletRequest request) throws Exception;
	public List viewUseOfAdjustLeaveList(HttpServletRequest request) throws Exception;
	public int viewOtApplyPersonalListCnt(HttpServletRequest request) throws Exception;
	public int viewUseOfAnnualLeaveListCnt(HttpServletRequest request) throws Exception;
	public int viewUseOfAdjustLeaveListCnt(HttpServletRequest request) throws Exception;
	@SuppressWarnings("unchecked")
	public List viewMedicalInfo(HttpServletRequest request) throws Exception;
	@SuppressWarnings("unchecked")
	public List leaveInfoList(HttpServletRequest request) throws Exception;
	/**
	 * 旷工查询
	 */

	public List viewAbsenteeismInfoList(HttpServletRequest request) throws Exception;
	public int viewAbsenteeismInfoListCnt(HttpServletRequest request) throws Exception;
	/**
	 * 考勤出入数据查询
	 */

	public List viewEntryInfoList(HttpServletRequest request) throws Exception;
	public int viewEntryInfoListCnt(HttpServletRequest request) throws Exception;

	/**
	 * manage 下的员工任职经历 
	 */

	public List ManageEmpPositionInfoList(HttpServletRequest request) throws Exception;
	
	public List getMonthAttDetailList(HttpServletRequest request) throws Exception;
	
	public int ManageEmpPositionInfoListCnt(HttpServletRequest request) throws Exception;
	/**
	 * manage 下的员工任职经历 个人
	 */

	public List ManageEmpPositionSinglList(HttpServletRequest request) throws Exception;
	public Object getEmpInfoById(HttpServletRequest request) throws Exception;
	/**
	 * gongsi
	 */
	@SuppressWarnings("unchecked")
	public String getCompanyCalendarViewHtml(HttpServletRequest request) ;
	
	/**
	 *上海离职率报表
	 */
	//总数
	public List totalEmpCountLastYear(HttpServletRequest request) throws Exception;
	
	public List getDemissionRateSpcSh(HttpServletRequest request) throws Exception;
	//离职
	public List LeftManTotalEmpCountLastYear(HttpServletRequest request) throws Exception;
	//新入社
	public List NewManTotalEmpCountLastYear(HttpServletRequest request) throws Exception;

	/**
	 *天津离职率报表
	 */
	//总数
	public List totalEmpCountLastYearTJ(HttpServletRequest request) throws Exception;
	//离职
	public List LeftManTotalEmpCountLastYearTJ(HttpServletRequest request) throws Exception;
	//新入社
	public List NewManTotalEmpCountLastYearTJ(HttpServletRequest request) throws Exception;
	
	
	public List getCountPosition(HttpServletRequest request) throws Exception;
	
	public List getOthers(HttpServletRequest request) throws Exception;
	
	public List getSearchMonth(HttpServletRequest request) throws Exception;
	
	public List getPositionRULIzhi(HttpServletRequest request) throws Exception;
	
	public List getEmpTypeRULIzhi(HttpServletRequest request) throws Exception;
	
	public List getGradeRuzhi(HttpServletRequest request) throws Exception;
	
	public List getGradeLizhi(HttpServletRequest request) throws Exception;
	
	public List getPCountEMP(HttpServletRequest request) throws Exception;
	
	public List getEmpTypeCountEMP(HttpServletRequest request) throws Exception;
	
	public List getGradeCountEmp(HttpServletRequest request) throws Exception;

/**
 * manage统计
 * @param request
 * @return
 */
	public List manageAgeCountList(HttpServletRequest request) throws Exception;
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeAgeList(HttpServletRequest request);
	//
	public List manageGradeCountList(HttpServletRequest request) throws Exception;
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeGradeList(HttpServletRequest request);
	//
	public List managePositionCountList(HttpServletRequest request) throws Exception;
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodePositionList(HttpServletRequest request);
	//
	public List manageEduCountList(HttpServletRequest request) throws Exception;
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeEduList(HttpServletRequest request);
	//
	public List manageEmpTypeCountList(HttpServletRequest request) throws Exception;
	@SuppressWarnings("unchecked")
	public LinkedHashMap getParentCodeEmpTypeList(HttpServletRequest request);
	
	
	//月别采用现状            月别退职现状
	
	public LinkedHashMap getParentCodeMonthList(HttpServletRequest request);

	
	public LinkedHashMap getParentCodeMonthResignList(HttpServletRequest request);

	/**
	 * 个人加班现况 manage
	 */

	public List viewOtApplyPersonalManageList(HttpServletRequest request) throws Exception;
	public int viewOtApplyPersonalManageListCnt(HttpServletRequest request) throws Exception;

	/**
	 * 个人考勤现况  
	 */
	public List viewArPersonalManageList(HttpServletRequest request) throws Exception;
	public int viewArPersonalManageListCnt(HttpServletRequest request) throws Exception;
	
}

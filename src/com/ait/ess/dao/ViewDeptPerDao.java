package com.ait.ess.dao;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface ViewDeptPerDao {
	
	
	public List getPersonList(Object object, int currentPage, int pageSize);

	public List getPersonList(Object object);

	public List getPersonListOt(Object object);
	
	public int getPersonListCnt(Object obj) throws Exception;
	
	
	public List getPersonManageList(Object object, int currentPage, int pageSize);

	public List getPersonManageList(Object object);
	public int getPersonManageListCnt(Object obj) throws Exception;
	
	public Object getPersonInfo(Object object);
	public Object getPersonInfo2(Object object);
	
	
	@SuppressWarnings("unchecked")
	public List getDynamicGroupList(Object object);
	

	@SuppressWarnings("unchecked")
	public List viewArPersonalSingleList(Object object);
	
	/**
	 * 上海离职率
	 * @param object
	 * @return
	 */
	
	//总数
	public List totalEmpCountLastYear(Object object);
	
	public List getDemissionRateSpcSh(Object object);
	
	public List getDemissionRateSpcSh(Object object, int currentPage, int pageSize);
		//离职
	public List LeftManTotalEmpCountLastYear(Object object);
		//新入社
	public List NewManTotalEmpCountLastYear(Object object);
	
	
	public List getCountPosition(Object object);
	
	public List getOthers(Object object);
	
	public List getSearchMonth(Object object);
	
	public List getPositionRULIzhi(Object object);
	
	public List getEmpTypeRULIzhi(Object object);
	
	public List getGradeRuzhi(Object object);
	
	public List getGradeLizhi(Object object);
	
	public List getPCountEMP(Object object);
	
	public List getEmpTypeCountEMP(Object object);
	
	public List getGradeCountEmp(Object object);
	
	/**
	 * 天津离职率
	 * @param object
	 * @return
	 */
	
	//总数
	public List totalEmpCountLastYearTJ(Object object);
		//离职
	public List LeftManTotalEmpCountLastYearTJ(Object object);
		//新入社
	public List NewManTotalEmpCountLastYearTJ(Object object);
	
	
	//总数
		public List totalEmpCountLastYear(Object object, int currentPage, int pageSize);
			//离职
		public List LeftManTotalEmpCountLastYear(Object object, int currentPage, int pageSize);
			//新入社
		public List NewManTotalEmpCountLastYear(Object object, int currentPage, int pageSize);

	
	@SuppressWarnings("unchecked")
	public List viewArSummarySingleList(Object object);
	
	@SuppressWarnings("unchecked")
	public List viewOtApplySingleList(Object object);
	
	@SuppressWarnings("unchecked")
	public List viewAllowanceSingleList(Object object);
	
	/**
	 * 个人考勤现况
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List viewArPersonalList(Object object, int currentPage, int pageSize);

	public List viewArPersonalList(Object object);
	

	public int viewArPersonalListCnt(Object obj) throws Exception;
	//个人加班考勤
	public List viewArPersonalYearList(Object object);
	
	//考勤汇总
	public List viewArSummaryList(Object object);
	
	//考勤汇总
	public List viewArDetailSummaryForMonthList(Object object);
	//考勤汇总
	public List viewArDetailSummaryForAttenceList(Object object);
	//考勤汇总
	public List viewAttendanceForMonthList(Object object);
	//考勤汇总
	public List viewAttendanceForSpcBjMonthList(Object object);
	//日报表
	public List viewAttendanceForSpcBjMonthList1(Object object);
	
	public List getAllShopDetailItem(Object object);
	
	public List getAllShopDetailItemsh(Object object);
	
	public List getAllShopDetailItemsh1(Object object);
	//个人年假信息
	public List yearInfo(Object object);
	
	public List vacInfo(Object object);
	//年假信息
	public List yearUseInfo(Object object);
	//动态列
	public List viewAutoColumnList(Object object);
	//医疗期天数信息
	public List arForMedicalCountInfoList(Object object, int currentPage, int pageSize);
	public List arForMedicalCountInfoList(Object object);
	/**
	 * 个人加班现况
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List viewOtApplyPersonalList(Object object, int currentPage, int pageSize);
	public List viewUseOfAnnualLeaveList(Object object, int currentPage, int pageSize);
	public List viewUseOfAdjustLeaveList(Object object, int currentPage, int pageSize);

	public List viewOtApplyPersonalList(Object object);
	public List viewUseOfAnnualLeaveList(Object object);
	public List viewUseOfAdjustLeaveList(Object object);
	public int viewOtApplyPersonalListCnt(Object obj) throws Exception;
	public int viewUseOfAnnualLeaveListCnt(Object obj) throws Exception;
	public int viewUseOfAdjustLeaveListCnt(Object obj) throws Exception;

	@SuppressWarnings("unchecked")
	public List viewMedicalInfo(Object object) throws Exception;
	@SuppressWarnings("unchecked")
	public List leaveInfoList(Object object) throws Exception;
	/**
	 * 旷工查询
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List viewAbsenteeismInfoList(Object object, int currentPage, int pageSize);

	public List viewAbsenteeismInfoList(Object object);
	public int viewAbsenteeismInfoListCnt(Object obj) throws Exception;
//end
	
	/**
	 * 考勤出入数据查询
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List viewEntryInfoList(Object object, int currentPage, int pageSize);

	public List viewEntryInfoList(Object object);
	public int viewEntryInfoListCnt(Object obj) throws Exception;

	

	/**
	 * manage下的部门员任职经历 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List ManageEmpPositionInfoList(Object object, int currentPage, int pageSize);

	public List ManageEmpPositionInfoList(Object object);
	
	public List getMonthAttDetailList(Object object);
	public int ManageEmpPositionInfoListCnt(Object obj) throws Exception;
/////end

	/**
	 * manage下的部门员任职经历   single
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */

	public List ManageEmpPositionSinglList(Object object);
	
	public Object getEmpInfoById(Object object);

/////////////////// 查询 
	@SuppressWarnings("unchecked")
	public List getCompanyCalendarList(Object object);
	//manage count 
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List getArClassCalendarList(Object object);
	@SuppressWarnings("unchecked")
	public String getDefaultGroup(Object object);
	@SuppressWarnings("unchecked")
	public List getArClassCalendarListGs(Object object) ;
	
	@SuppressWarnings("unchecked")
	public List getShiftList(Object object);
	/**
	 * 
	 */
	public List manageAgeCountList(Object object);
	
	/**
	 * 生成部门树
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getParentCodeList(Object object);
	
	/**
	 * 生成部门区分列表
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getParentCodeDifList(Object object);
	/**
	 * 得到所有部门
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAllDept(Object object);
	
	@SuppressWarnings("unchecked")
	public List getAllDept1(Object object);
	
	@SuppressWarnings("unchecked")
	public List getAllDeptForHr(Object object);
	
	@SuppressWarnings("unchecked")
	public List getAllShopItem(Object object);

	@SuppressWarnings("unchecked")
	public List getAllShopItemsh(Object object);
	
	@SuppressWarnings("unchecked")
	public List getAllShopItemsh1(Object object);
	
	@SuppressWarnings("unchecked")
	public List getAllShopCountItem(Object object);
	
	
	@SuppressWarnings("unchecked")
	public List getAllShopCountItemsh(Object object);
	
	@SuppressWarnings("unchecked")
	public List getAllShopCountItemsh1(Object object);
	
	@SuppressWarnings("unchecked")
	public int viewDeptLeve(Object object);
	
	@SuppressWarnings("unchecked")
	public int viewDeptClildNum(Object object);
	//部门区分
	@SuppressWarnings("unchecked")
	public List getAllDeptDif(Object object);
	/**
	 * 根据部门
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAgeListByDeptNo(Object object);
	//
	@SuppressWarnings("unchecked")
	public List getGradeBGZListByDeptNo(Object object);
	@SuppressWarnings("unchecked")
	public List getGradeSCZListByDeptNo(Object object);
	//
	@SuppressWarnings("unchecked")
	public List getPositionListByDeptNo(Object object);
	//
	@SuppressWarnings("unchecked")
	public List getEduListByDeptNo(Object object);
	//
	@SuppressWarnings("unchecked")
	public List getSexListByDeptNo(Object object);
	//
	@SuppressWarnings("unchecked")
	public List getEmpTypeListByDeptNo(Object object);
	
	//月别采用现状
	@SuppressWarnings("unchecked")
	public List getMonthListByDeptNo(Object object);
	
//月别退职现状
	
	@SuppressWarnings("unchecked")
	public List getMonthResignListByDeptNo(Object object);
	
	
	
	
	//manage权限 的
	public List viewArPersonalManageList(Object object, int currentPage, int pageSize);

	public List viewArPersonalManageList(Object object);
	

	public int viewArPersonalManageListCnt(Object obj) throws Exception;
	
	
	
	/**
	 * 个人加班现况manage
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List viewOtApplyPersonalManageList(Object object, int currentPage, int pageSize);

	public List viewOtApplyPersonalManageList(Object object);
	public int viewOtApplyPersonalManageListCnt(Object obj) throws Exception;

}

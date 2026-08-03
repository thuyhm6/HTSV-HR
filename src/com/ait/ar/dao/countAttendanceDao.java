package com.ait.ar.dao;

import java.util.LinkedHashMap;
import java.util.List;

public interface countAttendanceDao {

	public List getPersonList(Object object, int currentPage, int pageSize);

	public List getPersonList(Object object);

	public List getPersonListOt(Object object);

	public int getPersonListCnt(Object obj) throws Exception;

	public Object getPersonInfo(Object object);

	public Object getPersonInfo2(Object object);

	@SuppressWarnings("unchecked")
	public List getDynamicGroupList(Object object);

	@SuppressWarnings("unchecked")
	public List viewArPersonalSingleList(Object object);

	@SuppressWarnings("unchecked")
	public List viewOtApplySingleList(Object object);

	/**
	 * 个人考勤现况
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List viewArPersonalList(Object object, int currentPage, int pageSize);

	public List viewArPersonalList(Object object);

	public List viewArPersonalReport(Object object);

	// 动态列
	public List viewAutoColumnList(Object object);

	public int viewArPersonalListCnt(Object obj) throws Exception;

	/**
	 * 个人加班现况
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List viewOtApplyPersonalList(Object object, int currentPage,
			int pageSize);

	public int viewOtApplyPersonalListCnt(Object obj) throws Exception;

	/**
	 * 旷工查询
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List viewAbsenteeismInfoList(Object object, int currentPage,
			int pageSize);

	public List viewAbsenteeismInfoList(Object object);

	public int viewAbsenteeismInfoListCnt(Object obj) throws Exception;

	// end

	/**
	 * 考勤出入数据查询
	 * 
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
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List ManageEmpPositionInfoList(Object object, int currentPage,
			int pageSize);

	public List ManageEmpPositionInfoList(Object object);

	public int ManageEmpPositionInfoListCnt(Object obj) throws Exception;

	// ///end

	/**
	 * manage下的部门员任职经历 single
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */

	public List ManageEmpPositionSinglList(Object object);

	public Object getEmpInfoById(Object object);

	// ///////////////// 查询
	@SuppressWarnings("unchecked")
	public List getCompanyCalendarList(Object object);

	// manage count
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List getArClassCalendarList(Object object);

	@SuppressWarnings("unchecked")
	public String getDefaultGroup(Object object);

	@SuppressWarnings("unchecked")
	public List getArClassCalendarListGs(Object object);

	@SuppressWarnings("unchecked")
	public List getShiftList(Object object);

	/**
	 * 
	 */
	public List manageAgeCountList(Object object);

	/**
	 * 生成部门树
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getParentCodeList(Object object);

	/**
	 * 生成部门区分列表
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getParentCodeDifList(Object object);

	// 部门区分
	@SuppressWarnings("unchecked")
	public List getAllDeptDif(Object object);

	/**
	 * 根据部门
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAgeListByDeptNo(Object object);

	//
	@SuppressWarnings("unchecked")
	public List getGradeListByDeptNo(Object object);

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

	// 月别采用现状
	@SuppressWarnings("unchecked")
	public List getMonthListByDeptNo(Object object);

	// 月别退职现状

	@SuppressWarnings("unchecked")
	public List getMonthResignListByDeptNo(Object object);

	/************* 上面都可删除 *******************/
	/**
	 * 个人现状
	 */
	public List arCountInfoSonList(Object object);

	// 个人考勤现状
	public List viewOtApplyPersonalList(Object object);
	// 个人考勤现状
	public List viewOtYearPersonalList(Object object);

	/**
	 * 生成部门树
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getParentCodeDeptList(Object object);

	/**
	 * 生成日期树
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getParentCodeDateList(Object object);

	/**
	 * 生成职级树
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getParentCodeGradeList(Object object);

	/**
	 * 生成业务树
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getParentCodePositionList(Object object);

	/************************* 得到所有部门/日期/业务/职级 *********************/
	/**
	 * 得到所有部门
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAllDept(Object object);
	
	@SuppressWarnings("unchecked")
	public List getAllDeptForOrg(Object object);
	/**
	 * 得到二级部门
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSecDept(Object object);
	/**
	 * 得到所有日期
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAllDate(Object object);

	/**
	 * 得到所有职级
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAllGrade(Object object);

	/**
	 * 得到所有业务
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAllPosition(Object object);

	/**
	 * 八个sql统计 根据部门/日期/等
	 */
	// 部门现状 考勤
	@SuppressWarnings("unchecked")
	public List getDeptArListByDeptNo(Object object);

	// 部门现状 加班
	@SuppressWarnings("unchecked")
	public List getDeptOtListByDeptNo(Object object);

	// 日期现状 考勤
	@SuppressWarnings("unchecked")
	public List getDateArListByDeptNo(Object object);

	// 日期现状 加班
	@SuppressWarnings("unchecked")
	public List getDateOtListByDeptNo(Object object);

	// 业务现状 考勤
	@SuppressWarnings("unchecked")
	public List getPositionArListByDeptNo(Object object);

	// 业务现状 加班
	@SuppressWarnings("unchecked")
	public List getPositionOtListByDeptNo(Object object);

	// 职级现状 考勤
	@SuppressWarnings("unchecked")
	public List getGradeArListByDeptNo(Object object);

	// 职级现状 加班
	@SuppressWarnings("unchecked")
	public List getGradeOtListByDeptNo(Object object);

}

package com.ait.ar.service;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface countAttendanceSer {


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

	public List getDynamicGroup1List(HttpServletRequest request) ;
	
	public List viewArPersonalSingleList(HttpServletRequest request) ;
	
	public List viewOtApplySingleList(HttpServletRequest request) ;

	/**
	 * 个人考勤现况  
	 */
	public List viewArPersonalList(HttpServletRequest request) throws Exception;
	public int viewArPersonalListCnt(HttpServletRequest request) throws Exception;
	//考勤动态组
	public List viewAutoColumnList(HttpServletRequest request) throws Exception;

	
	/**
	 * 个人加班现况
	 */

	public int viewOtApplyPersonalListCnt(HttpServletRequest request) throws Exception;

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

	
	/*********************以上都可删除***************************************/
	/**
	 * 个人现况
	 */
	public List arCountInfoSonList(HttpServletRequest request) throws Exception;
//个人加班现况
	public List viewOtApplyPersonalList(HttpServletRequest request) throws Exception;
	//个人年加班现况
	public List viewOtYearPersonalList(HttpServletRequest request) throws Exception;

	/**
	 * 部门现状
	 */
	//考勤
	@SuppressWarnings("unchecked")
	public LinkedHashMap arForDeptCountInfoSonArList(HttpServletRequest request);
	//加班
	@SuppressWarnings("unchecked")
	public LinkedHashMap arForDeptCountInfoSonOtList(HttpServletRequest request);
	
	/**
	 * 日期现状
	 */
	//考勤
	@SuppressWarnings("unchecked")
	public List arForDateCountInfoSonArList(HttpServletRequest request);
	//加班
	@SuppressWarnings("unchecked")
	public List arForDateCountInfoSonOtList(HttpServletRequest request);
	
	
	/**
	 * 职级现状
	 */
	//考勤
	@SuppressWarnings("unchecked")
	public List arForGradeCountInfoSonArList(HttpServletRequest request);
	//加班
	@SuppressWarnings("unchecked")
	public List arForGradeCountInfoSonOtList(HttpServletRequest request);
	
	/**
	 * 业务现状
	 */
	//考勤
	@SuppressWarnings("unchecked")
	public List arForPositionCountInfoSonArList(HttpServletRequest request);
	//加班
	@SuppressWarnings("unchecked")
	public List arForPositionCountInfoSonOtList(HttpServletRequest request);
	
}

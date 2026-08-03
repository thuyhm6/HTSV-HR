package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.countAttendanceDao;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

@Repository
public class countAttendanceDaoImpl extends SqlMapClientSupport implements
		countAttendanceDao {

	/**
	 * 查询人员结果列表(search employee result list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getPersonList(obj, -1, -1);
		return returnList;
	}

	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * 
	 * @param object
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonListOt(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getPersonListOt(obj, -1, -1);
		return returnList;
	}

	/**
	 * 查询人员结果列表-判断是否分页,(search employee result list and judge if pagenation）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ar.countAr.getOtPersonList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ar.countAr.getOtPersonList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getPersonListOt(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ar.countAr.getOtPersonListOt",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ar.countAr.getOtPersonListOt",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int getPersonListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ar.countAr.getOtPersonListCnt", obj)),
				Integer.class);
	}

	/**
	 * 根据EMPID查询出人员信息(EMPID inquires according to the personnel information)
	 * 
	 * @param object
	 * @return retrunList
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPersonInfo(Object obj) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject("ar.countAr.getPersonInfoByid",
					obj);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getPersonInfo2(Object obj) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject("ar.countAr.getPersonInfoByid2",
					obj);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	/**
	 * 取得所有动态组列表(get DynamicGroup List)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getDynamicGroupList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getDynamicGroupList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 取得所有动态组信息列表(get DynamicGroup List)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getDynamicGroupList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ar.countAr.getDynamicArTypeList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"ar.countAr.getDynamicArTypeList", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 部门系人员 ，单个考勤信息列表(get DynamicGroup List)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List viewArPersonalSingleList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.viewArPersonalSingleList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 部门系人员 ，单个考勤信息列表((get DynamicGroup List)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List viewArPersonalSingleList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ar.countAr.viewArPersonalSingleList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ar.countAr.viewArPersonalSingleList", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 部门系人员 ，加班考勤信息列表(get DynamicGroup List)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List viewOtApplySingleList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.viewOtApplySingleList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 部门系人员 ，单个加班信息列表((get DynamicGroup List)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List viewOtApplySingleList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ar.countAr.viewOtApplySingleList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"ar.countAr.viewOtApplySingleList", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 动态组
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List viewArPersonalList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.viewArPersonalList(obj, -1, -1);
		return returnList;
	}

	/**
	 * 考勤报表
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List viewArPersonalReport(Object obj) {
		List returnList = new ArrayList();
		try {

			returnList = this.queryForList("ar.countAr.viewArPersonalReport", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 动态列
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List viewAutoColumnList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this
					.queryForList("ar.countAr.viewAutoColumnList", obj);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List viewArPersonalList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ar.countAr.viewArPersonalList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ar.countAr.viewArPersonalList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int viewArPersonalListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ar.countAr.viewArPersonalListCnt", obj)),
				Integer.class);
	}

	// ///end

	/**
	 * 个人加班现况
	 * 
	 * 
	 * 
	 * 
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List viewOtApplyPersonalList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ar.countAr.viewOtApplyPersonalList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"ar.countAr.viewOtApplyPersonalList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int viewOtApplyPersonalListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ar.countAr.viewOtApplyPersonalListCnt", obj)),
				Integer.class);
	}

	// end

	/**
	 *旷工查询页面
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewAbsenteeismInfoList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.viewAbsenteeismInfoList(obj, -1, -1);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List viewAbsenteeismInfoList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ar.countAr.viewAbsenteeismInfoList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"ar.countAr.viewAbsenteeismInfoList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int viewAbsenteeismInfoListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ar.countAr.viewAbsenteeismInfoListCnt", obj)),
				Integer.class);
	}

	// end

	/**
	 *考勤出入数据查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewEntryInfoList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.viewEntryInfoList(obj, -1, -1);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List viewEntryInfoList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ar.countAr.viewEntryInfoList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ar.countAr.viewEntryInfoList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int viewEntryInfoListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ar.countAr.viewEntryInfoListCnt", obj)),
				Integer.class);
	}

	// end

	/**
	 *manage权限下员工任职经历
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List ManageEmpPositionInfoList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.ManageEmpPositionInfoList(obj, -1, -1);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List ManageEmpPositionInfoList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ar.countAr.ManageEmpPositionInfoList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ar.countAr.ManageEmpPositionInfoList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int ManageEmpPositionInfoListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils
				.toString(this.queryForObject(
						"ar.countAr.ManageEmpPositionInfoListCnt", obj)),
				Integer.class);
	}

	// end

	/**
	 *manage权限下员工任职经历 single
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List ManageEmpPositionSinglList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"ar.countAr.ManageEmpPositionSinglList", obj);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getEmpInfoById(Object obj) {
		Object returnList = new Object();
		try {
			returnList = this.queryForObject("ar.countAr.getEmpInfoById", obj);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}

	// end

	/*****
	 * 公司日历 一系列
	 */
	/**
	 * 取得所有公司日历信息列表(get CompanyCalendar List)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getCompanyCalendarList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.getCompanyCalendarList",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 班组日历(get EmpCalendar List)
	 * 
	 * @param obj
	 * @return List
	 * @throws
	 */
	/*
	 * @author xuehaifei 班组日历查看 2014-7-9
	 */
	@SuppressWarnings("unchecked")
	public List getArClassCalendarList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.getArClassCalendarList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	@SuppressWarnings("unchecked")
	public String getDefaultGroup(Object object) {
		String returnValue = null;
		try {
			returnValue = StringUtil.checkNull(this.queryForObject(
					"ar.countAr.getDefaultGroup", object));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnValue;
	}

	@SuppressWarnings("unchecked")
	public List getArClassCalendarListGs(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"ar.countAr.getArClassCalendarListGs", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 取班次信息(get Shift List)
	 * 
	 * @param
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getShiftList(Object obj) {
		List returnList = new ArrayList();
		try {

			returnList = this.queryForList("ar.countAr.getShiftList", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	// //////////////////////////end

	/**
	 * manage count
	 */

	/*****
	 * 公司日历 一系列
	 */
	/**
	 * 取得所有公司日历信息列表(get CompanyCalendar List)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List manageAgeCountList(Object obj) {
		List returnList = new ArrayList();
		try {

			returnList = this
					.queryForList("ar.countAr.manageAgeCountList", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 生成部门树
	 */

	@SuppressWarnings("unchecked")
	public List getParentCodeList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.getParentCodeList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 生成部门区分列表
	 */

	@SuppressWarnings("unchecked")
	public List getParentCodeDifList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.getParentCodeDifList",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 根据部门编号统计年龄
	 */

	@SuppressWarnings("unchecked")
	public List getAgeListByDeptNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this
					.queryForList("ar.countAr.manageAgeCountList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getGradeListByDeptNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.manageGradeCountList",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getPositionListByDeptNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"ar.countAr.managePositionCountList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getEduListByDeptNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this
					.queryForList("ar.countAr.manageEduCountList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getSexListByDeptNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this
					.queryForList("ar.countAr.manageSexCountList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getEmpTypeListByDeptNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.manageEmpTypeCountList",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 得到所有部门 区分
	 */

	@SuppressWarnings("unchecked")
	public List getAllDeptDif(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.getAllDeptDif", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 月别采用现状
	 */
	@SuppressWarnings("unchecked")
	public List getMonthListByDeptNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.manageMonthCountList",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 月别退职现状
	 */
	@SuppressWarnings("unchecked")
	public List getMonthResignListByDeptNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"ar.countAr.manageMonthResignCountList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/************* 上面都可删除 ******/

	@SuppressWarnings("unchecked")
	@Override
	public List arCountInfoSonList(Object obj) {
		List returnList = new ArrayList();
		try {

			returnList = this
					.queryForList("ar.countAr.arCountInfoSonList", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 个人考勤 统计
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewOtApplyPersonalList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.viewOtApplyPersonalList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 个人年加班 统计
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List viewOtYearPersonalList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.viewOtYearPersonalList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/*************************** 四个树状结构 ********/
	/**
	 * 生成部门树
	 */

	@SuppressWarnings("unchecked")
	public List getParentCodeDeptList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.getParentCodeList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 医疗期天数统计
	 */
	@SuppressWarnings("unchecked")
	public List arForMedicalCountInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.arForMedicalCountInfoList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 生成日期树
	 */

	@SuppressWarnings("unchecked")
	public List getParentCodeDateList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.getParentCodeList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 生成职级树
	 */

	@SuppressWarnings("unchecked")
	public List getParentCodeGradeList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.getParentCodeList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 生成业务树
	 */

	@SuppressWarnings("unchecked")
	public List getParentCodePositionList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.getParentCodeList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/***************** 得到四个所有 ***************/

	/**
	 * 得到所有部门
	 */

	@SuppressWarnings("unchecked")
	public List getAllDept(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.getAllDept", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 得到所有部门
	 */

	@SuppressWarnings("unchecked")
	public List getAllDeptForOrg(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.getAllDeptForOrg", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 得到二级部门
	 */
	@SuppressWarnings("unchecked")
	public List getSecDept(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.getSecDept", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 得到所有日期
	 */

	@SuppressWarnings("unchecked")
	public List getAllDate(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.getAllDept", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 得到所有职级
	 */

	@SuppressWarnings("unchecked")
	public List getAllGrade(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.getAllDept", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 得到所有业务
	 */

	@SuppressWarnings("unchecked")
	public List getAllPosition(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.getAllDept", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 部门 、日期,职级，业务
	 */
	// 部门考勤
	@SuppressWarnings("unchecked")
	public List getDeptArListByDeptNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.getDeptArListByDeptNo",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	// 部门加班
	@SuppressWarnings("unchecked")
	public List getDeptOtListByDeptNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.getDeptOtListByDeptNo",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	// 日期考勤
	@SuppressWarnings("unchecked")
	public List getDateArListByDeptNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.getDateArListByDeptNo",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	// 日期加班
	@SuppressWarnings("unchecked")
	public List getDateOtListByDeptNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.getDateOtListByDeptNo",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	// 职级考勤
	@SuppressWarnings("unchecked")
	public List getGradeArListByDeptNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.getGradeArListByDeptNo",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	// 职级加班
	@SuppressWarnings("unchecked")
	public List getGradeOtListByDeptNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.countAr.getGradeOtListByDeptNo",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	// 业务考勤
	@SuppressWarnings("unchecked")
	public List getPositionArListByDeptNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"ar.countAr.getPositionArListByDeptNo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	// 业务加班
	@SuppressWarnings("unchecked")
	public List getPositionOtListByDeptNo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"ar.countAr.getPositionOtListByDeptNo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

}
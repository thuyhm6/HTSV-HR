package com.ait.report.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import com.ait.report.ar.dao.ArReportDao;
import com.ait.report.hr.dao.HrReportDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: ArReportDaoImpl.java
 * @Description: implement Class ArReportDaoImpl.java
 * @Create date: April 14, 2012 3:29:38 PM
 * @Create by: hanzhe (hanzhe@ait.net.cn)
 * @version 5.1
 */
@Repository
public class ArReportDaoImpl extends SqlMapClientSupport implements ArReportDao {

	/**
	 * 取得所有CODE列表,依据PARENT_CODE
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getCodeListByParentCode(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"report.ar.getCodeListByParentCode", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"report.ar.getCodeListByParentCode", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getCodeListByParentCode(Object object) {
		List returnList = new ArrayList();
		returnList = this.getCodeListByParentCode(object, -1, -1);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getArCodeNameByCode(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("report.ar.getArCodeNameByCode",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getreportList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("report.ar.getreportList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 按部门查出员工考勤 导出excel用
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getArDeptShiftList(Map paramMap) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("report.ar.getArDeptShiftList",
					paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 查找公司日历排班
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getArCompanyCalendarShiftList(Map paramMap) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"report.ar.getArCompanyCalendarShiftList", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getHrPersonFormalList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("report.ar.getHrPersonFormalList",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getHrPersonEntryList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("report.ar.getHrPersonEntryList",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getHrPersonEntryList1(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("report.ar.getHrPersonEntryList1",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getHrPersonRankList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this
					.queryForList("report.ar.getHrPersonRankList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getHrPersonDeptList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this
					.queryForList("report.ar.getHrPersonDeptList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

}

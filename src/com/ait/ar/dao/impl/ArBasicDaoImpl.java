package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.ArBasicDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class ArBasicDaoImpl extends SqlMapClientSupport implements ArBasicDao {
	/**
	 * 根据考勤员权限取得人员信息列表
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArSearchEmployeeList(Object obj) {
		List returnList = new ArrayList();

		returnList = this.getArSearchEmployeeList(obj, -1, -1);

		return returnList;
	}

	/**
	 * 根据考勤员权限取得人员信息列表
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArSearchEmployeeList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ar.basic.getArSearchEmployeeList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"ar.basic.getArSearchEmployeeList", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 根据考勤员权限取得人员信息人数
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getArSearchEmployeeCnt(Object obj) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ar.basic.getArSearchEmployeeCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
}

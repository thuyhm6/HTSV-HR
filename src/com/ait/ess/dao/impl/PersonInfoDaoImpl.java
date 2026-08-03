package com.ait.ess.dao.impl;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ait.ess.dao.PersonInfoDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class PersonInfoDaoImpl extends SqlMapClientSupport implements
		PersonInfoDao {

	/**
	 * 显示个人信息申请修改页面（View personal information apply）
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map getEssPersonInfo(Object obj) throws Exception {
		Map map = new LinkedHashMap();
		try {
			map = (Map) this.queryForObject(
					"ess.personinfo.retrieveEssPersonalInfoNew", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 显示个人信息申请修改页面（View personal information apply）
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map getHrPersonInfo(Object obj) throws Exception {
		Map map = new LinkedHashMap();
		try {
			map = (Map) this.queryForObject(
					"ess.personinfo.retrieveHrPersonalInfoNew", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map getEducationInfoList(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			List Rows = this.queryForList(
					"ess.personinfo.retrieveContractInfo", object, skipResults,
					maxResults);
			Integer count = (Integer) this.queryForObject(
					"ess.personinfo.retrieveContractInfoCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@Override
	public Map getEvaluateforList(Object object, int skipResults, int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			List Rows = this.queryForList(
					"ess.personinfo.retrieveEvaluateByYear", object,
					skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"ess.personinfo.retrieveEvaluateByYearCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

}

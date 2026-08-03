package com.ait.ess.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ess.dao.PersonalPaInfoDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class PersonalPaInfoDaoImpl extends SqlMapClientSupport implements
		PersonalPaInfoDao {

	/**
	 * 取工资加项(get AddPro List)
	 * @param request
	 * @return List
	 */
	@Override
	public List getAddProList(Map obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.personalpainfo.getAddProList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 取工资减项(get MiPro List)
	 * @param request
	 * @return List
	 */
	@Override
	public List getMiProList(Map obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.personalpainfo.getMiProList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	@Override
	public List getSalaryProvideDateEss(Map object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.personalpainfo.getSalaryProvideDateEss", object);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return returnList ;
	}

	@Override
	public int getSalaryDispark(Map object) {
		int returnInt = 0 ;
		try {
			// this.queryForList("ess.personalpainfo.getSalaryProvideDateEss", object);
			 returnInt =	NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.personalpainfo.getSalaryDispark", object)),Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

		return returnInt ;

	}
}

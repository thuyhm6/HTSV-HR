package com.ait.ess.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ess.dao.changeDao;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

@Repository
public class changeDaoImpl extends SqlMapClientSupport implements changeDao {
	
	

	@Override
	public void changePassword(Object object) throws Exception {
		
		this.update("ess.change.changePassword", object);

	}

	
	/**
	 * 员工信息查询(Employee Information Management)
	 * 
	 * @param obj
	 * @return object2
	 */
	@Override
	public Object getPersonalInfoByPid(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject(
					"ess.change.getPersonalInfoByPidNew", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}
		
}
package com.ait.hrm.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ait.hrm.dao.HrmDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class HrmDaoImpl extends SqlMapClientSupport implements HrmDao {	
	
	
	public Object getDeptById(Object object) {
		Object object2 = null;
		try {
			object2 = this.queryForObject("hrm.getDeptById",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return object2;		
	}

	@SuppressWarnings("unchecked")
	public List getDeptTree(String sm, Object object) {
		List list = new ArrayList();		
		try {
			list = this.queryForList(sm,object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List getBusiness(Object object) {
		List list = new ArrayList();		
		try {
			list = this.queryForList("hrm.getBusiness",object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}

	
}

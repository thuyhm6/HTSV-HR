package com.ait.hrm.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ait.hrm.dao.ResumeSearchDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class ResumeSearchDaoImpl extends SqlMapClientSupport implements ResumeSearchDao {

	@SuppressWarnings("unchecked")
	@Override
	public List getResumeInfoListForSearch(Object object) {
		List returnList = new ArrayList();
		try {
			
				returnList = this.queryForList(
						"hrm.getResumeInfoList", object);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List getResumeInfoListForSearchAffrim(Object object) {
		List returnList = new ArrayList();
		try {
			
				returnList = this.queryForList(
						"hrm.getResumeInfoAffrimList", object);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}	
	
		
}

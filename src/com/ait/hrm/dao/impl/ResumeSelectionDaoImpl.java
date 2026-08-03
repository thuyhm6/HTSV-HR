package com.ait.hrm.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.hrm.dao.ResumeSelectionDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class ResumeSelectionDaoImpl extends SqlMapClientSupport implements ResumeSelectionDao {

	@SuppressWarnings("unchecked")
	@Override
	public List getResumeSelectionInfoList(Object object) {
		List returnList = new ArrayList();
		try {
			
				returnList = this.queryForList(
						"hrm.resumeSelection.getResumeInfoList", object);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getResumeSelectionInfoList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"hrm.resumeSelection.getResumeInfoList", object,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"hrm.resumeSelection.getResumeInfoList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int getResumeSelectionCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("hrm.resumeSelection.getResumeInfoListCnt",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateResumeInfoForUpdate(Object object) {
		int returnInt = 1;
		try {
			
			this.updateForList("hrm.resumeSelection.updateResumeInfoForUpdate",(List)object);

		} catch (SQLException e) {
			e.printStackTrace();
			returnInt = 0;
		}
		return returnInt;
	}
		
}

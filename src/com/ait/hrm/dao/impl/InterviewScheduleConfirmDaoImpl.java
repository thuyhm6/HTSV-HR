package com.ait.hrm.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ait.hrm.dao.InterviewScheduleConfirmDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class InterviewScheduleConfirmDaoImpl extends SqlMapClientSupport implements InterviewScheduleConfirmDao{

	@SuppressWarnings("unchecked")
	@Override
	public List getInterviewScheduleConfirmList (LinkedHashMap map) throws Exception {
		List returnList = new ArrayList();
		returnList = this.queryForList("hrm.interviewScheduleConfirm.getInterviewScheduleConfirmList", map);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int saveInterviewConfirm (LinkedHashMap paramMap) throws Exception {
		List confirmList = new ArrayList();
		confirmList = this.queryForList("hrm.interviewScheduleConfirm.getInterviewStatus", paramMap);
		if (confirmList.size() > 0) {
			return 2;
		} else {
			this.update("hrm.interviewScheduleConfirm.saveInterviewConfirm", paramMap);
			return 1;
		}
	}
}

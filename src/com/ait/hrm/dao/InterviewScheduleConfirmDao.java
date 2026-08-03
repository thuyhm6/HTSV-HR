package com.ait.hrm.dao;

import java.util.LinkedHashMap;
import java.util.List;

public interface InterviewScheduleConfirmDao {
	
	@SuppressWarnings("unchecked")
	public List getInterviewScheduleConfirmList (LinkedHashMap map) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int saveInterviewConfirm (LinkedHashMap map) throws Exception;

}

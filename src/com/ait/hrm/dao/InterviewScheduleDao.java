package com.ait.hrm.dao;

import java.util.LinkedHashMap;
import java.util.List;

public interface InterviewScheduleDao {
	
	@SuppressWarnings("unchecked")
	public List getInterviewScheduleList (LinkedHashMap map) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void addRecAffirmInfo (LinkedHashMap map) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updateRecPageFlag(LinkedHashMap paramMap) throws Exception;
}

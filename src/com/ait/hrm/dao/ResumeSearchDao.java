package com.ait.hrm.dao;

import java.util.List;

public interface ResumeSearchDao {
	@SuppressWarnings("unchecked")
	public List getResumeInfoListForSearch(Object object);
	@SuppressWarnings("unchecked")
	public List getResumeInfoListForSearchAffrim(Object object);
}

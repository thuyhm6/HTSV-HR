package com.ait.hrm.dao;

import java.util.List;

public interface ResumeSelectionDao {
	@SuppressWarnings("unchecked")
	public List getResumeSelectionInfoList(Object object);
	@SuppressWarnings("unchecked")
	public List getResumeSelectionInfoList(Object object, int currentPage, int pageSiz);
	
	public int getResumeSelectionCnt(Object object);
	
	public int updateResumeInfoForUpdate(Object object);

}

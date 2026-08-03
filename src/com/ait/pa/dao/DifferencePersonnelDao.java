package com.ait.pa.dao;

import java.util.List;

public interface DifferencePersonnelDao {

	@SuppressWarnings("unchecked")
	public List getDifferencePersonnelList(Object object);
	
	public int getDifferencePersonnelCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getDifferencePersonnelList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public int addDifferencePersonnelInfo(List list);
	
	public int deleteDifferencePersonnelInfo(Object object) ;
	
}

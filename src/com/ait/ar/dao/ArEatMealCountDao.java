package com.ait.ar.dao;

import java.util.List;

public interface ArEatMealCountDao {
	/**
	 * 将初始化时间按天分解并封装到LIST(decompose apply date for list)
	 * 
	 * @param obj
	 * @return
	 */ 
	@SuppressWarnings("unchecked")
	public List getInitDateList(Object obj) throws Exception ;
	
	@SuppressWarnings("unchecked")
	public List getEatMealCountC12(Object obj);
	
	@SuppressWarnings("unchecked")
	public int getEmpMealCountByDateCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getEmpMealCountByDateList(Object object);
	
	public void initMealCount(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getEatMealCountList(Object obj);
	
	@SuppressWarnings("unchecked")
	public List getEatMealCountList(Object object,int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public int getEatMealCountCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getEatMealCountPersonList(Object obj);
	
	@SuppressWarnings("unchecked")
	public List getEatMealCountPersonList(Object object,int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public int getEatMealCountPersonListCnt(Object object);
	
	public void deleteArEatCountInfo(Object object) throws Exception;
	
	public void updateArEatCountInfo(Object object) throws Exception;
}

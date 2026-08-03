package com.ait.laborUnion.dao;

import java.util.List;

public interface LaborUnionInfoDao {
	
	
	@SuppressWarnings("unchecked")
	public Object getLaborUnionSumForSearch(Object object);
	
	@SuppressWarnings("unchecked")
	public Object getLaborUnionSumForSearch2(Object object);
	
	@SuppressWarnings("unchecked")
	public Object getLaborUnionExcel(Object object);
	
	
	@SuppressWarnings("unchecked")
	public int  getLaborUnionCnt(Object object);
}

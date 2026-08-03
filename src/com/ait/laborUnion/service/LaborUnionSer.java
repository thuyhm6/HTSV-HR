package com.ait.laborUnion.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface LaborUnionSer {
	@SuppressWarnings("unchecked")
	public Object getLaborUnionSum(Map paramMap);
	
	@SuppressWarnings("unchecked")
	public Object getLaborUnionSum2(Map paramMap);
	
	@SuppressWarnings("unchecked")
	public Object getLaborUnionExcel(Map paramMap);
	
	
	
	@SuppressWarnings("unchecked")
	public int getLaborUnionCnt(HttpServletRequest request);
	
}

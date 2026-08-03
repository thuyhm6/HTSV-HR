package com.ait.pa.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public interface PaArSummaryManageDao {
	
	@SuppressWarnings("unchecked")
	public List getPaArSummaryForManageList(Object object);
	
	@SuppressWarnings("unchecked")
	public void updatePaArSummaryForManageInfo(HttpServletRequest request,LinkedHashMap object) throws Exception;
	
}

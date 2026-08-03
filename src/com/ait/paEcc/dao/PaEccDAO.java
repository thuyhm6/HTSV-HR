package com.ait.paEcc.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface PaEccDAO {

	public List getEmpPaEcc(Object parameterObject);
	
	public List getPaEccInfo(Object parameterObject);
	
	public List getPaEccInfo(Object parameterObject,int currentPage, int pageSize);
	
	public int getPaEccCnt(Object parameterObject);
	
	public int delPaEccInfo(Map parameterMap);
	
	public int calculatePaEcc(Map parameterMap);
	
	public int updatePaEcc(List<Map<String, Object>> list);
	
	public int settlementPaEcc(Map parameterMap);
	
	public int cancelSettlementPaEcc(Map parameterMap);
	
	public List searchBatchesByPaMonth(Object parameterObject);
	
	public String getEccFlag(Object obj) throws Exception;
	
	public String getEccResultNo(Object parameterObject);
	
	public Map getResignInfo(Object parameterObject);
	
	public List getEccEmpPaInfo(Object parameterObject);
	
	public String getEmpPAStartMonth(Object parameterObject);
}

package com.ait.is.dao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface BaseManagementForSearchDao {
	@SuppressWarnings("unchecked")
	public int backInsuranceBaseNumUpdateBz()throws SQLException;
	@SuppressWarnings("unchecked")
	public List getInsuranceBaseNumListBz(Object object)throws SQLException;
	@SuppressWarnings("unchecked")
	public List getInsuranceBaseNumCheckListBz()throws SQLException;
	@SuppressWarnings("unchecked")
	public int createDataInstancenBaseBz(Object object)throws SQLException;
	@SuppressWarnings("unchecked")
	public int getInsuranceBaseCnt(Object obj);
	@SuppressWarnings("unchecked")
	public List getNOInsBaseNumList(Object obj)throws SQLException;
	@SuppressWarnings("unchecked")
	public List getInsuranceBaseNumListBz(Object obj, int pageNum, int numPerPage);
	@SuppressWarnings("unchecked")
	public void deleteInstanceaseManagement(Object obj)throws SQLException;
	@SuppressWarnings("unchecked")
	public List getupdateInstanceBaseManagement(Object obj)throws SQLException;
	@SuppressWarnings("unchecked")
	public void editInstanceBaseManagement(Object obj)throws SQLException;
	@SuppressWarnings("unchecked")
	public void allowInstanceBaseNumUpdate(Object obj)throws SQLException;

}

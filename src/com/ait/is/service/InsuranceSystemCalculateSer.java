package com.ait.is.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   AIT5.5
 * Company:     AIT5.5
 * @fileName: InsuranceSystemCalculateSer.java
 * @Description:
 * @Create date: 2014-1-21 下午02:56:24
 * @Create by: heran(heran@ait.net.cn)
 * @version 5.1
 */
public interface InsuranceSystemCalculateSer {
		
	@SuppressWarnings("unchecked")
	public List getInsuranceSystemInfoListForSearch(HttpServletRequest request) throws SQLException ;
	
	@SuppressWarnings("unchecked")
	public List getVersionDateListBz(HttpServletRequest request) throws SQLException ;
	
	@SuppressWarnings("unchecked")
	public List getModifyStandardSeriousBz(HttpServletRequest request) throws SQLException ;
	
	@SuppressWarnings("unchecked")
	public int ifUpdatedVersionBz(HttpServletRequest request) throws SQLException ;
	
	@SuppressWarnings("unchecked")
	public String getMaxManageCreateDateBz(HttpServletRequest request) throws SQLException ;
	
	@SuppressWarnings("unchecked")
	public int selectPaBenFalgByFalgBz(Map map) throws SQLException ;
	
	
	@SuppressWarnings("unchecked")
	public int createBenchmarkStandardVersionBz(Map map) throws SQLException ;
	
	@SuppressWarnings("unchecked")
	public void freshPaBenManageBz(Map map) throws SQLException ;
	
	@SuppressWarnings("unchecked")
	public int updateBenchmarkManagement(HttpServletRequest request) throws SQLException ;
	
	@SuppressWarnings("unchecked")
	public List getPaBenStandardNotSeriousBz(HttpServletRequest request) throws SQLException ;
	
	@SuppressWarnings("unchecked")
	public List getPaBenStandardSeriousBz(HttpServletRequest request) throws SQLException ;
	
	@SuppressWarnings("unchecked")
	public Map ViewBaseManagementForSearch(HttpServletRequest request) throws SQLException ;
	
	
}

package com.ait.disc.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface RetrieveSqlMasterSer {
	public List getSqlMasterList(HttpServletRequest request) throws SQLException;
	public int getSqlMasterListCnt(HttpServletRequest request) throws Exception;
	public int insertSqlMaster(HttpServletRequest request) throws Exception;
	public Object getSqlMaster(HttpServletRequest request) throws Exception;
	public int updateSqlMaster(HttpServletRequest request) throws Exception;
	public int updateSqlParam(HttpServletRequest request) throws Exception;
	public List getSqlParam(HttpServletRequest request) throws Exception;
	public int insertSqlParam(HttpServletRequest request) throws Exception;
	public List getSqlParamList(HttpServletRequest request) throws Exception;
	public int deleteSqlMaster(HttpServletRequest request) throws Exception;
	public int deleteSqlParamList(HttpServletRequest request) throws Exception;
	
}

package com.ait.disc.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface RetrieveSqlMasterDao {
	/** 
	* @Title: getRetrivevSqlMasterList 
	* @Description: 查询已配置自动下载excel列表 
	* @param @param obj
	* @param @return
	* @param @throws Exception    
	* @return List    
	* @throws 
	*/
	
	public List getRetrieveSqlMasterList(Object obj) throws SQLException;

	public List getRetrieveSqlMasterList(Object obj, int pageNum, int numPerPage);
	
	public int getRetrieveSqlMasterListCnt(Object obj);

	public int insertSqlMaster(Object object) throws Exception;
	
	public List getSqlMaster(Object object) throws Exception;
	
	public int updateSqlMaster(Object object) throws Exception;
	
	public int updateSqlParam(Object object) throws Exception;
	
	public List getSqlParamList(Object object) throws Exception;
	
	public int insertSqlParam(Object object) throws Exception;
	
	public int updateSqlParamUseN(Object object) throws Exception;
	public Map getSqlParam(Object object) throws Exception;

	public void deleteSqlMaster(Object object) throws Exception;
	public void deleteSqlParam(Object object) throws Exception;
	
	public List querySql(Object object) throws Exception;
}

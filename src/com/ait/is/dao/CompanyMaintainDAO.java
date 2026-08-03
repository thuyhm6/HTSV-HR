package com.ait.is.dao;

import java.sql.SQLException;
import java.util.List;



public interface CompanyMaintainDAO {

	/**
	 * 添加
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int addIsCompanyInfo(Object parameterObject) throws Exception;
	
	/**
	 * 修改
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int updateIsCompanyInfo(Object parameterObject) throws Exception;
	
	/**
	 * 删除
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int deleteIsCompanyInfo(List list) throws Exception;
	
	/**
	 * 查找List  不分页
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getIsCorpInfo(Object parameterObject)throws SQLException;
	public List getIsCorpInfoNotCH(Object parameterObject)throws SQLException;
	
	/**
	 * 查找数量
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getIsCorpCnt(Object parameterObject)throws SQLException;
	
	/**
	 * 查找List  分页
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getIsCorpInfo(Object obj, int currentPage, int pageSize)throws SQLException;

	public List getIsCorpInfoNotCH(Object obj, int currentPage, int pageSize)throws SQLException;

	public int getIsCorpCntNotCH(Object parameterObject) throws SQLException;
	
	/**
	 * 根据法人别查找  法人用的人员类型
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	
}

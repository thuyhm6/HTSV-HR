package com.ait.ess.dao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

public interface DimissionApplyDao {
	/**
	 * 检查是否存在该人的离职信息
	 * @param object
	 * @return
	 */
	public int checkAddDimissionInfo(Object object) ;
	/**
	 * 离职申请
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void addDimissionInfoApply(LinkedHashMap map) throws Exception;

	public String getDimissionInfoSeq() throws Exception;
	
	/**
	 * 查找离职申请的List详情数量
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getDimissionCnt(Object object)throws SQLException;
	
	/**
	 * 查找离职申请的List 不分页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */ 
	@SuppressWarnings("unchecked")
	public List getDimissionList(Object object);
	
	/**
	 * 查找离职申请的List 分页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */ 
	@SuppressWarnings("unchecked")
	public List getDimissionList(Object object, int currentPage, int pageSize);
	
	/**
	 * 删除还没有审批的离职申请信息
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws SQLException 
	 * @throws Exception
	 */ 
	@SuppressWarnings("unchecked")
	public void deleteDimissionInfo(Object object) throws SQLException;
}

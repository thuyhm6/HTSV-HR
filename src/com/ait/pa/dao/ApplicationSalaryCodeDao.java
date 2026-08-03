package com.ait.pa.dao;

import java.util.List;
import java.util.Map;

public interface ApplicationSalaryCodeDao {
	
	/**
	 * 查找人员工资代码权限匹配记录  不分页
	 */
	@SuppressWarnings("unchecked")
	public List getApplicationSalaryCodeList(Object object);
	
	/**
	 * 查找人员工资代码权限匹配记录  分页
	 */
	@SuppressWarnings("unchecked")
	public List getApplicationSalaryCodeList(Object object, int currentPage, int pageSize);
	
	/**
	 * 查找人员工资代码权限匹配记录  数量
	 */
	@SuppressWarnings("unchecked")
	public int getApplicationSalaryCodeCnt(Object object);
	
	/**
	 * 根据工资代码的item_no查找工资代码相应的名称
	 */
	@SuppressWarnings("unchecked")
	public List findSalaryNameByItemNo(String item_no);
	
	/**
	 * 添加人员工资代码匹配的记录
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addApplicationSalaryInfo(Object object)throws Exception;
	/**
	 * 检查该人员是否有相应的记录  如果有则不能添加  请修改
	 * @param object
	 * @return
	 */
	
	public int checkApplicationSalaryInfo(Object object);
	
	/**
	 * 删除人员工资代码匹配的记录
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void deleteApplicationSalaryInfo(Object object) throws Exception;
	
	/**
	 * 修改人员工资代码匹配的记录
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateApplicationSalaryInfo(Object object)throws Exception;
	
	/***
	 * 得到一条记录的详细信息
	 */
	@SuppressWarnings("unchecked")
	public Map getApplicationSalaryInfo(Object object) ;
	
	/***
	 * 添加页面的代码表
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaInputItemApplicationList(Object object);
}

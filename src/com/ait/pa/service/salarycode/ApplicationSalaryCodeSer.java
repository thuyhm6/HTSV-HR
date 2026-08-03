package com.ait.pa.service.salarycode;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface ApplicationSalaryCodeSer {
	/**
	 * 查找人员和工资代码权限的匹配记录   
	 */
	@SuppressWarnings("unchecked")
	public List getApplicationSalaryCodeList(HttpServletRequest request) ;
	
	/**
	 * 查找人员和工资代码权限的匹配记录    数量
	 */
	@SuppressWarnings("unchecked")
	public int getApplicationSalaryCodeCnt(HttpServletRequest request) ;
	
	/**
	 * 根据工资代码的item_no查找工资代码相应的名称
	 */
	public List findSalaryNameByItemNo(HttpServletRequest request,String item_no);
	
	/**
	 * 添加人员工资代码匹配的记录
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addApplicationSalaryInfo(HttpServletRequest request)throws Exception;
	/**
	 * 检查该人员是否有相应的记录  如果有则不能添加  请修改
	 * @param object
	 * @return
	 */
	
	public int checkApplicationSalaryInfo(HttpServletRequest request);
	
	/**
	 * 删除人员工资代码匹配的记录
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int deleteApplicationSalaryInfo(HttpServletRequest request) throws Exception;
	
	/**
	 * 修改人员工资代码匹配的记录
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updateApplicationSalaryInfo(HttpServletRequest request)throws Exception;
	
	/***
	 * 得到一条记录的详细信息
	 */
	@SuppressWarnings("unchecked")
	public Map getApplicationSalaryInfo(HttpServletRequest request) ;
	
	/***
	 * 添加页面的代码表
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaInputItemApplicationList(HttpServletRequest request);
}

package com.ait.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

public interface salaryMappingSer {
	/**
	 * 查找所有工资项目（基本项目，输入项目，计算项目）的和法人匹配的信息列表  
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSalaryMappingList(HttpServletRequest request) ;
	
	/**
	 * 查找所有工资项目（基本项目，输入项目，计算项目）的和法人匹配的信息列表  工资查看页面 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSalaryItemCheckMapList(HttpServletRequest request) ;
	
	/**
	 * 查找所有工资项目(基本项目，输入项目，计算项目)的和法人匹配的信息的数量 工资代码查看页面
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getSalaryItemCheckMapCnt(HttpServletRequest request);
	
	 /**
     * 根据item_no查找数据库中所有被指定的法人
     */
	public List SalaryCpnyList(HttpServletRequest request);
	
	/**
	 * 查找所有工资项目(基本项目，输入项目，计算项目)的和法人匹配的信息的数量
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getSalaryMappingCnt(HttpServletRequest request);
	
	/**
	 * 查找单行记录的详细信息
	 * @param request
	 * @return
	 */
	public Object getSalaryMappingInfo(HttpServletRequest request) ;
	
	/**
	 * 申请工资代码
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public int addAffirmSalaryCodeInfo(HttpServletRequest request) throws Exception;
	
	
	/**
	 * 申请工资代码（启用）
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public int addAffirmSalaryInfo(HttpServletRequest request) throws Exception;
	
	/**
	 * 申请工资代码决裁
	 * @param request
	 * @return
	 * 
	 */
	public int updateAffirmSalaryInfo(HttpServletRequest request);
	
	/**
	 * 申请工资代码修改
	 * @param request
	 * @return
	 */
	public int updateAffirmSalaryCodeInfo(HttpServletRequest request);
	
	/**
	 * 申请工资代码删除
	 * @param request
	 * @return
	 */
	public int deleteAffirmSalaryCodeInfo(HttpServletRequest request);

	List getSalaryMappingListForAll(HttpServletRequest request, ModelMap modelMap);//为获取某法人的所有工资参数
}

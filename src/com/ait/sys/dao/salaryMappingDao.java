package com.ait.sys.dao;

import java.sql.SQLException;
import java.util.List;

public interface salaryMappingDao {

	/**
	 * 查找所有工资项目（基本项目，输入项目，计算项目）的和法人匹配的信息列表   不分页
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSalaryMappingList(Object object);
	
	/**
	 * 查找所有工资项目（基本项目，输入项目，计算项目）的和法人匹配信息列表   工资代码查看页面
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSalaryItemCheckMapList(Object object);
	
	/**
	 * 查找所有工资项目参数（基本项目，输入项目，计算项目）的和法人匹配的信息列表   不分页
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSalaryCpnyList(String item_no);
	
	/**
	 * 查找所有工资项目(基本项目，输入项目，计算项目)的和法人匹配信息的数量
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getSalaryMappingCnt(Object object);
	
	/**
	 * 查找所有工资项目(基本项目，输入项目，计算项目)的和法人匹配信息的数量 工资代码查看页面
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getSalaryItemCheckMapCnt(Object object);
	
	/**
	 * 分页查找所有工资项目（基本项目，输入项目，计算项目）的和法人匹配信息列表   
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSalaryMappingList(Object object, int currentPage, int pageSize);
	
	/**
	 * 分页查找所有工资项目（基本项目，输入项目，计算项目）的和法人匹配信息列表   工资代码查看页面
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSalaryItemCheckMapList(Object object, int currentPage, int pageSize);
	
	/**
	 * 查找单条记录的详细信息
	 * @param object
	 * @return
	 */
	public Object getSalaryMappingInfo(Object object);
	
	/**
	 * 修改申请记录（工资代码）
	 * @param object
	 * @return
	 */
	public int updateAffirmSalaryCodeInfo(Object object);
	
	/**
	 * 决裁申请记录
	 * @param object
	 * @return
	 */
	public int updateAffirmSalaryInfo(Object object);
	
	/**
	 * 添加申请记录（工资代码申请）
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	public int addAffirmSalaryCodeInfo(Object object) throws Exception;
	
	/**
	 * 添加申请记录（工资代码申请（启用））
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	public int addAffirmSalaryInfo(Object object) throws Exception;
	
	/**
	 * 删除申请记录
	 * @param object
	 * @return
	 * @throws SQLException 
	 */
	public int deleteAffirmSalaryCodeInfo(Object object) throws SQLException;
}

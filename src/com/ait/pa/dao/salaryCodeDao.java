package com.ait.pa.dao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;


public interface salaryCodeDao {
	/**
	 * 查找所有工资项目（基本项目，输入项目，计算项目）的信息列表   不分页
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSalaryCodeList(Object object);
	
	/**
	 * 需要决裁的（决裁查看）
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmSalaryCodeList(Object object);
	
	
	/**
	 * 需要决裁的（决裁查看）决裁情况查看
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmSalary(Object object);
	
	
	/**
	 * 需要决裁的（决裁页面）
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmSalaryList(Object object);
	
	/**
	 * 查找所有工资项目(基本项目，输入项目，计算项目)的信息的数量
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getSalaryCodeCnt(Object object);
	
	/**
	 *需要决裁的 （决裁查看）
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getAffirmSalaryCodeCnt(Object object);
	
	/**
	 *需要决裁的 （决裁页面）
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getAffirmSalaryCnt(Object object);
	
	/**
	 * 分页查找所有工资项目（基本项目，输入项目，计算项目）的信息列表   
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSalaryCodeList(Object object, int currentPage, int pageSize);
	
	
	/**
	 * 需要决裁的（决裁查看）
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmSalaryCodeList(Object object, int currentPage, int pageSize);
	
	/**
	 * 需要决裁的（决裁页面）
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmSalaryList(Object object, int currentPage, int pageSize);
	
	/**
	 * 决裁通过
	 */
	public int updateAffirmSalaryInfo(Object object) throws Exception;
	/**
	 * 没有被决裁的可以被删除
	 * @param object
	 * @throws Exception
	 */
	public void deleteAffirmSalaryInfo(Object object) throws Exception ;
	
	/**
	 * 查找单条记录的详细信息
	 * @param object
	 * @return
	 */
	public Object getSalaryCodeInfo(Object object);
	
	/**
	 * 获得信息申请序列(get information apply sequences)
	 * @param object
	 * @return
	 */
	public int getSalaryApplySeq() throws Exception;
	
	/**
	 * 查找所有工资项目参数（基本项目参数，输入项目参数，计算项目参数）的信息列表   不分页
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSalaryCodeMappingList(Object object);
	
	/**
	 * 分页查找所有工资项目参数（基本项目参数，输入项目参数，计算项目参数）的信息列表   
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSalaryCodeMappingList(Object object, int currentPage, int pageSize);
	
	/**
	 * 查找所有工资项目参数(基本项目参数，输入项目参数，计算项目参数)的信息的数量
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getSalaryCodeMappingCnt(Object object);

	/**
	 * 工资财务代码管理
	 */
	public List getPaItemList(Object object) ;
	
	public List viewPaItemList(Object object) ;
	
	@SuppressWarnings("unchecked")
	public int addPaItemInfo(Object object)throws Exception ;
	
	@SuppressWarnings("unchecked")
	public int deletePaItemInfo(Object object)throws Exception;

	/**
	 * 工资财务代码公式管理
	 */
	public List getPaItemFormulaList(Object object) ;
	
	public List viewPaItemFormulaList(Object object) ;
	
	@SuppressWarnings("unchecked")
	public int addPaItemFormulaInfo(Object object)throws Exception ;
	
	@SuppressWarnings("unchecked")
	public int deletePaItemFormulaInfo(Object object)throws Exception;
}

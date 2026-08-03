package com.ait.pa.service.salarycode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ait.web.util.ObjectBindUtil;

public interface salaryCodeSer {

	/**
	 * 查找所有工资项目（基本项目，输入项目，计算项目）的信息列表  
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSalaryCodeList(HttpServletRequest request) ;
	
	/**
	 * 需要决裁的（决裁查看）
	 * @param request
	 * @return
	 */
	public List getAffirmSalaryCodeList(HttpServletRequest request) ;
	
	
	/**
	 * 需要决裁的（决裁页面）
	 * @param request
	 * @return
	 */
	public List getAffirmSalaryList(HttpServletRequest request) ;
	
	/**
	 * 查找所有工资项目(基本项目，输入项目，计算项目)的信息的数量
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getSalaryCodeCnt(HttpServletRequest request);
	/**
	 * 需要决裁的（决裁查看）
	 * @param request
	 * @return
	 */
	public int getAffirmSalaryCodeCnt(HttpServletRequest request);
	
	/**
	 * 需要决裁的（决裁查看）决裁情况查看
	 * @param request
	 * @return
	 */
	public List getAffirmSalary(HttpServletRequest request);
	
	/**
	 * 需要决裁的（决裁页面）
	 * @param request
	 * @return
	 */
	public int getAffirmSalaryCnt(HttpServletRequest request);
	
	/**
	 * 查找单行记录的详细信息
	 * @param request
	 * @return
	 */
	public Object getSalaryCodeInfo(HttpServletRequest request) ;
	
	
	/**
	 * 决裁通过
	 */
	public int updateAffirmSalaryInfo(HttpServletRequest request);
	
	/**
	 * 没有决裁的申请记录可以被删除
	 */
	public int deleteAffirmSalaryInfo(HttpServletRequest request);
	
	/**
	 * 查找所有工资项目参数（基本项目参数，输入项目参数，计算项目参数）的信息列表  
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSalaryCodeMappingList(HttpServletRequest request) ;
	
	/**
	 * 查找所有工资项目参数(基本项目参数，输入项目参数，计算项目参数)的信息的数量
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getSalaryCodeMappingCnt(HttpServletRequest request);
	
	/**
	 * 工资财务代码管理
	 */
	public List getPaItemList(HttpServletRequest request) ;
	
	public List viewPaItemList(HttpServletRequest request) ;

	@SuppressWarnings("unchecked")
	public int addPaItemInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int deletePaItemInfo(HttpServletRequest request);
	
	/**
	 * 工资财务代码公式管理
	 */
	public List getPaItemFormulaList(HttpServletRequest request) ;
	
	public List viewPaItemFormulaList(HttpServletRequest request) ;

	@SuppressWarnings("unchecked")
	public int addPaItemFormulaInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int deletePaItemFormulaInfo(HttpServletRequest request);
}

package com.ait.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface AttendItemMappingSer {
	/**
	 * 查找所有考勤项目（明细项目和汇总项目）的和法人匹配的信息列表  
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAttendItemMappingList(HttpServletRequest request) ;
	
	/**
	 * 查找所有考勤项目（明细项目和汇总项目）的和法人匹配的信息列表   考勤代码查看页面
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAttendItemCheckMapList(HttpServletRequest request) ;
	
	/**
	 * 查找所有考勤项目(明细项目和汇总项目)的和法人匹配的信息的数量
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getAttendItemMappingCnt(HttpServletRequest request);
	
	/**
	 * 查找所有考勤项目(明细项目和汇总项目)的和法人匹配的信息的数量 考勤代码查看页面
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getAttendItemCheckMapCnt(HttpServletRequest request);
	
	/**
	 * 查找单行记录的详细信息
	 * @param request
	 * @return
	 */
	public Object getAttendItemMappingInfo(HttpServletRequest request) ;
	
	/**
	 * 考勤工资代码（新代码申请添加方法）
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public int addAffirmAttendItemInfo(HttpServletRequest request) throws Exception;
	
	/**
	 * 考勤工资代码（代码启用申请添加方法）
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public int addAffirmAttendInfo(HttpServletRequest request) throws Exception;
}

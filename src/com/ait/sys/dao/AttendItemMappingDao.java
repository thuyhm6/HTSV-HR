package com.ait.sys.dao;

import java.util.List;

public interface AttendItemMappingDao {
	/**
	 * 查找所有考勤项目（明细项目和汇总项目）的和法人匹配的信息列表   不分页
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAttendItemMappingList(Object object);
	
	/**
	 * 查找所有考勤项目（明细项目和汇总项目）的和法人匹配的信息列表   不分页   考勤代码查看页面
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAttendItemCheckMapList(Object object);
	
	/**
	 * 查找所有考勤项目(明细项目和汇总项目)的和法人匹配信息的数量
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getAttendItemMappingCnt(Object object);
	
	/**
	 * 查找所有考勤项目(明细项目和汇总项目)的和法人匹配信息的数量 考勤代码查看页面
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getAttendItemCheckMapCnt(Object object);
	
	/**
	 * 分页查找所有考勤项目（明细项目和汇总项目）的和法人匹配信息列表   
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAttendItemMappingList(Object object, int currentPage, int pageSize);
	
	/**
	 * 分页查找所有考勤项目（明细项目和汇总项目）的和法人匹配信息列表    考勤代码查看页面
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAttendItemCheckMapList(Object object, int currentPage, int pageSize);
	
	/**
	 * 查找单条记录的详细信息
	 * @param object
	 * @return
	 */
	public Object getAttendItemMappingInfo(Object object);
	
	/**
	 * 添加申请记录（考勤代码申请（新代码申请添加））
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	public int addAffirmAttendItemInfo(Object object) throws Exception;
	
	/**
	 * 添加申请记录（考勤代码申请（启用添加））
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	public int addAffirmAttendInfo(Object object) throws Exception;
	
	/***
	 * 根据权限信息查找审批人  有工资考勤代码担当的权限的人就是审批人
	 */
	public List findAfirmorByRelation() throws Exception;
}

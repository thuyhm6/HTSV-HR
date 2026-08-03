package com.ait.sys.dao;

import java.util.List;

public interface AttendItemDao {
	/**
	 * 查找所有考勤项目（明细计算，汇总项目）的信息列表   不分页
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAttendItemList(Object object);
	
	/**
	 * 需要决裁的（决裁查看 查看列表分法人）
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmAttendItemList(Object object);
	
	/**
	 * 需要决裁的（决裁列表  不分法人  总部的某个人决裁所有法人的申请）
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmAttendList(Object object);
	
	/**
	 * 查找所有考勤项目(明细计算，汇总项目)的信息的数量
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getAttendItemCnt(Object object);
	
	/**
	 *需要决裁的 （决裁查看  分法人的）
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getAffirmAttendItemCnt(Object object);
	
	/**
	 *需要决裁的 （进行决裁  不分法人  总部的某个人进行决裁  所有法人的申请）
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getAffirmAttendCnt(Object object);
	
	/**
	 * 分页查找所有考勤项目（明细计算，汇总项目）的信息列表   
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAttendItemList(Object object, int currentPage, int pageSize);
	
	
	/**
	 * 需要决裁的（决裁查看    分页   分法人）
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmAttendItemList(Object object, int currentPage, int pageSize);
	
	
	/**
	 * 需要决裁的（决裁页面    分页  不分法人  总部的人决裁所有法人提交的申请）
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmAttendList(Object object, int currentPage, int pageSize);
	
	/**
	 * 决裁通过
	 */
	public int updateAffirmAttendInfo(Object object) throws Exception;
	/**
	 * 没有被决裁的可以被删除（决裁查看页面）
	 * @param object
	 * @throws Exception
	 */
	public void deleteAffirmAttendItemInfo(Object object) throws Exception ;
	
	/**
	 * 查找单条记录的详细信息
	 * @param object
	 * @return
	 */
	public Object getAttendItemInfo(Object object);
}

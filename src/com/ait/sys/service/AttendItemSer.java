package com.ait.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface AttendItemSer {
	/**
	 * 查找所有考勤项目（明细项目，汇总项目）的信息列表  
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAttendItemList(HttpServletRequest request) ;
	/**
	 * 需要决裁的（决裁查看列表）
	 * @param request
	 * @return
	 */
	public List getAffirmAttendItemList(HttpServletRequest request) ;
	
	/**
	 * 需要决裁的（决裁列表）
	 * @param request
	 * @return
	 */
	public List getAffirmAttendList(HttpServletRequest request) ;
	
	/**
	 * 查找所有考勤项目(明细项目，汇总项目)的信息的数量
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getAttendItemCnt(HttpServletRequest request);
	/**
	 * 需要决裁的（决裁查看列表   分法人）
	 * @param request
	 * @return
	 */
	public int getAffirmAttendItemCnt(HttpServletRequest request);
	
	/**
	 * 需要决裁的（决裁列表（不分法人））
	 * @param request
	 * @return
	 */
	public int getAffirmAttendCnt(HttpServletRequest request);
	
	/**
	 * 查找单行记录的详细信息
	 * @param request
	 * @return
	 */
	public Object getAttendItemInfo(HttpServletRequest request) ;
	
	/**
	 * 决裁通过
	 */
	public int updateAffirmAttendInfo(HttpServletRequest request);
	
	/**
	 * 没有决裁的申请记录可以被删除（决裁查看页面）
	 */
	public int deleteAffirmAttendItemInfo(HttpServletRequest request);
}

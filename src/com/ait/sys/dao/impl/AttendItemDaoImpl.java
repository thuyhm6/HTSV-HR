package com.ait.sys.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.sys.dao.AttendItemDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class AttendItemDaoImpl extends SqlMapClientSupport implements AttendItemDao{
	
	@Autowired
	private SyLanguageDao syLanguageDao;
	
	/**
	 * 查找所有考勤项目（明细计算，汇总项目）的信息列表   不分页
	 * @param object
	 * @return
	 */
	@Override
	public List getAttendItemList(Object object) {
        List returnList = new ArrayList() ;
		returnList = this.getAttendItemList(object, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 需要决裁的（决裁查看页面   不分页  分法人）
	 */
	@Override
	public List getAffirmAttendItemList(Object object) {
        List returnList = new ArrayList() ;
		returnList = this.getAffirmAttendItemList(object, -1, -1) ;
		return returnList ;
	}

	/**
	 * 查找所有工资项目(基本项目，输入项目，计算项目)的信息的数量
	 * @param object
	 * @return
	 */
	@Override
	public int getAttendItemCnt(Object object) {
        int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.attendancesetting.getAttendItemMapingCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 需要决裁的（决裁查看页面  ）
	 */
	@Override
	public int getAffirmAttendItemCnt(Object object) {
        int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.attendancesetting.getAffirmAttendItemCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}

	/**
	 * 分页查找所有考勤项目（明细项目，汇总项目）的信息列表   
	 * @param object
	 * @return
	 */
	@Override
	public List getAttendItemList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.attendancesetting.getAttendItemMapingList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("sys.attendancesetting.getAttendItemMapingList", object);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	
	/**
	 * 需要决裁的（决裁查看也页面的分页查找  分法人）
	 */
	@Override
	public List getAffirmAttendItemList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.attendancesetting.getAffirmAttendItemList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("sys.attendancesetting.getAffirmAttendItemList", object);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/**
	 * 查找单行记录的详细信息
	 */
	@Override
	public Object getAttendItemInfo(Object object) {
	    LinkedHashMap returnObj = new LinkedHashMap() ;
		List returnList = this.getAttendItemList(object) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		return returnObj ;
	}

	
	/**
	 * 需要决裁的（决裁页面  不分页   不分法人   总部的某个人有决裁权限的人能看到的页面）
	 */
	@Override
	public List getAffirmAttendList(Object object) {
		List returnList = new ArrayList() ;
		returnList = this.getAffirmAttendList(object, -1, -1) ;
		return returnList ;
	}

	
	/**
	 * 需要决裁的（决裁页面 ）
	 */
	@Override
	public int getAffirmAttendCnt(Object object) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.attendancesetting.getAffirmAttendCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}

	
	/**
	 * 需要决裁的（决裁页面  分页   不分法人   总部的某个人有决裁权限的人能看到的页面）
	 */
	@Override
	public List getAffirmAttendList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.attendancesetting.getAffirmAttendList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("sys.attendancesetting.getAffirmAttendList", object);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	
	/**
	 * 需要决裁的（决裁页面进行决裁）
	 */
	@Override
	public int updateAffirmAttendInfo(Object object) throws Exception {
		try {
			this.update("sys.attendancesetting.updateAffirmAttendInfo", object) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 没有被决裁的可以删除（决裁查看页面）
	 */
	@Override
	public void deleteAffirmAttendItemInfo(Object object) throws Exception {
		try {
			this.syLanguageDao.deleteSyGlobalName(object);
			this.delete("sys.attendancesetting.deleteAffirmAttendItemInfo", object) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}

}

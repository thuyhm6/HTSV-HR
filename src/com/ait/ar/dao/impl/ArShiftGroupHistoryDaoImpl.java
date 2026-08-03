package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.ArShiftGroupHistoryDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class ArShiftGroupHistoryDaoImpl extends SqlMapClientSupport implements ArShiftGroupHistoryDao {
	
	/**
	 * 取得人员信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArBaseEmpInfoList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
				returnList = this.queryForList("ar.arShiftGroupManagement.getArBaseEmpInfoList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 取得人员信息总数
	 * @param List
	 * @return
	 */
	public int getArBaseEmpInfoCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.arShiftGroupManagement.getArBaseEmpInfoCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 取得人员详细信息
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getArBaseEmpInfoDetail(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		try {
			returnObj = (LinkedHashMap)this.queryForObject("ar.arShiftGroupManagement.getArBaseEmpInfoDetail",obj);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return returnObj ;
	}
	
	/**
	 * 取得人员班组详细信息
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getArEmpShiftGroupFinalInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		try {
			returnObj = (LinkedHashMap)this.queryForObject("ar.arShiftGroupManagement.getArEmpShiftGroupFinalInfo",obj);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnObj ;
	}
	
	@SuppressWarnings("unchecked")
	public int updateArBaseEmpInfoDetail(Object object)throws Exception {
        this.update("ar.arShiftGroupManagement.updateArBaseEmpInfoDetail",object);
		return 1;
	}
	
	/**
	 * 班组履历的查询
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArShiftRecordCheckList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
				returnList = this.queryForList("ar.arShiftGroupManagement.getArShiftRecordCheckList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 取得班组履历总数
	 * @param List
	 * @return
	 */
	public int getArShiftRecordCheckListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.arShiftGroupManagement.getArShiftRecordCheckListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 取得班组人员详细信息
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getArShiftRecordCheckInfoDetail(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.arShiftGroupManagement.getArShiftRecordCheckInfoDetail",obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 班组的月别列表查询
	 */
	@SuppressWarnings("unchecked")
	public List getArClassCalendarListForNormalShift(Object object,int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.arShiftGroupManagement.getArClassCalendarListForNormalShift", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
		
	@SuppressWarnings("unchecked")
	public int getArShiftMonthCheckListViewHtmlCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.arShiftGroupManagement.getArShiftMonthCheckListViewHtmlCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	@Override
	public List getArClassCalendarList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.arShiftGroupManagement.getArClassCalendarList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

}

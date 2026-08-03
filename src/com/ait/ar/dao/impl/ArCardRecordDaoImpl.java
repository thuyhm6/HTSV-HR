package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.ArCardRecordDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class ArCardRecordDaoImpl extends SqlMapClientSupport implements
		ArCardRecordDao {

	@SuppressWarnings("unchecked")
	public List getArCardRecordList(Object object) {
		// TODO Auto-generated method stub
		List returnList = null;
		try {
			returnList = this.queryForList(
					"ar.arCardRecord.getArCardRecordList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getArCardRecordForSelfList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getArCardRecordForSelfList(object, -1, -1);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getArCardRecordList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ar.arCardRecord.getArCardRecordList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ar.arCardRecord.getArCardRecordList", obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	
	public List getArCardRecordDayList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
						"ar.arCardRecord.getArCardRecordDayList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	
	public List getArCardRecordCommon(Object obj, String target) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.arCardRecord." +target, obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getArCardRecordForSelfList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ar.arCardRecord.getArCardRecordForSelfList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ar.arCardRecord.getArCardRecordForSelfList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public int getArCardRecordListCnt(Object object) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ar.arCardRecord.getArCardRecordListCnt",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	
	@SuppressWarnings("unchecked")
	public int getArCardRecordListForSelfCnt(Object object) {
		int returnInt = 0;

		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ar.arCardRecord.getArCardRecordListForSelfCnt",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}

	/**
	 * 更新刷卡信息(update ArCardRecord Info)
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void updateArCardRecordInfo(Object object) throws Exception {

		this.update("ar.arCardRecord.updateArCardRecordInfo", object);
	}

	/**
	 * 删除刷卡信息(delete ArCardRecord Info)
	 * @param List
	 * @return 
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public void deleteArCardRecordInfo(List list) throws Exception {

		this.deleteForList("ar.arCardRecord.deleteArCardRecordInfo", list);
	}
	
	@SuppressWarnings("unchecked")
	public void deleteArCardInfoCommon(List list, String target) throws Exception {

		this.deleteForList("ar.arCardRecord."+target, list);
	}
	
	@SuppressWarnings("unchecked")
	public void addArShiftChangeForPreDeleteInfo(List list) throws Exception {

		this.insertForList("ar.arCardRecord.addArShiftChangeForPreDeleteInfo", list);
	}
   
	
	/**
	 * 添加刷卡数据(add ArCardRecord Info)
	 * 
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public void addArCardRecordInfo(Object object) throws Exception {

		this.insert("ar.arCardRecord.deleteArCardRepeat", object);
		
		this.insert("ar.arCardRecord.addArCardRecordInfo", object);
	}
	
	
	

	/**
	 * 添加刷卡数据后插入时候申请
	 * 
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public void addArShiftChangeInfo(Object object) throws Exception {

	 
		this.insert("ar.arCardRecord.addArShiftChangeInfo", object);
	}
	
	/**
	 * 取得刷卡数据
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getArCardRecordInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getArCardRecordList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 取得刷卡数据
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getArCardRecordInfoS(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = null;
		try {
			returnList = this.queryForList(
					"ar.arCardRecord.getArCardRecordListS", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	@SuppressWarnings("unchecked")
	public List getArCardRecordMealList(Object object) {
		// TODO Auto-generated method stub
		List returnList = null;
		try {
			returnList = this.queryForList(
					"ar.arCardRecord.getArCardRecordMealList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
}

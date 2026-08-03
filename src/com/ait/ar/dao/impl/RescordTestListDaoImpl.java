package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.RecordTestListDao;
import com.ait.web.util.SqlMapClientSupport;
@Repository
public class RescordTestListDaoImpl extends SqlMapClientSupport implements RecordTestListDao{
	
	
	/**
	 * 取得所有打卡记录
	 * @param Object
	 * @param int
	 * @param int
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getRecordTestList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.recordTestList.getRecordTestList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ar.recordTestList.getRecordTestList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/**
	 * 取得所有打卡记录数量
	 * @param List
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int getRecordTestCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.recordTestList.getRecordTestCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	
	/**
	 * 取得所有打卡记录
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getRecordTestList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getRecordTestList(obj, -1, -1) ;
		
		return returnList ;
	}
}

package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.ArAdjustRestDao;
import com.ait.ar.dao.ArAnnualLeaveDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArAdjustRestDaoImpl.java
 * @Description:
 * @Create date: 2012-3-23 下午04:34:01
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Repository
public class ArAdjustRestDaoImpl extends SqlMapClientSupport implements ArAdjustRestDao {
	
	/**
	 * 取得个人年假信息
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getArAdjustRestInfo(Object obj) {
		
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		try {
			
			returnObj = (LinkedHashMap)this.queryForObject("ar.adjustRest.getArAdjustRestInfo", obj);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnObj;
	}
	
	/**
	 * 取得所有个人年假列表(get ArAnnualLeave List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getArAdjustRestList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getArAdjustRestList(obj, -1, -1) ; 
		
		return returnList ;
	}
	
	/**
	 * 取得个人年假计数(get ArAnnualLeave Cnt)
	 * @param Object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int getArAdjustRestCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.adjustRest.getArAdjustRestCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 取得所有个人年假列表(get ArAnnualLeave List)
	 * @param obj
	 * @param int
	 * @param int
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getArAdjustRestList(Object obj, int currentPage, int pageSize) {
		
		List returnList = new ArrayList() ;
		
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.adjustRest.getArAdjustRestList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("ar.adjustRest.getArAdjustRestList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
}

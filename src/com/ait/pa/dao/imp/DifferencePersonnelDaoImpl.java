package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.DifferencePersonnelDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class DifferencePersonnelDaoImpl extends SqlMapClientSupport implements DifferencePersonnelDao {
	
	/**
	 * 取得所有计算补差人员信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getDifferencePersonnelInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getDifferencePersonnelList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 取得所有计算补差人员信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getDifferencePersonnelList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getDifferencePersonnelList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 取得所有计算补差人员信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getDifferencePersonnelList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.differencePersonnel.getDifferencePersonnelList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.differencePersonnel.getDifferencePersonnelList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有计算补差信息总数
	 * @param List
	 * @return
	 */
	public int getDifferencePersonnelCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.differencePersonnel.getDifferencePersonnelCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}

	/**
	 * 插入计算补差人员信息
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addDifferencePersonnelInfo(List list) {
		
		try {
			this.insertForList("pa.differencePersonnel.addDifferencePersonnelInfo", list) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 删除计算补差人员信息
	 * @param List
	 * @return
	 */
	@Override
	public int deleteDifferencePersonnelInfo(Object obj) {
		
		try {
			this.delete("pa.differencePersonnel.deleteDifferencePersonnelInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
}

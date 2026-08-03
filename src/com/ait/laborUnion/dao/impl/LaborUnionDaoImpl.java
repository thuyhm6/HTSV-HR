package com.ait.laborUnion.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ait.hrm.dao.ContractInfoDao;
import com.ait.laborUnion.dao.LaborUnionInfoDao;
import com.ait.web.util.SqlMapClientSupport;
@Repository
public class LaborUnionDaoImpl extends SqlMapClientSupport implements LaborUnionInfoDao{
    
	/**
	 * 工会查询 (Contract inquires)
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getLaborUnionSumForSearch(Object obj) {
		    Object obj1 = null;
			try {				
				obj1 = this.queryForList("LaborUnion.contractInfo.getLaborUnionSumForSearch", obj);
								
			} catch (SQLException e) {			
				e.printStackTrace();
			}
			return obj1 ;
	}	
	
	
	/**
	 * 工会上月查询 (Contract inquires)
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getLaborUnionSumForSearch2(Object obj) {
		    Object obj1 = null;
			try {				
				obj1 = this.queryForList("LaborUnion.contractInfo.getLaborUnionSumForSearch2", obj);
								
			} catch (SQLException e) {			
				e.printStackTrace();
			}
			return obj1 ;
	}	
	
	
	
	
	/**
	 * 工会excel (Contract inquires)
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object getLaborUnionExcel(Object obj) {
		    Object obj1 = null;
			try {				
				obj1 = this.queryForList("LaborUnion.contractInfo.getLaborUnionExcel", obj);
								
			} catch (SQLException e) {			
				e.printStackTrace();
			}
			return obj1 ;
	}
	
	
	
	/**
	 * 学生查询CNC (Contract inquires)
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @throws Exception
	 */
	@Override
	public int getLaborUnionCnt(Object obj) {
			int returnCNC = 0;
			try{ 
				returnCNC=NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("LaborUnion.contractInfo.getLaborUnionCnt", obj)), Integer.class) ;
				
			   } catch (SQLException e) {			
				e.printStackTrace();
			 }
			return returnCNC ;
	}
}

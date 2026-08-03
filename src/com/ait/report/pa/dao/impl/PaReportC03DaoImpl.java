package com.ait.report.pa.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.report.pa.dao.PaReportC03Dao;
import com.ait.web.util.SqlMapClientSupport;
@Repository
public class PaReportC03DaoImpl extends SqlMapClientSupport  implements PaReportC03Dao {

	/**
	 * 导出给予现状汇总报表
	 * @param object
	 * @return list
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPacurrentRenditionCollectList(Object object){
		List returnList = new ArrayList() ;
		try {			
				returnList = this.queryForList("report.pac03.getPaCurrentRenditionSumAvg", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPacurrentRenditionCollectList2(Object object){
		List returnList = new ArrayList() ;
		try {			
				returnList = this.queryForList("report.pac03.getPacurrentRenditionCollectList2", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPacurrentRenditionCollectList3(Object object){
		List returnList = new ArrayList() ;
		try {			
				returnList = this.queryForList("report.pac03.getPacurrentRenditionCollectList3", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPacurrentRenditionCollectList4(Object object){
		List returnList = new ArrayList() ;
		try {			
				returnList = this.queryForList("report.pac03.getPacurrentRenditionCollectList4", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPacurrentRenditionCollectList5(Object object){
		List returnList = new ArrayList() ;
		try {			
				returnList = this.queryForList("report.pac03.getPacurrentRenditionCollectList5", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 导出给予现状报表(按员工)
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaCurrentRenditionList(Object object) {
		List returnList = new ArrayList() ;
		try {			
			returnList = this.queryForList("report.pac03.getPaCurrentRenditionList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 导出给予现状报表(求和与求平均)
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaCurrentRenditionSumAvg(Object object) {
		List returnList = new ArrayList() ;
		try {			
			returnList = this.queryForList("report.pac03.getPaCurrentRenditionSumAvg", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	
	
	public int getEmpCnt1(Object obj) {
     int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("report.pac03.empCount1", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	public int getEmpCnt2(Object obj) {
	     int returnInt = 0 ;
			
			try {
				returnInt = 
					NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("report.pac03.empCount2", obj)), Integer.class) ;
			} catch (SQLException e) {			
				e.printStackTrace();
			}
			
			return returnInt ;
		}
	
	public int getEmpCnt3(Object obj) {
	     int returnInt = 0 ;
			
			try {
				returnInt = 
					NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("report.pac03.empCount3", obj)), Integer.class) ;
			} catch (SQLException e) {			
				e.printStackTrace();
			}
			
			return returnInt ;
		}


	public int getEmpCnt4(Object obj) {
	     int returnInt = 0 ;
			
			try {
				returnInt = 
					NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("report.pac03.empCount4", obj)), Integer.class) ;
			} catch (SQLException e) {			
				e.printStackTrace();
			}
			
			return returnInt ;
		}


	/**
	 * 导出给予现状汇总报表
	 * @param object
	 * @return list
	 */
	
	@Override
	@SuppressWarnings("unchecked")
	public List getPaHourWorkInfo(LinkedHashMap paramMap) {
		List returnList = new ArrayList() ;
		try {			
			returnList = this.queryForList("report.pac03.getPaHourWorkInfo", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<?> getPaHourWorkInfoByDeptNo(LinkedHashMap paramMap) {
		List returnList = new ArrayList() ;
		try {			
			returnList = this.queryForList("report.pac03.getPaHourWorkInfoByDeptNo", paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<?> getSumPaHourWorkInfo(LinkedHashMap paramMap) {
		List returnList = new ArrayList() ;
		try {			
			returnList = this.queryForList("report.pac03.getSumPaHourWorkInfo", paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
}

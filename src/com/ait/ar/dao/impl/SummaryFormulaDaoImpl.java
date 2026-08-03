package com.ait.ar.dao.impl;

import bsh.This;

import com.ait.ar.dao.SummaryFormulaDao;
import com.ait.web.util.SqlMapClientSupport;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: SummaryFormulaDaoImpl.java
 * @Description:
 * @Create date: 2012-1-13 下午05:11:26
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Repository
public class SummaryFormulaDaoImpl extends SqlMapClientSupport implements SummaryFormulaDao {

	/**
	 * 取汇总公式列表(get SummaryFormula Item List)
	 * @param Object
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getSummaryFormulaItemList(Object obj) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.sqlSummaryFormula.getSummaryFormulaItemList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/**
	 * 取汇总公式明细列表(get summaryFormula List)
	 * @param Object
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getsummaryFormulaList(Object obj) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.sqlSummaryFormula.getsummaryFormulaList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取汇总公式明细(get SummaryFormula ToCN)
	 * @param
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getSummaryFormulaItemToCN( ) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList =   this.queryForList ("ar.sqlSummaryFormula.getSummaryFormulaItemToCN");
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取汇总公式明细(get SummaryFormula ToCN)
	 * @param
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getSummaryFormulaItemToInter(Object obj) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList ("ar.sqlSummaryFormula.getSummaryFormulaItemToInter",obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getSummaryFormulaAR_STA_ITEM( ) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList =   this.queryForList ("ar.sqlSummaryFormula.getSummaryFormulaAR_STA_ITEM");
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取个人信息列表(get PersonBasic Info)
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getPersonBasicInfo(Object paramMap) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList ("ar.sqlSummaryFormula.getPersonBasicInfo",paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取项目列表(get Item List)
	 * @param Object
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getItemList(Object paramMap) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList ("ar.sqlSummaryFormula.getArItemList",paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	@SuppressWarnings("unchecked")
	public int addFormulaItem(Object obj) {
		
		try {
			
			this.insert("ar.sqlSummaryFormula.addFormulaItem", obj) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	@SuppressWarnings("unchecked")
	public int updateFormulaItem(Object obj) {
		
		try {
			
			this.update("ar.sqlSummaryFormula.updateFormulaItem", obj) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	@SuppressWarnings("unchecked")
	public int deleteFormulaInfo(Object obj) {
		
		try {
			
			this.update("ar.sqlSummaryFormula.deleteFormulaInfo", obj) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

	@SuppressWarnings("unchecked")
	public Object getFormulaInfo(Object object) {
		// TODO Auto-generated method stub
		Object returnObj=null;
		try {
		 returnObj =this.queryForObject("ar.sqlSummaryFormula.getFormulaInfo", object) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnObj ;
	}
}

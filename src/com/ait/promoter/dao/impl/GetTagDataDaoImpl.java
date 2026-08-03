package com.ait.promoter.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.ait.promoter.dao.GetTagDataDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:    LG CNS
 * Company:      LG CNS
 * @fileName:    GetTagDataDaoImpl.java
 * @Create date: 2014.06.11
 * @Create by:   CH.W.G
 * @version 1.0
 */
@Repository
public class GetTagDataDaoImpl extends SqlMapClientSupport implements GetTagDataDao {
	
	/**
	 * 取得State List
	 */
	@SuppressWarnings({ "rawtypes" })
	public List getStateList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("promoter.getState", obj,  currentPage,  pageSize);
			}else{
				returnList = this.queryForList("promoter.getStateList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 取得State List
	 */
	@SuppressWarnings({ "rawtypes" })
	public List getShengList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			  returnList = this.queryForList("promoter.getShengList", obj);
			 
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 取得State List
	 */
	@SuppressWarnings({ "rawtypes" })
	public List getStateList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("promoter.getStateList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	/**
	 * 取得City List
	 */
	@SuppressWarnings({ "rawtypes" })
	public List getCityByStateList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("promoter.getCityByStateList", obj,  currentPage,  pageSize);
			}else{
				returnList = this.queryForList("promoter.getCityByStateList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 取得City List
	 */
	@SuppressWarnings({ "rawtypes" })
	public List getCity2ByStateList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			 
				returnList = this.queryForList("promoter.getCity2ByStateList", obj);
			 
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 取得City List
	 */
	@SuppressWarnings({ "rawtypes" })
	public List getCityByStateList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("promoter.getCityByStateList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	/**
	 * 取得Region List
	 */
	@SuppressWarnings({ "rawtypes" })
	public List getRegionByCityList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("promoter.getRegionByCityList", obj,  currentPage,  pageSize);
			}else{
				returnList = this.queryForList("promoter.getRegionByCityList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 派遣地取得Region List by:wang qiang
	 */
	@SuppressWarnings({ "rawtypes" })
	public List getRegionByCityTwoList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
		 
				returnList = this.queryForList("promoter.getRegionByCityTwoList", obj);
			 
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 取得Region List
	 */
	@SuppressWarnings({ "rawtypes" })
	public List getRegionByCityList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("promoter.getRegionByCityList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	/**
	 * 取得支社 List
	 */
	@SuppressWarnings({ "rawtypes" })
	public List getBranchList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("promoter.getBranchList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
}

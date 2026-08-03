package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.SummaryItemDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: SummaryItemDaoImpl.java
 * @Description:
 * @Create date: 2012-1-13 上午10:09:07
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Repository
public class SummaryItemDaoImpl extends SqlMapClientSupport implements SummaryItemDao {
	
	@Autowired
	private SyLanguageDao syLanguageDao;
	
	/**
	 * 取得汇总项目信息(get Summary Item)
	 * @param Object
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public Object getSummaryItem(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getSummaryItemList(obj) ;
		if (returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 取得所有汇总项目列表(get SummaryItem List)
	 * @param obj
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getSummaryItemList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getSummaryItemList(obj, -1, -1) ; 
		
		return returnList ;
	}
	
	/**
	 * 取得所有除当前法人的 汇总项目列表(get SummaryItem List)
	 * @param obj
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getSummaryItemByCpnyList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getSummaryItemByCpnyList(obj, -1, -1) ; 
		
		return returnList ;
	}
	
	/**
	 * 取得汇总项目列表(get SummaryItem List)
	 * @param obj
	 * @param int
	 * @param int
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getSummaryItemList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.summaryItem.getSummaryItemList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("ar.summaryItem.getSummaryItemList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得除当前法人的 汇总项目列表(get SummaryItem List)
	 * @param obj
	 * @param int
	 * @param int
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getSummaryItemByCpnyList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.summaryItem.getSummaryItemByCpnyList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("ar.summaryItem.getSummaryItemByCpnyList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	/**
	 * 插入汇总项目信息(add SummaryItem Info)
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void addSummaryItemInfo(Object obj) throws Exception {
		
		LinkedHashMap object = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);
		
		this.insert("ar.summaryItem.addSummaryItemInfo", object) ;
		
	}
	
	/**
	 * 插入汇总项目信息(add SummaryItem Info)
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void addSummaryItemInfoAffirm(Object obj) throws Exception {
		
		this.insert("ar.summaryItem.addSummaryItemInfoAffirm", obj) ;
		
	}
	
	/**
	 * 更新汇总项目信息(update SummaryItem Info)
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void updateSummaryItemInfo(Object obj) throws Exception {
		
		this.syLanguageDao.updateSyGlobalName(obj);
		
		this.update("ar.summaryItem.updateSummaryItemInfo", obj);
		
	}
	
	/**
	 * 删除汇总项目信息(delete SummaryItem Info)
	 * @param Object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public void deleteSummaryItemInfo(Object obj) throws Exception {
		
		//this.syLanguageDao.deleteSyGlobalName(obj);
		
		//this.delete("ar.summaryItem.deleteSummaryItemInfo", obj);
		this.delete("ar.summaryItem.updateSummaryItemActivityInfo", obj);
		
	}
	
	/**
	 * 检查 汇总项目信息(check SummaryItem Info)
	 * @param Object
	 * @return
	 */
	@Override
	public int checkSummaryItemInfo(Object object) {
		int returnInt = 0;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.summaryItem.checkSummaryItemInfo", object)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
			return returnInt = 1;
		}
		
		return returnInt;
	}

	/**
	 * 调整参数顺序(update SummaryItemInfo CalOrder)
	 * @param List
	 * @return int
	 * @throws
	 */	
	@Override
	public int updateSummaryParamItemInfoCalOrder(List<LinkedHashMap> collection) {
		int returnInt = 1;
		
		try {
			 
			this.updateForList("ar.summaryItem.updateSummaryParamItemInfoCalOrder", collection);
			
		} catch (SQLException e) {			
			e.printStackTrace();
			returnInt = 0;
		}
		
		return returnInt ;
	}
	@Override
	public int getItemCnt(Map obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.summaryItem.getSummaryItemCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 汇总项目参数列表(get SummaryParamItem List)
	 * @param Map
	 * @param int
	 * @param int
	 * @return List
	 * @throws
	 */
	@Override
	public List getSummaryParamItemList(Map paramMap, int currentPage,
			int pageSize) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.summaryItem.getSummaryParamItemList", paramMap, currentPage, pageSize);
			}else{
				returnList = this.queryForList("ar.summaryItem.getSummaryParamItemList", paramMap);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 汇总项目参数列表(get SummaryParamItem List)
	 * @param Map
	 * @return List
	 * @throws
	 */
	@Override
	public List getSummaryParamItemList(Map paramMap) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		
		returnList = this.getSummaryParamItemList(paramMap, -1, -1) ; 
		
		return returnList ;
	}
	
	/**
	 * 汇总项目参数列表(get SummaryParamItem List)
	 * @param Map
	 * @return List
	 * @throws
	 */
	@Override
	public List getSummaryParamItemList1(Map paramMap) {
		// TODO Auto-generated method stub
		
		List returnList = new ArrayList() ;
		
		try {

			returnList = this.queryForList("ar.summaryItem.getSummaryParamItemList", paramMap);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 汇总项目参数条数(get ItemParam count)
	 * @param Map
	 * @return int
	 * @throws
	 */
	@Override
	public int getItemParamCnt(Map paramMap) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.summaryItem.getSummaryParamItemCnt", paramMap)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 添加汇总项目参数(add SummaryParamItem Info)
	 * @param LinkedHashMap
	 * @return int
	 * @throws
	 */
	@Override
	public void addSummaryParamItemInfo(LinkedHashMap paramMap) throws Exception {

		this.insert("ar.summaryItem.addSummaryParamItemInfo", paramMap);
		
	}
	
	@Override
	public void updateSummaryParamItem(LinkedHashMap paramMap) throws Exception {

		this.update("ar.summaryItem.updateSummaryParamItem", paramMap);
		
	}
	@Override
	public Object getSummaryParamItem(Map paramMap) {
		// TODO Auto-generated method stub
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getSummaryParamItemList(paramMap);
		if (returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 更新汇总项目参数
	 * @param List
	 * @return
	 */
	@Override
	public void updateSummaryParamItemInfo(LinkedHashMap paramMap) throws Exception {

		this.update("ar.summaryItem.updateSummaryParamItemInfo", paramMap) ;
		
	}
	
	
	@Override
	public void addShowSummaryParamItemList(Map paramMap) throws Exception {

		this.update("ar.summaryItem.updateShowSummaryParamItem", paramMap) ;
		
	}
	/**
	 * 删除汇总项目参数(delete SummaryParamItem Info)
	 * @param request
	 * @return
	 * @throws
	 */
	@Override
	public void deleteSummaryParamItemInfo(LinkedHashMap paramMap) throws Exception {

		//this.delete("ar.summaryItem.deleteSummaryParamItemInfo", paramMap);
		this.delete("ar.summaryItem.updateSummaryParamItemActivityInfo", paramMap);
		
	}
	
	/**
	 * 检查删除汇总项目(check For ItemDelete)
	 * @param paramMap
	 * @return int
	 * @throws
	 */
	@Override
	public int checkForItemDelete(LinkedHashMap paramMap) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.summaryItem.checkForItemDelete", paramMap)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
			returnInt = 1;
		}
		
		return returnInt;
	}
	
	/**
	 * 检查删除汇总项目参数(check For ItemDelete)
	 * @param LinkedHashMap
	 * @return int
	 * @throws
	 */
	@Override
	public int checkForItemParamDelete(LinkedHashMap paramMap) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.summaryItem.checkForItemParamDelete", paramMap)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
			returnInt = 1;
		}
		
		return returnInt;
	}
	
	/**
	 * 检查汇总项目参数(check For ItemParam Unique)
	 * @param request
	 * @return int
	 * @throws
	 */
	@Override
	public int checkForItemParamUnique(LinkedHashMap paramMap) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.summaryItem.checkForItemParamUnique", paramMap)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
			returnInt = 1;
		}
		
		return returnInt;
	}
	
	@Override
	public int checkForItemParam(LinkedHashMap paramMap) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.summaryItem.checkForItemParam", paramMap)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
			returnInt = 1;
		}
		
		return returnInt;
	}
	
	
	/**
	 * 根据item_no删除所有记录
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updateSummaryItemParamInfoAll(String item_no) throws Exception{
		try {
		    this.update("ar.summaryItem.updateSummaryItemParamInfoAll", item_no) ;
		    return 1;
	    } catch (RuntimeException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		    return 0;
	    }
	}
}

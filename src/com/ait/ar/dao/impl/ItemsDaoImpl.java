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

import com.ait.ar.dao.ItemsDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class ItemsDaoImpl extends SqlMapClientSupport implements ItemsDao {
	
	@Autowired
	private SyLanguageDao syLanguageDao;
	
	/**
	 * 取得明细项目信息
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getItem(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;

		List returnList = this.getItemList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 取得所有明细项目(get Item List)
	 * @param List
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getItemList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getItemList(obj, -1, -1) ; 
		
		return returnList ;
	}
	
	/**
	 * 取得所有明细项目(get Item List)添加法人参数时候的列表
	 * @param List
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getItemListSelect(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.item.getItemListSelect", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	
	/**
	 * 调整参数顺序(update ItemInfo CalOrder)
	 * @param List
	 * @return int
	 * @throws
	 */	
	@Override
	public int updateParamItemInfoCalOrder(List<LinkedHashMap> collection) {
		int returnInt = 1;
		
		try {
			 
			this.updateForList("ar.item.updateParamItemInfoCalOrder", collection);
			
		} catch (SQLException e) {			
			e.printStackTrace();
			returnInt = 0;
		}
		
		return returnInt ;
	}
	
	@Override
	public int getItemCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.item.getItemCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 取得所有明细(get Item List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getItemList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.item.getItemList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ar.item.getItemList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 插入明细项目信息(add Item Info)
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void addItemInfo(Object obj)throws Exception {
		
		LinkedHashMap object = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);
		
		this.insert("ar.item.addItemInfo", object);
		
	}
	
	@SuppressWarnings("unchecked")
	public void addItemInfoAffirm(Object object)throws Exception{
		this.insert("ar.item.addItemInfoAffirm", object);
	}
	
	/**
	 * 更新明细项目信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void updateItemInfo(Object obj)throws Exception {
		
		this.syLanguageDao.updateSyGlobalName(obj);
		
		this.update("ar.item.updateItemInfo", obj) ;
		
	}
	
	/**
	 * 删除明细项目信息(delete Item Info)
	 * @param Object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public void deleteItemInfo(Object obj) throws Exception {
		
		this.syLanguageDao.deleteSyGlobalName(obj);
		
		this.delete("ar.item.deleteItemInfo", obj) ;
		
	}
	
	/**
	 * 取出明细项目参数信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getItemParameterList(Object object){
		
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.item.getItemParameterList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	
	/**
	 * 取出明细项目参数信息  法人匹配页面
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getItemParamList(Object object){
		
		List returnList = new ArrayList() ;
		returnList = this.getItemParamList(object, -1, -1);
		
		return returnList ;
	}
	/**
	 * 取出明细项目参数信息  法人匹配页面
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getItemParamList2(Object object){
		
		List returnList = new ArrayList() ;
		returnList = this.getItemParamList2(object, -1, -1);
		
		return returnList ;
	}
	/**
	 * 取出明细项目参数信息  法人匹配页面
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getItemParamListForKaoqin(Object object){
		
		List returnList = new ArrayList() ;
		returnList = this.getItemParamListForKaoqin(object, -1, -1);
		
		return returnList ;
	}
	
	@Override
	public int getItemParamCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.item.getItemParamCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	@Override
	public int getItemParamCnt() {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.item.getItemParamCnt")), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 取得所有明细(get Item List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getItemParamList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.item.getItemParamList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ar.item.getItemParamList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	/**
	 * 取得所有明细(get Item List)
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getItemParamList2(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.item.getItemParamList2", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ar.item.getItemParamList2", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	@SuppressWarnings("unchecked")
	public List getItemParamListForKaoqin(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.item.getItemParamListForKaoqin", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ar.item.getItemParamListForKaoqin", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取出明细项目参数信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getItemParameter(Object object){
		
		Object returnObject = new Object() ;
		try {
			List returnList = this.queryForList("ar.item.getItemParameterList", object);
			
			if (returnList != null && returnList.size() > 0){
				returnObject = returnList.get(0) ;
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnObject ;
	}
	
	/**
	 * 插入明细项目参数信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void addItemParameterInfo(Object obj) throws Exception {
		
		this.insert("ar.item.addItemParameterInfo", obj) ;
		
	}
	
	@SuppressWarnings("unchecked")
	public void updateItemParameter(Object obj) throws Exception {
		
		this.update("ar.item.updateItemParameter", obj) ;
		
	}
	
	/**
	 * 修改明细项目参数信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void updateItemParameterInfo(Object obj) throws Exception {
		
		this.update("ar.item.updateItemParameterInfo", obj) ;
		
	}
	
	/**
	 * 删除明细项目参数信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void deleteItemParameterInfo(Object obj) throws Exception {
		this.delete("ar.item.deleteItemParameterInfo", obj);
	}
	
	/**
	 * 删除明细项目法人参数信息  删除
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void deleteItemParamInfo(Object obj) throws Exception {
		
		this.delete("ar.item.deleteItemParamInfo", obj);
		
	}

	/**
	 * 检查项目是否存在(check For Item Delete)
	 * @param paramMap
	 * @return int
	 * @throws 
	 */
	@Override
	public int checkForItemDelete(Map paramMap) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.item.checkForItemDelete", paramMap)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
			returnInt = 1;
		}
		
		return returnInt ;
	}

	/**
	 * 检查项目是否存在(check ForItemParam Delete)
	 * @param paramMap
	 * @return int
	 * @throws 
	 */
	@Override
	public int checkForItemParamDelete(Map paramMap) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.item.checkForItemParamDelete", paramMap)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
			returnInt = 1;
		}
		
		return returnInt ;
	}

	/**
	 * 检查项目是否唯一(check For Item Delete)
	 * @param paramMap
	 * @return int
	 * @throws 
	 */
	@Override
	public int checkForItemParamUnique(Map paramMap) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.item.checkForItemParamUnique", paramMap)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
			returnInt = -1;
		}
		
		return returnInt ;
	}
	
	public int checkForItemParam(Map paramMap) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.item.checkForItemParam", paramMap)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
			returnInt = -1;
		}
		
		return returnInt ;
	}
	
	/**
	 * 检查唯一性(check ItemInfo Unique)
	 * @param LinkedHashMap
	 * @return int
	 */
	@Override
	public int checkItemInfoUnique(LinkedHashMap paramMap) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.item.checkItemInfoUnique", paramMap)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 根据项目取有效日期类型列表(get DataType List)
	 * @param Object
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getDataTypeList(Object object){
		
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.item.getDataTypeList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 用于获取申请类型
	 */
	@SuppressWarnings("unchecked")
	public List getApplyList(Object object){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.item.getApplyList",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 根据item_no删除所有记录
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updateItemParamInfoAll(String item_no) throws Exception{
		try {
		    this.update("ar.item.updateItemParamInfoAll", item_no) ;
		    return 1;
	    } catch (RuntimeException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		    return 0;
	    }
	}

	/**
	 * 取得明细项目参数人事政策信息
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getParamItem(Object object) {
		LinkedHashMap returnObj = new LinkedHashMap() ;

		List returnList = this.getItemParamList(object);
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}

	/**
	 * 检查唯一性(check ParamItemInfo Unique)
	 * @param LinkedHashMap
	 * @return int
	 */
	@Override
	public int checkParamItemInfoUnique(LinkedHashMap paramMap) {
       int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.item.checkParamItemInfoUnique", paramMap)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}

	/**
	 * 更新明细项目参数信息
	 * @param Object
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public void updateParamItemInfo(Object obj) throws Exception {
	this.syLanguageDao.updateSyGlobalName(obj);
		
		this.update("ar.item.updateParamItemInfo", obj) ;
	}
}

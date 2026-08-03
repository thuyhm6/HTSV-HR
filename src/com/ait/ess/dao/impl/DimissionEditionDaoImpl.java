package com.ait.ess.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ess.dao.DimissionEditionDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class DimissionEditionDaoImpl extends SqlMapClientSupport implements DimissionEditionDao{
	
	@Autowired
	private SyLanguageDao syLanguageDao;

	/**
	 * 添加离职交接模版
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
    public int addDimissionEditionInfo(Object object) throws Exception{
		int returnInt = 1;
		try {
			this.insert("ess.dimissionApply.addDimissionEditionInfo", object);
		} catch (Exception e) {
			e.printStackTrace();
			returnInt = 0;
		}
		return returnInt;
	}
	
    /**
	 * 修改离职交接模版
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int updateDimissionEditionInfo(Object object) throws Exception{
		int returnInt = 1;
		try {
			this.update("ess.dimissionApply.deleteDimissionEditionInfoAll", object);
		} catch (Exception e) {
			e.printStackTrace();
			returnInt = 0;
		}
		return returnInt;
	}
	
	/**
	 * 删除离职交接模版
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int deleteDimissionEditionInfo(LinkedHashMap map) throws Exception{
		int returnInt = 1;
		try {
			this.delete("ess.dimissionApply.deleteDimissionEditionInfo", map);
		} catch (Exception e) {
			e.printStackTrace();
			returnInt = 0;
		}
		return returnInt;
	}
	
	/***
	 * 根据版本号查找人员类型
	 */
	public List findEmpTypeByeditionNo(Object object){
        List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.dimissionApply.getEmpTypeByeditionNo", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 查找离职交接模版单行记录的详细信息
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object getDimissionEditionInfo(Object object)throws SQLException{
		LinkedHashMap returnObj = new LinkedHashMap() ;
        List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.dimissionApply.getDimissionEditionList", object);
			if(returnList.size()>0){
				returnObj = (LinkedHashMap) returnList.get(0);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnObj ;
	}
	
	/**
	 * 查找离职交接模版数量
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getDimissionEditionCnt(Object object)throws SQLException{
		int returnInt = 0 ;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.dimissionApply.getDimissionEditionCnt", object)), Integer.class) ;	
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 分页查找离职交接模版List
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getDimissionEditionList(Object object, int currentPage, int pageSize)throws SQLException{
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.dimissionApply.getDimissionEditionList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ess.dimissionApply.getDimissionEditionList", object);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	
	/**
	 * 分页查找离职交接模版List
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getDimissionEditionListUpdate(Object object)throws SQLException{
		List returnList = new ArrayList() ;
		try {
		    returnList = this.queryForList("ess.dimissionApply.getDimissionEditionListUpdate", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	
	/**
	 * 分页查找离职交接模版List
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getDimissionEditionListUpdate(Object object, int currentPage, int pageSize)throws SQLException{
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.dimissionApply.getDimissionEditionListUpdate", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("ess.dimissionApply.getDimissionEditionListUpdate", object);
			}   
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 查找离职交接模版List
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getDimissionEditionList(Object object)throws SQLException{
		List returnList = new ArrayList() ;
		returnList = this.getDimissionEditionList(object, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 查找人员类型（修改）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getEmpTypeNameListUpdate(Object object)throws SQLException{
		List returnList = new ArrayList() ;
		returnList = this.queryForList("ess.dimissionApply.getEmpTypeNameListUpdate", object);
		return returnList;
	}
	
	/**
	 * 查找人员类型（添加）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getEmpTypeNameListAdd(Object object)throws SQLException{
		List returnList = new ArrayList() ;
		returnList = this.queryForList("ess.dimissionApply.getEmpTypeNameListAdd", object);
		return returnList;
	}
	
	/**
	 * 查找离职交接模版版本号信息   添加的时候不能重复
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getDimissionEditionListAdd(Object object)throws SQLException{
		LinkedHashMap returnObj = new LinkedHashMap() ;
        List returnList = new ArrayList() ;
        int returnNum = 0;
		try {
			returnList = this.queryForList("ess.dimissionApply.getDimissionEditionList", object);
			if(returnList.size()>0){
				returnNum = 1;
			}else{
				returnNum = 0;
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnNum;
	}
	
	/**
	 * 查找离职交接内容表的离职交接内容项目的List 不分页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */ 
	@SuppressWarnings("unchecked")
	public List getEditionItemTypeParamList(Object object){
		List returnList = new ArrayList() ;
		returnList = this.getEditionItemTypeParamList(object, -1, -1) ;
		return returnList ;
	}
	
	
	/************************************离职交接类型表管理（SYS_EDITION_ITEM）************************************************/
	
	/**
	 * 查找离职交接内容表的离职交接内容类型的List 不分页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */ 
	@SuppressWarnings("unchecked")
	public List getEditionItemTypeList(Object object){
		List returnList = new ArrayList() ;
		returnList = this.getEditionItemTypeList(object, -1, -1) ;
		return returnList ;
	}
	
	
	
	/**
	 * 查找离职交接内容表的离职交接内容类型的List 分页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */ 
	@SuppressWarnings("unchecked")
	public List getEditionItemTypeList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.dimissionApply.getEditionItemTypeList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ess.dimissionApply.getEditionItemTypeList", object);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 添加离职交接类型
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
    public int addEditionItemTypeInfo(Object object) throws Exception{
    	int returnInt = 1;
		try {
			LinkedHashMap obj = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(object);
			this.insert("ess.dimissionApply.addEditionItemTypeInfo", obj);
		} catch (Exception e) {
			e.printStackTrace();
			returnInt = 0;
		}
		return returnInt;
    }
	
    /**
	 * 修改离职交接类型
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int updateEditionItemTypeInfo(Object object) throws Exception{
		int returnInt = 1;
		try {
			this.syLanguageDao.updateSyGlobalName(object);
			this.update("ess.dimissionApply.updateEditionItemTypeInfo", object);
		} catch (Exception e) {
			e.printStackTrace();
			returnInt = 0;
		}
		return returnInt;
	}
	
	/**
	 * 删除离职交接类型
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int deleteEditionItemTypeInfo(LinkedHashMap map) throws Exception{
		int returnInt = 1;
		try {
			this.delete("ess.dimissionApply.deleteEditionItemTypeInfo", map);
		} catch (Exception e) {
			e.printStackTrace();
			returnInt = 0;
		}
		return returnInt;
	}
	
	/**
	 * 查找离职交接类型单行记录的详细信息
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public Object getEditionItemTypeInfo(Object object)throws SQLException{
		LinkedHashMap returnObj = new LinkedHashMap() ;
        List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ess.dimissionApply.getEditionItemTypeList", object);
			if(returnList.size()>0){
				returnObj = (LinkedHashMap) returnList.get(0);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnObj ;
	}
	
	/**
	 * 查找离职交接类型数量
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getEditionItemTypeCnt(Object object)throws SQLException{
		int returnInt = 0 ;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.dimissionApply.getEditionItemTypeCnt", object)), Integer.class) ;	
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	/**
	 * 查找版同一版本的交接类型 添加的时候类型名称不可以重复
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getEditionItemList(Object object)throws SQLException{
		LinkedHashMap returnObj = new LinkedHashMap() ;
        List returnList = new ArrayList() ;
        int returnNum = 0;
		try {
			returnList = this.queryForList("ess.dimissionApply.getEditionItemTypeList", object);
			if(returnList.size()>0){
				returnNum = 1;
			}else{
				returnNum = 0;
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnNum;
	}
	/*********************************交接类型的具体项目*********************************************/
	
	
	/**
	 * 查找离职交接内容表的离职交接内容项目的List 分页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */ 
	@SuppressWarnings("unchecked")
	public List getEditionItemTypeParamList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.dimissionApply.getEditionItemTypeParamList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ess.dimissionApply.getEditionItemTypeParamList", object);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 查找离职交接表类型项目数量
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getEditionItemTypeParamCnt(Object object)throws SQLException{
		int returnInt = 0 ;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.dimissionApply.getEditionItemTypeParamCnt", object)), Integer.class) ;	
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	
	/**
	 * 删除离职交类型的具体项目
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
    public int deleteEditionItemTypeParamInfo(LinkedHashMap map) throws Exception{
    	int returnInt = 1;
		try {
			this.delete("ess.dimissionApply.deleteEditionItemTypeParamInfo", map);
		} catch (Exception e) {
			e.printStackTrace();
			returnInt = 0;
		}
		return returnInt;
    }
    
    /**
	 * 添加离职交类型的具体项目
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
    public int addEditionItemTypeParamInfo(Object object) throws Exception{
    	int returnInt = 1;
		try {
			LinkedHashMap obj = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(object);
			this.insert("ess.dimissionApply.addEditionItemTypeParamInfo", obj);
		} catch (Exception e) {
			e.printStackTrace();
			returnInt = 0;
		}
		return returnInt;
    }
    
    /**
   	 * 修改离职交类型的具体项目
   	 * @param parameterObject
   	 * @return
   	 * @throws Exception
   	 */
       public int updateEditionItemTypeParamInfo(Object object) throws Exception{
       	int returnInt = 1;
   		try {
   			this.insert("ess.dimissionApply.updateEditionItemParamInfo", object);
   		} catch (Exception e) {
   			e.printStackTrace();
   			returnInt = 0;
   		}
   		return returnInt;
       }
       
       /**
      	 * 离职交接项目审批
      	 * @param parameterObject
      	 * @return
      	 * @throws Exception
      	 */
          public int updatepersonEditionParamInfo(Object object) throws Exception{
        	  int returnInt = 1;
         		try {
         			this.insert("ess.dimissionApply.updatepersonEditionParamInfo", object);
         		} catch (Exception e) {
         			e.printStackTrace();
         			returnInt = 0;
         		}
         		return returnInt;
          }
   	
       
       /**
      	 * 修改离职交类型的具体项目
      	 * @param parameterObject
      	 * @return
      	 * @throws Exception
      	 */
          public int updateEditionItemTypeParamInfo1(Object object) throws Exception{
          	int returnInt = 1;
      		try {
      			this.insert("ess.dimissionApply.updateEditionParamInfo", object);
      		} catch (Exception e) {
      			e.printStackTrace();
      			returnInt = 0;
      		}
      		return returnInt;
          }
    
/**********************************交接进度查询*********************************/
    
    /**
	 * 查找离职交接进度详情数量
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getEditionCheckCnt(Object object)throws SQLException{
		int returnInt = 0 ;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.dimissionApply.getEditionCheckCnt", object)), Integer.class) ;	
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 查找离职交接进度的List 不分页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */ 
	@SuppressWarnings("unchecked")
	public List getEditionCheckList(Object object){
		List returnList = new ArrayList() ;
		returnList = this.getEditionCheckList(object, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 查找离职交接内容表的离职交接进度的List 分页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */ 
	@SuppressWarnings("unchecked")
	public List getEditionCheckList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.dimissionApply.getEditionCheckList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ess.dimissionApply.getEditionCheckList", object);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 修改离职交接项目
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int updateEditionItemParamInfo(Object object) throws Exception{
		int returnInt = 1;
		try {
			this.update("ess.dimissionApply.updateEditionItemParamInfotype", object);
		} catch (Exception e) {
			e.printStackTrace();
			returnInt = 0;
		}
		return returnInt;
	}
	
	/**********************************交接项目设置（添加审批人）*********************************/

}

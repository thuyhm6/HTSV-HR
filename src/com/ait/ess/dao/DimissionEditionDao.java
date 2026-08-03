package com.ait.ess.dao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public interface DimissionEditionDao {
	
	/**
	 * 添加离职交接模版
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
    public int addDimissionEditionInfo(Object object) throws Exception;
	
    /**
	 * 修改离职交接模版
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int updateDimissionEditionInfo(Object object) throws Exception;
	
	/**
	 * 删除离职交接模版
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int deleteDimissionEditionInfo(LinkedHashMap map) throws Exception;
	
	/***
	 * 根据版本号查找人员类型
	 */
	public List findEmpTypeByeditionNo(Object object);
	
	/**
	 * 查找离职交接模版单行记录的详细信息
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public Object getDimissionEditionInfo(Object object)throws SQLException;
	
	/**
	 * 查找离职交接模版数量
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getDimissionEditionCnt(Object object)throws SQLException;
	
	/**
	 * 分页查找离职交接模版List
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getDimissionEditionList(Object object, int currentPage, int pageSize)throws SQLException;
	
	/**
	 * 查找离职交接模版List
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getDimissionEditionList(Object object)throws SQLException;
	
	/**
	 * 查找人员类型(添加)
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getEmpTypeNameListUpdate(Object object)throws SQLException;
	
	public List getDimissionEditionListUpdate(Object object)throws SQLException;
	
	public List getDimissionEditionListUpdate(Object object, int currentPage, int pageSize)throws SQLException;
	
	/**
	 * 查找人员类型(修改)
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getEmpTypeNameListAdd(Object object)throws SQLException;
	
	/**
	 * 查找版本号 添加的时候版本号不可以重复
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getDimissionEditionListAdd(Object object)throws SQLException;
	
	
	/**
	 * 查找离职交接内容表的离职交接内容项目的List 不分页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */ 
	@SuppressWarnings("unchecked")
	public List getEditionItemTypeParamList(Object object);
	
	
	/**********************************离职交接类型***********************************************/
	
	/**
	 * 添加离职交接类型
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
    public int addEditionItemTypeInfo(Object object) throws Exception;
	
    /**
	 * 修改离职交接类型
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int updateEditionItemTypeInfo(Object object) throws Exception;
	
	/**
	 * 删除离职交接类型
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int deleteEditionItemTypeInfo(LinkedHashMap map) throws Exception;
	
	/**
	 * 查找离职交接类型单行记录的详细信息
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public Object getEditionItemTypeInfo(Object object)throws SQLException;
	
	/**
	 * 查找离职交接类型数量
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getEditionItemTypeCnt(Object object)throws SQLException;
	
	/**
	 * 查找离职交接内容表的离职交接内容类型的List 不分页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */ 
	@SuppressWarnings("unchecked")
	public List getEditionItemTypeList(Object object);
	
	/**
	 * 查找离职交接内容表的离职交接内容类型的List 分页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */ 
	@SuppressWarnings("unchecked")
	public List getEditionItemTypeList(Object object, int currentPage, int pageSize);
	
	/**
	 * 查找版同一版本的交接类型 添加的时候类型名称不可以重复
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getEditionItemList(Object object)throws SQLException;
	
	/**************************************交接类型的项目*******************************************/
	/**
	 * 查找离职交接内容表的离职交接内容项目的List 分页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */ 
	@SuppressWarnings("unchecked")
	public List getEditionItemTypeParamList(Object object, int currentPage, int pageSize);
	
	/**
	 * 查找离职交接表类型项目数量
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getEditionItemTypeParamCnt(Object object)throws SQLException;
	
	
	/**
	 * 删除离职交类型的具体项目
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
    public int deleteEditionItemTypeParamInfo(LinkedHashMap map) throws Exception;
    
    /**
	 * 添加离职交类型的具体项目
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
    public int addEditionItemTypeParamInfo(Object object) throws Exception;
    
    /**
   	 * 修改离职交类型的具体项目
   	 * @param parameterObject
   	 * @return
   	 * @throws Exception
   	 */
       public int updateEditionItemTypeParamInfo(Object object) throws Exception;
       
       /**
      	 * 修改离职交类型的具体项目
      	 * @param parameterObject
      	 * @return
      	 * @throws Exception
      	 */
          public int updateEditionItemTypeParamInfo1(Object object) throws Exception;
    
    /**********************************交接进度查询*********************************/
    
    /**
	 * 查找离职交接进度详情数量
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getEditionCheckCnt(Object object)throws SQLException;
	
	/**
	 * 查找离职交接进度的List 不分页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */ 
	@SuppressWarnings("unchecked")
	public List getEditionCheckList(Object object);
	
	/**
	 * 查找离职交接内容表的离职交接进度的List 分页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */ 
	@SuppressWarnings("unchecked")
	public List getEditionCheckList(Object object, int currentPage, int pageSize);
	
	/**
	 * 修改离职交接项目
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int updateEditionItemParamInfo(Object object) throws Exception;
	
	/**********************************交接项目设置（添加审批人）*********************************/
	
	/**
   	 * 离职交接项目审批
   	 * @param parameterObject
   	 * @return
   	 * @throws Exception
   	 */
       public int updatepersonEditionParamInfo(Object object) throws Exception;
	
}

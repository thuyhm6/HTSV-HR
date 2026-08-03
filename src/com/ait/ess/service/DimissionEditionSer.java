package com.ait.ess.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

public interface DimissionEditionSer {
	/***
	 * 添加离职交接模版
	 * @param request
	 * @return
	 * @throws Exception
	 */
    public int addDimissionEditionInfo(HttpServletRequest request) throws Exception;
	
    /***
	 * 修改离职交接模版
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int updateDimissionEditionInfo(HttpServletRequest request) throws Exception;
	/***
	 * 删除离职交接模版
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	public int deleteDimissionEditionInfo(HttpServletRequest request) throws Exception;
	
	/***
	 * 查找离职交接模版List
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getDimissionEditionList(HttpServletRequest request) throws Exception;
	
	/***
	 * 根据版本号查找人员类型
	 */
	public List findEmpTypeByeditionNo(HttpServletRequest request);
	
	/**
	 * 查找离职交接模版LIst
	 */
	public List getDimissionEditionlist(HttpServletRequest request) throws Exception;
	
	/**
	 * 查找全部离职交接模版
	 */
	public List getDimissionEditionListUpdate(HttpServletRequest request) throws Exception;

	
	/***
	 * 查找离职交接模版数量
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getDimissionEditionCnt(HttpServletRequest request) throws Exception;
	
	/***
	 * 查找离职交接模版单行详细记录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Object getDimissionEditionInfo(HttpServletRequest request) throws Exception;
	
	/**
	 * 查找人员类型（修改）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getEmpTypeNameListUpdate(HttpServletRequest request)throws SQLException;
	
	/**
	 * 查找人员类型（添加）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getEmpTypeNameListAdd(HttpServletRequest request)throws SQLException;
	
	/***
	 * 查找离职交接模版的版本号  添加的时候不可以重复
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getDimissionEditionListAdd(HttpServletRequest request) throws Exception;
	
	
	
/**********************************离职交接类型***********************************************/
	
	/**
	 * 添加离职交接类型
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
    public int addEditionItemTypeInfo(HttpServletRequest request) throws Exception;
	
    /**
	 * 修改离职交接类型
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int updateEditionItemTypeInfo(HttpServletRequest request) throws Exception;
	
	/**
	 * 删除离职交接类型
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int deleteEditionItemTypeInfo(HttpServletRequest request) throws Exception;
	
	/**
	 * 查找离职交接类型单行记录的详细信息
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public Object getEditionItemTypeInfo(HttpServletRequest request)throws SQLException;
	
	/**
	 * 查找离职交接类型数量
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getEditionItemTypeCnt(HttpServletRequest request)throws SQLException;
	
	/**
	 * 查找离职交接内容表的离职交接内容类型的List 不分页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */ 
	@SuppressWarnings("unchecked")
	public List getEditionItemTypeList(HttpServletRequest request);
	

	/**
	 * 查找版同一版本的交接类型 添加的时候类型名称不可以重复
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getEditionItemList(HttpServletRequest request)throws SQLException;
	
	/*****************************交接类型的具体项目****************************/
	
	/**
	 * 查找离职交接内容表的离职交接内容项目的List 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */ 
	@SuppressWarnings("unchecked")
	public List getEditionItemTypeParamList(HttpServletRequest request);
	
	/**
	 * 查找离职交接表类型项目数量
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getEditionItemTypeParamCnt(HttpServletRequest request)throws SQLException;
	
	/**
	 * 添加离职交接类型的具体项目
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
    public int addEditionItemTypeParamInfo(HttpServletRequest request) throws Exception;
    
    /**
	 * 修改离职交接类型的具体项目
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
    public int updateEditionItemTypeParamInfo(HttpServletRequest request) throws Exception;
    public int updateEditionItemTypeParam(HttpServletRequest request) throws Exception;
    
    /**
	 * 删除离职交接类型的具体项目
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int deleteEditionItemTypeParamInfo(HttpServletRequest request) throws Exception;
	
 /**********************************交接进度查询*********************************/
    
    /**
	 * 查找离职交接进度详情数量
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getEditionCheckCnt(HttpServletRequest request)throws SQLException;
	
	/**
	 * 查找离职交接进度的List 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */ 
	@SuppressWarnings("unchecked")
	public List getEditionCheckList(HttpServletRequest request);
	
	/**
	 * 修改离职交接项目
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int updateEditionItemParamInfo(HttpServletRequest request) throws Exception;
	
	
	/**********************************交接项目设置（添加审批人）*********************************/
	
	/**
	 * 根据模版号查找所有交接项目
	 * @param object
	 * @return
	 */
	public Map<String,Map<String,List>> findEditionItem(HttpServletRequest request);
	
	/**
	 * 审批交接项目
	 */
	 public int updatepersonEditionParamInfo(HttpServletRequest request) throws Exception;
}

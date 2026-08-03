package com.ait.ess.service;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface EditionAffirmSer {
	/**
	 * 查找离职申请数量
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getEditionAffirmCnt(HttpServletRequest request)throws SQLException;
	
	/**
	 * 查找离职申请List
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getEditionAffirmList(HttpServletRequest request)throws SQLException;
	public List getEditionLgepAffirmList(HttpServletRequest request)throws SQLException;
	
	/**
	 * 根据NO查询此次申请的人(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEditionApplyorByApplyNoList(HttpServletRequest request) throws Exception;
	
	/**
	 * 根据离职申请NO决裁信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEditionAffirmorByApplyNoList(HttpServletRequest request) throws Exception;
	
	/**
	 * 离职申请check信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEditionCheckorByApplyNoList(HttpServletRequest request) throws Exception;
	
	/**
	 * 决裁离职申请
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-13
	 * @version V1.0
	 */
	public int affirmDimisionInfo(HttpServletRequest request);
	
	/**
	 * check离职申请
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-13
	 * @version V1.0
	 */
	public int checkDimissionInfo(HttpServletRequest request);
	
	/**
	 * check信息列表
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getDimissionCheckList(HttpServletRequest request);
	
	/**
	 * check信息列表
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author WENDI@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getDimissionCheckCnt(HttpServletRequest request);
	
	/**
	 * 添加Check人
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int addCheckAffirmDimission(HttpServletRequest request);
	
	/**
	 * 交接项目明细审批和查看
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmEditionItemList(LinkedHashMap paramMap,HttpServletRequest request) throws Exception;
	
	/**
	 * 根据离职申请NO查询此次（个人/代）申请的所有人(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDimissionByApplyNoList(HttpServletRequest request) throws Exception;
	
}

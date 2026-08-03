package com.ait.ess.dao;

import java.util.List;

/**
 * @fileName: AbsenteeismCtroller
 * @Description:旷工申请 
 */
public interface AbsenteeismDao {
	
	/**
	 * 查询旷工申请 信息列表
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getAbsenteeismList(Object obj);
	
	/**
	 *  查询旷工申请 信息列表-判断是否分页,(search employee result list and judge if pagenation）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getAbsenteeismList(Object obj, int currentPage, int pageSize) ;
	
	/**
	 *  查询旷工申请 信息列表总数
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getAbsenteeismListCnt(Object obj) throws Exception ;
}

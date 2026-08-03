package com.ait.sys.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
public interface HrmAffirmConfigSer {

	/**
	 * 查询人事令页面设置参数
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getHrmAffirmConfigDistinctList(HttpServletRequest request)throws Exception;

	/**
	 * 查询人事令页面设置参数
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getHrmAffirmConfigTransCodeList(HttpServletRequest request)throws Exception;
	
	/**
	 * 获得上次保存的配置记录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map getPreHrmAffirmConfigRecordList(HttpServletRequest request,List hrmAffirmConfigTransCodeList)throws Exception;
	

	/**
	 * 保存人事令配置
	 * @param request
	 * @throws Exception
	 */
	public Integer saveHrmAffirmConfig(HttpServletRequest request)throws Exception;
	
	
}

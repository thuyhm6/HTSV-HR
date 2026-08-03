package com.ait.sys.dao;

import java.util.LinkedHashMap;
import java.util.List;

public interface HrmAffirmConfigDao {

	/**
	 * 查询人事令页面设置参数(表头信息)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getHrmAffirmConfigDistinctList(LinkedHashMap paramMap)throws Exception;
	
	/**
	 * 查询人事令页面设置参数(发令类型)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getHrmAffirmConfigTransCodeList(LinkedHashMap paramMap)throws Exception;

	/**
	 * 获得上次保存的配置记录
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPreHrmAffirmConfigRecordList(LinkedHashMap paramMap)throws Exception;
	
	/**
	 * 查询人事令类型编号
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getHrmAffirmConfigTransCodeNoList(LinkedHashMap paramMap)throws Exception;

	/**
	 * 保存人事令配置选项
	 * @param paramMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void saveHrmAffirmConfig(LinkedHashMap paramMap)throws Exception;

	/**
	 * 按法人删除
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void deleteHrmAffirmConfigByCpnyId(LinkedHashMap paramMap)throws Exception;
	
	/**
	 * 按法人ID查询是否存在配置记录
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Integer selectHrmAffirmConfigByCpnyId(LinkedHashMap paramMap)throws Exception;

}

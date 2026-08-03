package com.ait.sys.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ait.sys.dao.HrmAffirmConfigDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class HrmAffirmConfigDaoImpl extends SqlMapClientSupport implements HrmAffirmConfigDao {

	/**
	 * 查询人事令页面设置参数(表头信息)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getHrmAffirmConfigDistinctList(LinkedHashMap paramMap)
			throws Exception {
		return this.queryForList("sys.hrmAffirmConfig.getHrmAffirmConfigDistinctList", paramMap);
	}

	/**
	 * 查询人事令页面设置参数(发令类型)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getHrmAffirmConfigTransCodeList(LinkedHashMap paramMap)
			throws Exception {
		return this.queryForList("sys.hrmAffirmConfig.getHrmAffirmConfigTransCodeList", paramMap);
	}

	/**
	 * 获得上次保存的配置记录
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPreHrmAffirmConfigRecordList(LinkedHashMap paramMap)
			throws Exception {
		return this.queryForList("sys.hrmAffirmConfig.getPreHrmAffirmConfigRecordList", paramMap);
	}
	
	/**
	 * 查询人事令类型编号
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getHrmAffirmConfigTransCodeNoList(LinkedHashMap paramMap)
			throws Exception {
		return this.queryForList("sys.hrmAffirmConfig.getHrmAffirmConfigTransCodeNoList",paramMap);
	}

	/**
	 * 保存人事令配置选项
	 * @param paramMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void saveHrmAffirmConfig(LinkedHashMap paramMap) throws Exception {
		this.insert("sys.hrmAffirmConfig.saveHrmAffirmConfig", paramMap);
		
	}

	/**
	 * 按法人删除
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void deleteHrmAffirmConfigByCpnyId(LinkedHashMap paramMap) throws Exception {
		this.delete("sys.hrmAffirmConfig.deleteHrmAffirmConfigByCpnyId", paramMap);
	}

	/**
	 * 按法人ID查询是否存在配置记录
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer selectHrmAffirmConfigByCpnyId(LinkedHashMap paramMap)
			throws Exception {
		return (Integer) this.queryForObject("sys.hrmAffirmConfig.selectHrmAffirmConfigRecords", paramMap);
	}

}

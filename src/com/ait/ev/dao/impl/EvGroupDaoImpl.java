package com.ait.ev.dao.impl;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;

import com.ait.ev.dao.EvGroupDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class EvGroupDaoImpl extends SqlMapClientSupport implements EvGroupDao{

	@Override
	public int delEvGroupInfo(Object parameterObject) {
		int returnInt = 1;
		try {
			this.delete("ev.basicsetting.delEvGroupInfo", parameterObject);
		} catch (Exception e) {
			returnInt = 0;
			e.printStackTrace();
		}
		return returnInt;
	}

	@Override
	public List getEvGroupInfo(Object parameterObject) throws Exception {
		return this.queryForList("ev.basicsetting.getEvGroupInfo", parameterObject);
	}

	@Override
	public int insertEvGroupInfo(Object parameterObject) {
		int returnInt = 1;
		try {
			this.insert("ev.basicsetting.insertEvGroupInfo", parameterObject);
		} catch (Exception e) {
			returnInt = 0;
			e.printStackTrace();
		}
		return returnInt;
	}

	@Override
	public int updateEvGroupInfo(Object parameterObject) {
		int returnInt = 1;
		try {
			this.update("ev.basicsetting.updateEvGroupInfo", parameterObject);
		} catch (Exception e) {
			returnInt = 0;
			e.printStackTrace();
		}
		return returnInt;
	}

	@Override
	public LinkedHashMap getEvGroupInfoById(Object parameterObject)
			throws Exception {
		return (LinkedHashMap) this.queryForObject("ev.basicsetting.getEvGroupInfoById", parameterObject);
	}

	@Override
	public List getEvGroupTree(Object parameterObject) throws Exception {
		return this.queryForList("ev.basicsetting.getEvGroupTree", parameterObject);
	}

	@Override
	public boolean getGroupHasChild(Object parameterObject) throws Exception {
		String has = ObjectUtils.toString(this.queryForObject("ev.basicsetting.getGroupHasChild", parameterObject));
		if(has!=null&&has!=""){
			return true;
		}
		return false;
	}

	
}

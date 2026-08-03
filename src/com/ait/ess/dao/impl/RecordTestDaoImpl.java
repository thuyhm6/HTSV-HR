package com.ait.ess.dao.impl;

import org.springframework.stereotype.Repository;

import com.ait.ess.dao.RecordTestDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class RecordTestDaoImpl extends SqlMapClientSupport implements RecordTestDao{
//添加打卡记录
	@SuppressWarnings("unchecked")
	@Override
	public void addRecordTest(Object object) throws Exception {
		this.insert("ess.recordTest.addRecordTest", object);
		
	}
}

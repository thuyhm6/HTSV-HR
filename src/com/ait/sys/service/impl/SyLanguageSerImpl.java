package com.ait.sys.service.impl;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.sys.service.SyLanguageSer;

@Service
public class SyLanguageSerImpl implements SyLanguageSer {
	
	Logger logger = Logger.getLogger(SysSerImpl.class);
	
	@Autowired
	private SyLanguageDao syLanguageDao;

	@SuppressWarnings("unchecked")
	public List getSyLanguageListByActivity() {
		return syLanguageDao.getSyLanguageListByActivity();
	}
	@SuppressWarnings("unchecked")
	public List getSyLanguageNameListByActivity(Object object) {
		return syLanguageDao.getSyLanguageNameListByActivity(object);
	}
	
}

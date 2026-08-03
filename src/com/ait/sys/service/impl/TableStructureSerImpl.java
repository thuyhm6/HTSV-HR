package com.ait.sys.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.sys.dao.SyLanguageDao;
import com.ait.sys.dao.TableStructureDao;
import com.ait.sys.service.TableStructureSer;
import com.ait.web.util.ObjectBindUtil;


/**
 * 
* @ClassName: TableStructureSerImpl 
* @Description: TODO
* @author yorio youjia@ait.net.cn
* @date Feb 4, 2013 6:12:55 PM 
*
 */
@Service
public class TableStructureSerImpl implements TableStructureSer {
	
	@Autowired
	private TableStructureDao tableStructureDao;
	
	@Autowired
	private SyLanguageDao syLanguageDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List getTableStructureList(HttpServletRequest request) {
		LinkedHashMap object=ObjectBindUtil.getRequestParamData(request);
		return this.tableStructureDao.getTableStructureList(object);
	}

	@Override
	public int getTableStructureListCnt(HttpServletRequest request) {
		return 0;
	}

	
}

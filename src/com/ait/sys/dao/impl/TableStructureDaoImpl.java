package com.ait.sys.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ait.sys.dao.SyLanguageDao;
import com.ait.sys.dao.TableStructureDao;
import com.ait.web.util.SqlMapClientSupport;


/**
 * Copyright:   LDCC (c)
 * Company:     LDCC
 * @fileName PageStructureDaoImpl.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-4-19 下午12:02:36
 * @version 5.0
 * 
 */
@Repository
public class TableStructureDaoImpl extends SqlMapClientSupport implements TableStructureDao {

	@Autowired
	private SyLanguageDao syLanguageDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List getTableStructureList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.tableStructure.getTableStructureList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
}

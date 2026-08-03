package com.ait.sys.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ait.sys.dao.PageStructureDao;
import com.ait.sys.dao.SyLanguageDao;
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
public class PageStructureDaoImpl extends SqlMapClientSupport implements PageStructureDao {

	@Autowired
	private SyLanguageDao syLanguageDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPageStructureList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.pageStructure.getPageStructureList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	public List getPsDataList(Object object){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.pageStructure.getPsDataList",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	public List retrieveReportItemListByTableName(Object object){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.pageStructure.retrieveReportItemListByTableName",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public void addNewAliasInfo (List list) throws Exception{
		 this.insertForList("sys.pageStructure.addNewAliasInfo", list);
	}
	
	@SuppressWarnings("unchecked")
	public List retrieveReportItemList(Object object){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.pageStructure.retrieveReportItemList",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public void deleteNewAliasInfo(List list) throws Exception{
		this.deleteForList("sys.pageStructure.deleteNewAliasInfo",list);
	}
	
	@SuppressWarnings("unchecked")
	public void addAliasInfo(List addList) throws Exception{
		this.insertForList("sys.pageStructure.addAliasInfo", addList);
	}
	
	 @SuppressWarnings("unchecked")
	public void deleteAliasInfo(List delList) throws Exception{
		 this.deleteForList("sys.pageStructure.deleteAliasInfo", delList);
	 }
}

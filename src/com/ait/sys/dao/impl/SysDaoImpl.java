package com.ait.sys.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ait.sys.bean.CodeBean;
import com.ait.sys.dao.SysDao;
import com.ait.web.util.SqlMapClientSupport;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName SysDaoImpl.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:21:33
 * @version 5.0
 *
 */
@Repository
public class SysDaoImpl extends SqlMapClientSupport implements SysDao{	

	@SuppressWarnings("unchecked")
	public void deleteModel(List list) {
		try {
			this.deleteForList("sys.model.deleteModel", list);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public void insertModel(List list) {
		try {
			this.insertForList("sys.model.insertModel", list);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public List getModel(Object object) {
		List temp=new ArrayList();
		try {
			temp = this.queryForList("sys.model.getModel", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getPosition(Object object) {
		List temp  = new ArrayList();
		try {
			temp = this.queryForList("sys.model.getPosition", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPostGrade(Object object) {
		List temp  = new ArrayList();
		try {
			temp = this.queryForList("sys.model.getPostGrade", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPostGroup(Object object) {
		List temp  = new ArrayList();
		try {
			temp = this.queryForList("sys.model.getPostGroup", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPost(Object object) {
		List temp  = new ArrayList();
		try {
			temp = this.queryForList("sys.model.getPost", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getLanguage(String object) {
		List temp  = new ArrayList();
		try {
			temp = this.queryForList("sys.model.getLanguage", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CodeBean> getParentCodeNo() {
		List<CodeBean> temp  = new ArrayList();
		try {
			temp = this.queryForList("sys.model.getParentCodeNo");
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getCode() {
		List temp  = new ArrayList();
		try {
			temp = this.queryForList("sys.model.getCode");
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List getCodeLanguage() {
		List temp  = new ArrayList();
		try {
			temp = this.queryForList("sys.model.getCodeLanguage");
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getCodeParamList() {
		List temp  = new ArrayList();
		try {
			temp = this.queryForList("sys.model.getCodeParamList");
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List getSelectTable(Object object) {
		List temp  = new ArrayList();
		try {
			temp = this.queryForList("sys.model.getSelectTable", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List getSelectTableByHrDept(Object object) {
		List temp  = new ArrayList();
		try {
			temp = this.queryForList("sys.model.getSelectTableByHrDept", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List getDeptListByCpnyID(Object object) {
		List temp  = new ArrayList();
		try {
			temp = this.queryForList("sys.basicMaintenance.getDeptListByCpnyID", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}
	
}

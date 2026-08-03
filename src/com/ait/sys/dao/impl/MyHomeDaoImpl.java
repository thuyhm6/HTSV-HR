package com.ait.sys.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ait.sys.dao.MyHomeDao;
import com.ait.web.util.SqlMapClientSupport;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName MyHomeDaoImpl.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:21:14
 * @version 5.0
 *
 */
@Repository
public class MyHomeDaoImpl extends SqlMapClientSupport implements MyHomeDao{	

	@SuppressWarnings("unchecked")
	public void deleteModel(List list) {
		try {
			this.deleteForList("sys.myhome.deleteModel", list);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public void insertModel(List list) {
		try {
			this.insertForList("sys.myhome.insertModel", list);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public List getModel(Object object) {
		List temp=new ArrayList();
		try {
			temp = this.queryForList("sys.myhome.getModel", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}

	@SuppressWarnings("unchecked")
	public List getHomePurview(Object object) {
		List temp=new ArrayList();
		try {
			temp = this.queryForList("sys.myhome.getHomePurview", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}

	public Object getHomePage(Object object) {
		Object temp="";
		try {
			temp = this.queryForObject("sys.myhome.getHomePage", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}
	
	public void updateApp(Object object) {
		try {
			this.update("sys.myhome.updateApp", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}		
	}

	public void deletePage(Object object) {
		try {
			this.update("sys.myhome.deletePage", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
	}

	public void updatePage(Object object) {
		try {
			this.update("sys.myhome.updatePage", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List getSyMenu(Object object) {
		List temp=new ArrayList();
		try {
			temp = this.queryForList("sys.myhome.getSyMenu", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}

	@SuppressWarnings("unchecked")
	public void deleteShort(List list) {
		try {
			this.deleteForList("sys.myhome.deleteShort", list);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("unchecked")
	public void insertShort(List list) {
		try {
			this.deleteForList("sys.myhome.insertShort", list);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List getShort(Object object) {
		List temp=new ArrayList();
		try {
			temp = this.queryForList("sys.myhome.getShort", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}
	@SuppressWarnings("unchecked")
	public List getApp(String type,Object object) {
		List temp=new ArrayList();
		try {
			temp = this.queryForList("sys.myhome."+type, object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}

	@SuppressWarnings("unchecked")
	public List getTipsMenu(Object object) {
		List temp=new ArrayList();
		try {
			temp = this.queryForList("sys.myhome.getTipsMenu", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}

	@SuppressWarnings("unchecked")
	public List getManualMenu(Object object) {
		List temp=new ArrayList();
		try {
			temp = this.queryForList("sys.myhome.getManualMenu", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}
	
	public int getTipsCnt(String sm,Object object) {
		int num = 0;
		try {
			num = (Integer) this.queryForObject("sys.myhome."+sm, object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}
	
	
	@SuppressWarnings("unchecked")
	public List getMenuCodeList(Object object) {
		List temp=new ArrayList();
		try {
			temp = this.queryForList("sys.myhome.getMenuCodeList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}


	@SuppressWarnings("unchecked")
	public List getTipsInMenu(Object object) {
		List temp=new ArrayList();
		try {
			temp = this.queryForList("sys.myhome.getTipsInMenu", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return temp;
	}
	@Override
	public List getInfoNotAffirm(Object object) throws Exception{
		List returnList = new ArrayList();
		returnList = this.queryForList("sys.myhome.getInfoNotAffirm", object);
		return returnList;
	}
	@Override
	public List getInfoNotConfirm(Object object) throws Exception {
		List returnList = new ArrayList();
		returnList = this.queryForList("sys.myhome.getInfoNotConfirm", object);
		return returnList;
	}
	@Override
	public List getOtInfoNotAffirm(Object object) throws Exception {
		List returnList = new ArrayList();
		returnList = this.queryForList("sys.myhome.getOtInfoNotAffirm", object);
		return returnList;
	}
	@Override
	public List getOtInfoNotConfirm(Object object) throws Exception {
		List returnList = new ArrayList();
		returnList = this.queryForList("sys.myhome.getOtInfoNotConfirm", object);
		return returnList;
	}
	@Override
	public List getPersonInfoNotConfirm(Object object) throws Exception{
		List returnList = new ArrayList();
		returnList = this.queryForList("sys.myhome.getPersonInfoNotConfirm", object);
		return returnList;
	}
	@Override
	public List getTransferOrderList(Object object) throws Exception {
		List returnList = new ArrayList();
		returnList = this.queryForList("sys.myhome.getTransferOrderList", object);
		return returnList;
	}
	@Override
	public List getPunishNoAffirmInfo(Object object) throws Exception {
		return this.queryForList("sys.myhome.getPunishNoAffirmInfo", object);
	}
	@Override
	public List getRewardNoAffirmInfo(Object object) throws Exception {
		return this.queryForList("sys.myhome.getRewardNoAffirmInfo", object);
	}
	
	@SuppressWarnings("unchecked")
	public Map getSystemDate(Object object){
		List returnList = new ArrayList() ;
		Map result = null;
		try {
			returnList = this.queryForList("sys.myhome.getSystemDate",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (returnList.size() > 0) {
			result =  (Map) returnList.get(0);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public void updateLoginInfo(Object object) throws Exception{
		this.update("sys.myhome.updateLoginInfo", object);
	}
	
	@SuppressWarnings("unchecked")
	public void addLoginInfo(Object object) throws Exception{
		this.insert("sys.myhome.addLoginInfo", object);
	}
	
	@SuppressWarnings("unchecked")
	public Map getSealControl(Object object){
		List returnList = new ArrayList() ;
		Map result = null;
		try {
			returnList = this.queryForList("sys.myhome.getSealControl",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (returnList.size() > 0) {
			result =  (Map) returnList.get(0);
		}
		return result;
	}
}

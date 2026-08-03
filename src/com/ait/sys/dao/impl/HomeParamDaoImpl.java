package com.ait.sys.dao.impl;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ait.sys.dao.HomeParamDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;
@Repository
public class HomeParamDaoImpl  extends SqlMapClientSupport implements HomeParamDao{

	@Autowired
	private SyLanguageDao syLanguageDao;

	@SuppressWarnings("unchecked")
	public List getHomeParamList(Object object){
		List returnList=null; 
		try {
			returnList=this.queryForList("sys.homeParam.getHomeParamList",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public Object getHomeParam(Object object){
		Object returnObj = new Object();
		List returnList = this.getHomeParamList(object);
		if (returnList.size() > 0) {
			returnObj = returnList.get(0);
		}
		return returnObj;
	}
	
	/**
	 * 只修改国际化的信息
	 */
	public void updateHomeParamInfo(Object object) throws Exception{
		this.syLanguageDao.updateSyGlobalName(object);
		this.saveHomeCheckParam(object);
	}
	
	@SuppressWarnings("unchecked")
	public void saveHomeCheckParam(Object object){
		try{
			List returnList=this.queryForList("sys.homeParam.getHomeCheckParamList", object);
			if (returnList.size()== 0) {
				this.insert("sys.homeParam.insertHomeCheckParamList", object);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List getCpnyList(Object object){
		List returnList=null; 
		try {
			returnList=this.queryForList("sys.homeParam.getCpnyList",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getHomeCheckParamList(Object object){
		List returnList=null; 
		try {
			returnList=this.queryForList("sys.homeParam.getHomeCheckParamList",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public void updateHomeCheckParamInfo(Object object) throws Exception{
		Object[] checkNos=(Object[])((Map)object).get("PARAM_NOS");
		for(int i=0;i<checkNos.length;i++){
			LinkedHashMap paramMap=new LinkedHashMap();
			paramMap.put("PARAM_NO", checkNos[i]);
			paramMap.put("PARAM_VALUE", ((Map)object).get("PARAM_VALUE_"+checkNos[i].toString()));
			this.update("sys.homeParam.updateHomeCheckParamInfo", paramMap);
		}
	}
	@SuppressWarnings("unchecked")
	public List getRoleListByCpnyId(Object object){
		List returnList=null; 
		try {
			returnList=this.queryForList("sys.homeParam.getRoleListByCpnyId",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getEmpTypeListByCpnyId(Object object){
		List returnList=null; 
		try {
			returnList=this.queryForList("sys.homeParam.getEmpTypeListByCpnyId",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getEmpTypeParamListByCpnyId(Object object){
		List returnList=null; 
		try {
			returnList=this.queryForList("sys.homeParam.getEmpTypeParamListByCpnyId",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public void updateHomeParamCpnyInfo(Object object) throws Exception{
		Object[] roleIds=(Object[])((Map)object).get("ROLEIDS");
		String paramValue=StringUtils.join(roleIds,",");
		((Map)object).put("PARAM_VALUE", paramValue);
		this.update("sys.homeParam.updateHomeParamCpnyInfo", object);
	}
	
	public void deleteHomeParamCpnyAndEmpTypeInfo(Object object) throws Exception{
		this.delete("sys.homeParam.deleteHomeParamCpnyAndEmpTypeInfo", object);
	}
	
	public void insertHomeParamCpnyAndEmpTypeInfo(Object object) throws Exception{
		this.insert("sys.homeParam.insertHomeParamCpnyAndEmpTypeInfo", object);
	}
	
	@SuppressWarnings("unchecked")
	public Object getHomeParamCheck(Object object){
//		Object returnObj = new Object();
		Object returnObj = null;
		List returnList = this.getHomeCheckParamList(object);
		if (returnList.size() > 0) {
			returnObj = returnList.get(0);
		}
		return returnObj;
	}
	
}

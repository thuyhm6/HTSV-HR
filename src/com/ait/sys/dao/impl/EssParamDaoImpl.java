package com.ait.sys.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.sys.dao.EssParamDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

@Repository
public class EssParamDaoImpl  extends SqlMapClientSupport implements EssParamDao {

	@Autowired
	private SyLanguageDao syLanguageDao;

	@SuppressWarnings("unchecked")
	public List getEssParamList(Object object){
		List returnList=null; 
		try {
			returnList=this.queryForList("sys.essParam.getEssParamList",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public Object getEssParam(Object object){
		Object returnObj = new Object();
		List returnList = this.getEssParamList(object);
		if (returnList.size() > 0) {
			returnObj = returnList.get(0);
		}
		return returnObj;
	}
	
	/**
	 * 只修改国际化的信息
	 */
	public void updateEssParamInfo(Object object) throws Exception{
		this.syLanguageDao.updateSyGlobalName(object);
		this.saveEssCheckParam(object);
	}
	
	@SuppressWarnings("unchecked")
	public void saveEssCheckParam(Object object){
		try{
			List returnList=this.queryForList("sys.essParam.getEssCheckParamList", object);
			if (returnList.size()== 0) {
				this.insert("sys.essParam.insertEssCheckParamList", object);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List getCpnyList(Object object){
		List returnList=null; 
		try {
			returnList=this.queryForList("sys.essParam.getCpnyList",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getEssCheckParamList(Object object){
		List returnList=null; 
		try {
			returnList=this.queryForList("sys.essParam.getEssCheckParamList",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public void updateEssCheckParamInfo(Object object) throws Exception{
		try{
			Object[] checkNos=(Object[])((Map)object).get("PARAM_NOS");
			for(int i=0;i<checkNos.length;i++){
				LinkedHashMap paramMap=new LinkedHashMap();
				paramMap.put("PARAM_NO", checkNos[i]);
				paramMap.put("PARAM_VALUE", ((Map)object).get("PARAM_VALUE_"+checkNos[i].toString()));
				paramMap.put("interCpnyID", ((Map)object).get("interCpnyID"));
				this.update("sys.essParam.updateEssCheckParamInfo", paramMap);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 取得该法人该加班类型转成调休的默认转换类型
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getOtConverParam(Object obj) {
		Object returnObj = new Object() ;
		List returnList = new ArrayList() ;
		returnList = this.getOtConverParamList(obj) ;
		if(returnList.size() > 0 ){
			returnObj = returnList.get(0) ;
		}
		return returnObj ;
	}
	
	/**
	 * 取得该法人所有加班类型转调休默认转换类型
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getOtConverParamList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getOtConverParamList(obj, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 取得该法人所有加班类型转调休默认转换类型数据总数
	 */
	@Override
	public int getOtConverParamCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.essParam.getOtConverParamCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 取得该法人所有加班类型转调休默认转换类型
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getOtConverParamList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.essParam.getOtConverParamList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("sys.essParam.getOtConverParamList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 添加一条该法人加班转换成调休的默认类型
	 * @param Object
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public int addOtConverParamInfo(Object obj) throws Exception {
		int returnInt = 1 ;
		this.delete("sys.essParam.deleteOtConverParamByParamNo", obj) ;
		this.insert("sys.essParam.insertOtConverParam", obj);
				
		return returnInt ;
	}
	
	/**
	 * 更新一条该法人加班转换成调休的默认类型
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updateOtConverParamInfo(Object obj)  throws Exception{
		int returnInt = 1 ;
		this.update("sys.essParam.updateOtConverParamInfo", obj);
		
		return returnInt ;
	}
	
	/**
	 * 删除该法人的一条加班转换成调休的默认类型
	 * @param Object
	 * @return
	 * @throws SQLException 
	 */
	public int deleteOtConverParam(Object obj) throws SQLException {
		int returnInt = 1 ;
		this.delete("sys.essParam.deleteOtConverParamByParamNo", obj) ;
				
		return returnInt ;
	}

	@Override
	public List getVacationStandardManageList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.essParam.getVacationStandardManageList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("sys.essParam.getVacationStandardManageList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	@Override
	public List getVacationStandardManageList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getVacationStandardManageList(obj, -1, -1) ;
		return returnList ;
	}

	@Override
	public int getVacationStandardManageCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.essParam.getVacationStandardManageCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	@Override
	public List getWorkAreaList(Object obj) {
		List returnList = new ArrayList() ;
		
	try {
		returnList = this.queryForList("sys.essParam.getWorkAreaList", obj);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
	
		return returnList ;
	}

	@Override
	public int saveVacationStandardManage(Object obj) throws Exception {
		int returnInt = 1 ;
		
		this.insert("sys.essParam.insertVacationStandardManage", obj);
				
		return returnInt ;
	}

	@Override
	public int deleteVacationStandardManage(Object object) throws SQLException {
		int returnInt = 1 ;
		this.delete("sys.essParam.deleteVacationStandardManage", object) ;
				
		return returnInt ;
	}

	@Override
	public List getManageList(Object object) {
		List returnList=null; 
		try {
			returnList=this.queryForList("sys.essParam.getManageList",object);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return returnList;
	}

	@Override
	public int updateVacationStandardManage(Object object) throws Exception {
		int returnInt = 1 ;
		this.update("sys.essParam.updateVacationStandardManage", object);
		
		return returnInt ;
	}
}

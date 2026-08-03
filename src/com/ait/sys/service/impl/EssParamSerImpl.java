package com.ait.sys.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.EssParamDao;
import com.ait.sys.service.EssParamSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class EssParamSerImpl implements  EssParamSer{

	Logger logger = Logger.getLogger(EssParamSerImpl.class);
	
	@Autowired
	private EssParamDao essParamDao;
	
	@SuppressWarnings("unchecked")
	public List getEssParamList(HttpServletRequest request){
		List returnList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnList = this.essParamDao.getEssParamList(paramMap) ;
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public Object getEssParam(HttpServletRequest request){
		Object returnObj = new Object() ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnObj = this.essParamDao.getEssParam(paramMap) ;
		return returnObj ;
	}
	
	@SuppressWarnings("unchecked")
	public int updateEssParamInfo(HttpServletRequest request){
		int resultInt=0;
		try{
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("CREATED_BY", admin.getAdminID());
			this.essParamDao.updateEssParamInfo(paramMap);
			resultInt=1;
		}catch(Exception e){
			resultInt=0;
			e.printStackTrace();
		}
		return resultInt;
	}
	
	@SuppressWarnings("unchecked")
	public List getCpnyList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnList = this.essParamDao.getCpnyList(paramMap) ;
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List getEssCheckParamList(HttpServletRequest request){
		List returnList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnList = this.essParamDao.getEssCheckParamList(paramMap) ;
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public int updateEssCheckParamInfo(HttpServletRequest request){
		int resultInt=0;
		try{
			Object obj=request.getParameterValues("PARAM_NOS");
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("CREATED_BY", admin.getAdminID());
			paramMap.put("PARAM_NOS", obj);	
			this.essParamDao.updateEssCheckParamInfo(paramMap);
			resultInt=1;
		}catch(Exception e){
			resultInt=0;
			e.printStackTrace();
		}
		return resultInt;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public List getOtConverParamList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				essParamDao.getOtConverParamList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;					
		}else{
			retrunList = essParamDao.getOtConverParamList(paramMap) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getOtConverParamCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		return essParamDao.getOtConverParamCnt(paramMap) ;
	}
	
	@SuppressWarnings("unchecked")
	public Object getOtConverParam(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		
		return this.essParamDao.getOtConverParam(paramMap) ; 
	}
	
	@SuppressWarnings("unchecked")
	public int addOtConverParamInfo(HttpServletRequest request){
		try{
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("CREATED_BY", admin.getAdminID());
			paramMap.put("CPNY_ID", admin.getCpnyId());
			
			this.essParamDao.addOtConverParamInfo(paramMap) ;
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int updateOtConverParamInfo(HttpServletRequest request) {
		try{
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("UPDATED_BY", admin.getAdminID()) ;
			paramMap.put("CPNY_ID", admin.getCpnyId());
			
			this.essParamDao.updateOtConverParamInfo(paramMap) ;
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int deleteOtConverParam(HttpServletRequest request) {
		try {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID()) ;
			paramMap.put("CPNY_ID", admin.getCpnyId());
			
			this.essParamDao.deleteOtConverParam(paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public List getVacationStandardManageList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				essParamDao.getVacationStandardManageList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;					
		}else{
			retrunList = essParamDao.getVacationStandardManageList(paramMap) ;
		}
		return retrunList ;
	}

	@Override
	public int getVacationStandardManageCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		return essParamDao.getVacationStandardManageCnt(paramMap) ;
	}

	@Override
	public List getWorkAreaList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunList = essParamDao.getWorkAreaList(paramMap) ;
		return retrunList ;
	}

	@Override
	public int saveVacationStandardManage(HttpServletRequest request) {
		try{
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("CREATED_BY", admin.getAdminID());
			paramMap.put("CPNY_ID", admin.getCpnyId());
			paramMap.put("STANDARD_EXPLAIN", paramMap.get("STANDARD_EXPLAIN").toString().replace("\n","<br>"));
			this.essParamDao.saveVacationStandardManage(paramMap) ;
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int deleteVacationStandardManage(HttpServletRequest request) {
		try {
			//AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
			//paramMap.put("UPDATED_BY", admin.getAdminID()) ;
			//paramMap.put("CPNY_ID", admin.getCpnyId());
			
			this.essParamDao.deleteVacationStandardManage(paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public Map getManageList(HttpServletRequest request) {
		Map returnMap = null;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		List returnList= this.essParamDao.getManageList(paramMap) ;
		if (returnList.size() > 0) {
			returnMap = (Map) returnList.get(0);
		}
		return returnMap;
	}

	@Override
	public int updateVacationStandardManage(HttpServletRequest request) {
		try{
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("UPDATED_BY", admin.getAdminID()) ;
			paramMap.put("CPNY_ID", admin.getCpnyId());
			paramMap.put("STANDARD_EXPLAIN", paramMap.get("STANDARD_EXPLAIN").toString().replace("\n","<br>"));
			this.essParamDao.updateVacationStandardManage(paramMap) ;
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
}

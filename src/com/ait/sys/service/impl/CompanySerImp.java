package com.ait.sys.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.CompanyDao;
import com.ait.sys.service.CompanySer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class CompanySerImp implements CompanySer {

	Logger logger = Logger.getLogger(CompanySerImp.class);
	
	@Autowired
	private CompanyDao companyDao;
	
	@SuppressWarnings("unchecked")
	public Object getCompanyItemInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		returnObj = companyDao.getCompany(paramMap) ;
		return returnObj ;
	}
	
	@SuppressWarnings("unchecked")
	public List getCompanyItemList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if (UiUtil.getPageNum(request) > 0){
			retrunList = companyDao.getCompanyList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ; 
		}else{
			retrunList = companyDao.getCompanyList(paramMap) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getAllCompanyItemList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		retrunList = companyDao.getCompanyList(paramMap) ;
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getCompanyItemAllList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		retrunList = companyDao.getCompanyItemAllList(paramMap) ;
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public int getCompanyItemCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		LinkedHashMap paramMap = this.setGetCompanyItemParam(request) ;
		retrunInt = companyDao.getCompanyListCnt(paramMap) ;
		return retrunInt ;
	}
	@SuppressWarnings("unchecked")
	public String getRoleID(HttpServletRequest request){
		String returnInt = "";
		Map paramMap = ObjectBindUtil.getRequestParamData(request); 
		returnInt = companyDao.getRoleID(paramMap);
		return returnInt;
	}
	
	@SuppressWarnings("unchecked")
	private LinkedHashMap setGetCompanyItemParam(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"search_") ;
		return paramMap ;
	}

	@SuppressWarnings("unchecked")
	public int addCompanyItemInfo(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		try {
			this.companyDao.addCompanyInfo(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int updateCompanyItemInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("UPDATED_BY", admin.getAdminID()) ;
		try {
			this.companyDao.updateCompanyInfo(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int checkCompanyIdExsit(HttpServletRequest request){
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return this.companyDao.checkCompanyIdExsit(paramMap) ;
	}
	
	@SuppressWarnings("unchecked")
	public int deleteCompanyItemInfo(HttpServletRequest request){
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
		try {
			this.companyDao.deleteCompanyInfo(paramMap) ;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1; 
	}
	
	@SuppressWarnings("unchecked")
	public List getHrOpeationList(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return companyDao.getHrOpeationList(paramMap);
	}
}

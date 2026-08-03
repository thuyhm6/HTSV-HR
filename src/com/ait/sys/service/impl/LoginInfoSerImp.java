package com.ait.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.LoginInfoDao;
import com.ait.sys.service.LoginInfoSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class LoginInfoSerImp implements LoginInfoSer {

	Logger logger = Logger.getLogger(LoginInfoSerImp.class);
	
	@Autowired
	private LoginInfoDao loginInfoDao;
	
	@SuppressWarnings("unchecked")
	public List getLoginInfoList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = this.getLoginInfoParamMap(request) ;
		if (UiUtil.getPageNum(request) > 0){
			retrunList = loginInfoDao.getLoginInfoList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;					
		}else{
			retrunList = loginInfoDao.getLoginInfoList(paramMap) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getLoginInfoCnt(HttpServletRequest request) {
		Map paramMap = this.getLoginInfoParamMap(request) ;
		return loginInfoDao.getLoginInfoCnt(paramMap) ;
	}
	
	// 数据,总条数,统一取得Map方法
	@SuppressWarnings("unchecked")
	private Map getLoginInfoParamMap(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		return paramMap ;
	}
	
}

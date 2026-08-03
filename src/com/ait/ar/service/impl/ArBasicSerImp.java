package com.ait.ar.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.ArBasicDao;
import com.ait.ar.service.ArBasicSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

@Service
public class ArBasicSerImp implements ArBasicSer {

	Logger logger = Logger.getLogger(ArBasicSerImp.class);
	
	@Autowired
	private ArBasicDao arBasicDao;
	
	@SuppressWarnings("unchecked")
	public List getArSearchEmployeeList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("AR_ADMIN_ID", ObjectUtils.toString(admin.getAdminID())) ;

		if (paramMap.get("page") != null && paramMap.get("pagesize") != null){
			retrunList = 
				arBasicDao.getArSearchEmployeeList(paramMap , 
							NumberUtils.parseNumber(ObjectUtils.toString(paramMap.get("page")), Integer.class), 
							NumberUtils.parseNumber(ObjectUtils.toString(paramMap.get("pagesize")), Integer.class) 
						) ;
		}
		else{
			retrunList = arBasicDao.getArSearchEmployeeList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public int getArSearchEmployeeCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("AR_ADMIN_ID", ObjectUtils.toString(admin.getAdminID())) ;

		retrunInt = arBasicDao.getArSearchEmployeeCnt(paramMap) ;
		
		return retrunInt ;
	}
	
	
}

package com.ait.ess.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import com.ait.ess.dao.PersonalPaInfoDao;
import com.ait.ess.service.PersonalPaInfoSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.ViewOptionDao;
import com.ait.sys.service.CompanySer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.ViewOptionUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PersonalPaInfoSerImp.java
 * @Description:
 * @Create date: 2012-5-23 下午06:18:31
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class PersonalPaInfoSerImp implements PersonalPaInfoSer {

	Logger logger = Logger.getLogger(PersonalPaInfoSerImp.class);
	
	@Autowired
	private ViewOptionDao viewOptionDao;
	@Autowired
	private PersonalPaInfoDao personalPaInfoDao;
	@Autowired
	private ViewOptionUtil viewOptionUtil;
	
	/**
	 * 个人考勤(make DataTable)
	 * @param request
	 * @return String
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public String makeDataTable(HttpServletRequest request, String menuNo) {
		
		String dataTable = "";
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("makeType", "pa");
		paramMap.put("ess", "1");
		
		paramMap.put("MENU_NO", menuNo);
		
		if(paramMap.get("essYear") != null && !"".equals(paramMap.get("essYear").toString())
				&& paramMap.get("essMonth") != null && !"".equals(paramMap.get("essMonth").toString())){
			
			paramMap.put("essMonth", paramMap.get("essYear").toString()+paramMap.get("essMonth").toString());
		}
		
		dataTable = viewOptionUtil.makeDataTable(paramMap);
		
		return dataTable;
	}
	
	/**
	 * 取工资增项(get AddPro List)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getAddProList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("PA_MONTH", request.getParameter("essYear")+request.getParameter("essMonth"));
		//paramMap.put("essGIVE_DATE", request.getParameter("essGIVE_DATE"));
		
		retrunList = personalPaInfoDao.getAddProList(paramMap) ;
		return retrunList ;
	}
	
	/**
	 * 取工资减项(get MiPro List)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getMiProList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("PA_MONTH", request.getParameter("essYear")+request.getParameter("essMonth"));
		//paramMap.put("GIVE_DATE", request.getParameter("essGIVE_DATE"));
		
		retrunList = personalPaInfoDao.getMiProList(paramMap) ;
		return retrunList ;
	}

	@SuppressWarnings("unchecked")
	public List getSalaryProvideDateEss(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("PA_MONTH", request.getParameter("essYear")+request.getParameter("essMonth"));
		
		retrunList = personalPaInfoDao.getSalaryProvideDateEss(paramMap) ;
		
		
		return retrunList ; 
	}

	@Override
	public int getSalaryDispark(HttpServletRequest request) {
		int returnInt=0;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CurrentOperator", request.getSession().getAttribute("CurrentOperator"));
		 returnInt = personalPaInfoDao.getSalaryDispark(paramMap) ;
		return returnInt;
	}
	
	@Override
	public int getSalaryDisparkNew(Map paramMap) {
		int returnInt=0;
		returnInt = personalPaInfoDao.getSalaryDispark(paramMap) ;
		return returnInt;
	}
}

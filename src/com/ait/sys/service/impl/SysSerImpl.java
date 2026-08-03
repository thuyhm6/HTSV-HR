package com.ait.sys.service.impl;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.sys.bean.AdminBean;
import com.ait.sys.bean.CodeBean;
import com.ait.sys.dao.SysDao;
import com.ait.sys.service.SysSer;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;


@Service
public class SysSerImpl implements SysSer {
	
	Logger logger = Logger.getLogger(SysSerImpl.class);
	
	@Autowired
	private SysDao sysDao;

	@SuppressWarnings("unchecked")
	public void updateModel(HttpServletRequest request) {
		
		List list = new ArrayList();
		Enumeration en = request.getParameterNames();
		while (en.hasMoreElements()) {
			
			String key = (String) en.nextElement();
			if(key.endsWith("Model")){
				Map user= new LinkedHashMap();
				user.put("mtype", request.getParameter("mtype"));
				user.put("mjsp", request.getParameter("mjsp"));
				user.put("mname", key);
				user.put("mcontent", request.getParameter(key));
				
				logger.info("key:"+user.get("mname"));
				logger.info("value:"+user.get("mcontent"));
				
				list.add(user);
			}
		}

		sysDao.deleteModel(list);
		sysDao.insertModel(list);
	}

	@SuppressWarnings("unchecked")
	public List getModel(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("mtype", request.getParameter("mtype"));
		paramMap.put("mjsp", request.getParameter("mjsp"));
		
		logger.info("mtype:"+ paramMap.get("mtype"));
		logger.info("mjsp:"+ paramMap.get("mjsp"));
		
		return sysDao.getModel(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPosition(Object object) {
		return sysDao.getPosition(object);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPostGrade(Object object) {
		return sysDao.getPostGrade(object);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPostGroup(Object object) {
		return sysDao.getPostGroup(object);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getPost(Object object) {
		return sysDao.getPost(object);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getLanguage(String object) {
		return sysDao.getLanguage(object);
	}
	
	@Override
	public List<CodeBean> getParentCodeNo() {
		return sysDao.getParentCodeNo();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getCode() {
		return sysDao.getCode();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getCodeLanguage() {
		return sysDao.getCodeLanguage();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getCodeParamList() {
		return sysDao.getCodeParamList();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List getSelectTable(Object object) {
		return sysDao.getSelectTable(object);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List getSelectTableByHrDept(Object object){
		return sysDao.getSelectTableByHrDept(object);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List getDeptListByCpnyID(Object object) {
		// TODO Auto-generated method stub
		return sysDao.getDeptListByCpnyID(object);
	}
}

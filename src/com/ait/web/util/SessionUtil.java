package com.ait.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ait.sys.bean.AdminBean;


public class SessionUtil {

	private static SessionUtil instance;
	
	public SessionUtil() {
		if (instance == null) {
			getInstance();
		}
	}

	public static SessionUtil getInstance() {
		return new SessionUtil();
	}
	
	/**
	 * get session LoginUser
	 * 
	 * @param req
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public static AdminBean getLoginUserFromSession(HttpServletRequest request) {
		// session用户信息
		HttpSession session = request.getSession() ;
		/*
		 * SY_ADMIN_V,从这个视图中提取信息
		*/
		AdminBean admin = (AdminBean)session.getAttribute("LoginUser") ;
		
		return admin ;
	}
	
}

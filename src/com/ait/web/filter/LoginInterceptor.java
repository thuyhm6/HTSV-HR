package com.ait.web.filter;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ait.sys.bean.AdminBean;
import com.ait.web.messages.Messages;
import com.ait.web.util.SessionUtil;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	Logger logger = Logger.getLogger(LoginInterceptor.class);
	
	private String requestUrl = null;

	private String queryString = null;
	
	@SuppressWarnings("unchecked")
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		boolean flag=false;
		
		String url = request.getServletPath().toString();
		
		requestUrl = request.getRequestURI();
		queryString = request.getQueryString();
		
		if(logger.isDebugEnabled()){
			// 数据基本request信息
			// parameter list
			logger.debug("request RemoteHost " + request.getRemoteHost());
			logger.debug("request URI : " + requestUrl);
			Enumeration parameters = request.getParameterNames();
			while (parameters.hasMoreElements()) {
				String parameter = parameters.nextElement().toString();
				logger.debug("Parameter: " + parameter + " = " + request.getParameter(parameter) );
			}
		
			/*
			// attribute list
			Enumeration attributes = request.getAttributeNames();
			while (attributes.hasMoreElements()) {
				String attribute = attributes.nextElement().toString();
				Logger.getLogger(getClass()).debug("Attribute: " + attribute + " = " + request.getParameter(attribute) );
			}
			*/
		}
		
		if(url.indexOf("/login/in") == 0 || url.indexOf("/resources/") == 0 || url.length() == 1 || url.indexOf("/androidInterface")==0 || url.indexOf("/LGEP/affirm/")==0){				
			flag = true;			
		}else{
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			if(admin != null && admin.getUsername() != null){
				flag = true;
			}else{
				request.setAttribute("msg",Messages.getMessage(request, "loginAgain"));
				request.getRequestDispatcher("/").forward(request, response); 
			}
		}
		
		return true ;
		
		//return flag;
	}
}
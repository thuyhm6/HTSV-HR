package com.ait.web.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class UserAuthenFilter implements Filter {
	private FilterConfig filterConfig;// 保留参数

	private static long counter = 0;

	private String requestUri = null;

	private String queryString = null;
	
	public void init(FilterConfig a_filterConfig) throws ServletException {
		filterConfig = a_filterConfig;
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		/**
		 * 输出用户request中的新系列表用于调试,也可用户基本验证
		 */

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
//		HttpSession session = request.getSession();
		
		//CharacterEncoding
//		String encoding = StringUtils.defaultIfEmpty(filterConfig.getInitParameter("encoding"), "UTF-8");
//		request.setCharacterEncoding(encoding);
		
		
//		requestUri = request.getRequestURI();
//		queryString = request.getQueryString();
//
//		if (!requestUri.startsWith("/images") && !requestUri.startsWith("/css")) {
//			Logger.getLogger(getClass()).debug("run sequence [" + ++counter + "] " + request.getRemoteHost());
//			Logger.getLogger(getClass()).debug("request URI : " + requestUri);
//			if (queryString != null)
//				Logger.getLogger(getClass()).debug("query string : " + queryString);
//		}
//
//		Enumeration parameters = request.getParameterNames();
//		while (parameters.hasMoreElements()) {
//			String parameter = parameters.nextElement().toString();
//			
//		}
//
//		// attribute list
//		Enumeration attributes = request.getAttributeNames();
//		while (attributes.hasMoreElements()) {
//			String attribute = attributes.nextElement().toString();
//			Logger.getLogger(getClass()).debug("Attribute: " + attribute + " = " + request.getParameter(attribute));
//		}

		if (request.getRequestURI().equals("/index.jsp")) {
			Logger.getLogger(getClass()).debug("User Session Timed out");
			RequestDispatcher dispatcher = filterConfig.getServletContext().getRequestDispatcher("/");
			dispatcher.forward(request, response);
		} else {

			chain.doFilter(request, response);
		}
		// /user login validate end
	}

	public void destroy() {
	}
}
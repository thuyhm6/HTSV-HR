package com.ait.web.util;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.ui.ModelMap;

/**
 * 分页AOP
 * @author fan
 *
 */
@Aspect
public class PageInterceptor {
		//切入点过滤条件
		@After("execution(* com.ait.*.action.*.view*List(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse,org.springframework.ui.ModelMap)) and args(request,response,modelMap)" +
			   "||execution(* com.ait.*.action.*.*.view*List(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse,org.springframework.ui.ModelMap)) and args(request,response,modelMap)")
		public void invokeMethod(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Throwable{
			
			modelMap.put(UiUtil.PAGE_NUM_NAME, UiUtil.getPageNum(request)) ;
			if(request.getParameter("itemType") != null){
				modelMap.put(UiUtil.NUM_PER_PAGE_NAME, UiUtil.getNumPerPage2(request));
			}else if(request.getParameter("specialNumPerPage") != null){
				modelMap.put(UiUtil.NUM_PER_PAGE_NAME, UiUtil.getNumPerPage(request,checkNumber(request.getParameter("specialNumPerPage"))));
			}else{
				modelMap.put(UiUtil.NUM_PER_PAGE_NAME, UiUtil.getNumPerPage(request));
			}
			if(!"LGEP".equals(request.getParameter("LGEP"))){
				modelMap.addAllAttributes(ObjectBindUtil.getRequestParamData(request,"seach_"));
			}
		}
		
		private int checkNumber(String temp){
			int result = 50 ;
			try {
				result = Integer.parseInt(temp);
			} catch (Exception e) {
				// TODO: handle exception
				result = 50;
			}
			return result ;
		}
}

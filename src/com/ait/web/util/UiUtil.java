package com.ait.web.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.util.NumberUtils;


public class UiUtil {

	private static UiUtil instance;
	
	public static String TOTAL_COUNT_NAME = "totalCount" ;
	
	public static String PAGE_NUM_NAME = "pageNum" ;
	
	public static String NUM_PER_PAGE_NAME = "numPerPage" ;
	
	public static String ORDER_FIELD_NAME = "orderField" ;
	
	public static String ORDER_DIRECTION = "orderDirection" ;
	
	public UiUtil() {
		if (instance == null) {
			getInstance();
		}
	}

	public static UiUtil getInstance() {
		return new UiUtil();
	}
	
	public static int getPageNum(HttpServletRequest request){
		if(request.getParameter(PAGE_NUM_NAME) != null && request.getParameter(PAGE_NUM_NAME) != "" && !"".equals(request.getParameter(PAGE_NUM_NAME)) && !"undefined".equals(request.getParameter(PAGE_NUM_NAME))){
			return NumberUtils.parseNumber(ObjectUtils.toString(request.getParameter(PAGE_NUM_NAME)), Integer.class) ;
		}
		else{
			return 0 ;
		}
	}
	
	public static int getNumPerPage(HttpServletRequest request){
		
		if(request.getParameter(NUM_PER_PAGE_NAME) != null && request.getParameter(NUM_PER_PAGE_NAME) != "" && !"".equals(request.getParameter(NUM_PER_PAGE_NAME)) && !"undefined".equals(request.getParameter(NUM_PER_PAGE_NAME))){
			return NumberUtils.parseNumber(ObjectUtils.toString(request.getParameter(NUM_PER_PAGE_NAME)), Integer.class) ;
		}
		else{
			return 50 ;
		}

	}

	
	public static int getNumPerPage2(HttpServletRequest request){
		
		if(request.getParameter(NUM_PER_PAGE_NAME) != null && request.getParameter(NUM_PER_PAGE_NAME) != "" && !"".equals(request.getParameter(NUM_PER_PAGE_NAME)) && !"undefined".equals(request.getParameter(NUM_PER_PAGE_NAME))){
			return NumberUtils.parseNumber(ObjectUtils.toString(request.getParameter(NUM_PER_PAGE_NAME)), Integer.class) ;
		}
		else{
			return 20 ;
		}

	}
	public static int getNumPerPage(HttpServletRequest request,int defaultNum){
		
		if(request.getParameter(NUM_PER_PAGE_NAME) != null && request.getParameter(NUM_PER_PAGE_NAME) != "" && !"".equals(request.getParameter(NUM_PER_PAGE_NAME)) && !"undefined".equals(request.getParameter(NUM_PER_PAGE_NAME))){
			return NumberUtils.parseNumber(ObjectUtils.toString(request.getParameter(NUM_PER_PAGE_NAME)), Integer.class) ;
		}
		else{
			return defaultNum ;
		}

	}
	public static String getOrderField(HttpServletRequest request){
		
		if(request.getParameter(ORDER_FIELD_NAME) != null && request.getParameter(ORDER_FIELD_NAME) != ""){
			return request.getParameter(ORDER_FIELD_NAME) ;
		}
		else{
			return "" ;
		}

	}
	//控制页面相反排序 升降 值要与页面class一致
	public static String getOrderDirection(HttpServletRequest request){
		
		if(request.getParameter(ORDER_DIRECTION) != null && request.getParameter(ORDER_DIRECTION) != ""){
			return request.getParameter(ORDER_DIRECTION);
		}
		else{
			return "desc" ;
		}

	}
}

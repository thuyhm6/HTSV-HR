<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ait.sys.bean.AdminBean"%>
<%@page import="com.ait.web.util.SessionUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

AdminBean admin = SessionUtil.getLoginUserFromSession(request);
if(admin==null)
	response.sendRedirect("/index.html");
else{
	response.sendRedirect("/openreports/login.action?userName="+admin.getUsername()+"&password="+admin.getPassword());
	}
%>
<%@ page language="java" import="java.util.*;com.ait.sys.bean.AdminBean" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<%
		HttpSession hs=  request.getSession();
		hs.getAttribute("LoginUser");


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'check.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <script>
  	function test(){
  		alert(1);
  		<%
  			AdminBean  a=(AdminBean)hs.getAttribute("LoginUser");
  		
  			if(hs.getAttribute("LoginUser")==null){
  		%>
  			alert("过期");
  		<%
  			}else{
  		%>
  			alert(1222);
  		<%}%>
  		setTimeout("test()",1000*61);
  	}
  
  
  </script>
  
  <body onload="test()">
    This is my JSP page. <br>
  </body>
</html>

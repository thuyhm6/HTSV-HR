 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--员工在职证明--%>
	<spring:message code="rp.report.title.certificateofemployee"/>
</title>
<style type="text/css">
	td {
		text-align: center;
	}
</style>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=BuMenKaoQinBiao.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%"  cellpadding="0" cellspacing="0">
  <tr>
  	<td>工号</td>
  	<td>姓名</td>
  	<td>部门</td>
  	<c:forEach items="${aliasNameList}" var="item">
  		<td>${item}</td>
  	</c:forEach>
  	
  </tr>
  
</table>
  	<c:forEach items="${returnList}" var="temp">
  	<table >
  	<tr>
  		<td>${temp.EMPID}</td>
  		<td>${temp.LOCAL_NAME}</td>
  		<td>${temp.DEPTNAME}</td>
  		<c:forEach items="${temp.SHIFT_NAME}" var="temp2">
  		<td>${temp2}</td>
  		</c:forEach>
  	</table>	
  	</c:forEach>
</body>
</html>
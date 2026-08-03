<%@ page contentType="application/msexcel" pageEncoding="UTF-8"%>
<%@ include file="../../inc/initTaglibs.jsp"%>
<% response.setHeader("Content-disposition","inline; filename=HumanResourcesDetail.xls"); %>
<html>
  <head>
  	<style>
  		table{border-color:#5588AA;}
		table tr{height:25px;}
		table td{border-color:#5588AA}
	</style>
  </head> 
  <body>	
	<table border="1">
		<tr>
			<td align="center" colspan="9">人力资源情况表${info.param.year}年${info.param.month}月</td>
		</tr>
		<tr>
			<td align="center" style="width:100px">行号</td>
			<td align="center" style="width:100px">姓名</td>
			<td align="center" style="width:100px">性别</td>
			<td align="center" style="width:100px">年龄</td>
			<td align="center" style="width:100px">学位</td>
			<td align="center" style="width:100px">司龄</td>
			<td align="center" style="width:100px">员工区分</td>
			<td align="center" style="width:120px">入职日期</td>
			<td align="center" style="width:120px">离职日期</td>
		</tr> 
		<c:forEach var="data" items="${info.Rows}">		 
	       	<tr>
	       		<td align="center">${data.EMPID}</td>
	       		<td align="center">${data.CHINESENAME}</td>
	       		<td align="center">${data.SEX}</td>
	       		<td align="center">${data.AGE}</td>
	       		<td align="center">${data.DEGREE}</td>
	       		<td align="center">${data.WORKAGE}</td>
	       		<td align="center">${data.EMPTYPE}</td>
	       		<td align="center" style="mso-number-format:'\@'">${data.DATESTARTED}</td>
	       		<td align="center" style="mso-number-format:'\@'">${data.DATELEFT}</td>	       		
			</tr>
		</c:forEach>	     	 	
	</table>
  </body>
</html>
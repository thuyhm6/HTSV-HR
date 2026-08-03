<%@ page contentType="application/msexcel" pageEncoding="UTF-8"%>
<%@ include file="../../inc/initTaglibs.jsp"%>
<% response.setHeader("Content-disposition","inline; filename=HumanResourcesSum.xls"); %>
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
			<td align="center" colspan="4">人力资源情况表${info.param.year}年${info.param.month}月</td>
		</tr> 
		<c:forEach var="data" items="${info.Rows}">		 
	       	<tr>
	       		<td align="center"
	       			<c:if test="${data.COL eq 0}">style="width:150px" colspan="1"</c:if>
	       			<c:if test="${data.COL eq 3}">colspan="4"</c:if>
				>${data.RNAME}</td>	
	       		<c:if test="${data.COL eq 0}">
	       			<td align="center" colspan="3">${data.CNT}</td>
				</c:if>
				<c:if test="${data.COL eq 1}">
	       			<td align="center" style="width:150px">${data.CNT}</td>
	    		    <td align="center" style="width:150px">所占比例</td>
	    		    <td align="center" style="width:150px">${data.BL}%</td>
				</c:if>
			</tr>
		</c:forEach>	     	 	
	</table>
  </body>
</html>

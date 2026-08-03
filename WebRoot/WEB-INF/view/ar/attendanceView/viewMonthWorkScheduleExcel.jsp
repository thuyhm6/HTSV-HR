 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><%--月上班日程信息--%>
			月上班日程信息
		</title>
	</head>                              
	<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=workMonthSchdule.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
    	<table width="100%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td align="center" colspan="22" >
	    			<b><font size="+2">月上班日程信息</font></b>
	    		</td>
	    	</tr>
			<tr>
			    <th style="text-align: center">序号</th>
				<th style="text-align: center">部门</th>
				<th style="text-align: center">工号</th>
				<th style="text-align: center">姓名</th>
				<th style="text-align: center">职位</th>
				<th style="text-align: center">职级名称</th>
				  
				<c:forEach items="${monthDay}" var="ma" varStatus="i">
				<th style="text-align: center">${ma.MONTHDAY}</th>	
			    </c:forEach>
				
			</tr>    
	       	<c:forEach items="${monthWorkSchedule}" var="pa" varStatus="i">
				<tr>
					<td style="text-align: center">${i.index+1 }</td>
					<td style="text-align: center">${pa.DEPT_NAME}</td>
					<td style="text-align: center">${pa.EMPID}</td>
					<td style="text-align: center">${pa.LOCAL_NAME}</td>
					<td style="text-align: center">${pa.POSITION_NAME}</td>
					<td style="text-align: center">${pa.DUTY_NAME}</td>
					
					<c:forEach items="${pa.monthDay}" var="monthDay" varStatus="i">
					<td style="text-align: center">${monthDay.BANCI}</td>
					</c:forEach> 
				</tr>
			</c:forEach>
      </table>  
	</body>
</html>
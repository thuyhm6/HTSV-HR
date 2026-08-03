 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><%--员工特殊值信息--%>
			员工特殊值信息
		</title>
	</head>                              
	<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=paInfoByItem.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
    	<table width="45%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td align="center" colspan="12" >
	    			<b><font size="+2">员工特殊值信息</font></b>
	    		</td>
	    	</tr>
			<tr>
				<th style="text-align: center">序号</th>
				<th style="text-align: center">公司</th>
				<th style="text-align: center">工资月</th>
				<th style="text-align: center">工资发放日</th>
				<th style="text-align: center">项目名称</th>
				
				<th style="text-align: center">工号</th>
				<th style="text-align: center">姓名</th>
				<th style="text-align: center">部门</th>
				<th style="text-align: center">开始月</th>
				<th style="text-align: center">结束月</th>
				
				<th style="text-align: center">值</th>
				<th style="text-align: center">备注</th>
			</tr>    
	       	<c:forEach items="${paInfoByItemList}" var="pa" varStatus="i">
				<tr target="EMPID" rel="${pa.EMPID}">
					<td style="text-align: center">${i.index+1 }</td>
					<td style="text-align: center">${pa.COMPANY_NAME}</td>
					<td style="text-align: center">${pa.PA_MONTH}</td>
					<td style="text-align: center">${pa.GIVE_DATE}</td>
					<td style="text-align: center">${pa.ITEM_NAME}</td>
					
					<td style="text-align: center">${pa.EMPID}</td>
					<td style="text-align: center">${pa.LOCAL_NAME}</td>
					<td style="text-align: center">${pa.DEPT_NAME}</td>
					<td style="text-align: center">${pa.START_MONTH}</td>
					<td style="text-align: center">${pa.END_MONTH}</td>
					
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.SALARY}</td>
					<td style="text-align: center">${pa.MARK}</td>
				</tr>
			</c:forEach>
      </table>  
	</body>
</html>
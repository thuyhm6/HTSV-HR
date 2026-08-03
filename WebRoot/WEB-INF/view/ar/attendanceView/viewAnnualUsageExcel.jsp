 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><%--年假使用现状--%>
			年假使用现状
		</title>
	</head>                              
	<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=annualUsedAndRemainInfo.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>       
      <table width="100%" border="2" align="center" cellpadding="0" cellspacing="0" style="padding: 2px 2px 2px 2px;border-collapse:collapse" >
		<thead>
			<tr>
				<th style="text-align: center" colspan="10">年假</th>								
			</tr>
			<tr>
				<th style="text-align: center" rowspan="2">社号/姓名</th>				
				<th style="text-align: center" rowspan="2">总年假</th>
				<th style="text-align: center" colspan="2">法定年假</th>				
				<th style="text-align: center" colspan="2">福利年假</th>
				<th style="text-align: center" rowspan="2">已用总天数</th>
				<th style="text-align: center" rowspan="2">剩余总年假</th>				
				<th style="text-align: center" colspan="2">剩余天数</th>				
			</tr>
			<tr>
				<th style="text-align: center">本年年假</th>				
				<th style="text-align: center">移年年假</th>
				<th style="text-align: center">福利年假</th>				
				<th style="text-align: center">福利年假调整</th>
				<th style="text-align: center">剩余法定年假天数</th>
				<th style="text-align: center">剩余福利年假天数</th>						
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${annualUsageList}" var="annual" varStatus="i">				
				<tr>
					<td style="text-align: center">${annual.EMPID }/${annual.LOCAL_NAME }</td>					
					<td style="text-align: center">${annual.TOTAL_VAC }</td>
					<td style="text-align: center">${annual.TOT_VAC_CNT1 }</td>
					<td style="text-align: center">${annual.LAST_YEAR_VAC1 }</td>
					<td style="text-align: center">${annual.TOT_VAC_CNT2  }</td>
					<td style="text-align: center">${annual.ADD_VAC }</td>					
					<td style="text-align: center">${annual.USE_FD_VAC + annual.USE_FL_VAC }</td>
					<td style="text-align: center">${(annual.TOT_VAC_CNT1 + annual.TOT_VAC_CNT2) - (annual.USE_FD_VAC + annual.USE_FL_VAC) }</td>
					<td style="text-align: center">${annual.TOT_VAC_CNT1 + annual.LAST_YEAR_VAC1 - annual.USE_FD_VAC} </td>					
					<td style="text-align: center">${annual.TOT_VAC_CNT2 + annual.ADD_VAC - annual.USE_FL_VAC}</td>
				</tr>				
			</c:forEach>    
		</tbody>
	</table>
       
	</body>
</html>
 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>月考勤监控导出</title>
<style type="text/css">
	td {
		text-align: center;
		font-size: 13;
	}
</style>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=MonthWorkControlExcel.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%" border="1" cellpadding="0" cellspacing="0">
	<tr>

		<td class="${orderDirection}" style="text-align: center" width="10%" rowspan=1>社号</td>
		<td class="${orderDirection}" style="text-align: center" width="10%" rowspan=1>姓名</td>
		<td class="${orderDirection}" style="text-align: center" width="19%" rowspan=1>部门</td>
		<td class="${orderDirection}" style="text-align: center" width="10%" rowspan=1>考勤日期</td>
		<td class="${orderDirection}" style="text-align: center" width="10%" rowspan=1>开始时间</td>
		<td class="${orderDirection}" style="text-align: center" width="10%" rowspan=1>结束时间</td>
		<td class="${orderDirection}" style="text-align: center" width="10%" rowspan=1>考勤项目</td>
		<td class="${orderDirection}" style="text-align: center" width="10%" rowspan=1>时长</td>
	</tr>
	
	<c:forEach items="${dataList}" var="month">
		<tr>
				<td class="${orderDirection}" style="text-align: center" width="10%">${month.EMPID}</td>
				<td class="${orderDirection}" style="text-align: center" width="10%">${month.PERSONNAME}</td>
				<td class="${orderDirection}" style="text-align: center" width="10%">${month.DEPTNAME}</td>
				<td class="${orderDirection}" style="text-align: center" width="10%">${month.AR_DATE_STR}</td>
				<td class="${orderDirection}" style="text-align: center" width="10%">${month.FROM_TIME}</td>
				<td class="${orderDirection}" style="text-align: center" width="10%">${month.TO_TIME}</td>
				<td class="${orderDirection}" style="text-align: center" width="10%">${month.ITEM_NAME}</td>
				<td class="${orderDirection}" style="text-align: center" width="10%">${month.QUANTITY}</td>

		</tr>
	</c:forEach>
</table>
</body>
</html>
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
		<td class="${orderDirection}" style="text-align: center" width="10%" rowspan=2>姓名</td>
		<td class="${orderDirection}" style="text-align: center" width="19%" rowspan=2>部门</td>
		<td class="${orderDirection}" style="text-align: center" width="10%" rowspan=2>开始日期</td>
		<td class="${orderDirection}" style="text-align: center" width="10%" rowspan=2>结束日期</td>
		<td class="${orderDirection}" style="text-align: center" width="10%" colspan=2>${controlMap[ITEM1]}</td>
		<td class="${orderDirection}" style="text-align: center" width="10%" colspan=2>${controlMap[ITEM2]}</td>
		<td class="${orderDirection}" style="text-align: center" width="10%" colspan=2>${controlMap[ITEM3]}</td>
		<td class="${orderDirection}" style="text-align: center" width="10%" colspan=2>${controlMap[ITEM4]}</td>
		<td class="${orderDirection}" style="text-align: center" width="10%" colspan=2>${controlMap[ITEM5]}</td>
	</tr>
	<tr>
		<td class="${orderDirection}" style="text-align: center" width="5%">排名</td>
		<td class="${orderDirection}" style="text-align: center" width="5%">次数</td>
		<td class="${orderDirection}" style="text-align: center" width="5%">排名</td>
		<td class="${orderDirection}" style="text-align: center" width="5%">次数</td>
		<td class="${orderDirection}" style="text-align: center" width="5%">排名</td>
		<td class="${orderDirection}" style="text-align: center" width="5%">次数</td>
		<td class="${orderDirection}" style="text-align: center" width="5%">排名</td>
		<td class="${orderDirection}" style="text-align: center" width="5%">次数</td>
		<td class="${orderDirection}" style="text-align: center" width="5%">排名</td>
		<td class="${orderDirection}" style="text-align: center" width="5%">次数</td>
	</tr>
	<c:forEach items="${dataList}" var="month">
		<tr>
			<td class="${orderDirection}" style="text-align: center" width="10%">${month.PERSONNAME}</td>
			<td class="${orderDirection}" style="text-align: center" width="19%">${month.DEPTNAME}</td>
			<td class="${orderDirection}" style="text-align: center" width="10%">${START_DATE}</td>
			<td class="${orderDirection}" style="text-align: center" width="10%">${END_DATE}</td>
			<td class="${orderDirection}" style="text-align: center" width="5%">${month.DESC1}</td>
			<td class="${orderDirection}" style="text-align: center" width="5%">${month.ITEM_NO1}</td>
			<td class="${orderDirection}" style="text-align: center" width="5%">${month.DESC2}</td>
			<td class="${orderDirection}" style="text-align: center" width="5%">${month.ITEM_NO2}</td>
			<td class="${orderDirection}" style="text-align: center" width="5%">${month.DESC3}</td>
			<td class="${orderDirection}" style="text-align: center" width="5%">${month.ITEM_NO3}</td>
			<td class="${orderDirection}" style="text-align: center" width="5%">${month.DESC4}</td>
			<td class="${orderDirection}" style="text-align: center" width="5%">${month.ITEM_NO4}</td>
			<td class="${orderDirection}" style="text-align: center" width="5%">${month.DESC5}</td>
			<td class="${orderDirection}" style="text-align: center" width="5%">${month.ITEM_NO5}</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>
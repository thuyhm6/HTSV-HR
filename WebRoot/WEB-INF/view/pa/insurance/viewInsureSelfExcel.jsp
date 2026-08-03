 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>保险查看(个人别)</title>
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
		response.setHeader("Content-Disposition", "attachment; filename=viewInsureSelfExcel.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%" border="1" cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="18" style="text-align: center">保险基本信息</td>
	</tr>
	<tr>
		<td rowspan="2" style="text-align: center">部门</td>
		<td rowspan="2" style="text-align: center">姓名/工号</td>
		<td rowspan="2" style="text-align: center">考勤月</td>
		<td rowspan="2" style="text-align: center">福利地区</td>
		<td colspan="10" style="text-align: center">社会保险</td>
		<td colspan="2" style="text-align: center">公积金</td>
		<td colspan="2" style="text-align: center">合计</td>
	</tr>
	<tr>
		<td style="text-align: center">养老(公司)</td>
		<td style="text-align: center">失业(公司)</td>
		<td style="text-align: center">医疗(公司)</td>
		<td style="text-align: center">工伤(公司)</td>
		<td style="text-align: center">生育(公司)</td>
		<td style="text-align: center">养老(个人)</td>
		<td style="text-align: center">失业(个人)</td>
		<td style="text-align: center">医疗(个人)</td>
		<td style="text-align: center">社保(公司)</td>
		<td style="text-align: center">社保(个人)</td>
		<td style="text-align: center">公积金(公司)	</td>
		<td style="text-align: center">公积金(个人)	</td>
		<td style="text-align: center">五险一金(公司)</td>
		<td style="text-align: center">五险一金(个人)</td>
	</tr>
	<c:forEach items="${dataList}" var="person">
		<tr>
			<td style="text-align: center" width="6%">${person.DEPT_NAME}</td>
			<td style="text-align: center" width="6%">${person.EMPNAME}</td>
			<td style="text-align: center" width="6%">${person.IS_MONTH}</td>
			<td style="text-align: center" width="6%">${person.INSRAREA_ID_NAME}</td>
			<td style="text-align: center" width="6%">${person.IS_ENDOWMENT_COR}</td>
			<td style="text-align: center" width="6%">${person.IS_UNEMPLOY_COR}</td>
			<td style="text-align: center" width="6%">${person.IS_MEDICAL_COR}</td>
			<td style="text-align: center" width="6%">${person.IS_INJURY_COR}</td>
			<td style="text-align: center" width="6%">${person.IS_FERTILITY_COR}</td>
			<td style="text-align: center" width="6%">${person.IS_ENDOWMENT_PER}</td>
			<td style="text-align: center" width="6%">${person.IS_UNEMPLOY_PER}</td>
			<td style="text-align: center" width="6%">${person.IS_MEDICAL_PER}</td>
			<td style="text-align: center" width="6%">${person.IS_TOTAL_COR}</td>
			<td style="text-align: center" width="6%">${person.IS_TOTAL_PER}</td>
			<td style="text-align: center" width="6%">${person.IS_FUND_COR}</td>
			<td style="text-align: center" width="6%">${person.IS_FUND_RER}</td>
			<td style="text-align: center" width="6%">${person.COR}</td>
			<td style="text-align: center" width="6%">${person.PER}</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>
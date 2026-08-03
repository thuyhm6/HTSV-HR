
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>营业员提成导入结果Excel导出</title>
</head>
<body>
	<%
		response.setHeader("Content-Type",
				"application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition",
				"attachment; filename=salesmanIncentiveCalcImpList.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0");
	%>
	<table width="60%" border="0" align="center" cellpadding="0"
		cellspacing="0">
		<tr>
			<td>
				<table class="table" width="100%" layoutH="150">
					<thead>
						<tr>
							<th width="20%"><spring:message code="ar.excelexport.title.month" /> <!--月份--></th>
							<th width="20%"><spring:message code="inct.salesman.empNo" /> <!--社号--></th>
							<th width="20%"><spring:message code="inct.salesman.adjustInct" /><!--调整提成--></th>
							<th width="20%"><spring:message code="inct.salesman.remark" /><!--备注--></th>
							<th width="20%"><spring:message code="inct.salesman.validateMessage" /> <!--验证结果--></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${itemList}" var="mdata" varStatus="i">
							<tr>
								<td class='td_center'>${mdata.INCTV_MON}</td>
								<td class='td_center'>${mdata.EMPNO}</td>
								<td class='td_right'>${mdata.ADJST_AMT}</td>
								<td>${mdata.REMARK}</td>
								<td>${mdata.UPLOAD_ERROR_MSG}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
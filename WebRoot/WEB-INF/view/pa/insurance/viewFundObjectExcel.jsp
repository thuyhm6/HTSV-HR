<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--公积金计算对象--%>
	公积金计算对象
</title>
</head>                              
	<body>
   	<%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=fundCalcObject.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   	%>
		<table width="45%" border="1" cellpadding="0" cellspacing="0">
			<tr>
				<th width="5%"><!--序号-->
					<spring:message code="ar.viewcycle.title.xuhao"/>
				</th>
				<th width="10%"><!--工资月-->
					<spring:message code="pa.insurance.title.salaryMonth"/>
				</th>
				<th width="10%"><!--工号-->
					<spring:message code="public.title.empId"/>
				</th>
				<th width="10%"><!--人事姓名-->
					<spring:message code="pa.title.message.empHrmName"/>
				</th>
				<th width="25%"><!--部门-->
					<spring:message code="public.title.deptName"/>
				</th>
				<th width="10%"><!--入司日期-->
					<spring:message code="pa.insurance.title.entryCpmpanyDate"/>
				</th>
				<th width="10%"><!--离职日期-->
					<spring:message code="pa.insurance.title.resignDate"/>
				</th>
				<th width="10%"><!--计算标识-->
					<spring:message code="pa.wagebase.title.caculateFlag"/>
				</th>
			</tr>
			<c:forEach items="${isCalcList}" var="item" varStatus="i">
				<tr target="sid" rel="${item.PERSON_ID}">
					<td>${i.index+1 }</td>
					<td>${item.PA_MONTH }</td>
					<td>${item.EMPID}</td>
					<td>${item.CHINESE_NAME}</td>
					<td>${item.DEPTNAME}</td>
					<td>${item.JOIN_COMPANY_DATE}</td>
					<td>${item.DATE_LEFT}</td>
					<td>${item.CALC_FLAG }</td>
				  </tr>
			</c:forEach>
		</table>
	</body>
</html>	
 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--员工在职证明--%>
	<spring:message code="rp.report.title.certificateofemployee"/>
</title>
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
		response.setHeader("Content-Disposition", "attachment; filename=CanBaoRenYuanBiao.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%" border="1" cellpadding="0" cellspacing="0">
			<tr>				
				<th width="11%" orderField="EMPID" class="${orderDirection}">
					<spring:message code="public.title.empId"/><!--工号--></th>
				<th width="11%" orderField="nlssort(LOCAL_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="public.title.name"/><!--姓名--></th>
				<th width="11%" orderField="nlssort(DEPT_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="public.title.deptName"/><!--部门--></th>
				<th width="11%" orderField="nlssort(POST_GRADE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="pa.insurance.title.postGrade"/><!--职级--></th>
				<th width="11%" orderField="nlssort(STATUS_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="liang.hr.viewPersonalInfo.title.WHETHER_OR_NOT_TO_TRY"/><!-- 试用与否 --></th>
				<th width="11%" orderField="HR.JOIN_COMPANY_DATE" class="${orderDirection}">
					<spring:message code="pa.insurance.title.entryCpmpanyDate"/><!--入司日期--></th>
				<th width="11%" orderField="HR.DATE_LEFT" class="${orderDirection}">
					<spring:message code="pa.insurance.title.resignDate"/><!--离职日期--></th>
				<th width="11%" orderField="HRE.SETTLEMENT_DATE" class="${orderDirection}">
					<spring:message code="pa.insurance.title.salaryCaculateDate"/><!--工资结算日期--></th>
				<th width="11%" orderField="IS_CALC_FLAG" class="${orderDirection}">
					<spring:message code="pa.insurance.title.ifCaculateInsuranse"/><!--是否计算保险--></th>
			</tr>
			<c:forEach items="${isPersonnelList}" var="isPersonnel" varStatus="i">			
				<tr target="sid" rel="${isPersonnel.PERSON_ID}">
					<td>${isPersonnel.EMPID}</td>
					<td>${isPersonnel.LOCAL_NAME}</td>
					<td>${isPersonnel.DEPT_NAME}</td>
					<td>${isPersonnel.POST_GRADE_NAME}</td>
					<td>${isPersonnel.IN_THE_DIFFERENCE}</td>
					<td>${isPersonnel.JOIN_COMPANY_DATE}</td>
					<td>${isPersonnel.DATE_LEFT}</td>
					<td>${isPersonnel.SETTLEMENT_DATE}</td>	
					<td>
						<c:if test="${isPersonnel.IS_CALC_FLAG=='Y'}">是</c:if>
						<c:if test="${isPersonnel.IS_CALC_FLAG=='N'}">否</c:if>
					</td>				
				</tr>			
			</c:forEach>
</table>
</body>
</html>
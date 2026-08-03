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
		response.setHeader("Content-Disposition", "attachment; filename=BonusObject.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%" border="1" cellpadding="0" cellspacing="0">
	<tr>
		<th width="5%" orderField="nlssort(PA_MONTH,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->
		</th>
		<th width="6%" orderField="nlssort(GIVE_DATE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="pa.salary.title.salaryProvideDate"/><!-- 工资发放日 -->
		</th>
		<th width="6%" orderField="EMPID" class="${orderDirection}">
			<spring:message code="public.title.empId"/><!--工号-->
		</th>
		<th width="6%" orderField="nlssort(CHINESE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="pa.title.message.empHrmName"/><!--人事姓名-->
		</th>
		<th width="6%" orderField="nlssort(DEPTNAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="public.title.deptName"/><!--部门-->
		</th>
		<%--<th width="6%" orderField="nlssort(DEPT_DISTINGUISH_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="org.orgManage.title.deptDistinct"/><!--部门区分-->
		</th>
		--%><th width="4%" orderField="nlssort(POST_GRADE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="pa.insurance.title.postGrade"/><!--职级-->
		</th>
		<th width="6%" orderField="nlssort(EMP_STATUS,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="hr.viewPersonalInfo.title.STATUS_NAME" /><!--员工状态-->
		</th>
		<th width="6%" orderField="nlssort(IN_THE_DIFFERENCE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection }">
			<spring:message code="liang.hr.viewPersonalInfo.title.WHETHER_OR_NOT_TO_TRY"/><!-- 试用与否 -->
		</th>
		<th width="6%" orderField="nlssort(JOIN_COMPANY_DATE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="pa.insurance.title.entryCpmpanyDate"/><!--入司日期-->
		</th>
		<th width="7%" orderField="nlssort(DATE_LEFT,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="pa.insurance.title.resignDate"/><!--离职日期-->
		</th>
	<%--	<th width="7%" orderField="nlssort(SETTLEMENT_DATE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="pa.wagebase.title.salaryCaculateDate"/><!--工资计算日期-->
		</th>
		<th width="8%" orderField="nlssort(CALC_FLAG,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="pa.wagebase.title.caculateFlag3"/><!-- 工资计算对象 -->
		</th>
		<th width="6%" orderField="nlssort(REMARK,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="hr.viewPromote.title.REMARK"/><!-- 备注  -->
		</th>
		
	--%>
			<th width="10%" orderField="CALC_FLAG" class="${orderDirection}"><!--计算标识-->
					<spring:message code="pa.wagebase.title.caculateFlag"/>
			</th>
	</tr>
	<c:forEach items="${itemList}" var="item" varStatus="i">
		<tr target="sid" rel="${item.PERSON_ID}">
			<td>${item.PA_MONTH }</td>
			<td>${item.GIVE_DATE }</td>
			<td>${item.EMPID}</td>
			<td>${item.CHINESE_NAME}</td>
			<td>${item.DEPTNAME}</td>
			<%--<td>${item.DEPT_DISTINGUISH_NAME}</td>
			--%><td>${item.POST_GRADE_NAME}</td>
			<td>${item.EMP_STATUS}</td>
			<td>${item.IN_THE_DIFFERENCE }</td>
			<td>${item.JOIN_COMPANY_DATE}</td>
			<td>${item.DATE_LEFT}</td>
			<%--<td>${item.SETTLEMENT_DATE}</td>
			--%><td>${item.CALC_FLAG }</td>
			<%--<td>${item.REMARK }</td>
		--%></tr>
	</c:forEach>
</table>
</body>
</html>
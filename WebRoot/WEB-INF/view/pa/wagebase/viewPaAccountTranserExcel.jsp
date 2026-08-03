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
		response.setHeader("Content-Disposition", "attachment; filename=PaAccountInfo.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%" border="1" cellpadding="0" cellspacing="0">
	<tr>
		<th width="6%" orderField="EMPID" class="${orderDirection}">
			<spring:message code="public.title.empId"/><!--工号-->
		</th>
		<th width="6%" orderField="nlssort(CHINESE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="pa.title.message.empHrmName"/><!--人事姓名-->
		</th>
		<th width="6%" orderField="nlssort(CARD_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="pa.title.message.empPaName"/><!--账号名-->
		</th>		
		<%--
		<th width="5%" orderField="nlssort(CHINESE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="public.title.name"/><!--姓名-->
		</th>
		--%>
		<th width="8%" orderField="nlssort(DEPTNAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="public.title.deptName"/><!--部门-->
		</th>
		<%--
		<th width="8%" orderField="nlssort(DISTINGUISH_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="org.orgManage.title.deptDistinct"/><!--部门区分-->
		</th>
		--%>
		<th width="4%" orderField="nlssort(POST_GRADE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="pa.insurance.title.postGrade"/><!--职级-->
		</th>
		<%--
		<th width="4%" orderField="nlssort(STATUS_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="pa.insurance.title.status"/><!--状态-->
		</th>
		--%>
		<th width="6%" orderField="nlssort(STATUS_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="hr.viewPersonalInfo.title.STATUS_NAME" /><!--员工状态-->
		</th>
		<th width="6%" orderField="nlssort(JOIN_COMPANY_DATE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="pa.insurance.title.entryCpmpanyDate"/><!--入司日期-->
		</th>
		<th width="6%" orderField="nlssort(DATE_LEFT,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="pa.insurance.title.resignDate"/><!--离职日期-->
		</th>
		<%--
		<th width="7%" orderField="nlssort(SETTLEMENT_DATE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="pa.wagebase.title.salaryCaculateDate"/><!--工资计算日期-->
		</th>
		--%>
		<th width="8%" orderField="nlssort(BANK_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="pa.wagebase.title.openAccountBanks"/><!--开户行-->
		</th>
		<th width="6%" orderField="nlssort(BANK_BRANCH_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<spring:message code="pa.title.message.bankBranchNameInfo"/><%--银行支行名称--%>
		</th>
		<th width="12%" orderField="CARD_NO" class="${orderDirection}">
			<spring:message code="pa.wagebase.title.accountNo"/><!--账号-->
		</th>
		<th width="10%" orderField="CALC_FLAG" class="${orderDirection}">
			修改原因
		</th>
		<th width="10%" orderField="CALC_FLAG" class="${orderDirection}"><!--计算标识-->
			<spring:message code="pa.wagebase.title.caculateFlag"/>
		</th>
		<th width="10%" orderField="CALC_FLAG" class="${orderDirection}">
			修改人
		</th>

	</tr>
	<c:forEach items="${itemList}" var="item" varStatus="i">
		<tr target="sid" rel="${item.PERSON_ID}">
			<td>${item.EMPID}</td>
			<td>${item.CHINESE_NAME}</td>
			<td>${item.CARD_NAME}</td>
			<td>${item.DEPTNAME}</td>
			<%--<td>${item.DISTINGUISH_NAME}</td>
			--%>
			<td>${item.POST_GRADE_NAME}</td>
			<td>${item.STATUS_NAME}</td>
			<td>${item.JOIN_COMPANY_DATE}</td>
			<td>${item.DATE_LEFT}</td>
			<td>${item.BANK_NAME}</td>
			<td>${item.BANK_BRANCH_NAME}</td>
			<td>${item.CARD_NO}&nbsp;</td>
			<td>
				${item.UPDATE_REMARK}
			</td>
			<td>
				${item.CALC_FLAG}
			</td>
			<td>
				${item.UPDATED_BY}
			</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>
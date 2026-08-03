 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>费用申请导出</title>
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
		response.setHeader("Content-Disposition", "attachment; filename=wageApplication.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%" border="1" cellpadding="0" cellspacing="0">
	<tr>
		<th width="6%" orderField="EMPID" class="${orderDirection}">
			<%--序号--%><spring:message code="ar.viewcycle.title.xuhao"/>
		</th>
		<th width="18%">
			<%--法人--%><spring:message code="sys.essParam.title.legalPerson"/>
		</th>
		<th width="6%">
			<%--部门--%><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName"/>
		</th>
		<th width="6%" orderField="nlssort(CHINESE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<%--职责--%><spring:message code="ess.infoApply.title.dutyName"/>
		</th>
		<th width="6%" orderField="nlssort(CARD_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<%--人员类型--%><spring:message code="is.company.title.PERSON_TYPE"/>
		</th>		
		<th width="8%" orderField="nlssort(DEPTNAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<%--数值--%><spring:message code="pa.insurance.title.dataValue"/>
		</th>
	</tr>
	<c:forEach items="${paBasicItemList}" var="item" varStatus="i">
		<tr target="ALLOWANCE_ID" rel="${item.ALLOWANCE_ID}">
			<td>${i.count}</td>
			<td>${item.CPNY_NAME}</td>
			<td>${item.DEPT_ID}</td>
			<td>${item.DUTY_ALLOWANCE}</td>
			<td>${item.TYPE_ALLOWANCE}</td>
			<td>${item.POSITION_ALLOWANCE}</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>
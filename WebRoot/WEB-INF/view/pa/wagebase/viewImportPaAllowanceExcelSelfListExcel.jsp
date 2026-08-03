 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>职责津贴个人标准导出</title>
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
		response.setHeader("Content-Disposition", "attachment; filename=PaAllowanceInfo.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%" border="1" cellpadding="0" cellspacing="0">
	<tr>
		<th width="6%" orderField="EMPID" class="${orderDirection}">
			<%--社号--%><spring:message code="hr.viewPersonalInfo.title.EMPID"/>
		</th>
		<th width="6%" orderField="nlssort(CHINESE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<%--姓名--%><spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
		</th>
		<th width="6%" orderField="nlssort(CARD_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<%--数值--%><spring:message code="pa.insurance.title.dataValue"/>
		</th>		
		<th width="8%" orderField="nlssort(DEPTNAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<%--支付比例(%)--%><spring:message code="pa.allowance.zhifubili"/>(%)
		</th>
		<th width="8%" orderField="nlssort(DEPTNAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<%--发令日期--%><spring:message code="display.emp.ben.transdate"/>
		</th>
		<th width="8%" orderField="nlssort(DEPTNAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<%--有效期月数--%><spring:message code="pa.allowance.youxiaoqiyueshu"/>
		</th>
		<th width="8%" orderField="nlssort(DEPTNAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			<%--备注--%><spring:message code="ar.viewarcardrecord.title.beizhu"/>
		</th>
		<th width="8%" orderField="nlssort(DEPTNAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
			错误原因
		</th>
	</tr>
	<c:forEach items="${paBasicItemList}" var="item" varStatus="i">
		<tr target="ALLOWANCE_ID" rel="${item.ALLOWANCE_ID}">
			<td>${item.PERSON_ID}</td>
			<td>${item.CHINESENAME}</td>
			<td>${item.POSITION_ALLOWANCE}</td>
			<td>${item.PERCENT_ALLOWANCE}</td>
			<td>${item.START_DATE}</td>
			<td>${item.VALID_MONTH}</td>
			<td>${item.DEMO_ALLOWANCE }</td>
			<td>${item.UPLOAD_ERROR_MSG}</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>
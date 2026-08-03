 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
	<spring:message code="inct.salesman.excel.importResult.EvaluationData"/><!--评价数据导入结果-->
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=importSalesmanEvaluationData.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table class="table" width="100%" layoutH="150">
		<thead>
			<tr>
				<th width="15%"><spring:message code="inct.salesman.daqu"/>*<!--大区--></th>
				<th width="10%"><spring:message code="inct.salesman.year"/>*<!--年--></th>
				<th width="5%"><spring:message code="inct.salesman.Season"/>*<!--季度--></th>			
				<th width="10%"><spring:message code="inct.salesman.evaluationItemType"/>*<!--评价项目--></th>
				<th width="10%"><spring:message code="inct.salesman.empNo"/>*<!--社号--></th>
				<th width="10%"><spring:message code="inct.salesman.currentYearAchieve"/>*<!--今年实绩--></th>
				<th width="10%"><spring:message code="inct.salesman.lastYearAchieve"/><!--去年实绩--></th>
				<th width="10%"><spring:message code="inct.salesman.validateMessage"/><!--验证结果--></th>
				<th width="10%"><spring:message code="inct.salesman.updateBy"/><!--更新人--></th>
				<th width="10%"><spring:message code="inct.salesman.updateTime"/><!--更新时间--></th>		
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${MDATA}" var="mdata" varStatus="i">			
				<tr>
					<td>${mdata.PAY_AREA_CD}</td>
					<td class='td_center'>${mdata.YYYY}</td>
					<td class='td_center'>${mdata.QUARTER}</td>	
					<td>${mdata.CATEGORY_CD}</td>				
					<td class='td_center'>${mdata.EMPNO}</td>					
					<td class='td_right'>${mdata.CURRENT_VALUE}</td>
					<td class='td_right'>${mdata.LAST_VALUE}</td>
					<td>${mdata.UPLOAD_ERROR_MSG}</td>
					<td class='td_center'>${mdata.UPDT_USER}</td>
					<td class='td_center'>${mdata.UPDT_DTIME}</td>	
				</tr>			
			</c:forEach>			
		</tbody>
	</table>  
	</td>
  </tr>
</table>
</body>
</html>
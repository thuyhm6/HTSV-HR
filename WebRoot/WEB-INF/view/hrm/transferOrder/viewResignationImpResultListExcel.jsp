 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
	临时职人员离职数据导入结果
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=tempEmpResignList.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table class="table" width="100%" layoutH="150">
		<thead>
			<tr>
				<th width="10%">社编</th>					
				<th width="10%">离职日期</th>
				<th width="10%">离职类型</th>
				<th width="10%">离职原因</th>
				<th width="15%"><spring:message code="inct.salesman.validateMessage"/><!--验证结果--></th>
				<th width="10%"><spring:message code="inct.salesman.updateBy"/><!--更新人--></th>
				<th width="10%"><spring:message code="inct.salesman.updateTime"/><!--更新时间--></th>			
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${MDATA}" var="mdata" varStatus="i">			
				<tr>
					<td class='td_center'>${mdata.EMPID}</td>
					<td class='td_center'>${mdata.RESIGN_DATE}</td>
					<td>${mdata.RESIGN_TYPE_CODE}</td>									
					<td>${mdata.RESIGN_REASON}</td>
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
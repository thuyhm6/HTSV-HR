<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="pageContent">
	<div>
	<form action="/ar/attendanceView/showAnnualUsageList" method="post" id="showAnnualUsage">
		<table width="100%" class="user_table margin_b">
			<tr>
				<td class="td_title" style="text-align: center" width="20%">姓名</td>
				<td class="td_title" style="text-align: center" width="80%">已休年假</td>
			</tr>
			<c:forEach items="${annualUsageFactList}" var="Usage" varStatus="j">
				<tr>
					
					<td class="td_type" style="text-align: center" width="20%">${Usage.LOCAL_NAME} </td>
					<td class="td_type" style="text-align: center" width="80%">${Usage.FROM_TIME}&nbsp;~&nbsp;${Usage.TO_TIME} &nbsp;&nbsp;${Usage.QUANTITY}小时 </td>
				</tr>
			</c:forEach>
	</form>
	</div>
</div>

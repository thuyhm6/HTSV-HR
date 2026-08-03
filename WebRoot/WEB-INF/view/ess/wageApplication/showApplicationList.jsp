<%@ page contentType="text/html; charset=UTF-8" language="java"	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<div>
		<table width="99%" align="center" class="user_table margin_b">
			<tr>
				<td class="td_title" width="15%" style="text-align: center">申请者/工号</td>
				<td class="td_title" width="15%" style="text-align: center">费用类型</td>
				<td class="td_title" width="30%" style="text-align: center">申请费用发放期间</td>
				<td class="td_title" width="15%" style="text-align: center">金额</td>
				<td class="td_title" width="15%" style="text-align: center">备注</td>
			</tr>
			<c:forEach items="${messageList}" var="message" varStatus="i">
				<tr>
					<td class="td_type" width="15%" style="text-align: center">${message.EMPNAME}[${message.EMPID}]</td>
					<td class="td_type" width="15%" style="text-align: center">${message.TYPENAME}</td>
					<td class="td_type" width="30%" style="text-align: center">${message.START_DATE}~${message.END_DATE}</td>
					<td class="td_type" width="15%" style="text-align: center">${message.MONEY}</td>
					<td class="td_type" width="15%" style="text-align: center">${message.DEMO}</td>
				</tr>
			</c:forEach>
			<tr>
				<c:if test="${fn:length(messageList) < 1}"><td colspan="5" class="td_type" style="text-align: center">没有相关数据</td></c:if>
			</tr>
		</table>
	</div>
</div>
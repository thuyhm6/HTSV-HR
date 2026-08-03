<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
<!-- aaa -->	
	<c:set value="/hrm/empinfo/viewAccount" var="turn_to_url" />
	
	<div class="panel">
		<h1>
			<spring:message code="hr.viewPersonalInfo.title.STAFF_FOUNDATION_INFORMATION"/>
			<!--员工基础信息-->
		</h1>
		<div layoutH="365"><%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead1.jsp"%></div>
	</div>
	
	<div class="panel">
		<h1>
			<spring:message code="hr.viewAccount.title.ACCOUNT"/>
			<!--账户-->
		</h1>
		<div>
	<table class="table" width="100%">
		<thead>
			<tr>
				<th width="100">
					<spring:message code="hr.viewAccount.title.CALC_FLAG"/>
					<!--工资计算标志-->
				</th>
				<th width="100">
					<spring:message code="hr.viewAccount.title.CARD_NO"/>
					<!--帐号-->
				</th>
				<th width="100">
					<spring:message code="hr.viewAccount.title.BANK_NAME"/>
					<!--开户行-->
				</th>
				<th width="100">
					<spring:message code="hr.viewAccount.title.BANK_AREA"/>
					<!--开户行地址-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${accountList}" var="item" varStatus="i">			
				<tr>
					<td>${item.CALC_FLAG}</td>
					<td>${item.CARD_NO}</td>
					<td>${item.BANK_NAME}</td>
					<td>${item.BANK_AREA}</td>
				</tr>	
			</c:forEach>		
		</tbody>
	</table>
	</div>
	</div>
</div>

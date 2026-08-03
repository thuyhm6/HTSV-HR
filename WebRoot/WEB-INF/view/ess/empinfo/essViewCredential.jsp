<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<div class="panel">
		<h1>
			<spring:message code="hr.viewPersonalInfo.title.STAFF_FOUNDATION_INFORMATION"/>
			<!--员工基础信息-->
		</h1>
		<div><%@ include file="/WEB-INF/view/ess/empinfo/viewPersonalInfoHead.jsp"%></div>
	</div>
	<div class="panel">
		<h1>
			<spring:message code="hr.viewCredential.title.CREDENTIAL_INFORMATINO"/>
			<!--证照信息-->
		</h1>
	<div>
	<table class="table" width="100%">
		<thead>
			<tr>
				<th width="80">
					<spring:message code="hr.viewCredential.title.CREDENTIAL_TYPE_NAME"/>
					<!--证照类型-->
				</th>
				<th width="80">
					<spring:message code="hr.viewCredential.title.CREDENTIAL_NO"/>
					<!--证照号码-->
				</th>
				<th width="80">
					<spring:message code="hr.viewCredential.title.CREDENTIAL_SOURCE"/>
					<!--签发地-->
				</th>
				<th width="80">
					<spring:message code="hr.viewCompetence.title.DATE_OBTAINED"/>
					<!--取证日期-->
				</th>
				<th width="80">
					<spring:message code="hr.viewCredential.title.CREDENTIAL_END_DATE"/>
					<!--到期日-->
				</th>
				<th width="80">
					<spring:message code="hr.viewPromote.title.REMARK"/>
					<!--备注-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${credentialList}" var="item" varStatus="i">			
				<tr>
					<td>${item.CREDENTIAL_TYPE_NAME}</td>
					<td>${item.CREDENTIAL_NO}</td>
					<td>${item.CREDENTIAL_SOURCE}</td>
					<td>${item.CREDENTIAL_BEGIN_DATE}</td>
					<td>${item.CREDENTIAL_END_DATE}</td>
					<td>${item.REMARK}</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
	</div>
	</div>	
</div>
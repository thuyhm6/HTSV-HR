<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>



<div class="pageContent">

	<c:set value="/hrm/empinfo/viewCredential" var="turn_to_url" />
	
	<div class="panel">
		<h1>
			<spring:message code="hr.viewPersonalInfo.title.STAFF_FOUNDATION_INFORMATION"/>
			<!--员工基础信息-->
		</h1>
		<div layoutH="365"><%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead1.jsp"%></div>
	</div>
	
	<c:set value="1250" var="add_width" />
	<c:set value="630" var="add_height" />
	<c:set value="dialog" var="add_tab"/>
	<c:set value="/hrm/empinfo/viewCredentialInfo?PERSON_ID=${personInfo.PERSON_ID }" var="add_Url"/>
	
	
	
	<c:set value="800" var="delete_width" />
	<c:set value="300" var="delete_height" />
	<c:set value="dialog" var="delete_tab"/>
	<c:set value="0" var="delete_range" />
	<c:set value="0" var="delete_mask_exit"/>
	<c:set value="true" var="delete_mask"/>
	<c:set value="/hrm/empinfo/deleteCredential?PERSON_ID=${personInfo.PERSON_ID}" var="delete_Url"/>
	
	
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="1000" var="edit_width"/>
	<c:set value="500" var="edit_height"/>
	<c:set value="/hrm/empinfo/updateCredentialInfo?PERSON_ID=${personInfo.PERSON_ID}" var="edit_Url"/>
	<div class="panel">
		<h1>
			<spring:message code="hr.viewCredential.title.CREDENTIAL_INFORMATINO"/>
			<!--证照信息-->
		</h1>
		
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<div>
	<table class="table" width="101%">
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

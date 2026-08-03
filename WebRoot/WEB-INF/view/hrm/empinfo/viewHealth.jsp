<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>



<div class="pageContent">
	
	<c:set value="/hrm/empinfo/viewHealth" var="turn_to_url" />
	
	<div class="panel">
		<h1>
			<spring:message code="hr.viewPersonalInfo.title.STAFF_FOUNDATION_INFORMATION"/>
			<!--员工基础信息-->
		</h1>
		<div layoutH="365"><%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead1.jsp"%></div>
	</div>

	<c:set value="1100" var="add_width" />
	<c:set value="440" var="add_height" />
	<c:set value="dialog" var="add_tab"/>
	<c:set value="/hrm/empinfo/viewHealthInfo?PERSON_ID=${personInfo.PERSON_ID }" var="add_Url"/>
	
	<c:set value="800" var="delete_width" />
	<c:set value="300" var="delete_height" />
	<c:set value="dialog" var="delete_tab"/>
	<c:set value="0" var="delete_range" />
	<c:set value="0" var="delete_mask_exit"/>
	<c:set value="true" var="delete_mask"/>
	<c:set value="/hrm/empinfo/deleteHealth?PERSON_ID=${personInfo.PERSON_ID}" var="delete_Url"/>
	
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="1100" var="edit_width"/>
	<c:set value="440" var="edit_height"/>
	
	<c:set value="/hrm/empinfo/updateHealthInfo?PERSON_ID=${personInfo.PERSON_ID}" var="edit_Url"/>
	<div class="panel">
		<h1>
			<spring:message code="hr.viewHealth.title.HEALTHINFORMATION"/>
			<!--健康信息-->
		</h1>
		
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<div>
	<table class="table" width="101%" >
		<thead>
			<tr>
				<th width="80">
					<spring:message code="hr.viewHealth.title.PHYSICAL_DATE"/>
					<!--检查日期-->
				</th>
				<th width="80">
					<spring:message code="hr.viewHealth.title.PHYSICAL_TYPE_NAME"/>
					<!--检查类型-->
				</th>
				<th width="80">
					<spring:message code="hr.viewHealth.title.INDUSTRY_DISTINGUISH_NAME"/>
					<!--区分-->
				</th>
				<th width="80">
					<spring:message code="hr.viewHealth.title.EFFECTIVE_DATE"/>
					<!--有效期-->
				</th>
				<th width="80">
					<spring:message code="hr.viewHealth.title.CHECK_YN_NAME"/>
					<!--检查与否-->
				</th>
				<th width="80">
					<spring:message code="hr.viewHealth.title.GENRAL_HEALTH_NAME"/>
					<!--健康情况-->
				</th>
				<th width="80">
					<spring:message code="hr.viewHealth.title.BLOOD_TYPE_NAME"/>
					<!--血型-->
				</th>
				<th width="80">
					<spring:message code="hr.viewHealth.title.HEALTH_CERTIFICATE_YN_NAME"/>
					<!--是否提交健康证-->
				</th>
				<th width="80">
					<spring:message code="hr.viewHealth.title.SPECIAL_MATTERS"/>
					<!--特殊事项-->
				</th>
				 
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${healthList}" var="item" varStatus="i">
			
				<tr target="healthNo" rel="${item.HEALTH_NO}">
					<td>${item.PHYSICAL_DATE}</td>
					<td>${item.PHYSICAL_TYPE_NAME}</td>
					<td>${item.INDUSTRY_DISTINGUISH_NAME}</td>
					<td>${item.EFFECTIVE_DATE}</td>
					<td>${item.CHECK_YN_NAME}</td>
					<td>${item.GENRAL_HEALTH_NAME}</td>
					<td>${item.BLOOD_TYPE_NAME}</td>
					<td>${item.HEALTH_CERTIFICATE_YN_NAME}</td>
					<td>${item.REMARK}</td>
				</tr>
			
			</c:forEach>
			
		</tbody>
	</table>
	</div>	
	</div>
</div>

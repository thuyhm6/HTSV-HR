<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>



<div class="pageContent">

	<c:set value="/hrm/empinfo/viewAdditional" var="turn_to_url" />
	
	<div class="panel">
		<h1>
			<spring:message code="hr.viewPersonalInfo.title.STAFF_FOUNDATION_INFORMATION"/>
			<!--员工基础信息-->
		</h1>
		<div layoutH="365"><%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead1.jsp"%></div>
	</div>
	
	
	<c:set value="900" var="add_width" />
	<c:set value="500" var="add_height" />
	<c:set value="dialog" var="add_tab"/>
	<c:set value="/hrm/empinfo/viewAdditionalInfo?PERSON_ID=${personInfo.PERSON_ID }" var="add_Url"/>


	<c:set value="800" var="delete_width" />
	<c:set value="300" var="delete_height" />
	<c:set value="dialog" var="delete_tab"/>
	<c:set value="0" var="delete_range" />
	<c:set value="0" var="delete_mask_exit"/>
	<c:set value="true" var="delete_mask"/>
	<c:set value="/hrm/empinfo/deleteAdditional?PERSON_ID=${personInfo.PERSON_ID}" var="delete_Url"/>
	
	
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="1000" var="edit_width"/>
	<c:set value="500" var="edit_height"/>
	<c:set value="/hrm/empinfo/updateAdditionalInfo?PERSON_ID=${personInfo.PERSON_ID}" var="edit_Url"/>
	
	
	<div class="panel">
		<h1>
			<spring:message code="hr.viewAdditional.title.SPECIAL_MATTERS"/>
			<!--特殊事项-->
		</h1>
		<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
		<div>
	<table class="table" width="101%">
		<thead>
			<tr>
				<th width="100">
					<spring:message code="hr.viewAdditional.title.EVENT_DATE"/>
					<!--发生日期-->
				</th>
				<th width="100">
					<spring:message code="hr.viewAdditional.title.INFO_TYPE_NAME"/>
					<!--信息类型-->
				</th>
				<th width="100">
					<spring:message code="hr.viewAdditional.title.REMARK"/>
					<!--详细内容-->
				</th>
				<th width="100">
					<spring:message code="hr.viewAdditional.title.CREATE_NAME"/>
					<!--登记者-->
				</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${additionalList}" var="item" varStatus="i">
			
				<tr target="trainNo" rel="${item.ADDITIONAL_NO}">
					<td>${item.EVENT_DATE}</td>
					<td>${item.INFO_TYPE_NAME}</td>
					<td>${item.REMARK}</td>
					<td>${item.CREATE_NAME}</td>
				</tr>
			
			</c:forEach>
			
		</tbody>
	</table>
	</div>	
	</div>
</div>

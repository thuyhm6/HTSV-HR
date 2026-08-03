<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>



<div class="pageContent">

	<c:set value="/hrm/empinfo/viewTranslate" var="turn_to_url" />
	
	<div class="panel">
		<h1>
			<spring:message code="hr.viewPersonalInfo.title.STAFF_FOUNDATION_INFORMATION"/>
			<!--员工基础信息-->
		</h1>
		<div layoutH="365"><%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead1.jsp"%></div>
	</div>
	
	<div class="panel">
		<h1>
			<spring:message code="hr.viewTranslate.title.PART_TIME_JOB"/>
			<!--兼职-->
		</h1>
		<div>
	<table class="table" width="101%">
		<thead>
			<tr>
				<th width="80">
					<spring:message code="hr.viewTranslate.title.THE_TYPE"/>
					<!--发令类型-->
				</th>
				<th width="80">
					<spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE"/>
					<!--开始日期-->
				</th>
				<th width="80">
					<spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE"/>
					<!--结束日期-->
				</th>
				<th width="80">
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!--部门-->
				</th>
				<th width="80">
					<spring:message code="hr.viewPersonalInfo.title.POSITION_NAME"/>
					<!--职(岗)位-->
				</th>
				<th width="80">
					<spring:message code="hr.viewTranslate.title.PART_TIME_TYPE"/>
					<!--兼职类型-->
				</th>
				<th width="80">
					<spring:message code="hr.viewPersonalInfo.title.PLU_DEPTNAME"/>
					<!--兼职部门-->
				</th>
				<th width="80">
					<spring:message code="hr.viewPersonalInfo.title.PLU_POSITINO_NAME"/>
					<!--兼职职(岗)位-->
				</th>
				<th width="80">
					<spring:message code="hr.viewPersonalInfo.title.PLU_DUTY_NAME"/>
					<!--兼职职责-->
				</th>
				<th width="80">
					<spring:message code="hr.viewTranslate.title.PART_TIME_CONTENTS"/>
					<!--兼职事由-->
				</th>
				<th width="80">
					<spring:message code="hr.viewPromote.title.REMARK"/>
					<!--备注-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pluralityList}" var="item" varStatus="i">
			
				<tr target="sid" rel="${item.PERSON_ID}">
					<td>${item.TRANS_NAME}</td>
					<td>${item.START_DATE}</td>
					<td>${item.END_DATE}</td>
					<td>${item.DEPTNAME}</td>
					<td>${item.POSITION_NAME}</td>
					<td>${item.PLU_TYPE_NAME}</td>
					<td>${item.PLU_DEPTNAME}</td>
					<td>${item.PLU_POSITION_NAME}</td>
					<td>${item.PLU_DUTY_NAME}</td>
					<td>${item.PLU_REASON}</td>
					<td>${item.REMARK}</td>
					
				</tr>
			
			</c:forEach>
			
		</tbody>
	</table>
		
	</div>	
</div>
	
</div>

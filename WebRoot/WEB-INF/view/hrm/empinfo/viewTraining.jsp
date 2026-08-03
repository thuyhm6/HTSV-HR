<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>



<div class="pageContent">
	
	<c:set value="/hrm/empinfo/viewTraining" var="turn_to_url" />
	
	<div class="panel">
		<h1>
			<spring:message code="hr.viewPersonalInfo.title.STAFF_FOUNDATION_INFORMATION"/>
			<!--员工基础信息-->
		</h1>
		<div layoutH="365"><%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead1.jsp"%></div>
	</div>

	<c:set value="1250" var="add_width" />
	<c:set value="440" var="add_height" />
	<c:set value="dialog" var="add_tab"/>
	<c:set value="/hrm/empinfo/viewTrainingInfo?PERSON_ID=${personInfo.PERSON_ID }" var="add_Url"/>
	
	
	
	<c:set value="850" var="delete_width" />
	<c:set value="300" var="delete_height" />
	<c:set value="dialog" var="delete_tab"/>
	<c:set value="0" var="delete_range" />
	<c:set value="0" var="delete_mask_exit"/>
	<c:set value="true" var="delete_mask"/>
	<c:set value="/hrm/empinfo/deleteTraining?PERSON_ID=${personInfo.PERSON_ID}" var="delete_Url"/>
	
	
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="1250" var="edit_width"/>
	<c:set value="440" var="edit_height"/>
	
	<c:set value="/hrm/empinfo/updateTrainingInfo?PERSON_ID=${personInfo.PERSON_ID}" var="edit_Url"/>
	
	<div class="panel">
		<h1>
			<spring:message code="hr.viewTraining.title.NAVIGATION_TRAINING"/>
			<!--培训信息-->
		</h1>
		
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<div>
	<table class="table" width="101%">
		<thead>
			<tr>
				<th width="80">
					<spring:message code="hr.viewTraining.title.COURSE_NAME"/>
					<!--课程名-->
				</th>
				<th width="80">
					<spring:message code="hr.viewTraining.title.COURSE_FEE"/>
					<!--课程费用-->
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
					<spring:message code="hr.viewTraining.title.INSTITUTION_NAME"/>
					<!--培训机关-->
				</th>
				<th width="80">
					<spring:message code="hr.viewTraining.title.CERTIFICATE_NAME"/>
					<!--证书名称-->
				</th>
				<th width="80">
					<spring:message code="hr.viewTraining.title.SUBMIT_REPORT_YN_NAME"/>
					<!--是否提交报告-->
				</th>
				<th width="80">
					<spring:message code="hr.viewTraining.title.AGREEMENT_YN_NAME"/>
					<!--是否有协议-->
				</th>
				<th width="80">
					<spring:message code="hr.viewTraining.title.AGREEMENT_START_DATE"/>
					<!--协议开始日期-->
				</th>
				<th width="80">
					<spring:message code="hr.viewTraining.title.AGREEMENT_END_DATE"/>
					<!--协议结束日期-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${trainingInfoList}" var="item" varStatus="i">
			
				<tr target="trainNo" rel="${item.TRAIN_NO}">
					<td>${item.COURSE_NAME}</td>
					<td><fmt:formatNumber value="${item.COURSE_FEE}" pattern="#,##0.00"/></td>
					<td>${item.START_DATE}</td>
					<td>${item.END_DATE}</td>
					<td>${item.INSTITUTION_NAME}</td>
					<td>${item.CERTIFICATE_NAME}</td>
					<td>${item.SUBMIT_REPORT_YN_NAME }</td>
					<td>${item.AGREEMENT_YN_NAME}</td>
					<td>${item.AGREEMENT_START_DATE}</td>
					<td>${item.AGREEMENT_END_DATE}</td>
				</tr>
			
			</c:forEach>
			
		</tbody>
	</table>
	</div>	
	</div>
</div>

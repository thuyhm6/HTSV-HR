<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<div class="pageContent">
	
	<c:set value="/hrm/empinfo/viewWorkInfo" var="turn_to_url" />
	
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
	<c:set value="/hrm/empinfo/viewWorkExperienceInfo?PERSON_ID=${personInfo.PERSON_ID }" var="add_Url"/>
	
	<c:set value="800" var="delete_width" />
	<c:set value="300" var="delete_height" />
	<c:set value="dialog" var="delete_tab"/>
	<c:set value="0" var="delete_range" />
	<c:set value="0" var="delete_mask_exit"/>
	<c:set value="/hrm/empinfo/deleteWorkExpreience?PERSON_ID=${personInfo.PERSON_ID}" var="delete_Url"/>
	
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="1100" var="edit_width"/>
	<c:set value="440" var="edit_height"/>
	<c:set value="/hrm/empinfo/updateWorkExperienceInfo?PERSON_ID=${personInfo.PERSON_ID}" var="edit_Url"/>
	<div class="panel">
		<h1>
			<spring:message code="hr.viewWorkInfo.title.WORK_EXPERIENCE"/>
			<!--工作经历-->
		</h1>
		
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<div>
	<table class="table" width="101%">
		<thead>
			<tr>
				<th width="80">
					<spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE"/>
					<!--开始时间-->
				</th>
				<th width="80">
					<spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE"/>
					<!--结束时间-->
				</th>
				<th width="80">
					<spring:message code="hr.viewWorkInfo.title.CPNY_NAME"/>
					<!--工作单位-->
				</th>
				<th width="80">
					<spring:message code="hr.viewWorkInfo.title.DUTY"/>
					<!--负责业务-->
				</th>
				<th width="80">
					<spring:message code="hr.viewWorkInfo.title.POSITION"/>
					<!--职位-->
				</th>
				<th width="80">
					<spring:message code="hr.viewWorkInfo.title.PAYROLL"/>
					<!--月工资-->
				</th>
				<th width="80">
					<spring:message code="hr.viewPromote.title.RESIGN_REASON"/>
					<!--离职事由-->
				</th>
				<th width="80">
					<spring:message code="hr.viewWorkInfo.title.WITNESS"/>
					<!--证明人-->
				</th>
				<th width="80">
					<spring:message code="hr.viewWorkInfo.title.TEL"/>
					<!--联系方式-->
				</th>
				 
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${workExperienceList}" var="item" varStatus="i">
			
				<tr target="personId" rel="${item.HEALTH_NO}">
					<td>${item.START_DATE}</td>
					<td>${item.END_DATE}</td>
					<td>${item.CPNY_NAME}</td>
					<td>${item.DUTY}</td>
					<td>${item.POSITION}</td>
					<td>${item.PAYROLL}</td>
					<td>${item.RESIGN_REASON}</td>
					<td>${item.WITNESS}</td>
					<td>${item.TEL}</td>
				</tr>
			
			</c:forEach>
			
		</tbody>
	</table>
	</div>	
	</div>
</div>

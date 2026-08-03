<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>



<div class="pageContent">
	
	<c:set value="/hrm/empinfo/viewRelation" var="turn_to_url" />
	
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
	<c:set value="/hrm/empinfo/viewFamilyInfo?PERSON_ID=${personInfo.PERSON_ID }" var="add_Url"/>
	
	<c:set value="1200" var="delete_width" />
	<c:set value="300" var="delete_height" />
	<c:set value="dialog" var="delete_tab"/>
	<c:set value="0" var="delete_range" />
	<c:set value="0" var="delete_mask_exit"/>
	<c:set value="true" var="delete_mask"/>
	
	
	<c:set value="/hrm/empinfo/deleteFamily?PERSON_ID=${personInfo.PERSON_ID}" var="delete_Url"/>
	
	
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="1200" var="edit_width"/>
	<c:set value="440" var="edit_height"/>
	<c:set value="/hrm/empinfo/updateFamilyInfo?PERSON_ID=${personInfo.PERSON_ID}" var="edit_Url"/>
	
	<div class="panel">
		<h1>
			<spring:message code="hr.viewRelation.title.SOCIAL_RELATIONS"/>
			<!--社会关系-->
		</h1>
		
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<div>
	<table class="table" width="101%">
		<thead>
			<tr>
				<th width="100">
					<spring:message code="hr.viewRelation.title.FAM_TYPE_NAME"/>
					<!--关系-->
				</th>
				<th width="100">
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
					<!--姓名-->
				</th>
				<th width="100">
					<spring:message code="hr.viewPersonalInfo.title.IDCARD_NO"/>
					<!--身份证号码-->
				</th>
				<th width="100">
					<spring:message code="hr.viewPersonalInfo.title.DOB"/>
					<!--出生日期-->
				</th>
				<th width="100">
					<spring:message code="hr.viewRelation.title.FAM_ADDRESS"/>
					<!--地址-->
				</th>
				<th width="100">
					<spring:message code="hr.viewRelation.title.FAM_PHONE"/>
					<!--联系电话-->
				</th>
				<th width="100">
					<spring:message code="hr.viewRelation.title.FAM_COMPANY_NAME"/>
					<!--工作单位/职(岗)位-->
				</th>
				<th width="100">
					<spring:message code="hr.viewRelation.title.LIVE_YN_NAME"/>
					<!--一起居住与否-->
				</th>
				<th width="100">
					<spring:message code="hr.viewRelation.title.EMERGENCY_CONTACT_YN_NAME"/>
					<!--是否紧急联系人-->
				</th>
				<th width="100">
					<spring:message code="hr.viewRelation.title.FAM_PERSON_ID"/>
					<!--亲属员工号-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${familyList}" var="item" varStatus="i">
			
				<tr target="sid" rel="${item.FAMILY_NO}">
					<td>${item.FAM_TYPE_NAME}</td>
					<td>${item.FAM_NAME}</td>
					<td>${item.FAM_IDCARD}</td>
					<td>${item.FAM_BORNDATE}</td>
					<td>${item.FAM_ADDRESS}</td>
					<td>${item.FAM_PHONE}</td>
					<td>${item.FAM_COMPANY_NAME}</td>
					<td>${item.LIVE_YN_NAME}</td>
					<td>${item.EMERGENCY_CONTACT_YN_NAME}</td>
					<td>${item.FAM_PERSON_ID}</td>
				</tr>
			
			</c:forEach>
			
		</tbody>
	</table>

</div>
</div>	

								<c:set value="1250" var="add_width" />
								<c:set value="440" var="add_height" />
								<c:set value="dialog" var="add_tab"/>
								<c:set value="/hrm/empinfo/viewHomeRelationInfo?PERSON_ID=${personInfo.PERSON_ID }&TABS_SELECTED=${tabsSelected }" var="add_Url"/>
								
								<c:set value="1200" var="delete_width" />
								<c:set value="300" var="delete_height" />
								<c:set value="dialog" var="delete_tab"/>
								<c:set value="0" var="delete_range" />
								<c:set value="0" var="delete_mask_exit"/>
								<c:set value="true" var="delete_mask"/>
								
								
								<c:set value="/hrm/empinfo/deleteHomeRelation?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }" var="delete_Url"/>
								
								
								<c:set value="dialog" var="edit_tab"/>
								<c:set value="1200" var="edit_width"/>
								<c:set value="440" var="edit_height"/>
								<c:set value="/hrm/empinfo/updateHomeRelation?PERSON_ID=${personInfo.PERSON_ID}&TABS_SELECTED=${tabsSelected }" var="edit_Url"/>
								
<div class="panel">
		<h1>
			<spring:message code="hr.viewRelation.title.FAMILY_RELATIONS"/>
			<!--家人关系-->
		</h1>
		
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<div>
	<table class="table" width="101%">
		<thead>
			<tr>
				<th width="100">
					<spring:message code="hr.viewRelation.title.FAM_TYPE_NAME"/>
					<!--关系-->
				</th>
				<th width="100">
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
					<!--姓名-->
				</th>
				<th width="100">
					<spring:message code="hr.viewPersonalInfo.title.IDCARD_NO"/>
					<!--身份证号码-->
				</th>
				<th width="100">
					<spring:message code="hr.viewPersonalInfo.title.DOB"/>
					<!--出生日期-->
				</th>
				<th width="100">
					<spring:message code="hr.viewRelation.title.FAM_ADDRESS"/>
					<!--地址-->
				</th>
				<th width="100">
					<spring:message code="hr.viewRelation.title.FAM_PHONE"/>
					<!--联系电话-->
				</th>
				<th width="100">
					<spring:message code="hr.viewRelation.title.FAM_COMPANY_NAME"/>
					<!--工作单位/职(岗)位-->
				</th>
				<th width="100">
					<spring:message code="hr.viewRelation.title.LIVE_YN_NAME"/>
					<!--一起居住与否-->
				</th>
				<th width="100">
					<spring:message code="hr.viewRelation.title.EMERGENCY_CONTACT_YN_NAME"/>
					<!--是否紧急联系人-->
				</th>
				<th width="100">
					<spring:message code="hr.viewRelation.title.FAM_PERSON_ID"/>
					<!--亲属员工号-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${homeRelationList}" var="item" varStatus="i">
			
				<tr target="sid" rel="${item.FAMILY_NO}">
					<td>${item.FAM_TYPE_NAME}</td>
					<td>${item.FAM_NAME}</td>
					<td>${item.FAM_IDCARD}</td>
					<td>${item.FAM_BORNDATE}</td>
					<td>${item.FAM_ADDRESS}</td>
					<td>${item.FAM_PHONE}</td>
					<td>${item.FAM_COMPANY_NAME}</td>
					<td>${item.LIVE_YN_NAME}</td>
					<td>${item.EMERGENCY_CONTACT_YN_NAME}</td>
					<td>${item.FAM_PERSON_ID}</td>
				</tr>
			
			</c:forEach>
			
		</tbody>
	</table>

</div>
</div>	
</div>

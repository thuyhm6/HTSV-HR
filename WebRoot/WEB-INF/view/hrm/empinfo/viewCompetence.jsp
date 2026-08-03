<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="pageContent">
	
	<c:set value="/hrm/empinfo/viewCompetence" var="turn_to_url" />
	
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
	<c:set value="/hrm/empinfo/viewCompetenceInfo?PERSON_ID=${personInfo.PERSON_ID }" var="add_Url"/>
	
	
	<c:set value="900" var="delete_width" />
	<c:set value="550" var="delete_height" />
	<c:set value="dialog" var="delete_tab"/>
	<c:set value="0" var="delete_range" />
	<c:set value="0" var="delete_mask_exit"/>
	<c:set value="true" var="delete_mask"/>
	<c:set value="/hrm/empinfo/deleteCompetenceInfo?PERSON_ID=${personInfo.PERSON_ID}" var="delete_Url"/>
	
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="1200" var="edit_width"/>
	<c:set value="600" var="edit_height"/>
	<c:set value="/hrm/empinfo/updateCompetenceInfo?PERSON_ID=${personInfo.PERSON_ID}" var="edit_Url"/>
	
	
	
	
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<div class="panel">
		<h1>
			<spring:message code="hr.viewCompetence.title.CREDENTIALS"/>
			<!--资格证书-->
		</h1>
		
		<div>
	
	<table class="table" width="101%">
				<thead>
					<tr>
						<th width="100">
							<spring:message code="hr.viewCompetence.title.QUAL_NAME"/>
							<!--资格证名称-->
						</th>
						<th width="100">
							<spring:message code="hr.viewCompetence.title.QUAL_CARD_NO"/>
							<!--证件号-->
						</th>
						<th width="100">
							<spring:message code="hr.viewCompetence.title.QUAL_LEVEL_NAME"/>
							<!--证件级别-->
						</th>
						<th width="100">
							<spring:message code="hr.viewCompetence.title.QUAL_INSTITUTE"/>
							<!--发证处-->
						</th>
						<th width="100">
							<spring:message code="hr.viewCompetence.title.ACQUISITION_NAME"/>
							<!--取得方式-->
						</th>
						<th width="100">
							<spring:message code="hr.viewCompetence.title.DATE_OBTAINED"/>
							<!--取证日期-->
						</th>
						<th width="100">
							<spring:message code="hr.viewCompetence.title.VALIDITY_DATE"/>
							<!--有效期-->
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${qualificationList}" var="item" varStatus="i">
					
						<tr target="sid" rel="${item.QUAL_NO}">
							<td>${item.QUAL_NAME}</td>
							<td>${item.QUAL_CARD_NO}</td>
							<td>${item.QUAL_LEVEL_NAME}</td>
							<td>${item.QUAL_INSTITUTE}</td>
							<td>${item.ACQUISITION_NAME}</td>
							<td>${item.DATE_OBTAINED}</td>
							<td>${item.VALIDITY_DATE}</td>
						</tr>
					
					</c:forEach>
					
				</tbody>
			</table>
			</div>
			</div>
<div class="panel">
		<h1>
			<spring:message code="hr.viewCompetence.title.FOREIGN_LANGUAGE"/>
			<!--外国语-->
		</h1>
		<div>
			<table class="table" width="101%">
				<thead>
					<tr>
						 <th width="100">
						 	<spring:message code="hr.viewCompetence.title.LANGUAGE_TYPE_NAME"/>
						 	<!--语言类型-->
						 </th>
						 <th width="100">
						 	<spring:message code="hr.viewCompetence.title.EXAM_NAME"/>
						 	<!--考试名-->
						 </th>
						 <th width="100">
						 	<spring:message code="hr.viewCompetence.title.QUALIFICATION_NAME"/>
						 	<!--证书名称-->
						 </th>
						 <th width="100">
						 	<spring:message code="hr.viewCompetence.title.LANGUAGE_LEVEL_NAME"/>
						 	<!--等级-->
						 </th>
						 <th width="100">
						 	<spring:message code="hr.viewCompetence.title.MARK"/>
						 	<!--分数-->
						 </th>
						 <th width="100">
						 	<spring:message code="hr.viewCompetence.title.DATE_OBTAINED_QUALIFICATION"/>
						 	<!--取得日期-->
						 </th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${languageLevelList}" var="item" varStatus="i">
					
						<tr target="sid" rel="${item.LANGUAGE_NO}">
							<td>${item.LANGUAGE_TYPE_NAME}</td>
							<td>${item.EXAM_NAME}</td>
							<td>${item.QUALIFICATION_NAME}</td>
							<td>${item.LANGUAGE_LEVEL_NAME}</td>
							<td>${item.MARK}</td>
							<td>${item.DATE_OBTAINED}</td>
						</tr>
					
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	
</div>

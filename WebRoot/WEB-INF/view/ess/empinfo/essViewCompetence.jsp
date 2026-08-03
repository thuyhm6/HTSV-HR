<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="pageContent">

	<div class="panel">
		<h1>
			<spring:message code="hr.viewPersonalInfo.title.STAFF_FOUNDATION_INFORMATION"/>
			<!--员工基础信息-->
		</h1>
		<div layoutH="365"><%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead1.jsp"%></div>
	</div>
	<div class="panel">
		<h1>
			<spring:message code="hr.viewCompetence.title.CREDENTIALS"/>
			<!--资格证书-->
		</h1>
		
		<div>
	
	<table class="table" width="100%">
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
			<table class="table" width="100%">
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
							<td><fmt:formatNumber value="${item.MARK}" pattern="#,##0.0"/></td>
							<td>${item.DATE_OBTAINED}</td>
						</tr>					
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

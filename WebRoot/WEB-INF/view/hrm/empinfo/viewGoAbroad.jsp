<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="pageContent">
	
	<c:set value="/hrm/empinfo/viewGoAbroad" var="turn_to_url" />
	
	<div class="panel">
		<h1>
			<spring:message code="hr.viewPersonalInfo.title.STAFF_FOUNDATION_INFORMATION"/>
			<!--员工基础信息-->
		</h1>
		<div layoutH="365"><%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead1.jsp"%></div>
	</div>

	<c:set value="1000" var="add_width" />
	<c:set value="600" var="add_height" />
	<c:set value="dialog" var="add_tab"/>
	<c:set value="/hrm/empinfo/viewGoAbroadInfo?PERSON_ID=${personInfo.PERSON_ID }" var="add_Url"/>
	
	
	
	<c:set value="800" var="delete_width" />
	<c:set value="500" var="delete_height" />
	<c:set value="dialog" var="delete_tab"/>
	<c:set value="0" var="delete_range" />
	<c:set value="0" var="delete_mask_exit"/>
	<c:set value="true" var="delete_mask"/>
	<c:set value="/hrm/empinfo/deleteGoAbroad?PERSON_ID=${personInfo.PERSON_ID}" var="delete_Url"/>
	
	
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="1000" var="edit_width"/>
	<c:set value="500" var="edit_height"/>
	<c:set value="/hrm/empinfo/updateGoAbroadInfo?PERSON_ID=${personInfo.PERSON_ID}" var="edit_Url"/>
	<div class="panel">
		<h1>
			<spring:message code="hr.viewGoAbroad.title.ABROAD_INFORMATION"/>
			<!--出国信息-->
		</h1>
		
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<div>
	<table class="table" width="101%">
		<thead>
			<tr>
				<th width="80">
					<spring:message code="hr.viewGoAbroad.title.COUNTRY_NAME"/>
					<!--国家-->
				</th>
				<th width="80">
					<spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE"/>
					<!--开始时间-->
				</th>
				<th width="80">
					<spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE"/>
					<!--结束日期-->
				</th>
				<th width="80">
					<spring:message code="hr.viewGoAbroad.title.COST"/>
					<!--费用(元)-->
				</th>
				<th width="80">
					<spring:message code="hr.viewGoAbroad.title.PURPOSE"/>
					<!--目的-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${goAbroadList}" var="item" varStatus="i">
			
				<tr>
					<td>${item.COUNTRY_NAME}</td>
					<td>${item.START_DATE}</td>
					<td>${item.END_DATE}</td>
					<td style="text-align:right"><fmt:formatNumber value="${item.COST}" pattern="#,##0.00"/></td>
					<td>${item.PURPOSE}</td>
				</tr>
			
			</c:forEach>
			
		</tbody>
	</table>
	</div>	
	
</div>

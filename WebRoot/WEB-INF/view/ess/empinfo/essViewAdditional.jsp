<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<div class="panel" defH="170">
		<h1>			
		<spring:message code="hr.viewPersonalInfo.title.STAFF_FOUNDATION_INFORMATION"/>
			<!--员工基础信息-->
		</h1>
		<div><%@ include file="/WEB-INF/view/ess/empinfo/viewPersonalInfoHead.jsp"%></div>
	</div>
	<table class="table" width="100%" layoutH="100">
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
					<td>${item.INFO_TYPE_CODE}</td>
					<td>${item.REMARK}</td>
					<td>${item.CREATED_BY}</td>
				</tr>			
			</c:forEach>		
		</tbody>
	</table>	
</div>

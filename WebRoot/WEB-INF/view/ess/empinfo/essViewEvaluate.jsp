<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
	
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
			<spring:message code="hr.viewEvaluate.title.EVALUATEIMFORMATION"/>
			<!--评价信息-->
		</h1>
	<div>	
	<table class="table" width="100%" >
		<thead>
			<tr>
				<th width="100">
					<spring:message code="hr.viewEvaluate.title.EV_PERIOD"/>
					<!--评价期间-->
				</th>
				<th width="100">
					<spring:message code="hr.viewEvaluate.title.EV_TYPE_NAME"/>
					<!--评价类型-->
				</th>
				<th width="100">
					<spring:message code="hr.viewEvaluate.title.EV_MARK"/>
					<!--评价分数-->
				</th>
				<th width="100">
					<spring:message code="hr.viewEvaluate.title.EV_GRADE_NAME"/>
					<!--评价等级-->
				</th>
				<th width="100">
					<spring:message code="hr.viewPromote.title.REMARK"/>
					<!--备注-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${EvsInfo}" var="item" varStatus="i">			
				<tr target="PERSON_ID" rel="${item.PERSON_ID}&EV_PERIOD=${item.EV_PERIOD}">
					<td>${item.EV_PERIOD}</td>
					<td>${item.EV_TYPE_NAME}</td>
					<td>${item.EV_MARK}</td>
					<td>${item.EV_GRADE_NAME}</td>
					<td>${item.EV_REMARK}</td>
				</tr>			
			</c:forEach>
		</tbody>
	</table>
	</div>
	</div>
</div>
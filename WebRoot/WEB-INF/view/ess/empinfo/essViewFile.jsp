<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">	
	<div class="panel">
		<h1>
			<spring:message code="hr.viewPersonalInfo.title.STAFF_FOUNDATION_INFORMATION"/>
			<!--员工基础信息-->
		</h1>
		<div><%@ include file="/WEB-INF/view/ess/empinfo/viewPersonalInfoHead.jsp"%></div>
	</div>
	
	<div class="panel">
    		<h1>
			<spring:message code="hr.viewContract.title.FILE_INFORMATION"/>
			<!--档案信息-->
		</h1>
	<div>
			<table class="table" width="100%" >
				<thead>
					<tr>
						<th width="100">
							<spring:message code="hr.viewContract.title.FILE_NO"/>
							<!--档案号-->
						</th>
						<th width="100">
							<spring:message code="hr.viewContract.title.FILE_TYPE_NAME"/>
							<!--档案类型-->
						</th>
						<th width="100">
							<spring:message code="hr.viewContract.title.FILE_RELATION_NAME"/>
							<!--档案关系-->
						</th>
						<th width="100">
							<spring:message code="hr.viewContract.title.FILE_INTO_YN_NAME"/>
							<!--档案转入-->
						</th>
						<th width="100">
							<spring:message code="hr.viewContract.title.FILE_DATE"/>
							<!--转入日期-->
						</th>
						<th width="100">
							<spring:message code="hr.viewContract.title.FILE_CONTENT"/>
							<!--档案内容-->
						</th>
						<th width="100">
							<spring:message code="hr.viewContract.title.FILE_AREA_NAME"/>
							<!--存档归属地-->
						</th>
						<th width="100">
							<spring:message code="hr.viewContract.title.COST_END_DATE"/>
							<!--存档费截至日-->
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${fileList}" var="item" varStatus="i">
					
						<tr target="sid" rel="${item.FILE_EMP_NO}">
							<td>${item.FILE_NO}</td>
							<td>${item.FILE_TYPE_NAME}</td>
							<td>${item.FILE_RELATION_NAME}</td>
							<td>${item.FILE_INTO_YN}</td>
							<td>${item.FILE_DATE}</td>
							<td>${item.FILE_CONTENT}</td>
							<td>${item.FILE_AREA_NAME}</td>
							<td>${item.COST_END_DATE}</td>
						</tr>					
					</c:forEach>
				</tbody>
	</table>
	</div>
	</div>
</div>

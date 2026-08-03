<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">

	<input type="hidden" id="turn_to_url" name="turn_to_url" value="/hrm/empinfo/viewPromote">

	
	<div class="panel">
		<h1>
			<spring:message code="hr.viewPersonalInfo.title.STAFF_FOUNDATION_INFORMATION"/>
			<!--员工基础信息-->
		</h1>
		<div layoutH="365"><%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead1.jsp"%></div>
	</div>
	
	<div class="panel">
		<h1>
			<spring:message code="hr.viewPromote.title.MATTERS_TO_THE"/>
			<!--发令事项-->
		</h1>
		<div id="edudiv">
			<table class="table table-border-lrt" width="101%">
				<thead>
					<tr>
						<th width="100">
							<spring:message code="hr.viewPromote.title.RESHUFFLED_TYPE"/>
							<!--异动类型-->
						</th>
						<th width="100">
							<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
							<!--部门-->
						</th>
						<th width="100">
							<spring:message code="hr.viewPersonalInfo.title.POSITION_NAME"/>
							<!--职(岗)位-->
						</th>
						<th width="100">
							<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
							<!--职级名称（职务）-->
						</th>
						<th width="100">
							<spring:message code="hr.viewPersonalInfo.title.DUTY_NAME"/>
							<!--职责-->
						</th>
						<th width="100">
							<spring:message code="hr.viewPersonalInfo.title.STATUS_NAME"/>
							<!--员工状态-->
						</th> 
						<th width="100">
							<spring:message code="hr.viewPromote.title.EFFECTIVE_DATE"/>
							<!--生效日期-->
						</th>
						<th width="100">
							<spring:message code="hr.viewPromote.title.RESHUFFLED_CONTENT"/>
							<!--异动内容-->
						</th>
						<th width="100">
							<spring:message code="hr.viewPromote.title.REMARK"/>
							<!--备注-->
						</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${expInsideList}" var="item" varStatus="i">
					
						<tr target="sid" rel="${item.EXP_INSIDE_NO}">
							<td>${item.TRANS_NO_NAME}</td>
							<td>${item.DEPTNAME}</td>
							<td>${item.POSITION_NAME}</td>
							<td>${item.POST_NAME}</td>
							<td>${item.DUTY_NAME}</td>
							<td>${item.STATUS_NAME}</td>
							<td>${item.START_DATE}</td>
							<td>${item.TRANS_NAME}</td>
							<td>${item.REMARK}</td>
		
						</tr>
					
					</c:forEach>					
				</tbody>
			</table>
		</div>
		</div>
		<div style="clear:both;"></div>
		<div class="panel">
		<h1>
			<spring:message code="hr.viewPromote.title.LEFT_MATTERS"/>
			<!--离职事项-->
		</h1>
		<div id="edudiv">
			<table class="table table-border-lrt" width="101%">
				<thead>
					<tr>
						<th width="100">
							<spring:message code="hr.viewPromote.title.RESIGN_DATE"/>
							<!--离职日期-->
						</th>
						<th width="100">
							<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
							<!--部门-->
						</th>
						<th width="100">
							<spring:message code="hr.viewPersonalInfo.title.POSITION_NAME"/>
							<!--职(岗)位-->
						</th>
						<th width="100">
							<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
							<!--职级名称（职务）-->
						</th>
						<th width="100">
							<spring:message code="hr.viewPromote.title.SETTLEMENT_DATE"/>
							<!--工资结算日-->
						</th>
						<th width="100">
							<spring:message code="hr.viewPromote.title.RESIGN_TYPE_NAME"/>
							<!--离职类型-->
						</th>
						<th width="100">
							<spring:message code="hr.viewPromote.title.RESIGN_REASON"/>
							<!--离职原因-->
						</th>
						<th width="100">
							<spring:message code="hr.viewPromote.title.REMARK"/>
							<!--备注-->
						</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${resignationInfo}" var="item" varStatus="i">
					
						<tr target="sid" rel="${item.PERSON_ID}">
							<td>${item.RESIGN_DATE}</td>
							<td>${item.DEPTNAME}</td>
							<td>${item.POSITION_NAME}</td>
							<td>${item.POST_NAME}</td>
							<td>${item.SETTLEMENT_DATE}</td>
							<td>${item.RESIGN_TYPE_NAME}</td>
							<td>${item.RESIGN_REASON}</td>
							<td>${item.REMARK}</td>
						</tr>
					
					</c:forEach>					
				</tbody>
			</table>
		</div>
	</div>
	
	
	
		<div class="panel">
		<h1>
			<spring:message code="hr.viewTranslate.title.PART_TIME_JOB"/>
			<!--兼职-->
		</h1>
		<div id="edudiv">
			<table class="table table-border-lrt" width="101%">
				<thead>
					<tr>
						<th width="100">
							<spring:message code="hr.viewTranslate.title.THE_TYPE"/>
							<!--发令类型-->
						</th>
						<th width="100">
							<spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE"/>
							<!--开始日期-->
						</th>
						<th width="100">
							<spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE"/>
							<!--结束日期-->
						</th>
						<th width="100">
							<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
							<!--部门-->
						</th>
						<th width="100">
							<spring:message code="hr.viewPersonalInfo.title.POSITION_NAME"/>
							<!--职(岗)位-->
						</th>
						<th width="100">
							<spring:message code="hr.viewTranslate.title.PART_TIME_TYPE"/>
							<!--兼职类型-->
						</th> 
						<th width="100">
							<spring:message code="hr.viewPersonalInfo.title.PLU_DEPTNAME"/>
							<!--兼职部门-->
						</th>
						<th width="100">
							<spring:message code="hr.viewPersonalInfo.title.PLU_DUTY_NAME"/>
							<!--兼职职责-->
						</th>
						<th width="100">
							<spring:message code="hr.viewTranslate.title.PART_TIME_CONTENTS"/>
							<!--兼职事由-->
						</th>
						<th width="100">
							<spring:message code="hr.viewPromote.title.REMARK"/>
							<!--备注-->
						</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach items="">
						<!-- 循环取值 -->
					</c:forEach>				
				</tbody>
			</table>
		</div>
		</div>
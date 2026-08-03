<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div class="pageContent">

	<c:set value="/hrm/empinfo/viewReward" var="turn_to_url" />
	
	<div class="panel">
		<h1>
			<spring:message code="hr.viewPersonalInfo.title.STAFF_FOUNDATION_INFORMATION"/>
			<!--员工基础信息-->
		</h1>
		<div layoutH="365"><%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead1.jsp"%></div>
	</div>
	
	<div class="panel">
		<h1>
			<spring:message code="hr.viewReward.title.REWARD"/>
			<!--奖励-->
		</h1>
			<table class="table" width="101%">
				<thead>
					<tr>
						<th width="100">
							<spring:message code="hr.viewReward.title.REWARD_DATE"/>
							<!--奖励日期-->
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
							<spring:message code="hr.viewReward.title.REWARD_TYPE_NAME"/>
							<!--奖励类型-->
						</th>
						<th width="100">
							<spring:message code="hr.viewReward.title.REWARD_BONUS"/>
							<!--奖励金额-->
						</th>
						<th width="100">
							<spring:message code="hr.viewReward.title.REWARD_CONTENTS"/>
							<!--功绩内容-->
						</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${rewardList}" var="item" varStatus="i">
					
						<tr target="PERSON_ID" rel="${item.PERSON_ID}&REWARD_DATE=${item.REWARD_DATE}">
							<td>${item.REWARD_DATE}</td>
							<td>${item.DEPTNAME}</td>
							<td>${item.POSITION_NAME}</td>
							<td>${item.POST_NAME}</td>
							<td>${item.REWARD_TYPE_NAME}</td>
							<td>${item.REWARD_BONUS}</td>
							<td>${item.REWARD_CONTENTS}</td>
						</tr>
					
					</c:forEach>
					
				</tbody>
			</table>
		</div>
	</div>
	<div class="panel">
		<h1>
			<spring:message code="hr.viewReward.title.PUNISH"/>
			<!--惩戒-->
		</h1>
		<div>
			<table class="table" width="101%">
				<thead>
					<tr>
						<th width="100">
							<spring:message code="hr.viewReward.title.DATE_PUNISHED"/>
							<!--惩戒日期-->
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
							<spring:message code="hr.viewReward.title.PUN_TYPE_NAME"/>
							<!--惩戒方式-->
						</th>
						<th width="100">
							<spring:message code="hr.viewReward.title.PUN_BONUS"/>
							<!--惩戒金额-->
						</th>
						<th width="100">
							<spring:message code="hr.viewReward.title.PUN_CONTENTS"/>
							<!--惩戒事由-->
						</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${punishmentList}" var="item" varStatus="i">
					
						<tr target="PERSON_ID" rel="${item.PERSON_ID}&PUN_TYPE_ID=${item.PUN_TYPE_ID}&DATE_PUNISHED=${item.DATE_PUNISHED }">
							<td>${item.DATE_PUNISHED}</td>
							<td>${item.DEPTNAME}</td>
							<td>${item.POSITION_NAME}</td>
							<td>${item.POST_NAME}</td>
							<td>${item.PUN_TYPE_NAME}</td>
							<td><fmt:formatNumber value="${item.PUN_BONUS}" pattern="#,##0.00"/></td>
							<td>${item.REWARD_CONTENTS}</td>
						</tr>					
					</c:forEach>					
				</tbody>
			</table>
	</div>
</div>

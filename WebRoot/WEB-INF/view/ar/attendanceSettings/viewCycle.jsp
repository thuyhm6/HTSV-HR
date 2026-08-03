<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ar/attendanceSettings/viewCycle" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<spring:message code="ar.viewcycle.title.qujianmingcheng"/>：<input type="text" name="seach_STAT_NAME" value="${STAT_NAME}"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="button.search"/></button></div></div></li>
				</ul>
			</div>
		</div>
	</form>	
</div>
<div class="pageContent">
	
	<c:set value="dialog" var="add_tab"/>
	<c:set value="800" var="add_width"/>
	<c:set value="400" var="add_height"/>
	<c:set value="/ar/attendanceSettings/addCycleView" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/ar/attendanceSettings/deleteCycleInfo?NO={statno}" var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="800" var="edit_width"/>
	<c:set value="400" var="edit_height"/>
	<c:set value="/ar/attendanceSettings/updateCycleView?NO={statno}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="10"><spring:message code="ar.viewcycle.title.xuhao"/></th>
				<th width="20"><spring:message code="ar.viewcycle.title.qujianmingcheng"/></th>
				<th width="10" orderField="orderField"><spring:message code="ar.viewcycle.title.kaishiri"/></th>
				<th width="10"><spring:message code="ar.viewcycle.title.jieshuri"/></th>
				<th width="10"><spring:message code="ar.viewcycle.title.zhuangtai"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${cycleList}" var="cycle" varStatus="i">
			
				<tr target="statno" rel="${cycle.STAT_NO}">
					<td>${i.index + 1}</td>
					<td>${cycle.STAT_NAME}</td>
					<td>${cycle.START_DAY}</td>
					<td>${cycle.END_DAY}</td>
					<td><img src="/resources/images/a_${cycle.ACTIVITY}.gif"></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/ar/attendanceSettings/viewCycle" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/sys/postManagement/viewDutyList" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="sys.postManage.title.dutyName"/><!--职责名称-->：
					<input type="text" name="seach_DUTY_NAME" value="${DUTY_NAME}"/>					
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.search"/><!-- 检索 --></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>

<div class="pageContent">
	<c:set value="dialog" var="add_tab"/>
	<c:set value="400" var="add_width"/>
	<c:set value="350" var="add_height"/>
	<c:set value="/sys/postManagement/addDutyView" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/sys/postManagement/deleteDutyInfo?NO={sid}&DUTY_NO={sid}" var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="400" var="edit_width"/>
	<c:set value="350" var="edit_height"/>
	<c:set value="/sys/postManagement/updateDutyView?NO={sid}&DUTY_NO={sid}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="80"><spring:message code="sys.affirm.title.duty"/><!--职责--></th>
				<th width="80"><spring:message code="sys.postManage.title.ifUsed"/><!--是否使用--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${dutyList}" var="item" varStatus="i">
			
				<tr target="sid" rel="${item.DUTY_NO}">
					<td>${item.DUTY_NAME}</td>
					<td><c:if test="${item.ACTIVITY eq '1'}"><spring:message code="sys.affirm.title.yes"/><!--是--></c:if>
					<c:if test="${item.ACTIVITY eq '0'}"><spring:message code="sys.affirm.title.no"/><!--否--></c:if>
					</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
		<c:set value="/sys/postManagement/viewDutyList" var="pageUrl"/>
        <%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>

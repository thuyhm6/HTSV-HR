<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/sys/rightsManagement/viewRolesGroup" method="post" rel="pagerForm">
	<input type="hidden" name="seach_SYS_TYPE" value="${SYS_TYPE }"/>
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="sys.rights.title.privilegeName"/><!--权限名称-->：
					<input type="text" name="seach_SCREEN_GRANT_NAME_ZH" value="${SCREEN_GRANT_NAME_ZH}"/>
				</td>
				<td>
					菜单名称：
					<input type="text" name="seach_MENU_NAME_ZH" value=""/>
				</td>
				<!--<td>
					人员类型组：
					 <ait:SelectEmpTypeCode name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="hr" type="group"/>
				
				</td>
			--></tr>
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
	<c:set value="navTab" var="add_tab"/>
	<c:set value="800" var="add_width"/>
	<c:set value="400" var="add_height"/>
	<c:set value="/sys/rightsManagement/addRolesGroupView?SYS_TYPE=${SYS_TYPE }" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/sys/rightsManagement/deleteRolesGroupView?NO={sid}" var="delete_Url"/>
	<c:set value="navTab" var="edit_tab"/>
	<c:set value="800" var="edit_width"/>
	<c:set value="400" var="edit_height"/>
	<c:set value="/sys/rightsManagement/updateRolesGroupView?NO={sid}&SYS_TYPE=${SYS_TYPE }" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="80"><spring:message code="sys.rights.title.privilegeIndex"/><!--权限序号--></th>
				<th width="150"><spring:message code="sys.rights.title.privilegeName"/><!--权限名称--></th>
				<th width="150"><spring:message code="sys.basic.title.companyName"/><!--公司名称--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${rolesGroupList}" var="item" varStatus="i">
				<tr target="sid" rel="${item.ROLE_NO}&seach_PARENT_CODE_NO=${item.MENU_NO}&pageNum=1">
					<td>${item.ROLE_ID}</td>
					<td>${item.SCREEN_NAME}</td>
					<td>${item.CPNY_NAME}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<c:set value="/sys/rightsManagement/viewRolesGroup" var="pageUrl"/>
        <%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>

<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
	<form id="viewMenuList_Form" onsubmit="return navTabSearch(this);" action="/sys/menu/viewMenuList" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="sys.menu.title.menuName"/><!--菜单名称-->：
					<input type="text" name="seach_MENU_NAME" value="${MENU_NAME}"/>
					
					<spring:message code="sys.menu.title.parentMenu"/><!--父级菜单名称-->：
					<input type="text" name="seach_ParentMENU_NAME" value="${ParentMENU_NAME}"/>
					
					<spring:message code="sys.menu.title.menuLevel"/><!--菜单级别-->：
					        <select name="seach_DEPTH" >
									<option value="" ><spring:message code="sys.affirm.title.choose"/></option>
									<option value="0" <c:if test="${DEPTH eq '0' }">selected</c:if>>1</option>
									<option value="1" <c:if test="${DEPTH eq '1' }">selected</c:if>>2</option>
									<option value="2" <c:if test="${DEPTH eq '2' }">selected</c:if>>3</option>
							</select>
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
	<c:set value="450" var="add_width"/>
	<c:set value="520" var="add_height"/>
	<c:set value="/sys/menu/addNewMenuView" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/sys/menu/disableMenu?MenuId={menuid}" var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="450" var="edit_width"/>
	<c:set value="520" var="edit_height"/>
	<c:set value="/sys/menu/editMenuView?NO={menuid}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="50"><spring:message code="sys.menu.title.menuCode"/><!--菜单代码--></th>
				<th width="90"><spring:message code="sys.menu.title.menuName"/><!--菜单名称--></th>
				<th width="100"><spring:message code="sys.menu.title.parentMenu"/><!--父级菜单--></th>
				<th width="230"><spring:message code="sys.menu.title.menuURL"/><!--菜单URL--></th>
				<th width="120"><spring:message code="sys.basic.title.createBy"/><!--创建者--></th>
				<th width="80"><spring:message code="sys.basic.title.createDate"/><!--创建时间--></th>
				<th width="80"><spring:message code="hr.viewPersonalInfo.title.ORDERTYPE"/><!--排序--></th>
				<th width="45"><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${MENUS}" var="menu" varStatus="i">			
				<tr target="menuid" rel="${menu.MENU_NO}">
					<td>${menu.MENU_CODE}</td>
					<td>${menu.MENU_NAME_ZH}</td>
					<td>${menu.PNAME}</td>
					<td>${menu.MENU_URL}</td>
					<td>${menu.CNAME}</td>
					<td>${menu.CREATE_DATE}</td>
					<td>${menu.ORDERNO}</td>
					<td>${menu.ACTIVITY}</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>		
	<c:set value="/sys/menu/viewMenuList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
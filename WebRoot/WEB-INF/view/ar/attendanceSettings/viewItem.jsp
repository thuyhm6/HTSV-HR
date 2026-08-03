<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ar/attendanceSettings/viewItem" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td>
					<!-- 项目名称 --><spring:message code="ar.viewItem.title.xiangmumingcheng"/>：<input type="text" name="seach_ITEM_NAME" value="${ITEM_NAME}"/>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 检索 --><spring:message code="public.title.search"/></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>

<div class="pageContent">
	
	<c:set value="dialog" var="add_tab"/>
	<c:set value="600" var="add_width"/>
	<c:set value="450" var="add_height"/>
	<c:set value="/ar/attendanceSettings/addItemView" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/ar/attendanceSettings/deleteItem?NO={itemno}" var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="600" var="edit_width"/>
	<c:set value="400" var="edit_height"/>
	<c:set value="/ar/attendanceSettings/updateItemView?NO={itemno}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="80"><!-- 项目ID --><spring:message code="ar.viewItem.title.xiangmumingID"/></th>
				<th width="80"><!-- 项目名称 --><spring:message code="ar.viewItem.title.xiangmumingcheng"/></th>
				<th width="80"><!-- 简称 --><spring:message code="ar.viewItem.title.jiancheng"/></th>
				<%--
				<th width="80">单位</th>
				 --%>
				<%-- <th width="150"><!-- 说明 --><spring:message code="ar.viewItem.title.shuoming"/></th> --%>
				<th width="80"><!-- 项目组 --><spring:message code="ar.viewItem.title.xiangmuzu"/></th>
				<th width="80"><!-- 活跃状态 --><spring:message code="ar.viewcycle.title.huoyuezhuangtai"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="item">
			
				<tr target="itemno" rel="${item.ITEM_NO}">
					<td>${item.ITEM_ID}</td>
					<td>${item.ITEM_NAME}</td>
					<td>${item.SHORT_NAME}</td>
					<%--
					<td><c:if test="${item.UNIT eq 'MINUTE'}">分钟</c:if>
					<c:if test="${item.UNIT eq 'HOUR'}">小时</c:if></td>
					--%>
					<%-- <td>${item.DESCRIPTION}</td> --%>
					<td>${item.ITEM_GROUP}</td>
					<td>
						<c:if test="${item.ACTIVITY eq 1}"><img src="/resources/images/a_1.gif" /></c:if>
						<c:if test="${item.ACTIVITY eq 0}"><img src="/resources/images/a_0.gif" /></c:if>
					</td>
				</tr>
			
			</c:forEach>
			
		</tbody>
	</table>
	
	<c:set value="/ar/attendanceSettings/viewItem" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	
</div>

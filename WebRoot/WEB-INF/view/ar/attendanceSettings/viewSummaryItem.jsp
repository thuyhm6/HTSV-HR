<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ar/attendanceSettings/viewSummaryItem" method="post" rel="pagerForm">
		<div class="searchBar">
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
	<c:set value="500" var="add_width"/>
	<c:set value="400" var="add_height"/>
	<c:set value="/ar/attendanceSettings/addSummaryItemView" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/ar/attendanceSettings/deleteSummaryItemInfo?NO={staItemdId}" var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="500" var="edit_width"/>
	<c:set value="400" var="edit_height"/>
	<c:set value="/ar/attendanceSettings/updateSummaryItemView?NO={staItemdId}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="20%"><!-- 汇总项目ID --><spring:message code="ar.viewsummaryitem.title.huizongxiangmuID"/></th>
				<th width="20%"><!-- 汇总项目名称 --><spring:message code="ar.viewsummaryitem.title.huizongxiangmumingcheng"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr target="staItemdId" rel="${item.ITEM_NO}">
					<td>${item.STA_ITEM_ID}</td>
					<td>${item.ITEM_NAME}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/ar/attendanceSettings/viewSummaryItem" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>		
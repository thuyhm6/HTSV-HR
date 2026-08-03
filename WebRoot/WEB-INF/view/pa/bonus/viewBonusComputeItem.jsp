<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/bonus/viewBonusComputeItem" method="post" rel="pagerForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->：
					<input type="text" name="seach_ITEM_NAME" value="${ITEM_NAME }"/>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.search"/><!--检索--></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>



<div class="pageContent">

	<c:set value="/pa/bonus/addBonusComputeItemView" var="add_Url"/>
	<c:set value="600" var="add_width" />
	<c:set value="400" var="add_height" />
	<c:set value="/pa/bonus/deleteBonusComputeItemInfo?NO={sid}" var="delete_Url"/>
	<c:set value="/pa/bonus/updateBonusComputeItemView?NO={sid}" var="edit_Url"/>
	<c:set value="600" var="edit_width" />
	<c:set value="400" var="edit_height" />
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="25%"><spring:message code="pa.insurance.title.projectID"/><!--项目ID--></th>
				<th width="25%"><spring:message code="pa.insurance.title.projectName"/><!--项目名称--></th>
				<th width="25%"><spring:message code="pa.insurance.title.dataType"/><!--数据类型--></th>
				<th width="25%"><spring:message code="pa.insurance.title.description"/><!--描述--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="item" varStatus="i">
			
				<tr target="sid" rel="${item.ITEM_NO}&ITEM_ID=${item.ITEM_ID}">
					<td class="td_center">${item.ITEM_ID}</td>
					<td class="td_center">${item.ITEM_NAME}</td>
					<td class="td_center">${item.DATATYPE}</td>
					<td class="td_center">${item.DESCR}</td>
				</tr>
			</c:forEach>
			
		</tbody>
	</table>
	
	<c:set value="/pa/bonus/viewBonusComputeItem" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	
</div>

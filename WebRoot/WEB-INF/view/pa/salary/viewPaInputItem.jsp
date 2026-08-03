<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/salary/viewPaInputItem" method="post" rel="pagerForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->：
					<input type="text" name="seach_KEY" value="${KEY }" />
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

<div class="pageContent" >	
	<c:set value="/pa/salary/addPaInputItemView?TABLE_NAME=PA_HR_V" var="add_Url"/>
	<c:set value="600" var="add_width"/>
	<c:set value="500" var="add_height"/>
	<c:set value="/pa/salary/deletePaInputItemInfo?NO={sid}" var="delete_Url"/>
	<c:set value="/pa/salary/updatePaInputItemView?NO={sid}" var="edit_Url"/>
	<c:set value="600" var="edit_width"/>
	<c:set value="500" var="edit_height"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	
	<table class="table" width="100%" layoutH="142">
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
				<tr target="sid" rel="${item.PARAM_ITEM_NO}">
					<td>${item.PARAM_ITEM_ID}</td>
					<td>${item.PARAM_ITEM_NAME}</td>
					<td>
					<c:if test="${item.DATA_TYPE eq 'NUMBER(14,4)' }">
					<spring:message code="pa.insurance.title.numberType"/><!--数字类型--></c:if>
					<c:if test="${item.DATA_TYPE eq 'VARCHAR(100)' }">
					<spring:message code="pa.insurance.title.varcharType"/><!--字符型--></c:if>
					</td>
					<td>${item.DESCR}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/salary/viewPaInputItem" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
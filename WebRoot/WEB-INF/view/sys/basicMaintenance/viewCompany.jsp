<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/sys/basicMaintenance/viewCompany" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="sys.basic.title.companyName"/><!--公司名称-->：
					<input type="text" name="seach_CONTENT" value="${CONTENT}"/>
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
	<c:set value="800" var="add_width"/>
	<c:set value="400" var="add_height"/>
	<c:set value="/sys/basicMaintenance/addCompanyView" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/sys/basicMaintenance/deleteCompany?NO={sid}" var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="800" var="edit_width"/>
	<c:set value="400" var="edit_height"/>
	<c:set value="/sys/basicMaintenance/updateCompanyView?NO={sid}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<table class="table" width="101%" layoutH="206" nowrapTD="true">
		<thead>
			<tr>
				<th width="80"><spring:message code="sys.basicMaint.title.companyName"/><!--公司-->ID</th>
				<th width="100"><spring:message code="sys.basicMaint.title.companyArea"/><!--公司区域--></th>
				<th width="100"><spring:message code="sys.basic.title.companyName"/><!--公司名称--></th>
				<th width="100"><spring:message code="sys.basicMaint.title.companyAddress"/><!--公司地址--></th>
				<th width="100"><spring:message code="sys.basicMaint.title.companyIntroduction"/><!--公司介绍--></th>
				<th width="100"><spring:message code="sys.basicMaint.title.companyHistory"/><!--公司历史--></th>
				<th width="100"><spring:message code="sys.basicMaint.title.companyNetAddress"/><!--公司网址--></th>
				<th width="80"><spring:message code="sys.basicMaint.title.companyTelPhoneNo"/><!--公司电话--></th>
				<th width="80"><spring:message code="sys.basicMaint.title.companyFaxNo"/><!--公司传真号--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${companyList}" var="item" varStatus="i">			
				<tr target="sid" rel="${item.CPNY_NO}">
					<td>${item.CPNY_ID}</td>
					<td>${item.CPNY_LOCATION}</td>
					<td>${item.CONTENT}</td>
					<td>${item.CPNY_ADDR}</td>
					<td>${item.CPNY_INTRO}</td>
					<td>${item.CPNY_HISTORY}</td>
					<td>${item.CPNY_WEB_ADDR}</td>
					<td>${item.CPNY_TEL_NO}</td>
					<td>${item.CPNY_FAX_NO}</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
		<c:set value="/sys/basicMaintenance/viewCompany" var="pageUrl"/>
        <%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
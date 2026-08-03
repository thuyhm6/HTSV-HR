<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/sys/menu/viewMenuParamList" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="sys.menu.title.menuName"/><!--菜单名称-->：
					<input type="text" name="seach_MENU_NAME" value="${MENU_NAME}"/>
				</td>
				<td>
					<spring:message code="sys.basic.title.companyName"/><!--公司名称-->：
					<input type="text" name="seach_CPNY_NAME" value="${CPNY_NAME}"/>
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
	<c:set value="navTab" var="add_tab"/>
	<c:set value="800" var="add_width"/>
	<c:set value="400" var="add_height"/>
	<c:set value="${add_name}" var="add_name"/>
	<c:set value="/sys/menu/editMenuParamView" var="add_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="90"><spring:message code="sys.menu.title.menuName"/><!--菜单名称--></th>
				<th width="100"><spring:message code="sys.basic.title.companyName"/><!--公司名称--></th>
				<th width="120"><spring:message code="sys.basic.title.createBy"/><!--创建者--></th>
				<th width="80"><spring:message code="sys.basic.title.createDate"/><!--创建时间--></th>
				<th width="45"><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${MenuParam}" var="menuP" varStatus="i">			
				<tr target="mpid" rel="${menuP.PARAM_NO}&MENU_NO=${menuP.MENU_NO}&CPNY_ID=${menuP.CPNY_ID}">
					<td>${menuP.MENU_NAME_ZH}</td>
					<td>${menuP.CPNY_NAME_ZH}</td>
					<td>${menuP.CHINESE_NAME}</td>
					<td>${menuP.CREATE_DATE}</td>
					<td>${menuP.ACTIVATE}</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>		
	<c:set value="/sys/menu/viewMenuParamList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
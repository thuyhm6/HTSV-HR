<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/sys/postManagement/viewPostList" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="sys.postManage.title.postName"/><!--职务名称-->：
					<input type="text" name="seach_POST_NAME" value="${POST_NAME}"/>
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
	<c:set value="500" var="add_width"/>
	<c:set value="400" var="add_height"/>
	<c:set value="/sys/postManagement/addPostView" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/sys/postManagement/deletePost?NO={sid}" var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="500" var="edit_width"/>
	<c:set value="400" var="edit_height"/>
	<c:set value="/sys/postManagement/updatePostView?NO={sid}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="50"><spring:message code="sys.postManage.title.post"/><!--职级名称(职务)--></th>
				<th width="50"><spring:message code="sys.basic.title.createDate"/><!--创建时间--></th>
				<th width="50"><spring:message code="sys.basic.title.createBy"/><!--创建者--></th>
				<th width="50"><spring:message code="sys.postManage.title.updateDate"/><!--更新时间--></th>
				<th width="50"><spring:message code="sys.postManage.title.updateBy"/><!--更新者--></th>
				<th width="20"><spring:message code="sys.essParam.title.ifEnabled"/><!--是否启用--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${postList}" var="item" varStatus="i">
				<tr target="sid" rel="${item.POST_NO}">
					<td>${item.POST_NAME}</td>
					<td>${item.CREATE_DATE}</td>
					<td>${item.CREATENAME}</td>
					<td>${item.UPDATE_DATE}</td>
					<td>${item.UPDATENAME}</td>
					<td><c:if test="${item.ACTIVITY eq '1'}"><spring:message code="sys.affirm.title.yes"/><!--是--></c:if>
						<c:if test="${item.ACTIVITY eq '0'}"><spring:message code="sys.affirm.title.no"/><!--否--></c:if>
					</td>
				</tr>
			</c:forEach>
			
		</tbody>
	</table>
	<form id="pagerForm" method="post" action="/sys/postManagement/viewPostList">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
	</form>
	
	<div class="panelBar">
		<div class="pages">
			<span><spring:message code="public.title.view"/><!-- 显示 --></span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
				<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
				<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
			</select>
			<span><spring:message code="public.title.tiao"/><!--条-->,<spring:message code="public.title.gong"/><!--共-->${totalCount}<spring:message code="public.title.tiao"/><!--条--></span>
		</div>	
		<div class="pagination" targetType="navTab" totalCount="${totalCount}" numPerPage="${numPerPage}" pageNumShown="10" currentPage="${pageNum}"></div>
	</div>
</div>
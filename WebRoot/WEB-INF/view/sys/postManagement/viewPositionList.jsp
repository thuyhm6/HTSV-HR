<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/sys/postManagement/viewPositionList" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="sys.postManage.title.position"/><!--职位-->：
					<input type="text" name="seach_POSITION_NAME" value="${POSITION_NAME}"/>
					<spring:message code="sys.postManage.title.postType"/><!--职类-->：
					<input type="text" name="seach_GATEGORY_NAME" value="${GATEGORY_NAME}"/>
					<spring:message code="sys.postManage.title.postGrade"/><!--职级-->：
					<input type="text" name="seach_POST_GRADE_NAME" value="${POST_GRADE_NAME}"/>
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
	<c:set value="450" var="add_height"/>
	<c:set value="/sys/postManagement/addPositionView" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/sys/postManagement/deletePositionInfo?POSITION_NO={sid}&NO={sid}" var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="500" var="edit_width"/>
	<c:set value="450" var="edit_height"/>
	<c:set value="/sys/postManagement/updatePositionView?POSITION_NO={sid}&NO={sid}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
			    <th width="16"><spring:message code="sys.postManage.title.position"/><!--职位--></th>
				<th width="16"><spring:message code="sys.postManage.title.postGrade"/><!--职级--></th>
				<th width="16"><spring:message code="sys.postManage.title.postType"/><!--职类--></th>
				<th width="16"><spring:message code="sys.postManage.title.ifUsed"/><!--是否使用--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${positionList}" var="item" varStatus="i">
				<tr target="sid" rel="${item.POSITION_NO}">
					<td>${item.POSITION_NAME}</td>
					<td>${item.POST_GRADE_NAME}</td>
					<td>${item.GATEGORY_NAME}</td>
					<td><c:if test="${item.ACTIVITY eq '1'}"><spring:message code="sys.affirm.title.yes"/><!--是--></c:if>
						<c:if test="${item.ACTIVITY eq '0'}"><spring:message code="sys.affirm.title.no"/><!--否--></c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/sys/postManagement/viewPositionList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>

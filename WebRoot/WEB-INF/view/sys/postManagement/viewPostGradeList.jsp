<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/sys/postManagement/viewPostGradeList" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
	              <td>
	               	<spring:message code="sys.postManage.title.postGrade"/><!--职级-->：
	               	<input type="text" name="seach_GRADE_NAME" value="${GRADE_NAME}"/>
	               	<spring:message code="sys.postManage.title.post"/><!--职级名称(职务)-->：
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
	<c:set value="600" var="add_width"/>
	<c:set value="500" var="add_height"/>
	<c:set value="/sys/postManagement/addPostGradeView" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/sys/postManagement/deletePostGrade?POST_GRADE_NO={sid}&NO={sid}" var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="600" var="edit_width"/>
	<c:set value="500" var="edit_height"/>
	<c:set value="/sys/postManagement/updatePostGradeView?POST_GRADE_NO={sid}&NO={sid}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>

	 
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
			    <th width="16"><spring:message code="sys.postManage.title.postGrade"/><!--职级--></th>
				<th width="16"><spring:message code="sys.postManage.title.post"/><!--职级名称(职务)--></th>
				<th width="16"><spring:message code="sys.postManage.title.postLevel"/><!--职等--></th>
				<th width="16"><spring:message code="sys.affirm.title.duty"/><!--职责--></th>
				<th width="16"><spring:message code="sys.basic.title.createDate"/><!--创建时间--></th>
				<th width="16"><spring:message code="sys.basic.title.createBy"/><!--创建者--></th>
				<th width="16"><spring:message code="sys.essParam.title.ifEnabled"/><!--是否启用--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${postGradeList}" var="item" varStatus="i">
				<tr target="sid" rel="${item.POST_GRADE_NO}">
					<td>${item.GRADENAME}</td>
					<td>${item.POSTNAME}</td>
					<td>${item.LEVELNAME}</td>
					<td>
					<c:forEach items="${item.dutyList}" var="duty" varStatus="i">
						  <c:if test="${i.count==1}">${duty.DUTY_NAME}</c:if>
						  <c:if test="${i.count>1}">,${duty.DUTY_NAME}</c:if>
					</c:forEach>
					</td>
					<td>${item.CREATEDATE}</td>
					<td>${item.CREATENAME}</td>
					<td><c:if test="${item.ACTIVITY eq '1'}"><spring:message code="sys.affirm.title.yes"/><!--是--></c:if>
						<c:if test="${item.ACTIVITY eq '0'}"><spring:message code="sys.affirm.title.no"/><!--否--></c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/sys/postManagement/viewPostGradeList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>

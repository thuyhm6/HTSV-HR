<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="/sys/postManagement/addPostGroupView" target="navTab"><span>
			<spring:message code="button.add"/><!--添加--></span></a></li>
			<li><a class="delete" href="/sys/postManagement/deletePostGroup?POST_GROUP_NO={sid}" target="ajaxTodo" 
			       title="<spring:message code='button.delete.sure'/>"><span><spring:message code="button.delete"/><!--删除--></span></a></li>
			<li><a class="edit" href="/sys/postManagement/updatePostGroupView?POST_GROUP_NO={sid}" target="navTab"><span>
			<spring:message code="button.update"/><!--修改--></span></a></li>
			<li class="line">line</li>
		</ul>
	</div>	
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="80"><spring:message code="sys.affirm.indexNum"/><!--序号--></th>
				<th width="80">ID</th>
				<th width="80"><spring:message code="sys.postManage.title.chineseName"/><!--中文名称--></th>
				<th width="80"><spring:message code="sys.postManage.title.englishName"/><!--英文名称--></th>
				<th width="80"><spring:message code="sys.basic.title.createDate"/><!--创建日期--></th>
				<th width="80"><spring:message code="sys.basic.title.createBy"/><!--创建者--></th>				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${postGroupList}" var="item" varStatus="i">			
				<tr target="sid" rel="${item.POST_GROUP_NO}">
				    <td>${item.POST_GROUP_NO}</td>
					<td>${item.POST_GROUP_ID}</td>
					<td>${item.POST_GROUP_NAME}</td>
					<td>${item.POST_GROUP_EN_NAME}</td>
					<td>${item.CREATE_DATE}</td>
					<td>${item.CREATED_BY_NAME}</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>	
</div>
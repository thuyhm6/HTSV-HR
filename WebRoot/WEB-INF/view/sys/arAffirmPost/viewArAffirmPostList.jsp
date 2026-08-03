<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<c:set value="dialog" var="add_tab"/>
	<c:set value="400" var="add_width"/>
	<c:set value="200" var="add_height"/>
	<c:set value="/sys/arAffirmPost/addArAffirmPostView" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/sys/arAffirmPost/deleteArAffirmPostInfo?DUTY={DUTY}" var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="400" var="edit_width"/>
	<c:set value="200" var="edit_height"/>
	<c:set value="/sys/arAffirmPost/updateArAffirmPostView?DUTY={DUTY}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<table class="table" width="100%" layoutH="183" nowrapTD="false">
		<thead>
			<tr>
				<th><spring:message code="sys.affirm.indexNum"/><!--序号--></th>   
				<th>职责</th> 
				<th>决裁等级</th>
				<th>操作人</th>
				<th>操作时间</th>
			</tr>
		</thead>
		<tbody id="tableCheck" >
			<c:forEach items="${affirmPostList}" var="item" varStatus="i">
				<tr target="DUTY" rel="${item.DUTY }">
					<td class='td_center' >${i.count }</td>
					<td class='td_center' >${item.DUTY_NAME }</td>
					<td class='td_center' >${item.AFFIRM_LEVEL }</td>
					<td class='td_center' >${item.LOCAL_NAME }</td>
					<td class='td_center' >${item.OP_DATE }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
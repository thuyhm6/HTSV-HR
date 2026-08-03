<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	
</script>

<div class="pageContent">
	<c:set value="dialog" var="add_tab"/>
	<c:set value="440" var="add_width"/>
	<c:set value="395" var="add_height"/>
	<c:set value="/sys/essParam/addVacationStandardManage" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/sys/essParam/deleteVacationStandardManage?STANDARD_MANAGE_ID={sid}" var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="440" var="edit_width"/>
	<c:set value="395" var="edit_height"/>
	<c:set value="/sys/essParam/updateVacationStandardManag?STANDARD_MANAGE_ID={sid}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<table class="table" width="100%" nowrapTD="false" layoutH="138">
		<thead>
			<tr>
			    <th width="20%">所在地
			    	
			    </th>
			    <th width="80%">说明
			    </th>
			</tr>
		</thead>
		<tbody>
			 <c:forEach items="${vacationStandardManageList}" var="itme" varStatus="i">
				<tr target="sid" rel="${itme.STANDARD_MANAGE_ID}">
					<td width="20%">${itme.WORK_AREA_NAME}</td>
					<td  width="80%">${itme.STANDARD_EXPLAIN}</td>
				</tr>
			</c:forEach> 
			<tr>
				
			</tr>
		</tbody>
	</table>
	<c:set value="/sys/essParam/viewOtConverParam" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>

<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<form name="updateForm" id="updateForm" method="post" action="/hrm/searchTransferOrder/cancelTransferNormaInBatch"
	  onsubmit="return cancelTransferNormaValidateCallback(this,navTabAjaxDone);">
<div class="pageContent">

 	<table class="table" width="120%" layoutH="50" nowrapTD="false">      
		<thead>
			<tr>
				<th><spring:message code="hrm.empinfo.empid" /></th>
				<th>姓名</th>
				<th>部门</th>
				<th>职(岗)位</th>
				<th>职级名称(职务)</th>
				<th>员工状态</th>							
			</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${hrTransferNormalList}" var="hrTransferNormal" varStatus="i">			
				<tr target="sid" rel="${hrTransferNormal.EXP_INSIDE_NO}">
					<td>${hrTransferNormal.EMPID}</td>
					<td>${hrTransferNormal.LOCAL_NAME}</td>
					<td>${hrTransferNormal.DEPT_NAME}</td>
					<td>${hrTransferNormal.POSITION_NAME}</td>
					<td>${hrTransferNormal.POST_NAME}</td>
					<td>${hrTransferNormal.STATUS_NAME}</td>
		       </tr>			
			</c:forEach>			
		</tbody>	
	</table>
</div>
</form>
	<c:set value="/hrm/searchTransferOrder/viewTransactionTransViewList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
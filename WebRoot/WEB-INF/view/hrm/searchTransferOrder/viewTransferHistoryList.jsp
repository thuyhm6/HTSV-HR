<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

      	
<form name="updateForm" id="updateForm" method="post" action="/hrm/searchTransferOrder/viewTransferHistoryList"
	  onsubmit="return ValidateCallback(this, navTabAjaxDone);">
<div class="pageContent">

 	<table class="table" width="120%" layoutH="50" nowrapTD="false">      
		<thead>
			<tr>
				<th width="80"><spring:message code="hrm.empinfo.empid" /><!--  社号--></th>
				<th width="80">姓名</th>
				<th width="100">部门</th>
				<th width="80">职(岗)位</th>
				<th width="120">职级名称(职务)</th>
			    <th width="40">职级</th>		
			    <th width="100">职责</th>		    
				<th width="80">工作地</th>
				<th width="160">调动类型</th>	
				<th width="140">生效日期</th>									
			</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${hrExperienceInsideList}" var="hrExpInside" varStatus="i">			
				<tr target="sid" rel="${hrExpInside.EXP_INSIDE_NO}">
					<td>${hrExpInside.EMPID}</td>
					<td>${hrExpInside.LOCAL_NAME}</td>
					<td>${hrExpInside.NEW_DEPT_NAME}</td>
					<td>${hrExpInside.NEW_POSITION_NAME}</td>
					<td>${hrExpInside.NEW_POST_NAME}</td>
					<td>${hrExpInside.NEW_POST_GRADE_NAME}</td>
					<td>${hrExpInside.NEW_DUTY_NAME}</td>
					<td>${hrExpInside.NEW_WORK_AR_NAME}</td>
					<td>${hrExpInside.TRANS_TYPE_NAME}</td>
					<td>${hrExpInside.START_DATE}</td>
		       </tr>			
			</c:forEach>			
		</tbody>	
	</table>
</div>
</form>
	<c:set value="/hrm/searchTransferOrder/viewTransferHistoryList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
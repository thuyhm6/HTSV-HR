<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form id="viewArDetailDataExcelImportResult" name="viewArDetailDataExcelImportResult"
			action="/ess/infoApplyAttendance/viewImportAttendanceTempList?" 
			onsubmit="return navTabSearch(this);"
			method="post" >
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="inct.salesman.excel.totalCnt"/><!-- 总行数-->
				</td>
				<td>
					${totalCnt}
					<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>
				</td>
				<td>
					<spring:message code="inct.salesman.excel.errCnt"/><!-- 出错行数-->
				</td>
				<td>							
					${errCnt}
				</td>
				<td>
					<spring:message code="pa.salary.canShu.chuCuoYuFou"/><!-- 出错与否-->
				</td>
				<td>							
					<select name="seach_RESULT_FLAG" id="seach_RESULT_FLAG">
							<option value="" <c:if test="${RESULT_FLAG eq '' }">selected</c:if>><!--全部--><spring:message code="org.title.ALL"/></option>
							<option value="E" <c:if test="${RESULT_FLAG eq 'E' }">selected</c:if>><!--是--><spring:message code="org.title.YES"/></option>
							<option value="N" <c:if test="${RESULT_FLAG eq 'N' }">selected</c:if>><!--否--><spring:message code="ar.viewcycle.content.no"/></option>
					</select>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="public.title.search"/><!-- 检索 -->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="formBar">
		<ul>			
			<li>
				<a class="buttonActive" onclick="excelimport_ess3401();">
					<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
				</a></li>
			<li>
				<a class="buttonActive" target="ajaxTodo" callback="navTabAjaxDoneRefreshCurrentPage" 
					href="/ess/infoApplyAttendance/submitImportExcelAttendanceData" title="<spring:message code="org.title.IS_SUBMIT"/>"><span>
				<spring:message code="public.title.submit"/><!--提交--></span></a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="220" nowrapTD="false">
		<thead>
			<tr>
				<th>Line<!--excel行号--></th>
				<th><!--姓名--><spring:message code="ess.infoApply.NAME"/></th>
				<th><!--社号--><spring:message code="ess.infoApply.EMPID"/></th>
				<th><!--部门--><spring:message code="ess.infoApply.DEPT"/></th>
				<th><!--考勤状态--><spring:message code="ess.infoApply.attendState"/></th>
				<th><!--开始日期--><spring:message code="public.title.startDate"/></th>
				<th><!--开始时间--><spring:message code="ess.infoApply.title.startTime"/></th>
				<th><!--结束日期--><spring:message code="public.title.endDate"/></th>
				<th><!--结束时间--><spring:message code="ess.infoApply.title.endTime"/></th>
				<th><!--时长--><spring:message code="ess.infoApply.duration"/></th>
				<th><!--原因--><spring:message code="ess.infoApply.Reason"/></th>
				<th><!--审批状态--><spring:message code="ess.infoApply.approval_status"/></th>
				<th><!--验证结果--><spring:message code="inct.salesman.validateMessage"/></th>
				<th><!--上传人--><spring:message code="org.title.UPLOAD_BY"/></th>
				<th><!--上传时间--><spring:message code="org.title.UPLOAD_DATE"/></th>			
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${attendanceTempList}" var="item" varStatus="i">			
				<tr>
					<td class='td_center'>${item.LINE_ID}</td>
					<td class='td_center'>${item.APPLY_NAME}</td>
					<td class='td_center'>${item.EMPID}</td>
					<td class='td_center'>${item.DEPTNAME}</td>
					<td class='td_center'>${item.LEAVE_TYPE_NAME}</td>
					<td class='td_center'>${item.LEAVE_FROM_DATE}</td>
					<td class='td_center'>${item.LEAVE_FROM_TIME}</td>
					<td class='td_center'>${item.LEAVE_TO_DATE}</td>
					<td class='td_center'>${item.LEAVE_TO_TIME}</td>
					<td class='td_center'>${item.APPLY_LENGTH}</td>
					<td class='td_center'>${item.LEAVE_REASON}</td>
					<td class='td_center'>${item.AFFIRM_FLAG_NAME}</td>
					<td style="text-align:left">${item.UPLOAD_ERROR_MSG}</td>
					<td class='td_center'>${item.UPLOAD_BY}</td>
					<td class='td_center'>${item.UPLOAD_DATE}</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
</div>
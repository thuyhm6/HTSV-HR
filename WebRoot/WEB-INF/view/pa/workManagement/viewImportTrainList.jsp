<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form id="viewPaEmpAccountDataExcelImportResult" name="viewPaEmpAccountDataExcelImportResult"
			action="/pa/workManagement/viewImportTraintList?" 
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
					出错与否
				</td>
				<td>							
					<select name="seach_RESULT_FLAG" id="seach_RESULT_FLAG">
							<option value="" <c:if test="${RESULT_FLAG eq '' }">selected</c:if>>全部</option>
							<option value="E" <c:if test="${RESULT_FLAG eq 'E' }">selected</c:if>>是</option>
							<option value="N" <c:if test="${RESULT_FLAG eq 'N' }">selected</c:if>>否</option>
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
				<a class="buttonActive" onclick="excelimport_pa0818();">
					<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
				</a></li>
			<li>
				<a class="buttonActive" target="ajaxTodo" callback="navTabAjaxDoneRefreshCurrentPage" 
					href="/pa/workManagement/submitImportExcelPaEmpAccountData" title="确定要提交吗?"><span>
				<spring:message code="public.title.submit"/><!--提交--></span></a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="220" nowrapTD="false">
		<thead>
			<tr>
			 <th>Lineexcel行号</th> 
				<th><spring:message code="hrm.empinfo.TRAIN_ADDRESS" /><!--培训地点--></th>
				<th><spring:message code="hrm.empinfo.training_distinction" /><!--培训区分--></th>
				<th><spring:message code="hrm.empinfo.TRAIN_curriculum" /><!--培训课程--></th>
				<th><spring:message code="hrm.empinfo.Training_form" /><!--培训形式--></th>
				<th><spring:message code="hrm.empinfo.START_DATE" /><!--培训开始日期--></th>
				<th><spring:message code="hrm.empinfo.END_DATE" /><!--培训结束日期--></th>
				<th><spring:message code="hrm.empinfo.TRAINING_RESULT" /><!--培训结果--></th>
				<th>评价结果</th> 
				<th >学分</th>
				<th><spring:message code="hrm.empinfo.REMARK" /><!--备注--></th>
				<th><spring:message code="inct.salesman.validateMessage"/><!--验证结果--></th>
				<th>上传人</th>
				<th>上传时间</th>			
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paEmpAccountTempList}" var="item" varStatus="i">			
				<tr>
				 	<td class='td_center'>${item.LINE_ID}</td>
			
					<td class='td_center'>${item.PERSON_ID}</td>
					<td class='td_center'>${item.PLACE}</td>
					<td class='td_center'>${item.TRAINING_DIFFERENTIATE }</td>
					<td class='td_center'>${item.COURSE_NAME }</td>
					<td class='td_center'>${item.TRAINING_METHOD }</td>
					<td class='td_center'>${item.START_DATE }</td>
					<td class='td_center'>${item.END_DATE }</td>
					<td class='td_center'>${item.TRAINING_RESULT }</td>
					<td class='td_center'>${item.MARK}</td>
					<td class='td_center'>${item.REMARKS}</td>
					<td class='td_center'>${item.UPLOAD_ERROR_MSG}</td>
					<td class='td_center'>${item.UPLOAD_BY}</td>
					<td class='td_center'>${item.UPLOAD_DATE}</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
</div>
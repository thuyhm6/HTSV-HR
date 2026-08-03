<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form id="viewImportShopShiftTempListResult" name="viewImportShopShiftTempListResult"
			action="/ess/tempEmp/viewImportShopShiftTempList" 
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
					<input type="hidden" name="navTabId" value="${navTabId }">
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
				<a class="buttonActive" onclick="excelimport_ess3422();">
					<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
				</a></li>
			<li>
				<a class="buttonActive" target="ajaxTodo" callback="navTabAjaxDoneRefreshCurrentPage" 
					href="/ess/tempEmp/submitImportExcelShopShiftData" title="确定要提交吗?"><span>
				<spring:message code="public.title.submit"/><!--提交--></span></a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="220" nowrapTD="false">
		<thead>
			<tr>
				<th>Line<!--excel行号--></th>
				<th>社号</th>
				<th>姓名</th>
				<th>考勤日期</th>
				<th>上班时间</th>
				<th>下班时间</th>
				<c:if test="${navTabId ne 'ess3422'}">
				<th>工时</th>
				</c:if>
				<th>差异</th>
				<th>考勤</th>
				<th><spring:message code="inct.salesman.validateMessage"/><!--验证结果--></th>
				<th>上传人</th>
				<th>上传时间</th>			
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${shopShiftTempList}" var="item" varStatus="i">			
				<tr>
					<td class='td_center'>${item.LINE_ID}</td>
					<td class='td_center'>${item.EMPID}</td>
					<td class='td_center'>${item.LOCAL_NAME}</td>
					<td class='td_center'>${item.AR_DATE_STR}</td>
					<td class='td_center'>${item.SHIFT_FROM_TIME}</td>
					<td class='td_center'>${item.SHIFT_TO_TIME}</td>
					<c:if test="${navTabId ne 'ess3422'}">
					<td class='td_center'>${item.WORKING_HOUR}</td>
					</c:if>
					<td class='td_center'>${item.MINUS_LENGTH}</td>
					<td class='td_center'>${item.ITEM_NAME}</td>
					<td style="text-align:left">${item.UPLOAD_ERROR_MSG}</td>
					<td class='td_center'>${item.UPLOAD_BY}</td>
					<td class='td_center'>${item.UPLOAD_DATE}</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
</div>
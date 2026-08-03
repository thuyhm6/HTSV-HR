<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form id="viewMappingDataExcelImportResult" name="viewMappingDataExcelImportResult"
			action="/sys/empMapping/viewImportExcelMappingDataList" 
			onsubmit="return navTabSearch(this);"
			method="post" 
			rel="pagerForm" >
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
			<li><a class="buttonActive" onclick="excelimport_viewapplyMappingbatch();">
			<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
				</a></li>
			<li><a class="buttonActive" target="ajaxTodo" callback="navTabAjaxDoneRefreshCurrentPage" 
			href="/sys/empMapping/submitImportExcelMappingEmpData"  title="确定要提交吗?"><span>
				<spring:message code="public.title.submit"/><!--提交--></span></a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="220" nowrapTD="false">
		<thead>
			<tr>
				<th>Line<!--excel行号--></th>
				<th>新社号</th>
				<th>姓名</th>
				<th>旧社号</th>
				<th><spring:message code="inct.salesman.validateMessage"/><!--验证结果--></th>
				<th>上传人</th>
				<th>上传时间</th>			
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paMappingTempList}" var="item" varStatus="i">			
				<tr>
					<td class='td_center'>${item.LINE_ID}</td>
					<td style="text-align:left">${item.NEW_EMPID}</td>
					<td style="text-align:left">${item.LOCAL_NAME}</td>
					<td style="text-align:left">${item.OLD_EMPID}</td>
					<td style="text-align:left">${item.UPLOAD_ERROR_MSG}</td>
					<td style="text-align:left">${item.UPLOAD_BY}</td>
					<td class='td_center'>${item.UPLOAD_DATE}</td>					
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
	<c:set value="/sys/empMapping/viewImportExcelMappingDataList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
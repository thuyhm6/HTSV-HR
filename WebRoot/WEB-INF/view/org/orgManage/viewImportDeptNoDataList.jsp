<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form id="viewDeptOrderExcelImportResult" name="viewDeptOrderExcelImportResult"
			action="/org/orgManage/viewImportDeptNoDataList" 
			onsubmit="return navTabSearch(this);"
			method="post" 
			rel="pagerForm" >
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<th>
					<spring:message code="inct.salesman.excel.totalCnt"/><!-- 总行数-->：
				</th>
				<td>
					${totalCnt}
					<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>
				</td>
				<th>
					<spring:message code="inct.salesman.excel.errCnt"/><!-- 出错行数-->：
				</th>
				<td>							
					${errCnt}
				</td>
				<th>
					<spring:message code="org.title.IS_ERROR"/><!-- 出错与否： -->
				</th>
				<td>							
					<select name="seach_RESULT_FLAG" id="seach_RESULT_FLAG">
							<option value="" <c:if test="${RESULT_FLAG eq '' }">selected</c:if>><spring:message code="org.title.ALL"/><!-- 全部 --></option>
							<option value="E" <c:if test="${RESULT_FLAG eq 'E' }">selected</c:if>><spring:message code="org.title.YES"/><!-- 是 --></option>
							<option value="N" <c:if test="${RESULT_FLAG eq 'N' }">selected</c:if>><spring:message code="ar.viewcycle.content.no"/><!-- 否 --></option>
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
			<li><a class="buttonActive" onclick="importDeptOrderData()"> <span><spring:message
			code="ar.addempshift.title.excelimport" /><!-- EXCEL导入 --></span> </a></li>
			<li><a class="buttonActive"
			href="/org/orgManage/downloadDeptOrderlateByExcelData"><span>
				<spring:message code="inct.salesman.downloadToExcel" /><!--excel导出--></span></a>
			</li>
			<li><a class="buttonActive" target="ajaxTodo" callback="navTabAjaxDoneRefreshCurrentPage" 
			href="/org/orgManage/submitImportExcelDeptOrderData"  title='<spring:message code="org.title.IS_SUBMIT"/>'><span>
				<spring:message code="public.title.submit"/><!--提交--></span></a>
			</li>
		</ul>
	</div>
		
	<table class="table" width="100%" layoutH="220" nowrapTD="false">
		<thead>
			<tr>
				<th>Line<!--excel行号--></th>
				<th><spring:message code="org.title.COMPANYID"/><!-- 公司ID --></th>
				<th><spring:message code="org.title.COMPANYNAME"/><!-- 公司名称 --></th>
				<th><spring:message code="org.title.deptNO" /><!-- 部门NO --></th>
				<th><spring:message code="org.title.DEPTNAME" /><!-- 部门名称 --></th>
				<th>ORDERNO</th>
				<th><spring:message code="inct.salesman.validateMessage"/><!--验证结果--></th>
				<th><spring:message code="org.title.UPLOAD_BY"/><!-- 上传人 --></th>
				<th><spring:message code="org.title.UPLOAD_DATE"/><!-- 上传时间 --></th>			
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${deptOrderTempList}" var="item" varStatus="i">			
				<tr>
				    <td class='td_center'>${item.LINE_ID}</td>
					<td class='td_center'>${item.CPNY_ID}</td>
					<td class='td_center'>${item.CPNY_LOCATION}</td>
					<td class='td_center'>${item.DEPTNO}</td>
					<td class='td_center'>${item.ORG_NAME_LOCAL}</td>
					<td class='td_center'>${item.ORDERNO}</td>
					<td style="text-align:left">${item.UPLOAD_ERROR_MSG}</td>
					<td class='td_center'>${item.UPLOAD_BY}</td>
					<td class='td_center'>${item.UPLOAD_DATE}</td>					
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
		
	<c:set value="/org/orgManage/viewImportDeptNoDataList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
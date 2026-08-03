<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form name="viewEvalDataExcelImportResult"
			action="/pa/tempsale/viewImportExcelTempSalesEmpDataList" 
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
					出错与否：
				</th>
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
			<li><a class="buttonActive" onclick="importExcelTempSalesEmpData()"> <span><spring:message
			code="ar.addempshift.title.excelimport" /><!-- EXCEL导入 --></span> </a></li>
			<li><a class="buttonActive"
			href="/pa/tempsale/downloadExcelTemplateByExcelData?type=2"><span>
				<spring:message code="inct.salesman.downloadToExcel"/><!--excel导出--></span></a>
			</li>
			<li><a class="buttonActive" target="ajaxTodo"  callback="navTabAjaxDoneRefreshCurrentPage" 
			href="/pa/tempsale/submitImportExcelTempSalesEmpData" title="确定要提交吗?"><span>
				<spring:message code="public.title.submit"/><!--提交--></span></a>
			</li>
		</ul>
	</div>	
		
	<table class="table" width="100%" layoutH="220" nowrapTD="false">
		<thead>
			<tr>
				<th>Line<!--excel行号--></th>
				<th>EventID</th>
				<th>姓名</th>
				<th>门店CODE</th>
				<th>门店名称</th>
				<th>身份证号</th>
				<th>银行帐号</th>
				<th>开户行</th>
				<th>联系方式</th>
				<th>评价等级</th>
				<th>产品类型</th>
				<th>工作天数</th>
				<th>应发工资</th>
				<th><spring:message code="inct.salesman.validateMessage"/><!--验证结果--></th>
				<th>上传人</th>
				<th>上传时间</th>			
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paTempSalesEmpTempList}" var="item" varStatus="i">			
				<tr>
					<td class='td_center'>${item.LINE_ID}</td>
					<td style="text-align:left">${item.EVENT_ID}</td>
					<td style="text-align:left">${item.EMP_NAME}</td>
					<td style="text-align:left">${item.EVENT_STORE_CODE}</td>
					<td style="text-align:left">${item.EVENT_STORE_NAME}</td>
					<td style="text-align:left">${item.IDCARD_NO}</td>
					<td style="text-align:left">${item.BANK_NO}</td>
					<td style="text-align:left">${item.BANK_NAME}</td>
					<td style="text-align:left">${item.CELLPHONE}</td>
					<td style="text-align:left">${item.EVS_GRADE}</td>
					<td style="text-align:left">${item.PROD_TP}</td>
					<td style="text-align:right">${item.WORK_DAYS}</td>
					<td style="text-align:right">${item.EVENT_SALARY}</td>
					<td style="text-align:left">${item.UPLOAD_ERROR_MSG}</td>
					<td style="text-align:left">${item.UPLOAD_BY}</td>
					<td class='td_center'>${item.UPLOAD_DATE}</td>					
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
		
	<c:set value="/pa/tempsale/viewImportExcelTempSalesEmpDataList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
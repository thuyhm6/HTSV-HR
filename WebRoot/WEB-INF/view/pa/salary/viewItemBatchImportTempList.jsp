<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
//导出
function exportSalesmanEvalImportInfo(a) {
	var $from = $("#viewEvalDataExcelImportResult");
	alertMsg.confirm("确定导出么?", {
		okCall : function() {
			doSalesmanEvalImportInfoExport($from);
		}
	});
}
function doSalesmanEvalImportInfoExport(from) {
	var $from = $("#viewEvalDataExcelImportResult");
	var url = "/hrm/empinfo/viewExportEmpInfoExcelTempList";
	window.location = url + (url.indexOf('?') == -1 ? "?" : "&")
			+ $from.serialize();
}
function delExceplImportLine(LINE_ID){
	var params = [];
	params.push({
		name: 'LINE_ID',
		value: LINE_ID
	});
	params.push({
		name: 'ITEM_DISTINGUISH',
		value: document.getElementById('ITEM_DISTINGUISH').value
	});
	if (confirm ("确定要删除吗?")){	  
		$.ajax({
		  url: '/pa/salary/delExceplImportLine',
		  data: params,
		  cache: false,
		  success: function(responseText){
			if (responseText == "Y"){
				//alert("删除成功！");
				//页面重载
				navTabSearch(document.viewItemBatchImportTempList);
			}else{
				alert("删除失败！");
			}
		  }
		});
	}
}
</script>
<div class="pageHeader">
	<form id="viewItemBatchImportTempList" name="viewItemBatchImportTempList"
			action="/pa/salary/viewItemBatchImportTempList" 
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
				<input type='hidden' value="${ITEM_DISTINGUISH}" name="ITEM_DISTINGUISH" id="ITEM_DISTINGUISH"/> 
					${totalCnt}
					<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>
					<input type='hidden' value='${ITEM_DISTINGUISH}' id='ITEM_DISTINGUISH' name='ITEM_DISTINGUISH'/>
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
			<%-- <li><a class="buttonActive" onclick="importExcelTempSalesAccrualData()"> <span><spring:message
			code="ar.addempshift.title.excelimport" /><!-- EXCEL导入 --></span> </a></li> 
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="button" onclick="exportSalesmanEvalImportInfo(this)">
							<!--excel导出--><spring:message code="inct.salesman.downloadToExcel" />
						</button>
					</div>
				</div>
			</li>--%>
			<li><a class="buttonActive" target="ajaxTodo" callback="navTabAjaxDoneRefreshCurrentPage" 
			href="/pa/salary/submitItemBatchData?ITEM_DISTINGUISH=${ITEM_DISTINGUISH}"  title="确定要提交吗?"><span>
				<spring:message code="public.title.submit"/><!--提交--></span></a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="220">
		<thead>
			<tr>
				<th>序号</th>
				<th>社号(必填)</th>
				<th>员工姓名(可为空)</th>
				<th>开始月</th>
				<th>结束月</th>
				<th>金额</th>
				<th>备注</th>
				<th>错误原因</th>
				<th width="40" style="text-align: center"><!--是否删除-->
					删除
				</th>
			</tr>
		</thead>
		<tbody>
		
			<c:forEach items="${MDATA}" var="info" varStatus="i">
				<tr>
					<td>${i.count}</td>
					<td>${info.EMPID}</td>
					<td>${info.LOCAL_NAME}</td>
					
					<td>						
						${info.START_DATE}
					</td>
						
					
					<td>${info.END_MONTH}</td>
					<td>${info.RETURN_VALUE}</td>
					<td>${info.REMARK}</td>
					<td>${info.UPLOAD_ERROR_MSG}</td>
					<td style="text-align: center">
						<img src="/resources/images/button/Delete_little.gif" onclick="delExceplImportLine('${info.LINE_ID}')"
					 		style="cursor: hand" />
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/salary/viewItemBatchImportTempList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
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
	var url = "/ess/wageApplication/viewImportWageApplicationExcelTempListExcel";
	window.location = url + (url.indexOf('?') == -1 ? "?" : "&")
			+ $from.serialize();
}
//提交导入数据
function doSalesmanEvalDataImport() {
	var params = $("#viewEvalDataExcelImportResult").serialize();
	alertMsg.confirm("确认导入费用申请数据?", {
		okCall : function() {
			$.ajax( {
				type : 'post',
				cache : false,
				url : "/ess/wageApplication/createImportApplicationExcelTempListExcel?"
						+ params,
				success : function(result) {
					if (result == 1) {
						alertMsg.info("费用申请导入成功！");
						navTabSearch($("#addNewWageApplication"));
					} else {
						alertMsg.info("费用申请导入失败！");
					}
					//页面重载
				navTabSearch($("#viewEvalDataExcelImportResult"));
			}
		  });
		}
	});
}
function importEssExcel(){
	$("#importAgain").click();
	$("#addNewWageApplication").submit();
}
</script>
<div class="pageHeader">
<a href="/pa/excelImport/importExcelData?importFunName=/importEssApplication" target="dialog" mask="true" width="500" height="200" id="importAgain"></a>
<form id="addNewWageApplication" action="/ess/wageApplication/addNewWageApplicationView"
		onsubmit="return navTabSearch(this);" method="post" ></form>
	<form id="viewEvalDataExcelImportResult" name="viewEvalDataExcelImportResult"
		action="/ess/wageApplication/viewWageApplicationTempList"
		onsubmit="return navTabSearch(this);" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<th>
						<!-- 总行数--><spring:message code="inct.salesman.excel.totalCnt" />：
					</th>
					<td>
						${totalCnt}
						<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display: none'></iframe>
					</td>
					<th>
						<!-- 出错行数--><spring:message code="inct.salesman.excel.errCnt" />：
					</th>
					<td>
						${errCnt}
					</td>
					<th>
						出错与否：
					</th>
					<td>
						<select name="seach_RESULT_FLAG" id="seach_RESULT_FLAG">
							<option value=""
								<c:if test="${RESULT_FLAG eq '' }">selected</c:if>>全部
							</option>
							<option value="E"
								<c:if test="${RESULT_FLAG eq 'E' }">selected</c:if>>是
							</option>
							<option value="N"
								<c:if test="${RESULT_FLAG eq 'N' }">selected</c:if>>否
							</option>
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
									<spring:message code="public.title.search" />
									<!-- 检索 -->
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
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="button" onclick="importEssExcel(this)">
							<spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 -->
						</button>
					</div>
				</div>
			</li>
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="button" onclick="exportSalesmanEvalImportInfo(this)">
							<spring:message code="inct.salesman.downloadToExcel" />
							<!--excel导出-->
						</button>
					</div>
				</div>
			</li>
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="submit" onclick="doSalesmanEvalDataImport()">
							<spring:message code="public.title.submit" />
							<!--提交-->
						</button>
					</div>
				</div>
			</li>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" id="btnClose" name="btnClose" class="close">
							<spring:message code="public.title.cancle" />
							<!--取消-->
						</button>
					</div>
				</div>
			</li>
		</ul>
	</div>

	<table class="table" width="100%" layoutH="150">
		<thead>
			<tr>
				<th width="5%">序号</th>
				<th width="10%">申请者/工号</th>
				<th width="10%">费用类型</th>
				<th width="20%">申请费用发放期间</th>
				<th width="10%">金额</th>
				<th width="20%">备注</th>
				<th width="20%">错误原因</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${MDATA}" var="item" varStatus="i">
				<tr>
					<td>${i.count}</td>
					<td>${item.LOCAL_NAME}[${item.COSTEMP}]</td>
					<td>${item.TYPENAME}</td>
					<td>${item.START_DATE}~${item.END_DATE}</td>
					<td>${item.MONEY}</td>
					<td>${item.DEMO}</td>
					<td>${item.UPLOAD_ERROR_MSG}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/ess/wageApplication/viewWageApplicationTempList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
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
	var url = "/pa/wagebase/viewImportPaAllowanceExcelSelfListExcel";
	window.location = url + (url.indexOf('?') == -1 ? "?" : "&")
			+ $from.serialize();
}
//提交导入数据
function doSalesmanEvalDataImport() {
	var params = $("#viewEvalDataExcelImportResult").serialize();
	alertMsg.confirm("确认导入职责津贴个人数据?", {
		okCall : function() {
			$.ajax( {
				type : 'post',
				cache : false,
				url : "/pa/wagebase/createImportPaAllowanceExcelSelfListExcel?"
						+ params,
				success : function(result) {
					if (result == 1) {
						alertMsg.info("职责津贴个人导入成功！");
						navTab.closeCurrentTab();
					} else {
						alertMsg.info("职责津贴个人导入失败！");
					}
					//页面重载
				navTabSearch($("#viewEvalDataExcelImportResult"));
			}
			});
		}
	});
}
</script>
<div class="pageHeader">
	<form id="viewEvalDataExcelImportResult" name="viewEvalDataExcelImportResult"
		action="/pa/wagebase/viewImportPaAllowanceExcelSelfList"
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
				<a class="buttonActive"
					href="/pa/excelImport/importExcelData?importFunName=/importPaAllowanceSelf" target="dialog" mask="true" width="500" height="200" >
					<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
				</a>
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
				<th width="10%"><%--序号--%><spring:message code="ar.viewcycle.title.xuhao"/></th>
				<th width="10%"><%--社号--%><spring:message code="hr.viewPersonalInfo.title.EMPID"/></th>
				<th width="10%"><%--姓名--%><spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/></th>
				<th width="10%"><%--支付比例(%)--%><spring:message code="pa.allowance.zhifubili"/>(%)</th>
				<th width="10%"><%--有效期月数--%><spring:message code="pa.allowance.youxiaoqiyueshu"/></th>
				<th width="10%"><%--备注--%><spring:message code="ar.viewarcardrecord.title.beizhu"/></th>
				<th width="15%">错误原因</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${MDATA}" var="item" varStatus="i">
				<tr>
					<td>${i.count}</td>
					<td>${item.EMPID}</td>
					<td>${item.CHINESENAME}</td>
					<td>${item.PERCENT_ALLOWANCE}</td>
					<td>${item.VALID_MONTH}</td>
					<td>${item.DEMO_ALLOWANCE }</td>
					<td>${item.UPLOAD_ERROR_MSG}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/wagebase/viewImportPaAllowanceExcelSelfList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
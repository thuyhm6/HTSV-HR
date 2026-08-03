<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
//导出
function exportPaAccountImportInfo(a) {
	var $from = $("#viewPaAccountExcelImportResult");
	alertMsg.confirm("确定导出么?", {
		okCall : function() {
			doPaAccountImportInfoExport($from);
		}
	});
}
function doPaAccountImportInfoExport(from) {
	var $from = $("#viewPaAccountExcelImportResult");
	var url = "/pa/wagebase/viewImportPaAccountExcelListExcel";
	window.location = url + (url.indexOf('?') == -1 ? "?" : "&")
			+ $from.serialize();
}
//提交导入数据
function doPaAccountDataImport() {
	var params = $("#viewPaAccountExcelImportResult").serialize();
	alertMsg.confirm("确认导入工资计算对象数据?", {
		okCall : function() {
			$.ajax( {
				type : 'post',
				cache : false,
				//url : "/pa/wagebase/createImportPaAllowanceExcelSelfListExcel?"
				url : "/pa/wagebase/createImportPaAccountExcelListExcel?"
						+ params,
				success : function(result) {
					if (result == 1) {
						alertMsg.info("工资计算对象导入成功！");
						navTab.closeCurrentTab();
					} else {
						alertMsg.info("工资计算对象导入失败！");
					}
					//页面重载
				navTabSearch($("#viewPaAccountExcelImportResult"));
			}
			});
		}
	});
}
</script>
<div class="pageHeader">
	<form id="viewPaAccountExcelImportResult" name="viewPaAccountExcelImportResult"
		action="/pa/wagebase/viewImportPaAccountExcelList"
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
					href="/pa/excelImport/importExcelData?importFunName=/importPaAccount" target="dialog" mask="true" width="500" height="200" >
					<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
				</a>
			</li>
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="button" onclick="exportPaAccountImportInfo(this)">
							<spring:message code="inct.salesman.downloadToExcel" />
							<!--excel导出-->
						</button>
					</div>
				</div>
			</li>
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="submit" onclick="doPaAccountDataImport()">
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
				<th width="5%"><%--序号--%><spring:message code="ar.viewcycle.title.xuhao"/></th>
				<th width="8%"><%--社号--%><spring:message code="hr.viewPersonalInfo.title.EMPID"/></th>
				<th width="6%"><%--姓名--%><spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/></th>
				<th width="5%">计算标识</th>
				<th width="6%">修改原因</th>
				<th width="15%">错误原因</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${MDATA}" var="item" varStatus="i">
				<tr>
					<td style="text-align:center">${i.count}</td>
					<td style="text-align:center">${item.EMPID}</td>
					<td style="text-align:center">${item.CHINESENAME}</td>
					<td style="text-align:center">${item.CALC_FLAG}</td>
					<td style="text-align:center">${item.UPDATE_REMARK}</td>
					<td style="text-align:center">${item.UPLOAD_ERROR_MSG}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/wagebase/viewImportPaAccountExcelList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
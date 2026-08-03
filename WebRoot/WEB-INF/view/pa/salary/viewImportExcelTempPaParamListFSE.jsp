<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
//导出
function exportPaParamImportInfo(a) {
	var $from = $("#viewPaParamExcelImportResultFSE");
	alertMsg.confirm("确定导出么?", {
		okCall : function() {
			doPaParamImportInfoExport($from);
		}
	});
}
function doPaParamImportInfoExport(from) {
	var $from = $("#viewPaParamExcelImportResultFSE");
	var url = "/pa/salary/viewImportPaParamListExcel";
	window.location = url + (url.indexOf('?') == -1 ? "?" : "&")
			+ $from.serialize();
}
//提交导入数据
function doPaParamDataImport() {
	var params = $("#viewPaParamExcelImportResultFSE").serialize();
	alertMsg.confirm("确认导入项目数据吗?", {
		okCall : function() {
			$.ajax( {
				type : 'post',
				cache : false,
				//url : "/pa/wagebase/createImportPaAllowanceExcelSelfListExcel?"
				url : "/pa/salary/createImportPaParamListExcel?"
						+ params,
				success : function(result) {
					if (result == 1) {
						alertMsg.info("输入项目数据导入成功！");
						navTab.closeCurrentTab();
					} else {
						alertMsg.info("输入项目数据导入失败！");
					}
					//页面重载
				navTabSearch($("#viewPaParamExcelImportResultFSE"));
			}
			});
		}
	});
}
</script>
<div class="pageHeader">
	<form id="viewPaParamExcelImportResultFSE" name="viewPaParamExcelImportResultFSE"
		action="/pa/salary/viewImportExcelTempPaParamListFSE"
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
						<button type="button" onclick="exportPaParamImportInfo(this)">
							<spring:message code="inct.salesman.downloadToExcel" />
							<!--excel导出-->
						</button>
					</div>
				</div>
			</li>
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="submit" onclick="doPaParamDataImport()">
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
				<th width="10%">开始月</th>
				<th width="10%">结束月</th>
				<th width="8%">数值</th>
				<th width="6%">备注</th>
				<th width="20%">错误原因</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${MDATA}" var="item" varStatus="i">
				<tr>
					<td>${i.count}</td>
					<td>${item.EMPID}</td>
					<td>${item.CHINESENAME}</td>
					<td>${item.START_MONTH}</td>
					<td>${item.END_MONTH}</td>
					<td>${item.RETURN_VALUE}</td>
					<td>${item.REMARK}</td>
					<td>${item.UPLOAD_ERROR_MSG}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/salary/viewImportExcelTempPaParamListFSE" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
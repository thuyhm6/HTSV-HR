<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
//导出
function exportPaParamImportInfo(a) {
	var $from = $("#viewPaParamExcelImportResult");
	alertMsg.confirm("<spring:message code='alert.message.confirm_export_out.b' />", {//确定导出么?
		okCall : function() {
			doPaParamImportInfoExport($from);
		}
	});
}
function doPaParamImportInfoExport(from) {
	var $from = $("#viewPaParamExcelImportResult");
	var url = "/pa/salary/viewImportPaParamListExcel";
	window.location = url + (url.indexOf('?') == -1 ? "?" : "&")
			+ $from.serialize();
}
//提交导入数据
function doPaParamDataImport() {
	var params = $("#viewPaParamExcelImportResult").serialize();
	alertMsg.confirm("<spring:message code='alert.message.confirm_import_item_data.b' />", {//确认导入项目数据吗?
		okCall : function() {
			$.ajax( {
				type : 'post',
				cache : false,
				//url : "/pa/wagebase/createImportPaAllowanceExcelSelfListExcel?"
				url : "/pa/salary/createImportPaParamListExcel?"
						+ params,
				success : function(result) {
					if (result == 1) {
						alertMsg.info("<spring:message code='alert.message.input_item_import_success.b' />");//输入项目数据导入成功！
						navTab.closeCurrentTab();
					} else {
						alertMsg.info("<spring:message code='alert.message.input_item_import_fail.b' />");//输入项目数据导入失败！
					}
					//页面重载
				navTabSearch($("#viewPaParamExcelImportResult"));
			}
			});
		}
	});
}
</script>
<div class="pageHeader">
	<form id="viewPaParamExcelImportResult" name="viewPaParamExcelImportResult"
		action="/pa/salary/viewImportExcelTempPaParamList"
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
						<!--出错与否：--><spring:message code="pa.salary.canShu.chuCuoYuFou" />
					</th>
					<td>
						<select name="seach_RESULT_FLAG" id="seach_RESULT_FLAG">
							<option value=""
								<c:if test="${RESULT_FLAG eq '' }">selected</c:if>><!--全部--><spring:message code="ar.viewarcardrecord.title.quanbu" />
							</option>
							<option value="E"
								<c:if test="${RESULT_FLAG eq 'E' }">selected</c:if>><!--是--><spring:message code="ar.viewcycle.content.yes" />
							</option>
							<option value="N"
								<c:if test="${RESULT_FLAG eq 'N' }">selected</c:if>><!--否--><spring:message code="ar.viewcycle.content.no" />
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
				<th width="10%"><!--开始月--><spring:message code="pa.insurance.title.startMonth"/></th>
				<th width="10%"><!--结束月--><spring:message code="pa.insurance.title.endMonth"/></th>
				<th width="10%"><!--数值--><spring:message code="pa.salary.canShu.shuZhi"/></th>
				<th width="5%"><!--备注--><spring:message code="pa.salary.canShu.beiZhu"/></th>
				<th width="15%"><!--错误原因--><spring:message code="ar.viewImportExcelTempList.ERROR_RESON.b"/></th>
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
	<c:set value="/pa/salary/viewImportExcelTempPaParamList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
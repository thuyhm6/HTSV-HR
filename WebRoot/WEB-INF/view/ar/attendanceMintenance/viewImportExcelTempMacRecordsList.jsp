<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
//导出
function exportMacRecordsImportInfo(a) {
	var $from = $("#viewMacRecordsExcelImportResult");
	alertMsg.confirm("<spring:message code='org.title.IS_INPUT' />", {//是否导出?
		okCall : function() {
			doMacRecordsImportInfoExport($from);
		}
	});
}
function doMacRecordsImportInfoExport(from) {
	var $from = $("#viewMacRecordsExcelImportResult");
	var url = "/ar/attendanceMintenance/viewImportMacRecordsListExcel";
	window.location = url + (url.indexOf('?') == -1 ? "?" : "&")
			+ $from.serialize();
}
//提交导入数据
function doPaAccountDataImport() {
	var params = $("#viewMacRecordsExcelImportResult").serialize();
	alertMsg.confirm("<spring:message code='alert.message.Confirm_To_Import.b' />", {//确认导入吗?
		okCall : function() {
			$.ajax( {
				type : 'post',
				cache : false,
				//url : "/pa/wagebase/createImportPaAllowanceExcelSelfListExcel?"
				url : "/ar/attendanceMintenance/createImportMacRecordsListExcel?"
						+ params,
				success : function(result) {
					if (result == 1) {
						alertMsg.info("<spring:message code='ar.alert.message.excelimport.importsuccess' />");//导入成功！
						navTab.closeCurrentTab();
					} else {
						alertMsg.info("<spring:message code='ar.alert.message.excelimport.importfail' />");//导入失败！
					}
					//页面重载
				navTabSearch($("#viewMacRecordsExcelImportResult"));
			}
			});
		}
	});
}
</script>
<div class="pageHeader">
	<form id="viewMacRecordsExcelImportResult" name="viewMacRecordsExcelImportResult"
		action="/ar/attendanceMintenance/viewImportExcelTempMacRecordsList"
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
						<spring:message code="pa.salary.canShu.chuCuoYuFou"/><!-- 出错与否-->
					</th>
					<td>
						<select name="seach_RESULT_FLAG" id="seach_RESULT_FLAG">
							<option value=""
								<c:if test="${RESULT_FLAG eq '' }">selected</c:if>><!--全部--><spring:message code="org.title.ALL"/>
							</option>
							<option value="E"
								<c:if test="${RESULT_FLAG eq 'E' }">selected</c:if>><!--是--><spring:message code="org.title.YES"/>
							</option>
							<option value="N"
								<c:if test="${RESULT_FLAG eq 'N' }">selected</c:if>><!--否--><spring:message code="ar.viewcycle.content.no"/>
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
					href="/pa/excelImport/importExcelData?
					&importFunName=/importArCardRecordExcel" target="dialog" mask="true" width="400" height="200" ><span><!-- EXCEL导入 --><spring:message code="ar.addempshift.title.excelimport"/></span>
				</a>
			</li>
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="button" onclick="exportMacRecordsImportInfo(this)">
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
				<th width="10%"><!--部门--><spring:message code="ess.infoApply.DEPT"/></th>
				<th width="10%"><!--考勤日期--><spring:message code="ess.infoApply.attendance_date"/></th>
				<th width="10%"><!--时间--><spring:message code="ess.infoApply.title.time"/></th>
				<th width="10%"><!--类型--><spring:message code="ess.trans.title.typeName"/></th>
				<th width="5%"><spring:message code="ess.empInfo.remarks"/><!--备注--></th>
				<th width="15%"><spring:message code="inct.salesman.validateMessage"/><!--验证结果--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${MDATA}" var="item" varStatus="i">
				<tr>
					<td>${i.count}</td>
					<td>${item.EMPID}</td>
					<td>${item.CHINESENAME}</td>
					<td>${item.DEPT}</td>
					<td>${item.AR_DATE_STR}</td>
					<td>${item.R_TIME}</td>
					<td>${item.DOOR_TYPE}</td>
					<td>${item.REMARK}</td>
					<td>${item.UPLOAD_ERROR_MSG}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/ar/attendanceMintenance/viewImportExcelTempMacRecordsList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
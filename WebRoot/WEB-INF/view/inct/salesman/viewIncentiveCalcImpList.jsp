<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	//导出
	function exportSalesmanIncImpResultList(a, navTabId) {
		var $form = $("#inctDataExcelImportResultForm");
		alertMsg.confirm("Do you want to export?", {
			okCall : function() {
				doSalesmanIncImpResultExport($form);
			}
		});
	}
	function doSalesmanIncImpResultExport(form) {
		var $form = $("#inctDataExcelImportResultForm");
		var url = "/inct/salesman/viewIncentiveCalcImpListExcel";
		window.location = url + (url.indexOf('?') == -1 ? "?" : "&")
				+ $form.serialize();
	}
	//提交导入数据   
	function doSalesmanInctDataImport() {
		var params = $("#inctDataExcelImportResultForm").serialize();
		alertMsg.confirm("确认导入调整数据?", {
			okCall : function() {
				$.ajax({
					type : 'post',
					cache : false,
					url : "/inct/salesman/callIncentiveDataImportResult?"
							+ params,
					success : function(result) {
						alert(result.message);
						if (result.statusCode == 200) {
							//页面重载
							$.pdialog.closeCurrent();
						} else {							
							//页面重载
							dwzSearch($("#inctDataExcelImportResultForm"), 'dialog');
						}						
					}
				});
			}
		});

	}
</script>
<div class="pageHeader">

	<form name="inctDataExcelImportResultForm"
		id="inctDataExcelImportResultForm"
		onsubmit="return dwzSearch(this,'dialog');"
		action="/inct/salesman/viewIncentiveCalcImpList" method="post"
		rel="pagerForm">
		<input type="hidden" name="CLOS_FLAG" value="${closeFlag}" />
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<input type="hidden" name="ACCRUAL_YN" value="${searchMap.ACCRUAL_YN}"/>
		<input type="hidden" name="PAY_AREA_CD" value="${searchMap.PAY_AREA_CD}"/>
		<input type="hidden" name="YEAR" value="${searchMap.YEAR}"/>
		<input type="hidden" name="MONTH" value="${searchMap.MONTH}"/>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="inct.salesman.daqu" /> <!--大区-->：</td>
					<td><ait:ComboDeptByCpnyIDTag id="display_PAY_AREA_CD"
							name="display_PAY_AREA_CD" parentNo="198659"
							selected="${PAY_AREA_CD}" cnpyID="${interCpnyID}" disabled="true"/>
					</td>
					<td><spring:message code="inct.salesman.excel.totalCnt" /> <!-- 总行数-->：</td>
					<td>${totalCnt}<iframe id='callbackframe' name='callbackframe'
							src='about:blank' style='display: none'></iframe>
					</td>
					<td><spring:message code="inct.salesman.excel.errCnt" /> <!-- 出错行数-->：</td>
					<td>${errCnt}</td>
					<td>出错与否：</td>
					<td><select name="seach_RESULT_FLAG" id="seach_RESULT_FLAG">
							<option value=""
								<c:if test="${RESULT_FLAG eq '' }">selected</c:if>>全部</option>
							<option value="E"
								<c:if test="${RESULT_FLAG eq 'E' }">selected</c:if>>是</option>
							<option value="N"
								<c:if test="${RESULT_FLAG eq 'N' }">selected</c:if>>否</option>
					</select></td>
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
			<li><div class="buttonActive">
					<div class="buttonContent">
						<button type="button" onclick="importExcelSalesIncData()">
							<spring:message code="inct.salesman.uploadExcel" />
							<!--上传excel-->
						</button>
					</div>
				</div></li>
			<li><div class="buttonActive">
					<div class="buttonContent">
						<button type="button"
							onclick="exportSalesmanIncImpResultList(this,'${request_navTabId}')">
							<spring:message code="inct.salesman.downloadToExcel" />
							<!--excel导出-->
						</button>
					</div>
				</div></li>
			<li><div class="buttonActive">
					<div class="buttonContent">
						<button type="submit" onclick="doSalesmanInctDataImport()">
							<spring:message code="public.title.submit" />
							<!--提交-->
						</button>
					</div>
				</div></li>
			<li><div class="button">
					<div class="buttonContent">
						<button type="button" id="btnClose" name="btnClose" class="close">
							<spring:message code="public.title.cancle" />
							<!--取消-->
						</button>
					</div>
				</div></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="150">
		<thead>
			<tr>
				<th width="10%">Line<!--excel行号--></th>
				<th width="15%"><spring:message code="ar.excelexport.title.month" /> <!--月份--></th>
				<th width="15%"><spring:message code="inct.salesman.empNo" /> <!--社号--></th>
				<th width="15%"><spring:message code="inct.salesman.adjustInct" /><!--调整提成--></th>
				<th width="15%"><spring:message code="inct.salesman.remark" /><!--备注--></th>
				<th width="30%"><spring:message code="inct.salesman.validateMessage" /> <!--验证结果--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="mdata" varStatus="i">
				<tr>
					<td class='td_right'>${mdata.LINE_ID}</td>
					<td class='td_center'>${mdata.INCTV_MON}</td>
					<td class='td_center'>${mdata.EMPNO}</td>
					<td class='td_right'>${mdata.ADJST_AMT}</td>
					<td>${mdata.REMARK}</td>
					<td>${mdata.UPLOAD_ERROR_MSG}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<form id="pagerForm" method="post" action="/inct/salesman/viewIncentiveCalcImpList?navTabId=${request_navTabId}">
	<div class="panelBar">
		<div class="pages">
			<span><spring:message code="public.title.view"/><!--显示--></span>
				<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
				</select>
			<span><spring:message code="public.title.tiao"/><!--条-->，<spring:message code="public.title.gong"/><!--共-->${totalCount}<spring:message code="public.title.tiao"/><!--条--></span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
	</form>

</div>
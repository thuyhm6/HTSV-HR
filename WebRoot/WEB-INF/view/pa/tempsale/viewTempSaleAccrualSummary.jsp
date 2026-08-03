<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
//excel导出
function exportContractInfo_pa0716() {
	var sform = document.getElementById("tempSaleAccrualSummary");
	alertMsg.confirm("Do you want to export?", {
		okCall : function() {
			//用于excel导出的表单参数处理
			var eForm = document.getElementById("excelExportForm_pa0716"); 
			document.getElementById("pa0716Link").innerHTML = "EXCEL密码设置";
			eForm.YEAR.value 		= sform.seach_YEAR.value;
			eForm.MONTH.value 			= sform.seach_MONTH.value;
			eForm.ACCRUAL_YN.value 		= sform.seach_ACCRUAL_YN.value;
			eForm.PAY_AREA_CD.value 		= sform.seach_PAY_AREA_CD.value;
			$("#importExcelDialog_pa0716").attr('href', "/sys/encryptExcel"
					+"?exportFunName=/pa/tempsale/viewTempSaleSendInfoExcel"
					+"&navTabId=pa0716"
					+"&formId=excelExportForm_pa0716");
			$("#importExcelDialog_pa0716").attr('width', "300");
			$("#importExcelDialog_pa0716").attr('height', "150");
			$("#importExcelDialog_pa0716").click();
		}
	});
}
function confirm_pa0716(){
	var $form = $("#tempSaleAccrualSummary");
	$form.attr("action","/pa/tempsale/viewTempSaleConfirm");
	$.ajax({
		type: 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: DWZ.ajaxDone,
		error: DWZ.ajaxError
	});
	$form.attr("action","/pa/tempsale/viewtempSaleAccrualSummary");
	return false;
}
</script>
<a id="importExcelDialog_pa0716" href="#" target="dialog" mask="true">
<span id="pa0716Link" style="display: none"></span></a> 
<div class="pageHeader">
<form id="tempSaleAccrualSummary" onsubmit="return navTabSearch(this);"
	action="/pa/tempsale/viewTempSaleAccrualSummary" rel="pagerForm" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
					<td><spring:message code="inct.salesman.daqu" /> <!--大区--></td>
					<td>
						<ait:deptTreeMulti id="seach_PAY_AREA_CD" name="seach_PAY_AREA_NM" limit="hr" level="2" 
						selected="${PAY_AREA_CD}"
						selectedNm="${PAY_AREA_NM}" />
					</td>
		<td>月份</td>
		<td>
	    	<ait:dateProMonth yearName="seach_YEAR" yearSelected="${YEAR}" monthName="seach_MONTH" monthSelected="${MONTH}"/>
	    	<input type="hidden" name="seach_ACCRUAL_YN" value="Y">
		</td>
	</tr>
</table>
<div class="subBar">
<ul>
	<li>
	<div class="buttonActive">
	<div class="buttonContent">
	<button type="submit"><spring:message
		code="public.title.search" /><!-- 检索 --></button>
	</div>
	</div>
	</li>
	
				<li>
					<div class="buttonActive"><div class="buttonContent"><!-- 检索 -->
						<button type="button" onclick="exportContractInfo_pa0716()" title="<spring:message code='rp.report.title.exportYN'/>">
							<%--导出Excel--%><spring:message code="ar.addempshift.title.excelexport"/>
						</button>
					</div></div>
				</li>
				<li>
					<div class="buttonActive"><div class="buttonContent"><!-- 确认 -->
						<button type="button" onclick="confirm_pa0716()">
							申请
						</button>
					</div></div>
				</li>
</ul>
</div>
</div>
</form>
</div>

<div class="pageContent">
	<table class="table" width="100%" layoutH="180" nowrapTD="false">
		<thead>
			<tr>
				<th>NO</th>
				<th>大区</th>
				<th>支社</th>
				<th>工资月</th>
				<th>产品类型</th>
				<th>实发金额</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paTempSalesSummaryList}" var="item" varStatus="i">
				<tr>
					<td class="td_center">${i.count }</td>
					<td class="td_center">${item.PAY_AREA_NAME }</td>
					<td class="td_center">${item.BRANCH_NAME }</td>
					<td class="td_center">${item.PA_MONTH }</td>
					<td class="td_center">${item.PROD_NAME }</td>
					<td class="td_center">${item.TOTAL_PAY }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
<c:set value="/pa/tempsale/viewTempSaleAccrualSummary" var="pageUrl" /> 
<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
<form id="excelExportForm_pa0716" name="excelExportForm_pa0716" method="post">
	<input type="hidden" id="password" 			name="password" 		value="" />
	<input type="hidden" id="YEAR" 				name="YEAR" 			value="" />
	<input type="hidden" id="MONTH" 			name="MONTH" 			value="" />
	<input type="hidden" id="ACCRUAL_YN" 			name="ACCRUAL_YN" 			value="" />
	<input type="hidden" id="PAY_AREA_CD" 		name="PAY_AREA_CD" 		value="" />
</form>
</div>
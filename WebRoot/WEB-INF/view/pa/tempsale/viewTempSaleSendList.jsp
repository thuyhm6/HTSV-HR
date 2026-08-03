<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
//excel导出
function exportContractInfo_pa0905() {
	var sform = document.getElementById("viewTempSaleSendList");
	alertMsg.confirm("Do you want to export?", {
		okCall : function() {
			//用于excel导出的表单参数处理
			var eForm = document.getElementById("excelExportForm_pa0905"); 
			document.getElementById("pa0905Link").innerHTML = "EXCEL密码设置";
			eForm.YEAR.value 		= sform.seach_YEAR.value;
			eForm.MONTH.value 			= sform.seach_MONTH.value;
			eForm.ACCRUAL_YN.value 		= sform.seach_ACCRUAL_YN.value;
			eForm.PAY_AREA_CD.value 		= sform.seach_PAY_AREA_CD.value;
			$("#importExcelDialog_pa0905").attr('href', "/sys/encryptExcel"
					+"?exportFunName=/pa/tempsale/viewTempSaleSendInfoExcel"
					+"&navTabId=pa0905"
					+"&formId=excelExportForm_pa0905");
			$("#importExcelDialog_pa0905").attr('width', "300");
			$("#importExcelDialog_pa0905").attr('height', "150");
			$("#importExcelDialog_pa0905").click();
		}
	});
}

function send_pa0905(){
	var $form = $("#viewTempSaleSendList");
	$form.attr("action","/pa/tempsale/tempSaleSend");
	$.ajax({
		type: 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: navTabAjaxDone || DWZ.ajaxDone,
		error: DWZ.ajaxError
	});
	$form.attr("action","/pa/tempsale/viewTempSaleSendList");
	return false;
}
</script>
<a id="importExcelDialog_pa0905" href="#" target="dialog" mask="true">
<span id="pa0905Link" style="display: none"></span></a> 
<div class="pageHeader">
<form id="viewTempSaleSendList" onsubmit="return navTabSearch(this);"
	action="/pa/tempsale/viewTempSaleSendList" rel="pagerForm" method="post">
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
	    	<input type="hidden" name="seach_ACCRUAL_YN" value="N">
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
						<button type="button" onclick="exportContractInfo_pa0905()" title="<spring:message code='rp.report.title.exportYN'/>">
							<%--导出Excel--%><spring:message code="ar.addempshift.title.excelexport"/>
						</button>
					</div></div>
				</li>
				<li>
					<div class="buttonActive"><div class="buttonContent"><!-- 检索 -->
						<button type="button" onclick="send_pa0905()">
							传送财务
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
				<th>应发金额</th>
				<th>税金</th>
				<th>实发金额</th>
				<th>传送状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paTempSalesSendList}" var="item" varStatus="i">
				<tr>
					<td class="td_center">${i.count }</td>
					<td class="td_center">${item.PAY_AREA_NAME }</td>
					<td class="td_center">${item.BRANCH_NAME }</td>
					<td class="td_center">${item.PA_MONTH }</td>
					<td class="td_center">${item.PROD_NAME }</td>
					<td class="td_center">${item.TOTAL_PAY }</td>
					<td class="td_center">${item.TAX_PAY }</td>
					<td class="td_center">${item.NET_PAY }</td>
					<td class="td_center">${item.IS_SEND }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
<c:set value="/pa/tempsale/viewTempSaleSendList?seach_KEY=${KEY}&seach_YEAR=${YEAR }&seach_MONTH=${MONTH }&seach_PAY_AREA_CD=${PAY_AREA_CD}&seach_ACCRUAL_YN=N" var="pageUrl" /> 
<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
<form id="excelExportForm_pa0905" name="excelExportForm_pa0905" method="post">
	<input type="hidden" id="password" 			name="password" 		value="" />
	<input type="hidden" id="YEAR" 				name="YEAR" 			value="" />
	<input type="hidden" id="MONTH" 			name="MONTH" 			value="" />
	<input type="hidden" id="ACCRUAL_YN" 			name="ACCRUAL_YN" 			value="" />
	<input type="hidden" id="PAY_AREA_CD" 		name="PAY_AREA_CD" 		value="" />
</form>
</div>
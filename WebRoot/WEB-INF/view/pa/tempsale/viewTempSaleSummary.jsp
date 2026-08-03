<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
//excel导出
function exportContractInfo_pa0715() {
	var sform = document.getElementById("tempSaleSummary");
	alertMsg.confirm("Do you want to export?", {
		okCall : function() {
			//用于excel导出的表单参数处理
			var eForm = document.getElementById("excelExportForm_pa0715"); 
			document.getElementById("pa0715Link").innerHTML = "EXCEL密码设置";
			eForm.KEY.value 			= sform.seach_KEY.value;
			eForm.YEAR.value 		= sform.seach_YEAR.value;
			eForm.MONTH.value 			= sform.seach_MONTH.value;
			eForm.TYPE.value 		= sform.seach_TYPE.value;
			eForm.PAY_AREA_CD.value 		= sform.seach_PAY_AREA_CD.value;
			$("#importExcelDialog_pa0715").attr('href', "/sys/encryptExcel"
					+"?exportFunName=/pa/tempsale/viewTempSaleSummaryInfoExcel"
					+"&navTabId=pa0715"
					+"&formId=excelExportForm_pa0715");
			$("#importExcelDialog_pa0715").attr('width', "300");
			$("#importExcelDialog_pa0715").attr('height', "150");
			$("#importExcelDialog_pa0715").click();
		}
	});
}

function summary_pa0715(){
	var $form = $("#tempSaleSummary");
	$form.attr("action","/pa/tempsale/tempSaleSummary");
	$.ajax({
		type: 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: DWZ.ajaxDone,
		error: DWZ.ajaxError
	});
	$form.attr("action","/pa/tempsale/viewTempSaleSummary");
	return false;
}
function confirm_pa0715(){
	var $form = $("#tempSaleSummary");
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
	$form.attr("action","/pa/tempsale/viewTempSaleSummary");
	return false;
}
</script>
<a id="importExcelDialog_pa0715" href="#" target="dialog" mask="true">
<span id="pa0715Link" style="display: none"></span></a> 
<div class="pageHeader">
<form id="tempSaleSummary" onsubmit="return navTabSearch(this);"
	action="/pa/tempsale/viewTempSaleSummary" rel="pagerForm" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
					<td><spring:message code="inct.salesman.daqu" /> <!--大区--></td>
					<td>
						<ait:deptTreeMulti id="seach_PAY_AREA_CD" name="seach_PAY_AREA_NM" limit="hr" level="2" 
						selected="${PAY_AREA_CD}"
						selectedNm="${PAY_AREA_NM}" />
					</td>
		<td>关键字</td>
		<td><input type="text" id="seach_KEY" name="seach_KEY"
			value="${KEY}" /></td>
		<td>月份</td>
		<td>
	    	<ait:dateProMonth yearName="seach_YEAR" yearSelected="${YEAR}" monthName="seach_MONTH" monthSelected="${MONTH}"/>
	    	<input type="hidden" name="ACCRUAL_YN" value="N"/>
		</td>
		<td>类别</td>
		<td>
			<select name="seach_TYPE">
				<option value="summary" <c:if test="${ TYPE eq 'summary' }" >selected</c:if>>汇总</option>
				<option value="detail" <c:if test="${ TYPE eq 'detail' }" >selected</c:if>>明细</option>
			</select>
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
						<button type="button" onclick="exportContractInfo_pa0715()" title="<spring:message code='rp.report.title.exportYN'/>">
							<%--导出Excel--%><spring:message code="ar.addempshift.title.excelexport"/>
						</button>
					</div></div>
				</li>
				<li>
					<div class="buttonActive"><div class="buttonContent"><!-- 计算税金 -->
						<button type="button" onclick="summary_pa0715()">
							计算税金
						</button>
					</div></div>
				</li>
				<li>
					<div class="buttonActive"><div class="buttonContent"><!-- 确认 -->
						<button type="button" onclick="confirm_pa0715()">
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
<c:if test="${TYPE eq 'summary'}">
	<table class="table" width="100%" layoutH="180" nowrapTD="false">
		<thead>
			<tr>
				<th>NO</th>
				<th>姓名</th>
				<th>工资月</th>
				<th>身份证号</th>
				<th>大区</th>
				<th>支社</th>
				<th>产品类型</th>
				<th>银行账号</th>
				<th>开户行</th>
				<th>应发金额</th>
				<th>税金</th>
				<th>实发工资</th>
				<th>工作天数</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paTempSalesSummaryList}" var="item" varStatus="i">
				<tr target="ID_CARD" rel="${item.ID_CARD }">
					<td class="td_center" >${i.count }</td>
					<td class="td_center">${item.LOCAL_NAME }</td>
					<td class="td_center">${item.PA_MONTH }</td>
					<td class="td_center">${item.ID_CARD }</td>
					<td class="td_center">${item.PAY_AREA_NAME }</td>
					<td class="td_center">${item.BRANCH_NAME }</td>
					<td class="td_center">${item.PROD_NAME }</td>
					<td class="td_center">${item.BANK_NO }</td>
					<td class="td_center">${item.BANK_NAME }</td>
					<td class="td_center">${item.TOTAL_PAY }</td>
					<td class="td_center">${item.TAX_PAY }</td>
					<td class="td_center">${item.NET_PAY }</td>
					<td class="td_center">${item.WORK_DAYS }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>

<c:if test="${TYPE eq 'detail'}">
	<table class="table" width="100%" layoutH="180" nowrapTD="false">
		<thead>
			<tr>
				<th>NO</th>
				<th>姓名</th>
				<th>工资月</th>
				<th>身份证号</th>
				<th>大区</th>
				<th>支社</th>
				<th>产品类型</th>
				<th>应发金额</th>
				<th>工作天数</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paTempSalesSummaryList}" var="item" varStatus="i">
				<tr target="ID_CARD" rel="${item.ID_CARD }">
					<td class="td_center">${i.count }</td>
					<td class="td_center">${item.LOCAL_NAME }</td>
					<td class="td_center">${item.PA_MONTH }</td>
					<td class="td_center">${item.ID_CARD }</td>
					<td class="td_center">${item.PAY_AREA_NAME }</td>
					<td class="td_center">${item.BRANCH_NAME }</td>
					<td class="td_center">${item.PROD_NAME }</td>
					<td class="td_center">${item.TOTAL_PAY }</td>
					<td class="td_center">${item.WORK_DAYS }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>
<c:set value="/pa/tempsale/viewTempSaleSummary?seach_KEY=${KEY}&seach_YEAR=${YEAR }&seach_MONTH=${MONTH }&seach_TYPE=${TYPE}&seach_PAY_AREA_CD=${PAY_AREA_CD}" var="pageUrl" /> 
<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
<form id="excelExportForm_pa0715" name="excelExportForm_pa0715" method="post">
	<input type="hidden" id="password" 			name="password" 		value="" />
	<input type="hidden" id="YEAR" 				name="YEAR" 			value="" />
	<input type="hidden" id="MONTH" 			name="MONTH" 			value="" />
	<input type="hidden" id="TYPE" 			name="TYPE" 			value="" />
	<input type="hidden" id="KEY" 		name="KEY" 		value="" />
	<input type="hidden" id="PAY_AREA_CD" 		name="PAY_AREA_CD" 		value="" />
</form>
</div>
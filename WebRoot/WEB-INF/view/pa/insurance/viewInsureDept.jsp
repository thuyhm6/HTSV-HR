<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function exportExcel() {
	var obj = document.getElementById('excelPost');
	obj.action = "/pa/insurance/viewInsureDeptExcel";
	obj.submit();
}
//excel导出
function exportSalesmanEvalInfo(a, navTabId) {
	var sform = document.getElementById("excelPost");
	alertMsg.confirm("确定要导出么?", {
		okCall : function() {
			//用于excel导出的表单参数处理
			var eForm = document.getElementById("excelExportForm_Insure0102"); 
			document.getElementById("Insure0102").innerHTML = "EXCEL密码设置";
			eForm.paYear.value		= sform.seach_paYear.value;
			eForm.paMonth.value	    = sform.seach_paMonth.value;
			eForm.DEPTNO.value 		= sform.seach_DEPT_NO.value;
			$("#importExcelDialog_Insure0102").attr('href', "/sys/encryptExcel"
					+"?exportFunName=/pa/insurance/viewInsureDeptExcel"
					+"&navTabId=pa0420"
					+"&formId=excelExportForm_Insure0102");
			$("#importExcelDialog_Insure0102").attr('width', "300");
			$("#importExcelDialog_Insure0102").attr('height', "150");
			$("#importExcelDialog_Insure0102").click();
		}
	});
}
</script>
<a id="importExcelDialog_Insure0102" href="#" target="dialog" mask="true">
	<span id="Insure0102" style="display: none"></span></a> 
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/pa/insurance/viewInsureDept" method="post" rel="pagerForm"
		id="excelPost">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td style="text-align: right">
						<!-- 年份 --><spring:message code="pa.payear.title.payear" />：
					</td>
					<td style="text-align: left">
						<ait:date yearName="seach_paYear" yearSelected="${paYear}"
							monthName="seach_paMonth" monthSelected="${paMonth}" />
					</td>
					<td>部门：</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" id="addInsureDept" limit="pa"/>
						<ait:deptTreeIcon name="seach_DEPT_NO" id="addInsureDept" selected="${DEPT_NO}" limit="pa"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<!--检索--><spring:message code="public.title.search" />
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
		<ul class="toolBar">
			<li>
				<a class="edit" onclick="exportSalesmanEvalInfo(this,'pa0420')"> <span>
						<%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport" />
				</span>
				</a>
			</li>
		</ul>
	</div>
	<table class="table" width="150%" layoutH="250">
		<thead>
			<%--<tr>
				<th colspan="16" style="text-align: center">保险基本信息</th>
			</tr>
			<tr>
				
				<th colspan="10" style="text-align: center" width="60%">社会保险</th>
				<th colspan="2" style="text-align: center" width="12%">公积金</th>
				<th colspan="2" style="text-align: center" width="12%">合计</th>
			</tr>--%>
			<tr>
				<th style="text-align: center">部门</th>
				<th style="text-align: center">考勤月</th>
				<th style="text-align: center">养老(公司)</th>
				<th style="text-align: center">失业(公司)</th>
				<th style="text-align: center">医疗(公司)</th>
				<th style="text-align: center">工伤(公司)</th>
				<th style="text-align: center">生育(公司)</th>
				<th style="text-align: center">养老(个人)</th>
				<th style="text-align: center">失业(个人)</th>
				<th style="text-align: center">医疗(个人)</th>
				<th style="text-align: center">社保(公司)</th>
				<th style="text-align: center">社保(个人)</th>
				<th style="text-align: center">保险补扣(个人)</th>
				<th style="text-align: center">保险补扣(公司)</th>
				<th style="text-align: center">大额大病(个人)</th>
				<th style="text-align: center">大额大病(公司)</th>
				<th style="text-align: center">大病统筹(公司)</th>
				<th style="text-align: center">公积金(公司)	</th>
				<th style="text-align: center">公积金(个人)	</th>
				<th style="text-align: center">合计五险一金(公司)</th>
				<th style="text-align: center">合计五险一金(个人)</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${dataList}" var="person">
				<tr>
					<td style="text-align: center">${person.DEPT_NAME }</td>
					<td style="text-align: center">${person.IS_MONTH}</td>
					<td style="text-align: center">${person.IS_ENDOWMENT_COR}</td>
					<td style="text-align: center">${person.IS_UNEMPLOY_COR}</td>
					<td style="text-align: center">${person.IS_MEDICAL_COR}</td>
					<td style="text-align: center">${person.IS_INJURY_COR}</td>
					<td style="text-align: center">${person.IS_FERTILITY_COR}</td>
					<td style="text-align: center">${person.IS_ENDOWMENT_PER}</td>
					<td style="text-align: center">${person.IS_UNEMPLOY_PER}</td>
					<td style="text-align: center">${person.IS_MEDICAL_PER}</td>
					<td style="text-align: center">${person.IS_TOTAL_COR}</td>
					<td style="text-align: center">${person.IS_TOTAL_PER}</td>
					<td style="text-align: center">${person.P_IS_AJUST_PER}</td>
					<td style="text-align: center">${person.P_IS_AJUST_COR}</td>
					<td style="text-align: center">${person.IS_SERIOUS_P}</td>
					<td style="text-align: center">${person.IS_SERIOUS_C}</td>
					<td style="text-align: center">${person.IS_BIGDISEASE_C}</td>
					<td style="text-align: center">${person.IS_FUND_COR}</td>
					<td style="text-align: center">${person.IS_FUND_RER}</td>
					<td style="text-align: center">${person.COR}</td>
					<td style="text-align: center">${person.PER}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/insurance/viewInsureDept" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
<form id="excelExportForm_Insure0102" name="excelExportForm_Insure0102" method="post">
	<input type="hidden" id="password" name="password" value="" />
	<input type="hidden" id="paYear"   name="paYear" value="" />
	<input type="hidden" id="paMonth"  name="paMonth" value="" />
	<input type="hidden" id="DEPTNO"   name="DEPTNO" value="" />
</form>
</div>
<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	//导入数据
	function importExcelSalesEvalData() {
		var payAreaCd = document.searchForm_se0102_aa.seach_PAY_AREA_NM.value;
		$("#importExcelDialog_se0102")
				.attr('href',
						'/pa/excelImport/importExcelData?importFunName=/importExcelSalesEvalData');
		$("#importExcelDialog_se0102").attr('height', "200");
		$("#importExcelDialog_se0102").attr('width', "400");
		$("#importExcelDialog_se0102").click();
	}
	//excel导出
	function exportSalesmanEvalInfo(a, navTabId) {
		var payAreaCd = document.searchForm_se0102_aa.seach_PAY_AREA_NM.value;
		var sform = document.getElementById("searchForm_se0102_aa");
		alertMsg.confirm("Do you want to export?", {
			okCall : function() {
				//用于excel导出的表单参数处理
				var eForm = document.getElementById("excelExportForm_se0102"); 
				document.getElementById("se0102Link").innerHTML = "EXCEL密码设置";
				eForm.PAY_AREA_CD.value		= sform.seach_PAY_AREA_CD.value;
				eForm.SALES_EVAL_TYPE.value	= sform.seach_SALES_EVAL_TYPE.value;
				eForm.EV_TP_CD.value 		= sform.seach_EV_TP_CD.value;
				eForm.YEAR.value 			= sform.seach_YEAR.value;
				eForm.QUARTER.value 		= sform.seach_QUARTER.value;
				eForm.EMPNO.value 			= sform.seach_EMPID.value;
				$("#importExcelDialog_se0102").attr('href', "/sys/encryptExcel"
						+"?exportFunName=/inct/salesman/viewEvaluationDataImportListExcel"
						+"&navTabId=se0102"
						+"&formId=excelExportForm_se0102");
				$("#importExcelDialog_se0102").attr('width', "300");
				$("#importExcelDialog_se0102").attr('height', "150");
				$("#importExcelDialog_se0102").click();
			}
		});
	}

</script>
<a id="importExcelDialog_se0102" href="#" width="400" height="200" target="dialog" mask="true"><span
		id="se0102Link" style="display: none"></span></a> 
<a id="importExcel_se0102" href="#" target="dialog" width="800" height="420" mask="true"><span
	style="display: none;"><spring:message
			code="inct.salesman.excel.importResult.EvaluationData" />
		<!--评价数据导入结果--></span></a>

<div class="pageHeader">
<a id="importExcelDialog_se0201" href="#" target="dialog" mask="true"><span
		id="se0201Link" style="display: none"></span></a> 
	<form name="searchForm_se0102_aa" 
		id="searchForm_se0102_aa" onsubmit="return navTabSearch(this);"
		action="/inct/salesman/viewEvaluationDataImportList" method="post"
		rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="inct.salesman.daqu" />
						<!--大区-->：</td>
					<td>
						<ait:deptTreeMulti id="seach_PAY_AREA_CD" name="seach_PAY_AREA_NM" limit="pa" level="2" 
						selected="${searchMap.PAY_AREA_CD}"
						selectedNm="${searchMap.PAY_AREA_NM}" />	
					</td>
					<td><spring:message code="inct.salesman.evaluationItemType" />
						<!--评价项目-->：</td>
					<td><ait:ComboSyCodeDescByCpnyID id="seach_SALES_EVAL_TYPE"
							name="seach_SALES_EVAL_TYPE" parentNo="210868"
							selected="${searchMap.SALES_EVAL_TYPE}" cnpyID="${interCpnyID}" limit="all" />
					</td>
					<td><spring:message code="inct.salesman.empNoNName" />
						<!--社号/姓名-->：</td>
					<td>
						<input id="seach_EMPID" name="dwz.person.empId" type="text" value="${searchMap.EMPNO}" lookupGroup="person"/>
						<input id="seach_PERSON_ID" name="dwz.person.personId" type="hidden" value="" readOnly lookupGroup="person"/>
						<a class="btnLook" style="float:right;" href="/ar/attendanceSettings/viewKeeperList?pageNum=1" width="900" height="400" lookupGroup="person"><!-- 查找带回 --><spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a>
					</td>
				</tr>
				<tr>
					<td><spring:message code="inct.salesman.yearNSeason" />
						<!--年/季度-->：</td>
					<td><ait:date yearName="seach_YEAR" yearSelected="${searchMap.YEAR}"
							yearPlus="10" /> <select id="seach_QUARTER" name="seach_QUARTER">
							<option value="1" <c:if test="${searchMap.QUARTER eq '1' }">selected</c:if>>1</option>
							<option value="2" <c:if test="${searchMap.QUARTER eq '2' }">selected</c:if>>2</option>
							<option value="3" <c:if test="${searchMap.QUARTER eq '3' }">selected</c:if>>3</option>
							<option value="4" <c:if test="${searchMap.QUARTER eq '4' }">selected</c:if>>4</option>
					</select></td>
					<td><spring:message code="inct.salesman.evaluationType" />
						<!--评价类型-->：</td>
					<td><ait:ComboSyCodeDescByCpnyID id="seach_EV_TP_CD"
							name="seach_EV_TP_CD" parentNo="14895"
							selected="${searchMap.EV_TP_CD}" cnpyID="${interCpnyID}" limit="all" /></td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button  id="btnSearch_se0102" name="btnSearch_se0102" type="submit">
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
						<c:if test="${toolbarInfo.UPDATER == '1'}">
							<a class="downloadExel" href="/inct/salesman/downloadSalesEvalDataImpTemplate">						
							<span>
								<spring:message code="inct.salesman.downloadExcelTemplate" />
							</span>
							</a>
						</c:if>
					</div>
				</div></li>
			<li><div class="buttonActive">
					<div class="buttonContent">
						<c:if test="${toolbarInfo.UPDATER == '1'}">
							<button type="button" onclick="importExcelSalesEvalData()">
								<spring:message code="inct.salesman.uploadExcel" />
								<!--上传excel-->
							</button>
						</c:if>
					</div>
				</div></li>
			<li><div class="buttonActive">
					<div class="buttonContent">
						<button type="button" id="btnExcelExport_se0102" name="btnExcelExport_se0102"
							onclick="exportSalesmanEvalInfo(this,'${request_navTabId}')">
							<spring:message code="inct.salesman.downloadToExcel" />
							<!--excel导出-->
						</button>
					</div>
				</div></li>
		</ul>
	</div>	
	<table class="table" width="100%" layoutH="231">
		<thead>
			<tr>
				<th width="6%"><spring:message code="inct.salesman.year" />
					<!--年--></th>
				<th width="5%"><spring:message code="inct.salesman.Season" />
					<!--季度--></th>
				<th width="10%"><spring:message code="inct.salesman.daqu" />
					<!--大区--></th>
				<th width="10%"><spring:message code="inct.salesman.empNo" />
					<!--社号--></th>
				<th width="8%"><spring:message code="inct.salesman.empName" />
					<!--员工姓名--></th>
				<th width="12%"><spring:message code="inct.salesman.evaluationType" />
					<!--评价类型--></th>
				<th width="10%"><spring:message
						code="inct.salesman.evaluationItemType" />
					<!--评价项目--></th>
				<th width="9%"><spring:message
						code="inct.salesman.currentYearAchieve" />
					<!--今年实绩--></th>
				<th width="9%"><spring:message
						code="inct.salesman.lastYearAchieve" />
					<!--去年实绩--></th>
				<th width="10%"><spring:message code="inct.salesman.updateBy" />
					<!--更新人--></th>
				<th width="10%"><spring:message code="inct.salesman.updateTime" />
					<!--更新时间--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="mdata" varStatus="i">
				<tr>
					<td class='td_center'>${mdata.YYYY}</td>
					<td class='td_center'>${mdata.QUARTER}</td>
					<td class='td_center'>${mdata.PAY_AREA_NM}</td>
					<td class='td_center'>${mdata.EMPNO}</td>
					<td class='td_center'>${mdata.EMP_NM}</td>
					<td>${mdata.EV_TP_NM}</td>					
					<td>${mdata.CATEGORY_NM}</td>
					<td class='td_right'>${mdata.CURRENT_VALUE}</td>
					<td class='td_right'>${mdata.LAST_VALUE}</td>
					<td class='td_center'>${mdata.UPDT_USER}</td>
					<td class='td_center'>${mdata.UPDT_DTIME}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/inct/salesman/viewEvaluationDataImportList"
		var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
<form id="excelExportForm_se0102" name="excelExportForm_se0102" method="post">
	<input type="hidden" id="password" 			name="password" 		value="" />
	<input type="hidden" id="PAY_AREA_CD" 		name="PAY_AREA_CD" 		value="" />
	<input type="hidden" id="SALES_EVAL_TYPE" 	name="SALES_EVAL_TYPE" 	value="" />
	<input type="hidden" id="EV_TP_CD" 			name="EV_TP_CD" 		value="" />
	<input type="hidden" id="YEAR" 				name="YEAR" 			value="" />
	<input type="hidden" id="QUARTER" 			name="QUARTER" 			value="" />
	<input type="hidden" id="EMPNO" 			name="EMPNO" 			value="" />
</form>
</div>
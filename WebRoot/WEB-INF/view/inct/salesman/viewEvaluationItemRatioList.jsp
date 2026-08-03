<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	//excel导出
	function exportEvaluationItemRatioInfo(a, navTabId) {
		var sform = document.getElementById("searchForm_se0103_aa");
		alertMsg
				.confirm(
						"Do you want to export?",
						{
							okCall : function() {
								//用于excel导出的表单参数处理
								var eForm = document
										.getElementById("excelExportForm_se0103");
								document.getElementById("se0103Link").innerHTML = "EXCEL密码设置";
								eForm.SALES_EVAL_TYPE.value = sform.seach_SALES_EVAL_TYPE.value;
								eForm.EV_TP_CD.value = sform.seach_EV_TP_CD.value;
								eForm.JOB_POSI_CD.value = sform.seach_JOB_POSI_CD.value;
								eForm.LEFT_VALUE.value = sform.seach_LEFT_VALUE.value;
								eForm.TOP_VALUE.value = sform.seach_TOP_VALUE.value;
								var url = "/sys/encryptExcel"
										+ "?exportFunName=/inct/salesman/viewEvaluationItemRatioListExcel"
										+ "&navTabId=se0103"
										+ "&formId=excelExportForm_se0103";
								$("#importExcelDialog_se0103")
										.attr('href', url);
								$("#importExcelDialog_se0103").attr('width',
										"300");
								$("#importExcelDialog_se0103").attr('height',
										"150");
								$("#importExcelDialog_se0103").click();
							}
						});
	}
	//系数修改
	function editEvaluationItemRatio(param) {
		$("#se0103Link_1").html("修改");
		$("#editLink_se0103").attr('href',
				'/inct/salesman/editEvaluationItemRatioView?' + param);
		$("#editLink_se0103").attr('width', "450");
		$("#editLink_se0103").attr('height', "430");
		$("#editLink_se0103").click();
	}
</script>
<div class="pageHeader">
	<a id="importExcelDialog_se0103" href="#" target="dialog" mask="true"><span
		id="se0103Link" name="se0103Link" style="display: none"></span></a> 
	<a id="editLink_se0103"
		href="#" target="dialog" mask="true"><span id="se0103Link_1" name="se0103Link_1"
		style="display: none"></span></a>
	<form id="searchForm_se0103_aa" name="searchForm_se0103_aa"
		onsubmit="return navTabSearch(this);"
		action="/inct/salesman/viewEvaluationItemRatioList" method="post"
		rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="inct.salesman.evaluationItemType" />
						<!--评价项目-->：</td>
					<td><ait:ComboSyCodeDescByCpnyID id="seach_SALES_EVAL_TYPE"
							name="seach_SALES_EVAL_TYPE" parentNo="210878"
							selected="${SALES_EVAL_TYPE}" cnpyID="${interCpnyID}" limit="all" />
					</td>
					<td><spring:message code="inct.salesman.evaluationType" />
						<!--评价类型-->：</td>
					<td><ait:ComboSyCodeDescByCpnyID id="seach_EV_TP_CD"
							name="seach_EV_TP_CD" parentNo="14895" selected="${EV_TP_CD}"
							cnpyID="${interCpnyID}" limit="all" /></td>
					<td><spring:message code="sys.affirm.title.duty" />
						<!--职责-->：</td>
					<td><ait:ComboJobPositionByCpnyIDTag id="seach_JOB_POSI_CD"
							name="seach_JOB_POSI_CD" parentNo="215918"
							selected="${JOB_POSI_CD}" cnpyID="${interCpnyID}" limit="all" />
					</td>
				</tr>
				<tr>
					<td><spring:message code="inct.salesman.eval.itemRatio.left" />
						<!--左侧值-->：</td>
					<td><input id="seach_LEFT_VALUE" name="seach_LEFT_VALUE"
						type="text" value="${LEFT_VALUE}" size="14" class="textInput"
						onkeyup="if(isNaN(value))execCommand('undo')"
						onafterpaste="if(isNaN(value))execCommand('undo')"></td>
					<td><spring:message code="inct.salesman.eval.itemRatio.top" />
						<!--顶部值-->：</td>
					<td><input id="seach_TOP_VALUE" name="seach_TOP_VALUE"
						type="text" value="${TOP_VALUE}" size="14" class="textInput"
						onkeyup="if(isNaN(value))execCommand('undo')"
						onafterpaste="if(isNaN(value))execCommand('undo')"></td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search" />
									<!-- 查询 -->
								</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<c:set value="dialog" var="add_tab" />
	<c:set value="450" var="add_width" />
	<c:set value="370" var="add_height" />
	<c:set value="/inct/salesman/addEvaluationItemRatioView" var="add_Url" />
	<c:set value="dialog" var="edit_tab" />
	<c:set value="450" var="edit_width" />
	<c:set value="430" var="edit_height" />
	<c:set
		value="/inct/salesman/editEvaluationItemRatioView?{evaluationItem}"
		var="edit_Url" />
	<c:set
		value="javascript:exportEvaluationItemRatioInfo(this,'${request_navTabId}');"
		var="excelD_Url" />
	<%@ include file="/WEB-INF/view/inc/includeExcelButton.jsp"%>

	<table class="table" width="100%" layoutH="231">
		<thead>
			<tr>
				<th width="5%"><spring:message
						code="inct.salesman.evaluationItemType" />
					<!--评价项目--></th>
				<th width="6%"><spring:message code="inct.salesman.evaluationType" />
						<!--评价类型--></th>
				<th width="8%"><spring:message code="sys.affirm.title.duty" />
					<!--职责--></th>
				<th width="4%"><spring:message
						code="inct.salesman.eval.itemRatio.leftStart" />
					<!--左侧区间开始值--></th>
				<th width="4%"><spring:message
						code="inct.salesman.eval.itemRatio.leftEnd" />
					<!--左侧区间结束值--></th>
				<th width="4%"><spring:message
						code="inct.salesman.eval.itemRatio.topStart" />
					<!--顶部区间开始值--></th>
				<th width="4%"><spring:message
						code="inct.salesman.eval.itemRatio.topEnd" />
					<!--顶部区间结束值--></th>
				<th width="3%"><spring:message
						code="inct.salesman.eval.itemRatio.Ratio" />
					<!--系数--></th>
				<th width="5%"><spring:message
						code="ar.excelexport.title.createempid" />
					<!--注册人--></th>
				<th width="6%"><spring:message
						code="ar.excelexport.title.createdate" />
					<!--注册时间--></th>
				<th width="5%"><spring:message code="inct.salesman.updateBy" />
					<!--更新人--></th>
				<th width="6%"><spring:message code="inct.salesman.updateTime" />
					<!--更新时间--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${MDATA}" var="mdata" varStatus="i">
				<tr target="evaluationItem"
					rel="MAP_SEQ=${mdata.MAP_SEQ}&SALES_EVAL_TYPE=${mdata.CATEGORY}&EV_TP_CD=${mdata.EV_TP_CD}&JOB_POSI_CD=${mdata.JOB_POSI_CD}">
					<td>${mdata.CATEGORY_NM}</td>
					<td>${mdata.EV_TP_NM}</td>
					<td>${mdata.JOB_POSI_NM}</td>
					<td class='td_right'>${mdata.LEFT_START_VALUE}</td>
					<td class='td_right'>${mdata.LEFT_END_VALUE}</td>
					<td class='td_right'>${mdata.TOP_START_VALUE}</td>
					<td class='td_right'>${mdata.TOP_END_VALUE}</td>
					<td class='td_right'
						onclick="editEvaluationItemRatio('MAP_SEQ=${mdata.MAP_SEQ}&SALES_EVAL_TYPE=${mdata.CATEGORY}&EV_TP_CD=${mdata.EV_TP_CD}&JOB_POSI_CD=${mdata.JOB_POSI_CD}')">
						${mdata.RATIO}</td>
					<td class='td_center'>${mdata.RGST_USER}</td>
					<td class='td_center'>${mdata.RGST_DTIME}</td>
					<td class='td_center'>${mdata.UPDT_USER}</td>
					<td class='td_center'>${mdata.UPDT_DTIME}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/inct/salesman/viewEvaluationItemRatioList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	<form id="excelExportForm_se0103" name="excelExportForm_se0103"
		method="post">
		<input type="hidden" id="password" name="password" value="" /> <input
			type="hidden" id="SALES_EVAL_TYPE" name="SALES_EVAL_TYPE" value="" />
		<input type="hidden" id="EV_TP_CD" name="EV_TP_CD" value="" /> <input
			type="hidden" id="JOB_POSI_CD" name="JOB_POSI_CD" value="" /> <input
			type="hidden" id="LEFT_VALUE" name="LEFT_VALUE" value="" /> <input
			type="hidden" id="TOP_VALUE" name="TOP_VALUE" value="" />
	</form>
</div>
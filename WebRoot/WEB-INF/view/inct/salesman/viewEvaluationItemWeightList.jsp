<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	//excel导出
	function exportEvaluationItemWeightInfo(a, navTabId) {
		var sform = document.getElementById("searchForm_se0101_aa");
		alertMsg
				.confirm(
						"Do you want to export?",
						{
							okCall : function() {
								//用于excel导出的表单参数处理
								var eForm = document
										.getElementById("excelExportForm_se0101");
								document.getElementById("se0101Link").innerHTML = "EXCEL密码设置";
								eForm.SALES_EVAL_TYPE.value = sform.seach_SALES_EVAL_TYPE.value;
								eForm.EV_TP_CD.value = sform.seach_EV_TP_CD.value;
								eForm.JOB_POSI_CD.value = sform.seach_JOB_POSI_CD.value;
								$("#editLink_se0101")
										.attr(
												'href',
												"/sys/encryptExcel"
														+ "?exportFunName=/inct/salesman/viewEvaluationItemWeightListExcel"
														+ "&navTabId=se0101"
														+ "&formId=excelExportForm_se0101");
								$("#editLink_se0101").attr('width', "300");
								$("#editLink_se0101").attr('height', "150");
								$("#editLink_se0101").click();
							}
						});
	}
	//权重修改
	function editEvaluationItemWeight(param) {
		var se0101Link = document.getElementById("se0101Link");
		se0101Link.innerHTML = "修改";
		$("#editLink_se0101").attr('href',
				'/inct/salesman/editEvaluationItemWeightView?' + param);
		$("#editLink_se0101").attr('width', "450");
		$("#editLink_se0101").attr('height', "350");
		$("#editLink_se0101").click();
	}
</script>
<div class="pageHeader">
	<a id="editLink_se0101" href="#" target="dialog" mask="true"><span
		id="se0101Link" style="display: none"></span></a>
	<form name="searchForm_se0101_aa" id="searchForm_se0101_aa"
		onsubmit="return navTabSearch(this);"
		action="/inct/salesman/viewEvaluationItemWeightList" method="post"
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
	<c:set value="230" var="add_height" />
	<c:set value="/inct/salesman/addEvaluationItemWeightView" var="add_Url" />
	<c:set value="dialog" var="edit_tab" />
	<c:set value="450" var="edit_width" />
	<c:set value="350" var="edit_height" />
	<c:set
		value="/inct/salesman/editEvaluationItemWeightView?{evaluationItem}"
		var="edit_Url" />
	<c:set
		value="javascript:exportEvaluationItemWeightInfo(this,'${request_navTabId}');"
		var="excelD_Url" />
	<%@ include file="/WEB-INF/view/inc/includeExcelButton.jsp"%>

	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="5%"><spring:message code="sys.affirm.CodeNo" />
					<!--代码--></th>
				<th width="12%"><spring:message
						code="inct.salesman.evaluationItemType" />
					<!--评价项目--></th>
				<th width="10%"><spring:message code="inct.salesman.evaluationType" />
						<!--评价类型--></th>
				<th width="12%"><spring:message code="sys.affirm.title.duty" />
					<!--职责--></th>
				<th width="6%"><spring:message
						code="inct.salesman.evaluation.weight" />
					<!--权重--></th>
				<th width="6%"><spring:message
						code="sys.postManage.title.ifUsed" />
					<!--是否使用--></th>
				<th width="8%"><spring:message
						code="ar.excelexport.title.createempid" />
					<!--注册人--></th>
				<th width="10%"><spring:message
						code="ar.excelexport.title.createdate" />
					<!--注册时间--></th>
				<th width="8%"><spring:message code="inct.salesman.updateBy" />
					<!--更新人--></th>
				<th width="10%"><spring:message code="inct.salesman.updateTime" />
					<!--更新时间--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${MDATA}" var="mdata" varStatus="i">
				<tr target="evaluationItem"
					rel="SALES_EVAL_TYPE=${mdata.CATEGORY}&EV_TP_CD=${mdata.EV_TP_CD}&JOB_POSI_CD=${mdata.JOB_POSI_CD}">
					<td class='td_center'>${mdata.CATEGORY}</td>
					<td>${mdata.CATEGORY_NM}</td>
					<td>${mdata.EV_TP_NM}</td>
					<td>${mdata.JOB_POSI_NM}</td>
					<td class='td_right'
						onclick="editEvaluationItemWeight('SALES_EVAL_TYPE=${mdata.CATEGORY}&EV_TP_CD=${mdata.EV_TP_CD}&JOB_POSI_CD=${mdata.JOB_POSI_CD}')">
						${mdata.WEIGHT}</td>
					<td class='td_center'>${mdata.USE_YN}</td>
					<td class='td_center'>${mdata.RGST_USER}</td>
					<td class='td_center'>${mdata.RGST_DTIME}</td>
					<td class='td_center'>${mdata.UPDT_USER}</td>
					<td class='td_center'>${mdata.UPDT_DTIME}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/inct/salesman/viewEvaluationItemWeightList"
		var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	<form id="excelExportForm_se0101" name="excelExportForm_se0101"
		method="post">
		<input type="hidden" id="password" name="password" value="" /> <input
			type="hidden" id="SALES_EVAL_TYPE" name="SALES_EVAL_TYPE" value="" />
		<input type="hidden" id="EV_TP_CD" name="EV_TP_CD" value="" /> <input
			type="hidden" id="JOB_POSI_CD" name="JOB_POSI_CD" value="" />
	</form>
</div>
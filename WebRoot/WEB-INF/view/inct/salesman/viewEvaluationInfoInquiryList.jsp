<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	//excel导出
	function exportEvaluationInfoInquiry() {
		var payAreaCd = document.searchForm_se0107_aa.seach_PAY_AREA_NM.value;
		var sform = document.getElementById("searchForm_se0107_aa");
		alertMsg
				.confirm(
						"Do you want to export?",
						{
							okCall : function() {
								//用于excel导出的表单参数处理
								var eForm = document
										.getElementById("excelExportForm_se0107");
								document.getElementById("se0107Link").innerHTML = "EXCEL密码设置";
								eForm.PAY_AREA_CD.value = sform.seach_PAY_AREA_CD.value;
								eForm.JOB_POSI_CD.value = sform.seach_JOB_POSI_CD.value;
								eForm.EV_TP_CD.value = sform.seach_EV_TP_CD.value;
								eForm.YEAR.value = sform.seach_YEAR.value;
								eForm.QUARTER.value = sform.seach_QUARTER.value;
								eForm.EMPNO.value = sform.seach_EMPID.value;
								eForm.ResultType.value = sform.seach_ResultType.value;
								$("#importExcelDialog_se0107")
										.attr(
												'href',
												"/sys/encryptExcel"
														+ "?exportFunName=/inct/salesman/viewEvaluationInfoInquiryListExcel"
														+ "&navTabId=se0107"
														+ "&formId=excelExportForm_se0107");
								$("#importExcelDialog_se0107").attr('width',
										"300");
								$("#importExcelDialog_se0107").attr('height',
										"150");
								$("#importExcelDialog_se0107").click();
							}
						});
	}
</script>
<div class="pageHeader">
	<a id="importExcelDialog_se0107" href="#" target="dialog" mask="true"><span
		id="se0107Link" style="display: none"></span></a>
	<form name="searchForm_se0107_aa" id="searchForm_se0107_aa"
		onsubmit="return navTabSearch(this);"
		action="/inct/salesman/viewEvaluationInfoInquiryList" method="post"
		rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="inct.salesman.yearNSeason" /> <!--年/季度-->：</td>
					<td><ait:date yearName="seach_YEAR" yearSelected="${searchMap.YEAR}"
							yearPlus="10" /> <select name="seach_QUARTER">
							<option value="1" <c:if test="${searchMap.QUARTER eq '1' }">selected</c:if>>1</option>
							<option value="2" <c:if test="${searchMap.QUARTER eq '2' }">selected</c:if>>2</option>
							<option value="3" <c:if test="${searchMap.QUARTER eq '3' }">selected</c:if>>3</option>
							<option value="4" <c:if test="${searchMap.QUARTER eq '4' }">selected</c:if>>4</option>
					</select></td>
					<td><spring:message code="inct.salesman.daqu" /> <!--大区-->：</td>
					<td>
						<ait:deptTreeMulti id="seach_PAY_AREA_CD" name="seach_PAY_AREA_NM" limit="pa" level="2" 
						selected="${searchMap.PAY_AREA_CD}"
						selectedNm="${searchMap.PAY_AREA_NM}" />	
					</td>
					<td><spring:message code="inct.salesman.evaluationType" /> <!--评价类型-->：</td>
					<td><ait:ComboSyCodeDescByCpnyID id="seach_EV_TP_CD"
							name="seach_EV_TP_CD" parentNo="14895" selected="${searchMap.EV_TP_CD}"
							cnpyID="${interCpnyID}" limit="all" /></td>
				</tr>
				<tr>
					<td><spring:message code="sys.affirm.title.duty" /> <!--职责-->：</td>
					<td><ait:ComboJobPositionByCpnyIDTag id="seach_JOB_POSI_CD"
							name="seach_JOB_POSI_CD" parentNo="215918"
							selected="${searchMap.JOB_POSI_CD}" cnpyID="${interCpnyID}" limit="all" />
					</td>
					<td><spring:message code="inct.salesman.empNoNName" /> <!--社号/姓名-->：</td>
					<td>
						<input id="seach_EMPID" name="dwz.person.empId" type="text" value="${searchMap.EMPNO}" lookupGroup="person"/>
						<input id="seach_PERSON_ID" name="dwz.person.personId" type="hidden" value="" readOnly lookupGroup="person"/>
						<a class="btnLook" style="float:right;" href="/ar/attendanceSettings/viewKeeperList?pageNum=1" width="900" height="400" lookupGroup="person"><!-- 查找带回 --><spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a>
					</td>
					<td><spring:message code="inct.salesman.eval.result" /> <!--评价结果-->：
					
					</td>
					<td><select name="seach_ResultType" id="seach_ResultType"
						width="100">
							<option value="Total"
								<c:if test="${searchMap.ResultType eq 'Total' }" > selected </c:if>><spring:message
									code="inct.salesman.eval.summary" /></option>
							<option value="Detail"
								<c:if test="${searchMap.ResultType eq 'Detail' }"> selected </c:if>><spring:message
									code="inct.salesman.eval.detail" /></option>
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
						<button type="button" id="btnExcelExport_se0107" name="btnExcelExport_se0107"
							onclick="exportEvaluationInfoInquiry(this,'${request_navTabId}')">
							<spring:message code="inct.salesman.downloadToExcel" />
							<!--excel导出-->
						</button>
					</div>
				</div></li>
		</ul>
	</div>
	<c:if test="${searchMap.ResultType == 'Total'}">
		<table class="table" width="100%" layoutH="231">
			<thead>
				<tr>
					<th width="5%"><spring:message code="inct.salesman.year" /> <!--年--></th>
					<th width="5%"><spring:message code="inct.salesman.Season" />
						<!--季度--></th>
					<th width="10%"><spring:message code="inct.salesman.daqu" />
						<!--大区--></th>
					<th width="10%"><spring:message
							code="inct.salesman.evaluationType" /> <!--评价类型--></th>
					<th width="10%"><spring:message code="sys.affirm.title.duty" />
						<!--职责--></th>
					<th width="6%"><spring:message code="inct.salesman.empNo" />
						<!--社号--></th>
					<th width="10%"><spring:message code="inct.salesman.empName" />
						<!--员工姓名--></th>
					<th width="5%"><spring:message
							code="inct.salesman.eval.totalScore" /> <!--评价总分--></th>
					<th width="5%"><spring:message
							code="inct.salesman.eval.bonusRatio" /> <!--奖金系数--></th>
					<th width="20%"><spring:message
							code="inct.salesman.eval.description" /> <!--评价说明--></th>
					<th width="7%">创建时间</th>
					<th width="7%">创建人</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${MDATA}" var="mdata" varStatus="i">
					<tr>
						<td class='td_center'>${mdata.YYYY}</td>
						<td class='td_center'>${mdata.QUARTER}</td>
						<td>${mdata.PAY_AREA_NM}</td>
						<td>${mdata.EV_TP_NM}</td>
						<td>${mdata.JOB_POSI_NM}</td>
						<td class='td_center'>${mdata.EMPNO}</td>
						<td class='td_center'>${mdata.EMPNM}</td>
						<td class='td_right'>${mdata.EVAL_VALUE}</td>
						<td class='td_right'>${mdata.BONUS_RATIO}</td>
						<td>${mdata.REMARK}</td>
						<td class='td_center'>${mdata.RGST_DTIME}</td>
						<td class='td_center'>${mdata.RGST_USER}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
	<c:if test="${ResultType == 'Detail'}">
		<table class="table" width="100%" layoutH="231">
			<thead>
				<tr>
					<th width="3%"><spring:message code="inct.salesman.year" /> <!--年--></th>
					<th width="3%"><spring:message code="inct.salesman.Season" />
						<!--季度--></th>
					<th width="5%"><spring:message code="inct.salesman.daqu" /> <!--大区--></th>
					<th width="6%"><spring:message code="inct.salesman.evaluationType" />
						<!--评价类型--></th>
					<th width="5%"><spring:message code="sys.affirm.title.duty" />
						<!--职责--></th>
					<th width="5%"><spring:message code="inct.salesman.empNo" />
						<!--社号--></th>
					<th width="4%"><spring:message code="inct.salesman.empName" />
						<!--员工姓名--></th>
					<th width="6%"><spring:message
							code="inct.salesman.evaluationItemType" /> <!--评价项目--></th>
					<th width="4%"><spring:message
							code="inct.salesman.eval.leftCurrent" /> <!--左侧当期--></th>
					<th width="4%"><spring:message
							code="inct.salesman.eval.leftLast" /> <!--左侧同期--></th>
					<th width="4%"><spring:message
							code="inct.salesman.eval.itemRatio.left" /> <!--左侧值--></th>
					<th width="4%"><spring:message
							code="inct.salesman.eval.topCurrent" /> <!--顶层当期--></th>
					<th width="4%"><spring:message
							code="inct.salesman.eval.topLast" /> <!--顶层同期--></th>
					<th width="4%"><spring:message
							code="inct.salesman.eval.itemRatio.top" /> <!--顶层值--></th>
					<th width="3%"><spring:message
							code="inct.salesman.evaluation.weight" /> <!--权重--></th>
					<th width="3%"><spring:message
							code="inct.salesman.eval.itemRatio.Ratio" /> <!--系数--></th>
					<th width="4%"><spring:message
							code="inct.salesman.eval.totalScore" /> <!--评价总分--></th>
					<th width="4%"><spring:message
							code="inct.salesman.eval.bonusRatio" /> <!--奖金系数--></th>
					<th width="7%"><spring:message
							code="inct.salesman.eval.description" /> <!--评价说明--></th>
					<th width="7%">创建时间</th>
					<th width="7%">创建人</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${MDATA}" var="mdata" varStatus="i">
					<tr>
						<td class='td_center'>${mdata.YYYY}</td>
						<td class='td_center'>${mdata.QUARTER}</td>
						<td>${mdata.PAY_AREA_NM}</td>
						<td>${mdata.EV_TP_NM}</td>
						<td>${mdata.JOB_POSI_NM}</td>
						<td class='td_center'>${mdata.EMPNO}</td>
						<td class='td_center'>${mdata.EMPNM}</td>
						<td>${mdata.CATEGORY_NM}</td>
						<td class='td_right'>${mdata.LEFT_CURRENT}</td>
						<td class='td_right'>${mdata.LEFT_LAST}</td>
						<td class='td_right'>${mdata.LEFT_VALUE}</td>
						<td class='td_right'>${mdata.TOP_CURRENT}</td>
						<td class='td_right'>${mdata.TOP_LAST}</td>
						<td class='td_right'>${mdata.TOP_VALUE}</td>
						<td class='td_right'>${mdata.WEIGHT}</td>
						<td class='td_right'>${mdata.RATIO}</td>
						<td class='td_right'>${mdata.EVAL_VALUE}</td>
						<td class='td_right'>${mdata.BONUS_RATIO}</td>
						<td>${mdata.REMARK}</td>
						<td class='td_center'>${mdata.RGST_DTIME}</td>
						<td class='td_center'>${mdata.RGST_USER}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
	<c:set value="/inct/salesman/viewEvaluationInfoInquiryList"
		var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	<form id="excelExportForm_se0107" name="excelExportForm_se0107"
		method="post">
		<input type="hidden" id="password" name="password" value="" /> <input
			type="hidden" id="PAY_AREA_CD" name="PAY_AREA_CD" value="" /> <input
			type="hidden" id="EV_TP_CD" name="EV_TP_CD" value="" /> <input
			type="hidden" id="JOB_POSI_CD" name="JOB_POSI_CD" value="" /> <input
			type="hidden" id="YEAR" name="YEAR" value="" /> <input type="hidden"
			id="QUARTER" name="QUARTER" value="" /> <input type="hidden"
			id="EMPNO" name="EMPNO" value="" /> <input type="hidden"
			id="ResultType" name="ResultType" value="" />
	</form>
</div>
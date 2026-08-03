<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>
	//excel导出
	function exportEvaluationResultForPersonal() {
		var sform = document.getElementById("searchForm_se0105_aa");
		alertMsg.confirm("Do you want to export?", {
			okCall : function() {
				//用于excel导出的表单参数处理
				var eForm = document.getElementById("excelExportForm_se0105"); 
				document.getElementById("se0105Link").innerHTML = "EXCEL密码设置";
				eForm.YEAR.value 			= sform.seach_YEAR.value;
				eForm.QUARTER.value 		= sform.seach_QUARTER.value;
				eForm.EMPNO.value 			= sform.seach_EMPNO.value;
				eForm.ResultType.value 		= sform.seach_ResultType.value;
				$("#importExcelDialog_se0105").attr('href', "/sys/encryptExcel"
						+"?exportFunName=/inct/salesman/viewEvaluationResultForPersonalInfoExcel"
						+"&navTabId=se0105"
						+"&formId=excelExportForm_se0105");
				$("#importExcelDialog_se0105").attr('width', "300");
				$("#importExcelDialog_se0105").attr('height', "150");
				$("#importExcelDialog_se0105").click();
			}
		});
	}
</script>
<div class="pageHeader">
<a id="importExcelDialog_se0105" href="#" target="dialog" mask="true"><span
		id="se0105Link" style="display: none"></span></a> 
	<form name="searchForm_se0105_aa" id="searchForm_se0105_aa"
		onsubmit="return navTabSearch(this);"
		action="/inct/salesman/viewEvaluationResultForPersonalInfo"
		method="post">
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
					<td><spring:message code="inct.salesman.empNoNName" /> <!--社号/姓名-->：</td>
					<td>${searchMap.EMPNO}&nbsp;/&nbsp;${searchMap.EMPNM} 
						<input type="hidden" name="seach_EMPNO" value="${searchMap.EMPNO}" />
						<input type="hidden" name="seach_EMPNM" value="${searchMap.EMPNM}" />
					</td>
					<td><spring:message code="inct.salesman.eval.result" /> <!--评价结果-->：</td>
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
	<c:set
		value="javascript:exportEvaluationResultForPersonal();"
		var="excelD_Url" />
	<%@ include file="/WEB-INF/view/inc/includeExcelButton.jsp"%>

	<c:if test="${searchMap.ResultType == 'Total'}">
		<table class="table" width="100%" layoutH="206">
			<thead>
				<tr>
					<th width="1%"><spring:message code="inct.salesman.year" /> <!--年--></th>
					<th width="1%"><spring:message code="inct.salesman.Season" />
						<!--季度--></th>
					<th width="2%"><spring:message code="inct.salesman.daqu" /> <!--大区--></th>
					<th width="2%"><spring:message code="inct.salesman.evaluationType" />
						<!--评价类型--></th>
					<th width="2%"><spring:message code="sys.affirm.title.duty" />
						<!--职责--></th>
					<th width="2%"><spring:message code="inct.salesman.empNo" />
						<!--社号--></th>
					<th width="2%"><spring:message code="inct.salesman.empName" />
						<!--员工姓名--></th>
					<th width="2%"><spring:message
							code="inct.salesman.eval.totalScore" /> <!--评价总分--></th>
					<th width="2%"><spring:message
							code="inct.salesman.eval.bonusRatio" /> <!--奖金系数--></th>
					<th width="9%"><spring:message
							code="inct.salesman.eval.description" /> <!--评价说明--></th>
				</tr>
			</thead>
			<tbody>
				<c:set value="${personalEvalResult}" var="evalRow" />
				<tr>
					<td class='td_center'>${evalRow.YYYY}</td>
					<td class='td_center'>${evalRow.QUARTER}</td>
					<td>${evalRow.PAY_AREA_NM}</td>
					<td>${evalRow.EV_TP_NM}</td>
					<td>${evalRow.JOB_POSI_NM}</td>
					<td class='td_center'>${evalRow.EMPNO}</td>
					<td class='td_center'>${evalRow.EMPNM}</td>
					<td class='td_right'>${evalRow.EVAL_VALUE}</td>
					<td class='td_right'>${evalRow.BONUS_RATIO}</td>
					<td>${evalRow.REMARK}</td>
				</tr>
			</tbody>
		</table>
	</c:if>
	<c:if test="${searchMap.ResultType == 'Detail'}">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table center_table">
			<c:set value="${personalEvalResult}" var="evalRow" />
			<tr>
				<td class="th_title"><spring:message code="inct.salesman.year" />
					<!--年--></td>
				<td class="th_title"><spring:message
						code="inct.salesman.Season" /> <!--季度--></td>
				<td class="th_title"><spring:message code="inct.salesman.empNo" />
					<!--社号--></td>
				<td class="th_title"><spring:message
						code="inct.salesman.empName" /> <!--员工姓名--></td>
				<td class="th_title" colspan="2"><spring:message
						code="inct.salesman.daqu" /> <!--大区--></td>
				<td class="th_title" colspan="2"><spring:message
						code="inct.salesman.evaluationType" /> <!--评价类型--></td>
				<td class="th_title" colspan="4"><spring:message
						code="sys.affirm.title.duty" /> <!--职责--></td>
			</tr>
			<tr>
				<td height="15" class="td_type">${evalRow.YYYY}</td>
				<td class="td_type">${evalRow.QUARTER}</td>
				<td class="td_type">${evalRow.EMPNO}</td>
				<td class="td_type">${evalRow.EMPNM}</td>
				<td class="td_type" colspan="2">${evalRow.PAY_AREA_NM}</td>
				<td class="td_type" colspan="2">${evalRow.EV_TP_NM}</td>
				<td class="td_type" colspan="4">${evalRow.JOB_POSI_NM}</td>
			</tr>
			<tr>
				<td width="2%" colspan="3" class="th_title"><spring:message
						code="inct.salesman.eval.personal.saleAchievementA" /> <!--卖出实绩(A)--></td>
				<td width="2%" colspan="3" class="th_title"><spring:message
						code="inct.salesman.eval.personal.receivePayBF" /> <!--收款(B、F)--></td>
				<td width="2%" colspan="3" class="th_title"><spring:message
						code="inct.salesman.eval.personal.limitProfitCE" /> <!--限界利润(C、E)--></td>
				<td width="2%" colspan="3" class="th_title"><spring:message
						code="inct.salesman.eval.personal.MSRatioD" /> <!--MS 率(D)--></td>
			</tr>
			<tr>
				<td width="2%" class="td_title"><spring:message
						code="inct.salesman.eval.personal.currentValue" /> <!--当期--></td>
				<td width="2%" class="td_title"><spring:message
						code="inct.salesman.eval.personal.lastValue" /> <!--去年同期--></td>
				<td width="2%" class="td_title"><spring:message
						code="inct.salesman.eval.personal.target" /> <!--目标--></td>
				<td width="2%" class="td_title"><spring:message
						code="inct.salesman.eval.personal.currentValue" /> <!--当期--></td>
				<td width="2%" class="td_title"><spring:message
						code="inct.salesman.eval.personal.lastValue" /> <!--去年同期--></td>
				<td width="2%" class="td_title"><spring:message
						code="inct.salesman.eval.personal.target" /> <!--目标--></td>
				<td width="2%" class="td_title"><spring:message
						code="inct.salesman.eval.personal.currentValue" /> <!--当期--></td>
				<td width="2%" class="td_title"><spring:message
						code="inct.salesman.eval.personal.lastValue" /> <!--去年同期--></td>
				<td width="2%" class="td_title"><spring:message
						code="inct.salesman.eval.personal.target" /> <!--目标--></td>
				<td width="2%" class="td_title"><spring:message
						code="inct.salesman.eval.personal.currentValue" /> <!--当期--></td>
				<td width="3%" class="td_title" colspan=2><spring:message
						code="inct.salesman.empType" /> <!--去年同期--></td>
			</tr>
			<tr>
				<td height="15" class="td_type">${evalRow.A_CURRENT_VALUE}</td>
				<td class="td_type">${evalRow.A_LAST_VALUE}</td>
				<td class="td_type">${evalRow.A_TARGET_VALUE}</td>
				<td class="td_type">${evalRow.B_CURRENT_VALUE}</td>
				<td class="td_type">${evalRow.B_LAST_VALUE}</td>
				<td class="td_type">${evalRow.B_TARGET_VALUE}</td>
				<td class="td_type">${evalRow.C_CURRENT_VALUE}</td>
				<td class="td_type">${evalRow.C_LAST_VALUE}</td>
				<td class="td_type">${evalRow.C_TARGET_VALUE}</td>
				<td class="td_type">${evalRow.D_CURRENT_VALUE}</td>
				<td class="td_type" colspan=2>${evalRow.D_LAST_VALUE}</td>
			</tr>
			<tr>
				<td width="2%" colspan="3" class="th_title"><spring:message
						code="inct.salesman.eval.personal.highEndH" /> <!--High-end(H)--></td>
				<td width="2%" colspan="3" class="th_title"><spring:message
						code="inct.salesman.eval.personal.creditJ" /> <!--Credit(J)--></td>
				<td width="2%" colspan="6" class="th_title"><spring:message
						code="inct.salesman.eval.personal.newCustomerG" /> <!--New Customer(G)--></td>
			</tr>
			<tr>
				<td width="2%" class="td_title"><spring:message
						code="inct.salesman.eval.personal.currentValue" /> <!--当期--></td>
				<td width="2%" class="td_title"><spring:message
						code="inct.salesman.eval.personal.lastValue" /> <!--去年同期--></td>
				<td width="2%" class="td_title"><spring:message
						code="inct.salesman.eval.personal.target" /> <!--目标--></td>
				<td width="2%" class="td_title"><spring:message
						code="inct.salesman.eval.personal.overDueValue" /> <!--超期债权--></td>
				<td width="2%" class="td_title"><spring:message
						code="inct.salesman.eval.personal.longTermValue" /> <!--长期债权--></td>
				<td width="2%" class="td_title"><spring:message
						code="inct.salesman.eval.personal.totalValue" /> <!--总债权--></td>
				<td width="2%" class="td_title" colspan=2><spring:message
						code="inct.salesman.eval.personal.geTargetCurrent" /> <!--下部流通(目标/实绩GE)--></td>
				<td width="2%" class="td_title" colspan=2><spring:message
						code="inct.salesman.eval.personal.gaTargetCurrent" /> <!--专卖店(目标/实绩GA)--></td>
				<td width="3%" class="td_title" colspan=2><spring:message
						code="inct.salesman.eval.personal.gcTargetCurrent" /> <!--店中店(目标/实绩GC)--></td>
			</tr>
			<tr>
				<td height="15" class="td_type">${evalRow.H_CURRENT_VALUE}</td>
				<td class="td_type">${evalRow.H_LAST_VALUE}</td>
				<td class="td_type">${evalRow.H_TARGET_VALUE}</td>
				<td class="td_type">${evalRow.J_OVERDUE_VALUE}</td>
				<td class="td_type">${evalRow.J_LOGNTERM_VALUE}</td>
				<td class="td_type">${evalRow.J_TOTAL_VALUE}</td>
				<td class="td_type">${evalRow.GE_TARGET_VALUE}</td>
				<td class="td_type">${evalRow.GE_CURRENT_VALUE}</td>
				<td class="td_type">${evalRow.GA_TARGET_VALUE}</td>
				<td class="td_type">${evalRow.GA_CURRENT_VALUE}</td>
				<td class="td_type">${evalRow.GC_TARGET_VALUE}</td>
				<td class="td_type">${evalRow.GC_CURRENT_VALUE}</td>
			</tr>
			<tr>
				<td width="2%" colspan="2" class="th_title"><spring:message
						code="inct.salesman.eval.personal.evalScore" /><!--评价总分  --></td>
				<td width="2%" colspan="2" class="th_title"><spring:message
						code="inct.salesman.eval.personal.bonusRatio" /><!--奖金系数 --></td>
				<td width="2%" colspan="8" class="th_title"><spring:message
						code="inct.salesman.eval.personal.evalDesc" /><!--评价说明 --></td>
			</tr>
			<tr>
				<td height="15" colspan="2" class="td_type">${evalRow.EVAL_VALUE}</td>
				<td height="15" colspan="2" class="td_type">${evalRow.BONUS_RATIO}</td>
				<td height="15" colspan="8" class="td_type">${evalRow.REMARK}</td>
			</tr>
		</table>
	</c:if>
<form id="excelExportForm_se0105" name="excelExportForm_se0105" method="post">
	<input type="hidden" id="password" 			name="password" 		value="" />
	<input type="hidden" id="YEAR" 				name="YEAR" 			value="" />
	<input type="hidden" id="QUARTER" 			name="QUARTER" 			value="" />
	<input type="hidden" id="EMPNO" 			name="EMPNO" 			value="" />
	<input type="hidden" id="ResultType" 		name="ResultType" 		value="" />
</form>
</div>

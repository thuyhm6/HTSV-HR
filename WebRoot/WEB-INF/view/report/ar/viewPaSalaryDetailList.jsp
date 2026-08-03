<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

var ait;
var naTa;
function exportpayPayFSE(a, navTabId) {

	ait = a;
	naTa = navTabId;
	var PAY_SCHEDULE_NOs = $("#PAY_SCHEDULE_NO_viewPaSalarySumList").attr(
			"value");
	var values = PAY_SCHEDULE_NOs.split(",");

	$("#PAY_DATE_viewPaSalarySumList").attr("value", values[1]);
	$("#SALARY_DISTIN_viewPaSalarySumList").attr("value", values[2]);
	$("#PAY_SCHEDULE_NO_viewPaSalarySum").attr("value", values[0]);

	exportpayPayFSE2();

}

function exportpayPayFSE2() {
	var a = ait;
	var navTabId = naTa;

	var suffix = $('input[name="suffix"]:checked').val();

	var report = $('input[name="reportType"]:checked').val();

	if (suffix == 'xls') {// --ok

		  $("#onlyForm").attr("action", "/report/pac04/exportDatilyReport2");
		$("#onlyForm").submit();
	} else if (suffix == 'pdf' && report == 'save') {
		$("#onlyForm").attr("target", "");
		$("#onlyForm").attr("action", "/report/pac04/exportDatilyReport");
		$("#onlyForm").submit();
	} else if (suffix == 'html' || report == 'display') {
		/**新页面显示*/
		//$("#onlyForm").attr("action","/report/pac04/exportDatilyHtmlReport3");
		//$("#onlyForm").attr("target","result");
		//$("#onlyForm").submit();
		var options = {
			mask : true,
			width : 1000,
			height : 600,
			drawable : true,
			resizable : true
		};
		$.pdialog.open("/report/common/showPDFPop?" + "actionUrl="
				+ "/report/pac04/exportDatilyHtmlReport", "showPDFPop",
				"正式工工资明细表", options);

	}
}
</script>
<div class="pageHeader">
	<a id="rp_payPayFSE" href="#" target="dialog" mask="true"><span
		id="rpPayPayFSELink" style="display: none"></span> </a>
	<form action="/report/pac04/exportDatilyReport" id="onlyForm"
		rel="htmlReport" method="post">
		<input type="hidden" id="reportName" name="reportName"
			value="payZhengGongDetail" />
		<input type="hidden" id="seach_CPNY_ID" name="seach_CPNY_ID"
			value="${defaultCpny}" />

		<div class="searchBar">
			<table class="searchContent">
				<tr>

					<td width="10%" style="text-align: center">
						查询日期：
					</td>
					<td width="20%">
						<input id="PAY_DATE_viewPaSalarySumList" name="PAY_DATE"
							type="hidden" value="">
						<input id="SALARY_DISTIN_viewPaSalarySumList" name="SALARY_DISTIN"
							type="hidden" value="">
						<input id="PAY_SCHEDULE_NO_viewPaSalarySum" name="PAY_SCHEDULE_NO"
							type="hidden" value="">

						<select id="PAY_SCHEDULE_NO_viewPaSalarySumList">
							<c:forEach items="${paPayScheduleList}" var="paySchedule"
								varStatus="i">
								<c:choose>
									<c:when test="${PAY_DATE == paySchedule.PAY_DATE }">
										<option
											value="${paySchedule.PAY_SCHEDULE_NO},${paySchedule.PAY_DATE},${paySchedule.SALARY_DISTIN}"
											selected="selected">
											${paySchedule.PAY_DATE }--${paySchedule.SALARY_DISTIN}
										</option>
									</c:when>
									<c:otherwise>
										<option
											value="${paySchedule.PAY_SCHEDULE_NO},${paySchedule.PAY_DATE},${paySchedule.SALARY_DISTIN}">
											${paySchedule.PAY_DATE }--${paySchedule.SALARY_DISTIN}
										</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td style="text-align: right">
						File Type：
					</td>
					<td>
						<input type="radio" name="suffix" value="pdf" checked
							onClick="result.location.href='/resources/reportFile/blank.html'" />
						pdf
						<input type="radio" name="suffix" value="xls"
							onClick="result.location.href='/resources/reportFile/blank.html'" />
						xls
						<input type="radio" name="suffix" value="html"
							onClick="result.location.href='/resources/reportFile/blank.html'" />
						html
					</td>
					<td style="text-align: right">
						Report Type：
					</td>
					<td>
						<input type="radio" name="reportType" value="display" />
						display
						<input type="radio" name="reportType" value="save" checked />
						save
					</td>
					<td colspan="3">
						&nbsp;
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button"
									onClick="exportpayPayFSE(this,'${request_navTabId}')">
									查询
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
	<form id="excelExportForm_PayPayFSE" name="excelExportForm_PayPayFSE"
		method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="CPNY_ID" name="CPNY_ID" value="" />
		<input type="hidden" id="JOB_TP" name="JOB_TP" value="" />
		<input name="PAY_DATE" type="hidden" value="2015-12-21">
		<input name="SALARY_DISTIN" type="hidden" value="正式工">
		<input name="PAY_SCHEDULE_NO" type="hidden" value="181">
		<input type="hidden" id="reportName" name="reportName"
			value="payZhengGongDetail" />
		<input type="hidden" id="suffix" name="suffix" value="" />
	</form>
	<div id="showPDFPop"></div>
</div>

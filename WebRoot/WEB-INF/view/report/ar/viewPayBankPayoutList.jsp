<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function changeDate() {

	var PAY_DATE = $("#PAY_DATE").val();
	var PAY_DATE_PRO = $("#PAY_DATE_PRO").val();
	var year = new Number(PAY_DATE.substring(0, 4));
	var month = new Number(PAY_DATE.substring(5, 7));
	var year_pro = new Number(PAY_DATE_PRO.substring(0, 4));
	var month_pro = new Number(PAY_DATE_PRO.substring(5, 7));

	var now;//
	if (month < 10) {
		$("#SEARCHDATE").val(year + "0" + month + "01");

		$("#date_now").val(year + "0" + month);
		now = year + "0" + month;
		$("#PAY_DATE2").val(PAY_DATE);
	}

	if (month >= 10) {
		$("#SEARCHDATE").val(year + "" + month + "01");

		$("#date_now").val(year + "" + month);
		now = year + "" + month;
		$("#PAY_DATE2").val(PAY_DATE);
	}
	if (month_pro < 10) {

		$("#date_pro_pro").val(year_pro + "01--" + year_pro + "0" + month_pro);
		$("#date_pro").val(year_pro + "0" + month_pro);

		$("#PAY_DATE2_PRO").val(PAY_DATE_PRO);
	}

	if (month_pro >= 10) {

		$("#date_pro_pro").val(year_pro + "01--" + year_pro + "" + month_pro);
		$("#date_pro").val(year_pro + "" + month_pro);

		$("#PAY_DATE2_PRO").val(PAY_DATE_PRO);
	}

	$("#date_after").val(year + "01--" + now);

	//职级
	var G_P = $('input[name="G_P"]:checked').val();
	$("#P_OR_G").val(G_P);
	$("#G_P").val(G_P);

}
function callProce() {
	$("#CALL_PRO").submit();

}

function navTabSearch_viewPayBankPay(form) {

	var $form = $("#CALL_PRO");
	$.ajax( {
		type : form.method || 'POST',
		url : $form.attr("action"),
		data : $form.serializeArray(),
		dataType : "json",
		cache : false,
		success : function() {
			exportpayPayFSE2();

		},
		error : DWZ.ajaxError
	});
	return false;

}
var ait;
var naTa;
function exportpayPayFSE(a, navTabId) {
	ait = a;
	naTa = navTabId;
	changeDate();
	callProce();
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
		$("#onlyForm").attr("action", "/report/pac04/exportDatilyReport2");
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
				+ "/report/pac04/exportDatilyHtmlReport3", "showPDFPop",
				"工资差异", options);

	}
}
</script>
<div class="pageHeader">
	<a id="rp_payPayFSE" href="#" target="dialog" mask="true"><span
		id="rpPayPayFSELink" style="display: none"></span> </a>
	<form action="/report/pac04/exportDatilyReport2" id="onlyForm"
		rel="htmlReport" method="post">
		<input type="hidden" id="reportName" name="reportName"
			value="payDifference" />
		<input type="hidden" id="seach_CPNY_ID" name="seach_CPNY_ID"
			value="${defaultCpny}" />
		<input type="hidden" id="SEARCHDATE" name="SEARCHDATE"
			value="20150203" />
		<input type="hidden" id="SUBREPORT_DIR" name="SUBREPORT_DIR"
			value="${SUBREPORT_DIR}" />
		<input type="hidden" id="date_pro_pro" name="date_pro_pro"
			value="201512" />
		<input type="hidden" id="date_pro" name="date_pro" value="201513" />
		<input type="hidden" id="date_now" name="date_now" value="201514" />
		<input type="hidden" id="date_after" name="date_after" value="201515" />
		<input type="hidden" id="G_P" name="G_P" value="" />
		<!-- 下面是没用年月 防止报错 -->
		<input type="hidden" id="YEAR" name="YEAR" value="2015" />
		<input type="hidden" id="MONTH" name="MONTH" value="12" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>

					<td width="10%" style="text-align: center">
						工资支付日期：
					</td>
					<td width="20%">

						<select id="PAY_DATE_PRO" name="PAY_DATE_PRO">
							<c:forEach items="${paPayScheduleList}" var="paySchedule"
								varStatus="i">
								<c:if test="${paySchedule.SALARY_DISTIN eq '正式工'}">
									<c:choose>
										<c:when test="${PAY_DATE == paySchedule.PAY_DATE }">
											<option value="${paySchedule.PAY_DATE }" selected="selected">
												${paySchedule.PAY_DATE }-${paySchedule.SALARY_DISTIN}
											</option>
										</c:when>
										<c:otherwise>
											<option value="${paySchedule.PAY_DATE}">
												${paySchedule.PAY_DATE }-${paySchedule.SALARY_DISTIN}
											</option>
										</c:otherwise>
									</c:choose>
								</c:if>
							</c:forEach>

						</select>

						<select id="PAY_DATE" name="PAY_DATE">
							<c:forEach items="${paPayScheduleList}" var="paySchedule"
								varStatus="i">
								<c:if test="${paySchedule.SALARY_DISTIN eq '正式工'}">
									<c:choose>
										<c:when test="${PAY_DATE == paySchedule.PAY_DATE }">
											<option value="${paySchedule.PAY_DATE }" selected="selected">
												${paySchedule.PAY_DATE }-${paySchedule.SALARY_DISTIN}
											</option>
										</c:when>
										<c:otherwise>
											<option value="${paySchedule.PAY_DATE}">
												${paySchedule.PAY_DATE }-${paySchedule.SALARY_DISTIN}
											</option>
										</c:otherwise>
									</c:choose>
								</c:if>
							</c:forEach>
						</select>
					</td>
					<td>
						职级
					</td>
					<td>
						<input type="radio" name="G_P" value="" />
						全部
						<input type="radio" name="G_P" value="G" />
						G职
						<input type="radio" name="G_P" value="P" />
						P职
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

	<form id="CALL_PRO" name="CALL_PRO"
		action="/pa/workManagement/callProForPayMonthDif" method="post"
		onsubmit="return navTabSearch_viewPayBankPay(this);">
		<input type="hidden" id="PAY_DATE2" name="PAY_DATE2" value="" />

		<input type="hidden" id="PAY_DATE2_PRO" name="PAY_DATE2_PRO" value="" />
		<input type="hidden" id="P_OR_G" name="P_OR_G" value="" />


	</form>


	<form id="excelExportForm_PayPayFSE" name="excelExportForm_PayPayFSE"
		method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="CPNY_ID" name="CPNY_ID" value="" />
		<input type="hidden" id="JOB_TP" name="JOB_TP" value="" />
		<input type="hidden" id="YEAR" name="YEAR" value="" />
		<input type="hidden" id="MONTH" name="MONTH" value="" />
		<input type="hidden" id="DEPTNO" name="DEPTNO" value="" />
		<input type="hidden" id="reportName" name="reportName"
			value="payPayFSE" />
		<input type="hidden" id="suffix" name="suffix" value="" />
	</form>
	<div id="showPDFPop"></div>
</div>

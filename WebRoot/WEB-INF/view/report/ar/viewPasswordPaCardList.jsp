<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function exportpayPayFSE2(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	if (suffix == 'xls') {// --ok
		document.getElementById("rpPayPayFSELink").innerHTML = "设置导出文件密码";
		var sform = document.getElementById("onlyForm");
		var eForm = document.getElementById("excelExportForm_PayPayFSE");
		eForm.reportName.value = sform.reportName.value;
		eForm.DEPTNO.value = $(":input[sysLong='seachBankDept']").val();
		eForm.suffix.value = $('input[name="suffix"]:checked').val();
		$("#rp_payPayFSE").attr('href',
				"/sys/encryptExcel"
						+ "?exportFunName=/report/pac04/exportDatilyReport2"
						+ "&navTabId=rpt0102"
						+ "&formId=excelExportForm_PayPayFSE");
		$("#rp_payPayFSE").attr('width', "300");
		$("#rp_payPayFSE").attr('height', "150");
		$("#rp_payPayFSE").click();
	} else if (suffix == 'pdf' && report == 'save') {
		$("#onlyForm").attr("target", "");
		$("#onlyForm").attr("action", "/report/pac04/exportDatilyReport");
		$("#onlyForm").submit();
	} else if (suffix == 'html' || report == 'display') {
		var options = {
			mask : true,
			width : 1000,
			height : 600,
			drawable : true,
			resizable : true
		};

		$.pdialog.open("/report/common/showPDFPop?" + "actionUrl="
				+ "/report/pac04/exportDatilyHtmlReport", "showPDFPop",
				"密码工资单", options);
	}
}
</script>
<div class="pageHeader">
	<a id="rp_payPayFSE" href="#" target="dialog" mask="true"><span
		id="rpPayPayFSELink" style="display: none"></span></a>
	<form action="/report/pac04/exportDatilyReport2" id="onlyForm"
		rel="htmlReport" method="post">
		<input type="hidden" id="reportName" name="reportName"
			value="passwprdSalary" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td  style="text-align: center">工资支付日期：</td>
					<td >
						<select id="PAY_DATE" name="PAY_DATE">
							<c:forEach items="${paPayScheduleList}" var="paySchedule"
								varStatus="i">
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
							</c:forEach>
						</select>
					</td>
					<td style="text-align: right">社号/姓名：</td>
					<td>
						
										<c:if test="${!empty paySchedule.EMPID}">
										<input id="personId" name="dwz.person.personId" value="${orgInfo.EMPID}" type="hidden" lookupGroup="person"/>
										<input name="dwz.person.empName" type="text" value="${orgInfo.EMPID}" readOnly lookupGroup="person" style="float:left;"/>
										<input name="dwz.person.empId" type="hidden" value="${orgInfo.EMPID}" readOnly lookupGroup="person" style="float:left;"/>
										<a class="btnLook" href="/ar/attendanceMintenance/viewEmpCalendarList?isEmployeement=1&firstFlag=1&limit=super&pageNum=1" lookupGroup="person">
										<spring:message code="pa.insurance.title.lookUpAndBack"/></a>
										<input name="dwz.person.empInfo" value="${paySchedule.EMPID}/${orgInfo.EMP_TYPE_NAME}/${orgInfo.EMP_OFFICE_NAME}" type="text" readOnly lookupGroup="person" size="45"/>
										</c:if>
										<c:if test="${empty paySchedule.EMPID}">
										<input name="dwz.person.empName" type="text" value="${orgInfo.EMPID}" readOnly lookupGroup="person" style="float:left;"/>
										<input name="dwz.person.empId" type="hidden" value="${orgInfo.EMPID}" readOnly lookupGroup="person" style="float:left;"/>
										<a class="btnLook" href="/ar/attendanceMintenance/viewEmpCalendarList?isEmployeement=1&firstFlag=1&limit=super&pageNum=1" lookupGroup="person">
										<spring:message code="pa.insurance.title.lookUpAndBack"/></a>
										<input name="dwz.person.empInfo" value="" type="text" readOnly lookupGroup="person" size="45"/>
										</c:if>
					</td>
				</tr>
				<tr>
					<td style="text-align: right">File Type：</td>
					<td>
						<input type="radio" name="suffix" value="pdf" checked onClick="result.location.href='/resources/reportFile/blank.html'" />
						pdf
						<input type="radio" name="suffix" value="xls" onClick="result.location.href='/resources/reportFile/blank.html'" />
						xls
						<input type="radio" name="suffix" value="html" onClick="result.location.href='/resources/reportFile/blank.html'" />
						html
					</td>
					<td style="text-align: right">Report Type：</td>
					<td>
						<input type="radio" name="reportType" value="display" />display
						<input type="radio" name="reportType" value="save" checked />save
					</td>
				</tr>
				<tr>
					<td style="text-align: right">在离职区分：</td>
					<td>
						<input type="radio" name="emp_office" value="在职" checked />在职
						<input type="radio" name="emp_office" value="离职" />离职
					</td>
					
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onClick="exportpayPayFSE2(this,'${request_navTabId}')">
									查询
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
	<form id="excelExportForm_PayPayFSE" name="excelExportForm_PayPayFSE" method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="CPNY_ID" name="CPNY_ID" value="" />
		<input type="hidden" id="JOB_TP" name="JOB_TP" value="" />
		<input type="hidden" id="YEAR" name="YEAR" value="" />
		<input type="hidden" id="MONTH" name="MONTH" value="" />
		<input type="hidden" id="DEPTNO" name="DEPTNO" value="" />
		<input type="hidden" id="reportName" name="reportName" value="passwprdSalary" />
		<input type="hidden" id="suffix" name="suffix" value="" />
	</form>
	<div id="showPDFPop"></div>
</div>

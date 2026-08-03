<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">

function searchPop(flag) {
	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel())
			.val()));

	var refreshUrl = '/hrm/report/viewHrmReportCondition?checkVal=report2';
	var refreshMenuCode = 'hr3301';
	var refreshMenuName = encodeURI(encodeURI('<spring:message code="hrm.empinfo.PERSONNEL_REPORT.Z" />'));//人事报表
	$("#searchPop", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=all&seach_KEY=' + name
			//+'&refreshUrl='+refreshUrl+'&refreshMenuCode='+refreshMenuCode+'&refreshMenuName='+refreshMenuName
			);
	if (flag == 'onkeyup')
		$("#searchPop", navTab.getCurrentPanel()).click();
}

function searchPop_Report4(flag) {
	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel())
			.val()));

	var refreshUrl = '/hrm/report/viewHrmReportCondition?checkVal=report4';
	var refreshMenuCode = 'hr3301';
	var refreshMenuName = encodeURI(encodeURI('<spring:message code="hrm.empinfo.PERSONNEL_REPORT.Z" />'));//人事报表
	$("#searchPop", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&seach_EmpOffice=15120&limit=all&seach_KEY=' + name
			//+'&refreshUrl='+refreshUrl+'&refreshMenuCode='+refreshMenuCode+'&refreshMenuName='+refreshMenuName
			);
	if (flag == 'onkeyup')
		$("#searchPop", navTab.getCurrentPanel()).click();
}

function searchPop_Report5(flag) {
	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel())
			.val()));

	var refreshUrl = '/hrm/report/viewHrmReportCondition?checkVal=report5';
	var refreshMenuCode = 'hr3301';
	var refreshMenuName = encodeURI(encodeURI('<spring:message code="hrm.empinfo.PERSONNEL_REPORT.Z" />'));//人事报表
	$("#searchPop", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=all&seach_KEY=' + name
			//+'&refreshUrl='+refreshUrl+'&refreshMenuCode='+refreshMenuCode+'&refreshMenuName='+refreshMenuName
			);
	if (flag == 'onkeyup')
		$("#searchPop", navTab.getCurrentPanel()).click();
}

function showSheet() {

	var S_DATE = $("#hrm_S_DATE").attr("value");
	var fileName = "人员日报";

	document.getElementById("reportpdf").href = '/hrm/report/payReport04?reportName=hrPersonRecordInfo&checkVal=report1'
			+ '&searchDate=' + S_DATE + '&filename=' + fileName;
}

function showSheetFour() {
	$("#onlyFormEmpOT").submit();
}

function callPD() {
	var myDate = new Date();
	var month = (myDate.getMonth() + 1);

	var day = myDate.getDate();
	if (month < 10) {

		month = '0' + '' + month;
	}

	if (day < 10) {

		day = '0' + '' + day;
	}

	var NOW_DATE = myDate.getFullYear() + '' + month + '' + day;

	var S_DATE = $("#hrm_S_DATE").attr("value");
	if (NOW_DATE != S_DATE) {
		alertMsg.info('只能生成今天的日报');
	} else {
		if (S_DATE != null && S_DATE != "") {
			$("#callPD").submit();

		} else {
			alertMsg.info("日期不能为空");
		}
	}

}
function navTabSearch_re(form) {

	var $form = $("#callPD");

	if (!$form.valid()) {
		return false;
	}

	$.ajax( {
		type : form.method || 'POST',
		url : $form.attr("action"),
		data : $form.serializeArray(),
		dataType : "json",
		cache : false,
		success : DWZ.ajaxDone,
		error : DWZ.ajaxError
	});

	return false;

}

function downloadExl(url){
		$('#hrm_report2').attr("action",url) ;
		$('#hrm_report2').attr("onsubmit",'') ;
		$('#hrm_report2').submit() ; 
	}
</script>


<script type="text/javascript">
function exportAttrieveEmpOTList(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	var sform = document.getElementById("onlyFormEmpOT");
	var JOB_TP = sform.seach_JOB_TP.value;
	var reportName = $("#reportName").val();
	var SUBSD_CD = $("#seach_SUBSD_CD").val();
	var ORG_ID = $(":input[sysLong='viewEmpInfoList_seachEmpOT']").val();
	if (suffix == 'xls') {
		document.getElementById("rp0102Link").innerHTML = "设置导出文件密码";
		var eForm = document.getElementById("excelExportForm_rp0102EmpOT");
		eForm.SUBSD_CD.value = SUBSD_CD;
		eForm.JOB_TP.value = JOB_TP;
		eForm.FROM_DATE.value = sform.seach_FROM_DATE.value;
		eForm.TO_DATE.value = sform.seach_TO_DATE.value;
		eForm.reportName.value = reportName;
		eForm.ORG_ID.value = ORG_ID;
		eForm.suffix.value = $('input[name="suffix"]:checked').val();
		$("#importExcelDialog_rp0102EmpOT").attr(
				'href',
				"/sys/encryptExcel"
						+ "?exportFunName=/report/pac04/exportDatilyReport"
						+ "&navTabId=rpt0102"
						+ "&formId=excelExportForm_rp0102EmpOT");
		$("#importExcelDialog_rp0102EmpOT").attr('width', "300");
		$("#importExcelDialog_rp0102EmpOT").attr('height', "150");
		$("#importExcelDialog_rp0102EmpOT").click();
	} else if (suffix == 'pdf' && report == 'save') {
		$("#onlyFormEmpOT").attr("target", "");
		$("#onlyFormEmpOT").attr("action", "/report/pac04/exportDatilyReport");
		$("#SUBSD_CD").attr('value', SUBSD_CD);
		$("#FROM_DATE").attr('value', sform.seach_FROM_DATE.value);
		$("#TO_DATE").attr('value', sform.seach_TO_DATE.value);
		$("#JOB_TP").attr('value', JOB_TP);
		$("#ORG_ID").attr('value', ORG_ID);
		$("#onlyFormEmpOT").submit();
	} else if (suffix == 'html' || report == 'display') {
		/**新页面显示*/
		var options = {
			mask : true,
			width : 1000,
			height : 600,
			drawable : true,
			resizable : true
		};
		//查询报表的参数 一定要用@进行连接 否则导致次条件无效
		var param = "SUBSD_CD=" + SUBSD_CD + "@JOB_TP="
				+ sform.seach_JOB_TP.value + "@seach_FROM_DATE="
				+ sform.seach_FROM_DATE.value + "@seach_TO_DATE="
				+ sform.seach_TO_DATE.value + "@reportName=" + reportName
				+ "@ORG_ID=" + ORG_ID + "@suffix="
				+ $('input[name="suffix"]:checked').val();
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?" + "params=" + param
				+ "&actionUrl=" + "/report/pac04/exportDatilyHtmlReport",
				"showPDFPop", "员工加班报表", options);
	}
}
</script>
<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
<c:if test="${checkVal eq 'report1'}">
	<div>
		<h1>
			<!-- 人事日报表 --><spring:message code="hrm.report.HR_RIBAOBIAO.Z" />
		</h1>
	</div>
</c:if>
<c:if test="${checkVal eq 'report2'}">
	<div>
		<h1>
			<!-- 统计局报表 --><spring:message code="hrm.report.TONGJIJU_BAOBIAO.Z" />
		</h1>
	</div>
</c:if>
<c:if test="${checkVal eq 'report3'}">
	<div>
		<h1>
			<!-- 劳动合同履行情况报表 --><spring:message code="hrm.report.LAODONGHETONG_LXQK.Z" />
		</h1>
	</div>
</c:if>
<c:if test="${checkVal eq 'report4'}">
	<div>
		<h1>
			<!-- 续签合同意向书 --><spring:message code="hrm.report.XUQIAN_HETONG_YIXIANGSHU.Z" />
		</h1>
	</div>
</c:if>
</c:if>

<div class="pageHeader">
	<a id="importExcelDialog_rp0102EmpOT" href="#" target="dialog"
		mask="true"><span id="rp0102Link" style="display: none"></span> </a>
	<a id="displayDept" href="#" target="ajax" rel="deptMon"></a>

	<input type="hidden" id="checkVal" value="${checkVal}">
	<c:if test="${checkVal eq 'report1'}">
		<form id="callPD" onsubmit="return navTabSearch_re(this);"
			action="/hrm/report/callPD" method="post">


			<div class="searchBar">


				<table class="searchContent">
					<tr>
						<td>
							<!-- 搜索时间： --><spring:message code="hrm.report.SEARCH_DATE.Z" />：
						</td>
						<td>
							<%-- <input type="text" id="hrm_S_DATE" name="AR_DATE"
								class="Wdate required" readonly="true" value="${ETIME}"
								onClick="WdatePicker({dateFmt:'yyyyMMdd'})" /> --%>
						<input type="text" id="YEAR" name="YEAR" class="Wdate" 
					 onClick="WdatePicker({dateFmt:'yyyy'})" value="${YEAR}"/>
						</td>
					<td style="padding-left: 50px;">
					 	<!-- 是否有试用期： --><spring:message code="hrm.report.YOUWUSHIYONGQI.Z" />
					 	</td>
					 	<td>
					 	<input type="checkbox" name="END_PROBATION_DATE" id="END_PROBATION_DATE"   value=""/>
					 	</td>
					</tr>
				</table>

				<div class="subBar">
					<ul>
							<li>
							<c:if test="${LoginUser.cpnyId eq 'SPC_SH'}">
								<a class="buttonActive"
								onclick="downloadExl('/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=224')"
								href="#"> <span><!-- 导出 --><spring:message code="org.title.INPUT" /></span> </a>
							</c:if>
						</li>
					</ul>
				</div>

			</div>
		</form>
	</c:if>

	<c:if test="${checkVal eq 'report2' or checkVal eq 'report3' or checkVal eq 'report4'}">
		<form id="hrm_report2" onsubmit="return navTabSearch(this);" action=""
			method="post">
			<input type="hidden" name="interLanguage"
				value="${LoginUser.language}">

			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							<input type="hidden" id='dwz.person.personid' name="PERSON_ID">
							<!-- 社号/姓名： -->
							<spring:message
								code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
						</td>
						<td>
							<input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"
								onkeydown="javascript:if(event.keyCode == 13)searchPop('onkeyup');" />
						</td>
						<td>
							<a class="btnLook" id="searchPop" onclick="searchPop()" href="#"
								lookupGroup="person"> </a>
						</td>
						<td colspan="3">
							<input id="dwz.person.empInfo" type="text" readonly
								lookupGroup="person" size="60" value="${empInfo}" />

							</a>
						</td>
					</tr>
					<tr>
						<td>
							<!-- 搜索时间： --><spring:message code="hrm.report.SEARCH_DATE.Z" />
						</td>
						<td>
							<input type="text" name="AR_FROM_DATE" class="Wdate required"
								readonly="true" onClick="WdatePicker({dateFmt:'dd.MM.yyyy',lang:'en'})" />
						</td>
						<td>
							--
						</td>
						<td>
							<input type="text" name="AR_TO_DATE" class="Wdate required"
								readonly="true" onClick="WdatePicker({dateFmt:'dd.MM.yyyy',lang:'en'})" />
						</td>

					</tr>

				</table>

				<div class="subBar">
					<ul>
						<li>
						<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
							<a class="buttonActive"
								<c:if test="${checkVal eq 'report2' }" >onclick="downloadExl('/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=226')"</c:if>
								<c:if test="${checkVal eq 'report3' }" >onclick="downloadExl('/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=227')"</c:if>
								<c:if test="${checkVal eq 'report4' }" >onclick="downloadExl('/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=169')"</c:if>
								href="#"> <span><!-- 导出 --><spring:message code="org.title.INPUT" /></span> </a>
						</c:if>
						<c:if test="${LoginUser.cpnyId eq 'SPC_DL'}">
							<a class="buttonActive"
								<c:if test="${checkVal eq 'report2' }" >onclick="downloadExl('/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=233')"</c:if>
								<c:if test="${checkVal eq 'report3' }" >onclick="downloadExl('/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=234')"</c:if>
								href="#"> <span><!-- 导出 --><spring:message code="org.title.INPUT" /></span> </a>
						</c:if>
						</li>
					</ul>
				</div>

			</div>
		</form>
	</c:if>
	<c:if test="${checkVal eq 'report5'}">
		<form action="/hrm/report/payReport04" id="onlyFormEmpOT"
			method="post">
			<input type="hidden" id="reportName" name="reportName"
				value="hrRenewTheContract" />
			<input type="hidden" name="filename" value="Evaluation" />
			<div class="searchBar">
				<table class="searchContent">
					<input type="hidden" name="checkVal" id="checkVal"
						value="${checkVal}" />

					<tr>
						<td>
							<input type="hidden" id='dwz.person.personid' name="PERSON_ID">
							<!-- 工号/姓名： -->
							<spring:message
								code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
						</td>
						<td>
							<input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"
								onkeydown="javascript:if(event.keyCode == 13)searchPop_Report5('onkeyup');" />
						</td>
						<td>
							<a class="btnLook" id="searchPop" onclick="searchPop_Report5()"
								href="#" lookupGroup="person"> </a>
						</td>
						<td colspan="3">
							<input id="dwz.person.empInfo" type="text" readonly
								lookupGroup="person" size="60" value="${empInfo}" />
							</a>
						</td>
					</tr>
					<tr>
						<td>
							<!-- 搜索时间： --><spring:message code="hrm.report.SEARCH_DATE.Z" />
						</td>
						<td>
							<input type="text" name=ENTER_DATE class="Wdate required"
								value="" readonly="true"
								onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})" />
						</td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li>
							<a id="reportpdf" class="button" onclick="showSheetFour();">
								<span>  <!-- pdf导出 --><spring:message code="ar.addempshift.title.pdfdaochu" /></span> </a>
						</li>
					</ul>
				</div>
			</div>
		</form>
	</c:if>
</div>
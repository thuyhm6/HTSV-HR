<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function exportPaInsuranceDetaillyDetailS(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	var sform = document.getElementById("onlyFormTYF");
	var CPNY_ID = 'TSTO';
	var EMPID = $("#seach_EMPID").val();
	var PERSON_ID = document.getElementById("dwz.person.personid").value;
	var S_DATE = $("#S_DATE").val();
	var E_DATE = $("#E_DATE").val();
	if (suffix == 'xls' && report == 'save') {
		document.getElementById("rp_PaInsuranceDetailLink").innerHTML = "设置导出文件密码";
		var eForm = document.getElementById("exportPaInsuranceDetail");
		var sform = document.getElementById("onlyFormTYF");
		eForm.EMPID.value = EMPID;
		eForm.reportName.value = reportName;
		eForm.suffix.value = $('input[name="suffix"]:checked').val();
		$("#rp_PaInsuranceDetail").attr(
				'href',
				"/sys/encryptExcel"
						+ "?exportFunName=/report/pac04/exportDatilyReport"
						+ "&navTabId=rpt0102"
						+ "&formId=exportPaInsuranceDetail");
		$("#rp_PaInsuranceDetail").attr('width', "300");
		$("#rp_PaInsuranceDetail").attr('height', "150");
		$("#rp_PaInsuranceDetail").click();
	} else if (suffix == 'pdf' && report == 'save') {
		$("#onlyFormTYF").attr("target", "");
		$("#onlyFormTYF").attr("action", "/report/pac04/exportDatilyReport");
		$("#CPNY_ID").attr('value', CPNY_ID);
		$("#EMPID").attr('value', EMPID);
		$("#onlyFormTYF").submit();
	} else if (suffix == 'html' || report == 'display') {
      
		var options = {
			mask : true,
			width : 1000,
			height : 600,
			drawable : true,
			resizable : true
		};
		//查询报表的参数 一定要用@进行连接 否则导致次条件无效
		var param = "@CPNY_ID=" + CPNY_ID + "@PERSON_ID=" + PERSON_ID
				+ "@reportName=insurdetail" + "@S_DATE=" + S_DATE+ "@E_DATE=" + E_DATE
				+ "@suffix=" + $('input[name="suffix"]:checked').val();
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?" + "params=" + param
				+ "&actionUrl=" + "/report/pac04/exportDatilyHtmlReport",
				"showPDFPop", "个人保险明细表", options);
	}
}

function searchPop(flag) {
	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel())
			.val()));

	var refreshUrl = '/report/ar/viewPaInsuranceDetailList?pageNum=1&fromPage=pa';
	var refreshMenuCode = 'pa0136';
	var refreshMenuName = encodeURI(encodeURI('工资报表'));
	$("#searchPop", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=all&seach_KEY=' + name
			//+'&refreshUrl='+refreshUrl+'&refreshMenuCode='+refreshMenuCode+'&refreshMenuName='+refreshMenuName
			);
	if (flag == 'onkeyup')
		$("#searchPop", navTab.getCurrentPanel()).click();
}
</script>


<script language=javascript>
</script>
<div class="pageHeader">
	<a id="rp_PaInsuranceDetail" href="#" target="dialog" mask="true"><span
		id="rp_PaInsuranceDetailLink" style="display: none"></span> </a>
	<a id="displayDept" href="#" target="ajax" rel="deptMon"></a>
	<form action="/report/pac04/exportDatilyReport" id="onlyFormTYF"
		rel="htmlReport" method="post">
		<input type="hidden" id="reportName" name="reportName"
			value="insurdetail" />

		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<input type="hidden" id='dwz.person.personid' name="PERSON_ID">
						<!-- 工号/姓名： -->
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
						时间区间：
					</td>
					<td>
						<input type="text" id="S_DATE" name="S_DATE" class="Wdate required"
							onClick="WdatePicker({dateFmt:'yyyyMM'})"  value="${S_DATE}"/>
					</td>
					<td>
						--
					</td>
					<td>
						<input type="text" id="E_DATE" name="E_DATE" class="Wdate required"
							value="${E_DATE}" onClick="WdatePicker({dateFmt:'yyyyMM'})" />
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
						<div>
							<div class="buttonContent">
								<a class="button"
									onClick="exportPaInsuranceDetaillyDetailS(this,'${request_navTabId}')">
									<span> 查询</span> </a>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
	<div id="deptMon" class="unitBox" width="150%">
	</div>
	<form id="exportPaInsuranceDetail" name="exportPaInsuranceDetail"
		method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="CPNY_ID" name="CPNY_ID" value="" />
		<input type="hidden" id="PAY_DATE" name="PAY_DATE" value="" />
		<input type="hidden" id="EMPID" name="EMPID" value="" />
		<input type="hidden" id="DEPTNO" name="DEPTNO" value="" />

		<input type="hidden" id="suffix" name="suffix" value="" />
	</form>
	<div id="showPDFPop"></div>
</div>

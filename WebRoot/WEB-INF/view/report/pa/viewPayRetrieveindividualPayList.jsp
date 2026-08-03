<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function exportPayRetrieveindualPay(a, navTabId) {
		var suffix = $('input[name="suffix"]:checked').val();
		var report = $('input[name="reportType"]:checked').val();
		var sform = document.getElementById("onlyForm");
		var reportName = $("#reportName").val();
		var SUBSD_CD = $("#SUBSD_CD").val();
		var ORG_ID = $(":input[sysLong='seachDeptQX']").val();
		var JOB_TP = $("#seach_JOB_TP").val();
		var POST_TYPE = $("#seach_POST_TYPE").val();
		if(SUBSD_CD == 'LGEKS'||SUBSD_CD == 'SST'||SUBSD_CD == 'LGEND' || SUBSD_CD == 'LGEHN' || SUBSD_CD == 'SST' || SUBSD_CD == 'LGETR' || SUBSD_CD == 'LGEQH'){
		    if(JOB_TP == '' || JOB_TP == null){
		        JOB_TP = '1368';
		    }
		}
		if(SUBSD_CD == 'LGEND' && JOB_TP == '211809'){
		    reportName = 'payRetrieveindividualPayLGENDT6';
		}
		if($("#type").val()=='211292'){
		    reportName = 'payRetrieveindividualPayLGENDT6';
		}
		var EMPID = $("#empId").val();
		if (suffix == 'xls') {
			document.getElementById("rePayRetrieveindualPayLGETALink").innerHTML = "设置导出文件密码";
			var eForm = document
					.getElementById("exportPayRetrieveindualPayLGETA");
			eForm.SUBSD_CD.value = SUBSD_CD;
			eForm.JOB_TP.value = JOB_TP;
			eForm.ORG_ID.value = ORG_ID;
			eForm.POST_TYPE.value = POST_TYPE;
			eForm.CurrentYear.value = sform.seach_YEAR.value;
			eForm.CurrentMonth.value = sform.seach_MONTH.value;
			eForm.ATT_MON.value = sform.seach_YEAR.value +''+ sform.seach_MONTH.value;
			eForm.reportName.value = reportName;
			eForm.EMPID.value = EMPID;
			eForm.suffix.value = $('input[name="suffix"]:checked').val();
			$("#re_PayRetrieveindualPayLGETA")
					.attr(
							'href',
							"/sys/encryptExcel"
									+ "?exportFunName=/report/pac04/exportDatilyReport2"
									+ "&navTabId=rpt0102"
									+ "&formId=exportPayRetrieveindualPayLGETA");
			$("#re_PayRetrieveindualPayLGETA").attr('width', "300");
			$("#re_PayRetrieveindualPayLGETA").attr('height', "150");
			$("#re_PayRetrieveindualPayLGETA").click();
		} else if (suffix == 'pdf' && report == 'save') {
			$("#onlyForm").attr("target", "");
			$("#onlyForm").attr("action", "/report/pac04/exportDatilyReport2");
			$("#SUBSD_CD").attr('value', SUBSD_CD);
			$("#ATT_MON").attr('value',
					sform.seach_YEAR.value + '' + sform.seach_MONTH.value);
			$("#CurrentMonth").attr('value', sform.seach_MONTH.value);
			$("#CurrentYear").attr('value', sform.seach_YEAR.value);
			$("#EMPID").attr('value', EMPID);
			$("#ORG_ID").attr('value', ORG_ID);
			$("#JOB_TP").attr('value', JOB_TP);
			$("#POST_TYPE").attr('value', POST_TYPE);
			$("#onlyForm").submit();
		}else if(suffix == 'html'|| report == 'display'){
		//dialog的参数
		var options = {mask:true, 
                    width:1000, height:600,
                    drawable:true,
                    resizable:true
                }; 
        //查询报表的参数 一定要用@进行连接 否则导致次条件无效
		var param = "SUBSD_CD="+SUBSD_CD
        +"@ATT_MON="+ sform.seach_YEAR.value + '' + sform.seach_MONTH.value  
		+"@CurrentMonth="+sform.seach_MONTH.value  
		+"@CurrentYear="+sform.seach_YEAR.value  
		+"@reportName=" + reportName  
		+"@EMPID="+ EMPID  
		+"@ORG_ID="+ ORG_ID  
		+"@JOB_TP="+ JOB_TP 
		+"@POST_TYPE="+ POST_TYPE
		+"@suffix="+ $('input[name="suffix"]:checked').val();
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac04/exportDatilyHtmlReport2"
				, "showPDFPop", "个人工资单",options);        
	}
}
	
</script>
<div class="pageHeader">
	<a id="re_PayRetrieveindualPayLGETA" href="#" target="dialog"
		mask="true"><span id="rePayRetrieveindualPayLGETALink"
		style="display: none"></span>
	</a>
	<form action="/report/pac04/exportDatilyReport2" id="onlyForm"
		rel="htmlReport" method="post">
		<input type="hidden" id="reportName" name="reportName"
			value="payRetrieveindividualPay${defaultCpny}" /> <input
			type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="${defaultCpny}" />
		<input type="hidden" id="EMPID" name="EMPID" value="" /> <input
			type="hidden" id="CurrentMonth" name="CurrentMonth" value="" /> <input
			type="hidden" id="CurrentYear" name="CurrentYear" value="" />
			<input type="hidden" id="ATT_MON" name="ATT_MON" value="" />
			<input type="hidden" id="ORG_ID" name="ORG_ID" value="" />
			<input type="hidden" id="JOB_TP" name="JOB_TP" value="" />
			<input type="hidden" id="POST_TYPE" name="POST_TYPE" value="" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td width="10%" style="text-align:center">公司： </td><td width="20%"><select id="seach_CPNY_ID" name="seach_CPNY_ID"
						disabled="disabled">
							<c:forEach items="${companyList}" var="item" varStatus="i">
								<option value="${item.CPNY_ID}"
									<c:if test="${defaultCpny eq item.CPNY_ID}">selected</c:if>>
									${item.CPNY_ID}</option>
							</c:forEach>
					</select></td>
					<td width="10%" style="text-align:center">工资月： </td><td> <select id="seach_YEAR" name="seach_YEAR"
						style="width:75px">
							<option value="">
								<%--请选择 --%>
								<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
							</option>
							<c:forEach var="i" begin="2012" end="2025" step="1">
								<option value="${i}" <c:if test="${YEAR eq i }">selected</c:if>>${i}</option>
							</c:forEach>
					</select> <select id="seach_MONTH" name="seach_MONTH">
							<option value="">
								<%--请选择--%>
								<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
							</option>
							<option value="01" <c:if test="${MONTH eq '01' }">selected</c:if>>01</option>
							<option value="02" <c:if test="${MONTH eq '02' }">selected</c:if>>02</option>
							<option value="03" <c:if test="${MONTH eq '03' }">selected</c:if>>03</option>
							<option value="04" <c:if test="${MONTH eq '04' }">selected</c:if>>04</option>
							<option value="05" <c:if test="${MONTH eq '05' }">selected</c:if>>05</option>
							<option value="06" <c:if test="${MONTH eq '06' }">selected</c:if>>06</option>
							<option value="07" <c:if test="${MONTH eq '07' }">selected</c:if>>07</option>
							<option value="08" <c:if test="${MONTH eq '08' }">selected</c:if>>08</option>
							<option value="09" <c:if test="${MONTH eq '09' }">selected</c:if>>09</option>
							<option value="10" <c:if test="${MONTH eq '10' }">selected</c:if>>10</option>
							<option value="11" <c:if test="${MONTH eq '11' }">selected</c:if>>11</option>
							<option value="12" <c:if test="${MONTH eq '12' }">selected</c:if>>12</option>
					</select></td>
					
					</tr>
					<tr>
					<c:if test="${defaultCpny ne 'LGEQD'}">
					<c:if test="${defaultCpny ne 'LGEND'}">
					<c:if test="${defaultCpny ne 'SST'}">
					<c:if test="${defaultCpny ne 'LGEHN'}">
					<c:if test="${defaultCpny ne 'LGETR'}">
					<c:if test="${defaultCpny ne 'LGEQH'}">
					<c:if test="${defaultCpny ne 'LGEYT'}">
					<c:if test="${defaultCpny ne 'LGEKS'}">
					<td width="10%" style="text-align:center">人员类型组：</td>
						<td>
							<ait:SelectEmpTypeCode name="seach_JOB_TP" id="seach_JOB_TP" selected="${EMP_TYPE_GROUP}"  limit="pa" type="group" />
					 	</td>
					</c:if>
					</c:if>
					</c:if>
					</c:if>
					</c:if>
					</c:if>
					</c:if>
					</c:if>
					<c:if test="${defaultCpny eq 'LGEKS'}">
					
					<td width="10%" style="text-align:center">人员类型：</td>
						<td>
							<ait:SelectEmpTypeCode id="seach_JOB_TP" name="seach_JOB_TP" selected="${EMP_TYPE}" cnpyID="${defaultCpny}" limit="pa"/>
					 	</td>
					</c:if>
					<c:if test="${defaultCpny eq 'LGEYT'}">
					
					<td width="10%" style="text-align:center">人员类型：</td>
						<td>
							<ait:SelectEmpTypeCode id="seach_JOB_TP" name="seach_JOB_TP" selected="${EMP_TYPE}" cnpyID="${defaultCpny}" limit="pa"/>
					 	</td>
					</c:if>
					<c:if test="${defaultCpny eq 'LGEND'}">
					
					<td width="10%" style="text-align:center">人员类型：</td>
						<td>
							<ait:SelectEmpTypeCode id="seach_JOB_TP" name="seach_JOB_TP" selected="${EMP_TYPE}" cnpyID="${defaultCpny}" limit="pa"/>
					 	</td>
					</c:if>
					<c:if test="${defaultCpny eq 'SST'}">
					
					<td width="10%" style="text-align:center">人员类型：</td>
						<td>
							<ait:SelectEmpTypeCode id="seach_JOB_TP" name="seach_JOB_TP" selected="${EMP_TYPE}" cnpyID="${defaultCpny}" limit="pa"/>
					 	</td>
					</c:if>
					<c:if test="${defaultCpny eq 'LGEHN'}">
					
					<td width="10%" style="text-align:center">人员类型：</td>
						<td>
							<ait:SelectEmpTypeCode id="seach_JOB_TP" name="seach_JOB_TP" selected="${EMP_TYPE}" cnpyID="${defaultCpny}" limit="pa"/>
					 	</td>
					</c:if>
					<c:if test="${defaultCpny eq 'LGETR'}">
					
					<td width="10%" style="text-align:center">人员类型：</td>
						<td>
							<ait:SelectEmpTypeCode id="seach_JOB_TP" name="seach_JOB_TP" selected="${EMP_TYPE}" cnpyID="${defaultCpny}" limit="pa"/>
					 	</td>
					</c:if>
					<c:if test="${defaultCpny eq 'LGEQD'}">
					
					<td width="10%" style="text-align:center">人员类型：</td>
						<td>
							<ait:SelectEmpTypeCode id="seach_JOB_TP" name="seach_JOB_TP" selected="${EMP_TYPE}" cnpyID="${defaultCpny}" limit="pa"/>
					 	</td>
					</c:if>
					
					<c:if test="${defaultCpny eq 'LGEQH'}">
					
					<td width="10%" style="text-align:center">人员类型：</td>
						<td>
							<ait:SelectEmpTypeCode id="seach_JOB_TP" name="seach_JOB_TP" selected="${EMP_TYPE}" cnpyID="${defaultCpny}" limit="pa"/>
					 	</td>
					</c:if>
					<td width="10%" style="text-align:center">部门： </td><td>
						<ait:deptList name="seach_ORG_ID" cpnyId="${defaultCpny}" id="seachDeptQX"/>
						<ait:deptTreeIcon name="seach_ORG_ID" cpnyId="${defaultCpny}" limit="pa" id="seachDeptQX" selected="${ORG_ID}"/>
					</td>
					<tr/>
					<tr>
					<td width="10%" style="text-align:center">在职状态：</td>
					<td>
						<select name="seach_POST_TYPE" id="seach_POST_TYPE">
						    <option value="0110">全部</option>
						    <option value="15119" selected="selected">在职</option>
						    <option value="15120">离职</option>
						</select>
					</td>
					<td>社号/姓名： </td><td> 
					<input id="jsonData" name="jsonData" value="" type="hidden" /> 
					<input id="empId" name="dwz.person.empId" value="" type="hidden" lookupGroup="person" /> 
					<input id="type" name="dwz.person.type" value="" type="hidden" lookupGroup="person" /> 
					<input id="personId" name="dwz.person.personId" value="" type="hidden" lookupGroup="person" /> 
					<input name="dwz.person.empId" id="empId" type="text" class="required" readOnly lookupGroup="person" /> 
					<a class="btnLook"
						href="/ar/attendanceSettings/viewKeeperList?pageNum=1&LIZHI=1&EmpOffice=15119"
						lookupGroup="person">
							<!-- 查找带回 --> <spring:message
								code="ar.alert.message.viewattendencekeeper.chazhaodaihui" />
					</a></td>
				</tr>
				<tr>
					<td style="text-align:right">File Type： <input type="radio"
						name="suffix" value="pdf"
						onClick="result.location.href='/resources/reportFile/blank.html'"
						checked />pdf <input type="radio" name="suffix" value="xls"
						onClick="result.location.href='/resources/reportFile/blank.html'" />xls
						<input type="radio" name="suffix" value="html"
						onClick="result.location.href='/resources/reportFile/blank.html'" />html
					</td>
					<td style="text-align:right">Report Type： <input type="radio"
						name="reportType" value="display"  checked/>display <input type="radio"
						name="reportType" value="save" />save</td>
					<td colspan="3">&nbsp;</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button"
									onClick="exportPayRetrieveindualPay(this,'${request_navTabId}')">
									查询</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</form>
	<form id="exportPayRetrieveindualPayLGETA"
		name="exportPayRetrieveindualPayLGETA" method="post">
		<input type="hidden" id="password" name="password" value="" /> <input
			type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="${defaultCpny}" />
		<input type="hidden" id="ATT_MON" name="ATT_MON" value="" /> <input
			type="hidden" id="CurrentMonth" name="CurrentMonth" value="" /> <input
			type="hidden" id="CurrentYear" name="CurrentYear" value="" /> <input
			type="hidden" id="EMPID" name="EMPID" value="" /> 
			<input type="hidden" id="ORG_ID" name="ORG_ID" value="" />
			<input type="hidden" id="JOB_TP" name="JOB_TP" value="" />
			<input type="hidden" id="POST_TYPE" name="POST_TYPE" value="" />
			<input
			type="hidden" id="reportName" name="reportName"
			value="payRetrieveindividualPay${defaultCpny}" /> <input
			type="hidden" id="suffix" name="suffix" value="" />
	</form>
	<div id="showPDFPop"></div>
</div>

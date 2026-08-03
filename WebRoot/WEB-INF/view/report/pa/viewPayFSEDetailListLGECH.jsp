<%@ page contentType="text/html; charset=UTF-8" language="java"errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function exportPayFSEDetailList(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	if(suffix == 'xls'){
		document.getElementById("rpPayFSEDetailListLGECHLink").innerHTML = "设置导出文件密码";
		var sform = document.getElementById("onlyForm");
		document.getElementById("onlyForm").reportName.value = 'payPayFSEDetailListLGECH';
		var eForm = document.getElementById("exportPayFSEDetailListLGECH");
		eForm.CPNY_ID.value	= sform.seach_CPNY_ID.value;
		//eForm.JOB_TP.value 		= sform.seach_JOB_TP.value;
		eForm.YEAR.value 		= sform.seach_YEAR.value;
		eForm.MONTH.value 		= sform.seach_MONTH.value;
		eForm.DEPTNO.value = sform.seach_DEPTNO.value;
		eForm.reportName.value 		= sform.reportName.value;
		eForm.suffix.value 		= $('input[name="suffix"]:checked').val();
		$("#rp_PayFSEDetailListLGECH").attr('href', "/sys/encryptExcel"
				+"?exportFunName=/report/pac04/exportDatilyReport2"
				+"&navTabId=rpt0102"
				+"&formId=exportPayFSEDetailListLGECH");
		$("#rp_PayFSEDetailListLGECH").attr('width', "300");
		$("#rp_PayFSEDetailListLGECH").attr('height', "150");
		$("#rp_PayFSEDetailListLGECH").click();
	}else if(suffix == 'pdf'  && report == 'save'){
		document.getElementById("onlyForm").reportName.value = 'payPayFSEDetailListLGECH';
		$("#onlyForm").attr("action","/report/pac04/exportDatilyReport2");
		$("#onlyForm").submit();
	}else if(suffix == 'html' || report == 'display'){
		document.getElementById("onlyForm").reportName.value = 'payPayFSEDetailListLGECH';
		/**新页面显示*/
		//$("#onlyForm").attr("action","/report/pac04/exportDatilyHtmlReport3");
		//$("#onlyForm").attr("target","result");
		//$("#onlyForm").submit();

		var options = {mask:true, 
                width:1000, height:600,
                drawable:true,
                resizable:true
            }; 

		$.pdialog.open("/report/common/showPDFPop?"
                +"actionUrl="+"/report/pac04/exportDatilyHtmlReport3"
				, "showPDFPop", "FSE详细报表",options);
		
	}
}

function exportPayFSEDetailListOnlyLgech(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	if(suffix == 'xls'){
		document.getElementById("rpPayFSEDetailListLGECHLink").innerHTML = "设置导出文件密码";
		var sform = document.getElementById("onlyForm");
		document.getElementById("onlyForm").reportName.value = 'payPayFSEDetailListLGECHOnly';
		var eForm = document.getElementById("exportPayFSEDetailListLGECHOnly");
		eForm.CPNY_ID.value	= sform.seach_CPNY_ID.value;
		//eForm.JOB_TP.value 		= sform.seach_JOB_TP.value;
		eForm.YEAR.value 		= sform.seach_YEAR.value;
		eForm.MONTH.value 		= sform.seach_MONTH.value;
		eForm.DEPTNO.value = sform.seach_DEPTNO.value;
		eForm.suffix.value 		= $('input[name="suffix"]:checked').val();
		$("#rp_PayFSEDetailListLGECH").attr('href', "/sys/encryptExcel"
				+"?exportFunName=/report/pac04/exportDatilyReport2"
				+"&navTabId=rpt0102"
				+"&formId=exportPayFSEDetailListLGECHOnly");
		$("#rp_PayFSEDetailListLGECH").attr('width', "300");
		$("#rp_PayFSEDetailListLGECH").attr('height', "150");
		$("#rp_PayFSEDetailListLGECH").click();
	}else if(suffix == 'pdf'  && report == 'save'){
		document.getElementById("onlyForm").reportName.value = 'payPayFSEDetailListLGECHOnly';
		$("#onlyForm").attr("action","/report/pac04/exportDatilyReport2");
		$("#onlyForm").submit();
	}else if(suffix == 'html' || report == 'display'){
		document.getElementById("onlyForm").reportName.value = 'payPayFSEDetailListLGECHOnly';
		/**新页面显示*/
		//$("#onlyForm").attr("action","/report/pac04/exportDatilyHtmlReport3");
		//$("#onlyForm").attr("target","result");
		//$("#onlyForm").submit();

		var options = {mask:true, 
                width:1000, height:600,
                drawable:true,
                resizable:true
            }; 

		$.pdialog.open("/report/common/showPDFPop?"
                +"actionUrl="+"/report/pac04/exportDatilyHtmlReport3"
				, "showPDFPop", "FSE详细报表",options);
		
	}
}
</script>
<div class="pageHeader">
	<a id="rp_PayFSEDetailListLGECH" href="#" target="dialog" mask="true"><span
		id="rpPayFSEDetailListLGECHLink" style="display: none"></span></a> 
	<form action="/report/pac04/exportDatilyReport2" id="onlyForm" rel="htmlReport" method="post">
		<input type="hidden" id="reportName" name="reportName" value="payPayFSEDetailListLGECH" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td width="10%" style="text-align:right">公司：</td>
					<td width="20%">
						<select id="seach_CPNY_ID" name="seach_CPNY_ID"  disabled="disabled">
							<c:forEach items="${companyList}" var="item" varStatus="i">
								<option value="${item.CPNY_ID}"
									<c:if test="${defaultCpny eq item.CPNY_ID}">selected</c:if>>
									${item.CPNY_ID}
								</option>
							</c:forEach>
						</select>
					</td>
					<td width="10%" style="text-align:right">工资结算单位：</td>
					<td width="20%">
						<ait:deptTree name="seach_DEPTNO" limit="pa" selected="${DEPTNO}" />
					</td>
					<td width="10%" style="text-align:right">工资月：</td>
					<td width="20%">
						<select id="seach_YEAR" name="seach_YEAR" style="width:75px">
					    	<option value=""><%--请选择 --%>
					    		<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/>
					    	</option>
							<c:forEach var="i" begin="2014" end="2020" step="1"> 
						    	<option value="${i}" <c:if test="${YEAR eq i }">selected</c:if> >${i}</option>
						    </c:forEach> 
						 </select>
						<select id="seach_MONTH" name="seach_MONTH" >
							<option value=""><%--请选择--%>
								<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/>
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
						</select>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td style="text-align:right">File Type：</td>
					<td>
						<input type="radio" name="suffix" value="pdf" checked onClick="result.location.href='/resources/reportFile/blank.html'"/>pdf
	                    <input type="radio" name="suffix" value="xls" onClick="result.location.href='/resources/reportFile/blank.html'"/>xls
	                    <input type="radio" name="suffix" value="html" onClick="result.location.href='/resources/reportFile/blank.html'"/>html
					</td>
					<td style="text-align:right">Report Type：</td>
					<td>
						<input type="radio" name="reportType" value="display"/>display
						<input type="radio" name="reportType" value="save" checked/>save
					</td>
					
					<td style="text-align:right">操作：</td>
					<td style="text-align:right" >
										<input type="button" onClick="exportPayFSEDetailListOnlyLgech(this,'${request_navTabId}')" value='TSTO'/>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="button" onClick="exportPayFSEDetailList(this,'${request_navTabId}')" value="查询"/>
					</td>
				</tr>
			</table>
		</div>
	</form>
	
	<form id="exportPayFSEDetailListLGECH" name="exportPayFSEDetailListLGECH" method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="CPNY_ID" name="CPNY_ID" value="" />
		<input type="hidden" id="JOB_TP" name="JOB_TP" value="" />
		<input type="hidden" id="YEAR" name="YEAR" value="" />
		<input type="hidden" id="MONTH" name="MONTH" value="" />
		<input type="hidden" id="DEPTNO" name="DEPTNO" value="" />
		<input type="hidden" id="reportName" name="reportName" value="payPayFSEDetailListLGECH" />
		<input type="hidden" id="suffix" name="suffix" value="" />
	</form>
	
	<form id="exportPayFSEDetailListLGECHOnly" name="exportPayFSEDetailListLGECHOnly" method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="CPNY_ID" name="CPNY_ID" value="" />
		<input type="hidden" id="JOB_TP" name="JOB_TP" value="" />
		<input type="hidden" id="YEAR" name="YEAR" value="" />
		<input type="hidden" id="MONTH" name="MONTH" value="" />
		<input type="hidden" id="DEPTNO" name="DEPTNO" value="" />
		<input type="hidden" id="reportName" name="reportName" value="payPayFSEDetailListLGECHOnly" />
		<input type="hidden" id="suffix" name="suffix" value="" />
	</form>
	
	<div id="showPDFPop" ></div>
</div>

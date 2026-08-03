<%@ page contentType="text/html; charset=UTF-8" language="java"errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function exportPayPayRetrieveRJFMonth(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	var sform = document.getElementById("onlyFormRenJianFei");
	var PA_YEAR 		= sform.PA_YEAR.value;
	var PA_TOPMON 		= sform.PA_TOPMON.value;
	var PA_DOWMON		= sform.PA_DOWMON.value;
	var DEPTNO			= $(":input[sysLong='seachBankDept']").val();
	var reportName 		= sform.reportName.value;
	if(suffix == 'xls' && report == 'save'){
		document.getElementById("rpPayPayRetrieveRJFMonLink").innerHTML = "设置导出文件密码";
		var sform = document.getElementById("onlyFormRenJianFei");
		var eForm = document.getElementById("exportPayPayRetrieveRJFMon"); 
		//eForm.CPNY_ID.value	= sform.seach_CPNY_ID.value;
		//eForm.JOB_TP.value 		= sform.seach_JOB_TP.value;
		eForm.PA_YEAR.value 		= PA_YEAR;
		eForm.PA_TOPMON.value 		= PA_TOPMON;
		eForm.PA_DOWMON.value 		= PA_DOWMON;
		eForm.DEPTNO.value 			= DEPTNO;
		eForm.reportName.value 		= reportName;
		eForm.suffix.value 		= $('input[name="suffix"]:checked').val();
		$("#rp_PayPayRetrieveRJFMon").attr('href', "/sys/encryptExcel"
				+"?exportFunName=/report/pac04/exportDatilyReport2"
				+"&navTabId=rpt0102"
				+"&formId=exportPayPayRetrieveRJFMon");
		$("#rp_PayPayRetrieveRJFMon").attr('width', "300");
		$("#rp_PayPayRetrieveRJFMon").attr('height', "150");
		$("#rp_PayPayRetrieveRJFMon").click();
	}else if(suffix == 'pdf'  && report == 'save'){
		//$("#result").attr("src","");
		$("#onlyFormRenJianFei").attr("target","");
		$("#onlyFormRenJianFei").attr("action","/report/pac04/exportDatilyReport2");
		$("#PA_YEAR").attr('value',PA_YEAR);
		$("#PA_TOPMON").attr('value',PA_TOPMON);
		$("#PA_DOWMON").attr('value',PA_DOWMON);
		$("#DEPTNO").attr('value',DEPTNO);
		$("#onlyFormRenJianFei").submit();
	}else if(suffix == 'html'|| report == 'display'){
		/*$("#result").attr("src","/resources/reportFile/blank.html");
		$("#onlyFormRenJianFei").attr("action","/report/pac04/exportDatilyHtmlReport2"
					                 +"?PA_YEAR="+PA_YEAR
					                 +"&PA_TOPMON="+PA_TOPMON
					                 +"&reportName="+sform.reportName.value
					                 +"&PA_DOWMON="+PA_DOWMON
					                 +"&DEPTNO="+DEPTNO
					                 +"&suffix="+$('input[name="suffix"]:checked').val());
		$("#onlyFormRenJianFei").attr("target","result");
		$("#onlyFormRenJianFei").submit();*/
		

		//dialog的参数
		var options = {mask:true, 
                    width:1000, height:600,
                    drawable:true,
                    resizable:true
                }; 
		//查询报表的参数 一定要用@进行连接 否则导致次条件无效
		var param ="@PA_YEAR="+PA_YEAR
         +"@reportName="+sform.reportName.value
         +"@PA_TOPMON="+PA_TOPMON
         +"@PA_DOWMON="+PA_DOWMON
         +"@DEPTNO="+DEPTNO
         +"@suffix="+$('input[name="suffix"]:checked').val();
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac04/exportDatilyHtmlReport2"
				, "showPDFPop", "年度人件费报表",options);
	}
}
</script>
<div class="pageHeader">
	<a id="rp_PayPayRetrieveRJFMon" href="#" target="dialog" mask="true"><span
		id="rpPayPayRetrieveRJFMonLink" style="display: none"></span></a> 
	<form action="/report/pac04/exportDatilyReport2" id="onlyFormRenJianFei" rel="htmlReport" method="post">
		<!--  <input type="hidden" id="reportName" name="reportName" value="payPayRetrieveRJFMon" />-->
		<input type="hidden" id="reportName" name="reportName" value="payPayRetrieveRJFMon${defaultCpny}" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td width="10%" style="text-align:center">公司：</td>
					<td width="20%">
						<select id="reportName_dab" name="reportName_dab" disabled="disabled">
							<c:forEach items="${companyList}" var="item" varStatus="i">
								<option value="payPayRetrieveRJFMon${item.CPNY_ID}"
									<c:if test="${defaultCpny eq item.CPNY_ID}">selected</c:if>>
									${item.CPNY_ID}
								</option>
							</c:forEach>
						</select>
					</td>
					<td width="10%" style="text-align:center">工资结算单位：</td>
					<td width="20%">
						<!--<ait:deptTree name="DEPTNO" limit="pa" selected="${DEPTNO}" />-->
						<ait:deptList name="DEPTNO" cpnyId="${defaultCpny}" id="seachBankDept"/>
						<ait:deptTreeIcon name="DEPTNO" cpnyId="${defaultCpny}" limit="pa" id="seachBankDept" selected="${DEPTNO}" /></td>
					</td>
					</td>
					</tr>
					<tr>
					<td width="10%" style="text-align:center">截止年月：</td>
					<td width="20%">
						<select id="PA_YEAR" name="PA_YEAR" style="width:75px">
					    	<option value=""><%--请选择 --%>
					    		<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/>
					    	</option>
							<c:forEach var="i" begin="2014" end="2020" step="1"> 
						    	<option value="${i}" <c:if test="${YEAR eq i }">selected</c:if> >${i}</option>
						    </c:forEach> 
						 </select>
						 <input type="hidden" name="PA_TOPMON" id="PA_TOPMON" value="01"/>
						<select id="PA_DOWMON" name="PA_DOWMON" >
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
					<td colspan="3">&nbsp;</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onClick="exportPayPayRetrieveRJFMonth(this,'${request_navTabId}')">
									查询
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
	<form id="exportPayPayRetrieveRJFMon" name="exportPayPayRetrieveRJFMon" method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="CPNY_ID" name="CPNY_ID" value="" />
		<input type="hidden" id="JOB_TP" name="JOB_TP" value="" />
		<input type="hidden" id="PA_YEAR" name="PA_YEAR" value="" />
		<input type="hidden" id="PA_TOPMON" name="PA_TOPMON" value="" />
		<input type="hidden" id="PA_DOWMON" name="PA_DOWMON" value="" />
		<input type="hidden" id="DEPTNO" name="DEPTNO" value="" />
		<input type="hidden" id="reportName" name="reportName" value="" />
		<input type="hidden" id="suffix" name="suffix" value="" />
	</form>
	<!--  <iframe name=result id=result width=100% height=465 style="z-index: 1;" frameborder=0 scrolling=auto src="/resources/reportFile/blank.html"></iframe>-->
	<div id="showPDFPop" ></div>
</div>

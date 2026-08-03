<%@ page contentType="text/html; charset=UTF-8" language="java"errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function exportWelInsureYear(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	//alert(suffix);
		if(suffix == 'xls' && report == 'save'){
			
			document.getElementById("rpWelInsurceYearlyLink").innerHTML = "设置导出文件密码";
			var sform = document.getElementById("onlyForm");
			var eForm = document.getElementById("exportWelInsuranceYearly");
			var CPNY_ID= $("#seach_CPNY_ID").val();
			//alert(CPNY_ID);
			eForm.CPNY_ID.value	= CPNY_ID;
			eForm.DEPTNO.value	= sform.seach_DEPTNO.value;
			
			//eForm.JOB_TP.value 		= sform.seach_JOB_TP.value;
			eForm.ITEM_NO.value = sform.seach_ITEM_NO.value;
			eForm.YEAR.value = sform.seach_YEAR.value;
			//eForm.MONTH.value = sform.seach_MONTH.value;不用月了
			//alert(1111);
			eForm.reportName.value = sform.reportName.value;
			
			eForm.suffix.value = $('input[name="suffix"]:checked').val();
			
			$("#rp_WelInsurceYearly").attr('href', "/sys/encryptExcel"
					+"?exportFunName=/report/pac04/exportDatilyReport2"
					+"&navTabId=rpt0102"           
					+"&formId=exportWelInsuranceYearly");
			$("#rp_WelInsurceYearly").attr('width', "300");
			$("#rp_WelInsurceYearly").attr('height', "150");
			$("#rp_WelInsurceYearly").click();
	}else if(suffix == 'pdf'  && report == 'save'){
		//$("#result").attr("src","");
		$("#onlyForm").attr("target","");
		$("#onlyForm").attr("action","/report/pac04/exportDatilyReport2");
		$("#onlyForm").submit();
	}else if(suffix == 'html'||report == 'display'){
		//$("#result").attr("src","/resources/reportFile/blank.html");
		//$("#onlyForm").attr("action","/report/pac04/exportDatilyHtmlReport2");
		//$("#onlyForm").attr("target","result");
		//$("#onlyForm").submit();
		var sform = document.getElementById("onlyForm");
		var options = {mask:true, 
                    width:1000, height:600,
                    drawable:true,
                    resizable:true
                }; 
		var param = "CPNY_ID="+sform.seach_CPNY_ID.value
        +"@YEAR="+sform.seach_YEAR.value
        +"@reportName="+sform.reportName.value
        +"@DEPTNO="+sform.seach_DEPTNO.value
        +"@ITEM_NO="+sform.seach_ITEM_NO.value
        +"@suffix="+$('input[name="suffix"]:checked').val();
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac04/exportDatilyHtmlReport2"
				, "showPDFPop", "福利按类型年度报表",options);
	}
}
</script>
<div class="pageHeader">
	<a id="rp_WelInsurceYearly" href="#" target="dialog" mask="true"><span
		id="rpWelInsurceYearlyLink" style="display: none"></span></a> 
	<form action="/report/pac04/exportDatilyReport2" id="onlyForm" rel="htmlReport" method="post">
		<input type="hidden" id="reportName" name="reportName" value="welInsuranceYearly${defaultCpny}" />
		<input type="hidden" id="CPNY_ID" name="CPNY_ID" value="${defaultCpny}" />
	    <input type="hidden" id="seach_CPNY_ID" name="seach_CPNY_ID" value="${defaultCpny}" /> 
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td width="10%" style="text-align:center">公司：</td>
					<td width="20%">
						<select id="seach_CPNY_ID" name="seach_CPNY_ID" disabled="disabled">
							<c:forEach items="${companyList}" var="item" varStatus="i">
								<option value="${item.CPNY_ID}"
									<c:if test="${defaultCpny eq item.CPNY_ID}">selected</c:if>>
									${item.CPNY_ID}
								</option>
							</c:forEach>
						</select>
					
					</td>
					<td width="10%" style="text-align:center">工资结算单位：</td>
					<td width="20%">
						<ait:deptTree name="seach_DEPTNO" limit="pa" selected="${DEPTNO}" />
					</td>
			    </tr>
			    <tr>
					<td width="10%" style="text-align:center">福利年：</td>
					<td width="17%">
						<select id="seach_YEAR" name="seach_YEAR" style="width:75px">
					    	<option value=""><%--请选择 --%>
					    		<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/>
					    	</option>
							<c:forEach var="i" begin="2014" end="2020" step="1"> 
						    	<option value="${i}" <c:if test="${YEAR eq i }">selected</c:if> >${i}</option>
						    </c:forEach> 
						 </select>
					</td>
					<td width="10%" style="text-align:center">展示类型：</td>
					<td>
					    <select id="seach_ITEM_NO" name="seach_ITEM_NO" style="width:110px">
					         <option value="217820" <c:if test="${ITEMNO eq '217820' }">selected</c:if> >
					                                  养老保险详细
					         </option>
					         <option value="217821" <c:if test="${ITEMNO eq '217821' }">selected</c:if> >
					                                  医疗保险详细
					         </option>
					         <option value="217822" <c:if test="${ITEMNO eq '217822' }">selected</c:if> >
					                                  失业保险详细
					         </option>
					         <option value="217823" <c:if test="${ITEMNO eq '217823' }">selected</c:if> >
					                                  公积金详细
					         </option>
					         <option value="217827" <c:if test="${ITEMNO eq '217827' }">selected</c:if> >
					                                  工伤保险详细
					         </option>
					         <option value="217828" <c:if test="${ITEMNO eq '217828' }">selected</c:if> >
					                                  生育保险详细
					         </option>
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
								<button type="button" onClick="exportWelInsureYear(this,'${request_navTabId}')">
									查询
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
	<form id="exportWelInsuranceYearly" name="exportWelInsuranceYearly" method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="CPNY_ID" name="CPNY_ID" value="" />
		<input type="hidden" id="ITEM_NO" name="ITEM_NO" value="" />
		<input type="hidden" id="DEPTNO" name="DEPTNO" value="" />
		<input type="hidden" id="YEAR" name="YEAR" value="" />
		<input type="hidden" id="MONTH" name="MONTH" value="" />
		<input type="hidden" id="reportName" name="reportName" value="welInsuranceYearly" />
		<input type="hidden" id="suffix" name="suffix" value="" />
	</form>
	<!--  
	<iframe name=result id=result width=100% height=465 frameborder=0 scrolling=auto src="/resources/reportFile/blank.html"></iframe>
	-->
	<div id="showPDFPop" ></div>
</div>

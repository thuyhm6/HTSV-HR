<%@ page contentType="text/html; charset=UTF-8" language="java"errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function exportwelAmountbyJob(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	var sform = document.getElementById("onlyForm");
	var JOB_TP = sform.seach_JOB_TP.value;
	if(JOB_TP == null || JOB_TP ==''){
		JOB_TP = '211807';
	}
	var reportName = $("#reportName").val();
	var SUBSD_CD = $("#SUBSD_CD").val();
	var ORG_ID = $("#seach_ORG_ID").val();
	if(suffix == 'xls'){
		document.getElementById("rpwelAmountbyJob_TpLink").innerHTML = "设置导出文件密码";
		var eForm = document.getElementById("exportwelAmountbyJob_Tp"); 
		eForm.SUBSD_CD.value	= SUBSD_CD;
		eForm.ATT_MON.value 		= sform.seach_YEAR.value+sform.seach_MONTH.value;
		eForm.ATT_MON_XS.value 		= sform.seach_YEAR.value+'/'+sform.seach_MONTH.value;
		eForm.reportName.value 		= reportName;
		eForm.ORG_ID.value 		= ORG_ID;
		eForm.JOB_TP.value 		= JOB_TP;
		eForm.suffix.value 		= $('input[name="suffix"]:checked').val();
		$("#rp_welAmountbyJob_Tp").attr('href', "/sys/encryptExcel"
				+"?exportFunName=/report/pac04/exportDatilyReport2"
				+"&navTabId=rpt0102"
				+"&formId=exportwelAmountbyJob_Tp");
		$("#rp_welAmountbyJob_Tp").attr('width', "300");
		$("#rp_welAmountbyJob_Tp").attr('height', "150");
		$("#rp_welAmountbyJob_Tp").click();
	}else if(suffix == 'pdf'  && report == 'save'){
		$("#onlyForm").attr("target","");
		$("#onlyForm").attr("action","/report/pac04/exportDatilyReport2");
	    $("#ATT_MON").attr('value',sform.seach_YEAR.value+''+sform.seach_MONTH.value);
	    $("#ATT_MON_XS").attr('value',sform.seach_YEAR.value+'/'+sform.seach_MONTH.value);
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
		+"@JOB_TP="+JOB_TP
        +"@ATT_MON="+sform.seach_YEAR.value+''+sform.seach_MONTH.value
        +"@ATT_MON_XS="+sform.seach_YEAR.value+'/'+sform.seach_MONTH.value
        +"@reportName="+reportName
        +"@ORG_ID="+ORG_ID
        +"@suffix="+$('input[name="suffix"]:checked').val();
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac04/exportDatilyHtmlReport"
				, "showPDFPop", "福利JOB_TP汇总报表",options);        
	}
}
</script>
<div class="pageHeader">
	<a id="rp_welAmountbyJob_Tp" href="#" target="dialog" mask="true"><span
		id="rpwelAmountbyJob_TpLink" style="display: none"></span></a> 
	<form action="/report/pac04/exportDatilyReport2" id="onlyForm" rel="htmlReport" method="post">
	   
		<c:if test="${defaultCpny eq 'LGEHZ'}">
		<input type="hidden" id="reportName" name="reportName" value="welAmountbyJob_TpLGEHZ" />
		</c:if>
		 <c:if test="${defaultCpny ne 'LGEHZ'}">
		<input type="hidden" id="reportName" name="reportName" value="welAmountbyJob_Tp" />
		</c:if>
		<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="${defaultCpny}" />
		<input type="hidden" id="seach_SUBSD_CD" name="seach_SUBSD_CD" value="${defaultCpny}" />
		<input type="hidden" id="ATT_MON" name="ATT_MON" value="" />
		<input type="hidden" id="ATT_MON_XS" name="ATT_MON_XS" value="" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td width="10%" style="text-align:center">公司：</td>
					<td width="20%">
						<select id="seach_SUBSD_CD" name="seach_SUBSD_CD" disabled="disabled">
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
						<ait:deptTree name="seach_ORG_ID" limit="pa" selected="${DEPTNO}" />
					</td>
					</tr>
					<tr>
					<td><c:if test="${defaultCpny ne'LGEND'}">
						人员类型组：
						</c:if>
						<c:if test="${defaultCpny eq'LGEND'}">
						&nbsp人员类型：
						</c:if></td>
					<td>
					 	<c:if test="${defaultCpny ne'LGEND'}">
						<ait:SelectEmpTypeCode name="seach_JOB_TP" selected="${EMP_TYPE_GROUP}" limit="pa" type="group" />
						</c:if>
						<c:if test="${defaultCpny eq'LGEND'}">
						<ait:SelectEmpTypeCode name="seach_JOB_TP" selected="${EMP_TYPE_GROUP}" limit="pa"  />
						</c:if>
					</td>
					<td width="10%" style="text-align:center">工资月：</td>
					<td width="20%">
						<select id="seach_YEAR" name="seach_YEAR" style="width:75px">
					    	<option value=""><%--请选择 --%>
					    		<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/>
					    	</option>
							<c:forEach var="i" begin="2012" end="2025" step="1"> 
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
						<input type="radio" name="reportType" value="display" checked/>display
						<input type="radio" name="reportType" value="save"/>save
					</td>
					<td colspan="3">&nbsp;</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onClick="exportwelAmountbyJob(this,'${request_navTabId}')">
									查询
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
	<form id="exportwelAmountbyJob_Tp" name="exportwelAmountbyJob_Tp" method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="" />
		<input type="hidden" id="ORG_ID" name="ORG_ID" value="" />
		<input type="hidden" id="JOB_TP" name="JOB_TP" value="" />
		<input type="hidden" id="YEAR" name="YEAR" value="" />
		<input type="hidden" id="MONTH" name="MONTH" value="" />
		<input type="hidden" id="ATT_MON" name="ATT_MON" value="" />
		<input type="hidden" id="ATT_MON_XS" name="ATT_MON_XS" value="" />
		<c:if test="${defaultCpny eq 'LGEHZ'}">
		<input type="hidden" id="reportName" name="reportName" value="welAmountbyJob_TpLGEHZ" />
		</c:if>
		<c:if test="${defaultCpny ne 'LGEHZ'}">
		<input type="hidden" id="reportName" name="reportName" value="welAmountbyJob_Tp" />
		</c:if>
		
		<input type="hidden" id="suffix" name="suffix" value="" />
	</form>
	<div id="showPDFPop" ></div>
</div>

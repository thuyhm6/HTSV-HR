<%@ page contentType="text/html; charset=UTF-8" language="java"errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function exportPayRetrieveAdjustRate(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	var sform = document.getElementById("onlyForm");
	var ORG_ID = $(":input[sysLong='seachDeptQQ']").val();
	var reportName = $("#reportName").val();
	var SUBSD_CD = $("#SUBSD_CD").val();
	if(suffix == 'xls'){
		document.getElementById("rp0102Link").innerHTML = "设置导出文件密码";
		var eForm = document.getElementById("exportPayRetrieveAdjustRateList"); 
		eForm.SUBSD_CD.value	= SUBSD_CD;
		eForm.ORG_ID.value	= ORG_ID;
		eForm.PA_MONTH.value 		= sform.seach_YEAR.value +''+ sform.seach_MONTH.value;
		eForm.PA_MONTH_XS.value 		= sform.seach_YEAR.value +'/'+ sform.seach_MONTH.value;
		eForm.reportName.value 		= reportName;
		eForm.suffix.value 		= $('input[name="suffix"]:checked').val();
		$("#importExcelDialog_rp10102Dept").attr('href', "/sys/encryptExcel"
				+"?exportFunName=/report/pac04/exportDatilyReport"
				+"&navTabId=rpt0102"
				+"&formId=exportPayRetrieveAdjustRateList");
		$("#importExcelDialog_rp10102Dept").attr('width', "300");
		$("#importExcelDialog_rp10102Dept").attr('height', "150");
		$("#importExcelDialog_rp10102Dept").click();
	}else if(suffix == 'pdf'  && report == 'save'){
		$("#onlyForm").attr("target","");
		$("#onlyForm").attr("action","/report/pac04/exportDatilyReport2");
	    $("#PA_MONTH").attr('value',sform.seach_YEAR.value+''+sform.seach_MONTH.value);
	    $("#PA_MONTH_XS").attr('value',sform.seach_YEAR.value+'/'+sform.seach_MONTH.value);
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
        +"@PA_MONTH="+sform.seach_YEAR.value+''+sform.seach_MONTH.value
        +"@PA_MONTH_XS="+sform.seach_YEAR.value+'/'+sform.seach_MONTH.value
        +"@reportName="+reportName
        +"@ORG_ID="+ORG_ID
        +"@suffix="+$('input[name="suffix"]:checked').val();
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac04/exportDatilyHtmlReport2"
				, "showPDFPop", "工资调整人员比例",options);        
	}
}
</script>
<div class="pageHeader">
	<a id="importExcelDialog_rp10102Dept" href="#" target="dialog" mask="true"><span
		id="rp0102Link" style="display: none"></span></a> 
	<form action="/report/pac04/exportDatilyReport2" id="onlyForm" rel="htmlReport" method="post">
		<input type="hidden" id="reportName" name="reportName" value="payRetrieveAdjustRateList" />
		<input type="hidden" id="PA_MONTH" name="PA_MONTH" value=""/>
		<input type="hidden" id="PA_MONTH_XS" name="PA_MONTH_XS" value=""/>
		<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="${defaultCpny}"/>
		<input type="hidden" id="seach_SUBSD_CD" name="seach_SUBSD_CD" value="${defaultCpny}"/>
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
						<ait:deptList name="seach_ORG_ID" cpnyId="${defaultCpny}" id="seachDeptQQ"/>
						<ait:deptTreeIcon name="seach_ORG_ID" cpnyId="${defaultCpny}" limit="pa" id="seachDeptQQ" selected="${ORG_ID}"/>
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
								<button type="button" onClick="exportPayRetrieveAdjustRate(this,'${request_navTabId}')">
									查询
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
	<form id="exportPayRetrieveAdjustRateList" name="exportPayRetrieveAdjustRateList" method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="" />
		<input type="hidden" id="ORG_ID" name="ORG_ID" value="" />
		<input type="hidden" id="PA_MONTH" name="PA_MONTH" value="" />
		<input type="hidden" id="reportName" name="reportName" value="payRetrieveAdjustRateList" />
		<input type="hidden" id="suffix" name="suffix" value="" />
		<input type="hidden" id="PA_MONTH_XS" name="PA_MONTH_XS" value=""/>
	</form>
	<div id="showPDFPop" ></div>
</div>

<%@ page contentType="text/html; charset=UTF-8" language="java"errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function exportPayRetrieveAdjustRateLge(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	var sform = document.getElementById("onlyFormRateList");
	var ATT_MONTH =sform.seach_YEAR.value+sform.seach_MONTH.value;
	var CPNY_ID =sform.seach_CPNY_ID.value;
	var reportName =sform.reportName.value;
	var SUBSD_CD = $("#SUBSD_CD").val();
	if(suffix == 'xls' && report == 'save'){
		document.getElementById("importExcelDialog_rp0102Link").innerHTML = "设置导出文件密码";
		var sform = document.getElementById("onlyFormRateList");
		var eForm = document.getElementById("excelExportForm_rp0102GZTZ"); 
		eForm.ATT_MONTH.value = ATT_MONTH;
		eForm.reportName.value 	= reportName;
		eForm.suffix.value 		= $('input[name="suffix"]:checked').val();
		$("#importExcelDialog_rp0102").attr('href', "/sys/encryptExcel"
				+"?exportFunName=/report/pac04/exportDatilyReport2"
				+"&navTabId=rpt0102"
				+"&formId=excelExportForm_rp0102GZTZ");
		$("#importExcelDialog_rp0102").attr('width', "300");
		$("#importExcelDialog_rp0102").attr('height', "150");
		$("#importExcelDialog_rp0102").click();
	}else if(suffix == 'pdf'  && report == 'save'){
		//$("#result").attr("src","");
		$("#onlyFormRateList").attr("target","");
		$("#onlyFormRateList").attr("action","/report/pac04/exportDatilyReport2");
		$("#ATT_MONTH").attr('value',ATT_MONTH);
		$("#onlyFormRateList").submit();
	}else if(suffix == 'html'||report == 'display'){
		/*$("#result").attr("src","/resources/reportFile/blank.html");
		$("#onlyFormRateList").attr("action","/report/pac04/exportDatilyHtmlReport2"
									+"?ATT_MONTH="+ATT_MONTH
									+"&suffix="+$('input[name="suffix"]:checked').val()								);
		$("#onlyFormRateList").attr("target","result");
		$("#onlyFormRateList").submit();*/
		
		//dialog的参数
		var options = {mask:true, 
                    width:1000, height:600,
                    drawable:true,
                    resizable:true
                }; 
		//查询报表的参数 一定要用@进行连接 否则导致次条件无效
		var param ="ATT_MONTH="+ATT_MONTH
		+"@reportName="+reportName
		+"@CPNY_ID="+CPNY_ID
		+"@SUBSD_CD="+SUBSD_CD
		+"@DEPTNO=212738"
		+"@suffix="+$('input[name="suffix"]:checked').val();
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac04/exportDatilyHtmlReport"
				, "showPDFPop", "法人别工资调整人员比例",options);
	}
}
</script>
<div class="pageHeader">
	<a id="importExcelDialog_rp0102" href="#" target="dialog" mask="true"><span
		id="importExcelDialog_rp0102Link" style="display: none"></span></a> 
		<a id="displayDept" href="#" target="ajax" rel="deptMon"></a>
	<form action="/report/pac04/exportDatilyReport2" id="onlyFormRateList" rel="htmlReport" method="post">
		<input type="hidden" id="reportName" name="reportName" value="payRetrieveAdjustRateLgeList" />
		<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="${defaultCpny}"/>
		<input type="hidden" id="seach_CPNY_ID" name="seach_CPNY_ID" value="${defaultCpny}"/>
		<input type="hidden" id="ATT_MONTH" name="ATT_MONTH" value=""/>
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
					<!--  
					<td width="10%" style="text-align:center">保险类型：</td>
					<td width="20%">
						<select type="text"  id="seach_ITEM_NO" name="seach_ITEM_NO">
						    <option value=""><%--请选择--%>
								<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/>
							</option>
					        <c:forEach items="${itemList}" var="cpny">
				 		         <option value="${cpny.ITEM_NO}" >${cpny.ITEM_NAME}</option>
					        </c:forEach>
				       </select>
					</td>
					<td>&nbsp;</td>
					
				</tr>
				<tr>-->	
					<!--  
					<td width="10%" style="text-align:center">工资结算单位：</td>
					<td width="20%">
						<ait:deptTree name="seach_DEPTNO" limit="hr" selected="${DEPTNO}" />
					</td>
					-->
					<td width="10%" style="text-align:center">月份：</td>
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
					<td colspan="3">&nbsp;</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<!--  <button type="button" onClick="exportPayRetrieveAdjustRateLge(this,'${request_navTabId}')">
									查询
								</button>-->
								<button type="button" onClick="exportPayRetrieveAdjustRateLge(this,'${request_navTabId}')">
									查询
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
	<div id="deptMon" class="unitBox" width="150%">
		<!--#include virtual="list1.html" -->
	</div>
	<form id="excelExportForm_rp0102GZTZ" name="excelExportForm_rp0102GZTZ" method="post">
		<input type="hidden" id="password" name="password" value="" />
		<!--  <input type="hidden" id="CPNY_ID" name="CPNY_ID" value="" />
		<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="" />
		<input type="hidden" id="JOB_TP" name="JOB_TP" value="" />
		<input type="hidden" id="YEAR" name="YEAR" value="" />
		<input type="hidden" id="MONTH" name="MONTH" value="" />
		<input type="hidden" id="ITEM_NO" name="ITEM_NO" value="" />
		<input type="hidden" id="DEPTNO" name="DEPTNO" value="" />-->
		<input type='hidden' id="ATT_MONTH" name="ATT_MONTH" value=""/>
		<input type="hidden" id="reportName" name="reportName" value="" />
		<input type="hidden" id="suffix" name="suffix" value="" />
	</form>
	<!--  <iframe name=result id=result width=100% height=465 style="z-index: 1;" frameborder=0 scrolling=auto src="/resources/reportFile/blank.html"></iframe>-->
	<div id="showPDFPop" ></div>
</div>

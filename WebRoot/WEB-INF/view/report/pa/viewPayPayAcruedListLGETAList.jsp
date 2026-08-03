<%@ page contentType="text/html; charset=UTF-8" language="java"errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function exportPayAcruedList(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	var sform = document.getElementById("onlyForm_Acrued");
	var JOB_TP = sform.seach_JOB_TP.value;
	var reportName = sform.seach_reportName.value;
	if(suffix == 'xls'){
		document.getElementById("rp0103Link").innerHTML = "设置导出文件密码";
		var eForm = document.getElementById("exportPayAcrued_rp0103Acrued"); 
		eForm.JOB_TP.value 		= sform.seach_JOB_TP.value;
		eForm.PA_MONTH.value 	= sform.seach_YEAR.value+''+sform.seach_MONTH.value;
		eForm.reportName.value 	= reportName;
		eForm.suffix.value 		= $('input[name="suffix"]:checked').val();
		$("#importExcelDialog_rp0103Acrued").attr('href', "/sys/encryptExcel"
				+"?exportFunName=/report/pac04/exportDatilyReport"
				+"&navTabId=rpt0103"
				+"&formId=exportPayAcrued_rp0103Acrued");
		$("#importExcelDialog_rp0103Acrued").attr('width', "300");
		$("#importExcelDialog_rp0103Acrued").attr('height', "150");
		$("#importExcelDialog_rp0103Acrued").click();
	}else if(suffix == 'pdf'  && report == 'save'){
		$("#onlyForm_Acrued").attr("target","");
		$("#onlyForm_Acrued").attr("action","/report/pac04/exportDatilyReport");
		$("#PA_MONTH").attr('value',sform.seach_YEAR.value+''+sform.seach_MONTH.value);
		$("#JOB_TP").attr('value',JOB_TP);
		$("#reportName").attr('value',reportName);
		$("#onlyForm_Acrued").submit();
	}else if(suffix == 'html'|| report == 'display'){
		/**新页面显示*/
		var options = {mask:true, 
                    width:1000, height:600,
                    drawable:true,
                    resizable:true
                }; 
		//查询报表的参数 一定要用@进行连接 否则导致次条件无效
		var param = "JOB_TP="+sform.seach_JOB_TP.value
        +"@PA_MONTH="+ sform.seach_YEAR.value+''+sform.seach_MONTH.value
        +"@reportName="+reportName
        +"@suffix="+$('input[name="suffix"]:checked').val();
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac04/exportDatilyHtmlReport"
				, "showPDFPop", "预提工资查询报表",options);
				
	}
}
</script>
<div class="pageHeader">
	<a id="importExcelDialog_rp0103Acrued" href="#" target="dialog" mask="true"><span
		id="rp0103Link" style="display: none"></span></a> 
	<form action="/report/pac04/exportDatilyReport" id="onlyForm_Acrued" rel="htmlReport"  method="post">
		<input type="hidden" id="reportName" name="reportName" value="" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td >展示类型：</td>
					<td >
						<select id="seach_reportName" name="seach_reportName" >
								<option value="payPayAcruedListLGETA"  >Acrued PayRoll List</option>
								<option value="payPayAcruedMainLGETA" >Acrued PayRoll Main</option>
							</select>
						</select>
					</td>
					<td>人员类型组： </td>
						<td>
						<ait:SelectEmpTypeCode name="seach_JOB_TP" selected="${EMP_TYPE_GROUP}"  limit="pa" type="group" />
						</td>
				</tr>
				<tr>
					<td>File Type：</td>
					<td>
						<input type="radio" name="suffix" value="pdf" checked onClick="result.location.href='/resources/reportFile/blank.html'"/>pdf
						<input type="radio" name="suffix" value="xls"  onClick="result.location.href='/resources/reportFile/blank.html'"/>xls
						<input type="radio" name="suffix" value="html" onClick="result.location.href='/resources/reportFile/blank.html'"/>html
					</td>
					<td>Report Type：</td>
					<td>
						<input type="radio" name="reportType" value="display"/>display
						<input type="radio" name="reportType" value="save" checked/>save
					</td>
					<td>YYYYMM：</td>
					<td>
						<ait:date yearName="seach_YEAR" yearSelected="${YEAR}" monthName="seach_MONTH" monthSelected="${MONTH}"/>
						<input type="hidden" id="PA_MONTH" name="PA_MONTH" value=""/>
					</td>
					
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onClick="exportPayAcruedList(this,'${request_navTabId}')">
									查询
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
	<form id="exportPayAcrued_rp0103Acrued" name="exportPayAcrued_rp0103Acrued" method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="" />
		<input type="hidden" id="JOB_TP" name="JOB_TP" value="" />
		<input type="hidden" id="ORG_ID" name="ORG_ID" value="" />
		<input type="hidden" id="PA_MONTH" name="PA_MONTH" value="" />
		<input type="hidden" id="reportName" name="reportName" value="" />
		<input type="hidden" id="suffix" name="suffix" value="" />
	</form>
	<div id="showPDFPop" ></div>
</div>

<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<title></title>
<head>   
	<meta http-equiv="X-UA-Compatible" content="IE=edge" >
<script type="text/javascript">
	function exportIncentiveCalcList(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	var sform = document.getElementById("onlyForm");
	var JOB_NAME = sform.seach_JOB_TP[sform.seach_JOB_TP.selectedIndex].text;
	var SUBSD_NAME = $("#seachDept").val();
	if(suffix == 'xls' && report == 'save'){
		document.getElementById("rp0102Link").innerHTML = "设置导出文件密码";
		var eForm = document.getElementById("excelExportForm_rp0102"); 
		eForm.SUBSD_CD.value	= sform.seach_SUBSD_CD.value;
		eForm.JOB_TP.value 		= sform.seach_JOB_TP.value;
		eForm.SUBSD_NAME.value	= SUBSD_NAME;
		eForm.JOB_NAME.value 		= JOB_NAME;
		eForm.ATT_MON.value 		= sform.seach_YEAR.value+''+sform.seach_MONTH.value;
		eForm.reportName.value 		= sform.reportName.value;
		eForm.suffix.value 		= $('input[name="suffix"]:checked').val();
		$("#importExcelDialog_rp0102").attr('href', "/sys/encryptExcel"
				+"?exportFunName=/report/pac04/exportDatilyReport"
				+"&navTabId=rpt0102"
				+"&formId=excelExportForm_rp0102");
		$("#importExcelDialog_rp0102").attr('width', "300");
		$("#importExcelDialog_rp0102").attr('height', "150");
		$("#importExcelDialog_rp0102").click();
	}else if(suffix == 'pdf'  && report == 'save'){
		alert(JOB_NAME);
		alert(SUBSD_NAME);
		$("#ATT_MON").attr('value',sform.seach_YEAR.value+''+sform.seach_MONTH.value);
		$("#JOB_NAME").attr('value',JOB_NAME);
		$("#SUBSD_NAME").attr('value',SUBSD_NAME);
		$("#onlyForm").submit();
	}else if(report == 'display'){
		/**新页面显示
		$("#onlyForm").attr("target","ajax");
		$("#onlyForm").attr("action","/report/pac04/exportDatilyHtmlReport");
		$("#onlyForm").submit();*/
		$("#displayDept").attr("href","/report/pac04/exportDatilyHtmlReport" 
									+"?SUBSD_CD="+sform.seach_SUBSD_CD.value
		                            +"&JOB_TP="+sform.seach_JOB_TP.value
		                            +"&ATT_MON="+sform.seach_YEAR.value+''+sform.seach_MONTH.value
		                            +"&reportName="+sform.reportName.value
		                            +"&JOB_NAME="+encodeURI(JOB_NAME)
		                            +"&SUBSD_NAME="+encodeURI(SUBSD_NAME)
		                            +"&suffix="+$('input[name="suffix"]:checked').val());
		$("#displayDept").click();
	}
}
</script>
</head>
<div class="pageHeader">
	<a id="importExcelDialog_rp0102" href="#" target="dialog" mask="true"><span
		id="rp0102Link" style="display: none"></span></a> 
	<a id="displayDept" href="#" target="ajax" rel="deptMon"></a>
	<form action="/report/pac04/exportDatilyReport" id="onlyForm" rel="deptMon" method="post">
		<div class="searchBar">
		    <table class="searchContent">
				<tr>
					<td>部门：</td>
					<td>
						<ait:deptList name="seach_SUBSD_CD" cpnyId="${defaultCpny}" id="seachDept"/>
						<ait:deptTreeIcon name="seach_SUBSD_CD" cpnyId="${defaultCpny}" limit="hr" id="seachDept" selected="${SUBSD_CD}"/></td>
						<input type="hidden" id="SUBSD_NAME" name="seach_SUBSD_NAME" value=""/>
					</td>
					<td>人员类型组：</td>
					<td>
						<ait:SelectSyCodeByCpnyID id="seach_JOB_TP" name="seach_JOB_TP" parentNo="211807" selected="${JOB_TP}" cnpyID="${defaultCpny}" limit="all" />
						<input type="hidden" id="JOB_NAME" name="seach_JOB_NAME" value=""/>
					</td>
				</tr>
				<tr>
					<td>日期：</td>
					<td>
						<ait:date yearName="seach_YEAR" yearSelected="${YEAR}" monthName="seach_MONTH" monthSelected="${MONTH}"/>
						<input type="hidden" id="ATT_MON" name="ATT_MON" value=""/>
					</td>
					<td>法人：</td>
					<td>
						<select name="reportName">
							<c:forEach items="${companyList}" var="item" varStatus="i">
								<option value="attRetrieveDeptMonAttByEmp${item.CPNY_ID}"
									<c:if test="${defaultCpny eq item.CPNY_ID}">selected</c:if>>
									${item.CPNY_ID}
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>File Type：</td>
					<td>
						<input type="radio" name="suffix" value="pdf" />pdf
						<input type="radio" name="suffix" value="xls" checked />xls
					</td>
					<td>Report Type：</td>
					<td>
						<input type="radio" name="reportType" value="display"/>display
						<input type="radio" name="reportType" value="save" checked/>save
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onClick="exportIncentiveCalcList(this,'${request_navTabId}')">
									<!--检索--><spring:message code="public.title.search" />
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
	<form id="excelExportForm_rp0102" name="excelExportForm_rp0102" method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="" />
		<input type="hidden" id="JOB_TP" name="JOB_TP" value="" />
		<input type="hidden" id="SUBSD_NAME" name="SUBSD_NAME" value="" />
		<input type="hidden" id="JOB_NAME" name="JOB_NAME" value="" />
		<input type="hidden" id="ATT_MON" name="ATT_MON" value="" />
		<input type="hidden" id="reportName" name="reportName" value="" />
		<input type="hidden" id="suffix" name="suffix" value="" />
	</form>
</div>
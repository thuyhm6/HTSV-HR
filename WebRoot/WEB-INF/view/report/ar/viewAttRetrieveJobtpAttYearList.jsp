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
	var sform = document.getElementById("onlyFormNDJB");
	var YEAR=sform.seach_YEAR.value;
	var DEPTNO=sform.seach_DEPTNO.value;
	var ATT_CPNYID=sform.seach_CPNY_ID.value;
	var reportName=sform.reportName.value;
	if(suffix == 'xls' && report == 'save'){
		document.getElementById("rp0102Link").innerHTML = "设置导出文件密码";
		var eForm = document.getElementById("excelExportForm_rp0102"); 
		eForm.DEPTNO.value 		= sform.seach_DEPTNO.value;
		eForm.YEAR.value 		= sform.seach_YEAR.value;
		eForm.reportName.value 		= sform.reportName.value;
		eForm.ATT_CPNYID.value 		= ATT_CPNYID;
		eForm.suffix.value 		= $('input[name="suffix"]:checked').val();
		$("#importExcelDialog_rp0102").attr('href', "/sys/encryptExcel"
				+"?exportFunName=/report/pac04/exportDatilyReport"
				+"&navTabId=rpt0102"
				+"&formId=excelExportForm_rp0102");
		$("#importExcelDialog_rp0102").attr('width', "300");
		$("#importExcelDialog_rp0102").attr('height', "150");
		$("#importExcelDialog_rp0102").click();
	}else if(suffix == 'pdf'  && report == 'save'){
		//$("#result").attr("src","");
		$("#onlyFormNDJB").attr("target","");
		$("#onlyFormNDJB").attr("action","/report/pac04/exportDatilyReport");
		$("#YEAR").attr('value',sform.seach_YEAR.value);
		$("#DEPTNO").attr('value',sform.seach_DEPTNO.value);
		$("#ATT_CPNYID").attr('value',ATT_CPNYID);
		$("#onlyFormNDJB").submit();
	}else if(suffix == 'html'||report == 'display'){
		/*
		$("#result").attr("src","/resources/reportFile/blank.html");
		$("#onlyFormNDJB").attr("action","/report/pac04/exportDatilyHtmlReport2"
				+"?YEAR="+YEAR
                +"&DEPTNO="+DEPTNO
                +"&reportName="+reportName
                +"&suffix="+$('input[name="suffix"]:checked').val());
		$("#onlyFormNDJB").attr("target","result");
		$("#onlyFormNDJB").submit();*/
		
		//dialog的参数
		var options = {mask:true, 
                    width:1000, height:600,
                    drawable:true,
                    resizable:true
                }; 
		//查询报表的参数 一定要用@进行连接 否则导致次条件无效
		var param ="YEAR="+YEAR
        +"@DEPTNO="+DEPTNO
        +"@ATT_CPNYID="+ATT_CPNYID
        +"@reportName="+reportName
        +"@suffix="+$('input[name="suffix"]:checked').val();
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac04/exportDatilyHtmlReport"
				, "showPDFPop", "年度工作类型加班报表",options);
		
	}
}
</script>
</head>
<div class="pageHeader">
	<a id="importExcelDialog_rp0102" href="#" target="dialog" mask="true"><span
		id="rp0102Link" style="display: none"></span></a> 
	<a id="displayDept" href="#" target="ajax" rel="deptMon"></a>
	<form action="/report/pac04/exportDatilyReport" id="onlyFormNDJB" rel="deptMon" method="post">
		<input type="hidden" name="YEAR" ID="YEAR" value=""/>
		<input type="hidden" name="DEPTNO" ID="DEPTNO" value=""/>
		<input type="hidden" name="ATT_CPNYID" ID="ATT_CPNYID" value=""/>
		<input type="hidden" name="seach_CPNY_ID" ID="seach_CPNY_ID" value="${defaultCpny}"/>
		<input type="hidden" name="reportName" ID="reportName" value="attRetrieveJobtpAttYear"/>
		<!--  <input type="hidden" id="SUBSD_CD" name="seach_SUBSD_CD" value="${defaultCpny}"/>-->
		<div class="searchBar">
		    <table class="searchContent">
				<tr>
					<td>部门：</td>
					<td>
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" id="seachDept"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="seachDept" /></td>
					</td>
					<!--  <td>人员类型组：</td>
					<td>
						<ait:SelectSyCodeByCpnyID id="seach_JOB_TP" name="seach_JOB_TP" parentNo="211807" selected="${JOB_TP}" cnpyID="${defaultCpny}" limit="all" />
						<input type="hidden" id="JOB_NAME" name="seach_JOB_NAME" value=""/>
					</td>
					-->
				</tr>
				<tr>
					<td>日期：</td>
					<td>
						<select id="seach_YEAR" name="seach_YEAR" style="width:75px">
					    	<option value=""><%--请选择 --%>
					    		<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/>
					    	</option>
							<c:forEach var="i" begin="2014" end="2020" step="1"> 
						    	<option value="${i}" <c:if test="${YEAR eq i }">selected</c:if> >${i}</option>
						    </c:forEach> 
						 </select>
					</td>
					<td>法人：</td>
					<td>
						<select name="seach_CPNY_ID_dab" id="seach_CPNY_ID_dab" disabled="disabled">
							<c:forEach items="${companyList}" var="item" varStatus="i">
								<option value="${item.CPNY_ID}"
									<c:if test="${defaultCpny eq item.CPNY_ID}">selected</c:if>>
									${item.CPNY_ID}
								</option>
							</c:forEach>
						</select>
					</td>
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
		<input type="hidden" id="DEPTNO" name="DEPTNO" value="" />
		<input type="hidden" id="ATT_CPNYID" name="ATT_CPNYID" value="" />
		<input type="hidden" id="SUBSD_NAME" name="SUBSD_NAME" value="" />
		<input type="hidden" id="JOB_NAME" name="JOB_NAME" value="" />
		<input type="hidden" id="YEAR" name="YEAR" value="" />
		<input type="hidden" id="reportName" name="reportName" value="attRetrieveJobtpAttYear" />
		<input type="hidden" id="suffix" name="suffix" value="" />
	</form>
	<div id="showPDFPop" ></div>
</div>
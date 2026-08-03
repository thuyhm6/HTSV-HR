<%@ page contentType="text/html; charset=UTF-8" language="java"errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

$(function() {
	$("#PROD_TP000").hide();
});
function exportIncentiveCalcList(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	var sform = document.getElementById("bankForm");
	var reportName = $("#reportName").val();
	var PROD_NM = sform.seach_PROD_TP[sform.seach_PROD_TP.selectedIndex].text;
	var PROD_TP = sform.seach_PROD_TP.value;
	var SUBSD_CD = $("#SUBSD_CD").val();
	
	if(suffix == 'xls'){
			document.getElementById("importExcelDialogLink").innerHTML = "设置导出文件密码";
			var sform = document.getElementById("bankForm");
			var eForm = document.getElementById("excelExportForm_rpb0102");
			eForm.SUBSD_CD.value	= SUBSD_CD;
			eForm.ORG_ID.value	= sform.seach_ORG_ID.value;
			eForm.JOB_TP.value 		= sform.seach_JOB_TP.value;
			eForm.PROD_TP.value 		= PROD_TP;
			eForm.PROD_NM.value 		= 	PROD_NM;
			eForm.ATT_MON.value 		= sform.seach_YEAR.value+''+sform.seach_MONTH.value;
			eForm.reportName.value 		= reportName;
			eForm.suffix.value 		= $('input[name="suffix"]:checked').val();
			$("#importExcelDialog_rpB0102").attr('href', "/sys/encryptExcel"
					+"?exportFunName=/report/pac07/exportDatilyReport2"
					+"&navTabId=rpt0102"           
					+"&formId=excelExportForm_rpb0102");
			$("#importExcelDialog_rpB0102").attr('width', "300");
			$("#importExcelDialog_rpB0102").attr('height', "150");
			$("#importExcelDialog_rpB0102").click();
	}else if(suffix == 'pdf' && report == 'save'){
	    //$("#result").attr("src","");
		$("#bankForm").attr("target","");
		$("#bankForm").attr("action","/report/pac07/exportDatilyReport2"+"?JOB_TP="+sform.seach_JOB_TP.value);
		$("#ATT_MON").attr('value',sform.seach_YEAR.value+''+sform.seach_MONTH.value);
		$("#PROD_TP").attr('value',PROD_TP);
		$("#PROD_NM").attr('value',PROD_NM);
		$("#bankForm").submit();
	}else if(suffix == 'html' || report == 'display'){
	   /** $("#result").attr("src","/resources/reportFile/blank.html");
		$("#bankForm").attr("action","/report/pac07/exportDatilyHtmlReport2" 
									  +"?SUBSD_CD="+SUBSD_CD
									  +"&ORG_ID="+sform.seach_ORG_ID.value
		                              +"&JOB_TP="+sform.seach_JOB_TP.value
		                              +"&PROD_TP="+PROD_TP
	 		                          +"&PROD_NM="+PROD_NM
		                              +"&ATT_MON="+sform.seach_YEAR.value+''+sform.seach_MONTH.value
		                              +"&reportName="+reportName
		                              +"&JOB_NAME="+sform.seach_JOB_NAME.value
		                              +"&suffix="+$('input[name="suffix"]:checked').val());
		$("#bankForm").attr("target","result");
		$("#bankForm").submit();*/

		var sform = document.getElementById("bankForm");
		var options = {mask:true, 
                    width:1000, height:600,
                    drawable:true,
                    resizable:true
                }; 
		//查询报表的参数 一定要用@进行连接 否则导致次条件无效
		var param = "@SUBSD_CD="+SUBSD_CD
        +"@ATT_MON="+sform.seach_YEAR.value+''+sform.seach_MONTH.value
        +"@reportName="+sform.reportName.value
        +"@JOB_NAME="+sform.seach_JOB_NAME.value
        +"@JOB_TP="+sform.seach_JOB_TP.value
        +"@PROD_NM="+sform.seach_PROD_TP[sform.seach_PROD_TP.selectedIndex].text
        +"@PROD_TP="+sform.seach_PROD_TP.value
        +"@ORG_ID="+sform.seach_ORG_ID.value
        +"@suffix="+$('input[name="suffix"]:checked').val();
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac07/exportDatilyHtmlReport2"
				, "showPDFPop", "部门工资明细",options);
	}
	
}

function onChangeType(){
	var no = $("#seach_JOB_TP").val();
	//alert(no);
	if (no == "211814"){
		$("#PROD_TP000").show();
	} else {
		$("#PROD_TP000").hide();
	}
}
</script>
<div class="pageHeader">
	<a id="importExcelDialog_rpB0102" href="#" target="dialog" mask="true"><span
		id="importExcelDialogLink" style="display: none"></span></a> 
	<a id="displayDetail" href="#" target="ajax" rel="deptMon"></a>
	<form action="/report/pac04/exportDatilyReport2" id="bankForm" rel="deptMon" method="post">
	<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="${defaultCpny}" />
	<input type="hidden" id="seach_SUBSD_CD" name="seach_SUBSD_CD" value="${defaultCpny}" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>部门：</td>
					<td>
						<ait:deptList name="seach_ORG_ID" cpnyId="${defaultCpny}" id="seachBankDept"/>
						<ait:deptTreeIcon name="seach_ORG_ID" cpnyId="${defaultCpny}" limit="pa" id="seachBankDept" selected="${DEPTNO}" /></td>
					</td>
					<td>人员类型组：</td>
						<td>
						<ait:SelectEmpTypeCode name="seach_JOB_TP" selected="${EMP_TYPE_GROUP}" id="seach_JOB_TP"  limit="pa" type="group" onChangeName="onChangeType()"/>
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
						<select name="reportName" id="reportName" disabled="disabled">
							<c:forEach items="${companyList}" var="item" varStatus="i">
								<option value="reportType${item.CPNY_ID}"
									<c:if test="${defaultCpny eq item.CPNY_ID}">selected</c:if>>
									${item.CPNY_ID}
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr id="PROD_TP000">
				     <td>产品类型:</td>
					<td ><ait:selectSyCode parentNo='211424'  name='seach_PROD_TP' limit='all'/>
					<input type="hidden" id="PROD_NM" name="PROD_NM" value=""/>
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
	<form id="excelExportForm_rpb0102" name="excelExportForm_rpb0102" method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="ORG_ID" name="ORG_ID" value="" />
		<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="${defaultCpny}" />
		<input type="hidden" id="JOB_TP" name="JOB_TP" value="" />
		<input type="hidden" id="ATT_MON" name="ATT_MON" value="" />
		<input type="hidden" id="PROD_TP" name="PROD_TP" value="" />
		<input type="hidden" id="PROD_NM" name="PROD_NM" value="" />
		<input type="hidden" id="reportName" name="reportName" value="" />
		<input type="hidden" id="suffix" name="suffix" value="" />
	</form>
	<!--  
	<iframe id=result name=result width=100% height=465 frameborder=0 scrolling=auto src="/resources/reportFile/blank.html"></iframe>
	-->
	<div id="showPDFPop" ></div>
</div>

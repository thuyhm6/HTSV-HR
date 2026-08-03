<%@ page contentType="text/html; charset=UTF-8" language="java"errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function exportIncentiveCalcList(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	var sform = document.getElementById("onlyFormDept");
	var JOB_NAME = sform.seach_JOB_TP[sform.seach_JOB_TP.selectedIndex].text;
	var JOB_TP = sform.seach_JOB_TP.value;
	var SUBSD_NAME = $("#viewEmpInfoList_seachDept").val();
	var reportName = $("#reportName").val();
	var SUBSD_CD = $("#SUBSD_CD").val();
	var ORG_ID = $(":input[sysLong='viewEmpInfoList_seachDept']").val();
	if(suffix == 'xls'){
		document.getElementById("rp0102Link").innerHTML = "设置导出文件密码";
		var eForm = document.getElementById("excelExportForm_rp0102Dept"); 
		eForm.SUBSD_CD.value	= SUBSD_CD;
		eForm.JOB_TP.value 		= sform.seach_JOB_TP.value;
		eForm.SUBSD_NAME.value	= SUBSD_NAME;
		eForm.JOB_NAME.value 		= JOB_NAME;
		eForm.ATT_MON.value 		= sform.seach_YEAR.value+''+sform.seach_MONTH.value;
		eForm.ATT_MON_XS.value 		= sform.seach_YEAR.value+'/'+sform.seach_MONTH.value;
		eForm.reportName.value 		= reportName;
		eForm.ORG_ID.value          = ORG_ID;
		eForm.suffix.value 		= $('input[name="suffix"]:checked').val();
		$("#importExcelDialog_rp0102Dept").attr('href', "/sys/encryptExcel"
				+"?exportFunName=/report/pac04/exportDatilyReport"
				+"&navTabId=rpt0102"
				+"&formId=excelExportForm_rp0102Dept");
		$("#importExcelDialog_rp0102Dept").attr('width', "300");
		$("#importExcelDialog_rp0102Dept").attr('height', "150");
		$("#importExcelDialog_rp0102Dept").click();
	}else if(suffix == 'pdf'  && report == 'save'){
		$("#onlyFormDept").attr("target","");
		$("#onlyFormDept").attr("action","/report/pac04/exportDatilyReport");
		$("#SUBSD_CD").attr('value',SUBSD_CD);
		$("#ATT_MON").attr('value',sform.seach_YEAR.value+''+sform.seach_MONTH.value);
		$("#ATT_MON_XS").attr('value',sform.seach_YEAR.value+'/'+sform.seach_MONTH.value);
		$("#JOB_NAME").attr('value',JOB_NAME);
		$("#JOB_TP").attr('value',JOB_TP);
		$("#SUBSD_NAME").attr('value',SUBSD_NAME);
		$("#ORG_ID").attr('value',ORG_ID);
		$("#onlyFormDept").submit();
	}else if(suffix == 'html'|| report == 'display'){
		/**新页面显示*/
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
        +"@SUBSD_NAME="+encodeURI(SUBSD_NAME)
        +"@JOB_NAME="+encodeURI(JOB_NAME)
        +"@ORG_ID="+ORG_ID
        +"@suffix="+$('input[name="suffix"]:checked').val();
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac04/exportDatilyHtmlReport"
				, "showPDFPop", "部门月考勤报表",options);        
	}
}
</script>
<div class="pageHeader">
	<a id="importExcelDialog_rp0102Dept" href="#" target="dialog" mask="true"><span
		id="rp0102Link" style="display: none"></span></a> 
	<a id="displayMonth" href="" target="ajax" rel="deptMon"></a>
	<form action="/report/pac04/exportDatilyReport" id="onlyFormDept" rel="deptMon" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>部门：</td>
					<td>
						<ait:deptList name="seach_ORG_ID" cpnyId="${defaultCpny}" id="viewEmpInfoList_seachDept"/>
						<ait:deptTreeIcon name="seach_ORG_ID" cpnyId="${defaultCpny}" limit="ar" id="viewEmpInfoList_seachDept" selected="${ORG_ID}"/>
						<input type="hidden" id="SUBSD_NAME" name="seach_SUBSD_NAME" value=""/>
						<input type="hidden" id="ORG_ID" name="seach_ORG_ID" value=""/>
						<input type="hidden" id="JOB_NAME" name="seach_JOB_NAME" value=""/>
						<input type="hidden" id="SUBSD_CD" name="seach_SUBSD_CD" value="${defaultCpny}"/>
						<input type="hidden" id="reportName" name="reportName" value="attRetrieveMonthlyAttSummary${defaultCpny}"/>
					</td>
					<c:if test="${defaultCpny ne 'LGEND'}">
					<td width="10%" style="text-align:center">人员类型组：</td>
						<td>
							<ait:SelectEmpTypeCode name="seach_JOB_TP" selected="${EMP_TYPE_GROUP}"  limit="ar" type="group" />
					 	</td>
					</c:if>
					<c:if test="${defaultCpny eq 'LGEND'}">
					<td width="10%" style="text-align:center">人员类型：</td>
						<td>
							<ait:SelectEmpTypeCode   name="seach_JOB_TP" selected="${EMP_TYPE}" cnpyID="${defaultCpny}" limit="ar"/>
					 	</td>
					</c:if> 
				</tr>
				<tr>
					<td>日期：</td>
					<td>
						<ait:date yearName="seach_YEAR" yearSelected="${YEAR}" monthName="seach_MONTH" monthSelected="${MONTH}"/>
						<input type="hidden" id="ATT_MON" name="ATT_MON" value=""/>
						<input type="hidden" id="ATT_MON_XS" name="ATT_MON_XS" value=""/>
					</td>
					<td>法人：</td>
					<td>
						<select name="reportName" disabled="disabled">
							<c:forEach items="${companyList}" var="item" varStatus="i">
								<option value="attRetrieveMonthlyAttSummary${item.CPNY_ID}"
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
						<input type="radio" name="suffix" value="pdf" checked onClick="result.location.href='/resources/reportFile/blank.html'"/>pdf
						<input type="radio" name="suffix" value="xls" onClick="result.location.href='/resources/reportFile/blank.html'"/>xls
						<input type="radio" name="suffix" value="html" onClick="result.location.href='/resources/reportFile/blank.html'"/>html
					</td>
					<td>Report Type：</td>
					<td>
						<input type="radio" name="reportType" value="display" checked/>display
						<input type="radio" name="reportType" value="save"/>save
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
	<form id="excelExportForm_rp0102Dept" name="excelExportForm_rp0102Dept" method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="" />
		<input type="hidden" id="JOB_TP" name="JOB_TP" value="" />
		<input type="hidden" id="SUBSD_NAME" name="SUBSD_NAME" value="" />
		<input type="hidden" id="JOB_NAME" name="JOB_NAME" value="" />
		<input type="hidden" id="ORG_ID" name="ORG_ID" value="" />
		<input type="hidden" id="ATT_MON" name="ATT_MON" value="" />
		<input type="hidden" id="reportName" name="reportName" value="attRetrieveMonthlyAttSummary${defaultCpny}" />
		<input type="hidden" id="suffix" name="suffix" value="" />
		<input type="hidden" id="ATT_MON_XS" name="ATT_MON_XS" value=""/>
	</form>
	<div id="showPDFPop" ></div>
</div>

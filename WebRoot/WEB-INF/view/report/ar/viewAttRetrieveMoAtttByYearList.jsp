<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function exportIncentiveCalcList(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	var sform = document.getElementById("onlyFormkaoqin");
	var JOB_NAME = sform.seach_JOB_TP[sform.seach_JOB_TP.selectedIndex].text;
	var reportName = $("#reportName").val();
	var SUBSD_CD = $("#SUBSD_CD").val();
	var ORG_ID = $(":input[sysLong='seachDept']").val();
	if(suffix == 'xls'){
		document.getElementById("rp0102Link").innerHTML = "设置导出文件密码";
		var eForm = document.getElementById("excelExportForm_rp0102"); 
		eForm.SUBSD_CD.value	= SUBSD_CD;
		eForm.ORG_ID.value	= ORG_ID;
		eForm.JOB_NAME.value	= JOB_NAME;
		eForm.JOB_TP.value 		= sform.seach_JOB_TP.value;
		eForm.ATT_MON_START.value 		= sform.seach_YEAR_START.value+''+sform.seach_MONTH_START.value;
		eForm.ATT_MON_END.value 		= sform.seach_YEAR_END.value+''+sform.seach_MONTH_END.value;
		eForm.ATT_MON_S.value 		= sform.seach_YEAR_START.value+'/'+sform.seach_MONTH_START.value;
		eForm.ATT_MON_E.value 		= sform.seach_YEAR_END.value+'/'+sform.seach_MONTH_END.value;
		eForm.reportName.value 		= reportName;
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
		$("#onlyFormkaoqin").attr("target","");
		$("#onlyFormkaoqin").attr("action","/report/pac04/exportDatilyReport");
		$("#ATT_MON_START").attr('value',sform.seach_YEAR_START.value+''+sform.seach_MONTH_START.value);
		$("#JOB_NAME").attr('value',JOB_NAME);
		$("#ATT_MON_END").attr('value',sform.seach_YEAR_END.value+''+sform.seach_MONTH_END.value);
		$("#ATT_MON_S").attr('value',sform.seach_YEAR_START.value+'/'+sform.seach_MONTH_START.value);
		$("#ATT_MON_E").attr('value',sform.seach_YEAR_END.value+'/'+sform.seach_MONTH_END.value);
		$("#onlyFormkaoqin").submit();
	}else if(suffix == 'html' || report == 'display'){
		var options = {mask:true, 
                    width:1000, height:600,
                    drawable:true,
                    resizable:true
                }; 
		//查询报表的参数 一定要用@进行连接 否则导致次条件无效
		var param = "SUBSD_CD="+SUBSD_CD
        +"@JOB_TP="+sform.seach_JOB_TP.value
        +"@ATT_MON_START="+sform.seach_YEAR_START.value+''+sform.seach_MONTH_START.value
        +"@ATT_MON_END="+sform.seach_YEAR_END.value+''+sform.seach_MONTH_END.value
        +"@reportName="+reportName
        +"@JOB_NAME="+encodeURI(JOB_NAME)
        +"@ATT_MON_S="+sform.seach_YEAR_START.value+'/'+sform.seach_MONTH_START.value
        +"@ATT_MON_E="+sform.seach_YEAR_END.value+'/'+sform.seach_MONTH_END.value
        +"@ORG_ID="+ORG_ID
        +"@suffix="+suffix;
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac04/exportDatilyHtmlReport"
				, "showPDFPop", "年度考勤总结报表",options);
	    }
}
</script>
<div class="pageHeader">
	<a id="importExcelDialog_rp0102" href="#" target="dialog" mask="true"><span
		id="rp0102Link" style="display: none"></span></a> 
	<a id="displayDept" href="#" target="ajax" rel="deptMon"></a>
	<form action="/report/pac04/exportDatilyReport" id="onlyFormkaoqin" rel="deptMon" method="post">
	<c:if test="${defaultCpny eq 'TSTO'}">
		<input type="hidden" id="reportName" name="reportName" value="attRetrieveMoAtttByYear" />
		</c:if>
		<c:if test="${defaultCpny ne 'TSTO'}">
		<input type="hidden" id="reportName" name="reportName" value="attRetrieveMoAtttByYear${defaultCpny }" />
		</c:if>
		<div class="searchBar">
		    <table class="searchContent">
				<tr>
					<td>部门：</td>
					<td>
						<ait:deptList name="seach_ORG_ID" cpnyId="${defaultCpny}" id="seachDept"/>
						<ait:deptTreeIcon name="seach_ORG_ID" cpnyId="${defaultCpny}" limit="ar" id="seachDept" selected="${ORG_ID}"/>
						<input type="hidden" id="SUBSD_CD" name="seach_SUBSD_CD" value="${defaultCpny}"/>
						</td>
					<c:if test="${defaultCpny ne 'LGEND'}">
					<td width="10%" style="text-align:center">人员类型组：</td>
						<td>
							<ait:SelectEmpTypeCode name="seach_JOB_TP" selected="${EMP_TYPE_GROUP}"  limit="ar" type="group" />
					 	    <input type="hidden" id="JOB_NAME" name="JOB_NAME" value=""/>
					 	</td>
					</c:if>
					<c:if test="${defaultCpny eq 'LGEND'}">
					<td width="10%" style="text-align:center">人员类型：</td>
						<td>
							<ait:SelectEmpTypeCode   name="seach_JOB_TP" selected="${EMP_TYPE}" cnpyID="${defaultCpny}" limit="ar"/>
					 	    <input type="hidden" id="JOB_NAME" name="JOB_NAME" value=""/>
					 	</td>
					</c:if> 
				</tr>
				<tr>
					<td>日期：</td>
					<td>
						<ait:date yearName="seach_YEAR_START" yearSelected="${YEAR}" monthName="seach_MONTH_START" monthSelected="${MONTH}"/>~
						<ait:date yearName="seach_YEAR_END" yearSelected="${YEAR}" monthName="seach_MONTH_END" monthSelected="${MONTH}"/>
						<input type="hidden" id="ATT_MON_START" name="ATT_MON_START" value=""/>
						<input type="hidden" id="ATT_MON_END" name="ATT_MON_END" value=""/>
						<input type="hidden" id="ATT_MON_S" name="ATT_MON_S" value=""/>
						<input type="hidden" id="ATT_MON_E" name="ATT_MON_E" value=""/>
					</td>
					<td>法人：</td>
					<td>
						<select name="" disabled="disabled">
							<c:forEach items="${companyList}" var="item" varStatus="i">
								<option value="attRetrieveMoAtttByYear"
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
						<input type="radio" name="suffix" value="xls"  onClick="result.location.href='/resources/reportFile/blank.html'"/>xls
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
	<form id="excelExportForm_rp0102" name="excelExportForm_rp0102" method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="" />
		<input type="hidden" id="ORG_ID" name="ORG_ID" value="" />
		<input type="hidden" id="JOB_TP" name="JOB_TP" value="" />
		<input type="hidden" id="JOB_NAME" name="JOB_NAME" value="" />
		<input type="hidden" id="ATT_MON_START" name="ATT_MON_START" value="" />
		<input type="hidden" id="ATT_MON_END" name="ATT_MON_END" value="" />
		<input type="hidden" id="ATT_MON_S" name="ATT_MON_S" value="" />
		<input type="hidden" id="ATT_MON_E" name="ATT_MON_E" value="" />
		<c:if test="${defaultCpny eq 'TSTO'}">
		<input type="hidden" id="reportName" name="reportName" value="attRetrieveMoAtttByYear" />
		</c:if>
		<c:if test="${defaultCpny ne 'TSTO'}">
		<input type="hidden" id="reportName" name="reportName" value="attRetrieveMoAtttByYear${defaultCpny }" />
		</c:if>
		<input type="hidden" id="suffix" name="suffix" value="" />
	</form>
</div>
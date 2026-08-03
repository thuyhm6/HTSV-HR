<%@ page contentType="text/html; charset=UTF-8" language="java"errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function exportIncentiveCalcList(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	var sform = document.getElementById("chrsForm");
	var reportName = $("#reportName").val();
	var PAY_AREA_CD = sform.seach_PAY_AREA_CD.value;
	var SUBSD_CD = $("#SUBSD_CD").val();
	var ORG_ID = $(":input[sysLong='viewEmpInfoList_seachDeptDD']").val();
	if(suffix == 'xls'){   
		document.getElementById("rpC0102Link").innerHTML = "设置导出文件密码";
		var eForm = document.getElementById("excelExportForm_rpC0102"); 
		eForm.PAY_AREA_CD.value	= PAY_AREA_CD;
		eForm.SUBSD_CD.value	= SUBSD_CD;
		eForm.JOB_TP.value 		= sform.seach_JOB_TP.value;
		eForm.ORG_ID.value 		= ORG_ID;
		eForm.ATT_MON.value 		= sform.seach_YEAR.value+''+sform.seach_MONTH.value;
		eForm.ATT_MON_XS.value 		= sform.seach_YEAR.value+'/'+sform.seach_MONTH.value;
		eForm.reportName.value 		= reportName;
		eForm.suffix.value 		= $('input[name="suffix"]:checked').val();
		$("#importExcelDialog_rpC0102").attr('href', "/sys/encryptExcel"
				+"?exportFunName=/report/pac04/exportDatilyReport"
				+"&navTabId=rpt0102"
				+"&formId=excelExportForm_rpC0102");
		$("#importExcelDialog_rpC0102").attr('width', "300");
		$("#importExcelDialog_rpC0102").attr('height', "150");
		$("#importExcelDialog_rpC0102").click();
	}else if(suffix == 'pdf' && report == 'save'){
	    //$("#result").attr("src","");
		$("#chrsForm").attr("target","");
		$("#chrsForm").attr("action","/report/pac04/exportDatilyReport");
		$("#ORG_ID").attr('value',ORG_ID);
		$("#ATT_MON").attr('value',sform.seach_YEAR.value+''+sform.seach_MONTH.value);
		$("#ATT_MON_XS").attr('value',sform.seach_YEAR.value+'/'+sform.seach_MONTH.value);
		$("#chrsForm").submit();
	}else if(report == 'display' || suffix == 'html'){
		//dialog的参数
		var options = {mask:true, 
                    width:1000, height:600,
                    drawable:true,
                    resizable:true
                }; 
		//查询报表的参数 一定要用@进行连接 否则导致次条件无效
		var param = "SUBSD_CD="+SUBSD_CD
        +"@JOB_TP="+sform.seach_JOB_TP.value
        +"@ATT_MON="+sform.seach_YEAR.value+''+sform.seach_MONTH.value
        +"@ATT_MON_XS="+sform.seach_YEAR.value+'/'+sform.seach_MONTH.value
        +"@reportName="+reportName
        +"@suffix="+suffix
        +"@ORG_ID="+ORG_ID
        +"@PAY_AREA_CD="+PAY_AREA_CD;
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac04/exportDatilyHtmlReport"
				, "showPDFPop", "人事工资财务核对",options);
	}
}
</script>
<div class="pageHeader">
	<a id="importExcelDialog_rpC0102" href="#" target="dialog" mask="true"><span
		id="rpC0102Link" style="display: none"></span></a> 
	<a id="displayChrs" href="#" target="ajax" rel="deptMon"></a>
	<form action="/report/pac04/exportDatilyReport" id="chrsForm" rel="deptMon" method="post">
	<input type="hidden" id="SUBSD_CD" name="seach_SUBSD_CD" value="${defaultCpny}"/>
	<c:if test="${defaultCpny eq 'TSTO'}">
		<input type="hidden" id="reportName" name="reportName" value="PayChrsAccMappingLGECH" />
		</c:if>
		<c:if test="${defaultCpny ne 'TSTO'}">
		<input type="hidden" id="reportName" name="reportName" value="PayChrsAccMapping" />
		</c:if>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
				    <c:if test="${defaultCpny eq 'TSTO'}">
					<td>大区：</td>
					<td>
						<ait:deptTreeMulti id="seach_PAY_AREA_CD" name="seach_PAY_AREA_NM" limit="pa" level="2" selected="${PAY_AREA_CD}" selectedNm="${PAY_AREA_NM}" />
						<input type="hidden" name="PAY_AREA_CD" id="PAY_AREA_CD" value=""/>
					</td>
					</c:if>
					<c:if test="${defaultCpny ne 'TSTO'}">
					<td>部门：</td>
					<td>
						<ait:deptList name="seach_ORG_ID" cpnyId="${defaultCpny}" id="viewEmpInfoList_seachDeptDD"/>
						<ait:deptTreeIcon name="seach_ORG_ID" cpnyId="${defaultCpny}" limit="pa" id="viewEmpInfoList_seachDeptDD" selected="${DEPTNO}"/>
						<input type="hidden" id="ORG_ID" name="seach_ORG_ID" value=""/>
						<input type="hidden" name="PAY_AREA_CD" id="PAY_AREA_CD" value="${defaultCpny}"/>
						<input type="hidden" name="seach_PAY_AREA_CD" id="seach_PAY_AREA_CD" value="${defaultCpny}"/>
					</td>
					</c:if>
					<td><c:if test="${defaultCpny ne'LGEND'}">
						人员类型组：
						</c:if>
						<c:if test="${defaultCpny eq'LGEND'}">
						&nbsp人员类型：
						</c:if></td>
					<td>
					 	<c:if test="${defaultCpny ne'LGEND'}">
						<ait:SelectEmpTypeCode name="seach_JOB_TP" selected="${EMP_TYPE_GROUP}" limit="ar" type="group" />
						</c:if>
						<c:if test="${defaultCpny eq'LGEND'}">
						<ait:SelectEmpTypeCode name="seach_JOB_TP" selected="${EMP_TYPE_GROUP}" limit="ar"  />
						</c:if>
					</td>
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
								<option value=""
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
	<form id="excelExportForm_rpC0102" name="excelExportForm_rpC0102" method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="PAY_AREA_CD" name="PAY_AREA_CD" value="" />
		<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="" />
		<input type="hidden" id="ORG_ID" name="ORG_ID" value="" />
		<input type="hidden" id="JOB_TP" name="JOB_TP" value="" />
		<input type="hidden" id="ATT_MON" name="ATT_MON" value="" />
		<c:if test="${defaultCpny eq 'TSTO'}">
		<input type="hidden" id="reportName" name="reportName" value="PayChrsAccMappingLGECH" />
		</c:if>
		<c:if test="${defaultCpny ne 'TSTO'}">  
		<input type="hidden" id="reportName" name="reportName" value="PayChrsAccMapping" />
		</c:if>
		<input type="hidden" id="suffix" name="suffix" value="" />
		<input type="hidden" id="ATT_MON_XS" name="ATT_MON_XS" value=""/>
	</form>
	<div id="showPDFPop" ></div>
</div>

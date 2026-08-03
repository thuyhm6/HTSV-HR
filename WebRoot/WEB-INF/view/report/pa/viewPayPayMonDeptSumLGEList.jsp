<%@ page contentType="text/html; charset=UTF-8" language="java"errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function exportIncentivePayMonList(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	var sform = document.getElementById("onlyFormPayMon");
	var JOB_TP = sform.seach_JOB_TP.value;
	var reportName = $("#reportName").val();
	var SUBSD_CD = $("#seach_SUBSD_CD").val();
	var ORG_ID = $(":input[sysLong='viewEmpInfoList_seachPayMon']").val();
	if(suffix == 'xls'){
		document.getElementById("rp0103Link").innerHTML = "设置导出文件密码";
		var eForm = document.getElementById("excelExportForm_rp0103PayMon"); 
		eForm.SUBSD_CD.value	= SUBSD_CD;
		eForm.JOB_TP.value 		= JOB_TP;
		eForm.PA_MONTH.value 		= sform.seach_YEAR.value+''+sform.seach_MONTH.value;
		eForm.reportName.value 		= reportName;
		eForm.ORG_ID.value          = ORG_ID;
		eForm.suffix.value 		= $('input[name="suffix"]:checked').val();
		$("#importExcelDialog_rp0103PayMon").attr('href', "/sys/encryptExcel"
				+"?exportFunName=/report/pac04/exportDatilyReport"
				+"&navTabId=rpt0103"
				+"&formId=excelExportForm_rp0103PayMon");
		$("#importExcelDialog_rp0103PayMon").attr('width', "300");
		$("#importExcelDialog_rp0103PayMon").attr('height', "150");
		$("#importExcelDialog_rp0103PayMon").click();
	}else if(suffix == 'pdf'  && report == 'save'){
		$("#onlyFormPayMon").attr("target","");
		$("#onlyFormPayMon").attr("action","/report/pac04/exportDatilyReport");
		$("#SUBSD_CD").attr('value',SUBSD_CD);
		$("#PA_MONTH").attr('value',sform.seach_YEAR.value+''+sform.seach_MONTH.value);
		$("#JOB_TP").attr('value',JOB_TP);
		$("#ORG_ID").attr('value',ORG_ID);
		$("#onlyFormPayMon").submit();
	}else if(suffix == 'html'|| report == 'display'){
		/**新页面显示*/
		var options = {mask:true, 
                    width:1000, height:600,
                    drawable:true,
                    resizable:true
                }; 
		//查询报表的参数 一定要用@进行连接 否则导致次条件无效
		var param = "SUBSD_CD="+SUBSD_CD
        +"@JOB_TP="+sform.seach_JOB_TP.value
        +"@PA_MONTH="+sform.seach_YEAR.value+''+sform.seach_MONTH.value
        +"@reportName="+reportName
        +"@ORG_ID="+ORG_ID
        +"@suffix="+$('input[name="suffix"]:checked').val();
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac04/exportDatilyHtmlReport"
				, "showPDFPop", "部门工资月汇总报表",options);
	}
}

function getCheckValue(){
	var jobtp=new Array();
	$("input[name='seach_JOB_TP_check']:checked").each(function(){
	          //alert( $(this).attr('value') );
	          jobtp.push($(this).val());	    	
		});
	$("#seach_JOB_TP").attr('value',jobtp.join(','));
	}
</script>
<div class="pageHeader">
	<a id="importExcelDialog_rp0103PayMon" href="#" target="dialog" mask="true"><span
		id="rp0103Link" style="display: none"></span></a> 
	<a id="displayDept" href="#" target="ajax" rel="deptMon"></a>
	<form action="/report/pac04/exportDatilyReport" id="onlyFormPayMon" rel="deptMon" method="post">
	<input type="hidden" id="reportName" name="reportName" value="payPayMonDeptSum${defaultCpny}" />
		<div class="searchBar">
		    <table class="searchContent">
				<tr>
					<td>部门：</td>
					<td>
						<ait:deptList name="seach_DEPTNO_TYPE" cpnyId="${defaultCpny}" id="viewEmpInfoList_seachPayMon"/>
			            <ait:deptTreeIcon name="seach_DEPTNO_TYPE" cpnyId="${defaultCpny}" limit="pa" id="viewEmpInfoList_seachPayMon" selected="${DEPTNO_TYPE}"/>
						<input type="hidden" id="ORG_ID" name="seach_ORG_ID" value=""/>
					</td>
					
					
					<td><c:if test="${defaultCpny ne'LGEND' and defaultCpny ne'LGEHZ'}">
						人员类型组：
						</c:if>
						<c:if test="${defaultCpny eq'LGEND' or defaultCpny eq'LGEHZ'}">
						&nbsp;人员类型：
						</c:if></td>
					<td>
						<c:if test="${defaultCpny eq'LGETR'}">
						<ait:SelectEmpTypeCodeCheckBox name="seach_JOB_TP" selected="${EMP_TYPE_GROUP}" limit="pa" type="group"/>
						</c:if>
					 	<c:if test="${defaultCpny ne'LGEND' and defaultCpny ne'LGEHZ'and defaultCpny ne'LGETR'}">
						<ait:SelectEmpTypeCode name="seach_JOB_TP" selected="${EMP_TYPE_GROUP}" limit="pa" type="group" />
						</c:if>
						<c:if test="${defaultCpny eq'LGEND' or defaultCpny eq'LGEHZ'}">
						<ait:SelectEmpTypeCode name="seach_JOB_TP" selected="${EMP_TYPE_GROUP}" limit="pa"  />
						</c:if>
					</td>
					</tr>
					<tr>
					<td>YYYYMM：</td>
					<td>
						<ait:date yearName="seach_YEAR" yearSelected="${YEAR}" monthName="seach_MONTH" monthSelected="${MONTH}"/>
						<input type="hidden" id="PA_MONTH" name="PA_MONTH" value=""/>
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
					<td colspan="3">&nbsp;</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onClick="exportIncentivePayMonList(this,'${request_navTabId}')">
									<!--检索--><spring:message code="public.title.search" />
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
	<form id="excelExportForm_rp0103PayMon" name="excelExportForm_rp0103PayMon" method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="" />
		<input type="hidden" id="JOB_TP" name="JOB_TP" value="" />
		<input type="hidden" id="ORG_ID" name="ORG_ID" value="" />
		<input type="hidden" id="PA_MONTH" name="PA_MONTH" value="" />
		<input type="hidden" id="reportName" name="reportName" value="payPayMonDeptSum${defaultCpny}" />
		<input type="hidden" id="suffix" name="suffix" value="" />
	</form>
	<div id="showPDFPop" ></div>
</div>
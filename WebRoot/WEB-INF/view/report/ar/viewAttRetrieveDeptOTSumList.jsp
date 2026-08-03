<%@ page contentType="text/html; charset=UTF-8" language="java"errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function exportAttRetrieveDeptOTList(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	var sform = document.getElementById("onlyFormDeptOT");
	var JOB_TP = sform.seach_JOB_TP.value;
	var reportName = $("#reportName").val();
	var SUBSD_CD = $("#seach_SUBSD_CD").val();
	var ORG_ID = $(":input[sysLong='viewEmpInfoList_seachDeptOT']").val();
	if(suffix == 'xls'){
		document.getElementById("rp0102Link").innerHTML = "设置导出文件密码";
		var eForm = document.getElementById("excelExportForm_rp0102DeptOT"); 
		eForm.SUBSD_CD.value	= SUBSD_CD;
		eForm.JOB_TP.value 		= JOB_TP;
		eForm.FROM_DATE.value 		= sform.seach_FROM_DATE.value;
		eForm.TO_DATE.value 		= sform.seach_TO_DATE.value;
		eForm.reportName.value 		= reportName;
		eForm.ORG_ID.value          = ORG_ID;
		eForm.suffix.value 		= $('input[name="suffix"]:checked').val();
		$("#importExcelDialog_rp0102DeptOT").attr('href', "/sys/encryptExcel"
				+"?exportFunName=/report/pac04/exportDatilyReport"
				+"&navTabId=rpt0102"
				+"&formId=excelExportForm_rp0102DeptOT");
		$("#importExcelDialog_rp0102DeptOT").attr('width', "300");
		$("#importExcelDialog_rp0102DeptOT").attr('height', "150");
		$("#importExcelDialog_rp0102DeptOT").click();
	}else if(suffix == 'pdf'  && report == 'save'){
		//$("#result").attr("src","");
		$("#onlyFormDeptOT").attr("target","");
		$("#onlyFormDeptOT").attr("action","/report/pac04/exportDatilyReport");
		$("#SUBSD_CD").attr('value',SUBSD_CD);
		$("#FROM_DATE").attr('value',sform.seach_FROM_DATE.value);
		$("#TO_DATE").attr('value',sform.seach_TO_DATE.value);
		$("#JOB_TP").attr('value',JOB_TP);
		$("#ORG_ID").attr('value',ORG_ID);
		$("#onlyFormDeptOT").submit();
	}else if(suffix == 'html'|| report == 'display'){
		/**新页面显示*/
		/* $("#result").attr("src","/resources/reportFile/blank.html");
		$("#onlyFormDeptOT").attr("action","/report/pac04/exportDatilyHtmlReport"
		                            +"?SUBSD_CD="+SUBSD_CD
 		                            +"&JOB_TP="+JOB_TP
 		                            +"&FROM_DATE="+sform.seach_FROM_DATE.value
 		                            +"&TO_DATE="+sform.seach_TO_DATE.value
 		                            +"&reportName="+reportName
 		                            +"&ORG_ID="+ ORG_ID
 		                            );
		$("#onlyFormDeptOT").attr("target","result");
		$("#onlyFormDeptOT").submit(); */
		//dialog的参数
			
		var options = {mask:true, 
                    width:1000, height:600,
                    drawable:true,
                    resizable:true
                }; 
		//查询报表的参数 一定要用@进行连接 否则导致次条件无效
		var param = "SUBSD_CD="+SUBSD_CD
        +"@JOB_TP="+sform.seach_JOB_TP.value
        +"@FROM_DATE="+ sform.seach_FROM_DATE.value
        +"@TO_DATE="+ sform.seach_TO_DATE.value
        +"@reportName="+reportName
        +"@ORG_ID="+ORG_ID
        +"@suffix="+$('input[name="suffix"]:checked').val();
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac04/exportDatilyHtmlReport"
				, "showPDFPop", "部门加班报表",options);
	}
}
</script>
<div class="pageHeader">
	<a id="importExcelDialog_rp0102DeptOT" href="#" target="dialog" mask="true"><span
		id="rp0102Link" style="display: none"></span></a> 
	<a id="displayDept" href="#" target="ajax" rel="deptMon"></a>
	<form action="/report/pac04/exportDatilyReport" id="onlyFormDeptOT" method="post">
	<input type="hidden" id="reportName" name="reportName" value="attRetrieveDeptOTSum" />
		<div class="searchBar">
		    <table class="searchContent">
				<tr>
					<td>部门：</td>
					<td>
						<ait:deptList name="seach_DEPTNO_TYPE" cpnyId="${defaultCpny}" id="viewEmpInfoList_seachDeptOT"/>
			            <ait:deptTreeIcon name="seach_DEPTNO_TYPE" cpnyId="${defaultCpny}" limit="ar" id="viewEmpInfoList_seachDeptOT" selected="${DEPTNO_TYPE}"/>
						<input type="hidden" id="ORG_ID" name="seach_ORG_ID" value=""/>
					</td>
					<td>人员类型组： </td>
						<td>
						<ait:SelectEmpTypeCode name="seach_JOB_TP" selected="${EMP_TYPE_GROUP}"  limit="ar" type="group" />
						</td>
					<td>法人：</td>
					<td>
						<select id="seach_SUBSD_CD" name="seach_SUBSD_CD" disabled="disabled">
							<c:forEach items="${companyList}" var="item" varStatus="i">
								<option value="${item.CPNY_ID}"
									<c:if test="${defaultCpny eq item.CPNY_ID}">selected</c:if>>
									${item.CPNY_ID}
								</option>
							</c:forEach>
						</select>
						<input type="hidden" id="seach_SUBSD_CD" name="seach_SUBSD_CD" value="${defaultCpny}" >
					</td>
				</tr>
				<tr>
					<td>开始日期：</td>
					<td>
						<input type="text" name="seach_FROM_DATE" id="seach_FROM_DATE" class="date" format="yyyyMMdd" readonly="true"  />
						<a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
						<!-- <input type="hidden" id="FROM_DATE" name="seach_FROM_DATE" value=""/> -->
					</td>
						<td>结束日期：</td>
					<td>
						<input type="text" name="seach_TO_DATE" id="seach_TO_DATE" class="date" format="yyyyMMdd" readonly="true"  />
						<a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
						<!-- <input type="hidden" id="TO_DATE" name="seach_TO_DATE" value=""/> -->
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
								<button type="button" onClick="exportAttRetrieveDeptOTList(this,'${request_navTabId}')">
									<!--检索--><spring:message code="public.title.search" />
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
	<form id="excelExportForm_rp0102DeptOT" name="excelExportForm_rp0102DeptOT" method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="" />
		<input type="hidden" id="JOB_TP" name="JOB_TP" value="" />
		<input type="hidden" id="ORG_ID" name="ORG_ID" value="" />
		<input type="hidden" id="FROM_DATE" name="FROM_DATE" value="" />
		<input type="hidden" id="TO_DATE" name="TO_DATE" value="" />
		<input type="hidden" id="reportName" name="reportName" value="attRetrieveDeptOTSum" />
		<input type="hidden" id="suffix" name="suffix" value="" />
	</form>
	<div id="showPDFPop" ></div>
	<!-- <iframe id=result name=result width=100% height=465 frameborder=0 scrolling=auto src="/resources/reportFile/blank.html"></iframe> -->
</div>
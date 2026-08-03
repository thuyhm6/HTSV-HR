<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function exportIncentiveCalcList(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	var sform = document.getElementById("onlyFormWSK");
	var JOB_NAME = sform.seach_JOB_TP[sform.seach_JOB_TP.selectedIndex].text;
	var SUBSD_NAME = $("#seachDept").val();
	var reportName = $("#reportName").val();
	var ATT_MON = $("#ATT_MON").val().replace(/\-/g,"");
	var TO_MON = $("#TO_MON").val();
	if(suffix == 'xls' && report == 'save'){
		
		document.getElementById("importExcelDialog_rp0102Link").innerHTML = "设置导出文件密码";
		var sform = document.getElementById("onlyFormWSK");
		var eForm = document.getElementById("excelExportForm_rp0102WSK");
		eForm.SUBSD_CD.value	= sform.seach_SUBSD_CD.value;
		eForm.JOB_TP.value 		= sform.seach_JOB_TP.value;
		eForm.SUBSD_NAME.value	= SUBSD_NAME;
		eForm.JOB_NAME.value 		= JOB_NAME;
		eForm.ATT_MON.value 		= ATT_MON;
		eForm.TO_MON.value 		= TO_MON;
		eForm.reportName.value 		= reportName;
		eForm.CPNY_ID.value		=sform.seach_CPNY_ID.value;
		eForm.suffix.value 		= $('input[name="suffix"]:checked').val();
		$("#importExcelDialog_rp0102").attr('href', "/sys/encryptExcel"
				+"?exportFunName=/report/pac04/exportDatilyReport"
				+"&navTabId=rpt0102"
				+"&formId=excelExportForm_rp0102WSK");
		$("#importExcelDialog_rp0102").attr('width', "300");
		$("#importExcelDialog_rp0102").attr('height', "150");
		$("#importExcelDialog_rp0102").click();
	}else if(suffix == 'pdf'&& report == 'save'){
		$("#onlyFormWSK").attr("target","");
		$("#onlyFormWSK").attr("action","/report/pac04/exportDatilyReport");
		$("#ATT_MON").attr('value',ATT_MON);
		$("#TO_MON").attr('value',TO_MON);
		$("#JOB_NAME").attr('value',JOB_NAME);
		$("#SUBSD_NAME").attr('value',SUBSD_NAME);
		$("#onlyFormWSK").submit();
	}else if(suffix == 'html'||report == 'display'){
				//dialog的参数
				var options = {mask:true, 
		                    width:1000, height:600,
		                    drawable:true,
		                    resizable:true
		                }; 
				
				//查询报表的参数 一定要用@进行连接 否则导致次条件无效
				var param = "SUBSD_CD="+sform.seach_SUBSD_CD.value
		                            +"@JOB_TP="+sform.seach_JOB_TP.value
		                            +"@ATT_MON="+ATT_MON
		                            +"@TO_MON="+TO_MON
		                           	+"@suffix="+$('input[name="suffix"]:checked').val()
		                            +"@reportName="+sform.reportName.value
		                            +"@CPNY_ID="+sform.seach_CPNY_ID.value
		                            +"@JOB_NAME="+encodeURI(JOB_NAME)
		                            +"@SUBSD_NAME="+encodeURI(SUBSD_NAME);
				//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac04/exportDatilyHtmlReport"
				, "showPDFPop", "员工未刷卡报表",options);
		//<!--$("#onlyFormWSK").attr("target","result");
		//$("#onlyFormWSK").submit();-->
	}
}
</script>
</head>
<div class="pageHeader">
	<a id="importExcelDialog_rp0102" href="#" target="dialog" mask="true"><span
		id="importExcelDialog_rp0102Link" style="display: none"></span></a> 
	<a id="displayDept" href="#" target="ajax" rel="deptMon"></a>
	<form action="/report/pac04/exportDatilyReport" id="onlyFormWSK" rel="htmlReport" method="post">
	<input type="hidden" id="reportName" name="reportName" value="attRetrieveNoCard" />
	<input type="hidden" id="seach_CPNY_ID" name="seach_CPNY_ID" value="${defaultCpny}" />
		<div class="searchBar">
		    <table class="searchContent">
				<tr>
					<td>部门：</td>
					<td>
						<ait:deptList name="seach_SUBSD_CD" cpnyId="${defaultCpny}" id="seachDept"/>
						<ait:deptTreeIcon name="seach_SUBSD_CD" cpnyId="${defaultCpny}" limit="ar" id="seachDept" selected="${SUBSD_CD}"/></td>
						<input type="hidden" id="SUBSD_NAME" name="seach_SUBSD_NAME" value=""/>
					</td>
					<td>
						<c:if test="${defaultCpny ne'LGEND'}">
						人员类型组：
						</c:if>
						<c:if test="${defaultCpny eq'LGEND'}">
						人员类型：
						</c:if>
					</td>
					<td>
						<!--<ait:SelectSyCodeByCpnyID id="seach_JOB_TP" name="seach_JOB_TP" parentNo="211807" selected="${JOB_TP}" cnpyID="${defaultCpny}" limit="all" />
						<select name="seach_JOB_TP">
					        <option value="211807">
								全部	
								</option>
							<c:forEach items="${jobTypeGroupNameList}" var="item" varStatus="i">
								<option value="${item.JOBTYPE_GROUP_NO }"
									<c:if test="${JOB_TP eq item.JOBTYPE_GROUP_NO}">selected</c:if>>
									${item.JOBTYPE_GROUP_NAME}
								</option>
							</c:forEach>
						</select>-->
						<c:if test="${defaultCpny ne'LGEND'}">
						<ait:SelectEmpTypeCode name="seach_JOB_TP" selected="${EMP_TYPE_GROUP}" limit="ar" type="group" />
						</c:if>
						<c:if test="${defaultCpny eq'LGEND'}">
						<ait:SelectEmpTypeCode name="seach_JOB_TP" selected="${EMP_TYPE_GROUP}" limit="ar"  />
						</c:if>
						<input type="hidden" id="JOB_NAME" name="seach_JOB_NAME" value=""/>
					</td>
				</tr>
				<tr>
					<td>开始日期：</td>
					<td>

						<input type="text" id="ATT_MON" name="ATT_MON" class="date required"  onpropertychange="getPOtApplyType();ajaxAdd_add_ot_apply_one_three();"format="yyyyMMdd" readonly="false" /><a class="inputDateButton" href="javascript:;">
						<spring:message code="public.title.choose"/><!-- 选择 --></a>
					</td>
					<td>法人：</td>
					<td>
						<select name="seach_CPNY_ID_dab" disabled="disabled">
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
					<td>截止日期：</td>
					<td>
						<input type="text" id="TO_MON" name="TO_MON" class="date required"  onpropertychange="getPOtApplyType();ajaxAdd_add_ot_apply_one_three();"format="yyyyMMdd" readonly="false" /><a class="inputDateButton" href="javascript:;">
						<spring:message code="public.title.choose"/><!-- 选择 --></a>
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
	<form id="excelExportForm_rp0102WSK" name="excelExportForm_rp0102WSK" method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="" />
		<input type="hidden" id="JOB_TP" name="JOB_TP" value="" />
		<input type="hidden" id="SUBSD_NAME" name="SUBSD_NAME" value="" />
		<input type="hidden" id="JOB_NAME" name="JOB_NAME" value="" />
		<input type="hidden" id="ATT_MON" name="ATT_MON" value="" />
		<input type="hidden" id="TO_MON" name="TO_MON" value="" />
		<input type="hidden" id="reportName" name="reportName" value="attRetrieveNoCard" />
		<input type="hidden" id="suffix" name="suffix" value="" />
		<input type="hidden" id="CPNY_ID" name="CPNY_ID" value="" />
	</form>
	<div id="showPDFPop" ></div>
</div>
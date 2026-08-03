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
	var sform = document.getElementById("onlyFormKG11");
	var JOB_NAME = sform.seach_JOB_TP[sform.seach_JOB_TP.selectedIndex].text;
	var SUBSD_NAME = $("#seachDept").val();
	var ITEMTYPE = $("#ITEMTYPE").val();
	var TIME_TOP=$("#TIME_TOP").val();
	var TIME_DOWN=$("#TIME_DOWN").val();
	var ATT_CPNYID=$("#ATT_CPNYID").val();
	if(suffix == 'xls' && report == 'save'){
		document.getElementById("rp0102Link").innerHTML = "设置导出文件密码";
		var eForm = document.getElementById("excelExportForm_rp0102"); 
		eForm.SUBSD_CD.value	= sform.seach_SUBSD_CD.value;
		eForm.ATT_CPNYID.value	= sform.ATT_CPNYID.value;
		eForm.JOB_TP.value 		= sform.seach_JOB_TP.value;
		eForm.SUBSD_NAME.value	= SUBSD_NAME;
		eForm.JOB_NAME.value 		= JOB_NAME;
		//eForm.ATT_MON.value 		= sform.seach_YEAR.value+''+sform.seach_MONTH.value;
		eForm.TIME_TOP.value		=TIME_TOP;
		eForm.TIME_DOWN.value		=TIME_DOWN;
		eForm.ITEMTYPE.value		=ITEMTYPE;
		eForm.reportName.value 		= sform.reportName.value;
		eForm.suffix.value 		= $('input[name="suffix"]:checked').val();
		$("#importExcelDialog_rp0102KG").attr('href', "/sys/encryptExcel"
				+"?exportFunName=/report/pac04/exportDatilyReport"
				+"&navTabId=rpt0102"
				+"&formId=excelExportForm_rp0102");
		$("#importExcelDialog_rp0102KG").attr('width', "300");
		$("#importExcelDialog_rp0102KG").attr('height', "150");
		$("#importExcelDialog_rp0102KG").click();
	}else if(suffix == 'pdf'  && report == 'save'){
		//$("#ATT_MON").attr('value',sform.seach_YEAR.value+''+sform.seach_MONTH.value);
		//$("#result").attr("src","");
		$("#onlyFormKG11").attr("target","");
		$("#onlyFormKG11").attr("action","/report/pac04/exportDatilyReport");
		$("#JOB_NAME").attr('value',JOB_NAME);
		$("#SUBSD_NAME").attr('value',SUBSD_NAME);
		$("#TIME_TOP").attr('value',TIME_TOP);
		$("#TIME_DOWN").attr('value',TIME_DOWN);
		$("#ITEMTYPE").attr('value',ITEMTYPE);
		$("#onlyFormKG11").submit();
	}else if(suffix == 'html'||report == 'display'){
		/*$("#result").attr("src","/resources/reportFile/blank.html");
		$("#onlyFormKG11").attr("action","/report/pac04/exportDatilyHtmlReport" 
									+"?SUBSD_CD="+sform.seach_SUBSD_CD.value
		                            +"&JOB_TP="+sform.seach_JOB_TP.value
		                            +"&TIME_DOWN="+TIME_DOWN
		                            +"&TIME_TOP="+TIME_TOP
		                            +"&ITEMTYPE="+ITEMTYPE
		                            +"&reportName="+sform.reportName.value
		                            +"&JOB_NAME="+encodeURI(JOB_NAME)
		                            +"&SUBSD_NAME="+encodeURI(SUBSD_NAME)
		                            +"&suffix="+$('input[name="suffix"]:checked').val());
		$("#onlyFormKG11").attr("target","result");
		$("#onlyFormKG11").submit();*/
		//dialog的参数
		var options = {mask:true, 
                    width:1000, height:600,
                    drawable:true,
                    resizable:true
                }; 
		//查询报表的参数 一定要用@进行连接 否则导致次条件无效
		var param = "SUBSD_CD="+sform.seach_SUBSD_CD.value
            +"@JOB_TP="+sform.seach_JOB_TP.value
            +"@TIME_DOWN="+TIME_DOWN
            +"@TIME_TOP="+TIME_TOP
            +"@ITEMTYPE="+ITEMTYPE
            +"@ATT_CPNYID="+ATT_CPNYID
            +"@reportName="+sform.reportName.value
            +"@JOB_NAME="+encodeURI(JOB_NAME)
            +"@SUBSD_NAME="+encodeURI(SUBSD_NAME)
            +"@suffix="+$('input[name="suffix"]:checked').val();
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac04/exportDatilyHtmlReport"
				, "showPDFPop", "员工旷工报表",options);
	}
}
</script>
</head>
<div class="pageHeader">
	<a id="importExcelDialog_rp0102KG" href="#" target="dialog" mask="true"><span
		id="rp0102Link"style="display: none" ></span></a> 
	<a id="displayDept" href="#" target="ajax" rel="deptMon"></a>
	<form action="/report/pac04/exportDatilyReport" id="onlyFormKG11" rel="deptMon" method="post">
	<input type="hidden" id="JOB_NAME" name="seach_JOB_NAME" value=""/>
	<input type="hidden" id="ITEMTYPE" name="seach_ITEMTYPE" value="141443"/><!-- 旷工的代码 -->
	<input type="hidden" id="reportName" name="reportName" value="attRetrieveAbsence" />
	<input type="hidden" id="ATT_CPNYID" name="ATT_CPNYID" value="${defaultCpny}" />
		<div class="searchBar">
		    <table class="searchContent">
				<tr>
					<td>部门：</td>
					<td>
						<ait:deptList name="seach_SUBSD_CD" cpnyId="${defaultCpny}" id="seachDept"/>
						<ait:deptTreeIcon name="seach_SUBSD_CD" cpnyId="${defaultCpny}" limit="ar" id="seachDept" selected="${SUBSD_CD}"/></td>
						<input type="hidden" id="SUBSD_NAME" name="seach_SUBSD_NAME" value=""/>
					</td>
					<td></td>
					<td><c:if test="${defaultCpny ne'LGEND'}">
						人员类型组：
						</c:if>
						<c:if test="${defaultCpny eq'LGEND'}">
						人员类型：
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
					<td>开始日期：</td>
					<td>
						<!--<ait:date yearName="seach_YEAR" yearSelected="${YEAR}" monthName="seach_MONTH" monthSelected="${MONTH}"/>
						<input type="hidden" id="ATT_MON" name="ATT_MON" value=""/>-->
						<input type="text" id="TIME_TOP" name="TIME_TOP" class="date required"  onpropertychange="getPOtApplyType();ajaxAdd_add_ot_apply_one_three();"format="yyyyMMdd" readonly="false" /><a class="inputDateButton" href="javascript:;">
						<spring:message code="public.title.choose"/><!-- 选择 --></a>
					</td>
					<td></td>
					<td>法人：</td>
					<td>
						<select name="ATT_CPNYID_dab" disabled="disabled">
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
					<td>结束日期：</td>
					<td>
						<input type="text" id="TIME_DOWN" name="TIME_DOWN" class="date required"  onpropertychange="getPOtApplyType();ajaxAdd_add_ot_apply_one_three();"format="yyyyMMdd" readonly="false" /><a class="inputDateButton" href="javascript:;">
						<spring:message code="public.title.choose"/><!-- 选择 --></a>
					</td>
				</tr>
				<tr>
					<td>File Type：</td>
					<td>
						<input type="radio" name="suffix" value="pdf" checked onClick="result.location.href='/resources/reportFile/blank.html'"/>pdf
						<input type="radio" name="suffix" value="xls"  onClick="result.location.href='/resources/reportFile/blank.html'"/>xls
						<input type="radio" name="suffix" value="html" onClick="result.location.href='/resources/reportFile/blank.html'"/>html
					</td>
					<td></td>
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
		<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="${defaultCpny}" />
		<input type="hidden" id="ATT_CPNYID" name="ATT_CPNYID" value="${defaultCpny}" />
		<input type="hidden" id="JOB_TP" name="JOB_TP" value="" />
		<input type="hidden" id="SUBSD_NAME" name="SUBSD_NAME" value="" />
		<input type="hidden" id="JOB_NAME" name="JOB_NAME" value="" />
		<!--  <input type="hidden" id="ATT_MON" name="ATT_MON" value="" />-->
		<input type="hidden" id="reportName" name="reportName" value="attRetrieveAbsence" />
		<input type="hidden" id="suffix" name="suffix" value="" />
		<input type="hidden" id="TIME_TOP" name="TIME_TOP" value="" />
		<input type="hidden" id="TIME_DOWN" name="TIME_DOWN" value="" />
		<input type="hidden" id="ITEMTYPE" name="ITEMTYPE" value="141443"/><!-- 旷工的代码 -->
		
	</form>
	<div id="showPDFPop" ></div>
	<!--  <iframe name=result id=result width=100% height=465 style="z-index: 1;" frameborder=0 scrolling=auto src="/resources/reportFile/blank.html"></iframe>-->
</div>
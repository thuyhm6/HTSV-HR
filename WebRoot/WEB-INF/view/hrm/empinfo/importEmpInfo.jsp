<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
function importExcel(){
	var TEMPLATE_TYPE = $("#TEMPLATE_TYPE").val();
	if(TEMPLATE_TYPE == ""){
		alertMsg.error('请先选择模板类型');
	}else{
		$("#importExcelDialog").attr('href','/pa/excelImport/importExcelData?importFunName=/importEmpInfo?TEMPLATE_TYPE=' + TEMPLATE_TYPE);
		$("#importExcelDialog").click();
	}
}
function downloadImportTemplate(){
	var TEMPLATE_TYPE = $("#TEMPLATE_TYPE").val();
	if(TEMPLATE_TYPE == ""){
		alertMsg.error('请先选择模板类型');
	}else{
		window.location.href="/hrm/empinfo/downloadExcelTemplate?TEMPLATE_TYPE=" + TEMPLATE_TYPE;
	}
}
</script>
<a id="importExcel_hrm4533"  href="#" target="navTab" mask="true"><span style="display:none;">人员基本信息导入结果</span></a>
<div class="pageHeader">
	<div class="searchBar">
	<a id="importExcelDialog"  href="" target="dialog" mask="true" width="500" height="200"></a>
	<form id="insertContract" method="post" action="/hrm/contractInfo/insertContract"
	class="pageForm required-validate" onsubmit="return validateCallbackInsertContract(this, navTabAjaxDone)" >
	<table>
		<tr>
			<th><spring:message code="hr.contract.title.mobanxinxi" /><!-- 模板信息 -->&#12288;</th>
			<td><spring:message code="hr.contract.title.daorumoban" /><!-- 导入模板 -->&nbsp;</td>
			<td><ait:SelectSyCodeByCpnyID name="TEMPLATE_TYPE" parentNo="215976" cnpyID="${defaultCpny}" limit="all"/></td>
		</tr>
	</table>
	</form>
		<div class="subBar">
			<a style="float:right; " class="buttonActive" id ="exportExcel" onclick="downloadImportTemplate();" href="#"><span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span></a>
					<div style="float:right; " class="buttonActive"  onclick="importExcel();"><span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span></div>
		</div>
	</div>
</div>
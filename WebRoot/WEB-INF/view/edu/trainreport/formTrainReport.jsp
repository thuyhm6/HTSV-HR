<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

function excelExport(){
	$('#trainReport').submit();
}

</script>
<form id="trainReport" action="/edu/trainreport/formTrainReportExcel" method="post">
	<div>
		<h1>
			<spring:message code="edu.trainreport.KECHENGXINGSHIBIEPEIXUNBAOBIAO.a"/><!--课程形式别培训报表-->
		</h1>
	</div>
<div class="pageHeader" >
<div class="searchBar">
     <div style="margin-left:auto;margin-right:auto;width:97%;" > 
         <table class="user_table" style="text-align:center" width="100%" border="1" cellpadding="2" cellspacing="1">
		<tr>
		<td class="td_title" width="4%"><spring:message code="edu.trainreport.KECHENGLEIBIE.a"/><!--课程类别--></td>
		<td class="td_type" width="4%">
		<ait:SelectSyCodeByCpnyID name="TRAIN_FORM_CODE" id="TRAIN_FORM_CODE"
                    parentNo="14014493" cnpyID="${defaultCpny}" selected="${TRAIN_FORM_CODE }" limit="all" />
		</td>
		<td class="td_type" width="4%">
               <a class="buttonActive" onclick="excelExport()" >
							<span><spring:message code="ar.addempshift.title.excelexport"/><!--excel导出--></span>
			   </a>
        </td>
		</tr>
		</table>
	   </div> 
</div>
</div>
</form>
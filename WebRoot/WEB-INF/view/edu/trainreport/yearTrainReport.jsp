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
<form id="trainReport" action="/edu/trainreport/yearTrainReportExcel" method="post" >
	<div>
		<h1>
			<spring:message code="edu.trainreport.NIANDUBIEPEIXUNBAOBIAO.a"/><!--年度别培训报表-->
		</h1>
	</div>
<div class="pageHeader" >
<div class="searchBar">
     <div style="margin-left:auto;margin-right:auto;width:97%;" > 
         <table class="user_table" style="text-align:center" width="100%" border="1" cellpadding="3" cellspacing="1">
		<tr>
		<td class="td_title" width="4%"><spring:message code="pa.salary.canShu.nianDu"/><!--年度--></td>
		<td class="td_type" width="4%">
		<input name="COURSE_DATE" class="required" onClick="WdatePicker({dateFmt:'yyyy'})" id="COURSE_DATE" type="text" value=""/>
		</td>
		<td class="td_type" width="4%">
               <a class="buttonActive" onclick="excelExport()" >
							<span><spring:message code="ess.infoApply.EXCEL_OUT"/><!--excel导出--></span>
			   </a>
        </td>
		</tr>
		</table>
	   </div> 
</div>
</div>
</form>
<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

function excelExport(){
	$('#trainReport').submit();
}

function panDiffReport(value){
	codeRelation(value,'TRAIN_TYPE_CODE','${TRAIN_TYPE_CODE}');
}
</script>
<form id="trainReport" action="/edu/trainreport/courseTrainReportExcel" method="post" >
	<div>
		<h1>
			<spring:message code="edu.trainreport.KECHENGBIEPEIXUNBAOBIAO.a"/><!--课程别培训报表-->
		</h1>
	</div>
<div class="pageHeader" >
<div class="searchBar">
     <div style="margin-left:auto;margin-right:auto;width:97%;" > 
         <table class="user_table" style="text-align:center" width="100%" border="1" cellpadding="3" cellspacing="1">
		<tr>
		<td class="td_title" width="4%"><spring:message code="liang.hr.viewTraining.title.TRAINING_DIFFERENTIATE"/><!--培训区分--></td>
		<td class="td_type" width="4%">
		<ait:SelectSyCodeByCpnyID name="TRAIN_DIFF_CODE" id="TRAIN_DIFF_CODE"
                    parentNo="14014478" cnpyID="${defaultCpny}" selected="${TRAIN_DIFF_CODE }" limit="all" onChangeName="panDiffReport(this.value)"/>
		</td>
		<td class="td_title" width="4%"><spring:message code="edu.systemManager.PEIXUNLEIXING.a"/><!--培训类型--></td>
		<td class="td_type" width="4%">
	        <select name="TRAIN_TYPE_CODE" id="TRAIN_TYPE_CODE" ></select>
	    </td>
		<td class="td_title" width="4%"><spring:message code="empsubject.subjectNm"/><!--课程名称--></td>
		<td class="td_type" width="4%">
		<input type="text" name="COURSE_NAME_CODE" id="COURSE_NAME_CODE" value="${COURSE_NAME_CODE }">
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
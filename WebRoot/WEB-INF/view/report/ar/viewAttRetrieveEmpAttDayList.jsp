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
	var sform = document.getElementById("onlyForm");
	var SUBSD_CD = $("#search_SUBSD_CD").val();
	var reportName = $("#reportName").val();
	var GNDR = $("#sex").val();
	var SUBSD_HIRE_DATE = $("#jion").val();
	var JOB_POSI_CD = $("#posision").val();
	var JOB_DUTY_CD = $("#duty").val();
	var PROB_END_DATE = $("#end").val();
	var JOB_GRADE = $("#emppostGradeName").val();
	var CN_ORG_NM = $("#empDept").val();
	var PERSON_ID = $("#personId").val();
	if(suffix == 'xls'){
		document.getElementById("rp0102Link").innerHTML = "设置导出文件密码";
		var eForm = document.getElementById("excelExportForm_rp0102"); 
		eForm.ATT_MON.value 		= sform.seach_YEAR.value+''+sform.seach_MONTH.value;
		eForm.ATT_MON_XS.value 		= sform.seach_YEAR.value+'/'+sform.seach_MONTH.value;
		eForm.reportName.value 		= reportName;
		eForm.GNDR.value 		= GNDR;
		eForm.PERSON_ID.value 		= PERSON_ID;
		eForm.SUBSD_HIRE_DATE.value     = SUBSD_HIRE_DATE;
		eForm.JOB_POSI_CD.value 		= JOB_POSI_CD;
		eForm.JOB_DUTY_CD.value 		= JOB_DUTY_CD;
		eForm.PROB_END_DATE.value 		= PROB_END_DATE;
		eForm.JOB_GRADE.value 		= JOB_GRADE;
		eForm.CN_ORG_NM.value 		= CN_ORG_NM;
		eForm.SUBSD_CD.value 		= SUBSD_CD;
		eForm.suffix.value 		= $('input[name="suffix"]:checked').val();
		$("#importExcelDialog_rp0102").attr('href', "/sys/encryptExcel"
				+"?exportFunName=/report/pac04/exportDatilyReport"
				+"&navTabId=rpt0102"
				+"&formId=excelExportForm_rp0102");
		$("#importExcelDialog_rp0102").attr('width', "300");
		$("#importExcelDialog_rp0102").attr('height', "150");
		$("#importExcelDialog_rp0102").click();
	}else if(suffix == 'pdf'  && report == 'save'){
		$("#onlyForm").attr("target","");
		$("#onlyForm").attr("action","/report/pac04/exportDatilyReport");
	    $("#SUBSD_CD").attr('value',SUBSD_CD);
	    $("#PERSON_ID").attr('value',PERSON_ID);
	    $("#ATT_MON").attr('value',sform.seach_YEAR.value+''+sform.seach_MONTH.value);
	    $("#ATT_MON_XS").attr('value',sform.seach_YEAR.value+'/'+sform.seach_MONTH.value);
		$("#onlyForm").submit();
	}else if(suffix == 'html'|| report == 'display'){
	  //dialog的参数
		var options = {mask:true, 
                    width:1000, height:600,
                    drawable:true,
                    resizable:true
                }; 
        //查询报表的参数 一定要用@进行连接 否则导致次条件无效
		var param = "SUBSD_CD="+SUBSD_CD
        +"@ATT_MON="+sform.seach_YEAR.value+''+sform.seach_MONTH.value
        +"@ATT_MON_XS="+sform.seach_YEAR.value+'/'+sform.seach_MONTH.value
        +"@reportName="+reportName
        +"@suffix="+$('input[name="suffix"]:checked').val()
        +"@PERSON_ID="+PERSON_ID
        +"@GNDR="+encodeURI(GNDR)
	    +"@SUBSD_HIRE_DATE="+SUBSD_HIRE_DATE
		+"@JOB_POSI_CD="+JOB_POSI_CD
		+"@PROB_END_DATE="+PROB_END_DATE
		+"@JOB_DUTY_CD="+JOB_DUTY_CD
		+"@JOB_GRADE="+JOB_GRADE
		+"@CN_ORG_NM="+encodeURI(CN_ORG_NM)
        ;
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac04/exportDatilyHtmlReport"
				, "showPDFPop", "员工日考勤报表",options);        
	}
}
</script>
</head>
<div class="pageHeader">
	<a id="importExcelDialog_rp0102" href="#" target="dialog" mask="true"><span
		id="rp0102Link" style="display: none"></span></a> 
	<a id="displayDeptEmp" href="#" target="ajax" rel="deptMon"></a>
	<form action="/report/pac04/exportDatilyReport" id="onlyForm" rel="deptMon" method="post">
	<input type="hidden" id="reportName" name="reportName" value="attRetrieveEmpAttDay" />
	<input type="hidden" id="PERSON_ID" name="PERSON_ID" value="" />
		<div class="searchBar">
		    <table class="searchContent">
				<tr>
				    <td>法人：
						<select name="reportName" disabled="disabled">
							<c:forEach items="${companyList}" var="item" varStatus="i">
								<option value="attRetrieveEmpAttDay"
									<c:if test="${defaultCpny eq item.CPNY_ID}">selected</c:if>>
									${item.CPNY_ID}
								</option>
							</c:forEach>
						</select>
						<input type="hidden" id="search_SUBSD_CD" name="search_SUBSD_CD" value="${defaultCpny}" />
						<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="${defaultCpny}" />
					</td>
					<td>日期：
						<ait:date yearName="seach_YEAR" yearSelected="${YEAR}" monthName="seach_MONTH" monthSelected="${MONTH}"/>
						<input type="hidden" id="ATT_MON" name="ATT_MON" value=""/>
						<input type="hidden" id="ATT_MON_XS" name="ATT_MON_XS" value=""/>
					</td>
					<td>
					         社号/姓名：
						<input id="jsonData" name="jsonData" value="" type="hidden"/>
									<input id="id" name="id" value="11" type="hidden"/>
									<input id="empDept" name="dwz.person.empDept" value="" type="hidden" lookupGroup="person"/>
									<input id="sex" name="dwz.person.sex" value="" type="hidden" lookupGroup="person"/>
									<input id="duty" name="dwz.person.duty" value="" type="hidden" lookupGroup="person"/>
									<input id="posision" name="dwz.person.posision" value="" type="hidden" lookupGroup="person"/>
									<input id="emppostGradeName" name="dwz.person.emppostGradeName" value="" type="hidden" lookupGroup="person"/>
									<input id="end" name="dwz.person.end" value="" type="hidden" lookupGroup="person"/>
									<input id="jion" name="dwz.person.jion" value="" type="hidden" lookupGroup="person"/>
									<input id="empId" name="dwz.person.empId" value="" type="hidden" lookupGroup="person"/>
									<input id="personId" name="dwz.person.personId" value="" type="hidden" lookupGroup="person"/>
									<input name="dwz.person.empId" type="text" class="required"  readOnly lookupGroup="person"/>
									<a class="btnLook" href="/ar/attendanceSettings/viewKeeperList?pageNum=1&LIZHI=1&EmpOffice=15119" lookupGroup="person"><!-- 查找带回 -->
									<spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a>
					  </td>
				</tr>
				<tr>
					<td>File Type：
						<input type="radio" name="suffix" value="pdf" checked onClick="result.location.href='/resources/reportFile/blank.html'"/>pdf
						<input type="radio" name="suffix" value="xls"  onClick="result.location.href='/resources/reportFile/blank.html'"/>xls
						<input type="radio" name="suffix" value="html" onClick="result.location.href='/resources/reportFile/blank.html'"/>html
					</td>
					<td>Report Type：
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
		<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="${defaultCpny}" />
		<input type="hidden" id="ATT_MON" name="ATT_MON" value="" />
		<input type="hidden" id="reportName" name="reportName" value="attRetrieveEmpAttDay" />
		<input type="hidden" id="suffix" name="suffix" value="" />
		<input type="hidden" id="GNDR" name="GNDR" value="" />
		<input type="hidden" id="SUBSD_HIRE_DATE" name="SUBSD_HIRE_DATE" value="" />
		<input type="hidden" id="JOB_POSI_CD" name="JOB_POSI_CD" value="" />
		<input type="hidden" id="JOB_DUTY_CD" name="JOB_DUTY_CD" value="" />
		<input type="hidden" id="CN_ORG_NM" name="CN_ORG_NM" value="" />
		<input type="hidden" id="PROB_END_DATE" name="PROB_END_DATE" value="" />
		<input type="hidden" id="JOB_GRADE" name="JOB_GRADE" value="" />
		<input type="hidden" id="ATT_MON_XS" name="ATT_MON_XS" value=""/>
		<input type="hidden" id="PERSON_ID" name="PERSON_ID" value="" />
	</form>
	<div id="showPDFPop" ></div>
</div>
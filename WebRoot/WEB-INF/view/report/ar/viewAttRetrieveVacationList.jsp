<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<title></title>
<head>   
	<meta http-equiv="X-UA-Compatible" content="IE=edge" >
<script type="text/javascript">
	function exportAttRetrieveVacationList(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	var sform = document.getElementById("onlyForm_vacation");
	var reportName = $("#reportName").val();
	var JOB_TP = sform.seach_JOB_TP.value;
	var SUBSD_CD = $("#seach_SUBSD_CD").val();
	var POST_TYPE = $("#seach_POST_TYPE").val();
	//var ORG_ID = $("#deptNo").val();
   //if (ORG_ID==null || typeof(ORG_ID)=="undefined" || ORG_ID==0)
//{
   var ORG_ID = $(":input[sysLong='viewEmpInfoList_seachDeptVa']").val();
//}
	var EMPID = $("#empId").val();
	if(suffix == 'xls'){
		document.getElementById("vacationLink").innerHTML = "设置导出文件密码";
		var eForm = document.getElementById("excelExportForm_vacation"); 
		eForm.SUBSD_CD.value	= SUBSD_CD;
		eForm.JOB_TP.value 		= JOB_TP;
		eForm.EMPID.value 		= EMPID;
		eForm.ATT_MON.value 		= sform.seach_YEAR.value;
		eForm.reportName.value 		= reportName;
		eForm.ORG_ID.value          = ORG_ID;
		eForm.POST_TYPE.value          = POST_TYPE;
		eForm.suffix.value 		= $('input[name="suffix"]:checked').val();
		$("#importExcelDialog_vacation").attr('href', "/sys/encryptExcel"
				+"?exportFunName=/report/pac04/exportDatilyReport_va"
				+"&navTabId=rpt0102"
				+"&formId=excelExportForm_vacation");
		$("#importExcelDialog_vacation").attr('width', "300");
		$("#importExcelDialog_vacation").attr('height', "150");
		$("#importExcelDialog_vacation").click();
	}else if(suffix == 'pdf'  && report == 'save'){
		//$("#result").attr("src","");
		$("#onlyForm_vacation").attr("target","");
		$("#onlyForm_vacation").attr("action","/report/pac04/exportDatilyReport_va");
		$("#SUBSD_CD").attr('value',SUBSD_CD);
		$("#EMPID").attr('value',EMPID);
		$("#JOB_TP").attr('value',JOB_TP);
		$("#ATT_MON").attr('value',sform.seach_YEAR.value);
		$("#ORG_ID").attr('value',ORG_ID);
		$("#POST_TYPE").attr('value',POST_TYPE);
		$("#onlyForm_vacation").submit();
	}else if(suffix == 'html'|| report == 'display'){
		/**新页面显示*/
		/**新页面显示*/
		/* $("#result").attr("src","/resources/reportFile/blank.html");
		$("#onlyForm_vacation").attr("action","/report/pac04/exportDatilyHtmlReport_va"
		                            +"?SUBSD_CD="+SUBSD_CD
		                            +"&JOB_TP="+JOB_TP
 		                            +"&EMPID="+EMPID
 		                            +"&ATT_MON="+sform.seach_YEAR.value
 		                            +"&reportName="+reportName
 		                            +"&ORG_ID="+ORG_ID
 		                            );
		$("#onlyForm_vacation").attr("target","result");
		$("#onlyForm_vacation").submit(); */
		var options = {mask:true, 
                    width:1000, height:600,
                    drawable:true,
                    resizable:true
                }; 
		//查询报表的参数 一定要用@进行连接 否则导致次条件无效
		var param = "SUBSD_CD="+SUBSD_CD
        +"@JOB_TP="+sform.seach_JOB_TP.value
        +"@EMPID="+EMPID
        +"@ATT_MON="+ sform.seach_YEAR.value
        +"@reportName="+reportName
        +"@ORG_ID="+ORG_ID
        +"@POST_TYPE="+POST_TYPE
        +"@suffix="+$('input[name="suffix"]:checked').val();
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac04/exportDatilyHtmlReport_va"
				, "showPDFPop", "年假报表",options);
	    }
}
</script>
</head>
<div class="pageHeader">
	<a id="importExcelDialog_vacation" href="#" target="dialog" mask="true"><span
		id="vacationLink" style="display: none"></span></a> 
	<a id="displayDept" href="#" target="ajax" rel="deptMon"></a>
	<form action="/report/pac04/exportDatilyReport_va" id="onlyForm_vacation" rel="deptMon" method="post">
		<input type="hidden" id="reportName" name="reportName" value="attRetrieveVacation" />
		<div class="searchBar">
		    <table class="searchContent">
				<tr>
					<td >法人：
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
					<td >部门：
						<ait:deptList name="seach_ORG_ID" cpnyId="${defaultCpny}" id="viewEmpInfoList_seachDeptVa"/>
			            <ait:deptTreeIcon name="seach_ORG_ID" cpnyId="${defaultCpny}" limit="ar" id="viewEmpInfoList_seachDeptVa" selected="${DEPTNO_TYPE}"/>
					</td>
					<td width="10%" style="text-align:center">在职状态：
						<select name="seach_POST_TYPE" id="seach_POST_TYPE">
						    <option value="0110" selected="selected">全部</option>
						    <option value="15119">在职</option>
						    <option value="15120">离职</option>
						</select>
					</td>
				<tr>
				<td >日期：
						<ait:date yearName="seach_YEAR" yearSelected="${YEAR}"/>
						<input type="hidden" id="ATT_MON" name="ATT_MON" value=""/>
					</td> 
				
					<td >
					         社号/姓名：
						<input id="jsonData" name="jsonData" value="" type="hidden"/>
									<input id="id" name="id" value="11" type="hidden"/>
									<input id="empDept" name="dwz.person.empDept" value="" type="hidden" lookupGroup="person"/>
									<input id="deptNo" name="dwz.person.deptNo" value="" type="hidden" lookupGroup="person"/>
									<input id="empName" name="dwz.person.empName" value="" type="hidden" lookupGroup="person"/>
									<input id="cnName" name="dwz.person.cnName" value="" type="hidden" lookupGroup="person"/>
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
					  <td>人员类型组：
						<ait:SelectEmpTypeCode name="seach_JOB_TP" selected="${EMP_TYPE_GROUP}"  limit="ar" type="group" />
						</td>
				    </tr>
				</tr>
				<tr>
					<td>File Type：
						<input type="radio" name="suffix" value="pdf" checked onClick="result.location.href='/resources/reportFile/blank.html'"/>pdf
						<input type="radio" name="suffix" value="xls"  onClick="result.location.href='/resources/reportFile/blank.html'"/>xls
						<input type="radio" name="suffix" value="html" onClick="result.location.href='/resources/reportFile/blank.html'"/>html
					</td>
					<td>Report Type：
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
								<button type="button" onClick="exportAttRetrieveVacationList(this,'${request_navTabId}')">
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
	<form id="excelExportForm_vacation" name="excelExportForm_vacation" method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="" />
		<input type="hidden" id="JOB_TP" name="JOB_TP" value="" />
		<input type="hidden" id="EMPID" name="EMPID" value="" />
		<input type="hidden" id="ATT_MON" name="ATT_MON" value="" />
		<input type="hidden" id="reportName" name="reportName" value="" />
		<input type="hidden" id="ORG_ID" name="ORG_ID" value="" />
		<input type="hidden" id="POST_TYPE" name="POST_TYPE" value="" />
		<input type="hidden" id="suffix" name="suffix" value="" />
	</form>
	<!-- <iframe style=" width: 100%; height: 100%; top: 0;left: 0; scrolling: no;" frameborder="0" src="about:blank"> -->
	<!-- 
	<iframe id=result name=result width=100% height=465 frameborder=0 scrolling=auto src="/resources/reportFile/blank.html"></iframe>
 -->
	<div id="showPDFPop" ></div>
</div>
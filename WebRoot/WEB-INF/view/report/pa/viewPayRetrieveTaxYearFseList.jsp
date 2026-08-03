<%@ page contentType="text/html; charset=UTF-8" language="java"errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function exportPayRetrieveTaxYearlyFse(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	var sform = document.getElementById("onlyFormTYF");
	//var DEPTNO =$("#seach_DEPTNO").val();
	var DEPTNO =$(":input[sysLong='seachBankDept']").val();
	var CPNY_ID =$("#seach_CPNY_ID").val();
	var EMPID =document.getElementById("EMPID_PERSON").value; 
	var PA_YEAR=$("#seach_PA_YEAR").val();
	var reportName = $("#reportName").val();
	if(suffix == 'xls' && report == 'save'){
		document.getElementById("rp_PayRetrieveTaxYearFseLink").innerHTML = "设置导出文件密码";
		var eForm = document.getElementById("exportPayRetrieveTaxYearFse"); 
		var sform = document.getElementById("onlyFormTYF");
		//eForm.JOB_TP.value 		= sform.seach_JOB_TP.value;
		eForm.PA_YEAR.value 		= PA_YEAR;
		//eForm.MONTH.value 		= sform.seach_MONTH.value;
		eForm.reportName.value 		= reportName;
		eForm.DEPTNO.value		= DEPTNO;
		eForm.EMPID.value		=EMPID;
		eForm.CPNY_ID.value			=CPNY_ID;
		eForm.suffix.value 		= $('input[name="suffix"]:checked').val();
		$("#rp_PayRetrieveTaxYearFse").attr('href', "/sys/encryptExcel"
				+"?exportFunName=/report/pac04/exportDatilyReport"
				+"&navTabId=rpt0102"
				+"&formId=exportPayRetrieveTaxYearFse");
		$("#rp_PayRetrieveTaxYearFse").attr('width', "300");
		$("#rp_PayRetrieveTaxYearFse").attr('height', "150");
		$("#rp_PayRetrieveTaxYearFse").click();
	}else if(suffix == 'pdf'  && report == 'save'){
		//$("#result").attr("src","");
		$("#onlyFormTYF").attr("target","");
		$("#onlyFormTYF").attr("action","/report/pac04/exportDatilyReport");
		$("#EMPID").attr('value',EMPID);
		$("#CPNY_ID").attr('value',CPNY_ID);
		$("#PA_YEAR").attr('value',PA_YEAR);
		$("#DEPTNO").attr('value',DEPTNO);
		$("#onlyFormTYF").submit();
	}else if(suffix == 'html'||report == 'display'){
		/*$("#result").attr("src","/resources/reportFile/blank.html");
		$("#onlyFormTYF").attr("action","/report/pac04/exportDatilyHtmlReport2"
								+"?CPNY_ID="+CPNY_ID
                				+"&PA_YEAR="+PA_YEAR
                				+"&reportName="+reportName
                				+"&DEPTNO="+DEPTNO
                				+"&EMPID="+EMPID
                				+"&suffix="+$('input[name="suffix"]:checked').val());
		$("#onlyFormTYF").attr("target","result");
		$("#onlyFormTYF").submit();*/
		
		//dialog的参数
		//alert(CPNY_ID+PA_YEAR+reportName+DEPTNO+EMPID);
		var options = {mask:true, 
                    width:1000, height:600,
                    drawable:true,
                    resizable:true
                }; 
		//查询报表的参数 一定要用@进行连接 否则导致次条件无效
		var param = "CPNY_ID="+CPNY_ID
		+"@PA_YEAR="+PA_YEAR
		+"@reportName="+reportName
		+"@DEPTNO="+DEPTNO
		+"@EMPID="+EMPID
		+"@suffix="+$('input[name="suffix"]:checked').val();
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac04/exportDatilyHtmlReport"
				, "showPDFPop", "个人所得税纳税申请表",options);
	}
}
</script>
<div class="pageHeader">
	<a id="rp_PayRetrieveTaxYearFse" href="#" target="dialog" mask="true"><span
		id="rp_PayRetrieveTaxYearFseLink" style="display: none"></span></a> 
		<a id="displayDept" href="#" target="ajax" rel="deptMon"></a>
	<form action="/report/pac04/exportDatilyReport" id="onlyFormTYF" rel="htmlReport" method="post">
		<input type="hidden" id="reportName" name="reportName" value="payRetrieveTaxYearFse${defaultCpny}" />
		<input type="hidden" id="seach_CPNY_ID" name="seach_CPNY_ID" value="${defaultCpny}" />
		<input type="hidden" id="EMPID" name="EMPID" value=""/>
		<div class="searchBar">
			<table class="searchContent">
			     <tr>
			        <td width="10%" style="text-align:center">公司：</td>
					<td width="20%">
						<select id="seach_CPNY_ID_dab" name="seach_CPNY_ID_dab" disabled="disabled">
							<c:forEach items="${companyList}" var="item" varStatus="i">
								<option value="${item.CPNY_ID}"
									<c:if test="${defaultCpny eq item.CPNY_ID}">selected</c:if>>
									${item.CPNY_ID}
								</option>
							</c:forEach>
						</select>
					</td>
					<td width="10%" style="text-align:center">
					   <spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>
					<!--工号：社号/姓名：-->
				    </td>
				    
				  	<td width="25%">
						<!--  <input id="jsonData" name="jsonData" value="" type="hidden"/>-->
						<!--<spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a>
					       <input type="text" class="required"  name="seach_EMPID" value="${EMPID}" />-->
					    <input id="EMPID_PERSON" name="dwz.person.empId" type="text" value=""  lookupGroup="person"/>
			 			<a class="btnLook" href="/ar/attendanceMintenance/viewEmpCalendarList?firstFlag=1&limit=pa&pageNum=1" lookupGroup="person"><!-- 查找带回 --><spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a>
				    </td>	
					<td>&nbsp;</td>
			     </tr>
				<tr>
					<td width="10%" style="text-align:center">工资结算单位：</td>
					<td width="20%">
						<!--<ait:deptTree name="seach_DEPTNO" limit="hr" selected="${DEPTNO}" />-->
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" id="seachBankDept"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="pa" id="seachBankDept" selected="${DEPTNO}" /></td>
					</td>
					<td width="10%" style="text-align:center">工资年：</td>
					<td width="20%">
						<select id="seach_PA_YEAR" name="seach_PA_YEAR" style="width:75px">
					    	<option value=""><%--请选择 --%>
					    		<!--<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/>-->
					    	</option>
							<c:forEach var="i" begin="2014" end="2020" step="1"> 
						    	<option value="${i}" <c:if test="${YEAR eq i }">selected</c:if> >${i}</option>
						    </c:forEach> 
						 </select>
						<!--  <select id="seach_MONTH" name="seach_MONTH" >
							<option value=""><%--请选择--%>
								<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/>
							</option>
							<option value="01" <c:if test="${MONTH eq '01' }">selected</c:if>>01</option>
							<option value="02" <c:if test="${MONTH eq '02' }">selected</c:if>>02</option>
							<option value="03" <c:if test="${MONTH eq '03' }">selected</c:if>>03</option>
							<option value="04" <c:if test="${MONTH eq '04' }">selected</c:if>>04</option>
							<option value="05" <c:if test="${MONTH eq '05' }">selected</c:if>>05</option>
							<option value="06" <c:if test="${MONTH eq '06' }">selected</c:if>>06</option>
							<option value="07" <c:if test="${MONTH eq '07' }">selected</c:if>>07</option>
							<option value="08" <c:if test="${MONTH eq '08' }">selected</c:if>>08</option>
							<option value="09" <c:if test="${MONTH eq '09' }">selected</c:if>>09</option>
							<option value="10" <c:if test="${MONTH eq '10' }">selected</c:if>>10</option>
							<option value="11" <c:if test="${MONTH eq '11' }">selected</c:if>>11</option>
							<option value="12" <c:if test="${MONTH eq '12' }">selected</c:if>>12</option>
						</select>-->
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td style="text-align:right">File Type：</td>
					<td>
						<input type="radio" name="suffix" value="pdf" checked onClick="result.location.href='/resources/reportFile/blank.html'"/>pdf
	                    <input type="radio" name="suffix" value="xls" onClick="result.location.href='/resources/reportFile/blank.html'"/>xls
	                    <input type="radio" name="suffix" value="html" onClick="result.location.href='/resources/reportFile/blank.html'"/>html
					</td>
					<td style="text-align:right">Report Type：</td>
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
								<button type="button" onClick="exportPayRetrieveTaxYearlyFse(this,'${request_navTabId}')">
									查询
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
	<form id="exportPayRetrieveTaxYearFse" name="exportPayRetrieveTaxYearFse" method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="CPNY_ID" name="CPNY_ID" value="" />
		<input type="hidden" id="PA_YEAR" name="PA_YEAR" value="" />
		<input type="hidden" id="EMPID" name="EMPID" value="" />
		<input type="hidden" id="DEPTNO" name="DEPTNO" value="" />
		<input type="hidden" id="reportName" name="reportName" value="payRetrieveTaxYearFse${defaultCpny}" />
		<input type="hidden" id="suffix" name="suffix" value="" />
	</form>
	<!--  <iframe name=result id=result width=100% height=465 style="z-index: 1;" frameborder=0 scrolling=auto src="/resources/reportFile/blank.html"></iframe>-->
	<div id="showPDFPop" ></div>
</div>

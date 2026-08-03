<%@ page contentType="text/html; charset=UTF-8" language="java"errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function exportPaPasswordSalaryS(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	var sform = document.getElementById("onlyFormTYF");
	var CPNY_ID =$("#seach_CPNY_ID").val();
	var CPNY_ID =$("#reportName").attr("value");
	var PAY_DATE=$("#seach_PAY_DATE").val();
	var PERSON_ID=$("#seach_PERSON_ID").val();
	if(suffix == 'xls' && report == 'save'){
		document.getElementById("rp_PaPasswordSalaryLink").innerHTML = "设置导出文件密码";
		var eForm = document.getElementById("exportPaPasswordSalary"); 
		var sform = document.getElementById("onlyFormTYF");
		eForm.PAY_DATE.value 		= PAY_DATE;
		eForm.PERSON_ID.value 		= PERSON_ID;
		eForm.reportName.value 		= reportName;
		eForm.suffix.value 		= $('input[name="suffix"]:checked').val();
		$("#rp_PaPasswordSalary").attr('href', "/sys/encryptExcel"
				+"?exportFunName=/report/pac04/exportDatilyReport"
				+"&navTabId=rpt0102"
				+"&formId=exportPaPasswordSalary");
		$("#rp_PaPasswordSalary").attr('width', "300");
		$("#rp_PaPasswordSalary").attr('height', "150");
		$("#rp_PaPasswordSalary").click();
	}else if(suffix == 'pdf'  && report == 'save'){
		$("#onlyFormTYF").attr("target","");
		$("#onlyFormTYF").attr("action","/report/pac04/exportDatilyReport");
	 
		$("#CPNY_ID").attr('value',CPNY_ID);
		$("#PERSON_ID").attr('value',PERSON_ID);
		$("#PAY_DATE").attr('value',PAY_DATE);
		$("#onlyFormTYF").submit();
	}else if(suffix == 'html'||report == 'display'){
		
		var options = {mask:true, 
                    width:1000, height:600,
                    drawable:true,
                    resizable:true
                }; 
		//查询报表的参数 一定要用@进行连接 否则导致次条件无效
		var param = "CPNY_ID="+CPNY_ID
		+"@PAY_DATE="+PAY_DATE
		+"@PERSON_ID="+PERSON_ID
		+"@reportName=passwprdSalary"
		 
		+"@EMPID="+EMPID
		+"@suffix="+$('input[name="suffix"]:checked').val();
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac04/exportDatilyHtmlReport"
				, "showPDFPop", "个人保险明细表",options);
	}
}
</script>
<div class="pageHeader">
	<a id="rp_PaPasswordSalary" href="#" target="dialog" mask="true"><span
		id="rp_PaPasswordSalaryLink" style="display: none"></span></a>  
		<a id="displayDept" href="#" target="ajax" rel="deptMon"></a>
	<form action="/report/pac04/exportDatilyReport" id="onlyFormTYF" rel="htmlReport" method="post">
		<input type="hidden" id="reportName" name="reportName" value="passwprdSalary" />
		<input type="hidden" id="seach_CPNY_ID" name="seach_CPNY_ID" value="${defaultCpny}" />
		<input type="hidden" id="EMPID" name="EMPID" value=""/>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td width="10%" style="text-align:center">工资查询日期：</td>
					<td width="20%">
						<select id="seach_PAY_DATE" name="seach_PAY_DATE">
							<c:forEach items="${paPayScheduleList}" var="paySchedule" varStatus="i">
								<c:choose>
									<c:when test="${PAY_DATE == paySchedule.PAY_DATE }">
										<option value="${paySchedule.PAY_DATE }" selected="selected">
											${paySchedule.PAY_DATE }
										</option>
									</c:when>
									<c:otherwise>
										<option value="${paySchedule.PAY_DATE}">
										 ${paySchedule.PAY_DATE }
										</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
					<td>&nbsp;</td>
					<td width="10%" style="text-align:center">PERSON_ID：</td>
					<td width="20%">
						<select id="seach_PERSON_ID" name="seach_PERSON_ID">
							<c:forEach items="${paPayScheduleList}" var="paySchedule" varStatus="i">
								<c:choose>
									<c:when test="${PERSON_ID == paySchedule.PERSON_ID }">
										<option value="${paySchedule.PERSON_ID }" selected="selected">
											${paySchedule.PERSON_ID }
										</option>
									</c:when>
									<c:otherwise>
										<option value="${paySchedule.PERSON_ID}">
										 ${paySchedule.PERSON_ID }
										</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
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
						<div >
							<div class="buttonContent">
								<a class="button" onClick="exportPaPasswordSalaryS(this,'${request_navTabId}')">
								<span>	查询</span>
							</a>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
	<div id="deptMon" class="unitBox" width="150%">
	</div>
	<form id="exportPaPasswordSalary" name="exportPaPasswordSalary" method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="CPNY_ID" name="CPNY_ID" value="" />
		<input type="hidden" id="PAY_DATE" name="PAY_DATE" value="" />
		<input type="hidden" id="PERSON_ID" name="PERSON_ID" value="" />
		<input type="hidden" id="EMPID" name="EMPID" value="" />
		<input type="hidden" id="DEPTNO" name="DEPTNO" value="" />
	 
		<input type="hidden" id="suffix" name="suffix" value="" />
	</form>
	<div id="showPDFPop" ></div>
</div>

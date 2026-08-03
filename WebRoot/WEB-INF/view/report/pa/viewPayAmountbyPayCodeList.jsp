<%@ page contentType="text/html; charset=UTF-8" language="java"errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function exportPayAmountByPay(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	var sform = document.getElementById("onlyFormFY");
	var PAY_NAME= $("#PARAM_ITEM").find("option:selected").text().trim();
	var ATT_MON=sform.seach_YEAR.value+''+sform.seach_MONTH.value;
	var PARAM_ITEM=sform.PARAM_ITEM.value;
	//var DEPTNO=sform.seach_DEPTNO.value;
	var DEPTNO=$(":input[sysLong='seachBankDept']").val();
	var reportName = sform.reportName.value;
	var JOB_TP=sform.seach_JOB_TP.value;
	var MONTH=sform.seach_MONTH.value;
	var ATT_CPNYID=sform.ATT_CPNYID.value;
	if(suffix == 'xls'&& report == 'save'){
		document.getElementById("importExcelDialog_rp0102GZLink").innerHTML = "设置导出文件密码";
		var sform = document.getElementById("onlyFormFY");
		var eForm = document.getElementById("exportPayAmountByPayCode"); 
		eForm.JOB_TP.value 	= JOB_TP;
		eForm.PAY_NAME.value 	= PAY_NAME;
		eForm.ATT_MON.value 	= ATT_MON;
		eForm.PARAM_ITEM.value 	= PARAM_ITEM;
		eForm.DEPTNO.value 	= DEPTNO;
		eForm.MONTH.value 	= MONTH;
		eForm.ATT_CPNYID.value 	= ATT_CPNYID;
		eForm.reportName.value 	= sform.reportName.value;
		eForm.suffix.value 		= $('input[name="suffix"]:checked').val();
		$("#importExcelDialog_rp0102GZ").attr('href', "/sys/encryptExcel"
				+"?exportFunName=/report/pac04/exportDatilyReport2"
				+"&navTabId=rpt0102"
				+"&formId=exportPayAmountByPayCode");
		$("#importExcelDialog_rp0102GZ").attr('width', "300");
		$("#importExcelDialog_rp0102GZ").attr('height', "150");
		$("#importExcelDialog_rp0102GZ").click();
	}else if(suffix == 'pdf'  && report == 'save'){
			//$("#result").attr("src","");
			$("#onlyFormFY").attr("target","");
			$("#onlyFormFY").attr("action","/report/pac04/exportDatilyReport2");
			$("#PA_MONTH").attr('value',ATT_MON);
			$("#PARAM_ITEM").attr('value',PARAM_ITEM);
			$("#PAY_NAME").attr('value',PAY_NAME);
			$("#DEPTNO").attr('value',DEPTNO);
			$("#JOB_TP").attr('value',JOB_TP);
			$("#onlyFormFY").submit();
	}else if(suffix == 'html'||report == 'display'){
		/*$("#result").attr("src","/resources/reportFile/blank.html");
			$("#onlyFormFY").attr("action","/report/pac04/exportDatilyHtmlReport2"
                      +"?JOB_TP="+sform.seach_JOB_TP.value
                      +"&ATT_MON="+ATT_MON
                      +"&reportName="+sform.reportName.value
                      +"&PARAM_ITEM="+PARAM_ITEM
                      +"&PAY_NAME="+PAY_NAME
                      +"&DEPTNO="+DEPTNO
                      +"&suffix="+$('input[name="suffix"]:checked').val());
			$("#onlyFormFY").attr("target","result");
			$("#onlyFormFY").submit();*/
		//dialog的参数
		var options = {mask:true, 
                    width:1000, height:600,
                    drawable:true,
                    resizable:true
                }; 
		//查询报表的参数 一定要用@进行连接 否则导致次条件无效
		var param = "JOB_TP="+sform.seach_JOB_TP.value
        +"@PAY_NAME="+encodeURI(PAY_NAME)
        +"@PA_MONTH="+ATT_MON
        +"@PARAM_ITEM="+PARAM_ITEM
        +"@DEPTNO="+DEPTNO
        +"@ATT_CPNYID="+ATT_CPNYID
        +"@reportName="+sform.reportName.value
        +"@suffix="+$('input[name="suffix"]:checked').val();
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac04/exportDatilyHtmlReport"
				, "showPDFPop", "费用按工资代码明细",options);
		}		
	}

$(document).ready(function(){
	if($("#seach_JOB_TP").val() != ''){
		var EMP_TYPE = $("#seach_JOB_TP").val();
		ajaxAdd_add_hr2100(EMP_TYPE);
	}
});
var ajaxGet_add_hr2100;
function ajaxAdd_add_hr2100(EMP_TYPE) {
		if (ajaxGet_add_hr2100 != null) {
			ajaxGet_add_hr2100.abort();
		}
		$.ajaxSettings.global = false;
		ajaxGet_add_hr2100 = $.ajax( {
			type : "POST",
			url : "/hrm/jobType/getEmpJobType",
			data : { JOB_TP : $("#seach_JOB_TP").val(), defaultCpny : $("#hr2100_seach_CPNY_ID").val(),authority:$("#authority").val()},
			dataType : "json",
			success : function(data) {
				$('#seach_EMP_TYPE_hr2100').html("");
				var html = '<option value="">请选择</option>';
				if (typeof (data['result']) != "undefined") {
					$.each(data['result'], function(commentIndex, comment) {
							html += '<option value="' + comment['CODE_NO'] + '">' + comment['CODE_NAME'] + '</option>';
						});
				}
				$('#seach_EMP_TYPE_hr2100').html(html);
				if(EMP_TYPE != -1){
					$("#seach_EMP_TYPE_hr2100").val(EMP_TYPE);
				}
			}
		});
		$.ajaxSettings.global = true;
}
</script>
<div class="pageHeader">
	<a id="importExcelDialog_rp0102GZ" href="#" target="dialog" mask="true"><span
		id="importExcelDialog_rp0102GZLink" style="display: none"></span></a> 
		<a id="displayDept" href="#" target="ajax" rel="deptMon"></a>
	<form action="/report/pac04/exportDatilyReport2" id="onlyFormFY" rel="htmlReport" method="post">
		<!--  <input type="hidden" id="reportName" name="reportName" value="payAmountbyPayCode" />-->
		<input type="hidden" id="PAY_NAME" name="PAY_NAME" value="" />
		<input type="hidden" id="reportName" name="reportName" value="payAmountbyPayCode" />
		<input type="hidden" id="ATT_CPNYID" name="ATT_CPNYID" value="${defaultCpny}" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td width="10%" style="text-align:center">公司：</td>
					<td width="20%">
						<select id="ATT_CPNYID_dab" name="ATT_CPNYID_dab" disabled="disabled">
							<c:forEach items="${companyList}" var="item" varStatus="i">
								<option value="${item.CPNY_ID}"
									<c:if test="${defaultCpny eq item.CPNY_ID}">selected</c:if>>
									${item.CPNY_ID}
								</option>
							</c:forEach>
						</select>
					</td>
					<td><c:if test="${defaultCpny ne'LGEND'}">
						人员类型组：
						</c:if>
						<c:if test="${defaultCpny eq'LGEND'}">
						&nbsp人员类型：
						</c:if></td>
					<td>
					 	<c:if test="${defaultCpny ne'LGEND'}">
						<ait:SelectEmpTypeCode name="seach_JOB_TP" selected="${EMP_TYPE_GROUP}" limit="pa" type="group" />
						</c:if>
						<c:if test="${defaultCpny eq'LGEND'}">
						<ait:SelectEmpTypeCode name="seach_JOB_TP" selected="${EMP_TYPE_GROUP}" limit="pa"  />
						</c:if>
					</td>
					</tr>
					
					<tr>
					<td width="10%" style="text-align:center">工资结算单位：</td>
					<td width="20%">
						<!--<ait:deptTree name="seach_DEPTNO" limit="pa" selected="${DEPTNO}" />-->
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" id="seachBankDept"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="pa" id="seachBankDept" selected="${DEPTNO}" /></td>
					</td>
					<td width="10%" style="text-align:center">工资月：</td>
					<td width="20%">
						<select id="seach_YEAR" name="seach_YEAR" style="width:75px">
					    	<option value=""><%--请选择 --%>
					    		<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/>
					    	</option>
							<c:forEach var="i" begin="2014" end="2020" step="1"> 
						    	<option value="${i}" <c:if test="${YEAR eq i }">selected</c:if> >${i}</option>
						    </c:forEach> 
						 </select>
						<select id="seach_MONTH" name="seach_MONTH" >
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
						</select>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td width="10%" style="text-align:center">工资项目：
					</td >
					<td width="20%">
					
						<select name="PARAM_ITEM" id="PARAM_ITEM" >
							<c:forEach items="${salarycodelist}" var="item" varStatus="i">
								<option value="${item.ITEM_ID }">
									${item.ITEM_NAME}
								</option>
							</c:forEach>
						</select>
						<!--  <select id="PARAM_ITEM" name="PARAM_ITEM" style="width:75px">
					    	<option value="P_WITHHOLD" selected>代扣</option>
					    	<option value="P_LEFT_FEE" selected>退社补偿金</option>

						</select>-->
					</td>
					<td></td>
					<td></td>
					
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
								<button type="button" onClick="exportPayAmountByPay(this,'${request_navTabId}')">
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
	<form id="exportPayAmountByPayCode" name="exportPayAmountByPayCode" method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="JOB_TP" name="JOB_TP" value="" />
		<input type="hidden" id="DEPTNO" name="DEPTNO" value="" />
		<input type="hidden" id="ATT_MON" name="ATT_MON" value="" />
		<input type="hidden" id="MONTH" name="MONTH" value="" />
		<input type="hidden" id="PARAM_ITEM" name="PARAM_ITEM" value="" />
		<input type="hidden" id="PAY_NAME" name="PAY_NAME" value="" />
		<input type="hidden" id="ATT_CPNYID" name="ATT_CPNYID" value="" />
		<input type="hidden" id="reportName" name="reportName" value="" />
		<input type="hidden" id="suffix" name="suffix" value="" />
	</form>
	<div id="showPDFPop" ></div>
	<!--  <iframe name=result id=result width=100% height=465 style="z-index: 1;" frameborder=0 scrolling=auto src="/resources/reportFile/blank.html"></iframe>-->
</div>

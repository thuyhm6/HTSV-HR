<%@ page contentType="text/html; charset=UTF-8" language="java"errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

function exportPAYtImportExcel(form,callback) {
var sform = document.getElementById("onlyFormPromtr");
sform.PA_MONTH.value 		= sform.seach_YEAR.value+''+sform.seach_MONTH.value;
$("#PA_MONTH").attr('value',sform.seach_YEAR.value+''+sform.seach_MONTH.value);
		var sform = document.getElementById(form);
		var ok=true;
		if(!ok)return false;
		alertMsg.confirm("确定要导出数据吗?", {
			okCall : function() {
				//用于excel导出的表单参数处理
				document.getElementById("rpPromtrLink").innerHTML = "EXCEL密码设置";
				$("#rp_PayPayDetailListPromtr").attr('href',"/disc/autoExcel/encryptExcelForExcel"
						+"?exportFunName=/disc/autoExcel/exportLOtImportExcel"
						+"&navTabId=disc0101"
						+"&formId=onlyFormPromtr");
				$("#rp_PayPayDetailListPromtr").attr('width', "300");
				$("#rp_PayPayDetailListPromtr").attr('height', "150");
				$("#rp_PayPayDetailListPromtr").click();
			}
		});
	}
</script>
<div class="pageHeader">
	<a id="rp_PayPayDetailListPromtr" href="#" target="dialog" mask="true"><span
		id="rpPromtrLink" style="display: none"></span></a> 
	<form id="onlyFormPromtr" name="onlyFormPromtr" method="post" 
		action="/disc/autoExcel/testExcel">
	<!-- action="/report/pac04/exportDatilyReport" id="onlyFormPromtr" rel="htmlReport" method="post" -->
		<input type="hidden" id="reportName" name="reportName" value="payPayDetailListPromtr" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<input  type= "hidden" id="password" name="password" value=""/>
					<input readonly type="hidden" name="SQL_SEQMEAN" id="SQL_SEQMEAN"   value="160"/>
					<input  readonly type="hidden" name="PGM_NM" id="PGM_NM"   value="PAY"/>
					<input readonly  type="hidden" name="SQL_NM" id="SQL_NM"     value="家电促销职工资下载"/>
					<input type="hidden" name="PERSON_ID" id="PERSON_ID"   value="${LoginUser.personId}" readOnly/></td>
				
					<td>大区：</td>
					<td>
						<ait:deptTreeMulti id="PAY_AREA_CD" name="PAY_AREA_NM" limit="pa" level="2" selected="${PAY_AREA_CD}" selectedNm="${PAY_AREA_NM}" />
						<input type="hidden" name="PAY_AREA_CD" id="PAY_AREA_CD" value=""/>
					</td>
					<td>工资月：</td>
					<td >
						<ait:date yearName="seach_YEAR" yearSelected="${YEAR}" monthName="seach_MONTH" monthSelected="${MONTH}"/>
						<input type="hidden" id="PA_MONTH" name="PA_MONTH" value=""/>
					</td>
					<td></td>
					<td></td>
					<td></td>
					<td colspan="3"><a class="buttonActive" id ="exportLOtImportExcel" onclick="exportPAYtImportExcel('onlyFormPromtr',DWZ.ajaxDone);" href="#">
					<span><spring:message code="ar.addempshift.title.excelexport"/><!-- 导出Excel --></span>
				</a>&nbsp;</td>					
				</tr>
				<%-- <tr>
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
					<td colspan="3"><a class="buttonActive" id ="exportLOtImportExcel" onclick="exportPAYtImportExcel('onlyFormPromtr',DWZ.ajaxDone);" href="#">
					<span><spring:message code="ar.addempshift.title.excelexport"/>导出Excel</span>
				</a>&nbsp;</td>
				</tr> --%>
			</table>
		
		</div>
	</form>
	
</div>

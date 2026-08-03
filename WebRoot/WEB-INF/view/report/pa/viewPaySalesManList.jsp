<%@ page contentType="text/html; charset=UTF-8" language="java"errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function exportPaySalesManList(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	var sform = document.getElementById("onlyForm_Sales");
	var reportName = $("#reportName").val();
	var SUBSD_CD = $("#seach_SUBSD_CD").val();
	var ORG_ID = $(":input[sysLong='viewEmpInfoList_seachPaySales']").val();
	if(suffix == 'xls'){
		document.getElementById("rpPaySalesLink").innerHTML = "设置导出文件密码";
		var eForm = document.getElementById("excelExportForm_rp0103PaySales"); 
		
		eForm.SUBSD_CD.value	= SUBSD_CD;
		eForm.YEAR.value	= sform.seach_YEAR.value;
		eForm.MONTH.value	= sform.seach_MONTH.value;
		eForm.PA_MONTH.value	= sform.seach_YEAR.value+''+sform.seach_MONTH.value;
		eForm.reportName.value 		= reportName;
		eForm.ORG_ID.value          = ORG_ID;
		eForm.suffix.value 		= $('input[name="suffix"]:checked').val();
		$("#importExcelDialog_rp0103PaySales").attr('href', "/sys/encryptExcel"
				+"?exportFunName=/report/pac04/exportDatilyReport"
				+"&navTabId=rpt0103"
				+"&formId=excelExportForm_rp0103PaySales");
		$("#importExcelDialog_rp0103PaySales").attr('width', "300");
		$("#importExcelDialog_rp0103PaySales").attr('height', "150");
		$("#importExcelDialog_rp0103PaySales").click();
	}else if(suffix == 'pdf'  && report == 'save'){
		$("#onlyForm_Sales").attr("target","");
		$("#onlyForm_Sales").attr("action","/report/pac04/exportDatilyReport");
		$("#SUBSD_CD").attr('value',SUBSD_CD);
		$("#PA_MONTH").attr('value',sform.seach_YEAR.value+''+sform.seach_MONTH.value);
		$("#ORG_ID").attr('value',ORG_ID);
		$("#onlyForm_Sales").submit();
	}else if(suffix == 'html'|| report == 'display'){
		/**新页面显示*/
			//dialog的参数
		var options = {mask:true, 
                    width:1000, height:600,
                    drawable:true,
                    resizable:true
                }; 
		//查询报表的参数 一定要用@进行连接 否则导致次条件无效
		var param = "SUBSD_CD="+SUBSD_CD
        +"@PA_MONTH="+sform.seach_YEAR.value+''+sform.seach_MONTH.value
        +"@YEAR="+sform.seach_YEAR.value
        +"@MONTH="+sform.seach_MONTH.value
        +"@reportName="+reportName
        +"@ORG_ID="+ORG_ID
        +"@suffix="+$('input[name="suffix"]:checked').val();
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac04/exportDatilyHtmlReport"
				, "showPDFPop", "业务员工资明细报表",options);
	}
}
</script>
<div class="pageHeader">
	<a id="importExcelDialog_rp0103PaySales" href="#" target="dialog" mask="true"><span
		id="rpPaySalesLink" style="display: none"></span></a> 
	<form action="/report/pac04/exportDatilyReport" id="onlyForm_Sales" method="post">
		<input type="hidden" id="reportName" name="reportName" value="payRetrieveSalesmanDetailList" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>法人：</td>
					<td>
						<select id="seach_SUBSD_CD" name="seach_SUBSD_CD" disabled="disabled">
							<c:forEach items="${companyList}" var="item" varStatus="i">
								<option value="${item.CPNY_ID}"
									<c:if test="${defaultCpny eq item.CPNY_ID}">selected</c:if> >
									${item.CPNY_ID}
								</option>
							</c:forEach>
						</select>
						<input type="hidden" id="seach_SUBSD_CD" name="seach_SUBSD_CD" value="${defaultCpny}" >
					</td>
					<td> 部门：</td>
					<td >
						<ait:deptList name="seach_ORG_ID" cpnyId="${defaultCpny}" id="viewEmpInfoList_seachPaySales"/>
			            <ait:deptTreeIcon name="seach_ORG_ID" cpnyId="${defaultCpny}" limit="pa" id="viewEmpInfoList_seachPaySales" selected="${DEPTNO_TYPE}"/>
					</td>
					<td >工资月：</td>
					<td >
						<ait:date yearName="seach_YEAR" yearSelected="${YEAR}" monthName="seach_MONTH" monthSelected="${MONTH}"/>
							<input type="hidden" id="PA_MONTH" name="PA_MONTH" value=""/>
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
								<button type="button" onClick="exportPaySalesManList(this,'${request_navTabId}')">
									查询
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
	<form id="excelExportForm_rp0103PaySales" name="excelExportForm_rp0103PaySales" method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="" />
			<input type="hidden" id="PA_MONTH" name="PA_MONTH" value="" />
		<input type="hidden" id="ORG_ID" name="ORG_ID" value="" />
		<input type="hidden" id="YEAR" name="YEAR" value="" />
		<input type="hidden" id="MONTH" name="MONTH" value="" />
		<input type="hidden" id="reportName" name="reportName" value="" />
		<input type="hidden" id="suffix" name="suffix" value="" />
	</form>
	<div id="showPDFPop" ></div>
</div>

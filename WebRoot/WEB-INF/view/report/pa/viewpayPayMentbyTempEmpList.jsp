<%@ page contentType="text/html; charset=UTF-8" language="java"errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function exportPayHouse(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	var sform = document.getElementById("onlyFormLSZ");
	var DEPTNO=sform.seach_DEPTNO.value;
	var ATT_MON=sform.seach_YEAR.value+''+sform.seach_MONTH.value;
	var FLNO=sform.seach_FLNO.value;
	if(suffix == 'xls' && report == 'save'){
		document.getElementById("rpPayHouseLGECHLink").innerHTML = "设置导出文件密码";
		var sform = document.getElementById("onlyFormLSZ");
		var eForm = document.getElementById("exportPayHouseLG"); 
		eForm.CPNY_ID.value	= sform.seach_CPNY_ID.value;
		eForm.JOB_TP.value 		= sform.seach_JOB_TP.value;
		eForm.YEAR.value 		= sform.seach_YEAR.value;
		eForm.FLNO.value 		= sform.seach_FLNO.value;
		
		eForm.DEPTNO.value		=DEPTNO;
		eForm.ATT_MON.value 	= sform.seach_YEAR.value+''+sform.seach_MONTH.value;
		eForm.MONTH.value 		= sform.seach_MONTH.value;
		eForm.reportName.value 		= sform.reportName.value;
		eForm.suffix.value 		= $('input[name="suffix"]:checked').val();
		$("#rp_PayHouseLGECH").attr('href', "/sys/encryptExcel"
				+"?exportFunName=/report/pac04/exportDatilyReport2"
				+"&navTabId=rpt0102"
				+"&formId=exportPayHouseLG");
		$("#rp_PayHouseLGECH").attr('width', "300");
		$("#rp_PayHouseLGECH").attr('height', "150");
		$("#rp_PayHouseLGECH").click();
	}else if(suffix == 'pdf'  && report == 'save'){
		//$("#result").attr("src","");
		$("#onlyFormLSZ").attr("target","");
		$("#onlyFormLSZ").attr("action","/report/pac04/exportDatilyReport2");
		$("#ATT_MON").attr('value',ATT_MON);
		$("#onlyFormLSZ").submit();
	}else if(suffix == 'html'||report == 'display'){
		//dialog的参数
		var options = {mask:true, 
                    width:1000, height:600,
                    drawable:true,
                    resizable:true
                }; 
		//查询报表的参数 一定要用@进行连接 否则导致次条件无效
		var param ="DEPTNO="+DEPTNO
		+"@reportName="+sform.reportName.value
		+"@ATT_MON="+ATT_MON
		+"@JOB_TP="+sform.seach_JOB_TP.value
		+"@DEPTNO="+DEPTNO
		+"@FLNO="+FLNO
		+"@SUBSD_CD="+sform.SUBSD_CD.value
		+"@suffix="+$('input[name="suffix"]:checked').val();
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac04/exportDatilyHtmlReport2"
				, "showPDFPop", "临时职打印工资条明细",options);
	}
}
</script>
<div class="pageHeader">
	<a id="rp_PayHouseLGECH" href="#" target="dialog" mask="true"><span
		id="rpPayHouseLGECHLink" style="display: none"></span></a> 
	<form action="/report/pac04/exportDatilyReport2" id="onlyFormLSZ" rel="htmlReport" method="post">
		<input type="hidden" id="reportName" name="reportName"   value="payPayMentbyTempEmp" />
		<input type="hidden" id="ATT_MON" name="ATT_MON" value="" />
		<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="${defaultCpny}" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td width="10%" style="text-align:center">公司：</td>
					<td width="20%">
						<select id="seach_CPNY_ID" name="seach_CPNY_ID" disabled="disabled">
							<c:forEach items="${companyList}" var="item" varStatus="i">
								<option value="${item.CPNY_ID}"
									<c:if test="${defaultCpny eq item.CPNY_ID}">selected</c:if>>
									${item.CPNY_ID}
								</option>
							</c:forEach>
						</select>
					</td>
					<td width="10%" style="text-align:center">人员类型组：</td>
					<td width="20%">
						<ait:SelectEmpTypeCode name="seach_JOB_TP" selected="${EMP_TYPE_GROUP}" limit="pa" type="group" />
						<input type="hidden" id="JOB_NAME" name="seach_JOB_NAME" value=""/>
					</td>
					</tr>
					<tr>
					<td width="10%" style="text-align:center">福利地区：</td>
					<td width="20%">
						<!--<ait:deptTree name="seach_DEPTNO" limit="hr" selected="${DEPTNO}" />
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" id="seachBankDept"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="pa" id="seachBankDept" selected="${DEPTNO}" /></td>
						<ait:SelectSyCodeByCpnyID name="seach_FLNO" id="seach_FLNO"  parentNo ="216736" cnpyID="${defaultCpny}" limit="all"  />-->
						<select id="seach_FLNO" name="seach_FLNO">
						<option value="">全部</option>
							<c:forEach items="${insrarea}" var="item" varStatus="i">
								<option value="${item.INSRAREAID}">
									${item.INSRAREANAME}
								</option>
							</c:forEach>
						</select>
					
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
					<td width="10%" style="text-align:center" >工资结算部门:</td>
					<td>
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" id="seachBankDept"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="pa" id="seachBankDept" selected="${DEPTNO}" />
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
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onClick="exportPayHouse(this,'${request_navTabId}')">
									查询
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
	<form id="exportPayHouseLG" name="exportPayHouseLG" method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="CPNY_ID" name="CPNY_ID" value="" />
		<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="" />
		<input type="hidden" id="JOB_TP" name="JOB_TP" value="" />
		<input type="hidden" id="FLNO" name="FLNO" value="" />
		<input type="hidden" id="YEAR" name="YEAR" value="" />
		<input type="hidden" id="MONTH" name="MONTH" value="" />
		<input type="hidden" id="ATT_MON" name="ATT_MON" value="" />
		<input type="hidden" id="DEPTNO" name="DEPTNO" value="" />
		<input type="hidden" id="reportName" name="reportName" value="payPayMentbyTempEmp" />
		<input type="hidden" id="suffix" name="suffix" value="" />
	</form>
<div id="showPDFPop" ></div>
</div>

<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

	function exportIncentiveCalcList1(a, navTabId) {
		var suffix = $('input[name="suffix"]:checked').val(); // pdf  xls  html
		var report = $('input[name="reportType"]:checked').val(); // display save
		var sform = document.getElementById("onlyForm"); 
	    var JOB_TP = sform.seach_JOB_TP.value;
	    var ATT_CD = sform.seach_ATT_CD.value;
	    var ATT_NAME = sform.seach_ATT_CD[sform.seach_ATT_CD.selectedIndex].text;
	    if(JOB_TP == null || JOB_TP ==''){
		   JOB_TP = '211807';
	    }
	    var reportName = $("#reportName").val();
	    var SUBSD_CD = $("#SUBSD_CD").val();
	    var ORG_ID = $(":input[sysLong='seachDeptQ']").val();
		if (suffix == 'xls') {
			document.getElementById("rp0102Link").innerHTML = "设置导出文件密码";
			var eForm = document.getElementById("excelExportForm_rp0102");
			eForm.SUBSD_CD.value = SUBSD_CD;
			eForm.ATT_CD.value = ATT_CD;
			eForm.ATT_NAME.value = ATT_NAME;
			eForm.ORG_ID.value = ORG_ID;
			eForm.JOB_TP.value = JOB_TP;
			eForm.ATT_STRT_DTIME.value = sform.seach_ATT_STRT_DTIME.value;
			eForm.ATT_END_DTIME.value = sform.seach_ATT_END_DTIME.value;
			eForm.reportName.value = reportName;
			eForm.suffix.value = $('input[name="suffix"]:checked').val();
			$("#importExcelDialog_rp0102").attr(
					'href',
					"/sys/encryptExcel"
							+ "?exportFunName=/report/pac04/exportDatilyReport"
							+ "&navTabId=rpt0102"
							+ "&formId=excelExportForm_rp0102");
			$("#importExcelDialog_rp0102").attr('width', "300");
			$("#importExcelDialog_rp0102").attr('height', "150");
			$("#importExcelDialog_rp0102").click();
		} else if (suffix == 'pdf' && report == 'save') {
		    //$("#result").attr("src","");
			$("#onlyForm").attr("target",""); 
		    $("#onlyForm").attr("action","/report/pac04/exportDatilyReport");
		    $("#ATT_STRT_DTIME").attr('value',sform.seach_ATT_STRT_DTIME.value);
		    $("#ORG_ID").attr('value',ORG_ID);
		    $("#ATT_CD").attr('value',ATT_CD);
		    $("#ATT_NAME").attr('value',ATT_NAME);
		    $("#ATT_END_DTIME").attr('value',sform.seach_ATT_END_DTIME.value);
		    $("#onlyForm").submit();
		} else if (report == 'display' || suffix == 'html' ) {
			/**新页面显示*/
			//dialog的参数
			
		var options = {mask:true, 
                    width:1000, height:600,
                    drawable:true,
                    resizable:true
                }; 
		//查询报表的参数 一定要用@进行连接 否则导致次条件无效
		var param = "SUBSD_CD="+SUBSD_CD
        +"@JOB_TP="+sform.seach_JOB_TP.value
        +"@ATT_STRT_DTIME="+ sform.seach_ATT_STRT_DTIME.value
        +"@ATT_END_DTIME="+ sform.seach_ATT_END_DTIME.value
        +"@reportName="+reportName
        +"@ATT_NAME="+encodeURI(ATT_NAME)
        +"@ATT_CD="+ATT_CD
        +"@ORG_ID="+ORG_ID
        +"@suffix="+$('input[name="suffix"]:checked').val();
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac04/exportDatilyHtmlReport"
				, "showPDFPop", "考勤项目筛选报表",options);
	    }
	}
</script>
<div class="pageHeader">
	<a id="importExcelDialog_rp0102" href="#" target="dialog" mask="true"><span
		id="rp0102Link" style="display: none"></span>
	</a> <a id="displayDept" href="#" target="ajax" rel="deptMon"></a>
	<form action="/report/pac04/exportDatilyReport" id="onlyForm"
		rel="deptMon" method="post">
		<input type="hidden" id="ATT_STRT_DTIME" name="ATT_STRT_DTIME" value="" /> 
		<input type="hidden" id="ATT_CD" name="ATT_CD" value="" /> 
		<input type="hidden" id="ATT_END_DTIME" name="ATT_END_DTIME" value="" /> 
		<input type="hidden" id="reportName" name="reportName"
			value="attRetrieveDepartmentKind" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>部门：</td>
					<td>
						<ait:deptList name="seach_ORG_ID" cpnyId="${defaultCpny}" id="seachDeptQ"/>
						<ait:deptTreeIcon name="seach_ORG_ID" cpnyId="${defaultCpny}" limit="ar" id="seachDeptQ" selected="${ORG_ID}"/>
					</td>
					
					<td>
						<!-- 考勤区分 -->
						<spring:message code="ar.viewardetail.title.kaoqinqufen" />:</td>

					<td><select name="seach_ATT_CD" id="seach_ATT_CD"  class="select">
							<option value="">
								全部考勤类型
							</option>
							<c:forEach items="${getCodeList}" var="item">
								<option value="${item.CODE_NO}"
									<c:if test="${item.CODE_NO eq ATT_CD}">selected</c:if>>
									${item.CODE_NAME}</option>
							</c:forEach>
					</select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="hidden" name="ATT_NAME" id="ATT_NAME" value=""/>
					</td>
					<td>
						<c:if test="${defaultCpny ne'LGEND'}">人员类型组：
						</c:if>
						<c:if test="${defaultCpny eq'LGEND'}">人员类型：
						</c:if>
						</td>
						<td>
							<c:if test="${defaultCpny ne'LGEND'}">
							<ait:SelectEmpTypeCode name="seach_JOB_TP" id="seach_JOB_TP" selected="${EMP_TYPE_GROUP}"  limit="ar" type="group" />
							</c:if>
							<c:if test="${defaultCpny eq'LGEND'}">
							<ait:SelectEmpTypeCode name="seach_JOB_TP" id="seach_JOB_TP" selected="${EMP_TYPE_GROUP}"  limit="ar"  />
							</c:if>
							 <input type="hidden" id="SUBSD_CD" name="seach_SUBSD_CD" value="${defaultCpny}" />
					 	</td>

				</tr>
				<tr>
					<td>
						<!-- 开始日期 -->
						<spring:message code="ar.viewcycleparameter.title.kaishiriqi" />:</td>
					<td><input type="text" name="seach_ATT_STRT_DTIME" class="date" id="seach_ATT_STRT_DTIME" format='yyyyMMdd'
						value="${ATT_STRT_DTIME}" yearstart="-20" yearend="20" readonly="true" /><a
						class="inputDateButton"><spring:message
								code="public.title.choose" />
							<!-- 选择 -->
					</a>
					</td>

					</td>
					<td>
						<!-- 结束日期 -->
						<spring:message code="ar.viewcycleparameter.title.jieshuriqi" />:</td>
					<td style="padding-left: 1px"><input type="text"
						name="seach_ATT_END_DTIME" class="date" value="${ATT_END_DTIME}" yearstart="-20" id="seach_ATT_END_DTIME" format='yyyyMMdd'
						yearend="20" readonly="true" /><a class="inputDateButton"><spring:message
								code="public.title.choose" />
							<!-- 选择 -->
					</a>
					</td>
					<td>法人：</td>
					<td>
						<select name="seach_SUBSD_CD" id="seach_SUBSD_CD" disabled="disabled">
							<c:forEach items="${companyList}" var="item" varStatus="i">
								<option value="${item.CPNY_ID}" <c:if test="${defaultCpny eq item.CPNY_ID}">selected</c:if>> ${item.CPNY_ID} </option>
							</c:forEach>
						</select>
					</td>

				</tr>
				<tr>
					<td>File Type：</td>
					<td><input type="radio" name="suffix" value="pdf" checked
						onClick="result.location.href='/resources/reportFile/blank.html'" />pdf
						<input type="radio" name="suffix" value="xls"
						onClick="result.location.href='/resources/reportFile/blank.html'" />xls
						<input type="radio" name="suffix" value="html"
						onClick="result.location.href='/resources/reportFile/blank.html'" />html
					</td>
					<td>Report Type：</td>
					<td><input type="radio" name="reportType" value="display" checked/>display
						<input type="radio" name="reportType" value="save" />save
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button"
									onClick="exportIncentiveCalcList1(this,'${request_navTabId}')">
									<!--检索-->
									<spring:message code="public.title.search" />
								</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</form>
	<div id="deptMon" class="unitBox" width="150%"></div>
	<form id="excelExportForm_rp0102" name="excelExportForm_rp0102" method="post">
			<input type="hidden" id="password" name="password" value="" /> 
			<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="" /> 
			<input type="hidden" id="ORG_ID" name="ORG_ID" value="" /> 
			<input type="hidden" id="JOB_TP" name="JOB_TP" value="" /> 
			<input type="hidden" id="DATE" name="DATE" value="" /> 
			<input type="hidden" id="ATT_STRT_DTIME" name="ATT_STRT_DTIME" value="" /> 
			<input type="hidden" id="ATT_CD" name="ATT_CD" value="" /> 
			<input type="hidden" id="ATT_END_DTIME" name="ATT_END_DTIME" value="" /> 
			<input type="hidden" id="reportName" name="reportName" value="attRetrieveDepartmentKind" /> 
			<input type="hidden" id="suffix" name="suffix" value="" />
			<input type="hidden" name="ATT_NAME" id="ATT_NAME" value=""/>
	</form>
	<div id="showPDFPop" ></div>
</div>
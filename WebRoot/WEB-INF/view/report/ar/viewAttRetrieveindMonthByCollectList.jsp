<%@ page contentType="text/html; charset=UTF-8" language="java"errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function exportIncentiveCalcList(a, navTabId) {
	var suffix = $('input[name="suffix"]:checked').val();
	var report = $('input[name="reportType"]:checked').val();
	var sform = document.getElementById("onlyForm");
	var SUBSD_CD = $("#SUBSD_CD").val();
	var JOB_NAME = sform.seach_JOB_TP[sform.seach_JOB_TP.selectedIndex].text;
	var JOB_TP = sform.seach_JOB_TP.value;
	var reportName = $("#reportName").val();
	
	
	if(SUBSD_CD == 'LGEND'){  //只有nd法人是单选
		var SUBSD_NAME = $("#seachDept").val();
	}if (SUBSD_CD == 'SST'){
		var SUBSD_NAME='SST';
	}
    else{
		var SUBSD_NAME = $(":input[name='seachDept']").val();
	}
	
	//var ORG_ID = $(":input[sysLong='seachDept']").val();
	
	if(SUBSD_CD == 'SST'){
    var ORG_ID=document.getElementById("deptNos").value;
	}else{
	var ORG_ID = $(":input[name='seach_ORG_ID']").val();
	}
	
	if(SUBSD_CD == 'LGEND'){
	var DEPT_ONLY=sform.dpet_only.value;
	}
	
	if(suffix == 'xls'){
		document.getElementById("rp0102Link").innerHTML = "设置导出文件密码";
		var eForm = document.getElementById("excelExportForm_rp0102");
		eForm.SUBSD_CD.value	= sform.seach_SUBSD_CD.value;
		eForm.JOB_TP.value 		= sform.seach_JOB_TP.value;
		eForm.SUBSD_NAME.value	= SUBSD_NAME;
		eForm.ATT_MON.value 	= sform.seach_YEAR.value+''+sform.seach_MONTH.value;
		eForm.reportName.value 	= sform.reportName.value;
		eForm.ORG_ID.value		= ORG_ID;
		if(SUBSD_CD == 'LGEND'){
		eForm.DEPT_ONLY.value	= DEPT_ONLY;
		}
		eForm.suffix.value 		= $('input[name="suffix"]:checked').val();
		$("#importExcelDialog_rp0102").attr('href', "/sys/encryptExcel"
				+"?exportFunName=/report/pac04/exportDatilyReport"
				+"&navTabId=rpt0102"
				+"&formId=excelExportForm_rp0102");
		$("#importExcelDialog_rp0102").attr('width', "300");
		$("#importExcelDialog_rp0102").attr('height', "150");
		$("#importExcelDialog_rp0102").click();
	}else if(suffix == 'pdf'  && report == 'save'){
		//$("#result").attr("src","");
		$("#onlyForm").attr("target",""); 
		$("#onlyForm").attr("action","/report/pac04/exportDatilyReport");
		$("#ATT_MON").attr('value',sform.seach_YEAR.value+''+sform.seach_MONTH.value);
		$("#SUBSD_NAME").attr('value',SUBSD_NAME);
		$("#ORG_ID").attr('value',ORG_ID);
		
		if(SUBSD_CD == 'LGEND'){
			$("#DEPT_ONLY").attr('value',DEPT_ONLY);
		}
		
		$("#onlyForm").submit();
		
	}else if(suffix == 'html'|| report == 'display'){
		/**新页面显示
		$("#onlyForm").attr("target","ajax");
		$("#onlyForm").attr("action","/report/pac04/exportDatilyHtmlReport");
		$("#onlyForm").submit();*/
		//alert(sform.seach_SUBSD_CD.value+","+ORG_ID+","+sform.seach_YEAR.value+''+sform.seach_MONTH.value);
	
		//dialog的参数
		var options = {mask:true, 
                    width:1000, height:600,
                    drawable:true,
                    resizable:true
                }; 
		
		//查询报表的参数 一定要用@进行连接 否则导致次条件无效
		var param = "SUBSD_CD="+sform.seach_SUBSD_CD.value
        +"@JOB_TP="+sform.seach_JOB_TP.value
        +"@ATT_MON="+sform.seach_YEAR.value+''+sform.seach_MONTH.value
        +"@reportName="+sform.reportName.value
        +"@SUBSD_NAME="+encodeURI(SUBSD_NAME)
        +"@ORG_ID="+ORG_ID
        if(SUBSD_CD == 'LGEND'){
        +"@DEPT_ONLY="+DEPT_ONLY
        }
        +"@suffix="+$('input[name="suffix"]:checked').val();
		//打开dialog 需要根据各自的页面更换actionUrl和报表名称 其它值固定
		
		$.pdialog.open("/report/common/showPDFPop?"
				+"params="+param
                +"&actionUrl="+"/report/pac04/exportDatilyHtmlReport"
				, "showPDFPop", "部门日考勤报表",options);
	}
}
function changeReportName(){
	var $form = $("#onlyForm");	
	var renyuan = $("#onlyForm select[name='seach_JOB_TP']").val();//人员类型组
	var faren = $("#onlyForm input[name='seach_SUBSD_CD']").val();//法人
	if(faren == "LGEHZ" && renyuan== "211792"){
 		$form.find("input[name='reportName']").attr("value","attRetrieveindMonthByCollectLGEHZ");
 		//当hz的人员类型为管理职时，有一个特殊的表
	}else if (faren == "LGEHZ"){
 		$form.find("input[name='reportName']").attr("value","attRetrieveindMonthByCollect");//
	}else if (faren == "SST" && renyuan== "211809"){
		$form.find("input[name='reportName']").attr("value","attRetrieveindMonthByCollectLGETAforLinShiZhi");
	}
	
}
//-------------------------------------

</script>
<div class="pageHeader">
	<a id="importExcelDialog_rp0102" href="#" target="dialog" mask="true"><span
		id="rp0102Link" style="display: none"></span></a> 
	<a id="displayCollect" href="#" target="ajax" rel="deptMon"></a>
	<form action="/report/pac04/exportDatilyReport" id="onlyForm" rel="htmlReport" method="post">
	<input type="hidden" id="reportName" name="reportName" 
	<c:if test="${defaultCpny eq 'SST'}">value="attRetrieveindMonthByCollectLGETA"</c:if>
	<c:if test="${defaultCpny eq 'LGEND'}">value="attRetrieveindMonthByCollectLGEND"</c:if>
	<c:if test="${defaultCpny ne 'SST' and defaultCpny ne 'LGEND'}">value="attRetrieveindMonthByCollect"</c:if>
	/>
	<input type="hidden" id="SUBSD_CD" name="seach_SUBSD_CD" value="${defaultCpny}"/>
	<input type="hidden" id="ORG_ID" name="ORG_ID" value=""/>
	  
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>部门：</td>
					<td>
						<c:if test="${defaultCpny eq 'LGEND'}"> 
						<ait:deptList name="seach_ORG_ID" cpnyId="${defaultCpny}" id="seachDept"/>
						<ait:deptTreeIcon name="seach_ORG_ID" cpnyId="${defaultCpny}" limit="ar" id="seachDept" selected="${ORG_ID}"/>
						</c:if>					
						<c:if test="${defaultCpny ne 'LGEND' and defaultCpny ne 'SST'}"> 
						 <!-- id与name互换是为了保证查出的value为数字。。。 -->
						<ait:deptTreeMulti name="seachDept" id="seach_ORG_ID"  limit="ar" level2="1,2,3,4,5,6,7,8"  />
						</c:if>
						<input type="hidden" id="SUBSD_NAME" name="seach_SUBSD_NAME" value=""/>
						
						<%-- <input type="text" id="deptNameShow" name="deptNameShow"  readonly="readonly"/>
						<input type="hidden" id="deptNoHidden" name="deptNoHidden" />
						<a rel="deptSeach" href="/sys/deptSearchPop/deptTreeSearchPop?limit=hr&defaultCpny=${defaultCpny }&deptNoInputId=deptNoHidden&deptNameInputId=deptNameShow" title="部门搜索"
					          target="dialog" mask="true" style="top: 0px;" width="350" height="350" id="deptSeachHref" >
						<span class="tree_icon"/></a> --%>
					</td>
					<td>
					<c:if test="${defaultCpny ne 'LGEHZ' and defaultCpny ne 'LGEND'}"> 
					人员类型组：
					</c:if>
					<c:if test="${defaultCpny eq 'LGEHZ' or defaultCpny eq 'LGEND'}"> 
					人员类型：
					</c:if>
					</td>
					<td>
						<!--  <select name="seach_JOB_TP">
					        <option value="211807">
								全部	
								</option>
							<c:forEach items="${jobTypeGroupNameList}" var="item" varStatus="i">
								<option value="${item.JOBTYPE_GROUP_NO }"
									<c:if test="${JOB_TP eq item.JOBTYPE_GROUP_NO}">selected</c:if>>
									${item.JOBTYPE_GROUP_NAME}
								</option>
							</c:forEach>
						</select>-->

						<c:if test="${defaultCpny eq 'LGEND' or defaultCpny eq 'LGEHZ'}"> 
						<ait:SelectEmpTypeCode name="seach_JOB_TP" selected="${EMP_TYPE_GROUP}" limit="ar" onChangeName="changeReportName();" /> <!--  -->
						</c:if>
						<c:if test="${defaultCpny ne 'LGEND' and defaultCpny ne 'LGEHZ'}">
						<ait:SelectEmpTypeCode name="seach_JOB_TP" selected="${EMP_TYPE_GROUP}" limit="ar" type="group"  onChangeName="changeReportName(); "/>
						</c:if>


					</td>
					
					
				</tr>
				<tr>
					<td>日期：</td>
					<td>
						<ait:date yearName="seach_YEAR" yearSelected="${YEAR}" monthName="seach_MONTH" monthSelected="${MONTH}"/>
						<input type="hidden" id="ATT_MON" name="ATT_MON" value=""/>
					</td>
					<td>法人：</td>
					<td>
						<select name="reportName_dab" disabled="disabled">
							<c:forEach items="${companyList}" var="item" varStatus="i">
								<option value="${item.CPNY_ID}"
									<c:if test="${defaultCpny eq item.CPNY_ID}">selected</c:if>>
									${item.CPNY_ID}
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
					
				<c:if test="${defaultCpny eq 'LGEND'}">
					<tr>
					    <td>只查所选部门：</td>
						<td>
							
								<select name="dpet_only" id='dpet_only'>
									<option value="1"> 所有部门
									</option>
									<option value="2"> 单选部门</option>
							</select>
							<input type='hidden' name="DEPT_ONLY" id="DEPT_ONLY" value=""/>
						</td>
					</tr>
				</c:if>
				<tr>
					<td>File Type：</td>
					<td>
						<input type="radio" name="suffix" value="pdf" checked onClick="result.location.href='/resources/reportFile/blank.html'"/>pdf
						<input type="radio" name="suffix" value="xls" onClick="result.location.href='/resources/reportFile/blank.html'"/>xls
						<input type="radio" name="suffix" value="html" onClick="result.location.href='/resources/reportFile/blank.html'"/>html
					</td>
					<td>Report Type：</td>
					<td>
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
								<button type="button" onClick="exportIncentiveCalcList(this,'${request_navTabId}')">
									<!--检索--><spring:message code="public.title.search" />
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<c:if test="${defaultCpny eq 'SST'}"> 
		<%@ include file="/WEB-INF/view/ar/attendanceSettings/updateAttendanceKeeperDeptTreeView.jsp"%>
		
			
		</c:if>
	</form>
	<div id="deptMon" class="unitBox" width="150%">
		<!--#include virtual="list1.html" -->
	</div>
	<form id="excelExportForm_rp0102" name="excelExportForm_rp0102" method="post">
		<input type="hidden" id="password" name="password" value="" />
		<input type="hidden" id="SUBSD_CD" name="SUBSD_CD" value="" />
		<input type="hidden" id="ORG_ID" name="ORG_ID" value="" />
		<input type="hidden" id="DEPT_ONLY" name="DEPT_ONLY" value="" />
		<input type="hidden" id="SUBSD_NAME" name="SUBSD_NAME" value="" />
		<input type="hidden" id="JOB_TP" name="JOB_TP" value="" />
		<input type="hidden" id="ATT_MON" name="ATT_MON" value="" />
		<input type="hidden" id="reportName" name="reportName" value="attRetrieveindMonthByCollect${item.CPNY_ID}" />
		<input type="hidden" id="suffix" name="suffix" value="" />
	</form>
	<div id="showPDFPop" ></div>
	
</div>

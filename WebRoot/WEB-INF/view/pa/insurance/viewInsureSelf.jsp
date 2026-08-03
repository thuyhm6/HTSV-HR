<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function exportExcel() {
	var obj = document.getElementById('excelPost');
	obj.action = "/pa/insurance/viewInsureSelfExcel";
	obj.submit();
}
//excel导出
function exportSalesmanEvalInfo(a, navTabId) {
	var sform = document.getElementById("excelSelfPost");
	alertMsg.confirm("确定要导出么?", {
		okCall : function() {
			//用于excel导出的表单参数处理
			var eForm = document.getElementById("excelExportForm_iself0102"); 
			document.getElementById("iself0102").innerHTML = "EXCEL密码设置";
			eForm.paYear.value		= sform.seach_paYear.value;
			eForm.paMonth.value	    = sform.seach_paMonth.value;
			eForm.EMPID.value 		= sform.seach_EMPID.value;
			eForm.DEPTNO.value 		= sform.seach_DEPTNO.value;
			eForm.AREA.value 		= sform.seach_AREA.value;
			eForm.EmpTypeCodeNo.value 		= sform.seach_EmpTypeCodeNo.value;
			eForm.JobTypeGroupNo.value 		= sform.seach_JobTypeGroupNo.value;
			eForm.EmpOffice.value 		= sform.seach_EmpOffice.value;
			$("#importExcelDialog_iself0102").attr('href', "/sys/encryptExcel"
					+"?exportFunName=/pa/insurance/viewInsureSelfExcel"
					+"&navTabId=pa0419"
					+"&formId=excelExportForm_iself0102");
			$("#importExcelDialog_iself0102").attr('width', "300");
			$("#importExcelDialog_iself0102").attr('height', "150");
			$("#importExcelDialog_iself0102").click();
		}
	});
}
$(document).ready(function(){
	if($("#pa2018fy_seach_JobTypeGroupNo").val() != ''){
		var EMP_TYPE = $("#pa2018fy_seach_EmpTypeCodeNo").val();
		ajaxEmpTypeForGroupToList(EMP_TYPE,"pa2018fy_seach_JobTypeGroupNo","pa2018fy_seach_EmpTypeCodeNo",
				"pa2018fy_seach_CPNY","pa2018fy_limit");
		//要传进的参数分别为 -1，人员类型组select 对象，人员类型select name，法人选项id，要查询的是否为group，权限super/hr/ar/pa
	}
});
</script>
<a id="importExcelDialog_iself0102" href="#" target="dialog" mask="true">
	<span id="iself0102" style="display: none"></span></a> 
<div class="pageHeader">
	<form action="/pa/insurance/viewInsureSelf" method="post"
		rel="pagerForm" onsubmit="return navTabSearch(this);" id="excelSelfPost">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td style="text-align: right">
						<!--年份--><spring:message code="pa.payear.title.payear" />
					</td>
					<td style="text-align: left">
						<ait:date yearName="seach_paYear" yearSelected="${paYear}"
							monthName="seach_paMonth" monthSelected="${paMonth}" />
					</td>
					<td>
						<!--工号/姓名--><spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td>
						<input type="text" name="seach_EMPID" value="${EMPID}" />
					</td>
					<td>
						<%--部门--%><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName"/>
					</td>
					<td>
						<ait:deptList name="seach_DEPTNO" id="addPaaaa" limit="pa"/>
						<ait:deptTreeIcon name="seach_DEPTNO" id="addPaaaa" selected="${DEPTNO}" limit="pa"/>
					</td>
					
					<td>福利地区</td>
					<td>
						<select name="seach_AREA">
							<option value="">请选择</option>
							<c:forEach items="${welfareList}" var="fare">
								<option value="${fare.NO}" <c:if test="${fare.NO eq AREA}">selected</c:if>>${fare.CONTENT}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
				   <td>人员类型组</td>
				   <td> 
				   	<input type="hidden" id="pa2018fy_limit" name="limit" value="pa">
					<input type="hidden" id="pa2018fy_seach_CPNY" name="seach_CPNY" value="${defaultCpny}">
			<ait:SelectEmpTypeCode id="pa2018fy_seach_JobTypeGroupNo" name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="pa" type="group"
			onChangeName="ajaxEmpTypeForGroupToList(-1,pa2018fy_seach_JobTypeGroupNo,pa2018fy_seach_EmpTypeCodeNo,pa2018fy_seach_CPNY,pa2018fy_limit)"/>
						</td>
						<td>
						人员类型</td>
						<td>
		 					<ait:SelectEmpTypeCode id="pa2018fy_seach_EmpTypeCodeNo" name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" cnpyID="${defaultCpny}" limit="pa"/>
						</td>
						<td>在职状态</td>
						<td>
		 <ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<!--检索--><spring:message code="public.title.search" />
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<from action="/pa/insurance/viewItemBatchImport" method="post" rel="pagerForm" onsubmit="return navTabSearch(this);">
	<div class="formBar">
		<ul class="toolBar">
			<li>
				<a onClick="exportSalesmanEvalInfo(this,'pa0419');"><span>
					<%--Excel导出--%>	<spring:message code="ar.addempshift.title.excelexport" />
				</span>
				</a>
			</li>
		</ul>
	</div>
	<table class="table" width="150%" layoutH="200">
		<thead>
			<%--<tr>
				<th colspan="18" style="text-align: center">保险基本信息</th>
			</tr>
			<tr>
				<th rowspan="2" style="text-align: center">部门</th>
				<th rowspan="2" style="text-align: center">姓名/工号</th>
				<th rowspan="2" style="text-align: center">考勤月</th>
				<th rowspan="2" style="text-align: center">福利地区</th>
				<th colspan="10" style="text-align: center" width="50%">社会保险</th>
				<th colspan="2" style="text-align: center">公积金</th>
				<th colspan="2" style="text-align: center">合计</th>
			</tr>--%>
			
			<c:choose>
				<c:when test="${CPNY_ID eq 'TSTO'}">
					<tr>
				<th style="text-align: center">部门</th>
				<th style="text-align: center">姓名/工号</th>
				<th style="text-align: center">考勤月</th>
				<th style="text-align: center">福利地区</th>
				<th style="text-align: center">养老(公司)</th>
				<th style="text-align: center">失业(公司)</th>
				<th style="text-align: center">医疗(公司)</th>
				<th style="text-align: center">工伤(公司)</th>
				<th style="text-align: center">生育(公司)</th>
				<th style="text-align: center">养老(个人)</th>
				<th style="text-align: center">失业(个人)</th>
				<th style="text-align: center">医疗(个人)</th>
				<th style="text-align: center">社保(公司)</th>
				<th style="text-align: center">社保(个人)</th>
				<th style="text-align: center">保险补扣(个人)</th>
				<th style="text-align: center">保险补扣(公司)</th>
				<th style="text-align: center">大额大病(个人)</th>
				<th style="text-align: center">大额大病(公司)</th>
				<th style="text-align: center">大病统筹(公司)</th>
				<th style="text-align: center">公积金(公司)	</th>
				<th style="text-align: center">公积金(个人)	</th>
				<th style="text-align: center">合计五险一金(公司)</th>
				<th style="text-align: center">合计五险一金(个人)</th>
				<th style="text-align: center">保险公积金合计个人+公司</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${dataList}" var="person">
				<tr>
					<td style="text-align: center">${person.DEPT_NAME}</td>
					<td style="text-align: center">${person.LOCAL_NAME}/${person.EMPID}</td>
					<td style="text-align: center">${person.IS_MONTH}</td>
					<td style="text-align: center">${person.INSRAREA_ID_NAME}</td>
					<td style="text-align: center">${person.IS_ENDOWMENT_COR}</td>
					<td style="text-align: center">${person.IS_UNEMPLOY_COR}</td>
					<td style="text-align: center">${person.IS_MEDICAL_COR}</td>
					<td style="text-align: center">${person.IS_INJURY_COR}</td>
					<td style="text-align: center">${person.IS_FERTILITY_COR}</td>
					<td style="text-align: center">${person.IS_ENDOWMENT_PER}</td>
					<td style="text-align: center">${person.IS_UNEMPLOY_PER}</td>
					<td style="text-align: center">${person.IS_MEDICAL_PER}</td>
					<td style="text-align: center">${person.IS_TOTAL_COR}</td>
					<td style="text-align: center">${person.IS_TOTAL_PER}</td>
					<td style="text-align: center">${person.IS_AJUST_PER}</td>
					<td style="text-align: center">${person.IS_AJUST_COR}</td>
					<td style="text-align: center">${person.IS_SERIOUS_P}</td>
					<td style="text-align: center">${person.IS_SERIOUS_C}</td>
					<td style="text-align: center">${person.IS_BIGDISEASE_C}</td>
					<td style="text-align: center">${person.IS_FUND_COR}</td>
					<td style="text-align: center">${person.IS_FUND_RER}</td>
					<td style="text-align: center">${person.COR}</td>
					<td style="text-align: center">${person.PER}</td>
					<td style="text-align: center">${person.PER + person.COR}</td>
				</tr>
			</c:forEach>
		</tbody>
				</c:when>
		<c:when test="${CPNY_ID eq 'LGEYT'}">
		<tr>
				<th style="text-align: center">部门</th>
				<th style="text-align: center">姓名/工号</th>
				<th style="text-align: center">考勤月</th>
				<th style="text-align: center">福利地区</th>
				<th style="text-align: center">人员类型</th>
				<!-- <th style="text-align: center">医疗保险基数(公司)</th>
				<th style="text-align: center">医疗保险基数(个人)</th>
				<th style="text-align: center">养老保险基数(公司)</th>
				<th style="text-align: center">养老保险基数(个人)</th>
				<th style="text-align: center">失业保险基数(公司)</th>
				<th style="text-align: center">失业保险基数(个人)</th>
				<th style="text-align: center">生育保险基数(公司)</th>
				<th style="text-align: center">公积金基数(公司)</th>
				<th style="text-align: center">公积金基数(个人)</th>
				<th style="text-align: center">工伤保险基数(公司)</th>
				<th style="text-align: center">大额大病保险基数(个人)</th> -->
				<th style="text-align: center">社保基数个人</th>
				<th style="text-align: center">社保基数公司</th> 
				<th style="text-align: center">公积金基数(个人)</th>
				<th style="text-align: center">公积金基数(公司)</th>
				<!-- <th style="text-align: center">保险补扣(公司)</th>
				<th style="text-align: center">保险补扣(个人)</th> -->
				<th style="text-align: center">个人养老保险</th>
				<th style="text-align: center">个人失业保险</th>
				<th style="text-align: center">个人医疗保险</th>
				<th style="text-align: center">个人公积金</th>
				<th style="text-align: center">个人保险补扣</th>
				<th style="text-align: center">公司养老保险</th>
				<th style="text-align: center">公司失业保险</th>
				<th style="text-align: center">公司医疗保险</th>
				<th style="text-align: center">公司公积金</th>
				<th style="text-align: center">公司生育保险</th>
				<th style="text-align: center">公司工伤保险</th>
				<th style="text-align: center">公司保险补扣</th>
				<th style="text-align: center">个人五险一金</th>
				<th style="text-align: center">公司五险一金</th>
								
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${dataList}" var="person">
				<tr>
					<td style="text-align: center">${person.DEPT_NAME}</td>
					<td style="text-align: center">${person.LOCAL_NAME}/${person.EMPID}</td>
					<td style="text-align: center">${person.IS_MONTH}</td>
					<td style="text-align: center">${person.INSRAREA_ID_NAME}</td>
					<td style="text-align: center">${person.EMP_TYPE_NAME}</td>
					<!-- <td style="text-align: center">${person.IS_MEDICAL_BC}</td>
					<td style="text-align: center">${person.IS_MEDICAL_BASE}</td>
					<td style="text-align: center">${person.IS_ENDOWMENT_BC}</td>
					<td style="text-align: center">${person.IS_ENDOWMENT_BASE}</td>
					<td style="text-align: center">${person.IS_UNEMPLOY_BC}</td>
					<td style="text-align: center">${person.IS_UNEMPLOY_BASE}</td>
					<td style="text-align: center">${person.IS_FERTILITY_BC}</td>
					<td style="text-align: center">${person.IS_FUND_BC}</td>
					<td style="text-align: center">${person.IS_FUND_BASE}</td>
					<td style="text-align: center">${person.IS_INJURY_BASE}</td>
					<td style="text-align: center">${person.IS_SERIOUS_P}</td>  -->
					<td style="text-align: center">${person.P_INSUR_BASE_PER}</td>
					<td style="text-align: center">${person.P_INSUR_BASE_COP}</td>
					<td style="text-align: center">${person.IS_FUND_BASE }</td>
					<td style="text-align: center">${person.IS_FUND_BC }</td>
					<!-- 
					<td style="text-align: center">${person.P_IS_AJUST_COR}</td>
					<td style="text-align: center">${person.P_IS_AJUST_PER}</td> -->
					<td style="text-align: center">${person.IS_ENDOWMENT_PER}</td>
					<td style="text-align: center">${person.IS_UNEMPLOY_PER}</td>
					<td style="text-align: center">${person.IS_MEDICAL_PER}</td>
					<td style="text-align: center">${person.IS_FUND_RER}</td>
					<td style="text-align: center">${person.IS_AJUST_PER}</td>
					<td style="text-align: center">${person.IS_ENDOWMENT_COR}</td>
					<td style="text-align: center">${person.IS_UNEMPLOY_COR}</td>
					<td style="text-align: center">${person.IS_MEDICAL_COR}</td>
					<td style="text-align: center">${person.IS_FUND_COR}</td>
					<td style="text-align: center">${person.IS_FERTILITY_COR}</td>
					<td style="text-align: center">${person.IS_INJURY_COR}</td>
					<td style="text-align: center">${person.IS_AJUST_COR}</td>
					<td style="text-align: center">${person.IS_TOTAL_PER}</td>
					<td style="text-align: center">${person.IS_TOTAL_COR}</td>
										
				</tr>
			</c:forEach>
		</tbody>
		</c:when>
			</c:choose> 
			
	</table>
	<c:set value="/pa/insurance/viewInsureSelf" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	</from>
<form id="excelExportForm_iself0102" name="excelExportForm_iself0102" method="post">
	<input type="hidden" id="password" name="password" value="" />
	<input type="hidden" id="paYear"   name="paYear" value="" />
	<input type="hidden" id="paMonth"  name="paMonth" value="" />
	<input type="hidden" id="EMPID"    name="EMPID" value="" />
	<input type="hidden" id="DEPTNO"   name="DEPTNO" value="" />
	<input type="hidden" id="AREA" 	   name="AREA" value="" />
	<input type="hidden" id="AREA" 	   name="AREA" value="" />
	<input type="hidden" id="EmpTypeCodeNo" name="EmpTypeCodeNo" value="" />
	<input type="hidden" id="JobTypeGroupNo" 	   name="JobTypeGroupNo" value="" />
	<input type="hidden" id="EmpOffice" 	   name="EmpOffice" value="" />
</form>
</div>
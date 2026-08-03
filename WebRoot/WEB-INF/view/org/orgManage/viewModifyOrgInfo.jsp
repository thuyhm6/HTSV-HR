<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">

//初始化右边页面
$(function(){
	openOnRight('/org/orgManage/viewModifyEmpInfo?DEPTNO=${orgInfo.DEPTNO}&RESUME_NO=' + $("#viewComposeOrgResumeNo",navTab.getCurrentPanel()).val(),'viewModifyOrgEmpInfo_unit');
});
function validateModifyOrgInfoCallback(form,callback) {
	if($("#DEPT_TYPE").val()==''){
		alertMsg.error('<spring:message code="org.title.DEPT_TYPE_ISNOTNULL" />');
		return false;
	}
	var $form = $("#" + form);	
	if (!$form.valid()) {
		return false;
	}
/* 	var COST_CENTER = $("#COST_CENTER",navTab.getCurrentPanel()).val();
	if(COST_CENTER == ''){
		alertMsg.warn('<spring:message code="org.title.COST_CENTER_ISNOTNULL" />');
		return false;
	} */
	var ORG_NAME_LOCAL = $("#ORG_NAME_LOCAL",navTab.getCurrentPanel()).val();
	if(ORG_NAME_LOCAL == ''){
		alertMsg.warn('<spring:message code="org.title.DEPTNAME_ISNOTNULL" />');
		return false;
	}
	var personId = $("input[name='dwz.person.personId']",navTab.getCurrentPanel()).val();
	if(personId == ''){
		alertMsg.warn('<spring:message code="org.title.EMPNAME_ISNOTNULL" />');
		return false;
	}

	if($("input[name='IS_PART_TIME']",navTab.getCurrentPanel()).attr("checked") != "checked" &&
			personId != 'Vacancy'){
		$.ajax({
				type: 'POST',
				url: '/org/orgManage/checkDeptManager',
				data:[{name: 'RESUME_NO', value: '${RESUME_NO}'},
				      {name: 'dwzPersonId', value: personId},
  				      {name: 'interCpnyID', value: '${LoginUser.cpnyId}'},
				      {name: 'DEPTNO', value: $("#DEPTNO",navTab.getCurrentPanel()).val()}],
				dataType:"json",
				cache: false,
				success: function(json){
					if(parseInt(json.result) > 0){
						if(confirm('<spring:message code="org.title.EMPNAME_REPEAT" />')){
							$("input[name='IS_PART_TIME']",navTab.getCurrentPanel()).attr("checked","checked");
						}
					}
				},
				error: DWZ.ajaxError
			});
	}
	alertMsg.confirm('<spring:message code="org.title.SAVE_CONFIRM" />',
  		  	{okCall:function(){
				if(personId == 'Vacancy'){
					$("input[name='dwz.person.personId']",navTab.getCurrentPanel()).val("");
				}
			  	$.ajax({
	  				type: form.method || 'POST',
	  				url:$form.attr("action"),
	  				data:$form.serializeArray(),
	  				dataType:"json",
	  				cache: false,
	  				success: callback || DWZ.ajaxDone,
	  				error: DWZ.ajaxError
	  			});
  	}});
	return false;
}

$(document).ready(function(){
	//Vacancy清空部门长
	$("#cleanVacancy").click(function(){
		$("input[name='dwz.person.personId']",navTab.getCurrentPanel()).val("<spring:message code='org.title.Vacancy' />");//Vacancy
		$("input[name='dwz.person.empName']",navTab.getCurrentPanel()).val("<spring:message code='org.title.Vacancy' />");
		$("input[name='dwz.person.empInfo']",navTab.getCurrentPanel()).val("<spring:message code='org.title.Vacancy' />");
	});
	$("#viewModifyOrgInfo_viewAddOrgInfo").click(function(){
		openOnRight('/org/orgManage/viewAddOrgInfo?PARENT_DEPT_NO=${orgInfo.DEPTNO}&DEPTNO=0&RESUME_NO=' + $("#viewComposeOrgResumeNo").val(),'viewModifyOrgInfo_right_unit');
	});
	$("#viewModifyOrgInfo_modifyOrgInfo").click(function(){
		var $form = $("#viewModifyOrgInfoForm");	
		if (!$form.valid()) {
			return false;
		}
		/* var COST_CENTER = $("#COST_CENTER",navTab.getCurrentPanel()).val();
		if(COST_CENTER == ''){
			alertMsg.warn('<spring:message code="org.title.COST_CENTER_ISNOTNULL" />');
			return false;
		} */
		var ORG_NAME_LOCAL = $("#ORG_NAME_LOCAL",navTab.getCurrentPanel()).val();
		if(ORG_NAME_LOCAL == ''){
			alertMsg.warn('<spring:message code="org.title.DEPTNAME_ISNOTNULL" />');
			return false;
		}
		var deptType = $("#DEPT_TYPE",navTab.getCurrentPanel()).val();
		if(deptType == '80000208'){
			alertMsg.warn('<spring:message code="org.orgManage.SELECT_ORGANIZATION_TYPE.Z" />');//请选择组织类型
			return false;
		}
		var personId = $("input[name='dwz.person.personId']",navTab.getCurrentPanel()).val();
		if(personId == ''){
			alertMsg.warn('<spring:message code="org.title.EMPNAME_ISNOTNULL" />');
			return false;
		}
		if($("input[name='IS_PART_TIME']",navTab.getCurrentPanel()).attr("checked") != "checked" &&
				personId != 'Vacancy'){
			$.ajax({
					type: 'POST',
					url: '/org/orgManage/checkDeptManager',
					data:[{name: 'RESUME_NO', value: '${RESUME_NO}'},
					      {name: 'dwzPersonId', value: personId},
	  				      {name: 'interCpnyID', value: '${LoginUser.cpnyId}'},
					      {name: 'DEPTNO', value: $("#DEPTNO",navTab.getCurrentPanel()).val()}],
					dataType:"json",
					cache: false,
					success: function(json){
						if(parseInt(json.result) > 0){
							if(confirm('<spring:message code="org.title.EMPNAME_REPEAT" />')){
								$("input[name='IS_PART_TIME']",navTab.getCurrentPanel()).attr("checked","checked");
							}
						}
					},
					error: DWZ.ajaxError
				});
		}
		alertMsg.confirm('<spring:message code="org.title.SAVE_CONFIRM" />',
	  		{okCall:function(){
				if(personId == 'Vacancy'){
					$("input[name='dwz.person.personId']",navTab.getCurrentPanel()).val("");
				}
			  	$.ajax({
	  				type: $form.method || 'POST',
	  				url:$form.attr("action"),
	  				data:$form.serializeArray(),
	  				dataType:"json",
	  				cache: false,
	  				success: divAjaxDone,
	  				error: DWZ.ajaxError
	  			});
	  		}});
		return false;
	});
	$("#viewModifyOrgInfo_deleteOrgInfo").click(function(){
		var $form = $("#viewModifyOrgInfoForm");	
		alertMsg.confirm('<spring:message code="button.delete.sure" />',
	  		{okCall:function(){
			  	$.ajax({
	  				type: $form.method || 'POST',
	  				url:"/org/orgManage/deleteOrgInfo",
	  				data:$form.serializeArray(),
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDoneWithForm,
	  				error: DWZ.ajaxError
	  			});
	  		}});
		return false;
	});
	$("#DEPT_TYPE",navTab.getCurrentPanel()).change(function(){
		var deptType = $("#DEPT_TYPE",navTab.getCurrentPanel()).val();
		var orgLevel = '';
		if(deptType == 14015635){
			orgLevel = '1';
		}else if(deptType == 14013951){
			orgLevel = '2';
		}else if(deptType == 14013952){
			orgLevel = '3';
		}else if(deptType == 14013954){
			orgLevel = '4';
		}else if(deptType == 14013953){
			orgLevel = '5';
		}else if(deptType == 14013955){
			orgLevel = '6';
		}else if(deptType == 14015496){
			orgLevel = '7';
		}else if(deptType == 14016371){
			orgLevel = '7';
		}else{
			orgLevel = '0';
		}
		$("#ORG_LEVEL",navTab.getCurrentPanel()).val(orgLevel);
	});
});

function excelimport_org0203(){
	$("#importExcelDialog_org0203").attr('href','/pa/excelImport/importExcelData?importFunName=/importDeptTemp&RESUME_NO=${RESUME_NO}');
	$("#importExcelDialog_org0203").click();
}
</script>
<a id="importExcelDialog_org0203"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_org0203" href="#" target="navTab" mask="true"><span style="display:none;"><spring:message code="ess.title.DAORUJIEGUO" /><!-- 导入结果 --></span></a>
<div class="pageContent">
		<form id="viewModifyOrgInfoForm" method="post" action="/org/orgManage/addOrgInfo" class="pageForm required-validate" >
			<div>
				<div style="margin-bottom:5px;float:left;">
					<c:if test="${resumeActivityInfo.ACTIVITY != '14013947'}">
					<a class="w_button" href="/org/orgManage/viewModifyOrgOrderNo?PARENT_DEPT_NO=${orgInfo.PARENT_DEPT_NO}&RESUME_NO=${RESUME_NO}" 
						target="dialog"  width="300" height="420" mask="true" rel="viewModifyOrgOrderNo"><span><spring:message code="org.title.DEPT_SORT" /><!-- 部门顺序 再定义 --></span></a>
					<a class="w_button" href="/org/orgManage/viewModifyOrgMerge?PARENT_DEPT_NO=${orgInfo.PARENT_DEPT_NO}&RESUME_NO=${RESUME_NO}" 
						target="dialog"  width="500" height="500" mask="true" rel="viewModifyOrgOrderNo"><span><spring:message code="org.title.DEPT_MERGE" /><!-- 部门 合并 --></span></a>
					<a class="w_button" href="/org/orgManage/viewModifyOrgSplit?PARENT_DEPT_NO=${orgInfo.PARENT_DEPT_NO}&RESUME_NO=${RESUME_NO}" 
						target="dialog"  width="700" height="530" mask="true" rel="viewModifyOrgOrderNo"><span><spring:message code="org.title.DEPT_SPLIT" /><!-- 部门 分割 --></span></a>
					</c:if>
				</div>
				<div style="margin-bottom:5px;float:right;">
					<c:if test="${resumeActivityInfo.ACTIVITY != '14013947'}">
					<a class="w_button" id="viewModifyOrgInfo_viewAddOrgInfo"><span><spring:message code="org.title.ADD" /><!-- 新增 --></span></a>
					<a class="w_button" id="viewModifyOrgInfo_deleteOrgInfo"><span><spring:message code="org.title.DELETE" /><!-- 删除 --></span></a>
					<a class="w_button" id="viewModifyOrgInfo_modifyOrgInfo"><span><spring:message code="org.title.SAVE" /><!-- 保存 --></span></a>
					<!--<a class="w_button" href="/pa/excelExport/downloadExcelDeptTemplate?file=DpetAction_Add"><span><spring:message code="ess.message.template_download" /> 模板下载 </span></a>
					<a class="w_button" href="#" onclick="excelimport_org0203();"><span><spring:message code="hrm.contract.Excel_import" /> Excel导入 </span></a>
					--></c:if>
					<a class="w_button" href="/org/orgManage/viewDeptManagerCheck?RESUME_NO=${RESUME_NO}" 
						target="navTab" rel="org0204" title='<spring:message code="org.title.OCCUPANT_CHECK" />'><span><spring:message code="org.title.NEXT_STAGE" /><!-- 下阶段 --></span></a>
				</div>
			</div>
			<div>
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td>
							<table  class="user_table" width="100%">
								<tr>
									<td width="15%" class="td_title"><spring:message code="org.title.dept" /><!-- 部门 --></td>
									<td width="35%" class="td_type">
										<input type="text" id="DEPTNO" name="DEPTNO" readonly="true" value="${orgInfo.DEPTNO}" class="required"  size="35"/>
										<input type="hidden" name="RESUME_NO" value="${RESUME_NO}"/>
									</td>
									<td width="15%" class="td_title">
										<spring:message code="org.title.parentDept" /><!-- 上级部门 -->
									</td>
									<td width="35%" class="td_type">
										<ait:resumeDeptList name="PARENT_DEPT_NO" resumeNo="${RESUME_NO}" id="viewModifyOrgInfo_deptList" />
										<ait:resumeDeptTreeIcon name="PARENT_DEPT_NO" resumeNo="${RESUME_NO}" id="viewModifyOrgInfo_deptList" selected="${orgInfo.PARENT_DEPT_NO}"/>
									</td>
								</tr>
							<c:if test="${LoginUser.cpnyId eq 'HAE'}">
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.ORG_NAME_LOCAL" /><!-- 部门中文名 -->
									</td>
									<td width="35%" class="td_type" colspan="3">
										<input type="text" id="ORG_NAME_LOCAL" name="ORG_NAME_LOCAL" class="required" value="${orgInfo.ORG_NAME_LOCAL}" size="112"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.ORG_NAME_KO" /><!-- 部门韩文名 -->
									</td>
									<td width="35%" class="td_type" colspan="3">
										<input type="text" id="ORG_NAME_KO" name="ORG_NAME_KO" class="required" value="${orgInfo.ORG_NAME_KO}" size="112"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.ORG_NAME_VI" /><!-- 部门越语名 -->
									</td>
									<td width="35%" class="td_type" colspan="3">
										<input type="text" id="ORG_NAME_VI" name="ORG_NAME_VI" class="required" value="${orgInfo.ORG_NAME_VI}" size="112"/>
									</td>
								</tr>
							</c:if>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.ORG_NAME_ENG" /><!-- 部门英文名 -->
									</td>
									<td width="35%" class="td_type" colspan="3">
										<input type="text" id="ORG_NAME_ENG" name="ORG_NAME_ENG" class="required" value="${orgInfo.ORG_NAME_ENG}" size="112"/>
									</td>
								</tr>
								
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.DEPT_TYPE" /><!-- 组织类型 -->
									</td>
									<td width="35%" class="td_type">
									  	<ait:SelectSyCodeByCpnyID name="DEPT_TYPE" selected="${orgInfo.DEPT_TYPE}" parentNo="14013950" cnpyID="${LoginUser.cpnyId}"/><span class="text_red" style="color:red; align=center">*</span>
									</td>
									<td width="15%" class="td_title">
										<spring:message code="org.title.ORG_LEVEL" /><!-- 部门级别 -->
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="ORG_LEVEL" name="ORG_LEVEL" value="${orgInfo.ORG_LEVEL}" size="35" />
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.MINISTER_NAME" /><!-- 部门长名 -->
									</td>
									<td width="35%" class="td_type" colspan="3">
										<c:if test="${!empty orgInfo.EMPID}">
										<input id="personId" name="dwz.person.personId" value="${orgInfo.MANAGER_EMP_ID}" type="hidden" lookupGroup="person"/>
										<input name="dwz.person.empName" type="text" value="${orgInfo.LOCAL_NAME}" readOnly lookupGroup="person" style="float:left;"/>
										<a class="btnLook" href="/ar/attendanceMintenance/viewEmpCalendarList?isEmployeement=1&firstFlag=1&limit=super&pageNum=1" lookupGroup="person">
										<spring:message code="pa.insurance.title.lookUpAndBack"/></a>
										<input name="dwz.person.empInfo" value="${orgInfo.EMPID}/${orgInfo.EMP_TYPE_NAME}/${orgInfo.EMP_OFFICE_NAME}" type="text" readOnly lookupGroup="person" size="45"/>
										</c:if>
										<c:if test="${empty orgInfo.EMPID}">
										<input id="personId" name="dwz.person.personId" value="Vacancy" type="hidden" lookupGroup="person"/>
										<input name="dwz.person.empName" type="text" value="Vacancy" readOnly lookupGroup="person" style="float:left;"/>
										<a class="btnLook" href="/ar/attendanceMintenance/viewEmpCalendarList?isEmployeement=1&firstFlag=1&limit=super&pageNum=1" lookupGroup="person">
										<spring:message code="pa.insurance.title.lookUpAndBack"/></a>
										<input name="dwz.person.empInfo" value="Vacancy" type="text" readOnly lookupGroup="person" size="45"/>
										</c:if>
										<a class="w_button" id="cleanVacancy"><span><spring:message code="org.title.Vacancy" /><!-- Vacancy --></span></a>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.IS_PART_TIME" /><!-- 兼职与否 -->
									</td>
									<td width="35%" class="td_type">
										<input type="checkbox" name="IS_PART_TIME" <c:if test="${orgInfo.IS_PART_TIME eq '1'}">checked</c:if> value="1">
									</td>
									<td width="15%" class="td_title">
										<spring:message code="org.title.DATE_CREATED" /><!-- 部门开始日 -->
									</td>
									<td width="35%" class="td_type">
										<input id="DATE_CREATED" name="DATE_CREATED" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" 
					                    	readonly="true" value="${orgInfo.DATE_CREATED}" size="35"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="hrm.empinfo.COST_CENTER_NAME_LOCAL" /><!-- 成本中心 -->
									</td>
									<td width="85%" class="td_type" colspan="3">
										<ait:SelectOrgInfo name="COST_CENTER" orgType="COST_CENTER" selected="${orgInfo.COST_CENTER}"/>
									</td>
								</tr>
								<!--<c:if test="${LoginUser.cpnyId eq 'SPC_SH' or LoginUser.cpnyId eq 'SPC_HZ' or LoginUser.cpnyId eq 'SPC_NJ'}">
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.att_area" /> 考勤区域 
									</td>
									<td width="35%" class="td_type" colspan="3">
									   	<input type="text" name="COST_CENTER" id="COST_CENTER" value="${orgInfo.COST_CENTER}">
									</td>
								</tr>
								</c:if>
								<tr style="display:none;">
									<td width="15%" class="td_title">
										<spring:message code="org.title.FEE_STANDARD" /> 费用基准 
									</td>
									<td width="35%" class="td_type">
									   	<ait:SelectSyCodeByCpnyID name="FEE_STANDARD" selected="${orgInfo.FEE_STANDARD}" parentNo="14014427" cnpyID="${LoginUser.cpnyId}" limit="ALL"/>
									</td>
									<td width="15%" class="td_title">
										<spring:message code="org.title.DEPT_DISTINGUISH_STANDARD" /> 部门区分基准 
									</td>
									<td width="35%" class="td_type">
									   	<ait:SelectSyCodeByCpnyID name="DEPT_DISTINGUISH_STANDARD" selected="${orgInfo.DEPT_DISTINGUISH_STANDARD}" parentNo="14014426" cnpyID="${LoginUser.cpnyId}" limit="ALL"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.WORD_AREA_NAME" /> 工作地 
									</td>
									<td width="35%" class="td_type">
									   	<ait:SelectOrgInfo name="WORK_AREA" orgType="WORK_AREA" selected="${orgInfo.WORK_AREA}"/>
									</td>
								</tr>
							--></table>
						</td>
					</tr>
				</table>
			</div>
	  	</form>	
</div>
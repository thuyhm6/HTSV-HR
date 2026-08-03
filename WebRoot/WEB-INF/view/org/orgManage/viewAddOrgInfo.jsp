<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
//初始化右边页面
$(function(){
	openOnRight('/org/orgManage/viewModifyEmpInfo?DEPTNO=empty&RESUME_NO=' + $("#viewComposeOrgResumeNo").val(),'viewModifyOrgEmpInfo_unit');
});

$(document).ready(function(){
	//Vacancy清空部门长
	$("#cleanVacancy").click(function(){
		$("input[name='dwz.person.personId']",navTab.getCurrentPanel()).val("Vacancy");
		$("input[name='dwz.person.empName']",navTab.getCurrentPanel()).val("Vacancy");
		$("input[name='dwz.person.empInfo']",navTab.getCurrentPanel()).val("Vacancy");
	});
	$("#viewAddOrgInfo_viewAddOrgInfo").click(function(){
		openOnRight('/org/orgManage/viewAddOrgInfo?PARENT_DEPT_NO=${PARENT_DEPT_NO}&RESUME_NO=' + $("#viewComposeOrgResumeNo").val(),'viewModifyOrgInfo_right_unit');
	});
	$("#viewAddOrgInfo_saveOrgInfo").click(function(){
		if($("#DEPT_TYPE").val()==''){
			alertMsg.error('<spring:message code="org.title.DEPT_TYPE_ISNOTNULL" />');
			return false;
		}
		var $form = $("#viewAddOrgInfoForm");	
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
			alertMsg.warn('<spring:message code="org.title.DEPTNAME_ISNOTNULL" />');//部门名称不能为空
			return false;
		}
		var personId = $("input[name='dwz.person.personId']",navTab.getCurrentPanel()).val();
		if(personId == ''){
			alertMsg.warn('<spring:message code="org.title.EMPNAME_ISNOTNULL" />');//部门长不能为空
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
		alertMsg.confirm('<spring:message code="org.title.SAVE_CONFIRM"/>',
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
	
	$("#viewAddOrgInfo_deleteOrgInfo").click(function(){
		var $form = $("#viewAddOrgInfoForm");	
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
/* 	$("#COST_CENTER",navTab.getCurrentPanel()).change(function(){
		$("#DEPTNO",navTab.getCurrentPanel()).val($("#COST_CENTER",navTab.getCurrentPanel()).val());
	}); */
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
		}
		$("#ORG_LEVEL",navTab.getCurrentPanel()).val(orgLevel);
	});
});

</script>
<div class="pageContent">
		<form id="viewAddOrgInfoForm" method="post" action="/org/orgManage/addOrgInfo" class="pageForm required-validate" >
			<div>
				<div style="margin-bottom:5px;float:left;">
					<c:if test="${resumeActivityInfo.ACTIVITY != '14013947'}">
					<a class="w_button" href="/org/orgManage/viewModifyOrgOrderNo?PARENT_DEPT_NO=${orgInfo.PARENT_DEPT_NO}&RESUME_NO=${RESUME_NO}" 
						target="dialog"  width="300" height="420" mask="true" rel="viewModifyOrgOrderNo"><span><spring:message code="org.title.DEPT_SORT" /><!-- 部门顺序 再定义 --></span></a>
					<a class="w_button" href="/org/orgManage/viewModifyOrgMerge?PARENT_DEPT_NO=${orgInfo.PARENT_DEPT_NO}&RESUME_NO=${RESUME_NO}" 
						target="dialog"  width="500" height="500" mask="true" rel="viewModifyOrgOrderNo"><span><spring:message code="org.title.DEPT_MERGE" /><!-- 部门 合并 --></span></a>
					<a class="w_button" href="/org/orgManage/viewModifyOrgSplit?PARENT_DEPT_NO=${orgInfo.PARENT_DEPT_NO}&RESUME_NO=${RESUME_NO}" 
						target="dialog"  width="500" height="500" mask="true" rel="viewModifyOrgOrderNo"><span><spring:message code="org.title.DEPT_SPLIT" /><!-- 部门 分割 --></span></a>
					</c:if>
				</div>
				<div style="margin-bottom:5px;float:right;">
					<c:if test="${resumeActivityInfo.ACTIVITY != '14013947'}">
					<a class="w_button" id="viewAddOrgInfo_viewAddOrgInfo"><span><spring:message code="org.title.ADD" /><!-- 新增 --></span></a>
					<a class="w_button" id="viewAddOrgInfo_deleteOrgInfo"><span><spring:message code="org.title.DELETE" /><!-- 删除 --></span></a>
					<a class="w_button" id="viewAddOrgInfo_saveOrgInfo"><span><spring:message code="org.title.SAVE" /><!-- 保存 --></span></a>
					</c:if>
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
										<input type="text" id="DEPTNO" name="DEPTNO" value="" class="required"  size="35" />
										<input type="hidden" name="RESUME_NO" value="${RESUME_NO}"/>
										<input type="hidden" name="NEW" value="NEW"/>
									</td>
									<td width="15%" class="td_title">
										<spring:message code="org.title.parentDept" /><!-- 上级部门 -->
									</td>
									<td width="35%" class="td_type">
										<ait:resumeDeptList name="PARENT_DEPT_NO" resumeNo="${RESUME_NO}" id="viewaddOrgInfo_deptList" isHtml="viewAddOrgInfo"/>
										<ait:resumeDeptTreeIcon name="PARENT_DEPT_NO" resumeNo="${RESUME_NO}"  id="viewaddOrgInfo_deptList" selected="${PARENT_DEPT_NO}" isHtml="viewAddOrgInfo"/>
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
										<input type="text" id="ORG_LEVEL" name="ORG_LEVEL" value="${orgInfo.ORG_LEVEL}" size="35" readonly="readonly"/>
									</td> 
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.MINISTER_NAME" /><!-- 部门长名 -->
									</td>
									<td width="35%" class="td_type" colspan="3">
										<input id="personId" name="dwz.person.personId" value="" type="hidden" lookupGroup="person"/>
										<input name="dwz.person.empName" type="text" readOnly lookupGroup="person" style="float:left;"/>
										<a class="btnLook" href="/ar/attendanceMintenance/viewEmpCalendarList?isEmployeement=1&firstFlag=1&limit=super&pageNum=1" lookupGroup="person">
										<spring:message code="pa.insurance.title.lookUpAndBack"/></a>
										<input name="dwz.person.empInfo" type="text" readOnly lookupGroup="person" size="45"/>
										<a class="w_button" id="cleanVacancy"><span><spring:message code="org.title.Vacancy" /></span></a>
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
					                    <input id="DATE_CREATED" name="DATE_CREATED" class="Wdate" onClick="WdatePicker({dateFmt:'dd.MM.yyyy',lang:'en'})" 
					                    	readonly="true" value="${START_DATE}" size="35"/>
					                    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
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
							</table>
						</td>
					</tr>
				</table>
			</div>
	  	</form>	
</div>
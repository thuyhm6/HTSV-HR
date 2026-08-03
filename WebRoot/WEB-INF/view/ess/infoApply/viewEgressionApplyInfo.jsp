<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
<!--
function validateEgressionApplyCallback(form,callback) {	
	var $form = $(form);	
	if (!$form.valid()) {
		return false;
	}
	
    var fromTime  = document.getElementById("EG_FROM_TIME").value;
    var toTime    = document.getElementById("EG_TO_TIME").value; 
    var applyTypeCode = document.getElementById("EG_APPLY_TYPE_CODE").value;

    if(applyTypeCode==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.egressionApplyTypeIsMust"/>');
       return false;
	}
    
    if(fromTime>toTime){
       alertMsg.error('<spring:message code="alert.message.ess.viewApply.evStartTimeNotLaterThanLeaveEndTime"/>');
	   return false;
	}
	
	if (confirm ('<spring:message code="alert.message.ess.infoApply.areYouSureToApply"/>')){	          
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: callback || DWZ.ajaxDone,
		error: DWZ.ajaxError
	});		
	}return false;
}

function passEgressionApplyValue()
{    
    var fromTime  = document.getElementById("EG_FROM_TIME").value;
    var toTime    = document.getElementById("EG_TO_TIME").value;
    var applyTypeCode = document.getElementById("EG_APPLY_TYPE_CODE").value;
    var applyTypeNo = document.getElementById("EG_APPLY_TYPE_NO").value;
    
    if(fromTime==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.egressionStartTimeIsMust"/>');
       return false;
	}
    if(toTime==""){
	   alertMsg.error('<spring:message code="alert.message.ess.infoApply.egressionEndTimeIsMust"/>');
	   return false;
	}
	if(applyTypeCode==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.egressionApplyTypeIsMust"/>');
       return false;
	}
    if(fromTime>toTime){
	   alertMsg.error('<spring:message code="alert.message.ess.viewApply.evStartTimeNotLaterThanLeaveEndTime"/>');
	   return false;
	}
    document.getElementById("viewEgressionApplyInfoHref").href = 
    document.getElementById("viewEgressionApplyInfoHref").href+
                            "&&APPLY_TYPE_CODE="+applyTypeCode+"&&FROM_TIME="+fromTime+"&&TO_TIME="+toTime+
                            "&&APPLY_TYPE_NO="+applyTypeNo;
    document.getElementById("viewEgressionApplyInfoHref").click();		
}
//-->
</script>

<div class="panel">
	<h1><spring:message code="ess.infoApply.title.egressionApply"/><!--外出申请--></h1>
	<div>
	<form method="post" action="/ess/infoApply/addEgressionApply" class="pageForm required-validate" onsubmit="return validateEgressionApplyCallback(this,navTabAjaxDone);">
		
	   <div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="ess.infoApply.title.apply"/><!--申请-->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	         
	   <table class="user_table" width="100%" layoutH="0" border="1" cellpadding="2" cellspacing="1">
			<tr>
				<td width="20%" class="td_title"><spring:message code="public.title.empIdAndName"/><!-- 工号/姓名 --></td>
				<td width="20%" class="td_type">
				    ${personInfo.EMPID} / ${personInfo.LOCAL_NAME}
                    <input id="PERSON_ID" name="PERSON_ID" type="hidden" size="30"
						   value="${personInfo.PERSON_ID}" />	
				    <input id="EG_APPLY_TYPE_NO" name="EG_APPLY_TYPE_NO" type="hidden" size="30"
						   value="16201" />		   
				</td>
				<td width="20%" class="td_title"><spring:message code="public.title.positionName"/><!--职岗位--></td>
				<td width="40%" class="td_type">
				    ${personInfo.POSITION_NAME}
				</td>
			</tr>
			<tr>
				<td width="20%" class="td_title"><spring:message code="ess.infoApply.title.essApplyTime"/><!--申请日期--></td>
				<td width="80%" class="td_type" colspan = "3">
                    ${CREATE_DATE}
				</td>
			</tr>
			<tr>
				<td width="20%" class="td_title"><spring:message code="ess.infoApply.title.egressionStartTime"/><!--外出开始时间--></td>
				<td width="20%" class="td_type">
				    <input type="text" id="EG_FROM_TIME" name="EG_FROM_TIME" class="date required" format="yyyy-MM-dd HH:mm" readonly="true" value="${FROM_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				</td>
				<td width="20%" class="td_title"><spring:message code="ess.infoApply.title.egressionEndTime"/><!--外出结束时间--></td>
				<td width="40%" class="td_type">
				    <input type="text" id="EG_TO_TIME" name="EG_TO_TIME" class="date required" format="yyyy-MM-dd HH:mm" readonly="true" value="${TO_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>				    
				</td>    
			</tr>
			<tr>
				<td width="20%" class="td_title"><spring:message code="ess.infoApply.title.egressionApplyType"/><!--外出类型--></td>
				<td width="20%" class="td_type">
				    <ait:SelectSyCodeByCpnyID name="EG_APPLY_TYPE_CODE" parentNo="16201" cnpyID="${defaultCpny}" selected="${EG_APPLY_TYPE_CODE}" limit="all"/>
				</td>
				<td width="20%" class="td_title"><spring:message code="ess.infoApply.title.affirmor"/><!--决裁者--></td>
				<td width="40%" class="td_type">
					<a rel="egressionApplyAffirmView" onclick="passEgressionApplyValue();">
					   <span style="cursor:pointer;"><spring:message code="ess.infoApply.title.viewDetail"/><!--查看详细--></span>
					</a>
				    <a rel="egressionApplyAffirmView" href="/ess/infoApply/viewAffirmorByPersonIdAndAppCode?APPLY_TYPE_NO=16201&&PERSON_ID=${personInfo.PERSON_ID}"  
				       target="dialog" mask="true" width="300" height="300" id="viewEgressionApplyInfoHref" ></a>
				</td>
			</tr>
			<tr>
			    <td width="20%" class="td_title"><spring:message code="ess.infoApply.title.workContent"/><!--工作内容--></td>
			    <td width="80%" class="td_type" colspan="3"><textarea name="LEAVE_REASON" cols="80" rows="2"></textarea></td>
			</tr>		
	</table>	
    <div id="egressionApplyAffirmView" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
  </form>	
</div>
</div>
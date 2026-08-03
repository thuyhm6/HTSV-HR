<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
<!--
function validateLeaveApplyCallbackEss0220(form,callback) {	
	var $form = $(form);	
	if (!$form.valid()) {
		return false;
	}
	
  	var fromTime  = $("#LEAVE_FROM_TIME",navTab.getCurrentPanel()).val();//document.getElementById("LEAVE_FROM_TIME").value;
    var toTime    = $("#LEAVE_TO_TIME",navTab.getCurrentPanel()).val();//document.getElementById("LEAVE_TO_TIME").value; 
    var applyTypeCode = $("#LEAVE_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val();//document.getElementById("LEAVE_APPLY_TYPE_CODE").value;
    
    //if(applyTypeCode==""){
      // alertMsg.error('<spring:message code="alert.message.ess.viewApply.leaveApplyApplyIsMust"/>');
       //return false;
	//}
    if(fromTime==toTime){
       alertMsg.error('调休日不能和原休息日相同');
	   return false;
    }
    if(fromTime>toTime){
	   alertMsg.error('<spring:message code="alert.message.ess.viewApply.leaveStartTimeNotLaterThanLeaveEndTime"/>');
	   return false;
	}
	
	if (confirm ("确定要申请吗？")){	          
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

function passLeaveApplyValueEss0220(){    
   	var fromTime  = $("#LEAVE_FROM_TIME",navTab.getCurrentPanel()).val();//document.getElementById("LEAVE_FROM_TIME").value;
    var toTime    = $("#LEAVE_TO_TIME",navTab.getCurrentPanel()).val();//document.getElementById("LEAVE_TO_TIME").value; 
    //var applyTypeCode = $("#LEAVE_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val();//document.getElementById("LEAVE_APPLY_TYPE_CODE").value;
    
    if(fromTime==""){
    	
       //alertMsg.error('<spring:message code="alert.message.ess.viewApply.leaveStartTimeIsMust"/>');
       alertMsg.error('原休息日不能为空');
       return false;
	}
    if(toTime==""){
	   //alertMsg.error('<spring:message code="alert.message.ess.viewApply.leaveEndTimeIsMust"/>');
	    alertMsg.error('调休日不能为空');
	   return false;
	}
    
<%--	if(applyTypeCode==""){--%>
<%--       alertMsg.error('<spring:message code="alert.message.ess.viewApply.leaveApplyApplyIsMust"/>');--%>
<%--       return false;--%>
<%--	}--%>
    //if(fromTime>toTime){
	   //alertMsg.error('<spring:message code="alert.message.ess.viewApply.leaveStartTimeNotLaterThanLeaveEndTime"/>');
	   //return false;
	//}
    document.getElementById("viewLeaveApplyInfoHrefEss0220").href = 
    document.getElementById("viewLeaveApplyInfoHrefEss0220").href+"&APPLY_TYPE_CODE="+123646+"&FROM_TIME="+fromTime+"&TO_TIME="+toTime;
    document.getElementById("viewLeaveApplyInfoHrefEss0220").click();		
}
//-->
</script>

<div class="panel">
	<h1><spring:message code="ess.infoApply.title.leaveApply"/><!--休假申请--></h1>
	<div>
	<form method="post" action="/ess/infoApply/addLeaveApply" class="pageForm required-validate" onsubmit="return validateLeaveApplyCallbackEss0220(this,navTabAjaxDone);">
		   <input id="defaultCpny" name="defaultCpny" type="hidden" size="30"
						   value="${defaultCpny}" />
		<input type="hidden" name="LEAVE_APPLY_TYPE_CODE" id="LEAVE_APPLY_TYPE_CODE" value="123646"/>	
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
		
	<%--<table  class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
	   <tr>
	   	   <c:if test="${defaultCpny=='C04'}">
	       <td width="20%" class="td_title" ><spring:message code="ess.infoApply.title.restAnnualLeaveJN"/><!--今年剩余年假--></td>
	       </c:if>
	       <c:if test="${defaultCpny!='C04'}">
	       <td width="20%" class="td_title"><spring:message code="ess.infoApply.title.restAnnualLeave"/><!--今年剩余年假--></td>
	       </c:if>
	       <td width="20%" class="td_type" >${restAnnualLeave} </td>
	         <input id="restAnnualLeaveJN" name="restAnnualLeaveJN" type="hidden" size="30" value="${restAnnualLeaveQN}" />	
	       <td width="20%" class="td_title"><!--剩余调休-->
	       		<spring:message code="ess.infoApply.title.surAdjustRest"/>
	       </td>
	       <td width="40%" class="td_type" >${adjustRest}</td>	          
	   </tr>
	   
	   <c:if test="${defaultCpny=='C04'}">
	    <tr>
	       <td width="20%" class="td_title"><spring:message code="ess.infoApply.title.restAnnualLeaveQN"/><!--去年剩余年假--></td>
	       <td width="20%" class="td_type" >${restAnnualLeaveQN} </td>
	                <input id="restAnnualLeaveQN" name="restAnnualLeaveQN" type="hidden" size="30"
					value="${restAnnualLeaveQN}" />	
	   </tr>
	   </c:if>
	   
	</table>		
	--%><table  class="user_table" width="100%" layoutH="0" border="1" cellpadding="2" cellspacing="1">
			<tr>
				<td width="20%" class="td_title"><spring:message code="public.title.empIdAndName"/><!-- 工号/姓名 --></td>
				<td width="20%" class="td_type">
				    ${personInfo.EMPID} / ${personInfo.LOCAL_NAME}
                    <input id="PERSON_ID" name="PERSON_ID" type="hidden" size="30"
						   value="${personInfo.PERSON_ID}" />	
				    <input id="APPLY_TYPE_NO" name="APPLY_TYPE_NO" type="hidden" size="30"
						   value="123645" />		   
				</td>
				<td width="20%" class="td_title"><spring:message code="hr.viewPersonalInfo.title.POSITION_NAME"/><!--职位-->/<spring:message code="hr.viewPersonalInfo.title.DUTY_NAME" /><!--职责-->
				</td>
				<td width="40%" class="td_type">
				    ${personInfo.POSITION_NO_NAME}/${personInfo.DUTY_NAME}
				</td>
			</tr>
			<tr>
				<td width="20%" class="td_title"><spring:message code="ess.infoApply.title.essApplyTime"/><!--申请日期--></td>
				<td width="20%" class="td_type" >
                    ${CREATE_DATE}
				</td>
				<td width="20%" class="td_title"><spring:message code="ess.infoApply.title.affirmor"/><!--决裁者--></td>
				<td width="40%" class="td_type">
					<a rel="leaveApplyAffirmView" onclick="passLeaveApplyValueEss0220();">
					   <span style="cursor:pointer;"><spring:message code="ess.infoApply.title.viewDetail"/><!--查看详细--></span>
					</a>
				    <a rel="leaveApplyAffirmView" href="/ess/infoApply/viewAffirmorByPersonIdAndAppCode?APPLY_TYPE_NO=123646&PERSON_ID=${personInfo.PERSON_ID}"  
				       target="dialog" mask="true" width="300" height="300" id="viewLeaveApplyInfoHrefEss0220" ></a>
				</td>
			</tr>
			<tr>
				<td width="20%" class="td_title"><spring:message code="heran.ess.viewLikeLeaveApplyInfo.yuanxiuxiri"/><!--  -原休息日--></td>
				<td width="20%" class="td_type">
				    <input type="text" id="LEAVE_FROM_TIME" name="LEAVE_FROM_TIME" class="date required" format="yyyy-MM-dd" readonly="true" value="${LEAVE_FROM_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				</td>
				<td width="20%" class="td_title"><spring:message code="heran.ess.viewLikeLeaveApplyInfo.tiaoxiuri"/><!-- 调休日 --></td>
				<td width="40%" class="td_type">
				    <input type="text" id="LEAVE_TO_TIME" name="LEAVE_TO_TIME" class="date required" format="yyyy-MM-dd" readonly="true" value="${LEAVE_TO_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>				    
				</td>    
			</tr>
			<%--<tr>
				<td width="20%" class="td_title"><spring:message code="ess.infoApply.title.leaveApplyType"/><!--休假类型--></td>
				<td width="20%" class="td_type">
				    <ait:SelectSyCodeByCpnyID name="LEAVE_APPLY_TYPE_CODE" parentNo="21" cnpyID="${defaultCpny}" selected="${LEAVE_APPLY_TYPE_CODE}" limit="all"/>
				</td>
				
			</tr>
			--%>
			<tr>
			    <td width="20%" class="td_title"><spring:message code="ess.infoApply.title.leaveContent"/><!--休假内容--></td>
			    <td width="80%" class="td_type" colspan="3"><textarea name="LEAVE_REASON" cols="80" rows="2"></textarea></td>
			</tr>		
	</table>	
    <div id="leaveApplyAffirmView" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
  </form>	
</div>
</div>
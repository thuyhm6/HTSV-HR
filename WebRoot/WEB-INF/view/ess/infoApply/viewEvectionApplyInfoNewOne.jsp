<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
<!--
function validateEvectionApplyCallbackEss0230(form,callback) {	
	var $form = $(form);	
	if (!$form.valid()) {
		return false;
	}
	
    var fromTime  = document.getElementById("EVE_FROM_TIME_ESS0230").value;
    var toTime    = document.getElementById("EVE_TO_TIME_ESS0230").value; 
    var applyTypeCode =$("#EVE_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val();//document.getElementById("EVE_APPLY_TYPE_CODE").value;

    if(applyTypeCode==""){                   
       alertMsg.error('<spring:message code="alert.message.ess.viewApply.evApplyCodeIsMust"/>');
        return false;
	}
    if(applyTypeCode=='124851'){
    	var fromTimeHour =$("#FROM_TIME_HOUR_ESS0230",navTab.getCurrentPanel()).val();
    	var fromTimeMinute =$("#FROM_TIME_MINUTE_ESS0230",navTab.getCurrentPanel()).val();
    	var toTimeHour =$("#TO_TIME_HOUR_ESS0230",navTab.getCurrentPanel()).val();
    	var toTimeMinute =$("#TO_TIME_MINUTE_ESS0230",navTab.getCurrentPanel()).val();

		if(fromTimeHour==""||fromTimeMinute==""||toTimeHour==""||toTimeMinute==""){
       		alertMsg.error('<spring:message code="ar.alert.message.viewCompanyCalendar.datenull"/>');
       		return false;
		}
    	fromTime=fromTime+" "+fromTimeHour+":"+fromTimeMinute+":00";
    	toTime=toTime+" "+toTimeHour+":"+toTimeMinute+":00";
    	
    }else{
    	fromTime=fromTime+" 09:00:00";
    	toTime=toTime+" 18:00:00";
    }
    
    $("#EVE_FROM_TIME_ESS0230A").val(fromTime);
    $("#EVE_TO_TIME_ESS0230A").val(toTime);
     fromTime=new Date(fromTime.replace(/-/g,"/"));
     toTime=new Date(toTime.replace(/-/g,"/"));
     
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

function passEvectionApplyValueEss0230()
{    
    var fromTime  = document.getElementById("EVE_FROM_TIME_ESS0230").value;
    var toTime    = document.getElementById("EVE_TO_TIME_ESS0230").value;
    var applyTypeCode =$("#EVE_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val(); //document.getElementById("EVE_APPLY_TYPE_CODE").value;
    var applyTypeNo = document.getElementById("EVE_APPLY_TYPE_NO_ESS0230").value;
    
    
    
    
    if(fromTime==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.evectionApplyTypeIsMust"/>');
       return false;
	}
    if(toTime==""){
	   alertMsg.error('<spring:message code="alert.message.ess.infoApply.evectionApplyTypeIsMust"/>');
	   return false;
	}
	if(applyTypeCode==""){
       alertMsg.error('<spring:message code="alert.message.ess.viewApply.evApplyCodeIsMust"/>');
       return false;
	}
	
	if(applyTypeCode=='124851'){
    	var fromTimeHour =$("#FROM_TIME_HOUR_ESS0230",navTab.getCurrentPanel()).val();
    	var fromTimeMinute =$("#FROM_TIME_MINUTE_ESS0230",navTab.getCurrentPanel()).val();
    	var toTimeHour =$("#TO_TIME_HOUR_ESS0230",navTab.getCurrentPanel()).val();
    	var toTimeMinute =$("#TO_TIME_MINUTE_ESS0230",navTab.getCurrentPanel()).val();
    	
    	if(fromTimeHour==""||fromTimeMinute==""||toTimeHour==""||toTimeMinute==""){
       		alertMsg.error('<spring:message code="ar.alert.message.viewCompanyCalendar.datenull"/>');
       		return false;
		}
    	//alert(fromTime+" "+fromTimeHour+":"+fromTimeMinute+":00");
    	fromTime=fromTime+" "+fromTimeHour+":"+fromTimeMinute+":00";
    	toTime=toTime+" "+toTimeHour+":"+toTimeMinute+":00";
    	
    }else{
    	fromTime=fromTime+" 09:00:00";
    	toTime=toTime+" 18:00:00";
    }
    
     fromTime=new Date(fromTime.replace(/-/g,"/"));
     toTime=new Date(toTime.replace(/-/g,"/"));
    
    if(fromTime>toTime){
	   alertMsg.error('<spring:message code="alert.message.ess.viewApply.evStartTimeNotLaterThanLeaveEndTime"/>');
	   return false;
	}
    document.getElementById("viewEvectionApplyInfoHrefEss0230").href = 
    document.getElementById("viewEvectionApplyInfoHrefEss0230").href+
                            "&&APPLY_TYPE_CODE="+applyTypeCode+"&&FROM_TIME="+fromTime+"&&TO_TIME="+toTime+
                            "&&APPLY_TYPE_NO="+applyTypeNo;
    document.getElementById("viewEvectionApplyInfoHrefEss0230").click();		
}

function waichuchang(code){
	if(code=='124851'){
		$("#waichu_ESS0230").show();
	}else{
		$("#waichu_ESS0230").hide();
		$("#FROM_TIME_HOUR_ESS0230").val("");
		$("#FROM_TIME_MINUTE_ESS0230").val("");
		$("#TO_TIME_HOUR_ESS0230").val("");
		$("#TO_TIME_MINUTE_ESS0230").val("");
	}
}
//-->
</script>

<div class="panel">
	<h1><spring:message code="ess.infoApply.title.evectionApply"/><!--出差申请--></h1>
	<div>
	<form method="post" action="/ess/infoApply/addEvectionApply" class="pageForm required-validate" onsubmit="return validateEvectionApplyCallbackEss0230(this,navTabAjaxDone);">
		
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
				    <input id="EVE_APPLY_TYPE_NO_ESS0230" name="EVE_APPLY_TYPE_NO" type="hidden" size="30"
						   value="18" />		   
				</td>
				<td width="20%" class="td_title"><spring:message code="public.title.positionName"/><!--职岗位--></td>
				<td width="40%" class="td_type">
				    ${personInfo.POSITION_NAME}
				</td>
			</tr>
			<tr>
				<td width="20%" class="td_title"><spring:message code="ess.infoApply.title.essApplyTime"/><!--申请日期--></td>
				<td width="40%" class="td_type" >
                    ${CREATE_DATE}
				</td>
				<td width="20%" class="td_title"><spring:message code="ess.infoApply.title.affirmor"/><!--决裁者--></td>
				<td width="40%" class="td_type">
					<a rel="evectionApplyAffirmView" onclick="passEvectionApplyValueEss0230();">
					   <span style="cursor:pointer;"><spring:message code="ess.infoApply.title.viewDetail"/><!--查看详细--></span>
					</a>
				    <a rel="evectionApplyAffirmView" href="/ess/infoApply/viewAffirmorByPersonIdAndAppCode?APPLY_TYPE_NO=18&&PERSON_ID=${personInfo.PERSON_ID}"  
				       target="dialog" mask="true" width="300" height="300" id="viewEvectionApplyInfoHrefEss0230" ></a>
				</td>
			</tr>
			<tr>
				<td width="20%" class="td_title"><spring:message code="ess.infoApply.title.evectionApplyType"/><!--出差类型--></td>
				<td width="40%" class="td_type"  colspan="3">
				    <ait:SelectSyCodeByCpnyID name="EVE_APPLY_TYPE_CODE" parentNo="18" cnpyID="${defaultCpny}" selected="${EVE_APPLY_TYPE_CODE}" limit="all" onChangeName="waichuchang(this.value);"/>
				</td>
			<%--<td width="20%" class="td_title"></td>
			<td width="40%" class="td_type"><input type="checkbox" id="continueApply_ess0230" name="continueApply_ess0230" value="1"/>&nbsp;&nbsp;<font color="red">连续申请</font></td>
			</tr> --%>
			<tr>
				<td width="20%" class="td_title"><spring:message code="ess.infoApply.title.applyTime1"/><!--出差开始时间--></td>
				<td width="20%" class="td_type">
					<input type="hidden" id="EVE_FROM_TIME_ESS0230A" name="EVE_FROM_TIME" >
				    <input type="text" id="EVE_FROM_TIME_ESS0230"  class="date required" format="yyyy-MM-dd" readonly="true" value="${LEAVE_FROM_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				</td>
				<td width="20%" class="td_title"><spring:message code="ess.infoApply.title.evectionEndTime1"/><!--出差结束时间--></td>
				<td width="40%" class="td_type">
					<input type="hidden" id="EVE_TO_TIME_ESS0230A" name="EVE_TO_TIME" >
				    <input type="text" id="EVE_TO_TIME_ESS0230"  class="date required" format="yyyy-MM-dd" readonly="true" value="${LEAVE_TO_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>				    
				</td>    
			</tr>
			
			<tr id="waichu_ESS0230" style="display:none">
				<td width="20" class="td_title"><spring:message code="ess.infoApply.title.startTime"/><!-- 开始时间 --></td>
				
				<td width="40" class="td_type">
				        <input type="text" id="FROM_TIME_HOUR_ESS0230" name="FROM_TIME_HOUR"  
				               value="${FROM_TIME_HOUR}" min="0" max="23" size="4">:
				        <input type="text" id="FROM_TIME_MINUTE_ESS0230" name="FROM_TIME_MINUTE"  
				               value="${FROM_TIME_MINUTE}" min="0" max="59" size="4">	
				    </td>
				<td width="20" class="td_title"> <spring:message code="ess.infoApply.title.endTime"/><!--结束时间--></td>
				<td width="40" class="td_type">
					    <input type="text" id="TO_TIME_HOUR_ESS0230"   name="TO_TIME_HOUR"   
					           value="${TO_TIME_HOUR}"  min="0" max="23" size="4">:
					    <input type="text" id="TO_TIME_MINUTE_ESS0230" name="TO_TIME_MINUTE" 
					           value="${TO_TIME_MINUTE}" min="0" max="59" size="4">					
				  </td>
			</tr>
			<tr>
			    <td width="20%" class="td_title"><spring:message code="ess.infoApply.title.workContent"/><!--工作内容--></td>
			    <td width="80%" class="td_type" colspan="3"><textarea name="LEAVE_REASON" cols="80" rows="2"></textarea></td>
			</tr>		
	</table>	
    <div id="evectionApplyAffirmView" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
  </form>	
</div>
</div>
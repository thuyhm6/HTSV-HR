<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
<!--
function validateLeaveApplyCallbackEss0202(form,callback) {	
	var $form = $(form);	
	if (!$form.valid()) {
		return false;
	}
	
    var fromTime  = $("#LEAVE_FROM_TIME",navTab.getCurrentPanel()).val(); //document.getElementById("LEAVE_FROM_TIME").value;
    var toTime    =  $("#LEAVE_TO_TIME",navTab.getCurrentPanel()).val();//document.getElementById("LEAVE_TO_TIME").value; 
    var applyTypeCode =  $("#LEAVE_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val();//document.getElementById("LEAVE_APPLY_TYPE_CODE").value;
    
  
    

    if(applyTypeCode==""){
       alertMsg.error('<spring:message code="alert.message.ess.viewApply.leaveApplyApplyIsMust"/>');
       return false;
	}
	if(applyTypeCode!='123643'){//休假类型不是产假(男)是 配偶出生日期字段设置为null
    	 $("#SPOUSE_BIRTH",navTab.getCurrentPanel()).val("");
    }
    if(applyTypeCode=='123641'||applyTypeCode=='123642'){//休假类型不是产假(男)是 配偶出生日期字段设置为null
    	if(fromTime!=toTime){
    		alertMsg.error("半假必须是同一天");
    		return false;
    	}
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

function passLeaveApplyValueEss0202(){    
     var fromTime  = $("#LEAVE_FROM_TIME",navTab.getCurrentPanel()).val(); //document.getElementById("LEAVE_FROM_TIME").value;
    var toTime    =  $("#LEAVE_TO_TIME",navTab.getCurrentPanel()).val();//document.getElementById("LEAVE_TO_TIME").value; 
    var applyTypeCode =  $("#LEAVE_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val();//document.getElementById("LEAVE_APPLY_TYPE_CODE").value;
    
    if(fromTime==""){
       alertMsg.error('<spring:message code="alert.message.ess.viewApply.leaveStartTimeIsMust"/>');
       return false;
	}
    if(toTime==""){
	   alertMsg.error('<spring:message code="alert.message.ess.viewApply.leaveEndTimeIsMust"/>');
	   return false;
	}
	if(applyTypeCode==""){
       alertMsg.error('<spring:message code="alert.message.ess.viewApply.leaveApplyApplyIsMust"/>');
       return false;
	}
    if(fromTime>toTime){
	   alertMsg.error('<spring:message code="alert.message.ess.viewApply.leaveStartTimeNotLaterThanLeaveEndTime"/>');
	   return false;
	}
    document.getElementById("viewLeaveApplyInfoHrefEss0202").href = 
    document.getElementById("viewLeaveApplyInfoHrefEss0202").href+"&APPLY_TYPE_CODE="+applyTypeCode+"&FROM_TIME="+fromTime+"&TO_TIME="+toTime;
    document.getElementById("viewLeaveApplyInfoHrefEss0202").click();		
}

function xiujialeixing(id){//休假类型
	if(id=='26'||id=='123641'||id=='123642'){
		$("#nianjiamingxi_ess0202").show();
		$("#yuchanqi_ess0202").hide();
		$("#peio_ess0202").hide();
		$("#peio1_ess0202").hide();
		$("#proveFile_ess0202").hide();
	}else if(id=='27'||id=='123643'||id=='24'||id=='23619'){
		if(id=='27'||id=='123643'){
			$("#yuchanqi_ess0202").show();
			if(id=='123643'){
				$("#peio_ess0202").show();
				$("#peio1_ess0202").show();
				$("#peio2_ess0202").hide();
			}else{
				$("#peio_ess0202").hide();
				$("#peio1_ess0202").hide();
				$("#peio2_ess0202").show();
				}
			
		}else{
			$("#peio_ess0202").hide();
			$("#peio1_ess0202").hide();
			$("#peio2_ess0202").hide();
			$("#yuchanqi_ess0202").hide();
		}
		$("#nianjiamingxi_ess0202").hide();
		$("#proveFile_ess0202").show();
	}else{
		$("#nianjiamingxi_ess0202").hide();
		$("#yuchanqi_ess0202").hide();
		$("#peio_ess0202").hide();
		$("#peio1_ess0202").hide();
		$("#peio2_ess0202").hide();
		$("#proveFile_ess0202").hide();
	}
	$("#PROVE_FILE_NAME_ESS0202").val("");
	$("#PROVE_FILE_URL_ESS0202").val("");
}
//-->
</script>

<div class="panel">
	<h1><spring:message code="ess.infoApply.title.leaveApply"/><!--休假申请--></h1>
	<div>
	<form method="post" action="/ess/infoApply/addLeaveApply" class="pageForm required-validate" onsubmit="return validateLeaveApplyCallbackEss0202(this,navTabAjaxDone);">
		   <input id="defaultCpny" name="defaultCpny" type="hidden" size="30"
						   value="${defaultCpny}" />	
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
					<div class="buttonActive">
						<a href="/ess/infoApply/viewVacationStandard" target="dialog" mask="true" width="440" height="350" ><span><spring:message code="heran.ess.viewLikeLeaveApplyInfo.xiajiajizhun"/></span><!--休假基准--></a>
					</div>
				</li>
			</ul>
		</div>
		
	<table  class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" style="display:none" id="nianjiamingxi_ess0202">
	   <thead>
		   	<tr>
		   		<td width="25%" class="td_title"><spring:message code="liang.ess.infoApply.title.totalAnnualLeave"/><!-- 总年假 --></td>
		   		<td width="25%" class="td_title"><spring:message code="liang.ess.infoApply.title.useAnnualLeave"/><!-- 已使用年假 --></td>
		   		<td width="25%" class="td_title"><spring:message code="liang.ess.infoApply.title.instantUseAnnualLeave"/><!-- 当月使用年假 --></td>
		   		<td width="25%" class="td_title"><spring:message code="ess.infoApply.title.restAnnualLeave"/><!-- 剩余年假 --></td>
		   	</tr>
		   	
	   </thead>
	   <tbody>
	   	<tr>
	   		<td width="25%" class="td_type td_center">${restAnnualLeave[0].TOTALANNUALLEAVE}</td>
	   		<td width="25%" class="td_type td_center">${restAnnualLeave[0].USEANNUALLEAVE}</td>
	   		<td width="25%" class="td_type td_center">${restAnnualLeave[0].INSTANTUSEANNUALLEAVE}</td>
	   		<td width="25%" class="td_type td_center">${restAnnualLeave[0].RESTANNUALLEAVE}</td>
	   	</tr
	   ></tbody>
	</table>		
	<table  class="user_table" width="100%" layoutH="0" border="1" cellpadding="2" cellspacing="1">
			<tr>
				<td width="20%" class="td_title"><spring:message code="public.title.empIdAndName"/><!-- 工号/姓名 --></td>
				<td width="20%" class="td_type">
				    ${personInfo.EMPID} / ${personInfo.LOCAL_NAME}
                    <input id="PERSON_ID" name="PERSON_ID" type="hidden" size="30"
						   value="${personInfo.PERSON_ID}" />	
				    <input id="APPLY_TYPE_NO" name="APPLY_TYPE_NO" type="hidden" size="30"
						   value="21" />		   
				</td>
				<td width="20%" class="td_title"><spring:message code="hr.viewPersonalInfo.title.POSITION_NAME"/><!--职位-->/<spring:message code="hr.viewPersonalInfo.title.DUTY_NAME" /><!--职责-->
				</td>
				<td width="40%" class="td_type">
				    ${personInfo.POSITION_NO_NAME}/${personInfo.DUTY_NAME}
				</td>
			</tr>
			<tr>
				<td width="20%" class="td_title"><spring:message code="ess.infoApply.title.essApplyTime"/><!--申请日期--></td>
				<td width="40%" class="td_type" >
                    ${CREATE_DATE}
				</td>
				<td width="20%" class="td_title"><spring:message code="ess.trans.title.entryJobDate"/><!--入职日期--></td>
				<td width="40%" class="td_type">
                    ${personInfo.DATE_STARTED}
				</td>
				
			</tr>
			<tr>
				<td width="20%" class="td_title"><spring:message code="public.title.startDate"/><!-- 开始日期 --></td>
				<td width="20%" class="td_type">
				    <input type="text" id="LEAVE_FROM_TIME" name="LEAVE_FROM_TIME" class="date required" format="yyyy-MM-dd" readonly="true" value="${LEAVE_FROM_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				</td>
				<td width="20%" class="td_title"><spring:message code="public.title.endDate"/><!-- 结束日期 --></td>
				<td width="40%" class="td_type">
				    <input type="text" id="LEAVE_TO_TIME" name="LEAVE_TO_TIME" class="date required" format="yyyy-MM-dd" readonly="true" value="${LEAVE_TO_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>				    
				</td>    
			</tr>
			<tr>
				<td width="20%" class="td_title"><spring:message code="ess.infoApply.title.leaveApplyType"/><!--休假类型--></td>
				<td width="20%" class="td_type">
				    <ait:SelectSyCodeByCpnyID name="LEAVE_APPLY_TYPE_CODE" parentNo="21" cnpyID="${defaultCpny}" selected="${LEAVE_APPLY_TYPE_CODE}" limit="all" onChangeName="xiujialeixing(this.value);"/>
				</td>
				<td width="20%" class="td_title"><spring:message code="ess.infoApply.title.affirmor"/><!--决裁者--></td>
				<td width="40%" class="td_type">
					<a rel="leaveApplyAffirmView" onclick="passLeaveApplyValueEss0202();">
					   <span style="cursor:pointer;"><spring:message code="ess.infoApply.title.viewDetail"/><!--查看详细--></span>
					</a>
				    <a rel="leaveApplyAffirmView" href="/ess/infoApply/viewAffirmorByPersonIdAndAppCode?APPLY_TYPE_NO=21&PERSON_ID=${personInfo.PERSON_ID}"  
				       target="dialog" mask="true" width="300" height="300" id="viewLeaveApplyInfoHrefEss0202" ></a>
				</td>
			</tr>
			<tr style="display:none" id="yuchanqi_ess0202">
				<td width="20%" class="td_title"><spring:message code="liang.ess.infoApply.title.expected_date"/><!-- 预产期 --></td>
				<td width="20%" class="td_type">
				    <input type="text" id="EXPECTED_DATE" name="EXPECTED_DATE" class="date" format="yyyy-MM-dd" readonly="true" value="${EXPECTED_DATE}"/>
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				</td>
				<td width="20%" class="td_title" id="peio_ess0202" tyle="display:none"><spring:message code="liang.ess.infoApply.title.spouse_birth"/><!--  配偶出生年月日--></td>
				<td width="40%" class="td_type" id="peio1_ess0202" tyle="display:none">
				<c:if test="${SPOUSE_BIRTH eq NULL  }">
				    <input type="text" id="SPOUSE_BIRTH" name="SPOUSE_BIRTH" class="date" format="yyyy-MM-dd" readonly="true" />
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>	
				 </c:if>			    
				<c:if test="${SPOUSE_BIRTH ne NULL  }">
				    <input type="text" id="SPOUSE_BIRTH" name="SPOUSE_BIRTH"  readonly="true" value="${SPOUSE_BIRTH}"/>
				 </c:if>			    
				</td>
				<td width="30%" class="td_type" colspan="2" id="peio2_ess0202">
				   
					</td>    
			</tr> 
			<tr id="proveFile_ess0202" style="display:none">
			    <td width="20%" class="td_title"><spring:message code="liang.ess.infoApply.title.prove_file"/><!-- 证明文件 --><br></td>
			    <td width="80%" class="td_type" colspan="3">
			    <input id="PROVE_FILE_NAME_ESS0202" name="PROVE_FILE_NAME" type="text"  readonly/><a href="/ess/infoApply/proveFiledialog?FILE_NAME=PROVE_FILE_NAME_ESS0202&FILE_URL=PROVE_FILE_URL_ESS0202" target="dialog" mask="true" width="300" height="200" ><input type="button" value="添加" /> </a>
			    <input id="PROVE_FILE_URL_ESS0202" name="PROVE_FILE_URL" type="hidden" />
			    </td>
			</tr>
			<tr>
			    <td width="20%" class="td_title"><spring:message code="ess.infoApply.title.leaveContent"/><!--休假内容--><br></td>
			    <td width="80%" class="td_type" colspan="3"><textarea name="LEAVE_REASON" cols="80" rows="2"></textarea></td>
			</tr>		
	</table>	
    <div id="leaveApplyAffirmView" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
  </form>	
</div>
</div>
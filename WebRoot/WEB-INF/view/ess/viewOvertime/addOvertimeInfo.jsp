<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
<!--
function validateOvertimeApplyCallback(form,callback) {	
	var $form = $(form);	
	if (!$form.valid()) {
		return false;
	}
	
    var fromDate  = document.getElementById("FROM_DATE").value;
    var toDate    = document.getElementById("TO_DATE").value;
    var fromTimeHour   = document.getElementById("FROM_TIME_HOUR").value;
    var fromTimeMinute = document.getElementById("FROM_TIME_MINUTE").value;
    var toTimeHour     = document.getElementById("TO_TIME_HOUR").value;
    var toTimeMinute   = document.getElementById("TO_TIME_MINUTE").value;        
    var applyTypeCode = document.getElementById("APPLY_TYPE_CODE").value;

    if(toDate==""){
       toDate = fromDate ;
    }else{
	    if(fromDate>toDate){
		   alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeNotLaterThanEndTime"/>');
		   return false;
		}   
	} 

    if(applyTypeCode==""){
	       alertMsg.error('<spring:message code="alert.message.ess.infoApply.overtimeApplyTypeIsMust"/>');
	       return false;
	}

  //如果是连续申请 则只判断时间；否则日期+时间 判断
	if(document.getElementById("continueApply").checked == true ){
		if((fromTimeHour-toTimeHour)>0){
		   alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeCanNotLaterThanEndTime"/>');
		   return false;
		}
	}else{
		var leavefromtime = fromDate + " " + fromTimeHour + ":" + fromTimeMinute + ":" + "00";
		var leavetotime = toDate + " " + toTimeHour + ":" + toTimeMinute + ":" + "00";
		if(comptime(leavefromtime,leavetotime)!=1){
			alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeCanNotLaterThanEndTime"/>');
			return false;
		}
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

function passOvertimeApplyValue()
{   
	var fromDate  = document.getElementById("FROM_DATE").value;
    var toDate    = document.getElementById("TO_DATE").value;
    var fromTimeHour   = document.getElementById("FROM_TIME_HOUR").value;
    var fromTimeMinute = document.getElementById("FROM_TIME_MINUTE").value;
    var toTimeHour     = document.getElementById("TO_TIME_HOUR").value;
    var toTimeMinute   = document.getElementById("TO_TIME_MINUTE").value;  
    var otDeductTime = document.getElementById("OT_DEDUCT_TIME").value;  
    var applyTypeCode = document.getElementById("APPLY_TYPE_CODE").value;
    var applyTypeNo = document.getElementById("APPLY_TYPE_NO").value;
    
    if(fromDate==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.startDateTimeIsMust"/>');
       return;
	}
    if(toDate==""){
       toDate = fromDate ; 
    }else{
		if(fromDate>toDate){
		   alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeNotLaterThanEndTime"/>');
		   return;
		}
	}
    if(fromTimeHour==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeIsMust"/>');
       return;
	}
    if(fromTimeMinute==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeIsMust"/>');
       return;
	}	
    if(toTimeHour==""){
	   alertMsg.error('<spring:message code="alert.message.ess.infoApply.endTimeIsMust"/>');
	   return;
	}
    if(toTimeMinute==""){
	   alertMsg.error('<spring:message code="alert.message.ess.infoApply.endTimeIsMust"/>');
	   return;
	}			
	if(applyTypeCode==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.overtimeApplyTypeIsMust"/>');
       return;
	}
	//如果是连续申请 则只判断时间；否则日期+时间 判断
	/*
	if(document.getElementById("continueApply").checked == true ){
		if(fromTimeHour>toTimeHour){
		   alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeCanNotLaterThanEndTime"/>');
		   return;
		}
	}else{
		var leavefromtime = fromDate + " " + fromTimeHour + ":" + fromTimeMinute + ":" + "00";
		var leavetotime = toDate + " " + toTimeHour + ":" + toTimeMinute + ":" + "00";
		if(comptime(leavefromtime,leavetotime)!=1){
			alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeCanNotLaterThanEndTime"/>');
			return false;
		}
	}
	*/
    
    document.getElementById("viewOvertimeApplyInfoHref").href = 
    document.getElementById("viewOvertimeApplyInfoHref").href+
                            "&&APPLY_TYPE_CODE="+applyTypeCode+"&&APPLY_TYPE_NO="+applyTypeNo+
                            "&&FROM_DATE="+fromDate+"&&TO_DATE="+toDate+"&&OT_DEDUCT_TIME="+otDeductTime+
                            "&&FROM_TIME_HOUR="+fromTimeHour+"&&FROM_TIME_MINUTE="+fromTimeMinute+
                            "&&TO_TIME_HOUR="+toTimeHour+"&&TO_TIME_MINUTE="+toTimeMinute;
    //document.getElementById("viewOvertimeApplyInfoHref").click();	
    var flag = document.getElementById("flag_detail");
    var div = document.getElementById("jczDiv");
    var span = document.getElementById("viewDetial");
    if(flag.value=="true"){
    	div.style.display="";	
    	flag.value="false";
    	span.innerHTML="<spring:message code='ess.infoApply.title.closeDetail'/>"; //关闭
    	
    }else{
    	div.style.display="none";	
    	flag.value="true";
    	span.innerHTML="<spring:message code='ess.infoApply.title.viewDetail'/>";//查看详细
    }
    
}

//比较时间 格式 yyyy-mm-dd hh:mi:ss
function comptime(beginTime,endTime){
	var beginTimes=beginTime.substring(0,10).split('-');
	var endTimes=endTime.substring(0,10).split('-');
	
	beginTime=beginTimes[1]+'-'+beginTimes[2]+'-'+beginTimes[0]+' '+beginTime.substring(10,19);
	endTime=endTimes[1]+'-'+endTimes[2]+'-'+endTimes[0]+' '+endTime.substring(10,19);

	// alert(beginTime+endTime+beginTime);
	
	//alert(Date.parse(endTime)+" "+Date.parse(beginTime));

	var a =(Date.parse(endTime)-Date.parse(beginTime))/3600/1000;

	if(a<0){
		return -1;
	}else if (a>0){
		return 1;
	}else if (a==0){
		return 0;
	}else{
		return 'exception'
	}
}
function getDate2_ess0302(personid){
	var startDate= $("#FROM_DATE").val();
	if(startDate==""){
		return false;
	}
	 $.ajax({
               type: 'POST',
               url:"/ess/viewOvertime/getDateByPersonIdAndCpny",
               data:'personid=' + personid+"&time="+startDate,
               dataType:"json",
               cache: false,
               success: function(data) {
		 				var code=data.CODE;
		 			 	var codeValue="";
		 			 	var code1="";
		 			 		if(code=="1440"){
		 			 		//平日
		 			 		code1="32";
		 			 		codeValue="平日加班";
		 			 	}else if(code=="1441"){
		 			 		//周末
		 			 		code1="33";
		 			 		codeValue="周末加班";
		 			 	}else if(code=="1442"){
		 			 		//节假日
		 			 		code1="34";
		 			 		codeValue="节假日加班";
		 			 	}
		 			 	//结束日期=开始日期
		 			 	$("#TO_DATE").val(startDate);
		 			 	//开始小时
		 			 	$("#FROM_TIME_HOUR").val(data.SHIFT_START_TIME.substring(11,13));
		 			 	//开始分钟
		 			 	$("#FROM_TIME_MINUTE").val(data.SHIFT_START_TIME.substring(14));
		 			 	//结束时间
		 			 	$("#TO_TIME_HOUR").val(data.SHIFT_END_TIME.substring(11,13));
		 			 	//结束分钟
		 			 	$("#TO_TIME_MINUTE").val(data.SHIFT_END_TIME.substring(14));
		 			 	 $("#APPLY_TYPE_CODE option[value='"+code1+"']").attr("selected", true);    
		 			   //data.SHIFT_START_TIME.substring(11)+" "+data.SHIFT_END_TIME.substring(11); 	 			 	
		 			 	//$("#select_id option[text='34']").attr("selected", true);    
                       //alertMsg.info(data.message);
                       //$(row).parent().parent().remove();
                       //document.getElementById("OrderType").onchange();
                        },
               error: DWZ.ajaxError
   		});
}



		function doSave(){
			var form = document.getElementById("pageForm");
			var hid = document.getElementById("saveType");
			hid.value="save";
			form.submit();
		}
		
		function doChangeInfo(){
			lbl = document.getElementById("lbl");
			lbls = document.getElementsByTagName("label");
			for(i=0;i<8;i++){
				lbls[i].style.display="none";
			}
			var applyTypeCode = document.getElementById("APPLY_TYPE_CODE").value;
			//alert(applyTypeCode);
			if(applyTypeCode==32){
				document.getElementById("147780").style.display="";
			}else if(applyTypeCode==33){
				document.getElementById("147781").style.display="";
			}else if(applyTypeCode==34){
				document.getElementById("147782").style.display="";
			}else if(applyTypeCode==17378){
				document.getElementById("147786").style.display="";
			}else if(applyTypeCode==141470){
				document.getElementById("147787").style.display="";
			}else if(applyTypeCode==17379){
				document.getElementById("147788").style.display="";
			}else if(applyTypeCode==141471){
				document.getElementById("147789").style.display="";
			}else if(applyTypeCode==141472){
				document.getElementById("147790").style.display="";
			}
		
			
			
		}
//-->
</script>

<div class="panel">
	<div>
	<form id="pageForm" method="post" action="/ess/viewOvertime/addOvertimeApply" class="pageForm required-validate" onsubmit="return validateOvertimeApplyCallback(this,navTabAjaxDone);">
		<input id="saveType" type="hidden" value="submit">
	   <div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							
							<button onclick="javascript:doSave()">
								<spring:message code="ess.viewOvertimeInfo.baocun"/><!--保存-->
							</button>
						</div>
					</div>
				</li>
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
	<table  class="user_table" width="100%" layoutH="0" border="1" cellpadding="2" cellspacing="1">
			<tr>
				<td width="20%" class="td_title"><spring:message code="public.title.empIdAndName"/><!-- 工号/姓名 --></td>
				<td width="30%" class="td_type" colspan="3">
				    ${personInfo.EMPID} / ${personInfo.LOCAL_NAME}
                    <input id="PERSON_ID" name="PERSON_ID" type="hidden" size="30"
						   value="${personInfo.PERSON_ID}" />	
				    <input id="APPLY_TYPE_NO" name="APPLY_TYPE_NO" type="hidden" size="30"
						   value="31" />		   
				</td>
				<td width="20%" class="td_title"><spring:message code="public.title.positionName"/><!--职岗位--></td>
				<td width="30%" class="td_type">
				    ${personInfo.POSITION_NAME}
				</td>
			</tr>

			<tr onclick="getDate2_ess0302(${personInfo.PERSON_ID})">
				<td width="20%" class="td_title"><spring:message code="public.title.startDate"/><!-- 开始日期 --></td>
				<td width="30%" class="td_type" colspan="3">
				    <input type="text" id="FROM_DATE" name="FROM_DATE" class="date required" format="yyyy-MM-dd" readonly="true" value="${FROM_DATE}" />
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				</td>
				<td width="20%" class="td_title"><spring:message code="public.title.endDate"/><!-- 结束日期 --></td>
				<td width="30%" class="td_type">
				    <input type="text" id="TO_DATE" name="TO_DATE" class="date" format="yyyy-MM-dd" readonly="true" value="${TO_DATE}"/>
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>				    
				</td>    
			</tr>
			<tr>
				<td width="20%" class="td_title"><spring:message code="ess.infoApply.title.startTime"/><!--开始时间--></td>
				<td width="30%" class="td_type" colspan="3">
				    <input type="text" id="FROM_TIME_HOUR" name="FROM_TIME_HOUR" class="required" value="${FROM_TIME_HOUR}" min="0" max="23" size="7">:
				    <input type="text" id="FROM_TIME_MINUTE" name="FROM_TIME_MINUTE" class="required" value="${FROM_TIME_MINUTE}" min="0" max="59" size="7">	
				</td>
				<td width="20%" class="td_title"><spring:message code="ess.infoApply.title.endTime"/><!--结束时间--></td>
				<td width="30%" class="td_type">
				    <input type="text" id="TO_TIME_HOUR"   name="TO_TIME_HOUR"   class="required" value="${TO_TIME_HOUR}"   min="0" max="23" size="7">:
				    <input type="text" id="TO_TIME_MINUTE" name="TO_TIME_MINUTE" class="required" value="${TO_TIME_MINUTE}" min="0" max="59" size="7">
				</td>    
			</tr>			
			<tr>
				<td width="20%" class="td_title"><spring:message code="ess.viewApply.title.overtimeApplyType"/><!--加班类型--></td>
				<td width="10%" class="td_type" colspan="3">
				    <ait:SelectSyCodeByCpnyID onChangeName="doChangeInfo();" name="APPLY_TYPE_CODE" parentNo="31" cnpyID="${defaultCpny}" selected="${APPLY_TYPE_CODE}" limit="all"/>
				</td>
                
				<td width="10%" class="td_title" style="display:none"><!-- 扣除时间 -->
					<spring:message code="ar.viewshift.title.kouchushijian"/>
				</td>
				<td width="10%" class="td_type" style="display:none">
				    <!--<ait:SelectSyCodeByCpnyID name="OT_DEDUCT_TIME" parentNo="123589" cnpyID="${defaultCpny}" 
				    	selected="${OT_DEDUCT_TIME}" limit="all"/>-->
				    <select name="OT_DEDUCT_TIME" id="OT_DEDUCT_TIME">
						<option value=""><!--请选择-->
                   			<spring:message code="sys.affirm.title.choose"/>
                   		</option>
						<c:forEach items="${otDeductTimeList}" var="deductList">
							<option value="${deductList.DEDUCT_TIME}" <c:if test="${deductList.DEDUCT_TIME eq OT_DEDUCT_TIME}">selected</c:if>>
								${deductList.DEDUCT_TIME}
							</option>
						</c:forEach>
					</select>	   
				</td>
                
				<td width="20%" class="td_title"><spring:message code="ess.infoApply.title.affirmor"/><!--决裁者--></td>
				<td width="30%" class="td_type">
					<a rel="overtimeApplyAffirmView" onclick="passOvertimeApplyValue();" id="btn_detail">
					   <span id="viewDetial" style="cursor:pointer;"><spring:message code="ess.infoApply.title.viewDetail"/><!--查看详细--></span>
					</a>
				    <a rel="overtimeApplyAffirmView" href="/ess/viewOvertime/viewAffirmorByPersonIdAndAppCode?APPLY_TYPE_NO=31&&PERSON_ID=${personInfo.PERSON_ID}"  
				       target="dialog" mask="true" width="300" height="300" id="viewOvertimeApplyInfoHref" ></a>
				</td>
			</tr>
			<tr id="jczDiv" style="display:none" >
			<td colspan="6">
			
				 <table class="user_table" width="100%" layoutH="0" border="1" cellpadding="2" cellspacing="1">	
					<thead>
						<tr height="20px">
							<th width="30%"><spring:message code="public.title.empId"/><!-- 工号 --></th>
							<th width="30%"><spring:message code="ess.infoApply.title.dutyName"/><!--职责--></th>
							<th width="40%"><spring:message code="public.title.name"/><!-- 姓名 --></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${affirmorList}" var="affirmor" varStatus="i">			
							<tr height="20px">
								<td width="30%">${affirmor.EMPID}</td>
								<td width="30%">${affirmor.DUTY_NAME}</td>
								<td width="40%">${affirmor.LOCAL_NAME}</td>
							</tr>			
						</c:forEach>					
					</tbody>		
				</table>
			
			</td>
			
			
			<input type="hidden" id="flag_detail" value="true">
			
			
			
			
			<%--
			    <td width="20%" class="td_title"><spring:message code="ess.infoApply.title.forcedTypeChoice"/><!--强制类型选择-->
			    </td>
			    <td width="80%" class="td_type" colspan="3">
					<select name="ifForcedTypeChoice">
						<option value="0" selected="true"><spring:message code="sys.affirm.title.no"/><!--否--></option>
						<option value="1"><spring:message code="sys.affirm.title.yes"/><!--是--></option>
					</select>	
			    </td>
			    
			    <td width="20%" class="td_title">连续申请</td>
				<td width="80%" class="td_type" colspan="5">
					<input type="checkbox" id="continueApply" name="continueApply" value="1"/>
				</td>
			    --%>
			</tr>	
			<tr>
			    <td width="20%" class="td_title"><spring:message code="ess.infoApply.title.workContent"/><!--工作内容--></td>
			    <td width="80%" class="td_type" colspan="5"><textarea name="APPLY_REMARK" cols="80" rows="2"></textarea></td>
			</tr>	
			
			<tr>
			    <td width="20%" class="td_title"><spring:message code="ess.viewOvertimeInfo.renshizhengce"/><!--人事政策--></td>
			    <td width="80%" class="td_type" colspan="5">
			    
			    
			    
			    <c:forEach items="${selectcode}" var="code" >
							
							<label id="${code.CODE_NO}" style="display:none">${code.DEDUCT_TIME }</label>
							
				</c:forEach>
			    
			    </td>
			</tr>	
	</table>	
    <div id="overtimeApplyAffirmView" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
  </form>	
</div>
</div>
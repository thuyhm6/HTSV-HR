<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
<!--
function validateBatchOvertimeApplyCallback(form,callback) {	
	var $form = $("#applyOvertimeBatchForm");
	if (!$form.valid()) {
		return false;
	}
	
	var checked = false ;
	$form.find(":checkbox[id='c1']").each(function(index, checkBoxObj){	    
	if(checkBoxObj.checked){
	   checked = true ;      
	  }	    
	  });
	  
	if(!checked){
	 	alertMsg.error('<spring:message code="alert.message.ess.infoApply.choosePersonFirst"/>');
		return false;
	}
	$form.find(":checkbox[id='c1']").each(function(index, checkBoxObj){
	    if(checkBoxObj.checked){
	      checked = true ;
	      var personId = $(checkBoxObj).val() ;	
		  if($form.find("[name='"+personId+"_FROM_DATE']").val()==''){
				alertMsg.error('<spring:message code="alert.message.ess.infoApply.startDateTimeIsMust"/>');
				$form.find("[name='"+personId+"_FROM_DATE']").focus();
				checked=false;
		   }  
		  if($form.find("[name='"+personId+"_FROM_TIME_HOUR']").val()==''){
				alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeIsMust"/>');
				$form.find("[name='"+personId+"_FROM_TIME_HOUR']").focus();
				checked=false;
		   }
          if($form.find("[name='"+personId+"_TO_TIME_HOUR']").val()==''){
				alertMsg.error('<spring:message code="alert.message.ess.infoApply.endTimeIsMust"/>');
				$form.find("[name='"+personId+"_TO_TIME_HOUR']").focus();
				checked=false;
		   }
		  if($form.find("[name='"+personId+"_FROM_TIME_MINUTE']").val()==''){
				alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeIsMust"/>');
				$form.find("[name='"+personId+"_FROM_TIME_MINUTE']").focus();
				checked=false;
		   }
          if($form.find("[name='"+personId+"_TO_TIME_MINUTE']").val()==''){
				alertMsg.error('<spring:message code="alert.message.ess.infoApply.endTimeIsMust"/>');
				$form.find("[name='"+personId+"_TO_TIME_MINUTE']").focus();
				checked=false;
		   }		   
          if($form.find("select[name='"+personId+"_OT_APPLY_TYPE_CODE']").val()==''){
				alertMsg.error('<spring:message code="alert.message.ess.infoApply.overtimeApplyTypeIsMust"/>');
				$form.find("select[name='"+personId+"_OT_APPLY_TYPE_CODE']").focus();
				checked=false;
			}

		  if($form.find("[name='"+personId+"_TO_DATE']").val()!=''){	 
			  if($form.find("[name='"+personId+"_FROM_DATE']").val()>$form.find("[name='"+personId+"_TO_DATE']").val())
				{
				   alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeNotLaterThanEndTime"/>');
				   $form.find("[name='"+personId+"_FROM_DATE']").focus();
				  checked=false;
				}
		  }	 

		//如果是连续申请 则只判断时间；否则日期+时间 判断
	      	if(document.getElementById("continueApply").checked == true ){
	      		if($form.find("[name='"+personId+"_FROM_TIME_HOUR']").val()>$form.find("[name='"+personId+"_TO_TIME_HOUR']").val())
				{
				   alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeCanNotLaterThanEndTime"/>');
				   $form.find("[name='"+personId+"_FROM_TIME_HOUR']").focus();
				  checked=false;
				}
	      	}else{
		      	var fromDate = $form.find("[name='"+personId+"_FROM_DATE']").val();
		      	var fromTimeHour = $form.find("[name='"+personId+"_FROM_TIME_HOUR']").val();
		      	var fromTimeMinute = $form.find("[name='"+personId+"_FROM_TIME_MINUTE']").val();

		      	var toDate = $form.find("[name='"+personId+"_TO_DATE']").val();
		      	var toTimeHour = $form.find("[name='"+personId+"_TO_TIME_HOUR']").val();
		      	var toTimeMinute = $form.find("[name='"+personId+"_TO_TIME_MINUTE']").val();
		      	//如果结束日期为空，默认为当天日期
		      	if(toDate == ''){
		      		toDate = fromDate;
			    }
		      	
	      		var leavefromtime = fromDate + " " + fromTimeHour + ":" + fromTimeMinute + ":" + "00";
	      		var leavetotime = toDate + " " + toTimeHour + ":" + toTimeMinute + ":" + "00";
	      		if(comptime(leavefromtime,leavetotime)!=1){
	      			alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeCanNotLaterThanEndTime"/>');
	      			checked=false;
	      		}
	      	}
	    }
	  });

	if(checked){		
		if (confirm ('<spring:message code="alert.message.ess.infoApply.areYouSureToApply"/>')){		
			$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: function(data){ //请求成功后处理函数。
				if(data.statusCode=="200"){
					navTabSearch("searchOvertimeApplyBatchForm");
					alertMsg.correct(data.message);
				}else{
					if(data.result=="2"){
						alertMsg.info(data.message);
					}else{
						alertMsg.error(data.message);
					}
				}   
	   	 	}  ,
			error: DWZ.ajaxError
		});			
	    return false;
		}
	}
	return false ;
}

function passOvertimeApplyValue(personId)
{   
    var fromDate      = document.getElementById(personId+"_FROM_DATE").value;
    var toDate        = document.getElementById(personId+"_TO_DATE").value;

    var fromTimeHour   = document.getElementById(personId+"_FROM_TIME_HOUR").value;
    var fromTimeMinute = document.getElementById(personId+"_FROM_TIME_MINUTE").value;
    var toTimeHour     = document.getElementById(personId+"_TO_TIME_HOUR").value;
    var toTimeMinute   = document.getElementById(personId+"_TO_TIME_MINUTE").value; 
    
    var otDeductTime = document.getElementById(personId+"_OT_DEDUCT_TIME").value;
    var applyTypeCode = document.getElementById(personId+"_OT_APPLY_TYPE_CODE").value;
    var applyTypeNo   = document.getElementById("APPLY_TYPE_NO").value;
    
    if(fromDate==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.startDateTimeIsMust"/>');
       return false;
	}
    if(toDate==""){
	   toDate = fromDate ;
	}else{
	    if(fromDate>toDate){
		   alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeNotLaterThanEndTime"/>');
		   return false;
		}	
	}
	if(fromTimeHour==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeIsMust"/>');
       return false;
	}
    if(toTimeHour==""){
	   alertMsg.error('<spring:message code="alert.message.ess.infoApply.endTimeIsMust"/>');
	   return false;
	}
	if(fromTimeMinute==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeIsMust"/>');
       return false;
	}
    if(toTimeMinute==""){
	   alertMsg.error('<spring:message code="alert.message.ess.infoApply.endTimeIsMust"/>');
	   return false;
	}	

  //如果是连续申请 则只判断时间；否则日期+时间 判断
	if(document.getElementById("continueApply").checked == true ){
		if(fromTimeHour>toTimeHour){
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
	
	if(applyTypeCode==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.overtimeApplyTypeIsMust"/>');
       return false;
	}

    document.getElementById(personId+"_viewOvertimeApplyBatchInfoHref").href = 
    document.getElementById(personId+"_viewOvertimeApplyBatchInfoHref").href+
                            "&&APPLY_TYPE_CODE="+applyTypeCode+"&&APPLY_TYPE_NO="+applyTypeNo+"&&OT_DEDUCT_TIME="+otDeductTime+
                            "&&FROM_DATE="+fromDate+"&&TO_DATE="+toDate+
                            "&&FROM_TIME_HOUR="+fromTimeHour+"&&TO_TIME_HOUR="+toTimeHour+
                            "&&FROM_TIME_MINUTE="+fromTimeMinute+"&&TO_TIME_MINUTE="+toTimeMinute;
    document.getElementById(personId+"_viewOvertimeApplyBatchInfoHref").click();			
}

function fillItemOt(){
  var otFromDate = document.getElementById("otFromDate").value; 
  var otToDate = document.getElementById("otToDate").value; 
  var otFromTimeHour = document.getElementById("otFromTimeHour").value; 
  var otToTimeHour = document.getElementById("otToTimeHour").value;
  var otFromTimeMinute = document.getElementById("otFromTimeMinute").value; 
  var otToTimeMinute = document.getElementById("otToTimeMinute").value;    
  var otApplyReason = document.getElementById("otApplyRemark").value; 
  var otApplyTypeCode = document.getElementById("otApplyTypeCode").value;
  var otDeductTime = document.getElementById("otDeductTime").value; 
  
  var $form = $("#applyOvertimeBatchForm");
  $form.find(":checkbox[id='c1']").each(function(index, checkBoxObj){
  if(checkBoxObj.checked){
      var personId = $(checkBoxObj).val() ;
         $form.find("[name='"+personId+"_FROM_DATE']").attr("value",otFromDate);
         $form.find("[name='"+personId+"_TO_DATE']").attr("value",otToDate);
         $form.find("[name='"+personId+"_FROM_TIME_HOUR']").attr("value",otFromTimeHour);
         $form.find("[name='"+personId+"_TO_TIME_HOUR']").attr("value",otToTimeHour);
         $form.find("[name='"+personId+"_FROM_TIME_MINUTE']").attr("value",otFromTimeMinute);
         $form.find("[name='"+personId+"_TO_TIME_MINUTE']").attr("value",otToTimeMinute);
         $form.find("select[name='"+personId+"_OT_DEDUCT_TIME']").attr("value",otDeductTime);         
         $form.find("select[name='"+personId+"_OT_APPLY_TYPE_CODE']").attr("value",otApplyTypeCode);
         $form.find("[name='"+personId+"_APPLY_REMARK']").attr("value",otApplyReason);
  }
  });
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
//-->
</script>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewOtBatchApplyPersonList" method="post" 
	      rel="pagerForm" id="searchOvertimeApplyBatchForm" name="searchOvertimeApplyBatchForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>			
				<td><!-- 部门 -->
					 <spring:message code="public.title.deptName"/>:
				</td>	
				<td>
					<ait:deptTree name="seach_DEPT_NO" limit="ar" selected="${DEPT_NO }"/>
				</td>
                <td><!--关键字-->
                    <spring:message code="ess.infoApply.title.kewWord"/>:
				</td>
				<td>
					<input type="text" name="seach_KEY" value="${KEY}" />
				</td>
                <td><!-- 开始日期 -->
                    <spring:message code="public.title.startDate"/>:
                </td>			
			    <td>
			        <input type="text" name="seach_FROM_TIME" class="date" format="yyyy-MM-dd" readonly="true" value="${FROM_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td><!-- 结束日期 -->
					<spring:message code="public.title.endDate"/>:
                </td>                			     
				<td>
				    <input type="text" name="seach_TO_TIME" class="date" format="yyyy-MM-dd" readonly="true" value="${TO_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"></a>
				</td> 
				<td><!-- 动态组 -->
					<spring:message code="ar.addempshift.title.dynamicgroup"/>:
                </td>                			     
				<td>
				    <select name="seach_GROUP_NO" id="seach_GROUP_NO">
						<option value=""><!-- 全部 -->
							<spring:message code="ar.viewarcardrecord.title.quanbu"/>
						</option>
						<c:forEach items="${dynamicGroupList}" var="groupList">
							<option value="${groupList.GROUP_NO}" <c:if test="${groupList.GROUP_NO eq GROUP_NO}">selected</c:if>>${groupList.GROUP_NAME}</option>
						</c:forEach>
					</select>
				</td> 									
			</tr>
			</table>
	    </div>
		<div class="formBar">
			<tr>
			<ul>
				<li>
					<div class="subBar">
                            <div class="buttonActive"><div class="buttonContent"><button type="submit">
                                 <spring:message code="public.title.search"/><!-- 检索 -->
                            </button></div></div> 					
						    <div class="buttonActive">
						         <a class="update" onclick="return validateBatchOvertimeApplyCallback('applyOvertimeBatchForm',DWZ.ajaxDone);" href="#" ><span>
						         <spring:message code="ess.infoApply.title.apply"/><!--申请--></span></a>
						         <a class="update" onclick="fillItemOt();" href="#" >
						         <span><spring:message code="ess.infoApply.title.fillItem"/><!--填充--></span></a>
                            </div>
				    </div>	
				</li>
		</tr>
	</div>
	</form>
</div>	

<div class="pageContent" style="padding:5px;">
     <form style="margin:0px;padding:0px;" name="applyOvertimeBatchForm" id="applyOvertimeBatchForm" method="post" action="/ess/infoApply/addBatchOvertimeApply" 
           class="pageForm required-validate" onsubmit="return validateBatchOvertimeApplyCallback(this,navTabAjaxDone);">  
	<table class="tablea" width="120%" layoutH="110">
		<thead>
			<tr>
			    <th width="40">
			    	<input type="checkbox" class="checkboxCtrl" group="c1" />
			    </th>
				<th width="80"><!--工号-->
					<spring:message code="public.title.empId"/>
				</th>
				<th width="80"><!--姓名-->
					<spring:message code="public.title.name"/>
				</th>
				<th width="80"><!--职岗位-->
					<spring:message code="public.title.positionName"/>
				</th>
				<th width="180"><!-- 开始日期 -->
					<spring:message code="public.title.startDate"/>
				</th>			
				<th width="180"><!-- 结束日期 -->
					<spring:message code="public.title.endDate"/>
				</th>
				
				<th width="180"><!--开始时间-->
					<spring:message code="ess.infoApply.title.startTime"/>
				</th>
				<th width="180"><!--结束时间-->
					<spring:message code="ess.infoApply.title.endTime"/>
				</th>
				<th width="100"><!--扣除时间-->
					<spring:message code="ar.viewshift.title.kouchushijian"/>
				</th>
				<th width="100"><!--加班类型-->
					<spring:message code="ess.viewApply.title.overtimeApplyType"/>
				</th>
				<th width="100"><!--强制类型选择-->
					<spring:message code="ess.infoApply.title.forcedTypeChoice"/>
				</th>
				<th width="100"><!--工作内容-->
					<spring:message code="ess.infoApply.title.workContent"/>
				</th>
				<th width="100"><!--决裁者-->
					<spring:message code="ess.infoApply.title.affirmor"/>
				</th>				
			</tr>
		</thead>
        <input id="APPLY_TYPE_NO" name="APPLY_TYPE_NO" type="hidden" size="30" value="31" />	
		    <tr>
			    <td width="40" colspan="4"><spring:message code="ess.infoApply.title.fillItemIntroduction"/><!--点击填充按钮将按此行数据对选中的行数据进行填充-->
			    	&nbsp;&nbsp;<input type="checkbox" id="continueApply" name="continueApply" value="1"/>&nbsp;&nbsp;<font color="red">连续申请</font>
			    </td>
				<td width="180"><!--开始日期-->
				    <input type="text" id="otFromDate" name="otFromDate" class="date" format="yyyy-MM-dd" readonly="true"/>
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
                </td>
				<td width="180"><!--结束日期-->
				    <input type="text" id="otToDate" name="otToDate" class="date" format="yyyy-MM-dd" readonly="true"/>
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
                </td>
				<td width="180"><!--开始时间-->
			        <input type="text" id="otFromTimeHour" name="fromTimeHour" min="0" max="23" size="4">:
			        <input type="text" id="otFromTimeMinute" name="fromTimeMinute"  min="0" max="59" size="4">	
			    </td>
				<td width="180"><!--结束时间-->
				    <input type="text" id="otToTimeHour"   name="toTimeHour"   min="0" max="23" size="4">:
				    <input type="text" id="otToTimeMinute" name="toTimeMinute" min="0" max="59" size="4">					
			    </td>
			    <td width="100"><!--扣除时间-->
				    <!--<ait:SelectSyCodeByCpnyID parentNo="123589" cnpyID="${defaultCpny}" name="otDeductTime" limit="all"/>-->
				    <select name="otDeductTime" id="otDeductTime">
						<option value=""><!--请选择-->
                   			<spring:message code="sys.affirm.title.choose"/>
                   		</option>
						<c:forEach items="${otDeductTimeList}" var="deductList">
							<option value="${deductList.DEDUCT_TIME}" <c:if test="${deductList.DEDUCT_TIME eq otDeductTime}">selected</c:if>>
								${deductList.DEDUCT_TIME}
							</option>
						</c:forEach>
					</select>	    
				</td>	                                 
				<td width="100" colspan="2"><!--申请类型-->
				    <ait:SelectSyCodeByCpnyID parentNo="31" cnpyID="${defaultCpny}" name="otApplyTypeCode" limit="all"/>		    
				</td>
				<td width="80" colspan="2"><!--工作内容-->
				   <textarea id="otApplyRemark" name="otApplyRemark" cols="40" rows="1"></textarea>
				</td>							
			</tr>		        
		<tbody>		 
			<c:forEach items="${personList}" var="person" varStatus="i">			
				<tr target="sid" rel="${person.PERSON_ID}">
				    <td>
				        <input type="checkbox" id="c1" name="c1" value="${person.PERSON_ID}" />
				    </td>
					<td>${person.EMPID}</td>
					<td>${person.LOCAL_NAME}</td>
					<td>${person.POSITION_NAME}</td>
					<td>
					    <input type="text" id="${person.PERSON_ID}_FROM_DATE" name="${person.PERSON_ID}_FROM_DATE" class="date" format="yyyy-MM-dd" readonly="true"/>
				        <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				    </td>
					<td>
					    <input type="text" id="${person.PERSON_ID}_TO_DATE" name="${person.PERSON_ID}_TO_DATE" class="date" format="yyyy-MM-dd" readonly="true"/>
				        <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				    </td>
					<td>
				        <input type="text" id="${person.PERSON_ID}_FROM_TIME_HOUR" name="${person.PERSON_ID}_FROM_TIME_HOUR"  
				               value="${FROM_TIME_HOUR}" min="0" max="23" size="4">:
				        <input type="text" id="${person.PERSON_ID}_FROM_TIME_MINUTE" name="${person.PERSON_ID}_FROM_TIME_MINUTE"  
				               value="${FROM_TIME_MINUTE}" min="0" max="59" size="4">	
				    </td>
					<td>
					    <input type="text" id="${person.PERSON_ID}_TO_TIME_HOUR"   name="${person.PERSON_ID}_TO_TIME_HOUR"   
					           value="${TO_TIME_HOUR}"  min="0" max="23" size="4">:
					    <input type="text" id="${person.PERSON_ID}_TO_TIME_MINUTE" name="${person.PERSON_ID}_TO_TIME_MINUTE" 
					           value="${TO_TIME_MINUTE}" min="0" max="59" size="4">					
				    </td>
				    <td>
					    <!--<ait:SelectSyCodeByCpnyID parentNo="123589" cnpyID="${defaultCpny}" 
							name="${person.PERSON_ID}_OT_DEDUCT_TIME" selected="${OT_DEDUCT_TIME}" limit="all"/>-->
						<select name="${person.PERSON_ID}_OT_DEDUCT_TIME" id="${person.PERSON_ID}_OT_DEDUCT_TIME">
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
					<td>
					    <ait:SelectSyCodeByCpnyID parentNo="31" cnpyID="${defaultCpny}" 
					        name="${person.PERSON_ID}_OT_APPLY_TYPE_CODE" selected="${OT_APPLY_TYPE_CODE}" limit="all"/>
					</td>
					<td>
					    <select name="${person.PERSON_ID}_ifForcedTypeChoice">
							<option value="0" selected="true"><spring:message code="sys.affirm.title.no"/><!--否--></option>
							<option value="1"><spring:message code="sys.affirm.title.yes"/><!--是--></option>
						</select>	
					</td>
					<td><textarea id="${person.PERSON_ID}_APPLY_REMARK" name="${person.PERSON_ID}_APPLY_REMARK" cols="40" rows="1"></textarea></td>
					<td>
                        <a rel="${person.PERSON_ID}_overtimeApplyBatchAffirmView" onclick="passOvertimeApplyValue(${person.PERSON_ID});">
						   <span style="cursor:pointer;"><spring:message code="ess.infoApply.title.viewDetail"/><!--查看详细--></span>
						</a>
					    <a rel="${person.PERSON_ID}_overtimeApplyBatchAffirmView" href="/ess/infoApply/viewAffirmorByPersonIdAndAppCode?APPLY_TYPE_NO=31&&PERSON_ID=${person.PERSON_ID}"  
					       target="dialog" mask="true" width="300" height="300" id="${person.PERSON_ID}_viewOvertimeApplyBatchInfoHref" ></a> 
	                </td>					
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
	<div id="overtimeApplyBatchAffirmView" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>	
	</form>
	<c:set value="/ess/infoApply/viewOtBatchApplyPersonList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>	
</div>

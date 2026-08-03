<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
<!--
function validateBatchLeaveApplyCallback(form,callback) {	
	var $form = $("#applyLeaveBatchForm");
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
	 	alert('<spring:message code="alert.message.ess.infoApply.choosePersonFirst"/>');
		return false;
	}
	$form.find(":checkbox[id='c1']").each(function(index, checkBoxObj){
	    if(checkBoxObj.checked){
	      checked = true ;
	      var personId = $(checkBoxObj).val() ;	   
	      var leaveStartTime = $form.find("[name='"+personId+"_FROM_TIME']").val();
	      var leaveEndTime = $form.find("[name='"+personId+"_TO_TIME']").val();
	      var leaveApplyApply = $form.find("select[name='"+personId+"_LEAVE_APPLY_TYPE_CODE']").val();
		  if(leaveStartTime ==''){
				alertMsg.error('<spring:message code="alert.message.ess.viewApply.leaveStartTimeIsMust"/>');
				$form.find("[name='"+personId+"_FROM_TIME']").focus();
				checked=false;
		   }
          if(leaveEndTime ==''){
				alertMsg.error('<spring:message code="alert.message.ess.viewApply.leaveEndTimeIsMust"/>');
				$form.find("[name='"+personId+"_TO_TIME']").focus();
				checked=false;
		   }
          if(leaveApplyApply ==''){
				alertMsg.error('<spring:message code="alert.message.ess.viewApply.leaveApplyApplyIsMust"/>');
				$form.find("select[name='"+personId+"_APPLY_TYPE_CODE']").focus();
				checked=false;
			}	 
		  if(leaveStartTime>leaveEndTime)
			{
			   alertMsg.error('<spring:message code="alert.message.ess.viewApply.leaveStartTimeNotLaterThanLeaveEndTime"/>');
			   $form.find("[name='"+personId+"_FROM_TIME']").focus();
			  checked=false;
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
				navTabSearch("searchLeaveApplyBatchForm");
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

function passLeaveApplyValue(personId)
{   
    var fromTime      = document.getElementById(personId+"_FROM_TIME").value;
    var toTime        = document.getElementById(personId+"_TO_TIME").value;
    var applyTypeCode = document.getElementById(personId+"_LEAVE_APPLY_TYPE_CODE").value;
    var applyTypeNo   = document.getElementById("APPLY_TYPE_NO").value;
    
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
    document.getElementById(personId+"_viewLeaveApplyBatchInfoHref").href = 
    document.getElementById(personId+"_viewLeaveApplyBatchInfoHref").href+
                            "&&APPLY_TYPE_CODE="+applyTypeCode+
                            "&&FROM_TIME="+fromTime+
                            "&&TO_TIME="+toTime+
                            "&&APPLY_TYPE_NO="+applyTypeNo;
    document.getElementById(personId+"_viewLeaveApplyBatchInfoHref").click();			
}
 
function fillItemLeave(){
  var leaveFromTime=document.getElementById("leaveFromTime").value; 
  var leaveToTime=document.getElementById("leaveToTime").value; 
  var leaveReason =document.getElementById("leaveReason").value; 
  var leaveApplyTypeCode=document.getElementById("leaveApplyTypeCode").value; 
 
  var $form = $("#applyLeaveBatchForm");
  $form.find(":checkbox[id='c1']").each(function(index, checkBoxObj){
  if(checkBoxObj.checked){
      var personId = $(checkBoxObj).val() ;
         $form.find("[name='"+personId+"_FROM_TIME']").attr("value",leaveFromTime);
         $form.find("[name='"+personId+"_TO_TIME']").attr("value",leaveToTime);
         $form.find("select[name='"+personId+"_LEAVE_APPLY_TYPE_CODE']").attr("value",leaveApplyTypeCode);
         $form.find("[name='"+personId+"_LEAVE_REASON']").attr("value",leaveReason);
  }
  });
}
//-->
</script>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewLeaveBatchApplyPersonList" 
	      method="post" rel="pagerForm" id="searchLeaveApplyBatchForm" name="searchLeaveApplyBatchForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>			
				<td>
					 <spring:message code="public.title.deptName"/><!-- 部门 -->:
				</td>	
				<td>
					<ait:deptTree name="seach_DEPT_NO" limit="ar" selected="${DEPT_NO }"/>
				</td>
                <td>
					<spring:message code="ess.infoApply.title.kewWord"/><!--关键字-->:
				</td>
				<td>
					<input type="text" name="seach_KEY" value="${KEY}" />
				</td>
                <td>
                    <spring:message code="public.title.startDate"/><!-- 开始日期 -->:
                </td>			
			    <td>
			        <input type="text" name="seach_FROM_TIME" class="date" format="yyyy-MM-dd" readonly="true" value="${FROM_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td>
                    <spring:message code="public.title.endDate"/><!-- 结束日期 -->:
                </td>                			     
				<td>
				    <input type="text" name="seach_TO_TIME" class="date" format="yyyy-MM-dd" readonly="true" value="${TO_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"></a>
				</td> 	
				<td>
                        <!-- 动态组 --><spring:message code="ar.addempshift.title.dynamicgroup"/>:
                </td>                			     
				<td>
				    <select name="seach_GROUP_NO" id="seach_GROUP_NO">
						<option value=""><!-- 全部 --><spring:message code="ar.viewarcardrecord.title.quanbu"/></option>
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
						         <a class="update" onclick="return validateBatchLeaveApplyCallback('applyLeaveBatchForm',DWZ.ajaxDone);" href="#" >
						         <span><spring:message code="ess.infoApply.title.apply"/><!--申请--></span></a>
						         <a class="update" onclick="fillItemLeave();" href="#" >
						         <span><spring:message code="ess.infoApply.title.fillItem"/><!--填充--></span></a> 
                            </div>
				    </div>	
				</li>
		</tr>
	</div>
	</form>
</div>	
      
<div class="pageContent" style="padding:5px;">
     <form style="margin:0px;padding:0px;" name="applyLeaveBatchForm" id="applyLeaveBatchForm" method="post" action="/ess/infoApply/addBatchLeaveApply"
	       class="pageForm required-validate" onsubmit="return validateBatchLeaveApplyCallback(this,navTabAjaxDone);">  
	<table class="tablea" width="100%" layoutH="110">
		<thead>
			<tr>
			    <th width="40"><input type="checkbox" class="checkboxCtrl" group="c1" /></th>
				<th width="80"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="80"><spring:message code="public.title.name"/><!--姓名--></th>
				<th width="180"><spring:message code="public.title.positionName"/><!--职岗位--></th>
				<th width="180"><spring:message code="ess.infoApply.title.leaveStartTime"/><!--休假开始时间--></th>
				<th width="180"><spring:message code="ess.infoApply.title.leaveEndTime"/><!--休假结束时间--></th>
				<th width="80"><spring:message code="ess.infoApply.title.leaveContent"/><!--休假内容--></th>
				<th width="130"><spring:message code="ess.infoApply.title.restAnnualLeave"/><!--剩余年假--></th>
				<c:if test="${ defaultCpny=='C04' }">
				<th width="130"><spring:message code="ess.infoApply.title.restAnnualLeaveQN"/><!--qn剩余年假--></th>
				</c:if>
				<th width="130"><spring:message code="ess.infoApply.title.surAdjustRest"/><!--剩余调休--></th>
				<th width="100"><spring:message code="ess.infoApply.title.leaveApplyType"/><!--休假类型--></th>
				<th width="100"><spring:message code="ess.infoApply.title.affirmor"/><!--决裁者--></th>				
			</tr>
		</thead>
		<input id="APPLY_TYPE_NO" name="APPLY_TYPE_NO" type="hidden" size="30" value="21" />
		    <tr>
			    <td width="40" colspan="4"><spring:message code="ess.infoApply.title.fillItemIntroduction"/><!--点击填充按钮将按此行数据对选中的行数据进行填充--></td>
				<td width="180">
				    <input type="text" id="leaveFromTime" name="leaveFromTime" class="date" format="yyyy-MM-dd HH:mm" readonly="true"/>
			        <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 -->
			        </a>
				</td>
				<td width="180">
				    <input type="text" id="leaveToTime" name="leaveToTime" class="date" format="yyyy-MM-dd HH:mm" readonly="true"/>
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
                </td>
				<td width="80" colspan="3">
				    <input type="text" id="leaveReason" name="leaveReason"/>
				</td>
					<c:if test="${ defaultCpny=='C04' }">
				<td></td>
				</c:if>
				<td width="100" colspan="2">
				    <ait:SelectSyCodeByCpnyID parentNo="21" cnpyID="${defaultCpny}" name="leaveApplyTypeCode" limit="all"/>		    
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
					    <input type="text" id="${person.PERSON_ID}_FROM_TIME" name="${person.PERSON_ID}_FROM_TIME" class="date" 
					           format="yyyy-MM-dd HH:mm" readonly="true"/>
				        <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				    </td>
					<td>
					    <input type="text" id="${person.PERSON_ID}_TO_TIME" name="${person.PERSON_ID}_TO_TIME" class="date" 
					           format="yyyy-MM-dd HH:mm" readonly="true"/>
				        <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				    </td>
					<td>
					    <input type="text" id="${person.PERSON_ID}_LEAVE_REASON" name="${person.PERSON_ID}_LEAVE_REASON" 
					           idName="LEAVE_REASON_${i.index}"/>
				    </td>
					<td>${person.REST_ANNUAL_LEAVE}</td>
					<c:if test="${ defaultCpny=='C04' }">
					<td>${person.REST_ANNUAL_LEAVEQN}</td>
					</c:if>
					<td>${person.SUR_ADJUST_REST}</td>			    				    
					<td>
					    <ait:SelectSyCodeByCpnyID parentNo="21" cnpyID="${defaultCpny}" 
					         name="${person.PERSON_ID}_LEAVE_APPLY_TYPE_CODE" selected="${LEAVE_APPLY_TYPE_CODE}" limit="all"/>
					</td>
					<td>
                        <a rel="${person.PERSON_ID}_LeaveApplyBatchAffirmView" onclick="passLeaveApplyValue(${person.PERSON_ID});">
						   <span style="cursor:pointer;"><spring:message code="ess.infoApply.title.viewDetail"/><!--查看详细--></span>
						</a>
					    <a rel="${person.PERSON_ID}_LeaveApplyBatchAffirmView" href="/ess/infoApply/viewAffirmorByPersonIdAndAppCode?APPLY_TYPE_NO=21&&PERSON_ID=${person.PERSON_ID}"  
					       target="dialog" mask="true" width="300" height="300" id="${person.PERSON_ID}_viewLeaveApplyBatchInfoHref" >
					    </a> 
	                </td>					
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
	<div id="overtimeApplyBatchAffirmView" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>	
	</form>
	<c:set value="/ess/infoApply/viewLeaveBatchApplyPersonList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>	
</div>

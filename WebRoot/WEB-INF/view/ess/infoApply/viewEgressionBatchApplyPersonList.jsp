<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
<!--
function validateBatchEgressionApplyCallback(form,callback) {	
	var $form = $("#applyEgressionBatchForm");
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
		  if($form.find("[name='"+personId+"_EG_FROM_TIME']").val()==''){
				alertMsg.error('<spring:message code="alert.message.ess.infoApply.egressionStartTimeIsMust"/>');
				$form.find("[name='"+personId+"_EG_FROM_TIME']").focus();
				checked=false;
		   }
          if($form.find("[name='"+personId+"_EG_TO_TIME']").val()==''){
				alertMsg.error('<spring:message code="alert.message.ess.infoApply.egressionEndTimeIsMust"/>');
				$form.find("[name='"+personId+"_EG_TO_TIME']").focus();
				checked=false;
		   }
          if($form.find("select[name='"+personId+"_EG_APPLY_TYPE_CODE']").val()==''){
				alertMsg.error('<spring:message code="alert.message.ess.infoApply.egressionApplyTypeIsMust"/>');
				$form.find("select[name='"+personId+"_EG_APPLY_TYPE_CODE']").focus();
				checked=false;
			}	 
		  if($form.find("[name='"+personId+"_EG_FROM_TIME']").val()>$form.find("[name='"+personId+"_EG_TO_TIME']").val())
			{
			   alertMsg.error('<spring:message code="alert.message.ess.viewApply.evStartTimeNotLaterThanLeaveEndTime"/>');
			   $form.find("[name='"+personId+"_EG_FROM_TIME']").focus();
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
				navTabSearch("searchEgressionApplyBatchForm");
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

function passEgressionApplyValue(personId)
{   
    var fromTime      = document.getElementById(personId+"_EG_FROM_TIME").value;
    var toTime        = document.getElementById(personId+"_EG_TO_TIME").value;
    var applyTypeCode = document.getElementById(personId+"_EG_APPLY_TYPE_CODE").value;
    var applyTypeNo   = document.getElementById("EG_APPLY_TYPE_NO").value;
    
    if(fromTime==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.egressionStartTimeIsMust"/>');
       return false;
	}
    if(toTime==""){
	   alertMsg.error('<spring:message code="alert.message.ess.viewApply.leaveEndTimeIsMust"/>');
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
    document.getElementById(personId+"_viewEvectionApplyBatchInfoHref").href = 
    document.getElementById(personId+"_viewEvectionApplyBatchInfoHref").href+
                            "&&APPLY_TYPE_CODE="+applyTypeCode+
                            "&&FROM_TIME="+fromTime+
                            "&&TO_TIME="+toTime+
                            "&&APPLY_TYPE_NO="+applyTypeNo;
    document.getElementById(personId+"_viewEvectionApplyBatchInfoHref").click();			
}

function fillItemEg(){
  var egFromTime=document.getElementById("egFromTime").value; 
  var egToTime=document.getElementById("egToTime").value; 
  var egReason =document.getElementById("egReason").value; 
  var egApplyTypeCode=document.getElementById("egApplyTypeCode").value; 
 
  var $form = $("#applyEgressionBatchForm");
  $form.find(":checkbox[id='c1']").each(function(index, checkBoxObj){
  if(checkBoxObj.checked){
      var personId = $(checkBoxObj).val() ;
         $form.find("[name='"+personId+"_EG_FROM_TIME']").attr("value",egFromTime);
         $form.find("[name='"+personId+"_EG_TO_TIME']").attr("value",egToTime);
         $form.find("select[name='"+personId+"_EG_APPLY_TYPE_CODE']").attr("value",egApplyTypeCode);
         $form.find("[idname='"+personId+"_EG_LEAVE_REASON']").attr("value",egReason);
  }
  });
}
//-->
</script>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewEgressionBatchApplyPersonList" 
	      method="post" rel="pagerForm" id="searchEgressionApplyBatchForm" name="searchEgressionApplyBatchForm">
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
						         <a class="update" onclick="return validateBatchEgressionApplyCallback('applyEgressionBatchForm',DWZ.ajaxDone);" href="#" >
						         <span><spring:message code="ess.infoApply.title.apply"/><!--申请--></span></a>
						         <a class="update" onclick="fillItemEg();" href="#" >
						         <span><spring:message code="ess.infoApply.title.fillItem"/><!--填充--></span></a>
                            </div>
				    </div>	
				</li>
		</tr>
	</div>
	</form>
</div>	

<div class="pageContent" style="padding:5px;">
     <form style="margin:0px;padding:0px;" name="applyEgressionBatchForm" id="applyEgressionBatchForm" method="post" 
           action="/ess/infoApply/addBatchEgressionApply"
	       class="pageForm required-validate" onsubmit="return validateBatchEgressionApplyCallback(this,navTabAjaxDone);">  
	<table class="tablea" width="100%" layoutH="110">
		<thead>
			<tr>
			    <th width="40"><input type="checkbox" class="checkboxCtrl" group="c1" /></th>
				<th width="80"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="80"><spring:message code="public.title.name"/><!--姓名--></th>
				<th width="80"><spring:message code="public.title.positionName"/><!--职岗位--></th>
				<th width="180"><spring:message code="ess.infoApply.title.egressionStartTime"/><!--外出开始时间--></th>
				<th width="180"><spring:message code="ess.infoApply.title.egressionEndTime"/><!--外出结束时间--></th>
				<th width="80"><spring:message code="ess.infoApply.title.egContent"/><!--外出内容--></th> 
				<th width="100"><spring:message code="ess.infoApply.title.egressionApplyType"/><!--外出类型--></th>
				<th width="100"><spring:message code="ess.infoApply.title.affirmor"/><!--决裁者--></th>				
			</tr>
		</thead>
        <input id="EG_APPLY_TYPE_NO" name="EG_APPLY_TYPE_NO" type="hidden" size="30" value="16201" />	
 		    <tr>
			    <td width="40" colspan="4"><spring:message code="ess.infoApply.title.fillItemIntroduction"/><!--点击填充按钮将按此行数据对选中的行数据进行填充--></td>
				<td width="180">
				    <input type="text" id="egFromTime" name="egFromTime" class="date" format="yyyy-MM-dd HH:mm" readonly="true"/>
			        <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 -->
			        </a>
				</td>
				<td width="180">
				    <input type="text" id="egToTime" name="egToTime" class="date" format="yyyy-MM-dd HH:mm" readonly="true"/>
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
                </td>
				<td width="80">
				    <input type="text" id="egReason" name="egReason"/>
				</td>
				<td width="100" colspan="2">
				    <ait:SelectSyCodeByCpnyID parentNo="16201" cnpyID="${defaultCpny}" name="egApplyTypeCode" limit="all"/>		    
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
					    <input type="text" id="${person.PERSON_ID}_EG_FROM_TIME" name="${person.PERSON_ID}_EG_FROM_TIME" class="date" format="yyyy-MM-dd HH:mm" readonly="true"/>
				        <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				    </td>
					<td>
					    <input type="text" id="${person.PERSON_ID}_EG_TO_TIME" name="${person.PERSON_ID}_EG_TO_TIME" class="date" format="yyyy-MM-dd HH:mm" readonly="true"/>
				        <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				    </td>
					<td>
					    <input type="text" id="${person.PERSON_ID}_LEAVE_REASON" name="${person.PERSON_ID}_LEAVE_REASON"
					           idName="${person.PERSON_ID}_EG_LEAVE_REASON"/>
				    </td> 		    				    
					<td>
					    <ait:SelectSyCodeByCpnyID parentNo="16201" cnpyID="${defaultCpny}" 
					                              name="${person.PERSON_ID}_EG_APPLY_TYPE_CODE" selected="${EG_APPLY_TYPE_CODE}" limit="all"/>
					</td>
					<td>
                        <a rel="${person.PERSON_ID}_EvectionApplyBatchAffirmView" onclick="passEgressionApplyValue(${person.PERSON_ID});">
						   <span style="cursor:pointer;"><spring:message code="ess.infoApply.title.viewDetail"/><!--查看详细--></span>
						</a>
					    <a rel="${person.PERSON_ID}_EvectionApplyBatchAffirmView" href="/ess/infoApply/viewAffirmorByPersonIdAndAppCode?APPLY_TYPE_NO=16201&&PERSON_ID=${person.PERSON_ID}"  
					       target="dialog" mask="true" width="300" height="300" id="${person.PERSON_ID}_viewEvectionApplyBatchInfoHref" >
					    </a> 
	                </td>					
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
	<div id="EvectionApplyBatchAffirmView" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>	
	</form>
	<c:set value="/ess/infoApply/viewEgressionBatchApplyPersonList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>	
</div>

<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
<!--
function validateBatchEvectionApplyCallback(form,callback) {	
	var $form = $("#applyEvectionBatchForm");
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
		  if($form.find("[name='"+personId+"_EVE_FROM_TIME']").val()==''){
				alertMsg.error('<spring:message code="alert.message.ess.viewApply.evectionStartTimeIsMust"/>');
				$form.find("[name='"+personId+"_EVE_FROM_TIME']").focus();
				checked=false;
		   }
          if($form.find("[name='"+personId+"_EVE_TO_TIME']").val()==''){
				alertMsg.error('<spring:message code="alert.message.ess.viewApply.evectionEndTimeIsMust"/>');
				$form.find("[name='"+personId+"_EVE_TO_TIME']").focus();
				checked=false;
		   }
          if($form.find("select[name='"+personId+"_EVE_APPLY_TYPE_CODE']").val()==''){
				alertMsg.error('<spring:message code="alert.message.ess.viewApply.evApplyCodeIsMust"/>');
				$form.find("select[name='"+personId+"_EVE_APPLY_TYPE_CODE']").focus();
				checked=false;
			}	 
		  if($form.find("[name='"+personId+"_EVE_FROM_TIME']").val()>$form.find("[name='"+personId+"_EVE_TO_TIME']").val())
			{
			   alertMsg.error('<spring:message code="alert.message.ess.viewApply.evStartTimeNotLaterThanLeaveEndTime"/>');
			   $form.find("[name='"+personId+"_EVE_FROM_TIME']").focus();
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
				navTabSearch("searchEvectionApplyBatchForm");
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

function passEvectionApplyValue(personId)
{   
    var fromTime      = document.getElementById(personId+"_EVE_FROM_TIME").value;
    var toTime        = document.getElementById(personId+"_EVE_TO_TIME").value;
    var applyTypeCode = document.getElementById(personId+"_EVE_APPLY_TYPE_CODE").value;
    var applyTypeNo   = document.getElementById("EVE_APPLY_TYPE_NO").value;
    
    if(fromTime==""){
       alertMsg.error('<spring:message code="alert.message.ess.viewApply.evectionStartTimeIsMust"/>');
       return false;
	}
    if(toTime==""){
	   alertMsg.error('<spring:message code="alert.message.ess.viewApply.evectionEndTimeIsMust"/>');
	   return false;
	}
	if(applyTypeCode==""){
       alertMsg.error('<spring:message code="alert.message.ess.viewApply.evApplyCodeIsMust"/>');
       return false;
	}

    if(fromTime>toTime){
	   alertMsg.error('<spring:message code="alert.message.ess.viewApply.evStartTimeNotLaterThanLeaveEndTime"/>');
	   return false;
	}
    document.getElementById(personId+"_ViewEvectionApplyBatchInfoHref").href = 
    document.getElementById(personId+"_ViewEvectionApplyBatchInfoHref").href+
                            "&&APPLY_TYPE_CODE="+applyTypeCode+
                            "&&FROM_TIME="+fromTime+
                            "&&TO_TIME="+toTime+
                            "&&APPLY_TYPE_NO="+applyTypeNo;
    document.getElementById(personId+"_ViewEvectionApplyBatchInfoHref").click();			
}

function fillItemEve(){
  var eveFromTime=document.getElementById("eveFromTime").value; 
  var eveToTime=document.getElementById("eveToTime").value; 
  var eveReason =document.getElementById("eveReason").value; 
  var eveApplyTypeCode=document.getElementById("eveApplyTypeCode").value; 
 
  var $form = $("#applyEvectionBatchForm");
  $form.find(":checkbox[id='c1']").each(function(index, checkBoxObj){
  if(checkBoxObj.checked){
      var personId = $(checkBoxObj).val() ;
         $form.find("[name='"+personId+"_EVE_FROM_TIME']").attr("value",eveFromTime);
         $form.find("[name='"+personId+"_EVE_TO_TIME']").attr("value",eveToTime);
         $form.find("select[name='"+personId+"_EVE_APPLY_TYPE_CODE']").attr("value",eveApplyTypeCode);
         $form.find("[idname='"+personId+"_EVE_LEAVE_REASON']").attr("value",eveReason);
  }
  });
}
//-->
</script>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewEvectionBatchApplyPersonList" 
	      method="post" rel="pagerForm" id="searchEvectionApplyBatchForm" name="searchEvectionApplyBatchForm">
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
						         <a class="update" onclick="return validateBatchEvectionApplyCallback('applyEvectionBatchForm',DWZ.ajaxDone);" 
						            href="#" ><span><spring:message code="ess.infoApply.title.apply"/><!--申请--></span></a>
						         <a class="update" onclick="fillItemEve();" href="#" >
						         <span><spring:message code="ess.infoApply.title.fillItem"/><!--填充--></span></a>   
                            </div>
				    </div>	
				</li>
		</tr>
	</div>
	</form>
</div>	

<div class="pageContent" style="padding:5px;">
     <form style="margin:0px;padding:0px;" name="applyEvectionBatchForm" id="applyEvectionBatchForm" method="post" 
           action="/ess/infoApply/addBatchEvectionApply"
	       class="pageForm required-validate" onsubmit="return validateBatchEvectionApplyCallback(this,navTabAjaxDone);">  
	<table class="tablea" width="100%" layoutH="110">
		<thead>
			<tr>
			    <th width="40"><input type="checkbox" class="checkboxCtrl" group="c1" /></th>
				<th width="80"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="80"><spring:message code="public.title.name"/><!--姓名--></th>
				<th width="80"><spring:message code="public.title.positionName"/><!--职岗位--></th>
				<th width="180"><spring:message code="ess.infoApply.title.evectionStartTime"/><!--出差开始时间--></th>
				<th width="180"><spring:message code="ess.infoApply.title.evectionEndTime"/><!--出差结束时间--></th>
				<th width="80"><spring:message code="ess.infoApply.title.evectionContent"/><!--出差内容--></th>
				<th width="100"><spring:message code="ess.infoApply.title.evectionApplyType"/><!--出差类型--></th>
				<th width="100"><spring:message code="ess.infoApply.title.affirmor"/><!--决裁者--></th>				
			</tr>
		</thead>
        <input id="EVE_APPLY_TYPE_NO" name="EVE_APPLY_TYPE_NO" type="hidden" size="30" value="18" />	
		    <tr>
			    <td width="40" colspan="4"><spring:message code="ess.infoApply.title.fillItemIntroduction"/><!--点击填充按钮将按此行数据对选中的行数据进行填充--></td>
				<td width="180">
				    <input type="text" id="eveFromTime" name="eveFromTime" class="date" format="yyyy-MM-dd HH:mm" readonly="true"/>
			        <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 -->
			        </a>
				</td>
				<td width="180">
				    <input type="text" id="eveToTime" name="eveToTime" class="date" format="yyyy-MM-dd HH:mm" readonly="true"/>
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
                </td>
				<td width="80"  >
				    <input type="text" id="eveReason" name="eveReason"/>
				</td>
				<td width="100" colspan="2">
				    <ait:SelectSyCodeByCpnyID parentNo="18" cnpyID="${defaultCpny}" name="eveApplyTypeCode" limit="all"/>		    
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
					    <input type="text" id="${person.PERSON_ID}_EVE_FROM_TIME" name="${person.PERSON_ID}_EVE_FROM_TIME" class="date" format="yyyy-MM-dd HH:mm" readonly="true"/>
				        <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/> <!--选择--></a>
				    </td>
					<td>
					    <input type="text" id="${person.PERSON_ID}_EVE_TO_TIME" name="${person.PERSON_ID}_EVE_TO_TIME" class="date" format="yyyy-MM-dd HH:mm" readonly="true"/>
				        <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/> <!--选择 --></a>
				    </td>
					<td>
					    <input type="text" id="${person.PERSON_ID}_LEAVE_REASON" name="${person.PERSON_ID}_LEAVE_REASON" 
					           idName="${person.PERSON_ID}_EVE_LEAVE_REASON"/>
				    </td>		    				    
					<td>
					    <ait:SelectSyCodeByCpnyID parentNo="18" cnpyID="${defaultCpny}" 
					                              name="${person.PERSON_ID}_EVE_APPLY_TYPE_CODE" selected="${EVE_APPLY_TYPE_CODE}" limit="all"/>
					</td>
					<td>
                        <a rel="${person.PERSON_ID}_EvectionApplyBatchAffirmView" onclick="passEvectionApplyValue(${person.PERSON_ID});">
						   <span style="cursor:pointer;"><spring:message code="ess.infoApply.title.viewDetail"/><!--查看详细--></span>
						</a>
					    <a rel="${person.PERSON_ID}_EvectionApplyBatchAffirmView" href="/ess/infoApply/viewAffirmorByPersonIdAndAppCode?APPLY_TYPE_NO=18&&PERSON_ID=${person.PERSON_ID}"  
					       target="dialog" mask="true" width="300" height="300" id="${person.PERSON_ID}_ViewEvectionApplyBatchInfoHref" >
					    </a> 
	                </td>					
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
	<div id="evectionApplyBatchAffirmView" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>	
	</form>
	<c:set value="/ess/infoApply/viewEvectionBatchApplyPersonList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>	
</div>
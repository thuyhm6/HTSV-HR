<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
<!--
function validateBatchMacRecordApplyCallback(form,callback) {	
	var $form = $("#applyMacRecordBatchForm");
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
		  	if($form.find("[name='"+personId+"_BATCH_APPLY_DATE']").val()==''){
				alertMsg.error("请选择申请日期！");
				$form.find("[name='"+personId+"_BATCH_APPLY_DATE']").focus();
				checked=false;
		   	}  
		  	if($form.find("[name='"+personId+"_BATCH_R_DATE']").val()==''){
				alertMsg.error("请选择进/出门的日期！");
				$form.find("[name='"+personId+"_BATCH_R_DATE']").focus();
				checked=false;
		   	}  
		  	if($form.find("[name='"+personId+"_BATCH_R_HOUR']").val()==''){
				alertMsg.error("请输入进/出门的小时！");
				$form.find("[name='"+personId+"_BATCH_R_HOUR']").focus();
				checked=false;
		   	}
		  	if($form.find("[name='"+personId+"_BATCH_R_MINUTE']").val()==''){
				alertMsg.error("请输入进/出门的分钟！");
				$form.find("[name='"+personId+"_BATCH_R_MINUTE']").focus();
				checked=false;
		   	}	
         	   
          	if($form.find("select[name='"+personId+"_BATCH_APPLY_TYPE_CODE']").val()==''){
				alertMsg.error("请选择进/出门类型！");
				$form.find("select[name='"+personId+"_BATCH_APPLY_TYPE_CODE']").focus();
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
					navTabSearch("searchArMacRecordApplyBatchForm");
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

function passMacRecordApplyValue(personId){   
    var applyType = document.getElementById(personId+"_BATCH_APPLY_TYPE_CODE").value;
   
 	if(applyType==""){
	    alertMsg.error("请选择进/出门类型！");
	    return false;
	}

    document.getElementById(personId+"_viewMacRecordApplyBatchInfoHref").href = 
    document.getElementById(personId+"_viewMacRecordApplyBatchInfoHref").href+"&APPLY_TYPE_CODE="+applyType;
    document.getElementById(personId+"_viewMacRecordApplyBatchInfoHref").click();			
}

function fillItemMacRecord(){
  var applyDate = document.getElementById("BATCH_APPLY_DATE").value; 
  var rDate     = document.getElementById("BATCH_R_DATE").value; 
  var rHour     = document.getElementById("BATCH_R_HOUR").value; 
  var rMinute   = document.getElementById("BATCH_R_MINUTE").value;
  var remark    = document.getElementById("BATCH_REMARK").value; 
  var applyType = document.getElementById("BATCH_APPLY_TYPE_CODE").value; 
  
  var $form = $("#applyMacRecordBatchForm");
  $form.find(":checkbox[id='c1']").each(function(index, checkBoxObj){
  if(checkBoxObj.checked){
      var personId = $(checkBoxObj).val() ;
         $form.find("[name='"+personId+"_BATCH_APPLY_DATE']").attr("value",applyDate);
         $form.find("[name='"+personId+"_BATCH_R_DATE']").attr("value",rDate);
         $form.find("[name='"+personId+"_BATCH_R_HOUR']").attr("value",rHour);
         $form.find("[name='"+personId+"_BATCH_R_MINUTE']").attr("value",rMinute);
         $form.find("[name='"+personId+"_BATCH_REMARK']").attr("value",remark);
         $form.find("select[name='"+personId+"_BATCH_APPLY_TYPE_CODE']").attr("value",applyType);
  }
  });
}
//-->
</script>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/recordApply/viewArMacRecordBatchApplyPersonList" method="post" 
	      rel="pagerForm" id="searchArMacRecordApplyBatchForm" name="searchArMacRecordApplyBatchForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>			
				<td><!-- 部门 -->
					 <spring:message code="public.title.deptName"/>:
				</td>	
				<td>
					<ait:deptTree name="seach_DEPT_NO" limit="hr" selected="${DEPT_NO }"/>
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
						         <a class="update" onclick="return validateBatchMacRecordApplyCallback('applyMacRecordBatchForm',DWZ.ajaxDone);" href="#" ><span>
						         <spring:message code="ess.infoApply.title.apply"/><!--申请--></span></a>
						         <a class="update" onclick="fillItemMacRecord();" href="#" >
						         <span><spring:message code="ess.infoApply.title.fillItem"/><!--填充--></span></a>
                            </div>
				    </div>	
				</li>
		</tr>
	</div>
	</form>
</div>	

<div class="pageContent" style="padding:5px;">
     <form style="margin:0px;padding:0px;" name="applyMacRecordBatchForm" id="applyMacRecordBatchForm" method="post" action="/ess/recordApply/addBatchArMacRecordApply" 
           class="pageForm required-validate" onsubmit="return validateBatchMacRecordApplyCallback(this,navTabAjaxDone);">  
	<table class="tablea" width="99%" layoutH="110">
		<thead>
			<tr>
			    <th width="40"><input type="checkbox" class="checkboxCtrl" group="c1" /></th>
				<th width="60"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="80"><spring:message code="public.title.name"/><!--姓名--></th>
				<th width="80"><spring:message code="public.title.positionName"/><!--职岗位--></th>
				<th width="150">申请日期</th>			
				<th width="150">日期</th>
				<th width="120">开始时间</th>
				<th width="80">上/下班类型</th>
				</th>
				<th width="40"><spring:message code="ess.infoApply.title.workContent"/><!--工作内容--></th>
				<th width="60"><spring:message code="ess.infoApply.title.affirmor"/><!--决裁者--></th>				
			</tr>
		</thead>
        	<input id="BATCH_APPLY_TYPE_NO" name="BATCH_APPLY_TYPE_NO" type="hidden" size="30" value="123499" />	
		    <tr>
			    <td colspan="4"><spring:message code="ess.infoApply.title.fillItemIntroduction"/><!--点击填充按钮将按此行数据对选中的行数据进行填充-->
			    	<!--  &nbsp;&nbsp;<input type="checkbox" id="continueApply" name="continueApply" value="1"/>&nbsp;&nbsp;<font color="red">连续申请</font>-->
			    </td>
				<td width="150">
				    <input type="text" id="BATCH_APPLY_DATE" name="BATCH_APPLY_DATE" class="date" format="yyyy-MM-dd" readonly="true"/>
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
                </td>
				<td width="150">
				    <input type="text" id="BATCH_R_DATE" name="BATCH_R_DATE" class="date" format="yyyy-MM-dd" readonly="true"/>
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
                </td>
				<td width="120">
			        <input type="text" id="BATCH_R_HOUR" name="BATCH_R_HOUR" min="0" max="23" size="4">:
			        <input type="text" id="BATCH_R_MINUTE" name="BATCH_R_MINUTE"  min="0" max="59" size="4">	
			    </td>                   
				<td width="80">
				    <ait:SelectSyCodeByCpnyID parentNo="123499" cnpyID="${defaultCpny}" name="BATCH_APPLY_TYPE_CODE" limit="all"/>		    
				</td>
				<td width="40">
				   <textarea id="BATCH_REMARK" name="BATCH_REMARK" cols="20" rows="1"></textarea>
				</td>		
				<td>&nbsp;</td>					
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
					    <input type="text" id="${person.PERSON_ID}_BATCH_APPLY_DATE" name="${person.PERSON_ID}_BATCH_APPLY_DATE" class="date" format="yyyy-MM-dd" readonly="true"/>
				        <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				    </td>
					<td>
					    <input type="text" id="${person.PERSON_ID}_BATCH_R_DATE" name="${person.PERSON_ID}_BATCH_R_DATE" class="date" format="yyyy-MM-dd" readonly="true"/>
				        <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				    </td>
					<td>
				        <input type="text" id="${person.PERSON_ID}_BATCH_R_HOUR" name="${person.PERSON_ID}_BATCH_R_HOUR" value="${BATCH_R_HOUR}" min="0" max="23" size="4">:
				        <input type="text" id="${person.PERSON_ID}_BATCH_R_MINUTE" name="${person.PERSON_ID}_BATCH_R_MINUTE" value="${BATCH_R_MINUTE}" min="0" max="59" size="4">	
				    </td>
					<td>
					    <ait:SelectSyCodeByCpnyID parentNo="123499" cnpyID="${defaultCpny}" 
							name="${person.PERSON_ID}_BATCH_APPLY_TYPE_CODE" selected="${BATCH_APPLY_TYPE_CODE}" limit="all"/>
					</td>
					<td>
						<textarea id="${person.PERSON_ID}_BATCH_REMARK" name="${person.PERSON_ID}_BATCH_REMARK" cols="20" rows="1"></textarea>
					</td>
					<td>
                        <a rel="${person.PERSON_ID}_macRecordApplyBatchAffirmView" onclick="passMacRecordApplyValue(${person.PERSON_ID});">
						   <span style="cursor:pointer;"><spring:message code="ess.infoApply.title.viewDetail"/><!--查看详细--></span>
						</a>
					    <a rel="${person.PERSON_ID}_macRecordApplyBatchAffirmView" href="/ess/recordApply/viewArMacRecordAffirmor?APPLY_TYPE_NO=123499&PERSON_ID=${person.PERSON_ID}"  
					       target="dialog" mask="true" width="300" height="300" id="${person.PERSON_ID}_viewMacRecordApplyBatchInfoHref" ></a> 
	                </td>					
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
	<div id="macRecordApplyBatchAffirmView" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>	
	</form>
	<c:set value="/ess/recordApply/viewArMacRecordBatchApplyPersonList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>	
</div>

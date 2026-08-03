<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script type="text/javascript">
//批量通过奖励
function validateRewardCallback(form, callback,flag) {
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	
    var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		alertMsg.info('<spring:message code="ess.trans.title.chooseFirstThenBatchOperation"/>');
		return false;
	}

    $form.attr("action","/ess/trans/approveRewardTransInBatch?AFFIRM_FLAG="+flag);

	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				alertMsg.correct(data.message);
				$("#searchAffirmForm").submit();
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

//批量通过惩罚
function approvePuValidateCallback(form,callback,flag) {
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	
    var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		alertMsg.info('<spring:message code="ess.trans.title.chooseFirstThenBatchOperation"/>');
		return false;
	}

    $form.attr("action","/ess/trans/approvePunishmentTransInBatch?AFFIRM_FLAG="+flag);

	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				alertMsg.correct(data.message);
				$("#searchAffirmForm").submit();
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

//调令类型变化时发生
$(document).ready(function(){
	var transCodeFromQuickMenu = "${transCodeFromQuickMenu}";
    
	$("#searchTransferOrderAffirm").change(function(){
		$("#searchAffirmForm").submit();
	});
	
	if(transCodeFromQuickMenu != "" && transCodeFromQuickMenu != "error"){
	    $("#searchTransferOrderAffirm > option[value=${transCodeFromQuickMenu}]").attr("selected", "true");
	    $("#searchAffirmForm").submit();
	}
	    
});

function passOrCancalCallback(form,callback,HR_AFFIRM_NO,START_DATE,EMPID,PERSON_ID,AFFIRM_FLAG,AFFIRM_LEVEL,EXP_INSIDE_NO) {
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
	if (!$form.valid()) {
		return false;
	}

	if("updateRewardTransAffirmForm" == form){
	    $form.attr("action","/ess/trans/approveRewardTrans?HR_AFFIRM_NO="+HR_AFFIRM_NO+"&&START_DATE="+START_DATE+"&&EMPID="+EMPID+"&&PERSON_ID="+PERSON_ID+"&&AFFIRM_FLAG="+AFFIRM_FLAG+"&&AFFIRM_LEVEL="+AFFIRM_LEVEL+"&&EXP_INSIDE_NO="+EXP_INSIDE_NO);
	}

	if("updatePunishTransAffirmForm" == form){
		$form.attr("action","/ess/trans/approvePunishmentTrans?HR_AFFIRM_NO="+HR_AFFIRM_NO+"&&START_DATE="+START_DATE+"&&EMPID="+EMPID+"&&PERSON_ID="+PERSON_ID+"&&AFFIRM_FLAG="+AFFIRM_FLAG+"&&AFFIRM_LEVEL="+AFFIRM_LEVEL+"&&EXP_INSIDE_NO="+EXP_INSIDE_NO);
	}
	 	

    $.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				alertMsg.correct(data.message);
				$("#searchAffirmForm").submit();
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

function pageFromSea(a){                         
	if('${tag}'==1){	
		var seach_KEY=$("#seach_KEY",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_KEY",navTab.getCurrentPanel()).val();
		var seach_DEPT_NO=$("#seach_DEPT_NO",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_DEPT_NO",navTab.getCurrentPanel()).val();
		var searchTransferOrderAffirm=$("#searchTransferOrderAffirm",navTab.getCurrentPanel()).val()==undefined?"":$("#searchTransferOrderAffirm",navTab.getCurrentPanel()).val();
		var seach_REWARD_FROM_DATE=$("#seach_REWARD_FROM_DATE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_REWARD_FROM_DATE",navTab.getCurrentPanel()).val();
		var seach_REWARD_TO_DATE=$("#seach_REWARD_TO_DATE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_REWARD_TO_DATE",navTab.getCurrentPanel()).val();
		var seach_STATUS_CODE=$("#seach_STATUS_CODE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_STATUS_CODE",navTab.getCurrentPanel()).val();
		var seach_TRANS_CODE=$("#seach_TRANS_CODE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_TRANS_CODE",navTab.getCurrentPanel()).val();
		
		$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ess/trans/viewRewardAndPunishmentTransAffirmList?seach_KEY="+seach_KEY+"&seach_DEPT_NO="+seach_DEPT_NO+"&searchTransferOrderAffirm="+searchTransferOrderAffirm+"&seach_REWARD_FROM_DATE="+seach_REWARD_FROM_DATE+"&seach_REWARD_TO_DATE="+seach_REWARD_TO_DATE+"&seach_STATUS_CODE="+seach_STATUS_CODE+"&seach_TRANS_CODE="+seach_TRANS_CODE);
	}
	if('${tag}'==2){
		var seach_KEY=$("#seach_KEY",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_KEY",navTab.getCurrentPanel()).val();
		var seach_DEPT_NO=$("#seach_DEPT_NO",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_DEPT_NO",navTab.getCurrentPanel()).val();
		var searchTransferOrderAffirm=$("#searchTransferOrderAffirm",navTab.getCurrentPanel()).val()==undefined?"":$("#searchTransferOrderAffirm",navTab.getCurrentPanel()).val();
		var seach_DATE_FROM_PUNISHED=$("#seach_DATE_FROM_PUNISHED",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_DATE_FROM_PUNISHED",navTab.getCurrentPanel()).val();
		var seach_DATE_TO_PUNISHED=$("#seach_DATE_TO_PUNISHED",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_DATE_TO_PUNISHED",navTab.getCurrentPanel()).val();
		var seach_STATUS_CODE=$("#seach_STATUS_CODE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_STATUS_CODE",navTab.getCurrentPanel()).val();
		var seach_TRANS_CODE=$("#seach_TRANS_CODE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_TRANS_CODE",navTab.getCurrentPanel()).val();
		
		$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ess/trans/viewRewardAndPunishmentTransAffirmList?seach_KEY="+seach_KEY+"&seach_DEPT_NO="+seach_DEPT_NO+"&searchTransferOrderAffirm="+searchTransferOrderAffirm+"&seach_DATE_FROM_PUNISHED="+seach_DATE_FROM_PUNISHED+"&seach_DATE_TO_PUNISHED="+seach_DATE_TO_PUNISHED+"&seach_STATUS_CODE="+seach_STATUS_CODE+"&seach_TRANS_CODE="+seach_TRANS_CODE);
	}
}
</script>
<a id="affirm_emp" name="affirm_emp"  href="/hrm/empinfo/viewEmpIdList?pageNum=1" lookupGroup="person" width="950"></a>
<%--
<div class="pageContent">
	<div class="panel">
		<h1></h1>
		<div>
			<form id="searchAffirmForm" onsubmit="return navTabSearch(this);" action="/ess/trans/viewRewardAndPunishmentTransAffirmList" method="post">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					height="20" class="user_table">
					<tr>
						<td class="td_type" width="6%">
							调令类型 : 
							<select id="searchTransferOrderAffirm" name="searchTransferOrderAffirm">
								<option value="-1">
									<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!--请选择-->
								</option>
								<option value="1" <c:if test="${tag eq '1' }">selected</c:if> >奖励</option>
								<option value="2" <c:if test="${tag eq '2' }">selected</c:if> >惩罚</option>
							</select>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
--%>
<!-- 奖励 -->
<%--<c:if test="${tag eq '1'}">--%>
	<div class="pageHeader">
		<form onsubmit="return navTabSearch(this);" action="/ess/trans/viewRewardAndPunishmentTransAffirmList" method="post"
		      rel="pagerForm" name="searchAffirmForm" id="searchAffirmForm">
		    <%--<input type="hidden" name="transferOrderAffirmType" value="reward"/>
			<input type="hidden" name="searchTransferOrderAffirm" value="1"/>--%>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							<spring:message code="heran.ess.viewHrExperienceInsideSave.DIAOLINGLEIXING"/> : 
							<select id="searchTransferOrderAffirm" name="searchTransferOrderAffirm">
								<option value="-1">
									<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/>
								</option>
								<option value="1" <c:if test="${tag eq '1' }">selected</c:if> ><spring:message code="hr.viewReward.title.REWARD"/><!-- 奖励--></option>
								<option value="2" <c:if test="${tag eq '2' }">selected</c:if> ><spring:message code="hr.viewReward.title.PUNISH"/><!-- 惩戒--></option>
							</select>
						</td>
						<c:if test="${tag eq '1' || tag eq '2' }">
							<td>
								 <spring:message code="public.title.deptName"/><!--部门-->:
								 <ait:deptTree name="seach_DEPT_NO" limit="hr" selected="${DEPT_NO }"/>
							</td>
							<td>
								<spring:message code="public.title.empIdAndName"/><!-- 工号/姓名 -->：
								<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}" />
							</td>
			                <td>
                            <%--
								<div class="subBar">
			                         <div class="buttonActive"><div class="buttonContent"><button type="submit">
			                         <spring:message code="public.title.search"/><!-- 检索 --></button></div></div>
			                    </div>	                
                            --%>
			                </td>
		                </c:if>				
					</tr>
				</table>
				<c:if test="${tag eq '1'}">
					<table class="searchContent">
						<tr>
			                <td>
			                    <spring:message code="public.title.startDate"/><!-- 开始日期 -->:
			                </td>			
						    <td>
						        <input type="text" id="seach_REWARD_FROM_DATE" name="seach_REWARD_FROM_DATE" class="date" format="yyyy-MM-dd" readonly="true" value="${REWARD_FROM_DATE }"/>
							    <a class="inputDateButton" href="javascript:;"></a>			   
						    </td>
			                <td>
			                    <spring:message code="public.title.endDate"/><!-- 结束日期 -->:
			                </td>			     
							<td>
							    <input type="text" id="seach_REWARD_TO_DATE" name="seach_REWARD_TO_DATE" class="date" format="yyyy-MM-dd" readonly="true" value="${REWARD_TO_DATE }"/>
							    <a class="inputDateButton" href="javascript:;"></a>
							</td> 
							<td>
								<spring:message code="ess.trans.title.affirmStatus"/><!-- 决裁状态 -->:
							</td>				
							<td>
                                 <ait:SelectSyCodeByCpnyID name="seach_STATUS_CODE" parentNo="3528" cnpyID="${defaultCpny}" selected="${STATUS_CODE}" limit="all"/>       
							<!-- 
                                 <ait:SelectSyCodeByCpnyID name="seach_ACTIVITY" parentNo="123489" cnpyID="${defaultCpny}" selected="${ACTIVITY}" limit="all"/>
                             -->
                            </td>
							<td>
								<spring:message code="ess.trans.title.rewardTypeName"/><!--奖励类型-->:
							</td>				
							<td>
							     <ait:SelectSyCodeByCpnyID name="seach_TRANS_CODE" parentNo="641" cnpyID="${defaultCpny}"  selected="${TRANS_CODE}" limit="all"/>       
							</td> 				 
						</tr>
					</table>				
				</c:if>
				<c:if test="${tag eq '2'}">
					<table class="searchContent">
						<tr>
			                <td>
			                    <spring:message code="public.title.startDate"/><!-- 开始日期 -->:
			                </td>			
						    <td>
						        <input type="text" id="seach_DATE_FROM_PUNISHED" name="seach_DATE_FROM_PUNISHED" class="date" format="yyyy-MM-dd" readonly="true" value="${DATE_FROM_PUNISHED }"/>
							    <a class="inputDateButton" href="javascript:;"></a>			   
						    </td>
			                <td>
			                     <spring:message code="public.title.endDate"/><!-- 结束日期 -->:
			                </td>			     
							<td>
							    <input type="text" id="seach_DATE_TO_PUNISHED" name="seach_DATE_TO_PUNISHED" class="date" format="yyyy-MM-dd" readonly="true" value="${DATE_TO_PUNISHED }"/>
							    <a class="inputDateButton" href="javascript:;"></a>
							</td> 
							<td>
								<spring:message code="ess.trans.title.affirmStatus"/><!-- 决裁状态 -->:
							</td>				
							<td>
                                 <ait:SelectSyCodeByCpnyID name="seach_STATUS_CODE" parentNo="3528" cnpyID="${defaultCpny}" selected="${STATUS_CODE}" limit="all"/>       
                                 <%-- 
                                 <ait:SelectSyCodeByCpnyID name="seach_ACTIVITY" parentNo="123489" cnpyID="${defaultCpny}" selected="${ACTIVITY}" limit="all"/>
                                  --%>
                            </td>
							<td>
								<spring:message code="ess.trans.title.punishTypeName"/><!--惩戒类型-->:
							</td>				
							<td>
							     <ait:SelectSyCodeByCpnyID name="seach_TRANS_CODE" parentNo="642" cnpyID="${defaultCpny}"  selected="${TRANS_CODE}" limit="all"/>       
							</td> 				 
						</tr>
					</table>
				</c:if>
			</div>
		
		<c:if test="${tag eq '1'}">
			<div class="formBar">
				<ul>
					<li>
						<div class="subBar">
                            <div class="buttonActive"><div class="buttonContent"><button type="submit">
                                <spring:message code="public.title.search"/><!-- 检索 --></button></div>
                            </div>
                            <div class="buttonActive">
								<a class="update" 
									onclick="validateRewardCallback('updateRewardTransAffirmForm',DWZ.ajaxDone,'1','${DEPT_NO}','${KEY }','${STATUS_CODE }','${TRANS_CODE }','${REWARD_FROM_DATE }','${REWARD_TO_DATE }')" href="#" >
									<span>
							        	<spring:message code="ess.trans.title.passInBatch"/><!--批量通过-->
							        </span>
							    </a>
	                        </div>
							<div class="buttonActive">
								<a class="update" 
									onclick="validateRewardCallback('updateRewardTransAffirmForm',DWZ.ajaxDone,'2','${DEPT_NO}','${KEY }','${STATUS_CODE }','${TRANS_CODE }','${REWARD_FROM_DATE }','${REWARD_TO_DATE }')" href="#" >
									<span>
							        	<spring:message code="ess.trans.title.rejectInBatch"/><!--批量否决-->
							        </span>
							   	</a>
	                    	</div>									
						</div>	
					</li>
				</ul>
			</div>		
		</c:if>
		<c:if test="${tag eq '2'}">
			<div class="formBar">
				<ul>
					<li>
						<div class="subBar">
                            <div class="buttonActive"><div class="buttonContent"><button type="submit">
                                <spring:message code="public.title.search"/><!-- 检索 --></button></div>
                            </div>
						    <div class="buttonActive">
						         <a class="update" onclick="approvePuValidateCallback('updatePunishTransAffirmForm',DWZ.ajaxDone,'1')" href="#" >
						         	<span>
						         		<spring:message code="ess.trans.title.passInBatch"/><!--批量通过-->
						         	</span>
						         </a>
	                        </div>
						    <div class="buttonActive">
						         <a class="update" onclick="approvePuValidateCallback('updatePunishTransAffirmForm',DWZ.ajaxDone,'2')" href="#" >
						         	<span>
						         		<spring:message code="ess.trans.title.rejectInBatch"/><!--批量否决-->
						        	</span>
						       	 </a>
	                    	</div>									
						</div>	
					</li>
				</ul>
			</div>		
		</c:if>
	</div>
	</form>
    	
<c:if test="${tag eq '1'}">
	<form name="updateRewardTransAffirmForm" id="updateRewardTransAffirmForm" method="post" 
	      action="/ess/trans/viewRewardTransAffirmList" class="pageForm required-validate"
		  onsubmit="return validateRewardCallback(this, navTabAjaxDone);">
	<input type="hidden" name="transferOrderAffirmType" value="reward"/>
	<input type="hidden" name="searchTransferOrderAffirm" value="1"/> 
	<div class="pageContent">      
		<table class="table" width="100%" layoutH="138" nowrapTD="false">
			<thead>
				<tr>
				    <th width="40"><input type="checkbox" class="checkboxCtrl" group="c1" /></th>
					<th width="100"><spring:message code="public.title.empId"/><!--工号--></th>
					<th width="100"><spring:message code="public.title.name"/><!--姓名--></th>
					<th width="100"><spring:message code="public.title.deptName"/><!-- 部门 --></th>
                    <%-- 
					<th width="100"><spring:message code="ess.trans.title.distinctDeptName"/><!--部门区分--></th>
                    --%>
					<th width="100"><spring:message code="public.title.positionName"/><!--职(岗)位--></th>
					<th width="160"><spring:message code="ess.trans.title.postName"/><!--职级名称(职务)--></th>
					<th width="100"><spring:message code="ess.trans.title.rewardDate"/><!--奖金日期--></th>
					<th width="100"><spring:message code="ess.trans.title.rewardTypeName"/><!--奖励类型--></th>
					<th width="100"><spring:message code="ess.trans.title.rewardContent"/><!--功绩内容--></th>	
					<th width="100"><spring:message code="ess.trans.title.rewardAmmount"/><!--奖励金额--></th>
					<th width="180"><spring:message code="ess.trans.title.affirmor"/><!--决裁者--></th>
					<th width="100"><spring:message code="ess.trans.title.affirmStatus"/><!--决裁状态--></th>
					<th width="100"><spring:message code="ess.trans.title.remark"/><!--备注--></th>									
				</tr>
			</thead>
	
			<tbody>
				<c:forEach items="${rewardTransList}" var="rewardTrans" varStatus="i">			
					<tr target="sid" rel="${rewardTrans.EXP_INSIDE_NO}">
					    <td class="td_center">
					        <input type="checkbox" id="c1" name="c1" value="${rewardTrans.EXP_INSIDE_NO}" />
					        <input type="hidden" id="${rewardTrans.EXP_INSIDE_NO}_AFFIRM_LEVEL" name = "${rewardTrans.EXP_INSIDE_NO}_AFFIRM_LEVEL" value="${rewardTrans.AFFIRM_LEVEL}" />                    
		                    <input type="hidden" id="${rewardTrans.EXP_INSIDE_NO}_HR_AFFIRM_NO" name = "${rewardTrans.EXP_INSIDE_NO}_HR_AFFIRM_NO" value="${rewardTrans.HR_AFFIRM_NO}" />                    
		                    <input type="hidden" id="${rewardTrans.EXP_INSIDE_NO}_AFFIRMOR_ID" name = "${rewardTrans.EXP_INSIDE_NO}_AFFIRMOR_ID" value="${rewardTrans.AFFIRMOR_ID}" />
							<input type="hidden" id="${rewardTrans.EXP_INSIDE_NO}_START_DATE" name = "${rewardTrans.EXP_INSIDE_NO}_START_DATE" value="${rewardTrans.REWARD_DATE}" />                                     
							<input type="hidden" id="${rewardTrans.EXP_INSIDE_NO}_EMPID" name = "${rewardTrans.EXP_INSIDE_NO}_EMPID" value="${rewardTrans.EMPID}" />   				        				        
					    </td>
						<td>${rewardTrans.EMPID}</td>
						<td>${rewardTrans.LOCAL_NAME}</td>
						<td>${rewardTrans.DEPT_NAME}</td>
                        <%--
						<td>${rewardTrans.DEPT_DISTINGUISH_NAME}</td>
						--%>
                        <td>${rewardTrans.POSITION_NAME}</td>
						<td>${rewardTrans.POST_NAME}</td>
						<td>${rewardTrans.REWARD_DATE}</td>
						<td>${rewardTrans.REWARD_TYPE_NAME}</td>
						<td>${rewardTrans.REWARD_CONTENTS}</td>
						<td style="text-align:right"><fmt:formatNumber value="${rewardTrans.REWARD_BONUS}" pattern="#,##0.00"/></td>
						<td>              
						    <c:forEach items="${rewardTrans.affirmerList}" var="affirmer" varStatus="i">					    					       					      
							        <dt style="padding: 1px;">
								        <c:if test="${affirmer.AFFIRM_FLAG==0 }" >
									        <span style="color:blue;">
				                              &nbsp;${affirmer.LOCAL_NAME}
				                            </span>
				                            <c:choose>
										        <c:when test="${affirmer.AFFIRMOR_ID == rewardTrans.CURRENT_AFFIRM_ID && affirmer.AFFIRM_LEVEL == rewardTrans.AFFIRM_LEVEL }">									
											         <c:if test="${affirmer.HREF_FLAG == 1}" >
														<a onclick="passOrCancalCallback('updateRewardTransAffirmForm',DWZ.ajaxDone,'${affirmer.HR_AFFIRM_NO}','${rewardTrans.START_DATE}','${rewardTrans.EMPID}','${rewardTrans.PERSON_ID}','1','${affirmer.AFFIRM_LEVEL}','${rewardTrans.EXP_INSIDE_NO}')" href="#">
								                           <span style="color:blue;">&nbsp;<spring:message code="ess.trans.title.pass"/><!--通过--></span> 
														</a>													    
														<a onclick="passOrCancalCallback('updateRewardTransAffirmForm',DWZ.ajaxDone,'${affirmer.HR_AFFIRM_NO}','${rewardTrans.START_DATE}','${rewardTrans.EMPID}','${rewardTrans.PERSON_ID}','2','${affirmer.AFFIRM_LEVEL}','${rewardTrans.EXP_INSIDE_NO}')" href="#">
								                           <span style="color:blue;">&nbsp;<spring:message code="ess.trans.title.reject"/><!--否决--></span> 
														</a>
												     </c:if>
												     <c:if test="${affirmer.HREF_FLAG != 1}" >
												       &nbsp;<spring:message code="ess.trans.title.notAffirmed"/><!--未决裁-->
												     </c:if>									
										        </c:when>									
										        <c:otherwise>									
										              &nbsp;<spring:message code="ess.trans.title.notAffirmed"/><!--未决裁-->						
										        </c:otherwise>									
										    </c:choose>				                            																	
										</c:if>						        
									</dt>
									<dt style="padding: 1px;">
										<c:if test="${affirmer.AFFIRM_FLAG==1}" >
											<span style="color:green;">&nbsp;${affirmer.LOCAL_NAME}</span>
										    <span style="color:green;">&nbsp;<spring:message code="ess.trans.title.pass"/><!--通过--></span>
										</c:if>	
									</dt>
									<dt style="padding: 1px;">
										<c:if test="${affirmer.AFFIRM_FLAG==2}" >
										    <span style="color:red;">&nbsp;${affirmer.LOCAL_NAME}</span>
										    <span style="color:red;">&nbsp;<spring:message code="ess.trans.title.reject"/><!--否决--></span>
										</c:if>								
									</dt>	
							</c:forEach> 							
						</td>
						<td>
						    <c:if test="${rewardTrans.AFFIRM_FLAG==0}" >
						        <span style="color:red;"><spring:message code="ess.trans.title.notAffirmed"/><!--未决裁--></span>
						    </c:if>
						    <c:if test="${rewardTrans.AFFIRM_FLAG!=0 }" >
						        <span style="color:green;"><spring:message code="ess.trans.title.affirmed"/><!--已决裁--></span>
						    </c:if>                    
						</td>
						<td>${rewardTrans.REWARD_CONTENTS}</td>											
					</tr>			
				</c:forEach>			
			</tbody>
		</table>	
	</form>
	<c:set value="/ess/trans/viewRewardAndPunishmentTransAffirmList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	</div>	
</c:if>
<!-- 惩戒 -->
<%--<c:if test="${tag eq '2'}">--%>
<%--
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/trans/viewRewardAndPunishmentTransAffirmList" method="post"
	      rel="pagerForm" name="searchPunishAffirmForm" id="searchPunishAffirmForm">
	<input type="hidden" name="transferOrderAffirmType" value="punishment"/>
	<input type="hidden" name="searchTransferOrderAffirm" value="2"/> 
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					 <spring:message code="public.title.deptName"/><!--部门-->:
					 <ait:deptTree name="seach_DEPT_NO" limit="hr" selected="${DEPT_NO }"/>
				</td>
				<td>
					<spring:message code="public.title.empIdAndName"/><!-- 工号/姓名 -->：
					<input type="text" name="seach_KEY" value="${KEY}" />
				</td>
                <td>
					<div class="subBar">
                         <div class="buttonActive"><div class="buttonContent"><button type="submit">
                         <spring:message code="public.title.search"/><!-- 检索 --></button></div></div>
                    </div>	                
                </td>				
			</tr>
			</table>
		<table class="searchContent">
			<tr>
                <td>
                    <spring:message code="public.title.startDate"/><!-- 开始日期 -->:
                </td>			
			    <td>
			        <input type="text" name="seach_DATE_FROM_PUNISHED" class="date required" format="yyyy-MM-dd" readonly="true" value="${DATE_FROM_PUNISHED }"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td>
                     <spring:message code="public.title.endDate"/><!-- 结束日期 -->:
                </td>			     
				<td>
				    <input type="text" name="seach_DATE_TO_PUNISHED" class="date required" format="yyyy-MM-dd" readonly="true" value="${DATE_TO_PUNISHED }"/>
				    <a class="inputDateButton" href="javascript:;"></a>
				</td> 
				<td>
					<spring:message code="ess.trans.title.affirmStatus"/><!-- 决裁状态 -->:
				</td>				
				<td>
				     <ait:SelectSyCodeByCpnyID name="seach_STATUS_CODE" parentNo="3528" cnpyID="${defaultCpny}" selected="${STATUS_CODE}" limit="all"/>       
				</td>
				<td>
					<spring:message code="ess.trans.title.punishTypeName"/><!--惩戒类型-->:
				</td>				
				<td>
				     <ait:SelectSyCodeByCpnyID name="seach_TRANS_CODE" parentNo="642" cnpyID="${defaultCpny}"  selected="${TRANS_CODE}" limit="all"/>       
				</td> 				 
			</tr>
		</table>
	</div>
	</form>
		<div class="formBar">
			<tr>
				<ul>
					<li>
						<div class="subBar">
						    <div class="buttonActive">
						         <a class="update" onclick="approvePuValidateCallback('updatePunishTransAffirmForm',DWZ.ajaxDone,'1')" href="#" >
						         	<span>
						         		<spring:message code="ess.trans.title.passInBatch"/><!--批量通过-->
						         	</span>
						         </a>
	                        </div>
						    <div class="buttonActive">
						         <a class="update" onclick="approvePuValidateCallback('updatePunishTransAffirmForm',DWZ.ajaxDone,'2')" href="#" >
						         	<span>
						         		<spring:message code="ess.trans.title.rejectInBatch"/><!--批量否决-->
						        	</span>
						       	 </a>
	                    	</div>									
						</div>	
					</li>
				</ul>
			</tr>
		</div>
	</div>
--%>
<c:if test="${tag eq '2'}">
	<div class="pageContent">	
	<form name="updatePunishTransAffirmForm" id="updatePunishTransAffirmForm" method="post" 
	      action="/ess/trans/approvePunishmentTransInBatch" class="pageForm required-validate"
		  onsubmit="return approvePuValidateCallback(this, navTabAjaxDone);">	
		<input type="hidden" name="transferOrderAffirmType" value="punishment"/>
		<input type="hidden" name="searchTransferOrderAffirm" value="2"/> 
		<table class="table" width="100%" layoutH="150" nowrapTD="false">
			<thead>
				<tr>
				    <th width="40"><input type="checkbox" class="checkboxCtrl" group="c1" /></th>
					<th width="80"><spring:message code="public.title.empId"/><!--工号--></th>
					<th width="80"><spring:message code="public.title.name"/><!--姓名--></th>
					<th width="120"><spring:message code="public.title.deptName"/><!-- 部门 --></th>
                    <%--
					<th width="120"><spring:message code="ess.trans.title.distinctDeptName"/><!--部门区分--></th>
					--%>
                    <th width="140"><spring:message code="public.title.positionName"/><!--职(岗)位--></th>
					<th width="140"><spring:message code="ess.trans.title.postName"/><!--职级名称(职务)--></th>
					<th width="120"><spring:message code="ess.trans.title.punishDate"/><!--惩戒日期--></th>
					<th width="120"><spring:message code="ess.trans.title.punishTypeName"/><!--惩戒类型--></th>
					<th width="140"><spring:message code="ess.trans.title.rewardContent"/><!--具体内容--></th>	
					<th width="80"><spring:message code="ess.trans.title.punishAmount"/><!--惩戒金额--></th>
					<th width="200"><spring:message code="ess.trans.title.affirmor"/><!--决裁者--></th>
					<th width="120"><spring:message code="ess.trans.title.affirmStatus"/><!--决裁状态--></th>
					<th width="120"><spring:message code="ess.trans.title.remark"/><!--备注--></th>									
				</tr>
			</thead>
		
			<tbody>
				<c:forEach items="${punishmentTransList}" var="punishmentTrans" varStatus="i">			
					<tr target="sid" rel="${punishmentTrans.EXP_INSIDE_NO}">
					    <td class="td_center">
					        <c:if test="${punishmentTrans.ACTIVITY eq '0'}">
                                <c:forEach items="${punishmentTrans.affirmerList}" var="affirmer" varStatus="i">
                                    <c:if test="${affirmer.AFFIRMOR_ID eq defaultPersonId }" >
                                            <c:if test="${affirmer.AFFIRM_FLAG == 0}" >
                                                 <input type="checkbox" id="c1" name="c1" value="${punishmentTrans.EXP_INSIDE_NO}" />
                                            </c:if>
                                            <c:if test="${affirmer.AFFIRM_FLAG == 1}" >
                                                 <input type="checkbox" id="c1" name="c1" value="${punishmentTrans.EXP_INSIDE_NO}" style="display:none" />
                                            </c:if>
                                            <c:if test="${affirmer.AFFIRM_FLAG == 2}" >
                                                <input type="checkbox" id="c1" name="c1" value="${punishmentTrans.EXP_INSIDE_NO}" style="display:none" />
                                            </c:if>
                                            <c:if test="${affirmer.AFFIRM_FLAG == 3}" >
                                                <input type="checkbox" id="c1" name="c1" value="${punishmentTrans.EXP_INSIDE_NO}" style="display:none" />
                                            </c:if>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                            <c:if test="${punishmentTrans.ACTIVITY eq '1'}">
                                <input type="checkbox" id="c1" name="c1" value="${punishmentTrans.EXP_INSIDE_NO}" style="display:none" />
                            </c:if>
                            <c:if test="${punishmentTrans.ACTIVITY eq '2'}">
                                <input type="checkbox" id="c1" name="c1" value="${punishmentTrans.EXP_INSIDE_NO}" style="display:none" />
                            </c:if>
                            <c:if test="${punishmentTrans.ACTIVITY eq '3'}">
                                <input type="checkbox" id="c1" name="c1" value="${punishmentTrans.EXP_INSIDE_NO}" style="display:none" />
                            </c:if>
                            <!-- 
                            <input type="checkbox" id="c1" name="c1" value="${punishmentTrans.EXP_INSIDE_NO}" />
                            -->
					        <input type="hidden" id="${punishmentTrans.EXP_INSIDE_NO}_AFFIRM_LEVEL" name = "${punishmentTrans.EXP_INSIDE_NO}_AFFIRM_LEVEL" value="${punishmentTrans.AFFIRM_LEVEL}" />                    
		                    <input type="hidden" id="${punishmentTrans.EXP_INSIDE_NO}_HR_AFFIRM_NO" name = "${punishmentTrans.EXP_INSIDE_NO}_HR_AFFIRM_NO" value="${punishmentTrans.HR_AFFIRM_NO}" />                    
		                    <input type="hidden" id="${punishmentTrans.EXP_INSIDE_NO}_AFFIRMOR_ID" name = "${punishmentTrans.EXP_INSIDE_NO}_AFFIRMOR_ID" value="${punishmentTrans.AFFIRMOR_ID}" />
							<input type="hidden" id="${punishmentTrans.EXP_INSIDE_NO}_START_DATE" name = "${punishmentTrans.EXP_INSIDE_NO}_START_DATE" value="${punishmentTrans.DATE_PUNISHED}" />                                     
							<input type="hidden" id="${punishmentTrans.EXP_INSIDE_NO}_EMPID" name = "${punishmentTrans.EXP_INSIDE_NO}_EMPID" value="${punishmentTrans.EMPID}" />   				        				        				        
					    </td>
						<td>${punishmentTrans.EMPID}</td>
						<td>${punishmentTrans.LOCAL_NAME}</td>
						<td>${punishmentTrans.DEPT_NAME}</td>
                        <%--
						<td>${punishmentTrans.DEPT_DISTINGUISH_NAME}</td>
						 --%>
                        <td>${punishmentTrans.POSITION_NAME}</td>
						<td>${punishmentTrans.POST_NAME}</td>
						<td>${punishmentTrans.DATE_PUNISHED}</td>
						<td>${punishmentTrans.PUNISHMENT_NAME}</td>
						<td>${punishmentTrans.PUN_REASON}</td>
						<td style="text-align:right"><fmt:formatNumber value="${punishmentTrans.PUN_BONUS}" pattern="#,##0.00"/></td>
						<td>              
						    <c:forEach items="${punishmentTrans.affirmerList}" var="affirmer" varStatus="i">					    					       					      
						       <dt style="padding: 1px;">
							        <c:if test="${affirmer.AFFIRM_FLAG==0 }" >
								        <span style="color:blue;">
			                              &nbsp;${affirmer.LOCAL_NAME}
			                            </span>
			                            <c:choose>
									        <c:when test="${affirmer.AFFIRMOR_ID == punishmentTrans.CURRENT_AFFIRM_ID && affirmer.AFFIRM_LEVEL == punishmentTrans.AFFIRM_LEVEL }">									
					                             <c:if test="${affirmer.HREF_FLAG == 1}" >
					                             <%--
												    <a href="/ess/trans/approvePunishmentTrans?HR_AFFIRM_NO=${affirmer.HR_AFFIRM_NO}&&START_DATE=${punishmentTrans.DATE_PUNISHED}&&EMPID=${punishmentTrans.EMPID}&&PERSON_ID=${punishmentTrans.PERSON_ID}&&AFFIRM_FLAG=1&&AFFIRM_LEVEL=${affirmer.AFFIRM_LEVEL}&&EXP_INSIDE_NO=${punishmentTrans.EXP_INSIDE_NO}" target="ajaxTodo">
							                           <span style="color:blue;">&nbsp;<spring:message code="ess.trans.title.pass"/><!--通过--></span>
													</a>
												    <a href="/ess/trans/approvePunishmentTrans?HR_AFFIRM_NO=${affirmer.HR_AFFIRM_NO}&&START_DATE=${punishmentTrans.DATE_PUNISHED}&&EMPID=${punishmentTrans.EMPID}&&PERSON_ID=${punishmentTrans.PERSON_ID}&&AFFIRM_FLAG=2&&AFFIRM_LEVEL=${affirmer.AFFIRM_LEVEL}&&EXP_INSIDE_NO=${punishmentTrans.EXP_INSIDE_NO}" target="ajaxTodo">
							                           <span style="color:blue;">&nbsp;<spring:message code="ess.trans.title.reject"/><!--否决--></span> 
													</a>
												 --%>
													<a onclick="passOrCancalCallback('updatePunishTransAffirmForm',DWZ.ajaxDone,'${affirmer.HR_AFFIRM_NO}','${punishmentTrans.DATE_PUNISHED}','${punishmentTrans.EMPID}','${punishmentTrans.PERSON_ID}','1','${affirmer.AFFIRM_LEVEL}','${punishmentTrans.EXP_INSIDE_NO}')" href="#">
							                           <span style="color:blue;">&nbsp;<spring:message code="ess.trans.title.pass"/><!--通过--></span> 
													</a>													    
													<a onclick="passOrCancalCallback('updatePunishTransAffirmForm',DWZ.ajaxDone,'${affirmer.HR_AFFIRM_NO}','${punishmentTrans.DATE_PUNISHED}','${punishmentTrans.EMPID}','${punishmentTrans.PERSON_ID}','2','${affirmer.AFFIRM_LEVEL}','${punishmentTrans.EXP_INSIDE_NO}')" href="#">
							                           <span style="color:blue;">&nbsp;<spring:message code="ess.trans.title.reject"/><!--否决--></span> 
													</a>
												 </c:if>
											     <c:if test="${affirmer.HREF_FLAG != 1}" >
											       &nbsp;<spring:message code="ess.trans.title.notAffirmed"/><!--未决裁-->
											     </c:if>									
									        </c:when>									
									        <c:otherwise>									
									               &nbsp;<spring:message code="ess.trans.title.notAffirmed"/><!--未决裁-->						
									        </c:otherwise>									
									    </c:choose>				                            																	
									</c:if>							        
								</dt>
								<dt style="padding: 1px;">
									<c:if test="${affirmer.AFFIRM_FLAG==1}" >
										<span style="color:green;">&nbsp;${affirmer.LOCAL_NAME}</span>
									    <span style="color:green;">&nbsp;<spring:message code="ess.trans.title.pass"/><!--通过--></span>
									</c:if>	
								</dt>
								<dt style="padding: 1px;">
									<c:if test="${affirmer.AFFIRM_FLAG==2}" >
									    <span style="color:red;">&nbsp;${affirmer.LOCAL_NAME}</span>
									    <span style="color:red;">&nbsp;<spring:message code="ess.trans.title.reject"/><!--否决--></span>
									</c:if>								
								</dt>	
							</c:forEach>
						</td>
						<td>
                        <%--
						    <c:if test="${punishmentTrans.AFFIRM_FLAG==0}" >
						        <span style="color:red;"><spring:message code="ess.trans.title.notAffirmed"/><!--未决裁--></span>
						    </c:if>
						    <c:if test="${punishmentTrans.AFFIRM_FLAG!=0 }" >
						        <span style="color:green;"><spring:message code="ess.trans.title.affirmed"/><!--已决裁--></span>
						    </c:if>
                         --%>    
                            <c:if test="${punishmentTrans.ACTIVITY eq '0'}">
                                <c:forEach items="${punishmentTrans.affirmerList}" var="affirmer" varStatus="i">
                                    <c:if test="${affirmer.AFFIRMOR_ID eq defaultPersonId }" >
                                            <c:if test="${affirmer.AFFIRM_FLAG == 0}" >
                                                 <span style="color:red;"><spring:message code="ess.trans.title.notAffirmed"/><!--未决裁--></span>
                                            </c:if>
                                            <c:if test="${affirmer.AFFIRM_FLAG == 1}" >
                                                 <span style="color:green;"><spring:message code="ess.trans.title.affirmed"/><!--已决裁--></span>
                                            </c:if>
                                            <c:if test="${affirmer.AFFIRM_FLAG == 2}" >
                                                <span style="color:green;"><spring:message code="ess.trans.title.affirmed"/><!--已决裁--></span>
                                            </c:if>
                                            <c:if test="${affirmer.AFFIRM_FLAG == 3}" >
                                                <span style="color:green;"><spring:message code="ess.trans.title.affirmed"/><!--已决裁--></span>
                                            </c:if>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                            <c:if test="${punishmentTrans.ACTIVITY eq '1'}">
                                <span style="color:green;"><spring:message code="ess.trans.title.affirmed"/><!--已决裁--></span>
                            </c:if>
                            <c:if test="${punishmentTrans.ACTIVITY eq '2'}">
                                <span style="color:green;"><spring:message code="ess.trans.title.affirmed"/><!--已决裁--></span>
                            </c:if>
                            <c:if test="${punishmentTrans.ACTIVITY eq '3'}">
                                <span style="color:green;"><spring:message code="ess.trans.title.affirmed"/><!--已决裁--></span>
                            </c:if>
                                        
						</td>
						<td>${punishmentTrans.PROBATION_MARK}</td>												
					</tr>			
				</c:forEach>			
			</tbody>
		</table>	
	</form>
		<c:set value="/ess/trans/viewRewardAndPunishmentTransAffirmList" var="pageUrl"/>
		<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
	</div>
</c:if>
<div id="initialPagination" style="display:none;">
	<c:set value="/ess/trans/viewRewardAndPunishmentTransAffirmList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>
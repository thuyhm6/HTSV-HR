<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
<!--
function validateEvectionApplyConfirmCallback(form,callback,flag) {
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
		alertMsg.error('<spring:message code="alert.message.ess.humanConfirm.choosePersonToConfirmEvectionApply"/>');
		return false;
	}

    $form.attr("action","/ess/humanAffirm/confirmEvectionApplyInBatch?CONFIRM_FLAG="+flag);

	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("searchEvectionApplyConfirmForm");
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

function pageFromSea(a){                         
		
	var seach_KEY=$("#seach_KEY",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_KEY",navTab.getCurrentPanel()).val();
	var seach_DEPT_NO=$("#seach_DEPT_NO",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_DEPT_NO",navTab.getCurrentPanel()).val();
	var seach_LEAVE_FROM_TIME=$("#seach_LEAVE_FROM_TIME",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_LEAVE_FROM_TIME",navTab.getCurrentPanel()).val();
	var seach_LEAVE_TO_TIME=$("#seach_LEAVE_TO_TIME",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_LEAVE_TO_TIME",navTab.getCurrentPanel()).val();
	var seach_LEAVE_TYPE_CODE=$("#seach_LEAVE_TYPE_CODE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_LEAVE_TYPE_CODE",navTab.getCurrentPanel()).val();
	var seach_STATUS_CODE=$("#seach_STATUS_CODE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_STATUS_CODE",navTab.getCurrentPanel()).val();
	
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ess/humanAffirm/viewEvectionApplyConfirmList?seach_KEY="+seach_KEY+"&seach_DEPT_NO="+seach_DEPT_NO+"&seach_LEAVE_FROM_TIME="+seach_LEAVE_FROM_TIME+"&seach_LEAVE_TO_TIME="+seach_LEAVE_TO_TIME+"&seach_LEAVE_TYPE_CODE="+seach_LEAVE_TYPE_CODE+"&seach_STATUS_CODE="+seach_STATUS_CODE);
}
//-->
</script>
         
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/humanAffirm/viewEvectionApplyConfirmList" method="post" 
	      name="searchEvectionApplyConfirmForm" id="searchEvectionApplyConfirmForm" rel="pagerForm">
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
					<spring:message code="public.title.empIdAndName"/><!-- 工号/姓名 -->：
				</td>						
				<td>
					<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}" />
				</td>
                <td>
					<div class="subBar">
                            <div class="buttonActive"><div class="buttonContent"><button type="submit">
                                <spring:message code="public.title.search"/><!-- 检索 -->
                   </button></div></div>
                    </div>	                
                </td>				
			</tr>
			</table>
			<table class="searchContent">
			<tr>
                <td>
                   <spring:message code="public.title.startDate"/><!-- 开始时间 -->:
                </td>			
			    <td>
			        <input type="text" id="seach_LEAVE_FROM_TIME" name="seach_LEAVE_FROM_TIME" class="date required" format="yyyy-MM-dd" readonly="true" value="${LEAVE_FROM_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td>
                        <spring:message code="public.title.endDate"/><!-- 结束日期 -->:
                </td>			     
				<td>
				    <input type="text" id="seach_LEAVE_TO_TIME" name="seach_LEAVE_TO_TIME" class="date required" format="yyyy-MM-dd" readonly="true" value="${LEAVE_TO_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"></a>
				</td>
				<td>
					<spring:message code="ess.viewApply.title.evectionApplyType"/><!--出差类型-->:
				</td>				
				<td>
				     <ait:SelectSyCodeByCpnyID name="seach_LEAVE_TYPE_CODE" parentNo="18" cnpyID="${defaultCpny}"  selected="${LEAVE_TYPE_CODE}" limit="all"/>       
				</td> 				 
				<td>
					<spring:message code="ess.viewApply.title.affirmStatus"/><!--决裁状态-->:
				</td>				
				<td>
				     <ait:SelectSyCodeByCpnyID name="seach_STATUS_CODE" parentNo="3528" cnpyID="${defaultCpny}" selected="${STATUS_CODE}" limit="all"/>       
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
						         <a class="update" onclick="validateEvectionApplyConfirmCallback('updateEvectionApplyPersonAffirmForm',DWZ.ajaxDone,'1')" href="#" >
						         <span><spring:message code="ess.title.passInBatch"/><!--批量通过--></span></a>
                            </div>
						    <div class="buttonActive">
						         <a class="update" onclick="validateEvectionApplyConfirmCallback('updateEvectionApplyPersonAffirmForm',DWZ.ajaxDone,'2')" href="#" >
						         <span><spring:message code="ess.title.rejectInBatch"/><!--批量否决--></span></a>
                    </div>									
				</div>	
				</li>
		</tr>
	</div>
</div>

<div class="pageContent" > 
<form name="updateEvectionApplyPersonAffirmForm" id="updateEvectionApplyPersonAffirmForm" 
      method="post" class="pageForm required-validate" 
      action="/ess/humanAffirm/confirmEvectionApplyInBatch" 
	  onsubmit="return validateEvectionApplyConfirmCallback(this, navTabAjaxDone);">    
	<table class="table" width="100%" height="80%" layoutH="150" nowrapTD="false">
		<thead>
			<tr>
			    <th width="50"><input type="checkbox" class="checkboxCtrl" group="c1" /></th>
				<th width="80"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="80"><spring:message code="public.title.name"/><!--姓名--></th>
				<th width="120"><spring:message code="public.title.deptName"/><!--部门--></th>
				<th width="120"><spring:message code="public.title.positionName"/><!--职岗位--></th>
				<th width="140"><spring:message code="public.title.postName"/><!--职级名称（职务）--></th>
				<th width="160"><spring:message code="ess.viewApply.title.applyDate"/><!--申请日期--></th>		
				<th width="100"><spring:message code="ess.infoApply.title.evectionApplyType"/><!-- 出差类型 --></th>				
				<th width="180"><spring:message code="ess.viewApply.title.evectionShift"/><!-- 出差时段 --></th>						
				<th width="80"><spring:message code="ess.viewApply.title.length"/><!-- 长度 --></th>
				<th width="120"><spring:message code="ess.infoApply.title.evectionContent"/><!-- 出差内容 --></th>	
				<th width="200"><spring:message code="ess.viewApply.title.affirmCondition"/><!--决裁情况--></th>
				<th width="120"><spring:message code="ess.viewApply.title.humanAffirm"/><!--人事确认--></th>									
			</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${evectionApplyPersonList}" var="evectionApplyPerson" varStatus="i">			
				<tr target="sid">
				    <td>
				        <input type="checkbox" id="c1" name="c1" value="${evectionApplyPerson.APPLY_NO}" />
				    </td>
					<td>${evectionApplyPerson.EMPID}</td>
					<td>${evectionApplyPerson.LOCAL_NAME}</td>
					<td>${evectionApplyPerson.DEPT_NAME}</td>
					<td>${evectionApplyPerson.POSITION_NAME}</td>
					<td>${evectionApplyPerson.POST_NAME}</td>
					<td>${evectionApplyPerson.APPLY_TIME}</td>
					<td>${evectionApplyPerson.LEAVE_TYPE_NAME}</td>
					<td>
			    	    <dt style="padding: 1px;">
                            ${evectionApplyPerson.LEAVE_FROM_TIME}
						</dt>
			    	    <dt style="padding: 1px;">
                            ${evectionApplyPerson.LEAVE_TO_TIME}
						</dt>	
                    </td>
					<td><fmt:formatNumber value="${evectionApplyPerson.LEAVE_LENGTH}" pattern="#,##0.00"/></td>
					<td>${evectionApplyPerson.LEAVE_REASON}</td>
					<td>              
                        <c:forEach items="${evectionApplyPerson.affirmerList}" var="affirmer" varStatus="i">					  					           
					        <dt style="padding: 1px;">
					            ${affirmer.LOCAL_NAME}
						         <c:if test="${affirmer.AFFIRM_FLAG==1}" >			                            
			                        <span style="color:green;">&nbsp;<spring:message code="ess.viewApply.title.pass"/><!--通过--></span>  
			                     </c:if> 
			                     <c:if test="${affirmer.AFFIRM_FLAG==2}" >         
			                        <span style="color:red;">&nbsp;<spring:message code="ess.viewApply.title.reject"/><!--否决 --></span> 
								 </c:if>
			                     <c:if test="${affirmer.AFFIRM_FLAG==0}" >         
			                        <span style="color:blue;">&nbsp;<spring:message code="ess.viewApply.title.notAffirmed"/><!--未决裁 --></span> 
								 </c:if>								 
							</dt>
						</c:forEach>					    						
					</td>
					<td>
						<dt style="padding: 1px;">
							<c:if test="${evectionApplyPerson.ACTIVITY == 0}" >
							    <a href="/ess/humanAffirm/confirmEvectionApply?APPLY_NO=${evectionApplyPerson.APPLY_NO}&&PERSON_ID=${evectionApplyPerson.PERSON_ID}&&CONFIRM_FLAG=1" target="ajaxTodo">
		                           <span style="color:blue;">&nbsp;<spring:message code="ess.viewApply.title.pass"/><!--通过--></span>
								</a>
							    <a href="/ess/humanAffirm/confirmEvectionApply?APPLY_NO=${evectionApplyPerson.APPLY_NO}&&PERSON_ID=${evectionApplyPerson.PERSON_ID}&&CONFIRM_FLAG=2" target="ajaxTodo">
		                           <span style="color:blue;">&nbsp;<spring:message code="ess.viewApply.title.reject"/><!--否决 --></span> 
								</a>							
							</c:if>
							<c:if test="${evectionApplyPerson.ACTIVITY==1}" >
								<span style="color:green;">&nbsp;${evectionApplyPerson.LOCAL_NAME}</span>
							    <span style="color:green;">&nbsp;<spring:message code="ess.viewApply.title.pass"/><!--通过--></span>
							</c:if>	
							<c:if test="${evectionApplyPerson.ACTIVITY==2}" >
							    <span style="color:red;">&nbsp;${evectionApplyPerson.LOCAL_NAME}</span>
							    <span style="color:red;">&nbsp;<spring:message code="ess.viewApply.title.reject"/><!--否决 --></span>
							</c:if>								
						</dt>	 
				     </td>		                    														
				</tr>			
			</c:forEach>			
		</tbody>
	</table>	
</form>
	<c:set value="/ess/humanAffirm/viewEvectionApplyConfirmList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>	
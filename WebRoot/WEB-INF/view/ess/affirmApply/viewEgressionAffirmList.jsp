<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
<!--
function validateAffirmEgressionApplyCallback(form,callback,flag) {
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
	    alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
		return false;
	}

    $form.attr("action","/ess/affirmApply/approveEgressionApplyInBatch?AFFIRM_FLAG="+flag+"&AFFIRM_TYPE=LEAVE_AFFIRM");

	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("searchEgressionApplyAffirmForm");
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
//-->
</script>
         
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/affirmApply/viewEgressionAffirmList" method="post" 
	      name="searchEgressionApplyAffirmForm" id="searchEgressionApplyAffirmForm" rel="pagerForm">
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
			        <input type="text" name="seach_FROM_TIME" class="date required" format="yyyy-MM-dd" readonly="true" value="${FROM_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td>
                    <spring:message code="public.title.endDate"/><!-- 结束日期 -->:
                </td>			     
				<td>
				    <input type="text" name="seach_TO_TIME" class="date required" format="yyyy-MM-dd" readonly="true" value="${TO_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"></a>
				</td> 
				<td>
					<spring:message code="ess.viewApply.title.affirmStatus"/><!--决裁状态-->:
				</td>				
				<td>
				     <ait:SelectSyCodeByCpnyID name="seach_STATUS_CODE" parentNo="3528" cnpyID="${defaultCpny}" selected="${STATUS_CODE}" limit="all"/>       
				</td>
				<td>
					<spring:message code="ess.infoApply.title.egressionApplyType"/><!-- 外出类型 -->:
				</td>				
				<td>
				     <ait:SelectSyCodeByCpnyID name="seach_APPLY_TYPE_CODE" parentNo="16201" cnpyID="${defaultCpny}"  selected="${APPLY_TYPE_CODE}" limit="all"/>       
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
						         <a class="update" onclick="validateAffirmEgressionApplyCallback('updateEgressionApplyAffirmForm',DWZ.ajaxDone,'1')" href="#" >
						         <span><spring:message code="ess.title.passInBatch"/><!--批量通过--></span></a>
                            </div>
						    <div class="buttonActive">
						         <a class="update" onclick="validateAffirmEgressionApplyCallback('updateEgressionApplyAffirmForm',DWZ.ajaxDone,'2')" href="#" >
						         <span><spring:message code="ess.title.rejectInBatch"/><!--批量否决--></span></a>
                    </div>									
				</div>	
				</li>
		</tr>
	</div>
</div>

<div class="pageContent" > 
<form name="updateEgressionApplyAffirmForm" id="updateEgressionApplyAffirmForm" method="post" action="/ess/affirmApply/approveEgressionApplyInBatch" 
	  onsubmit="return validateAffirmEgressionApplyCallback(this, navTabAjaxDone);">    
	<table class="table" width="100%" height="80%" layoutH="150" nowrapTD="false">
		<thead>
			<tr>
			    <th width="60"><input type="checkbox" class="checkboxCtrl" group="c1" /></th>
				<th width="80"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="80"><spring:message code="public.title.name"/><!--姓名--></th>
				<th width="120"><spring:message code="public.title.deptName"/><!--部门--></th>
				<th width="120"><spring:message code="public.title.positionName"/><!--职岗位--></th>
				<th width="140"><spring:message code="public.title.postName"/><!--职级名称（职务）--></th>
				<th width="160"><spring:message code="ess.viewApply.title.applyDate"/><!--申请日期--></th>		
				<th width="100"><spring:message code="ess.infoApply.title.egressionApplyType"/><!-- 外出类型 --></th>				
				<th width="180"><spring:message code="ess.viewApply.title.egressionShift"/><!-- 外出时段 --></th>						
				<th width="80"><spring:message code="ess.viewApply.title.length"/><!-- 长度 --></th>
				<th width="120"><spring:message code="ess.infoApply.title.egressionContent"/><!-- 外出内容 --></th>	
				<th width="200"><spring:message code="ess.viewApply.title.affirmCondition"/><!--决裁情况--></th>
				<th width="80"><spring:message code="ess.affirmApply.title.remark"/><!--备注--></th>									
			</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${egressionApplyPersonList}" var="egressionApplyPerson" varStatus="i">			
				<tr target="sid">
				    <td>
				        <input type="checkbox" id="c1" name="c1" value="${egressionApplyPerson.ESS_AFFIRM_NO}" />
				    </td>
					<td>${egressionApplyPerson.EMPID}</td>
					<td>${egressionApplyPerson.LOCAL_NAME}</td>
					<td>${egressionApplyPerson.DEPT_NAME}</td>
					<td>${egressionApplyPerson.POSITION_NAME}</td>
					<td>${egressionApplyPerson.POST_NAME}</td>
					<td>${egressionApplyPerson.APPLY_TIME}</td>
					<td>${egressionApplyPerson.APPLY_TYPE_NAME}</td>
					<td>
			    	    <dt style="padding: 1px;">
                            ${egressionApplyPerson.LEAVE_FROM_TIME}
						</dt>
			    	    <dt style="padding: 1px;">
                            ${egressionApplyPerson.LEAVE_TO_TIME}
						</dt>	
                    </td>
					<td><fmt:formatNumber value="${egressionApplyPerson.LEAVE_LENGTH}" pattern="#,##0.00"/></td>
					<td>${egressionApplyPerson.LEAVE_REASON}</td>
					<td>              
					    <c:forEach items="${egressionApplyPerson.affirmerList}" var="affirmer" varStatus="i">					    					       					      
						        <dt style="padding: 1px;">
							        <c:if test="${affirmer.AFFIRM_FLAG==0 }" >
								        <span style="color:blue;">
			                            &nbsp;${affirmer.LOCAL_NAME}
			                            </span>
			                            <c:choose>
									        <c:when test="${affirmer.AFFIRMOR_ID == egressionApplyPerson.CURRENT_AFFIRM_ID && affirmer.AFFIRM_LEVEL == egressionApplyPerson.AFFIRM_LEVEL }">									
										         <c:if test="${affirmer.HREF_FLAG == 1}" >
												    <a href="/ess/affirmApply/approveEgressionApply?ESS_AFFIRM_NO=${affirmer.ESS_AFFIRM_NO}&&AFFIRM_FLAG=1&AFFIRM_TYPE=LEAVE_AFFIRM" target="ajaxTodo">
							                           <span style="color:blue;">&nbsp;<spring:message code="ess.viewApply.title.pass"/><!--通过--></span>
													</a>
												    <a href="/ess/affirmApply/approveEgressionApply?ESS_AFFIRM_NO=${affirmer.ESS_AFFIRM_NO}&&AFFIRM_FLAG=2&AFFIRM_TYPE=LEAVE_AFFIRM" target="ajaxTodo">
							                           <span style="color:blue;">&nbsp;<spring:message code="ess.viewApply.title.reject"/><!--否决 --></span> 
													</a>
											     </c:if>
											     <c:if test="${affirmer.HREF_FLAG != 1}" >
											       &nbsp;<spring:message code="ess.viewApply.title.notAffirmed"/><!--未决裁-->
											     </c:if>									
									        </c:when>									
									        <c:otherwise>									
									              &nbsp;<spring:message code="ess.viewApply.title.notAffirmed"/><!--未决裁-->						
									        </c:otherwise>									
									    </c:choose>				                            																	
									</c:if>						        
								</dt>
								<dt style="padding: 1px;">
									<c:if test="${affirmer.AFFIRM_FLAG==1}" >
										<span style="color:green;">&nbsp;${affirmer.LOCAL_NAME}</span>
									    <span style="color:green;">&nbsp;<spring:message code="ess.viewApply.title.pass"/><!--通过--></span>
									</c:if>	
								</dt>
								<dt style="padding: 1px;">
									<c:if test="${affirmer.AFFIRM_FLAG==2}" >
									    <span style="color:red;">&nbsp;${affirmer.LOCAL_NAME}</span>
									    <span style="color:red;">&nbsp;<spring:message code="ess.viewApply.title.reject"/><!--否决 --></span>
									</c:if>								
								</dt>	
							</c:forEach> 							
					</td>
					<td>${egressionApplyPerson.REMARK}</td>		                    														
				</tr>			
			</c:forEach>			
		</tbody>
	</table>	
</form>
	<c:set value="/ess/affirmApply/viewEgressionAffirmList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>	
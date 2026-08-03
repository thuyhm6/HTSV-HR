<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
<!--
function validateAffirmLeaveApplyCallback_ess0228(form,callback,flag) {
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

    $form.attr("action","/ess/affirmApply/approveLeaveApplyInBatch?AFFIRM_FLAG="+flag+"&navTabId=ess0228");

	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("searchLeaveApplyAffirmForm_ess0228");
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
	var seach_START_DATE=$("#seach_START_DATE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_START_DATE",navTab.getCurrentPanel()).val();
	var seach_END_DATE=$("#seach_END_DATE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_END_DATE",navTab.getCurrentPanel()).val();
	var seach_STATUS_CODE=$("#seach_STATUS_CODE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_STATUS_CODE",navTab.getCurrentPanel()).val();
	//var seach_APPLY_TYPE_CODE=$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val();
	var seach_APPLY_TYPE_CODE="123646";
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ess/affirmApply/viewWorkingDaysAffirmList?seach_KEY="+seach_KEY+"&seach_DEPT_NO="+seach_DEPT_NO+"&seach_START_DATE="+seach_START_DATE+"&seach_END_DATE="+seach_END_DATE+"&seach_STATUS_CODE="+seach_STATUS_CODE+"&seach_APPLY_TYPE_CODE="+seach_APPLY_TYPE_CODE);
}

//-->
</script>
         
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/affirmApply/viewWorkingDaysAffirmList" method="post" 
	      name="searchLeaveApplyAffirmForm" id="searchLeaveApplyAffirmForm_ess0228" rel="pagerForm">
	<input type="hidden" id="menuNo" name="menuNo" value="124827"/>
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
                            <spring:message code="public.title.search"/><!-- 检索 --></button></div></div>
                    </div>	                
                </td>				
			</tr>
			</table>
			<table class="searchContent">
			<tr>
                <td>
                    <%--<spring:message code="public.title.startDate"/><!-- 开始日期 -->:
                --%>
					<spring:message code="heran.ess.viewLikeLeaveApplyInfo.yuanxiuxiri"/><!-- 原休息日-->:
                </td>			
			    <td><!--seach_FROM_TIME-->
			        <input type="text" id="seach_START_DATE" name="seach_START_DATE" class="date required" format="yyyy-MM-dd" readonly="true" value="${START_DATE}"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td><%--
                    <spring:message code="public.title.endDate"/><!-- 结束日期 -->:
                --%>
                	<spring:message code="heran.ess.viewLikeLeaveApplyInfo.tiaoxiuri"/><!-- 调休日 -->:
                </td>			     
				<td><!--seach_TO_TIME-->
				    <input type="text" id="seach_END_DATE" name="seach_END_DATE" class="date required" format="yyyy-MM-dd" readonly="true" value="${END_DATE}"/>
				    <a class="inputDateButton" href="javascript:;"></a>
				</td> 
				<%--<td>
					<spring:message code="ess.viewApply.title.affirmStatus"/><!-- 决裁状态 -->:
				</td>				
				<td>
				     <ait:SelectSyCodeByCpnyID name="seach_STATUS_CODE" parentNo="3528" cnpyID="${defaultCpny}" selected="${STATUS_CODE}" limit="all"/>       
				</td>
				--%><%--<td>
					<spring:message code="ess.viewApply.title.leaveApplyType"/><!-- 休假类型 -->:
				</td>				
				<td>
				     <ait:SelectSyCodeByCpnyID name="seach_APPLY_TYPE_CODE" parentNo="21" cnpyID="${defaultCpny}"  selected="${APPLY_TYPE_CODE}" limit="all"/>       
				</td> 				 
			--%></tr>
		</table>

	</div>
	</form>
			<div class="formBar">
			<tr>
				<ul>
				<li>
					<div class="subBar">
						    <div class="buttonActive">
						         <a class="update" onclick="validateAffirmLeaveApplyCallback_ess0228('updateLeaveApplyAffirmForm_ess0228',DWZ.ajaxDone,'1')" href="#" >
						         <span><spring:message code="ess.title.passInBatch"/><!--批量通过--></span></a>
                            </div>
						    <div class="buttonActive">
						         <a class="update" onclick="validateAffirmLeaveApplyCallback_ess0228('updateLeaveApplyAffirmForm_ess0228',DWZ.ajaxDone,'2')" href="#" >
						         <span><spring:message code="ess.title.rejectInBatch"/><!--批量否决--></span></a>
                    </div>									
				</div>	
				</li>
		</tr>
	</div>
</div>

<div class="pageContent" > 
<form name="updateLeaveApplyAffirmForm" id="updateLeaveApplyAffirmForm_ess0228" method="post" action="/ess/affirmApply/approveLeaveApplyInBatch" 
	  onsubmit="return validateAffirmLeaveApplyCallback_ess0228(this, navTabAjaxDone);">    
	<table class="table" width="100%" height="80%" layoutH="150" nowrapTD="false">
		<thead>
			<tr>
			    <th width="60"><input type="checkbox" class="checkboxCtrl" group="c1" /></th>
				<th width="80"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="80"><spring:message code="public.title.name"/><!--姓名--></th>
				<th width="120"><spring:message code="public.title.deptName"/><!--部门--></th>
				<th width="120"><spring:message code="public.title.positionName"/><!--职岗位--></th>
				<th width="140"><spring:message code="public.title.postName"/><!--职级名称（职务）--></th><%--
				<th width="120">申请时间</th>
				<th width="160">班次时段</th>				
				<th width="100"><spring:message code="ess.viewApply.title.leaveApplyType"/><!-- 休假类型 --></th>				
				<th width="160"><spring:message code="ess.viewApply.title.leaveShift"/><!--休假时段--></th>						
				<th width="80"><spring:message code="ess.viewApply.title.length"/><!--长度--></th>
				<th width="120"><spring:message code="ess.viewApply.title.leaveReason"/><!--休假原由--></th>	
				--%>
				<th width="160"><spring:message code="heran.ess.viewLikeLeaveApplyInfo.yuanxiuxiri"/><!--原休息日--></th>
				<th width="160"><spring:message code="heran.ess.viewLikeLeaveApplyInfo.tiaoxiuri"/><!--调休日--></th>
				<th width="120"><spring:message code="hr.viewCondSql.title.NEIRONG"/><!--内容--></th>
				<th width="200"><spring:message code="ess.viewApply.title.affirmCondition"/><!--决裁情况--></th>
				<th width="80"><spring:message code="ess.affirmApply.title.remark"/><!--备注--></th>									
			</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${leaveApplyAffirmList}" var="leaveApplyAffirm" varStatus="i">			
				<tr target="sid">
				    <td>
				        <input type="checkbox" id="c1" name="c1" value="${leaveApplyAffirm.ESS_AFFIRM_NO}" />
				    </td>
					<td>${leaveApplyAffirm.EMPID}</td>
					<td>${leaveApplyAffirm.LOCAL_NAME}</td>
					<td>${leaveApplyAffirm.DEPT_NAME}</td>
					<td>${leaveApplyAffirm.POSITION_NAME}</td>
					<td>${leaveApplyAffirm.POST_NAME}</td>
					<%--<td>${leaveApplyAffirm.APPLY_TIME}</td>
					<td>
			    	    <dt style="padding: 1px;">
                            ${leaveApplyAffirm.SHIFT_START_TIME}
						</dt>
			    	    <dt style="padding: 1px;">
                            ${leaveApplyAffirm.SHIFT_END_TIME}
						</dt>												
                    </td>
					<td>${leaveApplyAffirm.APPLY_TYPE_NAME}</td>
					<td>
			    	    <dt style="padding: 1px;">
                            ${leaveApplyAffirm.LEAVE_FROM_TIME}
						</dt>
			    	    <dt style="padding: 1px;">
                            ${leaveApplyAffirm.LEAVE_TO_TIME}
						</dt>												
                    </td>
					<td><fmt:formatNumber value="${leaveApplyAffirm.LEAVE_LENGTH}" pattern="#,##0.00"/></td>
					--%>
					<td>
						${fn:substring(leaveApplyAffirm.OLD_DAY,0,10)}
					</td>
					<td>
						${fn:substring(leaveApplyAffirm.LEAVE_TO_TIME,0,10)}
					</td>
					<td>${leaveApplyAffirm.LEAVE_REASON}</td>
					<td>              
					    <c:forEach items="${leaveApplyAffirm.affirmerList}" var="affirmer" varStatus="i">					    					       					      
						        <dt style="padding: 1px;">
							        <c:if test="${affirmer.AFFIRM_FLAG==0 }" >
								        <span style="color:blue;">
			                            &nbsp;${affirmer.LOCAL_NAME}
			                            </span>
			                            <c:choose>
									        <c:when test="${affirmer.AFFIRMOR_ID == leaveApplyAffirm.CURRENT_AFFIRM_ID && affirmer.AFFIRM_LEVEL == leaveApplyAffirm.AFFIRM_LEVEL }">									
										         <c:if test="${affirmer.HREF_FLAG == 1}" >
												    <a href="/ess/affirmApply/approveLeaveApply?ESS_AFFIRM_NO=${affirmer.ESS_AFFIRM_NO}&&AFFIRM_FLAG=1&navTabId=ess0228" target="ajaxTodo">
							                           <span style="color:blue;">&nbsp;<spring:message code="ess.viewApply.title.pass"/><!--通过--></span>
													</a>
												    <a href="/ess/affirmApply/approveLeaveApply?ESS_AFFIRM_NO=${affirmer.ESS_AFFIRM_NO}&&AFFIRM_FLAG=2&navTabId=ess0228" target="ajaxTodo">
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
					<td>${leaveApplyAffirm.REMARK}</td>		                    														
				</tr>			
			</c:forEach>			
		</tbody>
	</table>	
</form>
	<c:set value="/ess/affirmApply/viewWorkingDaysAffirmList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>	
<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
<!--
function validateAffirmOvertimeApplyCallback(form,callback,flag) {
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

    $form.attr("action","/ess/affirmApply/approveOvertimeApplyInBatch?AFFIRM_FLAG="+flag+"&AFFIRM_TYPE=OT_AFFIRM");

	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("searchPlurilityAffirmForm");
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
	var seach_FROM_TIME=$("#seach_FROM_TIME",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_FROM_TIME",navTab.getCurrentPanel()).val();
	var seach_TO_TIME=$("#seach_TO_TIME",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_TO_TIME",navTab.getCurrentPanel()).val();
	var seach_STATUS_CODE=$("#seach_STATUS_CODE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_STATUS_CODE",navTab.getCurrentPanel()).val();
	var seach_APPLY_TYPE_CODE=$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val();
	
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ess/affirmApply/viewOvertimeAffirmList?seach_KEY="+seach_KEY+"&seach_DEPT_NO="+seach_DEPT_NO+"&seach_FROM_TIME="+seach_FROM_TIME+"&seach_TO_TIME="+seach_TO_TIME+"&seach_STATUS_CODE="+seach_STATUS_CODE+"&seach_APPLY_TYPE_CODE="+seach_APPLY_TYPE_CODE);
}
//-->
</script>
         
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/affirmApply/viewOvertimeAffirmList" method="post" 
	      rel="pagerForm" name="searchPlurilityAffirmForm" id="searchPlurilityAffirmForm">
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
                    <spring:message code="public.title.startDate"/><!-- 开始日期 -->:
                </td>			
			    <td>
			        <input type="text" id="seach_FROM_TIME" name="seach_FROM_TIME" class="date required" format="yyyy-MM-dd" readonly="true" value="${FROM_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td>
                     <spring:message code="public.title.endDate"/><!-- 结束日期 -->:
                </td>			     
				<td>
				    <input type="text" id="seach_TO_TIME" name="seach_TO_TIME" class="date required" format="yyyy-MM-dd" readonly="true" value="${TO_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"></a>
				</td> 
				<%--<td>
					<spring:message code="ess.viewApply.title.affirmStatus"/><!-- 决裁状态 -->:
				</td>				
				<td>
				     <ait:SelectSyCodeByCpnyID name="seach_STATUS_CODE" parentNo="3528" cnpyID="${defaultCpny}" selected="${STATUS_CODE}" limit="all"/>       
				</td>
				--%><td>
					<spring:message code="ess.viewApply.title.overtimeApplyType"/><!-- 加班类型 -->:
				</td>				
				<td>
				     <ait:SelectSyCodeByCpnyID name="seach_APPLY_TYPE_CODE" parentNo="31" cnpyID="${defaultCpny}"  selected="${APPLY_TYPE_CODE}" limit="all"/>       
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
						         <a class="update" onclick="validateAffirmOvertimeApplyCallback('updateOvertimeApplyAffirmForm',DWZ.ajaxDone,'1')" href="#" >
						         <span><spring:message code="ess.title.passInBatch"/><!--批量通过--></span></a>
                            </div>
						    <div class="buttonActive">
						         <a class="update" onclick="validateAffirmOvertimeApplyCallback('updateOvertimeApplyAffirmForm',DWZ.ajaxDone,'2')" href="#" >
						         <span><spring:message code="ess.title.rejectInBatch"/><!--批量否决--></span></a>
                    </div>									
				</div>	
				</li>
		</tr>
	</div>
</div>

<div class="pageContent" > 
<form name="updateOvertimeApplyAffirmForm" id="updateOvertimeApplyAffirmForm" method="post" action="/ess/affirmApply/approveOvertimeApplyInBatch" 
	  onsubmit="return validateAffirmOvertimeApplyCallback(this, navTabAjaxDone);">    
	<table class="table" width="100%" height="80%" layoutH="150" nowrapTD="false">
		<thead>
			<tr>
			    <th width="60" style="text-align: center">
			    	<input type="checkbox" class="checkboxCtrl" group="c1" />
			    </th>
				<th width="80" style="text-align: center"><!--工号-->
					<spring:message code="public.title.empId"/>
				</th>
				<th width="80" style="text-align: center"><!--姓名-->
					<spring:message code="public.title.name"/>
				</th>
				<th width="120" style="text-align: center"><!--部门-->
					<spring:message code="public.title.deptName"/>
				</th>
				<th width="120" style="text-align: center"><!--职岗位-->
					<spring:message code="public.title.positionName"/>
				</th>
				<th width="140" style="text-align: center"><!--职级名称（职务）-->
					<spring:message code="public.title.postName"/>
				</th>
				
				<th width="160" style="text-align: center"><!--申请日期-->
					<spring:message code="ess.viewApply.title.applyDate"/>
				</th>		
				<th width="100" style="text-align: center"><!-- 加班类型 -->
					<spring:message code="ess.viewApply.title.overtimeApplyType"/>
				</th>				
				<th width="180" style="text-align: center"><!--加班时段-->
					<spring:message code="ess.viewApply.title.overtimeShift"/>
				</th>
				<th width="80" style="text-align: center"><!--扣除时间-->
					<spring:message code="ar.viewshift.title.kouchushijian"/>
				</th>						
				<th width="80" style="text-align: center"><!--长度(小时)-->
					<spring:message code="ess.viewApply.title.lengthHough"/>
				</th>
				
				<th width="120" style="text-align: center"><!--加班内容-->
					<spring:message code="ess.viewApply.title.overtimeContent"/>
				</th>
				<th width="200" style="text-align: center"><!--决裁情况-->
					<spring:message code="ess.viewApply.title.affirmCondition"/>
				</th><!--备注-->
				<th width="80" style="text-align: center">
					<spring:message code="ess.affirmApply.title.remark"/>
				</th>									
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ovetimeApplyPersonList}" var="ovetimeApplyPerson" varStatus="i">			
				<tr target="sid">
				    <td style="text-align: center">
				        <input type="checkbox" id="c1" name="c1" value="${ovetimeApplyPerson.ESS_AFFIRM_NO}" />
				    </td>
					<td style="text-align: center">${ovetimeApplyPerson.EMPID}</td>
					<td style="text-align: center">${ovetimeApplyPerson.LOCAL_NAME}</td>
					<td style="text-align: center">${ovetimeApplyPerson.DEPT_NAME}</td>
					<td style="text-align: center">${ovetimeApplyPerson.POSITION_NAME}</td>
					<td style="text-align: center">${ovetimeApplyPerson.POST_NAME}</td>
					<td style="text-align: center"><%--${ovetimeApplyPerson.APPLY_OT_DATE}--%>
						${fn:substring(ovetimeApplyPerson.CREATE_DATE,0,10)}
					</td>
					<td style="text-align: center">${ovetimeApplyPerson.APPLY_TYPE_NAME}</td>
					<td style="text-align: center">
			    	    <dt style="padding: 1px;">
                            ${ovetimeApplyPerson.OT_FROM_TIME}
						</dt>
			    	    <dt style="padding: 1px;">
                            ${ovetimeApplyPerson.OT_TO_TIME}
						</dt>						
					</td>
					<td style="text-align: center">${ovetimeApplyPerson.OT_DEDUCT_TIME}</td>
					<td style="text-align: center"><fmt:formatNumber value="${ovetimeApplyPerson.OT_LENGTH }" pattern="#,##0.00"/></td>
					<td style="text-align: center">${ovetimeApplyPerson.APPLY_OT_REMARK}</td>
					<td style="text-align: center">              
					    <c:forEach items="${ovetimeApplyPerson.affirmerList}" var="affirmer" varStatus="i">					    					       					      
						        <dt style="padding: 1px;">
							        <c:if test="${affirmer.AFFIRM_FLAG==0 }" >
								        <span style="color:blue;">
			                            &nbsp;${affirmer.LOCAL_NAME}
			                            </span>
			                            <c:choose>
									        <c:when test="${affirmer.AFFIRMOR_ID == ovetimeApplyPerson.CURRENT_AFFIRM_ID && affirmer.AFFIRM_LEVEL == ovetimeApplyPerson.AFFIRM_LEVEL }">									
										         <c:if test="${affirmer.HREF_FLAG == 1}" >
												    <a href="/ess/affirmApply/approveOvertimeApply?ESS_AFFIRM_NO=${affirmer.ESS_AFFIRM_NO}&&AFFIRM_FLAG=1&AFFIRM_TYPE=OT_AFFIRM" target="ajaxTodo">
							                           <span style="color:blue;">&nbsp;<spring:message code="ess.viewApply.title.pass"/><!--通过--></span>
													</a>
												    <a href="/ess/affirmApply/approveOvertimeApply?ESS_AFFIRM_NO=${affirmer.ESS_AFFIRM_NO}&&AFFIRM_FLAG=2&AFFIRM_TYPE=OT_AFFIRM" target="ajaxTodo">
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
					<td style="text-align: center">${ovetimeApplyPerson.APPLY_OT_REMARK}</td>		                    														
				</tr>			
			</c:forEach>			
		</tbody>
	</table>	
</form>
	<c:set value="/ess/affirmApply/viewOvertimeAffirmList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>	
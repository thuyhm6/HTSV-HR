<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
<!--
function validateApproveProbationCallback(form,callback,flag) {
	
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

    $form.attr("action","/ess/trans/approveProbationTransInBatch?AFFIRM_FLAG="+flag);
	
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("searchProbationAffirmForm");
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
	<form onsubmit="return navTabSearch(this);" action="/ess/trans/viewProbationTransAffirmList" method="post" 
	      rel="pagerForm" name="searchProbationAffirmForm" id="searchProbationAffirmForm">
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
			        <input type="text" name="seach_PROBATION_DATE_FROM" class="date required" format="yyyy-MM-dd" readonly="true" value="${PROBATION_DATE_FROM }"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td>
                     <spring:message code="public.title.endDate"/><!-- 结束日期 -->:
                </td>			     
				<td>
				    <input type="text" name="seach_PROBATION_DATE_TO" class="date required" format="yyyy-MM-dd" readonly="true" value="${PROBATION_DATE_TO }"/>
				    <a class="inputDateButton" href="javascript:;"></a>
				</td> 
				<td>
					<spring:message code="ess.trans.title.affirmStatus"/><!-- 决裁状态 -->:
				</td>				
				<td>
				    <ait:SelectSyCodeByCpnyID name="seach_STATUS_CODE" parentNo="3528" cnpyID="${defaultCpny}" selected="${STATUS_CODE}" limit="all"/>       
				</td>
				<td>
					<spring:message code="ess.trans.title.probationTypeName"/><!--转正类型-->:
				</td>				
				<td>
				    <ait:SelectSyCodeByCpnyID name="seach_TRANS_CODE" parentNo="1360" cnpyID="${defaultCpny}"  selected="${TRANS_CODE}" limit="all"/>       
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
						         <a class="update" onclick="validateApproveProbationCallback('updateProbationTransAffirmForm',DWZ.ajaxDone,'1')" href="#" ><span>
						         <spring:message code="ess.trans.title.passInBatch"/><!--批量通过--></span></a>
                            </div>
						    <div class="buttonActive">
						         <a class="update" onclick="validateApproveProbationCallback('updateProbationTransAffirmForm',DWZ.ajaxDone,'2')" href="#" ><span>
						         <spring:message code="ess.trans.title.rejectInBatch"/><!--批量否决--></span></a>
                    </div>									
				</div>	
				</li>
		</tr>
	</div>
</div>
<div class="pageContent"> 
<form name="updateProbationTransAffirmForm" id="updateProbationTransAffirmForm" method="post" 
      class="pageForm required-validate" action="/ess/trans/approveProbationTransInBatch"
	  onsubmit="return validateApproveProbationCallback(this, navTabAjaxDone);">	       
	<table class="table" width="100%" layoutH="150" nowrapTD="false">
		<thead>
			<tr>
			    <th width="40"><input type="checkbox" class="checkboxCtrl" group="c1" /></th>
				<th width="80"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="80"><spring:message code="public.title.name"/><!--姓名--></th>
				<th width="120"><spring:message code="public.title.deptName"/><!-- 部门 --></th>
				<th width="120"><spring:message code="ess.trans.title.distinctDeptName"/><!--部门区分--></th>
				<th width="140"><spring:message code="public.title.positionName"/><!--职(岗)位--></th>
				<th width="140"><spring:message code="ess.trans.title.postName"/><!--职级名称(职务)--></th>
				<th width="120"><spring:message code="ess.trans.title.beforeEndProbationDate"/><!--预转正日期--></th>
				<th width="80"><spring:message code="ess.trans.title.employeeStatus"/><!--员工状态--></th>
				<th width="120"><spring:message code="ess.trans.title.probationTypeName"/><!--转正类型--></th>
				<th width="120"><spring:message code="ess.trans.title.effectiveDate"/><!--生效日期--></th>	
				<th width="200"><spring:message code="ess.trans.title.affirmor"/><!--决裁者--></th>
				<th width="100"><spring:message code="ess.trans.title.affirmStatus"/><!--决裁状态--></th>
				<th width="100"><spring:message code="ess.trans.title.remark"/><!--备注--></th>									
			</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${probationList}" var="probation" varStatus="i">			
				<tr target="sid" rel="${probation.PERSON_ID}">
				    <td>
				        <input type="checkbox" id="c1" name="c1" value="${probation.EXP_INSIDE_NO}" />
				        <input type="hidden" id="${probation.EXP_INSIDE_NO}_AFFIRM_LEVEL" name = "${probation.EXP_INSIDE_NO}_AFFIRM_LEVEL" value="${probation.AFFIRM_LEVEL}" />                    
	                    <input type="hidden" id="${probation.EXP_INSIDE_NO}_HR_AFFIRM_NO" name = "${probation.EXP_INSIDE_NO}_HR_AFFIRM_NO" value="${probation.HR_AFFIRM_NO}" />                    
	                    <input type="hidden" id="${probation.EXP_INSIDE_NO}_AFFIRMOR_ID" name = "${probation.EXP_INSIDE_NO}_AFFIRMOR_ID" value="${probation.AFFIRMOR_ID}" />  
						<input type="hidden" id="${probation.EXP_INSIDE_NO}_START_DATE" name = "${probation.EXP_INSIDE_NO}_START_DATE" value="${probation.START_DATE}" />                                     
						<input type="hidden" id="${probation.EXP_INSIDE_NO}_EMPID" name = "${probation.EXP_INSIDE_NO}_EMPID" value="${probation.EMPID}" />   
						<input type="hidden" id="${probation.EXP_INSIDE_NO}_EMP_TYPE_CODE" name = "${probation.EXP_INSIDE_NO}_EMP_TYPE_CODE" value="${probation.EMP_TYPE_CODE}" /> 
						<input type="hidden" id="${probation.EXP_INSIDE_NO}_PERSON_ID" name = "${probation.EXP_INSIDE_NO}_PERSON_ID" value="${probation.PERSON_ID}" />
						<input type="hidden" id="${probation.EXP_INSIDE_NO}_TRANS_CODE" name = "${probation.EXP_INSIDE_NO}_TRANS_CODE" value="${probation.TRANS_CODE}" />     					
					</td>	
					<td>${probation.EMPID}</td>
					<td>${probation.LOCAL_NAME}</td>
					<td>${probation.DEPT_NAME}</td>
					<td>${probation.DEPT_DISTINGUISH_NAME}</td>
					<td>${probation.POSITION_NAME}</td>
					<td>${probation.POST_NAME}</td>
					<td>${probation.PROBATION_DATE}</td>
					<td>${probation.STATUS_NAME}</td>
					<td>${probation.PRO_TYPE_NAME}</td>
					<td>${probation.START_DATE}</td>
					<td>              
					    <c:forEach items="${probation.affirmerList}" var="affirmer" varStatus="i">					    					       					      
						        <dt style="padding: 1px;">
							        <c:if test="${affirmer.AFFIRM_FLAG==0 }" >
								        <span style="color:blue;">
			                            &nbsp;${affirmer.LOCAL_NAME}
			                            </span>
			                            <c:choose>
									        <c:when test="${affirmer.AFFIRMOR_ID == probation.CURRENT_AFFIRM_ID && affirmer.AFFIRM_LEVEL == probation.AFFIRM_LEVEL }">									
										         <c:if test="${affirmer.HREF_FLAG == 1}" >
												    <a href="/ess/trans/approveProbationTrans?HR_AFFIRM_NO=${affirmer.HR_AFFIRM_NO}&&START_DATE=${probation.START_DATE}&&EMPID=${probation.EMPID}&&PERSON_ID=${probation.PERSON_ID}&&AFFIRM_FLAG=1&&AFFIRM_LEVEL=${affirmer.AFFIRM_LEVEL}&&EXP_INSIDE_NO=${probation.EXP_INSIDE_NO}&&EMP_TYPE_CODE=${probation.EMP_TYPE_CODE}&&TRANS_CODE=${probation.TRANS_CODE}" target="ajaxTodo">
							                           <span style="color:blue;">&nbsp;<spring:message code="ess.trans.title.pass"/><!--通过--></span>
													</a>
												    <a href="/ess/trans/approveProbationTrans?HR_AFFIRM_NO=${affirmer.HR_AFFIRM_NO}&&START_DATE=${probation.START_DATE}&&EMPID=${probation.EMPID}&&PERSON_ID=${probation.PERSON_ID}&&AFFIRM_FLAG=2&&AFFIRM_LEVEL=${affirmer.AFFIRM_LEVEL}&&EXP_INSIDE_NO=${probation.EXP_INSIDE_NO}&&EMP_TYPE_CODE=${probation.EMP_TYPE_CODE}&&TRANS_CODE=${probation.TRANS_CODE}" target="ajaxTodo">
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
					    <c:if test="${probation.AFFIRM_FLAG==0}" >
					        <span style="color:red;"><spring:message code="ess.trans.title.notAffirmed"/><!--未决裁--></span>
					    </c:if>
					    <c:if test="${probation.AFFIRM_FLAG!=0 }" >
					        <span style="color:green;"><spring:message code="ess.trans.title.affirmed"/><!--已决裁--></span>
					    </c:if>                    
					</td>
					<td>${probation.PROBATION_MARK}</td>											
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
	</form>		
	<c:set value="/ess/trans/viewProbationTransAffirmList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>

</div>
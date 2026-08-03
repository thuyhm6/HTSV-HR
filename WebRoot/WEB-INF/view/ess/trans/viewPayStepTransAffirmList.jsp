<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
<!--
function validateApproveProReleCallback(form,callback,flag) {
	
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

    $form.attr("action","/ess/trans/approvePayStepTransInBatch?AFFIRM_FLAG="+flag);
	
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("searchProRelAffirmForm");
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
	<form onsubmit="return navTabSearch(this);" action="/ess/trans/viewPayStepTransAffirmList" method="post" 
	      rel="pagerForm" name="searchProRelAffirmForm" id="searchProRelAffirmForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					  <spring:message code="public.title.deptName"/><!-- 部门 -->:
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
			        <input type="text" name="seach_FROM_TIME" class="date required" format="yyyy-MM-dd" readonly="true" value="${FROM_TIME }"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td>
                    <spring:message code="public.title.endDate"/><!-- 结束日期 -->:
                </td>			     
				<td>
				    <input type="text" name="seach_TO_TIME" class="date required" format="yyyy-MM-dd" readonly="true" value="${TO_TIME }"/>
				    <a class="inputDateButton" href="javascript:;"></a>
				</td> 
				<td>
					<spring:message code="ess.trans.title.affirmStatus"/><!-- 决裁状态 -->:
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
						         <a class="update" onclick="validateApproveProReleCallback('updateProRelTransAffirmForm',DWZ.ajaxDone,'1')" href="#" ><span>
						         <spring:message code="ess.trans.title.passInBatch"/><!--批量通过--></span></a>
                            </div>
						    <div class="buttonActive">
						         <a class="update" onclick="validateApproveProReleCallback('updateProRelTransAffirmForm',DWZ.ajaxDone,'2')" href="#" ><span>
						         <spring:message code="ess.trans.title.rejectInBatch"/><!--批量否决--></span></a>
                    </div>									
				</div>	
				</li>
		</tr>
	</div>
</div>	

	  
<div class="pageContent">
<form name="updateProRelTransAffirmForm" id="updateProRelTransAffirmForm" method="post" 
      action="/ess/trans/approvePayStepTransInBatch" class="pageForm required-validate"
	  onsubmit="return validateApproveProReleCallback(this,navTabAjaxDone);">
 	<table class="table" width="120%" layoutH="150" nowrapTD="false">      
		<thead>
			<tr>
			    <th width="40"><input type="checkbox" class="checkboxCtrl" group="c1" /></th>
				<th width="80"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="80"><spring:message code="public.title.name"/><!--姓名--></th>
				<th width="100"><spring:message code="public.title.deptName"/><!--部门--></th>
				<th width="80"><spring:message code="public.title.positionName"/><!--职(岗)位--></th>
				<th width="140"><spring:message code="ess.trans.title.postName"/><!--职级名称(职务)--></th>
			    <th width="60"><spring:message code="ess.trans.title.postGradeName"/><!--原职级--></th>
				<th width="80">原号俸</th>
				<th width="140">原(薪资)职级,号俸</th>
				<th width="80">新号俸</th>
				<th width="140">新(薪资)职级,号俸</th>
				<th width="200"><spring:message code="ess.trans.title.affirmor"/><!--决裁者--></th>
				<th width="80"><spring:message code="ess.trans.title.affirmStatus"/><!--决裁状态--></th>	
				<th width="80"><spring:message code="ess.trans.title.remark"/><!--备注--></th>									
			</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${payStepList}" var="payStep" varStatus="i">			
				<tr target="sid" rel="${payStep.EXP_INSIDE_NO}">
				    <td>
				        <input type="checkbox" id="c1" name="c1" value="${payStep.EXP_INSIDE_NO}" />
			            <input type="hidden" id="${payStep.EXP_INSIDE_NO}_AFFIRM_LEVEL" name = "${payStep.EXP_INSIDE_NO}_AFFIRM_LEVEL" value="${payStep.AFFIRM_LEVEL}" />                    
                        <input type="hidden" id="${payStep.EXP_INSIDE_NO}_HR_AFFIRM_NO" name = "${payStep.EXP_INSIDE_NO}_HR_AFFIRM_NO" value="${payStep.HR_AFFIRM_NO}" />                    
                        <input type="hidden" id="${payStep.EXP_INSIDE_NO}_AFFIRMOR_ID" name = "${payStep.EXP_INSIDE_NO}_AFFIRMOR_ID" value="${payStep.AFFIRMOR_ID}" />                                     
						<input type="hidden" id="${payStep.EXP_INSIDE_NO}_START_DATE" name = "${payStep.EXP_INSIDE_NO}_START_DATE" value="${payStep.START_DATE}" />                                     
						<input type="hidden" id="${payStep.EXP_INSIDE_NO}_EMPID" name = "${payStep.EXP_INSIDE_NO}_EMPID" value="${payStep.EMPID}" />    				        
                        <input type="hidden" id="${payStep.EXP_INSIDE_NO}_PERSON_ID" name = "${payStep.EXP_INSIDE_NO}_PERSON_ID" value="${payStep.PERSON_ID}" />
                        <input type="hidden" id="${payStep.EXP_INSIDE_NO}_PAY_STEP_NO" name = "${payStep.EXP_INSIDE_NO}_PAY_STEP_NO" value="${payStep.NEW_PAY_STEP_NO}" /> 
                        <input type="hidden" id="${payStep.EXP_INSIDE_NO}_OLD_POST_GRADE_NO" name = "${payStep.EXP_INSIDE_NO}_OLD_POST_GRADE_NO" value="${payStep.NEW_OLD_POST_GRADE_NO}" />                                      
				    </td>
					<td>${payStep.EMPID}</td>
					<td>${payStep.LOCAL_NAME}</td>
					<td>${payStep.DEPT_NAME}</td>
					<td>${payStep.POSITION_NAME}</td>
					<td>${payStep.POST_NAME}</td>
					<td>${payStep.POST_GRADE_NAME}</td>
					<td>${payStep.PAY_STEP_NAME}</td>
					<td>${payStep.OLD_POST_GRADE_NAME}</td>
					<td>${payStep.NEW_PAY_STEP_NAME}</td>
					<td>${payStep.NEW_OLD_POST_GRADE_NAME}</td>
					<td>              
					    <c:forEach items="${payStep.affirmerList}" var="affirmer" varStatus="i">					    					       					      
						        <dt style="padding: 1px;">
							        <c:if test="${affirmer.AFFIRM_FLAG==0 }" >
								        <span style="color:blue;">
			                            &nbsp;${affirmer.LOCAL_NAME}
			                            </span>
			                            <c:choose>
									        <c:when test="${affirmer.AFFIRMOR_ID == payStep.CURRENT_AFFIRM_ID && affirmer.AFFIRM_LEVEL == payStep.AFFIRM_LEVEL }">									
										         <c:if test="${affirmer.HREF_FLAG == 1}" >
												    <a href="/ess/trans/approvePayStepTrans?HR_AFFIRM_NO=${affirmer.HR_AFFIRM_NO}&&START_DATE=${payStep.START_DATE}&&EMPID=${payStep.EMPID}&&PERSON_ID=${payStep.PERSON_ID}&&AFFIRM_FLAG=1&&AFFIRM_LEVEL=${affirmer.AFFIRM_LEVEL}&&EXP_INSIDE_NO=${payStep.EXP_INSIDE_NO}&&PAY_STEP_NO=${payStep.NEW_PAY_STEP_NO}&&OLD_POST_GRADE_NO=${payStep.NEW_OLD_POST_GRADE_NO}" target="ajaxTodo">
							                           <span style="color:blue;">&nbsp;<spring:message code="ess.trans.title.pass"/><!--通过--></span>
													</a>
												    <a href="/ess/trans/approvePayStepTrans?HR_AFFIRM_NO=${affirmer.HR_AFFIRM_NO}&&START_DATE=${payStep.START_DATE}&&EMPID=${payStep.EMPID}&&PERSON_ID=${payStep.PERSON_ID}&&AFFIRM_FLAG=2&&AFFIRM_LEVEL=${affirmer.AFFIRM_LEVEL}&&EXP_INSIDE_NO=${payStep.EXP_INSIDE_NO}&&PAY_STEP_NO=${payStep.NEW_PAY_STEP_NO}&&OLD_POST_GRADE_NO=${payStep.NEW_OLD_POST_GRADE_NO}" target="ajaxTodo">
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
									    <span style="color:red;">${affirmer.LOCAL_NAME}</span>
									    <span style="color:red;">&nbsp;<spring:message code="ess.trans.title.reject"/><!--否决--></span>
									</c:if>								
								</dt>	
							</c:forEach> 							
					</td>
					<td>
					    <c:if test="${payStep.AFFIRM_FLAG==0}" >
					        <span style="color:red;"><spring:message code="ess.trans.title.notAffirmed"/><!--未决裁--></span>
					    </c:if>
					    <c:if test="${payStep.AFFIRM_FLAG!=0 }" >
					        <span style="color:green;"><spring:message code="ess.trans.title.affirmed"/><!--已决裁--></span>
					    </c:if>                    
					</td>
					<td>${payStep.PROBATION_MARK}</td>	
				</tr>			
			</c:forEach>			
		</tbody>	
	</table>
	</form>
	<c:set value="/ess/trans/viewPayStepTransAffirmList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>

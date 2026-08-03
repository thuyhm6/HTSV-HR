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

    $form.attr("action","/ess/trans/approvePromotRelegatTransInBatch?AFFIRM_FLAG="+flag);
	
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
	<form onsubmit="return navTabSearch(this);" action="/ess/trans/viewPromotRelegatTransAffirmList" method="post" 
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
				<td>
					<spring:message code="ess.trans.title.promAndRelTypeName"/><!--晋升/降级类型-->:
				</td>				
				<td>
				     <ait:SelectSyCodeByCpnyID name="seach_TRANS_CODE" parentNo="1363" cnpyID="${defaultCpny}"  selected="${TRANS_CODE}" limit="all"/>       
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
      action="/ess/trans/approvePromotRelegatTransInBatch" class="pageForm required-validate"
	  onsubmit="return validateApproveProReleCallback(this,navTabAjaxDone);">
 	<table class="table" width="120%" layoutH="150" nowrapTD="false">      
		<thead>
			<tr>
			    <th width="40"><input type="checkbox" class="checkboxCtrl" group="c1" /></th>
				<th width="80"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="80"><spring:message code="public.title.name"/><!--姓名--></th>
				<th width="100"><spring:message code="ess.trans.title.oldDeptName"/><!--原部门--></th>
				<th width="80"><spring:message code="ess.trans.title.oldPositionName"/><!--原职(岗)位--></th>
				<th width="140"><spring:message code="ess.trans.title.oldPostName"/><!--原职级名称(职务)--></th>
			    <th width="60"><spring:message code="ess.trans.title.oldPostGradeName"/><!--原职级--></th>
			    <th width="100"><spring:message code="ess.trans.title.oldWorkArea"/><!--原工作地--></th>
				<th width="100"><spring:message code="ess.trans.title.newDeptName"/><!--现部门--></th>
				<th width="100"><spring:message code="ess.trans.title.newDistinctName"/><!--现部门区分--></th>
				<th width="80"><spring:message code="ess.trans.title.newPositionName"/><!--现职(岗)位--></th>
				<th width="120"><spring:message code="ess.trans.title.newPostName"/><!--现职级名称(职务)--></th>
			    <th width="40"><spring:message code="ess.trans.title.newPostGradeName"/><!--现职级--></th>			    
				<th width="100"><spring:message code="ess.trans.title.newWorkArea"/><!--现工作地--></th>
				<th width="120"><spring:message code="ess.trans.title.promAndRelTypeName"/><!--晋升/降级类型--></th>	
				<th width="80"><spring:message code="ess.trans.title.viewFullInfo"/><!--详细查看--></th>	
				<th width="200"><spring:message code="ess.trans.title.affirmor"/><!--决裁者--></th>
				<th width="80"><spring:message code="ess.trans.title.affirmStatus"/><!--决裁状态--></th>	
				<th width="80"><spring:message code="ess.trans.title.remark"/><!--备注--></th>									
			</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${proReleTransList}" var="proReleTrans" varStatus="i">			
				<tr target="sid" rel="${proReleTrans.EXP_INSIDE_NO}">
				    <td>
				        <input type="checkbox" id="c1" name="c1" value="${proReleTrans.EXP_INSIDE_NO}" />
			            <input type="hidden" id="${proReleTrans.EXP_INSIDE_NO}_AFFIRM_LEVEL" name = "${proReleTrans.EXP_INSIDE_NO}_AFFIRM_LEVEL" value="${proReleTrans.AFFIRM_LEVEL}" />                    
                        <input type="hidden" id="${proReleTrans.EXP_INSIDE_NO}_HR_AFFIRM_NO" name = "${proReleTrans.EXP_INSIDE_NO}_HR_AFFIRM_NO" value="${proReleTrans.HR_AFFIRM_NO}" />                    
                        <input type="hidden" id="${proReleTrans.EXP_INSIDE_NO}_AFFIRMOR_ID" name = "${proReleTrans.EXP_INSIDE_NO}_AFFIRMOR_ID" value="${proReleTrans.AFFIRMOR_ID}" />                                     
						<input type="hidden" id="${proReleTrans.EXP_INSIDE_NO}_START_DATE" name = "${proReleTrans.EXP_INSIDE_NO}_START_DATE" value="${proReleTrans.START_DATE}" />                                     
						<input type="hidden" id="${proReleTrans.EXP_INSIDE_NO}_EMPID" name = "${proReleTrans.EXP_INSIDE_NO}_EMPID" value="${proReleTrans.EMPID}" />    				        
                        <input type="hidden" id="${proReleTrans.EXP_INSIDE_NO}_PERSON_ID" name = "${proReleTrans.EXP_INSIDE_NO}_PERSON_ID" value="${proReleTrans.PERSON_ID}" />                                     
				    </td>
					<td>${proReleTrans.EMPID}</td>
					<td>${proReleTrans.LOCAL_NAME}</td>
					<td>${proReleTrans.DEPT_NAME}</td>
					<td>${proReleTrans.POSITION_NAME}</td>
					<td>${proReleTrans.POST_NAME}</td>
					<td>${proReleTrans.POST_GRADE_NAME}</td>
					<td>${proReleTrans.WORK_AREA_NAME}</td>
					<td>${proReleTrans.NEW_DEPT_NAME}</td>
					<td>${proReleTrans.DEPT_DISTINGUISH_NAME}</td>
					<td>${proReleTrans.NEW_POSITION_NAME}</td>
					<td>${proReleTrans.NEW_POST_NAME}</td>
					<td>${proReleTrans.NEW_POST_GRADE_NAME}</td>
					<td>${proReleTrans.NEW_WAOK_AREA_NAME}</td>
					<td>${proReleTrans.TRANS_TYPE_NAME}</td>
					<td>
					    <a href="/hrm/searchTransferOrder/viewEmployeeHistoryList?EXP_INSIDE_NO=${proReleTrans.EXP_INSIDE_NO}&&PERSON_ID=${proReleTrans.PERSON_ID}" target="navTab">
                            <span><spring:message code="ess.trans.title.viewInfo"/><!--查看--></span>
						</a>
                    </td>
					<td>              
					    <c:forEach items="${proReleTrans.affirmerList}" var="affirmer" varStatus="i">					    					       					      
						        <dt style="padding: 1px;">
							        <c:if test="${affirmer.AFFIRM_FLAG==0 }" >
								        <span style="color:blue;">
			                            &nbsp;${affirmer.LOCAL_NAME}
			                            </span>
			                            <c:choose>
									        <c:when test="${affirmer.AFFIRMOR_ID == proReleTrans.CURRENT_AFFIRM_ID && affirmer.AFFIRM_LEVEL == proReleTrans.AFFIRM_LEVEL }">									
										         <c:if test="${affirmer.HREF_FLAG == 1}" >
												    <a href="/ess/trans/approvePromotRelegatTrans?HR_AFFIRM_NO=${affirmer.HR_AFFIRM_NO}&&START_DATE=${proReleTrans.START_DATE}&&EMPID=${proReleTrans.EMPID}&&PERSON_ID=${proReleTrans.PERSON_ID}&&AFFIRM_FLAG=1&&AFFIRM_LEVEL=${affirmer.AFFIRM_LEVEL}&&EXP_INSIDE_NO=${proReleTrans.EXP_INSIDE_NO}" target="ajaxTodo">
							                           <span style="color:blue;">&nbsp;<spring:message code="ess.trans.title.pass"/><!--通过--></span>
													</a>
												    <a href="/ess/trans/approvePromotRelegatTrans?HR_AFFIRM_NO=${affirmer.HR_AFFIRM_NO}&&START_DATE=${proReleTrans.START_DATE}&&EMPID=${proReleTrans.EMPID}&&PERSON_ID=${proReleTrans.PERSON_ID}&&AFFIRM_FLAG=2&&AFFIRM_LEVEL=${affirmer.AFFIRM_LEVEL}&&EXP_INSIDE_NO=${proReleTrans.EXP_INSIDE_NO}" target="ajaxTodo">
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
					    <c:if test="${proReleTrans.AFFIRM_FLAG==0}" >
					        <span style="color:red;"><spring:message code="ess.trans.title.notAffirmed"/><!--未决裁--></span>
					    </c:if>
					    <c:if test="${proReleTrans.AFFIRM_FLAG!=0 }" >
					        <span style="color:green;"><spring:message code="ess.trans.title.affirmed"/><!--已决裁--></span>
					    </c:if>                    
					</td>
					<td>${proReleTrans.PROBATION_MARK}</td>	
				</tr>			
			</c:forEach>			
		</tbody>	
	</table>
	</form>
	<c:set value="/ess/trans/viewPromotRelegatTransAffirmList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>

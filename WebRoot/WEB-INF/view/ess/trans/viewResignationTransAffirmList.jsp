<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
<!--
function validateApproveResignCallback(form,callback,flag) {
	
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

    $form.attr("action","/ess/trans/approveResignationTransInBatch?AFFIRM_FLAG="+flag);
	
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("searchResignAffirmForm");
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
	<form onsubmit="return navTabSearch(this);" action="/ess/trans/viewResignationTransAffirmList" method="post"
	      rel="pagerForm" name="searchResignAffirmForm" id="searchResignAffirmForm">
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
			        <input type="text" name="seach_RESIGN_DATE_FROM" class="date required" format="yyyy-MM-dd" readonly="true" value="${RESIGN_DATE_FROM}"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td>
                     <spring:message code="public.title.endDate"/><!-- 结束日期 -->:
                </td>			     
				<td>
				    <input type="text" name="seach_RESIGN_DATE_TO" class="date required" format="yyyy-MM-dd" readonly="true" value="${RESIGN_DATE_TO}"/>
				    <a class="inputDateButton" href="javascript:;"></a>
				</td> 
				<td>
					<spring:message code="ess.trans.title.affirmStatus"/><!-- 决裁状态 -->:
				</td>				
				<td>
				    <ait:SelectSyCodeByCpnyID name="seach_STATUS_CODE" parentNo="3528" cnpyID="${defaultCpny}" selected="${STATUS_CODE}" limit="all"/>       
				</td>
				<td>
					<spring:message code="ess.trans.title.resignTypeName"/><!--离职类型-->:
				</td>				
				<td>
				    <ait:SelectSyCodeByCpnyID name="seach_TRANS_CODE" parentNo="643" cnpyID="${defaultCpny}"  selected="${TRANS_CODE}" limit="all"/>       
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
						         <a class="update" onclick="validateApproveResignCallback('updateResignTransAffirmForm',DWZ.ajaxDone,'1')" href="#" ><span>
						         <spring:message code="ess.trans.title.passInBatch"/><!--批量通过--></span></a>
                            </div>
						    <div class="buttonActive">
						         <a class="update" onclick="validateApproveResignCallback('updateResignTransAffirmForm',DWZ.ajaxDone,'2')" href="#" ><span>
						         <spring:message code="ess.trans.title.rejectInBatch"/><!--批量否决--></span></a>
                    </div>									
				</div>	
				</li>
		</tr>
	</div>
</div>
	
<div class="pageContent">
<form name="updateResignTransAffirmForm" id="updateResignTransAffirmForm" method="post" 
      action="/ess/trans/approveResignationTransInBatch" class="pageForm required-validate"
	  onsubmit="return validateApproveResignCallback(this, navTabAjaxDone,'');">  
	<table class="table" width="100%" layoutH="150" nowrapTD="false">
		<thead>
			<tr>
			    <th width="40"><input type="checkbox" class="checkboxCtrl" group="c1" /></th>
				<th width="80"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="80"><spring:message code="public.title.name"/><!--姓名--></th>
				<th width="80"><spring:message code="public.title.deptName"/><!-- 部门 --></th>
				<th width="80"><spring:message code="ess.trans.title.distinctDeptName"/><!--部门区分--></th>
				<th width="80"><spring:message code="public.title.positionName"/><!--职(岗)位--></th>
				<th width="120"><spring:message code="ess.trans.title.postName"/><!--职级名称(职务)--></th>
				<th width="80"><spring:message code="ess.trans.title.resignDate"/><!--离职日期--></th>
				<th width="80"><spring:message code="ess.trans.title.salarySettlementDate"/><!--工资结算日--></th>
				<th width="80"><spring:message code="ess.trans.title.resignTypeName"/><!--离职类型--></th>
				<th width="80"><spring:message code="ess.trans.title.resignReason"/><!--离职原因--></th>	
				<th width="80"><spring:message code="ess.trans.title.viewFullInfo"/><!--详细查看--></th>
				<th width="200"><spring:message code="ess.trans.title.affirmor"/><!--决裁者--></th>
				<th width="80"><spring:message code="ess.trans.title.affirmStatus"/><!--决裁状态--></th>
				<th width="80"><spring:message code="ess.trans.title.remark"/><!--备注--></th>									
			</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${resignationTransList}" var="resignationTrans" varStatus="i">			
				<tr target="sid" rel="${resignationTrans.EXP_INSIDE_NO}">
				    <td>
				        <input type="checkbox" id="c1" name="c1" value="${resignationTrans.EXP_INSIDE_NO}" />
				        <input type="hidden" id="${resignationTrans.EXP_INSIDE_NO}_AFFIRM_LEVEL" name = "${resignationTrans.EXP_INSIDE_NO}_AFFIRM_LEVEL" value="${resignationTrans.AFFIRM_LEVEL}" />                    
	                    <input type="hidden" id="${resignationTrans.EXP_INSIDE_NO}_HR_AFFIRM_NO" name = "${resignationTrans.EXP_INSIDE_NO}_HR_AFFIRM_NO" value="${resignationTrans.HR_AFFIRM_NO}" />                    
	                    <input type="hidden" id="${resignationTrans.EXP_INSIDE_NO}_AFFIRMOR_ID" name = "${resignationTrans.EXP_INSIDE_NO}_AFFIRMOR_ID" value="${resignationTrans.AFFIRMOR_ID}" />
						<input type="hidden" id="${resignationTrans.EXP_INSIDE_NO}_START_DATE" name = "${resignationTrans.EXP_INSIDE_NO}_START_DATE" value="${resignationTrans.START_DATE}" />                                     
						<input type="hidden" id="${resignationTrans.EXP_INSIDE_NO}_EMPID" name = "${resignationTrans.EXP_INSIDE_NO}_EMPID" value="${resignationTrans.EMPID}" /> 
						<input type="hidden" id="${resignationTrans.EXP_INSIDE_NO}_PERSON_ID" name = "${resignationTrans.EXP_INSIDE_NO}_PERSON_ID" value="${resignationTrans.PERSON_ID}" />   				        
				    </td>
					<td>${resignationTrans.EMPID}</td>
					<td>${resignationTrans.LOCAL_NAME}</td>
					<td>${resignationTrans.DEPT_NAME}</td>
					<td>${resignationTrans.DEPT_DISTINGUISH_NAME}</td>
					<td>${resignationTrans.POSITION_NAME}</td>
					<td>${resignationTrans.POST_NAME}</td>
					<td>${resignationTrans.START_DATE}</td>
					<td>${resignationTrans.SETTLEMENT_DATE}</td>
					<td>${resignationTrans.RES_TYPE_NAME}</td>
					<td>${resignationTrans.REASON_CODE_NAME}</td>
					<td><a href=""></a></td>
					<td>              
					    <c:forEach items="${resignationTrans.affirmerList}" var="affirmer" varStatus="i">					    					       					      
						        <dt style="padding: 1px;">
							        <c:if test="${affirmer.AFFIRM_FLAG==0 }" >
								        <span style="color:blue;">
			                            &nbsp;${affirmer.LOCAL_NAME}
			                            </span>
			                            <c:choose>
									        <c:when test="${affirmer.AFFIRMOR_ID == resignationTrans.CURRENT_AFFIRM_ID && affirmer.AFFIRM_LEVEL == resignationTrans.AFFIRM_LEVEL }">									
										         <c:if test="${affirmer.HREF_FLAG == 1}" >
											        <a href="/ess/trans/approveResignationTrans?HR_AFFIRM_NO=${affirmer.HR_AFFIRM_NO}&&START_DATE=${resignationTrans.START_DATE}&&EMPID=${resignationTrans.EMPID}&&PERSON_ID=${resignationTrans.PERSON_ID}&&AFFIRM_FLAG=1&&AFFIRM_LEVEL=${affirmer.AFFIRM_LEVEL}&&EXP_INSIDE_NO=${resignationTrans.EXP_INSIDE_NO}" target="ajaxTodo">
						                            <span style="color:blue;">&nbsp;<spring:message code="ess.trans.title.pass"/><!--通过--></span>
											     	</a>
											        <a href="/ess/trans/approveResignationTrans?HR_AFFIRM_NO=${affirmer.HR_AFFIRM_NO}&&START_DATE=${resignationTrans.START_DATE}&&EMPID=${resignationTrans.EMPID}&&PERSON_ID=${resignationTrans.PERSON_ID}&&AFFIRM_FLAG=2&&AFFIRM_LEVEL=${affirmer.AFFIRM_LEVEL}&&EXP_INSIDE_NO=${resignationTrans.EXP_INSIDE_NO}" target="ajaxTodo">
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
					    <c:if test="${resignationTrans.AFFIRM_FLAG==0}" >
					        <span style="color:red;"><spring:message code="ess.trans.title.notAffirmed"/><!--未决裁--></span>
					    </c:if>
					    <c:if test="${resignationTrans.AFFIRM_FLAG!=0 }" >
					        <span style="color:green;"><spring:message code="ess.trans.title.affirmed"/><!--已决裁--></span>
					    </c:if>                    
					</td>
					<td>${resignationTrans.REMARK}</td>											
				</tr>			
			</c:forEach>			
		</tbody>
	</table>	
</form>
	<c:set value="/ess/trans/viewResignationTransAffirmList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>	
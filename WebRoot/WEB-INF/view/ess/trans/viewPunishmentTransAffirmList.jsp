<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script type="text/javascript">
<!--
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
				navTabSearch("searchPunishAffirmForm");
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
	<form onsubmit="return navTabSearch(this);" action="/ess/trans/viewPunishmentTransAffirmList" method="post"
	      rel="pagerForm" name="searchPunishAffirmForm" id="searchPunishAffirmForm">
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
						         <a class="update" onclick="approvePuValidateCallback('updatePunishTransAffirmForm',DWZ.ajaxDone,'1')" href="#" ><span>
						         <spring:message code="ess.trans.title.passInBatch"/><!--批量通过--></span></a>
                            </div>
						    <div class="buttonActive">
						         <a class="update" onclick="approvePuValidateCallback('updatePunishTransAffirmForm',DWZ.ajaxDone,'2')" href="#" ><span>
						         <spring:message code="ess.trans.title.rejectInBatch"/><!--批量否决--></span></a>
                    </div>									
				</div>	
				</li>
		</tr>
	</div>
</div>

<div class="pageContent">	
<form name="updatePunishTransAffirmForm" id="updatePunishTransAffirmForm" method="post" 
      action="/ess/trans/approvePunishmentTransInBatch" class="pageForm required-validate"
	  onsubmit="return approvePuValidateCallback(this, navTabAjaxDone);">	       
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
				<th width="120"><spring:message code="ess.trans.title.punishDate"/><!--惩戒日期--></th>
				<th width="120"><spring:message code="ess.trans.title.punishTypeName"/><!--惩戒类型--></th>
				<th width="140"><spring:message code="ess.trans.title.punishReason"/><!--惩戒事由--></th>	
				<th width="80"><spring:message code="ess.trans.title.punishAmount"/><!--惩戒金额--></th>
				<th width="200"><spring:message code="ess.trans.title.affirmor"/><!--决裁者--></th>
				<th width="120"><spring:message code="ess.trans.title.affirmStatus"/><!--决裁状态--></th>
				<th width="120"><spring:message code="ess.trans.title.remark"/><!--备注--></th>									
			</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${punishmentTransList}" var="punishmentTrans" varStatus="i">			
				<tr target="sid" rel="${punishmentTrans.EXP_INSIDE_NO}">
				    <td>
				        <input type="checkbox" id="c1" name="c1" value="${punishmentTrans.EXP_INSIDE_NO}" />
				        <input type="hidden" id="${punishmentTrans.EXP_INSIDE_NO}_AFFIRM_LEVEL" name = "${punishmentTrans.EXP_INSIDE_NO}_AFFIRM_LEVEL" value="${punishmentTrans.AFFIRM_LEVEL}" />                    
	                    <input type="hidden" id="${punishmentTrans.EXP_INSIDE_NO}_HR_AFFIRM_NO" name = "${punishmentTrans.EXP_INSIDE_NO}_HR_AFFIRM_NO" value="${punishmentTrans.HR_AFFIRM_NO}" />                    
	                    <input type="hidden" id="${punishmentTrans.EXP_INSIDE_NO}_AFFIRMOR_ID" name = "${punishmentTrans.EXP_INSIDE_NO}_AFFIRMOR_ID" value="${punishmentTrans.AFFIRMOR_ID}" />
						<input type="hidden" id="${punishmentTrans.EXP_INSIDE_NO}_START_DATE" name = "${punishmentTrans.EXP_INSIDE_NO}_START_DATE" value="${punishmentTrans.DATE_PUNISHED}" />                                     
						<input type="hidden" id="${punishmentTrans.EXP_INSIDE_NO}_EMPID" name = "${punishmentTrans.EXP_INSIDE_NO}_EMPID" value="${punishmentTrans.EMPID}" />   				        				        				        
				    </td>
					<td>${punishmentTrans.EMPID}</td>
					<td>${punishmentTrans.LOCAL_NAME}</td>
					<td>${punishmentTrans.DEPT_NAME}</td>
					<td>${punishmentTrans.DEPT_DISTINGUISH_NAME}</td>
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
												    <a href="/ess/trans/approvePunishmentTrans?HR_AFFIRM_NO=${affirmer.HR_AFFIRM_NO}&&START_DATE=${punishmentTrans.DATE_PUNISHED}&&EMPID=${punishmentTrans.EMPID}&&PERSON_ID=${punishmentTrans.PERSON_ID}&&AFFIRM_FLAG=1&&AFFIRM_LEVEL=${affirmer.AFFIRM_LEVEL}&&EXP_INSIDE_NO=${punishmentTrans.EXP_INSIDE_NO}" target="ajaxTodo">
							                           <span style="color:blue;">&nbsp;<spring:message code="ess.trans.title.pass"/><!--通过--></span>
													</a>
												    <a href="/ess/trans/approvePunishmentTrans?HR_AFFIRM_NO=${affirmer.HR_AFFIRM_NO}&&START_DATE=${punishmentTrans.DATE_PUNISHED}&&EMPID=${punishmentTrans.EMPID}&&PERSON_ID=${punishmentTrans.PERSON_ID}&&AFFIRM_FLAG=2&&AFFIRM_LEVEL=${affirmer.AFFIRM_LEVEL}&&EXP_INSIDE_NO=${punishmentTrans.EXP_INSIDE_NO}" target="ajaxTodo">
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
					    <c:if test="${punishmentTrans.AFFIRM_FLAG==0}" >
					        <span style="color:red;"><spring:message code="ess.trans.title.notAffirmed"/><!--未决裁--></span>
					    </c:if>
					    <c:if test="${punishmentTrans.AFFIRM_FLAG!=0 }" >
					        <span style="color:green;"><spring:message code="ess.trans.title.affirmed"/><!--已决裁--></span>
					    </c:if>                    
					</td>
					<td>${punishmentTrans.PROBATION_MARK}</td>												
				</tr>			
			</c:forEach>			
		</tbody>
	</table>	
</form>
	<c:set value="/ess/trans/viewPunishmentTransAffirmList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>	
<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
//奖励的取消发令
function cancelRewardTransValidateCallback(form, callback) {
	
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	
    var checked=false;
	var ids= document.getElementsByName("searchHortation");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
			break;
		}
	}
	if(!checked){
		//请选择信息再进行保存操作
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
		return false;
	}

    $form.attr("action","/hrm/searchTransferOrder/cancelHortationInBatch");
	
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				alertMsg.correct(data.message);
				$("#searchTransferOrderForm").submit();
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

//惩戒的取消发令
function cancelTransValidateCallback_hr0506(form, callback) {
	
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	
    var checked=false;
	var ids= document.getElementsByName("searchPm");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
			break;
		}
	}
	if(!checked){
		//请选择信息再进行保存操作
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
		return false;
	}

    $form.attr("action","/hrm/searchTransferOrder/cancelPunishMentInBatch");
	
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				alertMsg.correct(data.message);
				$("#searchTransferOrderForm").submit();
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
	$("#searchTransferOrder").change(function(){
		$("#searchTransferOrder").submit();
	});
});

</script>

<%--<div class="pageContent">
	<div class="panel">
		<h1></h1>
		<div>
			<form id="searchTransferOrderForm" onsubmit="return navTabSearch(this);" action="/hrm/searchTransferOrder/viewSearchRewardAndPunishmentList" method="post">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					height="20" class="user_table">
					<tr>
						<td class="td_type" width="6%">
							调令类型 : 
							<select id="searchTransferOrder" name="searchTransferOrder">
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

<%--<c:if test="${tag eq '1'}">--%>
	<div class="pageHeader">
		<form onsubmit="return navTabSearch(this);" action="/hrm/searchTransferOrder/viewSearchRewardAndPunishmentList" 
		rel="pagerForm" method="post" name="searchTransferOrderForm" id="searchTransferOrderForm">
		<%--
		<input type="hidden" name="transferOrderType" value="reward"/>
		<input type="hidden" name="searchTransferOrder" value="1"/>
		--%><div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							调令类型 : 
							<select id="searchTransferOrder" name="searchTransferOrder">
								<option value="-1">
									<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!--请选择-->
								</option>
								<option value="1" <c:if test="${tag eq '1' }">selected</c:if> >奖励</option>
								<option value="2" <c:if test="${tag eq '2' }">selected</c:if> >惩罚</option>
							</select>
						</td>
						<c:if test="${tag eq '1'}">
							<td>
								<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
								<!-- 部门： -->
							</td>	
							<td>
								<ait:deptTree name="seach_DEPT_NO" limit="hr" selected="${DEPT_NO }"/>
							</td>
			                <td>
								<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>
								<!--社号/姓名：-->
							</td>
							<td>
								<input type="text" name="seach_KEY" value="${KEY}" />
							</td>
							<td>
								<spring:message code="hr.viewReward.title.REWARD_TYPE_NAME"/>
								<!--奖励类型:-->
							</td>									 
							<td>
							     <ait:SelectSyCodeByCpnyID name="seach_TRANS_CODE" parentNo="641" cnpyID="${defaultCpny}" selected="${TRANS_CODE}" limit="all"/>       
							</td>
						</c:if>
						<c:if test="${tag eq '2'}">
							<td>
								<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
								<!-- 部门： -->
							</td>	
							<td>
								<ait:deptTree name="seach_DEPT_NO" limit="hr" selected="${DEPT_NO }"/>
							</td>
			                <td>
								<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>
								<!--社号/姓名：-->
							</td>
							<td>
								<input type="text" name="seach_KEY" value="${KEY}" />
							</td>
							<td>
								<spring:message code="hr.searchPunishMent.title.PUNISHMENT_TYPE"/>
								<!--惩戒类型:-->
							</td>
							<td>
							     <ait:SelectSyCodeByCpnyID name="seach_TRANS_CODE" parentNo="642" cnpyID="${defaultCpny}" selected="${TRANS_CODE}" limit="all"/>       
							</td>
						</c:if>
					</tr>
				</table>
				<table class="searchContent">
					<tr>
						<c:if test="${tag eq '1'}">
							<td>
				               <spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE"/>
				         	   <!--开始时间:-->
				            </td>			
						    <td>
						        <input type="text" name="seach_FROM_DATE_REWARD" class="date" format="yyyy-MM-dd" readonly="true" value="${FROM_DATE_REWARD}"/>
							    <a class="inputDateButton" href="javascript:;"></a>			   
						    </td>
				            <td>
				               <spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE"/>
				               <!--结束时间:-->
				            </td>                			     
							<td>
							    <input type="text" name="seach_TO_DATE_REWARD" class="date" format="yyyy-MM-dd" readonly="true" value = "${TO_DATE_REWARD}"/>
							    <a class="inputDateButton" href="javascript:;"></a>
							</td>
							<td>
								<spring:message code="hr.viewTransactionTransViewList.title.TRANSSTATUSCODE"/>
								<!--决裁状态:-->
							</td>					
							<td>
                            <!-- 
							     <ait:SelectSyCodeByCpnyID name="seach_STATUS_CODE" parentNo="3528" cnpyID="${defaultCpny}" selected="${STATUS_CODE}" limit="all"/>       
							 -->
                                 <ait:SelectSyCodeByCpnyID name="seach_ACTIVITY" parentNo="123489" cnpyID="${defaultCpny}" selected="${ACTIVITY}" limit="all"/>
                            </td>						
						</c:if>
						<c:if test="${tag eq '2'}">
							<td>
			                    <spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE"/>
			                    <!--开始时间:-->
			                </td>			
						    <td>
						        <input type="text" name="seach_FROM_DATE_PUNISHED" class="date" format="yyyy-MM-dd" readonly="true" value="${FROM_DATE_PUNISHED}"/>
							    <a class="inputDateButton" href="javascript:;"></a>			   
						    </td>
			                <td>
			                    <spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE"/>
			                    <!--结束时间:-->
			                </td>                			     
							<td>
							    <input type="text" name="seach_TO_DATE_PUNISHED" class="date" format="yyyy-MM-dd" readonly="true" value = "${TO_DATE_PUNISHED}"/>
							    <a class="inputDateButton" href="javascript:;"></a>
							</td>
							<td>
								<spring:message code="hr.viewTransactionTransViewList.title.TRANSSTATUSCODE"/>
								<!--决裁状态:-->
							</td>					
							<td>
                            <!-- 
							     <ait:SelectSyCodeByCpnyID name="seach_STATUS_CODE" parentNo="3528" cnpyID="${defaultCpny}" selected="${STATUS_CODE}" limit="all"/>       
							-->
                                 <ait:SelectSyCodeByCpnyID name="seach_ACTIVITY" parentNo="123489" cnpyID="${defaultCpny}" selected="${ACTIVITY}" limit="all"/>
                            </td>
						</c:if>
					</tr>
				</table>
			</div>
			<c:if test="${tag eq '1'}">
				<div class="formBar">
					<label style="float: left">
						<input type="checkbox" class="checkboxCtrl" group="searchHortation" />
						<spring:message code="hr.viewUpgrade.title.CHECKALL"/>
						<!--全选-->
					</label>
					<ul>
						<li>
							<div class="subBar">
			                   	<div class="buttonActive">
			                   		<div class="buttonContent">
			                   			<button type="submit">
			                   				<spring:message code="public.title.search"/><!-- 检索 -->
			                   			</button>
			                   		</div>
			                   	</div>
								<div class="buttonActive">
									<a class="update" onclick="return cancelRewardTransValidateCallback('updateHortationForm',DWZ.ajaxDone);" href="#" >
										<span>
											<spring:message code="hr.viewTransactionTransViewList.title.CANCLETRANS"/><!-- 取消发令 -->
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
					<label style="float: left">
						<input type="checkbox" class="checkboxCtrl" group="searchPm" />
						<spring:message code="hr.viewUpgrade.title.CHECKALL"/>
						<!--全选-->
					</label>
					<ul>
						<li>
							<div class="subBar">
			                   	<div class="buttonActive">
			                   		<div class="buttonContent">
			                   			<button type="submit">
			                   				<spring:message code="public.title.search"/><!-- 检索 -->
			                   			</button>
			                   		</div>
			                   	</div> 					
								<div class="buttonActive">
								     <a class="update" onclick="return cancelTransValidateCallback_hr0506('updatePunishMentForm',DWZ.ajaxDone);" href="#" >
								     	<span>
								     		<spring:message code="hr.viewTransactionTransViewList.title.CANCLETRANS"/><!-- 取消发令 -->
								     	</span>
								     </a>
			    	            </div>
						    </div>	
						</li>
					</ul>
				</div>
			</c:if>
		</form>
	</div>
	
<c:if test="${tag eq '1'}">	
	<div class="pageContent">
	<form name="updateHortationForm" id="updateHortationForm" method="post" action="/hrm/searchTransferOrder/cancelHortationInBatch"
		  onsubmit="return cancelRewardTransValidateCallback(this, navTabAjaxDone);">
		  <input type="hidden" name="transferOrderType" value="reward"/>
		<input type="hidden" name="searchTransferOrder" value="1"/>
	 	<table class="table" width="100%" layoutH="150" nowrapTD="false">      
			<thead>
				<tr>
				    <th width="30">
				    	<spring:message code="hr.viewUpgrade.title.CHECKALL"/>
				    	<!--全选-->
				    </th>
					<th width="60">
						<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
						<!--工号-->
					</th>
					<th width="60">
						<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
						<!--姓名-->
					</th>
					<th width="60">
						<spring:message code="hr.viewPersonalInfo.title.XIANBUMEN"/>
						<!--现部门-->
					</th>
					<th width="60">
						<spring:message code="hr.viewPersonalInfo.title.XIANZHIWEI"/>
						<!--现职位-->
					</th>
					<th width="60">
						<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
						<!--职级名称(职务)-->
					</th>
					<%--
				    <th width="40">
				    	<spring:message code="hr.viewPersonalInfo.title.XIANZHIJI"/>
						<!--现职级-->
				    </th>
				    <th width="100">
				    	<spring:message code="hr.viewPersonalInfo.title.XIANZHIZE"/>
						<!--现职责-->
				    </th>
				    --%>
				    <th width="60">
				    	<spring:message code="hr.viewReward.title.REWARD_DATE"/>
				    	<!--奖励日期-->
				    </th>
					<th width="60">
						<spring:message code="hr.viewReward.title.REWARD_TYPE_NAME"/>
						<!--奖励类型-->
					</th>
					<th width="60">
						<spring:message code="hr.viewReward.title.REWARD_BONUS"/>
						<!--奖励金额-->
					</th>
					<th width="60">
						<spring:message code="hr.viewReward.title.REWARD_CONTENTS"/>
						<!--功绩内容-->
					</th>
					<%--
					<th width="80">
						<spring:message code="hr.viewUpgrade.title.UPGRADETYPE"/>
						<!--调动类型:-->
					</th>
					--%>
					<th width="60">
						<spring:message code="hr.viewTransactionTransViewList.title.DEFINITELY_CUTTION_CODITIONS"/>
						<!--决裁情况-->
					</th>	
					<th width="60">
						<spring:message code="hr.viewTransactionTransViewList.title.HANDLERS"/>
						<!--操作者-->
					</th>	
					<th width="60">
						<spring:message code="hr.viewTransactionTransViewList.title.IT_BECOME_EFFECTIVE"/>
						<!--是否生效-->
					</th>									
				</tr>
			</thead>
		
			<tbody>
				<c:forEach items="${hrHortationList}" var="hortation" varStatus="i">			
					<tr target="sid" rel="${hortation.EXP_INSIDE_NO}">
					    <td>
					    	<c:if test="${hortation.ACTIVITY_FLAG == 0 }" >
					        	<input type="checkbox" id="searchHortation" name="searchHortation" value="${hortation.EXP_INSIDE_NO}" />
					        </c:if>
					    </td>
						<td>${hortation.EMPID}</td>
						<td>${hortation.LOCAL_NAME}</td>
						<td>${hortation.DEPTNAME}</td>
						<td>${hortation.POSITIONNAME}</td>
						<td>${hortation.POST_NAME}</td>
						<td>${hortation.REWARD_DATE}</td>
						<td>${hortation.REWARD_TYPE_NAME}</td>
						<td style="text-align:right"><fmt:formatNumber value="${hortation.REWARD_BONUS}" pattern="#,##0.00"/></td>
						<td>${hortation.REWARD_CONTENTS}</td>
						<td>
						    <c:forEach items="${hortation.affirmerList}" var="affirmer" varStatus="i">					  					           
						        <dt style="padding: 1px;">
						            ${affirmer.LOCAL_NAME}
							         <c:if test="${affirmer.AFFIRM_FLAG==1}" >			                            
				                              &nbsp;<spring:message code="hr.viewTransactionTransViewList.title.PASS"/><!-- 已通过 -->  
				                     </c:if> 
				                     <c:if test="${affirmer.AFFIRM_FLAG==2}" >         
				                              &nbsp;<spring:message code="hr.viewTransactionTransViewList.title.VOTE_DOWN"/><!-- 已否决 --> 
									 </c:if>
				                     <c:if test="${affirmer.AFFIRM_FLAG==0}" >         
				                              &nbsp;<spring:message code="hr.viewTransactionTransViewList.title.PENDING_CUTTION"/><!-- 未决裁 -->
									 </c:if>									 
								</dt>
							</c:forEach>	
						</td>			
						<td>${hortation.CREATE_NAME}</td>
						<td>
                        <%--	    
				            <c:if test="${hortation.ACTIVITY_FLAG == 1 }" >
				             <img src="/resources/images/a_1.gif" style="cursor:hand"/>
				            </c:if>
				            <c:if test="${hortation.ACTIVITY_FLAG == 0 }" >
				             <img src="/resources/images/0.gif" style="cursor:hand"/>
				            </c:if>
				            <c:if test="${hortation.ACTIVITY_FLAG == 2 || hortation.ACTIVITY_FLAG == 3}" >
				                 &nbsp;<spring:message code="hr.viewTransactionTransViewList.title.CANCELLED"/><!-- 已取消 -->&nbsp;
				            </c:if>
                        --%>
                        
                            <c:if test="${hortation.ACTIVITY_FLAG== 0 }" >
                             <%--<img src="/resources/images/a_1.gif" style="cursor:hand"/>
                           标记这条发令是否生效: 0未生效 1已生效2裁决否定3发令取消 --%>
                                    <img src="/resources/images/0.gif" title="<spring:message code='alert.message.approval_status_ing'/>" />
                            </c:if>
                            <c:if test="${hortation.ACTIVITY_FLAG== 1 }" >
                             <img src="/resources/images/1.gif" style="cursor:hand" title="<spring:message code='alert.message.approval_status_end'/>" />
                            </c:if>
                            <c:if test="${hortation.ACTIVITY_FLAG== 2}" >
                                <img src="/resources/images/0.gif" style="cursor:hand" title="<spring:message code='alert.message.approval_status_reject'/>" />
                            </c:if> 
                            <c:if test="${hortation.ACTIVITY_FLAG== 3}" >
                                <img src="/resources/images/0.gif" style="cursor:hand" title="<spring:message code='alert.message.approval_status_cancel'/>" />
                            </c:if>
                            			            
				        </td> 						
			       </tr>			
				</c:forEach>			
			</tbody>	
		</table>
	</form>	
		<c:set value="/hrm/searchTransferOrder/viewSearchRewardAndPunishmentList" var="pageUrl"/>
		<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	</div>
</c:if>
<%--<c:if test="${tag eq '2'}">--%>
<%--
	<div class="pageHeader">
		<form onsubmit="return navTabSearch(this);" action="/hrm/searchTransferOrder/viewSearchRewardAndPunishmentList" 
		rel="pagerForm" method="post" name="searchPmForm" id="searchPmForm">
			<input type="hidden" name="transferOrderType" value="punishment"/>
			<input type="hidden" name="searchTransferOrder" value="1"/>
			<div class="searchBar">
				<table class="searchContent">
					<tr>			
						<td>
							<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
							<!-- 部门： -->
						</td>	
						<td>
							<ait:deptTree name="seach_DEPT_NO" limit="hr" selected="${DEPT_NO }"/>
						</td>
		                <td>
							<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>
							<!--工号/姓名：-->
						</td>
						<td>
							<input type="text" name="seach_KEY" value="${KEY}" />
						</td>
						<td>
							<spring:message code="hr.searchPunishMent.title.PUNISHMENT_TYPE"/>
							<!--惩戒类型:-->
						</td>									 
						<td>
						     <ait:SelectSyCodeByCpnyID name="seach_TRANS_CODE" parentNo="642" cnpyID="${defaultCpny}" selected="${TRANS_CODE}" limit="all"/>       
						</td>					
					</tr>
					</table>
					<table class="searchContent">
					<tr>
		                <td>
		                    <spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE"/>
		                    <!--开始时间:-->
		                </td>			
					    <td>
					        <input type="text" name="seach_FROM_DATE_PUNISHED" class="date required" format="yyyy-MM-dd" readonly="true" value="${FROM_DATE_PUNISHED}"/>
						    <a class="inputDateButton" href="javascript:;"></a>			   
					    </td>
		                <td>
		                    <spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE"/>
		                    <!--结束时间:-->
		                </td>                			     
						<td>
						    <input type="text" name="seach_TO_DATE_PUNISHED" class="date required" format="yyyy-MM-dd" readonly="true" value = "${TO_DATE_PUNISHED}"/>
						    <a class="inputDateButton" href="javascript:;"></a>
						</td>
						<td>
							<spring:message code="hr.viewTransactionTransViewList.title.TRANSSTATUSCODE"/>
							<!--决裁状态:-->
						</td>					
						<td>
						     <ait:SelectSyCodeByCpnyID name="seach_STATUS_CODE" parentNo="3528" cnpyID="${defaultCpny}" selected="${STATUS_CODE}" limit="all"/>       
						</td>		 
					</tr>
				</table>
		
			</div>
			<div class="formBar">
				<tr>
					<label style="float: left">
						<input type="checkbox" class="checkboxCtrl" group="searchPm" />
						<spring:message code="hr.viewUpgrade.title.CHECKALL"/>
						<!--全选-->
					</label>
					<ul>
						<li>
							<div class="subBar">
		                    	<div class="buttonActive">
		                    		<div class="buttonContent">
		                    			<button type="submit">
		                    				<spring:message code="public.title.search"/><!-- 检索 -->
		                    			</button>
		                    		</div>
		                    	</div> 					
								<div class="buttonActive">
								     <a class="update" onclick="return cancelTransValidateCallback_hr0506('updatePunishMentForm',DWZ.ajaxDone);" href="#" >
								     	<span>
								     		<spring:message code="hr.viewTransactionTransViewList.title.CANCLETRANS"/><!-- 取消发令 -->
								     	</span>
								     </a>
		                        </div>
						    </div>	
						</li>
					</ul>
				</tr>
			</div>
		</form>
	</div>
 --%>
 	
<c:if test="${tag eq '2'}">	
	<div class="pageContent">
	<form name="updatePunishMentForm" id="updatePunishMentForm" method="post" action="/hrm/searchTransferOrder/cancelPunishMentInBatch"
		  onsubmit="return cancelTransValidateCallback_hr0506(this, navTabAjaxDone);">
	 	<table class="table" width="100%" layoutH="150" nowrapTD="false">      
			<thead>
				<tr>
				    <th width="30">
				    	<spring:message code="hr.viewUpgrade.title.CHECKALL"/>
				    	<!--全选-->
				    </th>
					<th width="60">
						<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
						<!--工号-->
					</th>
					<th width="60">
						<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
						<!--姓名-->
					</th>
					<th width="60">
						<spring:message code="hr.viewPersonalInfo.title.XIANBUMEN"/>
						<!--现部门-->
					</th>
					<th width="60">
						<spring:message code="hr.viewPersonalInfo.title.XIANZHIWEI"/>
						<!--现职位-->
					</th>
					<th width="60">
						<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
						<!--职级名称(职务)-->
					</th>
				    <%--
				    <th width="40">
				    	<spring:message code="hr.viewPersonalInfo.title.XIANZHIJI"/>
						<!--现职级-->
				    </th>
				    <th width="100">
				    	<spring:message code="hr.viewPersonalInfo.title.XIANZHIZE"/>
						<!--现职责-->
				    </th>
				    --%>
				    <th width="60">
				    	<spring:message code="hr.viewReward.title.DATE_PUNISHED"/>
				    	<!--惩戒日期-->
				    </th>
					<th width="60">
						<spring:message code="hr.viewReward.title.PUN_TYPE_NAME"/>
						<!--惩戒类型-->
					</th>
					<th width="60">
						<spring:message code="hr.viewReward.title.PUN_BONUS"/>
						<!--惩戒金额-->
					</th>
					<th width="60">
						<spring:message code="hr.viewReward.title.REWARD_CONTENTS"/>
						<!--具体内容-->
					</th>
					<%--
					<th width="80">
						<spring:message code="hr.viewUpgrade.title.UPGRADETYPE"/>
						<!--调动类型:-->
					</th>
					--%>
					<th width="60">
						<spring:message code="hr.viewTransactionTransViewList.title.DEFINITELY_CUTTION_CODITIONS"/>
						<!--决裁情况-->
					</th>	
					<th width="60">
						<spring:message code="hr.viewTransactionTransViewList.title.HANDLERS"/>
						<!--操作者-->
					</th>	
					<th width="60">
						<spring:message code="hr.viewTransactionTransViewList.title.IT_BECOME_EFFECTIVE"/>
						<!--是否生效-->
					</th>										
				</tr>
			</thead>
		
			<tbody>
				<c:forEach items="${hrPunishMentList}" var="punishMent" varStatus="i">			
					<tr target="sid" rel="${punishMent.EXP_INSIDE_NO}">
					    <td>
					    	<c:if test="${punishMent.ACTIVITY_FLAG == 0 }" >
					        	<input type="checkbox" id="searchPm" name="searchPm" value="${punishMent.EXP_INSIDE_NO}" />
					        </c:if>
					    </td>
						<td>${punishMent.EMPID}</td>
						<td>${punishMent.LOCAL_NAME}</td>
						<td>${punishMent.DEPTNAME}</td>
						<td>${punishMent.POSITIONNAME}</td>
						<td>${punishMent.POST_NAME}</td>
						<td>${punishMent.DATE_PUNISHED}</td>
						<td>${punishMent.PUN_TYPE_NAME}</td>
						<td style="text-align:right"><fmt:formatNumber value="${punishMent.PUN_BONUS}" pattern="#,##0.00"/></td>
						<td>${punishMent.PUN_REASON}</td>
						<td>
						    <c:forEach items="${punishMent.affirmerList}" var="affirmer" varStatus="i">					  					           
						        <dt style="padding: 1px;">
						            ${affirmer.LOCAL_NAME}
							         <c:if test="${affirmer.AFFIRM_FLAG==1}" >			                            
				                              &nbsp;<spring:message code="hr.viewTransactionTransViewList.title.PASS"/><!-- 通过 -->  
				                     </c:if> 
				                     <c:if test="${affirmer.AFFIRM_FLAG==2}" >         
				                              &nbsp;<spring:message code="hr.viewTransactionTransViewList.title.VOTE_DOWN"/><!-- 否决 --> 
									 </c:if>
				                     <c:if test="${affirmer.AFFIRM_FLAG==0}" >         
				                              &nbsp;<spring:message code="hr.viewTransactionTransViewList.title.PENDING_CUTTION"/><!-- 未决裁 -->
									 </c:if>							 
								</dt>
							</c:forEach>	
						</td>			
						<td>${punishMent.CREATE_NAME}</td>
						<td>
                        <%--	    
				            <c:if test="${punishMent.ACTIVITY_FLAG == 1 }" >
				             <img src="/resources/images/a_1.gif" style="cursor:hand"/>
				            </c:if>
				            <c:if test="${punishMent.ACTIVITY_FLAG == 0 }" >
				             <img src="/resources/images/0.gif" style="cursor:hand"/>
				            </c:if>
				            <c:if test="${punishMent.ACTIVITY_FLAG == 2 || punishMent.ACTIVITY_FLAG == 3}" >
				                 &nbsp;<spring:message code="hr.viewTransactionTransViewList.title.CANCELLED"/><!-- 已取消 -->&nbsp;
				            </c:if>
                         --%>    
                            <c:if test="${punishMent.ACTIVITY_FLAG== 0 }" >
                             <%--<img src="/resources/images/a_1.gif" style="cursor:hand"/>
                           标记这条发令是否生效: 0未生效 1已生效2裁决否定3发令取消 --%>
                                    <img src="/resources/images/0.gif" title="<spring:message code='alert.message.approval_status_ing'/>" />
                            </c:if>
                            <c:if test="${punishMent.ACTIVITY_FLAG== 1 }" >
                             <img src="/resources/images/1.gif" style="cursor:hand" title="<spring:message code='alert.message.approval_status_end'/>" />
                            </c:if>
                            <c:if test="${punishMent.ACTIVITY_FLAG== 2}" >
                                <img src="/resources/images/0.gif" style="cursor:hand" title="<spring:message code='alert.message.approval_status_reject'/>" />
                            </c:if> 
                            <c:if test="${punishMent.ACTIVITY_FLAG== 3}" >
                                <img src="/resources/images/0.gif" style="cursor:hand" title="<spring:message code='alert.message.approval_status_cancel'/>" />
                            </c:if>
                            
                            			            
				        </td> 						
			       </tr>			
				</c:forEach>			
			</tbody>	
		</table>
	</form>	
	<c:set value="/hrm/searchTransferOrder/viewSearchRewardAndPunishmentList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	</div>
</c:if>
<div id="initPagination" style="display:none;">
	<c:set value="/hrm/searchTransferOrder/viewSearchRewardAndPunishmentList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
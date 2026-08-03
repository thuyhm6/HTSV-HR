<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
<!--
function validateAffirmLeaveApplyCallbackEss0802(form,callback,flag) {
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

    $form.attr("action","/ess/affirmLeaveApply/approveLeaveApplyInBatch?AFFIRM_FLAG="+flag+"&AFFIRM_TYPE=LEAVE_AFFIRM");

	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				//navTabSearch("searchLeaveApplyAffirmForm_leave");
				navTabSearchWithParam("searchLeaveApplyAffirmForm_leave",$("#searchLeaveApplyAffirmForm_leave").serializeArray());
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
	var seach_AFFIRM_FLAG=$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val();
	var seach_APPLY_TYPE_CODE=$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val();
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ess/affirmLeaveApply/viewLeaveAffirmList?seach_KEY="+seach_KEY+"&seach_DEPT_NO="+seach_DEPT_NO+"&seach_START_DATE="+seach_START_DATE+"&seach_END_DATE="+seach_END_DATE+"&seach_STATUS_CODE="+seach_STATUS_CODE+"&seach_APPLY_TYPE_CODE="+seach_APPLY_TYPE_CODE+"&seach_AFFIRM_FLAG="+seach_AFFIRM_FLAG);
}

function goToHrefUrl(obj,url){
	var seach_KEY=$("#seach_KEY",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_KEY",navTab.getCurrentPanel()).val();
	var seach_DEPT_NO=$("#seach_DEPT_NO",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_DEPT_NO",navTab.getCurrentPanel()).val();
	var seach_START_DATE=$("#seach_START_DATE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_START_DATE",navTab.getCurrentPanel()).val();
	var seach_END_DATE=$("#seach_END_DATE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_END_DATE",navTab.getCurrentPanel()).val();
	var seach_STATUS_CODE=$("#seach_STATUS_CODE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_STATUS_CODE",navTab.getCurrentPanel()).val();
	var seach_AFFIRM_FLAG=$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val();
	var seach_APPLY_TYPE_FLAG=$("#seach_APPLY_TYPE_FLAG",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_APPLY_TYPE_FLAG",navTab.getCurrentPanel()).val();
	
	url = url + "&parentParam=DEPT_NO="+seach_DEPT_NO+"@KEY="+seach_KEY+"@START_DATE="+seach_START_DATE+"@END_DATE="
			+seach_END_DATE+"@STATUS_CODE="+seach_STATUS_CODE
			+"@AFFIRM_FLAG="+seach_AFFIRM_FLAG + "@APPLY_TYPE_FLAG="+seach_APPLY_TYPE_FLAG;
	
	obj.target="navTab";
	obj.rel="ess0242_affirm";
	obj.href=url ;
}
//-->
</script>
         
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/affirmLeaveApply/viewLeaveAffirmList" method="post" 
	      name="searchLeaveApplyAffirmForm_leave" id="searchLeaveApplyAffirmForm_leave" rel="pagerForm">
	<input type="hidden" id="menuNo" name="menuNo" value="2449"/>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					 <spring:message code="public.title.deptName"/><!-- 部门 -->
				</td>
				<td>
					<ait:deptList name="seach_DEPT_NO" limit="super" id="viewLeaveAffirmList_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPT_NO" limit="super" id="viewLeaveAffirmList_seachDept" selected="${DEPT_NO}"/>
				</td>
				<td>社号/姓名
				</td>						
				<td>
					<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}" />
				</td>
				<td><!-- 审批状态 -->
					审批状态
				</td>				
				<td>  
				     <select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
						 <option value="-5">全部</option>
						 <option value="0" <c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>>提交</option>
						 <option value="1" <c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>通过</option>
						 <option value="2" <c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>否决</option>
						 <option value="4" <c:if test="${AFFIRM_FLAG eq '4'}">selected</c:if>>审批中</option>
						 <option value="10" <c:if test="${AFFIRM_FLAG eq '10'}">selected</c:if>>本人审批</option>
						 <option value="11" <c:if test="${AFFIRM_FLAG eq '11'}">selected</c:if>>未到审批</option>
					 </select>       
				</td>
				<td>
					<spring:message code="ess.viewApply.title.leaveApplyType"/><!-- 休假类型 -->
				</td>				
				<td>
				     <ait:SelectSyCodeByCpnyID name="seach_APPLY_TYPE_CODE" parentNo="21" cnpyID="${defaultCpny}"  selected="${APPLY_TYPE_CODE}" limit="all"/>       
				</td>			
			</tr>
			<tr>
                <td>
                    <spring:message code="public.title.startDate"/><!-- 开始日期 -->
                </td>			
			    <td><!--seach_FROM_TIME-->
			        <input type="text" id="seach_START_DATE" name="seach_START_DATE" class="date" format="yyyy-MM-dd" readonly="true" value="${START_DATE}"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td>
                    <spring:message code="public.title.endDate"/><!-- 结束日期 -->
                </td>			     
				<td><!--seach_TO_TIME-->
				    <input type="text" id="seach_END_DATE" name="seach_END_DATE" class="date" format="yyyy-MM-dd" readonly="true" value="${END_DATE}"/>
				    <a class="inputDateButton" href="javascript:;"></a>
				</td> 
				<td><!-- 审批状态 -->
					批量/个人
				</td>				
				<td>  
				     <select id="seach_APPLY_TYPE_FLAG" name="seach_APPLY_TYPE_FLAG">
						 <option value="">全部</option>
						 <option value="PERSON" <c:if test="${APPLY_TYPE_FLAG eq 'PERSON'}">selected</c:if>>个人</option>
						 <option value="BATCH" <c:if test="${APPLY_TYPE_FLAG eq 'BATCH'}">selected</c:if>>批量</option>
					 </select>       
				</td>
			</tr>
		</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent">
					    <button type="submit">
					       <spring:message code="public.title.search"/>
					    </button>
				        </div>
				        </div>
				    </li>
				</ul>
			</div>
	</div>
	</form>
</div>

<div class="pageContent" > 
<div class="formBar">
			<ul>
				<li>
					<div class="subBar">
						    <div class="buttonActive">
						         <a class="update" onclick="validateAffirmLeaveApplyCallbackEss0802('updateLeaveApplyAffirmForm',DWZ.ajaxDone,'1')" href="#" >
						         <span><spring:message code="ess.title.passInBatch"/><!--批量通过--></span></a>
                            </div>
						    <div class="buttonActive">
						         <a class="update" onclick="validateAffirmLeaveApplyCallbackEss0802('updateLeaveApplyAffirmForm',DWZ.ajaxDone,'2')" href="#" >
						         <span><spring:message code="ess.title.rejectInBatch"/><!--批量否决--></span></a>
                    	</div>									
					</div>	
				</li>
			</ul>
		</div>
<form name="updateLeaveApplyAffirmForm" id="updateLeaveApplyAffirmForm" method="post" action="/ess/affirmApply/approveLeaveApplyInBatch" 
	  onsubmit="return validateAffirmLeaveApplyCallbackEss0802(this, navTabAjaxDone);">    
	<table class="table" width="100%" layoutH="235" nowrapTD="false">
		<thead>
			<tr><th><input type="checkbox" class="checkboxCtrl" group="c1" /></th>
			    <th><!--NO.-->
					NO
				</th>
				<th><!--申请人-->
					申请人
				</th>
				<th><!--是否批量-->
					是否批量
				</th>
				<th><!-- 加班类型 -->
					开始时间
				</th>	
				<th><!-- 加班类型 -->
					结束时间
				</th>	
				<th><!-- 加班类型 -->
					申请时长
				</th>			
				<th><!-- 加班类型 -->
					申请类型
				</th>	
				<th><!-- 加班类型 -->
					申请部门
				</th>
				<th><!--加班事由-->
					申请事由
				</th>
				<th><!--审批情况-->
					审批情况
				</th>
				<th><!--Type-->
					审批
				</th>		
			</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${leaveApplyAffirmList}" var="leaveApplyAffirm" varStatus="i">			
				<tr target="sid">
					<td style="text-align: center">
				    	<c:if test="${leaveApplyAffirm.AFFIRM_FLAG_PERSON eq '1'}">
				        	<input type="checkbox" id="c1" name="c1" value="${leaveApplyAffirm.ESS_AFFIRM_NO}" />
				        </c:if>
				    </td>
				    <td style="text-align: center">${leaveApplyAffirm.NO}</td>
					<td style="text-align: center">[${leaveApplyAffirm.EMPID}]${leaveApplyAffirm.LOCAL_NAME}</td>
					<td style="text-align: center">
						<c:if test="${leaveApplyAffirm.APPLY_TYPE eq 'PERSON' }">
							个人
						</c:if>
						<c:if test="${leaveApplyAffirm.APPLY_TYPE eq 'BATCH' }">
							批量
						</c:if>
					</td>
					<td style="text-align: center">${leaveApplyAffirm.LEAVE_FROM_TIME}</td>
					<td style="text-align: center">${leaveApplyAffirm.LEAVE_TO_TIME}</td>
					<td style="text-align: center">${leaveApplyAffirm.APPLY_LENGTH}</td>
					<td style="text-align: center">
							${leaveApplyAffirm.APPLY_TYPE_NAME}
					</td>
					<td style="text-align: center">${leaveApplyAffirm.BATCH_APPLY_DEPT_NAME}</td>
					<td style="text-align: center">${leaveApplyAffirm.LEAVE_REASON}</td>
					<td style="text-align: center">
						<c:forEach items="${leaveApplyAffirm.affirmorList}" var="affirmList" varStatus="index">	
							<div style="display:block;line-height:15px;font-size:10px;">
								[${affirmList.EMPID }]${affirmList.LOCAL_NAME }&nbsp;
								<c:if test="${affirmList.AFFIRM_FLAG==0}" >
								    未审批
								</c:if>	
								<c:if test="${affirmList.AFFIRM_FLAG==1}" >
								    通过
								</c:if>	
								<c:if test="${affirmList.AFFIRM_FLAG==2}" >
								    否决
								</c:if>	
							</div>
						</c:forEach>
					</td>
					<td style="text-align: center">
					    <c:if test="${leaveApplyAffirm.AFFIRM_FLAG_PERSON eq '1'}">
					    	<a class="add" onclick="goToHrefUrl(this,'/ess/affirmLeaveApply/viewLeaveApplyAffirmorList?pageNum=1&APPLY_NO=${leaveApplyAffirm.APPLY_NO}&unDoApplyNo=${leaveApplyAffirm.UNDO_APPLY_NO}');"
					    	href="#" title="Leave审批"
								target="navTab" rel="ess0242_affirm"><font color="red">审批</font></a>
						</c:if>
					    <c:if test="${leaveApplyAffirm.AFFIRM_FLAG_PERSON ne '1'}">
						    <a rel="leaveAffirmRemark" href="/ess/affirmLeaveApply/viewLeaveApplyCheckInfo?pageNum=1&APPLY_NO=${leaveApplyAffirm.APPLY_NO}&unDoApplyNo=${leaveApplyAffirm.UNDO_APPLY_NO}" title="审批详情"
				          		target="navTab" rel="ess0242_affirm_info" id="leaveAffirmRemarkHref"><font color="red">审批查看</font></a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</form>
	<c:set value="/ess/affirmLeaveApply/viewLeaveAffirmList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>
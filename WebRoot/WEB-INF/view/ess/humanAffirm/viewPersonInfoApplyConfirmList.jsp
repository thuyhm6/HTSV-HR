<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
<!--
function validatePersonalInfoApplyConfirmCallback(form,callback,flag) {
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
		alert('<spring:message code="alert.message.ess.viewApply.choosePersonApplyRecordFirst"/>');
		return false;
	}

    $form.attr("action","/ess/humanAffirm/confirmPersonInfoApplyInBatch?CONFIRM_FLAG="+flag);

	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("searchOvertimeApplyConfirmForm");
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
	<form onsubmit="return navTabSearch(this);" action="/ess/humanAffirm/viewPersonInfoApplyConfirmList" 
	      method="post" name="searchOvertimeApplyConfirmForm" id="searchOvertimeApplyConfirmForm" rel="pagerForm">
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
					<div class="subBar">
                            <div class="buttonActive"><div class="buttonContent"><button type="submit">
                                 <spring:message code="public.title.search"/><!-- 检索 -->        
                            </button>
                    </div></div>
                    </div>	                
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
						         <a class="update" onclick="validatePersonalInfoApplyConfirmCallback('updatePersonalInfoApplyConfirmForm',DWZ.ajaxDone,'1')" href="#" >
						         <span><spring:message code="ess.title.passInBatch"/><!--批量通过--></span></a>
                            </div>
						    <div class="buttonActive">
						         <a class="update" onclick="validatePersonalInfoApplyConfirmCallback('updatePersonalInfoApplyConfirmForm',DWZ.ajaxDone,'2')" href="#" >
						         <span><spring:message code="ess.title.rejectInBatch"/><!--批量否决--></span></a>
                    </div>									
				</div>	
				</li>
		</tr>
	</div>
</div>

<div class="pageContent" > 
<form name="updatePersonalInfoApplyConfirmForm" id="updatePersonalInfoApplyConfirmForm" method="post" action="/ess/humanAffirm/confirmPersonInfoApplyInBatch" 
	  onsubmit="return validatePersonalInfoApplyConfirmCallback(this, navTabAjaxDone);">    
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
				<th width="100"><spring:message code="ess.viewApply.title.applyContent"/><!--申请内容--></th>				
				<th width="180"><spring:message code="ess.viewApply.title.humanAffirm"/><!--人事确认--></th>													
			</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${personApplyList}" var="personInfoConfirm" varStatus="i">			
				<tr target="sid">
				    <td>
				        <input type="checkbox" id="c1" name="c1" value="${personInfoConfirm.PERSON_ID}" />
				    </td>
					<td>${personInfoConfirm.EMPID}</td>
					<td>${personInfoConfirm.LOCAL_NAME}</td>
					<td>${personInfoConfirm.DEPT_NAME}</td>
					<td>${personInfoConfirm.POSITION_NAME}</td>
					<td>${personInfoConfirm.POST_NAME}</td>
					<td>${personInfoConfirm.CREATE_DATE}</td>
					<td>
                        <a href="/ess/viewApply/viewPersonInfo?PERSON_ID=${personInfoConfirm.PERSON_ID}" target="navTab">
                           <span><spring:message code="ess.viewApply.title.viewContent"/><!--查看内容--></span>
						</a>              
                    </td>
					<td>              
						<dt style="padding: 1px;">
							<c:if test="${personInfoConfirm.ACTIVITY == 0}" >
							    <a href="/ess/humanAffirm/confirmPersonInfoApply?PERSON_ID=${personInfoConfirm.PERSON_ID}&&CONFIRM_FLAG=1" target="ajaxTodo">
		                           <span style="color:blue;">&nbsp;
                                         <spring:message code="ess.viewApply.title.pass"/><!--通过-->
                                   </span>
								</a>
							    <a href="/ess/humanAffirm/confirmPersonInfoApply?PERSON_ID=${personInfoConfirm.PERSON_ID}&&CONFIRM_FLAG=2" target="ajaxTodo">
		                           <span style="color:blue;">&nbsp;
                                         <spring:message code="ess.viewApply.title.reject"/><!--否决 -->
                                   </span> 
								</a>							
							</c:if>
							<c:if test="${personInfoConfirm.ACTIVITY==1}" >
								<span style="color:green;">&nbsp;${personInfoConfirm.LOCAL_NAME}</span>
							    <span style="color:green;">&nbsp;
                                      <spring:message code="ess.viewApply.title.pass"/><!--通过-->
                                </span>
							</c:if>	
							<c:if test="${personInfoConfirm.ACTIVITY==2}" >
							    <span style="color:red;">&nbsp;${personInfoConfirm.LOCAL_NAME}</span>
							    <span style="color:red;">&nbsp;
                                     <spring:message code="ess.viewApply.title.reject"/><!--否决 -->
                                </span>
							</c:if>								
						</dt>														
					</td>	                    														
				</tr>			
			</c:forEach>			
		</tbody>
	</table>	
</form>
	<c:set value="/ess/humanAffirm/viewPersonInfoApplyConfirmList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>	
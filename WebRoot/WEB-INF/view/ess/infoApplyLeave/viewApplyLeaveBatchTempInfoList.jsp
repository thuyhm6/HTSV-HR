<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script>
function cancelTempApply(){
	
	$.ajax({
		type: 'POST',
		url:'/ess/infoApplyLeave/cancelTempApply',
		data:'',
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				$.pdialog.closeCurrent();
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
}

function changeType(flag){
	$("#submitTempApplyLeaveForm input[id='stype']").val(flag);
}

function submitTempApplyLeave(form, callback) {

	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: callback || DWZ.ajaxDone,
		error: DWZ.ajaxError
	});
	$.pdialog.closeCurrent();
	return false;
}
</script>

<div class="pageContent" >
	<%--
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="add" href="/ess/infoApplyLeave/viewApplyLeaveInfo?APPLY_TYPE_NO=21" 
					target="navTab"><span>申请</span></a>
			</li>
			 
			<li>
				<a class="delete" onclick="delLeaveApplyCallback('delLeaveApplyAffirmForm',DWZ.ajaxDone)" href="#" 
					target="ajaxTodo" title="确定要批量删除吗?"><span>取消</span></a>					
			</li>
		</ul>
	</div> 
	 --%>
	<form name="submitTempApplyLeaveForm" id="submitTempApplyLeaveForm" method="post" action="/ess/infoApplyLeave/addTempApplyLeaveInfo" 
	  onsubmit="return submitTempApplyLeave(this, navTabAjaxDone);"> 
	  	<input type="hidden" name="stype" id="stype" value=""/>
		<table class="table" width="100%" layoutH="70" nowrapTD="false">
			
			<thead>
				<tr>
				    <th width="40" style="text-align: center"><!--申请人-->
						申请人
					</th>
					<th width="60" style="text-align: center"><!--申请日期-->
						申请日期
					</th>
					<th width="90" style="text-align: center"><!--申请日期-->
						开始日期
					</th>
					<th width="90" style="text-align: center"><!--申请日期-->
						开始时间
					</th>
					<th width="90" style="text-align: center"><!--申请日期-->
						结束日期
					</th>
					<th width="90" style="text-align: center"><!--申请日期-->
						结束时间
					</th>
					<th width="40" style="text-align: center"><!--考勤类型-->
						考勤类型
					</th>
					<th width="40" style="text-align: center"><!--考勤类型-->
						错误信息
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${leaveAffirmTempList}" var="leaveApply" varStatus="i">			
					<tr target="sid" rel="${personInfo.PERSON_ID}">
					    <td style="text-align: center">${leaveApply.LOCAL_NAME}</td>
					    <td style="text-align: center">${leaveApply.APPLY_LEAVE_DATE}</td>
						<td style="text-align: center">${leaveApply.L_FROM_DATE}</td>
						<td style="text-align: center">${leaveApply.L_FROM_TIME}</td>
						<td style="text-align: center">${leaveApply.L_TO_DATE}</td>
						<td style="text-align: center">${leaveApply.L_TO_TIME}</td>
						<td style="text-align: center">${leaveApply.LEAVE_TYPE_NAME}</td>
						<td style="text-align: center">${leaveApply.ERROR_MSG}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button onclick="changeType(1);" type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button onclick="changeType(2);" type="submit"><!--删除--><spring:message code="button.delete"/></button></div></div></li>
			</ul>
		</div>
	</form>
	<a id="viewapplyleavebatchtempinfolist" href="#"></a>
</div>
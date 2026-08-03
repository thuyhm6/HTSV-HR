<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript"> 
<!--
function validateCallbackDeleteAdditionalInfo(form, callback) {


	var $form = $("#deleteAdditionalInfo");
	
	if (!$form.valid()) {
		return false;
	}
   
	var checked=false;
	var ids= document.getElementsByName("AN");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择信息再进行保存操作
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
		return false;
	}
	//确定要提交吗？
	if (confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>')){	
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
	}
	return false;
}
//-->
</script>


<div class="pageContent">
	<form id="deleteAdditionalInfo" method="post" action="/hrm/empinfo/deleteAdditionalInfo" class="pageForm required-validate" onsubmit="return validateCallbackDeleteAdditionalInfo(this, dialogAjaxDone);">
		
		
			<table class="table" width="102.6%" layoutH="60">
					<thead>
						<tr>
							<th width="10"><input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/></th>
							<th width="100">
								<spring:message code="hr.viewAdditional.title.EVENT_DATE"/>
								<!--发生日期-->
							</th>
							<th width="100">
								<spring:message code="hr.viewAdditional.title.INFO_TYPE_NAME"/>
								<!--信息类型-->
							</th>
							<th width="100">
								<spring:message code="hr.viewAdditional.title.REMARK"/>
								<!--详细内容-->
							</th>
							<th width="100">
								<spring:message code="hr.viewAdditional.title.CREATE_NAME"/>
								<!--登记者-->
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${additionalList}" var="item" >
						
							<tr>
								<td>
									<input type="checkbox" id="AN" name="AN" value="${item.ADDITIONAL_NO}" />
								</td>
								<td>${item.EVENT_DATE}</td>
								<td>${item.INFO_TYPE_CODE}</td>
								<td>${item.REMARK}</td>
								<td>${item.CREATED_BY}</td>
							</tr>
						
						</c:forEach>
						
					</tbody>
				</table>
		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.submit"/><!-- 保存 --></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div>
				</li>
			</ul>
		</div>
		
	</form>
</div>
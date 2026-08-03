<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">
<!--
function validateCallbackDeleteAssistInfo(form, callback) {


	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
   
	var checked=false;
	var ids= document.getElementsByName("ASSIST_NO");
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
<form method="post" action="/hrm/empinfo/deleteAssistInfo"
	class="pageForm required-validate"
	onsubmit="return validateCallbackDeleteAssistInfo(this, dialogAjaxDone);">


<table class="table" width="103%" layoutH="60">
	<thead>
		<tr>
			<th width="10"><input type="hidden" name="PERSON_ID"
				class="textInput" value="${PERSON_ID }" /></th>
			<th width="80"><spring:message
				code="hr.assist.title.title" /> <!--标题--></th>
			<th width="80"><spring:message
				code="hr.assist.title.content" /> <!--内容--></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${AssistList}" var="item">
			<tr>
				<td><input type="checkbox" id="ASSIST_NO" name="ASSIST_NO"
					value="${item.ASSIST_NO}" /></td>
				<td class='td_center' width="80">${item.TITLE}</td>
				<td class='td_center' width="80">${item.CONTENT}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<div class="formBar">
<ul>
	<li>
	<div class="buttonActive">
	<div class="buttonContent">
	<button type="submit"><spring:message
		code="public.title.submit" /><!-- 保存 --></button>
	</div>
	</div>
	</li>
	<li>
	<div class="button">
	<div class="buttonContent">
	<button type="button" class="close"><spring:message
		code="public.title.cancle" /><!-- 取消 --></button>
	</div>
	</div>
	</li>
</ul>
</div>

</form>
</div>
<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateCallbacksy0482update(form, callback) {
	
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
	
	return false;
}
</script>
<div class="pageContent">
	<form method="post"  action="/sys/arAffirmPost/updateArAffirmPost" class="pageForm required-validate"  onsubmit="return validateCallbacksy0482update(this,dialogAjaxDone)">
	<div class="pageFormContent nowrap"> 
		    <dl>
				<dt>职责</dt>
				<dd>
					${post.DUTY}
					<input type="hidden" name="DUTY" value="${post.DUTY}"/>
				</dd>
			</dl>
			<dl>
				<dt>等级</dt>
				<dd>
					<input type="text" name="AFFIRM_LEVEL" id="AFFIRM_LEVEL" value="${post.AFFIRM_LEVEL}" class="number required textInput" />				
				</dd>
			</dl>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
					<spring:message code="public.title.submit"/><!-- 提交 --></button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" class="close">
					<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
				</ul>
			</div> 
        </form>
</div>

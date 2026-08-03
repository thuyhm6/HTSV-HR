<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function submit_sendEmail() {
	$('#sendEmail').submit();
}
function navTabSearch_sendEmail(form) {
	var $form = $("#sendEmail");
	if (!$form.valid()) {
		return false;
	}
	$.ajax( {
		type : form.method || 'POST',
		url : $form.attr("action"),
		data : $form.serializeArray(),
		dataType : "json",
		cache : false,
		success : DWZ.ajaxDone,
		error : DWZ.ajaxError
	});
	return false;
}
</script>
<div class="pageContent">
	<form id="sendEmail" name="sealLoginForm" method="post"
		action="/login/sendEmail"
		onsubmit="return navTabSearch_sendEmail(this);">
		<%--<input   type="text" value="${name}"> <input   type="text" value="${date}"> --%>
		<table class="usertable">
			<tr>
				<td>
					发件人
				</td>
				<td>
					<input name="SEND_NAME" type="text" value="${LoginUser.localName}"
						readonly="readonly">
				</td>
			</tr>
			<tr>
				<td>
					主题
				</td>
				<td>
					<input name="title" type="text" value="祝你生日快乐"
						 >
				</td>
			</tr>
			<tr>
				<td>
					收件人
				</td>
				<td>
					<%--<input name="email" type="text" value="${email}">
				--%>
				<input name="email" type="text" value="765923345@qq.com">
				</td>
			</tr>
			<tr>
				<td>
					内容
				</td>
				<td>
					<textarea name="mains" class="editor textInput" name="description"
						rows="6" cols="100"
						tools="Cut,Copy,Blocktag,Fontface,FontSize,Bold,Italic,Underline,Strikethrough,FontColor,BackColor,Source,Fullscreen"
						style="display: none;">生日快乐</textarea>
				</td>
			</tr>
			<tr>
				<td>
					<a class="buttonActive" onclick="submit_sendEmail()"><span>发送</span>
					</a>
				</td>
			</tr>
		</table>
	</form>
</div>
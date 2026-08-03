<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script>
function checkAll() {
	var first = $('#first').attr('value');
	var second = $('#second').attr('value');
	if (first == null || first == '') {
		$("#messageA").html("<font color='red'><spring:message code='hrm.login.CANNOT_EMPTY.Z' /></font>");// 不能为空 
		return false;
	}

	if (first != second) {
		$("#messageW").html("<font color='red'> <spring:message code='hrm.login.TWO_PASSWORD_INCONSISTENT.Z' /></font>");//两次输入密码不一致
		return false;
	}
	//提交
	$("#changePassword").submit();
}

function changeTest() {
	var first = $('#first').attr('value');
	var second = $('#second').attr('value');

	if (first != second) {
		$("#messageW").html("<font color='red'> <spring:message code='hrm.login.TWO_PASSWORD_INCONSISTENT.Z' /></font>");//两次输入密码不一致

	}

}

function cleanMessage() {
	$("#messageW").html("");
	$("#messageA").html("");
}
function navTabSearch_changePassword(form) {

	var $form = $("#changePassword");

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

<div class="pageHeader">
	<form onsubmit="return navTabSearch_changePassword(this);"
		action="/ess/change/changePassword2" method="post" id="changePassword"
		name="viewEntryInfoList">
		<input type="hidden" name='CODE_NO' />
		<div class="searchBar">
			<table class="searchContent">
				<tr>


					<td>
						<spring:message code="ess.infoApply.NEWPASSWORD" /><!--新密码-->
					</td>
					<td>
						<input id="first" type="password" onfocus="cleanMessage()" />
					</td>
					<td>
						<span id="messageA"></span>
					</td>
				</tr>
				<tr>
					<td>
						<spring:message code="ess.infoApply.CONFIRMPASSWORD" /><!--确认密码-->
					</td>
					<td>
						<input id="second" type="password" name="NEW_PS" onfocus="cleanMessage()"
							onblur="changeTest();" />
					</td>
					<td>
						<span id="messageW"></span>
					</td>
				</tr>
				<tr>
					<td>
						<a class="button" onclick="checkAll();"> <span><spring:message
									code="public.title.submit" /> </span> </a>
				</tr>
			</table>

		</div>
	</form>
</div>

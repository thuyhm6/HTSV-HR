<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function checkAll() {
	var first = $('#first').attr('value');
	var second = $('#second').attr('value');
	var oldPass = $('#oldPass').attr('value');
	var currentPassword = ${LoginUser.password};
	if (first == null || first == '') {
		$("#messageA").html("<font color='red'><spring:message code='hrm.login.CANNOT_EMPTY.Z' /></font>");// 不能为空 
		return false;
	}

	if (first != second) {
		$("#messageW").html("<font color='red'> <spring:message code='hrm.login.TWO_PASSWORD_INCONSISTENT.Z' /></font>");//两次输入密码不一致
		return false;
	}
	if (first == currentPassword) {
		$("#messageA").html("<font color='red'> <spring:message code='hrm.login.MATCH_OLD_PASSWORD' /></font>");//两次输入密码不一致
		return false;
	}
	if (currentPassword != oldPass) {
		$("#messageE").html("<font color='red'> <spring:message code='hrm.login.OLD_PASSWORD_INCORRECT' /></font>");//两次输入密码不一致
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
	$("#messageE").html("");
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
		success: function(){
				$.pdialog.closeCurrent();
		} ,
		error : DWZ.ajaxError
	});

	return false;

}

$("a.close").click(function(){
		location.href = '/login/out' ;
});
</script>
<div >
	<h1  style="font-size: 16px;position: relative; color: red; bottom: 3px; padding: 5px 10px 3px 20px; margin: 0;"><!--Your password is not secure--><spring:message code="hrm.login.PASSWORD_NOT_SECURE" /></h1>
</div>
<div class="pageHeader">
	<form onsubmit="return navTabSearch_changePassword(this);"
		action="/ess/change/changePassword2" method="post" id="changePassword" name="viewEntryInfoList">
		<input type="hidden" name='CODE_NO' />
		<div class="searchBar">
			<table class="searchContent" class="lge_table">
				<tr>
					<td class="td_title">
						<spring:message code="hrm.login.OLD_PASSWORD" /><!--久密码-->
					</td>
					<td class="td_type">
						<input id="oldPass" type="password" onfocus="cleanMessage()" />
					</td>
					<td class="td_type">
						<span id="messageE"></span>
					</td>
				</tr>
				<tr>
					<td class="td_title">
						<spring:message code="ess.infoApply.NEWPASSWORD" /><!--新密码-->
					</td>
					<td class="td_type">
						<input id="first" type="password" onfocus="cleanMessage()" />
					</td>
					<td class="td_type">
						<span id="messageA"></span>
					</td>
				</tr>
				<tr>
					<td class="td_title">
						<spring:message code="ess.infoApply.CONFIRMPASSWORD" /><!--确认密码-->
					</td>
					<td class="td_type">
						<input id="second" type="password" name="NEW_PS" onfocus="cleanMessage()"
							onblur="changeTest();" />
					</td>
					<td class="td_type">
						<span id="messageW"></span>
					</td>
				</tr>
				
			</table>
			
		</div>
		<div class="formBar" style="border-bottom: 0; background: none;">
			<ul style="float: left;">
				<li>
					<div class="button">
						<div class="buttonContent"><!-- 通过 -->
							<button type="button" onclick="checkAll()" style="font-size: 16px;">
								<spring:message code="public.title.submit" />
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
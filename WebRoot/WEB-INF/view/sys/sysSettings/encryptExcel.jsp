<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!-- EXCEL导出加密 -->
<script type="text/javascript">
	function URLencode(sStr) {
		return escape(sStr).replace(/\+/g, '%2B').replace(/\"/g, '%22')
				.replace(/\'/g, '%27').replace(/\//g, '%2F').replace(/\#/g,
						'%23');
	}
	function checkePWD(PWD) {
		var str = PWD;
		//在JavaScript中，正则表达式只能使用"/"开头和结束，不能使用双引号
		var Expression = /^(?!(?:[^a-zA-Z]+|\D+|[a-zA-Z0-9]+)$).{8,}$/;
		var objExp = new RegExp(Expression);
		if (objExp.test(str) == true) {
			return true;
		} else {
			return false;
		}
	}
	//加密
	function validateCallback_exportExcel(form, callback) {
		var $form = $(form);
		if (!$form.valid()) {
			return false;
		}
		var pwd = document.getElementById("pwd").value;
		var repwd = document.getElementById("repwd").value;
		if (pwd.length < 8) {
			alert("密码位数不能少于8位！");
			return false;
		}
		if (pwd.length > 15) {
			alert("Excel密码最长为15位！");
			return false;
		}
		if (!checkePWD(pwd)) {
			alert("密码必须由字符数字以及特殊字符组成！\r\n 例如： abcdef123!@#");
			return false;
		}
		if (pwd !== repwd) {
			alert("登录密码与再次输入密码不一致");
			return false;
		}
		var params = $("#encryptExcelform").serialize();
		$.ajax( {
			type : 'post',
			cache : false,
			url : "/sys/confirmPassward?" + params,
			success : function(rtn) {
				$.pdialog.closeCurrent();												
				//页面重载
				var form1=document.getElementById(rtn.formId);
				form1.password.value=rtn.pwd;
		        form1.action=rtn.exportFunName;
		        form1.submit();
			},
			error :	DWZ.ajaxError
		});				
	}	
</script>
<div class="pageContent">
	<form id="encryptExcelform" name="encryptExcelform"
		method="post" action=""
		class="pageForm required-validate"
		onsubmit="return validateCallback_exportExcel(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap">
			<table>	
				<input type="hidden" name="exportFunName" id="exportFunName" value="${exportFunName}">
				<input type="hidden" name="navTabId" id="navTabId" value="${navTabId}">
				<input type="hidden" name="formId" id="formId" value="${formId}">
				<tr>			
					<td style="width: 100px;padding-top:5px;">
						设置打开密码：
					</td>
					<td style="width: 100px;padding-top:5px;">
						<input type="password" name="pwd" id="pwd" value="">
					</td>
				</tr>
				<tr>	
					<td style="width: 100px;padding-top:5px;">
						确认密码输入：
					</td>
					<td style="width: 100px;padding-top:5px;">
						<input type="password" name="repwd" id="repwd" value="">
					</td>
				</tr>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="submit">
								<!-- 提交 -->
								<spring:message code="public.title.submit" />
							</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close" id="btnCloseExcelPwd" name="btnCloseExcelPwd">
								<!-- 取消 -->
								<spring:message code="public.title.cancle" />
							</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>

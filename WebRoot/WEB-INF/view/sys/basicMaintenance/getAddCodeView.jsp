<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<form method="post" action="/sys/basicMaintenance/saveCode" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="60">
			
			<dl>
				<dt> </dt>
				<dd><input type="hidden" value="${PARENT_CODE_NO}"  name="PARENT_CODE_NO" />
					<input type="hidden" name="CODE_ID" size="30"  />
				</dd>
			</dl>
			<ait:SyLanguage/>
			<!--<dl>
				<dt>代码中文名称:</dt>
				<dd>
					<input type="text" name="CODE_NAME_ZH" class="required textInput" size="30"  />
				</dd>
			</dl>
			
			<dl>
				<dt>代码英文名称:</dt>
				<dd>
					<input type="text" name="CODE_NAME_EN" class="textInput" size="30"  />
				</dd>
			</dl>
			<dl>
				<dt>菜单韩文名称:</dt>
				<dd>
					<input type="text" name="CODE_NAME_KR" class="textInput" size="30" />
				</dd>
			</dl>  -->
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.submit"/><!--提交--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>
</div>

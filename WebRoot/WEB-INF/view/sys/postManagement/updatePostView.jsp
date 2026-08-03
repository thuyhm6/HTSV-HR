<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/sys/postManagement/updatePostInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<input name="POST_NO" type="hidden" value="${postInfo.POST_NO}" />
		<input name="NO" type="hidden" value="${postInfo.POST_NO}" />
		<div class="pageFormContent nowrap" layoutH="97">
			<ait:SyLanguage languageNo="${postInfo.POST_NO}"/>
			<dl>
				<dt><spring:message code="sys.postManage.title.ifUsed"/><!--是否使用-->:</dt>
				<dd>
					<select name="ACTIVITY">
						<option value="1" <c:if test="${postInfo.ACTIVITY eq '1'}">selected</c:if>>
						<spring:message code="sys.affirm.title.yes"/><!--是--></option>
						<option value="0" <c:if test="${postInfo.ACTIVITY eq '0'}">selected</c:if>>
						<spring:message code="sys.affirm.title.no"/><!--否--></option>
					</select>
				</dd>
			</dl>
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="submit">
				<spring:message code="button.sys.affirm.save"/><!--保存--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>
<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<form method="post" action="/sys/basicMaintenance/editCode" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="60">
			
			<!-- <dl>
				<dt>代码ID:</dt>
				<dd><input type="hidden" value="${SY_CODE.CODE_NO}"  name="CODE_NO" />
					<input type="hidden" value="${SY_CODE.CODE_ID}"  name="CODE_ID"   size="30"  />
				</dd>
			</dl> -->
			<input type="hidden" name="NO" value="${SY_CODE.CODE_NO}"/>
			<ait:SyLanguage languageNo="${SY_CODE.CODE_NO}"/>
			<dl>
				<dt>代码Code:</dt>
				<dd>
					<input type="text" name="DESCRIPTION" value="${SY_CODE.DESCRIPTION}"/>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态-->:</dt>
				<dd>
					<select class="combox" name="ACTIVITY" id="PA_RELEVANCE_FLAG">
						<option value="0" <c:if test="${SY_CODE.ACTIVITY eq 0}">selected</c:if>>
						<spring:message code="sys.arAffirmPost.title.enable"/><!--不启用--></option>
						<option value="1" <c:if test="${SY_CODE.ACTIVITY eq 1}">selected</c:if>>
						<spring:message code="sys.arAffirmPost.title.able"/><!--启用--></option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>排序字段</dt>
				<dd><input type="" class="required digits textInput" value="${SY_CODE.ORDERNO }" name="ORDERNO" id="ORDERNO" /><dd>
			
			</dl>
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

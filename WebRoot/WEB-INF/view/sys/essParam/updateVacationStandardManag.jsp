<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<form method="post" action="/sys/essParam/editVacationStandardManage" class="pageForm required-validate" 
		onsubmit="return validateCallback(this,dialogAjaxDone);">
		<input type="hidden" name="seach_STANDARD_MANAGE_ID" value="${ManageList.STANDARD_MANAGE_ID }"/>
		<div class="pageFormContent" layoutH="90">
			<dl>
				<dt>
					<spring:message code="org.orgManage.title.ownArea"/><!--所属地区-->:
				</dt>
				<dd>
					${ManageList.WORK_AREA_NAME }
				</dd>
			</dl>
			<dl>
				<dt>
					<spring:message code="heran.ess.viewLikeLeaveApplyInfo.xiajiajizhun"/><!--休假基准-->:
				</dt>
				<dd>
					<textarea name="seach_STANDARD_EXPLAIN" rows="10" cols="42">${ManageList.STANDARD_EXPLAIN}</textarea>  
				</dd>
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

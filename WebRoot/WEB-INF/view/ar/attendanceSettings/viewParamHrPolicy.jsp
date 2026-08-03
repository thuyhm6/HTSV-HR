<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/ar/attendanceSettings/updateItemInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		
		<div class="pageFormContent nowrap" layoutH="60">
			
			<h1><spring:message code="ar.attend.title.hr_policy"/>:<br></h1>
			<dd>${itemInfo.DETAIL_CONTENT}</dd>
			
		</div>
		<div class="formBar">
			<ul>
				
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>

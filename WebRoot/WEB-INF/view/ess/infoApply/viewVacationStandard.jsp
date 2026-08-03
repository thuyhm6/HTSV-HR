<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	
		<div class="pageFormContent" layoutH="90">
			<dl>
				<dt>
					<spring:message code="heran.ess.viewLikeLeaveApplyInfo.xiajiajizhun"/><!--休假基准-->
				</dt>
				<dd>
					 ${VACATIONSTANDAR}
				</dd>
			</dl>
		</div>		
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
					<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	
</div>
<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent" layouth="10">
<c:if test="${notice.COLOR_FLAG eq '1'}">
<div style="width:530px;height:30px;font-size:14px;text-align:center;padding:15px;color: red;">${notice.TITLE}</div>
</c:if>
<c:if test="${notice.COLOR_FLAG ne '1'}">
<div style="width:530px;height:30px;font-size:14px;text-align:center;padding:15px;">${notice.TITLE}</div>
</c:if>
<div style="width:530px;height:270px;font-size:12px;padding-right:15px;padding-left:15px;line-height:20px;margin-top:30px">${notice.CONTENT}</div>
</div>
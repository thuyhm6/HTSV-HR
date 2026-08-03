<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<div style="padding-top:170px;padding-left:350px;">
	<div <c:if test="${LoginUser.language eq 'vi'}"> style="background-image:url('/resources/images/no_evs.png');width:410px;height:124px;"> </c:if>
		<c:if test="${LoginUser.language eq 'ko'}"> style="background-image:url('/resources/images/no_evs_KR.png');width:410px;height:124px;"> </c:if>
	</div>
</div>


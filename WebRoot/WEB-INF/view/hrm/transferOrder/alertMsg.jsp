<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<c:if test="${sign eq '1'}">
{
	"statusCode":"200",
	"message":"${message}",
	"navTabId":"${navTabId}",
	"rel":"${rel}",
	"callbackType":"forward",
	"forwardUrl":"${forwardUrl}"
}
 </c:if>
 <c:if test="${sign ne '1'}">
{
	"statusCode":"300",
	"message":"${message}",
	"navTabId":"${navTabId}",
	"rel":"",
	"callbackType":"closeCurrent",
	"forwardUrl":""
}
 </c:if>

 
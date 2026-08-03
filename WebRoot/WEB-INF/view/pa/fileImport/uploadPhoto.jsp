<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<c:if test="${sign eq '1'}">
{
	"statusCode":"3000",
	"message":"Upload successful",
	"rel":"",
	"callbackType":"closeCurrent",
	"forwardUrl":""
}
</c:if>
<c:if test="${sign eq '0'}">
{
	"statusCode":"300",
	"message":"The file was not uploaded, please contact the administrator ",
	"rel":"",
	"callbackType":"closeCurrent",
	"forwardUrl":""
}
</c:if>
<c:if test="${sign eq '-1'}">
{
	"statusCode":"300",
	"message":"File creation failed, please contact the administrator! ",
	"rel":"",
	"callbackType":"closeCurrent",
	"forwardUrl":""
}
</c:if>

<c:if test="${sign eq '2'}">
{
	"statusCode":"200",
	"message":"Upload successful",
	"rel":"",
	"callbackType":"closeCurrent",
	"forwardUrl":""
}
</c:if>
<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<c:if test="${sign eq '1'}">
{
	"statusCode":"3000",
	"message":"文件上传成功",
	"rel":"",
	"callbackType":"closeCurrent",
	"forwardUrl":""
}
</c:if>
<c:if test="${sign eq '0'}">
{
	"statusCode":"300",
	"message":"文件未上传,请与管理员联系",
	"rel":"",
	"callbackType":"closeCurrent",
	"forwardUrl":""
}
</c:if>
<c:if test="${sign eq '-1'}">
{
	"statusCode":"300",
	"message":"文件创建失败,请与管理员联系!",
	"rel":"",
	"callbackType":"closeCurrent",
	"forwardUrl":""
}
</c:if>

<c:if test="${sign eq '2'}">
{
	"statusCode":"200",
	"message":"文件上传成功",
	"rel":"",
	"callbackType":"closeCurrent",
	"forwardUrl":""
}
</c:if>
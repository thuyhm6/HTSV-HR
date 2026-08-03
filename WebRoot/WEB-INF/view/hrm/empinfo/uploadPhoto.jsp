<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<c:if test="${sign eq '1'}">
{
	"statusCode":"200",
	"message":"上传成功",
	"navTabId":"${navTabId}",
	"rel":"",
	"callbackType":"closeCurrent",
	"forwardUrl":"${forwardUrl}"
}
</c:if>
<c:if test="${sign eq '-1'}">
{
	"statusCode":"300",
	"message":"文件创建失败,请与管理员联系",
	"rel":"",
	"callbackType":"closeCurrent",
	"forwardUrl":""
}
</c:if>
<c:if test="${sign eq '-2'}">
{
	"statusCode":"300",
	"message":"文件传输失败,请与管理员联系",
	"rel":"",
	"callbackType":"closeCurrent",
	"forwardUrl":""
}
</c:if>
<c:if test="${sign eq '-3'}">
{
	"statusCode":"300",
	"message":"照片上传失败,请与管理员联系",
	"rel":"",
	"callbackType":"closeCurrent",
	"forwardUrl":""
}
</c:if>
<c:if test="${sign eq '-4'}">
{
	"statusCode":"300",
	"message":"上传portal失败,请与管理员联系",
	"rel":"",
	"callbackType":"closeCurrent",
	"forwardUrl":""
}
</c:if>
<c:if test="${sign eq '-5'}">
{
	"statusCode":"300",
	"message":"照片文件超过规定2M,上传失败",
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
<c:if test="${sign eq 'fail'}">
{
	"statusCode":"300",
	"message":"文件上传失败,请与管理员联系",
	"rel":"",
	"callbackType":"closeCurrent",
	"forwardUrl":""
}
</c:if>
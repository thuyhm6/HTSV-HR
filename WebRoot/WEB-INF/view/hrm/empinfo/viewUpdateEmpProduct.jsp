<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
function validateCallbackUpdateProductInfo(form, callback) {
	var $form = $("#updateProductInfo");
	if (!$form.valid()) {
		return false;
	}
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: callback || DWZ.ajaxDone,
		error: DWZ.ajaxError
	});
	return false;
}
</script>
<div class="pageContent">
<form id="updateProductInfo" method="post"
	action="/hrm/empinfo/editProductInfo"
	class="pageForm required-validate"
	onsubmit="return validateCallbackUpdateProductInfo(this, dialogAjaxDone);">
<table class="table" width="103%" layoutH="56">
		<tr>
			<th colspan="4" style="text-align:center;height:30px;font-size:14px;font-weight:bold;">兼卖产品设置
			<input type="hidden" name="PERSON_ID" value="${PERSON_ID }"/>
			</th>
		</tr>
		<c:forEach items="${codeList}" var="item" varStatus="i">
			<c:if test="${i.count%4 eq 1}">
				<tr><td width="80"><input type="checkbox" name="PRODUCT_NO"
					value="${item.CODE_NO}" <c:if test="${item.SELECTED eq 1}">Checked = "true"</c:if>/>&nbsp;&nbsp;${item.CODENAME }</td> 
			</c:if>
			<c:if test="${i.count%4 eq 0}">
				<td width="80"><input type="checkbox" name="PRODUCT_NO"
					value="${item.CODE_NO}" <c:if test="${item.SELECTED eq 1}">Checked = "true"</c:if>/>&nbsp;&nbsp;${item.CODENAME }</td></tr>
			</c:if>
			<c:if test="${i.count%4 ne 1 and i.count%4 ne 0}">
				<td width="80"><input type="checkbox" name="PRODUCT_NO"
					value="${item.CODE_NO}" <c:if test="${item.SELECTED eq 1}">Checked = "true"</c:if>/>&nbsp;&nbsp;${item.CODENAME }</td> 
			</c:if>
		</c:forEach>
</table>

<div class="formBar">
<ul>
	<li>
	<div class="buttonActive">
	<div class="buttonContent">
	<button type="submit"><spring:message
		code="public.title.submit" /><!-- 保存 --></button>
	</div>
	</div>
	</li>
	<li>
	<div class="button">
	<div class="buttonContent">
	<button type="button" class="close"><spring:message
		code="public.title.cancle" /><!-- 取消 --></button>
	</div>
	</div>
	</li>
</ul>
</div>
</form>
</div>
<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function f_save1(form) {

   var zhname = document.getElementById("proName_zh").value;
   if(zhname == ''){
   		alertMsg.error("中文名称为必填项 !");
   		return false;
   }
	var $form = $(form);
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: navTabAjaxDone || DWZ.ajaxDone,
		error: DWZ.ajaxError
	});
	return false;
}
</script>
<div class="pageContent">
	<form method="post" action="/ar/attendanceSettings/addPersonDynGroup" class="pageForm required-validate" onsubmit="return f_save1(this)">
		<div class="pageFormContent nowrap" layoutH="65">
			
			<ait:SyLanguage/>
		
			<dl style="height:auto;">
				<table>
					<tr>
						<td class="td_title" style="width:122px">小时上限</td>
						<td class="td_type"><input type="text" name="HOURS_LIMIT" id="HOURS_LIMIT" class="required number"/></td>
					</tr>
					<tr>
						<td class="td_title" style="width:122px"><!-- 描述 --><spring:message code="ar.viewcycle.title.miaoshu"/></td>
						<td class="td_type"><textarea name="DESCRIPTION" cols="60" rows="2"></textarea></td>
					</tr>
				</table>
			</dl>
		</div>
		<div class="formBar" >
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>
	</form>
</div>

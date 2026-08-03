<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../inc/initTaglibs.jsp"%>
<script type="text/javascript">
 function validateCallbackViewPopMarkToHire(form, callback) {
	
		var $form = $("#viewPopMarkToHire");
		
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

<div class="pageContent" style="overflow:scroll">


	<form id="viewPopMarkToHire" method="post" action="/hrm/transferOrder/saveSaBnIn" class="pageForm required-validate" onsubmit="return validateCallbackViewPopMarkToHire(this, dialogAjaxDone);">
	
		<div class="panel collapse" id="qualDiv" style="display:block" width="200">
			<h1>
				工资基础项目
			</h1>
			<div>
				<table width="80%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
					<c:forEach items="${paBasicItemParamList}" var="item" >
	   					 <tr><td width=120">${item.TITLE}:</td><td><input type="input" id="pa_${item.PARAM_NO}" name="pa_${item.PARAM_NO}" class="number"></td></tr>
					</c:forEach>
				</table>
			</div>
		</div>
	
	
		<div class="panel collapse" id="qualDiv" style="display:block" width="200">
			<h1>
				工资输入项目
			</h1>
			<div>
				<table width="80%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
					<c:forEach items="${saParamItemParamList}" var="item" >
	   					 <tr><td width=120">${item.TITLE}:</td><td><input type="input" id="sa_${item.PARAM_NO}" name="sa_${item.PARAM_NO}" class="number"></td></tr>
					</c:forEach>
				</table>
			</div>
		</div>
		
		 		
		
		<div class="panel collapse"  id="languageDiv" style="display:block" width="200">
			<h1>
				<input type="hidden" id="IDCARD_NO" name="IDCARD_NO" size="10" value="${IDCARD_NO}" />
				<input type="hidden" id="FOREIGNER_IDCARD_NO" name="FOREIGNER_IDCARD_NO" size="10" value="${FOREIGNER_IDCARD_NO}" />
				<input type="hidden" id="PASSPORT_NO" name="PASSPORT_NO" size="10" value="${PASSPORT_NO}" />
				<input type="hidden" id="JOIN_COMPANY_DATE" name="JOIN_COMPANY_DATE" size="10" value="${JOIN_COMPANY_DATE}" />
				奖金输入项目
			</h1>
			<div>
				<table width="80%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
					<c:forEach items="${bnParamItemParamList}" var="item" >
	   					 <tr><td width="120">${item.TITLE}:</td><td width="20"><input type="input" id="bn_${item.PARAM_NO}" name="bn_${item.PARAM_NO}" class="number"></td></tr>
					</c:forEach>
				</table>
			</div>
		</div>
		
		
		 
		<div class="panel collapse"  id="languageDiv" style="display:block" width="200">
			<h1>
				保险输入项目
			</h1>
			<div>
				<table width="80%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
					<c:forEach items="${inParamItemParamList}" var="item" >
	   					 <tr><td  width="120">${item.TITLE}:</td><td><input type="input" id="in_${item.PARAM_NO}" name="in_${item.PARAM_NO}" class="number"></td></tr>
					</c:forEach>
				</table>
			</div>
		</div>
		
		
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.submit"/><!-- 保存 --></button></div></div></li>
			<li>
				<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div>
			</li>
		</ul>
	</div>
	</form>	
</div>
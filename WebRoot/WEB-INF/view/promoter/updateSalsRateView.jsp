<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/promoter/updateSalsRate" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
	    <input name="PAY_AREA_CD" type="hidden" value="${itemInfo.PAY_AREA_CD}" />
	    <input name="PROD_TP" type="hidden" value="${itemInfo.PROD_TP}" />
		
		<div class="pageFormContent nowrap" layoutH="60">
			<dl>
				<dt>大区</dt>
				<dd><input name="PROD_ID" value="${itemInfo.PAY_AREA_NM}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>产品类型</dt>
				<dd><input name="PROD_ID" value="${itemInfo.PROD_TP_NM}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>起始达成率</dt>
				<dd style="width:60px"><input name="FRUT_STRT_RNG" value="${itemInfo.FRUT_STRT_RNG}"  readonly="true"/></dd>
			</dl>
			<dl>
				<dt>截止达成率</dt>
				<dd style="width:60px"><input name="FRUT_END_RNG" value="${itemInfo.FRUT_END_RNG}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></dd>
			</dl>
			<dl>
				<dt>参数类型</dt>
				<dd>
					<select name="PARAM_TP">
						<option value="1" <c:if test="${itemInfo.PARAM_TP eq '1'}">selected</c:if>>1</option>
						<option value="-1" <c:if test="${itemInfo.PARAM_TP eq '-1'}">selected</c:if>>-1</option>
						<option value="0" <c:if test="${itemInfo.PARAM_TP eq '0'}">selected</c:if>>0</option>
				    </select>
				</dd>
			</dl>
			<dl>
				<dt>调整系数</dt>
				<dd style="width:60px"><input name="PARA_RAT" value="${itemInfo.PARA_RAT}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></dd>
			</dl>
			<dl>
				<dt>参数</dt>
				<dd style="width:60px"><input name="PARAM_VAL" value="${itemInfo.PARAM_VAL}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></dd>
			</dl>
			<dl>
				<dt>状态</dt>
				<dd>
					<select name="USE_YN">
						<option value="Y" <c:if test="${itemInfo.USE_YN eq 'Y'}">selected</c:if>>
						<spring:message code="sys.affirm.title.yes"/><!--是--></option>
						<option value="N" <c:if test="${itemInfo.USE_YN eq 'N'}">selected</c:if>>
						<spring:message code="sys.affirm.title.no"/><!--否--></option>
					</select>
				</dd>
			</dl>
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="submit">
				<spring:message code="button.sys.affirm.save"/><!--保存--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>
<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="icon" href="/pa/bonus/viewBonusFormularTool" target="dialog" width="800" height="410"><span>
			<spring:message code="pa.insurance.title.tool"/><!--工具--></span></a></li>
		</ul>
	</div>
	<form method="post" id="bonusFormularForm" action="/pa/bonus/updateBonusFormulaInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<input type="hidden" name="ITEM_NO" value="${formula.ITEM_NO}"/>
		<input type="hidden" name="CPNY_ID" value="${formula.CPNY_ID}"/>
		<input type="hidden" name="FORMULAR_NO" value="${formula.FORMULAR_NO}"/>
		<div class="pageFormContent" layoutH="60">
			<dl class="nowrap">
				<dt><spring:message code="pa.insurance.title.condition"/><!--条件-->：</dt>
				<dd><textarea name="CONDITION" id="CONDITION" cols="130" rows="4">${formula.CONDITION}</textarea></dd>
			</dl>
			<dl class="nowrap">
				<dt><spring:message code="pa.insurance.title.formula"/><!--公式-->：</dt>
				<dd><textarea name="FORMULAR" id="FORMULAR" cols="130" rows="4" class="required">${formula.FORMULAR}</textarea></dd>
			</dl>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
					<spring:message code="pa.insurance.title.submit"/><!--保存--></button></div></div></li>
				</ul>
			</div>
		</div>
	</form>
	<form id="pagerForm" method="post" action="/pa/bonus/viewBonusFormulaList?ITEM_NO=${ITEM_NO}&CPNY_ID=${CPNY_ID}">
	</form>
</div>
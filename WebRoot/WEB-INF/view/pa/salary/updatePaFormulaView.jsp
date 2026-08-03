<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" id="paformularForm" action="/pa/salary/updatePaFormulaInfo" class="pageForm required-validate" 
	      onsubmit="return validateCallback(this, navTabAjaxDone);">
	<div class="formBar">
		<ul class="toolBar">
			<li><a class="icon" href="/pa/salary/viewPaFormularTool" target="dialog" width="950" height="410"><span>
			<spring:message code="pa.insurance.title.tool"/><!--工具--></span></a></li>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
                <spring:message code="pa.insurance.title.submit"/><!--保存--></button></div></div></li>
		</ul>
	</div>
		<input type="hidden" name="PA_ITEM" value="${paFormulaInfo.ITEM_NO}"/>
		<input type="hidden" name="CPNY_ID" value="${paFormulaInfo.CPNY_ID}"/>
		<input type="hidden" name="FORMULAR_NO" value="${paFormulaInfo.FORMULAR_NO}"/>
		<div class="pageFormContent" layoutH="60">
		<dl style="height:auto;width:100%;">
			<table>
				<tr>
					<td class="td_title" style="width:122px;">
						<spring:message code="pa.insurance.title.condition"/><!--条件-->：
					</td>
					<td class="td_thype">
						<textarea name="CONDITION" id="CONDITION" cols="130" rows="4">${paFormulaInfo.CONDITION}</textarea>
					</td>
				</tr>
			</table>
		</dl>
		<dl style="height:auto;width:100%;">
			<table>
				<tr>
					<td class="td_title" style="width:122px;">
						<spring:message code="pa.insurance.title.formula"/><!--公式-->：
					</td>
					<td class="td_thype">
						<textarea name="FORMULAR" id="FORMULAR" cols="130" rows="4" class="required">${paFormulaInfo.FORMULAR}</textarea>
					</td>
				</tr>
			</table>
		</dl>
		</div>
		</div>
	</form>
	<form id="pagerForm" method="post" action="/pa/salary/viewPaFormulaList?ITEM_NO=${ITEM_NO }&CPNY_ID=${CPNY_ID}">
	</form>
</div>
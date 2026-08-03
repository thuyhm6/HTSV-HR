<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<div class="pageContent">
	<form method="post" id="paformularForm" action="/pa/salary/addPaFormulaInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<div class="formBar">
		<ul class="toolBar">
			<li><a class="icon" href="/pa/salary/viewPaFormularTool" target="dialog" width="950" height="410"><span>
			<spring:message code="pa.insurance.title.tool"/><!--工具--></span></a></li>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
					<spring:message code="pa.insurance.title.submit"/><!--保存--></button></div></div></li>
		</ul>
	</div>
	<input type="hidden" name="ITEM_NO" value="${ITEM_NO}"/>
		<input type="hidden" name="CPNY_ID" value="${CPNY_ID}"/>
		<div class="pageFormContent" >
		<dl style="height:auto;width:100%;">
			<table>
				<tr>
					<td class="td_title" style="width:122px;">
						<spring:message code="pa.insurance.title.condition"/><!--条件-->：
					</td>
					<td class="td_thype">
						<textarea name="CONDITION" id="CONDITION" cols="100" rows="4"></textarea>
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
						<textarea name="FORMULAR" id="FORMULAR" cols="100" rows="4" class="required"></textarea>
					</td>
				</tr>
			</table>
		</dl>
		</div>
		</div>
	</form>
	<form id="pagerForm" method="post" action="/pa/salary/viewPaFormulaList?ITEM_NO=${ITEM_NO}&CPNY_ID=${CPNY_ID}">
	</form>
</div>
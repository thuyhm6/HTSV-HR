 <%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<div class="pageContent">
	<div class="formBar">
		<ul class="toolBar">
			<li><a class="icon" href="/pa/insurance/viewInsuranceFormularTool" target="dialog" width="800" height="410"><span>
			<spring:message code="pa.insurance.title.tool"/><!--工具--></span></a></li>
		</ul>
	</div>
	<form method="post" id="isformularForm" action="/pa/insurance/addInsuranceFormulaInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<input type="hidden" name="ITEM_NO" value="${ITEM_NO}"/>
		<input type="hidden" name="CPNY_ID" value="${CPNY_ID}"/>
		<div class="pageFormContent nowrap">
			<dl style="height:auto">
			<table>
				<tr>
					<td class="td_title" style="width:122px;">
						<spring:message code="pa.insurance.title.condition"/><!--条件-->：
					</td>
					<td class="td_thype">
						<textarea name="CONDITION" id="CONDITION" cols="130" rows="4" ></textarea>
					</td>
				</tr>
			</table>
			</dl>
			<dl style="height:auto">
			<table>
				<tr>
					<td class="td_title" style="width:122px;">
						<spring:message code="pa.insurance.title.formula"/><!--公式-->：
					</td>
					<td class="td_thype">
						<textarea name="FORMULAR" id="FORMULAR" cols="130" rows="4" class="required"></textarea>
					</td>
				</tr>
			</table>
			</dl>
			</div>
			<div class="formBar" layoutH="206">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
					<spring:message code="public.title.submit"/><!--保存--></button></div></div></li>
				</ul>
			</div>
	
	</form>
	<form id="pagerForm" method="post" action="/pa/insurance/viewInsuranceFormulaList?ITEM_NO=${ITEM_NO}&CPNY_ID=${CPNY_ID}">
	</form>
</div>
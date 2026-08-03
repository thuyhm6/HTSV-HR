<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<div class="formBar">
		<ul class="toolBar">
			<li><a class="icon" href="/ar/attendanceSettings/ViewFormularTool" target="dialog" width="810" height="410"><span><!-- 工具 --><spring:message code="ar.viewSummaryFormula.title.tool"/></span></a></li>
			
		</ul>
	</div>
	<form method="post" id="formularForm" action="/ar/attendanceSettings/addSummaryFormulaInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<input type="hidden" name="ITEM_NO" value="${ITEM_NO}"/>
		<input type="hidden" name="CPNY_ID" value="${CPNY_ID}"/>
		
			<div class="pageFormContent nowrap">
			<dl style="height:auto">
								<table>
								<tr>
								<td class="td_title" style="width:122px;"><!-- 条件 --><spring:message code="ar.viewSummaryFormula.title.tiaojian"/>：</td>
								<td class="td_type">
									<textarea name="CONDITION" id="CONDITION" cols="100" rows="4"></textarea>
								</td>
								</tr>
							</table>
			</dl>
			<dl style="height:auto">
								<table>
								<tr>
								<td class="td_title" style="width:122px;"><!-- 公式 --><spring:message code="ar.viewSummaryFormula.title.gongshi"/>：</td>
								<td class="td_type">
									<textarea name="FORMULAR" id="FORMULAR" cols="100" rows="4" class="required"></textarea>
								</td>
								</tr>
							</table>
			</dl>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				</ul>
			</div>
	</form>
	<form id="pagerForm" method="post" action="/ar/attendanceSettings/viewFormulaList?ITEM_NO=${ITEM_NO}&CPNY_ID=${CPNY_ID}">
	</form>
</div>
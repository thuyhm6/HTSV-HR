<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	
	<c:set value="ajax" var="add_tab"/>
	<c:set value="jbsxBoxItemFormula" var="add_rel"/>
	<c:set value="200" var="add_width"/>
	<c:set value="100" var="add_height"/>
	<c:set value="/ar/attendanceSettings/addSummaryFormulaView?ITEM_NO=${ITEM_NO}&CPNY_ID=${CPNY_ID}" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/ar/attendanceSettings/deleteSummaryFormulaInfo?FORMULAR_NO={FORMULAR_NO}&CPNY_ID=${CPNY_ID}" var="delete_Url"/>
	<c:set value="ajaxSelect" var="edit_tab"/>
	<c:set value="jbsxBoxItemFormula" var="edit_rel"/>
	<c:set value="500" var="edit_width"/>
	<c:set value="400" var="edit_height"/>
	<c:set value="/ar/attendanceSettings/updateSummaryFormulaView?FORMULAR_NO={FORMULAR_NO}&CPNY_ID=${CPNY_ID}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	
	<table class="table" width="99%" layoutH="80" nowrapTD="false">
		<thead>
			<tr>
				<th width="5"><!-- 序号 --><spring:message code="ar.viewcycle.title.xuhao"/></th>
				<th width="40"><!-- 条件 --><spring:message code="ar.viewSummaryFormula.title.tiaojian"/></th>
				<th width="70"><!-- 公式 --><spring:message code="ar.viewSummaryFormula.title.gongshi"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${formulas}" var="formula" varStatus="ix">
				<tr target="FORMULAR_NO" rel="${formula.FORMULAR_NO}">
					<td>${ix.index + 1}</td>
					<td>${formula.CONDITION}</td>
					<td>${formula.FORMULAR}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<form id="pagerForm" method="post" action="/ar/attendanceSettings/viewFormulaList?ITEM_NO=${ITEM_NO}&CPNY_ID=${CPNY_ID}">
	</form>
</div>
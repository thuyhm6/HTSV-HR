<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
		<c:set value="ajax" var="add_tab"/>
		<c:set value="800" var="add_width"/>
		<c:set value="300" var="add_height"/>
		<c:set value="viewPaDayFormulaData " var="add_rel"/>
		<c:set value="/pa/salary/addPaDayFormulaView?ITEM_NO=${ITEM_NO}&CPNY_ID=${CPNY_ID}" var="add_Url"/>
		<c:set value="ajaxTodo" var="delete_tab"/>
		<c:set value="/pa/salary/deletePaDayFormulaInfo?FORMULAR_NO={FORMULAR_NO}" var="delete_Url"/>
		<c:set value="ajaxSelect" var="edit_tab"/>
		<c:set value="800" var="edit_width"/>
		<c:set value="400" var="edit_height"/>
		<c:set value="viewPaDayFormulaData " var="edit_rel"/>
		<c:set value="/pa/salary/updatePaDayFormulaView?FORMULAR_NO={FORMULAR_NO}&ITEM_NO=${ITEM_NO}&CPNY_ID=${CPNY_ID}" var="edit_Url"/>
		<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
		<table class="table" width="99%" layoutH="260" nowrapTD="false">
		<thead>
			<tr>
				<th width="50"><spring:message code="pa.salary.title.order"/><!--顺序--></th>
				<th width="100"><spring:message code="pa.insurance.title.condition"/><!--条件--></th>
				<th width="150"><spring:message code="pa.insurance.title.formula"/><!--公式--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paDayFormulaList}" var="list" varStatus="i">
				<tr target="FORMULAR_NO" rel="${list.FORMULAR_NO}&&ITEM_NO=${list.ITEM_NO}" >
					<td>
					<c:if test="${i.first and not i.last}">
								<a
									href="/pa/salary/updatePaDayFormulaByConditionSeq?type=0&&formular_no=${list.FORMULAR_NO }&&condition_seq=${list.CONDITION_SEQ }&&item_no=${list.ITEM_NO}"
									target="ajaxTodo"> <img
										src="/resources/images/button/down.gif" style="cursor: hand" />
								</a>
							</c:if>
							<c:if test="${i.last and not i.first}">
								<a
									href="/pa/salary/updatePaDayFormulaByConditionSeq?type=1&&formular_no=${list.FORMULAR_NO }&&condition_seq=${list.CONDITION_SEQ }&&item_no=${list.ITEM_NO}"
									target="ajaxTodo"> <img
										src="/resources/images/button/up.gif" style="cursor: hand" /> </a>
							</c:if>
							<c:if test="${not i.first and not i.last}">
								<a
									href="/pa/salary/updatePaDayFormulaByConditionSeq?type=0&&formular_no=${list.FORMULAR_NO }&&condition_seq=${list.CONDITION_SEQ }&&item_no=${list.ITEM_NO}"
									target="ajaxTodo"> <img
										src="/resources/images/button/down.gif" style="cursor: hand" />
								</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
								<a
									href="/pa/salary/updatePaDayFormulaByConditionSeq?type=1&&formular_no=${list.FORMULAR_NO }&&condition_seq=${list.CONDITION_SEQ }&&item_no=${list.ITEM_NO}"
									target="ajaxTodo"> <img
										src="/resources/images/button/up.gif" style="cursor: hand" /> </a>
							</c:if>
					</td>
					<td>${list.CONDITION}</td>
					<td>${list.FORMULAR}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<form id="pagerForm" method="post" action="/pa/salary/viewPaDayFormulaList?ITEM_NO=${ITEM_NO}&CPNY_ID=${CPNY_ID}">
	</form>
</div>
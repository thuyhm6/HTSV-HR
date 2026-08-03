<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
			
		<c:set value="ajax" var="add_tab"/>
		<c:set value="800" var="add_width"/>
		<c:set value="300" var="add_height"/>
		<c:set value="ViewInsuranceFormula" var="add_rel"/>
		<c:set value="/pa/insurance/addInsuranceFormulaView?ITEM_NO=${ITEM_NO}&CPNY_ID=${CPNY_ID}" var="add_Url"/>
		<c:set value="ajaxTodo" var="delete_tab"/>
		<c:set value="/pa/insurance/deleteInsuranceFormulaInfo?FORMULAR_NO={FORMULAR_NO}" var="delete_Url"/>
		<c:set value="ajaxSelect" var="edit_tab"/>
		<c:set value="800" var="edit_width"/>
		<c:set value="400" var="edit_height"/>
		<c:set value="ViewInsuranceFormula " var="edit_rel"/>
		<c:set value="/pa/insurance/updateInsuranceFormulaView?FORMULAR_NO={FORMULAR_NO}&ITEM_NO=${ITEM_NO}&CPNY_ID=${CPNY_ID}" var="edit_Url"/>
		<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
		
	<table class="table" width="100%" layoutH="206" nowrapTD="false">
		<thead>
			<tr>
				<th width="8%"><spring:message code="pa.insurance.title.caculateOrder"/><!--计算顺序--></th>
				<th width="32%"><spring:message code="pa.insurance.title.condition"/><!--条件--></th>
				<th width="60%"><spring:message code="pa.insurance.title.formula"/><!--公式--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${insuranceFormulaList}" var="formula" varStatus="i">
				<tr style="height:40px" target="FORMULAR_NO" rel="${formula.FORMULAR_NO}">
					<c:if test="${toolbarInfo.UPDATER == '1' || toolbarInfo.DELETER == '1' || toolbarInfo.INSERTR == '1'}">
						<td>
							<c:if test="${i.first and not i.last}">
									<a
										href="/pa/insurance/updateIsFormulaByConditionSeq?type=0&&formular_no=${formula.FORMULAR_NO }&&condition_seq=${formula.CONDITION_SEQ }&&item_no=${formula.ITEM_NO}"
										target="ajaxTodo"> <img
											src="/resources/images/button/down.gif" style="cursor: hand" />
									</a>
								</c:if>
								<c:if test="${i.last and not i.first}">
									<a
										href="/pa/insurance/updateIsFormulaByConditionSeq?type=1&&formular_no=${formula.FORMULAR_NO }&&condition_seq=${formula.CONDITION_SEQ }&&item_no=${formula.ITEM_NO}"
										target="ajaxTodo"> <img
											src="/resources/images/button/up.gif" style="cursor: hand" /> </a>
								</c:if>
								<c:if test="${not i.first and not i.last}">
									<a
										href="/pa/insurance/updateIsFormulaByConditionSeq?type=0&&formular_no=${formula.FORMULAR_NO }&&condition_seq=${formula.CONDITION_SEQ }&&item_no=${formula.ITEM_NO}"
										target="ajaxTodo"> <img
											src="/resources/images/button/down.gif" style="cursor: hand" />
									</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
									<a
										href="/pa/insurance/updateIsFormulaByConditionSeq?type=1&&formular_no=${formula.FORMULAR_NO }&&condition_seq=${formula.CONDITION_SEQ }&&item_no=${formula.ITEM_NO}"
										target="ajaxTodo"> <img
											src="/resources/images/button/up.gif" style="cursor: hand" /> </a>
								</c:if>
						</td>
					</c:if>
					<c:if test="${toolbarInfo.UPDATER != '1' && toolbarInfo.DELETER != '1' && toolbarInfo.INSERTR != '1'}">
						<td style="text-align: center">${i.index+1 }</td>
					</c:if>
					<td>
					<dt style="padding: 1px;">
					${formula.CONDITION}
					</dt>
					</td>
					<td><dt style="padding: 1px;">${formula.FORMULAR}</dt></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<form id="pagerForm" method="post" action="/pa/insurance/viewInsuranceFormulaList?ITEM_NO=${ITEM_NO}&CPNY_ID=${CPNY_ID}">
	</form>
</div>
<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
		<c:set value="ajax" var="add_tab"/>
		<c:set value="800" var="add_width"/>
		<c:set value="300" var="add_height"/>
		<c:set value="viewPaFormulaData " var="add_rel"/>
		<c:set value="/pa/salary/addPaFormulaView?ITEM_NO=${ITEM_NO}&CPNY_ID=${CPNY_ID}" var="add_Url"/>
		<c:set value="ajaxTodo" var="delete_tab"/>
		<c:set value="/pa/salary/deletePaFormulaInfo?FORMULAR_NO={FORMULAR_NO}" var="delete_Url"/>
		<c:set value="ajaxSelect" var="edit_tab"/>
		<c:set value="800" var="edit_width"/>
		<c:set value="400" var="edit_height"/>
		<c:set value="viewPaFormulaData " var="edit_rel"/>
		<c:set value="/pa/salary/updatePaFormulaView?FORMULAR_NO={FORMULAR_NO}&ITEM_NO=${ITEM_NO}&CPNY_ID=${CPNY_ID}" 
		       var="edit_Url"/>
		<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
		 <div style = "padding-top: 5px;">
			<h2 class="thisPageStyle_pa0131"
			style="background-image: url('/resources/css/dwzUI/themes/partner/images/li_prent.jpg'); background-repeat: no-repeat">
			&nbsp;&nbsp;${ITEM_NAME}</h2>
		</div>
	<table class="table" width="100%" layoutH="120" nowrapTD="false">
		<thead>
			<tr>
				<th width="8%"><spring:message code="pa.insurance.title.caculateOrder"/><!--计算顺序--></th>
				<th width="42%"><spring:message code="pa.insurance.title.condition"/><!--条件--></th>
				<th width="50%"><spring:message code="pa.insurance.title.formula"/><!--公式--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paFormulaList}" var="list" varStatus="i">
				<tr target="FORMULAR_NO" rel="${list.FORMULAR_NO}&&ITEM_NO=${list.ITEM_NO }" >
					<c:if test="${toolbarInfo.UPDATER == '1' || toolbarInfo.DELETER == '1' || toolbarInfo.INSERTR == '1'}">
						<td>
							<c:if test="${i.first and not i.last}">
									<a
										href="/pa/salary/updatePaFormulaByConditionSeq?type=0&&formular_no=${list.FORMULAR_NO }&&condition_seq=${list.CONDITION_SEQ }&&item_no=${list.ITEM_NO}"
										target="ajaxTodo"> <img
											src="/resources/images/button/down.gif" style="cursor: hand" />
									</a>
								</c:if>
								<c:if test="${i.last and not i.first}">
									<a
										href="/pa/salary/updatePaFormulaByConditionSeq?type=1&&formular_no=${list.FORMULAR_NO }&&condition_seq=${list.CONDITION_SEQ }&&item_no=${list.ITEM_NO}"
										target="ajaxTodo"> <img
											src="/resources/images/button/up.gif" style="cursor: hand" /> </a>
								</c:if>
								<c:if test="${not i.first and not i.last}">
									<a
										href="/pa/salary/updatePaFormulaByConditionSeq?type=0&&formular_no=${list.FORMULAR_NO }&&condition_seq=${list.CONDITION_SEQ }&&item_no=${list.ITEM_NO}"
										target="ajaxTodo"> <img
											src="/resources/images/button/down.gif" style="cursor: hand" />
									</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
									<a
										href="/pa/salary/updatePaFormulaByConditionSeq?type=1&&formular_no=${list.FORMULAR_NO }&&condition_seq=${list.CONDITION_SEQ }&&item_no=${list.ITEM_NO}"
										target="ajaxTodo"> <img
											src="/resources/images/button/up.gif" style="cursor: hand" /> </a>
								</c:if>
						</td>
					</c:if>
					<c:if test="${toolbarInfo.UPDATER != '1' && toolbarInfo.DELETER != '1' && toolbarInfo.INSERTR != '1'}">
						<td style="text-align: center">${i.index+1 }</td>
					</c:if>
					<td><dt style="padding: 1px;">${list.CONDITION}</dt></td>
					<td><dt style="padding: 1px;">${list.FORMULAR}</dt></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<form id="pagerForm" method="post" action="/pa/salary/viewPaFormulaList?ITEM_NO=${ITEM_NO}&CPNY_ID=${CPNY_ID}">
	</form>
</div>
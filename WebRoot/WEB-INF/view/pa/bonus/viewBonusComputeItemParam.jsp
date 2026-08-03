<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">

	<form onsubmit="return navTabSearch(this);"
		action="/pa/bonus/viewBonusComputeItemParam?type=1" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<spring:message code="pa.insurance.title.projectName" />
						<!--项目名称-->
						：
						<input type="text" name="seach_ALIAS" value="${ALIAS}" />
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search" />
									<!--检索-->
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">
	<c:set
		value="/pa/bonus/addBonusComputeItemParamView?TABLE_NAME=PA_HR_V"
		var="add_Url" />
	<c:set value="600" var="add_width" />
	<c:set value="400" var="add_height" />
	<c:set value="/pa/bonus/deleteBonusComputeItemParamInfo?PARAM_NO={sid}"
		var="delete_Url" />
	<c:set value="/pa/bonus/updateBonusComputeItemParamView?PARAM_NO={sid}"
		var="edit_Url" />
	<c:set value="600" var="edit_width" />
	<c:set value="400" var="edit_height" />
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>

	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="50">
					<spring:message code="sys.basic.title.companyName" />
					<!--公司名称-->
				</th>
				<th width="50">
					<spring:message code="pa.insurance.title.projectName" />
					<!--项目名称-->
				</th>
				<th width="50">
					<spring:message code="pa.insurance.title.caculateOrder" />
					<!--计算顺序-->
				</th>
				<th width="50">
					<spring:message code="pa.insurance.title.precision" />
					<!--精度-->
				</th>
				<th width="50">
					<spring:message code="pa.insurance.title.carry" />
					<!--进位-->
				</th>
					<th width="50">
					<spring:message code="pa.insurance.title.relatedWithSalary"/><!--是否与工资有关联-->
				</th>
				</th>
					<th width="50">
					<spring:message code="pa.title.message.bonusTitle.bonusmonth"/><!--是否与工资有关联-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="item" varStatus="i">

				<tr target="sid" rel="${item.PARAM_NO}&CPNY_ID=${item.CPNY_ID}">
					<td class="td_center">
						${item.CPNY_NAME}
					</td>
					<td class="td_center">
						${item.ALIAS_NAME}
					</td>
					<td class="td_center">
						<c:if test="${i.first and not i.last}">
							<a
								href="/pa/bonus/updateBonusItemParamCalcuOrder?type=0&&param_no=${item.PARAM_NO }&&calcu_order=${item.CALCU_ORDER }"
								target="ajaxTodo"> <img
									src="/resources/images/button/down.gif" style="cursor: hand" />
							</a>
						</c:if>
						<c:if test="${i.last and not i.first}">
							<a
								href="/pa/bonus/updateBonusItemParamCalcuOrder?type=1&&param_no=${item.PARAM_NO }&&calcu_order=${item.CALCU_ORDER }"
								target="ajaxTodo"> <img
									src="/resources/images/button/up.gif" style="cursor: hand" /> </a>
						</c:if>
						<c:if test="${not i.first and not i.last}">
							<a
								href="/pa/bonus/updateBonusItemParamCalcuOrder?type=0&&param_no=${item.PARAM_NO }&&calcu_order=${item.CALCU_ORDER }"
								target="ajaxTodo"> <img
									src="/resources/images/button/down.gif" style="cursor: hand" />
							</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
							<a
								href="/pa/bonus/updateBonusItemParamCalcuOrder?type=1&&param_no=${item.PARAM_NO }&&calcu_order=${item.CALCU_ORDER }"
								target="ajaxTodo"> <img
									src="/resources/images/button/up.gif" style="cursor: hand" /> </a>
						</c:if>
					</td>
					<td class="td_center">
						${item.PRICISION}
					</td>
					<td class="td_center">
						${item.CARRY_BIT}
					</td>
					<td class="td_center">
						${item.PA_RELEVANCE_FLAG}
					</td>
					<td class="td_center">
						${item.PA_BONUS_MONTH}
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
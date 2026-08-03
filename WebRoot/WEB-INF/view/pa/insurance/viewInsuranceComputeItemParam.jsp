<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/insurance/viewInsuranceComputeItemParam?type=1" method="post">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->：
					<input type="text" name="seach_KEY" value="${KEY }"/>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.search"/><!--检索--></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>


<div class="pageContent">

	<c:set value="/pa/insurance/addInsuranceComputeItemParamView" var="add_Url"/>
	<c:set value="600" var="add_width"/>
	<c:set value="450" var="add_height"/>
	<c:set value="/pa/insurance/deleteInsuranceComputeItemParamInfo?PARAM_NO={PARAM_NO}" var="delete_Url"/>
	<c:set value="/pa/insurance/updateInsuranceComputeItemParamView?PARAM_NO={PARAM_NO}" var="edit_Url"/>
	<c:set value="600" var="edit_width"/>
	<c:set value="450" var="edit_height"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	
	
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="50"><spring:message code="pa.insurance.title.company"/><!--公司--></th>
				<th width="50"><spring:message code="pa.insurance.title.projectName"/><!--项目名称--></th>
				<!--<th width="80"><spring:message code="pa.insurance.title.relatedWithSalary"/>是否与工资有关联--></th>
				<th width="50"><spring:message code="pa.insurance.title.precision"/><!--精度--></th>
				<th width="50"><spring:message code="pa.insurance.title.carry"/><!--进位--></th>
				<th width="50"><spring:message code="pa.insurance.title.caculateOrder"/><!--计算顺序--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${isItemPramList}" var="list" varStatus="i">
				<tr target="PARAM_NO" rel="${list.PARAM_NO}&ITEM_NO=${list.ITEM_NO }" >
					<td>${list.CPNY_NAME}</td>
					<td>${list.ALIAS_NAME}</td>
					<!--<td>
						<c:if test="${list.PA_RELEVANCE_FLAG==0}">
						<spring:message code="pa.insurance.title.no"/>否</c:if>
						<c:if test="${list.PA_RELEVANCE_FLAG==1}">
						<spring:message code="pa.insurance.title.yes"/><!--是</c:if>
					</td>-->
					<td>${list.PRICISION}</td>
					<td>${list.CARRY_BIT}</td>
					<td>
						<c:if test="${i.first and not i.last}">
							<a href="/pa/insurance/updateICInfoByCalcuOrder?type=0&&param_no=${list.PARAM_NO }&&calcu_order=${list.CALCU_ORDER }" target="ajaxTodo">
								<img src="/resources/images/button/down.gif" style="cursor:hand"/>
							</a>
						</c:if>
						<c:if test="${i.last and not i.first}">
							<a href="/pa/insurance/updateICInfoByCalcuOrder?type=1&&param_no=${list.PARAM_NO }&&calcu_order=${list.CALCU_ORDER }" target="ajaxTodo">
								<img src="/resources/images/button/up.gif" style="cursor:hand"/>
							</a>
						</c:if>
						<c:if test="${not i.first and not i.last}">
							<a href="/pa/insurance/updateICInfoByCalcuOrder?type=0&&param_no=${list.PARAM_NO }&&calcu_order=${list.CALCU_ORDER }" target="ajaxTodo">
								<img src="/resources/images/button/down.gif" style="cursor:hand"/>
							</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
							<a href="/pa/insurance/updateICInfoByCalcuOrder?type=1&&param_no=${list.PARAM_NO }&&calcu_order=${list.CALCU_ORDER }" target="ajaxTodo">
								<img src="/resources/images/button/up.gif" style="cursor:hand"/>
							</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>	
</div>
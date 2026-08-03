<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/pa/insurance/viewInsuranceComputeItem" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->：
						<input type="text" name="seach_ITEM_NAME" value="${ITEM_NAME}" />
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search"/><!--检索-->
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
	<c:set value="600" var="add_width" />
	<c:set value="400" var="add_height" />
	<c:set value="/pa/insurance/addInsuranceComputeItemView" var="add_Url" />
	<c:set value="/pa/insurance/deleteInsuranceComputeItemInfo?NO={sid}" var="delete_Url" />
	<c:set value="/pa/insurance/updateInsuranceComputeItemView?NO={sid}" var="edit_Url" />
	<c:set value="600" var="edit_width" />
	<c:set value="400" var="edit_height" />
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>

	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="150">
					<spring:message code="pa.insurance.title.projectID"/><!--项目ID-->
				</th>
				<th width="150">
					<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->
				</th>
				<th width="120">
					<spring:message code="pa.insurance.title.dataType"/><!--数据类型-->
				</th>
				<th width="100">
					<spring:message code="pa.insurance.title.description"/><!--描述-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr target="sid" rel="${item.ITEM_NO}">
					<td>
						${item.ITEM_ID}
					</td>
					<td>
						${item.ITEM_NAME}
					</td>
					<td>
						<c:if test="${item.DATATYPE eq 'NUMBER(14,4)' }">
						<spring:message code="pa.insurance.title.numberType"/><!--数字类型--></c:if>
						<c:if test="${item.DATATYPE eq 'VARCHAR(100)' }">
						<spring:message code="pa.insurance.title.varcharType"/><!--字符型--></c:if>
					</td>
					<td>
						${item.DESCR}
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/insurance/viewInsuranceComputeItem" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
<form id="viewTempSaleState" onsubmit="return navTabSearch(this);"
	action="/pa/tempsale/viewTempSaleState" rel="pagerForm" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td>月份</td>
		<td><ait:dateProMonth yearName="seach_YEAR" yearSelected="${YEAR}"
			monthName="seach_MONTH" monthSelected="${MONTH}" /></td>
	</tr>
</table>
<div class="subBar">
<ul>
	<li>
	<div class="buttonActive">
	<div class="buttonContent">
	<button type="submit"><spring:message
		code="public.title.search" /><!-- 检索 --></button>
	</div>
	</div>
	</li>
</ul>
</div>
</div>
</form>
</div>
<div class="pageContent">
<table class="table" width="100%" layoutH="180">
	<thead>
		<tr>
			<th>NO</th>
			<th>大区</th>
			<th>工资月</th>
			<th>工资计算</th>
			<th>工资确认</th>
			<th>工资传送财务</th>
			<th>预提确认</th>
			<th>预提传送财务</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${paTempSalesState}" var="item" varStatus="i">
			<tr>
				<td class="td_center">${i.count }</td>
				<td class="td_center">${item.PAY_AREA_NAME }</td>
				<td class="td_center">${item.PA_MONTH }</td>
				<td class="td_center">${item.CALC_FLAG }</td>
				<td class="td_center">
					<c:if test="${item.AFFIRM_FLAG eq '通过' or item.AFFIRM_FLAG eq '否决' or item.AFFIRM_FLAG eq '审批中'}">
						<a href="/pa/tempsale/viewTempSaleSummaryInfoList?pageNum=1&PAY_DATE=${item.PA_MONTH}&PAY_AREA_CD=${item.PAY_AREA_CD}&ACCRUAL_FLAG=CONFIRM_N"
							target="navTab" 
								rel="pa0901_affirm">${item.AFFIRM_FLAG }</a>
					</c:if>
					<c:if test="${item.AFFIRM_FLAG eq '未确认'}">
						${item.AFFIRM_FLAG }
					</c:if>
				</td>
				<td class="td_center">${item.SEND_FLAG }</td>
				<td class="td_center">
					<c:if test="${item.AFFIRM_FLAG_ACCRUAL eq '通过' or item.AFFIRM_FLAG_ACCRUAL eq '否决' or item.AFFIRM_FLAG_ACCRUAL eq '审批中'}">
						<a href="/pa/tempsale/viewTempSaleSummaryInfoList?pageNum=1&PAY_DATE=${item.PA_MONTH}&PAY_AREA_CD=${item.PAY_AREA_CD}&ACCRUAL_FLAG=CONFIRM_Y"
							target="navTab" 
								rel="pa0901_affirm">${item.AFFIRM_FLAG_ACCRUAL }</a>
					</c:if>
					<c:if test="${item.AFFIRM_FLAG_ACCRUAL  eq '未确认'}">
						${item.AFFIRM_FLAG_ACCRUAL }
					</c:if>
				</td>
				<td class="td_center">${item.SEND_FLAG_ACCRUAL }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:set value="/pa/tempsale/viewTempSaleEmpInfoList?EVENT_ID=${EVENT_ID }&SUBMIT_STATUS=${SUBMIT_STATUS }" var="pageUrl"/>
<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
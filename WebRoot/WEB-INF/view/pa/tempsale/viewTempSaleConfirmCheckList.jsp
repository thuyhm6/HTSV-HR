<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
<form onsubmit="return navTabSearch(this);"
	action="/pa/tempsale/viewTempSaleConfirmCheckList" rel="pagerForm" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td>月份</td>
		<td>
	    	<ait:dateProMonth yearName="seach_YEAR" yearSelected="${YEAR}" monthName="seach_MONTH" monthSelected="${MONTH}"/>
		</td>
		<td>预提与否</td>
		<td>
			<select name="seach_ACCRUAL_YN">
				<option value="">全部</option>
				<option value="Y" <c:if test="${ACCRUAL_YN eq 'Y' }">selected</c:if>>Y</option>
				<option value="N" <c:if test="${ACCRUAL_YN eq 'N' }">selected</c:if>>N</option>
			</select>
		</td>
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
<table class="table" width="100%" layoutH="180" nowrapTD="false">
	<thead>
		<tr>
			<th style="width:25%">大区</th>
			<th style="width:20%">工资支付月</th>
			<th style="width:25%">工资类型</th>
			<th style="width:15%">审批状态</th>
			<th style="width:15%">Type</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${paTempSalesList}" var="item" varStatus="i">
			<tr>
				<td style="text-align:center">${item.PAY_AREA_NAME }</td>
				<td style="text-align:center">${item.PAY_DATE }</td>
				<td style="text-align:center">
					<c:if test="${item.ACCRUAL_FLAG eq 'CONFIRM_Y'}">临促预提</c:if>
					<c:if test="${item.ACCRUAL_FLAG eq 'CONFIRM_N'}">临促工资</c:if>
				</td>
				<td style="text-align:center">
					<c:if test="${item.AFFIRM_FLAG eq '-1'}">提交</c:if>
					<c:if test="${item.AFFIRM_FLAG eq '0'}">审批中</c:if>
					<c:if test="${item.AFFIRM_FLAG eq '1'}">通过</c:if>
					<c:if test="${item.AFFIRM_FLAG eq '2'}">否决</c:if>
				</td>
				<td style="text-align:center">
					<c:if test="${item.AFFIRM_FLAG_PERSON eq '1'}">
						<a href="/pa/tempsale/viewTempSaleAffirm?pageNum=1&EVENT_ID=${item.EVENT_ID }&affirmOrCheck=2"
							target="navTab"
								rel="pa0901_affirm">Check</a>
					</c:if>
					<c:if test="${item.AFFIRM_FLAG_PERSON ne '1'}">
						<a onclick="navTabNum('/pa/tempsale/viewTempSaleEmpInfoList?pageNum=1&navTabId=pa0701_EMPINFO&EVENT_ID=${item.EVENT_ID }','pa0701_EMPINFO','已Check');">已Check</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:set value="/pa/tempsale/viewTempSaleConfirmCheckList?seach_YEAR=${YEAR}&seach_MONTH=${MONTH}&seach_ACCRUAL_YN=${ACCRUAL_YN}" var="pageUrl" /> 
<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>
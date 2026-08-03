<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
<form onsubmit="return navTabSearch(this);"
	action="/pa/tempsale/viewTempSaleAccrualCheckList" rel="pagerForm" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td>决裁状态</td>
		<td><select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
			<option value="">全部</option>
			<option value="-1" <c:if test="${AFFIRM_FLAG eq '-1'}">selected</c:if>>未决裁</option>
			<option value="0" <c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>>进行中</option>
			<option value="1" <c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>已通过</option>
			<option value="2" <c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>已否决</option>
		</select></td>
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
<table class="table" width="100%" layoutH="172" nowrapTD="false">
	<thead>
		<tr>
			<th>大区</th>
			<th align="center">支社代码</th>
			<th>支社名称</th>
			<th align="center">门店编码</th>
			<th>门店名称</th>
			<th align="center">产品类型</th>
			<th align="center">支付月份</th>
			<th align="center">对应共同社编</th>
			<th align="right">总金额</th>
			<th align="center">决裁状态</th>
			<th align="center">Type</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${paTempSalesList}" var="item" varStatus="i">
			<tr>
				<td>${item.PAY_AREA_CD }</td>
				<td>${item.ACC_ORG_CODE }</td>
				<td>${item.ORG_NAME_LOCAL }</td>
				<td>${item.EVENT_STORE_CODE }</td>
				<td>${item.EVENT_STORE_NAME }</td>
				<td>
					<c:if test="${item.PRODUCT_NAME ne '[]'}">${item.PRODUCT_NAME }</c:if>
				</td>
				<td>${item.PAY_DATE }</td>
				<td>${item.COMMON_EMPID }</td>
				<td>${item.TOTAL_SALARY }</td>
				<td>
					<c:if test="${item.AFFIRM_FLAG eq '-1'}">未决裁</c:if>
					<c:if test="${item.AFFIRM_FLAG eq '0'}">进行中</c:if>
					<c:if test="${item.AFFIRM_FLAG eq '1'}">已通过</c:if>
					<c:if test="${item.AFFIRM_FLAG eq '2'}">已否决</c:if>
				</td>
				<td>
					<c:if test="${item.AFFIRM_FLAG_PERSON eq '1'}">
						<a href="/pa/tempsale/viewTempSaleAccrualAffirm?EVENT_ID=${item.EVENT_ID }&affirmOrCheck=2&accrualFlag=Y"
							target="navTab"
								rel="pa0901_affirm">Check</a>
					</c:if>
					<c:if test="${item.AFFIRM_FLAG_PERSON ne '1'}">
						<a onclick="navTabNum('/pa/tempsale/viewTempSaleAccrualAffirmInfoList?pageNum=1&navTabId=pa0701_EMPINFO&EVENT_ID=${item.EVENT_ID }','pa0701_EMPINFO','已Check');">已Check</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:set value="/pa/tempsale/viewTempSaleAccrualCheckList" var="pageUrl" /> 
<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>
<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
<form onsubmit="return navTabSearch(this);"
	action="/pa/tempsale/viewTempSaleCheckList" rel="pagerForm" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td>Event名称</td>
		<td><input type="text" id="seach_EVENT_NAME" name="seach_EVENT_NAME"
			value="${EVENT_NAME}" /></td>
		<td><spring:message code="public.title.startDate" /><!-- 开始日期 -->
		</td>
		<td><input id="seach_START_DATE" type="text"
			name="seach_START_DATE" class="date required" readonly="true"
			value="${START_DATE}" /> <a class="inputDateButton"><spring:message
			code="public.title.choose" /><!-- 选择 --></a></td>
		<td><spring:message code="public.title.endDate" /><!-- 结束日期 -->
		</td>
		<td><input id="seach_END_DATE" type="text" name="seach_END_DATE"
			class="date required" readonly="true" value="${END_DATE}" /> <a
			class="inputDateButton"><spring:message
			code="public.title.choose" /><!-- 选择 --></a></td>
		<td>审批状态</td>
		<td><select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
			<option value="">全部</option>
			<option value="-1" <c:if test="${AFFIRM_FLAG eq '-1'}">selected</c:if>>提交</option>
			<option value="0" <c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>>审批中</option>
			<option value="1" <c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>通过</option>
			<option value="2" <c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>否决</option>
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
<table class="table" width="100%" layoutH="180" nowrapTD="false">
	<thead>
		<tr>
			<th>Event名称</th>
			<th>EventID</th>
			<th>Event部门</th>
			<th>Event门店</th>
			<th>开始日期</th>
			<th>结束日期</th>
			<th>总人数</th>
			<th>总金额</th>
			<th>工资支付月</th>
			<th>附件查看</th>
			<th>备注</th>
			<th>决裁状态</th>
			<th>Type</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${paTempSalesList}" var="item" varStatus="i">
			<tr>
				<td style="text-align:left">${item.EVENT_NAME }</td>
				<td style="text-align:left">${item.EVENT_ID }</td>
				<td style="text-align:left">${item.EVENT_DEPTNO }</td>
				<td style="text-align:left">${item.EVENT_STORE_NAME }</td>
				<td>${item.START_DATE }</td>
				<td>${item.END_DATE }</td>
				<td>${item.TOTAL_NUM }</td>
				<td>${item.TOTAL_SALARY }</td>
				<td>${item.PAY_DATE }</td>
				<td style="text-align: center">
							<c:forEach items="${item.fileList}" var="file" varStatus="j">	
								<div style="display:block;line-height:30px;"><a href="/ess/infoApplyLeave/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME }</a></div>
							</c:forEach>
				</td>
				<td>${item.REMARK }</td>
				<td>
					<c:if test="${item.AFFIRM_FLAG eq '-1'}">提交</c:if>
					<c:if test="${item.AFFIRM_FLAG eq '0'}">审批中</c:if>
					<c:if test="${item.AFFIRM_FLAG eq '1'}">通过</c:if>
					<c:if test="${item.AFFIRM_FLAG eq '2'}">否决</c:if>
				</td>
				<td>
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
<c:set value="/pa/tempsale/viewTempSaleCheckList" var="pageUrl" /> 
<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>
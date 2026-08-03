<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">	
//查看详细
function searchSalesmanIncAdjuDetail(url) {
	$("#importExcel_se0203").attr('href', url);
	$("#importExcel_se0203").attr('width', "800");
	$("#importExcel_se0203").attr('height', "420");
	var tabName_se0203 = document.getElementById("tabName_se0203");
	tabName_se0203.innerHTML = "查看详细";
	$("#importExcel_se0203").click();
}

</script>
<div class="pageHeader">
	<a id="importExcelDialog_se0203" href="#" target="dialog" mask="true"><span
		id="se0203Link" style="display: none"></span></a> 
	<a id="importExcel_se0203" href="#" width="800" height="420" target="dialog" mask="true"><span
		id="tabName_se0203" style="display: none"></span></a>
	<form name="searchForm_se0203_aa" id="searchForm_se0203_aa"
		onsubmit="return navTabSearch(this);" 
		action="/inct/salesman/viewIncentiveAdjustList" method="post"
		rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<input type="hidden" id="CLOS_FLAG" name="CLOS_FLAG" value="${closeFlag}" />
				<tr>
					<td><spring:message code="public.title.startDate" /><!-- 开始日期 -->:
					</td>
					<td><input id="seach_START_DATE" type="text"
						name="seach_START_DATE" class="date required" readonly="true"
						value="${START_DATE}" /> <a class="inputDateButton"><spring:message
						code="public.title.choose" /><!-- 选择 --></a></td>
					<td><spring:message code="public.title.endDate" /><!-- 结束日期 -->:
					</td>
					<td><input id="seach_END_DATE" type="text" name="seach_END_DATE"
						class="date required" readonly="true" value="${END_DATE}" /> <a
						class="inputDateButton"><spring:message
						code="public.title.choose" /><!-- 选择 --></a></td>
				</tr>
				<tr>
					<td>预提与否:</td>
					<td><select id="seach_ACCRUAL_YN" name="seach_ACCRUAL_YN">
						<option value="">全部</option>
						<option value="Y" <c:if test="${ACCRUAL_YN eq 'Y'}">selected</c:if>>是</option>
						<option value="N" <c:if test="${ACCRUAL_YN eq 'N'}">selected</c:if>>否</option>
					</select></td>
					<td>决裁状态:</td>
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
								<button type="submit">
									<spring:message code="public.title.search" />
									<!-- 检索 -->
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
<a id="importExcel_se0203" href="#" target="dialog" width="800" height="420" mask="true"><span
	style="display: none;">查看详细</span></a>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive">
					<div class="buttonContent">
						<button type="button" id="btnExcelExport_se0203" name="btnExcelExport_se0203"
							onclick="exportIncentiveCalcList(this,'${request_navTabId}')">
							<spring:message code="inct.salesman.downloadToExcel" />
							<!--excel导出-->
						</button>
					</div>
				</div></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="231">
		<thead>
			<tr>
				<th width="10%">申请ID<!--申请ID--></th>
				<th width="10%">类别<!--类别--></th>
				<th width="10%">申请人<!--申请人--></th>
				<th width="10%">申请日期<!--申请日期--></th>
				<th width="10%">调整总人数<!--调整总人数--></th>
				<th width="10%">调整总金额<!--调整总金额--></th>
				<th width="10%">查看详细<!--查看详细--></th>
				<th width="15%">决裁状态<!--审批状态--></th>
				<th width="15%">当前决裁人<!--当前决裁人--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="mdata" varStatus="i">
				<tr>
					<td class='td_center'>${mdata.REQ_ID}</td>
					<td class='td_center'>${mdata.ACCRUAL_YN}</td>
					<td class='td_center'>${mdata.REQ_EMPNO}</td>
					<td class='td_center'>${mdata.REQ_DATE}</td>
					<td class='td_right'>${mdata.EMP_CNT}</td>
					<td class='td_right'>${mdata.ADJST_AMT_TOT}</td>
					<td class='td_center'>
						<a
						onclick="searchSalesmanIncAdjuDetail('/inct/salesman/viewIncentiveCalcAdjuDtlList?pageNum=1&navTabId=se0203_ADJUDTL&REQ_ID=${mdata.REQ_ID }');">
						查看详细 </a>
					</td>
					<td class='td_center'>${mdata.AFFIRM_STATUS}</td>
					<td class='td_center'>${mdata.CURRENT_AFFIRM_EMPNM}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/inct/salesman/viewIncentiveAdjustList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	
<form id="excelExportForm_se0203" name="excelExportForm_se0203" method="post">
	<input type="hidden" id="password" name="password" value="" />
	<input type="hidden" id="PAY_AREA_CD" name="PAY_AREA_CD" value="" />
	<input type="hidden" id="DEPTNO" name="DEPTNO" value="" />
	<input type="hidden" id="YEAR" name="YEAR" value="" />
	<input type="hidden" id="MONTH" name="MONTH" value="" />
	<input type="hidden" id="EMPNO" name="EMPNO" value="" />
</form>
</div>
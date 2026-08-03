<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" rel="pagerForm"
		action="/sys/attendancesetting/viewAttendItemMappingList" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>法人： 
					</td>
					<td>
<!-- 					<input type="text" name="seach_CPNY" value="${CPNY }" /> -->
                        <select name="seach_CPNY">
						   <option></option>
						<c:forEach items="${companyList}" var="item" varStatus="i">
						   <option value="${item.CPNY_ID}" <c:if test="${item.CPNY_ID eq CPNY }">selected</c:if>>${item.CPNY_ID}</option>
						</c:forEach>
						</select>
					</td>
					<td>
						<spring:message code="pa.insurance.title.projectType" />
						<!--项目类型-->： 
					</td>
					<td>
						<select id="seach_TYPE" name="seach_TYPE" value="${TYPE }"> 
						<option value="">
						</option>
						<option value="明细项目" <c:if test="${TYPE eq '明细项目'}">selected</c:if>>
							<spring:message code="ar.attenditem.title.mingxixiangmu"/>
						</option>
						<option value="汇总项目" <c:if test="${TYPE eq '汇总项目'}">selected</c:if>>
							<spring:message code="ar.viewsummaryparameteritem.title.huizongxiangmu"/>
						</option>
						</select>
					</td>
					<td>
						<spring:message code="pa.insurance.title.projectName" />
						<!--项目名称-->： 
					</td>
					<td>
						<input type="text" name="seach_KEY" value="${KEY }" />
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
</div>	

<div class="pageContent">

	<c:set value="/sys/attendancesetting/newAttendItemMappingView?&navTabId=ar0317_newAttendItemMappingView&PROJECT_TYPE=1"
		var="add_Url" />
	<c:set value="600" var="add_width" />
	<c:set value="500" var="add_height" />
	<c:set value="${add_name}" var="add_name" />
	<c:set value="navTab" var="add_tab" />

	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>

	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr width="100%">
				<th width="3%"><spring:message
						code="sys.affirm.indexNum" />
					<!--序号--></th>
				<th width="6%"><spring:message
						code="pa.insurance.title.projectType" />
					<!--项目类型--></th>
				<th width="12%"><spring:message
						code="pa.insurance.title.projectName" />
					<!--项目名称--></th>
				<th width="13%"><spring:message
						code="pa.insurance.title.description" />
					<!--描述--></th>
				<th >TST0</th>
				<th >SST</th>
				</tr>
		</thead>
		<tbody>
			<c:forEach items="${attendItemList}" var="item" varStatus="i">
				<tr target="ITEM_NO"
					rel="${item.ITEM_NO}&PROJECT_TYPE=${item.PROJECT_TYPE eq '明细项目' ? 1 : 2}">
					<td>${i.index + 1}&nbsp;</td>
					<td>${item.PROJECT_TYPE}</td>
					<td>${item.ITEM_NAME}</td>
					<td>${item.DESCRIPTION}</td>
					<td ><c:if test="${item.TST0 == 1}">√</c:if>
					</td>
					<td ><c:if test="${item.SST == 1}">√</c:if></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
	
	<c:set value="/sys/attendancesetting/viewAttendItemMappingList?seach_CPNY=${CPNY}" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	
</div>

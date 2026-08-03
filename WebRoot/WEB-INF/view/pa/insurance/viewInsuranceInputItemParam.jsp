<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/insurance/viewInsuranceInputItemParam" method="post" rel="pagerForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->：
					<input type="text" name="seach_ALIAS_NAME" value="${ALIAS_NAME}" />
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

<div class="pageContent" >
	<c:set value="600" var="add_width"/>
	<c:set value="470" var="add_height"/>
	<c:set value="/pa/insurance/addInsuranceInputItemParamView?TABLE_NAME=PA_HR_V" var="add_Url"/>
	<c:set value="/pa/insurance/deleteInsuranceInputItemParamInfo?PARAM_NO={sid}" var="delete_Url"/>
	<c:set value="/pa/insurance/updateInsuranceInputItemParamView?TABLE_NAME=PA_HR_V&seach_PARAM_NO={sid}" var="edit_Url"/>
	<c:set value="600" var="edit_width"/>
	<c:set value="470" var="edit_height"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="80"><spring:message code="pa.insurance.title.companyID"/><!--公司ID--></th>
				<th width="80"><spring:message code="pa.insurance.title.distinctName1"/><!--区分项目1:--></th>
				<th width="80"><spring:message code="pa.insurance.title.distinctName2"/><!--区分项目2:--></th>
				<th width="80"><spring:message code="pa.insurance.title.projectName"/><!--项目名称--></th>
				<th width="80"><spring:message code="pa.insurance.title.defaltValue"/><!--默认值:--></th>
				<th width="80"><spring:message code="pa.insurance.title.applyFlag"/><!--是否需要申请--></th>
				<th width="80"><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${isInputItemParamList}" var="item" varStatus="i">
			
				<tr target="sid" rel="${item.PARAM_NO}">
					<td>${item.CPNY_NAME}</td>
					<td>${item.DISTINCT_FIELD_NAME}</td>
					<td>${item.DISTINCT_FIELD_2ND_NAME}</td>
					<td>${item.ALIAS_NAME}</td>
					<td class='td_center'>${item.DEFAULT_VAL}</td>
					<td  class='td_center'>${item.APPLY_FLAG}</td>
					<td class='td_center'>
						<c:if test="${item.ACTIVITY eq 1 }"><spring:message code="sys.arAffirmPost.title.able"/><!--启用--></c:if>
						<c:if test="${item.ACTIVITY ne 1 }"><spring:message code="sys.arAffirmPost.title.enable"/><!--不启用--></c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>	
	<c:set value="/pa/insurance/viewInsuranceInputItemParam" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
function pageFromSea(a){
	
	var seach_ALIAS=$("#seach_ALIAS").val();
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/pa/bonus/viewBonusInputItemParam?seach_ALIAS="+seach_ALIAS);
}

</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/pa/bonus/viewBonusInputItemParam" method="post"
		rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->：
						<input type="text"  id="seach_ALIAS" name="seach_ALIAS" value="${ALIAS}" />
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
	<c:set value="/pa/bonus/addBonusInputItemParamView?TABLE_NAME=PA_HR_V"
		var="add_Url" />
	<c:set value="600" var="add_width" />
	<c:set value="420" var="add_height" />
	<c:set value="/pa/bonus/deleteBonusInputItemParamInfo?PARAM_NO={sid}"
		var="delete_Url" />
	<c:set value="/pa/bonus/updateBonusInputItemParamView?PARAM_NO={sid}&TABLE_NAME=PA_HR_V"
		var="edit_Url" />
	<c:set value="600" var="edit_width" />
	<c:set value="420" var="edit_height" />
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>

	<table class="table" width="100%" layoutH="145">
		<thead>
			<tr>
				<th width="16%">
					<spring:message code="pa.insurance.title.company"/><!--公司-->
				</th>
				<th width="16%">
					<spring:message code="pa.insurance.title.distinctName1"/><!--区分项目1:-->
				</th>
				<th width="16%">
					<spring:message code="pa.insurance.title.distinctName2"/><!--区分项目2:-->
				</th>
				<th width="16%">
					<spring:message code="pa.insurance.title.defaltValue"/><!--默认值:-->
				</th>
				<th width="16%">
					<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->
				</th>
				<th width="16%">
					<spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="item" varStatus="i">

				<tr target="sid" rel="${item.PARAM_NO}">
					<td class="td_center">
						${item.CPNY_NAME}
					</td>
					<td class="td_center">
						${item.DISTINCT_FIELD_NAME}
					</td>
					<td class="td_center">
						${item.DISTINCT_FIELD_2ND_NAME}
					</td>
					<td class="td_center">
						${item.DEFAULT_VAL}
					</td>
					<td class="td_center">
						${item.ALIAS_NAME}
					</td>
					<td class='td_center'>
						<c:if test="${item.ACTIVITY eq 1 }"><spring:message code="sys.arAffirmPost.title.able"/><!--启用--></c:if>
						<c:if test="${item.ACTIVITY ne 1 }"><spring:message code="sys.arAffirmPost.title.enable"/><!--不启用--></c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/bonus/viewBonusInputItemParam" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>
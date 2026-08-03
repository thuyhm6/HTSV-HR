<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/pa/wagebase/viewPaBasicItemParam" method="post"
		rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->：
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
	<c:set value="/pa/wagebase/addPaBasicItemParamView?TABLE_NAME=PA_HR_V"
		var="add_Url" />
	<c:set value="600" var="add_width" />
	<c:set value="420" var="add_height" />
	<c:set value="dialog" var="target" />
	<c:set
		value="/pa/wagebase/deletePaBasicItemParamInfo?PARAM_NO={PARAM_NO}"
		var="delete_Url" />
	<c:set
		value="/pa/wagebase/updatePaBasicItemParamView?PARAM_NO={PARAM_NO}&TABLE_NAME=PA_HR_V"
		var="edit_Url" />
	<c:set value="600" var="edit_width" />
	<c:set value="400" var="edit_height" />
	<c:set value="dialog" var="edit_tab" />
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>

	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="50">
					<spring:message code="pa.wagebase.title.companyName"/><!--公司名称-->
				</th>
				<th width="50">
					<spring:message code="pa.insurance.title.distinctName1"/><!--区分项目1:-->
				</th>
				<th width="50">
					<spring:message code="pa.insurance.title.distinctName2"/><!--区分项目2:-->
				</th>
				<th width="50">
					<spring:message code="pa.insurance.title.defaltValue"/><!--默认值:-->
				</th>
				<th width="50">
					<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->
				</th>
				<th width="50"><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paBasicItemParamList}" var="item" varStatus="i">
				<tr target="PARAM_NO" rel="${item.PARAM_NO}">
					<td>
						${item.CPNY_NAME}
					</td>
					<td>
						${item.DISTINCT_FIELD_NAME}
					</td>
					<td>
						${item.DISTINCT_FIELD_2ND_NAME}
					</td>
					<td>
						${item.DEFAULT_VAL}
					</td>
					<td>
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

	<c:set value="/pa/wagebase/viewPaBasicItemParam" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
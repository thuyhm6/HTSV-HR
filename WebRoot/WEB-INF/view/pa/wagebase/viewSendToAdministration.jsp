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
					<td><spring:message code="sys.essParam.title.legalPerson" />
						<!--法人-->： ${CPNY_ID}</td>
					<td><spring:message code="sys.basic.title.codeName" />
						<!--代码名称-->： <ait:selectSyCode name="CONTRACT_TYPE_CODE"
							parentNo="" /></td>

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
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">
	<c:set
		value="/pa/wagebase/addPaSendToAdministration?TABLE_NAME=PA_HR_V"
		var="add_Url" />
	<c:set value="600" var="add_width" />
	<c:set value="335" var="add_height" />
	<c:set value="dialog" var="target" />
	<c:set value="delete_pa9999" var="deleteName" />
	<c:set value="/pa/wagebase/deletePaSendToAdministrationInfo?SENDID={SENDID}"
		var="delete_Url" />
	<c:set
		value="/pa/wagebase/updatePaBasicItemParamView?SENDID={SENDID}&TABLE_NAME=PA_HR_V"
		var="edit_Url" />
	<c:set value="600" var="edit_width" />
	<c:set value="400" var="edit_height" />
	<c:set value="dialog" var="edit_tab" />
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
			
				<th width="15%"><spring:message code="ar.viewcycle.title.xuhao" />
					<!--序号--></th>
				<th width="15%"><spring:message
						code="sys.essParam.title.legalPerson" />
					<!--法人--></th>
				<th width="15%"><spring:message
						code="display.emp.ben.or.sendtoadministrator" />
					<!--派遣地--></th>
				<th width="15%"><spring:message
						code="ess.infoApply.title.dutyName" />
					<!--职责--></th>
				<th width="15%"><spring:message code="rp.report.title.amount" />
					<!--金额--></th>
				<th width="15%"><spring:message
						code="liang.public.title.ItemName" />
					<!--项目名称--></th>
				<th width="15%"><spring:message
						code="sys.arAffirmPost.title.ableStatus" />
					<!--启用状态--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${sendList}" var="item" varStatus="i">
				<tr target=SENDID rel="${item.SENDID}">
					
					<td class='td_center'>${i.count}</td>
					<td class='td_center'>${CPNY_ID}</td>
					<td class='td_center'>${item.SENDNAME}</td>
					<td class='td_center'>${item.DUTYNAME}</td>
					<td class='td_center'>${item.SENDMONEY}</td>
					<td class='td_center'>${item.SENDTITLE}</td>
					<td class='td_center'><c:if test="${item.ACTIVITY eq 1 }">
							<spring:message code="sys.arAffirmPost.title.able" />
							<!--启用-->
						</c:if> <c:if test="${item.ACTIVITY ne 1 }">
							<spring:message code="sys.arAffirmPost.title.enable" />
							<!--不启用-->
						</c:if></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<c:set value="/pa/wagebase/viewPaBasicItemParam" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
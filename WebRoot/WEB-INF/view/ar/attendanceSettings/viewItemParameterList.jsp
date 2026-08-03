<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	
	<c:set value="dialog" var="add_tab"/>
	<c:set value="500" var="add_width"/>
	<c:set value="400" var="add_height"/>
	<c:set value="/ar/attendanceSettings/addItemParameterView?AR_ITEM_NO=${AR_ITEM_NO}" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/ar/attendanceSettings/deleteItemParameterInfo?NO={PARAM_NO}" var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="500" var="edit_width"/>
	<c:set value="400" var="edit_height"/>
	<c:set value="/ar/attendanceSettings/updateItemParameterView?NO={PARAM_NO}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	
	<table class="table" width="99%" layoutH="260">
		<thead>
			<tr>
				<th width="50"><!-- 公司 --><spring:message code="ar.viewcycleparameter.title.gongsi"/></th>
				<th width="50"><!-- 组名称 --><spring:message code="ar.viewitemparameter.title.zumingcheng"/></th>
				<th width="50"><!-- 单位 --><spring:message code="ar.viewitemparameter.title.unit"/></th>
				<th width="50"><!-- 是否参考刷卡 --><spring:message code="ar.viewitemparameter.title.cankaoshuaka"/></th>
				<th width="50"><!-- 是否参考申请 --><spring:message code="ar.viewitemparameter.title.cankaoshenqing"/></th>
				<th width="100"><!-- 有效日期类型 --><spring:message code="ar.viewitemparameter.title.riqileixing"/></th>
				<th width="50"><!-- 活跃状态 --><spring:message code="ar.viewcycle.title.huoyuezhuangtai"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemParameterList}" var="list">
				<tr target="PARAM_NO" rel="${list.AR_PARAM_NO}&CPNY_ID=${list.CPNY_ID}&ITEM_ID=${list.ITEM_ID}" >
				<td>${list.CPNY_NAME}</td>
				<td>${list.AR_GROUP_NAME}</td>
				<td><c:if test="${list.UNIT eq 'MINUTE'}"><!-- 分钟 --><spring:message code="ar.viewitemparameter.title.fenzhong"/></c:if>
					<c:if test="${list.UNIT eq 'HOUR'}"><!-- 小时 --><spring:message code="ar.viewitemparameter.title.xiaoshi"/></c:if>
					<c:if test="${list.UNIT eq 'DAY'}"><!-- 天 --><spring:message code="ar.viewitemparameter.title.dayofunit"/></c:if>
					<c:if test="${list.UNIT eq 'TIME'}"><!-- 次 --><spring:message code="ar.viewitemparameter.title.timeofunit"/></c:if>
				</td>
				<td><c:if test="${list.CARD_FLAG eq 1}"><!-- 是 --><spring:message code="ar.viewcycle.content.yes"/></c:if><c:if test="${list.CARD_FLAG ne 1}"><!-- 否 --><spring:message code="ar.viewcycle.content.no"/></c:if></td>
				<td><c:if test="${list.APPLY_FLAG eq 1}"><!-- 是 --><spring:message code="ar.viewcycle.content.yes"/></c:if><c:if test="${list.APPLY_FLAG ne 1}"><!-- 否 --><spring:message code="ar.viewcycle.content.no"/></c:if></td>
				<td>
					<c:set var="dateType" value="${list.DATE_TYPE}"></c:set>
					<!-- ${fn:replace(fn:replace(fn:replace(dateType,'1440','平日'),'1441','公休'),'1442','节假日')} -->
					<c:if test="${fn:contains(dateType,'1440')}">
						<!-- 平日--><spring:message code="ar.viewitemparameter.title.pingshi"/>&nbsp;
					</c:if>
					<c:if test="${fn:contains(dateType,'1441')}">
						<!-- 周末--><spring:message code="ar.viewitemparameter.title.zhoumo"/>&nbsp;
					</c:if>
					<c:if test="${fn:contains(dateType,'1442')}">
						<!-- 节假日--><spring:message code="ar.viewitemparameter.title.jiejiari"/>&nbsp;
					</c:if>
					<c:if test="${fn:contains(dateType,'90000425')}">
						<!-- 带薪假--><spring:message code="ar.viewComanyCalendar.DAIXINJIA.b"/>&nbsp;
					</c:if>
				</td>
				<td style="text-align:left"><img src="/resources/images/a_${list.ACTIVITY}.gif"></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<form id="pagerForm" method="post" action="/ar/attendanceSettings/viewItemParameterList?AR_ITEM_NO=${AR_ITEM_NO}">
	</form>
</div>
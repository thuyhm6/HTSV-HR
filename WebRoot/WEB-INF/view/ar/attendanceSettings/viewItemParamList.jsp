<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ar/attendanceSettings/viewItemParamList" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<!-- 项目名称 --><spring:message code="ar.viewItem.title.xiangmumingcheng"/>：<input type="text" name="seach_ITEM_NAME" value="${ITEM_NAME}"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 检索 --><spring:message code="public.title.search"/></button></div></div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	
	<c:set value="dialog" var="add_tab"/>
	<c:set value="500" var="add_width"/>
	<c:set value="400" var="add_height"/>
	<c:set value="/ar/attendanceSettings/addItemParamView" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/ar/attendanceSettings/deleteItemParamInfo?NO={ITEM_NO}" var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="600" var="edit_width"/>
	<c:set value="400" var="edit_height"/>
	<c:set value="/ar/attendanceSettings/updateItemParamView?NO={ITEM_NO}" var="edit_Url"/>
<%--  <%@ include file="/WEB-INF/view/inc/includeButton.jsp"%> --%>	
	<%@ include file="/WEB-INF/view/inc/includeButton_nodelete.jsp"%>
	
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="10%"><!-- 公司名称 --><spring:message code="ar.viewcycleparameter.title.gongsimingcheng"/></th>
				<th width="20%"><!-- 项目名称 --><spring:message code="ar.viewItem.title.xiangmumingcheng"/></th>
				<th width="20%"><!-- 单位 --><spring:message code="ar.viewitemparameter.title.unit"/></th>
				<th width="50"><!-- 是否参考刷卡 --><spring:message code="ar.viewitemparameter.title.cankaoshuaka"/></th>
				<th width="50"><!-- 是否参考申请 --><spring:message code="ar.viewitemparameter.title.cankaoshenqing"/></th>
				<th width="80"><!-- 人事政策 --><spring:message code="ar.attend.title.hr_policy"/></th>
				<th width="10%"><!-- 活跃状态 --><spring:message code="ar.viewcycle.title.huoyuezhuangtai"/></th>
				
			</tr>
		</thead>
		<tbody>
			    <c:forEach items="${itemParamList}" var="list">
				<tr target="ITEM_NO" rel="${list.AR_PARAM_NO}" >
				<td>${list.CPNY_NAME}</td>
				<td>${list.ITEM_NAME}</td>
				<td><c:if test="${list.UNIT eq 'MINUTE'}"><!-- 分钟 --><spring:message code="ar.viewitemparameter.title.fenzhong"/></c:if>
					<c:if test="${list.UNIT eq 'HOUR'}"><!-- 小时 --><spring:message code="ar.viewitemparameter.title.xiaoshi"/></c:if>
				    <c:if test="${list.UNIT eq 'DAY'}"><!-- 天 --><spring:message code="ar.viewitemparameter.title.dayofunit"/></c:if>
				    <c:if test="${list.UNIT eq 'TIME'}"><!-- 次 --><spring:message code="ar.viewitemparameter.title.timeofunit"/></c:if>
				
				</td>
				<td><c:if test="${list.CARD_FLAG eq 1}"><!-- 是 --><spring:message code="ar.viewcycle.content.yes"/></c:if><c:if test="${list.CARD_FLAG ne 1}"><!-- 否 --><spring:message code="ar.viewcycle.content.no"/></c:if></td>
				<td><c:if test="${list.APPLY_FLAG eq 1}"><!-- 是 --><spring:message code="ar.viewcycle.content.yes"/></c:if><c:if test="${list.APPLY_FLAG ne 1}"><!-- 否 --><spring:message code="ar.viewcycle.content.no"/></c:if></td>
                <td ><a target="dialog" style="color: blue" mask="true" href="/ar/attendanceSettings/viewParamHrPolicy?AR_PARAM_NO=${list.AR_PARAM_NO}">${list.ITEM_NAME}</a></td>
	            <td><img src="/resources/images/a_${list.ACTIVITY}.gif"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/ar/attendanceSettings/viewItemParamList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>		
<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<table class="table" width="99%" layoutH="260">
		<thead>
			<tr>
				<th width="50"><!-- 班次ID --><spring:message code="ar.viewshift.title.banciID"/></th>
				<th width="50"><!-- 班次类型 --><spring:message code="ar.viewshift.title.bancileixing"/></th>
				<th width="50"><!-- 开始日期 --><spring:message code="public.title.startDate"/></th>
				<th width="50"><!-- 开始时间 --><spring:message code="ess.infoApply.title.startTime"/></th>
				<th width="50"><!-- 结束日期 --><spring:message code="public.title.endDate"/></th>
				<th width="50"><!-- 结束时间 --><spring:message code="ess.infoApply.title.endTime"/></th>
				<th width="50"><!-- 加班开始时间 --><spring:message code="ess.infoApply.overtime_start_time"/></th>
				<!--<th width="50"> 中夜班津贴 <spring:message code="ar.viewShiftParameter.ZHONGYEBANJINTIE.b"/></th>-->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${shiftParams}" var="sp">
				<tr>
					<td>${sp.SHIFT_ID}</td>
					<td>${sp.ITEM_NAME}</td>
					<td>
						<c:if test="${sp.BEGIN_DAY_OFFSET eq '0'}"><!-- 当日 --><spring:message code="ar.viewshift.title.dangri"/></c:if>
						<c:if test="${sp.BEGIN_DAY_OFFSET eq '1'}"><!-- 次日 --><spring:message code="ar.viewshift.title.ciri"/></c:if>
						<c:if test="${sp.BEGIN_DAY_OFFSET ne '0' and sp.BEGIN_DAY_OFFSET ne '1'}"><!-- 前日 --><spring:message code="ar.viewShiftParameter.QIANRI.b"/></c:if>
					</td>
					<td>${sp.FROM_TIME}</td>
					<td>
						<c:if test="${sp.END_DAY_OFFSET eq '0'}"><!-- 当日 --><spring:message code="ar.viewshift.title.dangri"/></c:if>
						<c:if test="${sp.END_DAY_OFFSET eq '1'}"><!-- 次日 --><spring:message code="ar.viewshift.title.ciri"/></c:if>
						<c:if test="${sp.END_DAY_OFFSET ne '0' and sp.END_DAY_OFFSET ne '1'}"><!-- 前日 --><spring:message code="ar.viewShiftParameter.QIANRI.b"/></c:if>
					</td>
					<td>${sp.TO_TIME}</td>
					<td>${sp.OT_TIME_START}</td>
					<!--<td>${sp.OT_ALLOWANCE}</td>-->
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
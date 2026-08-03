<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="pageContent" style="padding:0;">
   <div style="font: 12px/ 20px arial, sans-serif; float: left; height: 30px; line-height: 30px;"></div>
	<table width="100%" class="table">
		<thead>
			<tr>
			        <th width="2%">No.</th>
					<th><!-- 开始日期 --><spring:message code="ar.viewcycleparameter.title.kaishiriqi" /></th>
					<th><!-- 班次类型 --><spring:message code="ar.viewshift.title.bancileixing" /></th>
					<th ><!-- 原因 --><spring:message code="ess.infoApply.Reason" /></th>
				</tr>
		</thead>
		<tbody>
		<c:forEach items="${viewArShiftRecordCheckInfoDetail}" var="item"  varStatus="i">
			<tr>
			    <td style="text-align:left">${i.count}</td>
				<td class='td_center'>${item.START_DATE}</td>
				<td class='td_center' >${item.SHIFT_NAME}</td>
				<td class='td_center' >${item.REMARK}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>















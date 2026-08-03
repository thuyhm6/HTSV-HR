<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;">
	<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="evs.viewEvsResultEmp.PINGJIAJIEGUOLVLI.a"/><!--评价结果履历--></div>
		<table class="table" layoutH="100" width="100%">
			<thead>
				<tr>
					<th width="8%"><spring:message code="hrm.empinfo.Evaluation_year"/><!--评价年度--></th>
					<%-- <c:if test="${LoginUser.language eq 'vi'}">
					<th width="7%"><spring:message code="display.mutual.month"/> 1<!--月--></th>
					<th width="7%"><spring:message code="display.mutual.month"/> 2<!--月--></th>
					<th width="7%"><spring:message code="display.mutual.month"/> 3<!--月--></th>
					<th width="7%"><spring:message code="display.mutual.month"/> 4<!--月--></th>
					<th width="7%"><spring:message code="display.mutual.month"/> 5<!--月--></th>
					<th width="7%"><spring:message code="display.mutual.month"/> 6<!--月--></th>
					<th width="7%"><spring:message code="display.mutual.month"/> 7<!--月--></th>
					<th width="7%"><spring:message code="display.mutual.month"/> 8<!--月--></th>
					<th width="7%"><spring:message code="display.mutual.month"/> 9<!--月--></th>
					<th width="7%"><spring:message code="display.mutual.month"/> 10<!--月--></th>
					<th width="7%"><spring:message code="display.mutual.month"/> 11<!--月--></th>
					<th width="7%"><spring:message code="display.mutual.month"/> 12<!--月--></th>
					</c:if>
					<c:if test="${LoginUser.language eq 'ko' || LoginUser.language eq 'zh' || LoginUser.language eq 'en'}">
					<th width="7%">1 <spring:message code="display.mutual.month"/><!--月--></th>
					<th width="7%">2 <spring:message code="display.mutual.month"/><!--月--></th>
					<th width="7%">3 <spring:message code="display.mutual.month"/><!--月--></th>
					<th width="7%">4 <spring:message code="display.mutual.month"/><!--月--></th>
					<th width="7%">5 <spring:message code="display.mutual.month"/><!--月--></th>
					<th width="7%">6 <spring:message code="display.mutual.month"/><!--月--></th>
					<th width="7%">7 <spring:message code="display.mutual.month"/><!--月--></th>
					<th width="7%">8 <spring:message code="display.mutual.month"/><!--月--></th>
					<th width="7%">9 <spring:message code="display.mutual.month"/><!--月--></th>
					<th width="7%">10 <spring:message code="display.mutual.month"/><!--月--></th>
					<th width="7%">11 <spring:message code="display.mutual.month"/><!--月--></th>
					<th width="7%">12 <spring:message code="display.mutual.month"/><!--月--></th>
					</c:if> --%>
					<th width="8%"><spring:message code="hr.viewEvaluate.title.EV_ACHI"/><!--绩效--></th>
					<th width="8%"><spring:message code="hr.viewEvaluate.title.EV_ABIL"/><!--能力--></th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${objectList}" var="item" varStatus="i">
				<tr>
					<td style="text-align:center">${item.EVS_YEAR}</td>
					<%-- <td style="text-align:center">${item.EVS_MONTH1}</td>
					<td style="text-align:center">${item.EVS_MONTH2 }</td>
					<td style="text-align:center">${item.EVS_MONTH3 }</td>
					<td style="text-align:center">${item.EVS_MONTH4 }</td>
					<td style="text-align:center">${item.EVS_MONTH5 }</td>
					<td style="text-align:center">${item.EVS_MONTH6 }</td>
					<td style="text-align:center">${item.EVS_MONTH7 }</td>
					<td style="text-align:center">${item.EVS_MONTH8 }</td>
					<td style="text-align:center">${item.EVS_MONTH9}</td>
					<td style="text-align:center">${item.EVS_MONTH10}</td>
					<td style="text-align:center">${item.EVS_MONTH11}</td> --%>
					<td style="text-align:center">${item.EVS_MONTH12}</td>
					<td style="text-align:center">${item.EVS_MONTH13}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	
	</div>
</div>
<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<table class="table"  id="daTable" width="100%" layoutH="60">
		<thead>
			<tr>
				<th>No.</th>
				<th><!--工作名称--><spring:message code="pa.salary.title.salaryOpen" /></th>
				<th><!--工作执行时间--><spring:message code="pa.viewPaWorkFlowOperationRecordList.GONGZUOZHIXINGSHIJIAN.C" /></th>
				<th><!--工作者--><spring:message code="pa.viewPaWorkFlowOperationRecordList.GONGZUOZHE.C" /></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${paWorkFlowOperationRecordList}" var="paWorkFlowOp"
			varStatus="i">
			<tr>
				<td style="text-align: center">${i.count}</td>
				<td style="text-align: center">
					<c:if test="${paWorkFlowOp.FLOW_STEP eq '1'}"><!--对象者生成--><spring:message code="pa.viewPaWorkFlow.DUIXIANGZHESHENGCHENG.C" /></c:if>
					<c:if test="${paWorkFlowOp.FLOW_STEP eq '2'}"><!--勤态汇总--><spring:message code="pa.viewPaWorkFlow.KAOQINHUIZONG.b" /></c:if>
					<c:if test="${paWorkFlowOp.FLOW_STEP eq '3'}"><!--工资计算--><spring:message code="pa.salary.title.salarycalculation" /></c:if>
					<c:if test="${paWorkFlowOp.FLOW_STEP eq '4'}"><!--工资锁定--><spring:message code="ar.viewarprogress.title.gongzisuoding" /></c:if>
					<c:if test="${paWorkFlowOp.FLOW_STEP eq '5'}"><!--工资开放--><spring:message code="pa.salary.title.salaryOpen" /></c:if>
					<c:if test="${paWorkFlowOp.FLOW_STEP eq '6'}"><!--工资取消锁定--><spring:message code="pa.viewPaWorkFlow.GONGZIQUXIAOSUODING.b" /></c:if>
					<c:if test="${paWorkFlowOp.FLOW_STEP eq '7'}"><!--工资取消开放--><spring:message code="pa.viewPaWorkFlow.GONGZIQUXIAOKAIFANG.b" /></c:if>
				</td>
				<td style="text-align: center">${paWorkFlowOp.CREATE_DATE}</td>
				<td style="text-align: center">${paWorkFlowOp.CREATED_BY}</td>
			</tr>
		</c:forEach>
		<c:if test="${totalCount == 0 }">
			<tr>
				<td style="text-align: left;" colspan="4"><!--没有查找的数据--><spring:message code="pa.monthPersonCountInfoList.MEIYOUCHAZHAODESHUJU.b" /></td>
			</tr>
		</c:if>
		</tbody>
	</table>
	<c:set value="/pa/workManagement/viewPaWorkFlowOperationRecordList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
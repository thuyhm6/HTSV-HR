 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=insuranceCalcObject.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
	<table width="45%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<!--<th width="5%">序号
				<spring:message code="ar.viewcycle.title.xuhao"/>
			</th>-->
			<th width="10%"><!--工资月-->
				<spring:message code="pa.insurance.title.salaryMonth"/>
			</th>
			<th width="10%"><!--工号-->
				<spring:message code="public.title.empId"/>
			</th>
			<th width="10%"><!--人事姓名-->
				<spring:message code="pa.title.message.empHrmName"/>
			</th>
			<th width="25%"><!--部门-->
				<spring:message code="public.title.deptName"/>
			</th>
			<!--<th width="10%">入司日期
				<spring:message code="pa.insurance.title.entryCpmpanyDate"/>
			</th>-->
			<!--<th width="10%">离职日期
				<spring:message code="pa.insurance.title.resignDate"/>
			</th>-->
			<th width="10%"><!--计算标识-->
				<spring:message code="pa.wagebase.title.caculateFlag"/>(社保)
			</th>
			<c:if test="${defaultCpny eq 'LGEHN' || defaultCpny eq 'LGEHZ' || defaultCpny eq 'LGEYT'}">
			<th width="10%"><!--计算标识-->
				<spring:message code="pa.wagebase.title.caculateFlag"/>(公积金)
			</th>
			</c:if>
			<th width="10%">备注(可以为空)
			</th>
		</tr>
		<c:forEach items="${isCalcList}" var="item" varStatus="i">
			<tr target="sid" rel="${item.PERSON_ID}">
				<%-- <td>${i.index+1 }</td> --%>
				<td>${item.PA_MONTH }</td>
				<td>${item.EMPID}</td>
				<td>${item.CHINESE_NAME}</td>
				<td>${item.DEPTNAME}</td>
			<%--<td>${item.JOIN_COMPANY_DATE}</td>
				<td>${item.DATE_LEFT}</td> --%>
				<td>${item.CALC_FLAG }</td>
				<c:if test="${defaultCpny eq 'LGEHN' || defaultCpny eq 'LGEHZ' || defaultCpny eq 'LGEYT'}">
				<td>${item.CALC_GJJ_FLAG }</td>
				</c:if>
				<td>${item.REMARK }</td>
			  </tr>
		</c:forEach>
	</table>
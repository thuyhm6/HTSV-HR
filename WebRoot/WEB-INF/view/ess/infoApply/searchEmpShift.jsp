<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent" >
		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.close"/><!-- 关闭 -->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>

	<table class="table" width="100%" layoutH="100">	
		<thead>
			<tr>
				<th width="15%"><spring:message code="public.title.empId"/><!-- 工号 --></th>
				<th width="15%"><spring:message code="public.title.name"/><!-- 姓名 --></th>
				<th width="15%"><spring:message code="ess.infoApply.title.date"/><!--日期--></th>
				<th width="15%"><spring:message code="ess.infoApply.title.shift"/><!--班次--></th>
				<th width="40%"><spring:message code="ess.infoApply.title.time"/><!--时间--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${empShiftList}" var="empShift" varStatus="i">			
				<tr>
					<td width="15%">${empShift.EMPID}</td>
					<td width="15%">${empShift.LOCAL_NAME}</td>
					<td width="15%">${empShift.SHIFT_TIME}</td>
					<td width="15%">${empShift.SHIFT_NAME}</td>
					<td width="40%">${empShift.FROM_TIME}~~${empShift.TO_TIME}</td>
				</tr>			
			</c:forEach>					
		</tbody>		
	</table>
</div>
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
				<th width="15%" style="text-align: center"><spring:message code="ess.viewApply.title.number"/><!-- 序号 --> </th>
				<th width="25%" style="text-align: center"><spring:message code="public.title.empId"/><!-- 工号 --></th>
				<th width="30%" style="text-align: center"><spring:message code="ess.infoApply.title.dutyName"/><!--职责--></th>
				<th width="30%" style="text-align: center"><spring:message code="public.title.name"/><!-- 姓名 --></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${affirmorList}" var="affirmor" varStatus="i">			
				<tr>
					<td width="15%" style="text-align: center">${i.index+1 }</td>
					<td width="25%" style="text-align: center">${affirmor.EMPID}</td>
					<td width="30%" style="text-align: center">${affirmor.DUTY_NAME}</td>
					<td width="30%" style="text-align: center">${affirmor.LOCAL_NAME}</td>
				</tr>			
			</c:forEach>					
		</tbody>		
	</table>
</div>
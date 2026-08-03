<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">

</script>

<div class="pageContent">

 	<table class="user_table" width="100%" layoutH="100%" nowrapTD="false">      
		<thead>
			<tr>
				<td class="td_title"><spring:message code="sys.affirm.title.indexNum"/><!--序号--></td>
				<td class="td_title"><spring:message code="public.title.empId"/><!--社号--></td>
				<td class="td_title"><spring:message code="public.title.empName"/><!--姓名--></td>
				<td class="td_title"><spring:message code="heran.trans.STARTDATE"/><!--调令日--></td>
				<td class="td_title"><spring:message code="heran.trans.ENDDATE"/><!--终止日--></td>
				<td class="td_title"><spring:message code="hr.viewCondSql.title.DIAOLINGLEIXING"/><!--调令类型--></td>
				<td class="td_title"><spring:message code="public.title.deptName"/><!--部门--></td>
				<td class="td_title"><spring:message code="hr.viewPersonalInfo.title.GRADE_LEVEL_NAME"/><!--职等--></td>
				<td class="td_title"><spring:message code="hr.viewPersonalInfo.title.DUTY_NAME"/><!--职责--></td>
				<td class="td_title"><spring:message code="hr.viewCondSql.title.ZHIJI"/><!--职级--></td>
				<td class="td_title"><spring:message code="public.title.postName"/><!--职级名称--></td>
				<td class="td_title"><spring:message code="heranr.trans.POSITIONNO"/><!--职(岗)位--></td>
				<td class="td_title"><spring:message code="hr.viewCondSql.title.transReason"/><!--调令事由--></td>
				<td class="td_title"><spring:message code="liang.hr.viewTraining.title.REMARKS"/><!--备注--></td>
				<td class="td_title"><spring:message code="hr.viewCondSql.title.transNo"/><!--调令编号--></td>
				
			</tr>
		</thead>
		<tbody>
				
				<c:forEach items="${insideList}" var="temp" varStatus="i">					  					           
				<tr>
					<td class="td_center">${i.count}</td>	        
					<td>${temp.EMPID}</td>
					<td>${temp.LOCAL_NAME}</td>
					<td><fmt:formatDate value="${temp.STARTDATE}" pattern="yyyy-MM-dd"/>   </td>
					<td><fmt:formatDate value="${temp.ENDDATE}" pattern="yyyy-MM-dd"/>   </td>
					<td>${temp.TRANOS_NO}</td>
					<td>${temp.DEPTNO}</td>
					<td>${temp.GRADE_LEVEL}</td>
					<td>${temp.DUTY_NO}</td>
					<td>${temp.POST_GRADE_NO}</td>
					<td>${temp.POST_NO}</td>
					<td>${temp.POSITION_NO}</td>
					<td>${temp.REASON}</td>
					<td>${temp.REMARK}</td>
					<td>${temp.TRANSNUM}</td>
				
			
				</tr>
			</c:forEach>
		  
		</tbody>
	</table>

	
</div>
      	



<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
	
</title>
</head>
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=CertificateofEmployee.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>

 <table  width="45%" border="0" align="center" cellpadding="0" cellspacing="0"> 
 	 <tr>
    <td>     
    <table width="45%" border="2" align="center" cellpadding="0" cellspacing="0" >
		<thead>
			<tr>
				<c:forEach items="${listTitle}" var="title" varStatus="i">
					<th>${title.CONTENT}</th>
				</c:forEach>
				<td>裁决情况</td>
				<td>是否生效
				</td>
			</tr>
		</thead>
		<tbody>
				<c:forEach items="${insideList}" var="temp">  
				<tr>  
				<c:forEach items="${temp}" var="map">          
					
					<c:choose>
						<c:when test="${map.key == 'EXP_INSIDE_NO'}"></c:when>
						<c:when test="${map.key == 'ACTIVITY'}"></c:when>
						<c:when test="${map.key == 'affirmerList'}"></c:when>
						<c:when test="${map.key == 'ROWNUM_'}"></c:when>
						<c:when test="${map.key == 'DEPTNO'}"></c:when>
						<c:when test="${map.key == 'CURRENT_AFFIRM_ID'}"></c:when>
						<c:when test="${map.key == 'AFFIRM_LEVEL'}"></c:when>
						<c:when test="${map.key == 'START_DATE'}"><td><fmt:formatDate  value="${map.value}" pattern="yyyy-MM-dd"/></td></c:when>
						<c:when test="${map.key == 'END_DATE'}"><td><fmt:formatDate  value="${map.value}" pattern="yyyy-MM-dd"/></td></c:when>
						<c:otherwise><td class="title">${map.value} </td> </c:otherwise>
					</c:choose>
					
					
				</c:forEach>
				<td>
					<c:forEach items="${temp.affirmerList}" var="affirmer" varStatus="i">					  					           
					        <dt style="padding: 1px;">
					            ${affirmer.LOCAL_NAME}
						         <c:if test="${affirmer.AFFIRM_FLAG==1}" >			                            
			                              &nbsp;<spring:message code="hr.viewTransactionTransViewList.title.PASS"/><!-- 通过 -->  
			                     </c:if> 
			                     <c:if test="${affirmer.AFFIRM_FLAG==2}" >         
			                              &nbsp;<spring:message code="hr.viewTransactionTransViewList.title.VOTE_DOWN"/><!-- 否决 --> 
								 </c:if>
			                     <c:if test="${affirmer.AFFIRM_FLAG==0}" >         
			                              &nbsp;<spring:message code="hr.viewTransactionTransViewList.title.PENDING_CUTTION"/><!-- 未决裁 -->
								 </c:if>								 
							</dt>
						</c:forEach>
				</td>	
				<td>	    
<%--				标记这条发令是否生效: 0未生效 1已生效2裁决否定3发令取消--%>
			        <c:if test="${temp.ACTIVITY== 1 }" >
<%--			             <img src="/resources/images/a_1.gif" style="cursor:hand"/>--%>
						已生效
						
			            </c:if>
			            <c:if test="${temp.ACTIVITY== 0 }" >
<%--			             <img src="/resources/images/0.gif" style="cursor:hand"/>--%>
未生效

			            </c:if>
			            <c:if test="${temp.ACTIVITY== 2 || temp.ACTIVITY== 3}" >
			            	&nbsp;<spring:message code="hr.viewTransactionTransViewList.title.CANCELLED"/><!-- 已取消 -->&nbsp;
			            </c:if>			            
			        </td>	
				</tr>
		    </c:forEach>
		    
		</tbody>
	</table> 
	</td>
  </tr>
</table>
</body>
</html>     	



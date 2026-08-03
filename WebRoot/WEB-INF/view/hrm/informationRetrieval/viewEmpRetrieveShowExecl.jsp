  <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=Hrm.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
   
 
 	<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
	    <td>
	    	<table width="100%" border="2" align="center" cellpadding="0" cellspacing="0" 
	    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
	    		 
	    			 <tr align="center">
	    			   <th align="center" colspan="${colslenth }" ><font size="4" > ${tablename }</font></th>
	    			 </tr>
	    			  <tr align="center">
	    			         <th align="center" ><spring:message code="hr.viewPersonalInfo.title.EMPID"/></th>
		                	<th align="center" ><spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/></th>
			                 <th align="center" ><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/></th>
					  	<c:forEach items="${ColTitle}" var="titleName" varStatus="i">
							 
								 <c:if test="${i.count>3}">
									 <th align="center">${titleName}</th>
									 </c:if>
							</c:forEach>
					     
					 </tr>
					 <c:forEach items="${getEmpRetrieveShowList}" var="map" varStatus="y">
					 <tr align="center">
						 <c:forEach items="${newKeys}" var="keys" varStatus="k">
							 <td align="center">${map[keys]}&nbsp;</td>
						 </c:forEach>
								    
					 </tr>
					 </c:forEach>	
	    		 
	      </table>  
		</td>
	  </tr>
	</table>
	
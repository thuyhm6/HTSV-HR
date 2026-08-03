 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
	<spring:message code="edu.trainreport.KECHENGXINGSHIBIEPEIXUNBAOBIAO.a"/><!--课程形式别培训报表-->
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=empOnStatus.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%" border="1" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="45%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    	    <c:if test="${LoginUser.cpnyId eq 'HAE'}">
    		<tr>
	    		<td align="center" colspan="13" >
	    			<b><font size="+2">
	    				<spring:message code="edu.trainreport.KECHENGXINGSHIBIEPEIXUNBAOBIAO.a"/><!--课程形式别培训报表-->
	    			</font></b>
	    		</td>
	    	</tr>
	    	</c:if>
	    	<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
	    	   <tr>
	    		<td align="center" colspan="10" >
	    			<b><font size="+2">
	    				<spring:message code="edu.trainreport.KECHENGXINGSHIBIEPEIXUNBAOBIAO.a"/><!--课程形式别培训报表-->
	    			</font></b>
	    		</td>
	    	</tr>
	    	</c:if>
			<tr>
				<th width="2%" align="center"><spring:message code="ar.viewcycle.title.xuhao"/><!--序号-->
				</th>
				<th width="4%" align="center"><spring:message code="edu.trainreport.KECHENGXINGSHI.a"/><!--课程形式--></th>
				<th width="4%" align="center"><spring:message code="edu.trainreport.KECHENGSHULIANG.a"/><!--课程数量--></th>
				<th width="4%" align="center"><spring:message code="edu.trainreport.KECHENGCISHU.a"/><!--课程次数--></th>
				<th width="4%" align="center"><spring:message code="edu.planManager.PEIXUNRENSHU.a"/><!--培训人数--></th>
				<th width="4%" align="center"><spring:message code="edu.trainreport.RENJUNPEIXUNCISHU.a"/><!--人均培训次数--></th>
				<th width="4%" align="center"><spring:message code="edu.trainreport.ZONGPEIXUNSHIJIAN.a"/><!--总培训时间--></th>
				 <c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<th width="4%" align="center"><spring:message code="edu.trainreport.PEIXUNSHIJIANRENYUAN.a"/><!--培训时间*人员--></th>
				</c:if>
				<th width="4%" align="center"><spring:message code="edu.trainreport.RENJUNPEIXUNSHIJIAN.a"/><!--人均培训时间--></th>
				<th width="4%" align="center"><spring:message code="edu.trainreport.ZONGPEIXUNFEIYONG.a"/><!--总培训费用--></th>
				 <c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<th width="4%" align="center"><spring:message code="edu.trainCostMANAGER.ZHIJIEJINGFEI.a"/><!--直接经费--></th>
				<th width="4%" align="center"><spring:message code="edu.trainCostMANAGER.JIANJIEJINGFEI.a"/><!--间接经费--></th>
				</c:if>
				<th width="4%" align="center"><spring:message code="edu.trainreport.RENJUNPEIXUNFEIYONG.a"/><!--人均培训费用--></th>
				
			</tr>
			
	       	<c:forEach items="${reportList}" var="item" varStatus="i">
				<tr>
					<td width="5%" align="center">${i.index+1}</td>
					
					<td align="center">${item.TRAIN_FORM_CODE }</td>
		           <td align="center">${item.COUNTS }</td>
		           <td align="center">${item.COUNTS }</td>
		           <td align="center">${item.NUMB }</td>
		           <td align="center">${item.AVGCOUTS }</td>
		           <td align="center">${item.ALLTIME }<spring:message code="ar.viewitemparameter.title.xiaoshi"/><!--小时--></td>
		            <c:if test="${LoginUser.cpnyId eq 'HAE'}">
		           <td align="center">${item.TOTALPT}<spring:message code="ar.viewitemparameter.title.xiaoshi"/><!--小时--></td>
		           </c:if>
		           <td align="center">${item.AVGTIME }<spring:message code="ar.viewitemparameter.title.xiaoshi"/><!--小时--></td>
		            <c:if test="${LoginUser.cpnyId eq 'HAE'}">
		           <td align="center">${item.ZHIJIECOUST }</td>
		           <td align="center">${item.JIANJIECOUST}</td>
		           </c:if>
		           <td align="center">${item.ALLCOST }</td>
		           <td align="center">${item.AVGCOST }</td>
				</tr>
			</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>
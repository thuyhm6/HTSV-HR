 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>
	<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>考勤查看&gt;个人休假</title>
	</head>
	  <% 
      
        response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=yuenianjia.xls");
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "max-age=0"); 
     %>

	<body>
	<table width="100.8%" class="table" layoutH="100" border="1">
	<thead>
	  <tr>
	    <th width="5%" rowspan="2" class="td_center" nowrap="nowrap">职号</th>
	    <th width="5%" rowspan="2" class="td_center" nowrap="nowrap">姓名</th>
	    <th width="5%" rowspan="2" class="td_center" nowrap="nowrap">部门</th>
	    <th width="5%" rowspan="2" class="td_center" nowrap="nowrap">职系</th>
	    <th width="5%" rowspan="2" class="td_center" nowrap="nowrap">入社日期</th>
	    <th width="5%" colspan="3" class="td_center" nowrap="nowrap">年假数</th>
	    <th width="5%" rowspan="2" class="td_center" nowrap="nowrap">累计年假补偿单价</th>
	    <th width="5%" colspan="3" class="td_center" nowrap="nowrap">金额</th>
	  </tr>
	  <tr>
	    <th width="5%" class="td_center" nowrap="nowrap">转年</th>
	    <th width="5%" class="td_center" nowrap="nowrap">今年</th>
	    <th width="5%" class="td_center" nowrap="nowrap">计</th>
	    <th width="5%" class="td_center" nowrap="nowrap">转年</th>
	    <th width="5%" class="td_center" nowrap="nowrap">今年</th>
	    <th width="5%" class="td_center" nowrap="nowrap">计</th>
	  </tr>
	  </thead>
	  
	  
	  <c:forEach items="${arVacationLiquidationList}" var="oneResult"varStatus="i">
			<tr align="center">
				<td nowrap="nowrap"> 
                   <input type="hidden" name="yinian_${oneResult.VACATION_NO}"value="${oneResult.YINIAN}" />
                   <input type="hidden" name="bennian_${oneResult.VACATION_NO}"value="${oneResult.BENNIAN}" />&nbsp;
                                        <c:set var="empsum" value="${empsum+1}">
                                        </c:set>
                                        <c:set var="yiniansum" value="${yiniansum+oneResult.YINIAN}">
                                        </c:set>
                                        <c:set var="benniansum"
                                            value="${benniansum+oneResult.BENNIAN}">
                                        </c:set>
                                        <c:set var="xiajisum"
                                            value="${xiajisum+oneResult.YINIAN+oneResult.BENNIAN}">
                                        </c:set>
                                        <c:set var="yinianjinsum"
                                            value="${yinianjinsum+oneResult.YINIANJE}">
                                        </c:set>
                                        <c:set var="bennianjinsum"
                                            value="${bennianjinsum+oneResult.BENNIANJE}">
                                        </c:set>
                                        <c:set var="xiajijinsum"
                                            value="${xiajijinsum+oneResult.YINIANJE+oneResult.BENNIANJE}">
                                        </c:set>
                                        <c:set var="danjiaheji"
                                            value="${danjiaheji+oneResult.DANJIA}">
                                        </c:set>
										
										${oneResult.EMPID}
									</td>
									<td  nowrap="nowrap">${oneResult.LOCAL_NAME} &nbsp;</td>
									<td  nowrap="nowrap">${oneResult.DEPTNAME} &nbsp;</td>
									<td nowrap="nowrap">${oneResult.POST_COEFNAME} &nbsp;</td>
									<td nowrap="nowrap">${oneResult.DATE_STARTED}</td>
									<td nowrap="nowrap">
										<c:if test="${liquidation eq '125081'}">-</c:if>
										<c:if test="${liquidation eq '125080' || liquidation eq '125082'}"> ${oneResult.YINIAN}<spring:message code="display.mutual.day"/></c:if>
									</td>
									<td nowrap="nowrap">
										<c:if test="${liquidation eq '125080'}">-</c:if>
										<c:if test="${liquidation eq '125081' || liquidation eq '125082'}">${oneResult.BENNIAN}<spring:message code="display.mutual.day"/></c:if>
									</td>
									<td nowrap="nowrap">
										${oneResult.YINIAN+oneResult.BENNIAN}
										<spring:message code="display.mutual.day"/>
									</td>
									<td nowrap="nowrap">
										${oneResult.DANJIA}
									</td>
									<td nowrap="nowrap">
										<c:if test="${liquidation eq '125081'}">-</c:if>
										<c:if test="${liquidation eq '125080' || liquidation eq '125082'}"> ${oneResult.YINIANJE}</c:if>
									</td>
									<td nowrap="nowrap">
										<c:if test="${liquidation eq '125080'}">-</c:if>
										<c:if test="${liquidation eq '125081' || liquidation eq '125082'}"> ${oneResult.BENNIANJE} </c:if>
									</td>

									<td nowrap="nowrap">${oneResult.YINIANJE+oneResult.BENNIANJE}</td>
								</tr>
							</c:forEach>
	</table>
	</body>
</html>
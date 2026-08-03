 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--在职人员信息统计表--%>
	在职人员信息统计表
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=empOnStatus.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="45%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td align="center" colspan="12" >
	    			<b><font size="+2"><%--在职人员信息统计表--%>
	    				江苏玛特${YEAR_ONSTATUS }年${MONTH_ONSTATUS }月在职人员信息统计表
	    			</font></b>
	    		</td>
	    	</tr>
	    	
			<tr><%--
				<th style="text-align: center" rowspan="2">序号
					序号
				</th>--%>
				<th style="text-align: center" rowspan="2"><%--组织/类型--%>
					组织/类型
				</th>
				<th style="text-align: center" rowspan="2"><%--合同工人数--%>
					合同工人数
				</th>
				<th style="text-align: center" rowspan="2"><%--劳务工人数--%>
					劳务工人数
				</th>
				<th style="text-align: center" rowspan="2"><%--计时人数--%>
					计时人数
				</th>
				<th style="text-align: center" rowspan="2"><%-- 学生人数--%>
					学生人数
				</th>
				<th style="text-align: center" rowspan="2"><%--全店在职人数--%>
					全店在职人数
				</th>
				<th style="text-align: center" colspan="3"><%--参保人数--%>
					参保人数
				</th>
				<th style="text-align: center" rowspan="2"><%-- 离职人数--%>
					 离职人数
				</th>
				<th style="text-align: center" rowspan="2"><%--计薪人数--%>
					计薪人数
				</th>
				<th style="text-align: center" rowspan="2"><%--离职率--%>
					离职率
				</th>
			</tr>
			<tr>
				<th style="text-align: center"><%--店内--%>
					店内
				</th>
				<th style="text-align: center"><%--派入--%>
					派入
				</th>
				<th style="text-align: center"><%--合计--%>
					合计
				</th>
			</tr>   
	       	<c:forEach items="${empOnStatusList}" var="item" varStatus="i">
				<tr>
					<%--
					<td style="text-align: center">${i.index+1 }</td>
					--%>
					<c:if test="${item.DEPT_DISTINGUISH eq 'COMPANY_TOTAL'}">
						<td style="text-align: center"><font size="3" color="red"><b>全公司~合计</b></font></td>
					</c:if>
					<c:if test="${item.DEPT_DISTINGUISH eq 'ZONGFEN_TOTAL' && item.DEPT_ALL_NO eq 'zongbu'}">
						<td style="text-align: center"><font size="2" color="red"><b>总部~合计</b></font></td>
					</c:if>
					<c:if test="${item.DEPT_DISTINGUISH eq 'ZONGFEN_TOTAL' && item.DEPT_ALL_NO eq 'fendian'}">
						<td style="text-align: center"><font size="2" color="red"><b>分店~合计</b></font></td>
					</c:if>
					<c:if test="${item.DEPT_DISTINGUISH eq 'QUYU_TOTAL'}">
						<td style="text-align: center"><font size="2" color="red"><b>${item.DEPT_TYPE_NAME }~小计</b></font></td>
					</c:if>
					<c:if test="${item.DEPT_DISTINGUISH ne 'COMPANY_TOTAL' && item.DEPT_DISTINGUISH ne 'ZONGFEN_TOTAL' && item.DEPT_DISTINGUISH ne 'QUYU_TOTAL'}">
						<td style="text-align: center">${item.DEPT_DISTINGUISH_NAME }</td>
					</c:if>
					
					<td style="text-align: center">${item.CON_CNT }</td>
					<td style="text-align: center">${item.DIS_CNT }</td>
					<td style="text-align: center">${item.HOU_CNT }</td>
					<td style="text-align: center">${item.STU_CNT }</td>
					
					<td style="text-align: center">${item.ALL_CNT }</td>
					<td style="text-align: center">${item.DIANLI_CNT }</td>
					<td style="text-align: center">${item.DISPATCH_CNT }</td>
					<td style="text-align: center">${item.IS_CNT }</td>
					
					
					<td style="text-align: center">${item.LIZHI_CNT }</td>
					<td style="text-align: center">${item.PA_CNT }</td>
					<td style="text-align: right">
						<fmt:formatNumber value="${item.LIZHI_PET }" pattern="#,##0.00#"/>
					</td>
				</tr>
			</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>
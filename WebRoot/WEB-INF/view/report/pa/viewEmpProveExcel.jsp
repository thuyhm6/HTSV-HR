 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--员工在职证明--%>
	<spring:message code="rp.report.title.certificateofemployee"/>
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=officeProveofEmployee.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="45%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
<!--     		<tr> -->
<!--     			<td colspan="5" align="left"><%--发行号码&nbsp;&nbsp;xx公司 2012-01 号&nbsp;&nbsp;--%> -->
<!--     				<spring:message code="rp.report.title.certificatenumber"/>&nbsp;&nbsp;${COMPANY_NAME }&nbsp;${YEAR }-${MONTH }&nbsp;号&nbsp;&nbsp; -->
<!--     			</td> -->
<!--     		</tr> -->
<!--     		<br/> -->
    		<tr>
	    		<td align="center" colspan="5" >
	    			<b><font size="+2"><%--(在   职) 证 明 书--%><spring:message code="rp.report.title.vocationalcertificate"/></font></b>
	    		</td>
	    	</tr>
			<tr>
				<th align="center"><%--区分--%>
					<spring:message code="hr.viewHealth.title.INDUSTRY_DISTINGUISH_NAME"/>
				</th>
				<th align="center"><%--姓名--%>
					<spring:message code="public.title.name"/>
				</th>
				<th align="center"><%--护照号码 /身份证号码--%>
					<spring:message code="rp.report.title.idcardorpassport"/>
				</th>
				<th align="center"><%--职位--%>
					<spring:message code="sys.postManage.title.position"/>
				</th>
				<th align="center"><%--在职期间--%>
					<spring:message code="rp.report.title.officepersoid"/>
				</th>
			</tr>        
	       	<c:forEach items="${empOfficeProveList}" var="item" varStatus="i">
				<tr target="EMPID" rel="${item.EMPID}">
					<td align="center">${i.index+1 }</td>
					<td align="center">${item.LOCAL_NAME }</td>
					<td align="center" style="vnd.ms-excel.numberformat:@">${item.IDCARD_NO }</td>
					<td align="center">${item.POSTNO }</td>
					<td align="center">${fn:substring(item.DATE_STARTED,2,4)}<spring:message code="liang.hr.viewWorkInfo.title.YEAR"/>${fn:substring(item.DATE_STARTED,5,7)}<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/>${fn:substring(item.DATE_STARTED,8,10)}<spring:message code="liang.hr.viewWorkInfo.title.DAY"/>
 						<%--<c:if test="${item.DATE_LEFT ne null}">~&nbsp;&nbsp;${item.DATE_LEFT }</c:if>  --%> 
					</td>
				</tr>
			</c:forEach>    
<!-- 			<tr> -->
<!-- 				<td colspan="2"><b><%--用途--%><spring:message code="rp.report.title.useness"/></b></td> -->
<!-- 				<td colspan="3" align="center"><%--银行提交用--%><spring:message code="rp.report.title.bankssubmittedwith"/></td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td colspan="5" align="center"> -->
<!-- 					<font size="3"><%--上述一起证明.--%><spring:message code="rp.report.title.abovewiththeproof"/></font> -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td colspan="5" align="right"><%--xxxx年xx月xx日--%> -->
<!-- 					${YEAR }<spring:message code="rp.report.title.year"/>${MONTH }<spring:message code="rp.report.title.month"/>${DDATE }<spring:message code="rp.report.title.date"/> -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td style="text-align:center" colspan="5"> -->
<!-- 					<b><font size="5">${COMPANY_NAME }</font></b>	 -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td style="text-align:center" colspan="5"> -->
<!-- 					<b><font size="5">董&nbsp;&nbsp;事&nbsp;&nbsp;长&nbsp;&nbsp;&nbsp;&nbsp;</font></b>	 -->
<!-- 				</td> -->
<!-- 			</tr> -->
      </table>  
	</td>
  </tr>
</table>
</body>
</html>
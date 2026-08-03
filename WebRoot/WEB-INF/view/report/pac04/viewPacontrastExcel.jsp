 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
	<spring:message code="rp.report.title.certificateofemployee"/>
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=gongziduizhao.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>

    	<TABLE  width="45%" border="1" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
   	<tr>
	    		<td  colspan="7" align="center">
	    			<b><font size="+1">乐天（中国）食品有限公司${YEAR }年${MONTH }工资对照表</font></b>
	    		</td>
	    	</tr>
			  <TR>
				<TD >序号</TD>
				<TD >姓名</TD>
				<TD>部门名称</TD>
				<TD>${MONTH-1}月基本工资</TD>
				<TD >${MONTH}月基本工资</TD>
				<TD>基本工资差额</TD>
				<TD>原因</TD>
			  </TR>
			  	<c:forEach  items="${viewPacontrastList}" var="PacontrastList"  varStatus="i"  >
			  <tr>
			  <td> ${i.count}   </td>
			  <td> ${PacontrastList.MZ}</td>
			  <td> ${PacontrastList.BM } </td>
			  <td> ${PacontrastList.DESERVE_BASIC_WAGE2 } </td>
			  <td> ${PacontrastList.DESERVE_BASIC_WAGE1} </td>
			  <td> ${PacontrastList.CHA } </td>
			  <td> </td>
			  </tr>
			  </c:forEach>
			  <tr >
			  	<td colspan="3"  align="center">合计</td>
			  	<c:forEach items="${viewPacontrastListsum}" var="PacontrastListsum" >
			  	<td> ${PacontrastListsum.HE2 } </td>
			  	<td> ${PacontrastListsum.HE1}</td>
			    <td> ${PacontrastListsum.HE3 }</td>
			  	</c:forEach>
			  </tr>
  </TABLE>
</body>
</html>
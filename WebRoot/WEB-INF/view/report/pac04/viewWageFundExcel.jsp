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
		response.setHeader("Content-Disposition", "attachment; filename=viewWageFund.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
   <!-- 
   	这个公司没有年终奖，项目奖少的可怜。拖欠工资，还不给交保险。月月还扣保险钱。别想了兄弟。走吧。
   	这个系统BUG 倍出 来了有你好受的。好自为之。
    -->
<table width="45%"  align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<TABLE border=1 width="45%"  align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
   	<tr>
	    		<td align="center" colspan="33" >
	    			<b><font size="+1">乐天（中国）食品有限公司${YEAR }年${MONTH }月考勤工资基金计算表</font></b>
	    		</td>
	    	</tr>
			  <TR>
				<TD rowspan="3"  colspan="3" >项目 </TD>
				<TD colspan="10" align="center">正式工</TD>
				<TD colspan="9"  align="center">劳务工</TD>
				<TD rowspan="3"  colspan="3" >项目 </TD>
				<TD colspan="8"  align="center">${YEAR }年${MONTH }月发放人件费</TD>
			  </TR>
			  <TR>
				<TD rowspan="2"  width="45" >工资人数</TD>
				<TD colspan="7"  align="center">公司缴纳社保及公积金</TD>
				<TD colspan="2"  align="center">工会经费</TD>
				<TD rowspan="2"  width="45" >工资人数</TD>
				<TD colspan="6"  align="center">公司缴纳社保及公积金</TD>
				<TD colspan="2"  align="center">工会经费</TD>
				<TD colspan="4"  align="center">正式工</TD>
				<TD colspan="4"  align="center">劳务工</TD>
			<!-- 	<TD colspan="4"  align="center">公司合计</TD> -->
			  </TR>
			  <TR>
				<TD width="40">医疗保险</TD>
				<TD width="40">养老保险</TD>
				<TD width="40">失业保险</TD>
				<TD width="40">工伤保险</TD>
				<TD width="40">生育保险</TD>
				<TD width="40">住房积金</TD>
				<TD width="40">小计</TD>
				<TD width="40">工资总额</TD>
				<TD width="40">工会经费</TD>
				<TD width="40">医疗保险</TD>
				<TD width="40">养老保险</TD>
				<TD width="40">失业保险</TD>
				<TD width="40">工伤保险</TD>
				<TD width="40">生育保险</TD>
				<TD width="40">小计</TD>
				<TD width="40">工资总额</TD>
				<TD width="40">工会经费</TD>
				<TD width="40">人数</TD>
				<TD width="40">工资总额</TD>
				<TD width="40">公司社保</TD>
				<TD width="40">人件费</TD>
				<TD width="40">人数</TD>
				<TD width="40">工资总额</TD>
				<TD width="40">公司社保</TD>
				<TD width="40">人件费</TD>
			<!-- 	<TD width="40">人数</TD>
				<TD width="40">工资总额</TD>
				<TD width="40">公司社保</TD>
				<TD width="40">人件费</TD> -->
			  </TR>
			  	<c:forEach  items="${paWageFundList}" var="WageFundList"  >
			  <tr>
			  <td> ${WageFundList.SCQF}</td>
			  <td> ${WageFundList.EJBM}</td>
			  <td> ${WageFundList.SJBM }</td>
			  <td> ${WageFundList.RS }</td>
			  <td>${ WageFundList.YLBX }</td>
			  <td> ${WageFundList.YLAOBX }</td>
			  <td> ${WageFundList.SYBX }</td>
			  <td> ${WageFundList.GSBX }</td>
			  <td> ${WageFundList.SYUBX }</td>
			  <td> ${WageFundList.GJJ }</td>
			  <td> ${WageFundList.XJ }</td>
			  <td> ${WageFundList.GZZE }</td>
			  <td> ${WageFundList.GHJF }</td>
			  
			  <td> ${WageFundList.RS }</td>
			  <td>${ WageFundList.YLBX }</td>
			  <td> ${WageFundList.YLAOBX }</td>
			  <td> ${WageFundList.SYBX }</td>
			  <td> ${WageFundList.GSBX }</td>
			  <td> ${WageFundList.SYUBX }</td>

			  <td> ${WageFundList.XJ }</td>
			  
			  <td> ${WageFundList.GZZE }</td>
			  <td> ${WageFundList.GHJF }</td>
			  
		      <td> ${WageFundList.SCQF}</td>
			  <td> ${WageFundList.EJBM}</td>
			  <td> ${WageFundList.SJBM }</td>
			  <td> ${WageFundList.RS }</td>
			  <td> ${WageFundList.GZZE} </td>
			  <td> ${WageFundList.XJ}</td>
			  <td> ${WageFundList.RJF}</td>
			   
			  <td> ${WageFundList.RS} </td>
			  <td> ${WageFundList.GZZE} </td>
			  <td> ${WageFundList.XJ}</td>
			  <td> ${WageFundList.RJF}</td>
			  
			  </tr>
			  </c:forEach>
  			<tr>
				<td style="text-align:center" colspan="3"><b>总经理：</b></td>
				<td style="text-align:center" colspan="3"><b>部长：</b></td>
				<td style="text-align:center" colspan="3"><b>科长：</b></td>
				<td style="text-align:center" colspan="3"><b>制表人：</b></td>
				<td style="text-align:center"><b>制表日期：</b></td>
				<td style="text-align:center"><%--xxxx年xx月xx日--%>${date }</td>
			</tr> 
  </TABLE>
	</td>
  </tr>
</table>
</body>
</html>
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
		response.setHeader("Content-Disposition", "attachment; filename=geshui.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>

    	<TABLE border="1" width="45%" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
   	<tr>
	    		<td  colspan="18"  align="center">
	    			<b><font size="+1">乐天（中国）食品有限公司${YEAR }年${MONTH }工资个税表</font></b>
	    		</td>
	    	</tr>
			  <TR>
				<TD >序号</TD>
				<TD >姓名</TD>
				<TD>身份证照类型</TD>
				<TD>身份证号</TD>
				<TD >国家与地区</TD>
				<TD>职业编码</TD>
				<TD>所得项目</TD>
				<TD>应税工资</TD>
				<TD>免税收入额</TD>
				<TD>允许扣除的税费</TD>
				<TD>费用扣除标准</TD>
				<TD>准予扣除的捐赠额</TD>
				<TD>应纳税所得额</TD>
				<TD>税率</TD>
				<TD>应扣税额</TD>
				<TD>已扣税额</TD>
				<TD>所属单位</TD>
				<TD>备注</TD>
			  </TR>
			  	<c:forEach  items="${viewPersonaltaxList}" var="PersonaltaxList"  varStatus="i"  >
			  <tr>
			  <td> ${i.count}   </td>
			  <td> ${PersonaltaxList.MZ}</td>
			  <td> 身份证 </td>
			  <td> ${PersonaltaxList.SFZ}&nbsp;</td>
			  <td> &nbsp;001 </td>
			  <td> 6000000 </td>
			  <td> &nbsp;0101</td>
			  <td>${PersonaltaxList.YSGZ }</td>
			  <td>${PersonaltaxList.SSKCS}</td>
			  <td>0</td>
			  <td>${PersonaltaxList.KSJZ }</td>
			  <td>0</td>
			  <td>${PersonaltaxList.YNSE }</td>
			  <td>${PersonaltaxList.SL }</td>
			  <td>${PersonaltaxList.GRSDS }</td>
			  <td>${PersonaltaxList.GRSDS }</td>
			  <td></td>
			  <td></td>
			  </tr>
			  </c:forEach>
  </TABLE>
</body>
</html>
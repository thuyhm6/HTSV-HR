<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
MONTH_DAY	</td>
	<td width="25%" align="center">
日期	</td>
<td width="25%" align="center">
日期</td>
<td width="35%">
<input type="text" name="MONTH_DAY"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})" value="2017/01/01"/></td>
</tr>
<%@include file="newfooter.jsp" %>

<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
AR_MONTH	</td>
	<td width="25%" align="center">
月份	</td>
<td width="25%" align="center">
月份</td>
<td width="35%">
<input type="text" name="AR_MONTH"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyyMM'})" value="201701"/></td>
</tr>
<%@include file="newfooter.jsp" %>

<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
AR_MONTH	</td>
	<td width="25%" align="center">
AR_MONTH	</td>
<td width="25%" align="center">
年月</td>
<td width="35%">
<input type="text" id="AR_MONTH" name="AR_MONTH" class="Wdate" 
					 onClick="WdatePicker({dateFmt:'yyyy/MM'})" value="2017/01"/></td>
</tr>
<%@include file="newfooter.jsp" %>

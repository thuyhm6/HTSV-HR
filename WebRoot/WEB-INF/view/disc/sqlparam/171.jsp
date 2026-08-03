<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
DATE	</td>
	<td width="25%" align="center">
日期	</td>
<td width="25%" align="center">
日期</td>
<td width="35%">
 <input type="text"  name="DATE" class="Wdate required"  readonly="true"  onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})"  />
</td>
</tr>
<%@include file="newfooter.jsp" %>

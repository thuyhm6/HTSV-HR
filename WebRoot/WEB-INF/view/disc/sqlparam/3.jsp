<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
interCpnyID	</td>
	<td width="25%" align="center">
null	</td>
<td width="25%" align="center">
null</td>
<td width="35%">
<ait:deptList name="interCpnyID" cpnyId="${CPNY_ID}" id="interCpnyID" limit="hr" /><ait:deptTreeIcon name="interCpnyID" cpnyId="${CPNY_ID}" id="interCpnyID" limit="hr"/></td>
</tr>
<%@include file="newfooter.jsp" %>

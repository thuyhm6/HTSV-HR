<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
RESUME_NO	</td>
	<td width="25%" align="center">
null	</td>
<td width="25%" align="center">
null</td>
<td width="35%">
<input type="text" id="RESUME_NO" name="RESUME_NO" class="date required"  format="yyyy-MM-dd" readonly="true" /><a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a></td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
interCpnyID	</td>
	<td width="25%" align="center">
null	</td>
<td width="25%" align="center">
null</td>
<td width="35%">
<ait:SelectArItem   name="interCpnyID" parentNo="all" 	 limit="all"/> </td>
</tr>
<%@include file="newfooter.jsp" %>

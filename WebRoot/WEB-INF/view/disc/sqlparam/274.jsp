<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
YEAR	</td>
	<td width="25%" align="center">
年份	</td>
<td width="25%" align="center">
年份</td>
<td width="35%">
<input type="text" id="YEAR" name="YEAR" class="date required"  format="yyyy" readonly="true" /><a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"></spring:message><!-- 选择 --></a></td>
</tr>
<%@include file="newfooter.jsp" %>

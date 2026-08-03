<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
AR_YEAR	</td>
	<td width="25%" align="center">
年份(例如:2016)	</td>
<td width="25%" align="center">
年份(例如:2016)</td>
<td width="35%">
<input type="text" id="AR_YEAR" name="AR_YEAR" class="date required"  format="yyyy" readonly="true" /><a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a></td>
</tr>
<%@include file="newfooter.jsp" %>

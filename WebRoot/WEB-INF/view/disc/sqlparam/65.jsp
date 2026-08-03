<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
SELECT_DATE	</td>
	<td width="25%" align="center">
null	</td>
<td width="25%" align="center">
null</td>
<td width="35%">
<input type="text" id="SELECT_DATE" name="SELECT_DATE" class="date required"  format="yyyy.MM.dd" readonly="true" /><a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a></td>
</tr>
<%@include file="newfooter.jsp" %>

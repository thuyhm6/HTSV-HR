<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
S_DATE	</td>
	<td width="25%" align="center">
null	</td>
<td width="25%" align="center">
起始</td>
<td width="35%">
<input type="text" id="S_DATE" name="S_DATE" class="date required"  format="yyyy.MM.dd" readonly="true" /><a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"></spring:message><!-- 选择 --></a></td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
E_DATE	</td>
	<td width="25%" align="center">
null	</td>
<td width="25%" align="center">
结束</td>
<td width="35%">
<input type="text" id="E_DATE" name="E_DATE" class="date required"  format="yyyy.MM.dd" readonly="true" /><a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"></spring:message><!-- 选择 --></a></td>
</tr>
<%@include file="newfooter.jsp" %>

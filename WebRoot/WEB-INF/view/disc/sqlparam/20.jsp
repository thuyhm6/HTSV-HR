<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr  style="display:none"   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
PERSON_ID	</td>
	<td width="25%" align="center">
PERSON_ID	</td>
<td width="25%" align="center">
人员ID</td>
<td width="35%">
<input type="text" name="PERSON_ID" readonly id="PERSON_ID"  value="${LoginUser.personId}"/></td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
interLanguage	</td>
	<td width="25%" align="center">
语言	</td>
<td width="25%" align="center">
语言</td>
<td width="35%">
<input type="text" name="interLanguage" id="interLanguage"   value="""/></td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
interCpnyID	</td>
	<td width="25%" align="center">
null	</td>
<td width="25%" align="center">
null</td>
<td width="35%">
<input type="text" name="interCpnyID" id="interCpnyID"   value=""/></td>
</tr>
<%@include file="newfooter.jsp" %>

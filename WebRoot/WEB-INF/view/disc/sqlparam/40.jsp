<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
PA_MONTH	</td>
	<td width="25%" align="center">
月份	</td>
<td width="25%" align="center">
月份</td>
<td width="35%">
<input type="text" name="PA_MONTH" id="PA_MONTH"   value=""/></td>
</tr>
<tr   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
PERSON_ID	</td>
	<td width="25%" align="center">
员工号	</td>
<td width="25%" align="center">
员工号</td>
<td width="35%">
<input type="text" name="PERSON_ID" id="PERSON_ID"   value="${LoginUser.personId}" readOnly/></td>
</tr>
<%@include file="newfooter.jsp" %>

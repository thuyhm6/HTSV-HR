<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
PA_MONTH	</td>
	<td width="25%" align="center">
null	</td>
<td width="25%" align="center">
null</td>
<td width="35%">
<input type="text" name="PA_MONTH" id="PA_MONTH"   value=""/></td>
</tr>
<tr>
<td colspan="4" ></td>
</tr>
<tr   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
PERSON_ID	</td>
	<td width="25%" align="center">
null	</td>
<td width="25%" align="center">
null</td>
<td width="35%">
<input type="text" name="PERSON_ID" id="PERSON_ID"   value=""/></td>
</tr>
<tr>
<td colspan="4" ></td>
</tr>
<tr   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
PAY_AREA_CD	</td>
	<td width="25%" align="center">
null	</td>
<td width="25%" align="center">
null</td>
<td width="35%">
<ait:deptTreeMulti id="PAY_AREA_CD" name="PAY_AREA_CD" limit="hr" level="2" ></ait:deptTreeMulti></td>
</tr>
<tr>
<td colspan="4" ></td>
</tr>
<%@include file="newfooter.jsp" %>

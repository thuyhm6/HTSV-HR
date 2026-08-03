<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
KEY	</td>
	<td width="25%" align="center">
null	</td>
<td width="25%" align="center">
null</td>
<td width="35%">
<ait:deptList name="KEY" cpnyId="${CPNY_ID}" id="KEY" limit="hr" ></ait:deptList><ait:deptTreeIcon name="KEY" cpnyId="${CPNY_ID}" id="KEY" limit="hr"></ait:deptTreeIcon></td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
EMP_TYPE_CODE	</td>
	<td width="25%" align="center">
null	</td>
<td width="25%" align="center">
null</td>
<td width="35%">
<input type="text" name="EMP_TYPE_CODE" id="EMP_TYPE_CODE"   value=""/></td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
adminId	</td>
	<td width="25%" align="center">
null	</td>
<td width="25%" align="center">
null</td>
<td width="35%">
<input type="text" name="adminId" id="adminId"   value=""/></td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
YEAR	</td>
	<td width="25%" align="center">
null	</td>
<td width="25%" align="center">
null</td>
<td width="35%">
<input type="text" name="YEAR" id="YEAR"   value=""/></td>
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
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
DEPT_NO	</td>
	<td width="25%" align="center">
null	</td>
<td width="25%" align="center">
null</td>
<td width="35%">
<input type="text" name="DEPT_NO" id="DEPT_NO"   value=""/></td>
</tr>
<%@include file="newfooter.jsp" %>

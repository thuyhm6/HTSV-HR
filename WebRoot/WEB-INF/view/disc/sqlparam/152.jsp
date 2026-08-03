<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr  style="display:none"   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
CPNY	</td>
	<td width="25%" align="center">
CPNY	</td>
<td width="25%" align="center">
法人</td>
<td width="35%">
<input type="text" name="CPNY" readonly id="CPNY"  value='${CPNY_ID}'/></td>
</tr>
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
RTIME	</td>
	<td width="25%" align="center">
null	</td>
<td width="25%" align="center">
null</td>
<td width="35%">
<input type="text" name="RTIME" id="RTIME"   value=""/></td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
DEPTNO	</td>
	<td width="25%" align="center">
null	</td>
<td width="25%" align="center">
null</td>
<td width="35%">
<input type="text" name="DEPTNO" id="DEPTNO"   value=""/></td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
KEY	</td>
	<td width="25%" align="center">
null	</td>
<td width="25%" align="center">
null</td>
<td width="35%">
<input type="text" name="KEY" id="KEY"   value=""/></td>
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
STIME	</td>
	<td width="25%" align="center">
null	</td>
<td width="25%" align="center">
null</td>
<td width="35%">
<input type="text" name="STIME" id="STIME"   value=""/></td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
interLanguage	</td>
	<td width="25%" align="center">
null	</td>
<td width="25%" align="center">
null</td>
<td width="35%">
<input type="text" name="interLanguage" id="interLanguage"   value=""/></td>
</tr>
<%@include file="newfooter.jsp" %>

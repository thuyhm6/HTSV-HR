<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
DEPTNO	</td>
	<td width="25%" align="center">
部门	</td>
<td width="25%" align="center">
部门</td>
<td width="35%">
<ait:deptList name="DEPTNO" cpnyId="${CPNY_ID}" id="DEPTNO" limit="hr"  /> <ait:deptTreeIcon	name="DEPTNO" cpnyId="${CPNY_ID}" id="DEPTNO" limit="hr" /></td>
</tr>
<tr   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
USER_NO	</td>
	<td width="25%" align="center">
员工号	</td>
<td width="25%" align="center">
员工号</td>
<td width="35%">
<input type="text" name="USER_NO" id="USER_NO"   value="${LoginUser.userNo}" readOnly/></td>
</tr>
<%@include file="newfooter.jsp" %>

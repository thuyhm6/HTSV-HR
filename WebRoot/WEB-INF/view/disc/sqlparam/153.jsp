<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
AR_MONTH_STR	</td>
	<td width="25%" align="center">
日期（如：201509）	</td>
<td width="25%" align="center">
日期（如：201509）</td>
<td width="35%">
<input type="text" name="AR_MONTH_STR" id="AR_MONTH_STR"   value=""/></td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
DEPT_NO	</td>
	<td width="25%" align="center">
部门	</td>
<td width="25%" align="center">
部门</td>
<td width="35%">
<ait:deptList name="DEPT_NO" cpnyId="${CPNY_ID}" id="DEPT_NO" limit="pa"  ></ait:deptList> <ait:deptTreeIcon	name="DEPT_NO" cpnyId="${CPNY_ID}" id="DEPT_NO" limit="pa" ></ait:deptTreeIcon></td>
</tr>
<%@include file="newfooter.jsp" %>

<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
YEAR	</td>
	<td width="25%" align="center">
年份	</td>
<td width="25%" align="center">
年份</td>
<td width="35%">
<input type="text" id="YEAR" name="YEAR" class="Wdate" 
					 onClick="WdatePicker({dateFmt:'yyyy'})" value="${YEAR}"/></td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
DEPTNO_Multi	</td>
	<td width="25%" align="center">
部门	</td>
<td width="25%" align="center">
部门</td>
<td width="35%">
<ait:deptTreeMulti id="DEPTNO" name="DEPTNO_Multi" limit="ar" selectedNm="${DEPTNO_Multi}" selected="${DEPTNO}"></ait:deptTreeMulti></td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
EMPLOYEE_OWNED	</td>
	<td width="25%" align="center">
员工所属	</td>
<td width="25%" align="center">
员工所属</td>
<td width="35%">
<ait:selectCodeMulti id="EMPLOYEE_OWNED"  name="EMPLOYEE_OWNED1" parentNo="14015585"></ait:selectCodeMulti></td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
END_PROBATION_DATE	</td>
	<td width="25%" align="center">
是否含试用期	</td>
<td width="25%" align="center">
是否含试用期</td>
<td width="35%">
<input type="checkbox" name="END_PROBATION_DATE1" id="END_PROBATION_DATE"   value="Y"/></td>
</tr>
<%@include file="newfooter.jsp" %>

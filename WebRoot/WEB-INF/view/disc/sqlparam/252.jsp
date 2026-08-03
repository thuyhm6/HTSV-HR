<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
S_PA_MONTH	</td>
	<td width="25%" align="center">
开始月	</td>
<td width="25%" align="center">
开始月</td>
<td width="35%">
<input type="text" name="S_PA_MONTH" id="S_PA_MONTH"   value=""/></td>
</tr>
<tr   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
E_PA_MONTH	</td>
	<td width="25%" align="center">
结束月	</td>
<td width="25%" align="center">
结束月</td>
<td width="35%">
<input type="text" name="E_PA_MONTH" id="E_PA_MONTH"   value=""/></td>
</tr>
<tr   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
EMP_TYPE_CODE	</td>
	<td width="25%" align="center">
人员类型	</td>
<td width="25%" align="center">
人员类型</td>
<td width="35%">
<ait:SelectEmpTypeCode name="EMP_TYPE_CODE" id="EMP_TYPE_CODE" limit="pa" type=""></ait:SelectEmpTypeCode></td>
</tr>
<tr   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
PAY_AREA_CD	</td>
	<td width="25%" align="center">
大区	</td>
<td width="25%" align="center">
大区</td>
<td width="35%">
<ait:deptTreeMulti id="PAY_AREA_CD" name="PAY_AREA_NM" limit="hr" level="2" ></ait:deptTreeMulti></td>
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

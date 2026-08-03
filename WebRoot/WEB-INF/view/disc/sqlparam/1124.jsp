<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr  style="display:none"   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
CPNY_ID	</td>
	<td width="25%" align="center">
CPNY_ID	</td>
<td width="25%" align="center">
法人</td>
<td width="35%">
<input type='text' name='CPNY_ID' id='CPNY_ID' readonly  value='${CPNY_ID}'/></td>
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
PAY_AREA_CD	</td>
	<td width="25%" align="center">
大区	</td>
<td width="25%" align="center">
大区</td>
<td width="35%">
<ait:deptTreeMulti id="PAY_AREA_CD" name="PAY_AREA_NM" limit="pa" level="2" ></ait:deptTreeMulti></td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
AR_MONTH_STR	</td>
	<td width="25%" align="center">
考勤月	</td>
<td width="25%" align="center">
考勤月</td>
<td width="35%">
<input type="text" name="AR_MONTH_STR" id="AR_MONTH_STR"   value=""/></td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
JOBTYPE_GROUP_NO	</td>
	<td width="25%" align="center">
工作类型组	</td>
<td width="25%" align="center">
工作类型组</td>
<td width="35%">
<ait:SelectEmpTypeCode name="JOBTYPE_GROUP_NO" id="JOBTYPE_GROUP_NO" limit="pa" type="group"></ait:SelectEmpTypeCode></td>
</tr>
<%@include file="newfooter.jsp" %>

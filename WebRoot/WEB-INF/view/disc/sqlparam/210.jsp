<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
PERSON_ID	</td>
	<td width="25%" align="center">
PERSON_ID	</td>
<td width="25%" align="center">
人员ID</td>
<td width="35%">
<input type="text" name="PERSON_ID" readonly id="PERSON_ID"  value="${LoginUser.personId}"/></td>
</tr>
<tr   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
CPNY_ID	</td>
	<td width="25%" align="center">
CPNY_ID	</td>
<td width="25%" align="center">
法人</td>
<td width="35%">
<input type='text' name='CPNY_ID' id='CPNY_ID' readonly  value='${CPNY_ID}'/></td>
</tr>
<tr   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
CPNY	</td>
	<td width="25%" align="center">
CPNY	</td>
<td width="25%" align="center">
法人</td>
<td width="35%">
<input type="text" name="CPNY" readonly id="CPNY"  value='${CPNY_ID}'/></td>
</tr>
<tr   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
FROM_TIME	</td>
	<td width="25%" align="center">
FROM_TIME	</td>
<td width="25%" align="center">
开始日期</td>
<td width="35%">
<input type="text" id="FROM_TIME" name="FROM_TIME" class="date required"  onpropertychange="getPOtApplyType();ajaxAdd_add_ot_apply_one_three();"format="yyyyMMdd" readonly="true" /><a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"></spring:message><!-- 选择 --></a></td>
</tr>
<tr   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
TO_TIME	</td>
	<td width="25%" align="center">
TO_TIME	</td>
<td width="25%" align="center">
结束日期</td>
<td width="35%">
<input type="text" id="TO_TIME" name="TO_TIME" class="date required"  onpropertychange="getPOtApplyType();ajaxAdd_add_ot_apply_one_three();"format="yyyyMMdd" readonly="true" /><a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"></spring:message><!-- 选择 --></a></td>
</tr>
<tr   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
DEPTID	</td>
	<td width="25%" align="center">
部门	</td>
<td width="25%" align="center">
部门</td>
<td width="35%">
<ait:deptList name="DEPTID" cpnyId="${CPNY_ID}" id="DEPTID" limit="hr"  ></ait:deptList> <ait:deptTreeIcon	name="DEPTID" cpnyId="${CPNY_ID}" id="DEPTID" limit="hr" ></ait:deptTreeIcon></td>
</tr>
<tr   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
JOB_TP	</td>
	<td width="25%" align="center">
人员类型组	</td>
<td width="25%" align="center">
人员类型组</td>
<td width="35%">
<ait:SelectEmpTypeCode name="JOB_TP" id="JOB_TP" limit="ar" type="group"></ait:SelectEmpTypeCode></td>
</tr>
<%@include file="newfooter.jsp" %>

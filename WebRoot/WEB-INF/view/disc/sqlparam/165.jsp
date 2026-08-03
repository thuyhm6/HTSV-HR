<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
DEPT_NO	</td>
	<td width="25%" align="center">
部门	</td>
<td width="25%" align="center">
部门</td>
<td width="35%">
<ait:deptList name="DEPT_NO" cpnyId="${CPNY_ID}"  id="DEPT_NO"  limit="ar" selected="D11AR000" ></ait:deptList> 
				
				<ait:deptTreeIcon	name="DEPT_NO" cpnyId="${CPNY_ID}" id="DEPT_NO"   limit="ar" selected="D11AR000" ></ait:deptTreeIcon></td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
FROM_DATE	</td>
	<td width="25%" align="center">
开始日期	</td>
<td width="25%" align="center">
开始日期</td>
<td width="35%">
<input type="text" name="FROM_DATE" class="Wdate required"
              readonly="true" onClick="WdatePicker({dateFmt:'yyyyMMdd'})" /></td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
TO_DATE	</td>
	<td width="25%" align="center">
结束日期	</td>
<td width="25%" align="center">
结束日期</td>
<td width="35%">
  <input type="text" name="TO_DATE" class="Wdate required"
              readonly="true" onClick="WdatePicker({dateFmt:'yyyyMMdd'})" /></td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
EMP_TYPE_CODE1	</td>
	<td width="25%" align="center">
员工类型	</td>
<td width="25%" align="center">
 员工类型</td>
<td width="35%">
<input type="checkbox" name="EMP_TYPE_CODE1"     value="10418"/>驻在员</td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
EMP_TYPE_CODE3	</td>
	<td width="25%" align="center">
 	</td>
<td width="25%" align="center">
 </td>
<td width="35%">
<input type="checkbox" name="EMP_TYPE_CODE2"     value="211794"/>技能职</td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
EMP_TYPE_CODE2	</td>
	<td width="25%" align="center">
 	</td>
<td width="25%" align="center">
 </td>
<td width="35%">
<input type="checkbox" name="EMP_TYPE_CODE3"     value="10420"/>正式</td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
EMP_TYPE_CODE4	</td>
	<td width="25%" align="center">
 	</td>
<td width="25%" align="center">
 </td>
<td width="35%">
<input type="checkbox" name="EMP_TYPE_CODE4"     value="10415"/>Vistor</td>
</tr>
<%@include file="newfooter.jsp" %>

<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
DAY	</td>
	<td width="25%" align="center">
日期在职人员</td>
<td width="25%" align="center">
日期在职人员</td>
<td width="35%">
<input type="text" id="DAY" name="DAY" class="Wdate" 
					 onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})" value="${DAY}"/></td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
EMP_OFFICE	</td>
	<td width="25%" align="center">
在离职区分	</td>
<td width="25%" align="center">
在离职区分</td>
<td width="35%">
<ait:selectCodeMulti id="EMP_OFFICE"  name="EMP_OFFICE1" parentNo="15118"></ait:selectCodeMulti>    </td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
EMPLOYEE_OWNED	</td>
	<td width="25%" align="center">
员工所属	</td>
<td width="25%" align="center">
员工所属</td>
<td width="35%">
<ait:selectCodeMulti id="EMPLOYEE_OWNED"  name="EMPLOYEE_OWNED1" parentNo="14015585"></ait:selectCodeMulti>  </td>
</tr>
<%@include file="newfooter.jsp" %>

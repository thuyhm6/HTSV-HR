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
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
REPORT_TYPE	</td>
	<td width="25%" align="center">
报表类型	</td>
<td width="25%" align="center">
报表类型</td>
<td width="35%">
<ait:SelectSyCodeByCpnyID id="REPORT_TYPE" name="REPORT_TYPE" parentNo="14015465" cnpyID="${CPNY_ID}" limit="all"></ait:SelectSyCodeByCpnyID></td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
PA_MONTH	</td>
	<td width="25%" align="center">
工资月	</td>
<td width="25%" align="center">
工资月</td>
<td width="35%">
<input type="text" name="PA_MONTH" id="PA_MONTH"   value=""/></td>
</tr>
<%@include file="newfooter.jsp" %>

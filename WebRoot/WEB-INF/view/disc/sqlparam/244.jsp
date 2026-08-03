<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
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
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
S_END_CONTRACT_DATE	</td>
	<td width="25%" align="center">
查询开始日期	</td>
<td width="25%" align="center">
查询开始日期</td>
<td width="35%">
<input type="text" id="S_END_CONTRACT_DATE" name="S_END_CONTRACT_DATE" class="date required"  format="yyyy-MM-dd" readonly="true" /><a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"></spring:message><!-- 选择 --></a></td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
E_END_CONTRACT_DATE	</td>
	<td width="25%" align="center">
查询结束日期	</td>
<td width="25%" align="center">
查询结束日期</td>
<td width="35%">
<input type="text" id="E_END_CONTRACT_DATE" name="E_END_CONTRACT_DATE" class="date required"  format="yyyy-MM-dd" readonly="true" /><a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"></spring:message><!-- 选择 --></a></td>
</tr>
<%@include file="newfooter.jsp" %>

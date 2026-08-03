<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
START_DATE	</td>
	<td width="25%" align="center">
开始日期	</td>
<td width="25%" align="center">
开始日期</td>
<td width="35%">
<input type="text" id="START_DATE" name="START_DATE"  class="Wdate required"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="true" /><a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"></spring:message><!-- 选择 --></a>
</td>
</tr>
<tr    height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
END_DATE	</td>
	<td width="25%" align="center">
结束日期	</td>
<td width="25%" align="center">
结束日期</td>
<td width="35%">
<input type="text" id="END_DATE" name="END_DATE" class="Wdate required" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="true" /><a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"></spring:message><!-- 选择 --></a></td>
</tr>
<%@include file="newfooter.jsp" %>

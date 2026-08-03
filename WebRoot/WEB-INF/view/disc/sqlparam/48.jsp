<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@include file="newheader.jsp" %>
<tr   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
EMPID	</td>
	<td width="25%" align="center">
社编	</td>
<td width="25%" align="center">
社编</td>
<td width="35%">
<input type="text" name="EMPID" id="EMPID"   value=""/></td>
</tr>
<tr   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
STRT_DATE	</td>
	<td width="25%" align="center">
开始日期	</td>
<td width="25%" align="center">
开始日期</td>
<td width="35%">
<input type="text" id="STRT_DATE" name="STRT_DATE" class="date required"  onpropertychange="getPOtApplyType();ajaxAdd_add_ot_apply_one_three();"format="yyyy-MM-dd" readonly="true" /><a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"></spring:message><!-- 选择 --></a></td>
</tr>
<tr   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
END_DATE	</td>
	<td width="25%" align="center">
结束日期	</td>
<td width="25%" align="center">
结束日期</td>
<td width="35%">
<input type="text" id="END_DATE" name="END_DATE" class="date required"  onpropertychange="getPOtApplyType();ajaxAdd_add_ot_apply_one_three();"format="yyyy-MM-dd" readonly="true" /><a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"></spring:message><!-- 选择 --></a></td>
</tr>
<tr   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
PAY_AREA_CD	</td>
	<td width="25%" align="center">
大区	</td>
<td width="25%" align="center">
大区</td>
<td width="35%">
<ait:deptTreeMulti id="PAY_AREA_CD" name="PAY_AREA_NM" limit="ar" level="2" ></ait:deptTreeMulti></td>
</tr>
<tr   height= "50" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF">
	<td width="15%" align="center">
CPNY_ID	</td>
	<td width="25%" align="center">
CPNY_ID	</td>
<td width="25%" align="center">
法人代码</td>
<td width="35%">
<input type='text' name='CPNY_ID' id='CPNY_ID'  readonly value='${CPNY_ID}'/></td>
</tr>
<%@include file="newfooter.jsp" %>

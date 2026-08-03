<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
    function filltext(values) {
		if (parent){
			if (parent.paDayFormularForm) {
				if($(":radio:checked").val() == 'CONDITION'){
					var target = parent.paDayFormularForm.CONDITION;
				}else{
					var target = parent.paDayFormularForm.FORMULAR;
				}
				target.value+=values+' ';
			}
		}
	}
</script> 

<div class="pageContent">
	<div class="pageFormContent" layoutH="60">   
   
<table width="100%" height="300" border="1" cellpadding="0" cellspacing="0" bordercolorlight="#E7E7E7" 
       bordercolordark="#FFFFFF" style="padding: 2px 2px 2px 2px;">
  <tr align="center">
    <td height="25"><spring:message code="pa.salary.title.salaryBasicItemList"/><!--工资基础项目列表--> </td>
    <td><spring:message code="pa.salary.title.salaryItemList"/><!--工资项目列表--></td>
    <td><spring:message code="pa.salary.title.salaryParamList"/><!--工资参数列表--></td>
    <td><spring:message code="pa.salary.title.insuranceParamList"/><!--保险参数列表--></td>
    <td><spring:message code="pa.salary.title.bonusParamList"/><!--奖金参数列表--></td>
    <td><spring:message code="pa.salary.title.timeCardItem"/><!--考勤项目--></td>
    <td><spring:message code="pa.salary.title.rivetParam"/><!--固定参数--></td>
    <td><spring:message code="pa.insurance.title.caculateTools"/><!--计算工具--></td>
  </tr>
  
  <tr align="center" >  
  	<td align="center"  style="padding:5 5 5 5 " valign="top">
    <select name="select" id="paBasicItem" size="15" style="width:120px " onClick="filltext(this.value)">
	    <c:forEach items="${paBasicInputItemList}" var="oneResult" varStatus="i">
	    	<option value="${oneResult.ITEM_ID}">
	    		${oneResult.ALIAS_NAME} 
	    	</option>
	    </c:forEach>
    </select>
	</td>
  
    <td align="center"  style="padding:5 5 5 5 " valign="top">
    <select name="select" id="paComputeItem" size="15" style="width:120px " onClick="filltext(this.value)">
	    <c:forEach items="${paComputeItemList}" var="oneResult" varStatus="i">
	    	<option value="${oneResult.ITEM_ID}">
	    		${oneResult.ALIAS_NAME} 
	    	</option>
	    </c:forEach>
    </select>
	</td>
	
    <td  align="center"  style="padding:5 5 5 5 " valign="top">
	 	<select name="select" id="paInputItem" size="15" style="width:120px " onClick="filltext(this.value)">
	    <c:forEach items="${paInputItemList}" var="oneResult" varStatus="i">
	    	<option value="${oneResult.PARAM_ITEM_ID}">
	    		 ${oneResult.ALIAS_NAME} 
	    	</option>
	    </c:forEach>
    </select>
	</td>
	
	<td  align="center"  style="padding:5 5 5 5 " valign="top">
	 	<select name="select" id="insuranceInputItem" size="15" style="width:120px " onClick="filltext(this.value)">
	    <c:forEach items="${insuranceComputeItemList}" var="oneResult" varStatus="i">
	    	<option value="${oneResult.ITEM_ID}">
	    		${oneResult.ALIAS_NAME} 
	    	</option>
	    </c:forEach>
    </select>
	</td>
	
	<td  align="center"  style="padding:5 5 5 5 " valign="top">
	 	<select name="select" id="bonusInputItem" size="15" style="width:120px " onClick="filltext(this.value)">
	    <c:forEach items="${bonusComputeItemList}" var="oneResult" varStatus="i">
	    	<option value="${oneResult.ITEM_ID}">
	    		${oneResult.ALIAS_NAME} 
	    	</option>
	    </c:forEach>
    </select>
	</td>
	<td  align="center"  style="padding:5 5 5 5 " valign="top">
	 	<select name="select" id="arItem" size="15" style="width:120px " onClick="filltext(this.value)">
	    <c:forEach items="${arItemDayList}" var="paList">
				<option value="${paList.ITEM_ID}">
		    	 ${paList.ITEM_NAME} 
		    	</option>
		</c:forEach>
    </select>
	</td>
	
	<td  align="center"  style="padding:5 5 5 5 " valign="top">
	 	<select name="select" id="fixedParameter" size="15" style="width:120px " onClick="filltext(this.value)">
				<c:forEach items="${hrItemList}" var="paList">
					<option value="${paList.DISTINCT_FIELD}">
			    	 ${paList.FIELD_NAME} 
			    	</option>
				</c:forEach>
		</select>
	</td>
	
    <td style="padding:5 5 5 5 "><table width="100" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><input type="radio" name="radiobutton" value="CONDITION" checked>
        <spring:message code="pa.insurance.title.condition"/><!--条件--></td>
        <td><input type="radio" name="radiobutton" value="FORMULAR" >
        <spring:message code="pa.insurance.title.formula"/><!--公式--></td>
      </tr>
    </table>
      <table width="200" border="0" cellspacing="0" cellpadding="0">
        <tr align="center">
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value="1"></td>
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value="2"></td>
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value="3"></td>
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value="+"></td>
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value="'"></td>
        </tr>
        <tr align="center">
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value="4"></td>
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value="5"></td>
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value="6"></td>
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value="-"></td>
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value="in"></td>
        </tr>
        <tr align="center">
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value="7"></td>
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value="8"></td>
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value="9"></td>
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value="*"></td>
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value="and"></td>
        </tr>
        <tr align="center">
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value="("></td>
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value="0"></td>
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value=")"></td>
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value="%"></td>
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value="or"></td>
        </tr>
        <tr align="center">
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value="="></td>
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value="<"></td>
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value="between"></td>
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value=">"></td>
          <td width="40" height="36"><input name="button" type="button" class="l-button" onClick="filltext(this.value)" value="<>"></td>
        </tr>
        <tr align="center">
          <td height="36" colspan="5"><input name="button" type="button" onClick="filltext(' ')" value="Space" style="width:120px "></td>
        </tr>
      </table></td>
  </tr>
</table>
</div>
</div>
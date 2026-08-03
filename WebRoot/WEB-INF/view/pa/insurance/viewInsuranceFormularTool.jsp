<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%> 
<script type="text/javascript">
	function filltext(values){		
		if (parent){
			if (parent.isformularForm){
				if($(":radio:checked").val() == 'condition'){
					var target = parent.isformularForm.CONDITION;
				}else{
					var target = parent.isformularForm.FORMULAR;
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
				<td  height="25"><spring:message code="pa.insurance.title.caculateItemList"/><!--计算项目列表--></td>
				<td><spring:message code="pa.insurance.title.inputItemList"/><!--输入项目列表--></td>
				<td><spring:message code="pa.insurance.title.regularPamatersList"/><!--固定参数列表--></td>
				<td><spring:message code="pa.insurance.title.caculateTools"/><!--计算工具--></td>
			</tr>
			<tr align="center" >
				<td align="center"  style="padding:5 5 5 5 " valign="top">
					<select name="select" id="param_no" size="17" style="width:170px " onClick="filltext(' '+this.value)">
						<c:forEach items="${insuranceComputeItemList}" var="ci">
							<option value="${ci.ITEM_ID}">${ci.ALIAS_NAME}</option>
						</c:forEach>
					</select>
				</td>
				<td  align="center"  style="padding:5 5 5 5 " valign="top">
					<select name="select" id="param_no" size="17" style="width:170px " onClick="filltext(''+this.value)">
						<c:forEach items="${insuranceInputItemList}" var="cip">
							<option value="${cip.PARAM_ID}">${cip.ALIAS_NAME}</option>
						</c:forEach>
					</select>
				</td>
				<td  align="center"  style="padding:5 5 5 5 " valign="top">
					<select name="select" id="param_no" size="17" style="width:170px " onClick="filltext(' '+this.value)">
						<c:forEach items="${hrItemList}" var="sp">
							<option value="${sp.DISTINCT_FIELD}">${sp.FIELD_NAME}</option>
						</c:forEach>
					</select>
				</td>
				<td style="padding:5 5 5 5 ">
					<table width="100" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td><input type="radio" name="radiobutton" value="condition" checked>
							<spring:message code="pa.insurance.title.condition"/><!--条件--></td>
							<td><input type="radio" name="radiobutton" value="formular" >
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
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>
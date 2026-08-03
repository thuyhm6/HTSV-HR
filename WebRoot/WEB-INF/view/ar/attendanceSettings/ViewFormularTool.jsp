<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%> 
<script type="text/javascript">
	function filltext_ar_formular1(name,values){
		
		if(values != ''){
			if (parent){
				
				values = name + values;
				
				if (parent.formularForm){
					if($(":radio:checked").val() == 'condition'){
						var target = parent.formularForm.CONDITION;
					}else{
						var target = parent.formularForm.FORMULAR;
					}
					target.value+=values+' ';
				}
			}
		}	
	}

	function filltext(values){
		if (parent){
			if (parent.formularForm){
				if($(":radio:checked").val() == 'condition'){
					var target = parent.formularForm.CONDITION;
				}else{
					var target = parent.formularForm.FORMULAR;
				}
				target.value+=values+' ';
			}
		}
	}
</script> 
<div class="pageContent">
	<div class="pageFormContent" layoutH="60">
		<table width="100%" height="300" border="1" cellpadding="0" cellspacing="0" bordercolorlight="#E7E7E7" bordercolordark="#FFFFFF" style="padding: 2px 2px 2px 2px;">
			<tr align="center">
				<td  height="25"><!-- 考勤项目列表 --><spring:message code="ar.viewSummaryFormula.title.kaoqingxiangmuliebiao"/></td>
				<td> <!-- 考勤汇总列表--><spring:message code="ar.viewSummaryFormula.title.kaoqinghuizongliebiao"/></td>
				<td><!-- 人员基本信息--><spring:message code="ar.viewSummaryFormula.title.renyuanjibenxinxi"/></td>
				<td><!-- 计算工具--><spring:message code="ar.viewSummaryFormula.title.jisuangongju"/></td>
			</tr>
			<tr align="center" >
				<td align="center"  style="padding:5 5 5 5 " valign="top">
					<select name="select" id="param_no" size="17" style="width:170px " onClick="filltext_ar_formular1(' ATT_ITEM.',this.value)">
						<c:forEach items="${arItems}" var="ai">
							<option value="${ai.ITEM_ID}">${ai.ITEM_NAME}</option>
						</c:forEach>
					</select>
				</td>
				<td  align="center"  style="padding:5 5 5 5 " valign="top">
					<select name="select" id="param_no" size="17" style="width:170px " onClick="filltext_ar_formular1(' STA_ITEM.',this.value)">
						<c:forEach items="${summaryItems}" var="si">
							<option value="${si.STA_ITEM_ID}">${si.ITEM_NAME}</option>
						</c:forEach>
					</select>
				</td>
				<td  align="center"  style="padding:5 5 5 5 " valign="top">
					<select name="select" id="param_no" size="17" style="width:170px " onClick="filltext_ar_formular1(' STA_ITEM.',this.value)">
						<c:forEach items="${PersonBasicInfoList}" var="pi">
							<option value="${pi.DISTINCT_FIELD}">${pi.FIELD_NAME}</option>
						</c:forEach>
						    <option value="PAY_DATE">支付日期</option>
					</select>
				</td>
				<td style="padding:5 5 5 5 ">
					<table width="100" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td><input type="radio" name="radiobutton" value="condition" checked><!-- 条件 --><spring:message code="ar.viewSummaryFormula.title.tiaojian"/></td>
							<td><input type="radio" name="radiobutton" value="formular" ><!-- 公式 --><spring:message code="ar.viewSummaryFormula.title.gongshi"/></td>
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
<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script language="JavaScript" type="text/JavaScript">
    function checkValue(){
        var deduct_ratio = document.form1.DEDUCT_RATIO.value;
        if (deduct_ratio < 0 || deduct_ratio > 1) {
            alert("(提成比例 输入错误!应为0.00 ~ 1.00的值.)");
            document.form1.DEDUCT_RATIO.focus();
            return false;
        }
        return true;
	 }
</script>
		
<div class="pageContent">
	<form name="form1" method="post" action="/promoter/updateCustInfo" class="pageForm required-validate" 
	onsubmit="return validateCallback(this,dialogAjaxDone);">
		<input name="COM_CD" type="hidden" value="${CustInfo.COM_CD}" />
		<input name="DIV_CD" type="hidden" value="${CustInfo.DIV_CD}" />
		<input name="DEPT_CD" type="hidden" value="${CustInfo.DEPT_CD}" />
		
		<div class="pageFormContent nowrap" layoutH="60">
			<dl>
				<dt>年份</dt>
				<dd style="width:60px"><input name="YYYY" value="${CustInfo.YYYY}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>大区</dt>
				<dd style="width:60px"><input name="DIV_CD_NM" value="${CustInfo.DIV_CD_NM}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>门店地区</dt>
				<dd style="width:60px">
				    <ait:ComboSyCodeDescByCpnyID id="CITY_LEVEL" name="CITY_LEVEL" parentNo="211057" selected="${CustInfo.CITY_LEVEL}" cnpyID="${defaultCpny}" limit="all"/>
				</dd>
			</dl>
			<dl>
				<dt>门店代码</dt>
				<dd style="width:60px"><input name="SHOP_CD" value="${CustInfo.SHOP_CD}" readonly="true" /></dd>
			</dl>
			<dl>
				<dt>门店名称</dt>
				<dd style="width:60px"><input name="SHOP_NAME" value="${CustInfo.SHOP_NAME}" readonly="true" /></dd>
			</dl>
			<dl>
				<dt>门店等级</dt>
				<dd style="width:60px">
				    <ait:ComboSyCodeDescByCpnyID id="SHOP_LEVEL" name="SHOP_LEVEL" parentNo="210608" selected="${CustInfo.SHOP_LEVEL}" cnpyID="${defaultCpny}" limit="all"/>
				</dd>
			</dl>
			<dl>
				<dt>状态</dt>
				<dd style="width:60px">
					<select name="USE_YN">
						<option value="Y" <c:if test="${CustInfo.USE_YN eq 'Y'}">selected</c:if>>
						<spring:message code="sys.affirm.title.yes"/><!--是--></option>
						<option value="N" <c:if test="${CustInfo.USE_YN eq 'N'}">selected</c:if>>
						<spring:message code="sys.affirm.title.no"/><!--否--></option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>门店提成率</dt>
				<dd style="width:220px">
				   <table>
				     <tr>
				       <td><input name="DEDUCT_RATIO" style="width:30px" value="${CustInfo.DEDUCT_RATIO}" 
				       onblur='checkValue()' onkeyup="if(isNaN(value))execCommand('undo')" 
				       onafterpaste="if(isNaN(value))execCommand('undo')"/></td><td>(0.00 ~ 1.00)</td>
				     </tr>
				   </table>
				</dd>
			</dl>
			<dl>
				<dt>门店提成上限</dt>
				<dd style="width:220px">
				   <input name="INC_UP_LIMIT" size="10" maxlength="10" class="required text"
				   	   value="${CustInfo.INC_UP_LIMIT}" 
				       onkeyup="if(isNaN(value))execCommand('undo')" 
				       onafterpaste="if(isNaN(value))execCommand('undo')"/>
				(0~999999.00)
				</dd>
			</dl>
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="submit">
				<spring:message code="button.sys.affirm.save"/><!--保存--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>
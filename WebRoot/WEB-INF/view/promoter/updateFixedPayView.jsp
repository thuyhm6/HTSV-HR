<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
function checkChgFix(){
var deduct_ratio = document.chgFixedPay.BASIC_INCTV_AMT.value;
if (deduct_ratio < 0) {
    document.chgFixedPay.BASIC_INCTV_AMT.focus();
    alert("基本工资不能小于0.");
    return false;
}
var deduct_ratio = document.chgFixedPay.ALOWN_AMT.value;
if (deduct_ratio < 0) {
    document.chgFixedPay.ALOWN_AMT.focus();
    alert("其他补助不能小于0.");
    return false;
}

return true;
}
</script>

<div class="pageContent">
	<form id="chgFixedPay" name="chgFixedPay" method="post" action="/promoter/updateFixedPay" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<input name="PAY_AREA_CD" type="hidden" value="${fixedPayInfo.PAY_AREA_CD}" />
		<input name="BRANCH_CD" type="hidden" value="${fixedPayInfo.BRANCH_CD}" />
		<input name="SHOP_AREA_ID" type="hidden" value="${fixedPayInfo.SHOP_AREA_ID}" />
		<input name="PROD_TP" type="hidden" value="${fixedPayInfo.PROD_TP}" />
		<input name="SHOP_LEVEL" type="hidden" value="${fixedPayInfo.SHOP_LEVEL}" />
		
		<div class="pageFormContent nowrap" layoutH="60">
			<dl>
				<dt>大区</dt>
				<dd style="width:100px"><input name="PAY_AREA_NM" value="${fixedPayInfo.PAY_AREA_NM}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>支社</dt>
				<dd style="width:100px"><input name="BRANCH_NM" value="${fixedPayInfo.BRANCH_NM}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>门店地区</dt>
				<dd style="width:100px"><input name="SHOP_AREA_NM" value="${fixedPayInfo.SHOP_AREA_NM}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>产品类型</dt>
				<dd style="width:100px"><input name="PROD_TP_NM" value="${fixedPayInfo.PROD_TP_NM}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>门店等级</dt>
				<dd style="width:100px"><input name="SHOP_LEVEL_NM" value="${fixedPayInfo.SHOP_LEVEL_NM}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>基本工资</dt>
				<dd style="width:100px"><input name="BASIC_INCTV_AMT" value="${fixedPayInfo.BASIC_INCTV_AMT}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></dd>
			</dl>
			<dl>
				<dt>其他补助</dt>
				<dd style="width:100px"><input name="ALOWN_AMT" value="${fixedPayInfo.ALOWN_AMT}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></dd>
			</dl><!-- 
			<dl>
				<dt>最低补助</dt>
				<dd style="width:100px"><input name="LOWST_INCTV_AMT" value="${fixedPayInfo.LOWST_INCTV_AMT}" /></dd>
			</dl>
			<dl>
				<dt>DVD 补助</dt>
				<dd style="width:100px"><input name="DVD_ALOWN_AMT" value="${fixedPayInfo.DVD_ALOWN_AMT}" /></dd>
			</dl>
			<dl>
				<dt>MIC 补助</dt>
				<dd style="width:100px"><input name="MIC_ALOWN_AMT" value="${fixedPayInfo.MIC_ALOWN_AMT}" /></dd>
			</dl>
			<dl>
				<dt>RAC 补助</dt>
				<dd style="width:100px"><input name="RAC_ALOWN_AMT" value="${fixedPayInfo.RAC_ALOWN_AMT}" /></dd>
			</dl>
			<dl>
				<dt>VACL 补助</dt>
				<dd style="width:100px"><input name="VACL_ALOWN_AMT" value="${fixedPayInfo.VACL_ALOWN_AMT}" /></dd>
			</dl>
			<dl>
				<dt>扩展补助</dt>
				<dd style="width:100px"><input name="BASIC_INCTV_AMT" value="${fixedPayInfo.SML_PROD_ALOWN_AMT}" /></dd>
			</dl> -->
			<dl>
				<dt>更新人</dt>
				<dd style="width:100px"><input name="UPDT_USER" value="${fixedPayInfo.UPDT_USER}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>更新时间</dt>
				<dd style="width:100px"><input name="UPDT_DTIME" value="${fixedPayInfo.UPDT_DTIME}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>状态</dt>
				<dd style="width:100px">
					<select name="USE_YN">
						<option value="Y" <c:if test="${fixedPayInfo.USE_YN eq 'Y'}">selected</c:if>>
						<spring:message code="sys.affirm.title.yes"/><!--是--></option>
						<option value="N" <c:if test="${fixedPayInfo.USE_YN eq 'N'}">selected</c:if>>
						<spring:message code="sys.affirm.title.no"/><!--否--></option>
					</select>
				</dd>
			</dl>
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="submit" onClick="return checkChgFix();">
				<spring:message code="button.sys.affirm.save"/><!--保存--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>
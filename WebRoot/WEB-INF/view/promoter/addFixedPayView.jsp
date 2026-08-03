<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
$(document).ready(function() {
	var payAreaCd=$('#PAY_AREA_CD').val();
	var branch0=$('#hAddBRANCH').val();
	changeAddPayArea(payAreaCd,branch0);
	
	$('#PAY_AREA_CD').live('change',function(){
		var st=$('#PAY_AREA_CD').val();
		changeAddPayArea(st,branch0);
	});
	
});

function changeAddPayArea(payAreaCd, branch){
	$.ajax({
		cache: false,
		url : '${base}/promoter/getListBySelect?type=BRANCH&parentNo='+payAreaCd+'&selected='+branch+'&name=BRANCH_CD',
		type : "get",
		dataType : "html",
		success : function(data) {
			$("#BRANCH_CD").html(data);
		}
	});
}
function checkAddFix(){
	var PAY_AREA_CD=document.addFixedPay.PAY_AREA_CD.value;
	var BRANCH_CD=document.addFixedPay.BRANCH_CD.value;

    if(PAY_AREA_CD == null||PAY_AREA_CD ==""){
	    alert("请选择大区！");
	    return false;
	    }
	if(BRANCH_CD == null||BRANCH_CD ==""){
	    alert("请选择支社！");
	    return false;
    }
    var deduct_ratio = document.addFixedPay.BASIC_INCTV_AMT.value;
    if (deduct_ratio < 0) {
        document.addFixedPay.BASIC_INCTV_AMT.focus();
        alert("基本工资不能小于0.");
        return false;
    }
    var deduct_ratio = document.addFixedPay.ALOWN_AMT.value;
    if (deduct_ratio < 0) {
        document.addFixedPay.ALOWN_AMT.focus();
        alert("其他补助不能小于0.");
        return false;
    }
	
	return true;
}
</script>

<div class="pageContent">
	<form id="addFixedPay" name="addFixedPay" method="post" action="/promoter/addFixedPay" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<input id="hAddBRANCH" name="hAddBRANCH" type="hidden" value="${BRANCH}" />
		<div class="pageFormContent nowrap" layoutH="60">
			<dl>
				<dt>大区</dt>
				<dd style="width:100px"><ait:SelectState id="PAY_AREA_CD" name="PAY_AREA_CD" type="PAYAREA" parentNo="" selected="${PAY_AREA_CD}" limit="all"/></dd>
			</dl>
			<dl>
				<dt>支社</dt>
				<dd style="width:100px"><span id="BRANCH_CD" name="BRANCH_CD"></select></span></dd>
			</dl>
			<dl>
				<dt>门店地区</dt>
				<dd style="width:100px">
				   <ait:ComboSyCodeDescByCpnyID id="SHOP_AREA_ID" name="SHOP_AREA_ID" parentNo="211057" cnpyID="${defaultCpny}" limit="all"/>
				</dd>
			</dl>
			<dl>
				<dt>产品类型</dt>
				<dd style="width:100px"><ait:ComboSyCodeDescByCpnyID id="PROD_TP" name="PROD_TP" parentNo="211424" cnpyID="${defaultCpny}" limit="all"/></dd>
			</dl>
			<dl>
				<dt>门店等级</dt>
				<dd style="width:100px"><ait:ComboSyCodeDescByCpnyID id="SHOP_LEVEL" name="SHOP_LEVEL" parentNo="210608" cnpyID="${defaultCpny}" limit="all"/></dd>
			</dl>
			<dl>
				<dt>基本工资</dt>
				<dd style="width:100px"><input name="BASIC_INCTV_AMT" value="" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></dd>
			</dl>
			<dl>
				<dt>其他补助</dt>
				<dd style="width:100px"><input name="ALOWN_AMT" value="" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></dd>
			</dl><!-- 
			<dl>
				<dt>最低补助</dt>
				<dd style="width:100px"><input name="LOWST_INCTV_AMT" value="" /></dd>
			</dl>
			<dl>
				<dt>DVD 补助</dt>
				<dd style="width:100px"><input name="DVD_ALOWN_AMT" value="" /></dd>
			</dl>
			<dl>
				<dt>MIC 补助</dt>
				<dd style="width:100px"><input name="MIC_ALOWN_AMT" value="" /></dd>
			</dl>
			<dl>
				<dt>RAC 补助</dt>
				<dd style="width:100px"><input name="RAC_ALOWN_AMT" value="" /></dd>
			</dl>
			<dl>
				<dt>VACL 补助</dt>
				<dd style="width:100px"><input name="VACL_ALOWN_AMT" value="" /></dd>
			</dl>
			<dl>
				<dt>扩展补助</dt>
				<dd style="width:100px"><input name="SML_PROD_ALOWN_AMT" value="" /></dd>
			</dl> -->
			<dl>
				<dt>状态</dt>
				<dd style="width:100px">
					<select name="USE_YN">
						<option value="Y" selected>
						<spring:message code="sys.affirm.title.yes"/><!--是--></option>
						<option value="N">
						<spring:message code="sys.affirm.title.no"/><!--否--></option>
					</select>
				</dd>
			</dl>
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="submit" onClick="return checkAddFix();">
				<spring:message code="button.sys.affirm.save"/><!--保存--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>
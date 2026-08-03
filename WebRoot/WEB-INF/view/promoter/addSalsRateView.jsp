<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
function checkaddSalsRate(){
	var PAY_AREA_CD=document.addSalsRate.PAY_AREA_CD.value;
	var PROD_TP=document.addSalsRate.PROD_TP.value;

    if(PAY_AREA_CD == null||PAY_AREA_CD ==""){
	    alert("请选择大区！");
	    return false;
	}
	
	return true;
}
</script>

<div class="pageContent">
	<form id="addSalsRate" name="addSalsRate" method="post" action="/promoter/addSalsRate" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		
		<div class="pageFormContent nowrap" layoutH="60">
			<dl>
				<dt>大区</dt>
				<dd style="width:60px"><ait:SelectState id="PAY_AREA_CD" name="PAY_AREA_CD" type="PAYAREA" parentNo="" limit="all"/></dd>
			</dl>
			<dl>
				<dt>产品类型</dt>
				<dd style="width:60px"><ait:ComboSyCodeDescByCpnyID id="PROD_TP" name="PROD_TP" parentNo="211424" cnpyID="${defaultCpny}" limit="all"/></dd>
			</dl>
			<dl>
				<dt>起始达成率</dt>
				<dd style="width:60px"><input name="FRUT_STRT_RNG" value="" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></dd>
			</dl>
			<dl>
				<dt>截止达成率</dt>
				<dd style="width:60px"><input name="FRUT_END_RNG" value="" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></dd>
			</dl>
			<dl>
				<dt>参数类型</dt>
				<dd style="width:60px">
				    <select name="PARAM_TP" id="PARAM_TP" class="table_padding" >                     
				        <option value="1" selected>1</option>
				        <option value="-1">-1</option>  
				        <option value="0" selected>0</option>
				    </select>
				</dd>
			</dl>
			<dl>
				<dt>调整系数</dt>
				<dd style="width:60px"><input name="PARA_RAT" value="" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></dd>
			</dl>
			<dl>
				<dt>参数</dt>
				<dd style="width:60px"><input name="PARAM_VAL" value="" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></dd>
			</dl>
			<dl>
				<dt>状态</dt>
				<dd>
					<select name="USE_YN">
						<option value="Y" <c:if test="${itemInfo.USE_YN eq 'Y'}">selected</c:if>>
						<spring:message code="sys.affirm.title.yes"/><!--是--></option>
						<option value="N" <c:if test="${itemInfo.USE_YN eq 'N'}">selected</c:if>>
						<spring:message code="sys.affirm.title.no"/><!--否--></option>
					</select>
				</dd>
			</dl>
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="submit" onClick="return checkaddSalsRate();">
				<spring:message code="button.sys.affirm.save"/><!--保存--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>
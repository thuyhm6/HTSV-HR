<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script language="JavaScript" type="text/JavaScript">
function incAdjAddCheck(){
    var obj = document.viewOfficeIncAdd.DIFF_RAT.value;
    var checknumb = parseFloat(obj);  
    var PAY_AREA_CD = document.viewOfficeIncAdd.PAY_AREA_CD.value;
    var prod_tp = document.viewOfficeIncAdd.PROD_TP.value ;
    var prod_id = document.viewOfficeIncAdd.PROD_ID.value ;

    if(PAY_AREA_CD == null||PAY_AREA_CD ==""){
	    alert("请选择大区！");
	    return false;
	}
	if(prod_tp == null||prod_tp ==""){
	    alert("请选择产品类型！");
	    return false;
    }
	if(prod_id == null||prod_id ==""){
	    alert("请输入产品ID！");
	    return false;
    }
	
    if(isNaN(checknumb)){
        alert("please check numeric format");
        document.viewOfficeIncAdd.DIFF_RAT.value = 0;
        document.viewOfficeIncAdd.DIFF_RAT.focus();
        return;
	}else{
        var std  = document.viewOfficeIncAdd.SUBSD_INCTV_AMT.value;
        var mathtemp = ""+Math.round(std*(1+checknumb/100)*100);
        if(mathtemp.indexOf(".") != -1){
            mathtemp = mathtemp.substring(0,mathtemp.indexOf(".")+1);
	           mathtemp = parseFloat(mathtemp)/100;
        }else{
            mathtemp = parseFloat(mathtemp)/100;
        }
        document.viewOfficeIncAdd.BASE_AMT.value=mathtemp;
    }
}
</script>

<div class="pageContent">
	<form id="viewOfficeIncAdd" name="viewOfficeIncAdd" method="post" action="/promoter/addOfficeIncAdjust" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		
		<div class="pageFormContent nowrap" layoutH="60">
			<dl>
				<dt>大区</dt>
				<dd style="width:60px"><ait:SelectState id="PAY_AREA_CD" name="PAY_AREA_CD" type="PAYAREA" parentNo="" selected="${PAY_AREA_CD}" limit="all"/></dd>
			</dl>
			<dl>
				<dt>产品类型</dt>
				<dd style="width:60px"><ait:ComboSyCodeDescByCpnyID id="PROD_TP" name="PROD_TP" parentNo="211424" selected="${PROD_TP}" cnpyID="${defaultCpny}" limit="all"/></dd>
			</dl>
			<dl>
				<dt>产品ID</dt>
				<dd style="width:60px"><input name="PROD_ID" value="" class="required textInput"/></dd>
			</dl>
			<dl>
				<dt>调整比率</dt>
				<dd>
				   <table>
				     <tr>
				       <td><input name="DIFF_RAT" value="0" onblur='incAdjAddCheck()' onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></td><td>%</td>
				     </tr>
				   </table>
				</dd>
			</dl>
			<dl>
				<dt>固定提成</dt>
				<dd style="width:60px"><input name="FXD_AMT" value="0" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></dd>
			</dl>
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="submit" onClick="return incAdjAddCheck();">
				<spring:message code="button.sys.affirm.save"/><!--保存--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>
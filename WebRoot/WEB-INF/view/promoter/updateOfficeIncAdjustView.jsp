<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script language="JavaScript" type="text/JavaScript">
function dataCheck(){
    var obj = document.viewOfficeIncAdjust.DIFF_RAT.value;
    var checknumb = parseFloat(obj);  
    var payAreaCD = document.viewOfficeIncAdjust.PAY_AREA_CD.value;
    var prod = document.viewOfficeIncAdjust.PROD_TP.value ;
    var flag = document.viewOfficeIncAdjust.USE_YN.value;

    if(isNaN(checknumb)){
        alert("please check numeric format");
        document.viewOfficeIncAdjust.DIFF_RAT.value = 0;
        document.viewOfficeIncAdjust.DIFF_RAT.focus();
        return;
	}else{
        var std  = document.viewOfficeIncAdjust.SUBSD_INCTV_AMT.value;
        var mathtemp = ""+Math.round(std*(1+checknumb/100)*100);
        if(mathtemp.indexOf(".") != -1){
            mathtemp = mathtemp.substring(0,mathtemp.indexOf(".")+1);
	           mathtemp = parseFloat(mathtemp)/100;
        }else{
            mathtemp = parseFloat(mathtemp)/100;
        }
        document.viewOfficeIncAdjust.BASE_AMT.value=mathtemp;
    }
}
</script>

<div class="pageContent">
	<form name="viewOfficeIncAdjust" method="post" action="/promoter/updateOfficeIncAdjust" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<input name="PAY_AREA_CD" type="hidden" value="${itemInfo.PAY_AREA_CD}" />
		<input name="PROD_TP" type="hidden" value="${itemInfo.PROD_TP}" />
		
		<div class="pageFormContent nowrap" layoutH="60">
			<dl>
				<dt>大区</dt>
				<dd style="width:60px"><input name="PAY_AREA_NM" value="${itemInfo.PAY_AREA_NM}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>产品类型</dt>
				<dd style="width:60px"><input name="PROD_TP_NM" value="${itemInfo.PROD_TP_NM}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>产品ID</dt>
				<dd style="width:60px"><input name="PROD_ID" value="${itemInfo.PROD_ID}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>单价</dt>
				<dd style="width:60px"><input name="UNIT_PRC" value="${itemInfo.UNIT_PRC}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>标准提成</dt>
				<dd style="width:60px"><input name="SUBSD_INCTV_AMT" value="${itemInfo.SUBSD_INCTV_AMT}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>调整比率</dt>
				<dd style="width:60px">
				   <table>
				     <tr>
				       <td><input name="DIFF_RAT" value="${itemInfo.DIFF_RAT}" onblur='dataCheck()'/></td><td>%</td>
				     </tr>
				   </table>
				 </dd>
			</dl>
			<dl>
				<dt>基本提成</dt>
				<dd style="width:60px"><input name="BASE_AMT" value="${itemInfo.BASE_AMT}" readonly="true" /></dd>
			</dl>
			<dl>
				<dt>固定提成</dt>
				<dd style="width:60px"><input name="FXD_AMT" value="${itemInfo.FXD_AMT}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></dd>
			</dl>
			<dl>
				<dt>更新人</dt>
				<dd style="width:60px"><input name="UPDT_USER" value="${itemInfo.UPDT_USER}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>状态</dt>
				<dd style="width:60px"><input name="USE_YN" value="${itemInfo.USE_YN}" readonly="true"/></dd>
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
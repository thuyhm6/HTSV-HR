<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script language="JavaScript" type="text/JavaScript">
function dataCheck(){
	var HEAD_UNIT_PRC = document.viewIncBasicSetup.HEAD_UNIT_PRC.value;
	var INC_RATE      = document.viewIncBasicSetup.INC_RATE.value;	  	
	if(!isNaN(HEAD_UNIT_PRC)&&!isNaN(INC_RATE)){
			document.viewIncBasicSetup.SUBSD_INCTV_AMT.value = HEAD_UNIT_PRC*INC_RATE/100;
	}
 }
</script>

<div class="pageContent">
	<form name="viewIncBasicSetup" method="post" action="/promoter/updateIncBasicSetup" class="pageForm required-validate" onsubmit="dataCheck(); return validateCallback(this,dialogAjaxDone);">
		<input name="PROD_TP" type="hidden" value="${itemInfo.PROD_TP}" />
		
		<div class="pageFormContent nowrap" layoutH="60">
			<dl>
				<dt>产品ID</dt>
				<dd style="width:60px"><input name="PROD_ID" value="${itemInfo.PROD_ID}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>产品类型</dt>
				<dd style="width:60px"><input name="PROD_TP" value="${itemInfo.PROD_TP_NM}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>单价</dt>
				<dd style="width:60px"><input name="UNIT_PRC" value="${itemInfo.UNIT_PRC}" readonly="true" /></dd>
			</dl>
			<dl>
				<dt>总部单价</dt>
				<dd style="width:60px"><input name="HEAD_UNIT_PRC" value="${itemInfo.HEAD_UNIT_PRC}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" onblur="dataCheck()"/></dd>
			</dl>
			<dl>
				<dt>提成率</dt>
				<dd style="width:60px">
				   <table>
				     <tr>
				       <td><input name="INC_RATE" value="${itemInfo.INC_RATE}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" onblur="dataCheck()"/></td><td>%</td>
				     </tr>
				   </table>
				 </dd>
			</dl>
			<dl>
				<dt>总部提成</dt>
				<dd style="width:60px"><input name="SUBSD_INCTV_AMT" value="${itemInfo.SUBSD_INCTV_AMT}" readonly="true" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></dd>
			</dl>
			<dl>
				<dt>更新人</dt>
				<dd style="width:60px"><input name="UPDT_USER" value="${itemInfo.UPDT_USER}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>状态</dt>
				<dd style="width:60px"><input name="USE_YN" value="${itemInfo.USE_YN}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>单价同步时间</dt>
				<dd style="width:60px"><input name="PRICE_IF_DATE" value="${itemInfo.PRICE_IF_DATE}" readonly="true"/></dd>
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
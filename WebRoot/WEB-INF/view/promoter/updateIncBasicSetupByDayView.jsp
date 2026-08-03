<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script language="JavaScript" type="text/JavaScript">
function dataCheck(){
	var HEAD_UNIT_PRC = document.viewIncBasicByDay.HEAD_UNIT_PRC.value;
	var INC_RATE = document.viewIncBasicByDay.INC_RATE.value;
	if(!isNaN(HEAD_UNIT_PRC)&&!isNaN(INC_RATE)){
		document.viewIncBasicByDay.SUBSD_INCTV_AMT.value = HEAD_UNIT_PRC*INC_RATE/100;
	}
}
</script>

<div class="pageContent">
	<form name="viewIncBasicByDay" method="post" action="/promoter/updateIncBasicSetupByDay" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<input name="SEQ" type="hidden" value="${itemInfo.SEQ}" />
		<input name="PROD_TP" type="hidden" value="${itemInfo.PROD_TP}" />
		
		<div class="pageFormContent nowrap" layoutH="60">
			<dl>
				<dt>产品类型</dt>
				<dd><input name="PROD_TP_NM" value="${itemInfo.PROD_TP_NM}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>产品ID</dt>
				<dd><input name="PROD_ID" value="${itemInfo.PROD_ID}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>实贩卖月份</dt>
				<dd style="width:100px"><input name="YYYYMM" value="${itemInfo.YYYYMM}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>开始日期</dt>
				<dd>
				    <input type="text" name="PRC_BEGIN_DAY" class="date required" format="yyyyMMdd" readonly="true" value="${itemInfo.PRC_BEGIN_DAY }"/>
				    <a class="inputDateButton"><!-- 选择 --><spring:message code="ar.viewcycleparameter.content.choose"/></a>
				</dd>
			</dl>
			<dl>
				<dt>结束日期</dt>
				<dd>
				    <input type="text" name="PRC_END_DAY" class="date required" format="yyyyMMdd" readonly="true" value = "${itemInfo.PRC_END_DAY }"/>
				    <a class="inputDateButton"><spring:message code="ar.viewcycleparameter.content.choose"/></a>
				</dd>
			</dl>
			<dl>
				<dt>总部单价</dt>
				<dd><input name="HEAD_UNIT_PRC" value="${itemInfo.HEAD_UNIT_PRC}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" onblur="dataCheck()"/></dd>
			</dl>
			<dl>
				<dt>提成率</dt>
				<dd><input name="INC_RATE" value="${itemInfo.INC_RATE}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" onblur="dataCheck()"/>%</dd>
			</dl>
			<dl>
				<dt>总部提成</dt>
				<dd><input name="SUBSD_INCTV_AMT" value="${itemInfo.SUBSD_INCTV_AMT}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>更新人</dt>
				<dd><input name="UPDT_USER" value="${itemInfo.UPDT_USER}" readonly="true"/></dd>
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
				<li><div class="button"><div class="buttonContent"><button type="submit">
				<spring:message code="button.sys.affirm.save"/><!--保存--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>
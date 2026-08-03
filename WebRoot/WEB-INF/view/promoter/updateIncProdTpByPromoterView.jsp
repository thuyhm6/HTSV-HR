<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/promoter/updateIncProdTpByPromoter" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<input name="SUBSD_CD" type="hidden" value="${itemInfo.SUBSD_CD}" />
		<input name="EMPNO" type="hidden" value="${itemInfo.EMPNO}" />
		
		<div class="pageFormContent nowrap" layoutH="60">
			<dl>
				<dt>员工姓名</dt>
				<dd><input name="EMP_NM" value="${itemInfo.EMP_NM}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>大区</dt>
				<dd><ait:ComboDeptByCpnyIDTag id="PAY_AREA_CD" name="PAY_AREA_CD" parentNo="198659" cnpyID="${defaultCpny}" limit="all"/></dd>
			</dl>
			<dl>
				<dt>工作地区</dt>
				<dd>
				    <ait:SelectSyCodeByCpnyID id="PAY_AREA_NM" name="PAY_AREA_NM" parentNo="216736" selected="${itemInfo.PAY_AREA_NM}" cnpyID="${defaultCpny}" limit="all"/>
				</dd>
			</dl>
			<dl>
				<dt>产品类型</dt>
				<dd><ait:SelectSyCodeByCpnyID id="PROD_TP" name="PROD_TP" parentNo="211424" selected="${itemInfo.PROD_TP}" cnpyID="${defaultCpny}" limit="all"/></dd>
			</dl>
			<dl>
				<dt>地区区分</dt>
				<dd><ait:SelectSyCodeByCpnyID id="OFICE_AREA_ID" name="OFICE_AREA_ID" parentNo="211557" selected="${itemInfo.OFICE_AREA_ID}" cnpyID="${defaultCpny}" limit="all"/></dd>
			</dl>
			<dl>
				<dt>工作类型</dt>
				<dd><ait:SelectSyCodeByCpnyID id="PROMTR_WORK_TP" name="PROMTR_WORK_TP" parentNo="211554" selected="${itemInfo.PROMTR_WORK_TP}" cnpyID="${defaultCpny}" limit="all"/></dd>
			</dl>
			<dl>
				<dt>促销员所属</dt>
				<dd><ait:SelectSyCodeByCpnyID id="PROMTR_PAYMNT_TP" name="PROMTR_PAYMNT_TP" parentNo="211837" selected="${itemInfo.PROMTR_PAYMNT_TP}" cnpyID="${defaultCpny}" limit="all"/></dd>
			</dl>
			<dl>
				<dt>使用标记</dt>
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
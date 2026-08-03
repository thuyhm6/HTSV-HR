<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
     <form method="post" action="/pa/wagebase/addCityCoefficientInfo" class="pageForm required-validate" 
     	onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap">  
			<dl>
				<dt><spring:message code="pa.wagebase.title.workArea"/><!-- 工作地 -->:</dt>
				<dd>
					<ait:SelectSyCodeByCpnyID name="S_WORK_AREA" parentNo="4604" cnpyID="${defaultCpny}" limit="all"/>
				</dd>
			</dl>  
			<dl>
				<dt><spring:message code="pa.wagebase.title.socialArea"/><!--社保地-->:</dt>
				<dd>
					<ait:SelectSyCodeByCpnyID name="E_WORK_AREA" parentNo="4604" cnpyID="${defaultCpny}" limit="all"/>
				</dd>
			</dl>  
			<dl>
				<dt><spring:message code="pa.wagebase.title.cityCoefficient"/><!--同城系数-->:</dt>
				<dd>
					<select name="COEFFICIENT">
						<option value=""><spring:message code="pa.insurance.title.pleaseChoose"/><!--请选择:--></option>
						<option value="0">0</option>
						<option value="0.5">0.5</option>
						<option value="1">1</option>
					</select>
				</dd>
			</dl> 
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.submit"/><!-- 提交 --></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div> 
		</div>
        </form>
 </div>
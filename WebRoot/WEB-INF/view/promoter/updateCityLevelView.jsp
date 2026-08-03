<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/promoter/updateCityLevel" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		
		<div class="pageFormContent nowrap" layoutH="60">
			<dl>
				<dt>省编号</dt>
				<dd style="width:60px"><input name="STATE_CD" value="${CityLevelInfo.STATE_CD}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>省名称</dt>
				<dd style="width:60px"><input name="STATE_NM" value="${CityLevelInfo.STATE_NM}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>城市编号</dt>
				<dd style="width:60px"><input name="CITY_CD" value="${CityLevelInfo.CITY_CD}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt>城市名称</dt>
				<dd style="width:60px"><input name="CITY_NM" value="${CityLevelInfo.CITY_NM}" readonly="true" /></dd>
			</dl>
			<dl>
				<dt>地区编号</dt>
				<dd style="width:60px"><input name="REGION_CD" value="${CityLevelInfo.REGION_CD}" readonly="true" /></dd>
			</dl>
			<dl>
				<dt>地区名称</dt>
				<dd style="width:60px"><input name="REGION_NM" value="${CityLevelInfo.REGION_NM}" readonly="true" /></dd>
			</dl>
			<dl>
				<dt>城市等级</dt>
				<dd style="width:60px">
				<ait:ComboSyCodeDescByCpnyID id="CITY_LEVEL_EN" name="CITY_LEVEL_EN" parentNo="211057" selected="${CityLevelInfo.CITY_LEVEL_EN}" cnpyID="${defaultCpny}" limit="all"/>
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
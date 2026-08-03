<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/ar/attendanceSettings/addCycleInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap">
			
			<ait:SyLanguage/>
		
			<dl>
				<dt><!-- 开始日 --><spring:message code="ar.viewcycle.title.kaishiri"/></dt>
				<dd>
					<input id="START_DAY" type="text" name="START_DAY" class="required digits" min="1" max="31"/>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 结束日 --><spring:message code="ar.viewcycle.title.jieshuri"/></dt>
				<dd>
					<input id="END_DAY" type="text" name="END_DAY" class="required digits" min="1" max="31"/>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 是否活跃 --><spring:message code="ar.viewcycle.title.shifouhuoyue"/></dt>
				<dd>
					<input id="ACTIVITY" type="radio" name="ACTIVITY" value="1" checked/><!-- 是 --><spring:message code="ar.viewcycle.content.yes"/>
					<input id="ACTIVITY" type="radio" name="ACTIVITY" value="0" /><!-- 否 --><spring:message code="ar.viewcycle.content.no"/>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>
	</form>
</div>

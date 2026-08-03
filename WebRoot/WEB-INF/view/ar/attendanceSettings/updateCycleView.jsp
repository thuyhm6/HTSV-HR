<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/ar/attendanceSettings/updateCycleInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap">
			<input type="hidden" name="NO" value="${cycleInfo.STAT_NO}"/>
			
			<ait:SyLanguage languageNo="${cycleInfo.STAT_NO}"/>
			
			<dl>
				<dt><!-- 开始日 --><spring:message code="ar.viewcycle.title.kaishiri"/></dt>
				<dd>
					<input id="START_DAY" type="text" name="START_DAY" class="required digits" min="1" max="31" value="${cycleInfo.START_DAY}"/>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 结束日 --><spring:message code="ar.viewcycle.title.jieshuri"/></dt>
				<dd>
					<input id="END_DAY" type="text" name="END_DAY" class="required digits" min="1" max="31" value="${cycleInfo.END_DAY}"/>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 是否活跃 --><spring:message code="ar.viewcycle.title.shifouhuoyue"/></dt>
				<dd>
					<input id="ACTIVITY" type="radio" name="ACTIVITY" value="1" <c:if test="${cycleInfo.ACTIVITY eq 1}">checked</c:if>/><!-- 是 --><spring:message code="ar.viewcycle.content.yes"/>
					<input id="ACTIVITY" type="radio" name="ACTIVITY" value="0" <c:if test="${cycleInfo.ACTIVITY eq 0}">checked</c:if>/><!-- 否 --><spring:message code="ar.viewcycle.content.no"/>
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

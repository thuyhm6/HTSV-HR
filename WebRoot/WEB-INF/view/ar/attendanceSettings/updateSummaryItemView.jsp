<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/ar/attendanceSettings/updateSummaryItemInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input name="NO" type="hidden" value="${summaryItemInfo.ITEM_NO}" />
		<div class="pageFormContent nowrap" layoutH="56">
			<dl>
				<dt><!-- 汇总项目ID --><spring:message code="ar.viewsummaryitem.title.huizongxiangmuID"/>: </dt>
				<dd>
					<input name="STA_ITEM_ID" type="text" class="required" value="${summaryItemInfo.STA_ITEM_ID}" />
				</dd>
			</dl>
			
			<ait:SyLanguage languageNo="${summaryItemInfo.ITEM_NO}"/>
			
			<%--
			<dl>
				<dt>单位：</dt>
				<dd>
					<select name="UNIT" class="combox"> 
						<option value="DAY" <c:if test="${summaryItemInfo.UNIT eq 'DAY'}">selected</c:if>>天</option>
						<option value="HOUR" <c:if test="${summaryItemInfo.UNIT eq 'HOUR'}">selected</c:if>>小时</option>
						<option value="MINUTE" <c:if test="${summaryItemInfo.UNIT eq 'MINUTE'}">selected</c:if>>分钟</option>
						<option value="TIME" <c:if test="${summaryItemInfo.UNIT eq 'TIME'}">selected</c:if>>计数</option>
					</select>
				</dd>
			</dl>
			 --%>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>

<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<form method="post" action="/ar/attendanceSettings/updateSummaryParamItemInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" name="PARAM_NO" value="${summaryParamItemInfo.PARAM_NO}"/>
		<div class="pageFormContent nowrap" layoutH="56">
			<dl>
				<dt><!-- 汇总项目 --><spring:message code="ar.viewsummaryparameteritem.title.huizongxiangmu"/></dt>
				<dd>
					<select name="ITEM_NO" class="combox">
					<%--<c:forEach items="${itemList}" var="item">
							<option value="${item.ITEM_NO}" <c:if test="${summaryParamItemInfo.ITEM_NO eq item.ITEM_NO}">selected</c:if>> ${summaryParamItemInfo.ITEM_NO} ${item.ITEM_NAME} ${item.ITEM_NO}</option>
						</c:forEach>
					 --%>
					 <option value="${summaryParamItemInfo.ITEM_NO}"> ${summaryParamItemInfo.ITEM_NAME}</option>		
					</select>
				</dd>
			</dl>
			<dl>
				<dt><!-- 公司 --><spring:message code="ar.viewcycleparameter.title.gongsi"/></dt>
				<dd>
					<select name="CPNY_ID" disabled="disabled">
						<c:forEach items="${cpnyList}" var="cpny">
							<option value="${cpny.CPNY_ID}" <c:if test="${summaryParamItemInfo.CPNY_ID eq cpny.CPNY_ID}">selected</c:if>>${cpny.CONTENT}</option>
						</c:forEach>
					</select>	
					<input id="CPNY_ID" type="hidden" name="CPNY_ID" value="${summaryParamItemInfo.CPNY_ID}"/>
				</dd>
			</dl>
			<dl>
				<dt><!-- 单位 --><spring:message code="ar.viewitemparameter.title.unit"/></dt>
				<dd>
					<select name="UNIT" class="combox">
						<option value="DAY" <c:if test="${summaryParamItemInfo.UNIT eq 'DAY'}">selected</c:if>><!-- 天 --><spring:message code="ar.viewsummaryparameteritem.title.day"/></option>
						<option value="HOUR" <c:if test="${summaryParamItemInfo.UNIT eq 'HOUR'}">selected</c:if>><!-- 小时 --><spring:message code="ar.viewsummaryparameteritem.title.hour"/></option>
						<option value="MINUTE" <c:if test="${summaryParamItemInfo.UNIT eq 'MINUTE'}">selected</c:if>><!-- 分钟 --><spring:message code="ar.viewsummaryparameteritem.title.minite"/></option>
						<option value="TIME" <c:if test="${summaryParamItemInfo.UNIT eq 'TIME'}">selected</c:if>><!-- 计数 --><spring:message code="ar.viewsummaryparameteritem.title.count"/></option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><!-- 最小值 --><spring:message code="ar.viewitemparameter.title.zuixiaozhi"/></dt>
				<dd>
					<select name="MIN_UNIT" class="combox" >
						<option value="0.5" <c:if test="${summaryParamItemInfo.MIN_UNIT eq '0.5'}">selected</c:if>>0.5</option>
						<option value="1" <c:if test="${summaryParamItemInfo.MIN_UNIT eq '1'}">selected</c:if>>1</option>
						<option value="0.25" <c:if test="${summaryParamItemInfo.MIN_UNIT eq '0.25'}">selected</c:if>>0.25</option>
						<option value="0.05" <c:if test="${summaryParamItemInfo.MIN_UNIT eq '0.05'}">selected</c:if>>0.05</option>
						<option value="0.01" <c:if test="${summaryParamItemInfo.MIN_UNIT eq '0.01'}">selected</c:if>>0.01</option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><!-- 活跃状态 --><spring:message code="ar.viewcycle.title.huoyuezhuangtai"/></dt>
				<dd>
					<select name="ACTIVITY" class="combox">
				      <option value="1" <c:if test="${summaryParamItemInfo.ACTIVITY eq 1}">selected</c:if>><!-- 是--><spring:message code="ar.viewcycle.content.yes"/></option>
				      <option value="0" <c:if test="${summaryParamItemInfo.ACTIVITY eq 0}">selected</c:if>><!-- 否--><spring:message code="ar.viewcycle.content.no"/></option>
				    </select>
				</dd>
			</dl>
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

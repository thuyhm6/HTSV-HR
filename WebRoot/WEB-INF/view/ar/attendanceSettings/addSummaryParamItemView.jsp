<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<form method="post" action="/ar/attendanceSettings/addSummaryParamItemInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="56">
			
			<dl>
				<dt><!-- 汇总项目 --><spring:message code="ar.viewsummaryparameteritem.title.huizongxiangmu"/></dt>
				<dd>
					<select name="ITEM_NO" class="combox">
						<c:forEach items="${itemList}" var="item">
							<option value="${item.ITEM_NO}">${item.ITEM_NAME}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><!-- 公司 --><spring:message code="ar.viewcycleparameter.title.gongsi"/></dt>
				<dd>
					<select name="dis_CPNY_ID" disabled="disabled">
						<c:forEach items="${cpnyList}" var="cpny">
							<option value="${cpny.CPNY_ID}" <c:if test="${defaultCpnyID eq cpny.CPNY_ID}">selected</c:if>>${cpny.CONTENT}</option>
						</c:forEach>
					</select>
					<input id="CPNY_ID" type="hidden" name="CPNY_ID" value="${defaultCpnyID}"/>
				</dd>
			</dl>
			<dl>
				<dt><!-- 单位 --><spring:message code="ar.viewitemparameter.title.unit"/></dt>
				<dd>
					<select name="UNIT" class="combox">
						<option value="DAY"><!-- 天 --><spring:message code="ar.viewsummaryparameteritem.title.day"/></option>
						<option value="HOUR" selected><!-- 小时 --><spring:message code="ar.viewsummaryparameteritem.title.hour"/></option>
						<option value="MINUTE"><!-- 分钟 --><spring:message code="ar.viewsummaryparameteritem.title.minite"/></option>
						<option value="TIME"><!-- 计数 --><spring:message code="ar.viewsummaryparameteritem.title.count"/></option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><!-- 最小值 --><spring:message code="ar.viewitemparameter.title.zuixiaozhi"/></dt>
				<dd>
					<select name="MIN_UNIT" class="combox">
						<option value="0.5">0.5</option>
						<option value="1" selected>1</option>
						<option value="0.25">0.25</option>
						<option value="0.05">0.05</option>
						<option value="0.01">0.01</option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><!-- 活跃状态 --><spring:message code="ar.viewcycle.title.huoyuezhuangtai"/></dt>
				<dd>
					<select name="ACTIVITY" class="combox">
				      <option value="1" selected><!-- 是--><spring:message code="ar.viewcycle.content.yes"/></option>
				      <option value="0"><!-- 否--><spring:message code="ar.viewcycle.content.no"/></option>
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

<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
</script>
<div class="pageContent">
	<form method="post" action="/sys/attendancesetting/updateAttendItemInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" name="NO" value="${AttendItemInfo.ITEM_NO}"/>
		<input type="hidden" name="OLD_ITEM_ID" value="${AttendItemInfo.ITEM_ID}"/>
		<div class="pageFormContent nowrap" layoutH="60">
		<dl>
		    <dt><spring:message code="pa.insurance.title.projectType"/><!--项目类型-->：</dt>
			<dd><select  disabled="disabled"> 
			<option value="1" <c:if test="${AttendItemInfo.PROJECT_TYPE eq '明细项目'}">selected</c:if>>
				<spring:message code="ar.attenditem.title.mingxixiangmu"/>
			</option>
			<option value="2" <c:if test="${AttendItemInfo.PROJECT_TYPE eq '汇总项目'}">selected</c:if>>
				<spring:message code="ar.viewsummaryparameteritem.title.huizongxiangmu"/>
			</option>
			</select></dd>
			<c:if test="${AttendItemInfo.PROJECT_TYPE eq '明细项目'}">
					<input name="PROJECT_TYPE" type="hidden" id="PROJECT_TYPE" value="1"/>
			</c:if>
			<c:if test="${AttendItemInfo.PROJECT_TYPE eq '汇总项目'}">
					<input name="PROJECT_TYPE" type="hidden" id="PROJECT_TYPE" value="2"/>
			</c:if>
			</dl>
			
		    
			<c:if test="${AttendItemInfo.PROJECT_TYPE eq '明细项目'}">
			<dl id="NAME6"  style=display:>
				<dt><!-- 项目ID --><spring:message code="ar.viewItem.title.xiangmumingID"/></dt>
				<dd><input name="ITEM_ID" type="text" size="30" value="${AttendItemInfo.ITEM_ID}" readonly="readonly"/></dd>
			</dl>
			</c:if>
			<c:if test="${AttendItemInfo.PROJECT_TYPE eq '汇总项目'}">
			<dl>
				<dt ><!-- 汇总项目ID --><spring:message code="ar.viewsummaryitem.title.huizongxiangmuID"/>:</dt>
				<dd>
					<input name="STA_ITEM_ID" type="text" size="30" value="${AttendItemInfo.ITEM_ID}" readonly="readonly"/>
				</dd>
			</dl>
			</c:if>
			<ait:SyLanguage1 languageNo="${AttendItemInfo.ITEM_NO}" readonlyName="readonly" />
			<c:if test="${AttendItemInfo.PROJECT_TYPE eq '明细项目'}">
			<dl>
				<dt><!-- 简称 --><spring:message code="ar.viewItem.title.jiancheng"/></dt> 
				<dd><input name="SHORT_NAME" type="text" value="${AttendItemInfo.SHORT_NAME}" readonly="readonly"/></dd>
			</dl>
			<dl>
				<dt><!-- 项目组 --><spring:message code="ar.viewItem.title.xiangmuzu"/></dt>
				<dd><ait:SelectSyCodeByCpnyID1 disabledYn="disabled" name="ITEM_GROUP_CODE" parentNo="1429" selected="${AttendItemInfo.ITEM_GROUP_CODE}" cnpyID="${CPNY_ID}" />
				    <input name="ITEM_GROUP_CODE" id="ITEM_GROUP_CODE" type="hidden" value="${AttendItemInfo.ITEM_GROUP_CODE}"/>
				</dd>
			</dl>
			<%--
			<dl>
				<dt>单位</dt>
				<dd><select name="UNIT" class="combox">
					<option value="MINUTE" <c:if test="${itemInfo.UNIT eq 'MINUTE'}">selected</c:if>>分钟</option>
					<option value="HOUR" <c:if test="${itemInfo.UNIT eq 'HOUR'}">selected</c:if>>小时</option>
				</select></dd>
			</dl>
			 --%>
			<dl>
				<dt><!-- 活跃状态 --><spring:message code="ar.viewcycle.title.huoyuezhuangtai"/></dt>
				<dd><input readonly="readonly" name="ACTIVITY" type="radio" value="1" <c:if test="${AttendItemInfo.ACTIVITY eq 1}">checked</c:if>/><!-- 活跃 --><spring:message code="ar.viewItem.title.activity"/>
				<input readonly="readonly" name="ACTIVITY" type="radio" value="0" <c:if test="${AttendItemInfo.ACTIVITY eq 0}">checked</c:if>/><!-- 不活跃 --><spring:message code="ar.viewItem.title.noactivity"/></dd>
			</dl>
			<dl>
				<dt ><!-- 明细项目ID -->CHRS1.0<spring:message code="ar.viewItem.title.xiangmumingID"/>:</dt>
				<dd>
					<input name="ITEM_ID_MAPPING" id="ITEM_ID_MAPPING" type="text" size="30" value="${AttendItemInfo.ITEM_ID_MAPPING}"/>
				</dd>
			</dl>
			<dl style="height:auto">
			<table>
					<tr>
						<td class="td_title" style="width:123px"><!-- 描述 --><spring:message code="ar.viewcycle.title.miaoshu"/></td>
						<td class="td_type"><textarea readonly="readonly" name="DESCRIPTION" cols="60" rows="2">${AttendItemInfo.DESCRIPTION}</textarea></td>
					</tr>
				</table>
			</dl>
			</c:if>
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

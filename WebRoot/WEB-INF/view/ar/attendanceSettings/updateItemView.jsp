<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/ar/attendanceSettings/updateItemInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" name="NO" value="${itemInfo.ITEM_NO}"/>
		<input type="hidden" name="OLD_ITEM_ID" value="${itemInfo.ITEM_ID}"/>
		<div class="pageFormContent nowrap" layoutH="60">
			<dl>
				<dt><!-- 项目ID --><spring:message code="ar.viewItem.title.xiangmumingID"/></dt>
				<dd><input name="ITEM_ID" type="text" class="required" value="${itemInfo.ITEM_ID}"/></dd>
			</dl>
			
			<ait:SyLanguage languageNo="${itemInfo.ITEM_NO}"/>
			
			<dl>
				<dt><!-- 简称 --><spring:message code="ar.viewItem.title.jiancheng"/></dt> 
				<dd><input name="SHORT_NAME" type="text" value="${itemInfo.SHORT_NAME}"/></dd>
			</dl>
			<dl>
				<dt><!-- 项目组 --><spring:message code="ar.viewItem.title.xiangmuzu"/></dt>
				<dd><ait:SelectSyCodeByCpnyID name="ITEM_GROUP_CODE" parentNo="1429" selected="${itemInfo.ITEM_GROUP_CODE}" cnpyID="${CPNY_ID}"/></dd>
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
				<dd><input name="ACTIVITY" type="radio" value="1" <c:if test="${itemInfo.ACTIVITY eq 1}">checked</c:if>/><!-- 活跃 --><spring:message code="ar.viewItem.title.activity"/>
				<input name="ACTIVITY" type="radio" value="0" <c:if test="${itemInfo.ACTIVITY eq 0}">checked</c:if>/><!-- 不活跃 --><spring:message code="ar.viewItem.title.noactivity"/></dd>
			</dl>
			<dl style="height:auto;">
				<table>
					<tr>
						<td class="td_title" style="width:122px"><!-- 人事政策 --><spring:message code="ar.attend.title.hr_policy"/></td>
						<td class="td_type"><textarea name="HR_POLICY" cols="60" rows="2">${itemInfo.HR_POLICY}</textarea>
										
						</td>
					</tr>
				</table>
			</dl>
			<dl style="height:auto;">
				<table>
					<tr>
						<td class="td_title" style="width:122px"><!-- 描述 --><spring:message code="ar.viewcycle.title.miaoshu"/></td>
						<td class="td_type"><textarea name="DESCRIPTION" cols="60" rows="2">${itemInfo.DESCRIPTION}</textarea>
										
						</td>
					</tr>
				</table>
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

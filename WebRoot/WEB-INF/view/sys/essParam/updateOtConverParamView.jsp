<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/sys/essParam/updateOtConverParamInfo" class="pageForm required-validate" 
		onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>
					<spring:message code="ess.viewApply.title.overtimeApplyType"/><!--加班类型-->:
				</dt>
				<dd>${otConverParam.PARAM_NAME }</dd>
				<input type="hidden" id="seach_PARAM_NO_SEQ" name="seach_PARAM_NO_SEQ" value="${otConverParam.PARAM_NO_SEQ }"/>
				<input type="hidden" id="seach_PARAM_NO" name="seach_PARAM_NO" value="${otConverParam.PARAM_NO }"/>
			</dl>
			<dl>
				<dt>
					<spring:message code="ess.viewApply.title.overtimeTranslate"/><!--加班转换类型-->:
				</dt>
				<dd>
					<select name="seach_PARAM_VALUE" id="seach_PARAM_VALUE">
                		<c:forEach items="${converTypeList}" var="conver" varStatus="j">	
                     		<option value="${conver.CODE_NO }" <c:if test="${otConverParam.PARAM_VALUE eq conver.CODE_NO}"></c:if>>
                     			${conver.CODE_NAME }
                     		</option>
                     	</c:forEach>
                    </select>      
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="sys.postManage.title.ifUsed"/><!--是否使用--></dt>
				<dd>
					<select id="seach_ACTIVITY" name="seach_ACTIVITY">
						<option value="1" <c:if test="${otConverParam.ACTIVITY eq '1'}">selected</c:if>>
                       		<spring:message code="sys.affirm.title.yes"/><!--是-->
                        </option>
						<option value="0" <c:if test="${otConverParam.ACTIVITY eq '0'}">selected</c:if>>
							<spring:message code="sys.affirm.title.no"/><!--否-->
						</option>
					</select>
				</dd>
			</dl>
		</div>		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="button.sys.affirm.save"/><!--保存--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>	
</div>
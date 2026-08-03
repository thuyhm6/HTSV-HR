<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/sys/essParam/addOtConverParamInfo" class="pageForm required-validate" 
		onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="90">
			<dl>
				<dt>
					<spring:message code="ess.viewApply.title.overtimeApplyType"/><!--加班类型-->:
				</dt>
				<dd>
					<select name="seach_PARAM_NO" id="seach_PARAM_NO">
						<!-- NO=77 没有对应的名称 -->
						<option value="77"><!--请选择-->
							--<spring:message code="sys.affirm.title.choose"/>--
                    	</option>
                		<c:forEach items="${overTimeApplyTypeList}" var="apply" varStatus="j">	
                     		<option value="${apply.CODE_NO }">${apply.CODE_NAME }</option>
                     	</c:forEach>
                    </select>
				</dd>
			</dl>
			<dl>
				<dt>
					<spring:message code="ess.viewApply.title.overtimeTranslate"/><!--加班转换类型-->:
				</dt>
				<dd>
					<select name="seach_PARAM_VALUE" id="seach_PARAM_VALUE">
                		<c:forEach items="${converTypeList}" var="conver" varStatus="j">	
                     		<option value="${conver.CODE_NO }">${conver.CODE_NAME }</option>
                     	</c:forEach>
                    </select>      
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="sys.postManage.title.ifUsed"/><!--是否使用--></dt>
				<dd>
					<select id="seach_ACTIVITY" name="seach_ACTIVITY">
						<option value="1"><spring:message code="sys.affirm.title.yes"/><!--是--></option>
						<option value="0"><spring:message code="sys.affirm.title.no"/><!--否--></option>
					</select>
				</dd>
			</dl>
		</div>		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
					<spring:message code="public.title.submit"/><!--提交--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
					<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>
</div>
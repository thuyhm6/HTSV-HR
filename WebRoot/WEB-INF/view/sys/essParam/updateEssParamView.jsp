<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT type='text/javascript'>
</SCRIPT>
 
<div class="pageContent">
	<form method="post" action="/sys/essParam/updateEssParamInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			 <input type="hidden" name="PARAM_NO" value="${essParam.PARAM_NO}"/>
			 <input type="hidden" name="NO" value="${essParam.PARAM_NO}"/>
			 <dl>
			 	<dt><spring:message code="sys.essParam.title.paramValue"/><!--参数值--></dt>
			 	<dd>
			 	<!-- 最终决裁后是否自动进行人事确认  -->
			 		<c:if test="${essParam.PARAM_NO eq '4155'}">
						<select name="PARAM_VALUE">
							<option value="0"><spring:message code="sys.affirm.title.no"/><!--否--></option>
							<option value="1"><spring:message code="sys.affirm.title.yes"/><!--是--></option>
						</select>			 		
			 		</c:if>
			 		<!-- 人事是否可以提前进行确认   -->
			 		<c:if test="${essParam.PARAM_NO eq '4156'}">
			 			<select name="PARAM_VALUE">
							<option value="0"><spring:message code="sys.affirm.title.no"/><!--否--></option>
							<option value="1"><spring:message code="sys.affirm.title.yes"/><!--是--></option>
						</select>			 
			 		</c:if>
			 		<!-- 人事是否可以在确认后重新确认  -->
			 		<c:if test="${essParam.PARAM_NO eq '4157'}">
			 			<select name="PARAM_VALUE">
							<option value="0"><spring:message code="sys.affirm.title.no"/><!--否--></option>
							<option value="1"><spring:message code="sys.affirm.title.yes"/><!--是--></option>
						</select>			 
			 		</c:if>
			 		<!--  决裁是否可反悔  -->
			 		<c:if test="${essParam.PARAM_NO eq '4158'}">
			 			<select name="PARAM_VALUE">
			 				<option value="1"><spring:message code="sys.affirm.title.yes"/><!--是--></option>
							<option value="0"><spring:message code="sys.affirm.title.no"/><!--否--></option>
						</select>			 
			 		</c:if>
			 		<!--  是否需要决裁  -->
			 		<c:if test="${essParam.PARAM_NO eq '4159'}">
			 			<select name="PARAM_VALUE">
			 				<option value="1"><spring:message code="sys.affirm.title.yes"/><!--是--></option>
							<option value="0"><spring:message code="sys.affirm.title.no"/><!--否--></option>
						</select>	
			 		</c:if>
			 		<!-- 是否需要人事确认   -->
			 		<c:if test="${essParam.PARAM_NO eq '4160'}">
			 			<select name="PARAM_VALUE">
			 				<option value="1"><spring:message code="sys.affirm.title.yes"/><!--是--></option>
							<option value="0"><spring:message code="sys.affirm.title.no"/><!--否--></option>
						</select>	
			 		</c:if>
			 		<!--  加班可申请多少天前的加班(-1表示不限制)  -->
			 		<c:if test="${essParam.PARAM_NO eq '4161'}">
			 			<input type="text" name="PARAM_VALUE" value="-1"/>
			 		</c:if>
			 		<!--  加班可申请多少天后的加班(-1表示不限制)   -->
			 		<c:if test="${essParam.PARAM_NO eq '4162'}">
			 			<input type="text" name="PARAM_VALUE" value="-1"/>
			 		</c:if>
			 		<!-- 加班月累计小时数上限 -1:不判断(可无限申请)  -->
			 		<c:if test="${essParam.PARAM_NO eq '4163'}">
			 			<input type="text" name="PARAM_VALUE" value="-1"/>
			 		</c:if>
			 		<!-- 决裁参照体系   -->
			 		<c:if test="${essParam.PARAM_NO eq '4164'}">
			 			<select name="PARAM_VALUE">
			 				<option value="1"><spring:message code="sys.affirm.title.yes"/><!--是--></option>
							<option value="0"><spring:message code="sys.affirm.title.no"/><!--否--></option>
						</select>
			 		</c:if>
			 		<!--  有年假是否能申请病事假  -->
			 		<c:if test="${essParam.PARAM_NO eq '4165'}">
			 			<select name="PARAM_VALUE">
			 				<option value="1"><spring:message code="sys.affirm.title.yes"/><!--是--></option>
							<option value="0"><spring:message code="sys.affirm.title.no"/><!--否--></option>
						</select>
			 		</c:if>
			 		<!--  入职发令是否需要决裁  -->
			 		<c:if test="${essParam.PARAM_NO >=4548 && essParam.PARAM_NO<=4569}">
			 			<select name="PARAM_VALUE">
			 				<option value="1"><spring:message code="sys.affirm.title.yes"/><!--是--></option>
							<option value="0"><spring:message code="sys.affirm.title.no"/><!--否--></option>
						</select>
			 		</c:if>
			  		
			 	 <!--  合同到期日期提醒天数(本日之后)   -->
			 		<!--  试用转正提醒天数(本日之后)   -->
			 		<!--  试用转正提醒天数(本日之后)   -->
			 		<!--  证件到期提醒天数(本日之后)    -->
			 		<!--  证件到期提醒天数(本日之后)    -->
			 		<c:if test="${essParam.PARAM_NO >=4542 &&essParam.PARAM_NO <=4547}">
			 			<input type="text" name="PARAM_VALUE" value="-1"/>
			 		</c:if> 
			 		<!--  是否进行加班转换 -->
			 		<c:if test="${essParam.PARAM_NO==4166}">
			 			<select name="PARAM_VALUE">
			 				<option value="1"><spring:message code="sys.affirm.title.yes"/><!--是--></option>
							<option value="0"><spring:message code="sys.affirm.title.no"/><!--否--></option>
						</select>
			 		</c:if>
			 	</dd>
			 </dl>
			 <dl>
			 	<dt><spring:message code="sys.essParam.title.legalPerson"/><!--法人-->:</dt>
			 	<dd>
			 		<select class="combox" name="PARAM_CPNY_ID">
						 <c:forEach items="${companyList}" var="company" varStatus="var">
								<option value="${company.CPNY_ID}">${company.CONTENT}</option>
						 </c:forEach>
					 </select>
			 	</dd>
			 </dl>			 
			 <ait:SyLanguage languageNo="${request_PARAM_NO}"/>
			 <dl>
				 <dt><spring:message code="sys.essParam.title.ifEnabled"/><!--是否启用--></dt>
				 <dd>
					  <select class="combox" name="ACTIVITY">
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

<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	 
</script> 
 
<div class="pageContent" >
	<form method="post" action="/sys/essParam/updateEssCheckParamInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone);">
	<table class="table" width="100%" layoutH="90" nowrapTD="false">
			<thead>
			<tr>
			    <th width="10"><spring:message code="sys.essParam.title.ApplicationModules"/><!--应用模块--></th>
				<th width="180"><spring:message code="sys.essParam.title.essParamName"/><!--ESS参数名称--></th>
				<th width="10"><spring:message code="sys.essParam.title.paramValue"/><!--参数值--></th>
			</tr>
		</thead>
		<tbody>
		
			<c:forEach items="${essParamList}" var="essParam" varStatus="i">
				<tr height="30" target="sid" rel="${essParam.PARAM_NO}">
				     <td>
						<c:if test="${essParam.MODULE eq 'Partner'}">
						<spring:message code="sys.essParam.title.essParameter"/><!--ESS参数--></c:if>
						<c:if test="${essParam.MODULE eq 'HUB'}">
						<spring:message code="sys.essParam.title.hrParameter"/><!--人事参数--></c:if>&nbsp;&nbsp;&nbsp;
					</td>
					<td>${essParam.PARAM_NAME}&nbsp;&nbsp;</td>
					<td>
			 	<!-- 最终决裁后是否自动进行人事确认  -->
			 		<c:if test="${essParam.PARAM_NO eq '4155'}">
			 		    <input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
						<select name="PARAM_VALUE_${essParam.PARAM_NO}">
							<option value="0" <c:if test="${essParam.PARAM_VALUE eq '0'}">selected</c:if>><spring:message code="sys.affirm.title.no"/><!--否--></option>
							<option value="1" <c:if test="${essParam.PARAM_VALUE eq '1'}">selected</c:if>><spring:message code="sys.affirm.title.yes"/><!--是--></option>
						</select>			 		
			 		</c:if>
			 		<!-- 人事是否可以提前进行确认   -->
			 		<c:if test="${essParam.PARAM_NO eq '4156'}">
			 			 <input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
			 			<select name="PARAM_VALUE_${essParam.PARAM_NO}">
							<option value="0" <c:if test="${essParam.PARAM_VALUE eq '0'}">selected</c:if>><spring:message code="sys.affirm.title.no"/><!--否--></option>
							<option value="1" <c:if test="${essParam.PARAM_VALUE eq '1'}">selected</c:if>><spring:message code="sys.affirm.title.yes"/><!--是--></option>
						</select>			 
			 		</c:if>
			 		<!-- 人事是否可以在确认后重新确认  -->
			 		<c:if test="${essParam.PARAM_NO eq '4157'}">
			 		 	<input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
			 			<select name="PARAM_VALUE_${essParam.PARAM_NO}">
							<option value="0" <c:if test="${essParam.PARAM_VALUE eq '0'}">selected</c:if>><spring:message code="sys.affirm.title.no"/><!--否--></option>
							<option value="1" <c:if test="${essParam.PARAM_VALUE eq '1'}">selected</c:if>><spring:message code="sys.affirm.title.yes"/><!--是--></option>
						</select>			 
			 		</c:if>
			 		<!--  决裁是否可反悔  -->
			 		<c:if test="${essParam.PARAM_NO eq '4158'}">
			 		 	<input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
			 			<select name="PARAM_VALUE_${essParam.PARAM_NO}">
			 				<option value="1" <c:if test="${essParam.PARAM_VALUE eq '1'}">selected</c:if>><spring:message code="sys.affirm.title.yes"/><!--是--></option>
							<option value="0" <c:if test="${essParam.PARAM_VALUE eq '0'}">selected</c:if>><spring:message code="sys.affirm.title.no"/><!--否--></option>
						</select>			 
			 		</c:if>
					<!--  加班是否需要决裁  -->
			 		<c:if test="${essParam.PARAM_NO eq '4159'}">
			 			 <input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
			 			<select name="PARAM_VALUE_${essParam.PARAM_NO}">
			 				<option value="1" <c:if test="${essParam.PARAM_VALUE eq '1'}">selected</c:if>><spring:message code="sys.affirm.title.yes"/><!--是--></option>
							<option value="0" <c:if test="${essParam.PARAM_VALUE eq '0'}">selected</c:if>><spring:message code="sys.affirm.title.no"/><!--否--></option>
						</select>	
			 		</c:if>
			 		<!-- 加班是否需要人事确认   -->
			 		<c:if test="${essParam.PARAM_NO eq '4160'}">
			 		 	<input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
			 			<select name="PARAM_VALUE_${essParam.PARAM_NO}">
			 				<option value="1" <c:if test="${essParam.PARAM_VALUE eq '1'}">selected</c:if>><spring:message code="sys.affirm.title.yes"/><!--是--></option>
							<option value="0" <c:if test="${essParam.PARAM_VALUE eq '0'}">selected</c:if>><spring:message code="sys.affirm.title.no"/><!--否--></option>
						</select>	
			 		</c:if>
			 		
			 		
			 		<!--  休假是否需要决裁  -->
					<c:if test="${essParam.PARAM_NO eq '78'}">
						<input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
					 	<select name="PARAM_VALUE_${essParam.PARAM_NO}">
					   		<option value="1" <c:if test="${essParam.PARAM_VALUE eq '1'}">selected</c:if>><spring:message code="sys.affirm.title.yes"/><!--是--></option>
					  		<option value="0" <c:if test="${essParam.PARAM_VALUE eq '0'}">selected</c:if>><spring:message code="sys.affirm.title.no"/><!--否--></option>
						</select>  
					</c:if>
					<!-- 休假是否需要人事确认   -->
					<c:if test="${essParam.PARAM_NO eq '79'}">
					  	<input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
					 	<select name="PARAM_VALUE_${essParam.PARAM_NO}">
					   		<option value="1" <c:if test="${essParam.PARAM_VALUE eq '1'}">selected</c:if>><spring:message code="sys.affirm.title.yes"/><!--是--></option>
					  		<option value="0" <c:if test="${essParam.PARAM_VALUE eq '0'}">selected</c:if>><spring:message code="sys.affirm.title.no"/><!--否--></option>
						</select>  
					</c:if>
					
					<!--  (备用)是否需要决裁  -->
					<c:if test="${essParam.PARAM_NO eq '80'}">
						<input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
					 	<select name="PARAM_VALUE_${essParam.PARAM_NO}">
					   		<option value="1" <c:if test="${essParam.PARAM_VALUE eq '1'}">selected</c:if>><spring:message code="sys.affirm.title.yes"/><!--是--></option>
					  		<option value="0" <c:if test="${essParam.PARAM_VALUE eq '0'}">selected</c:if>><spring:message code="sys.affirm.title.no"/><!--否--></option>
						</select>  
					</c:if>
					<!-- (备用)是否需要人事确认   -->
					<c:if test="${essParam.PARAM_NO eq '81'}">
					  	<input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
					 	<select name="PARAM_VALUE_${essParam.PARAM_NO}">
					   		<option value="1" <c:if test="${essParam.PARAM_VALUE eq '1'}">selected</c:if>><spring:message code="sys.affirm.title.yes"/><!--是--></option>
					  		<option value="0" <c:if test="${essParam.PARAM_VALUE eq '0'}">selected</c:if>><spring:message code="sys.affirm.title.no"/><!--否--></option>
						</select>  
					</c:if>
					
					<!--  (备用)是否需要决裁  -->
					<c:if test="${essParam.PARAM_NO eq '82'}">
						<input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
					 	<select name="PARAM_VALUE_${essParam.PARAM_NO}">
					   		<option value="1" <c:if test="${essParam.PARAM_VALUE eq '1'}">selected</c:if>><spring:message code="sys.affirm.title.yes"/><!--是--></option>
					  		<option value="0" <c:if test="${essParam.PARAM_VALUE eq '0'}">selected</c:if>><spring:message code="sys.affirm.title.no"/><!--否--></option>
						</select>  
					</c:if>
					<!-- (备用)是否需要人事确认   -->
					<c:if test="${essParam.PARAM_NO eq '224'}">
					  	<input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
					 	<select name="PARAM_VALUE_${essParam.PARAM_NO}">
					   		<option value="1" <c:if test="${essParam.PARAM_VALUE eq '1'}">selected</c:if>><spring:message code="sys.affirm.title.yes"/><!--是--></option>
					  		<option value="0" <c:if test="${essParam.PARAM_VALUE eq '0'}">selected</c:if>><spring:message code="sys.affirm.title.no"/><!--否--></option>
						</select>  
					</c:if>
					
					<!--  (备用)是否需要决裁  -->
					<c:if test="${essParam.PARAM_NO eq '225'}">
						<input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
					 	<select name="PARAM_VALUE_${essParam.PARAM_NO}">
					   		<option value="1" <c:if test="${essParam.PARAM_VALUE eq '1'}">selected</c:if>><spring:message code="sys.affirm.title.yes"/><!--是--></option>
					  		<option value="0" <c:if test="${essParam.PARAM_VALUE eq '0'}">selected</c:if>><spring:message code="sys.affirm.title.no"/><!--否--></option>
						</select>  
					</c:if>
					<!-- (备用)是否需要人事确认   -->
					<c:if test="${essParam.PARAM_NO eq '363'}">
					  	<input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
					 	<select name="PARAM_VALUE_${essParam.PARAM_NO}">
					   		<option value="1" <c:if test="${essParam.PARAM_VALUE eq '1'}">selected</c:if>><spring:message code="sys.affirm.title.yes"/><!--是--></option>
					  		<option value="0" <c:if test="${essParam.PARAM_VALUE eq '0'}">selected</c:if>><spring:message code="sys.affirm.title.no"/><!--否--></option>
						</select>  
					</c:if>
			 		
			 		<!--  加班可申请多少天前的加班(-1表示不限制)  -->
			 		<c:if test="${essParam.PARAM_NO eq '4161'}">
			 			 <input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
			 			<input type="text" name="PARAM_VALUE_${essParam.PARAM_NO}" value="${essParam.PARAM_VALUE}"  class="number"/>
			 		</c:if>
			 		<!--  加班可申请多少天后的加班(-1表示不限制)   -->
			 		<c:if test="${essParam.PARAM_NO eq '4162'}">
			 		 	<input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
			 			<input type="text" name="PARAM_VALUE_${essParam.PARAM_NO}" value="${essParam.PARAM_VALUE}" class="number"/>
			 		</c:if>
			 		<!-- 加班月累计小时数上限 -1:不判断(可无限申请)  -->
			 		<c:if test="${essParam.PARAM_NO eq '4163'}">
			 			 <input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
			 			<input type="text" name="PARAM_VALUE_${essParam.PARAM_NO}" value="${essParam.PARAM_VALUE}"  class="number" />
			 		</c:if>
			 		<!-- 决裁参照体系   -->
			 		<c:if test="${essParam.PARAM_NO eq '4164'}">
			 			 <input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
			 			<select name="PARAM_VALUE_${essParam.PARAM_NO}">
			 				<option value="1" <c:if test="${essParam.PARAM_VALUE eq '1'}">selected</c:if>><spring:message code="sys.affirm.title.yes"/><!--是--></option>
							<option value="0" <c:if test="${essParam.PARAM_VALUE eq '0'}">selected</c:if>><spring:message code="sys.affirm.title.no"/><!--否--></option>
						</select>
			 		</c:if>
			 		<!--  有年假是否能申请病事假  -->
			 		<c:if test="${essParam.PARAM_NO eq '4165'}">
			 			<input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
			 			<select name="PARAM_VALUE_${essParam.PARAM_NO}">
			 				<option value="1" <c:if test="${essParam.PARAM_VALUE eq '1'}">selected</c:if>><spring:message code="sys.affirm.title.yes"/><!--是--></option>
							<option value="0" <c:if test="${essParam.PARAM_VALUE eq '0'}">selected</c:if>><spring:message code="sys.affirm.title.no"/><!--否--></option>
						</select>
			 		</c:if>
			 		<!--  异动发令是否需要决裁  -->
			 		<c:if test="${essParam.PARAM_NO >=4548 && essParam.PARAM_NO<=4569}">
			 			<input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
			 			<select name="PARAM_VALUE_${essParam.PARAM_NO}">
			 				<option value="1" <c:if test="${essParam.PARAM_VALUE eq '1'}">selected</c:if>><spring:message code="sys.affirm.title.yes"/><!--是--></option>
							<option value="0" <c:if test="${essParam.PARAM_VALUE eq '0'}">selected</c:if>><spring:message code="sys.affirm.title.no"/><!--否--></option>
						</select>
			 		</c:if>
			 		
			 		<!--  异动发令是否需要决裁  -->
			 		<c:if test="${essParam.PARAM_NO >=5309 && essParam.PARAM_NO<=5321}">
			 			<input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
			 			<select name="PARAM_VALUE_${essParam.PARAM_NO}">
			 				<option value="1" <c:if test="${essParam.PARAM_VALUE eq '1'}">selected</c:if>><spring:message code="sys.affirm.title.yes"/><!--是--></option>
							<option value="0" <c:if test="${essParam.PARAM_VALUE eq '0'}">selected</c:if>><spring:message code="sys.affirm.title.no"/><!--否--></option>
						</select>
			 		</c:if>
			  		
			 		<!--  合同到期日期提醒天数(本日之后)   -->
			 		<!--  试用转正提醒天数(本日之后)   -->
			 		<!--  试用转正提醒天数(本日之后)   -->
			 		<!--  证件到期提醒天数(本日之后)    -->
			 		<!--  证件到期提醒天数(本日之后)    -->
			 		<c:if test="${essParam.PARAM_NO >=4542 &&essParam.PARAM_NO <=4547}">
			 			 <input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
			 			<input type="text" name="PARAM_VALUE_${essParam.PARAM_NO}" value="${essParam.PARAM_VALUE}"  class="number"/>
			 		</c:if> 
			 		<!--  合同到期日期提醒天数(本日之前) -->
			 		<c:if test="${essParam.PARAM_NO==15713}">
			 			<input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
			 			<input type="text" name="PARAM_VALUE_${essParam.PARAM_NO}" value="${essParam.PARAM_VALUE}"  class="number"/>
			 		</c:if> 
			 		<!--  是否进行加班转换 -->
			 		<c:if test="${essParam.PARAM_NO==4166}">
			 			<input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
			 			<select name="PARAM_VALUE_${essParam.PARAM_NO}">
			 				<option value="1" <c:if test="${essParam.PARAM_VALUE eq '1'}">selected</c:if>>
			 				<spring:message code="sys.affirm.title.yes"/><!--是--></option>
							<option value="0" <c:if test="${essParam.PARAM_VALUE eq '0'}">selected</c:if>>
							<spring:message code="sys.affirm.title.no"/><!--否--></option>
						</select>
			 		</c:if>
			 		
			 		<!-- 入职发令成功后是否弹出录入工资数据页面   -->
			 		<c:if test="${essParam.PARAM_NO eq '122095'}">
			 			 <input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
			 			<select name="PARAM_VALUE_${essParam.PARAM_NO}">
							<option value="0" <c:if test="${essParam.PARAM_VALUE eq '0'}">selected</c:if>><spring:message code="sys.affirm.title.no"/><!--否--></option>
							<option value="1" <c:if test="${essParam.PARAM_VALUE eq '1'}">selected</c:if>><spring:message code="sys.affirm.title.yes"/><!--是--></option>
						</select>			 
			 		</c:if>
			 		
			 		<!-- 号奉发令是否需要决裁   -->
			 		<c:if test="${essParam.PARAM_NO eq '122018'}">
			 			 <input type="hidden" name="PARAM_NOS" value="${essParam.PARAM_NO}"/>
			 			<select name="PARAM_VALUE_${essParam.PARAM_NO}">
							<option value="0" <c:if test="${essParam.PARAM_VALUE eq '0'}">selected</c:if>><spring:message code="sys.affirm.title.no"/><!--否--></option>
							<option value="1" <c:if test="${essParam.PARAM_VALUE eq '1'}">selected</c:if>><spring:message code="sys.affirm.title.yes"/><!--是--></option>
						</select>			 
			 		</c:if>
			 		
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	 <div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="button.sys.affirm.save"/><!--保存--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="sys.essParam.title.return"/><!--返回--></button></div></div></li>
			</ul>
	 </div>
</form>
</div>

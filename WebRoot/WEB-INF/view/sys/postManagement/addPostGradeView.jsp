<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/sys/postManagement/addPostGradeInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
		    <ait:SyLanguage/>
			<dl>
				<dt><spring:message code="sys.postManage.title.postGrade"/><!--职级-->:</dt>
				<dd>
					<select name="POST_NO">
						<c:forEach items="${postItemList}" var="post">
							<option value="${post.POST_NO}">${post.POST_NAME}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="sys.postManage.title.postLevel"/><!--职等-->:</dt>
				<dd>
					<select name="GRADE_LEVEL">
						<c:forEach items="${levelList}" var="level">
							<option value="${level.CODE_NO}">${level.CODE_NAME}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl style="height:auto;">
				<dt style="height:auto;"><spring:message code="sys.affirm.title.duty"/><!--职责-->:</dt>
				<dd style="height:auto;">
					<ul>
	    			 
				       <c:forEach items="${dutyList}" var="vList" varStatus="i">
				             <c:choose>
				              	<c:when test="${i.count % 5 == 0}">
						           <li> 
						              <input name="DUTY_NOS"  value="${vList.DUTY_NO}"  type="checkbox" style="border:0px"/>
						              	${vList.DUTY_NAME}
						            </li>
								</c:when>
				  				<c:otherwise>
							         <li> 
							           <input name="DUTY_NOS"  value="${vList.DUTY_NO}" type="checkbox" style="border:0px"/>					              		
							             ${vList.DUTY_NAME}
							          </li>	  							
				  				</c:otherwise>
					      </c:choose>	
						</c:forEach>
		    		</ul>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="sys.postManage.title.ifUsed"/><!--是否使用--></dt>
				<dd>
					<select name="ACTIVITY">
						<option value="1"><spring:message code="sys.affirm.title.yes"/><!--是--></option>
						<option value="0"><spring:message code="sys.affirm.title.no"/><!--否--></option>
					</select>
				</dd>
			</dl>			
		</div>		
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.submit"/><!--提交--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>
</div>
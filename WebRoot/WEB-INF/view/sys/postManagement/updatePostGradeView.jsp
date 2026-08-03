<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/sys/postManagement/updatePostGradeInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			<input type="hidden"  name="POST_GRADE_NO" value="${postGradeInfo.POST_GRADE_NO}">
			<input type="hidden"  name="NO" value="${postGradeInfo.POST_GRADE_NO}">
			<ait:SyLanguage languageNo="${postGradeInfo.POST_GRADE_NO}"/>
			<dl>
				<dt><spring:message code="sys.postManage.title.postGrade"/><!--职级-->:</dt>
				<dd>
					<select name="POST_NO">
						<c:forEach items="${postItemList}" var="post">
							<option value="${post.POST_NO}" <c:if test="${post.POST_NO eq postGradeInfo.POST_NO}">selected</c:if>>${post.POST_NAME}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="sys.postManage.title.postLevel"/><!--职等-->:</dt>
				<dd>
					<select name="GRADE_LEVEL">
						<c:forEach items="${levelList}" var="level">
							<option value="${level.CODE_NO}" <c:if test="${level.CODE_NO eq postGradeInfo.GRADE_LEVEL }">selected</c:if>>${level.CODE_NAME}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="sys.affirm.title.duty"/><!--职责-->:</dt>
				<dd>
					<ul> 
				       <c:forEach items="${dutyList}" var="vList" varStatus="i">
				             <c:choose>
				              	<c:when test="${i.count % 5 == 0}">
						           <li>
						              <input name="DUTY_NOS"  value="${vList.DUTY_NO}" <c:if test="${vList.ISCHOOSE eq '1'}">checked="checked"</c:if> type="checkbox" style="border:0px"/>
						              	${vList.DUTY_NAME}
						            </li>
								</c:when>
				  				<c:otherwise>
							         <li>
							           <input name="DUTY_NOS"  <c:if test="${vList.ISCHOOSE eq '1'}">checked="checked"</c:if> value="${vList.DUTY_NO}" type="checkbox" style="border:0px"/>					              		
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
						<option value="1" <c:if test="${postGradeInfo.ACTIVITY eq '1'}">selected</c:if>>
                        <spring:message code="sys.affirm.title.yes"/><!--是-->
                        </option>
						<option value="0" <c:if test="${postGradeInfo.ACTIVITY eq '0'}">selected</c:if>>
						<spring:message code="sys.affirm.title.no"/><!--否--></option>
					</select>
				</dd>
			</dl>
		</div>		
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="submit">
				<spring:message code="button.sys.affirm.save"/><!--保存--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>	
</div>
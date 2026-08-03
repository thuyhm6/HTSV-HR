<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/sys/postManagement/addPostGroupInfo" class="pageForm required-validate" onsubmit="return validateCallback(this);">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>ID:</dt>
				<dd>
					<input type="text" name="POST_GROUP_ID" class="required textInput" size="30"  />
				</dd>			
			</dl>			
			<dl>
				<dt><spring:message code="sys.postManage.title.chineseName"/><!--中文名称-->:</dt>
				<dd>
					<input type="text" name="POST_GROUP_NAME" class="required textInput" size="30"  />
				</dd>				
			</dl>						
			<dl>
				<dt><spring:message code="sys.postManage.title.englishName"/><!--英文名称-->:</dt>
				<dd>
					<input type="text" name="POST_GROUP_EN_NAME" size="30" />
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
<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT type='text/javascript'>		
 $(function(){
 	$tree = $("#parentMenuTree").ligerTree(
	          { 
		          checkbox: false
	          }
          );
	 
	});
</SCRIPT>
<div class="pageContent" style="padding:5px">
	<!-- 页签主容器开始 -->
	<div class="tabs">
		<!-- 单个页签主体开始 -->
		<div class="tabsContent">
			<!-- 主体内容开始 -->
			<div>
				<!-- 树框开始 -->
				<div layoutH="23" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff" 
				     title="<spring:message code='sys.menu.title.menu'/>">
		           <c:set var="parentSize" value="${fn:length(parentMenuList)}" />
		            <!--不带复选框-->
				    <ul id="parentMenuTree">
	
				    	<c:forEach items="${parentMenuList}" var="menu" varStatus="i">
				    		<c:if test="${i.index == 0}" >
				    			<li url="${menu.MENU_NO}" isexpand="false">
						            <span >${menu.CONTENT}</span>
						            <ul>
				    		</c:if>
				    		
				    		<c:if test="${i.index != 0 && menu.DEPTH == 0}" >
						            </ul>
						        </li> 
				    			<li url="${menu.MENU_NO}" isexpand="false">
						            <span >${menu.CONTENT}</span>
						            <ul>
				    		</c:if>
				    		<c:if test="${menu.DEPTH != 0}" >
				    			<li url="${menu.MENU_NO}"><span ><a href="/sys/menu/getMenuTreeByParentMenu?MENU_NO=${menu.MENU_NO}"  target="ajax" rel="MenujbsxBox">${menu.CONTENT}</a></span></li>
				    		</c:if>
				    	</c:forEach>
							
				    </ul>
				</div>
				<!-- 树框结束 -->
				<!-- 工作区容器开始 -->
				<div id="MenujbsxBox" class="unitBox" style="margin-left:246px;">
					<div class="pageContent">
						<form method="post" action="/sys/menu/saveMenuParam" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
							<div class="pageFormContent nowrap" layoutH="60">			
								<dl>
									<dt><spring:message code="sys.basicMaint.title.companyName"/><!--公司-->:</dt>
									<dd>
										<select  class="combox"  name="CPNY_ID">
											<c:forEach items="${companyList}" var="company">
											<option value="${company.CPNY_ID}">${company.CONTENT}</option>
											</c:forEach>
										</select>
									</dd>
								</dl>
								<dl>
									<dt><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态-->:</dt>
									<dd>
										<select class="combox" name="ACTIVITY" id="PA_RELEVANCE_FLAG">
											<option value="0" selected><spring:message code="sys.arAffirmPost.title.enable"/><!--不启用--></option>
											<option value="1"><spring:message code="sys.arAffirmPost.title.able"/><!--启用--></option>
										</select>
									</dd>
								</dl>
								<dl>
									<dt><spring:message code="sys.menu.title.menu"/><!--菜单-->:</dt>
									<dd>
										<input type="hidden" id="MenuNo" name="MENU_NOS"  class="required textInput"   />
										<div id="treeDiv" style=" float:left; display:block; margin:10px; overflow:auto;width:600px; height:500px;border:solid 1px #CCC; line-height:21px; background:#FFF;">
					    					<ul id="tree1"></ul>
									</dd>
								</dl>
							</div>
							<div class="formBar">
								<ul>
									<li><div class="buttonActive"><div class="buttonContent"> </div></div></li>
									<li><div class="button"><div class="buttonContent"><button type="button" class="close">
									<spring:message code="public.title.close"/><!--取消--></button></div></div></li>
								</ul>
							</div>
						</form>
					</div>
				</div>
				<!-- 工作区容器结束 -->
			</div>
			<!-- 主体内容结束-->
		</div>
		<!-- 单个页签主体结束-->
		<!-- 单个页签页脚开始-->
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
		<!-- 单个页签页脚开始结束-->
	</div>
	<!-- 页签主容器结束 -->
</div>
<!-- 页面主容器结束-->
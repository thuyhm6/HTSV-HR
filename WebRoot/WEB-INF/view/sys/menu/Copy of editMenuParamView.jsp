<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT type='text/javascript'>		
 $(function(){
 	$tree = $("#parentMenuTree").ligerTree(
	          { 
		          checkbox: false
	          }
          );
	 $("#tree1").ligerTree(
	          { 
		          checkbox: true,idFieldName :'id',parentIDFieldName :'pid',
			      nodeWidth:300,
			      onClick: function(){
					   var checkMenuNoForMenuList=new Array();
					   $.each($('.l-checkbox-incomplete','#treeDiv'),function(index,value){
							checkMenuNoForMenuList.push(value.parentNode.parentNode.id);
						});
					   $.each($('.l-checkbox-checked','#treeDiv'),function(index,value){
							checkMenuNoForMenuList.push(value.parentNode.parentNode.id);
						});
						$.each($('.l-checkbox-unchecked','#treeDiv'),function(index,value){
							if($(".AliasClass",$(value).parent()).length!=0)
								$(".AliasClass",$(value).parent()).remove();
						});
						$('#MenuNo').attr('value',checkMenuNoForMenuList);
					}   
	          }
          );
           var checkCodeNoForList=new Array();
          <c:forEach items="${subMenusSelect}" var="selete" varStatus="i">
				$(".l-checkbox-unchecked",$("li[id=${selete.MENU_NO}][treedataindex!=0]")).removeClass("l-checkbox-unchecked").addClass("l-checkbox-checked");
				$($(".l-tree-icon-folder",$("li[id=${selete.MENU_NO}]")).parent().children().get(1)).removeClass("l-checkbox-unchecked").addClass("l-checkbox-incomplete");
				checkCodeNoForList.push('${selete.MENU_NO}');
          </c:forEach>
          if(checkCodeNoForList.length>0)
          	$('#MenuNo').attr('value',checkCodeNoForList);
	
	});
function addCpnyId(cpnyId){
		$.each($('a',$('#parentMenuTree')),function(index,value){
			newHref=value.href.split("CPNY_ID="); 
			backHref=newHref[0]+"CPNY_ID="+cpnyId.value;
			$(value).attr("href",backHref);
			
		});
		
	}
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
				     title="<spring:message code='sys.menu.title.menu'/><!--菜单-->">
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
				    			<li url="${menu.MENU_NO}"><span ><a href="/sys/menu/getMenuTreeByParentMenu?MENU_NO=${menu.MENU_NO}&CPNY_ID=${menuP.CPNY_ID}" target="ajax" rel="MenuEditjbsxBox">${menu.CONTENT}</a></span></li>
				    		</c:if>
				    	</c:forEach>
							    </ul>
							 </li>
				    </ul>
				</div>
				<!-- 树框结束 -->
				<!-- 工作区容器开始 -->
				<div id="MenuEditjbsxBox" class="unitBox" style="margin-left:246px;">
<div class="pageContent">
	<form method="post" action="/sys/menu/updateMenuParam" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="60">			
			<dl>
				<dt><spring:message code="sys.basicMaint.title.companyName"/><!--公司-->:</dt>
				<dd> <input type="hidden" name="PARAM_NO" value="${menuP.PARAM_NO}"/>
					<select  class="combox"  name="CPNY_ID"   onchange="addCpnyId(this)">
						<c:forEach items="${companyList}" var="company">
						<option <c:if test="${menuP.CPNY_ID eq company.CPNY_ID}">selected</c:if>  value="${company.CPNY_ID}">${company.CONTENT}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态-->:</dt>
				<dd>
					<select class="combox" name="ACTIVITY" id="PA_RELEVANCE_FLAG">
						<option value="0" <c:if test="${menuP.ACTIVITY eq 0}">selected</c:if>   >
						<spring:message code="sys.arAffirmPost.title.enable"/><!--不启用--></option>
						<option value="1" <c:if test="${menuP.ACTIVITY eq 1}">selected</c:if>>
						<spring:message code="sys.arAffirmPost.title.able"/><!--启用--></option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="sys.menu.title.menu"/><!--菜单-->:</dt>
				<dd>
					<input type="hidden" id="MenuNo" name="MENU_NOS" value="${menuP.MENU_NO}" class="required textInput"   />
					<div id="treeDiv" style=" float:left; display:block; margin:10px; overflow:auto;width:600px; height:500px;border:solid 1px #CCC; line-height:21px; background:#FFF;">
    					<ul id="tree1">
							<c:forEach items="${subMenus}" var="subMenu" varStatus="i"> 
							
									<c:if test="${i.index == 0}" >
										    			<li id="${subMenu.MENU_NO}" url="${subMenu.MENU_NO}" isexpand="false">
												            <span >${subMenu.CONTENT} </span>
												            <ul>
									</c:if>
										    		
									<c:if test="${i.index != 0 && subMenu.DEPTH == 1}" >
												            </ul>
												        </li> 
										    			<li id="${subMenu.MENU_NO}" url="${subMenu.MENU_NO}" isexpand="false">
												            <span >${subMenu.CONTENT} </span>
												            <ul>
									</c:if>
									<c:if test="${subMenu.DEPTH != 1}" >
										    			<li id="${subMenu.MENU_NO}" pid="${subMenu.MENU_PARENT_NO}"  url="${subMenu.MENU_NO}">
															<span ><a>${subMenu.CONTENT}</a>
											    				<input id="${subMenu.MENU_NO}zh" type="hidden" value="${subMenu.ALIAS_ZH }"/>
											    				<input id="${subMenu.MENU_NO}en" type="hidden" value="${subMenu.ALIAS_EN }"/>
											    				<input id="${subMenu.MENU_NO}kr" type="hidden" value="${subMenu.ALIAS_KR }"/>
										    				</span>
										    			</li>
									</c:if>
							</c:forEach>
												    </ul>
							  </li>    					
    					</ul>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.submit"/><!--提交--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
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

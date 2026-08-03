<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT type='text/javascript'>		
var zTree;
var demoIframe;

var setting_sy0460 = {
	view: {
		dblClickExpand: false,
		showLine: true,
		selectedMulti: false,
		expandSpeed: "fast"
	},
	data: {
		key: {
			name: "CONTENT",
			open:"true"
		},
		simpleData: {
			enable:true,
			idKey: "MENU_NO",
			pIdKey: "MENU_PARENT_NO",
			rootPId: ""
		}
	}, 
	callback: {
		beforeClick: function(treeId, treeNode) { 
			
			var hrefUrl="/sys/menu/getMenuTreeByParentMenu?";
		 	var cpnyId=document.getElementById("CPNY_ID").value;
		 	var backHref=hrefUrl+"MENU_NO="+treeNode.MENU_NO+"&CPNY_ID="+cpnyId;
		 	document.getElementById("childLink_sy0460").href=backHref;
		 	document.getElementById("childLink_sy0460").click();
		}
	}
};

 var zNodes;
 
 $.ajax({  
        async : false,  
        cache:false,  
        type: 'POST',  
        dataType : "json",  
        url: "/sys/menu/getParentTreeData",//请求的action路径  
        error: function () {//请求失败处理函数  
            alertMsg.error('<spring:message code="alert.message.sys.arAffirm.qequestFailed"/>');   
        },  
        success:function(data){ //请求成功后处理函数。    
        	zNodes = data;   //把后台封装好的简单Json格式赋给treeNodes
        }  
    });
  
// 初始调用
$(document).ready(function(){
    //布局
	var t = $("#parentMenuTree_sy0460");
	t = $.fn.zTree.init(t, setting_sy0460, zNodes);
});

function addCpnyIdSy0460(cpnyId){
	/**
		$.each($('a',$('#parentMenuTree')),function(index,value){
			newHref=value.href.split("CPNY_ID="); 
			
			backHref=newHref[0]+"CPNY_ID="+cpnyId;
			$(value).attr("href",backHref);
			
		});
	*/
	var hrefUrl=document.getElementById("childLink_sy0460").href;
	var hrefArr=hrefUrl.split("CPNY_ID");
	var newUrl=hrefArr[0]+"CPNY_ID="+cpnyId.value;
	document.getElementById("childLink_sy0460").href=newUrl;
	
 	document.getElementById("childLink_sy0460").click();
}
</SCRIPT>
<div class="pageContent" style="padding:5px">
	<!-- 页签主容器开始 -->
		<!-- 单个页签主体开始 -->
		<div class="tabsContent">
			<!-- 主体内容开始 -->
			<div>
				<!-- 树框开始 -->
				<div layoutH="23" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff" 
				     title="<spring:message code='sys.menu.title.menu'/><!--菜单-->">
		            <!--不带复选框-->
				    <ul id="parentMenuTree_sy0460" class="ztree">
				    </ul>
				    <a id="childLink_sy0460" href="/sys/menu/getMenuTreeByParentMenu?" target="ajax" rel="MenuEditjbsxBox"></a>
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
										<select  class="combox"  name="CPNY_ID"  id="CPNY_ID"  onchange="addCpnyIdSy0460(this)">
											<c:forEach items="${companyList}" var="company">
											<option <c:if test="${menuP.CPNY_ID eq company.CPNY_ID}">selected</c:if>  value="${company.CPNY_ID}">${company.CONTENT}</option>
											</c:forEach>
										</select>
									</dd>
								</dl>			
								<dl>
									<dt><spring:message code="sys.menu.title.menu"/><!--菜单-->:</dt>
									<dd>
										<input type="hidden" id="MenuNo" name="MENU_NOS" value="${menuP.MENU_NO}" class="required textInput"   />
										<div id="treeDiv" style=" float:left; display:block; overflow:auto;width:600px; height:300px;border:solid 1px #CCC; line-height:21px; background:#FFF;">
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
									<li><div class="button"><div class="buttonContent"><button type="submit">
									<spring:message code="public.title.submit"/><!--提交--></button></div></div></li>
									<li><div class="button"><div class="buttonContent"><button type="button" class="close">
									<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
								</ul>
							</div>
						</form>
					</div>
				</div>
				<!-- 工作区容器结束 -->
			</div>
			<!-- 主体内容结束-->
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
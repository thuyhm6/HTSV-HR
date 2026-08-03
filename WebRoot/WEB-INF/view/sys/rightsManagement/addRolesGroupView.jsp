<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT type='text/javascript'>		
 $(function(){
 	$("#menuHidden").hide();
 	var parentMenuTree = [];
 	<c:forEach items="${info}" var="menu" varStatus="i">
 			parentMenuTree.push({ id: '${menu.MENU_NO}', pid: '${menu.MENU_PARENT_NO}', text: '${menu.MENU_NAME_ZH}' ,depth : '${menu.DEPTH}'});
 	</c:forEach>
 		
  
 
 $('#parentMenu').ligerComboBox({
							width: 250,selectBoxWidth:300,selectBoxHeight: 300,isMultiSelect: false,
							tree:$("#menuTreeForRoles").ligerTree({checkbox:false,nodeWidth:300
							}),
							onSelected:function (note,newText){  
									$.each(parentMenuTree, function(i, item){
										if(note==parentMenuTree[i].id)
											$('#Menudepth').attr('value',parentMenuTree[i].depth);
								 	});
									$('#MenuNo').attr('value',note);
									}
						});
$.each($(".l-checkbox",$("#menuTreeG")),function(index,value){
	$(value).remove();
});				

						 
	});
	function submitFrom()
	{ 
		return false;
		var $form = $("RolesForm");
		if (!$form.valid()) {
			return false;
		}
		$.ajax({
			type: $form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
		return false;
	}
	function selectParent(str){
		document.getElementById("parentMenu").value=str;
	}
	var xmlHttp;
	var time=null;
	function validateExsitSy0110(obj){
		var cpnyId=document.getElementById("CPNY_ID").value; 
		var url='/sys/rightsManagement/checkExistRoleId?ROLE_ID='+obj.value+"&CPNY_ID="+cpnyId;
		if(window.ActiveXObject){
			xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
		}else{
			xmlHttp=new XMLHttpRequest();
		}
		xmlHttp.onreadystatechange = buildExsitSy0110;
		xmlHttp.open("POST",url,false);
		xmlHttp.setRequestHeader("If-Modified-Since","0");                                                        
		xmlHttp.send(null); 
	}
	function buildExsitSy0110(){
	   if(window.ActiveXObject){
			xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
		}else{
			xmlHttp=new XMLHttpRequest();
		}
		if(xmlHttp.readyState ==4){
			if(xmlHttp.status ==200){
				if(xmlHttp.responseText=='no'){
					document.getElementById("redSpan").innerHTML="该法人已经存在这个权限ID"
				}else{
					document.getElementById("redSpan").innerHTML="&nbsp;";
				}
			}
		}
	}
	function validateCallback_sy0110(form, callback) {
		var $form = $(form);
		
		if (!$form.valid()) {
			return false;
		}
		buildExsitSy0110();
		if($('#redSpan').attr('value')=='&nbsp;'){
			return false;
		}
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
		return false;
	} 
</SCRIPT>
<div class="pageContent">
	<form id="RolesForm" method="post" action="/sys/rightsManagement/saveOrUpdateRolesGroupInfo" class="pageForm required-validate" onsubmit="return validateCallback_sy0110(this,navTabAjaxDone);">
		<input type="hidden" name="SYS_TYPE" value="${SYS_TYPE }"/>
		<div class="pageFormContent nowrap" layoutH="67">

			<dl>
				<dt><spring:message code="sys.rights.title.privilegeNo"/><!--权限号-->:</dt>
				<dd> ${roleid2}<span id="redSpan" style="color: red">&nbsp;</span>
				<input type="hidden"   name="ROLE_ID" id="ROLE_ID" value="${roleid2}" ></input>
				</dd>
			</dl>

			<dl>
				<dt><spring:message code="sys.basicMaint.title.companyName"/><!--公司-->:</dt>
				<dd>
					<select name="CPNY_ID" id="CPNY_ID" class="combox">
					<c:forEach items="${cpnyList}" var="cpny">
						<option value="${cpny.CPNY_ID}" <c:if test="${cpny.CPNY_ID eq RolesGroupInfo.CPNY_ID}">selected</c:if>>${cpny.CONTENT}</option>
					</c:forEach>
				</select>
				</dd>
			</dl>
		    <ait:SyLanguage/>
			<dl>
				<dt><spring:message code="sys.menu.title.parentMenu"/><!--父级菜单-->:</dt>
				<dd  id="menuTreeG">
					<input id="parentMenu" size="30"      type="text" />
					<input type="hidden" id="MenuNo" name="MENU_PARENT_NO"  class="required"   />
					<input type="hidden" id="Menudepth" name="DEPTH"  />
				</dd>
			</dl>

			<div id="jbsxBox" class="unitBox" style="margin-left:2px;margin-top:200px;">
 					 
					<!-- 表格主体开始 -->
					<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
						<div class="panelBar">
							<ul class="toolBar" style="float:right;">
								<li><a class="add" href="#"><div class="buttonContent"><button type="submit" >
								<spring:message code="button.sys.affirm.save"/><!--保存--></button></div></a></li>
								<li class="line">line</li>
							</ul>
						</div>
						<table class="table" width="99%" layoutH="5">
							<thead>
								<tr>
									<th><spring:message code="sys.affirm.indexNum"/><!--序号--></th>
									<!--<th><spring:message code="sys.rights.depth"/>深度</th>
									--><th><spring:message code="sys.rights.menuNo"/><!--屏幕编号--></th>
									<th><spring:message code="sys.rights.menuExplain"/><!--屏幕解释--></th>
									<th><spring:message code="button.sys.view"/><!--查看--></th>
									<th><spring:message code="button.add"/><!--添加--></th>
									<th><spring:message code="button.update"/><!--修改--></th>
									<th><spring:message code="button.delete"/><!--删除--></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${codeLists}" var="code" varStatus="i">
								<tr target="sid_obj" rel="${code.CODE_NO}">
									<td>${i.index+1}</td>
									<td>${ code.CODE_ID }</td>
									<td>${ code.CODE_NAME_ZH }</td>
									<td>${ code.CODE_NAME_EN}</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
						<div class="panelBar">
						</div>
					</div>
					<!--表格主体开始 -->
				</div>
		
			</div>
		<div class="formBar">
			<ul>
				<div  id="menuHidden" style="visivility:hidden; "  >
		           	<c:set var="parentSize" value="${fn:length(info)}" />
		            <!--不带复选框-->
				    <ul id="menuTreeForRoles">
				    	<c:forEach items="${info}" var="parentMenu" varStatus="i">
				    		<c:if test="${i.index == 0}" >
				    			<li url="${parentMenu.MENU_NO}" isexpand="false">
						            <span ><a onclick="selectParent('${parentMenu.MENU_NAME}');"  href="/sys/rightsManagement/addRolesGroupMenuView?seach_PARENT_CODE_NO=${parentMenu.MENU_NO}&pageNum=1" target="ajax" rel="jbsxBox">${parentMenu.MENU_NAME}</a></span>
						            <ul>
				    		</c:if>
				    		
				    		<c:if test="${i.index != 0 && parentMenu.DEPTH == 1}" >
						            </ul>
						        </li> 
				    			<li url="${parentMenu.MENU_NO}" isexpand="false">
						            <span ><a onclick="selectParent('${parentMenu.MENU_NAME}');"  href="/sys/rightsManagement/addRolesGroupMenuView?seach_PARENT_CODE_NO=${parentMenu.MENU_NO}&pageNum=1" target="ajax" rel="jbsxBox">${parentMenu.MENU_NAME}</a></span>
						            <ul>
				    		</c:if>
				    		<c:if test="${parentMenu.DEPTH != 1}" >
				    			<li url="${parentMenu.MENU_NO}"><span ><a onclick="selectParent('${parentMenu.MENU_NAME}');"  href="/sys/rightsManagement/addRolesGroupMenuView?seach_PARENT_CODE_NO=${parentMenu.MENU_NO}&pageNum=1" target="ajax" rel="jbsxBox">${parentMenu.MENU_NAME}</a></span></li>
				    		</c:if>
				    	</c:forEach>
							    </ul>
							 </li>
				    </ul>
					</div>
		</div>
	</form>
	
</div>
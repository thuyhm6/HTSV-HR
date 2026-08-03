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
	function choseVale(check,menu_No){
		if($(check).attr("checked")) 
			check.value=1+"|"+menu_No;
		else
			check.value=0+"|"+menu_No;
	}
	function addGroupNo(gourpNo){
		$.each($('a',$('.l-body')),function(index,value){
			newHref=value.href.split("SCREEN_GRANT_NO="); 
			backHref=newHref[0]+"SCREEN_GRANT_NO="+gourpNo.value+"&seach_CPNY_ID="+$("#CPNY_ID").val();
			$(value).attr("href",backHref);
		});
	}
	function addCpnyId(cpnyId){
		$.each($('a',$('.l-body')),function(index,value){
			newHref=value.href.split("CPNY_ID="); 
			backHref=newHref[0]+"CPNY_ID="+cpnyId.value;
			$(value).attr("href",backHref);
		});
	}
	function selectParent(str,idStr){
		document.getElementById("parentMenu").value=str;
	//	/sys/rightsManagement/addRolesGroupMenuView?seach_PARENT_CODE_NO=${parentMenu.MENU_NO}&pageNum=1&seach_ROLE_NO=${roleGroup.ROLE_NO}&seach_CPNY_ID=LOTTE
	}

</SCRIPT>
<div class="pageContent">
	<form  method="post" action="/sys/rightsManagement/updateERolesGroupInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="67">

			<dl>
				<dt><spring:message code="sys.rights.title.privilegeGroup"/><!--权限组-->:</dt>
				<dd>
					 ${roleGroup.ROLE_ID}
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="sys.basicMaint.title.companyName"/><!--公司-->:</dt>
				<dd>
					<input type="hidden" name="CPNY_ID"  id="CPNY_ID" value="${roleGroup.CPNY_ID}"/>
					${roleGroup.CPNY_NAME}
				</dd>
			</dl>
			<!-- <dl>
				<dt>公司:</dt>
				<dd>
					<select name="CPNY_ID" id="CPNY_ID" onchange="addCpnyId(this)">
					<c:forEach items="${cpnyList}" var="cpny">
						<option value="${cpny.CPNY_ID}" <c:if test="${cpny.CPNY_ID eq roleGroup.CPNY_ID}">selected</c:if>>${cpny.CONTENT}</option>
					</c:forEach>
				</select>
				</dd>
			</dl> -->
			<input type="hidden" name="NO" value="${roleGroup.ROLE_NO}"/>
			<input type="hidden" name="ROLE_NO" value="${roleGroup.ROLE_NO}"/>
		    <ait:SyLanguage languageNo="${roleGroup.ROLE_NO}"/>
			<dl>
				<dt><spring:message code="sys.menu.title.parentMenu"/><!--父级菜单-->:</dt>
				<dd id="menuTreeG" >
					<input id="parentMenu" class="required textInput" size="30"  type="text" />
					<a type="hidden" id="parentMenuA"  target="ajax" rel="jbsxBox"></a>
					<input type="hidden" id="MenuNo" name="MENU_PARENT_NO"  class="required"   />
					<input type="hidden" id="Menudepth" name="DEPTH"  />
				</dd>
			</dl>

			<div id="jbsxBox" class="unitBox" style="margin-left:2px;margin-top:200px;">
 					 
					<!-- 表格主体开始 -->
					<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
						<div class="panelBar">
					  
							<ul class="toolBar" style="float:right;">
								<li><a class="add" href="#"><div class="buttonContent"><button type="submit">
								<spring:message code="button.sys.affirm.save"/><!--保存--></button></div></a></li>
								<li class="line">line</li>
							</ul>
							
						</div>
						<table class="table" width="99%" layoutH="160">
							<thead>
								<tr>
									<th><spring:message code="sys.affirm.indexNum"/><!--序号--></th><!--
									<th><spring:message code="sys.rights.depth"/>深度</th>
									--><th><spring:message code="sys.rights.menuNo"/><!--屏幕编号--></th>
									<th><spring:message code="sys.rights.menuExplain"/><!--屏幕解释--></th>
									<th>
										<spring:message code="button.sys.view"/><!--查看-->
										
									
									</th>
									<th><spring:message code="button.add"/><!--添加--></th>
									<th><spring:message code="button.update"/><!--修改--></th>
									<th><spring:message code="button.delete"/><!--删除--></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${subMenuLists}" var="menu" varStatus="i">
								<tr target="CodeNO" rel="${menu.MENU_NO}">
									<td>${i.index+1}</td>
									<td> ${menu.DEPTH}</td>
									<td>${ menu.MENU_NO }<input type="hidden" name="MENU_NOS" value="${menu.MENU_NO}"/></td>
									<td>${ menu.CONTENT}</td>
									<td><input type="checkbox" value="0" name="SELECTR" onclick="choseVale(this,${menu.MENU_NO});"> </input></td>
									<td><input type="checkbox" value="0" name="INSERTR" onclick="choseVale(this,${menu.MENU_NO});"> </input></td>
									<td><input type="checkbox" value="0" name="UPDATER" onclick="choseVale(this,${menu.MENU_NO});"> </input></td>
									<td><input type="checkbox" value="0" name="DELETER" onclick="choseVale(this,${menu.MENU_NO});"> </input></td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
						<div class="panelBar">
							<div class="pages">
								 
								 
							<span> <spring:message code="public.title.gong"/><!--共-->${totalCount}<spring:message code="public.title.tiao"/><!--条--></span>
							</div>
						 
						</div>
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
						            <span ><a onclick="selectParent('${parentMenu.MENU_NAME}');"  href="/sys/rightsManagement/updateRolesGroupMenuView?seach_PARENT_CODE_NO=${parentMenu.MENU_NO}&pageNum=1&seach_ROLE_NO=${roleGroup.ROLE_NO}" target="ajax" rel="jbsxBox">${parentMenu.MENU_NAME}</a></span>
						            <ul>
				    		</c:if>
				    		
				    		<c:if test="${i.index != 0 && parentMenu.DEPTH == 1}" >
						            </ul>
						        </li> 
				    			<li url="${parentMenu.MENU_NO}" isexpand="false">
						            <span ><a onclick="selectParent('${parentMenu.MENU_NAME}');"  href="/sys/rightsManagement/updateRolesGroupMenuView?seach_PARENT_CODE_NO=${parentMenu.MENU_NO}&pageNum=1&seach_ROLE_NO=${roleGroup.ROLE_NO}" target="ajax" rel="jbsxBox">${parentMenu.MENU_NAME}</a></span>
						            <ul>
				    		</c:if>
				    		<c:if test="${parentMenu.DEPTH != 1}" >
				    			<li url="${parentMenu.MENU_NO}"><span ><a onclick="selectParent('${parentMenu.MENU_NAME}');"  href="/sys/rightsManagement/updateRolesGroupMenuView?seach_PARENT_CODE_NO=${parentMenu.MENU_NO}&pageNum=1&seach_ROLE_NO=${roleGroup.ROLE_NO}" target="ajax" rel="jbsxBox">${parentMenu.MENU_NAME}</a></span></li>
				    		</c:if>
				    	</c:forEach>
							    </ul>
							 </li>
				    </ul>
					</div>
			</div>
		
	</form>
	
</div>
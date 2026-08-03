<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT type='text/javascript'>		
 $(function(){
	var parentMenuTree = [];
	$.ajax({
			type:'get',cache:false,contentType:'application/json',
			url:'/sys/menu/getMenuTree',dataType:'json',
			success:function(data){
						$.each(data, function(i, item){
							parentMenuTree.push({ id: item.MENU_NO, pid: item.MENU_PARENT_NO, text: item.MENU_NAME ,depth : item.DEPTH});
						 });
						$('#parentMenu').ligerComboBox({
							width: 250,selectBoxWidth: 300,selectBoxHeight: 300,treeLeafOnly:false,
							tree: {
									data: parentMenuTree,checkbox:false,idFieldName :'id',parentIDFieldName :'pid',
									nodeWidth:300
									},
							onSelected:function (note,newText){ 
									$.each(data, function(i, item){
										if(note==parentMenuTree[i].id)
											$('#Menudepth').attr('value',parentMenuTree[i].depth);
								 	});
									$('#MenuNo').attr('value',note);
									}
						});
			 }
		});
	});
</SCRIPT>
<div class="pageContent">
	<form method="post" action="/sys/menu/updateMenu" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDoneWithForm);">
		<div class="pageFormContent nowrap" layoutH="60">
			
			<dl>
				<dt><spring:message code="sys.menu.title.menuCode"/><!--菜单代码-->:</dt>
				<dd><input type="hidden" name="MENU_NO" value="${menu.MENU_NO }" />
					<input type="text" value="${menu.MENU_CODE}" name="MENU_CODE" class="required textInput" size="30"  />
				</dd>
				<input type="hidden" name="NO" value="${menu.MENU_NO }" />
			</dl>
			
			<ait:SyLanguage languageNo="${menu.MENU_NO }"/>
			<dl>
				<dt><spring:message code="sys.menu.title.parentMenu"/><!--父级菜单-->:</dt>
				<dd>
					<input id="parentMenu" value="${menu.PNAME}"  class="required textInput" size="30"      type="text" />
					<input type="hidden"  value="${menu.MENU_PARENT_NO}" id="MenuNo" name="MENU_PARENT_NO"  class="required"   />
					<input type="hidden" value="${menu.DEPTH}" id="Menudepth" name="DEPTH"  />
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="sys.menu.title.menuURL"/><!--菜单URL-->:</dt>
				<dd>
					<input type="text" value="${menu.MENU_URL}"  name="MENU_URL" class="textInput" size="30"  />
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="hr.viewPersonalInfo.title.ORDERTYPE"/><!--排序-->:</dt>
				<dd>
					<input type="text" name="ORDERNO" value="${menu.ORDERNO}" class="textInput" size="30"  />
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态-->:</dt>
				<dd> 
					<select class="combox" name="ACTIVITY" id="PA_RELEVANCE_FLAG">
						<option value="0" <c:if test="${menu.ACTIVITY eq 0}" >selected</c:if>>
						<spring:message code="sys.arAffirmPost.title.enable"/><!--不启用--></option>
						<option value="1" <c:if test="${menu.ACTIVITY eq 1}" >selected</c:if>>
						<spring:message code="sys.arAffirmPost.title.able"/><!--启用--></option>
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

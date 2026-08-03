<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type='text/javascript'>		
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

 function validateCallbackSy0410(form, callback) {
	    var menuCode=document.getElementById("MENU_CODE").value;
		
		var $form = $(form);
		
		if (!$form.valid()) {
			return false;
		}
		var flagStr="N";
		if(menuCode){
			$.ajax({  
			    async : false,  
			    cache:false,  
			    type: 'POST',  
			    dataType : "json",  
			    url: "/sys/menu/validateMenuIdExist?MENU_CODE="+menuCode,//请求的action路径  
			    error: function () {//请求失败处理函数  
				    alertMsg.error('<spring:message code="alert.message.sys.arAffirm.qequestFailed"/>'); 
			    },  
			    success:function(data){ //请求成功后处理函数。  
			    	flagStr=data.flagYn;
			    	return false; //把后台封装好的简单Json格式赋给treeNodes
			    }  
			});
		}
		if(flagStr=="N"){
		    alertMsg.error('<spring:message code="alert.message.sys.menu.codeIsExsist"/>');
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
</script>
<div class="pageContent">
	<form method="post" action="/sys/menu/saveMenu" class="pageForm required-validate" onsubmit="return validateCallbackSy0410(this, dialogAjaxDoneWithForm);">
		<div class="pageFormContent nowrap" layoutH="60">
			
			<dl>
				<dt><spring:message code="sys.menu.title.menuCode"/><!--菜单代码-->:</dt>
				<dd>
					<input type="text" name="MENU_CODE" id="MENU_CODE" class="required textInput" size="30"  />
				</dd>
			</dl>
			<ait:SyLanguage/>
			<dl>
				<dt><spring:message code="sys.menu.title.parentMenu"/><!--父级菜单-->:</dt>
				<dd>
					<input id="parentMenu" class="required textInput" size="30"      type="text" />
					<input type="hidden" id="MenuNo" name="MENU_PARENT_NO"  class="required"   />
					<input type="hidden" id="Menudepth" name="DEPTH"  />
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="sys.menu.title.menuURL"/><!--菜单URL-->:</dt>
				<dd>
					<input type="text" name="MENU_URL" class="textInput" size="30"  />
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="hr.viewPersonalInfo.title.ORDERTYPE"/><!--排序-->:</dt>
				<dd>
					<input type="text" name="ORDERNO" class="textInput" size="30"  />
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态-->:</dt>
				<dd>
					<select class="combox" name="ACTIVITY" id="PA_RELEVANCE_FLAG">
						<option value="1" selected>
						<spring:message code="sys.arAffirmPost.title.able"/><!--启用-->
						</option>
						<option value="0" >
						<spring:message code="sys.arAffirmPost.title.enable"/><!--不启用-->
						</option>
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

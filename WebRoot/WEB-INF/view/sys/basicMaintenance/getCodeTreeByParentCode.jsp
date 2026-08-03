<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%> 
<SCRIPT type='text/javascript'>	
var myMap = new Map();	
 $(function(){
     $tree = $("#tree12").ligerTree(
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
							/**
							if($(".AliasClass",$(value).parent()).length==0)
								var str="<div class='AliasClass' style='position: absolute;z-index: 91000;margin-left:150px; margin-top:-20px;height:22px; '>";
								var countInt=0;
							
							<c:forEach items="${languageList}" var="lVar">
								var valueAliasZh='输入${lVar.DESCRIPTION}别名';
									
								var idStr=value.parentNode.parentNode.id+"${lVar.LANGUAGE}";
								if($("input[id="+idStr+"]").val()&&$("input[id="+idStr+"]").val()!=""){
									valueAliasZh=$("input[id="+idStr+"]").val();
								}
								if($(".AliasClass",$(value).parent()).length==0){
								    str=str+"<input alias='"+value.parentNode.parentNode.id+"' id='"+value.parentNode.parentNode.id+"Alias${lVar.LANGUAGE}' name='Alias${lVar.LANGUAGE}' value='"+valueAliasZh+
											"' style='position: absolute;margin-left:"+countInt*150+"px;' onclick='clearTitle(this)'/>";
											countInt++;
								  }
							</c:forEach>
							str+="</div>";
							$("span",$(value).parent()).after(str);*/
							
							 /**
							if($(".AliasClass",$(value).parent()).length==0)
							$("span",$(value).parent()).after("<div class='AliasClass' style='position: absolute;z-index: 91000;margin-left:150px; margin-top:-20px;height:22px; '>"+
							"<input alias='"+value.parentNode.parentNode.id+"' id='"+value.parentNode.parentNode.id+"AliasZH' name='AliasZH' value='输入中文别名' style='position: absolute;margin-left:0px;' onclick='clearTitle(this)'/>"+
							"<input alias='"+value.parentNode.parentNode.id+"' id='"+value.parentNode.parentNode.id+"AliasKR' name='AliasKR' value='输入韩文别名' style='position: absolute;margin-left:150px;' onclick='clearTitle(this)'/>"+
							"<input alias='"+value.parentNode.parentNode.id+"' id='"+value.parentNode.parentNode.id+"AliasEN' name='AliasEN' value='输入英文别名' style='position: absolute;margin-left:300px;' onclick='clearTitle(this)'/></div>");
							*/
						});
						$.each($('.l-checkbox-unchecked','#treeDiv'),function(index,value){
							if($(".AliasClass",$(value).parent()).length!=0)
								$(".AliasClass",$(value).parent()).remove();
						});
						$('#CodeNo').attr('value',checkMenuNoForMenuList);
					}   
	          }
          );
 
	});
function validateCallback(form, callback) {
	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	/**if($('#MenuNo').attr('value')==''){
			 alert('请选择菜单！');
			return false;
	}
	
	<c:forEach items="${languageList}" var="lVar">
	
	var valueAliasZh='输入${lVar.DESCRIPTION}别名';
	$.each($("input[name='Alias${lVar.LANGUAGE}']"),function(i,value){
		$(value).hide();
		if(valueAliasZh!=value.value){
			value.value=value.value+"|"+value.alias;
			
			$form.append(value);	
		}
	});
</c:forEach>*/
/**		
$.each($("input[name='AliasZH']"),function(i,value){
	$(value).hide();
	if('输入中文别名'!=value.value){
		value.value=value.value+"|"+value.alias;
		$form.append(value);	
	}
});
$.each($("input[name='AliasKR']"),function(i,value){
	$(value).hide();
	if('输入韩文别名'!=value.value){
		value.value=value.value+"|"+value.alias;
		$form.append(value);
	}	
});
$.each($("input[name='AliasEN']"),function(i,value){
	$(value).hide();
	if('输入英文别名'!=value.value){
		value.value=value.value+"|"+value.alias;
		$form.append(value);	
	}
});*/
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
function clearTitle(value){
	if(value.value=='输入中文别名'||value.value=='输入韩文别名'||value.value=='输入英文别名')
	value.value="";
}
</SCRIPT>
<div class="pageContent">
						<form method="post" action="/sys/basicMaintenance/saveCodeParam" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
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
								</dl><!--  
								<dl>
									<dt>中文别名:</dt>
									<dd>
										<input type="text" name="ALIAS_ZH" class=" textInput" size="30"  />
									</dd>
								</dl>
								
								<dl>
									<dt> 英文别名:</dt>
									<dd>
										<input type="text" name="ALIAS_EN" class="textInput" size="30"  onblur="clearTitle(this);"/>
									</dd>
								</dl>
								
								<dl>
									<dt>韩文别名:</dt>
									<dd>
										<input type="text" name="ALIAS_KR" class="textInput" size="30" />
									</dd>
								</dl>-->
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
									<dt><spring:message code="sys.arAffirmPost.title.code"/><!--代码-->:</dt>
									<dd>
										<input type="hidden" id="CodeNo" name="CODE_NOS"  class="required textInput"   />
										<div id="treeDiv"  style=" float:left; display:block; margin:10px; overflow:auto; width:600px; height:500px; border:solid 1px #CCC; line-height:21px; background:#FFF;">
											<ul id="tree12">
												<c:forEach items="${codeLists}" var="code" varStatus="i">
										    		<c:if test="${i.index == 0}" >
										    			<li id="${code.CODE_NO}" url="${code.CODE_NO}" isexpand="false">
												            <span >${code.CONTENT}</span>
												            <ul>
										    		</c:if>
										    		<c:if test="${i.index != 0 && code.DEPTH == 1}" >
												            </ul>
												        </li> 
										    			<li id="${code.CODE_NO}" url="${code.CODE_NO}" isexpand="false">
												            <span >${code.CONTENT}</span>
												            <ul>
										    		</c:if>
										    		<c:if test="${code.DEPTH != 1}" >
										    			<li id="${code.CODE_NO}" pid="${PARENT_CODE_NO}"  url="${code.CODE_NO}"><span ><a>${code.CONTENT}</a></span></li>
										    		</c:if>
										    	</c:forEach>
							    </ul>
							 </li>
											</ul>
										</div>
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

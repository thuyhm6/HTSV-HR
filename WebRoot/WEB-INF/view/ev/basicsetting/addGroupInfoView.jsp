<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
$(function(){
var parentObjTree = [];
$.ajax({
		type:'get',cache:false,contentType:'application/json',
		url:'/ev/basicsetting/getEvGroupTree',dataType:'json',
		success:function(data){
					$.each(data, function(i, item){
						parentObjTree.push({ id: item.ID, pid: item.PARENT_ID, text: item.TITLE ,depth : item.DEPTH});
					 });
					$('#parentMenu').ligerComboBox({
						width: 250,selectBoxWidth: 300,selectBoxHeight: 300,treeLeafOnly:false,
						tree: {
								data: parentObjTree,checkbox:false,idFieldName :'id',parentIDFieldName :'pid',
								nodeWidth:300
						},
						onSelected:function (note,newText){ 
							$.each(data, function(i, item){
								if(note==parentObjTree[i].id)
									$('#groupDepth').attr('value',parentObjTree[i].depth);
						 	});
							$('#groupNo').attr('value',note);
							}
					
					});
		 }
	});
});
</script>
<div class="pageContent">
	<form id="addGroupInfoView" name="addGroupInfoView" method="post" action="/ev/basicsetting/addGroupInfo" 
			class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="56">
			<dl>
				<dt>标题</dt>
				<dd>
					<input type="text" name="TITLE" class="required">
				</dd>
			</dl>
			<dl>
				<dt>说明</dt>
				<dd>
					<input type="text" name="CONTENT" class="required">
				</dd>
			</dl>
			<dl>
				<dt>父级对象:</dt>
				<dd>
					<input id="parentMenu" class="textInput" size="30" type="text" />
					<input type="hidden" id="groupNo" name="PARENT_ID"   />
					<input type="hidden" id="groupDepth" name="DEPTH"  />
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="hr.viewPersonalInfo.title.ORDERTYPE"/><!--排序-->:</dt>
				<dd>
					<input type="text" name="ORDERNO" class="textInput digits">
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>
	</form>
</div>

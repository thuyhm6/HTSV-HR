<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	 function validateCallbackNewAliasView(form, callback) {
			var $form = $(form);
			if (!$form.valid()) {
				return false;
			} 
			var parameterPa = {};
			var alias=document.getElementsByName("searchNewAlias");
			var init=0;
			if(document.getElementById("RT_NO").value==""){
				document.getElementById("inputItemNo_${ROW_NUM}").value ="";
				document.getElementById("inputItemID_${ROW_NUM}").value ="";
			}
			for(var i=0;i<alias.length;i++){
				if(alias[i].checked==true){ 
					parameterPa['searchNewAlias'+init] = alias[i].value ;
					parameterPa['newAliasTypeAlias'+alias[i].value] = document.getElementById('newAliasTypeAlias'+alias[i].value).value;
					parameterPa['newAliasTableName'+alias[i].value] = document.getElementById('newAliasTableName'+alias[i].value).value;
					parameterPa['newAliasOrderNo'+alias[i].value] = document.getElementById('newAliasOrderNo'+alias[i].value).value;
					parameterPa['newAliasItemNo'+alias[i].value] = document.getElementById('newAliasItemNo'+alias[i].value).value;
					parameterPa['newAliasItemID'+alias[i].value] = document.getElementById('newAliasItemID'+alias[i].value).value;
					if(document.getElementById("RT_NO").value==""){//新添加的表
						var itemValue = document.getElementById("inputItemNo_${ROW_NUM}").value;
						if(itemValue==""){
							document.getElementById("inputItemNo_${ROW_NUM}").value = document.getElementById('newAliasItemNo'+alias[i].value).value;
							document.getElementById("inputItemID_${ROW_NUM}").value = alias[i].value;
						}else{
							document.getElementById("inputItemNo_${ROW_NUM}").value+=","+document.getElementById('newAliasItemNo'+alias[i].value).value;
							document.getElementById("inputItemID_${ROW_NUM}").value+=","+alias[i].value;
						}
					}
					init++;
				}
			}
		 	parameterPa["checkedNum"]=init;
		 	parameterPa["RT_NO"]=document.getElementById('RT_NO').value;
		  	if(document.getElementById("RT_NO").value!=""){
				$.ajax({
					type: form.method || 'POST',
					url:$form.attr("action"),
					data:parameterPa,
					dataType:"json",
					cache: false,
					success: callback || DWZ.ajaxDone,
					error: DWZ.ajaxError
				});
		  	}else{
		  	  $.pdialog.closeCurrent();
		 	}
			return false;
		}
</script>
<div class="pageContent">
<form method="post" action="/sys/pageStructure/addNewAliasInfo" class="pageForm required-validate" onsubmit="return validateCallbackNewAliasView(this,dialogAjaxDone);">
	<table class="table" width="101%" layoutH="138" nowrapTD="true">
		<thead>
			<tr>
				<th width="80"><label style="float: left">
				<input type="checkbox" class="checkboxCtrl" group="searchNewAlias" />
				 &nbsp;
			</label></th>
				<th width="100">项目名称</th>
			</tr>
		</thead>
		<tbody>
		    <input type="hidden" id="RT_NO" name="RT_NO" value="${RT_NO}"/>
			<c:forEach items="${aliasList}" var="item" varStatus="i">
				<tr target="sid" rel="${item.ITEM_ID}">
					<td width="5%"> 
						<input type="checkbox" name="searchNewAlias" value="${item.ITEM_ID}" />
						<input type="hidden" id="newAliasTypeAlias${item.ITEM_ID}" name="newAliasType" value="${item.TYPE}" />
						<input type="hidden" id="newAliasTableName${item.ITEM_ID}" name="newAliasTableName"  value="${item.TABLE_NAME}" />
						<input type="hidden" id="newAliasOrderNo${item.ITEM_ID}" name="newAliasOrderNo" value="${item.ORDER_NO}" />
						<input type="hidden" id="newAliasItemNo${item.ITEM_ID}" name="newAliasItemNo" value="${item.ITEM_NO}" />
						<input type="hidden" id="newAliasItemID${item.ITEM_ID}" name="newAliasItemID" value="${item.ITEM_ID}" />
					</td>
					<td>${item.ITEM_NAME}</td>
				</tr>
			</c:forEach>
			
		</tbody>
	</table>
	 <div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
	 </div>
</form>
</div>

<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	 function deleteItemAliasView(obj,item_no){
		var rowNum = obj.parentElement.parentElement.parentElement.rowIndex;
		var tableItem = document.getElementById("updateItemTable");
		tableItem.deleteRow(rowNum);
		var v=document.getElementById("delete_item_no");
		if(v.value==""){
			document.getElementById("delete_item_no").value=item_no;
		}else{
			document.getElementById("delete_item_no").value+=","+item_no;
		}
	 }
</script>
<div class="pageContent">
<form method="post" action="/sys/pageStructure/updateNewAliasInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
	<table class="table" width="101%" layoutH="138" nowrapTD="true" >
		<thead>
			<tr>
				 <th width="80"><label style="float: left">
				 序号
			</label></th>
				<th width="100">项目名称
					<input type="hidden" id="delete_item_no" name="delete_item_no" value=""/>
					<input type="hidden" id="RT_NO" name="RT_NO" value="${RT_NO}"/>
				</th>
				<th width="100">&nbsp;</th>
			</tr>
		</thead>
		<tbody id="updateItemTable">
			<c:forEach items="${aliasItemList}" var="item" varStatus="i">
				<tr target="sid" rel="${item.ITEM_NO}">
					<td width="5%"> 
						 ${i.count}
					</td>
					<td>${item.ITEM_NAME}</td>
					<td><a onclick="deleteItemAliasView(this,'${item.ITEM_NO}');" style="cursor:hand" >删除</a></td>
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

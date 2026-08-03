<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function delete_EditionItemTypeParamInfo(callback){
		var checked=false;
		var ids= document.getElementsByName("chooseOne");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
			}
		}
		if(!checked){
			//请选择信息再进行删除操作!
			alert("<spring:message code='ar.alert.message.viewArAnnualStandard.choosedelete'/>");
			return ;
		}
		var jsonData = '[';

		$.each($("input[name='chooseOne']"),
		function(i, obj) {
			if (obj.checked) {
				
				if (jsonData.length > 1) {

					jsonData += ',{';
				} else {
					jsonData += '{';
				}

				jsonData += ' "EDITION_ITEM_NO": "' + obj.value + '"';
				//jsonData += ' "CPNY_ID": "' + defaultCpny + '"';
				jsonData += '}';

			}
		});
		jsonData += ']';

		if (jsonData.length == 2) {
			//请选择要删除的数据
			alertMsg.error("<spring:message code='ar.alert.message.viewArAnnualStandard.chooseinfo'/> ");
			return;
		}
		
		//确定要提交吗？
		if (confirm ("<spring:message code='ar.alert.message.viewArAnnualStandard.consubmit'/>")){	
			$.ajax({
				type: 'POST',
				url: '/ess/dimissionApply/deleteEditionItemTypeParamInfo',
				data: [{ name: 'jsonData', value: jsonData }],
				dataType:"json",
				cache: false,
				success: callback || DWZ.ajaxDone,
				error: DWZ.ajaxError
			});	
		}
	}
</script>
<div class="pageContent">
	<form id="viewEditionItemTypeParamList_sys2014" onsubmit="return divSearch(this, 'viewEditionItemTypeData');" 
	action="/ess/dimissionApply/viewEditionItemTypeParamList?pageNum=1&seach_EDITION_NO=${EDITION_NO1}&seach_EDITION_ITEM_TYPE=${EDITION_ITEM_TYPE}&CPNY_ID=${CPNY_ID}&numPerPage=${numPerPage}" method="post">

	<c:set value="dialog" var="add_tab"/>
	<c:set value="500" var="add_width"/>
	<c:set value="400" var="add_height"/>
	<c:set value="/ess/dimissionApply/addEditionItemTypeParamInfoView?EDITION_NO=${EDITION_NO1}&EDITION_ITEM_TYPE=${EDITION_ITEM_TYPE }" var="add_Url"/>
	<c:set value="0" var="delete_target_exit"/>
	<c:set value="javascript:delete_EditionItemTypeParamInfo(navTabAjaxDone);" var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="600" var="edit_width"/>
	<c:set value="400" var="edit_height"/>
	<c:set value="/ess/dimissionApply/updateEditionItemTypeParamInfoView?EDITION_NO1=${EDITION_NO1 }&EDITION_ITEM_TYPE=${EDITION_ITEM_TYPE }&EDITION_ITEM_NO={EDITION_ITEM_NO}" var="edit_Url"/>

	<%@ include file="/WEB-INF/view/inc/includeButton2.jsp"%>
</div>	

	<table class="table" width="99%" layoutH="260">
		<thead>
			<tr>
				<th width="10%"><input type="checkbox" class="checkboxCtrl" group="chooseOne"/></th>
				<th width="30"><!-- 交接项目 --><spring:message code="ess.edition.title.editionItem"/></th>
				<th width="50"><!-- 描述 --><spring:message code="ar.viewcycle.title.miaoshu"/></th>
				<th width="10">显示顺序</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${editionItemParamList}" var="list">
				<tr target="EDITION_ITEM_NO" rel="${list.EDITION_ITEM_NO}" >
				    <td><input type="checkbox" name="chooseOne" value="${list.EDITION_ITEM_NO}"/></td>
				    <td>${list.EDITION_PARAM_NAME}</td>
				    <td>${list.REMARK}</td>
				    <td>${list.ORDERNO}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
</div>
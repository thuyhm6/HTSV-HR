<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function delete_EditionItemTypeInfo(callback){
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

		//var defaultCpny = $("#defaultCpny").val();
		//json传值
		var jsonData = '[';

		$.each($("input[name='chooseOne']"),
		function(i, obj) {
			if (obj.checked) {
				
				if (jsonData.length > 1) {

					jsonData += ',{';
				} else {
					jsonData += '{';
				}

				jsonData += ' "EDITION_ITEM_TYPE": "' + obj.value + '"';
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
				url: '/ess/dimissionApply/deleteEditionItemTypeInfo',
				data: [{ name: 'jsonData', value: jsonData }],
				dataType:"json",
				cache: false,
				success: callback || DWZ.ajaxDone,
				error: DWZ.ajaxError
			});	
		}
	}
</script>
<div class="pageHeader">
     <form onsubmit="return navTabSearch(this);" action="/ess/dimissionApply/viewEditionItemTypeList" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
	              <td>
	               	<spring:message code="ess.dimission.title.editionnumber"/><!--版本号-->：
	               	<input type="text" name="seach_EDITION_NO" value="${EDITION_NO}"/>
	               	<spring:message code="ess.edition.title.editionItemType"/><!--交接类型-->：
	               	<input type="text" name="seach_EDITION_ITEM_TYPE" value="${EDITION_ITEM_TYPE}"/>
	              </td> 
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
					<spring:message code="public.title.search"/><!-- 检索 --></button></div></div></li>
				</ul>
			</div>
		</div>
	</form>	
</div>
<div class="pageContent">
  	<c:set value="dialog" var="add_tab"/>
	<c:set value="600" var="add_width"/>
	<c:set value="400" var="add_height"/>
	<c:set value="/ess/dimissionApply/addEditionItemTypeInfoView" var="add_Url"/>
	<c:set value="0" var="delete_target_exit"/>
	<c:set value="javascript:delete_EditionItemTypeInfo(navTabAjaxDone); " var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="600" var="edit_width"/>
	<c:set value="400" var="edit_height"/>
	<c:set value="/ess/dimissionApply/updateEditionItemTypeInfoView?EDITION_ITEM_TYPE={EDITION_ITEM_TYPE}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton2.jsp"%>	
  <table width="100%" class="table" layoutH="206">
	<thead>
	  <tr>
	  	<th width="10%"><input type="checkbox" class="checkboxCtrl" group="chooseOne"/></th>
		<th width="10%"><spring:message code="ess.dimission.title.editionnumber"/><!--版本号 --></th>
		<th width="15%"><spring:message code="ess.edition.title.editionItemType"/><!-- 交接类型--></th>
		<th width="55%"><spring:message code="ar.viewItem.title.shuoming"/><!-- 说明 --></th>
		<th width="55%">显示顺序</th>
	  </tr>
	</thead>
   <tbody>
  <c:forEach items="${editionItemTypeList }" var="corp">
	<tr target="EDITION_ITEM_TYPE" rel="${corp.EDITION_ITEM_TYPE}&EDITION_NO=${corp.EDITION_NO}&CPNY_ID=${corp.CPNY_ID}">
		<td><input type="checkbox" name="chooseOne" value="${corp.EDITION_ITEM_TYPE}"/>
<!-- 		<input type="hidden" name="EDITION_NO" value="${corp.EDITION_NO}"/> -->
		</td>
		<td>${corp.EDITION_NO }</td>
		<td>${corp.EDITION_ITEM_NAME }</td>
		<td>${corp.REMARK }</td>
		<td>${corp.ORDERNO }</td>
	</tr>
  </c:forEach>
   </tbody>
</table>
 <c:set value="/ess/dimissionApply/viewEditionItemTypeList" var="pageUrl"/> 
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%> 
</div>

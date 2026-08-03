<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function delete_DimissionEditionInfo(callback){
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
				jsonData += ' "EDITION_NO": "' + obj.value + '"';
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
				url: '/ess/dimissionApply/deleteDimissionEditionInfo',
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
     <form onsubmit="return navTabSearch(this);" action="/ess/dimissionApply/viewDimissionEditionList" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
	              <td>
	               	<spring:message code="ess.dimission.title.editionnumber"/><!--版本号-->：
	               	<input type="text" name="seach_EDITION_NO" value="${EDITION_NO}"/>
<!-- 	               	<spring:message code="is.company.title.PERSON_TYPE"/>人员类型： -->
<!-- 	               	<input type="text" name="seach_EMP_TYPE_NAME" value="${EMP_TYPE_NAME}"/> -->
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
	<c:set value="700" var="add_width"/>
	<c:set value="550" var="add_height"/>
	<c:set value="/ess/dimissionApply/addDimissionEditionView" var="add_Url"/>
	<c:set value="0" var="delete_target_exit"/>
	<c:set value="javascript:delete_DimissionEditionInfo(navTabAjaxDone); " var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="700" var="edit_width"/>
	<c:set value="550" var="edit_height"/>
	<c:set value="/ess/dimissionApply/updateDimissionEditionInfoView?EDITION_NO={EDITION_NO}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>	
  <table width="100%" class="table" layoutH="206">
	<thead>
	  <tr>
	  	<th width="10%"><input type="checkbox" class="checkboxCtrl" group="chooseOne"/></th>
		<th width="10%"><spring:message code="ess.dimission.title.editionnumber"/><!--版本号 --></th>
		<th width="15%"><spring:message code="is.company.title.PERSON_TYPE"/><!-- 人员类型--></th>
		<th width="55%"><spring:message code="ar.viewItem.title.shuoming"/><!-- 说明 --></th>
		<th width="10%"><spring:message code="sys.essParam.title.ifEnabled"/><!-- 是否启用 --></th>
	  </tr>
	</thead>
   <tbody>
  <c:forEach items="${editionList }" var="corp">
	<tr target="EDITION_NO" rel="${corp.EDITION_NO}">
		<td><input type="checkbox" name="chooseOne" value="${corp.EDITION_NO}"/></td>
		<td style="color: blue;"><a target="navTab" href="/ess/dimissionApply/viewEditionItemTypeData?EDITION_NO=${corp.EDITION_NO}&pageNum=1&navTabId=sys2015" title="版本交接项目管理">${corp.EDITION_NO }</a></td>
		<td title="${corp.EMP_TYPE_NAME }">
		    ${corp.EMP_TYPE_NAME }
		</td>
		<td>${corp.REMARK }</td>
		<td><c:if test="${corp.ACTIVITY==0}"><spring:message code="sys.arAffirmPost.title.enable"/></c:if>
		<c:if test="${corp.ACTIVITY==1}"><spring:message code="sys.arAffirmPost.title.able"/></c:if>
		</td>
	</tr>
  </c:forEach>
   </tbody>
</table>
 <c:set value="/ess/dimissionApply/viewDimissionEditionList" var="pageUrl"/> 
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%> 
</div>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function delete_IsCompanyInfo(callback){
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

				jsonData += ' "CP_NO": "' + obj.value + '"';
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
				url: '/is/insurancecompany/deleteIsCompanyInfo',
				data: [{ name: 'jsonData', value: jsonData }],
				dataType:"json",
				cache: false,
				success: callback || DWZ.ajaxDone,
				error: DWZ.ajaxError
			});	
		}
	}
</script>
<form onsubmit="return navTabSearch(this);" action="/is/insurancecompany/viewCompanyMaintainList" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
	              <td>
	               	<spring:message code="pa.wagebase.title.companyName"/><!--公司名称-->：
	               	<input type="text" name="seach_CP_NAME" value="${searchMap.seach_CP_NAME}"/>
	               	<spring:message code="hr.viewCondSql.title.FEIYONG"/><!--维护费用-->：
	               	<input type="text" name="seach_COST" value="${searchMap.seach_COST}"/>
		            <spring:message code="is.company.title.PERSON_TYPE"/><!-- 人员类型 -->：
		            <c:if test="${defaultCpny ne 'TSTO'}">
 		            <ait:SelectSyCodeByCpnyID id="seach_PERSON_TYPE" name="seach_PERSON_TYPE" parentNo="1368" selected="${PERSON_TYPE}" cnpyID="${defaultCpny}" limit="all"/> 
                    </c:if>
                    <c:if test="${defaultCpny eq 'TSTO'}">
                    <select name="seach_PERSON_TYPE" value="${PERSON_TYPE}">
                        <option value=""></option>
                        <option value="0" <c:if test="${PERSON_TYPE eq 0}">selected</c:if>>促销员</option>
                        <option value="1" <c:if test="${PERSON_TYPE eq 1}">selected</c:if>>其他</option>
                    </select>
                    </c:if>
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
	</div>
<div class="tabsContent">
  	<c:set value="dialog" var="add_tab"/>
	<c:set value="450" var="add_width"/>
	<c:set value="520" var="add_height"/>
	<c:set value="/is/insurancecompany/addIsCompanyInfoView" var="add_Url"/>
	<c:set value="0" var="delete_target_exit"/>
	<c:set value="javascript:delete_IsCompanyInfo(navTabAjaxDone); " var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="450" var="edit_width"/>
	<c:set value="520" var="edit_height"/>
	<c:set value="/is/insurancecompany/updateIsCompanyInfoView?CP_NO={CP_NO}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton2.jsp"%>
  <table width="100%" class="table" layoutH="206">
	<thead>
	  <tr>
	  	<th><input type="checkbox" class="checkboxCtrl" group="chooseOne"/></th>
		<th><spring:message code="pa.wagebase.title.companyName"/><!-- 公司名称 --></th>
		<th><spring:message code="hr.viewPersonalInfo.title.fulidiqu"/><!-- 公司地区 --></th>
		<th><spring:message code="hr.viewCondSql.title.FEIYONG"/><!-- 维护费 --></th>
		<th><spring:message code="is.company.title.PERSON_TYPE"/><!-- 人员类型 --></th>
		<th><spring:message code="ar.viewarcardrecord.title.beizhu"/><!-- 备注 --></th>
	  </tr>
	</thead>
   <tbody>
  <c:forEach items="${corpList }" var="corp">
	<tr target="CP_NO" rel="${corp.CP_NO}">
		<td><input type="checkbox" name="chooseOne" value="${corp.CP_NO}"/></td>
		<td align="left">${corp.CP_NAME }</td>
		<td align="left">${corp.CP_ADDR }</td>
		<td>${corp.COST }</td>
		<c:if test="${defaultCpny eq 'TSTO'}">
		<td align="left"><c:if test="${corp.PERSON_TYPE eq '0'}">促销员</c:if>
		<c:if test="${corp.PERSON_TYPE eq '1'}">其他</c:if>
		</td>
		</c:if>
		<c:if test="${defaultCpny ne 'TSTO'}">
		<td align="left">
		${corp.JOB_TYPE_NAME}
		</td>
		</c:if>
		<td align="left">${corp.REMARK }</td>
	</tr>
  </c:forEach>
   </tbody>
</table>
</div>
</form>	
 <c:set value="/is/insurancecompany/viewCompanyMaintainList" var="pageUrl"/> 
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%> 

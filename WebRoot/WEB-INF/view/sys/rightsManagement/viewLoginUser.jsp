<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function  openLoginUserExecl(a){
    	var $this=$(a);
      	var title = $this.attr("title"); 
      	var $from = $("#viewLoginUser");  
      
	  	var url ="/sys/rightsManagement/viewLoginUserExcel";
	   	alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
					}});
    
 	}
 </script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/sys/rightsManagement/viewLoginUser" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td>
					 <select name="seach_COL_NAME" id="seach_COL_NAME" >
			                <option value="EMPID" <c:if test="${ COL_NAME == 'EMPID' }">selected</c:if> ><!--社号/姓名--><spring:message code="public.title.empIdAndName"/></option>
			                <option value="USER_NAME" <c:if test="${ COL_NAME == 'USER_NAME' }">selected</c:if> >
			                <spring:message code="sys.rights.title.userName"/><!--用户名--></option>
			                <option value="SCREEN_NAME" <c:if test="${ COL_NAME == 'SCREEN_NAME' }">selected</c:if> >
			                <spring:message code="sys.rights.title.privilegeGroup"/><!--权限组--></option>
		             </select>
				</td>
				<td>
					<input name="seach_COL_VAL" type="text" id="seach_COL_VAL" value="${COL_VAL}" />
					<input name="seach_IT_YN" type="hidden" value="IT_NOT"/>
				</td>
				 
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">
					<spring:message code="public.title.search"/><!-- 检索 --></button></div></div>
				</li>
				<li>
				  <a class="button" onclick="openLoginUserExecl(this)" <%--是否导出?--%> title="<spring:message code='rp.report.title.exportYN'/>">
				  <span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
	           	</li>
			</ul>
			
		</div>
	</div>
	</form>
</div>

<div class="pageContent">
	<c:set value="navTab" var="add_tab"/>
	<c:set value="800" var="add_width"/>
	<c:set value="400" var="add_height"/>
	<c:set value="/sys/rightsManagement/addLoginUserView" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/sys/rightsManagement/deleteLoginUser?USER_NO={sid}" var="delete_Url"/>
	<c:set value="navTab" var="edit_tab"/>
	<c:set value="800" var="edit_width"/>
	<c:set value="400" var="edit_height"/>
	<c:set value="/sys/rightsManagement/updateLoginUserView?USERNO={sid}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
			    <th width="60"><spring:message code="sys.rights.title.employeeType"/><!--用户类型--></th>
				<th width="80"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="80"><spring:message code="public.title.name"/><!--姓名--></th>
				<th width="80"><spring:message code="public.title.deptName"/><!--部门--></th>
				<th width="80"><spring:message code="sys.rights.title.userName"/><!--用户名--></th>
				<th width="150"><spring:message code="sys.rights.title.privilegeGroup"/><!--权限组--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${loginUserList}" var="loginUser" varStatus="i">
			
				<tr target="sid" rel="${loginUser.USER_NO}&PERSON_ID=${loginUser.PERSON_ID}">
				
				    <td><c:if test="${loginUser.SPECIAL_PARAM eq 'special'}">
				    <spring:message code="sys.rights.title.specialEmployee"/><!--特殊用户--></c:if>
				    <c:if test="${loginUser.SPECIAL_PARAM eq 'manager'}">
				    <spring:message code="sys.rights.title.adminstrator"/><!--管理者--></c:if>
				     <c:if test="${loginUser.SPECIAL_PARAM eq 'general'}">
				     <spring:message code="sys.rights.title.commonEmployee"/><!--普通用户--></c:if>
				    </td>
					<td>${loginUser.EMPID}</td>
					<td>${loginUser.LOCAL_NAME}</td>
					<td>${loginUser.DEPT_NAME_ZH}</td>
					<td>${loginUser.USER_NAME}</td>
					<td>
						<c:forEach items="${loginUser.relationList}" var="relation">
						<c:if test="${relation.CHECKED eq '1'}">${relation.GROUPNAME}</c:if>
						</c:forEach>
					 &nbsp;
					</td>
				</tr>
			
			</c:forEach>
			
		</tbody>
	</table>
	
		<c:set value="/sys/rightsManagement/viewLoginUser" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	
</div>

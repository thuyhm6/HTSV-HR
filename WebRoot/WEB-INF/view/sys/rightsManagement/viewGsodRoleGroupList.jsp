<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/sys/rightsManagement/viewGsodRoleGroupList" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
			
			    <td>
					<spring:message code="sys.gsodrole.title.gsodname"/><!--权限组名称-->：
					<input type="text" name="seach_ROLE_GSOD_NAME" value="${ROLE_GSOD_NAME}"/>
				</td>
				
				<td>
					<spring:message code="sys.rights.title.privilegeGroupName"/><!--权限组名称-->：
					<input type="text" name="seach_SY_ROLE_GROUP_NAME" value="${SY_ROLE_GROUP_NAME}"/>
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
	


<div class="pageContent">
	<c:set value="navTab" var="add_tab"/>
	<c:set value="addGsodRoleGroupInfoView" var="add_rel"/>
	<c:set value="/sys/rightsManagement/addGsodRoleGroupInfoView" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/sys/rightsManagement/deleteGsodRoleGroupInfo?GROUPNO={GROUPNO}" var="delete_Url"/>
	<c:set value="navTab" var="edit_tab"/>
	<c:set value="updateGsodRoleGroupInfoVew" var="edit_rel"/>
	<c:set value="/sys/rightsManagement/updateGsodRoleGroupInfoVew?GROUPNO={GROUPNO}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="150"><spring:message code="sys.gsodrole.title.gsodname"/><!--权限组名称--></th>
				<th width="150"><spring:message code="sys.rights.title.privilegeGroupName"/><!--权限组名称--></th>
				<th width="150">法人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${gsodRoleGroupList}" var="item" varStatus="i">
				<tr target="GROUPNO" rel="${item.GROUPNO}&ROLE_GSOD_NO1=${item.ROLE_GSOD_NO }&pageNum=1">
					<td>${item.ROLE_GSOD_NAME}
					</td>
					<td>${item.SY_ROLE_GROUP_NAME}</td>
					<td>${item.CPNY_ID}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>	
	
	</div>
	</form>
	<c:set value="/sys/rightsManagement/viewGsodRoleGroupList" var="pageUrl"/>
        <%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>

</div>
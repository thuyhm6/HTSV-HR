<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include  file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<style>
<!--
.gridScroller td div{
		text-align: center;
	}
-->
</style>
<script type="text/javascript">

</script>
<div class="pageHeader">
	<form action="/ev/basicsetting/viewGroupInfo" method="post" name="searchForm" onsubmit="return navTabSearch(this)">
	<input type="hidden" name="pageNum" value="1" />
	<div class="searchBar">
	<div class="subBar">
		<ul>
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						 <button type="submit">&nbsp;Button&nbsp;</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
   </div>
 </form>
</div>
<div class="pageContent">
<c:set value="dialog" var="add_tab"/>
	<c:set value="600" var="add_width"/>
	<c:set value="520" var="add_height"/>
	<c:set value="/ev/basicsetting/addGroupInfoView" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/ev/basicsetting/deleteGroupInfo?ID={ID}" var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="600" var="edit_width"/>
	<c:set value="520" var="edit_height"/>
	<c:set value="/ev/basicsetting/updateGroupInfoView?ID={ID}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>	
  <table width="980" class="table">
	<thead>
	  <tr>
		<th>标题</th>
		<th><spring:message code="ar.viewItem.title.shuoming"/><!-- 说明 --></th>
		<th>父级对象</th>
		<th><spring:message code="sys.basic.title.createBy"/><!--创建者--></th>
		<th><spring:message code="sys.basic.title.createDate"/><!--创建时间--></th>
		<th><spring:message code="hr.viewPersonalInfo.title.ORDERTYPE"/><!--排序--></th>
		<th><spring:message code="sys.arAffirmPost.title.ableStatus"/> <!--启用状态--></th>
	  </tr>
	</thead>
  <c:forEach items="${objList }" var="item">
	<tr target="ID" rel="${item.ID}">
		<td>${item.TITLE }
		</td>
		<td>${item.CONTENT }</td>
		<td>${item.PNAME }</td>
		<td>${item.CREATE_BY }</td>
		<td>${item.CREATE_DATE }</td>
		<td>${item.ORDERNO }</td>
		<td>
			<c:if test="${item.ACTIVITY eq 1}">启用</c:if>
			<c:if test="${item.ACTIVITY eq 0}">关闭</c:if>
		</td>
	</tr>
  </c:forEach>
</table>
</div>
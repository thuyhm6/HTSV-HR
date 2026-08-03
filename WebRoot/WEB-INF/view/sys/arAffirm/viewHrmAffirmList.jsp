<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
</script> 
<div class="pageHeader">
 
	<form onsubmit="return navTabSearch(this);" action="/sys/arAffirm/viewArAffirmList" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
	              <td>
					<spring:message code="sys.affirm.title.applyType"/><!--申请类型-->：<select name="seach_APPLY_TYPE">
					    <option value="">select</option>
						<c:forEach items="${applyList}" var="apply">
						  <option value="${apply.CODE_NO}" <c:if test="${apply.CODE_NO eq APPLY_TYPE}">selected="selected"</c:if>>${apply.CODENAME}</option>
						</c:forEach>
					</select>
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
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="/sys/hrmAffirm/addHrmAffirmView" target="navTab" mask="true" width="500" height="400" ><span>
			<spring:message code="button.add"/><!--添加--></span></a></li>
			<li><a class="delete" id="deleteButton" href="/sys/hrmAffirm/deleteHrmAffirmInfo?TRANS_PARAM_NO={sid}" 
			       target="ajaxTodo" title="<spring:message code='button.delete.sure'/>"><span>
			<spring:message code="button.delete"/><!--删除--></span></a></li>
			<li><a class="edit" id="editButton" href="/sys/hrmAffirm/updateHrmAffirmView?TRANS_PARAM_NO={sid}" target="navTab" mask="true" width="500" height="400" ><span>
			<spring:message code="button.update"/><!--修改--></span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138" nowrapTD="false">
			<thead>
			<tr>
				<th><spring:message code="sys.affirm.title.transactionType"/><!--发令类型--></th>   
				<th><spring:message code="sys.affirm.title.affirmCategory"/><!--决裁类别--> </th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${hrmAffirmList}" var="item" varStatus="i">
			
				<tr target="sid" rel="${item.TRANS_PARAM_NO}">
				    <td>${item.APPLY_NAME}</td>
					<td> 
						<a rel="affirmDetail"   href="/sys/arAffirm/getShowDetail?PARAM_NO=${item.PARAM_NO}"  target="dialog" mask="true" width="200" height="200" >
						<spring:message code="sys.affirm.title.viewDetail"/><!--查看详细--></a>
					</td>
					
				</tr>
			
			</c:forEach>
			
		</tbody>
		
	</table>
	 <div id="affirmDetail" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
	<c:set value="/sys/affirm/viewAffirmList" var="pageUrl"/>
	 <%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>

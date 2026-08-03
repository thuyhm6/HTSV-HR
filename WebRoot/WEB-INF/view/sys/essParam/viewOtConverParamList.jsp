<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/sys/essParam/viewOtConverParam" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="ess.viewApply.title.overtimeApplyType"/><!--加班类型-->NO/Name:
				</td>
				<td>
					<input name="seach_PARAM_NAME" id="seach_PARAM_NAME" type="text" value="${PARAM_NAME}" />
				</td>
				<td>
					<spring:message code="ess.viewApply.title.overtimeTranslate"/><!--加班转换-->NO/Name:
				</td>
				<td>
					<input name="seach_PARAM_VALUE_NAME" id="seach_PARAM_VALUE_NAME" type="text" value="${PARAM_VALUE_NAME}" />
				</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">
					<spring:message code="public.title.search"/><!-- 检索 --></button></div></div>
				</li>
			</ul>
		</div>
	</div>
	</form>
</div>

<div class="pageContent">
	<c:set value="dialog" var="add_tab"/>
	<c:set value="300" var="add_width"/>
	<c:set value="300" var="add_height"/>
	<c:set value="/sys/essParam/addOtConverParamView" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/sys/essParam/deleteOtConverParam?PARAM_NO_SEQ={sid}" var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="300" var="edit_width"/>
	<c:set value="300" var="edit_height"/>
	<c:set value="/sys/essParam/updateOtConverParamView?PARAM_NO_SEQ={sid}" var="edit_Url"/>
	
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
			    <th width="10%"><!--序号-->
			    	<spring:message code="pa.insurance.title.orderNo"/>
			    </th>
			    <th width="15%"><!--加班类型NO-->
			    	<spring:message code="ess.viewApply.title.overtimeApplyType"/>NO
			    </th>
				<th width="20%"><!--加班类型-->
			    	<spring:message code="ess.viewApply.title.overtimeApplyType"/>
			    </th>
				<th width="15%"><!--加班转换NO-->
					<spring:message code="ess.viewApply.title.overtimeTranslate"/>NO
				</th>
				<th width="20%"><!--加班转换-->
					<spring:message code="ess.viewApply.title.overtimeTranslate"/>
				</th>
				<th width="20%"><!--是否使用-->
					<spring:message code="sys.postManage.title.ifUsed"/>
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${otConverParamList}" var="otConverParam" varStatus="i">
				<tr target="sid" rel="${otConverParam.PARAM_NO_SEQ}">
					<td>${i.index+1 }</td>
					<td>${otConverParam.PARAM_NO }</td>
					<td>${otConverParam.PARAM_NAME }</td>
					<td>${otConverParam.PARAM_VALUE }</td>
					<td>${otConverParam.PARAM_VALUE_NAME }</td>
					<td>
						<c:if test="${otConverParam.ACTIVITY eq '1' }">
							<font color="green"><!--是-->
								<spring:message code="sys.affirm.title.yes"/>
							</font>
						</c:if>
						<c:if test="${otConverParam.ACTIVITY ne '1' }">
							<font color="red"><!--否-->
								<spring:message code="sys.affirm.title.no"/>
							</font>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<c:set value="/sys/essParam/viewOtConverParam" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
